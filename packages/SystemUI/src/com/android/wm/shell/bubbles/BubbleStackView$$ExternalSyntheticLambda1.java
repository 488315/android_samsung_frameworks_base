package com.android.wm.shell.bubbles;

import android.graphics.PointF;
import android.view.View;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.bubbles.BubbleStackView;
import com.android.wm.shell.bubbles.animation.ExpandedAnimationController;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda1(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BubbleStackView bubbleStackView = (BubbleStackView) this.f$0;
                List list = (List) this.f$1;
                PhysicsAnimator.SpringConfig springConfig = BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG;
                bubbleStackView.getClass();
                for (int i = 0; i < list.size(); i++) {
                    bubbleStackView.mBubbleContainer.reorderView(((Bubble) list.get(i)).mIconView, i);
                }
                break;
            case 1:
                BubbleStackView bubbleStackView2 = (BubbleStackView) this.f$0;
                PointF pointF = (PointF) this.f$1;
                ExpandedAnimationController expandedAnimationController = bubbleStackView2.mExpandedAnimationController;
                boolean z = bubbleStackView2.mRemovingLastBubbleWhileExpanded;
                BubbleStackView$$ExternalSyntheticLambda5 bubbleStackView$$ExternalSyntheticLambda5 = new BubbleStackView$$ExternalSyntheticLambda5(bubbleStackView2, 13);
                expandedAnimationController.mAnimatingExpand = false;
                expandedAnimationController.mPreparingToCollapse = false;
                expandedAnimationController.mAnimatingCollapse = true;
                expandedAnimationController.mAfterCollapse = bubbleStackView$$ExternalSyntheticLambda5;
                expandedAnimationController.mCollapsePoint = pointF;
                expandedAnimationController.mFadeBubblesDuringCollapse = z;
                expandedAnimationController.startOrUpdatePathAnimation(false);
                break;
            default:
                BubbleStackView.AnonymousClass4 anonymousClass4 = (BubbleStackView.AnonymousClass4) this.f$0;
                View view = (View) this.f$1;
                BubbleStackView bubbleStackView3 = BubbleStackView.this;
                Bubble bubbleWithPredicate = BubbleData.getBubbleWithPredicate(bubbleStackView3.mBubbleData.mBubbles, new BubbleData$$ExternalSyntheticLambda7(view, 2));
                if (bubbleWithPredicate != null) {
                    BubbleData bubbleData = bubbleStackView3.mBubbleData;
                    String str = bubbleWithPredicate.mKey;
                    if (bubbleData.hasBubbleInStackWithKey(str)) {
                        if (bubbleStackView3.mIsExpanded && Collections.unmodifiableList(bubbleStackView3.mBubbleData.mBubbles).size() > 1 && bubbleWithPredicate.equals(bubbleStackView3.mExpandedBubble)) {
                            bubbleStackView3.mIsBubbleSwitchAnimating = true;
                        }
                        bubbleStackView3.mBubbleData.dismissBubbleWithKey(1, str);
                    }
                }
                bubbleStackView3.mBubbleSALogger.sendEventCDLog("QPNE0101", "type", SystemUIAnalytics.QPNE_VID_SINGLE);
                break;
        }
    }
}
