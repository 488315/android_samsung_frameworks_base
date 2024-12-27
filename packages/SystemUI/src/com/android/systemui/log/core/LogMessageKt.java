package com.android.systemui.log.core;

import android.icu.text.SimpleDateFormat;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
