package com.android.server.display.feature;

import java.io.PrintWriter;

public abstract /* synthetic */ class DisplayManagerFlags$$ExternalSyntheticOutline0 {
    public static StringBuilder m(
            StringBuilder sb,
            DisplayManagerFlags.FlagState flagState,
            PrintWriter printWriter,
            String str) {
        sb.append(flagState);
        printWriter.println(sb.toString());
        return new StringBuilder(str);
    }
}
