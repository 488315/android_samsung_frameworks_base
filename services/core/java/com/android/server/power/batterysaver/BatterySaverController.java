package com.android.server.power.batterysaver;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManagerInternal;
import android.os.BatterySaverPolicyConfig;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;

import com.android.server.LocalServices;
import com.android.server.pm.PackageManagerService;
import com.android.server.power.Slog;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public final class BatterySaverController implements BatterySaverPolicy.BatterySaverPolicyListener {
    public boolean mAdaptiveEnabledRaw;
    public boolean mAdaptivePreviouslyEnabled;
    public final BatterySaverPolicy mBatterySaverPolicy;
    public final BatterySavingStats mBatterySavingStats;
    public final Context mContext;
    public boolean mFullEnabledRaw;
    public boolean mFullPreviouslyEnabled;
    public final MyHandler mHandler;
    public boolean mIsPluggedIn;
    public final Object mLock;
    public PowerManager mPowerManager;
    public Optional mPowerSaveModeChangedListenerPackage;
    public final ArrayList mListeners = new ArrayList();
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.power.batterysaver.BatterySaverController.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    boolean z;
                    z = true;
                    String action = intent.getAction();
                    action.getClass();
                    switch (action) {
                        case "android.intent.action.SCREEN_OFF":
                        case "android.intent.action.SCREEN_ON":
                            if (BatterySaverController.this.isPolicyEnabled()) {
                                BatterySaverController.this.mHandler.postStateChanged(5, false);
                                return;
                            } else {
                                BatterySaverController.this.updateBatterySavingStats();
                                return;
                            }
                        case "android.intent.action.BATTERY_CHANGED":
                            synchronized (BatterySaverController.this.mLock) {
                                BatterySaverController batterySaverController =
                                        BatterySaverController.this;
                                if (intent.getIntExtra("plugged", 0) == 0) {
                                    z = false;
                                }
                                batterySaverController.mIsPluggedIn = z;
                                break;
                            }
                        case "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED":
                        case "android.os.action.DEVICE_IDLE_MODE_CHANGED":
                            break;
                        default:
                            return;
                    }
                    BatterySaverController.this.updateBatterySavingStats();
                }
            };

    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void dispatchMessage(android.os.Message r20) {
            /*
                Method dump skipped, instructions count: 412
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.power.batterysaver.BatterySaverController.MyHandler.dispatchMessage(android.os.Message):void");
        }

        public final void postStateChanged(int i, boolean z) {
            obtainMessage(1, z ? 1 : 0, i).sendToTarget();
        }
    }

    public BatterySaverController(
            Object obj,
            Context context,
            Looper looper,
            BatterySaverPolicy batterySaverPolicy,
            BatterySavingStats batterySavingStats) {
        this.mLock = obj;
        this.mContext = context;
        this.mHandler = new MyHandler(looper);
        this.mBatterySaverPolicy = batterySaverPolicy;
        batterySaverPolicy.addListener(this);
        this.mBatterySavingStats = batterySavingStats;
        PowerManager.invalidatePowerSaveModeCaches();
    }

    public void enableBatterySaver(boolean z, int i) {
        Slog.d("BatterySaverController", "enableBatterySaver: " + z + "(" + i + ")");
        synchronized (this.mLock) {
            try {
                boolean z2 = this.mFullEnabledRaw;
                if (z2 == z) {
                    return;
                }
                if (z2 != z) {
                    PowerManager.invalidatePowerSaveModeCaches();
                    this.mFullEnabledRaw = z;
                }
                boolean z3 = this.mFullEnabledRaw;
                BatterySaverPolicy batterySaverPolicy = this.mBatterySaverPolicy;
                if (z3
                        ? batterySaverPolicy.setPolicyLevel(2)
                        : this.mAdaptiveEnabledRaw
                                ? batterySaverPolicy.setPolicyLevel(1)
                                : batterySaverPolicy.setPolicyLevel(0)) {
                    this.mHandler.postStateChanged(i, true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final BatterySaverPolicyConfig getPolicyLocked() {
        BatterySaverPolicy batterySaverPolicy = this.mBatterySaverPolicy;
        batterySaverPolicy.getClass();
        BatterySaverPolicy.Policy policy = batterySaverPolicy.mFullPolicy;
        policy.getClass();
        return new BatterySaverPolicyConfig.Builder()
                .setAdjustBrightnessFactor(policy.adjustBrightnessFactor)
                .setAdvertiseIsEnabled(policy.advertiseIsEnabled)
                .setDeferFullBackup(policy.deferFullBackup)
                .setDeferKeyValueBackup(policy.deferKeyValueBackup)
                .setDisableAnimation(policy.disableAnimation)
                .setDisableAod(policy.disableAod)
                .setDisableLaunchBoost(policy.disableLaunchBoost)
                .setDisableOptionalSensors(policy.disableOptionalSensors)
                .setDisableVibration(policy.disableVibration)
                .setEnableAdjustBrightness(policy.enableAdjustBrightness)
                .setEnableDataSaver(policy.enableDataSaver)
                .setEnableFirewall(policy.enableFirewall)
                .setEnableNightMode(policy.enableNightMode)
                .setEnableQuickDoze(policy.enableQuickDoze)
                .setForceAllAppsStandby(policy.forceAllAppsStandby)
                .setForceBackgroundCheck(policy.forceBackgroundCheck)
                .setLocationMode(policy.locationMode)
                .setSoundTriggerMode(policy.soundTriggerMode)
                .build();
    }

    public final Optional getPowerSaveModeChangedListenerPackage() {
        if (this.mPowerSaveModeChangedListenerPackage == null) {
            String string = this.mContext.getString(R.string.ext_media_new_notification_message);
            this.mPowerSaveModeChangedListenerPackage =
                    string.equals(
                                    PackageManagerService.ensureSystemPackageName(
                                            ((PackageManagerService.PackageManagerInternalImpl)
                                                            ((PackageManagerInternal)
                                                                    LocalServices.getService(
                                                                            PackageManagerInternal
                                                                                    .class)))
                                                    .mService.snapshotComputer(),
                                            string))
                            ? Optional.of(string)
                            : Optional.empty();
        }
        return this.mPowerSaveModeChangedListenerPackage;
    }

    public final boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z =
                        this.mFullEnabledRaw
                                || (this.mAdaptiveEnabledRaw
                                        && this.mBatterySaverPolicy.shouldAdvertiseIsEnabled());
            } finally {
            }
        }
        return z;
    }

    public final boolean isPolicyEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mFullEnabledRaw || this.mAdaptiveEnabledRaw;
        }
        return z;
    }

    public final boolean setAdaptivePolicyLocked(
            BatterySaverPolicyConfig batterySaverPolicyConfig) {
        BatterySaverPolicy.Policy fromConfig =
                BatterySaverPolicy.Policy.fromConfig(batterySaverPolicyConfig);
        BatterySaverPolicy batterySaverPolicy = this.mBatterySaverPolicy;
        if (fromConfig == null) {
            batterySaverPolicy.getClass();
            int i = Slog.$r8$clinit;
            android.util.Slog.wtf("BatterySaverPolicy", "setAdaptivePolicy given null policy");
        } else if (!batterySaverPolicy.mAdaptivePolicy.equals(fromConfig)) {
            batterySaverPolicy.mAdaptivePolicy = fromConfig;
            if (batterySaverPolicy.mPolicyLevel == 1) {
                batterySaverPolicy.updatePolicyDependenciesLocked();
                this.mHandler.postStateChanged(11, true);
                return true;
            }
        }
        return false;
    }

    public final boolean setFullPolicyLocked(BatterySaverPolicyConfig batterySaverPolicyConfig) {
        BatterySaverPolicy.Policy fromConfig =
                BatterySaverPolicy.Policy.fromConfig(batterySaverPolicyConfig);
        BatterySaverPolicy batterySaverPolicy = this.mBatterySaverPolicy;
        if (fromConfig == null) {
            batterySaverPolicy.getClass();
            int i = Slog.$r8$clinit;
            android.util.Slog.wtf("BatterySaverPolicy", "setFullPolicy given null policy");
        } else if (!batterySaverPolicy.mFullPolicy.equals(fromConfig)) {
            batterySaverPolicy.mFullPolicy = fromConfig;
            if (batterySaverPolicy.mPolicyLevel == 2) {
                batterySaverPolicy.updatePolicyDependenciesLocked();
                this.mHandler.postStateChanged(13, true);
                return true;
            }
        }
        return false;
    }

    public final void updateBatterySavingStats() {
        if (this.mPowerManager == null) {
            PowerManager powerManager =
                    (PowerManager) this.mContext.getSystemService(PowerManager.class);
            Objects.requireNonNull(powerManager);
            this.mPowerManager = powerManager;
        }
        PowerManager powerManager2 = this.mPowerManager;
        if (powerManager2 == null) {
            int i = Slog.$r8$clinit;
            android.util.Slog.wtf("BatterySaverController", "PowerManager not initialized");
            return;
        }
        boolean isInteractive = powerManager2.isInteractive();
        int i2 = 2;
        int i3 =
                powerManager2.isDeviceIdleMode()
                        ? 2
                        : powerManager2.isLightDeviceIdleMode() ? 1 : 0;
        synchronized (this.mLock) {
            BatterySavingStats batterySavingStats = this.mBatterySavingStats;
            if (this.mFullEnabledRaw) {
                i2 = 1;
            } else if (!this.mAdaptiveEnabledRaw) {
                i2 = 0;
            }
            boolean z = this.mIsPluggedIn;
            synchronized (batterySavingStats.mLock) {
                batterySavingStats.transitionStateLocked(
                        BatterySavingStats.statesToIndex(i2, isInteractive ? 1 : 0, i3, z ? 1 : 0));
            }
        }
    }
}
