package com.samsung.android.biometrics.app.setting.fingerprint.enroll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.biometrics.app.setting.R;

/* loaded from: classes.dex */
public class FingerprintProgressEffectView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    private Context mContext;
    private LottieAnimationView mFingerLottieEffect;
    private int mMaxFrame;
    private float mPercent;
    private ImageView mSuccessImageView;
    private float mTempPercent;

    public FingerprintProgressEffectView(Context context) {
        super(context);
        this.mPercent = 0.0f;
        this.mTempPercent = 0.0f;
        init(context, null);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (context == null || attributeSet == null) {
            throw new IllegalArgumentException("context or attrs is null.");
        }
        setLayoutDirection(0);
        this.mContext = context;
        this.mPercent = 0.0f;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        this.mMaxFrame = getResources().getInteger(R.integer.finger_enroll_progress_vi_max_frame);
        LottieAnimationView lottieAnimationView = new LottieAnimationView(this.mContext);
        this.mFingerLottieEffect = lottieAnimationView;
        lottieAnimationView.setAnimation("fingerprint_registration_progress.json");
        this.mFingerLottieEffect.setLayoutParams(layoutParams);
        addView(this.mFingerLottieEffect);
        ImageView imageView = new ImageView(this.mContext);
        this.mSuccessImageView = imageView;
        imageView.setImageResource(R.drawable.sec_fingerprint_ic_success);
        this.mSuccessImageView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fingerprint_enroll_success));
        this.mSuccessImageView.setLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public final void destroyDrawingCache() {
        clearAnimation();
        removeAllViews();
        super.destroyDrawingCache();
    }

    public final void setCompleteImage() {
        LottieAnimationView lottieAnimationView = this.mFingerLottieEffect;
        if (lottieAnimationView != null && lottieAnimationView.getVisibility() == 0) {
            this.mFingerLottieEffect.animate().alpha(0.0f).setDuration(150L).setListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintProgressEffectView.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    FingerprintProgressEffectView fingerprintProgressEffectView = FingerprintProgressEffectView.this;
                    fingerprintProgressEffectView.removeView(fingerprintProgressEffectView.mFingerLottieEffect);
                }
            });
        }
        ImageView imageView = this.mSuccessImageView;
        if (imageView != null) {
            addView(imageView);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x002c, code lost:
    
        if (r0 >= 100.0f) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setFingerStatus(int i) {
        if (i == 0) {
            setPercent(this.mPercent, false, true);
            this.mTempPercent = this.mPercent;
            return;
        }
        if (i != 1) {
            return;
        }
        float f = this.mPercent;
        float f2 = 1.0f + f;
        if (f == 0.0f) {
            f2 += 5.0f;
        }
        float f3 = 80.0f;
        if (f >= 80.0f || f2 < 80.0f) {
            f3 = 100.0f;
            if (f < 100.0f) {
            }
            this.mTempPercent = f2;
            setPercent(f2, false, false);
        }
        f2 = (f + f3) / 2.0f;
        this.mTempPercent = f2;
        setPercent(f2, false, false);
    }

    public void setPercent(float f) {
        setPercent(f, true, false);
    }

    private void setPercent(float f, boolean z, boolean z2) {
        LottieAnimationView lottieAnimationView;
        float f2;
        float f3 = this.mPercent;
        if (f3 > f || (lottieAnimationView = this.mFingerLottieEffect) == null) {
            return;
        }
        if (z2) {
            f2 = this.mTempPercent;
            if (f > f2) {
                return;
            }
            lottieAnimationView.setSpeed(-1.0f);
            f3 = f;
        } else {
            if (z) {
                float f4 = this.mTempPercent;
                if (f4 > f3) {
                    f3 = f4;
                }
            }
            if (f3 > f) {
                return;
            }
            lottieAnimationView.setSpeed(1.0f);
            f2 = f;
        }
        int i = this.mMaxFrame;
        int i2 = (int) ((i * f3) / 100.0f);
        int i3 = (int) ((i * f2) / 100.0f);
        if (i2 > i3) {
            return;
        }
        this.mFingerLottieEffect.setMinAndMaxFrame(i2, i3);
        if (i2 + i3 > 0) {
            this.mFingerLottieEffect.playAnimation();
        }
        if (z) {
            this.mPercent = f;
        }
    }

    public FingerprintProgressEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPercent = 0.0f;
        this.mTempPercent = 0.0f;
        init(context, attributeSet);
    }

    public FingerprintProgressEffectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPercent = 0.0f;
        this.mTempPercent = 0.0f;
        init(context, attributeSet);
    }
}
