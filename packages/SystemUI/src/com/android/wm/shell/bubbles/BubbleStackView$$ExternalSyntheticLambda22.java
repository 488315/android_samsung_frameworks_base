package com.android.wm.shell.bubbles;

import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda22 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
        BubbleExpandedView bubbleExpandedView = ((Bubble) obj).mExpandedView;
        if (bubbleExpandedView != null) {
            bubbleExpandedView.applyThemeAttrs();
        }
    }
}
