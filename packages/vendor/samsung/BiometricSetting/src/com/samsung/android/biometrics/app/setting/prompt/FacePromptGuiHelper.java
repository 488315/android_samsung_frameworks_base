package com.samsung.android.biometrics.app.setting.prompt;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.hardware.SensorPrivacyManager;
import android.hardware.face.FaceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.RenderMode;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.face.PunchHoleVIHelper;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class FacePromptGuiHelper extends BiometricPromptGuiHelper {
    public TextView mFeedbackTextView;
    public boolean mIsCameraBlocked;
    public boolean mIsSupportNegativeButton;
    public LottieAnimationView mPunchHoleIcon;
    public PunchHoleVIHelper mPunchHoleVIHelper;
    public SensorPrivacyManager mSensorPrivacyManager;
    public long mTimePreviousHelpMessage;
    public Rect mViViewLocation;

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void cleanUpPrompt() {
        super.cleanUpPrompt();
        pausePunchHoleAnimation();
        TextView textView = this.mFeedbackTextView;
        if (textView != null) {
            textView.setVisibility(8);
        }
        this.mReTryButton.setVisibility(8);
        this.mConfirmButton.setVisibility(8);
        this.mIconImgView.setVisibility(8);
        updateIcon(0);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final int getBottomMarginForPortrait() {
        return (Utils.isTablet() || (this.mIsSupportDualDisplay && this.mDisplayType != 5))
                ? (int)
                        this.mContext
                                .getResources()
                                .getDimension(
                                        R.dimen
                                                .biometric_prompt_verification_dialog_bottom_margin_portrait_winner_tablet)
                : super.getBottomMarginForPortrait();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final String getDefaultDescriptionMessage() {
        return this.mContext.getString(R.string.face_verification_description_default_text);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final String getToastMessageInDex() {
        return this.mContext.getString(R.string.face_dex_toast);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationError(int i, int i2) {
        boolean z = i == 7 || i == 9;
        if (i2 == 100003) {
            setTextOnDescriptionTextView(
                    this.mContext.getString(R.string.camera_access_turn_on_message));
            this.mReTryButton.setText(
                    this.mContext.getString(R.string.camera_access_turn_on_button));
        } else {
            setTextOnDescriptionTextView(
                    z
                            ? getLockoutErrorMessage(i)
                            : FaceManager.getErrorString(this.mContext, i, i2));
        }
        updateIcon(1);
        pausePunchHoleAnimation();
        if (z) {
            return;
        }
        BiometricPromptGuiHelper.setVisibilityDelayAnimation(
                this.mReTryButton, new FacePromptGuiHelper$$ExternalSyntheticLambda0(this, 6));
        this.mReTryButton.setEnabled(true);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationFailed() {
        if (this.mBaseView == null) {
            Log.e(this.TAG, "mBaseView is null");
            return;
        }
        updateIcon(1);
        setTextOnDescriptionTextView(
                this.mContext.getString(R.string.face_biometric_prompt_identify_failure_title));
        pausePunchHoleAnimation();
        BiometricPromptGuiHelper.setVisibilityDelayAnimation(
                this.mReTryButton, new FacePromptGuiHelper$$ExternalSyntheticLambda0(this, 3));
        this.mReTryButton.setEnabled(true);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationHelp(int i, String str) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mTimePreviousHelpMessage < 800) {
            return;
        }
        updateIcon(0);
        this.mTimePreviousHelpMessage = currentTimeMillis;
        if (str != null) {
            if (Utils.isScreenLandscape(this.mContext)) {
                setTextOnDescriptionTextView(str);
            } else {
                BiometricPromptGuiHelper.setText(this.mFeedbackTextView, str);
            }
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationSucceeded() {
        BiometricPromptGuiHelper.setText(this.mFeedbackTextView, "");
        PromptConfig promptConfig = this.mPromptConfig;
        if (promptConfig.mPromptInfo.isConfirmationRequested()) {
            pausePunchHoleAnimation();
            this.mReTryButton.setVisibility(8);
            this.mReTryButton.setEnabled(false);
            if (promptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
                this.mSubTitleTxtView.setText(R.string.biometric_prompt_confirmed_description_text);
            } else {
                this.mDescriptionTxtView.setText(
                        R.string.biometric_prompt_confirmed_description_text);
            }
            this.mConfirmButton.setEnabled(true);
            BiometricPromptGuiHelper.setVisibilityDelayAnimation(
                    this.mConfirmButton,
                    new FacePromptGuiHelper$$ExternalSyntheticLambda0(this, 0));
            updateIcon(0);
            if (promptConfig.isDeviceCredentialAllowed()) {
                CharSequence negativeButtonText = promptConfig.mPromptInfo.getNegativeButtonText();
                if (TextUtils.isEmpty(negativeButtonText)) {
                    negativeButtonText =
                            this.mContext.getString(R.string.biometric_prompt_default_cancel);
                    this.mIsSupportNegativeButton = false;
                }
                this.mBottomButton.setText(negativeButtonText);
            }
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void initPrompt() {
        super.initPrompt();
        SensorPrivacyManager sensorPrivacyManager =
                (SensorPrivacyManager) this.mContext.getSystemService("sensor_privacy");
        this.mSensorPrivacyManager = sensorPrivacyManager;
        this.mIsCameraBlocked =
                sensorPrivacyManager != null && sensorPrivacyManager.isSensorPrivacyEnabled(2);
        if (!this.mPromptConfig.isDeviceCredentialAllowed()) {
            this.mIsSupportNegativeButton = true;
        }
        LinearLayout linearLayout =
                (LinearLayout) this.mBaseView.findViewById(R.id.id_prompt_feedback_layout);
        if (linearLayout != null) {
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.topMargin = Utils.getStatusBarHeight(this.mContext);
            linearLayout.setLayoutParams(layoutParams);
        }
        setUpPunchHoleVI();
        setUpPunchHoleFeedBackText();
        updateIcon(0);
        this.mBaseView.setFocusableInTouchMode(true);
        this.mBaseView.requestFocus();
        View view = this.mBaseView;
        if (view != null) {
            final RelativeLayout relativeLayout =
                    (RelativeLayout) view.findViewById(R.id.id_prompt_main_layout);
            relativeLayout
                    .getViewTreeObserver()
                    .addOnGlobalLayoutListener(
                            new ViewTreeObserver
                                    .OnGlobalLayoutListener() { // from class:
                                                                // com.samsung.android.biometrics.app.setting.prompt.FacePromptGuiHelper.1
                                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                                public final void onGlobalLayout() {
                                    WindowInsets rootWindowInsets =
                                            FacePromptGuiHelper.this.mBaseView
                                                    .getRootWindowInsets();
                                    if (rootWindowInsets != null) {
                                        if (rootWindowInsets.getDisplayCutout() != null) {
                                            Log.d(
                                                    FacePromptGuiHelper.this.TAG,
                                                    "Notch support, setPunchHoleUI");
                                            FacePromptGuiHelper.this.playPunchHoleAnimation();
                                        } else {
                                            Log.d(
                                                    FacePromptGuiHelper.this.TAG,
                                                    "Notch not support");
                                            FacePromptGuiHelper.this.pausePunchHoleAnimation();
                                        }
                                        relativeLayout
                                                .getViewTreeObserver()
                                                .removeOnGlobalLayoutListener(this);
                                    }
                                }
                            });
        }
        final int i = 0;
        this.mReTryButton.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.biometrics.app.setting.prompt.FacePromptGuiHelper$$ExternalSyntheticLambda2
                    public final /* synthetic */ FacePromptGuiHelper f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        SensorPrivacyManager sensorPrivacyManager2;
                        int i2 = i;
                        FacePromptGuiHelper facePromptGuiHelper = this.f$0;
                        switch (i2) {
                            case 0:
                                if (facePromptGuiHelper.mIsCameraBlocked
                                        && (sensorPrivacyManager2 =
                                                        facePromptGuiHelper.mSensorPrivacyManager)
                                                != null) {
                                    sensorPrivacyManager2.setSensorPrivacy(2, 2, false);
                                    facePromptGuiHelper.mReTryButton.setText(
                                            facePromptGuiHelper.mContext.getString(
                                                    R.string.biometric_prompt_postive_retry));
                                    facePromptGuiHelper.mIsCameraBlocked = false;
                                }
                                facePromptGuiHelper.mReTryButton.setVisibility(8);
                                facePromptGuiHelper.mReTryButton.setEnabled(false);
                                facePromptGuiHelper.updateIcon(0);
                                PromptConfig promptConfig = facePromptGuiHelper.mPromptConfig;
                                if (promptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
                                    facePromptGuiHelper.mSubTitleTxtView.setText(
                                            promptConfig.mPromptInfo.getSubtitle());
                                } else {
                                    facePromptGuiHelper.mDescriptionTxtView.setText(
                                            facePromptGuiHelper.mDescriptionText);
                                }
                                facePromptGuiHelper.playPunchHoleAnimation();
                                ((BiometricPromptClient) promptConfig.mCallback).sendEvent(1001, 0);
                                break;
                            case 1:
                                BiometricPromptClient biometricPromptClient =
                                        (BiometricPromptClient)
                                                facePromptGuiHelper.mPromptConfig.mCallback;
                                biometricPromptClient.mDismissedReason = 1;
                                biometricPromptClient.stop();
                                break;
                            default:
                                PromptConfig promptConfig2 = facePromptGuiHelper.mPromptConfig;
                                if (promptConfig2.isDeviceCredentialAllowed()
                                        && facePromptGuiHelper.mConfirmButton.getVisibility()
                                                != 0) {
                                    promptConfig2.mCallback.onDeviceCredentialPressed();
                                    break;
                                } else if (!facePromptGuiHelper.mIsSupportNegativeButton) {
                                    ((BiometricPromptClient) promptConfig2.mCallback)
                                            .onUserCancel(2);
                                    break;
                                } else {
                                    BiometricPromptClient biometricPromptClient2 =
                                            (BiometricPromptClient) promptConfig2.mCallback;
                                    biometricPromptClient2.mDismissedReason = 2;
                                    biometricPromptClient2.stop();
                                    break;
                                }
                        }
                    }
                });
        final int i2 = 1;
        this.mConfirmButton.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.biometrics.app.setting.prompt.FacePromptGuiHelper$$ExternalSyntheticLambda2
                    public final /* synthetic */ FacePromptGuiHelper f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        SensorPrivacyManager sensorPrivacyManager2;
                        int i22 = i2;
                        FacePromptGuiHelper facePromptGuiHelper = this.f$0;
                        switch (i22) {
                            case 0:
                                if (facePromptGuiHelper.mIsCameraBlocked
                                        && (sensorPrivacyManager2 =
                                                        facePromptGuiHelper.mSensorPrivacyManager)
                                                != null) {
                                    sensorPrivacyManager2.setSensorPrivacy(2, 2, false);
                                    facePromptGuiHelper.mReTryButton.setText(
                                            facePromptGuiHelper.mContext.getString(
                                                    R.string.biometric_prompt_postive_retry));
                                    facePromptGuiHelper.mIsCameraBlocked = false;
                                }
                                facePromptGuiHelper.mReTryButton.setVisibility(8);
                                facePromptGuiHelper.mReTryButton.setEnabled(false);
                                facePromptGuiHelper.updateIcon(0);
                                PromptConfig promptConfig = facePromptGuiHelper.mPromptConfig;
                                if (promptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
                                    facePromptGuiHelper.mSubTitleTxtView.setText(
                                            promptConfig.mPromptInfo.getSubtitle());
                                } else {
                                    facePromptGuiHelper.mDescriptionTxtView.setText(
                                            facePromptGuiHelper.mDescriptionText);
                                }
                                facePromptGuiHelper.playPunchHoleAnimation();
                                ((BiometricPromptClient) promptConfig.mCallback).sendEvent(1001, 0);
                                break;
                            case 1:
                                BiometricPromptClient biometricPromptClient =
                                        (BiometricPromptClient)
                                                facePromptGuiHelper.mPromptConfig.mCallback;
                                biometricPromptClient.mDismissedReason = 1;
                                biometricPromptClient.stop();
                                break;
                            default:
                                PromptConfig promptConfig2 = facePromptGuiHelper.mPromptConfig;
                                if (promptConfig2.isDeviceCredentialAllowed()
                                        && facePromptGuiHelper.mConfirmButton.getVisibility()
                                                != 0) {
                                    promptConfig2.mCallback.onDeviceCredentialPressed();
                                    break;
                                } else if (!facePromptGuiHelper.mIsSupportNegativeButton) {
                                    ((BiometricPromptClient) promptConfig2.mCallback)
                                            .onUserCancel(2);
                                    break;
                                } else {
                                    BiometricPromptClient biometricPromptClient2 =
                                            (BiometricPromptClient) promptConfig2.mCallback;
                                    biometricPromptClient2.mDismissedReason = 2;
                                    biometricPromptClient2.stop();
                                    break;
                                }
                        }
                    }
                });
        Button button = this.mBottomButton;
        if (button != null) {
            final int i3 = 2;
            button.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.biometrics.app.setting.prompt.FacePromptGuiHelper$$ExternalSyntheticLambda2
                        public final /* synthetic */ FacePromptGuiHelper f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            SensorPrivacyManager sensorPrivacyManager2;
                            int i22 = i3;
                            FacePromptGuiHelper facePromptGuiHelper = this.f$0;
                            switch (i22) {
                                case 0:
                                    if (facePromptGuiHelper.mIsCameraBlocked
                                            && (sensorPrivacyManager2 =
                                                            facePromptGuiHelper
                                                                    .mSensorPrivacyManager)
                                                    != null) {
                                        sensorPrivacyManager2.setSensorPrivacy(2, 2, false);
                                        facePromptGuiHelper.mReTryButton.setText(
                                                facePromptGuiHelper.mContext.getString(
                                                        R.string.biometric_prompt_postive_retry));
                                        facePromptGuiHelper.mIsCameraBlocked = false;
                                    }
                                    facePromptGuiHelper.mReTryButton.setVisibility(8);
                                    facePromptGuiHelper.mReTryButton.setEnabled(false);
                                    facePromptGuiHelper.updateIcon(0);
                                    PromptConfig promptConfig = facePromptGuiHelper.mPromptConfig;
                                    if (promptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
                                        facePromptGuiHelper.mSubTitleTxtView.setText(
                                                promptConfig.mPromptInfo.getSubtitle());
                                    } else {
                                        facePromptGuiHelper.mDescriptionTxtView.setText(
                                                facePromptGuiHelper.mDescriptionText);
                                    }
                                    facePromptGuiHelper.playPunchHoleAnimation();
                                    ((BiometricPromptClient) promptConfig.mCallback)
                                            .sendEvent(1001, 0);
                                    break;
                                case 1:
                                    BiometricPromptClient biometricPromptClient =
                                            (BiometricPromptClient)
                                                    facePromptGuiHelper.mPromptConfig.mCallback;
                                    biometricPromptClient.mDismissedReason = 1;
                                    biometricPromptClient.stop();
                                    break;
                                default:
                                    PromptConfig promptConfig2 = facePromptGuiHelper.mPromptConfig;
                                    if (promptConfig2.isDeviceCredentialAllowed()
                                            && facePromptGuiHelper.mConfirmButton.getVisibility()
                                                    != 0) {
                                        promptConfig2.mCallback.onDeviceCredentialPressed();
                                        break;
                                    } else if (!facePromptGuiHelper.mIsSupportNegativeButton) {
                                        ((BiometricPromptClient) promptConfig2.mCallback)
                                                .onUserCancel(2);
                                        break;
                                    } else {
                                        BiometricPromptClient biometricPromptClient2 =
                                                (BiometricPromptClient) promptConfig2.mCallback;
                                        biometricPromptClient2.mDismissedReason = 2;
                                        biometricPromptClient2.stop();
                                        break;
                                    }
                            }
                        }
                    });
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void onConfigurationChanged(Configuration configuration) {
        LinearLayout linearLayout =
                (LinearLayout) this.mBaseView.findViewById(R.id.id_prompt_feedback_layout);
        if (linearLayout != null) {
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.topMargin = Utils.getStatusBarHeight(this.mContext);
            linearLayout.setLayoutParams(layoutParams);
        }
        setUpPunchHoleVI();
        setUpPunchHoleFeedBackText();
        super.onConfigurationChanged(configuration);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void onRotationInfoChanged(int i) {
        setPunchHoleRotation(i);
        super.onRotationInfoChanged(i);
    }

    public final void pausePunchHoleAnimation() {
        LottieAnimationView lottieAnimationView = this.mPunchHoleIcon;
        if (lottieAnimationView != null && lottieAnimationView.isAnimating()) {
            this.mPunchHoleIcon.setVisibility(8);
            this.mPunchHoleIcon.pauseAnimation();
        }
    }

    public final void playPunchHoleAnimation() {
        if (this.mPunchHoleIcon == null) {
            return;
        }
        Context context = this.mContext;
        if (context == null) {
            Log.w("BSS_Utils", "isOneHandedModeActive : context = null");
        } else if (Settings.System.getInt(context.getContentResolver(), "any_screen_running", 0)
                == 1) {
            pausePunchHoleAnimation();
            return;
        }
        if (this.mPunchHoleIcon.isAnimating()) {
            return;
        }
        this.mPunchHoleIcon.setVisibility(0);
        this.mPunchHoleIcon.setAnimation(this.mPunchHoleVIHelper.getAnimationName(false));
        this.mPunchHoleIcon.playAnimation();
    }

    public final void setPunchHoleRotation(int i) {
        if (this.mPunchHoleIcon == null) {
            return;
        }
        this.mViViewLocation = this.mPunchHoleVIHelper.getPunchHoleVIRect();
        this.mPunchHoleIcon.setTranslationX(r0.left);
        this.mPunchHoleIcon.setTranslationY(this.mViViewLocation.top);
        if (i == 1) {
            this.mPunchHoleIcon.setRotation(-90.0f);
        } else {
            if (i != 3) {
                this.mPunchHoleIcon.setRotation(RecyclerView.DECELERATION_RATE);
                return;
            }
            this.mPunchHoleIcon.setTranslationX(
                    this.mViViewLocation.left - Utils.getNavigationBarHeight(this.mContext));
            this.mPunchHoleIcon.setTranslationY(this.mViViewLocation.top);
            this.mPunchHoleIcon.setRotation(90.0f);
        }
    }

    public final void setTextOnDescriptionTextView(String str) {
        BiometricPromptGuiHelper.setText(this.mFeedbackTextView, "");
        if (this.mPromptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
            BiometricPromptGuiHelper.setText(this.mSubTitleTxtView, str);
        } else {
            BiometricPromptGuiHelper.setText(this.mDescriptionTxtView, str);
        }
    }

    public final void setUpPunchHoleFeedBackText() {
        TextView textView = (TextView) this.mBaseView.findViewById(R.id.id_prompt_feedback_text);
        this.mFeedbackTextView = textView;
        if (textView != null) {
            textView.setVisibility(Utils.isScreenLandscape(this.mContext) ? 8 : 0);
        }
    }

    public final void setUpPunchHoleVI() {
        PunchHoleVIHelper punchHoleVIHelper = new PunchHoleVIHelper(this.mContext);
        this.mPunchHoleVIHelper = punchHoleVIHelper;
        if (punchHoleVIHelper.initialize()) {
            LottieAnimationView lottieAnimationView =
                    (LottieAnimationView)
                            this.mBaseView.findViewById(R.id.id_prompt_punch_hole_lottie_view);
            this.mPunchHoleIcon = lottieAnimationView;
            lottieAnimationView.setVisibility(0);
            this.mPunchHoleIcon.loop(true);
            this.mPunchHoleIcon.setRenderMode(RenderMode.HARDWARE);
            this.mViViewLocation = this.mPunchHoleVIHelper.getPunchHoleVIRect();
            this.mPunchHoleIcon.setTranslationX(r0.left);
            this.mPunchHoleIcon.setTranslationY(this.mViViewLocation.top);
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) this.mPunchHoleIcon.getLayoutParams();
            layoutParams.width = this.mViViewLocation.width();
            layoutParams.height = this.mViViewLocation.height();
            this.mPunchHoleIcon.setLayoutParams(layoutParams);
            setPunchHoleRotation(Utils.getRotation(this.mContext));
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void show() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mContext.getDisplay().getRealMetrics(displayMetrics);
        this.mDisplayMetrics = displayMetrics;
        boolean isScreenLandscape = Utils.isScreenLandscape(this.mContext);
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) this.mDialogLayout.getLayoutParams();
        layoutParams.width = Utils.dipToPixel(this.mContext, 340.0d);
        layoutParams.gravity = 1;
        this.mDialogLayout.setLayoutParams(layoutParams);
        setUpLeftMarginViewWidth(getLeftMargin(layoutParams.width, isScreenLandscape));
        customizeSwitch(layoutParams.width);
        this.mBaseView.setVisibility(0);
        this.mTimePreviousHelpMessage = 0L;
    }

    public final void updateIcon(int i) {
        LottieAnimationView lottieAnimationView = this.mIconImgView;
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) lottieAnimationView.getLayoutParams();
        lottieAnimationView.setImageDrawable(this.mContext.getDrawable(R.drawable.face_default));
        layoutParams.width =
                (int)
                        this.mContext
                                .getResources()
                                .getDimension(R.dimen.fingerprint_verification_icon_width);
        layoutParams.height =
                (int)
                        this.mContext
                                .getResources()
                                .getDimension(R.dimen.fingerprint_verification_icon_height);
        lottieAnimationView.setLayoutParams(layoutParams);
        lottieAnimationView.setColorFilter((ColorFilter) null);
        lottieAnimationView.setVisibility(0);
        if (i == 1) {
            lottieAnimationView.setAnimation("face_error_nomatch.json");
        }
        lottieAnimationView.playAnimation();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void updateLayoutAsConfigurationChanged(int i) {
        int i2;
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) this.mDialogLayout.getLayoutParams();
        layoutParams.width = Utils.dipToPixel(this.mContext, 340.0d);
        if (i == 2) {
            Log.d(this.TAG, "onConfigurationChanged:  LAND MODE");
            i2 = (this.mScreenLandWidth - layoutParams.width) / 2;
        } else {
            int displayWidthInPortraitMode = Utils.getDisplayWidthInPortraitMode(this.mContext);
            this.mScreenPortraitWidth = displayWidthInPortraitMode;
            i2 = (displayWidthInPortraitMode - layoutParams.width) / 2;
        }
        layoutParams.gravity = 1;
        this.mDialogLayout.setLayoutParams(layoutParams);
        setUpLeftMarginViewWidth(i2);
        customizeSwitch(layoutParams.width);
        setPunchHoleRotation(Utils.getRotation(this.mContext));
        setUpKnoxBrandLogoImage();
    }
}
