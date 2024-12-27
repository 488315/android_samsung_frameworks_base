package com.samsung.android.biometrics.app.setting.prompt;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.samsung.android.biometrics.app.setting.BackgroundThread;
import com.samsung.android.biometrics.app.setting.FocusableWindow;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.Utils$$ExternalSyntheticLambda0;
import com.samsung.android.biometrics.app.setting.fingerprint.FingerprintSensorInfo;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthSensorWindow;

public class BiometricPromptWindow extends FocusableWindow
        implements BiometricPromptGuiHelper.OnModalityChangeListener {
    public int mCurrentBiometric;
    public final LinearLayout mDialog;
    public final FingerprintSensorInfo mFingerprintSensorInfo;
    public final PromptConfig mPromptConfig;
    public BiometricPromptGuiHelper mPromptGuiHelper;
    public final Handler mUIHandler;

    public BiometricPromptWindow(
            Context context,
            PromptConfig promptConfig,
            Looper looper,
            FingerprintSensorInfo fingerprintSensorInfo) {
        super(context);
        this.mUIHandler = new Handler(looper);
        this.mPromptConfig = promptConfig;
        this.mCurrentBiometric = promptConfig.getPrimaryBiometricAuthenticator();
        this.mFingerprintSensorInfo = fingerprintSensorInfo;
        try {
            View baseView = getBaseView();
            this.mBaseView = baseView;
            LinearLayout linearLayout = (LinearLayout) baseView.findViewById(R.id.id_prompt_dialog);
            this.mDialog = linearLayout;
            linearLayout.addView(
                    new View(
                            context) { // from class:
                                       // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptWindow.1
                        @Override // android.view.View
                        public final void onAttachedToWindow() {
                            super.onAttachedToWindow();
                            BiometricPromptWindow biometricPromptWindow =
                                    BiometricPromptWindow.this;
                            Configuration configuration =
                                    ((View) this).mContext.getResources().getConfiguration();
                            BiometricPromptGuiHelper biometricPromptGuiHelper =
                                    biometricPromptWindow.mPromptGuiHelper;
                            if (biometricPromptGuiHelper != null) {
                                biometricPromptGuiHelper.updateScrollViewHeight();
                                biometricPromptWindow.mPromptGuiHelper.onConfigurationChanged(
                                        configuration);
                                BiometricPromptGuiHelper biometricPromptGuiHelper2 =
                                        biometricPromptWindow.mPromptGuiHelper;
                                biometricPromptGuiHelper2
                                        .mDialogLayout
                                        .getViewTreeObserver()
                                        .addOnGlobalLayoutListener(
                                                new BiometricPromptGuiHelper.AnonymousClass3(
                                                        biometricPromptGuiHelper2, 1));
                            }
                        }

                        @Override // android.view.View
                        public final void onConfigurationChanged(Configuration configuration) {
                            super.onConfigurationChanged(configuration);
                            BiometricPromptWindow biometricPromptWindow =
                                    BiometricPromptWindow.this;
                            BiometricPromptGuiHelper biometricPromptGuiHelper =
                                    biometricPromptWindow.mPromptGuiHelper;
                            if (biometricPromptGuiHelper != null) {
                                biometricPromptGuiHelper.updateScrollViewHeight();
                                biometricPromptWindow.mPromptGuiHelper.onConfigurationChanged(
                                        configuration);
                                BiometricPromptGuiHelper biometricPromptGuiHelper2 =
                                        biometricPromptWindow.mPromptGuiHelper;
                                biometricPromptGuiHelper2
                                        .mDialogLayout
                                        .getViewTreeObserver()
                                        .addOnGlobalLayoutListener(
                                                new BiometricPromptGuiHelper.AnonymousClass3(
                                                        biometricPromptGuiHelper2, 1));
                            }
                        }
                    },
                    0,
                    0);
        } catch (Exception e) {
            Log.w("BSS_SysUiWindow.P", "BiometricPromptWindow: ", e);
            ((BiometricPromptClient) this.mPromptConfig.mCallback).onPromptError(-1);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow,
              // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void addView() {
        BiometricPromptGuiHelper biometricPromptGuiHelper = this.mPromptGuiHelper;
        if (biometricPromptGuiHelper != null) {
            biometricPromptGuiHelper.show();
        }
        super.addView();
    }

    public BiometricPromptGuiHelper createGuiHelper(int i) {
        FingerprintPromptGuiHelper fingerprintPromptGuiHelper;
        if (i == 2) {
            fingerprintPromptGuiHelper =
                    new FingerprintPromptGuiHelper(
                            this.mContext,
                            this.mUIHandler.getLooper(),
                            this.mBaseView,
                            this.mPromptConfig,
                            this.mFingerprintSensorInfo);
        } else {
            if (i != 8) {
                return null;
            }
            FacePromptGuiHelper facePromptGuiHelper =
                    new FacePromptGuiHelper(
                            this.mContext,
                            this.mBaseView,
                            this.mPromptConfig,
                            this.mFingerprintSensorInfo);
            facePromptGuiHelper.mTimePreviousHelpMessage = 0L;
            facePromptGuiHelper.mPunchHoleIcon = null;
            facePromptGuiHelper.mPunchHoleVIHelper = null;
            facePromptGuiHelper.mViViewLocation = null;
            facePromptGuiHelper.mSensorPrivacyManager = null;
            facePromptGuiHelper.TAG += ".Fa";
            fingerprintPromptGuiHelper = facePromptGuiHelper;
        }
        return fingerprintPromptGuiHelper;
    }

    public View getBaseView() {
        return LayoutInflater.from(this.mContext)
                .inflate(R.layout.biometric_prompt_generic_dialog, (ViewGroup) null);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        int i =
                !Utils.isTpaMode(this.mContext)
                        ? 17309954
                        : android.R.drawable.ab_solid_shadow_mtrl_alpha;
        if (this.mPromptConfig.isCheckToEnrollMode()) {
            i |= 128;
        }
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(-1, -1, 2618, i, -3);
        layoutParams.layoutInDisplayCutoutMode = 1;
        int integer = this.mContext.getResources().getInteger(R.integer.face_verification_bg_alpha);
        if (this.mPromptGuiHelper instanceof FingerprintPromptGuiHelper) {
            integer =
                    this.mContext
                            .getResources()
                            .getInteger(R.integer.fingerprint_verification_bg_alpha);
        }
        layoutParams.dimAmount = integer / 100.0f;
        layoutParams.privateFlags |= 16;
        layoutParams.setFitInsetsIgnoringVisibility(true);
        layoutParams.setFitInsetsTypes(
                layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_SysUiWindow.P";
    }

    public final void init() {
        BiometricPromptGuiHelper createGuiHelper = createGuiHelper(this.mCurrentBiometric);
        this.mPromptGuiHelper = createGuiHelper;
        if (createGuiHelper == null) {
            Log.w("BSS_SysUiWindow.P", "BiometricPromptWindow: Unknown Prompt Type");
            ((BiometricPromptClient) this.mPromptConfig.mCallback).onPromptError(-2);
            return;
        }
        if (Utils.Config.FEATURE_SUPPORT_DESKTOP_MODE) {
            Context context = this.mContext;
            String toastMessageInDex = createGuiHelper.getToastMessageInDex();
            BackgroundThread backgroundThread = BackgroundThread.get();
            Utils$$ExternalSyntheticLambda0 utils$$ExternalSyntheticLambda0 =
                    new Utils$$ExternalSyntheticLambda0(context, toastMessageInDex);
            backgroundThread.getClass();
            BackgroundThread.sHandler.post(utils$$ExternalSyntheticLambda0);
        }
        this.mPromptGuiHelper.initPrompt();
        this.mPromptGuiHelper.mOnModalityChangeListener = this;
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    public final void onFocusLost() {
        ((BiometricPromptClient) this.mPromptConfig.mCallback).onUserCancel(1);
    }

    public final void onModalitySwitched(int i) {
        Log.d(
                "BSS_SysUiWindow.P",
                "onModalitySwitched: oldModality = ["
                        + this.mCurrentBiometric
                        + "] -> newModality = ["
                        + i
                        + "]");
        if (i == this.mCurrentBiometric) {
            return;
        }
        Log.d(
                "BSS_SysUiWindow.P",
                "changePromptGUIHelper() called with: newModality = [" + i + "]");
        BiometricPromptGuiHelper biometricPromptGuiHelper = this.mPromptGuiHelper;
        if (biometricPromptGuiHelper != null) {
            biometricPromptGuiHelper.cleanUpPrompt();
            this.mPromptGuiHelper = null;
        }
        BiometricPromptClient biometricPromptClient =
                (BiometricPromptClient) this.mPromptConfig.mCallback;
        Log.d(
                "BSS_BiometricPromptClient",
                "onModalitySwitched: " + biometricPromptClient.mCurrentModality + " --> " + i);
        if (biometricPromptClient.mCurrentModality != i) {
            biometricPromptClient.mCurrentModality = i;
            biometricPromptClient.sendEvent(1001, i);
            UdfpsAuthSensorWindow udfpsAuthSensorWindow =
                    biometricPromptClient.mUdfpsAuthSensorWindow;
            if (udfpsAuthSensorWindow != null) {
                if (i == 2) {
                    udfpsAuthSensorWindow.showSensorIcon();
                } else {
                    udfpsAuthSensorWindow.hideSensorIcon(0);
                }
            }
        }
        this.mCurrentBiometric = i;
        BiometricPromptGuiHelper createGuiHelper = createGuiHelper(i);
        this.mPromptGuiHelper = createGuiHelper;
        createGuiHelper.initPrompt();
        BiometricPromptGuiHelper biometricPromptGuiHelper2 = this.mPromptGuiHelper;
        biometricPromptGuiHelper2.mOnModalityChangeListener = this;
        biometricPromptGuiHelper2.show();
        this.mUIHandler.sendEmptyMessageDelayed(1, 2000L);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onRotationInfoChanged(int i) {
        BiometricPromptGuiHelper biometricPromptGuiHelper = this.mPromptGuiHelper;
        if (biometricPromptGuiHelper != null) {
            biometricPromptGuiHelper.onRotationInfoChanged(i);
            this.mPromptGuiHelper.updateScrollViewHeight();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    public final void onSystemDialogClosed() {
        ((BiometricPromptClient) this.mPromptConfig.mCallback).onUserCancel(4);
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow,
              // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void removeView() {
        super.removeView();
        this.mUIHandler.removeCallbacksAndMessages(null);
        BiometricPromptGuiHelper biometricPromptGuiHelper = this.mPromptGuiHelper;
        if (biometricPromptGuiHelper != null) {
            biometricPromptGuiHelper.cleanUpPrompt();
        }
        LinearLayout linearLayout = this.mDialog;
        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onConfigurationChanged(Configuration configuration) {}
}
