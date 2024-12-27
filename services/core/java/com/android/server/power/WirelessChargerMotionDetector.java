package com.android.server.power;

import android.content.Context;
import android.hardware.scontext.SContext;
import android.hardware.scontext.SContextEvent;
import android.hardware.scontext.SContextListener;
import android.hardware.scontext.SContextManager;
import android.os.BatteryManagerInternal;
import android.os.SystemClock;

public final class WirelessChargerMotionDetector {
    public final BatteryManagerInternal mBatteryManagerInternal;
    public final PowerManagerService.AnonymousClass1 mCallbacks;
    public boolean mIsDeviceMoving;
    public boolean mIsWirelessChargerSContextRegistered;
    public final Object mLock;
    public final SContextManager mWirelessChargerSContextManager;
    public final AnonymousClass1 mWirelessChargerSContextListener =
            new SContextListener() { // from class:
                                     // com.android.server.power.WirelessChargerMotionDetector.1
                public final void onSContextChanged(SContextEvent sContextEvent) {
                    SContext sContext = sContextEvent.scontext;
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    if (sContext.getType() == 46) {
                        int action =
                                sContextEvent.getWirelessChargingDetectionContext().getAction();
                        if (action == 0) {
                            android.util.Slog.d(
                                    "WirelessChargerMotionDetector",
                                    "SContextListener: WIRELESS_CHARGING_DETECTION_NOMOVE");
                            WirelessChargerMotionDetector.this.mIsDeviceMoving = false;
                            return;
                        }
                        if (action != 1) {
                            return;
                        }
                        android.util.Slog.d(
                                "WirelessChargerMotionDetector",
                                "SContextListener: WIRELESS_CHARGING_DETECTION_MOVE");
                        WirelessChargerMotionDetector wirelessChargerMotionDetector =
                                WirelessChargerMotionDetector.this;
                        wirelessChargerMotionDetector.mIsDeviceMoving = true;
                        if (!wirelessChargerMotionDetector.mIsWirelessChargerSContextRegistered
                                || wirelessChargerMotionDetector.mBatteryManagerInternal.isPowered(
                                        4)) {
                            return;
                        }
                        WirelessChargerMotionDetector wirelessChargerMotionDetector2 =
                                WirelessChargerMotionDetector.this;
                        if (wirelessChargerMotionDetector2.mIsWirelessChargerSContextRegistered) {
                            wirelessChargerMotionDetector2.mWirelessChargerSContextManager
                                    .unregisterListener(
                                            wirelessChargerMotionDetector2
                                                    .mWirelessChargerSContextListener,
                                            46);
                            wirelessChargerMotionDetector2.mIsWirelessChargerSContextRegistered =
                                    false;
                        }
                        if (elapsedRealtime - WirelessChargerMotionDetector.this.mPassedWakeupTime
                                < 300) {
                            android.util.Slog.d(
                                    "WirelessChargerMotionDetector",
                                    "SContextListener: received move lately");
                            WirelessChargerMotionDetector wirelessChargerMotionDetector3 =
                                    WirelessChargerMotionDetector.this;
                            wirelessChargerMotionDetector3.mPassedWakeupTime = 0L;
                            PowerManagerService.AnonymousClass1 anonymousClass1 =
                                    wirelessChargerMotionDetector3.mCallbacks;
                            synchronized (PowerManagerService.this.mLock) {
                                PowerManagerService powerManagerService = PowerManagerService.this;
                                powerManagerService.mDirty |= 64;
                                powerManagerService.mScreenOnReason = " powered change";
                                powerManagerService.wakePowerGroupLocked(
                                        (PowerGroup) powerManagerService.mPowerGroups.get(0),
                                        SystemClock.uptimeMillis(),
                                        3,
                                        "android.server.power:POWER",
                                        1000,
                                        PowerManagerService.this.mContext.getOpPackageName(),
                                        1000,
                                        false);
                                PowerManagerService.this.updatePowerStateLocked();
                            }
                        }
                    }
                }
            };
    public long mPassedWakeupTime = 0;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.power.WirelessChargerMotionDetector$1] */
    public WirelessChargerMotionDetector(
            Object obj,
            Context context,
            BatteryManagerInternal batteryManagerInternal,
            PowerManagerService.AnonymousClass1 anonymousClass1) {
        this.mWirelessChargerSContextManager =
                (SContextManager) context.getSystemService("scontext");
        this.mLock = obj;
        this.mBatteryManagerInternal = batteryManagerInternal;
        this.mCallbacks = anonymousClass1;
    }
}
