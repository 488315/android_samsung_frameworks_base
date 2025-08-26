package com.android.wm.shell.transition;

import android.app.ActivityManager;
import com.android.wm.shell.shared.FocusTransitionListener;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class FocusTransitionObserver$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ FocusTransitionObserver f$0;

    public /* synthetic */ FocusTransitionObserver$$ExternalSyntheticLambda1(FocusTransitionObserver focusTransitionObserver) {
        this.f$0 = focusTransitionObserver;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        FocusTransitionObserver focusTransitionObserver = this.f$0;
        final ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) focusTransitionObserver.mFocusedTaskOnDisplay.get(runningTaskInfo.displayId);
        final boolean z = runningTaskInfo2 != null && runningTaskInfo2.taskId == runningTaskInfo.taskId;
        final boolean zHasGlobalFocus = focusTransitionObserver.hasGlobalFocus(runningTaskInfo);
        ((HashMap) focusTransitionObserver.mLocalListeners).forEach(new BiConsumer() { // from class: com.android.wm.shell.transition.FocusTransitionObserver$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj2, Object obj3) {
                final ActivityManager.RunningTaskInfo runningTaskInfo3 = runningTaskInfo;
                final boolean z2 = z;
                final boolean z3 = zHasGlobalFocus;
                final FocusTransitionListener focusTransitionListener = (FocusTransitionListener) obj2;
                ((Executor) obj3).execute(new Runnable() { // from class: com.android.wm.shell.transition.FocusTransitionObserver$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        FocusTransitionListener focusTransitionListener2 = focusTransitionListener;
                        ActivityManager.RunningTaskInfo runningTaskInfo4 = runningTaskInfo3;
                        focusTransitionListener2.onFocusedTaskChanged(runningTaskInfo4.taskId, z2, z3);
                    }
                });
            }
        });
    }
}
