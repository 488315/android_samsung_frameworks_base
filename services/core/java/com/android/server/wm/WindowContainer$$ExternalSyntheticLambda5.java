package com.android.server.wm;

import com.android.server.display.DisplayManagerService$$ExternalSyntheticLambda5;

public final /* synthetic */ class WindowContainer$$ExternalSyntheticLambda5
        implements SurfaceAnimator.OnAnimationFinishedCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WindowContainer$$ExternalSyntheticLambda5(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
    public final void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
        switch (this.$r8$classId) {
            case 0:
                ((WindowContainer) this.f$0).onAnimationFinished(i, animationAdapter);
                break;
            default:
                ((WindowContainer.AnimationRunnerBuilder) this.f$0)
                        .mOnAnimationFinished.forEach(
                                new DisplayManagerService$$ExternalSyntheticLambda5(0));
                break;
        }
    }
}
