package kotlinx.coroutines;

import kotlin.Result;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
