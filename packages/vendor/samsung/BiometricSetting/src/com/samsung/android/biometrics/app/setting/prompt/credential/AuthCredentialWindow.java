package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.samsung.android.biometrics.app.setting.FocusableWindow;
import com.samsung.android.biometrics.app.setting.FocusableWindow$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

public class AuthCredentialWindow extends FocusableWindow {
    public AuthCredentialView mCredentialView;
    public final FrameLayout mFrameLayout;
    public final PromptConfig mPromptConfig;
    public AnonymousClass1 mScreenOffBR;

    public AuthCredentialWindow(Context context, PromptConfig promptConfig) {
        super(context);
        this.mPromptConfig = promptConfig;
        View inflate =
                LayoutInflater.from(context)
                        .inflate(R.layout.biometric_prompt_auth_container_view, (ViewGroup) null);
        this.mBaseView = inflate;
        this.mFrameLayout =
                (FrameLayout) inflate.findViewById(R.id.id_prompt_auth_container_layout);
        setCredentialView();
        View view = this.mBaseView;
        if (view != null) {
            view.setOnKeyListener(this.mCredentialView);
        }
        this.mBaseView.setFocusableInTouchMode(true);
        this.mBaseView.requestFocus();
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow,
              // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void addView() {
        super.addView();
        if (this.mScreenOffBR == null) {
            this.mScreenOffBR =
                    new BroadcastReceiver() { // from class:
                                              // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialWindow.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                                AuthCredentialWindow authCredentialWindow =
                                        AuthCredentialWindow.this;
                                if (authCredentialWindow.mBaseView != null) {
                                    ((BiometricPromptClient)
                                                    authCredentialWindow.mPromptConfig.mCallback)
                                            .onUserCancel(6);
                                }
                            }
                        }
                    };
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                this.mContext.registerReceiverAsUser(
                        this.mScreenOffBR, UserHandle.ALL, intentFilter, null, this.mH);
            } catch (Exception e) {
                FocusableWindow$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("registerBroadcastReceiver: "), "BSS_SysUiWindow.C");
            }
        }
        StatusBarManager statusBarManager = getStatusBarManager();
        if (statusBarManager != null) {
            statusBarManager.disable(18874368);
        }
    }

    public AuthCredentialView getCredentialView() {
        return this.mCredentialView;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(
                        -1, -2, 2017, !Utils.isTpaMode(this.mContext) ? 16785410 : 16777218, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.dimAmount = 0.6f;
        layoutParams.softInputMode |= 16;
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_SysUiWindow.C";
    }

    public int getPasswordLayoutId() {
        return isApplyingTabletGUI()
                ? R.layout.biometric_prompt_credential_password_tablet
                : R.layout.biometric_prompt_credential_password;
    }

    public int getPatternLayoutId() {
        return isApplyingTabletGUI()
                ? Utils.isScreenLandscape(this.mContext)
                        ? R.layout.biometric_prompt_credential_pattern_tablet_landscape
                        : R.layout.biometric_prompt_credential_pattern_tablet
                : R.layout.biometric_prompt_credential_pattern;
    }

    public StatusBarManager getStatusBarManager() {
        return (StatusBarManager) this.mContext.getSystemService("statusbar");
    }

    public boolean isApplyingTabletGUI() {
        return this.mContext.getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onConfigurationChanged(Configuration configuration) {
        AuthCredentialView authCredentialView = this.mCredentialView;
        if (authCredentialView == null || !authCredentialView.isTwoPaneLandScape()) {
            return;
        }
        this.mFrameLayout.removeView(this.mCredentialView);
        setCredentialView();
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    public final void onFocusLost() {
        ((BiometricPromptClient) this.mPromptConfig.mCallback).onUserCancel(1);
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    public final void onSystemDialogClosed() {
        ((BiometricPromptClient) this.mPromptConfig.mCallback).onUserCancel(4);
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow,
              // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void removeView() {
        super.removeView();
        AnonymousClass1 anonymousClass1 = this.mScreenOffBR;
        if (anonymousClass1 != null) {
            try {
                this.mContext.unregisterReceiver(anonymousClass1);
                this.mScreenOffBR = null;
            } catch (Exception e) {
                FocusableWindow$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("unregisterBroadcastReceiver: "), "BSS_SysUiWindow.C");
            }
        }
        StatusBarManager statusBarManager = getStatusBarManager();
        if (statusBarManager != null) {
            statusBarManager.disable(0);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setCredentialView() {
        /*
            r11 = this;
            java.lang.String r0 = "Unknown credential type: "
            java.lang.String r1 = "setCredentialView"
            java.lang.String r2 = "BSS_SysUiWindow.C"
            android.util.Log.d(r2, r1)
            com.samsung.android.biometrics.app.setting.prompt.PromptConfig r1 = r11.mPromptConfig     // Catch: java.lang.Exception -> L2b
            int r1 = r1.mCredentialType     // Catch: java.lang.Exception -> L2b
            r3 = 3
            r4 = 1
            r5 = 0
            r6 = 0
            if (r1 == r4) goto L35
            r7 = 2
            if (r1 == r7) goto L2d
            if (r1 != r3) goto L19
            goto L35
        L19:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException     // Catch: java.lang.Exception -> L2b
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L2b
            r3.<init>(r0)     // Catch: java.lang.Exception -> L2b
            r3.append(r1)     // Catch: java.lang.Exception -> L2b
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Exception -> L2b
            r11.<init>(r0)     // Catch: java.lang.Exception -> L2b
            throw r11     // Catch: java.lang.Exception -> L2b
        L2b:
            r11 = move-exception
            goto L7b
        L2d:
            int r0 = r11.getPatternLayoutId()     // Catch: java.lang.Exception -> L2b
        L31:
            r8 = r5
            r9 = r8
            r7 = r6
            goto L4b
        L35:
            int r0 = r11.getPasswordLayoutId()     // Catch: java.lang.Exception -> L2b
            com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView r7 = r11.mCredentialView     // Catch: java.lang.Exception -> L2b
            if (r7 == 0) goto L31
            java.lang.String r7 = r7.getPassword()     // Catch: java.lang.Exception -> L2b
            com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView r8 = r11.mCredentialView     // Catch: java.lang.Exception -> L2b
            int r8 = r8.getSelection()     // Catch: java.lang.Exception -> L2b
            com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView r9 = r11.mCredentialView     // Catch: java.lang.Exception -> L2b
            boolean r9 = r9.mIsPasswordShown     // Catch: java.lang.Exception -> L2b
        L4b:
            android.content.Context r10 = r11.mContext     // Catch: java.lang.Exception -> L2b
            android.view.LayoutInflater r10 = android.view.LayoutInflater.from(r10)     // Catch: java.lang.Exception -> L2b
            android.view.View r0 = r10.inflate(r0, r6, r5)     // Catch: java.lang.Exception -> L2b
            com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView r0 = (com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView) r0     // Catch: java.lang.Exception -> L2b
            r11.mCredentialView = r0     // Catch: java.lang.Exception -> L2b
            com.samsung.android.biometrics.app.setting.prompt.PromptConfig r5 = r11.mPromptConfig     // Catch: java.lang.Exception -> L2b
            r0.setPromptConfig(r5)     // Catch: java.lang.Exception -> L2b
            android.widget.FrameLayout r0 = r11.mFrameLayout     // Catch: java.lang.Exception -> L2b
            com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView r5 = r11.mCredentialView     // Catch: java.lang.Exception -> L2b
            r0.addView(r5)     // Catch: java.lang.Exception -> L2b
            if (r1 == r4) goto L69
            if (r1 != r3) goto L90
        L69:
            com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView r0 = r11.mCredentialView     // Catch: java.lang.Exception -> L2b
            r0.setPasswordShown(r9)     // Catch: java.lang.Exception -> L2b
            if (r7 == 0) goto L90
            com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView r0 = r11.mCredentialView     // Catch: java.lang.Exception -> L2b
            r0.setPassword(r7)     // Catch: java.lang.Exception -> L2b
            com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView r11 = r11.mCredentialView     // Catch: java.lang.Exception -> L2b
            r11.setSelection(r8)     // Catch: java.lang.Exception -> L2b
            goto L90
        L7b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "AuthCredentialWindow: "
            r0.<init>(r1)
            java.lang.String r1 = r11.getMessage()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.w(r2, r0, r11)
        L90:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialWindow.setCredentialView():void");
    }

    public void setLockoutTimer(LockoutTimer lockoutTimer) {
        AuthCredentialView authCredentialView = this.mCredentialView;
        if (authCredentialView != null) {
            authCredentialView.setLockoutTimer(lockoutTimer);
        }
    }
}
