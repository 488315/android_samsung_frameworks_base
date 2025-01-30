package com.android.p038wm.shell.onehanded;

import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OneHandedTimeoutHandler {
    public final ShellExecutor mMainExecutor;
    public int mTimeout = 8;
    public long mTimeoutMs = TimeUnit.SECONDS.toMillis(8);
    public final OneHandedTimeoutHandler$$ExternalSyntheticLambda0 mTimeoutRunnable = new Runnable() { // from class: com.android.wm.shell.onehanded.OneHandedTimeoutHandler$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            ArrayList arrayList = (ArrayList) OneHandedTimeoutHandler.this.mListeners;
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0) {
                    return;
                } else {
                    ((OneHandedController$$ExternalSyntheticLambda3) arrayList.get(size)).f$0.stopOneHanded(6);
                }
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
        ((HandlerExecutor) shellExecutor).executeDelayed(this.mTimeoutMs, oneHandedTimeoutHandler$$ExternalSyntheticLambda0);
    }
}
