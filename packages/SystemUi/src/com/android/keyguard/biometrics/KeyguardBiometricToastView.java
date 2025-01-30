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
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
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
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardBiometricToastView extends FrameLayout {
    public static final PathInterpolator INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    public static float mToastIconFrom = 1.28f;
    public final HandlerC08511 mAnimHandler;
    public AnimatorSet mAnimatorSet;
    public String mAssetName;
    public Consumer mBiometricToastViewStateUpdater;
    public ValueAnimator mBodyAnimator;
    public int mCurrentToastViewWidth;
    public boolean mIsAnimating;
    public boolean mIsBackgroundAuth;
    public boolean mIsShowing;
    public LinearLayout mToastBodyView;
    public TextView mToastGuideText;
    public LottieAnimationView mToastIcon;
    public int mToastLockIconWidth;
    public FrameLayout mToastRoot;
    public int mToastViewMinWidth;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.biometrics.KeyguardBiometricToastView$4 */
    public abstract /* synthetic */ class AbstractC08544 {

        /* renamed from: $SwitchMap$com$android$keyguard$biometrics$KeyguardBiometricToastView$ToastType */
        public static final /* synthetic */ int[] f217x3578f13a;

        static {
            int[] iArr = new int[ToastType.values().length];
            f217x3578f13a = iArr;
            try {
                iArr[ToastType.Authenticating.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f217x3578f13a[ToastType.AuthenticationSuccess.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f217x3578f13a[ToastType.FingerprintAuthenticationSuccess.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f217x3578f13a[ToastType.FingerprintAuthenticationFail.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f217x3578f13a[ToastType.AuthenticationFail.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f217x3578f13a[ToastType.FingerprintAuthenticationError.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f217x3578f13a[ToastType.FingerprintAuthenticationHelp.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f217x3578f13a[ToastType.FaceAuthenticationError.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f217x3578f13a[ToastType.FaceAuthenticationHelp.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f217x3578f13a[ToastType.FaceAuthenticationFail.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum ToastType {
        Authenticating,
        AuthenticationSuccess,
        AuthenticationFail,
        FingerprintAuthenticationSuccess,
        FingerprintAuthenticationFail,
        FingerprintAuthenticationError,
        FingerprintAuthenticationHelp,
        FaceAuthenticationFail,
        FaceAuthenticationError,
        FaceAuthenticationHelp
    }

    public KeyguardBiometricToastView(Context context) {
        this(context, null);
    }

    public final void changeTextAnim(float f, float f2, final ToastType toastType) {
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
        this.mBodyAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.keyguard.biometrics.KeyguardBiometricToastView.3
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (toastType != ToastType.Authenticating) {
                    if (hasMessages(4)) {
                        removeMessages(4);
                    }
                    sendEmptyMessageDelayed(4, 3000L);
                } else {
                    Log.d("KeyguardBiometricToastView", "disappearAnimation end");
                    KeyguardBiometricToastView keyguardBiometricToastView = KeyguardBiometricToastView.this;
                    float f3 = KeyguardBiometricToastView.mToastIconFrom;
                    keyguardBiometricToastView.reset();
                    KeyguardBiometricToastView.this.setVisibility(8);
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
        });
        this.mBodyAnimator.start();
    }

    public final void dismiss(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("dismiss() , force = ", z, "KeyguardBiometricToastView");
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
        Consumer consumer = this.mBiometricToastViewStateUpdater;
        if (consumer != null) {
            consumer.accept(Boolean.FALSE);
        }
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
        int i;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mToastRoot.getLayoutParams();
        Resources resources = getResources();
        this.mToastGuideText.measure(0, 0);
        int measuredWidth = this.mToastGuideText.getMeasuredWidth();
        Resources resources2 = getResources();
        int screenWidth = DeviceState.getScreenWidth(getContext());
        int min = Math.min(measuredWidth, ((((DeviceType.isTablet() || (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened)) ? (int) (screenWidth * 0.7d) : screenWidth - (resources2.getDimensionPixelSize(R.dimen.kg_biometric_toast_outer_margin) * 2)) - this.mToastLockIconWidth) - resources2.getDimensionPixelSize(R.dimen.kg_biometric_toast_inner_start_margin)) - resources2.getDimensionPixelSize(R.dimen.kg_biometric_toast_inner_end_margin));
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_biometric_toast_inner_end_margin) + resources.getDimensionPixelSize(R.dimen.kg_biometric_toast_inner_start_margin) + this.mToastLockIconWidth + min;
        this.mCurrentToastViewWidth = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        this.mToastGuideText.measure(0, 0);
        int measuredWidth2 = this.mToastGuideText.getMeasuredWidth();
        if (measuredWidth2 > min) {
            i = (measuredWidth2 / min) + 1;
            if (i > this.mToastGuideText.getMaxLines()) {
                i = this.mToastGuideText.getMaxLines();
            }
        } else {
            i = 1;
        }
        layoutParams.height = (getResources().getDimensionPixelSize(R.dimen.kg_biometric_toast_text_view_margin) * 2) + (getResources().getDimensionPixelSize(R.dimen.kg_biometric_toast_text_view_height) * i);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.status_bar_height) + ((getResources().getConfiguration().orientation == 2 ? 0 : resources.getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin)) - ((int) (((resources.getDimensionPixelSize(R.dimen.kg_biometric_toast_image_height) / 2.0f) + resources.getDimensionPixelSize(R.dimen.kg_biometric_toast_image_view_margin)) - (resources.getDimensionPixelSize(R.dimen.kg_biometric_view_min_height) / 2.0f))));
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
        StringBuilder sb = new StringBuilder("Update toast contents = ");
        sb.append(toastType);
        sb.append(" , already showing = ");
        sb.append(this.mIsShowing);
        sb.append(" , text = ");
        sb.append(str);
        sb.append(" , backgroundAuth = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, z3, "KeyguardBiometricToastView");
        final String str2 = "unlock_icon.json";
        switch (AbstractC08544.f217x3578f13a[toastType.ordinal()]) {
            case 2:
                if (!DeviceType.isTablet()) {
                    i = R.string.kg_background_auth_unlock_succeeded_text;
                    break;
                } else {
                    i = R.string.kg_background_auth_tablet_unlock_succeeded_text;
                    break;
                }
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
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                str2 = "unlock_fail_icon.json";
                i = 0;
                break;
            case 10:
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
                if (!LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                    if (getResources().getConfiguration().orientation == 2) {
                        this.mToastGuideText.setMaxLines(2);
                    }
                } else if (((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
                    this.mToastGuideText.setMaxLines(2);
                } else {
                    this.mToastGuideText.setMaxLines(4);
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
                        keyguardBiometricToastView2.mToastIcon.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new LottieValueCallback(new SimpleColorFilter(keyguardBiometricToastView2.getResources().getColor(R.color.biometric_toast_text_color, null))));
                    }
                    KeyguardBiometricToastView.this.scaleIconAnim(KeyguardBiometricToastView.mToastIconFrom, 1.0f);
                    LottieAnimationView lottieAnimationView = KeyguardBiometricToastView.this.mToastIcon;
                    if (lottieAnimationView != null) {
                        lottieAnimationView.setVisibility(0);
                    }
                    LottieAnimationView lottieAnimationView2 = KeyguardBiometricToastView.this.mToastIcon;
                    if (lottieAnimationView2 == null) {
                        Log.d("KeyguardBiometricToastView", "Icon view is null");
                    } else {
                        lottieAnimationView2.playAnimation();
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
    }
}
