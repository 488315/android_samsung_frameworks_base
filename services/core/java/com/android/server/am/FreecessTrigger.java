package com.android.server.am;

import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.PowerManager;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.am.mars.database.MARsDBManager;

/* loaded from: classes.dex */
public class FreecessTrigger {
  public static String TAG = "FreecessTrigger";
  public Context mContext;
  public BroadcastReceiver mIntentReceiver;
  public BroadcastReceiver mIntentReceiverForBird;
  public boolean mIsRegisteredReceiverForEnhancedFreecess;
  public final BroadcastReceiver mPkgIntentReceiver;
  public BroadcastReceiver mSmartSwitchIntentReceiver;

  public abstract class FreecessTriggerHolder {
    public static final FreecessTrigger INSTANCE = new FreecessTrigger();
  }

  public FreecessTrigger() {
    this.mIsRegisteredReceiverForEnhancedFreecess = false;
    this.mIntentReceiver =
        new BroadcastReceiver() { // from class: com.android.server.am.FreecessTrigger.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.SCREEN_ON")) {
              FreecessController.getInstance().setScreenOnState(true);
              if (FreecessController.getInstance().getFreecessEnabled()) {
                if (MARsPolicyManager.getInstance().isChinaPolicyEnabled()) {
                  FreecessHandler.getInstance().sendUnfreezeActivePackagesMsg("screenOn");
                } else {
                  FreecessHandler.getInstance().sendUnfreezeActivePackagesMsg("screenOn-widget");
                }
                FreecessHandler.getInstance().sendUnfreezeRequestFocusPackageMsg();
                return;
              }
              return;
            }
            if (action.equals("android.intent.action.SCREEN_OFF")) {
              FreecessController.getInstance().setScreenOnState(false);
              if (FreecessController.getInstance().getScreenOnFreecessEnabled()) {
                FreecessHandler.getInstance().removeBgTriggerMsg();
              }
              if (FreecessTrigger.this.mIsRegisteredReceiverForEnhancedFreecess) {
                return;
              }
              MARsDBManager.getInstance().sendGetRestrictionFlagMsgToMainHandler();
              return;
            }
            if (action.equals(UiModeManager.ACTION_ENTER_CAR_MODE)) {
              FreecessController.getInstance().setCarModeOnState(true);
              if (FreecessController.getInstance().getFreecessEnabled()) {
                FreecessHandler.getInstance().sendResetAllStateMsg("CarMode");
                return;
              }
              return;
            }
            if (action.equals("sec.app.policy.UPDATE.ssrm_update_freecess")) {
              if (FreecessController.getInstance().getFreecessEnabled()) {
                FreecessHandler.getInstance().sendSCPMChangedPkgMsgToDBHandler();
              }
            } else {
              if (action.equals("com.samsung.intent.action.EMERGENCY_STATE_CHANGED")) {
                boolean z = intent.getIntExtra("reason", 0) == 3;
                FreecessController.getInstance().setEmergencyModeOnState(z);
                if (FreecessController.getInstance().getFreecessEnabled() && z) {
                  FreecessHandler.getInstance().sendResetAllStateMsg("EmeregencyMode");
                  return;
                }
                return;
              }
              if ((action.equals("android.intent.action.ACTION_SHUTDOWN")
                      || action.equals("android.intent.action.REBOOT"))
                  && FreecessController.getInstance().getFreecessEnabled()) {
                FreecessHandler.getInstance().sendResetAllStateMsg("ShutDown");
              }
            }
          }
        };
    this.mPkgIntentReceiver =
        new BroadcastReceiver() { // from class: com.android.server.am.FreecessTrigger.2
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            Uri data;
            int intExtra;
            if (!"android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())
                || intent.getBooleanExtra("android.intent.extra.REPLACING", false)
                || (data = intent.getData()) == null
                || (intExtra = intent.getIntExtra("android.intent.extra.UID", -1)) == -1) {
              return;
            }
            FreecessHandler.getInstance()
                .sendRemovePackageMsg(data.getSchemeSpecificPart(), intExtra);
          }
        };
    this.mSmartSwitchIntentReceiver =
        new BroadcastReceiver() { // from class: com.android.server.am.FreecessTrigger.3
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
              return;
            }
            if ("com.samsung.android.intent.action.SMARTSWITCH_WORK_START".equals(action)) {
              FreecessController.getInstance().setFreecessEnableForSmartSwitch(false);
              return;
            }
            if ("com.samsung.android.intent.action.SMARTSWITCH_WORK_ONGOING".equals(action)) {
              MARsPolicyManager.getInstance()
                  .addDebugInfoToHistory("FRZ", "OFF by SmartSwitch-Ongoing");
              FreecessController.getInstance().setIsSmartSwitchWorking(true);
              FreecessHandler.getInstance().sendSetFreecessEnableDelayedMsg(2);
            } else {
              if (!"com.samsung.android.intent.action.SMARTSWITCH_WORK_FINISH".equals(action)
                  || FreecessController.getInstance().getIsDumpstateWorking()) {
                return;
              }
              FreecessHandler.getInstance().removeBgTriggerMsgByObj(11, null);
              FreecessController.getInstance().setIsSmartSwitchWorking(false);
              FreecessController.getInstance().setFreecessEnableForSmartSwitch(true);
            }
          }
        };
    this.mIntentReceiverForBird =
        new BroadcastReceiver() { // from class: com.android.server.am.FreecessTrigger.4
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent) {
            PowerManager powerManager;
            String action = intent.getAction();
            if ((action.equals("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED")
                    || action.equals("android.os.action.DEVICE_IDLE_MODE_CHANGED"))
                && (powerManager = (PowerManager) context.getSystemService("power")) != null) {
              boolean z = powerManager.isDeviceIdleMode() || powerManager.isLightDeviceIdleMode();
              Slog.d(FreecessTrigger.TAG, "doze state changed : " + z);
              MARsPolicyManager.getInstance().setDeviceIdleModeState(z);
              if (MARsPolicyManager.getInstance().isChinaPolicyEnabled() || z) {
                return;
              }
              FreecessHandler.getInstance().sendUnfreezeActivePackagesMsg("DeviceIdleOFF");
            }
          }
        };
  }

  public static FreecessTrigger getInstance() {
    return FreecessTriggerHolder.INSTANCE;
  }

  public void init(Context context) {
    this.mContext = context;
    registerReceiver();
  }

  public void registerReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.SCREEN_ON");
    intentFilter.addAction("android.intent.action.SCREEN_OFF");
    intentFilter.addAction("sec.app.policy.UPDATE.ssrm_update_freecess");
    intentFilter.addAction(UiModeManager.ACTION_ENTER_CAR_MODE);
    intentFilter.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
    intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
    intentFilter.addAction("android.intent.action.REBOOT");
    intentFilter.setPriority(1000);
    this.mContext.registerReceiver(this.mIntentReceiver, intentFilter);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
    intentFilter2.addDataScheme("package");
    this.mContext.registerReceiverAsUser(
        this.mPkgIntentReceiver, UserHandle.ALL, intentFilter2, null, null);
    IntentFilter intentFilter3 = new IntentFilter();
    intentFilter3.addAction("com.samsung.android.intent.action.SMARTSWITCH_WORK_START");
    intentFilter3.addAction("com.samsung.android.intent.action.SMARTSWITCH_WORK_ONGOING");
    intentFilter3.addAction("com.samsung.android.intent.action.SMARTSWITCH_WORK_FINISH");
    this.mContext.registerReceiver(
        this.mSmartSwitchIntentReceiver, intentFilter3, "com.wssnps.permission.COM_WSSNPS", null);
  }

  public void registerReceiverForEnhancedFreecess() {
    if (this.mIsRegisteredReceiverForEnhancedFreecess) {
      return;
    }
    try {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
      intentFilter.addAction("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED");
      intentFilter.setPriority(1000);
      this.mContext.registerReceiver(this.mIntentReceiverForBird, intentFilter);
      this.mIsRegisteredReceiverForEnhancedFreecess = true;
    } catch (Exception e) {
      Slog.e(TAG, "exception registerReceiverForBird " + e);
    }
  }
}
