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

/* loaded from: classes.dex */
public abstract class BriefNowBarBaseView extends LinearLayout {
    public static final int $stable = 0;
    public static final long FOUR_STAR_ANIMATION_DURATION = 150;
    public static final float NOWBAR_SUBTEXT_VIEW_ALPHA = 0.6f;
    public static final float NOWBAR_VIEW_ALPHA = 0.7f;
    public static final Companion Companion = new Companion(null);
    private static final PathInterpolator TRANSLATION_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

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

    /* JADX WARN: Multi-variable type inference failed */
    public BriefNowBarBaseView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    public final void dismissAnimation(final LottieAnimationView lottieAnimationView, final Function1 function1) {
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat.setDuration(150L);
        valueAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.aibrief.ui.BriefNowBarBaseView$dismissAnimation$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                lottieAnimationView.setAlpha(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue());
                function1.mo781invoke((Float) valueAnimatorOfFloat.getAnimatedValue());
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.aibrief.ui.BriefNowBarBaseView$dismissAnimation$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                lottieAnimationView.setAlpha(0.0f);
            }
        });
        valueAnimatorOfFloat.start();
    }

    public abstract void initAnimatedViews();

    public abstract void resetViews();

    public final void showAnimation(final LottieAnimationView lottieAnimationView) {
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(150L);
        valueAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.aibrief.ui.BriefNowBarBaseView$showAnimation$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                lottieAnimationView.setAlpha(((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat.start();
    }

    public abstract void startFourStarAnimation();

    public final Bitmap toBitmap(byte[] bArr) {
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        bitmapDecodeByteArray.compress(Bitmap.CompressFormat.PNG, 100, new ByteArrayOutputStream());
        return bitmapDecodeByteArray;
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
