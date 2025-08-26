package com.android.systemui.shade;

import com.android.keyguard.KeyguardUnfoldTransition;
import com.android.systemui.R;
import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationShadeWindowViewController$$ExternalSyntheticLambda4 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        KeyguardUnfoldTransition keyguardUnfoldTransition = (KeyguardUnfoldTransition) obj;
        float dimensionPixelSize = keyguardUnfoldTransition.context.getResources().getDimensionPixelSize(R.dimen.keyguard_unfold_translation_x);
        UnfoldConstantTranslateAnimator unfoldConstantTranslateAnimator = (UnfoldConstantTranslateAnimator) keyguardUnfoldTransition.translateAnimator$delegate.getValue();
        if (unfoldConstantTranslateAnimator.rootView == null) {
            unfoldConstantTranslateAnimator.progressProvider.addCallback(unfoldConstantTranslateAnimator);
        }
        unfoldConstantTranslateAnimator.rootView = keyguardUnfoldTransition.shadeWindowView;
        unfoldConstantTranslateAnimator.translationMax = dimensionPixelSize;
        UnfoldConstantTranslateAnimator unfoldConstantTranslateAnimator2 = (UnfoldConstantTranslateAnimator) keyguardUnfoldTransition.shortcutButtonsAnimator$delegate.getValue();
        if (unfoldConstantTranslateAnimator2.rootView == null) {
            unfoldConstantTranslateAnimator2.progressProvider.addCallback(unfoldConstantTranslateAnimator2);
        }
        unfoldConstantTranslateAnimator2.rootView = keyguardUnfoldTransition.keyguardRootView;
        unfoldConstantTranslateAnimator2.translationMax = dimensionPixelSize;
    }
}
