package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.hardware.biometrics.PromptInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.AuthDialog;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AuthBiometricView extends LinearLayout implements AuthBiometricViewAdapter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager mAccessibilityManager;
    int mAnimationDurationHideDialog;
    int mAnimationDurationLong;
    int mAnimationDurationShort;
    public final AuthBiometricView$$ExternalSyntheticLambda1 mBackgroundClickListener;
    public Callback mCallback;
    Button mCancelButton;
    Button mConfirmButton;
    public final int mCustomBpHeight;
    public final int mCustomBpWidth;
    public TextView mDescriptionView;
    public boolean mDialogSizeAnimating;
    public int mEffectiveUserId;
    public final Handler mHandler;
    AuthIconController mIconController;
    public View mIconHolderView;
    public float mIconOriginalY;
    public LottieAnimationView mIconView;
    public LottieAnimationView mIconViewOverlay;
    public TextView mIndicatorView;
    public Animator.AnimatorListener mJankListener;
    AuthDialog.LayoutParams mLayoutParams;
    public final LockPatternUtils mLockPatternUtils;
    Button mNegativeButton;
    public AuthPanelController mPanelController;
    public PromptInfo mPromptInfo;
    public boolean mRequireConfirmation;
    public final AuthBiometricView$$ExternalSyntheticLambda2 mResetErrorRunnable;
    public final AuthBiometricView$$ExternalSyntheticLambda2 mResetHelpRunnable;
    public Bundle mSavedState;
    public int mSize;
    public int mState;
    public TextView mSubtitleView;
    public final int mTextColorError;
    public final int mTextColorHint;
    public TextView mTitleView;
    Button mTryAgainButton;
    Button mUseCredentialButton;
    public final boolean mUseCustomBpSize;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void onAction(int i);
    }

    public AuthBiometricView(Context context) {
        this(context, null);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void cancelAnimation() {
        animate().cancel();
    }

    public abstract AuthIconController createIconController();

    public boolean forceRequireConfirmation(int i) {
        return false;
    }

    public int getConfirmationPrompt() {
        return R.string.biometric_dialog_tap_confirm;
    }

    public int getDelayAfterAuthenticatedDurationMs() {
        return 0;
    }

    public int getStateForAfterError() {
        return 0;
    }

    public boolean ignoreUnsuccessfulEventsFrom(int i, String str) {
        return false;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public boolean isCoex() {
        return this instanceof AuthBiometricFingerprintAndFaceView;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mTitleView.setText(this.mPromptInfo.getTitle());
        this.mTitleView.setSelected(true);
        this.mSubtitleView.setSelected(true);
        this.mDescriptionView.setMovementMethod(new ScrollingMovementMethod());
        if (Utils.isDeviceCredentialAllowed(this.mPromptInfo)) {
            int credentialType = Utils.getCredentialType(this.mLockPatternUtils, this.mEffectiveUserId);
            String string = credentialType != 1 ? credentialType != 2 ? getResources().getString(R.string.biometric_dialog_use_password) : getResources().getString(R.string.biometric_dialog_use_pattern) : getResources().getString(R.string.biometric_dialog_use_pin);
            this.mNegativeButton.setVisibility(8);
            this.mUseCredentialButton.setText(string);
            this.mUseCredentialButton.setVisibility(0);
        } else {
            this.mNegativeButton.setText(this.mPromptInfo.getNegativeButtonText());
        }
        TextView textView = this.mSubtitleView;
        CharSequence subtitle = this.mPromptInfo.getSubtitle();
        if (TextUtils.isEmpty(subtitle)) {
            textView.setVisibility(8);
        } else {
            textView.setText(subtitle);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        TextView textView2 = this.mDescriptionView;
        CharSequence description = this.mPromptInfo.getDescription();
        if (TextUtils.isEmpty(description)) {
            textView2.setVisibility(8);
        } else {
            textView2.setText(description);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        Bundle bundle = this.mSavedState;
        if (bundle == null) {
            updateState(1);
            return;
        }
        updateState(bundle.getInt("state"));
        this.mConfirmButton.setVisibility(this.mSavedState.getInt("confirm_visibility"));
        if (this.mConfirmButton.getVisibility() == 8) {
            this.mRequireConfirmation = false;
        }
        this.mTryAgainButton.setVisibility(this.mSavedState.getInt("try_agian_visibility"));
    }

    public void onAuthenticationFailed(int i, String str) {
        if (ignoreUnsuccessfulEventsFrom(i, str)) {
            return;
        }
        showTemporaryMessage(this.mResetErrorRunnable, str);
        updateState(4);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onAuthenticationSucceeded(int i) {
        removePendingAnimations();
        if (this.mRequireConfirmation || forceRequireConfirmation(i)) {
            updateState(5);
        } else {
            updateState(6);
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Bundle bundle = this.mSavedState;
        if (bundle != null) {
            updateState(bundle.getInt("state"));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIconController.deactivated = true;
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onDialogAnimatedIn(boolean z) {
        updateState(2);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onError(int i, String str) {
        if (ignoreUnsuccessfulEventsFrom(i, str)) {
            return;
        }
        showTemporaryMessage(this.mResetErrorRunnable, str);
        updateState(4);
        this.mHandler.postDelayed(new AuthBiometricView$$ExternalSyntheticLambda2(this, 4), this.mAnimationDurationHideDialog);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleView = (TextView) findViewById(R.id.title);
        this.mSubtitleView = (TextView) findViewById(R.id.subtitle);
        this.mDescriptionView = (TextView) findViewById(R.id.description);
        this.mIconViewOverlay = (LottieAnimationView) findViewById(R.id.biometric_icon_overlay);
        this.mIconView = (LottieAnimationView) findViewById(R.id.biometric_icon);
        this.mIconHolderView = findViewById(R.id.biometric_icon_frame);
        this.mIndicatorView = (TextView) findViewById(R.id.indicator);
        this.mNegativeButton = (Button) findViewById(R.id.button_negative);
        this.mCancelButton = (Button) findViewById(R.id.button_cancel);
        this.mUseCredentialButton = (Button) findViewById(R.id.button_use_credential);
        this.mConfirmButton = (Button) findViewById(R.id.button_confirm);
        this.mTryAgainButton = (Button) findViewById(R.id.button_try_again);
        this.mNegativeButton.setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda1(this, 0));
        this.mCancelButton.setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda1(this, 1));
        this.mUseCredentialButton.setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda1(this, 2));
        this.mConfirmButton.setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda1(this, 3));
        this.mTryAgainButton.setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda1(this, 4));
        AuthIconController createIconController = createIconController();
        this.mIconController = createIconController;
        if (createIconController.getActsAsConfirmButton()) {
            this.mIconViewOverlay.setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda1(this, 5));
            this.mIconView.setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda1(this, 6));
        }
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onHelp(int i, String str) {
        if (ignoreUnsuccessfulEventsFrom(i, str)) {
            return;
        }
        if (this.mSize != 2) {
            Log.w("AuthBiometricView", "Help received in size: " + this.mSize);
        } else if (TextUtils.isEmpty(str)) {
            Log.w("AuthBiometricView", "Ignoring blank help message");
        } else {
            showTemporaryMessage(this.mResetHelpRunnable, str);
            updateState(3);
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mIconOriginalY == 0.0f) {
            this.mIconOriginalY = this.mIconHolderView.getY();
            Bundle bundle = this.mSavedState;
            if (bundle == null) {
                updateSize((this.mRequireConfirmation || !supportsSmallDialog()) ? 2 : 1);
                return;
            }
            updateSize(bundle.getInt("size"));
            String string = this.mSavedState.getString("indicator_string");
            if (this.mSavedState.getBoolean("hint_is_temporary")) {
                onHelp(0, string);
            } else if (this.mSavedState.getBoolean("error_is_temporary")) {
                onAuthenticationFailed(0, string);
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int min;
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (this.mUseCustomBpSize) {
            min = this.mCustomBpWidth;
            size2 = this.mCustomBpHeight;
        } else {
            min = Math.min(size, size2);
        }
        this.mLayoutParams = onMeasureInternal(min, size2);
        Insets navbarInsets = Utils.getNavbarInsets(((LinearLayout) this).mContext);
        int i3 = navbarInsets.bottom;
        int i4 = this.mPanelController.mPosition;
        int i5 = i4 == 2 ? navbarInsets.left : i4 == 3 ? navbarInsets.right : 0;
        if (i5 != 0 || i3 != 0) {
            AuthDialog.LayoutParams layoutParams = this.mLayoutParams;
            this.mLayoutParams = new AuthDialog.LayoutParams(layoutParams.mMediumWidth + i5, layoutParams.mMediumHeight + i3);
        }
        AuthDialog.LayoutParams layoutParams2 = this.mLayoutParams;
        setMeasuredDimension(layoutParams2.mMediumWidth, layoutParams2.mMediumHeight);
    }

    public AuthDialog.LayoutParams onMeasureInternal(int i, int i2) {
        int childCount = getChildCount();
        int i3 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getId() == R.id.space_above_icon || childAt.getId() == R.id.space_below_icon || childAt.getId() == R.id.button_bar) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
            } else if (childAt.getId() == R.id.biometric_icon_frame) {
                View findViewById = findViewById(R.id.biometric_icon);
                childAt.measure(View.MeasureSpec.makeMeasureSpec(findViewById.getLayoutParams().width, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(findViewById.getLayoutParams().height, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
            } else if (childAt.getId() == R.id.biometric_icon) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, VideoPlayer.MEDIA_ERROR_SYSTEM), View.MeasureSpec.makeMeasureSpec(i2, VideoPlayer.MEDIA_ERROR_SYSTEM));
            } else {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(i2, VideoPlayer.MEDIA_ERROR_SYSTEM));
            }
            if (childAt.getVisibility() != 8) {
                i3 = childAt.getMeasuredHeight() + i3;
            }
        }
        return new AuthDialog.LayoutParams(i, i3);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onOrientationChanged() {
        updateSize(this.mSize);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onSaveState(Bundle bundle) {
        bundle.putInt("confirm_visibility", this.mConfirmButton.getVisibility());
        bundle.putInt("try_agian_visibility", this.mTryAgainButton.getVisibility());
        bundle.putInt("state", this.mState);
        bundle.putString("indicator_string", this.mIndicatorView.getText() != null ? this.mIndicatorView.getText().toString() : "");
        bundle.putBoolean("error_is_temporary", this.mHandler.hasCallbacks(this.mResetErrorRunnable));
        bundle.putBoolean("hint_is_temporary", this.mHandler.hasCallbacks(this.mResetHelpRunnable));
        bundle.putInt("size", this.mSize);
    }

    public final void removePendingAnimations() {
        this.mHandler.removeCallbacks(this.mResetHelpRunnable);
        this.mHandler.removeCallbacks(this.mResetErrorRunnable);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void restoreState(Bundle bundle) {
        this.mSavedState = bundle;
    }

    public final void showTemporaryMessage(AuthBiometricView$$ExternalSyntheticLambda2 authBiometricView$$ExternalSyntheticLambda2, String str) {
        removePendingAnimations();
        this.mIndicatorView.setText(str);
        this.mIndicatorView.setTextColor(this.mTextColorError);
        this.mIndicatorView.setVisibility(0);
        this.mIndicatorView.setSelected((this.mAccessibilityManager.isEnabled() && this.mAccessibilityManager.isTouchExplorationEnabled()) ? false : true);
        this.mHandler.postDelayed(authBiometricView$$ExternalSyntheticLambda2, this.mAnimationDurationHideDialog);
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void startTransitionToCredentialUI() {
        updateSize(3);
        ((AuthContainerView.BiometricCallback) this.mCallback).onAction(6);
    }

    public boolean supportsSmallDialog() {
        return this instanceof AuthBiometricFaceView;
    }

    public final void updateSize(final int i) {
        Insets navbarInsets = Utils.getNavbarInsets(((LinearLayout) this).mContext);
        final int i2 = 3;
        final int i3 = 0;
        final int i4 = 2;
        if (i != 3) {
            int i5 = this.mPanelController.mPosition;
            if (i5 == 2) {
                setPadding(navbarInsets.left, 0, 0, 0);
            } else if (i5 == 3) {
                setPadding(0, 0, navbarInsets.right, 0);
            } else {
                setPadding(0, 0, 0, navbarInsets.bottom);
            }
        } else {
            setPadding(0, 0, 0, 0);
        }
        final int i6 = 1;
        if (i == 1) {
            this.mTitleView.setVisibility(8);
            this.mSubtitleView.setVisibility(8);
            this.mDescriptionView.setVisibility(8);
            this.mIndicatorView.setVisibility(8);
            this.mNegativeButton.setVisibility(8);
            this.mUseCredentialButton.setVisibility(8);
            float dimension = getResources().getDimension(R.dimen.biometric_dialog_icon_padding);
            this.mIconHolderView.setY((getHeight() - this.mIconHolderView.getHeight()) - dimension);
            this.mPanelController.updateForContentDimensions(this.mLayoutParams.mMediumWidth, (((((int) dimension) * 2) + this.mIconHolderView.getHeight()) - this.mIconHolderView.getPaddingTop()) - this.mIconHolderView.getPaddingBottom(), 0);
            this.mSize = i;
        } else if (this.mSize == 1 && i == 2) {
            if (this.mDialogSizeAnimating) {
                return;
            }
            this.mDialogSizeAnimating = true;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mIconHolderView.getY(), this.mIconOriginalY);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda0
                public final /* synthetic */ AuthBiometricView f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    switch (i3) {
                        case 0:
                            this.f$0.mIconHolderView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        case 1:
                            AuthBiometricView authBiometricView = this.f$0;
                            int i7 = AuthBiometricView.$r8$clinit;
                            authBiometricView.getClass();
                            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            authBiometricView.mTitleView.setAlpha(floatValue);
                            authBiometricView.mIndicatorView.setAlpha(floatValue);
                            authBiometricView.mNegativeButton.setAlpha(floatValue);
                            authBiometricView.mCancelButton.setAlpha(floatValue);
                            authBiometricView.mTryAgainButton.setAlpha(floatValue);
                            if (!TextUtils.isEmpty(authBiometricView.mSubtitleView.getText())) {
                                authBiometricView.mSubtitleView.setAlpha(floatValue);
                            }
                            if (!TextUtils.isEmpty(authBiometricView.mDescriptionView.getText())) {
                                authBiometricView.mDescriptionView.setAlpha(floatValue);
                                break;
                            }
                            break;
                        case 2:
                            AuthBiometricView authBiometricView2 = this.f$0;
                            int i8 = AuthBiometricView.$r8$clinit;
                            authBiometricView2.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        default:
                            AuthBiometricView authBiometricView3 = this.f$0;
                            int i9 = AuthBiometricView.$r8$clinit;
                            authBiometricView3.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                    }
                }
            });
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda0
                public final /* synthetic */ AuthBiometricView f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    switch (i6) {
                        case 0:
                            this.f$0.mIconHolderView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        case 1:
                            AuthBiometricView authBiometricView = this.f$0;
                            int i7 = AuthBiometricView.$r8$clinit;
                            authBiometricView.getClass();
                            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            authBiometricView.mTitleView.setAlpha(floatValue);
                            authBiometricView.mIndicatorView.setAlpha(floatValue);
                            authBiometricView.mNegativeButton.setAlpha(floatValue);
                            authBiometricView.mCancelButton.setAlpha(floatValue);
                            authBiometricView.mTryAgainButton.setAlpha(floatValue);
                            if (!TextUtils.isEmpty(authBiometricView.mSubtitleView.getText())) {
                                authBiometricView.mSubtitleView.setAlpha(floatValue);
                            }
                            if (!TextUtils.isEmpty(authBiometricView.mDescriptionView.getText())) {
                                authBiometricView.mDescriptionView.setAlpha(floatValue);
                                break;
                            }
                            break;
                        case 2:
                            AuthBiometricView authBiometricView2 = this.f$0;
                            int i8 = AuthBiometricView.$r8$clinit;
                            authBiometricView2.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        default:
                            AuthBiometricView authBiometricView3 = this.f$0;
                            int i9 = AuthBiometricView.$r8$clinit;
                            authBiometricView3.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                    }
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(this.mAnimationDurationShort);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthBiometricView.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    AuthBiometricView authBiometricView = AuthBiometricView.this;
                    authBiometricView.mSize = i;
                    authBiometricView.mDialogSizeAnimating = false;
                    Utils.notifyAccessibilityContentChanged(authBiometricView.mAccessibilityManager, authBiometricView);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    AuthBiometricView.this.mTitleView.setVisibility(0);
                    AuthBiometricView.this.mIndicatorView.setVisibility(0);
                    if (Utils.isDeviceCredentialAllowed(AuthBiometricView.this.mPromptInfo)) {
                        AuthBiometricView.this.mUseCredentialButton.setVisibility(0);
                    } else {
                        AuthBiometricView.this.mNegativeButton.setVisibility(0);
                    }
                    AuthBiometricView authBiometricView = AuthBiometricView.this;
                    authBiometricView.getClass();
                    if (authBiometricView instanceof AuthBiometricFaceView) {
                        AuthBiometricView.this.mTryAgainButton.setVisibility(0);
                    }
                    if (!TextUtils.isEmpty(AuthBiometricView.this.mSubtitleView.getText())) {
                        AuthBiometricView.this.mSubtitleView.setVisibility(0);
                    }
                    if (TextUtils.isEmpty(AuthBiometricView.this.mDescriptionView.getText())) {
                        return;
                    }
                    AuthBiometricView.this.mDescriptionView.setVisibility(0);
                }
            });
            Animator.AnimatorListener animatorListener = this.mJankListener;
            if (animatorListener != null) {
                animatorSet.addListener(animatorListener);
            }
            animatorSet.play(ofFloat).with(ofFloat2);
            animatorSet.start();
            AuthPanelController authPanelController = this.mPanelController;
            AuthDialog.LayoutParams layoutParams = this.mLayoutParams;
            authPanelController.updateForContentDimensions(layoutParams.mMediumWidth, layoutParams.mMediumHeight, 150);
        } else if (i == 2) {
            AuthPanelController authPanelController2 = this.mPanelController;
            AuthDialog.LayoutParams layoutParams2 = this.mLayoutParams;
            authPanelController2.updateForContentDimensions(layoutParams2.mMediumWidth, layoutParams2.mMediumHeight, 0);
            this.mSize = i;
        } else if (i == 3) {
            ValueAnimator ofFloat3 = ValueAnimator.ofFloat(getY(), getY() - getResources().getDimension(R.dimen.biometric_dialog_medium_to_large_translation_offset));
            ofFloat3.setDuration(this.mAnimationDurationLong);
            ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda0
                public final /* synthetic */ AuthBiometricView f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    switch (i4) {
                        case 0:
                            this.f$0.mIconHolderView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        case 1:
                            AuthBiometricView authBiometricView = this.f$0;
                            int i7 = AuthBiometricView.$r8$clinit;
                            authBiometricView.getClass();
                            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            authBiometricView.mTitleView.setAlpha(floatValue);
                            authBiometricView.mIndicatorView.setAlpha(floatValue);
                            authBiometricView.mNegativeButton.setAlpha(floatValue);
                            authBiometricView.mCancelButton.setAlpha(floatValue);
                            authBiometricView.mTryAgainButton.setAlpha(floatValue);
                            if (!TextUtils.isEmpty(authBiometricView.mSubtitleView.getText())) {
                                authBiometricView.mSubtitleView.setAlpha(floatValue);
                            }
                            if (!TextUtils.isEmpty(authBiometricView.mDescriptionView.getText())) {
                                authBiometricView.mDescriptionView.setAlpha(floatValue);
                                break;
                            }
                            break;
                        case 2:
                            AuthBiometricView authBiometricView2 = this.f$0;
                            int i8 = AuthBiometricView.$r8$clinit;
                            authBiometricView2.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        default:
                            AuthBiometricView authBiometricView3 = this.f$0;
                            int i9 = AuthBiometricView.$r8$clinit;
                            authBiometricView3.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                    }
                }
            });
            ofFloat3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthBiometricView.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    if (this.getParent() instanceof ViewGroup) {
                        ((ViewGroup) this.getParent()).removeView(this);
                    }
                    AuthBiometricView.this.mSize = i;
                }
            });
            ValueAnimator ofFloat4 = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat4.setDuration(this.mAnimationDurationLong / 2);
            ofFloat4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda0
                public final /* synthetic */ AuthBiometricView f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    switch (i2) {
                        case 0:
                            this.f$0.mIconHolderView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        case 1:
                            AuthBiometricView authBiometricView = this.f$0;
                            int i7 = AuthBiometricView.$r8$clinit;
                            authBiometricView.getClass();
                            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            authBiometricView.mTitleView.setAlpha(floatValue);
                            authBiometricView.mIndicatorView.setAlpha(floatValue);
                            authBiometricView.mNegativeButton.setAlpha(floatValue);
                            authBiometricView.mCancelButton.setAlpha(floatValue);
                            authBiometricView.mTryAgainButton.setAlpha(floatValue);
                            if (!TextUtils.isEmpty(authBiometricView.mSubtitleView.getText())) {
                                authBiometricView.mSubtitleView.setAlpha(floatValue);
                            }
                            if (!TextUtils.isEmpty(authBiometricView.mDescriptionView.getText())) {
                                authBiometricView.mDescriptionView.setAlpha(floatValue);
                                break;
                            }
                            break;
                        case 2:
                            AuthBiometricView authBiometricView2 = this.f$0;
                            int i8 = AuthBiometricView.$r8$clinit;
                            authBiometricView2.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                        default:
                            AuthBiometricView authBiometricView3 = this.f$0;
                            int i9 = AuthBiometricView.$r8$clinit;
                            authBiometricView3.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                            break;
                    }
                }
            });
            AuthPanelController authPanelController3 = this.mPanelController;
            authPanelController3.mUseFullScreen = true;
            authPanelController3.updateForContentDimensions(authPanelController3.mContainerWidth, authPanelController3.mContainerHeight, this.mAnimationDurationLong);
            AnimatorSet animatorSet2 = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            arrayList.add(ofFloat3);
            arrayList.add(ofFloat4);
            Animator.AnimatorListener animatorListener2 = this.mJankListener;
            if (animatorListener2 != null) {
                animatorSet2.addListener(animatorListener2);
            }
            animatorSet2.playTogether(arrayList);
            animatorSet2.setDuration((this.mAnimationDurationLong * 2) / 3);
            animatorSet2.start();
        } else {
            Log.e("AuthBiometricView", "Unknown transition from: " + this.mSize + " to: " + i);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
    }

    public void updateState(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("newState: ", i, "AuthBiometricView");
        AuthIconController authIconController = this.mIconController;
        int i2 = this.mState;
        if (authIconController.deactivated) {
            IconCompat$$ExternalSyntheticOutline0.m30m("Ignoring updateState when deactivated: ", i, "AuthIconController");
        } else {
            authIconController.updateIcon(i2, i);
        }
        int i3 = 1;
        int i4 = 0;
        if (i == 1 || i == 2) {
            removePendingAnimations();
            if (this.mRequireConfirmation) {
                this.mConfirmButton.setEnabled(false);
                this.mConfirmButton.setVisibility(0);
            }
        } else if (i != 4) {
            if (i == 5) {
                removePendingAnimations();
                this.mNegativeButton.setVisibility(8);
                this.mCancelButton.setVisibility(0);
                this.mUseCredentialButton.setVisibility(8);
                this.mConfirmButton.setEnabled(this.mRequireConfirmation);
                this.mConfirmButton.setVisibility(this.mRequireConfirmation ? 0 : 8);
                this.mIndicatorView.setTextColor(this.mTextColorHint);
                this.mIndicatorView.setText(getConfirmationPrompt());
                this.mIndicatorView.setVisibility(0);
            } else if (i != 6) {
                IconCompat$$ExternalSyntheticOutline0.m30m("Unhandled state: ", i, "AuthBiometricView");
            } else {
                removePendingAnimations();
                if (this.mSize != 1) {
                    this.mConfirmButton.setVisibility(8);
                    this.mNegativeButton.setVisibility(8);
                    this.mUseCredentialButton.setVisibility(8);
                    this.mCancelButton.setVisibility(8);
                    this.mIndicatorView.setVisibility(4);
                }
                announceForAccessibility(getResources().getString(R.string.biometric_dialog_authenticated));
                if (this.mState == 5) {
                    this.mHandler.postDelayed(new AuthBiometricView$$ExternalSyntheticLambda2(this, i4), getDelayAfterAuthenticatedDurationMs());
                } else {
                    this.mHandler.postDelayed(new AuthBiometricView$$ExternalSyntheticLambda2(this, i3), getDelayAfterAuthenticatedDurationMs());
                }
            }
        } else if (this.mSize == 1) {
            updateSize(2);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        this.mState = i;
    }

    public AuthBiometricView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSize = 0;
        this.mAnimationDurationShort = 150;
        this.mAnimationDurationLong = 450;
        this.mAnimationDurationHideDialog = 2000;
        this.mBackgroundClickListener = new AuthBiometricView$$ExternalSyntheticLambda1(this, 7);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mTextColorError = getResources().getColor(R.color.biometric_dialog_error, context.getTheme());
        this.mTextColorHint = getResources().getColor(R.color.biometric_dialog_gray, context.getTheme());
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mResetErrorRunnable = new AuthBiometricView$$ExternalSyntheticLambda2(this, 2);
        this.mResetHelpRunnable = new AuthBiometricView$$ExternalSyntheticLambda2(this, 3);
        this.mUseCustomBpSize = getResources().getBoolean(R.bool.use_custom_bp_size);
        this.mCustomBpWidth = getResources().getDimensionPixelSize(R.dimen.biometric_dialog_width);
        this.mCustomBpHeight = getResources().getDimensionPixelSize(R.dimen.biometric_dialog_height);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final View asView() {
        return this;
    }

    public void handleResetAfterError() {
    }

    public void handleResetAfterHelp() {
    }
}
