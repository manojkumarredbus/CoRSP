/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoRSP;

/**
 *
 * @author manoj.kumar
 */
public class Prompt {
    private String promptId;
    private String text;
    private String nextPromptId;
    private String version;
    private String modelId;

    // Constructor
    public Prompt(String promptId, String text, String nextPromptId, String version, String modelId) {
        this.promptId = promptId;
        this.text = text;
        this.nextPromptId = nextPromptId;
        this.version = version;
        this.modelId = modelId;
    }

    // Getters and Setters
    public String getPromptId() {
        return promptId;
    }

    public void setPromptId(String promptId) {
        this.promptId = promptId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNextPromptId() {
        return nextPromptId;
    }

    public void setNextPromptId(String nextPromptId) {
        this.nextPromptId = nextPromptId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    // toString method for easier debugging and logging
    @Override
    public String toString() {
        return "Prompt{" +
                "promptId='" + promptId + '\'' +
                ", text='" + text + '\'' +
                ", nextPromptId='" + nextPromptId + '\'' +
                ", version='" + version + '\'' +
                ", modelId='" + modelId + '\'' +
                '}';
    }
}
