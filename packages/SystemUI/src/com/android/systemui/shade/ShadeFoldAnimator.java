package com.android.systemui.shade;

import android.view.ViewGroup;
import com.android.systemui.unfold.FoldAodAnimationController$startAnimationRunnable$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ShadeFoldAnimator {
    void cancelFoldToAodAnimation();

    ViewGroup getView();

    void prepareFoldToAodAnimation();

    void startFoldToAodAnimation(FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass1 anonymousClass1, FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass2 anonymousClass2, FoldAodAnimationController$startAnimationRunnable$1.AnonymousClass3 anonymousClass3);
}
