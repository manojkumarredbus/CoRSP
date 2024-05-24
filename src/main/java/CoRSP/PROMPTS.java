/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoRSP;

/**
 * https://medium.com/redbus-in/design-pattern-for-ai-backend-chain-of-responsibility-pattern-strategy-pattern-77e65c877e1d
 * @author manoj.kumar
 */
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PROMPTS {

    private Map<String, Prompt> prompts = new ConcurrentHashMap<>();
// Method to add prompts

    public void addPrompt(String promptId, String text, String nextPromptId, String version, String modelId) {
        Prompt prompt = new Prompt(promptId, text, nextPromptId, version, modelId);
        prompts.put(promptId, prompt);
    }
// Method to get a prompt by its ID

    public String getPrompt(String promptId, Map<String, String> variables) {
        Prompt prompt = prompts.get(promptId);
        if (prompt != null) {
            return replaceVariables(prompt.getText(), variables);
        }
        return null;
    }
// Method to get the next prompt ID

    public String getNextPromptId(String promptId) {
        Prompt prompt = prompts.get(promptId);
        return (prompt != null) ? prompt.getNextPromptId() : null;
    }
// Method to replace variables in a text

    private String replaceVariables(String text, Map<String, String> variables) {
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            text = text.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return text;
    }
// Method to update variables in a thread-safe manner

    private Map<String, String> updateVariables(Map<String, String> currentVariables, Map<String, String> newVariables) {
        currentVariables.putAll(newVariables);
        return new ConcurrentHashMap<>(currentVariables);
    }
// Method to iterate through the chain and handle responses

    public Map<String, Object> iterateThroughChain(String startPromptId, Map<String, String> variables, PromptResponseHandler responseHandler) {
        String currentPromptId = startPromptId;
        Map<String, String> currentVariables = new ConcurrentHashMap<>(variables);
        Map<String, Object> promptResponses = new ConcurrentHashMap<>();
        while (currentPromptId != null) {
            String promptText = getPrompt(currentPromptId, currentVariables);
            if (promptText != null) {
                System.out.println("Prompt: " + promptText);
            }
// Call the abstract method to modify variables with the current prompt ID
            Map<String, String> newVariables = responseHandler.modifyVariables(currentPromptId, currentVariables);
// Update current variables in a thread-safe manner
            currentVariables = updateVariables(currentVariables, newVariables);
// Handle the response for the current prompt ID and store the parsed response
            Object parsedResp = responseHandler.handleResponse(currentPromptId, currentVariables);
            if(parsedResp!=null)promptResponses.put(currentPromptId, parsedResp);
            currentPromptId = getNextPromptId(currentPromptId);
        }
        return promptResponses;
    }
}
