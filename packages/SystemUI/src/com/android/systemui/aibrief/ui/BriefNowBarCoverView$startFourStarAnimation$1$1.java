package com.android.systemui.aibrief.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.airbnb.lottie.LottieAnimationView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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
        briefNowBarCoverView.getIconContainer().setAlpha(1.0f - f);
        briefNowBarCoverView.getSubText().setAlpha(0.6f - f);
        return Unit.INSTANCE;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        final BriefNowBarCoverView briefNowBarCoverView = this.this$0;
        briefNowBarCoverView.dismissAnimation(this.$this_apply, new Function1() { // from class: com.android.systemui.aibrief.ui.BriefNowBarCoverView$startFourStarAnimation$1$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return BriefNowBarCoverView$startFourStarAnimation$1$1.onAnimationEnd$lambda$0(briefNowBarCoverView, ((Float) obj).floatValue());
            }
        });
        this.this$0.translationAnimation();
    }
}
