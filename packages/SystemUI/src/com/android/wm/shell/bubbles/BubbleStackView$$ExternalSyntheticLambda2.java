package com.android.wm.shell.bubbles;

import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda2 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
        return ((Bubble) obj).mIconView;
    }
}
