package com.android.server.desktopmode;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeManager;

/* loaded from: classes2.dex */
public class EmergencyModeBlocker implements SemDesktopModeManager.DesktopModeBlocker {
  public static final String TAG = "[DMS]" + EmergencyModeBlocker.class.getSimpleName();
  public final Context mContext;
  public boolean mEmergencyModeEnabledInSettings;
  public final Injector mInjector;
  public boolean mLimitAppsAndHomeScreenEnabledInSettings;
  public final SemDesktopModeManager mManager;
  public boolean mMpsmEnabledInSettings;
  public final SettingsListener mSettingListener;
  public final IStateManager mStateManager;
  public final Runnable mUpdateRunnable = new Runnable() { // from class:
        // com.android.server.desktopmode.EmergencyModeBlocker$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
          EmergencyModeBlocker.this.updateBlockerRegistration();
        }
      };
  public boolean mEnabledInBroadcast = false;
  public boolean mBlockerRegistered = false;
  public final Handler mHandler = new Handler();

  public class BroadcastListener extends BroadcastReceiver {
    public BroadcastListener() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      int intExtra = intent.getIntExtra("reason", 0);
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(EmergencyModeBlocker.TAG, "EMERGENCY_STATE_CHANGED reason=" + intExtra);
      }
      if (intExtra == 2 || intExtra == 3 || intExtra == 4) {
        EmergencyModeBlocker.this.mEnabledInBroadcast = true;
        EmergencyModeBlocker.this.scheduleUpdateBlockerRegistration();
      } else {
        if (intExtra != 5) {
          return;
        }
        EmergencyModeBlocker.this.mEnabledInBroadcast = false;
        EmergencyModeBlocker.this.scheduleUpdateBlockerRegistration(10000);
      }
    }
  }

  public class SettingsListener extends ContentObserver {
    public SettingsListener() {
      super(null);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
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
        Log.m7d(
            EmergencyModeBlocker.TAG,
            "emergency_mode="
                + EmergencyModeBlocker.this.mEmergencyModeEnabledInSettings
                + ", ultra_powersaving_mode="
                + EmergencyModeBlocker.this.mMpsmEnabledInSettings
                + ", minimal_battery_use="
                + EmergencyModeBlocker.this.mLimitAppsAndHomeScreenEnabledInSettings);
      }
      EmergencyModeBlocker.this.scheduleUpdateBlockerRegistration();
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
    this.mSettingListener = settingsListener;
    context.registerReceiverAsUser(
        new BroadcastListener(),
        UserHandle.ALL,
        new IntentFilter("com.samsung.intent.action.EMERGENCY_STATE_CHANGED"),
        null,
        null);
    context
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("emergency_mode"), false, settingsListener, -1);
    context
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("ultra_powersaving_mode"), false, settingsListener, -1);
    context
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("minimal_battery_use"), false, settingsListener, -1);
    this.mManager = semDesktopModeManager;
    scheduleUpdateBlockerRegistration();
  }

  public final void scheduleUpdateBlockerRegistration() {
    scheduleUpdateBlockerRegistration(0);
  }

  public final void scheduleUpdateBlockerRegistration(int i) {
    this.mHandler.removeCallbacks(this.mUpdateRunnable);
    this.mHandler.postDelayed(this.mUpdateRunnable, i);
  }

  public final void updateBlockerRegistration() {
    if (shouldBlockDesktopMode()) {
      if (this.mBlockerRegistered) {
        return;
      }
      this.mBlockerRegistered = true;
      this.mManager.registerBlocker(this);
      this.mStateManager.setEmergencyModeEnabled(true);
      return;
    }
    if (this.mBlockerRegistered) {
      this.mBlockerRegistered = false;
      this.mManager.unregisterBlocker(this);
      this.mStateManager.setEmergencyModeEnabled(false);
    }
  }

  public final boolean isEmergencyModeEnabledInSettings() {
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    boolean z =
        Settings.System.getIntForUser(this.mContext.getContentResolver(), "emergency_mode", 0, -2)
            != 0;
    this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
    return z;
  }

  public final boolean isMpsmEnabledInSettings() {
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    boolean z =
        Settings.System.getIntForUser(
                this.mContext.getContentResolver(), "ultra_powersaving_mode", 0, -2)
            != 0;
    this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
    return z;
  }

  public final boolean isLimitAppsAndHomeScreenEnabledInSettings() {
    long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
    boolean z =
        Settings.System.getIntForUser(
                this.mContext.getContentResolver(), "minimal_battery_use", 0, -2)
            != 0;
    this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
    return z;
  }

  public boolean shouldBlockDesktopMode() {
    return this.mEnabledInBroadcast
        || this.mEmergencyModeEnabledInSettings
        || this.mMpsmEnabledInSettings
        || this.mLimitAppsAndHomeScreenEnabledInSettings;
  }

  public String onBlocked() {
    int i;
    if (this.mLimitAppsAndHomeScreenEnabledInSettings) {
      Context context = this.mContext;
      return context.getString(
          R.string.global_actions_airplane_mode_on_status,
          context.getString(R.string.global_action_power_options));
    }
    Context context2 = this.mContext;
    if (this.mMpsmEnabledInSettings) {
      i = R.string.global_action_screenshot;
    } else {
      i =
          this.mEmergencyModeEnabledInSettings
              ? R.string.global_action_lockdown
              : R.string.global_actions_toggle_airplane_mode;
    }
    return context2.getString(i);
  }
}
