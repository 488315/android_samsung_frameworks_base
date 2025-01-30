package com.samsung.android.server.battery;

import android.app.AlarmManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.LocalServices;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.os.SemCompanionDeviceBatteryInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class LegacyWatchBatteryManager implements WatchBatteryManagerInterface {
    public ArrayList AUTHORITY_URI_LIST;
    public ArrayList SUPPORT_URI_LIST;
    public AlarmManager mAlarmManager;
    public DeviceBatteryInfoServiceInternal mBatteryInfoServiceInternal;
    public Context mContext;
    public Handler mHandler;
    public ContentObserver mWatchBatteryObserver;
    public String BATTERY_INFO_PROVIDER_PERMISSION = "com.samsung.android.companionservice.BATTERY_INFO_PROVIDER";
    public SemCompanionDeviceBatteryInfo mWatchBatteryInfo = null;
    public boolean mRegistered = false;
    public boolean mScreenOn = false;
    public int mSyncStopOffset = 60;
    public int mSyncState = 0;
    public boolean mConnected = false;
    public boolean mCanSyncBatteryInfo = false;
    public boolean mProviderRegistered = false;
    public int mCheckSupportedVersionRetry = 0;

    @Override // com.samsung.android.server.battery.WatchBatteryManagerInterface
    public void aodShowStateChanged(int i) {
    }

    @Override // com.samsung.android.server.battery.WatchBatteryManagerInterface
    public void displayStateChanged(boolean z) {
    }

    public LegacyWatchBatteryManager(Context context, Handler handler) {
        this.mWatchBatteryObserver = null;
        this.mContext = context;
        this.mHandler = handler;
        this.mWatchBatteryObserver = new ContentObserver(this.mHandler) { // from class: com.samsung.android.server.battery.LegacyWatchBatteryManager.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                Slog.i("LegacyWatchBatteryManager", "onChange : " + uri);
                Cursor cursor = null;
                try {
                    try {
                        cursor = LegacyWatchBatteryManager.this.mContext.getContentResolver().query(uri, null, null, null, null);
                    } catch (Exception e) {
                        Slog.e("LegacyWatchBatteryManager", "Exception occurred : " + e);
                        if (0 == 0) {
                            return;
                        }
                    }
                    if (cursor == null) {
                        Slog.e("LegacyWatchBatteryManager", "cur is null");
                        if (cursor != null) {
                            cursor.close();
                            return;
                        }
                        return;
                    }
                    while (cursor.moveToNext()) {
                        String string = cursor.getString(cursor.getColumnIndex("_btAddress"));
                        String string2 = cursor.getString(cursor.getColumnIndex("Level"));
                        String string3 = cursor.getString(cursor.getColumnIndex("Status"));
                        Slog.i("LegacyWatchBatteryManager", "bt : " + DeviceBatteryInfoUtil.getAddressForLog(string) + ", batteryLevel : " + string2 + ", batteryStatus : " + string3 + ", storedTime : " + cursor.getString(cursor.getColumnIndex("Time")));
                        if (LegacyWatchBatteryManager.this.mWatchBatteryInfo.getAddress().equals(string)) {
                            int batteryLevel = LegacyWatchBatteryManager.this.mWatchBatteryInfo.getBatteryLevel();
                            int batteryStatus = LegacyWatchBatteryManager.this.mWatchBatteryInfo.getBatteryStatus();
                            int parseInt = Integer.parseInt(string2);
                            int parseInt2 = Integer.parseInt(string3);
                            LegacyWatchBatteryManager.this.mWatchBatteryInfo.setBatteryLevel(parseInt);
                            LegacyWatchBatteryManager.this.mWatchBatteryInfo.setBatteryStatus(parseInt2);
                            if (batteryLevel < 0) {
                                LegacyWatchBatteryManager.this.mBatteryInfoServiceInternal.addBatteryInfo(string, LegacyWatchBatteryManager.this.mWatchBatteryInfo);
                            } else if (batteryLevel != parseInt || batteryStatus != parseInt2) {
                                LegacyWatchBatteryManager.this.mBatteryInfoServiceInternal.sendBroadcast("com.samsung.battery.ACTION_BATTERY_INFO_CHANGED", LegacyWatchBatteryManager.this.mWatchBatteryInfo);
                            }
                        }
                    }
                    cursor.close();
                } catch (Throwable th) {
                    if (0 != 0) {
                        cursor.close();
                    }
                    throw th;
                }
            }
        };
    }

    @Override // com.samsung.android.server.battery.WatchBatteryManagerInterface
    public void systemServicesReady() {
        this.mBatteryInfoServiceInternal = (DeviceBatteryInfoServiceInternal) LocalServices.getService(DeviceBatteryInfoServiceInternal.class);
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
        final ScreenOffAlarmListener screenOffAlarmListener = new ScreenOffAlarmListener();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.wearable.action.WEARABLE_DEVICE_CONNECTED");
        intentFilter.addAction("com.samsung.android.wearable.action.WEARABLE_DEVICE_DISCONNECTED");
        intentFilter.addAction("com.samsung.keyguard.KEYGUARD_STATE_UPDATE");
        intentFilter.addAction("com.samsung.android.wearable.action.CAPABILITY_SUPPORT_BATTERY_INFO_SYNC");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.samsung.android.server.battery.LegacyWatchBatteryManager.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                try {
                    String action = intent.getAction();
                    Slog.i("LegacyWatchBatteryManager", "action: " + action);
                    if ("com.samsung.android.wearable.action.WEARABLE_DEVICE_CONNECTED".equals(action)) {
                        LegacyWatchBatteryManager.this.mConnected = true;
                        LegacyWatchBatteryManager.this.makeWatchAuthorities();
                        String stringExtra = intent.getStringExtra("device_address");
                        LegacyWatchBatteryManager legacyWatchBatteryManager = LegacyWatchBatteryManager.this;
                        legacyWatchBatteryManager.mWatchBatteryInfo = legacyWatchBatteryManager.createWatchBatteryInfo(stringExtra);
                        int checkSupportedVersion = LegacyWatchBatteryManager.this.checkSupportedVersion();
                        if (checkSupportedVersion != 4 && checkSupportedVersion != 2) {
                            LegacyWatchBatteryManager.this.prepareWatchBatteryInfoSync();
                            return;
                        }
                        Slog.e("LegacyWatchBatteryManager", "NOT_SUPPORTED or UNAVAILABLE_METHOD");
                        if (checkSupportedVersion == 4) {
                            LegacyWatchBatteryManager.this.mCheckSupportedVersionRetry = 0;
                            LegacyWatchBatteryManager.this.mCanSyncBatteryInfo = false;
                            LegacyWatchBatteryManager.this.retryCheckSupportedVersion();
                            return;
                        }
                        return;
                    }
                    if ("com.samsung.android.wearable.action.WEARABLE_DEVICE_DISCONNECTED".equals(action)) {
                        LegacyWatchBatteryManager.this.mConnected = false;
                        LegacyWatchBatteryManager.this.cleanupWatchBatteryInfo(intent.getStringExtra("device_address"));
                        return;
                    }
                    if ("com.samsung.android.wearable.action.CAPABILITY_SUPPORT_BATTERY_INFO_SYNC".equals(action)) {
                        if (!LegacyWatchBatteryManager.this.mConnected) {
                            Slog.e("LegacyWatchBatteryManager", "not connected");
                            return;
                        } else if (LegacyWatchBatteryManager.this.checkSupportedVersion() != 1) {
                            Slog.e("LegacyWatchBatteryManager", "checkSupportedVersion fail");
                            return;
                        } else {
                            LegacyWatchBatteryManager.this.prepareWatchBatteryInfoSync();
                            return;
                        }
                    }
                    if ("com.samsung.keyguard.KEYGUARD_STATE_UPDATE".equals(action)) {
                        boolean booleanExtra = intent.getBooleanExtra("showing", false);
                        Slog.i("LegacyWatchBatteryManager", "showing: " + booleanExtra);
                        if (booleanExtra) {
                            LegacyWatchBatteryManager.this.mScreenOn = false;
                            Slog.i("LegacyWatchBatteryManager", "mRegistered: " + LegacyWatchBatteryManager.this.mRegistered + "/ mSyncState:" + LegacyWatchBatteryManager.this.mSyncState);
                            if (LegacyWatchBatteryManager.this.mSyncState == 1) {
                                LegacyWatchBatteryManager.this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + (LegacyWatchBatteryManager.this.mSyncStopOffset * 60 * 1000), "WatchBatteryManagerAlarm", screenOffAlarmListener, LegacyWatchBatteryManager.this.mHandler);
                                return;
                            }
                            return;
                        }
                        LegacyWatchBatteryManager.this.mScreenOn = true;
                        LegacyWatchBatteryManager.this.mAlarmManager.cancel(screenOffAlarmListener);
                        LegacyWatchBatteryManager.this.checkSyncStart();
                    }
                } catch (Exception e) {
                    Slog.e("LegacyWatchBatteryManager", "exception occurred : " + e);
                }
            }
        }, intentFilter, null, this.mHandler);
    }

    public final void retryCheckSupportedVersion() {
        this.mHandler.postDelayed(new Runnable() { // from class: com.samsung.android.server.battery.LegacyWatchBatteryManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LegacyWatchBatteryManager.this.lambda$retryCheckSupportedVersion$0();
            }
        }, 30000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$retryCheckSupportedVersion$0() {
        Slog.e("LegacyWatchBatteryManager", "retryCheckSupportedVersion : " + this.mCheckSupportedVersionRetry);
        if (!this.mConnected || this.mCanSyncBatteryInfo || this.mCheckSupportedVersionRetry >= 2) {
            this.mCheckSupportedVersionRetry = 0;
            return;
        }
        int checkSupportedVersion = checkSupportedVersion();
        if (checkSupportedVersion == 4) {
            Slog.e("LegacyWatchBatteryManager", "NOT_SUPPORTED");
            this.mCheckSupportedVersionRetry++;
            retryCheckSupportedVersion();
        } else if (checkSupportedVersion == 1) {
            prepareWatchBatteryInfoSync();
        }
    }

    public final void prepareWatchBatteryInfoSync() {
        this.mCanSyncBatteryInfo = true;
        registerBatteryInfoObserver();
        checkSyncStart();
    }

    public final void cleanupWatchBatteryInfo(String str) {
        unRegisterBatteryInfoObserver();
        if (this.mBatteryInfoServiceInternal.containsBatteryInfo(str)) {
            this.mBatteryInfoServiceInternal.removeBatteryInfo(str);
        }
        this.mWatchBatteryInfo = null;
        this.mCanSyncBatteryInfo = false;
        checkSyncStop();
    }

    public final void checkSyncStart() {
        Slog.i("LegacyWatchBatteryManager", "checkSyncStart() / mRegistered: " + this.mRegistered + " / mScreenOn:" + this.mScreenOn + " / mCanSyncBatteryInfo:" + this.mCanSyncBatteryInfo + " / mSyncState:" + this.mSyncState);
        if (this.mRegistered && this.mScreenOn && this.mCanSyncBatteryInfo && this.mSyncState == 0) {
            requestBatteryDataSync(1);
            this.mSyncState = 1;
        }
    }

    public final void checkSyncStop() {
        Slog.i("LegacyWatchBatteryManager", "checkSyncStop() / mRegistered: " + this.mRegistered + " / mScreenOn:" + this.mScreenOn + " / mCanSyncBatteryInfo:" + this.mCanSyncBatteryInfo + " / mSyncState:" + this.mSyncState);
        if (this.mSyncState == 1) {
            requestBatteryDataSync(0);
            this.mSyncState = 0;
        }
    }

    @Override // com.samsung.android.server.battery.WatchBatteryManagerInterface
    public void notifyPackageRegistered(boolean z) {
        Slog.i("LegacyWatchBatteryManager", "isRegistered: " + z + "/ mSyncState:" + this.mSyncState + "/ mScreenOn:" + this.mScreenOn);
        if (z) {
            this.mRegistered = true;
            checkSyncStart();
        } else {
            this.mRegistered = false;
            checkSyncStop();
        }
    }

    public final SemCompanionDeviceBatteryInfo createWatchBatteryInfo(String str) {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Slog.e("LegacyWatchBatteryManager", "bluetoothAdapter is null");
            return null;
        }
        BluetoothDevice remoteDevice = defaultAdapter.getRemoteDevice(str);
        SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo = new SemCompanionDeviceBatteryInfo();
        semCompanionDeviceBatteryInfo.setAddress(remoteDevice.getAddress());
        semCompanionDeviceBatteryInfo.setDeviceName(remoteDevice.getAlias());
        semCompanionDeviceBatteryInfo.setDeviceType(4);
        semCompanionDeviceBatteryInfo.setBatteryLevel(-1);
        semCompanionDeviceBatteryInfo.setBatteryStatus(1);
        return semCompanionDeviceBatteryInfo;
    }

    public final void makeWatchAuthorities() {
        this.AUTHORITY_URI_LIST = new ArrayList();
        List<ResolveInfo> queryIntentContentProviders = this.mContext.getPackageManager().queryIntentContentProviders(new Intent(this.BATTERY_INFO_PROVIDER_PERMISSION), 0);
        for (ResolveInfo resolveInfo : queryIntentContentProviders) {
            this.AUTHORITY_URI_LIST.add(Uri.parse("content://" + resolveInfo.providerInfo.authority));
            Slog.i("LegacyWatchBatteryManager", "authority : " + resolveInfo.providerInfo.authority);
        }
        Slog.i("LegacyWatchBatteryManager", "resolve info size : " + queryIntentContentProviders.size());
    }

    public final void registerBatteryInfoObserver() {
        if (this.mProviderRegistered) {
            return;
        }
        try {
            Iterator it = this.SUPPORT_URI_LIST.iterator();
            while (it.hasNext()) {
                Uri uri = (Uri) it.next();
                Slog.i("LegacyWatchBatteryManager", "registerBatteryInfoObserver : " + uri.toString());
                this.mContext.getContentResolver().registerContentObserver(uri, false, this.mWatchBatteryObserver);
            }
            this.mProviderRegistered = true;
        } catch (Exception e) {
            Slog.e("LegacyWatchBatteryManager", "Exception occurred : " + e);
        }
    }

    public final void unRegisterBatteryInfoObserver() {
        if (this.mProviderRegistered) {
            try {
                this.mContext.getContentResolver().unregisterContentObserver(this.mWatchBatteryObserver);
                this.mProviderRegistered = false;
            } catch (Exception e) {
                Slog.e("LegacyWatchBatteryManager", "Exception occurred : " + e);
            }
        }
    }

    public final void requestBatteryDataSync(final int i) {
        Slog.i("LegacyWatchBatteryManager", "requestBatteryDataSync syncData: " + i);
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.battery.LegacyWatchBatteryManager.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putInt("sync_battery_data", i);
                    Iterator it = LegacyWatchBatteryManager.this.SUPPORT_URI_LIST.iterator();
                    while (it.hasNext()) {
                        Uri uri = (Uri) it.next();
                        Slog.i("LegacyWatchBatteryManager", "requestBatteryDataSync : " + uri.toString());
                        Slog.i("LegacyWatchBatteryManager", "requestBatteryDataSync : " + LegacyWatchBatteryManager.this.mContext.getContentResolver().call(uri, "sync_request", (String) null, bundle).getInt(KnoxCustomManagerService.SPCM_KEY_RESULT));
                    }
                } catch (IllegalStateException e) {
                    Slog.e("LegacyWatchBatteryManager", "requestBatteryDataSync - IllegalStateException : " + e);
                } catch (Exception e2) {
                    Slog.e("LegacyWatchBatteryManager", "Exception occurred : " + e2);
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x007c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int checkSupportedVersion() {
        int i;
        boolean z;
        this.SUPPORT_URI_LIST = new ArrayList();
        boolean z2 = false;
        try {
            Iterator it = this.AUTHORITY_URI_LIST.iterator();
            i = 4;
            z = false;
            while (it.hasNext()) {
                try {
                    Uri uri = (Uri) it.next();
                    i = this.mContext.getContentResolver().call(uri, "check_sync_battery_data_supported_version", (String) null, (Bundle) null).getInt(KnoxCustomManagerService.SPCM_KEY_RESULT);
                    Slog.i("LegacyWatchBatteryManager", "checkSupportedVersion(" + uri.toString() + ") : " + i);
                    if (i == 1) {
                        this.SUPPORT_URI_LIST.add(uri);
                        z2 = true;
                    } else if (i == 4) {
                        z = true;
                    }
                } catch (Exception e) {
                    e = e;
                    Slog.e("LegacyWatchBatteryManager", "checkSupportedVersion - IllegalStateException : " + e);
                    if (!z2) {
                    }
                }
            }
        } catch (Exception e2) {
            e = e2;
            i = 4;
            z = false;
        }
        if (!z2) {
            return 1;
        }
        if (z) {
            return 4;
        }
        return i;
    }

    public class ScreenOffAlarmListener implements AlarmManager.OnAlarmListener {
        public ScreenOffAlarmListener() {
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            Slog.i("LegacyWatchBatteryManager", "ScreenOffAlarmListener onAlarm() ");
            if (LegacyWatchBatteryManager.this.mSyncState == 1) {
                LegacyWatchBatteryManager.this.requestBatteryDataSync(0);
                LegacyWatchBatteryManager.this.mSyncState = 0;
            }
        }
    }
}
