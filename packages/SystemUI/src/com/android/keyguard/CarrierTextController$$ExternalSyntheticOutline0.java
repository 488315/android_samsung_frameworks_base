package com.android.keyguard;

import java.io.PrintWriter;

public abstract /* synthetic */ class CarrierTextController$$ExternalSyntheticOutline0 {
    public static StringBuilder m(PrintWriter printWriter, String str, String str2) {
        printWriter.println(str);
        return new StringBuilder(str2);
    }

    public static void m(StringBuilder sb, String str, PrintWriter printWriter) {
        sb.append(str);
        printWriter.println(sb.toString());
    }
}
