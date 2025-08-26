package com.android.wm.shell.transition;

import android.app.ActivityManager;
import android.util.ArraySet;
import android.util.SparseArray;
import com.android.wm.shell.shared.FocusTransitionListener;
import com.android.wm.shell.shared.IFocusTransitionListener$Stub$Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class FocusTransitionObserver {
    public IFocusTransitionListener$Stub$Proxy mRemoteListener;
    public final Map mLocalListeners = new HashMap();
    public int mFocusedDisplayId = 0;
    public final SparseArray mFocusedTaskOnDisplay = new SparseArray();
    public final ArraySet mTmpTasksToBeNotified = new ArraySet();

    public final boolean hasGlobalFocus(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        int i = runningTaskInfo.displayId;
        return i == this.mFocusedDisplayId && (runningTaskInfo2 = (ActivityManager.RunningTaskInfo) this.mFocusedTaskOnDisplay.get(i)) != null && runningTaskInfo2.taskId == runningTaskInfo.taskId;
    }

    public final void setLocalFocusTransitionListener(FocusTransitionListener focusTransitionListener, Executor executor) {
        ((HashMap) this.mLocalListeners).put(focusTransitionListener, executor);
        executor.execute(new FocusTransitionObserver$$ExternalSyntheticLambda0(this, focusTransitionListener, 0));
    }
}
