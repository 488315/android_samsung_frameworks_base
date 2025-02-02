package com.samsung.android.biometrics.app.setting.prompt;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.AuthenticationConsumer;
import com.samsung.android.biometrics.app.setting.SysUiClient;
import com.samsung.android.biometrics.app.setting.SysUiManager$$ExternalSyntheticLambda3;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.credential.AuthCredentialWindow;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthSensorWindow;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsInfo;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsPrivilegedAuthSensorWindow;
import com.samsung.android.biometrics.app.setting.knox.UCMAuthCredentialWindow;
import com.samsung.android.biometrics.app.setting.knox.UCMUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;

/* loaded from: classes.dex */
public class BiometricPromptClient extends SysUiClient implements BiometricPromptCallback, AuthenticationConsumer {

    @VisibleForTesting
    protected int mCurrentModality;
    private int mDismissedReason;

    @VisibleForTesting
    protected final List<Integer> mEligibleModalities;
    private final PromptConfig mPromptConfig;
    protected BiometricPromptWindow mPromptWindow;
    private IntConsumer mRemoveUdfpsClient;
    private UdfpsAuthSensorWindow mUdfpsAuthSensorWindow;
    private UdfpsInfo mUdfpsInfo;

    public BiometricPromptClient(Context context, int i, ISemBiometricSysUiCallback iSemBiometricSysUiCallback, Looper looper, Bundle bundle, String str, PromptConfig promptConfig) {
        super(context, i, iSemBiometricSysUiCallback, looper, bundle, str);
        this.mPromptConfig = promptConfig;
        promptConfig.setCallback(this);
        this.mCurrentModality = promptConfig.getPrimaryBiometricAuthenticator();
        ArrayList arrayList = new ArrayList(2);
        this.mEligibleModalities = arrayList;
        if (promptConfig.canUseFingerprint()) {
            arrayList.add(2);
        }
        if (promptConfig.canUseFace()) {
            arrayList.add(8);
        }
    }

    @VisibleForTesting
    protected SysUiWindow createAuthCredentialWindow() {
        return new AuthCredentialWindow(this.mContext, this.mPromptConfig);
    }

    @VisibleForTesting
    protected BiometricPromptWindow createBiometricPromptWindow() {
        Context context = this.mContext;
        return new BiometricPromptWindow(context, this.mPromptConfig, context.getMainLooper(), this.mUdfpsInfo);
    }

    public final PromptConfig getPromptConfig() {
        return this.mPromptConfig;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public int getUiType() {
        return 45064;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void handleOnTaskStackListener() {
        if (this.mPromptConfig.isAllowBackgroundAuthentication()) {
            return;
        }
        String packageName = getPackageName();
        boolean z = Utils.DEBUG;
        if ("android".contentEquals(packageName)) {
            return;
        }
        if (Utils.isDesktopMode(this.mContext)) {
            Log.i("BSS_BiometricPromptClient", "Keep Prompt: DeX");
        } else {
            if (Utils.isForegroundTask(getPackageName())) {
                return;
            }
            Log.w("BSS_BiometricPromptClient", "Evicting client due to: not in foreground");
            onUserCancel(5);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public final void onAuthenticationError(int i, int i2, String str) {
        boolean z = false;
        boolean z2 = i == 7 || i == 9;
        int i3 = this.mCurrentModality;
        if ((i == 100 || i == 3) || (i3 == 8 && i2 == 1006)) {
            this.mPromptWindow.showErrorMessage(i, i2, str);
        } else {
            this.mEligibleModalities.remove(Integer.valueOf(i3));
            if (!this.mEligibleModalities.isEmpty()) {
                this.mPromptWindow.showErrorMessage(i, i2, str);
            } else if (!this.mPromptConfig.isDeviceCredentialAllowed()) {
                z = true;
            } else if (z2) {
                onDeviceCredentialPressed();
            } else {
                this.mPromptWindow.showErrorMessage(i, i2, str);
            }
            if (this.mCurrentModality == 2) {
                UdfpsAuthSensorWindow udfpsAuthSensorWindow = this.mUdfpsAuthSensorWindow;
                if (udfpsAuthSensorWindow != null) {
                    udfpsAuthSensorWindow.hideSensorIcon();
                }
                IntConsumer intConsumer = this.mRemoveUdfpsClient;
                if (intConsumer != null) {
                    intConsumer.accept(getSessionId());
                }
            }
        }
        if (z) {
            this.mPromptWindow.showErrorMessage(i, i2, str);
            this.mHandler.postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricPromptClient.this.onDismissed(5, null);
                }
            }, 2000L);
        }
    }

    public void onAuthenticationFailed(String str) {
        this.mPromptWindow.handleAuthenticationFailed();
    }

    public void onAuthenticationHelp(int i, String str) {
        this.mPromptWindow.showHelpMessage(i, str);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient, com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public void onAuthenticationSucceeded(String str) {
        if (this.mCurrentModality != 2 && this.mPromptConfig.isConfirmationRequested()) {
            this.mPromptWindow.showConfirmOfAuthenticationSuccess();
        } else {
            this.mDismissedReason = 4;
            stop();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
    public final void onConfirmPressed() {
        this.mDismissedReason = 1;
        stop();
    }

    public void onDeviceCredentialPressed() {
        sendEvent(1002, 0);
        Iterator<SysUiWindow> it = this.mWindows.iterator();
        while (it.hasNext()) {
            it.next().removeView();
        }
        this.mWindows.clear();
        IntConsumer intConsumer = this.mRemoveUdfpsClient;
        if (intConsumer != null) {
            intConsumer.accept(getSessionId());
        }
        if (UCMUtils.isUCMKeyguardEnabled() && UCMUtils.isSupportBiometricForUCM()) {
            UCMAuthCredentialWindow uCMAuthCredentialWindow = new UCMAuthCredentialWindow(this.mContext, this.mPromptConfig);
            this.mWindows.add(uCMAuthCredentialWindow);
            uCMAuthCredentialWindow.addView();
        } else {
            SysUiWindow createAuthCredentialWindow = createAuthCredentialWindow();
            this.mWindows.add(createAuthCredentialWindow);
            createAuthCredentialWindow.addView();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
    public final void onDismissed(int i, byte[] bArr) {
        this.mDismissedReason = i;
        sendDismissedEvent(i, bArr);
        stop();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
    public final void onModalitySwitched(int i) {
        Log.d("BSS_BiometricPromptClient", "onModalitySwitched: " + this.mCurrentModality + " --> " + i);
        if (this.mCurrentModality == i) {
            return;
        }
        this.mCurrentModality = i;
        sendEvent(1001, i);
        UdfpsAuthSensorWindow udfpsAuthSensorWindow = this.mUdfpsAuthSensorWindow;
        if (udfpsAuthSensorWindow != null) {
            if (i == 2) {
                udfpsAuthSensorWindow.showSensorIcon();
            } else {
                udfpsAuthSensorWindow.hideSensorIcon();
            }
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
    public final void onNegativeButtonPressed() {
        this.mDismissedReason = 2;
        stop();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
    public final void onPromptError(int i) {
        sendErrorEventToBioService(i);
        stop();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
    public final void onSystemEvent() {
        sendEvent(1003, 1);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
    public final void onTryAgainPressed() {
        sendEvent(1001, 0);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
    public final void onUserCancel(int i) {
        Log.i("BSS_BiometricPromptClient", "onUserCancel: " + i);
        this.mDismissedReason = 3;
        stop();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void prepareWindows() {
        UdfpsAuthSensorWindow udfpsAuthSensorWindow;
        if (UCMUtils.isUCMKeyguardEnabled() && (!UCMUtils.isSupportBiometricForUCM() || !this.mPromptConfig.canUseFingerprint())) {
            Log.i("BSS_BiometricPromptClient", "prepareWindows: UCMKeyguardEnabled");
            this.mWindows.clear();
            this.mWindows.add(new UCMAuthCredentialWindow(this.mContext, this.mPromptConfig));
            return;
        }
        if (this.mPromptConfig.isCredentialPrimary()) {
            this.mWindows.add(createAuthCredentialWindow());
        } else {
            BiometricPromptWindow createBiometricPromptWindow = createBiometricPromptWindow();
            this.mPromptWindow = createBiometricPromptWindow;
            createBiometricPromptWindow.init();
            this.mWindows.add(this.mPromptWindow);
            if (this.mUdfpsInfo != null && (udfpsAuthSensorWindow = this.mUdfpsAuthSensorWindow) != null) {
                this.mWindows.add(udfpsAuthSensorWindow);
                this.mUdfpsAuthSensorWindow.showSensorIcon();
            }
        }
        sendEvent(1004, 0);
    }

    public final void setRemoveUdfpsClient(SysUiManager$$ExternalSyntheticLambda3 sysUiManager$$ExternalSyntheticLambda3) {
        this.mRemoveUdfpsClient = sysUiManager$$ExternalSyntheticLambda3;
    }

    public final void setUdfpsAuthSensorWindow(UdfpsPrivilegedAuthSensorWindow udfpsPrivilegedAuthSensorWindow) {
        this.mUdfpsAuthSensorWindow = udfpsPrivilegedAuthSensorWindow;
    }

    public final void setUdfpsSensorInfo(UdfpsInfo udfpsInfo) {
        this.mUdfpsInfo = udfpsInfo;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void stop() {
        if (this.mDismissedReason == 0) {
            this.mDismissedReason = 3;
        }
        sendDismissedEvent(this.mDismissedReason, null);
        super.stop();
    }
}
