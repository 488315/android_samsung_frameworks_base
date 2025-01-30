package com.android.server.policy;

import android.content.Context;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.policy.GlobalActionsProvider;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.policy.globalactions.presentation.view.SamsungGlobalActionsDialog;
import com.samsung.android.knox.custom.ProKioskManager;

/* loaded from: classes3.dex */
public class GlobalActions implements GlobalActionsProvider.GlobalActionsListener {
    public final Context mContext;
    public boolean mDeviceProvisioned;
    public boolean mGlobalActionsAvailable;
    public final GlobalActionsProvider mGlobalActionsProvider;
    public boolean mKeyguardShowing;
    public SamsungGlobalActionsDialog mSamsungGlobalActions;
    public final Runnable mShowTimeout = new Runnable() { // from class: com.android.server.policy.GlobalActions.1
        @Override // java.lang.Runnable
        public void run() {
            GlobalActions.this.ensureLegacyCreated();
            GlobalActions.m10045$$Nest$fgetmLegacyGlobalActions(GlobalActions.this);
            boolean unused = GlobalActions.this.mKeyguardShowing;
            boolean unused2 = GlobalActions.this.mDeviceProvisioned;
            throw null;
        }
    };
    public boolean mShowing;
    public final WindowManagerPolicy.WindowManagerFuncs mWindowManagerFuncs;

    /* renamed from: -$$Nest$fgetmLegacyGlobalActions, reason: not valid java name */
    public static /* bridge */ /* synthetic */ LegacyGlobalActions m10045$$Nest$fgetmLegacyGlobalActions(GlobalActions globalActions) {
        globalActions.getClass();
        return null;
    }

    @Override // com.android.server.policy.GlobalActionsProvider.GlobalActionsListener
    public void onGlobalActionsShown() {
    }

    public GlobalActions(Context context, WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs) {
        this.mContext = context;
        this.mWindowManagerFuncs = windowManagerFuncs;
        GlobalActionsProvider globalActionsProvider = (GlobalActionsProvider) LocalServices.getService(GlobalActionsProvider.class);
        this.mGlobalActionsProvider = globalActionsProvider;
        if (globalActionsProvider != null) {
            globalActionsProvider.setGlobalActionsListener(this);
        } else {
            Slog.i("GlobalActions", "No GlobalActionsProvider found, defaulting to LegacyGlobalActions");
        }
    }

    public final void ensureLegacyCreated() {
        if (this.mSamsungGlobalActions != null) {
            return;
        }
        this.mSamsungGlobalActions = new SamsungGlobalActionsDialog(this.mContext, this.mWindowManagerFuncs, new Runnable() { // from class: com.android.server.policy.GlobalActions$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GlobalActions.this.onGlobalActionsDismissed();
            }
        });
    }

    public void showDialog(boolean z, boolean z2, int i) {
        GlobalActionsProvider globalActionsProvider = this.mGlobalActionsProvider;
        if (globalActionsProvider == null || !globalActionsProvider.isGlobalActionsDisabled()) {
            this.mKeyguardShowing = z;
            this.mDeviceProvisioned = z2;
            this.mShowing = true;
            if (this.mGlobalActionsAvailable) {
                this.mGlobalActionsProvider.showGlobalActions(i);
            } else {
                ensureLegacyCreated();
                this.mSamsungGlobalActions.show(this.mKeyguardShowing, this.mDeviceProvisioned, true, i);
            }
        }
    }

    @Override // com.android.server.policy.GlobalActionsProvider.GlobalActionsListener
    public void onGlobalActionsDismissed() {
        this.mShowing = false;
        this.mSamsungGlobalActions = null;
    }

    @Override // com.android.server.policy.GlobalActionsProvider.GlobalActionsListener
    public void onGlobalActionsAvailableChanged(boolean z) {
        this.mGlobalActionsAvailable = z;
        if (!this.mShowing || z) {
            return;
        }
        ensureLegacyCreated();
        this.mSamsungGlobalActions.show(this.mKeyguardShowing, this.mDeviceProvisioned, true, -1);
    }

    public boolean isDialogShowing() {
        return this.mShowing;
    }

    public boolean isDialogPowerOptionHidden() {
        ProKioskManager proKioskManager = ProKioskManager.getInstance();
        if (proKioskManager != null && proKioskManager.getProKioskState()) {
            int powerDialogOptionMode = proKioskManager.getPowerDialogOptionMode();
            r0 = powerDialogOptionMode != 2;
            Slog.d("GlobalActions", "Knox Custom: getPowerDialogOptionMode() : " + powerDialogOptionMode + " returning : " + r0);
        }
        return r0;
    }
}
