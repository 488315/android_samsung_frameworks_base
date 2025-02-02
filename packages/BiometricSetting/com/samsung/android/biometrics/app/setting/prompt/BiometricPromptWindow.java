package com.samsung.android.biometrics.app.setting.prompt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsInfo;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptGuiHelper;

/* loaded from: classes.dex */
public class BiometricPromptWindow extends FocusableWindow implements Handler.Callback, BiometricPromptGuiHelper.OnModalityChangeListener {
    private int mCurrentBiometric;
    private LinearLayout mDialog;
    private int mNextBiometric;
    protected final PromptConfig mPromptConfig;
    private BiometricPromptGuiHelper mPromptGuiHelper;
    protected final Handler mUIHandler;
    private final UdfpsInfo mUdfpsInfo;

    @SuppressLint({"InflateParams"})
    public BiometricPromptWindow(Context context, PromptConfig promptConfig, Looper looper, UdfpsInfo udfpsInfo) {
        super(context);
        this.mUIHandler = new Handler(looper, this);
        this.mPromptConfig = promptConfig;
        this.mCurrentBiometric = promptConfig.getPrimaryBiometricAuthenticator();
        this.mUdfpsInfo = udfpsInfo;
        try {
            View baseView = getBaseView();
            this.mBaseView = baseView;
            LinearLayout linearLayout = (LinearLayout) baseView.findViewById(R.id.id_prompt_dialog);
            this.mDialog = linearLayout;
            linearLayout.addView(new View(context) { // from class: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptWindow.1
                @Override // android.view.View
                protected final void onAttachedToWindow() {
                    super.onAttachedToWindow();
                    Configuration configuration = ((View) this).mContext.getResources().getConfiguration();
                    configuration.orientation = Utils.isScreenLandscape(((View) this).mContext) ? 2 : 1;
                    BiometricPromptWindow.this.handleConfigurationChanged(configuration);
                }

                @Override // android.view.View
                protected final void onConfigurationChanged(Configuration configuration) {
                    super.onConfigurationChanged(configuration);
                    BiometricPromptWindow.this.handleConfigurationChanged(configuration);
                }
            }, 0, 0);
        } catch (Exception e) {
            Log.w("BSS_SysUiWindow.P", "BiometricPromptWindow: ", e);
            this.mPromptConfig.getCallback().onPromptError(-1);
        }
    }

    private void changePromptGUIHelper(int i) {
        Log.d("BSS_SysUiWindow.P", "changePromptGUIHelper() called with: newModality = [" + i + "]");
        BiometricPromptGuiHelper biometricPromptGuiHelper = this.mPromptGuiHelper;
        if (biometricPromptGuiHelper != null) {
            biometricPromptGuiHelper.cleanUpPrompt();
            this.mPromptGuiHelper = null;
        }
        this.mPromptConfig.getCallback().onModalitySwitched(i);
        this.mCurrentBiometric = i;
        BiometricPromptGuiHelper createGuiHelper = createGuiHelper(i);
        this.mPromptGuiHelper = createGuiHelper;
        createGuiHelper.initPrompt();
        BiometricPromptGuiHelper biometricPromptGuiHelper2 = this.mPromptGuiHelper;
        biometricPromptGuiHelper2.mOnModalityChangeListener = this;
        biometricPromptGuiHelper2.show();
        this.mUIHandler.sendEmptyMessageDelayed(1, 2000L);
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void addView() {
        BiometricPromptGuiHelper biometricPromptGuiHelper = this.mPromptGuiHelper;
        if (biometricPromptGuiHelper != null) {
            biometricPromptGuiHelper.show();
        }
        super.addView();
    }

    protected BiometricPromptGuiHelper createGuiHelper(int i) {
        BiometricPromptGuiHelper fingerprintPromptGuiHelper;
        if (i == 2) {
            fingerprintPromptGuiHelper = new FingerprintPromptGuiHelper(getContext(), this.mUIHandler.getLooper(), this.mBaseView, this.mPromptConfig, this.mUdfpsInfo);
        } else {
            if (i != 8) {
                return null;
            }
            fingerprintPromptGuiHelper = new FacePromptGuiHelper(getContext(), this.mBaseView, this.mPromptConfig, this.mUdfpsInfo);
        }
        return fingerprintPromptGuiHelper;
    }

    @SuppressLint({"InflateParams"})
    protected View getBaseView() {
        return getLayoutInflater().inflate(R.layout.biometric_prompt_generic_dialog, (ViewGroup) null);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        int i = !Utils.isTpaMode(getContext()) ? 17309954 : android.R.drawable.ab_solid_shadow_mtrl_alpha;
        if (this.mPromptConfig.isCheckToEnrollMode()) {
            i |= 128;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2618, i, -3);
        layoutParams.layoutInDisplayCutoutMode = 1;
        int integer = getContext().getResources().getInteger(R.integer.face_verification_bg_alpha);
        if (this.mPromptGuiHelper instanceof FingerprintPromptGuiHelper) {
            integer = getContext().getResources().getInteger(R.integer.fingerprint_verification_bg_alpha);
        }
        layoutParams.dimAmount = integer / 100.0f;
        layoutParams.privateFlags |= 16;
        layoutParams.setFitInsetsIgnoringVisibility(true);
        layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_SysUiWindow.P";
    }

    public final void handleAuthenticationFailed() {
        this.mPromptGuiHelper.handleAuthenticationFailed();
    }

    final void handleConfigurationChanged(Configuration configuration) {
        BiometricPromptGuiHelper biometricPromptGuiHelper = this.mPromptGuiHelper;
        if (biometricPromptGuiHelper != null) {
            biometricPromptGuiHelper.onConfigurationChanged(configuration);
        }
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int i;
        BiometricPromptGuiHelper biometricPromptGuiHelper;
        Log.d("BSS_SysUiWindow.P", Utils.getLogFormat(message));
        if (message.what == 1 && (i = this.mNextBiometric) != 0 && (biometricPromptGuiHelper = this.mPromptGuiHelper) != null) {
            biometricPromptGuiHelper.updateBiometricSwitch(i);
            changePromptGUIHelper(this.mNextBiometric);
            this.mNextBiometric = 0;
        }
        return true;
    }

    public final void init() {
        BiometricPromptGuiHelper createGuiHelper = createGuiHelper(this.mCurrentBiometric);
        this.mPromptGuiHelper = createGuiHelper;
        if (createGuiHelper == null) {
            Log.w("BSS_SysUiWindow.P", "BiometricPromptWindow: Unknown Prompt Type");
            this.mPromptConfig.getCallback().onPromptError(-2);
            return;
        }
        if (Utils.Config.FEATURE_SUPPORT_DESKTOP_MODE) {
            Context context = getContext();
            String toastMessageInDex = this.mPromptGuiHelper.getToastMessageInDex();
            BackgroundThread backgroundThread = BackgroundThread.get();
            Utils$$ExternalSyntheticLambda0 utils$$ExternalSyntheticLambda0 = new Utils$$ExternalSyntheticLambda0(context, toastMessageInDex);
            backgroundThread.getClass();
            BackgroundThread.post(utils$$ExternalSyntheticLambda0);
        }
        this.mPromptGuiHelper.initPrompt();
        this.mPromptGuiHelper.mOnModalityChangeListener = this;
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    protected final void onFocusLost() {
        this.mPromptConfig.getCallback().onUserCancel(1);
    }

    public final void onModalitySwitched(int i) {
        Log.d("BSS_SysUiWindow.P", "onModalitySwitched: oldModality = [" + this.mCurrentBiometric + "] -> newModality = [" + i + "]");
        if (i == this.mCurrentBiometric) {
            return;
        }
        if (!this.mUIHandler.hasMessages(1)) {
            changePromptGUIHelper(i);
        } else {
            this.mPromptGuiHelper.showProgressSwitching(i);
            this.mNextBiometric = i;
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onRotationInfoChanged(int i) {
        BiometricPromptGuiHelper biometricPromptGuiHelper = this.mPromptGuiHelper;
        if (biometricPromptGuiHelper != null) {
            biometricPromptGuiHelper.onRotationInfoChanged(i);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    protected final void onSystemDialogClosed() {
        this.mPromptConfig.getCallback().onUserCancel(4);
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
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

    public final void showBiometricName(String str) {
        this.mPromptGuiHelper.showBiometricName(str);
    }

    public final void showConfirmOfAuthenticationSuccess() {
        if (this.mPromptConfig.isConfirmationRequested()) {
            if (!this.mPromptConfig.isSingleSensorMode()) {
                this.mPromptGuiHelper.hideSwitch();
                ((LinearLayout) this.mBaseView.findViewById(R.id.id_prompt_dialog_authenticate_complete_icon_layout)).setVisibility(0);
            }
            this.mPromptGuiHelper.handleAuthenticationSucceeded();
        }
    }

    public final void showErrorMessage(int i, int i2, String str) {
        this.mPromptGuiHelper.handleAuthenticationError(i, i2);
    }

    public final void showHelpMessage(int i, String str) {
        this.mPromptGuiHelper.handleAuthenticationHelp(i, str);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onConfigurationChanged(Configuration configuration) {
    }
}
