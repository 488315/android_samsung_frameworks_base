package com.android.server.am;

import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.PowerManager;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.am.mars.database.MARsDBHandler;

public final class FreecessTrigger {
    public Context mContext;
    public final AnonymousClass2 mIntentReceiverForBird;
    public final AnonymousClass2 mPkgIntentReceiver;
    public final AnonymousClass2 mSmartSwitchIntentReceiver;
    public boolean mIsRegisteredReceiverForEnhancedFreecess = false;
    public final AnonymousClass1 mIntentReceiver =
            new BroadcastReceiver() { // from class: com.android.server.am.FreecessTrigger.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    FreecessHandler freecessHandler;
                    FreecessHandler.MainHandler mainHandler;
                    String action = intent.getAction();
                    if (action.equals("android.intent.action.SCREEN_ON")) {
                        boolean z = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        FreecessController freecessController =
                                FreecessController.FreecessControllerHolder.INSTANCE;
                        freecessController.setScreenOnState(true);
                        if (freecessController.mIsFreecessEnable) {
                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
                            if (MARsPolicyManager.isChinaPolicyEnabled()) {
                                FreecessHandler.FreecessHandlerHolder.INSTANCE
                                        .sendUnfreezeActivePackagesMsg("screenOn");
                            } else {
                                FreecessHandler.FreecessHandlerHolder.INSTANCE
                                        .sendUnfreezeActivePackagesMsg("screenOn-widget");
                            }
                            FreecessHandler freecessHandler2 =
                                    FreecessHandler.FreecessHandlerHolder.INSTANCE;
                            FreecessHandler.MainHandler mainHandler2 =
                                    freecessHandler2.mMainHandler;
                            if (mainHandler2 == null) {
                                return;
                            }
                            mainHandler2.removeMessages(24);
                            freecessHandler2.mMainHandler.sendMessage(
                                    freecessHandler2.mMainHandler.obtainMessage(24));
                            return;
                        }
                        return;
                    }
                    if (action.equals("android.intent.action.SCREEN_OFF")) {
                        boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        FreecessController freecessController2 =
                                FreecessController.FreecessControllerHolder.INSTANCE;
                        freecessController2.setScreenOnState(false);
                        if (freecessController2.mIsScreenOnFreecessEnabled) {
                            FreecessHandler.FreecessHandlerHolder.INSTANCE.removeBgTriggerMsg();
                        }
                        if (FreecessTrigger.this.mIsRegisteredReceiverForEnhancedFreecess) {
                            return;
                        }
                        MARsDBHandler.getInstance();
                        MARsDBHandler mARsDBHandler = MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                        MARsDBHandler.MainHandler mainHandler3 = mARsDBHandler.mMainHandler;
                        if (mainHandler3 == null) {
                            return;
                        }
                        mARsDBHandler.mMainHandler.sendMessage(mainHandler3.obtainMessage(9));
                        return;
                    }
                    if (action.equals(UiModeManager.ACTION_ENTER_CAR_MODE)) {
                        boolean z3 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        FreecessController freecessController3 =
                                FreecessController.FreecessControllerHolder.INSTANCE;
                        freecessController3.mCarModeOn = true;
                        if (freecessController3.mIsFreecessEnable) {
                            FreecessHandler.FreecessHandlerHolder.INSTANCE.sendResetAllStateMsg(
                                    "CarMode");
                            return;
                        }
                        return;
                    }
                    if (action.equals("sec.app.policy.UPDATE.ssrm_update_freecess")) {
                        boolean z4 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        if (!FreecessController.FreecessControllerHolder.INSTANCE.mIsFreecessEnable
                                || (mainHandler =
                                                (freecessHandler =
                                                                FreecessHandler
                                                                        .FreecessHandlerHolder
                                                                        .INSTANCE)
                                                        .mMainHandler)
                                        == null) {
                            return;
                        }
                        freecessHandler.mMainHandler.sendMessageDelayed(
                                mainHandler.obtainMessage(12), 30000L);
                        return;
                    }
                    if (!action.equals("com.samsung.intent.action.EMERGENCY_STATE_CHANGED")) {
                        if (action.equals("android.intent.action.ACTION_SHUTDOWN")
                                || action.equals("android.intent.action.REBOOT")) {
                            boolean z5 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                            if (FreecessController.FreecessControllerHolder.INSTANCE
                                    .mIsFreecessEnable) {
                                FreecessHandler.FreecessHandlerHolder.INSTANCE.sendResetAllStateMsg(
                                        "ShutDown");
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    boolean z6 = intent.getIntExtra("reason", 0) == 3;
                    boolean z7 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                    FreecessController freecessController4 =
                            FreecessController.FreecessControllerHolder.INSTANCE;
                    freecessController4.mEmergencyModeOn = z6;
                    if (freecessController4.mIsFreecessEnable && z6) {
                        FreecessHandler.FreecessHandlerHolder.INSTANCE.sendResetAllStateMsg(
                                "EmeregencyMode");
                    }
                }
            };

    public abstract class FreecessTriggerHolder {
        public static final FreecessTrigger INSTANCE = new FreecessTrigger();
    }

    public FreecessTrigger() {
        final int i = 0;
        this.mPkgIntentReceiver =
                new BroadcastReceiver() { // from class: com.android.server.am.FreecessTrigger.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        Uri data;
                        int intExtra;
                        PowerManager powerManager;
                        boolean z = true;
                        switch (i) {
                            case 0:
                                if (!"android.intent.action.PACKAGE_REMOVED"
                                                .equals(intent.getAction())
                                        || intent.getBooleanExtra(
                                                "android.intent.extra.REPLACING", false)
                                        || (data = intent.getData()) == null
                                        || (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.UID", -1))
                                                == -1) {
                                    return;
                                }
                                FreecessHandler freecessHandler =
                                        FreecessHandler.FreecessHandlerHolder.INSTANCE;
                                String schemeSpecificPart = data.getSchemeSpecificPart();
                                if (freecessHandler.mMainHandler == null) {
                                    return;
                                }
                                boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                FreecessPkgStatus packageStatus =
                                        FreecessController.FreecessControllerHolder.INSTANCE
                                                .getPackageStatus(intExtra);
                                if (packageStatus == null) {
                                    return;
                                }
                                String str = packageStatus.name;
                                freecessHandler.removeBgTriggerMsgByObj(1, str);
                                freecessHandler.removeBgTriggerMsgByObj(2, str);
                                freecessHandler.removeBgTriggerMsgByObj(28, str);
                                freecessHandler.removeBgTriggerMsgByObj(3, str);
                                freecessHandler.removeBgTriggerMsgByObj(4, str);
                                Bundle bundle = new Bundle();
                                bundle.putString("packageName", schemeSpecificPart);
                                bundle.putInt("uid", intExtra);
                                Message obtainMessage =
                                        freecessHandler.mMainHandler.obtainMessage(16);
                                obtainMessage.setData(bundle);
                                freecessHandler.mMainHandler.sendMessage(obtainMessage);
                                return;
                            case 1:
                                String action = intent.getAction();
                                if (action == null) {
                                    return;
                                }
                                if ("com.samsung.android.intent.action.SMARTSWITCH_WORK_START"
                                        .equals(action)) {
                                    boolean z3 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    if (freecessController.mIsSmartSwitchWorking) {
                                        return;
                                    }
                                    freecessController.mIsSmartSwitchWorking = true;
                                    freecessController.setFreecessEnableForSpecificReason(2, false);
                                    return;
                                }
                                if ("com.samsung.android.intent.action.SMARTSWITCH_WORK_ONGOING"
                                        .equals(action)) {
                                    boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                            .addDebugInfoToHistory(
                                                    "FRZ", "OFF by SmartSwitch-Ongoing");
                                    boolean z5 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController.FreecessControllerHolder.INSTANCE
                                                    .mIsSmartSwitchWorking =
                                            true;
                                    FreecessHandler.FreecessHandlerHolder.INSTANCE
                                            .sendSetFreecessEnableDelayedMsg(2);
                                    return;
                                }
                                if ("com.samsung.android.intent.action.SMARTSWITCH_WORK_FINISH"
                                        .equals(action)) {
                                    boolean z6 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController2 =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    if (freecessController2.mIsDumpstateWorking) {
                                        return;
                                    }
                                    FreecessHandler.FreecessHandlerHolder.INSTANCE
                                            .removeBgTriggerMsgByObj(11, null);
                                    freecessController2.mIsSmartSwitchWorking = false;
                                    freecessController2.setFreecessEnableForSpecificReason(2, true);
                                    return;
                                }
                                return;
                            default:
                                String action2 = intent.getAction();
                                if ((action2.equals(
                                                        "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED")
                                                || action2.equals(
                                                        "android.os.action.DEVICE_IDLE_MODE_CHANGED"))
                                        && (powerManager =
                                                        (PowerManager)
                                                                context.getSystemService("power"))
                                                != null) {
                                    if (!powerManager.isDeviceIdleMode()
                                            && !powerManager.isLightDeviceIdleMode()) {
                                        z = false;
                                    }
                                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                                            "doze state changed : ", "FreecessTrigger", z);
                                    boolean z7 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    synchronized (mARsPolicyManager) {
                                        mARsPolicyManager.mIsDeviceIdleMode = z;
                                    }
                                    if (MARsPolicyManager.isChinaPolicyEnabled() || z) {
                                        return;
                                    }
                                    FreecessHandler.FreecessHandlerHolder.INSTANCE
                                            .sendUnfreezeActivePackagesMsg("DeviceIdleOFF");
                                    return;
                                }
                                return;
                        }
                    }
                };
        final int i2 = 1;
        this.mSmartSwitchIntentReceiver =
                new BroadcastReceiver() { // from class: com.android.server.am.FreecessTrigger.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        Uri data;
                        int intExtra;
                        PowerManager powerManager;
                        boolean z = true;
                        switch (i2) {
                            case 0:
                                if (!"android.intent.action.PACKAGE_REMOVED"
                                                .equals(intent.getAction())
                                        || intent.getBooleanExtra(
                                                "android.intent.extra.REPLACING", false)
                                        || (data = intent.getData()) == null
                                        || (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.UID", -1))
                                                == -1) {
                                    return;
                                }
                                FreecessHandler freecessHandler =
                                        FreecessHandler.FreecessHandlerHolder.INSTANCE;
                                String schemeSpecificPart = data.getSchemeSpecificPart();
                                if (freecessHandler.mMainHandler == null) {
                                    return;
                                }
                                boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                FreecessPkgStatus packageStatus =
                                        FreecessController.FreecessControllerHolder.INSTANCE
                                                .getPackageStatus(intExtra);
                                if (packageStatus == null) {
                                    return;
                                }
                                String str = packageStatus.name;
                                freecessHandler.removeBgTriggerMsgByObj(1, str);
                                freecessHandler.removeBgTriggerMsgByObj(2, str);
                                freecessHandler.removeBgTriggerMsgByObj(28, str);
                                freecessHandler.removeBgTriggerMsgByObj(3, str);
                                freecessHandler.removeBgTriggerMsgByObj(4, str);
                                Bundle bundle = new Bundle();
                                bundle.putString("packageName", schemeSpecificPart);
                                bundle.putInt("uid", intExtra);
                                Message obtainMessage =
                                        freecessHandler.mMainHandler.obtainMessage(16);
                                obtainMessage.setData(bundle);
                                freecessHandler.mMainHandler.sendMessage(obtainMessage);
                                return;
                            case 1:
                                String action = intent.getAction();
                                if (action == null) {
                                    return;
                                }
                                if ("com.samsung.android.intent.action.SMARTSWITCH_WORK_START"
                                        .equals(action)) {
                                    boolean z3 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    if (freecessController.mIsSmartSwitchWorking) {
                                        return;
                                    }
                                    freecessController.mIsSmartSwitchWorking = true;
                                    freecessController.setFreecessEnableForSpecificReason(2, false);
                                    return;
                                }
                                if ("com.samsung.android.intent.action.SMARTSWITCH_WORK_ONGOING"
                                        .equals(action)) {
                                    boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                            .addDebugInfoToHistory(
                                                    "FRZ", "OFF by SmartSwitch-Ongoing");
                                    boolean z5 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController.FreecessControllerHolder.INSTANCE
                                                    .mIsSmartSwitchWorking =
                                            true;
                                    FreecessHandler.FreecessHandlerHolder.INSTANCE
                                            .sendSetFreecessEnableDelayedMsg(2);
                                    return;
                                }
                                if ("com.samsung.android.intent.action.SMARTSWITCH_WORK_FINISH"
                                        .equals(action)) {
                                    boolean z6 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController2 =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    if (freecessController2.mIsDumpstateWorking) {
                                        return;
                                    }
                                    FreecessHandler.FreecessHandlerHolder.INSTANCE
                                            .removeBgTriggerMsgByObj(11, null);
                                    freecessController2.mIsSmartSwitchWorking = false;
                                    freecessController2.setFreecessEnableForSpecificReason(2, true);
                                    return;
                                }
                                return;
                            default:
                                String action2 = intent.getAction();
                                if ((action2.equals(
                                                        "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED")
                                                || action2.equals(
                                                        "android.os.action.DEVICE_IDLE_MODE_CHANGED"))
                                        && (powerManager =
                                                        (PowerManager)
                                                                context.getSystemService("power"))
                                                != null) {
                                    if (!powerManager.isDeviceIdleMode()
                                            && !powerManager.isLightDeviceIdleMode()) {
                                        z = false;
                                    }
                                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                                            "doze state changed : ", "FreecessTrigger", z);
                                    boolean z7 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    synchronized (mARsPolicyManager) {
                                        mARsPolicyManager.mIsDeviceIdleMode = z;
                                    }
                                    if (MARsPolicyManager.isChinaPolicyEnabled() || z) {
                                        return;
                                    }
                                    FreecessHandler.FreecessHandlerHolder.INSTANCE
                                            .sendUnfreezeActivePackagesMsg("DeviceIdleOFF");
                                    return;
                                }
                                return;
                        }
                    }
                };
        final int i3 = 2;
        this.mIntentReceiverForBird =
                new BroadcastReceiver() { // from class: com.android.server.am.FreecessTrigger.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        Uri data;
                        int intExtra;
                        PowerManager powerManager;
                        boolean z = true;
                        switch (i3) {
                            case 0:
                                if (!"android.intent.action.PACKAGE_REMOVED"
                                                .equals(intent.getAction())
                                        || intent.getBooleanExtra(
                                                "android.intent.extra.REPLACING", false)
                                        || (data = intent.getData()) == null
                                        || (intExtra =
                                                        intent.getIntExtra(
                                                                "android.intent.extra.UID", -1))
                                                == -1) {
                                    return;
                                }
                                FreecessHandler freecessHandler =
                                        FreecessHandler.FreecessHandlerHolder.INSTANCE;
                                String schemeSpecificPart = data.getSchemeSpecificPart();
                                if (freecessHandler.mMainHandler == null) {
                                    return;
                                }
                                boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                FreecessPkgStatus packageStatus =
                                        FreecessController.FreecessControllerHolder.INSTANCE
                                                .getPackageStatus(intExtra);
                                if (packageStatus == null) {
                                    return;
                                }
                                String str = packageStatus.name;
                                freecessHandler.removeBgTriggerMsgByObj(1, str);
                                freecessHandler.removeBgTriggerMsgByObj(2, str);
                                freecessHandler.removeBgTriggerMsgByObj(28, str);
                                freecessHandler.removeBgTriggerMsgByObj(3, str);
                                freecessHandler.removeBgTriggerMsgByObj(4, str);
                                Bundle bundle = new Bundle();
                                bundle.putString("packageName", schemeSpecificPart);
                                bundle.putInt("uid", intExtra);
                                Message obtainMessage =
                                        freecessHandler.mMainHandler.obtainMessage(16);
                                obtainMessage.setData(bundle);
                                freecessHandler.mMainHandler.sendMessage(obtainMessage);
                                return;
                            case 1:
                                String action = intent.getAction();
                                if (action == null) {
                                    return;
                                }
                                if ("com.samsung.android.intent.action.SMARTSWITCH_WORK_START"
                                        .equals(action)) {
                                    boolean z3 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    if (freecessController.mIsSmartSwitchWorking) {
                                        return;
                                    }
                                    freecessController.mIsSmartSwitchWorking = true;
                                    freecessController.setFreecessEnableForSpecificReason(2, false);
                                    return;
                                }
                                if ("com.samsung.android.intent.action.SMARTSWITCH_WORK_ONGOING"
                                        .equals(action)) {
                                    boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                            .addDebugInfoToHistory(
                                                    "FRZ", "OFF by SmartSwitch-Ongoing");
                                    boolean z5 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController.FreecessControllerHolder.INSTANCE
                                                    .mIsSmartSwitchWorking =
                                            true;
                                    FreecessHandler.FreecessHandlerHolder.INSTANCE
                                            .sendSetFreecessEnableDelayedMsg(2);
                                    return;
                                }
                                if ("com.samsung.android.intent.action.SMARTSWITCH_WORK_FINISH"
                                        .equals(action)) {
                                    boolean z6 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController2 =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    if (freecessController2.mIsDumpstateWorking) {
                                        return;
                                    }
                                    FreecessHandler.FreecessHandlerHolder.INSTANCE
                                            .removeBgTriggerMsgByObj(11, null);
                                    freecessController2.mIsSmartSwitchWorking = false;
                                    freecessController2.setFreecessEnableForSpecificReason(2, true);
                                    return;
                                }
                                return;
                            default:
                                String action2 = intent.getAction();
                                if ((action2.equals(
                                                        "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED")
                                                || action2.equals(
                                                        "android.os.action.DEVICE_IDLE_MODE_CHANGED"))
                                        && (powerManager =
                                                        (PowerManager)
                                                                context.getSystemService("power"))
                                                != null) {
                                    if (!powerManager.isDeviceIdleMode()
                                            && !powerManager.isLightDeviceIdleMode()) {
                                        z = false;
                                    }
                                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                                            "doze state changed : ", "FreecessTrigger", z);
                                    boolean z7 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    synchronized (mARsPolicyManager) {
                                        mARsPolicyManager.mIsDeviceIdleMode = z;
                                    }
                                    if (MARsPolicyManager.isChinaPolicyEnabled() || z) {
                                        return;
                                    }
                                    FreecessHandler.FreecessHandlerHolder.INSTANCE
                                            .sendUnfreezeActivePackagesMsg("DeviceIdleOFF");
                                    return;
                                }
                                return;
                        }
                    }
                };
    }
}
