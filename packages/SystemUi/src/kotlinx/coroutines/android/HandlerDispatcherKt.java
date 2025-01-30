package kotlinx.coroutines.android;

import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import kotlin.Result;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class HandlerDispatcherKt {
    private static volatile Choreographer choreographer;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r2v1, types: [kotlin.Result$Failure] */
    static {
        HandlerContext failure;
        ?? r0 = 0;
        ?? r02 = 0;
        try {
            int i = Result.$r8$clinit;
            failure = new HandlerContext(asHandler(Looper.getMainLooper()), r02 == true ? 1 : 0, 2, r0 == true ? 1 : 0);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        HandlerDispatcher handlerDispatcher = failure instanceof Result.Failure ? null : failure;
    }

    public static final Handler asHandler(Looper looper) {
        return (Handler) Handler.class.getDeclaredMethod("createAsync", Looper.class).invoke(null, looper);
    }
}
