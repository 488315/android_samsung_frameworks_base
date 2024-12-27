package com.android.keyguard.biometrics;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.vibrate.VibrationUtil;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardBiometricToastView extends FrameLayout {
    public static final PathInterpolator INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    public static float mToastIconFrom = 1.28f;
    public final AnonymousClass1 mAnimHandler;
    public AnimatorSet mAnimatorSet;
    public String mAssetName;
    public Consumer mBiometricToastViewStateUpdater;
    public ValueAnimator mBodyAnimator;
    public int mCurrentToastViewWidth;
    public final Handler mHandler;
    public boolean mIsAnimating;
    public boolean mIsBackgroundAuth;
    public boolean mIsShowing;
    public LinearLayout mToastBodyView;
    public TextView mToastGuideText;
    public LottieAnimationView mToastIcon;
    public int mToastLockIconWidth;
    public FrameLayout mToastRoot;
    public int mToastViewMinWidth;
    public VibrationUtil mVibrationUtil;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public enum ToastType {
        Authenticating,
        /* JADX INFO: Fake field, exist only in values array */
        AuthenticationSuccess,
        /* JADX INFO: Fake field, exist only in values array */
        AuthenticationFail,
        FingerprintAuthenticationSuccess,
        FingerprintAuthenticationFail,
        FingerprintAuthenticationError,
        FingerprintAuthenticationHelp,
        FaceAuthenticationFail,
        FaceAuthenticationError,
        /* JADX INFO: Fake field, exist only in values array */
        FaceAuthenticationHelp
    }

    public KeyguardBiometricToastView(Context context) {
        this(context, null);
    }

    public final void changeTextAnim(float f, float f2, ToastType toastType) {
        final int i = this.mCurrentToastViewWidth - this.mToastViewMinWidth;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        this.mBodyAnimator = ofFloat;
        ofFloat.setStartDelay(0L);
        this.mBodyAnimator.setDuration(350L);
        this.mBodyAnimator.setInterpolator(INTERPOLATOR);
        this.mBodyAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.biometrics.KeyguardBiometricToastView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardBiometricToastView keyguardBiometricToastView = KeyguardBiometricToastView.this;
                float f3 = i;
                keyguardBiometricToastView.mToastRoot.getLayoutParams().width = keyguardBiometricToastView.mToastViewMinWidth + ((int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * f3));
                keyguardBiometricToastView.mToastBodyView.getLayoutParams().width = keyguardBiometricToastView.mToastViewMinWidth + ((int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * f3));
                keyguardBiometricToastView.mToastRoot.requestLayout();
            }
        });
        this.mBodyAnimator.addListener(new AnonymousClass3(toastType));
        this.mBodyAnimator.start();
    }

    public final void dismiss(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("dismiss() , force = ", "KeyguardBiometricToastView", z);
        AnimatorSet animatorSet = this.mAnimatorSet;
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            this.mAnimatorSet.cancel();
        }
        ValueAnimator valueAnimator = this.mBodyAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
            this.mBodyAnimator.cancel();
        }
        if (z) {
            reset();
            setVisibility(8);
            this.mIsShowing = false;
            Consumer consumer = this.mBiometricToastViewStateUpdater;
            if (consumer != null) {
                consumer.accept(Boolean.FALSE);
                return;
            }
            return;
        }
        this.mIsShowing = false;
        if (hasGuideText()) {
            scaleIconAnim(1.0f, shouldDisappearLockIcon() ? 0.0f : mToastIconFrom);
            changeTextAnim(1.0f, 0.0f, ToastType.Authenticating);
        }
        this.mIsBackgroundAuth = false;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mToastRoot, "alpha", 1.0f, 0.0f);
        ofFloat.setDuration(200L);
        ofFloat.setInterpolator(INTERPOLATOR);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mToastGuideText, "alpha", 1.0f, 0.0f);
        ofFloat2.setDuration(100L);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mAnimatorSet = animatorSet2;
        animatorSet2.setStartDelay(0L);
        this.mAnimatorSet.playTogether(ofFloat, ofFloat2);
        this.mAnimatorSet.start();
    }

    public final boolean hasGuideText() {
        TextView textView = this.mToastGuideText;
        return (textView == null || textView.getText() == null || this.mToastGuideText.getText().length() <= 0) ? false : true;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mToastRoot = (FrameLayout) findViewById(R.id.toast_root);
        this.mToastBodyView = (LinearLayout) findViewById(R.id.toast_body_view);
        this.mToastGuideText = (TextView) findViewById(R.id.biometric_toast_text);
        this.mToastIcon = (LottieAnimationView) findViewById(R.id.biometric_toast_icon);
        this.mToastGuideText.setText(getResources().getString(R.string.kg_background_auth_unlock_instruction_text));
        Resources resources = getResources();
        int dimensionPixelSize = (resources.getDimensionPixelSize(R.dimen.kg_biometric_toast_image_margin_start_end) * 2) + resources.getDimensionPixelSize(R.dimen.kg_biometric_toast_image_height);
        this.mToastLockIconWidth = dimensionPixelSize;
        this.mToastViewMinWidth = (resources.getDimensionPixelSize(R.dimen.kg_biometric_toast_inner_start_margin) * 2) + dimensionPixelSize;
        mToastIconFrom = resources.getDimensionPixelSize(R.dimen.kg_biometric_view_min_height) / resources.getDimensionPixelSize(R.dimen.kg_biometric_toast_image_height);
        setViewAttribution(false);
        reset();
        setVisibility(8);
    }

    public final void reset() {
        this.mToastRoot.setAlpha(0.0f);
        AnimatorSet animatorSet = this.mAnimatorSet;
        if (animatorSet != null && (this.mIsAnimating || animatorSet.isRunning())) {
            this.mAnimatorSet.removeAllListeners();
            this.mAnimatorSet.cancel();
            this.mAnimatorSet = null;
        }
        ValueAnimator valueAnimator = this.mBodyAnimator;
        if (valueAnimator != null && (this.mIsAnimating || valueAnimator.isRunning())) {
            this.mBodyAnimator.removeAllListeners();
            this.mBodyAnimator.cancel();
            this.mBodyAnimator = null;
        }
        this.mIsAnimating = false;
    }

    public final void scaleIconAnim(float f, float f2) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mToastIcon, (Property<LottieAnimationView, Float>) View.SCALE_X, f, f2);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mToastIcon, (Property<LottieAnimationView, Float>) View.SCALE_Y, f, f2);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(shouldDisappearLockIcon() ? 200L : 350L);
        animatorSet.setInterpolator(INTERPOLATOR);
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.start();
    }

    public final void setViewAttribution(boolean z) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mToastRoot.getLayoutParams();
        Resources resources = getResources();
        this.mToastGuideText.measure(0, 0);
        int measuredWidth = this.mToastGuideText.getMeasuredWidth();
        Resources resources2 = getResources();
        int screenWidth = DeviceState.getScreenWidth(getContext());
        int min = Math.min(measuredWidth, ((((DeviceType.isTablet() || (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened)) ? (int) (screenWidth * 0.7d) : screenWidth - (resources2.getDimensionPixelSize(R.dimen.kg_biometric_toast_outer_margin) * 2)) - this.mToastLockIconWidth) - resources2.getDimensionPixelSize(R.dimen.kg_biometric_toast_inner_start_margin)) - resources2.getDimensionPixelSize(R.dimen.kg_biometric_toast_inner_end_margin));
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_biometric_toast_inner_end_margin) + resources.getDimensionPixelSize(R.dimen.kg_biometric_toast_inner_start_margin) + this.mToastLockIconWidth + min;
        this.mCurrentToastViewWidth = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        this.mToastGuideText.measure(0, 0);
        int measuredWidth2 = this.mToastGuideText.getMeasuredWidth();
        int i = 1;
        if (measuredWidth2 > min && (i = 1 + (measuredWidth2 / min)) > this.mToastGuideText.getMaxLines()) {
            i = this.mToastGuideText.getMaxLines();
        }
        layoutParams.height = (getResources().getDimensionPixelSize(R.dimen.kg_biometric_toast_text_view_margin) * 2) + (getResources().getDimensionPixelSize(R.dimen.kg_biometric_toast_text_view_height) * i);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.status_bar_height) + ((getResources().getConfiguration().orientation != 2 ? resources.getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin) : 0) - ((int) (((resources.getDimensionPixelSize(R.dimen.kg_biometric_toast_image_height) / 2.0f) + resources.getDimensionPixelSize(R.dimen.kg_biometric_toast_image_view_margin)) - (resources.getDimensionPixelSize(R.dimen.kg_biometric_view_min_height) / 2.0f))));
        layoutParams.topMargin = dimensionPixelSize2;
        if (z) {
            layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.kg_extend_lock_view_padding) + dimensionPixelSize2;
        }
        this.mToastRoot.setLayoutParams(layoutParams);
        this.mToastBodyView.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams2 = this.mToastGuideText.getLayoutParams();
        layoutParams2.width = min;
        this.mToastGuideText.setLayoutParams(layoutParams2);
    }

    public final boolean shouldDisappearLockIcon() {
        return this.mIsBackgroundAuth || (LsRune.SECURITY_BIOMETRICS_TABLET && DeviceState.getRotation(getResources().getConfiguration().windowConfiguration.getRotation()) == 2);
    }

    public final void update(final ToastType toastType, String str, boolean z, boolean z2, boolean z3) {
        int i;
        Log.d("KeyguardBiometricToastView", "Update toast contents = " + toastType + " , already showing = " + this.mIsShowing + " , text = " + str + " , backgroundAuth = " + z3);
        final String str2 = "unlock_icon.json";
        switch (toastType.ordinal()) {
            case 1:
                if (!DeviceType.isTablet()) {
                    i = R.string.kg_background_auth_unlock_succeeded_text;
                    break;
                } else {
                    i = R.string.kg_background_auth_tablet_unlock_succeeded_text;
                    break;
                }
            case 2:
            case 5:
            case 6:
            case 8:
            case 9:
                str2 = "unlock_fail_icon.json";
                i = 0;
                break;
            case 3:
                if (!DeviceType.isTablet()) {
                    i = R.string.kg_background_auth_fingerprint_unlock_succeeded_text;
                    break;
                } else {
                    i = R.string.kg_background_auth_tablet_fingerprint_unlock_succeeded_text;
                    break;
                }
            case 4:
                i = R.string.kg_fingerprint_no_match;
                str2 = "unlock_fail_icon.json";
                break;
            case 7:
                i = R.string.kg_face_no_match;
                str2 = "unlock_fail_icon.json";
                break;
            default:
                str2 = "";
                i = 0;
                break;
        }
        if (z2 && "unlock_fail_icon.json".equals(str2)) {
            str2 = "unlock_fail_icon_lock_stay.json";
        }
        this.mIsBackgroundAuth = z3;
        if (toastType != ToastType.Authenticating) {
            if (i != 0) {
                str = getResources().getString(i);
            }
            reset();
            if (TextUtils.isEmpty(str)) {
                Log.d("KeyguardBiometricToastView", "Toast view text is empty");
                return;
            }
            this.mIsShowing = true;
            setVisibility(0);
            FrameLayout frameLayout = this.mToastRoot;
            if (frameLayout == null) {
                Log.d("KeyguardBiometricToastView", "Root view is null");
            } else {
                frameLayout.getBackground().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.biometric_toast_bg_color, null), PorterDuff.Mode.SRC_ATOP));
            }
            if (this.mToastGuideText == null) {
                Log.d("KeyguardBiometricToastView", "Text view is null");
            } else {
                if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                    if (((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                        this.mToastGuideText.setMaxLines(2);
                    } else {
                        this.mToastGuideText.setMaxLines(4);
                    }
                } else if (getResources().getConfiguration().orientation == 2) {
                    this.mToastGuideText.setMaxLines(2);
                }
                this.mToastGuideText.setTextColor(getResources().getColor(R.color.biometric_toast_text_color, null));
                this.mToastGuideText.setText(str);
            }
            if (hasGuideText()) {
                setViewAttribution(z);
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mToastRoot, "alpha", 0.0f, 1.0f);
            ofFloat.setDuration(200L);
            ofFloat.setInterpolator(INTERPOLATOR);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mToastGuideText, "alpha", 0.0f, 1.0f);
            ofFloat2.setDuration(100L);
            AnimatorSet animatorSet = new AnimatorSet();
            this.mAnimatorSet = animatorSet;
            animatorSet.playTogether(ofFloat, ofFloat2);
            this.mAnimatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.keyguard.biometrics.KeyguardBiometricToastView.2
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    KeyguardBiometricToastView keyguardBiometricToastView = KeyguardBiometricToastView.this;
                    keyguardBiometricToastView.mIsAnimating = true;
                    Consumer consumer = keyguardBiometricToastView.mBiometricToastViewStateUpdater;
                    if (consumer != null) {
                        consumer.accept(Boolean.TRUE);
                    }
                    KeyguardBiometricToastView keyguardBiometricToastView2 = KeyguardBiometricToastView.this;
                    String str3 = str2;
                    if (keyguardBiometricToastView2.mToastIcon == null) {
                        Log.d("KeyguardBiometricToastView", "Icon view is null");
                    } else {
                        if (!Objects.equals(keyguardBiometricToastView2.mAssetName, str3)) {
                            keyguardBiometricToastView2.mAssetName = str3;
                            keyguardBiometricToastView2.mToastIcon.setAnimation(str3);
                        }
                        keyguardBiometricToastView2.mToastIcon.addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(keyguardBiometricToastView2.getResources().getColor(R.color.biometric_toast_text_color, null))));
                    }
                    KeyguardBiometricToastView.this.scaleIconAnim(KeyguardBiometricToastView.mToastIconFrom, 1.0f);
                    LottieAnimationView lottieAnimationView = KeyguardBiometricToastView.this.mToastIcon;
                    if (lottieAnimationView != null) {
                        lottieAnimationView.setVisibility(0);
                    }
                    KeyguardBiometricToastView keyguardBiometricToastView3 = KeyguardBiometricToastView.this;
                    LottieAnimationView lottieAnimationView2 = keyguardBiometricToastView3.mToastIcon;
                    if (lottieAnimationView2 == null) {
                        Log.d("KeyguardBiometricToastView", "Icon view is null");
                    } else {
                        lottieAnimationView2.playAnimation();
                        VibrationUtil vibrationUtil = keyguardBiometricToastView3.mVibrationUtil;
                        if (vibrationUtil != null) {
                            vibrationUtil.playVibration(114);
                            keyguardBiometricToastView3.mVibrationUtil = null;
                        }
                    }
                    if (KeyguardBiometricToastView.this.hasGuideText()) {
                        KeyguardBiometricToastView.this.changeTextAnim(0.0f, 1.0f, toastType);
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }
            });
            this.mAnimatorSet.start();
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.keyguard.biometrics.KeyguardBiometricToastView$1] */
    public KeyguardBiometricToastView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mToastLockIconWidth = 0;
        this.mToastViewMinWidth = 0;
        this.mCurrentToastViewWidth = 0;
        this.mAnimHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.keyguard.biometrics.KeyguardBiometricToastView.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what == 4) {
                    KeyguardBiometricToastView.this.dismiss(false);
                }
            }
        };
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.biometrics.KeyguardBiometricToastView$3, reason: invalid class name */
    public final class AnonymousClass3 implements Animator.AnimatorListener {
        public final /* synthetic */ ToastType val$type;

        public AnonymousClass3(ToastType toastType) {
            this.val$type = toastType;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (this.val$type != ToastType.Authenticating) {
                if (hasMessages(4)) {
                    removeMessages(4);
                }
                sendEmptyMessageDelayed(4, 3000L);
                return;
            }
            Log.d("KeyguardBiometricToastView", "disappearAnimation end");
            KeyguardBiometricToastView keyguardBiometricToastView = KeyguardBiometricToastView.this;
            float f = KeyguardBiometricToastView.mToastIconFrom;
            keyguardBiometricToastView.reset();
            KeyguardBiometricToastView.this.setVisibility(8);
            KeyguardBiometricToastView keyguardBiometricToastView2 = KeyguardBiometricToastView.this;
            if (keyguardBiometricToastView2.mBiometricToastViewStateUpdater != null) {
                keyguardBiometricToastView2.mHandler.postDelayed(new Runnable() { // from class: com.android.keyguard.biometrics.KeyguardBiometricToastView$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardBiometricToastView.this.mBiometricToastViewStateUpdater.accept(Boolean.FALSE);
                    }
                }, 300L);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            if (hasMessages(4)) {
                removeMessages(4);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    }
}
