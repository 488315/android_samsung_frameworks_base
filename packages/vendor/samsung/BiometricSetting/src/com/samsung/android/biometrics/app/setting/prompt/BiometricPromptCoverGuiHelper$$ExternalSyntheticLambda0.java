package com.samsung.android.biometrics.app.setting.prompt;

import android.hardware.SensorPrivacyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.samsung.android.biometrics.app.setting.R;

public final /* synthetic */ class BiometricPromptCoverGuiHelper$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BiometricPromptCoverGuiHelper f$0;

    public /* synthetic */ BiometricPromptCoverGuiHelper$$ExternalSyntheticLambda0(
            BiometricPromptCoverGuiHelper biometricPromptCoverGuiHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = biometricPromptCoverGuiHelper;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        BiometricPromptCoverGuiHelper biometricPromptCoverGuiHelper = this.f$0;
        switch (i) {
            case 0:
                PromptConfig promptConfig = biometricPromptCoverGuiHelper.mPromptConfig;
                if (!promptConfig.isDeviceCredentialAllowed()) {
                    if (!(!TextUtils.isEmpty(promptConfig.mPromptInfo.getNegativeButtonText()))) {
                        ((BiometricPromptClient) promptConfig.mCallback).onUserCancel(3);
                        break;
                    } else {
                        BiometricPromptClient biometricPromptClient =
                                (BiometricPromptClient) promptConfig.mCallback;
                        biometricPromptClient.mDismissedReason = 2;
                        biometricPromptClient.stop();
                        break;
                    }
                } else if (!biometricPromptCoverGuiHelper.mIsAuthenticated) {
                    promptConfig.mCallback.onDeviceCredentialPressed();
                    break;
                } else {
                    ((BiometricPromptClient) promptConfig.mCallback).onUserCancel(3);
                    break;
                }
            case 1:
                if (biometricPromptCoverGuiHelper.mPurposeOfRetryButton == 2) {
                    SensorPrivacyManager sensorPrivacyManager =
                            (SensorPrivacyManager)
                                    biometricPromptCoverGuiHelper.mContext.getSystemService(
                                            "sensor_privacy");
                    if (sensorPrivacyManager != null) {
                        sensorPrivacyManager.setSensorPrivacy(2, 2, false);
                    }
                    biometricPromptCoverGuiHelper.mReTryButton.setText(
                            biometricPromptCoverGuiHelper.mContext.getString(
                                    R.string.biometric_prompt_postive_retry));
                    biometricPromptCoverGuiHelper.mPurposeOfRetryButton = 1;
                }
                biometricPromptCoverGuiHelper.showDefaultIcon();
                Button button = biometricPromptCoverGuiHelper.mReTryButton;
                button.setVisibility(4);
                button.setEnabled(false);
                biometricPromptCoverGuiHelper.mPositiveButtonGuide.setVisibility(8);
                biometricPromptCoverGuiHelper.setNegativeButtonMargin(
                        biometricPromptCoverGuiHelper.mMarginOfSingleNegativeButton);
                biometricPromptCoverGuiHelper.mDescriptionTxtView.setText(
                        biometricPromptCoverGuiHelper.mDescriptionText);
                ((BiometricPromptClient) biometricPromptCoverGuiHelper.mPromptConfig.mCallback)
                        .sendEvent(1001, 0);
                break;
            case 2:
                BiometricPromptClient biometricPromptClient2 =
                        (BiometricPromptClient)
                                biometricPromptCoverGuiHelper.mPromptConfig.mCallback;
                biometricPromptClient2.mDismissedReason = 1;
                biometricPromptClient2.stop();
                break;
            case 3:
                biometricPromptCoverGuiHelper.handleOnClickSwitchButton(2);
                break;
            default:
                biometricPromptCoverGuiHelper.handleOnClickSwitchButton(8);
                break;
        }
    }
}
