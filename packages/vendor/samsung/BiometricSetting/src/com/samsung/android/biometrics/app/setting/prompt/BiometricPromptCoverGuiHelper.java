package com.samsung.android.biometrics.app.setting.prompt;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.ResourceManager;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class BiometricPromptCoverGuiHelper extends BiometricPromptGuiHelper {
    public final String TAG;
    public final Handler mH;
    public boolean mIsAuthenticated;
    public int mMarginOfMultiButton;
    public int mMarginOfSingleNegativeButton;
    public final int mModalityType;
    public Button mPositiveButtonGuide;
    public int mPurposeOfRetryButton;
    public final BiometricPromptCoverGuiHelper$$ExternalSyntheticLambda3 mRunnableClearGuide;
    public long mTimePreviousHelpMessage;

    /* JADX WARN: Type inference failed for: r2v4, types: [com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCoverGuiHelper$$ExternalSyntheticLambda3] */
    public BiometricPromptCoverGuiHelper(
            Context context, Looper looper, View view, PromptConfig promptConfig, int i) {
        super(context, view, promptConfig, null);
        this.TAG = "BSS_BiometricCoverGuiHelper";
        this.mPurposeOfRetryButton = 1;
        this.mH = new Handler(looper);
        this.mModalityType = i;
        this.mRunnableClearGuide =
                new Runnable() { // from class:
                                 // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCoverGuiHelper$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        BiometricPromptCoverGuiHelper.this.clearGuide(0L);
                    }
                };
        this.TAG = "BSS_BiometricCoverGuiHelper[" + hashCode() + "]";
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void cleanUpPrompt() {
        super.cleanUpPrompt();
        this.mH.removeCallbacks(this.mRunnableClearGuide);
    }

    public final void clearGuide(long j) {
        Handler handler = this.mH;
        BiometricPromptCoverGuiHelper$$ExternalSyntheticLambda3
                biometricPromptCoverGuiHelper$$ExternalSyntheticLambda3 = this.mRunnableClearGuide;
        handler.removeCallbacks(biometricPromptCoverGuiHelper$$ExternalSyntheticLambda3);
        if (j > 0) {
            handler.postDelayed(biometricPromptCoverGuiHelper$$ExternalSyntheticLambda3, j);
            return;
        }
        showDefaultIcon();
        PromptConfig promptConfig = this.mPromptConfig;
        if (promptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
            BiometricPromptGuiHelper.setText(
                    this.mSubTitleTxtView, promptConfig.mPromptInfo.getSubtitle());
        } else {
            BiometricPromptGuiHelper.setText(this.mDescriptionTxtView, this.mDescriptionText);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void customizeSwitch(int i) {
        if (this.mPromptConfig.mNumberOfAvailableBiometrics <= 1) {
            this.mBaseView.findViewById(R.id.id_prompt_switch).setVisibility(8);
            return;
        }
        Button button = (Button) this.mDialogLayout.findViewById(R.id.button_switch_biometric_left);
        Button button2 =
                (Button) this.mDialogLayout.findViewById(R.id.button_switch_biometric_right);
        button.setEnabled(true);
        button.setOnClickListener(
                new BiometricPromptCoverGuiHelper$$ExternalSyntheticLambda0(this, 3));
        button2.setEnabled(true);
        button2.setOnClickListener(
                new BiometricPromptCoverGuiHelper$$ExternalSyntheticLambda0(this, 4));
        if (this.mModalityType == 2) {
            button.setText(R.string.biometric_prompt_button_fingerprint);
            button.setBackground(this.mContext.getDrawable(R.drawable.custom_thumb));
            button.setTextColor(this.mContext.getColor(R.color.biometric_prompt_switch_thumb_text));
            button2.setBackground(this.mContext.getDrawable(R.drawable.btn_bp_switch_biometric));
            button2.setTextColor(
                    this.mContext.getColor(R.color.biometric_prompt_switch_track_text));
            return;
        }
        button2.setText(R.string.biometric_prompt_button_face);
        button2.setBackground(this.mContext.getDrawable(R.drawable.custom_thumb));
        button2.setTextColor(this.mContext.getColor(R.color.biometric_prompt_switch_thumb_text));
        button.setBackground(this.mContext.getDrawable(R.drawable.btn_bp_switch_biometric));
        button.setTextColor(this.mContext.getColor(R.color.biometric_prompt_switch_track_text));
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final String getDefaultDescriptionMessage() {
        return this.mModalityType == 2
                ? this.mContext.getString(
                        R.string.fingerprint_verification_description_default_text)
                : this.mContext.getString(R.string.face_verification_description_default_text);
    }

    public String getErrorString(int i, int i2) {
        int i3 = this.mModalityType;
        return i3 == 2
                ? FingerprintManager.getErrorString(this.mContext, i, i2)
                : i3 == 8
                        ? i2 == 100003
                                ? this.mContext.getString(R.string.camera_access_turn_on_message)
                                : FaceManager.getErrorString(this.mContext, i, i2)
                        : "";
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final int getNavigationBarHeight() {
        return 0;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final int getOrientation() {
        return 2;
    }

    public CharSequence getRejectString() {
        int identifier;
        if (this.mModalityType != 2) {
            return this.mContext.getString(R.string.face_biometric_prompt_identify_failure_title);
        }
        Context context = this.mContext;
        new ResourceManager(context, "android");
        if (TextUtils.isEmpty("sem_fingerprint_result_failed")
                || (identifier =
                                context.getResources()
                                        .getIdentifier(
                                                "sem_fingerprint_result_failed",
                                                "string",
                                                "android"))
                        <= 0) {
            return "";
        }
        try {
            return context.getText(identifier);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final int getResourceIdOfPositiveButtonTextSize() {
        return R.dimen.biometric_prompt_verification_negative_text_size;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final int getScreenLandscapeWidthWithoutNavigationBar() {
        return this.mDisplayMetrics.widthPixels;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final int getStatusBarHeight() {
        return 0;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationError(int i, int i2) {
        boolean z = true;
        boolean z2 = i == 7 || i == 9;
        int i3 = this.mModalityType;
        if (i3 == 2) {
            showDefaultIcon();
        } else if (i3 == 8) {
            if (i2 == 100003) {
                this.mPurposeOfRetryButton = 2;
                this.mReTryButton.setText(
                        this.mContext.getString(R.string.camera_access_turn_on_button));
            } else if (z2) {
                z = false;
            }
            if (i3 == 8) {
                showIcon("face_error_nomatch.json");
            }
            if (z) {
                showPositiveButton(this.mReTryButton);
            }
        }
        showMessage(z2 ? getLockoutErrorMessage(i) : getErrorString(i, i2));
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationFailed() {
        int i = this.mModalityType;
        showIcon(
                i == 2
                        ? "fingerprint_error_nomatch.json"
                        : i == 8 ? "face_error_nomatch.json" : "");
        showMessage(getRejectString());
        if (i == 2) {
            clearGuide(3000L);
        } else if (i == 8) {
            showPositiveButton(this.mReTryButton);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationHelp(int i, String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        int i2 = this.mModalityType;
        if (i2 == 8) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.mTimePreviousHelpMessage < 800) {
                return;
            } else {
                this.mTimePreviousHelpMessage = currentTimeMillis;
            }
        }
        if (i2 == 2) {
            if (i == 1 || i == 2) {
                str2 = "fingerprint_error_move.json";
            } else if (i == 3) {
                str2 = "fingerprint_error_wipe.json";
            } else if (i == 5) {
                str2 = "fingerprint_error_timeout.json";
            } else if (i == 1003) {
                str2 = "fingerprint_error_presslong.json";
            }
            showIcon(str2);
            showMessage(str);
            clearGuide(3000L);
        }
        str2 = "";
        showIcon(str2);
        showMessage(str);
        clearGuide(3000L);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationSucceeded() {
        this.mIsAuthenticated = true;
        PromptConfig promptConfig = this.mPromptConfig;
        if (promptConfig.mPromptInfo.isConfirmationRequested() && this.mModalityType == 8) {
            this.mReTryButton.setVisibility(4);
            this.mReTryButton.setEnabled(false);
            this.mIconImgView.setVisibility(8);
            if (promptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
                this.mSubTitleTxtView.setText(R.string.biometric_prompt_confirmed_description_text);
            } else {
                this.mDescriptionTxtView.setText(
                        R.string.biometric_prompt_confirmed_description_text);
            }
            if (promptConfig.isDeviceCredentialAllowed()) {
                CharSequence negativeButtonText = promptConfig.mPromptInfo.getNegativeButtonText();
                if (TextUtils.isEmpty(negativeButtonText)) {
                    negativeButtonText =
                            this.mContext.getString(R.string.biometric_prompt_default_cancel);
                }
                this.mBottomButton.setText(negativeButtonText);
            }
            showPositiveButton(this.mConfirmButton);
        }
    }

    public final void handleOnClickSwitchButton(int i) {
        Log.i(
                this.TAG,
                "handleCnClickSwitchButton: modality = "
                        + i
                        + ", OnModalityChangeListener = "
                        + this.mOnModalityChangeListener);
        BiometricPromptGuiHelper.OnModalityChangeListener onModalityChangeListener =
                this.mOnModalityChangeListener;
        if (onModalityChangeListener != null) {
            ((BiometricPromptWindow) onModalityChangeListener).onModalitySwitched(i);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void hideSwitch() {
        this.mBaseView.findViewById(R.id.id_prompt_switch).setVisibility(8);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void initPrompt() {
        super.initPrompt();
        this.mDescriptionTxtView.setText(this.mDescriptionText);
        this.mIconImgView.setVisibility(8);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mContext.getDisplay().getRealMetrics(displayMetrics);
        this.mDisplayMetrics = displayMetrics;
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) this.mDialogLayout.getLayoutParams();
        this.mScreenLandWidth = this.mDisplayMetrics.widthPixels;
        int fraction =
                (int)
                        (this.mContext
                                        .getResources()
                                        .getFraction(
                                                R.fraction
                                                        .fingerprint_verification_width_percent_flip_cover,
                                                1,
                                                1)
                                * displayMetrics.widthPixels);
        layoutParams.width = fraction;
        customizeSwitch(fraction);
        this.mDialogLayout.setLayoutParams(layoutParams);
        showDefaultIcon();
        Button button = (Button) this.mDialogLayout.findViewById(R.id.button_positive_area_guide);
        this.mPositiveButtonGuide = button;
        button.setVisibility(8);
        this.mBottomButton.setOnClickListener(
                new BiometricPromptCoverGuiHelper$$ExternalSyntheticLambda0(this, 0));
        this.mMarginOfSingleNegativeButton =
                (int)
                        this.mContext
                                .getResources()
                                .getDimension(
                                        R.dimen.biometric_prompt_negative_bottom_cover_margin);
        this.mMarginOfMultiButton =
                (int)
                        this.mContext
                                .getResources()
                                .getDimension(R.dimen.biometric_prompt_bottom_cover_margin);
        setNegativeButtonMargin(this.mMarginOfSingleNegativeButton);
        this.mReTryButton.setOnClickListener(
                new BiometricPromptCoverGuiHelper$$ExternalSyntheticLambda0(this, 1));
        this.mConfirmButton.setOnClickListener(
                new BiometricPromptCoverGuiHelper$$ExternalSyntheticLambda0(this, 2));
        this.mBaseView.setFocusableInTouchMode(true);
        this.mBaseView.requestFocus();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final boolean isScreenLandscape() {
        return true;
    }

    public final void setNegativeButtonMargin(int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams;
        Button button = this.mBottomButton;
        if (button == null
                || (marginLayoutParams = (ViewGroup.MarginLayoutParams) button.getLayoutParams())
                        == null) {
            return;
        }
        marginLayoutParams.setMarginStart(i);
        marginLayoutParams.setMarginEnd(i);
        this.mBottomButton.setLayoutParams(marginLayoutParams);
    }

    public final void showDefaultIcon() {
        boolean z = this.mPromptConfig.mNumberOfAvailableBiometrics > 1;
        LottieAnimationView lottieAnimationView = this.mIconImgView;
        if (z) {
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) lottieAnimationView.getLayoutParams();
            marginLayoutParams.topMargin =
                    (int)
                            this.mContext
                                    .getResources()
                                    .getDimension(
                                            R.dimen
                                                    .biometric_prompt_verification_description_top_margin);
            lottieAnimationView.setLayoutParams(marginLayoutParams);
            this.mIconImgView.setVisibility(8);
            return;
        }
        int i = this.mModalityType;
        if (i == 2) {
            lottieAnimationView.setImageResource(
                    R.drawable.sem_biometric_prompt_dialog_fingerprint);
            lottieAnimationView.setColorFilter(
                    this.mContext
                            .getResources()
                            .getColor(
                                    R.color.fingerprint_verification_fingerprint_icon_default_color,
                                    null));
            lottieAnimationView.setVisibility(0);
        } else if (i == 8) {
            lottieAnimationView.setImageDrawable(
                    this.mContext.getDrawable(R.drawable.face_default));
            lottieAnimationView.setVisibility(0);
        }
    }

    public final void showIcon(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        LottieAnimationView lottieAnimationView = this.mIconImgView;
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) lottieAnimationView.getLayoutParams();
        marginLayoutParams.topMargin =
                (int)
                        this.mContext
                                .getResources()
                                .getDimension(
                                        R.dimen
                                                .biometric_prompt_verification_description_top_margin_in_multi);
        lottieAnimationView.setLayoutParams(marginLayoutParams);
        lottieAnimationView.setAnimation(str);
        lottieAnimationView.setColorFilter((ColorFilter) null);
        lottieAnimationView.setVisibility(0);
        lottieAnimationView.playAnimation();
    }

    public final void showMessage(CharSequence charSequence) {
        this.mH.removeCallbacks(this.mRunnableClearGuide);
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        if (this.mPromptConfig.mPromptInfo.semIsDescriptionOptionalUsed()) {
            BiometricPromptGuiHelper.setText(this.mSubTitleTxtView, charSequence);
        } else {
            BiometricPromptGuiHelper.setText(this.mDescriptionTxtView, charSequence);
        }
    }

    public final void showPositiveButton(Button button) {
        this.mPositiveButtonGuide.setVisibility(4);
        button.setVisibility(0);
        button.setEnabled(true);
        setNegativeButtonMargin(this.mMarginOfMultiButton);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void setUpCustomView() {}

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void setUpScrollView() {}

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void show() {}
}
