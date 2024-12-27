package com.android.systemui.shade;

import android.view.ViewGroup;
import com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1;

public final class ShadeFoldAnimatorEmptyImpl implements ShadeFoldAnimator {
    @Override // com.android.systemui.shade.ShadeFoldAnimator
    public final ViewGroup getView() {
        return null;
    }

    @Override // com.android.systemui.shade.ShadeFoldAnimator
    public final void cancelFoldToAodAnimation() {
    }

    @Override // com.android.systemui.shade.ShadeFoldAnimator
    public final void prepareFoldToAodAnimation() {
    }

    @Override // com.android.systemui.shade.ShadeFoldAnimator
    public final void startFoldToAodAnimation(FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass1 anonymousClass1, FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass2 anonymousClass2, FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass3 anonymousClass3) {
    }
}
