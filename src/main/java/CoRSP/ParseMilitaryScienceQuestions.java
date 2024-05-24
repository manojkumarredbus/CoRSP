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
import org.json.JSONException;
import java.util.HashMap;
import java.util.Map;

public class ParseMilitaryScienceQuestions {

    public static void main(String[] args) {
        String jsonString = "["
                + "\"Calculate the trajectory of a mortar shell with an initial velocity of {velocity} m/s and a muzzle elevation of {elevation} degrees.\","
                + "\"What is the maximum range of a rifle bullet fired at an angle of {angle} degrees with an initial velocity of {velocity} m/s?\","
                + "\"Determine the time of flight for an artillery shell traveling {distance} km at an altitude of {altitude} km.\","
                + "\"Calculate the distance to a target that is 500 meters away and has a height of 10 meters above the surrounding terrain.\","
                + "\"What is the effective range of a machine gun firing 7.62mm rounds at a rate of 600 rounds per minute?\","
                + "\"Determine the velocity of a tank moving at an angle of 45 degrees with an initial speed of 30 km/h.\","
                + "\"Calculate the trajectory of a rocket-propelled grenade launcher with an initial velocity of 150 m/s and a muzzle elevation of 60 degrees.\","
                + "\"What is the maximum effective range of a sniper rifle firing .308 Winchester rounds?\","
                + "\"Determine the time it takes for a helicopter to travel 50 km at an altitude of 1000 meters.\","
                + "\"Calculate the distance to a target that is 800 meters away and has a height of 20 meters above the surrounding terrain.\""
                + "]";
// Extract the JSON array string from the input string
        String jsonArrayString = jsonString.substring(jsonString.indexOf("["));
        try {
            // Parse the JSON array
            JSONArray questionsArray = new JSONArray(jsonArrayString);
// Initialize the PROMPTS repository
            PROMPTS promptRepository = new PROMPTS();
// Add prompts with placeholders and next prompt IDs
            for (int i = 0; i < questionsArray.length(); i++) {
                String question = questionsArray.getString(i);
                String promptId = "Q" + (i + 1); // Generate a unique identifier for each question
                String nextPromptId = (i < questionsArray.length() - 1) ? "Q" + (i + 2) : null; // Chain the next prompt
                promptRepository.addPrompt(promptId, question, nextPromptId, "1.0", "model1");
            }
// Initialize the response handler with an example IP address
            PromptResponseHandler responseHandler = new PromptResponseHandler("192.168.0.1");
            ExampleHandler exampleHandler = new ExampleHandler();
// Register the example handler for all prompts
            for (int i = 1; i <= questionsArray.length(); i++) {
                responseHandler.registerHandler("Q" + i, exampleHandler);
            }
// Initial variables
            Map<String, String> variables = new HashMap<>();
            variables.put("velocity", "100");
            variables.put("elevation", "45");
            variables.put("angle", "30");
            variables.put("distance", "10");
            variables.put("altitude", "1");
// Iterate through the chain and handle responses
            Map<String, Object> promptResponses = promptRepository.iterateThroughChain("Q1", variables, responseHandler);
// Print the responses
            for (Map.Entry<String, Object> entry : promptResponses.entrySet()) {
                System.out.println("Prompt ID: " + entry.getKey() + ", Response: " + entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
