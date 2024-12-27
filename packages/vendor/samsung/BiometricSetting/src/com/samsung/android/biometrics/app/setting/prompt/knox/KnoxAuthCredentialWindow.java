package com.samsung.android.biometrics.app.setting.prompt.knox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.UserHandle;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.samsung.android.biometrics.app.setting.FocusableWindow;
import com.samsung.android.biometrics.app.setting.FocusableWindow$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

public final class KnoxAuthCredentialWindow extends FocusableWindow {
    public KnoxAuthCredentialView mCredentialView;
    public final FrameLayout mFrameLayout;
    public final KnoxSysUiClientHelper mHelper;
    public final PromptConfig mPromptConfig;
    public AnonymousClass1 mScreenOffBR;

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public KnoxAuthCredentialWindow(
            android.content.Context r9,
            com.samsung.android.biometrics.app.setting.prompt.PromptConfig r10,
            com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper r11) {
        /*
            r8 = this;
            java.lang.String r0 = "BSS_SysUiWindow.K"
            java.lang.String r1 = "Unknown credential type: "
            r8.<init>(r9)
            r8.mPromptConfig = r10
            r8.mHelper = r11
            android.view.LayoutInflater r9 = android.view.LayoutInflater.from(r9)
            r2 = 2131558460(0x7f0d003c, float:1.8742236E38)
            r3 = 0
            android.view.View r2 = r9.inflate(r2, r3)     // Catch: java.lang.Exception -> L39
            r8.mBaseView = r2     // Catch: java.lang.Exception -> L39
            r4 = 2131362052(0x7f0a0104, float:1.8343874E38)
            android.view.View r2 = r2.findViewById(r4)     // Catch: java.lang.Exception -> L39
            android.widget.FrameLayout r2 = (android.widget.FrameLayout) r2     // Catch: java.lang.Exception -> L39
            r8.mFrameLayout = r2     // Catch: java.lang.Exception -> L39
            int r4 = r10.mCredentialType     // Catch: java.lang.Exception -> L39
            r5 = 0
            r6 = 1
            if (r4 == r6) goto L59
            r7 = 2
            if (r4 == r7) goto L4d
            r7 = 3
            if (r4 == r7) goto L59
            r7 = 6
            if (r4 != r7) goto L3b
            java.lang.String r1 = "KnoxAuthCredentialView: UCMKeyguardEnabled"
            android.util.Log.i(r0, r1)     // Catch: java.lang.Exception -> L39
            goto L59
        L39:
            r8 = move-exception
            goto L8f
        L3b:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException     // Catch: java.lang.Exception -> L39
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L39
            r9.<init>(r1)     // Catch: java.lang.Exception -> L39
            r9.append(r4)     // Catch: java.lang.Exception -> L39
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Exception -> L39
            r8.<init>(r9)     // Catch: java.lang.Exception -> L39
            throw r8     // Catch: java.lang.Exception -> L39
        L4d:
            r1 = 2131558462(0x7f0d003e, float:1.874224E38)
            android.view.View r9 = r9.inflate(r1, r3, r5)     // Catch: java.lang.Exception -> L39
            com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView r9 = (com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView) r9     // Catch: java.lang.Exception -> L39
            r8.mCredentialView = r9     // Catch: java.lang.Exception -> L39
            goto L64
        L59:
            r1 = 2131558461(0x7f0d003d, float:1.8742238E38)
            android.view.View r9 = r9.inflate(r1, r3, r5)     // Catch: java.lang.Exception -> L39
            com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView r9 = (com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView) r9     // Catch: java.lang.Exception -> L39
            r8.mCredentialView = r9     // Catch: java.lang.Exception -> L39
        L64:
            android.view.View r9 = r8.mBaseView     // Catch: java.lang.Exception -> L39
            com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView r9 = r11.changeCredentialViewIfNeeded(r9)     // Catch: java.lang.Exception -> L39
            r8.mCredentialView = r9     // Catch: java.lang.Exception -> L39
            r9.setPromptConfig(r10)     // Catch: java.lang.Exception -> L39
            com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView r9 = r8.mCredentialView     // Catch: java.lang.Exception -> L39
            r9.setKnoxClientHelper(r11)     // Catch: java.lang.Exception -> L39
            com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView r9 = r8.mCredentialView     // Catch: java.lang.Exception -> L39
            r2.addView(r9)     // Catch: java.lang.Exception -> L39
            android.view.View r9 = r8.mBaseView     // Catch: java.lang.Exception -> L39
            if (r9 == 0) goto L84
            com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView r10 = r8.mCredentialView     // Catch: java.lang.Exception -> L39
            com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView$1 r10 = r10.mOnKeyListener     // Catch: java.lang.Exception -> L39
            r9.setOnKeyListener(r10)     // Catch: java.lang.Exception -> L39
        L84:
            android.view.View r9 = r8.mBaseView     // Catch: java.lang.Exception -> L39
            r9.setFocusableInTouchMode(r6)     // Catch: java.lang.Exception -> L39
            android.view.View r8 = r8.mBaseView     // Catch: java.lang.Exception -> L39
            r8.requestFocus()     // Catch: java.lang.Exception -> L39
            goto La4
        L8f:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "KnoxAuthCredentialWindow: "
            r9.<init>(r10)
            java.lang.String r10 = r8.getMessage()
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.w(r0, r9, r8)
        La4:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialWindow.<init>(android.content.Context,"
                    + " com.samsung.android.biometrics.app.setting.prompt.PromptConfig,"
                    + " com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper):void");
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow,
              // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void addView() {
        super.addView();
        if (this.mScreenOffBR == null) {
            this.mScreenOffBR =
                    new BroadcastReceiver() { // from class:
                                              // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialWindow.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                                KnoxAuthCredentialWindow knoxAuthCredentialWindow =
                                        KnoxAuthCredentialWindow.this;
                                if (knoxAuthCredentialWindow.mBaseView != null) {
                                    ((BiometricPromptClient)
                                                    knoxAuthCredentialWindow
                                                            .mPromptConfig
                                                            .mCallback)
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
                        e, new StringBuilder("registerBroadcastReceiver: "), "BSS_SysUiWindow.K");
            }
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(-1, -1, 2017, 16785408, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.setFitInsetsTypes(
                layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
        this.mHelper.modifyLayoutParamsIfNeeded(layoutParams);
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
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
        KnoxAuthCredentialView changeCredentialViewIfNeeded =
                this.mHelper.changeCredentialViewIfNeeded(this.mBaseView);
        this.mCredentialView = changeCredentialViewIfNeeded;
        changeCredentialViewIfNeeded.setPromptConfig(this.mPromptConfig);
        this.mCredentialView.setKnoxClientHelper(this.mHelper);
        this.mFrameLayout.addView(this.mCredentialView);
        this.mHelper.onConfigurationChanged();
    }

    @Override // com.samsung.android.biometrics.app.setting.FocusableWindow
    public final void onFocusLost() {
        if (this.mHelper.isForgotbtnDialogShowing()) {
            return;
        }
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
                        e, new StringBuilder("unregisterBroadcastReceiver: "), "BSS_SysUiWindow.K");
            }
        }
    }
}
