package android.p009os;

import com.android.internal.util.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes3.dex */
public class HandlerExecutor implements Executor {
    private final Handler mHandler;

    public HandlerExecutor(Handler handler) {
        this.mHandler = (Handler) Preconditions.checkNotNull(handler);
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable command) {
        if (!this.mHandler.post(command)) {
            throw new RejectedExecutionException(this.mHandler + " is shutting down");
        }
    }
}
