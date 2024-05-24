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
public class ExampleHandler extends AbstractPromptHandler {
 @Override
 public Object handleResponse(String promptId, Map<String, String> updatedVariables, String ipAddress) {
 switch (promptId) {
 case "Q1":
 return handleQ1(updatedVariables, ipAddress);
 case "Q2":
 return handleQ2(updatedVariables, ipAddress);
 case "Q3":
 return handleQ3(updatedVariables, ipAddress);
 default:
 System.out.println("No specific handler for prompt ID: " + promptId);
 return null;
 }
 }
private Object handleQ1(Map<String, String> updatedVariables, String ipAddress) {
 System.out.println("Handling Q1 response from IP: " + ipAddress);
 for (Map.Entry<String, String> entry : updatedVariables.entrySet()) {
 System.out.println(entry.getKey() + ": " + entry.getValue());
 }
 return "Response for Q1";
 }
private Object handleQ2(Map<String, String> updatedVariables, String ipAddress) {
 System.out.println("Handling Q2 response from IP: " + ipAddress);
 for (Map.Entry<String, String> entry : updatedVariables.entrySet()) {
 System.out.println(entry.getKey() + ": " + entry.getValue());
 }
 return "Response for Q2";
 }
private Object handleQ3(Map<String, String> updatedVariables, String ipAddress) {
 System.out.println("Handling Q3 response from IP: " + ipAddress);
 for (Map.Entry<String, String> entry : updatedVariables.entrySet()) {
 System.out.println(entry.getKey() + ": " + entry.getValue());
 }
 return "Response for Q3";
 }
}