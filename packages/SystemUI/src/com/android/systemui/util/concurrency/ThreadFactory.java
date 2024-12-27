package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface ThreadFactory {
    DelayableExecutor buildDelayableExecutorOnHandler(Handler handler);

    DelayableExecutor buildDelayableExecutorOnLooper(Looper looper);

    DelayableExecutor buildDelayableExecutorOnNewThread(String str);

    Executor buildExecutorOnNewThread(String str);

    Handler buildHandlerOnNewThread(String str);

    Looper buildLooperOnNewThread(String str);
}
