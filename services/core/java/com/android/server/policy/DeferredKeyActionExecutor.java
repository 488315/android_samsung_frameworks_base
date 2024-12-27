package com.android.server.policy;

import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DeferredKeyActionExecutor {
    public static final boolean DEBUG = PhoneWindowManager.DEBUG_INPUT;
    public final SparseArray mBuffers = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TimedActionsBuffer {
        public final List mActions = new ArrayList();
        public final long mDownTime;
        public boolean mExecutable;
        public final int mKeyCode;

        public TimedActionsBuffer(int i, long j) {
            this.mKeyCode = i;
            this.mDownTime = j;
        }
    }

    public final TimedActionsBuffer getActionsBufferWithLazyCleanUp(int i, long j) {
        TimedActionsBuffer timedActionsBuffer = (TimedActionsBuffer) this.mBuffers.get(i);
        if (timedActionsBuffer != null && timedActionsBuffer.mDownTime == j) {
            return timedActionsBuffer;
        }
        if (DEBUG && timedActionsBuffer != null) {
            Log.d("DeferredKeyAction", "getActionsBufferWithLazyCleanUp: cleaning up gesture actions for key " + KeyEvent.keyCodeToString(i));
        }
        TimedActionsBuffer timedActionsBuffer2 = new TimedActionsBuffer(i, j);
        this.mBuffers.put(i, timedActionsBuffer2);
        return timedActionsBuffer2;
    }

    public final void queueKeyAction(Runnable runnable, long j) {
        TimedActionsBuffer actionsBufferWithLazyCleanUp = getActionsBufferWithLazyCleanUp(264, j);
        if (!actionsBufferWithLazyCleanUp.mExecutable) {
            ((ArrayList) actionsBufferWithLazyCleanUp.mActions).add(runnable);
            return;
        }
        if (DEBUG) {
            Log.i("DeferredKeyAction", "addAction: execute action for key " + KeyEvent.keyCodeToString(actionsBufferWithLazyCleanUp.mKeyCode));
        }
        runnable.run();
    }
}
