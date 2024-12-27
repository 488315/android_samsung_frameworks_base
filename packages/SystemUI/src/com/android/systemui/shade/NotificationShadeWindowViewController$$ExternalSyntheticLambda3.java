package com.android.systemui.shade;

import com.android.keyguard.KeyguardUnfoldTransition;
import com.android.systemui.R;
import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationShadeWindowViewController$$ExternalSyntheticLambda3 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        KeyguardUnfoldTransition keyguardUnfoldTransition = (KeyguardUnfoldTransition) obj;
        float dimensionPixelSize = keyguardUnfoldTransition.context.getResources().getDimensionPixelSize(R.dimen.keyguard_unfold_translation_x);
        ((UnfoldConstantTranslateAnimator) keyguardUnfoldTransition.translateAnimator$delegate.getValue()).init(keyguardUnfoldTransition.shadeWindowView, dimensionPixelSize);
        ((UnfoldConstantTranslateAnimator) keyguardUnfoldTransition.shortcutButtonsAnimator$delegate.getValue()).init(keyguardUnfoldTransition.keyguardRootView, dimensionPixelSize);
    }
}
