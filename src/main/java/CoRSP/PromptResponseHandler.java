/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoRSP;

/**
 *
 * @author manoj.kumar
 */
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class PromptResponseHandler {
 private Map<String, AbstractPromptHandler> promptHandlers;
 private String ipAddress;
public PromptResponseHandler(String ipAddress) {
 this.promptHandlers = new ConcurrentHashMap<>();
 this.ipAddress = ipAddress;
 }
// Method to register a handler for a specific prompt ID
 public void registerHandler(String promptId, AbstractPromptHandler handler) {
 promptHandlers.put(promptId, handler);
 }
// Method to modify variables based on prompt ID
 public Map<String, String> modifyVariables(String promptId, Map<String, String> variables) {
 Map<String, String> newVariables = new ConcurrentHashMap<>();
 if (promptId.equals("Q1")) {
 newVariables.put("newVariable1", "newValue1");
 } else if (promptId.equals("Q2")) {
 newVariables.put("newVariable2", "newValue2");
 }
 return newVariables;
 }
// Method to handle the response for the current prompt ID and return the parsed response
 public Object handleResponse(String promptId, Map<String, String> updatedVariables) {
 AbstractPromptHandler handler = promptHandlers.get(promptId);
 if (handler != null) {
 return handler.handleResponse(promptId, updatedVariables, ipAddress);
 } else {
 System.out.println("No handler registered for prompt ID: " + promptId);
 return null;
 }
 }
}
