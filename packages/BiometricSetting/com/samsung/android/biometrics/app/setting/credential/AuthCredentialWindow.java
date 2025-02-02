package com.samsung.android.biometrics.app.setting.credential;

import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$Injector$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.FocusableWindow;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* loaded from: classes.dex */
public class AuthCredentialWindow extends FocusableWindow {
    private AuthCredentialView mCredentialView;
    private final FrameLayout mFrameLayout;
    private final PromptConfig mPromptConfig;
    private BroadcastReceiver mScreenOffBR;

    public AuthCredentialWindow(Context context, PromptConfig promptConfig) {
        super(context);
        this.mPromptConfig = promptConfig;
        View inflate = getLayoutInflater().inflate(R.layout.biometric_prompt_auth_container_view, (ViewGroup) null);
        this.mBaseView = inflate;
        this.mFrameLayout = (FrameLayout) inflate.findViewById(R.id.id_prompt_auth_container_layout);
        setCredentialView();
        View view = this.mBaseView;
        if (view != null) {
            view.setOnKeyListener(this.mCredentialView);
        }
        this.mBaseView.setFocusableInTouchMode(true);
        this.mBaseView.requestFocus();
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004f A[Catch: Exception -> 0x0082, TryCatch #0 {Exception -> 0x0082, blocks: (B:3:0x0009, B:9:0x001b, B:10:0x002c, B:12:0x002d, B:14:0x004b, B:16:0x004f, B:17:0x0054, B:21:0x0070, B:23:0x0077, B:26:0x0035, B:28:0x003d), top: B:2:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x006e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0077 A[Catch: Exception -> 0x0082, TRY_LEAVE, TryCatch #0 {Exception -> 0x0082, blocks: (B:3:0x0009, B:9:0x001b, B:10:0x002c, B:12:0x002d, B:14:0x004b, B:16:0x004f, B:17:0x0054, B:21:0x0070, B:23:0x0077, B:26:0x0035, B:28:0x003d), top: B:2:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setCredentialView() {
        int passwordLayoutId;
        String password;
        int selection;
        boolean z;
        AuthCredentialView authCredentialView;
        Log.d("BSS_SysUiWindow.C", "setCredentialView");
        try {
            int credentialType = this.mPromptConfig.getCredentialType();
            if (credentialType != 1) {
                if (credentialType == 2) {
                    passwordLayoutId = getPatternLayoutId();
                    selection = 0;
                    z = false;
                    password = null;
                    authCredentialView = this.mCredentialView;
                    if (authCredentialView != null) {
                        this.mFrameLayout.removeView(authCredentialView);
                    }
                    AuthCredentialView authCredentialView2 = (AuthCredentialView) getLayoutInflater().inflate(passwordLayoutId, (ViewGroup) null, false);
                    this.mCredentialView = authCredentialView2;
                    authCredentialView2.setPromptConfig(this.mPromptConfig);
                    this.mFrameLayout.addView(this.mCredentialView);
                    if (credentialType != 1 || credentialType == 3) {
                        this.mCredentialView.setPasswordShown(z);
                        if (password != null) {
                            this.mCredentialView.setPassword(password);
                            this.mCredentialView.setSelection(selection);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (credentialType != 3) {
                    throw new IllegalStateException("Unknown credential type: " + credentialType);
                }
            }
            passwordLayoutId = getPasswordLayoutId();
            AuthCredentialView authCredentialView3 = this.mCredentialView;
            if (authCredentialView3 != null) {
                password = authCredentialView3.getPassword();
                selection = this.mCredentialView.getSelection();
                z = this.mCredentialView.mIsPasswordShown;
                authCredentialView = this.mCredentialView;
                if (authCredentialView != null) {
                }
                AuthCredentialView authCredentialView22 = (AuthCredentialView) getLayoutInflater().inflate(passwordLayoutId, (ViewGroup) null, false);
                this.mCredentialView = authCredentialView22;
                authCredentialView22.setPromptConfig(this.mPromptConfig);
                this.mFrameLayout.addView(this.mCredentialView);
                if (credentialType != 1) {
                }
                this.mCredentialView.setPasswordShown(z);
                if (password != null) {
                }
            }
            selection = 0;
            z = false;
            password = null;
            authCredentialView = this.mCredentialView;
            if (authCredentialView != null) {
            }
            AuthCredentialView authCredentialView222 = (AuthCredentialView) getLayoutInflater().inflate(passwordLayoutId, (ViewGroup) null, false);
            this.mCredentialView = authCredentialView222;
            authCredentialView222.setPromptConfig(this.mPromptConfig);
            this.mFrameLayout.addView(this.mCredentialView);
            if (credentialType != 1) {
            }
            this.mCredentialView.setPasswordShown(z);
            if (password != null) {
            }
        } catch (Exception e) {
            Log.w("BSS_SysUiWindow.C", "AuthCredentialWindow: " + e.getMessage(), e);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void addView() {
        super.addView();
        if (this.mScreenOffBR == null) {
            this.mScreenOffBR = new BroadcastReceiver() { // from class: com.samsung.android.biometrics.app.setting.credential.AuthCredentialWindow.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (!"android.intent.action.SCREEN_OFF".equals(intent.getAction()) || ((SysUiWindow) AuthCredentialWindow.this).mBaseView == null) {
                        return;
                    }
                    AuthCredentialWindow.this.mPromptConfig.getCallback().onUserCancel(6);
                }
            };
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                getContext().registerReceiverAsUser(this.mScreenOffBR, UserHandle.ALL, intentFilter, null, this.f22mH);
            } catch (Exception e) {
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("registerBroadcastReceiver: "), "BSS_SysUiWindow.C");
            }
        }
        StatusBarManager statusBarManager = getStatusBarManager();
        if (statusBarManager != null) {
            statusBarManager.disable(18874368);
        }
    }

    @VisibleForTesting
    protected AuthCredentialView getCredentialView() {
        return this.mCredentialView;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -2, 2017, !Utils.isTpaMode(getContext()) ? 16785410 : 16777218, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.dimAmount = 0.6f;
        layoutParams.softInputMode |= 16;
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_SysUiWindow.C";
    }

    protected int getPasswordLayoutId() {
        return Utils.isApplyingTabletGUI(getContext()) ? Utils.isScreenLandscape(getContext()) ? R.layout.biometric_prompt_credential_password_tablet_landscape : R.layout.biometric_prompt_credential_password_tablet : R.layout.biometric_prompt_credential_password;
    }

    protected int getPatternLayoutId() {
        return Utils.isApplyingTabletGUI(getContext()) ? Utils.isScreenLandscape(getContext()) ? R.layout.biometric_prompt_credential_pattern_tablet_landscape : R.layout.biometric_prompt_credential_pattern_tablet : R.layout.biometric_prompt_credential_pattern;
    }

    @VisibleForTesting
    protected StatusBarManager getStatusBarManager() {
        return (StatusBarManager) getContext().getSystemService("statusbar");
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onConfigurationChanged(Configuration configuration) {
        setCredentialView();
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    protected final void onFocusLost() {
        this.mPromptConfig.getCallback().onUserCancel(1);
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    protected final void onSystemDialogClosed() {
        this.mPromptConfig.getCallback().onUserCancel(4);
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void removeView() {
        super.removeView();
        if (this.mScreenOffBR != null) {
            try {
                getContext().unregisterReceiver(this.mScreenOffBR);
                this.mScreenOffBR = null;
            } catch (Exception e) {
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("unregisterBroadcastReceiver: "), "BSS_SysUiWindow.C");
            }
        }
        StatusBarManager statusBarManager = getStatusBarManager();
        if (statusBarManager != null) {
            statusBarManager.disable(0);
        }
    }
}
