package com.gabrielvidaljr.influx.conditions;

import com.gabrielvidaljr.strings.condition.component.ComponentCondition;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradientCondition implements ComponentCondition {

    @Override
    public boolean hasConditions(String s) {
        return s.contains("[gradient:") || s.contains("[!gradient:");
    }

    @Override
    public List<Integer> getConditionalLines(List<String> list) {
        final List<Integer> indexes = new LinkedList<>();

        for (final String line : list) {
            if (hasConditions(line)) {
                indexes.add(list.indexOf(line));
            }
        }

        return indexes;
    }

    @Override
    public List<String> handleCondition(List<String> text) {
        // Define a regular expression pattern to match the gradient tags and their content
        Pattern pattern = Pattern.compile("\\[gradient:([A-Fa-f0-9]+-[A-Fa-f0-9]+(?:-[A-Fa-f0-9]+)*)\\](.*?)\\[/gradient\\]");

        // Create a list to store the modified text
        List<String> modifiedText = new ArrayList<>();

        // Get the indexes of lines containing gradient tags
        List<Integer> gradientLines = getConditionalLines(text);

        int currentIndex = 0; // Initialize the current index in the text

        for (int gradientIndex : gradientLines) {
            // Add the text between the current index and the gradient tag
            modifiedText.addAll(text.subList(currentIndex, gradientIndex));

            String line = text.get(gradientIndex);

            // If the line contains a gradient tag, process it
            Matcher matcher = pattern.matcher(line);
            StringBuffer sb = new StringBuffer();

            while (matcher.find()) {
                String gradientHex = matcher.group(1); // Extract the gradient HEX codes
                String content = matcher.group(2); // Extract the content inside the gradient tags

                // Split the gradientHex into individual HEX codes
                String[] hexCodes = gradientHex.split("-");

                // Calculate the number of steps for interpolation based on the content length
                int steps = content.length();

                // Interpolate colors between the HEX codes based on the number of steps
                List<String> interpolatedColors = interpolateColors(hexCodes, steps);

                // Create a new gradient string with interpolated colors and content
                StringBuilder interpolatedGradient = new StringBuilder();
                for (int i = 0; i < steps; i++) {
                    interpolatedGradient.append("#").append(interpolatedColors.get(i)).append(content.charAt(i));
                }

                // Append the interpolated gradient to the modified text
                matcher.appendReplacement(sb, interpolatedGradient.toString());
            }

            matcher.appendTail(sb);
            modifiedText.add(sb.toString());

            // Update the current index to the next line after the gradient tag
            currentIndex = gradientIndex + 1;
        }

        // Add the remaining text after the last gradient tag
        modifiedText.addAll(text.subList(currentIndex, text.size()));

        return modifiedText;
    }


    // Function to interpolate colors between HEX codes based on the number of steps
    private List<String> interpolateColors(String[] hexCodes, int steps) {
        List<String> interpolatedColors = new ArrayList<>();

        for (int i = 0; i < hexCodes.length - 1; i++) {
            String color1 = hexCodes[i];
            String color2 = hexCodes[i + 1];

            // Interpolate colors between color1 and color2 based on the number of steps
            List<String> interpolated = interpolateBetweenColors(color1, color2, steps);

            // Add the interpolated colors to the result
            interpolatedColors.addAll(interpolated);
        }

        return interpolatedColors;
    }

    // Function to interpolate between two HEX colors based on the number of steps
    private List<String> interpolateBetweenColors(String hexColor1, String hexColor2, int steps) {
        Color color1 = Color.decode("#" + hexColor1);
        Color color2 = Color.decode("#" + hexColor2);

        List<String> interpolatedColors = new ArrayList<>();

        for (int i = 0; i < steps; i++) {
            float ratio = (float) i / (steps - 1);
            int r = (int) (color1.getRed() * (1 - ratio) + color2.getRed() * ratio);
            int g = (int) (color1.getGreen() * (1 - ratio) + color2.getGreen() * ratio);
            int b = (int) (color1.getBlue() * (1 - ratio) + color2.getBlue() * ratio);

            // Convert RGB to HEX
            String hex = String.format("%02X%02X%02X", r, g, b);
            interpolatedColors.add(hex);
        }

        return interpolatedColors;
    }
}
