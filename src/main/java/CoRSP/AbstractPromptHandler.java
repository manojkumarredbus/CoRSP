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
public abstract class AbstractPromptHandler {
 public abstract Object handleResponse(String promptId, Map<String, String> updatedVariables, String ipAddress);
}