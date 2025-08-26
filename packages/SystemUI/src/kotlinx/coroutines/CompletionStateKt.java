package kotlinx.coroutines;

import kotlin.Result;

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
