package kotlinx.coroutines.android;

import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import kotlin.Result;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class HandlerDispatcherKt {
    public static final /* synthetic */ int $r8$clinit = 0;
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
