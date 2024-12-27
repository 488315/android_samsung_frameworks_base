package com.android.server.desktopmode;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Binder;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeManager;

public final class EmergencyModeBlocker implements SemDesktopModeManager.DesktopModeBlocker {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public boolean mEmergencyModeEnabledInSettings;
    public final Injector mInjector;
    public boolean mLimitAppsAndHomeScreenEnabledInSettings;
    public final SemDesktopModeManager mManager;
    public boolean mMpsmEnabledInSettings;
    public final IStateManager mStateManager;
    public final EmergencyModeBlocker$$ExternalSyntheticLambda0 mUpdateRunnable =
            new Runnable() { // from class:
                             // com.android.server.desktopmode.EmergencyModeBlocker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EmergencyModeBlocker emergencyModeBlocker = EmergencyModeBlocker.this;
                    if (emergencyModeBlocker.mEnabledInBroadcast
                            || emergencyModeBlocker.mEmergencyModeEnabledInSettings
                            || emergencyModeBlocker.mMpsmEnabledInSettings
                            || emergencyModeBlocker.mLimitAppsAndHomeScreenEnabledInSettings) {
                        if (emergencyModeBlocker.mBlockerRegistered) {
                            return;
                        }
                        emergencyModeBlocker.mBlockerRegistered = true;
                        emergencyModeBlocker.mManager.registerBlocker(emergencyModeBlocker);
                        ((StateManager) emergencyModeBlocker.mStateManager)
                                .setEmergencyModeEnabled(true);
                        return;
                    }
                    if (emergencyModeBlocker.mBlockerRegistered) {
                        emergencyModeBlocker.mBlockerRegistered = false;
                        emergencyModeBlocker.mManager.unregisterBlocker(emergencyModeBlocker);
                        ((StateManager) emergencyModeBlocker.mStateManager)
                                .setEmergencyModeEnabled(false);
                    }
                }
            };
    public boolean mEnabledInBroadcast = false;
    public boolean mBlockerRegistered = false;
    public final Handler mHandler = new Handler();

    public final class BroadcastListener extends BroadcastReceiver {
        public BroadcastListener() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("reason", 0);
            if (DesktopModeFeature.DEBUG) {
                int i = EmergencyModeBlocker.$r8$clinit;
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        intExtra, "EMERGENCY_STATE_CHANGED reason=", "[DMS]EmergencyModeBlocker");
            }
            if (intExtra == 2 || intExtra == 3 || intExtra == 4) {
                EmergencyModeBlocker emergencyModeBlocker = EmergencyModeBlocker.this;
                emergencyModeBlocker.mEnabledInBroadcast = true;
                emergencyModeBlocker.scheduleUpdateBlockerRegistration(0);
            } else {
                if (intExtra != 5) {
                    return;
                }
                EmergencyModeBlocker emergencyModeBlocker2 = EmergencyModeBlocker.this;
                emergencyModeBlocker2.mEnabledInBroadcast = false;
                emergencyModeBlocker2.scheduleUpdateBlockerRegistration(10000);
            }
        }
    }

    public final class SettingsListener extends ContentObserver {
        public SettingsListener() {
            super(null);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            EmergencyModeBlocker emergencyModeBlocker = EmergencyModeBlocker.this;
            emergencyModeBlocker.mEmergencyModeEnabledInSettings =
                    emergencyModeBlocker.isEmergencyModeEnabledInSettings();
            EmergencyModeBlocker emergencyModeBlocker2 = EmergencyModeBlocker.this;
            emergencyModeBlocker2.mMpsmEnabledInSettings =
                    emergencyModeBlocker2.isMpsmEnabledInSettings();
            EmergencyModeBlocker emergencyModeBlocker3 = EmergencyModeBlocker.this;
            emergencyModeBlocker3.mLimitAppsAndHomeScreenEnabledInSettings =
                    emergencyModeBlocker3.isLimitAppsAndHomeScreenEnabledInSettings();
            if (DesktopModeFeature.DEBUG) {
                int i = EmergencyModeBlocker.$r8$clinit;
                Log.d(
                        "[DMS]EmergencyModeBlocker",
                        "emergency_mode="
                                + EmergencyModeBlocker.this.mEmergencyModeEnabledInSettings
                                + ", ultra_powersaving_mode="
                                + EmergencyModeBlocker.this.mMpsmEnabledInSettings
                                + ", minimal_battery_use="
                                + EmergencyModeBlocker.this
                                        .mLimitAppsAndHomeScreenEnabledInSettings);
            }
            EmergencyModeBlocker.this.scheduleUpdateBlockerRegistration(0);
        }
    }

    public EmergencyModeBlocker(
            Context context,
            IStateManager iStateManager,
            SemDesktopModeManager semDesktopModeManager,
            Injector injector) {
        this.mEmergencyModeEnabledInSettings = false;
        this.mMpsmEnabledInSettings = false;
        this.mLimitAppsAndHomeScreenEnabledInSettings = false;
        this.mContext = context;
        this.mStateManager = iStateManager;
        this.mInjector = injector;
        this.mEmergencyModeEnabledInSettings = isEmergencyModeEnabledInSettings();
        this.mMpsmEnabledInSettings = isMpsmEnabledInSettings();
        this.mLimitAppsAndHomeScreenEnabledInSettings = isLimitAppsAndHomeScreenEnabledInSettings();
        SettingsListener settingsListener = new SettingsListener();
        context.registerReceiverAsUser(
                new BroadcastListener(),
                UserHandle.ALL,
                new IntentFilter("com.samsung.intent.action.EMERGENCY_STATE_CHANGED"),
                null,
                null,
                2);
        context.getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("emergency_mode"), false, settingsListener, -1);
        context.getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("ultra_powersaving_mode"),
                        false,
                        settingsListener,
                        -1);
        context.getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("minimal_battery_use"),
                        false,
                        settingsListener,
                        -1);
        this.mManager = semDesktopModeManager;
        scheduleUpdateBlockerRegistration(0);
    }

    public final boolean isEmergencyModeEnabledInSettings() {
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z =
                Settings.System.getIntForUser(
                                this.mContext.getContentResolver(), "emergency_mode", 0, -2)
                        != 0;
        this.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    public final boolean isLimitAppsAndHomeScreenEnabledInSettings() {
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z =
                Settings.System.getIntForUser(
                                this.mContext.getContentResolver(), "minimal_battery_use", 0, -2)
                        != 0;
        this.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    public final boolean isMpsmEnabledInSettings() {
        this.mInjector.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z =
                Settings.System.getIntForUser(
                                this.mContext.getContentResolver(), "ultra_powersaving_mode", 0, -2)
                        != 0;
        this.mInjector.getClass();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    public final String onBlocked() {
        if (!this.mLimitAppsAndHomeScreenEnabledInSettings) {
            return this.mContext.getString(
                    this.mMpsmEnabledInSettings
                            ? R.string.httpErrorIO
                            : this.mEmergencyModeEnabledInSettings
                                    ? R.string.httpErrorAuth
                                    : R.string.imProtocolAim);
        }
        Context context = this.mContext;
        return context.getString(
                R.string.icu_abbrev_wday_month_day_no_year,
                context.getString(R.string.httpErrorFile));
    }

    public final void scheduleUpdateBlockerRegistration(int i) {
        this.mHandler.removeCallbacks(this.mUpdateRunnable);
        this.mHandler.postDelayed(this.mUpdateRunnable, i);
    }
}
