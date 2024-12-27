package com.android.server.wm;

import android.os.RemoteException;
import android.view.RemoteAnimationTarget;
import android.window.IBackAnimationFinishedCallback;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class BackNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ BackNavigationController.AnimationHandler.ScheduleAnimationBuilder
            f$0;
    public final /* synthetic */ RemoteAnimationTarget[] f$1;
    public final /* synthetic */ IBackAnimationFinishedCallback f$2;

    public /* synthetic */
    BackNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0(
            BackNavigationController.AnimationHandler.ScheduleAnimationBuilder
                    scheduleAnimationBuilder,
            RemoteAnimationTarget[] remoteAnimationTargetArr,
            BackNavigationController.AnimationHandler.ScheduleAnimationBuilder.AnonymousClass1
                    anonymousClass1) {
        this.f$0 = scheduleAnimationBuilder;
        this.f$1 = remoteAnimationTargetArr;
        this.f$2 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BackNavigationController.AnimationHandler.ScheduleAnimationBuilder
                scheduleAnimationBuilder = this.f$0;
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.f$1;
        IBackAnimationFinishedCallback iBackAnimationFinishedCallback = this.f$2;
        scheduleAnimationBuilder.getClass();
        try {
            if (!scheduleAnimationBuilder.this$0.hasTargetDetached()) {
                int i = BackNavigationController.sDefaultAnimationResId;
                if (remoteAnimationTargetArr != null && remoteAnimationTargetArr.length != 0) {
                    for (int length = remoteAnimationTargetArr.length - 1; length >= 0; length--) {
                        if (remoteAnimationTargetArr[length].leash.isValid()) {}
                    }
                    scheduleAnimationBuilder
                            .mBackAnimationAdapter
                            .getRunner()
                            .onAnimationStart(
                                    remoteAnimationTargetArr,
                                    (RemoteAnimationTarget[]) null,
                                    (RemoteAnimationTarget[]) null,
                                    iBackAnimationFinishedCallback);
                    return;
                }
            }
            scheduleAnimationBuilder.mNavigationMonitor.cancelBackNavigating("cancelAnimation");
            scheduleAnimationBuilder.mBackAnimationAdapter.getRunner().onAnimationCancelled();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
