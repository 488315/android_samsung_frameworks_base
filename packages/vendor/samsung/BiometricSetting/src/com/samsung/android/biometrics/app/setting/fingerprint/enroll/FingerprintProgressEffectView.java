package com.samsung.android.biometrics.app.setting.fingerprint.enroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.biometrics.app.setting.R;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public class FingerprintProgressEffectView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public LottieAnimationView mFingerLottieEffect;
    public int mMaxFrame;
    public float mPercent;
    public ImageView mSuccessImageView;
    public float mTempPercent;

    public FingerprintProgressEffectView(Context context) {
        super(context);
        this.mPercent = RecyclerView.DECELERATION_RATE;
        this.mTempPercent = RecyclerView.DECELERATION_RATE;
        init(context, null);
    }

    @Override // android.view.View
    public final void destroyDrawingCache() {
        clearAnimation();
        removeAllViews();
        super.destroyDrawingCache();
    }

    public final void init(Context context, AttributeSet attributeSet) {
        if (context == null || attributeSet == null) {
            throw new IllegalArgumentException("context or attrs is null.");
        }
        setLayoutDirection(0);
        this.mContext = context;
        this.mPercent = RecyclerView.DECELERATION_RATE;
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
        this.mSuccessImageView.setAnimation(
                AnimationUtils.loadAnimation(getContext(), R.anim.fingerprint_enroll_success));
        this.mSuccessImageView.setLayoutParams(layoutParams);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x002c, code lost:

       if (r0 >= 100.0f) goto L13;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setFingerStatus(int r6) {
        /*
            r5 = this;
            r0 = 1
            r1 = 0
            if (r6 == 0) goto L35
            if (r6 == r0) goto L7
            goto L3e
        L7:
            float r6 = r5.mPercent
            r0 = 1065353216(0x3f800000, float:1.0)
            float r0 = r0 + r6
            r2 = 0
            int r2 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r2 != 0) goto L14
            r2 = 1084227584(0x40a00000, float:5.0)
            float r0 = r0 + r2
        L14:
            r2 = 1117782016(0x42a00000, float:80.0)
            int r3 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            r4 = 1073741824(0x40000000, float:2.0)
            if (r3 >= 0) goto L24
            int r3 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r3 < 0) goto L24
        L20:
            float r6 = r6 + r2
            float r0 = r6 / r4
            goto L2f
        L24:
            r2 = 1120403456(0x42c80000, float:100.0)
            int r3 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r3 >= 0) goto L2f
            int r3 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r3 < 0) goto L2f
            goto L20
        L2f:
            r5.mTempPercent = r0
            r5.setPercent(r0, r1, r1)
            goto L3e
        L35:
            float r6 = r5.mPercent
            r5.setPercent(r6, r1, r0)
            float r6 = r5.mPercent
            r5.mTempPercent = r6
        L3e:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.fingerprint.enroll.FingerprintProgressEffectView.setFingerStatus(int):void");
    }

    public void setPercent(float f) {
        setPercent(f, true, false);
    }

    public final void setPercent(float f, boolean z, boolean z2) {
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
        float f5 = this.mMaxFrame;
        int i = (int) ((f3 * f5) / 100.0f);
        int i2 = (int) ((f5 * f2) / 100.0f);
        if (i > i2) {
            return;
        }
        this.mFingerLottieEffect.setMinAndMaxFrame(i, i2);
        if (i + i2 > 0) {
            this.mFingerLottieEffect.playAnimation();
        }
        if (z) {
            this.mPercent = f;
        }
    }

    public FingerprintProgressEffectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPercent = RecyclerView.DECELERATION_RATE;
        this.mTempPercent = RecyclerView.DECELERATION_RATE;
        init(context, attributeSet);
    }

    public FingerprintProgressEffectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPercent = RecyclerView.DECELERATION_RATE;
        this.mTempPercent = RecyclerView.DECELERATION_RATE;
        init(context, attributeSet);
    }
}
