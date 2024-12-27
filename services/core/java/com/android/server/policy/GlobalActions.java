package com.android.server.policy;

import android.content.Context;
import android.os.Handler;
import android.util.Slog;

import com.android.server.LocalServices;
import com.android.server.policy.globalactions.presentation.view.SamsungGlobalActionsDialog;
import com.android.server.statusbar.StatusBarManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class GlobalActions {
    public final Context mContext;
    public boolean mDeviceProvisioned;
    public boolean mGlobalActionsAvailable;
    public final StatusBarManagerService.AnonymousClass3 mGlobalActionsProvider;
    public boolean mKeyguardShowing;
    public SamsungGlobalActionsDialog mSamsungGlobalActions;
    public boolean mShowing;
    public final WindowManagerPolicy.WindowManagerFuncs mWindowManagerFuncs;
    public final AnonymousClass1 mShowTimeout =
            new Runnable() { // from class: com.android.server.policy.GlobalActions.1
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalActions.this.ensureLegacyCreated();
                    GlobalActions.this.getClass();
                    GlobalActions globalActions = GlobalActions.this;
                    boolean z = globalActions.mKeyguardShowing;
                    boolean z2 = globalActions.mDeviceProvisioned;
                    throw null;
                }
            };
    public final Handler mHandler = new Handler();

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.policy.GlobalActions$1] */
    public GlobalActions(
            Context context, WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs) {
        this.mContext = context;
        this.mWindowManagerFuncs = windowManagerFuncs;
        StatusBarManagerService.AnonymousClass3 anonymousClass3 =
                (StatusBarManagerService.AnonymousClass3)
                        LocalServices.getService(StatusBarManagerService.AnonymousClass3.class);
        this.mGlobalActionsProvider = anonymousClass3;
        if (anonymousClass3 == null) {
            Slog.i(
                    "GlobalActions",
                    "No GlobalActionsProvider found, defaulting to LegacyGlobalActions");
            return;
        }
        StatusBarManagerService statusBarManagerService = StatusBarManagerService.this;
        statusBarManagerService.mGlobalActionListener = this;
        onGlobalActionsAvailableChanged(statusBarManagerService.mBar != null);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.server.policy.GlobalActions$$ExternalSyntheticLambda0] */
    public final void ensureLegacyCreated() {
        if (this.mSamsungGlobalActions != null) {
            return;
        }
        this.mSamsungGlobalActions =
                new SamsungGlobalActionsDialog(
                        this.mContext,
                        this.mWindowManagerFuncs,
                        new Runnable() { // from class:
                                         // com.android.server.policy.GlobalActions$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                GlobalActions.this.mShowing = false;
                            }
                        });
    }

    public final void onGlobalActionsAvailableChanged(boolean z) {
        this.mGlobalActionsAvailable = z;
        if (!this.mShowing || z) {
            return;
        }
        this.mHandler.removeCallbacks(this.mShowTimeout);
        ensureLegacyCreated();
        this.mSamsungGlobalActions.show(this.mKeyguardShowing, this.mDeviceProvisioned, true, -1);
    }
}
