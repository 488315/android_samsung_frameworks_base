package kotlinx.coroutines.internal;

import _COROUTINE.ArtificialStackFrames;
import kotlin.Result;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class StackTraceRecoveryKt {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        Object failure;
        Object failure2;
        new ArtificialStackFrames();
        StackTraceElement stackTraceElement = new Exception().getStackTrace()[0];
        new StackTraceElement("_COROUTINE._BOUNDARY", "_", stackTraceElement.getFileName(), stackTraceElement.getLineNumber());
        try {
            int i = Result.$r8$clinit;
            failure = Class.forName("kotlin.coroutines.jvm.internal.BaseContinuationImpl").getCanonicalName();
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        try {
            failure2 = StackTraceRecoveryKt.class.getCanonicalName();
        } catch (Throwable th2) {
            int i3 = Result.$r8$clinit;
            failure2 = new Result.Failure(th2);
        }
        if (Result.m3422exceptionOrNullimpl(failure2) != null) {
            failure2 = "kotlinx.coroutines.internal.StackTraceRecoveryKt";
        }
    }
}
