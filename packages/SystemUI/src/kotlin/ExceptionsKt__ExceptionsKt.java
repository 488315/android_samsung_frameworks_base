package kotlin;

import java.io.PrintWriter;
import java.io.StringWriter;
import kotlin.internal.PlatformImplementationsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ExceptionsKt__ExceptionsKt {
    public static void addSuppressed(Throwable th, Throwable th2) {
        if (th != th2) {
            PlatformImplementationsKt.IMPLEMENTATIONS.addSuppressed(th, th2);
        }
    }

    public static String stackTraceToString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }
}
