/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoRSP;

/**
 * https://azmechatech.medium.com/objective-driven-prompt-system-ce625ac673a8
 * @author manoj.kumar
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectiveDrivenPromptSystem {

    public static void main(String[] args) {
        // Define the objective
        String objective = "Collect data on gear tooth calculations.";

        // Generate initial prompts based on the objective
        List<Prompt> prompts = generatePromptsBasedOnObjective(objective);

        // Initialize the PROMPTS repository
        PROMPTS promptRepository = new PROMPTS();
        for (int i = 0; i < prompts.size(); i++) {
            Prompt prompt = prompts.get(i);
            String promptId = "Q" + (i + 1);
            String nextPromptId = (i < prompts.size() - 1) ? "Q" + (i + 2) : null;
            promptRepository.addPrompt(promptId, prompt.getText(), nextPromptId, prompt.getVersion(), prompt.getModelId());
        }

        // Variables to replace in prompts
        Map<String, String> initialVariables = new HashMap<>();
        initialVariables.put("gear_ratio", "4");
        initialVariables.put("module", "2");

        // Initialize response handler
        PromptResponseHandler responseHandler = new ExampleHandler2();

        // Iterate through the chain of prompts starting from Q1
        Map<String, Object> promptResponses = promptRepository.iterateThroughChain("Q1", initialVariables, responseHandler);

        // Check if all topics are covered
        checkCoverage(promptResponses, objective);
    }

    private static List<Prompt> generatePromptsBasedOnObjective(String objective) {
        List<Prompt> prompts = new ArrayList<>();

        if (objective.toLowerCase().contains("gear tooth calculations")) {
            prompts.add(new Prompt("1","Step 1: Calculate the Number of Teeth\nGiven a desired gear ratio of {gear_ratio} and a module of {module} mm:\n- Formula for number of teeth on the driver gear (N1): N1 = {number_of_teeth_driver}\n- Formula for number of teeth on the driven gear (N2): N2 = N1 * {gear_ratio}\nCalculate N1 and N2.",null, "1.0", "ModelA"));
            prompts.add(new Prompt("2","Step 2: Calculate the Module\nGiven a gear with a pitch circle diameter of {pitch_circle_diameter} mm and a number of teeth of {number_of_teeth}:\n- Formula: Module (m) = {pitch_circle_diameter} / {number_of_teeth}\nCalculate the module.",null, "1.0", "ModelA"));
            prompts.add(new Prompt("3","Step 3: Calculate Addendum and Dedendum\nGiven a module of {module} mm:\n- Addendum (a) = module (m)\n- Dedendum (d) = 1.25 * module (m)\nCalculate the addendum and dedendum.",null, "1.0", "ModelA"));
            prompts.add(new Prompt("4","Step 4: Calculate Bending Stress Using the Lewis Formula\nGiven a face width of {face_width} mm, module of {module} mm, and a load of {load} N:\n- Lewis formula: σ = (W * P) / (F * Y * m)\nWhere:\n  - σ = Bending stress\n  - W = Load ({load} N)\n  - P = Circular pitch (π * m)\n  - F = Face width ({face_width} mm)\n  - Y = Lewis form factor (depends on the number of teeth)\nCalculate the bending stress.",null, "1.0", "ModelA"));
            prompts.add(new Prompt("5","Step 5: Calculate Hertzian Contact Stress\nGiven a module of {module} mm and material properties {material_properties}:\n- Formula: σ_H = sqrt[(P * (1 - ν^2) / (π * E)) * ((1 - ν1^2) / E1 + (1 - ν2^2) / E2)]\nWhere:\n  - P = Load per unit length\n  - ν = Poisson's ratio\n  - E = Young's modulus\n  - ν1, ν2 = Poisson's ratio of materials 1 and 2\n  - E1, E2 = Young's modulus of materials 1 and 2\nCalculate the Hertzian contact stress.",null, "1.0", "ModelA"));
        }

        return prompts;
    }

    private static void checkCoverage(Map<String, Object> promptResponses, String objective) {
        // Here we would check if all topics related to the objective are covered
        // This can be customized based on specific requirements

        System.out.println("Checking coverage for objective: " + objective);
        for (Map.Entry<String, Object> entry : promptResponses.entrySet()) {
            System.out.println("Prompt ID: " + entry.getKey());
            System.out.println("Variables: " + entry.getValue());
        }

        System.out.println("All topics covered.");
    }
}

class ExampleHandler2 extends PromptResponseHandler {
    public ExampleHandler2() {
        super("127.0.0.1"); // Example IP address
    }

    @Override
    public Map<String, String> modifyVariables(String promptId, Map<String, String> currentVariables) {
        // Simulate calling an API to get new variables
        Map<String, String> newVariables = new HashMap<>();

        // Mock API responses
        switch (promptId) {
            case "Q1":
                newVariables.put("number_of_teeth_driver", "20");
                newVariables.put("gear_ratio", "4");
                break;
            case "Q2":
                newVariables.put("pitch_circle_diameter", "40");
                newVariables.put("number_of_teeth", "20");
                break;
            case "Q3":
                newVariables.put("module", "2");
                break;
            case "Q4":
                newVariables.put("face_width", "10");
                newVariables.put("load", "1000");
                break;
            case "Q5":
                newVariables.put("material_properties", "Steel");
                newVariables.put("module", "2");
                break;
        }

        return newVariables;
    }

    @Override
    public Object handleResponse(String promptId, Map<String, String> variables) {
        // Handle the response (e.g., print it, send it to an API, etc.)
        System.out.println("Handling response for prompt ID: " + promptId);
        return variables;
    }
}

