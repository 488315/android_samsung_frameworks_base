package com.samsung.android.server.battery;

import android.app.ActivityManagerInternal;
import android.app.AppGlobals;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.os.SemCompanionDeviceBatteryInfo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

public final class DeviceBatteryInfoService {
    public Handler mBroadcastHandler;
    public HandlerThread mBroadcastHandlerThread;
    public Context mContext;
    public Handler mHandler;
    public HandlerThread mHandlerThread;
    public final HashMap mBatteryInfos = new HashMap();
    public SemCompanionDeviceBatteryInfo mPhoneBatteryInfo = null;
    public final List mRegisteredPackage = new ArrayList();
    public BluetoothDeviceBatteryManager mBluetoothDeviceBatteryManager = null;
    public WatchBatteryManager mWatchBatteryManager = null;
    public final String[] mRequirePermissions = {
        "android.permission.BLUETOOTH_CONNECT", "com.samsung.android.permission.SEM_BATTERY_INFO"
    };
    public final Object mBatteryInfosLock = new Object();
    public AnonymousClass3 mDeviceNameObserver = null;
    public ActivityManagerInternal mActivityManagerInternal = null;
    public boolean mUncaughtExceptionOccurred = false;
    public AnonymousClass3 mAodObserver = null;
    public final HashMap packageAddressMap = new HashMap();
    public final HashMap packageReceiverMap = new HashMap();

    /* renamed from: com.samsung.android.server.battery.DeviceBatteryInfoService$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DeviceBatteryInfoService this$0;

        public /* synthetic */ AnonymousClass1(
                DeviceBatteryInfoService deviceBatteryInfoService, int i) {
            this.$r8$classId = i;
            this.this$0 = deviceBatteryInfoService;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    try {
                        String action = intent.getAction();
                        Slog.i("DeviceBatteryInfoService", "action: " + action);
                        if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                            DeviceBatteryInfoService deviceBatteryInfoService = this.this$0;
                            if (deviceBatteryInfoService.mPhoneBatteryInfo == null) {
                                deviceBatteryInfoService.addPhoneBatteryInfo();
                            }
                        }
                        if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                            int intExtra = intent.getIntExtra("level", -1);
                            int intExtra2 =
                                    intent.getIntExtra(Constants.JSON_CLIENT_DATA_STATUS, -1);
                            Slog.i("DeviceBatteryInfoService", "phone battery level: " + intExtra);
                            Slog.i(
                                    "DeviceBatteryInfoService",
                                    "phone battery status: " + intExtra2);
                            DeviceBatteryInfoService deviceBatteryInfoService2 = this.this$0;
                            SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo =
                                    deviceBatteryInfoService2.mPhoneBatteryInfo;
                            if (semCompanionDeviceBatteryInfo != null) {
                                if (semCompanionDeviceBatteryInfo.getBatteryLevel() != intExtra
                                        || this.this$0.mPhoneBatteryInfo.getBatteryStatus()
                                                != intExtra2) {
                                    this.this$0.mPhoneBatteryInfo.setBatteryLevel(intExtra);
                                    Slog.d(
                                            "DeviceBatteryInfoService",
                                            "setBatteryStatus : " + intExtra2);
                                    this.this$0.mPhoneBatteryInfo.setBatteryStatus(intExtra2);
                                    DeviceBatteryInfoService deviceBatteryInfoService3 =
                                            this.this$0;
                                    deviceBatteryInfoService3.sendBroadcast(
                                            "com.samsung.battery.ACTION_BATTERY_INFO_CHANGED",
                                            deviceBatteryInfoService3.mPhoneBatteryInfo);
                                    break;
                                }
                            } else {
                                deviceBatteryInfoService2.addPhoneBatteryInfo();
                                break;
                            }
                        }
                    } catch (Exception e) {
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                e, "exception occurred : ", "DeviceBatteryInfoService");
                        return;
                    }
                    break;
                case 1:
                    try {
                        String action2 = intent.getAction();
                        Slog.i("DeviceBatteryInfoService", "action: " + action2);
                        String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                        if (this.this$0.packageAddressMap.containsKey(schemeSpecificPart)) {
                            String str =
                                    (String) this.this$0.packageAddressMap.get(schemeSpecificPart);
                            if ("android.intent.action.PACKAGE_REMOVED".equals(action2)) {
                                this.this$0.removeInfoOnPackageChange(schemeSpecificPart, str);
                            } else if ("android.intent.action.PACKAGE_CHANGED".equals(action2)) {
                                int applicationEnabledSetting =
                                        AppGlobals.getPackageManager()
                                                .getApplicationEnabledSetting(
                                                        schemeSpecificPart,
                                                        intent.getIntExtra(
                                                                "android.intent.extra.user_handle",
                                                                0));
                                if (applicationEnabledSetting != 0
                                        && applicationEnabledSetting != 1) {
                                    this.this$0.removeInfoOnPackageChange(schemeSpecificPart, str);
                                }
                            }
                        }
                        break;
                    } catch (Exception e2) {
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                e2, "Exception occurred : ", "DeviceBatteryInfoService");
                        return;
                    }
                    break;
                default:
                    try {
                        String action3 = intent.getAction();
                        Slog.i("DeviceBatteryInfoService", "action: " + action3);
                        if ("android.intent.action.SCREEN_ON".equals(action3)) {
                            final int i = 0;
                            this.this$0.mHandler.post(
                                    new Runnable(
                                            this) { // from class:
                                                    // com.samsung.android.server.battery.DeviceBatteryInfoService$4$$ExternalSyntheticLambda0
                                        public final /* synthetic */ DeviceBatteryInfoService
                                                        .AnonymousClass1
                                                f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i2 = i;
                                            DeviceBatteryInfoService.AnonymousClass1
                                                    anonymousClass1 = this.f$0;
                                            anonymousClass1.getClass();
                                            switch (i2) {
                                                case 0:
                                                    Slog.i("DeviceBatteryInfoService", "screen on");
                                                    anonymousClass1.this$0.mWatchBatteryManager
                                                            .displayStateChanged(true);
                                                    break;
                                                default:
                                                    Slog.i(
                                                            "DeviceBatteryInfoService",
                                                            "screen off");
                                                    anonymousClass1.this$0.mWatchBatteryManager
                                                            .displayStateChanged(false);
                                                    break;
                                            }
                                        }
                                    });
                        } else if ("android.intent.action.SCREEN_OFF".equals(action3)) {
                            final int i2 = 1;
                            this.this$0.mHandler.post(
                                    new Runnable(
                                            this) { // from class:
                                                    // com.samsung.android.server.battery.DeviceBatteryInfoService$4$$ExternalSyntheticLambda0
                                        public final /* synthetic */ DeviceBatteryInfoService
                                                        .AnonymousClass1
                                                f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i22 = i2;
                                            DeviceBatteryInfoService.AnonymousClass1
                                                    anonymousClass1 = this.f$0;
                                            anonymousClass1.getClass();
                                            switch (i22) {
                                                case 0:
                                                    Slog.i("DeviceBatteryInfoService", "screen on");
                                                    anonymousClass1.this$0.mWatchBatteryManager
                                                            .displayStateChanged(true);
                                                    break;
                                                default:
                                                    Slog.i(
                                                            "DeviceBatteryInfoService",
                                                            "screen off");
                                                    anonymousClass1.this$0.mWatchBatteryManager
                                                            .displayStateChanged(false);
                                                    break;
                                            }
                                        }
                                    });
                        }
                        break;
                    } catch (Exception e3) {
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                e3, "exception occurred : ", "DeviceBatteryInfoService");
                        return;
                    }
            }
        }
    }

    public static void printBatteryInfo(
            SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
        Slog.i(
                "DeviceBatteryInfoService",
                "Name : "
                        + semCompanionDeviceBatteryInfo.getDeviceName()
                        + " / BatteryLevel : "
                        + semCompanionDeviceBatteryInfo.getBatteryLevel()
                        + " / Status : "
                        + semCompanionDeviceBatteryInfo.getBatteryStatus());
    }

    public final void addBatteryInfo(
            String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "addBatteryInfo : address is null");
            return;
        }
        synchronized (this.mBatteryInfosLock) {
            this.mBatteryInfos.put(str, semCompanionDeviceBatteryInfo);
        }
        sendBroadcast(
                "com.samsung.battery.ACTION_BATTERY_INFO_ADDED", semCompanionDeviceBatteryInfo);
    }

    public final void addPhoneBatteryInfo() {
        Slog.i("DeviceBatteryInfoService", "addPhoneBatteryInfo");
        try {
            String string =
                    Settings.Global.getString(this.mContext.getContentResolver(), "device_name");
            Intent registerReceiver =
                    this.mContext.registerReceiver(
                            null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = registerReceiver.getIntExtra("level", -1);
            int intExtra2 = registerReceiver.getIntExtra(Constants.JSON_CLIENT_DATA_STATUS, -1);
            SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo =
                    new SemCompanionDeviceBatteryInfo();
            semCompanionDeviceBatteryInfo.setAddress("00:00:00:00:00:00");
            semCompanionDeviceBatteryInfo.setDeviceName(string);
            semCompanionDeviceBatteryInfo.setDeviceType(2);
            semCompanionDeviceBatteryInfo.setBatteryLevel(intExtra);
            semCompanionDeviceBatteryInfo.setBatteryStatus(intExtra2);
            this.mPhoneBatteryInfo = semCompanionDeviceBatteryInfo;
            synchronized (this.mBatteryInfosLock) {
                this.mBatteryInfos.put("00:00:00:00:00:00", semCompanionDeviceBatteryInfo);
            }
        } catch (Exception e) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    e, "exception occurred : ", "DeviceBatteryInfoService");
        }
    }

    public final boolean containsBatteryInfo(String str) {
        boolean containsKey;
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "containsBatteryInfo : address is null");
            return false;
        }
        synchronized (this.mBatteryInfosLock) {
            containsKey = this.mBatteryInfos.containsKey(str);
        }
        return containsKey;
    }

    public final void dump(final PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("Companion Device Baterry Infos: ");
        synchronized (this.mBatteryInfosLock) {
            try {
                HashMap hashMap = this.mBatteryInfos;
                if (hashMap != null && hashMap.size() > 0) {
                    this.mBatteryInfos.forEach(
                            new BiConsumer() { // from class:
                                               // com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda2
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    PrintWriter printWriter2 = printWriter;
                                    SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo =
                                            (SemCompanionDeviceBatteryInfo) obj2;
                                    StringBuilder sb = new StringBuilder("    [ ");
                                    sb.append(
                                            DeviceBatteryInfoUtil.getAddressForLog(
                                                    semCompanionDeviceBatteryInfo.getAddress()));
                                    sb.append(", ");
                                    sb.append(semCompanionDeviceBatteryInfo.getDeviceType());
                                    sb.append(", ");
                                    sb.append(semCompanionDeviceBatteryInfo.getBatteryLevel());
                                    sb.append(", ");
                                    sb.append(
                                            semCompanionDeviceBatteryInfo
                                                    .getExtraBatteryLevelLeft());
                                    sb.append(", ");
                                    sb.append(
                                            semCompanionDeviceBatteryInfo
                                                    .getExtraBatteryLevelRight());
                                    sb.append(", ");
                                    sb.append(
                                            semCompanionDeviceBatteryInfo
                                                    .getExtraBatteryLevelCradle());
                                    sb.append(", ");
                                    sb.append(semCompanionDeviceBatteryInfo.getBatteryStatus());
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            sb, " ]", printWriter2);
                                }
                            });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.mUncaughtExceptionOccurred) {
            printWriter.println();
            printWriter.println("UncaughtException occurred in sembatteryservice-handler");
        }
        this.mWatchBatteryManager.getClass();
        printWriter.println();
    }

    public final SemCompanionDeviceBatteryInfo getDeviceBatteryInfo(String str) {
        SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo;
        requirePermissions();
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "getDeviceBatteryInfo : address is null");
            return null;
        }
        Slog.i(
                "DeviceBatteryInfoService",
                "semGetBatteryInfo() : " + DeviceBatteryInfoUtil.getAddressForLog(str));
        synchronized (this.mBatteryInfosLock) {
            semCompanionDeviceBatteryInfo =
                    (SemCompanionDeviceBatteryInfo) this.mBatteryInfos.get(str);
        }
        return semCompanionDeviceBatteryInfo;
    }

    public final void removeBatteryInfo(String str) {
        SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo;
        boolean z;
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "removeBatteryInfo : address is null");
            return;
        }
        synchronized (this.mBatteryInfosLock) {
            try {
                semCompanionDeviceBatteryInfo =
                        (SemCompanionDeviceBatteryInfo) this.mBatteryInfos.get(str);
                if (this.mBatteryInfos.containsKey(str)) {
                    this.mBatteryInfos.remove(str);
                    z = true;
                } else {
                    z = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            sendBroadcast(
                    "com.samsung.battery.ACTION_BATTERY_INFO_REMOVED",
                    semCompanionDeviceBatteryInfo);
        }
    }

    public final void removeInfoOnPackageChange(String str, String str2) {
        if (str2 == null) {
            Slog.i("DeviceBatteryInfoService", "address is null");
            throw new IllegalArgumentException();
        }
        if (containsBatteryInfo(str2)) {
            this.mHandler.post(
                    new DeviceBatteryInfoService$$ExternalSyntheticLambda5(this, str2, str, 1));
        } else {
            Slog.i("DeviceBatteryInfoService", "device is not exist");
        }
    }

    public final void requirePermissions() {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.BLUETOOTH_CONNECT", "Permission Require");
        this.mContext.enforceCallingOrSelfPermission(
                "com.samsung.android.permission.SEM_BATTERY_INFO", "Permission Require");
    }

    public final void sendBroadcast(
            String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
        Iterator it = ((ArrayList) this.mRegisteredPackage).iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            try {
                Intent intent = new Intent(str);
                intent.setPackage(str2);
                intent.putExtra(
                        "com.samsung.battery.EXTRA_BATTERY_INFO",
                        (Parcelable) semCompanionDeviceBatteryInfo);
                intent.addFlags(16777216);
                intent.addFlags(268435456);
                this.mContext.sendBroadcastAsUserMultiplePermissions(
                        intent, UserHandle.CURRENT, this.mRequirePermissions);
                Slog.i(
                        "DeviceBatteryInfoService",
                        "sendBroadcast : action : " + str + " / package : " + str2);
                printBatteryInfo(semCompanionDeviceBatteryInfo);
            } catch (Exception e) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        e, "Exception occurred : ", "DeviceBatteryInfoService");
            }
        }
    }
}
