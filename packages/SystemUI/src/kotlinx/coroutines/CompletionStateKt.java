package kotlinx.coroutines;

import kotlin.Result;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class CompletionStateKt {
    public static final Object recoverResult(Object obj) {
        if (obj instanceof CompletedExceptionally) {
            int i = Result.$r8$clinit;
            return new Result.Failure(((CompletedExceptionally) obj).cause);
        }
        int i2 = Result.$r8$clinit;
        return obj;
    }
}
