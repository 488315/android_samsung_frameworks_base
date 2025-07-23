package com.android.systemui.aibrief.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BriefNowBarCoverView$startFourStarAnimation$1$1 extends AnimatorListenerAdapter {
    final /* synthetic */ LottieAnimationView $this_apply;
    final /* synthetic */ BriefNowBarCoverView this$0;

    public BriefNowBarCoverView$startFourStarAnimation$1$1(BriefNowBarCoverView briefNowBarCoverView, LottieAnimationView lottieAnimationView) {
        this.this$0 = briefNowBarCoverView;
        this.$this_apply = lottieAnimationView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onAnimationEnd$lambda$0(BriefNowBarCoverView briefNowBarCoverView, float f) {
        FrameLayout iconContainer;
        TextView subText;
        iconContainer = briefNowBarCoverView.getIconContainer();
        iconContainer.setAlpha(1.0f - f);
        subText = briefNowBarCoverView.getSubText();
        subText.setAlpha(0.6f - f);
        return Unit.INSTANCE;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        final BriefNowBarCoverView briefNowBarCoverView = this.this$0;
        briefNowBarCoverView.dismissAnimation(this.$this_apply, new Function1() { // from class: com.android.systemui.aibrief.ui.BriefNowBarCoverView$startFourStarAnimation$1$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Unit onAnimationEnd$lambda$0;
                onAnimationEnd$lambda$0 = BriefNowBarCoverView$startFourStarAnimation$1$1.onAnimationEnd$lambda$0(BriefNowBarCoverView.this, ((Float) obj).floatValue());
                return onAnimationEnd$lambda$0;
            }
        });
        this.this$0.translationAnimation();
    }
}
