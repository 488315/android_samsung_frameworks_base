package com.android.wm.shell.bubbles;

import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda29 implements Consumer {
    public final /* synthetic */ BubbleStackView f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda29(BubbleStackView bubbleStackView) {
        this.f$0 = bubbleStackView;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        BubbleStackView bubbleStackView = this.f$0;
        PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
        bubbleStackView.getClass();
        if (((Boolean) obj).booleanValue() || !bubbleStackView.mIsExpanded) {
            return;
        }
        bubbleStackView.startMonitoringSwipeUpGesture();
    }
}
