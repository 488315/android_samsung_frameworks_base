package com.android.server.wm;

import com.android.server.wm.TransitionController;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class TransitionController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TransitionController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ TransitionController$$ExternalSyntheticLambda0(TransitionController transitionController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = transitionController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mAtm.mWindowOrganizerController.startTransition(-1, ((Transition) this.f$1).mToken, null);
                return;
            default:
                TransitionController transitionController = this.f$0;
                TransitionController.QueuedTransition queuedTransition = (TransitionController.QueuedTransition) this.f$1;
                WindowManagerGlobalLock windowManagerGlobalLock = transitionController.mAtm.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        queuedTransition.mOnStartCollect.onCollectStarted(true);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                return;
        }
    }
}
