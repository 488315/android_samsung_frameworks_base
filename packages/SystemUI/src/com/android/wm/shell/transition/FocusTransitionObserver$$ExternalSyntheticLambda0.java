package com.android.wm.shell.transition;

import com.android.wm.shell.shared.FocusTransitionListener;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FocusTransitionObserver$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FocusTransitionObserver f$0;
    public final /* synthetic */ FocusTransitionListener f$1;

    public /* synthetic */ FocusTransitionObserver$$ExternalSyntheticLambda0(FocusTransitionObserver focusTransitionObserver, FocusTransitionListener focusTransitionListener, int i) {
        this.$r8$classId = i;
        this.f$0 = focusTransitionObserver;
        this.f$1 = focusTransitionListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FocusTransitionObserver focusTransitionObserver = this.f$0;
                this.f$1.onFocusedDisplayChanged(focusTransitionObserver.mFocusedDisplayId);
                focusTransitionObserver.mTmpTasksToBeNotified.forEach(new FocusTransitionObserver$$ExternalSyntheticLambda1(focusTransitionObserver));
                break;
            default:
                this.f$1.onFocusedDisplayChanged(this.f$0.mFocusedDisplayId);
                break;
        }
    }
}
