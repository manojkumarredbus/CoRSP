/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoRSP;

/**
 *
 * @author manoj.kumar
 */
import java.util.ArrayList;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;

public class MapDBVectorStore implements VectorStore {
    private DB db;
    private ConcurrentNavigableMap<String, Vector> vectorMap;

    public MapDBVectorStore(String dbFilePath) {
        db = DBMaker.fileDB(dbFilePath).make();
        vectorMap = db.treeMap("vectors", Serializer.STRING, new VectorGroupSerializer()).createOrOpen();
    }

    @Override
    public void insertVector(Vector vector) {
        vectorMap.put(vector.getId(), vector);
        db.commit();
    }

    @Override
    public Vector getVector(String id) {
        return vectorMap.get(id);
    }

    @Override
    public void updateVector(Vector vector) {
        vectorMap.put(vector.getId(), vector);
        db.commit();
    }

    @Override
    public void deleteVector(String id) {
        vectorMap.remove(id);
        db.commit();
    }

   /**
     * Calculates the cosine similarity between two vectors.
     *
     * @param vectorA the first vector
     * @param vectorB the second vector
     * @return the cosine similarity between the two vectors
     */
    private double cosineSimilarity(double[] vectorA, double[] vectorB) {
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

    /**
     * Searches for vectors in the vector store that have a cosine similarity
     * above the given threshold with the query vector.
     *
     * @param queryVector the query vector
     * @param threshold   the similarity threshold
     * @return a list of vectors that have a similarity above the threshold
     */
    public List<Vector> searchVectors(double[] queryVector, double threshold) {
        List<Vector> result = new ArrayList<>();
        for (Vector vector : vectorMap.values()) {
            double similarity = cosineSimilarity(vector.getEmbedding(), queryVector);
            if (similarity >= threshold) {
                result.add(vector);
            }
        }
        return result;
    }
    
    public void close() {
        db.close();
    }
}
