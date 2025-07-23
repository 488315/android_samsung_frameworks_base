package com.android.systemui.log.core;

import android.icu.text.SimpleDateFormat;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LogMessageKt {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);

    /* JADX INFO: Access modifiers changed from: private */
    public static final void printLikeLogcat(PrintWriter printWriter, String str, String str2, String str3, String str4) {
        printWriter.print(str);
        printWriter.print(" ");
        printWriter.print(str2);
        printWriter.print(" ");
        printWriter.print(str3);
        printWriter.print(": ");
        printWriter.println(str4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void printLikeLogcat(PrintWriter printWriter, String str, String str2, String str3, String str4, long j, Character ch) {
        printWriter.print(str);
        printWriter.print(" ");
        if (j > 0) {
            int i = StringCompanionObject.$r8$clinit;
            printWriter.print(String.format(Locale.US, "%5d ", Arrays.copyOf(new Object[]{Long.valueOf(j)}, 1)));
        }
        printWriter.print(str2);
        printWriter.print(" ");
        printWriter.print(str3);
        if (ch != null) {
            printWriter.print(ch.charValue());
        } else {
            printWriter.print(":");
        }
        printWriter.print(" ");
        printWriter.println(str4);
    }
}
