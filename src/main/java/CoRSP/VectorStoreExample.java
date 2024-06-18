/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoRSP;

/**
 *
 * @author manoj.kumar
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import static org.mapdb.Serializer.UUID;

public class VectorStoreExample {
    public static void main(String[] args) {
        MapDBVectorStore store = new MapDBVectorStore("vectors.db");

        EmbeddingService embeddingService = new EmbeddingService();
        String text = "This is a sample text for embedding.";
        try {
            double[] embeddings = embeddingService.getEmbeddingFromAPI(text);
            System.out.print("Received embeddings: [");
            for (double embedding : embeddings) {
                System.out.print(embedding + ", ");
            }
            System.out.println("]");

            // Create a vector
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("category", "text");
            Vector vector = new Vector("vec1", text, embeddings, metadata);

            // Insert the vector
            store.insertVector(vector);

            // Retrieve the vector
            Vector retrievedVector = store.getVector("vec1");
            System.out.println(retrievedVector.getContent());

            // Update the vector
            vector.setContent("Updated content");
            store.updateVector(vector);

            // Delete the vector
            store.deleteVector("vec1");

            //store.close();
        } catch (IOException e) {
            System.err.println("Error retrieving embeddings: " + e.getMessage());
        }
        
        try {
            //Sentence split test
            testSentenceSplit(store);
            //Test similarity
            test(store);
        } catch (IOException ex) {
            Logger.getLogger(VectorStoreExample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(VectorStoreExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Utility method to calculate cosine similarity between two vectors
    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
        if (vectorA.length != vectorB.length) {
            throw new IllegalArgumentException("Vectors must be of the same length");
        }
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public static void test(MapDBVectorStore store) throws IOException, InterruptedException {
        // Open database
        DB db = DBMaker.fileDB("vectorstoresearchtest.db").make();

        // Create vector store
        Map<String, Vector> vectorStore = db.treeMap("vectorStore", Serializer.STRING, new VectorGroupSerializer()).createOrOpen();

        // Add multiple texts to the vector store
        String[] texts = {
            "hello world",
            "goodbye world",
            "hello everyone",
            "goodbye everyone"
        };

        EmbeddingService embeddingService = new EmbeddingService();

        //Uncomment below for testing the data inserts
//        for (String text : texts) {
//            double[] embedding = embeddingService.getEmbeddingFromAPI(text);
//            UUID t1=generateType1UUID();
//            Vector vector = new Vector(t1.toString(),text, embedding, new HashMap<>());
//            vectorStore.put(t1.toString(), vector);
//        }
//
//        db.commit();

        // Compute similarity scores between stored vectors
        Set<Map.Entry<String, Vector>> entries = vectorStore.entrySet();
        for (Map.Entry<String, Vector> entryA : entries) {
            for (Map.Entry<String, Vector> entryB : entries) {
                if (!entryA.getKey().equals(entryB.getKey())) {
                    double similarity = cosineSimilarity(entryA.getValue().getEmbedding(), entryB.getValue().getEmbedding());
                    System.out.println("Similarity between \"" + entryA.getValue().getContent() + "\" and \"" + entryB.getValue().getContent() + "\": " + similarity);
                }
            }
        }

        // Close database
        db.close();
    }
    
    public static void testSentenceSplit(MapDBVectorStore store){
        // Sample article text
        String article = "This is a sample article text that needs to be split into smaller segments. "
                + "Each segment will be processed to generate embeddings and then stored in the vector store. "
                + "We will use the RecursiveCharacterTextSplitter to handle the splitting of the text.";

        // Initialize the text splitter with a maximum segment length of 100 characters
        RecursiveCharacterTextSplitter textSplitter = new RecursiveCharacterTextSplitter();
        char splitChar = '.';
        // Split the article into segments
        List<String> segments = textSplitter.splitText(article,splitChar);

        // Open database
        //DB db = DBMaker.fileDB("vectorstore.db").make();

        // Create vector store
        //Map<String, Vector> vectorStore = db.treeMap("vectorStore", Serializer.STRING, new VectorGroupSerializer()).createOrOpen();

        // Initialize the embedding service
        EmbeddingService embeddingService = new EmbeddingService();

        // Process each segment
        for (String segment : segments) {
            try {
                // Generate embedding for the segment
                double[] embedding = embeddingService.getEmbeddingFromAPI(segment);
                
                
                // Generate a unique ID for the vector using MD5 hash
                String id = EmbeddingService.md5(segment);
                
                // Create a Vector object
                Vector vector = new Vector(id,segment, embedding, new HashMap<>());
                
                
                
                // Insert the vector into the vector store
                store.insertVector( vector);
            } catch (IOException ex) {
                Logger.getLogger(VectorStoreExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        double[] queryVector;
        try {
            queryVector = embeddingService.getEmbeddingFromAPI("Splitting of the text to be handled");
            List<Vector> results=store.searchVectors(queryVector, 0.01);
            for(Vector result:results){
                System.out.println(result.getContent());
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(VectorStoreExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Commit the transaction
        //store.commit();

        // Close the database
        store.close();

        System.out.println("Article segments and their embeddings have been successfully stored in the vector store.");
    
    }
    
    public static UUID generateType1UUID() {
    long most64SigBits = get64MostSignificantBitsForVersion1();
    long least64SigBits = get64LeastSignificantBitsForVersion1();
    return new UUID(most64SigBits, least64SigBits);
}
    
    private static long get64LeastSignificantBitsForVersion1() {
    Random random = new Random();
    long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
    long variant3BitFlag = 0x8000000000000000L;
    return random63BitLong | variant3BitFlag;
}

    private static long get64MostSignificantBitsForVersion1() {
    final long currentTimeMillis = System.currentTimeMillis();
    final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32; 
    final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
    final long version = 1 << 12; 
    final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
    return time_low | time_mid | version | time_hi;
}
    
}
