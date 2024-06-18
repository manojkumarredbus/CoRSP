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
import java.util.List;

public class RecursiveCharacterTextSplitter {

    /**
     * Splits the input text recursively based on the provided split character.
     *
     * @param text     The input text to split.
     * @param splitChar The character to split the text on.
     * @return A list of strings split based on the split character.
     */
    public List<String> splitText(String text, char splitChar) {
        List<String> result = new ArrayList<>();
        splitTextRecursive(text, splitChar, result);
        return result;
    }

    /**
     * Recursive helper method to split the text based on the split character.
     *
     * @param text     The current portion of text to split.
     * @param splitChar The character to split the text on.
     * @param result   The list to store split segments.
     */
    private void splitTextRecursive(String text, char splitChar, List<String> result) {
        int index = text.indexOf(splitChar);
        if (index == -1) {
            // No more split character found, add the remaining text
            result.add(text);
        } else {
            // Split at the index and add the substring before the split character
            result.add(text.substring(0, index));
            // Recursive call with the substring after the split character
            splitTextRecursive(text.substring(index + 1), splitChar, result);
        }
    }

    /**
     * Example usage of RecursiveCharacterTextSplitter.
     */
    public static void main(String[] args) {
        RecursiveCharacterTextSplitter splitter = new RecursiveCharacterTextSplitter();
        String text = "Hello,world,this,is,a,test";
        char splitChar = ',';
        List<String> segments = splitter.splitText(text, splitChar);
        segments.forEach(System.out::println);
    }
}
