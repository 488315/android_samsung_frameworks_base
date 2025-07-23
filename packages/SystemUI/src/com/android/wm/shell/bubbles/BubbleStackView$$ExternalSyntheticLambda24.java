package com.android.wm.shell.bubbles;

import android.view.Choreographer;
import com.android.wm.shell.bubbles.BubbleStackView;
import com.android.wm.shell.shared.animation.PhysicsAnimator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda24 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleStackView f$0;
    public final /* synthetic */ BubbleViewProvider f$1;
    public final /* synthetic */ BubbleViewProvider f$2;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda24(BubbleStackView bubbleStackView, BubbleViewProvider bubbleViewProvider, BubbleViewProvider bubbleViewProvider2, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleStackView;
        this.f$1 = bubbleViewProvider;
        this.f$2 = bubbleViewProvider2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        switch (this.$r8$classId) {
            case 0:
                BubbleStackView bubbleStackView = this.f$0;
                BubbleViewProvider bubbleViewProvider = this.f$1;
                BubbleViewProvider bubbleViewProvider2 = this.f$2;
                bubbleStackView.mExpandedViewContainer.setAlpha(0.0f);
                BubbleStackView.SurfaceSynchronizer surfaceSynchronizer = bubbleStackView.mSurfaceSynchronizer;
                BubbleStackView$$ExternalSyntheticLambda24 bubbleStackView$$ExternalSyntheticLambda24 = new BubbleStackView$$ExternalSyntheticLambda24(bubbleStackView, bubbleViewProvider, bubbleViewProvider2, i);
                BubbleStackView.AnonymousClass1 anonymousClass1 = (BubbleStackView.AnonymousClass1) surfaceSynchronizer;
                anonymousClass1.getClass();
                Choreographer.getInstance().postFrameCallback(new BubbleStackView.AnonymousClass1.ChoreographerFrameCallbackC04251(anonymousClass1, bubbleStackView$$ExternalSyntheticLambda24));
                break;
            default:
                BubbleStackView bubbleStackView2 = this.f$0;
                BubbleViewProvider bubbleViewProvider3 = this.f$1;
                BubbleViewProvider bubbleViewProvider4 = this.f$2;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                if (bubbleViewProvider3 != null) {
                    bubbleStackView2.getClass();
                    bubbleViewProvider3.setTaskViewVisibility();
                }
                bubbleStackView2.updateExpandedBubble();
                bubbleStackView2.requestUpdate();
                bubbleStackView2.logBubbleEvent(bubbleViewProvider3, 4);
                bubbleStackView2.logBubbleEvent(bubbleViewProvider4, 3);
                BubbleController$$ExternalSyntheticLambda5 bubbleController$$ExternalSyntheticLambda5 = bubbleStackView2.mExpandListener;
                if (bubbleController$$ExternalSyntheticLambda5 != null && bubbleViewProvider3 != null) {
                    bubbleController$$ExternalSyntheticLambda5.onBubbleExpandChanged(bubbleViewProvider3.getKey(), false);
                }
                BubbleController$$ExternalSyntheticLambda5 bubbleController$$ExternalSyntheticLambda52 = bubbleStackView2.mExpandListener;
                if (bubbleController$$ExternalSyntheticLambda52 != null && bubbleViewProvider4 != null) {
                    bubbleController$$ExternalSyntheticLambda52.onBubbleExpandChanged(bubbleViewProvider4.getKey(), true);
                    break;
                }
                break;
        }
    }
}
