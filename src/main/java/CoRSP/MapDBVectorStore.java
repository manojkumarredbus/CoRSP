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
import java.util.PriorityQueue;
import java.util.Comparator;

/**
+----------------+        +-----------------+
|                |        |                 |
|  Client Apps   |<-----> |   Vector Store  |
|                |        |       API       |
+----------------+        +--------+--------+
                                  |
                                  |
                          +-------+--------+
                          |                |
                          |    MapDB Store |
                          |                |
                          +----------------+

 * @author manoj.kumar
 */
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
                vector.getMetadata().put("score", similarity);
            }
        }
        return result;
    }
    
     /**
     * Searches for the vector in the vector store that has the highest cosine similarity
     * with the query vector, provided the similarity is above the given threshold.
     *
     * @param queryVector the query vector
     * @param threshold   the similarity threshold
     * @return the vector with the highest similarity above the threshold, or null if none found
     */
    public Vector searchTopScoringVector(double[] queryVector, double threshold) {
        Vector topScoringVector = null;
        double highestSimilarity = threshold;

        for (Vector vector : vectorMap.values()) {
            double similarity = cosineSimilarity(vector.getEmbedding(), queryVector);
            if (similarity > highestSimilarity) {
                highestSimilarity = similarity;
                topScoringVector = vector;
            }
        }

        if (topScoringVector != null) {
            topScoringVector.getMetadata().put("score", highestSimilarity);
        }

        return topScoringVector;
    }
    
    /**
     * Searches for the top N vectors in the vector store that have the highest cosine similarity
     * with the query vector, provided the similarity is above the given threshold.
     *
     * @param queryVector the query vector
     * @param threshold   the similarity threshold
     * @param topN        the number of top matching vectors to return
     * @return a list of the top N vectors with similarity above the threshold
     */
    public List<Vector> searchTopNVectors(double[] queryVector, double threshold, int topN) {
        PriorityQueue<Vector> topVectors = new PriorityQueue<>(topN, Comparator.comparingDouble(v -> (double) v.getMetadata().get("score")));

        for (Vector vector : vectorMap.values()) {
            double similarity = cosineSimilarity(vector.getEmbedding(), queryVector);
            if (similarity >= threshold) {
                vector.getMetadata().put("score", similarity);
                if (topVectors.size() < topN) {
                    topVectors.offer(vector);
                } else if (similarity > (double) topVectors.peek().getMetadata().get("score")) {
                    topVectors.poll();
                    topVectors.offer(vector);
                }
            }
        }

        List<Vector> result = new ArrayList<>(topVectors);
        result.sort((v1, v2) -> Double.compare((double) v2.getMetadata().get("score"), (double) v1.getMetadata().get("score")));
        return result;
    }
    
    public void close() {
        db.close();
    }
}
