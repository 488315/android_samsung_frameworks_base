package com.android.internal.util;

import android.os.Trace;

import java.util.regex.Pattern;

public class NotificationBigTextNormalizer {
    private static final Pattern MULTIPLE_NEWLINES = Pattern.compile("\\v(\\s*\\v)?");
    private static final Pattern HORIZONTAL_WHITESPACES = Pattern.compile("\\h+");

    private NotificationBigTextNormalizer() {}

    public static String normalizeBigText(String text) {
        try {
            Trace.beginSection("NotifBigTextNormalizer#normalizeBigText");
            return normalizeLines(
                    HORIZONTAL_WHITESPACES
                            .matcher(MULTIPLE_NEWLINES.matcher(text).replaceAll("\n"))
                            .replaceAll(" "));
        } finally {
            Trace.endSection();
        }
    }

    private static String normalizeLines(String text) {
        String[] lines = text.split("\n");
        StringBuilder textSB = new StringBuilder(text.length());
        for (String line : lines) {
            StringBuilder lineSB = new StringBuilder(line.length());
            boolean spaceSeen = false;
            for (int j = 0; j < line.length(); j++) {
                char character = line.charAt(j);
                if ((character < 8203 || character > 8205)
                        && character != 65279
                        && character != 847
                        && ((character < 8288 || character > 8293)
                                && ((character < 8298 || character > 8303)
                                        && (character < 65529 || character > 65531)))) {
                    if (isSpace(character)) {
                        if (!spaceSeen) {
                            lineSB.append(" ");
                        }
                        spaceSeen = true;
                    } else {
                        spaceSeen = false;
                        lineSB.append(character);
                    }
                }
            }
            String currentLine = lineSB.toString().trim();
            if (currentLine.length() > 0) {
                if (textSB.length() > 0) {
                    textSB.append("\n");
                }
                textSB.append(currentLine);
            }
        }
        return textSB.toString();
    }

    private static boolean isSpace(char ch) {
        return ch != '\n' && Character.isSpaceChar(ch);
    }
}
