package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class AuthCredentialListBoxGuiHelper extends BiometricPromptGuiHelper {
    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final String getDefaultDescriptionMessage() {
        return null;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void initPrompt() {
        super.initPrompt();
        TextView textView = this.mSubTitleTxtView;
        if (textView != null) {
            textView.setVisibility(8);
        }
        Button button = this.mBottomButton;
        if (button != null) {
            button.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialListBoxGuiHelper$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            AuthCredentialListBoxGuiHelper.this.mPromptConfig.mCallback
                                    .onDeviceCredentialPressed();
                        }
                    });
        }
        this.mBaseView.setFocusableInTouchMode(true);
        this.mBaseView.requestFocus();
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
        this.mBaseView.setVisibility(0);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void updateLayoutAsConfigurationChanged(int i) {
        int i2;
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) this.mDialogLayout.getLayoutParams();
        if (i == 2) {
            Log.d(this.TAG, "onConfigurationChanged:  LAND MODE");
            layoutParams.bottomMargin =
                    (int)
                            this.mContext
                                    .getResources()
                                    .getDimension(
                                            R.dimen
                                                    .biometric_prompt_verification_dialog_bottom_margin_land);
            int fraction =
                    (int)
                            (this.mContext
                                            .getResources()
                                            .getFraction(
                                                    R.fraction.face_verification_width_percent_land,
                                                    1,
                                                    1)
                                    * this.mDisplayMetrics.widthPixels);
            layoutParams.width = fraction;
            i2 = (this.mScreenLandWidth - fraction) / 2;
        } else {
            this.mScreenPortraitWidth = Utils.getDisplayWidthInPortraitMode(this.mContext);
            layoutParams.width =
                    (int)
                            (this.mContext
                                            .getResources()
                                            .getFraction(
                                                    R.fraction
                                                            .face_verification_width_percent_portrait,
                                                    1,
                                                    1)
                                    * this.mDisplayMetrics.widthPixels);
            layoutParams.bottomMargin = getBottomMarginForPortrait();
            i2 = (this.mScreenPortraitWidth - layoutParams.width) / 2;
        }
        layoutParams.gravity = 1;
        this.mDialogLayout.setLayoutParams(layoutParams);
        setUpLeftMarginViewWidth(i2);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationFailed() {}

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationSucceeded() {}

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationError(int i, int i2) {}

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper
    public final void handleAuthenticationHelp(int i, String str) {}
}
