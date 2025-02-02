package com.samsung.android.biometrics.app.setting.prompt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ColorFilter;
import android.hardware.SensorPrivacyManager;
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
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.ResourceManager;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper;

/* loaded from: classes.dex */
public final class BiometricCoverGuiHelper extends BiometricPromptGuiHelper {
    private String TAG;

    /* renamed from: mH */
    private final Handler f31mH;
    private boolean mIsAuthenticated;
    private int mMarginOfMultiButton;
    private int mMarginOfSingleNegativeButton;
    private final int mModalityType;
    private Button mPositiveButtonGuide;
    private int mPurposeOfRetryButton;
    private final BiometricCoverGuiHelper$$ExternalSyntheticLambda1 mRunnableClearGuide;
    private long mTimePreviousHelpMessage;

    public static void $r8$lambda$SUfGDoiSA7N3qtPCMxYFe9wvgq8(BiometricCoverGuiHelper biometricCoverGuiHelper) {
        if (biometricCoverGuiHelper.mPurposeOfRetryButton == 2) {
            SensorPrivacyManager sensorPrivacyManager = (SensorPrivacyManager) biometricCoverGuiHelper.mContext.getSystemService("sensor_privacy");
            if (sensorPrivacyManager != null) {
                sensorPrivacyManager.setSensorPrivacy(2, 2, false);
            }
            biometricCoverGuiHelper.mReTryButton.setText(biometricCoverGuiHelper.mContext.getString(R.string.biometric_prompt_postive_retry));
            biometricCoverGuiHelper.mPurposeOfRetryButton = 1;
        }
        biometricCoverGuiHelper.showDefaultIcon();
        Button button = biometricCoverGuiHelper.mReTryButton;
        button.setVisibility(4);
        button.setEnabled(false);
        biometricCoverGuiHelper.mPositiveButtonGuide.setVisibility(8);
        biometricCoverGuiHelper.setNegativeButtonMargin(biometricCoverGuiHelper.mMarginOfSingleNegativeButton);
        biometricCoverGuiHelper.mDescriptionTxtView.setText(biometricCoverGuiHelper.mDescriptionText);
        biometricCoverGuiHelper.mPromptConfig.getCallback().onTryAgainPressed();
    }

    /* renamed from: $r8$lambda$sZ3yAzKtGBE-RKcoGrCNXoT5G_0, reason: not valid java name */
    public static /* synthetic */ void m241$r8$lambda$sZ3yAzKtGBERKcoGrCNXoT5G_0(BiometricCoverGuiHelper biometricCoverGuiHelper) {
        if (biometricCoverGuiHelper.mPromptConfig.isDeviceCredentialAllowed()) {
            if (biometricCoverGuiHelper.mIsAuthenticated) {
                biometricCoverGuiHelper.mPromptConfig.getCallback().onUserCancel(3);
                return;
            } else {
                biometricCoverGuiHelper.mPromptConfig.getCallback().onDeviceCredentialPressed();
                return;
            }
        }
        if (biometricCoverGuiHelper.mPromptConfig.hasNegativeButton()) {
            biometricCoverGuiHelper.mPromptConfig.getCallback().onNegativeButtonPressed();
        } else {
            biometricCoverGuiHelper.mPromptConfig.getCallback().onUserCancel(3);
        }
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.samsung.android.biometrics.app.setting.prompt.BiometricCoverGuiHelper$$ExternalSyntheticLambda1] */
    public BiometricCoverGuiHelper(Context context, Looper looper, View view, PromptConfig promptConfig, int i) {
        super(context, view, promptConfig, null);
        this.TAG = "BSS_BiometricCoverGuiHelper";
        this.mPurposeOfRetryButton = 1;
        this.f31mH = new Handler(looper);
        this.mModalityType = i;
        this.mRunnableClearGuide = new Runnable() { // from class: com.samsung.android.biometrics.app.setting.prompt.BiometricCoverGuiHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BiometricCoverGuiHelper.this.clearGuide(0L);
            }
        };
        this.TAG += "[" + hashCode() + "]";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearGuide(long j) {
        Handler handler = this.f31mH;
        BiometricCoverGuiHelper$$ExternalSyntheticLambda1 biometricCoverGuiHelper$$ExternalSyntheticLambda1 = this.mRunnableClearGuide;
        handler.removeCallbacks(biometricCoverGuiHelper$$ExternalSyntheticLambda1);
        if (j > 0) {
            handler.postDelayed(biometricCoverGuiHelper$$ExternalSyntheticLambda1, j);
        } else {
            showDefaultIcon();
            BiometricPromptGuiHelper.setText(this.mDescriptionTxtView, this.mDescriptionText);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnClickSwitchButton(int i) {
        Log.i(this.TAG, "handleCnClickSwitchButton: modality = " + i + ", OnModalityChangeListener = " + this.mOnModalityChangeListener);
        BiometricPromptGuiHelper.OnModalityChangeListener onModalityChangeListener = this.mOnModalityChangeListener;
        if (onModalityChangeListener != null) {
            ((BiometricPromptWindow) onModalityChangeListener).onModalitySwitched(i);
        }
    }

    private void setNegativeButtonMargin(int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams;
        Button button = this.mBottomButton;
        if (button == null || (marginLayoutParams = (ViewGroup.MarginLayoutParams) button.getLayoutParams()) == null) {
            return;
        }
        marginLayoutParams.setMarginStart(i);
        marginLayoutParams.setMarginEnd(i);
        this.mBottomButton.setLayoutParams(marginLayoutParams);
    }

    @SuppressLint({"UseCompatLoadingForDrawables"})
    private void showDefaultIcon() {
        if (this.mPromptConfig.isMultiSensorMode()) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mIconImgView.getLayoutParams();
            marginLayoutParams.topMargin = (int) this.mContext.getResources().getDimension(R.dimen.biometric_prompt_verification_description_top_margin);
            this.mIconImgView.setLayoutParams(marginLayoutParams);
            this.mIconImgView.setVisibility(8);
            return;
        }
        int i = this.mModalityType;
        if (i != 2) {
            if (i == 8) {
                this.mIconImgView.setImageDrawable(this.mContext.getDrawable(R.drawable.face_default));
                this.mIconImgView.setVisibility(0);
                return;
            }
            return;
        }
        if (this.mUdfpsInfo == null) {
            this.mIconImgView.setImageResource(R.drawable.sem_biometric_prompt_dialog_fingerprint);
            this.mIconImgView.setColorFilter(this.mContext.getResources().getColor(R.color.fingerprint_verification_fingerprint_icon_default_color, null));
            this.mIconImgView.setVisibility(0);
        }
    }

    private void showIcon(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mIconImgView.getLayoutParams();
        marginLayoutParams.topMargin = (int) this.mContext.getResources().getDimension(R.dimen.biometric_prompt_verification_description_top_margin_in_multi);
        this.mIconImgView.setLayoutParams(marginLayoutParams);
        this.mIconImgView.setAnimation(str);
        this.mIconImgView.setColorFilter((ColorFilter) null);
        this.mIconImgView.setVisibility(0);
        this.mIconImgView.playAnimation();
    }

    private void showMessage(CharSequence charSequence) {
        this.f31mH.removeCallbacks(this.mRunnableClearGuide);
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        BiometricPromptGuiHelper.setText(this.mDescriptionTxtView, charSequence);
    }

    private void showPositiveButton(Button button) {
        this.mPositiveButtonGuide.setVisibility(4);
        button.setVisibility(0);
        button.setEnabled(true);
        setNegativeButtonMargin(this.mMarginOfMultiButton);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void cleanUpPrompt() {
        this.f31mH.removeCallbacks(this.mRunnableClearGuide);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    protected final void customizeSwitch(int i, int i2) {
        if (!this.mPromptConfig.isMultiSensorMode()) {
            this.mBaseView.findViewById(R.id.id_prompt_switch).setVisibility(8);
            return;
        }
        Button button = (Button) this.mDialogLayout.findViewById(R.id.button_switch_biometric_left);
        Button button2 = (Button) this.mDialogLayout.findViewById(R.id.button_switch_biometric_right);
        button.setEnabled(true);
        button.setOnClickListener(new BiometricCoverGuiHelper$$ExternalSyntheticLambda0(this, 0));
        button2.setEnabled(true);
        button2.setOnClickListener(new BiometricCoverGuiHelper$$ExternalSyntheticLambda0(this, 1));
        if (this.mModalityType == 2) {
            button.setText(R.string.biometric_prompt_button_fingerprint);
            button.setBackground(this.mContext.getDrawable(R.drawable.custom_thumb));
            button.setTextColor(this.mContext.getColor(R.color.biometric_prompt_switch_thumb_text));
            button2.setBackground(this.mContext.getDrawable(R.drawable.btn_bp_switch_biometric));
            button2.setTextColor(this.mContext.getColor(R.color.biometric_prompt_switch_track_text));
            return;
        }
        button2.setText(R.string.biometric_prompt_button_face);
        button2.setBackground(this.mContext.getDrawable(R.drawable.custom_thumb));
        button2.setTextColor(this.mContext.getColor(R.color.biometric_prompt_switch_thumb_text));
        button.setBackground(this.mContext.getDrawable(R.drawable.btn_bp_switch_biometric));
        button.setTextColor(this.mContext.getColor(R.color.biometric_prompt_switch_track_text));
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    protected final String getDefaultDescriptionMessage() {
        return this.mModalityType == 2 ? this.mContext.getString(R.string.fingerprint_verification_description_default_text) : this.mContext.getString(R.string.face_verification_description_default_text);
    }

    @VisibleForTesting
    String getErrorString(int i, int i2) {
        int i3 = this.mModalityType;
        return i3 == 2 ? FingerprintManager.getErrorString(this.mContext, i, i2) : i3 == 8 ? i2 == 100003 ? this.mContext.getString(R.string.camera_access_turn_on_message) : FaceManager.getErrorString(this.mContext, i, i2) : "";
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    protected final int getNavigationBarHeight() {
        return 0;
    }

    @VisibleForTesting
    CharSequence getRejectString() {
        return this.mModalityType == 2 ? new ResourceManager(this.mContext, "android").getString() : this.mContext.getString(R.string.face_biometric_prompt_identify_failure_title);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    protected final int getResourceIdOfPositiveButtonTextSize() {
        return R.dimen.biometric_prompt_verification_negative_text_size;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    protected final int getScreenLandscapeWidthWithoutNavigationBar() {
        return this.mDisplayMetrics.widthPixels;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    protected final int getStatusBarHeight() {
        return 0;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationError(int i, int i2) {
        int i3 = this.mModalityType;
        if (i3 == 2) {
            showDefaultIcon();
        } else if (i3 == 8) {
            boolean z = true;
            if (i2 == 100003) {
                this.mPurposeOfRetryButton = 2;
                this.mReTryButton.setText(this.mContext.getString(R.string.camera_access_turn_on_button));
            } else {
                if (i == 7 || i == 9) {
                    z = false;
                }
            }
            if (i3 == 8) {
                showIcon("face_error_nomatch.json");
            }
            if (z) {
                showPositiveButton(this.mReTryButton);
            }
        }
        showMessage(getErrorString(i, i2));
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationFailed() {
        int i = this.mModalityType;
        showIcon(i == 2 ? "fingerprint_error_nomatch.json" : i == 8 ? "face_error_nomatch.json" : "");
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
        if (this.mPromptConfig.isConfirmationRequested() && this.mModalityType == 8) {
            this.mReTryButton.setVisibility(4);
            this.mReTryButton.setEnabled(false);
            this.mIconImgView.setVisibility(8);
            this.mDescriptionTxtView.setText(R.string.biometric_prompt_confirmed_description_text);
            if (this.mPromptConfig.isDeviceCredentialAllowed()) {
                CharSequence negativeButtonText = this.mPromptConfig.getNegativeButtonText();
                if (TextUtils.isEmpty(negativeButtonText)) {
                    negativeButtonText = this.mContext.getString(R.string.biometric_prompt_default_cancel);
                }
                this.mBottomButton.setText(negativeButtonText);
            }
            showPositiveButton(this.mConfirmButton);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void hideSwitch() {
        this.mBaseView.findViewById(R.id.id_prompt_switch).setVisibility(8);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void initPrompt() {
        int i = 2;
        this.mLastOrientation = 2;
        View view = this.mBaseView;
        if (view != null) {
            view.setOnKeyListener(new BiometricPromptGuiHelper.ViewOnKeyListenerC02954());
        }
        this.mDescriptionTxtView.setText(this.mDescriptionText);
        this.mIconImgView.setVisibility(8);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mContext.getDisplay().getRealMetrics(displayMetrics);
        this.mDisplayMetrics = displayMetrics;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDialogLayout.getLayoutParams();
        this.mScreenLandWidth = this.mDisplayMetrics.widthPixels;
        int fraction = (int) (this.mContext.getResources().getFraction(R.fraction.fingerprint_verification_width_percent_flip_cover, 1, 1) * displayMetrics.widthPixels);
        layoutParams.width = fraction;
        customizeSwitch(fraction, 0);
        this.mDialogLayout.setLayoutParams(layoutParams);
        showDefaultIcon();
        Button button = (Button) this.mDialogLayout.findViewById(R.id.button_positive_area_guide);
        this.mPositiveButtonGuide = button;
        button.setVisibility(8);
        this.mBottomButton.setOnClickListener(new BiometricCoverGuiHelper$$ExternalSyntheticLambda0(this, i));
        this.mMarginOfSingleNegativeButton = (int) this.mContext.getResources().getDimension(R.dimen.biometric_prompt_negative_bottom_cover_margin);
        this.mMarginOfMultiButton = (int) this.mContext.getResources().getDimension(R.dimen.biometric_prompt_bottom_cover_margin);
        setNegativeButtonMargin(this.mMarginOfSingleNegativeButton);
        this.mReTryButton.setOnClickListener(new BiometricCoverGuiHelper$$ExternalSyntheticLambda0(this, 3));
        this.mConfirmButton.setOnClickListener(new BiometricCoverGuiHelper$$ExternalSyntheticLambda0(this, 4));
        this.mBaseView.setFocusableInTouchMode(true);
        this.mBaseView.requestFocus();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    protected final boolean isScreenLandscape() {
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void onConfigurationChanged(Configuration configuration) {
        Log.d(this.TAG, "onConfigurationChanged() called with: newConfig = [" + configuration + "]");
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void showProgressSwitching(int i) {
        Log.d(this.TAG, "showProgressSwitching() called with: modality = [" + i + "]");
        LottieAnimationView lottieAnimationView = (LottieAnimationView) this.mDialogLayout.findViewById(R.id.id_prompt_switch_progress_left);
        LottieAnimationView lottieAnimationView2 = (LottieAnimationView) this.mDialogLayout.findViewById(R.id.id_prompt_switch_progress_right);
        lottieAnimationView2.setVisibility(4);
        lottieAnimationView.setVisibility(4);
        Button button = (Button) this.mDialogLayout.findViewById(R.id.button_switch_biometric_left);
        Button button2 = (Button) this.mDialogLayout.findViewById(R.id.button_switch_biometric_right);
        if (i == 2) {
            button.setText("");
            button.setEnabled(false);
            lottieAnimationView.setVisibility(0);
            lottieAnimationView.setRepeatCount(2);
            lottieAnimationView.playAnimation();
            return;
        }
        if (i == 8) {
            button2.setText("");
            button2.setEnabled(false);
            lottieAnimationView2.setVisibility(0);
            lottieAnimationView2.setRepeatCount(2);
            lottieAnimationView2.playAnimation();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void updateBiometricSwitch(int i) {
        LottieAnimationView lottieAnimationView = (LottieAnimationView) this.mDialogLayout.findViewById(R.id.id_prompt_switch_progress_left);
        ((LottieAnimationView) this.mDialogLayout.findViewById(R.id.id_prompt_switch_progress_right)).setVisibility(8);
        lottieAnimationView.setVisibility(8);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void show() {
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void onRotationInfoChanged(int i) {
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    protected final void setUpLeftMarginViewWidth(int i) {
    }
}
