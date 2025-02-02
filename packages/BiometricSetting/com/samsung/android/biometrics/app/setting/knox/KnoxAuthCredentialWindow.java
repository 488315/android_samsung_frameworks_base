package com.samsung.android.biometrics.app.setting.knox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$Injector$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.FocusableWindow;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* loaded from: classes.dex */
public final class KnoxAuthCredentialWindow extends FocusableWindow {
    private KnoxAuthCredentialView mCredentialView;
    private FrameLayout mFrameLayout;
    private final KnoxSysUiClientHelper mHelper;
    private final PromptConfig mPromptConfig;
    private BroadcastReceiver mScreenOffBR;

    /* JADX WARN: Removed duplicated region for block: B:18:0x007f A[Catch: Exception -> 0x0091, TryCatch #0 {Exception -> 0x0091, blocks: (B:3:0x0013, B:11:0x0035, B:12:0x003b, B:13:0x004c, B:15:0x004d, B:16:0x0064, B:18:0x007f, B:19:0x0086, B:21:0x0059), top: B:2:0x0013 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public KnoxAuthCredentialWindow(Context context, PromptConfig promptConfig, KnoxSysUiClientHelper knoxSysUiClientHelper) {
        super(context);
        View view;
        this.mPromptConfig = promptConfig;
        this.mHelper = knoxSysUiClientHelper;
        LayoutInflater layoutInflater = getLayoutInflater();
        try {
            View inflate = layoutInflater.inflate(R.layout.sec_knox_biometric_prompt_auth_container_view, (ViewGroup) null);
            this.mBaseView = inflate;
            this.mFrameLayout = (FrameLayout) inflate.findViewById(R.id.id_prompt_knox_auth_container_layout);
            int credentialType = promptConfig.getCredentialType();
            if (credentialType != 1) {
                if (credentialType == 2) {
                    this.mCredentialView = (KnoxAuthCredentialView) layoutInflater.inflate(R.layout.sec_knox_biometric_prompt_credential_pattern, (ViewGroup) null, false);
                    KnoxAuthCredentialView changeCredentialViewIfNeeded = knoxSysUiClientHelper.changeCredentialViewIfNeeded(this.mBaseView);
                    this.mCredentialView = changeCredentialViewIfNeeded;
                    changeCredentialViewIfNeeded.setPromptConfig(promptConfig);
                    this.mCredentialView.setKnoxClientHelper(knoxSysUiClientHelper);
                    this.mFrameLayout.addView(this.mCredentialView);
                    view = this.mBaseView;
                    if (view != null) {
                        view.setOnKeyListener(this.mCredentialView.mOnKeyListener);
                    }
                    this.mBaseView.setFocusableInTouchMode(true);
                    this.mBaseView.requestFocus();
                }
                if (credentialType != 3) {
                    if (credentialType != 6) {
                        throw new IllegalStateException("Unknown credential type: " + credentialType);
                    }
                    Log.i("BSS_SysUiWindow.K", "KnoxAuthCredentialView: UCMKeyguardEnabled");
                }
            }
            this.mCredentialView = (KnoxAuthCredentialView) layoutInflater.inflate(R.layout.sec_knox_biometric_prompt_credential_password, (ViewGroup) null, false);
            KnoxAuthCredentialView changeCredentialViewIfNeeded2 = knoxSysUiClientHelper.changeCredentialViewIfNeeded(this.mBaseView);
            this.mCredentialView = changeCredentialViewIfNeeded2;
            changeCredentialViewIfNeeded2.setPromptConfig(promptConfig);
            this.mCredentialView.setKnoxClientHelper(knoxSysUiClientHelper);
            this.mFrameLayout.addView(this.mCredentialView);
            view = this.mBaseView;
            if (view != null) {
            }
            this.mBaseView.setFocusableInTouchMode(true);
            this.mBaseView.requestFocus();
        } catch (Exception e) {
            Log.w("BSS_SysUiWindow.K", "KnoxAuthCredentialWindow: " + e.getMessage(), e);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void addView() {
        super.addView();
        if (this.mScreenOffBR == null) {
            this.mScreenOffBR = new BroadcastReceiver() { // from class: com.samsung.android.biometrics.app.setting.knox.KnoxAuthCredentialWindow.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (!"android.intent.action.SCREEN_OFF".equals(intent.getAction()) || ((SysUiWindow) KnoxAuthCredentialWindow.this).mBaseView == null) {
                        return;
                    }
                    KnoxAuthCredentialWindow.this.mPromptConfig.getCallback().onUserCancel(6);
                }
            };
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                getContext().registerReceiverAsUser(this.mScreenOffBR, UserHandle.ALL, intentFilter, null, this.f22mH);
            } catch (Exception e) {
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("registerBroadcastReceiver: "), "BSS_SysUiWindow.K");
            }
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2017, 16785408, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
        this.mHelper.modifyLayoutParamsIfNeeded(layoutParams);
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow, com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_SysUiWindow.K";
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void onConfigurationChanged(Configuration configuration) {
        if (!this.mPromptConfig.isKnoxProfile()) {
            KnoxAuthCredentialView knoxAuthCredentialView = this.mCredentialView;
            if (knoxAuthCredentialView != null) {
                knoxAuthCredentialView.onConfigurationChanged(configuration);
                return;
            }
            return;
        }
        this.mFrameLayout.removeView(this.mCredentialView);
        KnoxAuthCredentialView changeCredentialViewIfNeeded = this.mHelper.changeCredentialViewIfNeeded(this.mBaseView);
        this.mCredentialView = changeCredentialViewIfNeeded;
        changeCredentialViewIfNeeded.setPromptConfig(this.mPromptConfig);
        this.mCredentialView.setKnoxClientHelper(this.mHelper);
        this.mFrameLayout.addView(this.mCredentialView);
        this.mHelper.onConfigurationChanged();
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    protected final void onFocusLost() {
        if (this.mHelper.isForgotbtnDialogShowing()) {
            return;
        }
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
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("unregisterBroadcastReceiver: "), "BSS_SysUiWindow.K");
            }
        }
    }
}
