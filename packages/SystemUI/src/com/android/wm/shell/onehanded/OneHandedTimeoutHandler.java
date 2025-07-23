package com.android.wm.shell.onehanded;

import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class OneHandedTimeoutHandler {
    public final ShellExecutor mMainExecutor;
    public int mTimeout = 8;
    public long mTimeoutMs = TimeUnit.SECONDS.toMillis(8);
    public final OneHandedTimeoutHandler$$ExternalSyntheticLambda0 mTimeoutRunnable = new Runnable() { // from class: com.android.wm.shell.onehanded.OneHandedTimeoutHandler$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            OneHandedTimeoutHandler oneHandedTimeoutHandler = OneHandedTimeoutHandler.this;
            for (int size = ((ArrayList) oneHandedTimeoutHandler.mListeners).size() - 1; size >= 0; size--) {
                ((OneHandedController$$ExternalSyntheticLambda10) ((ArrayList) oneHandedTimeoutHandler.mListeners).get(size)).f$0.stopOneHanded(6);
            }
        }
    };
    public final List mListeners = new ArrayList();

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.wm.shell.onehanded.OneHandedTimeoutHandler$$ExternalSyntheticLambda0] */
    public OneHandedTimeoutHandler(ShellExecutor shellExecutor) {
        this.mMainExecutor = shellExecutor;
    }

    public boolean hasScheduledTimeout() {
        return ((HandlerExecutor) this.mMainExecutor).mHandler.hasCallbacks(this.mTimeoutRunnable);
    }

    public final void resetTimer() {
        ShellExecutor shellExecutor = this.mMainExecutor;
        OneHandedTimeoutHandler$$ExternalSyntheticLambda0 oneHandedTimeoutHandler$$ExternalSyntheticLambda0 = this.mTimeoutRunnable;
        ((HandlerExecutor) shellExecutor).removeCallbacks(oneHandedTimeoutHandler$$ExternalSyntheticLambda0);
        int i = this.mTimeout;
        if (i == 0 || i == 0) {
            return;
        }
        ((HandlerExecutor) shellExecutor).executeDelayed(oneHandedTimeoutHandler$$ExternalSyntheticLambda0, this.mTimeoutMs);
    }
}
