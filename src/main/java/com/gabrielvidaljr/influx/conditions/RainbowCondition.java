package com.gabrielvidaljr.influx.conditions;

import com.gabrielvidaljr.influx.logger.InfluxLogger;
import com.gabrielvidaljr.strings.condition.component.ComponentCondition;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RainbowCondition implements ComponentCondition {

    @Override
    public boolean hasConditions(String s) {
        return s.contains("[rainbow]") || s.contains("[!rainbow]");
    }

    @Override
    public List<Integer> getConditionalLines(List<String> list) {
        final List<Integer> indexes = new LinkedList<>();

        for (final String line : list) {
            if (!this.hasConditions(line)) continue;
            indexes.add(line.indexOf(line));
        }

        return indexes;
    }

    @Override
    public List<String> handleCondition(final List<String> text) {
        final String startPlaceholder = "[rainbow]";
        final String endPlaceholder = "[/rainbow]";
        final String boldStartPlaceholder = "[!rainbow]";
        final List<String> lines = new ArrayList<>(text);

        for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
            String line = lines.get(lineIndex);
            boolean bold = false; // Initialize a boolean flag for bold formatting

            int start = line.indexOf(startPlaceholder);
            int boldStart = line.indexOf(boldStartPlaceholder);

            if (boldStart != -1 && (start == -1 || boldStart < start)) {
                // If [!rainbow] is present and either [rainbow] is not present or [!rainbow] comes before [rainbow]
                start = boldStart;
                bold = true; // Set the bold flag to true
            }

            if (start == -1) {
                continue; // Skip lines without the specified start placeholder
            }

            StringBuilder processedLine = new StringBuilder();
            int lastIndex = 0;

            while (start != -1) {
                int end = line.indexOf(endPlaceholder, start);
                if (end == -1) {
                    break; // Automatically end the effect if [/<placeholder>] is not present
                }

                // Remove [!rainbow] or [rainbow] while applying rainbow effect to the transformed text
                String content = line.substring(start + (start == boldStart ? boldStartPlaceholder : startPlaceholder).length(), end);
                String transformedText = generateSmoothRainbowText(content, bold);

                if (start == boldStart) {
                    // Replace [!rainbow] with &l only within [!rainbow]
                    processedLine.append("&l");
                }

                processedLine.append(line, lastIndex, start);
                processedLine.append(transformedText);

                lastIndex = end + endPlaceholder.length();
                start = line.indexOf(startPlaceholder, lastIndex);
                boldStart = line.indexOf(boldStartPlaceholder, lastIndex);

                if (boldStart != -1 && (start == -1 || boldStart < start)) {
                    // If [!rainbow] is present and either [rainbow] is not present or [!rainbow] comes before [rainbow]
                    start = boldStart + boldStartPlaceholder.length();
                    bold = true; // Set the bold flag to true
                }
            }

            processedLine.append(line.substring(lastIndex));
            lines.set(lineIndex, processedLine.toString());
        }

        return lines;
    }

    private String interpolateColor(final String color1, final String color2, final double t) {
        final int r1 = Integer.parseInt(color1.substring(1, 3), 16);
        final int g1 = Integer.parseInt(color1.substring(3, 5), 16);
        final int b1 = Integer.parseInt(color1.substring(5, 7), 16);

        final int r2 = Integer.parseInt(color2.substring(1, 3), 16);
        final int g2 = Integer.parseInt(color2.substring(3, 5), 16);
        final int b2 = Integer.parseInt(color2.substring(5, 7), 16);

        final int r = (int) (r1 + t * (r2 - r1));
        final int g = (int) (g1 + t * (g2 - g1));
        final int b = (int) (b1 + t * (b2 - b1));

        return String.format("#%02x%02x%02x", r, g, b);
    }

    private String generateSmoothRainbowText(final String input, boolean bold) {
        final StringBuilder rainbowText = new StringBuilder();

        final String[] rainbowColors = {
                "#ff0000", // Red
                "#ff7f00", // Orange
                "#ffff00", // Yellow
                "#00ff00", // Green
                "#0000ff", // Blue
                "#4b0082", // Indigo
                "#9400d3"  // Violet
        };

        final int numSteps = input.length();
        final double colorStep = (double) rainbowColors.length / numSteps;

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            int colorIndex1 = (int) Math.floor(i * colorStep);
            int colorIndex2 = (int) Math.ceil(i * colorStep);

            if (colorIndex2 >= rainbowColors.length) {
                colorIndex2 = 0;
            }

            final double t = i * colorStep - colorIndex1;
            String colorCode = interpolateColor(rainbowColors[colorIndex1], rainbowColors[colorIndex2], t);

            rainbowText.append(colorCode);

            if (bold) {
                rainbowText.append("&l");
            }

            rainbowText.append(currentChar);
        }

        return rainbowText.toString();
    }
}
