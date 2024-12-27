package com.android.systemui.shade;

import android.view.ViewGroup;
import com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1;

public interface ShadeFoldAnimator {
    void cancelFoldToAodAnimation();

    ViewGroup getView();

    void prepareFoldToAodAnimation();

    void startFoldToAodAnimation(FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass1 anonymousClass1, FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass2 anonymousClass2, FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass3 anonymousClass3);
}
