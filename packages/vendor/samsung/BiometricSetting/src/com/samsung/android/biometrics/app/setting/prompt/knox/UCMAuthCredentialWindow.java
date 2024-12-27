package com.samsung.android.biometrics.app.setting.prompt.knox;

import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;

import com.samsung.android.biometrics.app.setting.FocusableWindow;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

public final class UCMAuthCredentialWindow extends FocusableWindow {
    public boolean mIsRegistered;
    public final PromptConfig mPromptConfig;
    public AnonymousClass1 mScreenOffBR;

    public UCMAuthCredentialWindow(Context context, PromptConfig promptConfig) {
        super(context);
        this.mIsRegistered = false;
        this.mPromptConfig = promptConfig;
        LayoutInflater from = LayoutInflater.from(context);
        View inflate =
                LayoutInflater.from(context)
                        .inflate(R.layout.biometric_prompt_auth_container_view, (ViewGroup) null);
        this.mBaseView = inflate;
        FrameLayout frameLayout =
                (FrameLayout) inflate.findViewById(R.id.id_prompt_auth_container_layout);
        int i = promptConfig.mCredentialType;
        if (i != 6) {
            throw new IllegalStateException(
                    SubMenuBuilder$$ExternalSyntheticOutline0.m(i, "Unknown credential type: "));
        }
        UCMAuthCredentialView uCMAuthCredentialView =
                (UCMAuthCredentialView)
                        from.inflate(
                                R.layout.sec_ucm_biometric_prompt_credential_password,
                                (ViewGroup) null,
                                false);
        uCMAuthCredentialView.setPromptConfig(promptConfig);
        if (frameLayout != null) {
            frameLayout.addView(uCMAuthCredentialView);
        }
        this.mBaseView.setOnKeyListener(uCMAuthCredentialView.mOnKeyListener);
        this.mBaseView.setFocusableInTouchMode(true);
        this.mBaseView.requestFocus();
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow,
              // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void addView() {
        super.addView();
        if (!this.mIsRegistered) {
            if (this.mScreenOffBR == null) {
                this.mScreenOffBR =
                        new BroadcastReceiver() { // from class:
                                                  // com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialWindow.1
                            @Override // android.content.BroadcastReceiver
                            public final void onReceive(Context context, Intent intent) {
                                if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                                    UCMAuthCredentialWindow uCMAuthCredentialWindow =
                                            UCMAuthCredentialWindow.this;
                                    if (uCMAuthCredentialWindow.mBaseView != null) {
                                        ((BiometricPromptClient)
                                                        uCMAuthCredentialWindow
                                                                .mPromptConfig
                                                                .mCallback)
                                                .onUserCancel(6);
                                    }
                                }
                            }
                        };
            }
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                this.mContext.registerReceiverAsUser(
                        this.mScreenOffBR, UserHandle.ALL, intentFilter, null, this.mH);
                this.mIsRegistered = true;
            } catch (Exception e) {
                Log.e("BSS_SysUiWindow.U", "registerBroadcastReceiver: " + e.getMessage());
            }
        }
        StatusBarManager statusBarManager =
                (StatusBarManager) this.mContext.getSystemService("statusbar");
        if (statusBarManager != null) {
            statusBarManager.disable(18874368);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(-1, -2, 2017, 16785410, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.dimAmount = 0.8f;
        layoutParams.softInputMode |= 16;
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_SysUiWindow.U";
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
        try {
            this.mContext.unregisterReceiver(this.mScreenOffBR);
            this.mIsRegistered = false;
        } catch (Exception e) {
            Log.e("BSS_SysUiWindow.U", "unregisterBroadcastReceiver: " + e.getMessage());
        }
        StatusBarManager statusBarManager =
                (StatusBarManager) this.mContext.getSystemService("statusbar");
        if (statusBarManager != null) {
            statusBarManager.disable(0);
        }
    }
}
