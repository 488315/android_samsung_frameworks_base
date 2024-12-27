package com.android.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManagerInternal;
import android.os.PowerManager;
import android.util.Slog;
import com.android.internal.os.CachedDeviceState;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CachedDeviceStateService extends SystemService {
    public final AnonymousClass1 mBroadcastReceiver;
    public final CachedDeviceState mDeviceState;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.CachedDeviceStateService$1] */
    public CachedDeviceStateService(Context context) {
        super(context);
        this.mDeviceState = new CachedDeviceState();
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.CachedDeviceStateService.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                action.getClass();
                switch (action) {
                    case "android.intent.action.SCREEN_OFF":
                        CachedDeviceStateService.this.mDeviceState.setScreenInteractive(false);
                        break;
                    case "android.intent.action.BATTERY_CHANGED":
                        CachedDeviceStateService.this.mDeviceState.setCharging(intent.getIntExtra("plugged", 0) != 0);
                        break;
                    case "android.intent.action.SCREEN_ON":
                        CachedDeviceStateService.this.mDeviceState.setScreenInteractive(true);
                        break;
                }
            }
        };
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (500 == i) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.setPriority(1000);
            getContext().registerReceiver(this.mBroadcastReceiver, intentFilter);
            CachedDeviceState cachedDeviceState = this.mDeviceState;
            BatteryManagerInternal batteryManagerInternal = (BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class);
            boolean z = false;
            boolean z2 = true;
            if (batteryManagerInternal == null) {
                Slog.wtf("CachedDeviceStateService", "BatteryManager null while starting CachedDeviceStateService");
            } else if (batteryManagerInternal.getPlugType() == 0) {
                z2 = false;
            }
            cachedDeviceState.setCharging(z2);
            CachedDeviceState cachedDeviceState2 = this.mDeviceState;
            PowerManager powerManager = (PowerManager) getContext().getSystemService(PowerManager.class);
            if (powerManager == null) {
                Slog.wtf("CachedDeviceStateService", "PowerManager null while starting CachedDeviceStateService");
            } else {
                z = powerManager.isInteractive();
            }
            cachedDeviceState2.setScreenInteractive(z);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishLocalService(CachedDeviceState.Readonly.class, this.mDeviceState.getReadonlyClient());
    }
}
