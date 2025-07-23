package com.android.systemui.keyguard;

import android.view.IRemoteAnimationFinishedCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WindowManagerOcclusionManager$unoccludeAnimationRunner$1$onAnimationStart$1 extends IRemoteAnimationFinishedCallback.Stub {
    public final /* synthetic */ IRemoteAnimationFinishedCallback $finishedCallback;
    public final /* synthetic */ WindowManagerOcclusionManager this$0;

    public WindowManagerOcclusionManager$unoccludeAnimationRunner$1$onAnimationStart$1(IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, WindowManagerOcclusionManager windowManagerOcclusionManager) {
        this.$finishedCallback = iRemoteAnimationFinishedCallback;
        this.this$0 = windowManagerOcclusionManager;
    }

    public final void onAnimationFinished() {
        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = this.$finishedCallback;
        if (iRemoteAnimationFinishedCallback != null) {
            iRemoteAnimationFinishedCallback.onAnimationFinished();
        }
        this.this$0.unoccludeAnimationFinishedCallback = null;
    }
}
