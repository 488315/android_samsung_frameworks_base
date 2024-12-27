package com.samsung.android.biometrics.app.setting.prompt;

import android.content.Context;
import android.hardware.biometrics.PromptVerticalListContentView;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.biometrics.ISemBiometricSysUiCallback;
import com.samsung.android.biometrics.app.setting.AuthenticationConsumer;
import com.samsung.android.biometrics.app.setting.FocusableWindow$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.SysUiClient;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.fingerprint.FingerprintSensorInfo;
import com.samsung.android.biometrics.app.setting.fingerprint.UdfpsAuthSensorWindow;
import com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialListBoxWindow;
import com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialWindow;
import com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialWindow;
import com.samsung.android.biometrics.app.setting.prompt.knox.UCMUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public class BiometricPromptClient extends SysUiClient
        implements BiometricPromptCallback, AuthenticationConsumer {
    protected int mCurrentModality;
    public int mDismissedReason;
    protected final List mEligibleModalities;
    public FingerprintSensorInfo mFingerprintSensorInfo;
    public final PromptConfig mPromptConfig;
    public BiometricPromptWindow mPromptWindow;
    public IntConsumer mRemoveUdfpsClient;
    public UdfpsAuthSensorWindow mUdfpsAuthSensorWindow;

    public BiometricPromptClient(
            Context context,
            int i,
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback,
            Looper looper,
            Bundle bundle,
            String str,
            PromptConfig promptConfig) {
        super(context, i, iSemBiometricSysUiCallback, looper, bundle, str);
        this.mPromptConfig = promptConfig;
        promptConfig.mCallback = this;
        this.mCurrentModality = promptConfig.getPrimaryBiometricAuthenticator();
        ArrayList arrayList = new ArrayList(2);
        this.mEligibleModalities = arrayList;
        if (promptConfig.canUseFingerprint()) {
            arrayList.add(2);
        }
        if ((promptConfig.mAvailableBiometric & 8) != 0) {
            arrayList.add(8);
        }
    }

    public SysUiWindow createAuthCredentialWindow() {
        return createAuthCredentialWindow(false);
    }

    public BiometricPromptWindow createBiometricPromptWindow() {
        Context context = this.mContext;
        return new BiometricPromptWindow(
                context, this.mPromptConfig, context.getMainLooper(), this.mFingerprintSensorInfo);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void handleOnTaskStackListener(int i, int i2) {
        if (this.mPromptConfig.mPromptInfo.isAllowBackgroundAuthentication()) {
            return;
        }
        String str = this.mPackageName;
        boolean z = Utils.DEBUG;
        if ("android".contentEquals(str)) {
            return;
        }
        if (Utils.isDesktopMode(this.mContext)) {
            Log.i("BSS_BiometricPromptClient", "Keep Prompt: DeX");
        } else {
            if (Utils.isForegroundTask(this.mPackageName)) {
                return;
            }
            Log.w("BSS_BiometricPromptClient", "Evicting client due to: not in foreground");
            onUserCancel(5);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void handleTspBlock(boolean z) {
        UdfpsAuthSensorWindow udfpsAuthSensorWindow;
        if (this.mPromptConfig.canUseFingerprint()
                && (udfpsAuthSensorWindow = this.mUdfpsAuthSensorWindow) != null) {
            if (!z) {
                if (udfpsAuthSensorWindow.isVisible()) {
                    this.mUdfpsAuthSensorWindow.showSensorIcon();
                }
            } else {
                udfpsAuthSensorWindow.hideSensorIcon(0);
                String acquiredString =
                        FingerprintManager.getAcquiredString(this.mContext, 6, 1004);
                if (TextUtils.isEmpty(acquiredString)) {
                    return;
                }
                onAuthenticationHelp(1004, acquiredString);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00b1  */
    @Override // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onAuthenticationError(int r8, int r9, java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient.onAuthenticationError(int,"
                    + " int, java.lang.String):void");
    }

    @Override // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public void onAuthenticationFailed(String str) {
        this.mPromptWindow.mPromptGuiHelper.handleAuthenticationFailed();
    }

    @Override // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public void onAuthenticationHelp(int i, String str) {
        this.mPromptWindow.mPromptGuiHelper.handleAuthenticationHelp(i, str);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient,
              // com.samsung.android.biometrics.app.setting.AuthenticationConsumer
    public void onAuthenticationSucceeded(String str) {
        if (this.mCurrentModality == 2
                || !this.mPromptConfig.mPromptInfo.isConfirmationRequested()) {
            this.mDismissedReason = 4;
            stop();
            return;
        }
        BiometricPromptWindow biometricPromptWindow = this.mPromptWindow;
        if (biometricPromptWindow.mPromptConfig.mPromptInfo.isConfirmationRequested()) {
            if (biometricPromptWindow.mPromptConfig.mNumberOfAvailableBiometrics != 1) {
                biometricPromptWindow.mPromptGuiHelper.hideSwitch();
            }
            biometricPromptWindow.mPromptGuiHelper.handleAuthenticationSucceeded();
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback
    public void onDeviceCredentialPressed() {
        sendEvent(1002, 0);
        Iterator it = this.mWindows.iterator();
        while (it.hasNext()) {
            ((SysUiWindow) it.next()).removeView();
        }
        this.mWindows.clear();
        IntConsumer intConsumer = this.mRemoveUdfpsClient;
        if (intConsumer != null) {
            intConsumer.accept(this.mSessionId);
        }
        if (UCMUtils.isUCMKeyguardEnabled(this.mPromptConfig.mUserId)
                && UCMUtils.isSupportBiometricForUCM(this.mPromptConfig.mUserId)) {
            UCMAuthCredentialWindow uCMAuthCredentialWindow =
                    new UCMAuthCredentialWindow(this.mContext, this.mPromptConfig);
            this.mWindows.add(uCMAuthCredentialWindow);
            uCMAuthCredentialWindow.addView();
        } else {
            SysUiWindow createAuthCredentialWindow = createAuthCredentialWindow();
            this.mWindows.add(createAuthCredentialWindow);
            createAuthCredentialWindow.addView();
        }
    }

    public final void onDismissed(int i, byte[] bArr) {
        this.mDismissedReason = i;
        sendDismissedEvent(i, bArr);
        stop();
    }

    public final void onPromptError(int i) {
        try {
            ISemBiometricSysUiCallback iSemBiometricSysUiCallback = this.mSysUiCallback;
            if (iSemBiometricSysUiCallback != null) {
                iSemBiometricSysUiCallback.onError(this.mSessionId, 10, i);
            }
        } catch (Exception e) {
            FocusableWindow$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("sendErrorEventToBioService: "), "BSS_SysUiClient");
        }
        stop();
    }

    public final void onUserCancel(int i) {
        Log.i("BSS_BiometricPromptClient", "onUserCancel: " + i);
        this.mDismissedReason = 3;
        stop();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public final void prepareWindows() {
        UdfpsAuthSensorWindow udfpsAuthSensorWindow;
        if ((this.mPromptConfig.mPrimaryBiometric & 32768) != 0) {
            this.mWindows.add(createAuthCredentialWindow(true));
        } else {
            BiometricPromptWindow createBiometricPromptWindow = createBiometricPromptWindow();
            this.mPromptWindow = createBiometricPromptWindow;
            createBiometricPromptWindow.init();
            this.mWindows.add(this.mPromptWindow);
            FingerprintSensorInfo fingerprintSensorInfo = this.mFingerprintSensorInfo;
            if (fingerprintSensorInfo != null
                    && fingerprintSensorInfo.mIsAnyUdfps
                    && (udfpsAuthSensorWindow = this.mUdfpsAuthSensorWindow) != null) {
                this.mWindows.add(udfpsAuthSensorWindow);
                this.mUdfpsAuthSensorWindow.showSensorIcon();
            }
        }
        sendEvent(1004, 0);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiClient
    public void stop() {
        if (this.mPromptConfig.mPromptInfo.isForLegacyFingerprintManager()) {
            int i = this.mDismissedReason;
            if (i != 0 && i != 4) {
                sendDismissedEvent(10, null);
            }
        } else {
            if (this.mDismissedReason == 0) {
                this.mDismissedReason = 3;
            }
            sendDismissedEvent(this.mDismissedReason, null);
        }
        super.stop();
    }

    public SysUiWindow createAuthCredentialWindow(boolean z) {
        if (!z
                || !(this.mPromptConfig.mPromptInfo.getContentView()
                        instanceof PromptVerticalListContentView)) {
            return new AuthCredentialWindow(this.mContext, this.mPromptConfig);
        }
        Context context = this.mContext;
        AuthCredentialListBoxWindow authCredentialListBoxWindow =
                new AuthCredentialListBoxWindow(
                        context, this.mPromptConfig, context.getMainLooper(), null);
        authCredentialListBoxWindow.init();
        return authCredentialListBoxWindow;
    }
}
