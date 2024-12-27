package com.android.server.wm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.PowerManager;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.provider.Settings;
import android.view.CrossWindowBlurListeners;
import android.view.TunnelModeEnabledListener;

import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;

public final class BlurController {
    public boolean mBlurDisabledSetting;
    public volatile boolean mBlurEnabled;
    public final Context mContext;
    public boolean mCriticalThermalStatus;
    public boolean mInPowerSaveMode;
    public final AnonymousClass1 mTunnelModeListener;
    public final RemoteCallbackList mBlurEnabledListeners = new RemoteCallbackList();
    public final Object mLock = new Object();
    public boolean mTunnelModeEnabled = false;

    public BlurController(Context context, final PowerManager powerManager) {
        TunnelModeEnabledListener tunnelModeEnabledListener =
                new TunnelModeEnabledListener(
                        new SystemServerInitThreadPool$$ExternalSyntheticLambda0()) { // from class:
                                                                                      // com.android.server.wm.BlurController.1
                    public final void onTunnelModeEnabledChanged(boolean z) {
                        BlurController blurController = BlurController.this;
                        blurController.mTunnelModeEnabled = z;
                        blurController.updateBlurEnabled();
                    }
                };
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        context.registerReceiverForAllUsers(
                new BroadcastReceiver() { // from class: com.android.server.wm.BlurController.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("android.os.action.POWER_SAVE_MODE_CHANGED"
                                .equals(intent.getAction())) {
                            BlurController.this.mInPowerSaveMode = powerManager.isPowerSaveMode();
                            BlurController.this.updateBlurEnabled();
                        }
                    }
                },
                intentFilter,
                null,
                null);
        this.mInPowerSaveMode = powerManager.isPowerSaveMode();
        context.getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("disable_window_blurs"),
                        false,
                        new ContentObserver() { // from class:
                                                // com.android.server.wm.BlurController.3
                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z) {
                                super.onChange(z);
                                BlurController blurController = BlurController.this;
                                blurController.mBlurDisabledSetting =
                                        Settings.Global.getInt(
                                                        blurController.mContext
                                                                .getContentResolver(),
                                                        "disable_window_blurs",
                                                        0)
                                                == 1;
                                BlurController.this.updateBlurEnabled();
                            }
                        });
        this.mBlurDisabledSetting =
                Settings.Global.getInt(context.getContentResolver(), "disable_window_blurs", 0)
                        == 1;
        powerManager.addThermalStatusListener(
                new PowerManager
                        .OnThermalStatusChangedListener() { // from class:
                                                            // com.android.server.wm.BlurController$$ExternalSyntheticLambda0
                    @Override // android.os.PowerManager.OnThermalStatusChangedListener
                    public final void onThermalStatusChanged(int i) {
                        BlurController blurController = BlurController.this;
                        blurController.getClass();
                        blurController.mCriticalThermalStatus = i >= 4;
                        blurController.updateBlurEnabled();
                    }
                });
        this.mCriticalThermalStatus = powerManager.getCurrentThermalStatus() >= 4;
        TunnelModeEnabledListener.register(tunnelModeEnabledListener);
        updateBlurEnabled();
    }

    public final void updateBlurEnabled() {
        synchronized (this.mLock) {
            try {
                boolean z =
                        (!CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED
                                        || this.mBlurDisabledSetting
                                        || this.mInPowerSaveMode
                                        || this.mTunnelModeEnabled
                                        || this.mCriticalThermalStatus)
                                ? false
                                : true;
                if (this.mBlurEnabled == z) {
                    return;
                }
                this.mBlurEnabled = z;
                int beginBroadcast = this.mBlurEnabledListeners.beginBroadcast();
                while (beginBroadcast > 0) {
                    beginBroadcast--;
                    try {
                        this.mBlurEnabledListeners
                                .getBroadcastItem(beginBroadcast)
                                .onCrossWindowBlurEnabledChanged(z);
                    } catch (RemoteException unused) {
                    }
                }
                this.mBlurEnabledListeners.finishBroadcast();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
