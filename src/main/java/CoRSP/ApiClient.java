package CoRSP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

// Class to represent a variable definition
class VariableDefinition {
    private final String name;
    private final String type;
    private final String description;
    private final String[] enumValues;

    public VariableDefinition(String name, String type, String description, String[] enumValues) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.enumValues = enumValues;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", this.type);
        jsonObject.put("description", this.description);
        if (enumValues != null) {
            jsonObject.put("enum", new JSONArray(enumValues));
        }
        return jsonObject;
    }

    public String getName() {
        return name;
    }
}

// Interface for handling functions
interface FunctionHandler {
    String getFunctionName();
    String getDescription();
    JSONObject getFunctionJSON();
    Map<String, VariableDefinition> getVariables();
    String execute(JSONObject arguments);
}

class CalculateCuboidVolume implements FunctionHandler {

    @Override
    public String getFunctionName() {
        return "calculate_cuboid_volume";
    }

    @Override
    public String getDescription() {
        return "Calculates the volume of a cuboid given its dimensions.";
    }

    @Override
    public JSONObject getFunctionJSON() {
        JSONObject functionObject = new JSONObject();
        functionObject.put("name", getFunctionName());
        functionObject.put("description", getDescription());

        JSONObject parametersObject = new JSONObject();
        parametersObject.put("type", "object");

        JSONObject propertiesObject = new JSONObject();

        for (VariableDefinition variable : getVariables().values()) {
            propertiesObject.put(variable.getName(), variable.toJSON());
        }

        parametersObject.put("properties", propertiesObject);
        parametersObject.put("required", new JSONArray(getVariables().keySet()));

        functionObject.put("parameters", parametersObject);

        return functionObject;
    }

    @Override
    public Map<String, VariableDefinition> getVariables() {
        Map<String, VariableDefinition> variables = new HashMap<>();
        variables.put("length", new VariableDefinition("length", "number", "The length of the cuboid.", null));
        variables.put("width", new VariableDefinition("width", "number", "The width of the cuboid.", null));
        variables.put("height", new VariableDefinition("height", "number", "The height of the cuboid.", null));
        return variables;
    }

    @Override
    public String execute(JSONObject arguments) {
        double length = arguments.getDouble("length");
        double width = arguments.getDouble("width");
        double height = arguments.getDouble("height");

        double volume = length * width * height;

        return ("The volume of the cuboid is: " + volume);
    }
}

// Concrete class for getCurrentWeather function
class GetCurrentWeatherFunction implements FunctionHandler {

    @Override
    public String getFunctionName() {
        return "get_current_weather";
    }

    @Override
    public String getDescription() {
        return "Get the current weather for a location";
    }

    @Override
    public JSONObject getFunctionJSON() {
        JSONObject functionObject = new JSONObject();
        functionObject.put("name", getFunctionName());
        functionObject.put("description", getDescription());

        JSONObject parametersObject = new JSONObject();
        parametersObject.put("type", "object");

        JSONObject propertiesObject = new JSONObject();

        for (VariableDefinition variable : getVariables().values()) {
            propertiesObject.put(variable.getName(), variable.toJSON());
        }

        parametersObject.put("properties", propertiesObject);
        parametersObject.put("required", new JSONArray(getVariables().keySet()));

        functionObject.put("parameters", parametersObject);

        return functionObject;
    }

    @Override
    public Map<String, VariableDefinition> getVariables() {
        Map<String, VariableDefinition> variables = new HashMap<>();
        variables.put("location", new VariableDefinition("location", "string", "The location to get the weather for, e.g. San Francisco, CA", null));
        variables.put("format", new VariableDefinition("format", "string", "The format to return the weather in, e.g. 'celsius' or 'fahrenheit'", new String[]{"celsius", "fahrenheit"}));
        return variables;
    }

    @Override
    public String execute(JSONObject arguments) {
        String location = arguments.getString("location");
        String format = arguments.getString("format");
        return ("Executing getCurrentWeather with location: " + location + " and format: " + format);
        // Logic to get weather data (you can integrate with a real weather API here)
    }
}

// Concrete class for getForecast function
class GetForecastFunction implements FunctionHandler {

    @Override
    public String getFunctionName() {
        return "get_forecast";
    }

    @Override
    public String getDescription() {
        return "Get the weather forecast for a location";
    }

    @Override
    public JSONObject getFunctionJSON() {
        JSONObject functionObject = new JSONObject();
        functionObject.put("name", getFunctionName());
        functionObject.put("description", getDescription());

        JSONObject parametersObject = new JSONObject();
        parametersObject.put("type", "object");

        JSONObject propertiesObject = new JSONObject();

        for (VariableDefinition variable : getVariables().values()) {
            propertiesObject.put(variable.getName(), variable.toJSON());
        }

        parametersObject.put("properties", propertiesObject);
        parametersObject.put("required", new JSONArray(getVariables().keySet()));

        functionObject.put("parameters", parametersObject);

        return functionObject;
    }

    @Override
    public Map<String, VariableDefinition> getVariables() {
        Map<String, VariableDefinition> variables = new HashMap<>();
        variables.put("location", new VariableDefinition("location", "string", "The location to get the weather forecast for, e.g. San Francisco, CA", null));
        variables.put("days", new VariableDefinition("days", "integer", "The number of days to get the forecast for", null));
        return variables;
    }

    @Override
    public String execute(JSONObject arguments) {
        String location = arguments.getString("location");
        int days = arguments.getInt("days");
        return ("Executing getForecast with location: " + location + " for " + days + " days");
        // Logic to get forecast data (you can integrate with a real forecast API here)
    }
}

class CalculateConeSurfaceArea implements FunctionHandler {

    @Override
    public String getFunctionName() {
        return "calculate_cone_surface_area";
    }

    @Override
    public String getDescription() {
        return "Calculates the surface area of a cone given its radius and height.";
    }

    @Override
    public JSONObject getFunctionJSON() {
        JSONObject functionObject = new JSONObject();
        functionObject.put("name", getFunctionName());
        functionObject.put("description", getDescription());

        JSONObject parametersObject = new JSONObject();
        parametersObject.put("type", "object");

        JSONObject propertiesObject = new JSONObject();

        for (VariableDefinition variable : getVariables().values()) {
            propertiesObject.put(variable.getName(), variable.toJSON());
        }

        parametersObject.put("properties", propertiesObject);
        parametersObject.put("required", new JSONArray(getVariables().keySet()));

        functionObject.put("parameters", parametersObject);

        return functionObject;
    }

    @Override
    public Map<String, VariableDefinition> getVariables() {
        Map<String, VariableDefinition> variables = new HashMap<>();
        variables.put("radius", new VariableDefinition("radius", "number", "The radius of the cone.", null));
        variables.put("height", new VariableDefinition("height", "number", "The height of the cone.", null));
        return variables;
    }

    @Override
    public String execute(JSONObject arguments) {
        double radius = arguments.getDouble("radius");
        double height = arguments.getDouble("height");

        // Calculate slant height using Pythagorean theorem
        double slantHeight = Math.sqrt(radius * radius + height * height);

        // Calculate surface area of the cone (πr(r+sl))
        double surfaceArea = Math.PI * radius * (radius + slantHeight);

        return("The surface area of the cone is: " + surfaceArea);
    }
}

// ApiClient class to handle the API request and response
public class ApiClient {
    private Map<String, FunctionHandler> functionHandlers = new HashMap<>();

    public ApiClient() {
        // Registering available functions
        registerFunction(new GetCurrentWeatherFunction());
        registerFunction(new GetForecastFunction());
        registerFunction(new CalculateCuboidVolume());
        registerFunction(new CalculateConeSurfaceArea ());

    }

    public void registerFunction(FunctionHandler handler) {
        functionHandlers.put(handler.getFunctionName(), handler);
    }

    public void callApiAndHandleResponse(JSONArray messagesArray) {
        try {
            // Construct the JSON payload
            JSONObject payload = new JSONObject();
            payload.put("model", "llama3.1");

            //JSONArray messagesArray = new JSONArray();

            if(messagesArray.length()==0){// for first time call
            JSONObject messageSystemObject = new JSONObject();
            messageSystemObject.put("role", "system");
            messageSystemObject.put("content", "You are a helpful customer support assistant. Use the supplied tools to assist the user. Do not assume required properties values for tools, always ask for clarification to user."); //forecast for a location  What is the weather today 
            messagesArray.put(messageSystemObject);
            }

    // Create an input dialog with a text area to display the content of messageArray
    JFrame frame = new JFrame("Message Viewer");
    JTextArea textArea = new JTextArea(20, 30);

    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < messagesArray.length(); i++) {
        JSONObject message = (JSONObject) messagesArray.get(i);
        builder.append(message.getString("role")).append(": ").append(message.getString("content"))
               .append("\n");
    }
    System.out.println(builder.toString());
    textArea.setText(builder.toString());
    
    textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
    textArea.setBackground(Color.BLACK);
    textArea.setForeground(Color.WHITE);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);

    JDialog dialog = new JDialog(frame, "View Messages", true);

    // Add a button to close the dialog
    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            dialog.dispose();
        }
    });

    // Add a text field for user input
    JTextField textField = new JTextField(20);
    JLabel label = new JLabel("Enter your message:");
    label.setLabelFor(textField);
    textField.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            dialog.dispose();
        }
    });
    // Set the text field to match the style of the text area
    textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
    textField.setBackground(Color.BLACK);
    textField.setForeground(Color.WHITE);

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(label, BorderLayout.NORTH);
    panel.add(textField, BorderLayout.CENTER);

    dialog.getContentPane().add(panel, BorderLayout.NORTH);
    dialog.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
    dialog.getContentPane().add(closeButton, BorderLayout.SOUTH);

    // Show the input dialog
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setSize(1000, 800);
    dialog.setVisible(true);

    // Get the user's message from the text field
    String userInput = textField.getText();

            JSONObject messageObject = new JSONObject();
            messageObject.put("role", "user");
            messageObject.put("content", userInput); //forecast for a location  What is the weather today // JOptionPane.showInputDialog("What is your question?")
            messagesArray.put(messageObject);
            payload.put("messages", messagesArray);

            payload.put("stream", false);

            JSONArray toolsArray = new JSONArray();

            // Add each function's JSON to the payload
            for (FunctionHandler handler : functionHandlers.values()) {
                JSONObject toolObject = new JSONObject();
                toolObject.put("type", "function");
                toolObject.put("function", handler.getFunctionJSON());
                toolsArray.put(toolObject);
            }

            payload.put("tools", toolsArray);
            
            System.out.println(payload.toString(1));

            // URL of the API endpoint
            String value_name = System.getenv("LLM_OLLAMA_HOST");
            URL url = new URL(value_name+"/api/chat");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Send JSON payload
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Handle the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Extract tool calls and execute corresponding functions
                JSONObject messageObjectResponse = jsonResponse.getJSONObject("message");
                if(messageObjectResponse.has("tool_calls"))
               {JSONArray toolCallsArray = messageObjectResponse.getJSONArray("tool_calls");

                for (int i = 0; i < toolCallsArray.length(); i++) {
                    JSONObject toolCall = toolCallsArray.getJSONObject(i);
                    JSONObject functionResponse = toolCall.getJSONObject("function");
                    String functionName = functionResponse.getString("name");

                    // Execute the registered function
                    FunctionHandler handler = functionHandlers.get(functionName);
                    if (handler != null) {
                        JSONObject arguments = functionResponse.getJSONObject("arguments");
                        String funcVal=handler.execute(arguments);

                        JSONObject messageObjectT = new JSONObject();
                    messageObjectT.put("role",  messageObjectResponse.getString("role"));
                    messageObjectT.put("content", funcVal); //forecast for a location  What is the weather today // JOptionPane.showInputDialog("What is your question?")
                    messagesArray.put(messageObjectT);

                    callApiAndHandleResponse( messagesArray);
                    } else {
                        System.out.println("No handler registered for function: " + functionName);
                    }
                }
            }else {

                    System.out.println(messageObjectResponse); // print messageObjectResponse

                    JSONObject messageObjectT = new JSONObject();
                    messageObjectT.put("role",  messageObjectResponse.getString("role"));
                    messageObjectT.put("content", messageObjectResponse.getString("content")); //forecast for a location  What is the weather today // JOptionPane.showInputDialog("What is your question?")
                    messagesArray.put(messageObjectT);

                    callApiAndHandleResponse( messagesArray);

            }

                System.out.println("Response handled successfully");
            } else {
                System.out.println("POST request failed with response code: " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        JSONArray messagesArray=new JSONArray();
        apiClient.callApiAndHandleResponse(messagesArray);
    }
}

