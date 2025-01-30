package com.samsung.android.server.battery;

import android.app.ActivityManagerInternal;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.LocalServices;
import com.samsung.android.os.SemCompanionDeviceBatteryInfo;
import com.samsung.android.server.battery.DeviceBatteryInfoService;
import java.io.PrintWriter;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public class DeviceBatteryInfoService implements DeviceBatteryInfoServiceInternal {
    public static final int mOneUIVersion = SystemProperties.getInt("ro.build.version.oneui", 0);
    public Handler mBroadcastHandler;
    public HandlerThread mBroadcastHandlerThread;
    public Context mContext;
    public Handler mHandler;
    public HandlerThread mHandlerThread;
    public HashMap mBatteryInfos = new HashMap();
    public SemCompanionDeviceBatteryInfo mPhoneBatteryInfo = null;
    public List mRegisteredPackage = new ArrayList();
    public BluetoothDeviceBatteryManager mBluetoothDeviceBatteryManager = null;
    public WatchBatteryManagerInterface mLegacyWatchManagerManager = null;
    public ModernWatchBatteryManager mModernWatchBatteryManager = null;
    public String[] mRequirePermissions = {"android.permission.BLUETOOTH_CONNECT", "com.samsung.android.permission.SEM_BATTERY_INFO"};
    public final Object mBatteryInfosLock = new Object();
    public ContentObserver mDeviceNameObserver = null;
    public ActivityManagerInternal mActivityManagerInternal = null;
    public boolean mUncaughtExceptionOccurred = false;
    public ContentObserver mAodObserver = null;

    public void addPhoneBatteryInfo() {
        Slog.i("DeviceBatteryInfoService", "addPhoneBatteryInfo");
        try {
            String string = Settings.Global.getString(this.mContext.getContentResolver(), "device_name");
            Intent registerReceiver = this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = registerReceiver.getIntExtra("level", -1);
            int intExtra2 = registerReceiver.getIntExtra("status", -1);
            SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo = new SemCompanionDeviceBatteryInfo();
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
            Slog.e("DeviceBatteryInfoService", "exception occurred : " + e);
        }
    }

    @Override // com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal
    public void systemServicesReady(Context context) {
        this.mContext = context;
        Slog.e("DeviceBatteryInfoService", "systemServicesReady()");
        this.mHandlerThread = new HandlerThread("sembatteryservice-handler");
        this.mHandlerThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda4
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(Thread thread, Throwable th) {
                DeviceBatteryInfoService.this.lambda$systemServicesReady$0(thread, th);
            }
        });
        this.mHandlerThread.start();
        Handler handler = new Handler(this.mHandlerThread.getLooper());
        this.mHandler = handler;
        this.mBluetoothDeviceBatteryManager = new BluetoothDeviceBatteryManager(this.mContext, handler);
        int i = mOneUIVersion;
        if (i < 60100) {
            this.mLegacyWatchManagerManager = new LegacyWatchBatteryManager(this.mContext, this.mHandler);
        } else {
            if (i < 60101) {
                this.mLegacyWatchManagerManager = new WatchBatteryManager(this.mContext, this.mHandler);
            }
            this.mModernWatchBatteryManager = new ModernWatchBatteryManager(this.mContext, this.mHandler);
        }
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.samsung.android.server.battery.DeviceBatteryInfoService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                try {
                    String action = intent.getAction();
                    Slog.i("DeviceBatteryInfoService", "action: " + action);
                    if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                        DeviceBatteryInfoService deviceBatteryInfoService = DeviceBatteryInfoService.this;
                        if (deviceBatteryInfoService.mPhoneBatteryInfo == null) {
                            deviceBatteryInfoService.addPhoneBatteryInfo();
                        }
                    }
                    if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                        int intExtra = intent.getIntExtra("level", -1);
                        int intExtra2 = intent.getIntExtra("status", -1);
                        Slog.i("DeviceBatteryInfoService", "phone battery level: " + intExtra);
                        Slog.i("DeviceBatteryInfoService", "phone battery status: " + intExtra2);
                        DeviceBatteryInfoService deviceBatteryInfoService2 = DeviceBatteryInfoService.this;
                        SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo = deviceBatteryInfoService2.mPhoneBatteryInfo;
                        if (semCompanionDeviceBatteryInfo == null) {
                            deviceBatteryInfoService2.addPhoneBatteryInfo();
                            return;
                        }
                        if (semCompanionDeviceBatteryInfo.getBatteryLevel() == intExtra && DeviceBatteryInfoService.this.mPhoneBatteryInfo.getBatteryStatus() == intExtra2) {
                            return;
                        }
                        DeviceBatteryInfoService.this.mPhoneBatteryInfo.setBatteryLevel(intExtra);
                        Slog.d("DeviceBatteryInfoService", "setBatteryStatus : " + intExtra2);
                        DeviceBatteryInfoService.this.mPhoneBatteryInfo.setBatteryStatus(intExtra2);
                        DeviceBatteryInfoService deviceBatteryInfoService3 = DeviceBatteryInfoService.this;
                        deviceBatteryInfoService3.sendBroadcast("com.samsung.battery.ACTION_BATTERY_INFO_CHANGED", deviceBatteryInfoService3.mPhoneBatteryInfo);
                    }
                } catch (Exception e) {
                    Slog.e("DeviceBatteryInfoService", "exception occurred : " + e);
                }
            }
        }, intentFilter, null, this.mHandler);
        this.mBluetoothDeviceBatteryManager.systemServicesReady();
        if (i < 60101) {
            this.mLegacyWatchManagerManager.systemServicesReady();
        }
        registerDeviceNameContentObserver();
        if (i >= 60100) {
            this.mModernWatchBatteryManager.systemServicesReady();
            registerScreenOnOffReceiver();
            registerAodShowStateObserver();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemServicesReady$0(Thread thread, Throwable th) {
        Slog.e("DeviceBatteryInfoService", "Exception occurred in Battery Manager thread");
        Slog.e("DeviceBatteryInfoService", "Exception : " + th);
        this.mUncaughtExceptionOccurred = true;
    }

    public void registerDeviceNameContentObserver() {
        this.mDeviceNameObserver = new ContentObserver(this.mHandler) { // from class: com.samsung.android.server.battery.DeviceBatteryInfoService.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                Slog.i("DeviceBatteryInfoService", "onChange : " + uri);
                String string = Settings.Global.getString(DeviceBatteryInfoService.this.mContext.getContentResolver(), "device_name");
                Slog.i("DeviceBatteryInfoService", "device name : " + string);
                DeviceBatteryInfoService.this.mPhoneBatteryInfo.setDeviceName(string);
                DeviceBatteryInfoService deviceBatteryInfoService = DeviceBatteryInfoService.this;
                deviceBatteryInfoService.sendBroadcast("com.samsung.battery.ACTION_BATTERY_INFO_CHANGED", deviceBatteryInfoService.mPhoneBatteryInfo);
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_name"), false, this.mDeviceNameObserver);
    }

    public void registerScreenOnOffReceiver() {
        HandlerThread handlerThread = new HandlerThread("broadcastreceiver-handler");
        this.mBroadcastHandlerThread = handlerThread;
        handlerThread.start();
        this.mBroadcastHandler = new Handler(this.mBroadcastHandlerThread.getLooper());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        this.mContext.registerReceiver(new C31573(), intentFilter, null, this.mBroadcastHandler);
    }

    /* renamed from: com.samsung.android.server.battery.DeviceBatteryInfoService$3 */
    public class C31573 extends BroadcastReceiver {
        public C31573() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                Slog.i("DeviceBatteryInfoService", "action: " + action);
                if ("android.intent.action.SCREEN_ON".equals(action)) {
                    DeviceBatteryInfoService.this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.battery.DeviceBatteryInfoService$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            DeviceBatteryInfoService.C31573.this.lambda$onReceive$0();
                        }
                    });
                } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
                    DeviceBatteryInfoService.this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.battery.DeviceBatteryInfoService$3$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DeviceBatteryInfoService.C31573.this.lambda$onReceive$1();
                        }
                    });
                }
            } catch (Exception e) {
                Slog.e("DeviceBatteryInfoService", "exception occurred : " + e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0() {
            Slog.i("DeviceBatteryInfoService", "screen on");
            if (DeviceBatteryInfoService.mOneUIVersion < 60101) {
                DeviceBatteryInfoService.this.mLegacyWatchManagerManager.displayStateChanged(true);
            }
            DeviceBatteryInfoService.this.mModernWatchBatteryManager.displayStateChanged(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$1() {
            Slog.i("DeviceBatteryInfoService", "screen off");
            if (DeviceBatteryInfoService.mOneUIVersion < 60101) {
                DeviceBatteryInfoService.this.mLegacyWatchManagerManager.displayStateChanged(false);
            }
            DeviceBatteryInfoService.this.mModernWatchBatteryManager.displayStateChanged(false);
        }
    }

    public void registerAodShowStateObserver() {
        this.mAodObserver = new ContentObserver(this.mHandler) { // from class: com.samsung.android.server.battery.DeviceBatteryInfoService.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                Slog.i("DeviceBatteryInfoService", "onChange : " + uri);
                try {
                    int i = Settings.System.getInt(DeviceBatteryInfoService.this.mContext.getContentResolver(), "aod_show_state");
                    if (DeviceBatteryInfoService.mOneUIVersion < 60101) {
                        DeviceBatteryInfoService.this.mLegacyWatchManagerManager.aodShowStateChanged(i);
                    }
                    DeviceBatteryInfoService.this.mModernWatchBatteryManager.aodShowStateChanged(i);
                    Slog.i("DeviceBatteryInfoService", "aod_show_state : " + i);
                } catch (Settings.SettingNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("aod_show_state"), false, this.mAodObserver);
    }

    @Override // com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal
    public SemCompanionDeviceBatteryInfo[] getDeviceBatteryInfos() {
        SemCompanionDeviceBatteryInfo[] semCompanionDeviceBatteryInfoArr;
        requirePermissions();
        Slog.i("DeviceBatteryInfoService", "semGetBatteryInfos()");
        synchronized (this.mBatteryInfosLock) {
            HashMap hashMap = this.mBatteryInfos;
            if (hashMap != null) {
                hashMap.forEach(new BiConsumer() { // from class: com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        DeviceBatteryInfoService.printBatteryInfo((SemCompanionDeviceBatteryInfo) obj2);
                    }
                });
                semCompanionDeviceBatteryInfoArr = (SemCompanionDeviceBatteryInfo[]) this.mBatteryInfos.values().toArray(new SemCompanionDeviceBatteryInfo[this.mBatteryInfos.size()]);
            } else {
                semCompanionDeviceBatteryInfoArr = null;
            }
        }
        return semCompanionDeviceBatteryInfoArr;
    }

    @Override // com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal
    public SemCompanionDeviceBatteryInfo getDeviceBatteryInfo(String str) {
        SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo;
        requirePermissions();
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "getDeviceBatteryInfo : address is null");
            return null;
        }
        Slog.i("DeviceBatteryInfoService", "semGetBatteryInfo() : " + DeviceBatteryInfoUtil.getAddressForLog(str));
        synchronized (this.mBatteryInfosLock) {
            semCompanionDeviceBatteryInfo = (SemCompanionDeviceBatteryInfo) this.mBatteryInfos.get(str);
        }
        return semCompanionDeviceBatteryInfo;
    }

    @Override // com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal
    public void registerDeviceBatteryInfoChanged(String str) {
        requirePermissions();
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "registerDeviceBatteryInfoChanged : packageName is null");
            throw new IllegalArgumentException();
        }
        Slog.i("DeviceBatteryInfoService", "mRegisteredPackage size :" + this.mRegisteredPackage.size());
        boolean z = this.mRegisteredPackage.size() == 0;
        Slog.i("DeviceBatteryInfoService", "registerDeviceBatteryInfoChanged() : " + str);
        if (!this.mRegisteredPackage.contains(str)) {
            this.mRegisteredPackage.add(str);
        }
        if (z) {
            int i = mOneUIVersion;
            if (i < 60101) {
                this.mLegacyWatchManagerManager.notifyPackageRegistered(true);
            }
            if (i >= 60100) {
                this.mModernWatchBatteryManager.notifyPackageRegistered(true);
            }
        }
    }

    @Override // com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal
    public void unRegisterDeviceBatteryInfoChanged(String str) {
        requirePermissions();
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "unRegisterDeviceBatteryInfoChanged : packageName is null");
            throw new IllegalArgumentException();
        }
        Slog.i("DeviceBatteryInfoService", "unRegisterDeviceBatteryInfoChanged() : " + str);
        if (this.mRegisteredPackage.contains(str)) {
            this.mRegisteredPackage.remove(str);
        }
        Slog.i("DeviceBatteryInfoService", "mRegisteredPackage size :" + this.mRegisteredPackage.size());
        if (this.mRegisteredPackage.size() == 0) {
            int i = mOneUIVersion;
            if (i < 60101) {
                this.mLegacyWatchManagerManager.notifyPackageRegistered(false);
            }
            if (i >= 60100) {
                this.mModernWatchBatteryManager.notifyPackageRegistered(false);
            }
        }
    }

    public static void printBatteryInfo(SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
        Slog.i("DeviceBatteryInfoService", "Name : " + semCompanionDeviceBatteryInfo.getDeviceName() + " / BatteryLevel : " + semCompanionDeviceBatteryInfo.getBatteryLevel() + " / Status : " + semCompanionDeviceBatteryInfo.getBatteryStatus());
    }

    @Override // com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal
    public void sendBroadcast(String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
        for (String str2 : this.mRegisteredPackage) {
            try {
                Intent intent = new Intent(str);
                intent.setPackage(str2);
                intent.putExtra("com.samsung.battery.EXTRA_BATTERY_INFO", (Parcelable) semCompanionDeviceBatteryInfo);
                intent.addFlags(16777216);
                intent.addFlags(268435456);
                this.mContext.sendBroadcastAsUserMultiplePermissions(intent, UserHandle.CURRENT, this.mRequirePermissions);
                Slog.i("DeviceBatteryInfoService", "sendBroadcast : action : " + str + " / package : " + str2);
                printBatteryInfo(semCompanionDeviceBatteryInfo);
            } catch (Exception e) {
                Slog.e("DeviceBatteryInfoService", "Exception occurred : " + e);
            }
        }
    }

    @Override // com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal
    public boolean containsBatteryInfo(String str) {
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

    @Override // com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal
    public void removeBatteryInfo(String str) {
        SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo;
        boolean z;
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "removeBatteryInfo : address is null");
            return;
        }
        synchronized (this.mBatteryInfosLock) {
            semCompanionDeviceBatteryInfo = (SemCompanionDeviceBatteryInfo) this.mBatteryInfos.get(str);
            if (this.mBatteryInfos.containsKey(str)) {
                this.mBatteryInfos.remove(str);
                z = true;
            } else {
                z = false;
            }
        }
        if (z) {
            sendBroadcast("com.samsung.battery.ACTION_BATTERY_INFO_REMOVED", semCompanionDeviceBatteryInfo);
        }
    }

    @Override // com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal
    public void addBatteryInfo(String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "addBatteryInfo : address is null");
            return;
        }
        synchronized (this.mBatteryInfosLock) {
            this.mBatteryInfos.put(str, semCompanionDeviceBatteryInfo);
        }
        sendBroadcast("com.samsung.battery.ACTION_BATTERY_INFO_ADDED", semCompanionDeviceBatteryInfo);
    }

    public void requirePermissions() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BLUETOOTH_CONNECT", "Permission Require");
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.SEM_BATTERY_INFO", "Permission Require");
    }

    @Override // com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal
    public void setDeviceBatteryInfo(final String str, final SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
        Slog.i("DeviceBatteryInfoService", "setDeviceBatteryInfo()");
        requireProviderPermissions();
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "address is null");
            throw new IllegalArgumentException();
        }
        if (!containsBatteryInfo(str)) {
            final int callingPid = Binder.getCallingPid();
            Slog.i("DeviceBatteryInfoService", "addBatteryInfo / callingPid :" + callingPid);
            this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DeviceBatteryInfoService.this.lambda$setDeviceBatteryInfo$2(str, semCompanionDeviceBatteryInfo, callingPid);
                }
            });
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DeviceBatteryInfoService.this.lambda$setDeviceBatteryInfo$3(str, semCompanionDeviceBatteryInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDeviceBatteryInfo$2(String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo, int i) {
        Slog.i("DeviceBatteryInfoService", "address : " + DeviceBatteryInfoUtil.getAddressForLog(str));
        addBatteryInfo(semCompanionDeviceBatteryInfo.getAddress(), semCompanionDeviceBatteryInfo);
        String packageNameByPid = this.mActivityManagerInternal.getPackageNameByPid(i);
        Slog.i("DeviceBatteryInfoService", "packageName : " + packageNameByPid);
        int i2 = mOneUIVersion;
        if (i2 >= 60100) {
            if (i2 < 60101) {
                if (semCompanionDeviceBatteryInfo.getDeviceType() == 6) {
                    this.mModernWatchBatteryManager.addWatchPackageInfo(packageNameByPid, str);
                }
            } else if (semCompanionDeviceBatteryInfo.getDeviceType() == 4 || semCompanionDeviceBatteryInfo.getDeviceType() == 6) {
                this.mModernWatchBatteryManager.addWatchPackageInfo(packageNameByPid, str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDeviceBatteryInfo$3(String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
        Slog.i("DeviceBatteryInfoService", "address : " + DeviceBatteryInfoUtil.getAddressForLog(str));
        SemCompanionDeviceBatteryInfo deviceBatteryInfo = getDeviceBatteryInfo(str);
        if (deviceBatteryInfo != null) {
            deviceBatteryInfo.setDeviceName(semCompanionDeviceBatteryInfo.getDeviceName());
            deviceBatteryInfo.setBatteryLevel(semCompanionDeviceBatteryInfo.getBatteryLevel());
            deviceBatteryInfo.setBatteryStatus(semCompanionDeviceBatteryInfo.getBatteryStatus());
            deviceBatteryInfo.setExtraBatteryLevelLeft(semCompanionDeviceBatteryInfo.getExtraBatteryLevelLeft());
            deviceBatteryInfo.setExtraBatteryLevelRight(semCompanionDeviceBatteryInfo.getExtraBatteryLevelRight());
            deviceBatteryInfo.setExtraBatteryLevelCradle(semCompanionDeviceBatteryInfo.getExtraBatteryLevelCradle());
            deviceBatteryInfo.setExtraBatteryStatusLeft(semCompanionDeviceBatteryInfo.getExtraBatteryStatusLeft());
            deviceBatteryInfo.setExtraBatteryStatusRight(semCompanionDeviceBatteryInfo.getExtraBatteryStatusRight());
            deviceBatteryInfo.setExtraBatteryStatusCradle(semCompanionDeviceBatteryInfo.getExtraBatteryStatusCradle());
            sendBroadcast("com.samsung.battery.ACTION_BATTERY_INFO_CHANGED", deviceBatteryInfo);
            return;
        }
        Slog.i("DeviceBatteryInfoService", "batteryInfo is null");
    }

    @Override // com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal
    public void unsetDeviceBatteryInfo(final String str) {
        Slog.i("DeviceBatteryInfoService", "unsetDeviceBatteryInfo()");
        requireProviderPermissions();
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "address is null");
            throw new IllegalArgumentException();
        }
        if (!containsBatteryInfo(str)) {
            Slog.i("DeviceBatteryInfoService", "device is not exist");
        } else {
            final int callingPid = Binder.getCallingPid();
            this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DeviceBatteryInfoService.this.lambda$unsetDeviceBatteryInfo$4(callingPid, str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unsetDeviceBatteryInfo$4(int i, String str) {
        try {
            String packageNameByPid = this.mActivityManagerInternal.getPackageNameByPid(i);
            Slog.i("DeviceBatteryInfoService", "address : " + DeviceBatteryInfoUtil.getAddressForLog(str) + " packageName : " + packageNameByPid);
            SemCompanionDeviceBatteryInfo deviceBatteryInfo = getDeviceBatteryInfo(str);
            int i2 = mOneUIVersion;
            if (i2 >= 60100) {
                if (i2 < 60101) {
                    if (deviceBatteryInfo.getDeviceType() == 6) {
                        this.mModernWatchBatteryManager.removeWatchPackageInfo(packageNameByPid);
                    }
                } else if (deviceBatteryInfo.getDeviceType() == 4 || deviceBatteryInfo.getDeviceType() == 6) {
                    this.mModernWatchBatteryManager.removeWatchPackageInfo(packageNameByPid);
                }
            }
            removeBatteryInfo(str);
        } catch (Exception e) {
            Slog.e("DeviceBatteryInfoService", "Exception occurred : " + e);
        }
    }

    public void requireProviderPermissions() {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.SEM_BATTERY_INFO_PROVIDER", "Permission Require");
    }

    @Override // com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal
    public void dump(final PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("Companion Device Baterry Infos: ");
        synchronized (this.mBatteryInfosLock) {
            HashMap hashMap = this.mBatteryInfos;
            if (hashMap != null && hashMap.size() > 0) {
                this.mBatteryInfos.forEach(new BiConsumer() { // from class: com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda5
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        DeviceBatteryInfoService.lambda$dump$5(printWriter, (String) obj, (SemCompanionDeviceBatteryInfo) obj2);
                    }
                });
            }
        }
        if (this.mUncaughtExceptionOccurred) {
            printWriter.println();
            printWriter.println("UncaughtException occurred in sembatteryservice-handler");
        }
        printWriter.println();
    }

    public static /* synthetic */ void lambda$dump$5(PrintWriter printWriter, String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
        printWriter.println("    [ " + DeviceBatteryInfoUtil.getAddressForLog(semCompanionDeviceBatteryInfo.getAddress()) + ", " + semCompanionDeviceBatteryInfo.getDeviceType() + ", " + semCompanionDeviceBatteryInfo.getBatteryLevel() + ", " + semCompanionDeviceBatteryInfo.getExtraBatteryLevelLeft() + ", " + semCompanionDeviceBatteryInfo.getExtraBatteryLevelRight() + ", " + semCompanionDeviceBatteryInfo.getExtraBatteryLevelCradle() + ", " + semCompanionDeviceBatteryInfo.getBatteryStatus() + " ]");
    }
}
