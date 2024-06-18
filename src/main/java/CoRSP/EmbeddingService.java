/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoRSP;

/**
 *
 * @author manoj.kumar
 */
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EmbeddingService {

    /**
     * Retrieves embeddings from an HTTP API based on the given text.
     *
     * @param text The text for which embeddings are requested.
     * @return Embeddings received from the API as a float array.
     * @throws IOException If an error occurs during HTTP communication or JSON parsing.
     */
    public double[] getEmbeddingFromAPI(String text) throws IOException {
        String apiEndpoint = "http://127.0.0.1:5000/embedding"; // Replace with actual API endpoint
        URL url = new URL(apiEndpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Prepare JSON data to send
        String jsonInputString = "{\"text\": \"" + text + "\"}";

        // Write JSON data to request body
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Read response from API
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        conn.disconnect();


        JSONArray embeddingsArray = new JSONArray(response.toString());
        double[] embeddings = new double[embeddingsArray.length()];
        for (int i = 0; i < embeddingsArray.length(); i++) {
            embeddings[i] = embeddingsArray.getFloat(i);
        }

        return embeddings;
    }
    
    /**
     * Generates MD5 hash for the given input string.
     *
     * @param input the input string
     * @return the MD5 hash of the input string
     */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(number.toString(16));
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Example usage of getEmbeddingFromAPI.
     */
    public static void main(String[] args) {
        EmbeddingService embeddingService = new EmbeddingService();
        String text = "This is a sample text for embedding.";
        try {
            double[] embeddings = embeddingService.getEmbeddingFromAPI(text);
            System.out.print("Received embeddings: [");
            for (double embedding : embeddings) {
                System.out.print(embedding + ", ");
            }
            System.out.println("]");
        } catch (IOException e) {
            System.err.println("Error retrieving embeddings: " + e.getMessage());
        }
    }
}
