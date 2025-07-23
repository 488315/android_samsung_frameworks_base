package androidx.core.provider;

import android.os.Handler;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class RequestExecutor$HandlerExecutor implements Executor {
    public final Handler mHandler;

    public RequestExecutor$HandlerExecutor(Handler handler) {
        handler.getClass();
        this.mHandler = handler;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        Handler handler = this.mHandler;
        runnable.getClass();
        if (handler.post(runnable)) {
            return;
        }
        throw new RejectedExecutionException(this.mHandler + " is shutting down");
    }
}
