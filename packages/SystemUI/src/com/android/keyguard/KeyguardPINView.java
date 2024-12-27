package com.android.keyguard;

import android.animation.ValueAnimator;
import android.app.FragmentBreadCrumbs;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.LinearLayout;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardInputView;
import com.android.settingslib.animation.DisappearAnimationUtils;
import com.android.systemui.R;

public class KeyguardPINView extends KeyguardSecPinBasedInputView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAlreadyUsingSplitBouncer;
    public final ValueAnimator mAppearAnimator;
    public View mBouncerMessageArea;
    public final DisappearAnimationUtils mDisappearAnimationUtils;
    public final DisappearAnimationUtils mDisappearAnimationUtilsLocked;
    public final int mDisappearYTranslation;
    public boolean mIsSmallLockScreenLandscapeEnabled;
    public int mLastDevicePosture;
    public View[][] mViews;
    public final int mYTrans;
    public final int mYTransOffset;

    public KeyguardPINView(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getPasswordTextViewId() {
        return R.id.pinEntry;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getWrongPasswordStringId() {
        return R.string.kg_wrong_pin;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final void onDevicePostureChanged(int i) {
        if (this.mLastDevicePosture == i) {
            return;
        }
        this.mLastDevicePosture = i;
        if (this.mIsSmallLockScreenLandscapeEnabled) {
            boolean z = i == 1 && getResources().getConfiguration().orientation == 2 && getResources().getBoolean(R.bool.update_bouncer_constraints);
            if (this.mAlreadyUsingSplitBouncer == z || !this.mIsSmallLockScreenLandscapeEnabled) {
                return;
            }
            this.mAlreadyUsingSplitBouncer = z;
            if (z) {
                throw null;
            }
            if (this.mLastDevicePosture != 2) {
                throw null;
            }
            if (((LinearLayout) this).mContext.getResources().getConfiguration().orientation != 1) {
                throw null;
            }
            throw null;
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputView, com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardInputView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBouncerMessageArea = findViewById(R.id.bouncer_message_area);
        this.mViews = new View[][]{new View[]{findViewById(R.id.row0), null, null}, new View[]{findViewById(R.id.key1), findViewById(R.id.key2), findViewById(R.id.key3)}, new View[]{findViewById(R.id.key4), findViewById(R.id.key5), findViewById(R.id.key6)}, new View[]{findViewById(R.id.key7), findViewById(R.id.key8), findViewById(R.id.key9)}, new View[]{findViewById(R.id.delete_button), findViewById(R.id.key0), findViewById(R.id.key_enter)}, new View[]{null, this.mEcaView, null}};
    }

    @Override // com.android.keyguard.KeyguardInputView
    public final void startAppearAnimation() {
        setAlpha(1.0f);
        setTranslationY(0.0f);
        if (this.mAppearAnimator.isRunning()) {
            this.mAppearAnimator.cancel();
        }
        this.mAppearAnimator.setDuration(650L);
        this.mAppearAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardPINView$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardPINView keyguardPINView = KeyguardPINView.this;
                int i = KeyguardPINView.$r8$clinit;
                keyguardPINView.getClass();
                float animatedFraction = valueAnimator.getAnimatedFraction();
                Interpolator interpolator = Interpolators.STANDARD_DECELERATE;
                Interpolator interpolator2 = Interpolators.LEGACY_DECELERATE;
                float interpolation = ((PathInterpolator) interpolator).getInterpolation(animatedFraction);
                View view = keyguardPINView.mBouncerMessageArea;
                float f = keyguardPINView.mYTrans;
                view.setTranslationY(f - (f * interpolation));
                keyguardPINView.mBouncerMessageArea.setAlpha(interpolation);
                int i2 = 0;
                while (true) {
                    View[][] viewArr = keyguardPINView.mViews;
                    if (i2 >= viewArr.length) {
                        return;
                    }
                    for (FragmentBreadCrumbs fragmentBreadCrumbs : viewArr[i2]) {
                        if (fragmentBreadCrumbs != 0) {
                            fragmentBreadCrumbs.setAlpha(((PathInterpolator) interpolator2).getInterpolation(MathUtils.constrain((animatedFraction - (i2 * 0.075f)) / (1.0f - (keyguardPINView.mViews.length * 0.075f)), 0.0f, 1.0f)));
                            float f2 = (keyguardPINView.mYTransOffset * i2) + keyguardPINView.mYTrans;
                            fragmentBreadCrumbs.setTranslationY(f2 - (f2 * interpolation));
                            if (fragmentBreadCrumbs instanceof NumPadAnimationListener) {
                            }
                        }
                    }
                    i2++;
                }
            }
        });
        this.mAppearAnimator.addListener(new KeyguardInputView.AnonymousClass1(19));
        this.mAppearAnimator.start();
    }

    public KeyguardPINView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAppearAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mAlreadyUsingSplitBouncer = false;
        this.mIsSmallLockScreenLandscapeEnabled = false;
        this.mLastDevicePosture = 0;
        this.mDisappearAnimationUtils = new DisappearAnimationUtils(context, 125L, 0.6f, 0.45f, AnimationUtils.loadInterpolator(((LinearLayout) this).mContext, android.R.interpolator.fast_out_linear_in));
        this.mDisappearAnimationUtilsLocked = new DisappearAnimationUtils(context, 187L, 0.6f, 0.45f, AnimationUtils.loadInterpolator(((LinearLayout) this).mContext, android.R.interpolator.fast_out_linear_in));
        this.mDisappearYTranslation = getResources().getDimensionPixelSize(R.dimen.disappear_y_translation);
        this.mYTrans = getResources().getDimensionPixelSize(R.dimen.pin_view_trans_y_entry);
        this.mYTransOffset = getResources().getDimensionPixelSize(R.dimen.pin_view_trans_y_entry_offset);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
    }
}
