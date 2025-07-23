package com.android.systemui.aibrief.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.LinearLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.aibrief.data.NowBarData;
import java.io.ByteArrayOutputStream;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BriefNowBarBaseView extends LinearLayout {
    public static final int $stable = 0;
    public static final long FOUR_STAR_ANIMATION_DURATION = 150;
    public static final float NOWBAR_SUBTEXT_VIEW_ALPHA = 0.6f;
    public static final float NOWBAR_VIEW_ALPHA = 0.7f;
    public static final Companion Companion = new Companion(null);
    private static final PathInterpolator TRANSLATION_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final PathInterpolator getTRANSLATION_INTERPOLATOR() {
            return BriefNowBarBaseView.TRANSLATION_INTERPOLATOR;
        }

        private Companion() {
        }
    }

    public BriefNowBarBaseView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public final void dismissAnimation(final LottieAnimationView lottieAnimationView, final Function1 function1) {
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setDuration(150L);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.aibrief.ui.BriefNowBarBaseView$dismissAnimation$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LottieAnimationView.this.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
                function1.mo779invoke((Float) ofFloat.getAnimatedValue());
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.aibrief.ui.BriefNowBarBaseView$dismissAnimation$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                LottieAnimationView.this.setAlpha(0.0f);
            }
        });
        ofFloat.start();
    }

    public abstract void initAnimatedViews();

    public abstract void resetViews();

    public final void showAnimation(final LottieAnimationView lottieAnimationView) {
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(150L);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.aibrief.ui.BriefNowBarBaseView$showAnimation$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LottieAnimationView.this.setAlpha(((Float) ofFloat.getAnimatedValue()).floatValue());
            }
        });
        ofFloat.start();
    }

    public abstract void startFourStarAnimation();

    public final Bitmap toBitmap(byte[] bArr) {
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        decodeByteArray.compress(Bitmap.CompressFormat.PNG, 100, new ByteArrayOutputStream());
        return decodeByteArray;
    }

    public abstract void updateNowBarData(NowBarData nowBarData, GradientDrawable gradientDrawable);

    public abstract void updateNowBarResources();

    public abstract void updateViewAlpha(boolean z);

    public /* synthetic */ BriefNowBarBaseView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public BriefNowBarBaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
