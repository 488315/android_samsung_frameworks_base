package com.samsung.android.server.dynamicfeature.dlog;

import com.samsung.android.server.dynamicfeature.InfoBoard;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class Dlog {
    public static final String[] circularQueue = new String[40];
    public static int queueIndex;

    public static void event(String... strArr) {
        StringBuilder sb = new StringBuilder();
        String str = InfoBoard.sExecutableBinaryType;
        sb.append(ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss")));
        sb.append(">\n ");
        StringBuilder sb2 = new StringBuilder(sb.toString());
        for (String str2 : strArr) {
            sb2.append("      " + str2 + "\n");
        }
        circularQueue[queueIndex] = sb2.toString();
        queueIndex = (queueIndex + 1) % 40;
    }

    public static String getCallers(int i) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i2 = 3; i2 < stackTrace.length && i + 3 != i2; i2++) {
            sb.append("\n   " + stackTrace[i2]);
        }
        return sb.toString();
    }
}
