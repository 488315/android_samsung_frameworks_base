package androidx.compose.runtime.tooling;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ComposeStackTraceKt {
    public static final boolean tryAttachComposeStackTrace(Throwable th, Function0 function0) {
        DiagnosticComposeException diagnosticComposeException;
        List suppressed = PlatformImplementationsKt.IMPLEMENTATIONS.getSuppressed(th);
        boolean z = false;
        if (!(suppressed instanceof Collection) || !suppressed.isEmpty()) {
            Iterator it = suppressed.iterator();
            while (it.hasNext()) {
                if (((Throwable) it.next()) instanceof DiagnosticComposeException) {
                    return false;
                }
            }
        }
        try {
            List list = (List) function0.invoke();
            boolean isEmpty = list.isEmpty();
            z = !isEmpty;
            diagnosticComposeException = !isEmpty ? new DiagnosticComposeException(list) : null;
        } catch (Throwable th2) {
            diagnosticComposeException = th2;
        }
        if (diagnosticComposeException != null) {
            ExceptionsKt__ExceptionsKt.addSuppressed(th, diagnosticComposeException);
        }
        return z;
    }
}
