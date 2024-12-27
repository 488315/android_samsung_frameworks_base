package com.android.server.enterprise.device;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.StatFs;
import android.os.SystemProperties;
import android.os.storage.StorageVolume;
import android.provider.Settings;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IStorageManagerAdapter;
import com.android.server.enterprise.adapterlayer.StorageManagerAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.deviceinfo.IDeviceInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DeviceInfo extends IDeviceInfo.Stub implements EnterpriseServiceCallback {
    public static String mSignalStrength = Integer.toString(99);
    public final Context mContext;
    public final AnonymousClass1 mDataConnectionStateChangeReceiver;
    public final AnonymousClass1 mDataStatisticsReceiver;
    public final AnonymousClass3 mDataStatisticsUpdateRun;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public final AnonymousClass1 mMessagingReceiver;
    public final TelephonyManager mTelMgr;
    public WifiManager mWifiManager = null;
    public long mLastUpdateWifiTx = 0;
    public long mLastUpdateWifiRx = 0;
    public long mLastUpdateMobileTx = 0;
    public long mLastUpdateMobileRx = 0;
    public long mStorageWifiTx = 0;
    public long mStorageWifiRx = 0;
    public long mStorageMobileTx = 0;
    public long mStorageMobileRx = 0;
    public int mDataStatsCounter = 0;
    public int mDataUsageTimer = 3000;
    public boolean mDataUsageTimerActivated = false;
    public boolean mWifiStatsEnabled = false;
    public boolean mDataStatsEnabled = false;
    public boolean mDataLogEnabled = false;
    public final Handler mDataUsageEventsHandler = new Handler();
    public long mDataCallLogLastTime = 0;
    public String mDataCallLogLastStatus = "";
    public String mDataCallLogLastNetType = "";
    public long mDataCallLogLastValue = 0;
    public boolean mDataCallConnected = false;

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.server.enterprise.device.DeviceInfo$3] */
    public DeviceInfo(Context context) {
        this.mEdmStorageProvider = null;
        final int i = 0;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.enterprise.device.DeviceInfo.1
                    public final /* synthetic */ DeviceInfo this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        ContentValues contentValues;
                        boolean z;
                        switch (i) {
                            case 0:
                                if (intent.getAction()
                                        .equals("android.net.conn.DATA_ACTIVITY_CHANGE")) {
                                    if (!intent.getBooleanExtra("isActive", false)) {
                                        DeviceInfo deviceInfo = this.this$0;
                                        deviceInfo.mDataCallLogLastTime = 0L;
                                        deviceInfo.mDataCallLogLastValue = 0L;
                                        deviceInfo.mDataCallConnected = false;
                                        break;
                                    } else {
                                        this.this$0.mDataCallConnected = true;
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                Log.d("DeviceInfo", intent.getAction());
                                if (!intent.getAction()
                                        .equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                                    if (intent.getAction()
                                            .equals("android.intent.action.ACTION_SHUTDOWN")) {
                                        DeviceInfo deviceInfo2 = this.this$0;
                                        deviceInfo2.mDataStatsCounter = 10;
                                        deviceInfo2.mDataUsageEventsHandler.removeCallbacks(
                                                deviceInfo2.mDataStatisticsUpdateRun);
                                        DeviceInfo deviceInfo3 = this.this$0;
                                        if (deviceInfo3.mDataUsageTimerActivated) {
                                            deviceInfo3.mDataUsageEventsHandler.postDelayed(
                                                    deviceInfo3.mDataStatisticsUpdateRun, 0L);
                                            break;
                                        }
                                    }
                                } else {
                                    DeviceInfo deviceInfo4 = this.this$0;
                                    deviceInfo4.getClass();
                                    deviceInfo4.mLastUpdateWifiTx = DeviceInfo.getTrafficWifiTx();
                                    deviceInfo4.mLastUpdateWifiRx = DeviceInfo.getTrafficWifiRx();
                                    deviceInfo4.mLastUpdateMobileTx =
                                            deviceInfo4.getTrafficMobileTx();
                                    deviceInfo4.mLastUpdateMobileRx =
                                            deviceInfo4.getTrafficMobileRx();
                                    deviceInfo4.mDataCallLogLastTime = 0L;
                                    int strictDataUsageTimer =
                                            deviceInfo4.getStrictDataUsageTimer();
                                    if (strictDataUsageTimer == 0) {
                                        strictDataUsageTimer = 3;
                                    }
                                    deviceInfo4.mDataUsageTimer = strictDataUsageTimer * 1000;
                                    deviceInfo4.mDataStatsEnabled =
                                            deviceInfo4.getDataCallStatisticsEnabled(null);
                                    deviceInfo4.mDataLogEnabled =
                                            deviceInfo4.getDataCallLoggingEnabled(null);
                                    deviceInfo4.mWifiStatsEnabled =
                                            deviceInfo4.getWifiStatisticEnabled(null);
                                    ArrayList dataByFields =
                                            deviceInfo4.mEdmStorageProvider.getDataByFields(
                                                    "DEVICE",
                                                    null,
                                                    null,
                                                    new String[] {
                                                        "deviceWifiSent",
                                                        "deviceWifiReceived",
                                                        "deviceNetworkSent",
                                                        "deviceNetworkReceived"
                                                    });
                                    if (!dataByFields.isEmpty()
                                            && (contentValues = (ContentValues) dataByFields.get(0))
                                                    != null) {
                                        try {
                                            deviceInfo4.mStorageWifiTx =
                                                    contentValues
                                                            .getAsLong("deviceWifiSent")
                                                            .longValue();
                                            deviceInfo4.mStorageWifiRx =
                                                    contentValues
                                                            .getAsLong("deviceWifiReceived")
                                                            .longValue();
                                            deviceInfo4.mStorageMobileTx =
                                                    contentValues
                                                            .getAsLong("deviceNetworkSent")
                                                            .longValue();
                                            deviceInfo4.mStorageMobileRx =
                                                    contentValues
                                                            .getAsLong("deviceNetworkReceived")
                                                            .longValue();
                                        } catch (NullPointerException unused) {
                                            Log.d(
                                                    "DeviceInfo",
                                                    "initializeStorageValues - Error reading from"
                                                        + " Device Storage");
                                            deviceInfo4.resetDataUsage(null);
                                        }
                                    }
                                    this.this$0.dataUsageTimerActivation(null);
                                    break;
                                }
                                break;
                            default:
                                if (this.this$0.isMMSCaptureEnabled(null)) {
                                    String action = intent.getAction();
                                    Bundle extras = intent.getExtras();
                                    if (action != null && extras != null) {
                                        if (!"com.samsung.mms.RECEIVED_MSG".equals(action)) {
                                            if (!"com.samsung.mms.SENT_MSG".equals(action)) {
                                                Log.d(
                                                        "DeviceInfo",
                                                        "Unexpected intent arrived at"
                                                            + " mMessagingReceiver");
                                                break;
                                            } else {
                                                z = false;
                                            }
                                        } else {
                                            z = true;
                                        }
                                        if ("mms".equals(extras.getString("msg_type"))) {
                                            String string = extras.getString("msg_address");
                                            long j = extras.getLong("date");
                                            String string2 = extras.getString("msg_subject");
                                            String string3 = extras.getString("msg_body");
                                            String string4 = extras.getString("content_location");
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(string4 == null ? "" : string4.concat(" "));
                                            sb.append(string2 == null ? "" : string2.concat(" "));
                                            if (string3 == null) {
                                                string3 = "";
                                            }
                                            sb.append(string3);
                                            this.this$0.storeMMS(
                                                    string, String.valueOf(j), sb.toString(), z);
                                            break;
                                        }
                                    } else {
                                        Log.d(
                                                "DeviceInfo",
                                                "No data arrived at mMessagingReceiver");
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.enterprise.device.DeviceInfo.1
                    public final /* synthetic */ DeviceInfo this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        ContentValues contentValues;
                        boolean z;
                        switch (i2) {
                            case 0:
                                if (intent.getAction()
                                        .equals("android.net.conn.DATA_ACTIVITY_CHANGE")) {
                                    if (!intent.getBooleanExtra("isActive", false)) {
                                        DeviceInfo deviceInfo = this.this$0;
                                        deviceInfo.mDataCallLogLastTime = 0L;
                                        deviceInfo.mDataCallLogLastValue = 0L;
                                        deviceInfo.mDataCallConnected = false;
                                        break;
                                    } else {
                                        this.this$0.mDataCallConnected = true;
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                Log.d("DeviceInfo", intent.getAction());
                                if (!intent.getAction()
                                        .equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                                    if (intent.getAction()
                                            .equals("android.intent.action.ACTION_SHUTDOWN")) {
                                        DeviceInfo deviceInfo2 = this.this$0;
                                        deviceInfo2.mDataStatsCounter = 10;
                                        deviceInfo2.mDataUsageEventsHandler.removeCallbacks(
                                                deviceInfo2.mDataStatisticsUpdateRun);
                                        DeviceInfo deviceInfo3 = this.this$0;
                                        if (deviceInfo3.mDataUsageTimerActivated) {
                                            deviceInfo3.mDataUsageEventsHandler.postDelayed(
                                                    deviceInfo3.mDataStatisticsUpdateRun, 0L);
                                            break;
                                        }
                                    }
                                } else {
                                    DeviceInfo deviceInfo4 = this.this$0;
                                    deviceInfo4.getClass();
                                    deviceInfo4.mLastUpdateWifiTx = DeviceInfo.getTrafficWifiTx();
                                    deviceInfo4.mLastUpdateWifiRx = DeviceInfo.getTrafficWifiRx();
                                    deviceInfo4.mLastUpdateMobileTx =
                                            deviceInfo4.getTrafficMobileTx();
                                    deviceInfo4.mLastUpdateMobileRx =
                                            deviceInfo4.getTrafficMobileRx();
                                    deviceInfo4.mDataCallLogLastTime = 0L;
                                    int strictDataUsageTimer =
                                            deviceInfo4.getStrictDataUsageTimer();
                                    if (strictDataUsageTimer == 0) {
                                        strictDataUsageTimer = 3;
                                    }
                                    deviceInfo4.mDataUsageTimer = strictDataUsageTimer * 1000;
                                    deviceInfo4.mDataStatsEnabled =
                                            deviceInfo4.getDataCallStatisticsEnabled(null);
                                    deviceInfo4.mDataLogEnabled =
                                            deviceInfo4.getDataCallLoggingEnabled(null);
                                    deviceInfo4.mWifiStatsEnabled =
                                            deviceInfo4.getWifiStatisticEnabled(null);
                                    ArrayList dataByFields =
                                            deviceInfo4.mEdmStorageProvider.getDataByFields(
                                                    "DEVICE",
                                                    null,
                                                    null,
                                                    new String[] {
                                                        "deviceWifiSent",
                                                        "deviceWifiReceived",
                                                        "deviceNetworkSent",
                                                        "deviceNetworkReceived"
                                                    });
                                    if (!dataByFields.isEmpty()
                                            && (contentValues = (ContentValues) dataByFields.get(0))
                                                    != null) {
                                        try {
                                            deviceInfo4.mStorageWifiTx =
                                                    contentValues
                                                            .getAsLong("deviceWifiSent")
                                                            .longValue();
                                            deviceInfo4.mStorageWifiRx =
                                                    contentValues
                                                            .getAsLong("deviceWifiReceived")
                                                            .longValue();
                                            deviceInfo4.mStorageMobileTx =
                                                    contentValues
                                                            .getAsLong("deviceNetworkSent")
                                                            .longValue();
                                            deviceInfo4.mStorageMobileRx =
                                                    contentValues
                                                            .getAsLong("deviceNetworkReceived")
                                                            .longValue();
                                        } catch (NullPointerException unused) {
                                            Log.d(
                                                    "DeviceInfo",
                                                    "initializeStorageValues - Error reading from"
                                                        + " Device Storage");
                                            deviceInfo4.resetDataUsage(null);
                                        }
                                    }
                                    this.this$0.dataUsageTimerActivation(null);
                                    break;
                                }
                                break;
                            default:
                                if (this.this$0.isMMSCaptureEnabled(null)) {
                                    String action = intent.getAction();
                                    Bundle extras = intent.getExtras();
                                    if (action != null && extras != null) {
                                        if (!"com.samsung.mms.RECEIVED_MSG".equals(action)) {
                                            if (!"com.samsung.mms.SENT_MSG".equals(action)) {
                                                Log.d(
                                                        "DeviceInfo",
                                                        "Unexpected intent arrived at"
                                                            + " mMessagingReceiver");
                                                break;
                                            } else {
                                                z = false;
                                            }
                                        } else {
                                            z = true;
                                        }
                                        if ("mms".equals(extras.getString("msg_type"))) {
                                            String string = extras.getString("msg_address");
                                            long j = extras.getLong("date");
                                            String string2 = extras.getString("msg_subject");
                                            String string3 = extras.getString("msg_body");
                                            String string4 = extras.getString("content_location");
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(string4 == null ? "" : string4.concat(" "));
                                            sb.append(string2 == null ? "" : string2.concat(" "));
                                            if (string3 == null) {
                                                string3 = "";
                                            }
                                            sb.append(string3);
                                            this.this$0.storeMMS(
                                                    string, String.valueOf(j), sb.toString(), z);
                                            break;
                                        }
                                    } else {
                                        Log.d(
                                                "DeviceInfo",
                                                "No data arrived at mMessagingReceiver");
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        this.mDataStatisticsUpdateRun =
                new Runnable() { // from class: com.android.server.enterprise.device.DeviceInfo.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        long j;
                        long j2;
                        DeviceInfo.this.mDataUsageEventsHandler.removeCallbacks(this);
                        DeviceInfo deviceInfo = DeviceInfo.this;
                        deviceInfo.mDataStatsCounter++;
                        long trafficWifiTx = DeviceInfo.getTrafficWifiTx();
                        long j3 = deviceInfo.mLastUpdateWifiTx;
                        if (trafficWifiTx > j3) {
                            long j4 = trafficWifiTx - j3;
                            if (deviceInfo.isWifiStateEnabled() && deviceInfo.mWifiStatsEnabled) {
                                deviceInfo.mStorageWifiTx += j4;
                            }
                        }
                        deviceInfo.mLastUpdateWifiTx = trafficWifiTx;
                        long trafficWifiRx = DeviceInfo.getTrafficWifiRx();
                        long j5 = deviceInfo.mLastUpdateWifiRx;
                        if (trafficWifiRx > j5) {
                            long j6 = trafficWifiRx - j5;
                            if (deviceInfo.isWifiStateEnabled() && deviceInfo.mWifiStatsEnabled) {
                                deviceInfo.mStorageWifiRx += j6;
                            }
                        }
                        deviceInfo.mLastUpdateWifiRx = trafficWifiRx;
                        long trafficMobileTx = deviceInfo.getTrafficMobileTx();
                        long j7 = deviceInfo.mLastUpdateMobileTx;
                        if (trafficMobileTx >= j7) {
                            j = trafficMobileTx - j7;
                            if (deviceInfo.mDataStatsEnabled) {
                                deviceInfo.mStorageMobileTx += j;
                            }
                        } else {
                            deviceInfo.mDataCallLogLastTime = 0L;
                            deviceInfo.mDataCallLogLastValue = 0L;
                            j = 0;
                        }
                        deviceInfo.mLastUpdateMobileTx = trafficMobileTx;
                        long trafficMobileRx = deviceInfo.getTrafficMobileRx();
                        long j8 = deviceInfo.mLastUpdateMobileRx;
                        if (trafficMobileRx >= j8) {
                            j2 = trafficMobileRx - j8;
                            if (deviceInfo.mDataStatsEnabled) {
                                deviceInfo.mStorageMobileRx += j2;
                            }
                        } else {
                            deviceInfo.mDataCallLogLastTime = 0L;
                            deviceInfo.mDataCallLogLastValue = 0L;
                            j2 = 0;
                        }
                        deviceInfo.mLastUpdateMobileRx = trafficMobileRx;
                        long j9 = j + j2;
                        if (j9 > 0) {
                            if (!deviceInfo.mDataLogEnabled) {
                                Log.d("DeviceInfo", "Logging disabled");
                            } else if (!deviceInfo.mDataCallConnected) {
                                Log.d("DeviceInfo", "Data Disconnected, don't log");
                            } else if (j9 <= 0) {
                                Log.d("DeviceInfo", "No bytes to log");
                            } else {
                                TelephonyManager telephonyManager = deviceInfo.mTelMgr;
                                if (telephonyManager == null) {
                                    Log.d(
                                            "DeviceInfo",
                                            "failed logDataCall because mTelMgr is null");
                                } else {
                                    String str =
                                            telephonyManager.isNetworkRoaming()
                                                    ? "ROAMING"
                                                    : "NORMAL";
                                    String networkTypeName =
                                            deviceInfo.mTelMgr.getNetworkTypeName();
                                    if (!str.equals(deviceInfo.mDataCallLogLastStatus)
                                            || !networkTypeName.equals(
                                                    deviceInfo.mDataCallLogLastNetType)) {
                                        deviceInfo.mDataCallLogLastTime = 0L;
                                        deviceInfo.mDataCallLogLastValue = 0L;
                                    }
                                    deviceInfo.mDataCallLogLastStatus = str;
                                    if (!networkTypeName.equals("UNKNOWN")) {
                                        deviceInfo.mDataCallLogLastNetType = networkTypeName;
                                    }
                                    deviceInfo.mDataCallLogLastValue += j9;
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(
                                            "dataCallDate",
                                            Long.valueOf(deviceInfo.mDataCallLogLastTime));
                                    contentValues.put(
                                            "dataCallStatus", deviceInfo.mDataCallLogLastStatus);
                                    contentValues.put(
                                            "dataCallNetType", deviceInfo.mDataCallLogLastNetType);
                                    deviceInfo.mDataCallLogLastTime =
                                            Calendar.getInstance().getTimeInMillis();
                                    ContentValues contentValues2 = new ContentValues();
                                    contentValues2.put(
                                            "dataCallDate",
                                            Long.valueOf(deviceInfo.mDataCallLogLastTime));
                                    contentValues2.put(
                                            "dataCallStatus", deviceInfo.mDataCallLogLastStatus);
                                    contentValues2.put(
                                            "dataCallNetType", deviceInfo.mDataCallLogLastNetType);
                                    contentValues2.put(
                                            "dataCallBytes",
                                            Long.valueOf(deviceInfo.mDataCallLogLastValue));
                                    deviceInfo.mEdmStorageProvider.putValues(
                                            "DATACALLLOG", contentValues2, contentValues);
                                }
                            }
                        }
                        deviceInfo
                                .getEDM$8()
                                .getPhoneRestrictionPolicy()
                                .updateDateAndDataCallCounters(j9);
                        if (deviceInfo.mDataStatsCounter >= 10) {
                            ContentValues contentValues3 = new ContentValues();
                            contentValues3.put(
                                    "deviceWifiSent", Long.valueOf(deviceInfo.mStorageWifiTx));
                            contentValues3.put(
                                    "deviceWifiReceived", Long.valueOf(deviceInfo.mStorageWifiRx));
                            contentValues3.put(
                                    "deviceNetworkSent", Long.valueOf(deviceInfo.mStorageMobileTx));
                            contentValues3.put(
                                    "deviceNetworkReceived",
                                    Long.valueOf(deviceInfo.mStorageMobileRx));
                            deviceInfo.mEdmStorageProvider.putValues("DEVICE", contentValues3);
                            deviceInfo.mDataStatsCounter = 0;
                        }
                        DeviceInfo deviceInfo2 = DeviceInfo.this;
                        if (deviceInfo2.mDataUsageTimerActivated) {
                            deviceInfo2.mDataUsageEventsHandler.postDelayed(
                                    this, deviceInfo2.mDataUsageTimer);
                        }
                    }
                };
        this.mEDM = null;
        final int i3 = 2;
        BroadcastReceiver broadcastReceiver3 =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.enterprise.device.DeviceInfo.1
                    public final /* synthetic */ DeviceInfo this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        ContentValues contentValues;
                        boolean z;
                        switch (i3) {
                            case 0:
                                if (intent.getAction()
                                        .equals("android.net.conn.DATA_ACTIVITY_CHANGE")) {
                                    if (!intent.getBooleanExtra("isActive", false)) {
                                        DeviceInfo deviceInfo = this.this$0;
                                        deviceInfo.mDataCallLogLastTime = 0L;
                                        deviceInfo.mDataCallLogLastValue = 0L;
                                        deviceInfo.mDataCallConnected = false;
                                        break;
                                    } else {
                                        this.this$0.mDataCallConnected = true;
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                Log.d("DeviceInfo", intent.getAction());
                                if (!intent.getAction()
                                        .equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                                    if (intent.getAction()
                                            .equals("android.intent.action.ACTION_SHUTDOWN")) {
                                        DeviceInfo deviceInfo2 = this.this$0;
                                        deviceInfo2.mDataStatsCounter = 10;
                                        deviceInfo2.mDataUsageEventsHandler.removeCallbacks(
                                                deviceInfo2.mDataStatisticsUpdateRun);
                                        DeviceInfo deviceInfo3 = this.this$0;
                                        if (deviceInfo3.mDataUsageTimerActivated) {
                                            deviceInfo3.mDataUsageEventsHandler.postDelayed(
                                                    deviceInfo3.mDataStatisticsUpdateRun, 0L);
                                            break;
                                        }
                                    }
                                } else {
                                    DeviceInfo deviceInfo4 = this.this$0;
                                    deviceInfo4.getClass();
                                    deviceInfo4.mLastUpdateWifiTx = DeviceInfo.getTrafficWifiTx();
                                    deviceInfo4.mLastUpdateWifiRx = DeviceInfo.getTrafficWifiRx();
                                    deviceInfo4.mLastUpdateMobileTx =
                                            deviceInfo4.getTrafficMobileTx();
                                    deviceInfo4.mLastUpdateMobileRx =
                                            deviceInfo4.getTrafficMobileRx();
                                    deviceInfo4.mDataCallLogLastTime = 0L;
                                    int strictDataUsageTimer =
                                            deviceInfo4.getStrictDataUsageTimer();
                                    if (strictDataUsageTimer == 0) {
                                        strictDataUsageTimer = 3;
                                    }
                                    deviceInfo4.mDataUsageTimer = strictDataUsageTimer * 1000;
                                    deviceInfo4.mDataStatsEnabled =
                                            deviceInfo4.getDataCallStatisticsEnabled(null);
                                    deviceInfo4.mDataLogEnabled =
                                            deviceInfo4.getDataCallLoggingEnabled(null);
                                    deviceInfo4.mWifiStatsEnabled =
                                            deviceInfo4.getWifiStatisticEnabled(null);
                                    ArrayList dataByFields =
                                            deviceInfo4.mEdmStorageProvider.getDataByFields(
                                                    "DEVICE",
                                                    null,
                                                    null,
                                                    new String[] {
                                                        "deviceWifiSent",
                                                        "deviceWifiReceived",
                                                        "deviceNetworkSent",
                                                        "deviceNetworkReceived"
                                                    });
                                    if (!dataByFields.isEmpty()
                                            && (contentValues = (ContentValues) dataByFields.get(0))
                                                    != null) {
                                        try {
                                            deviceInfo4.mStorageWifiTx =
                                                    contentValues
                                                            .getAsLong("deviceWifiSent")
                                                            .longValue();
                                            deviceInfo4.mStorageWifiRx =
                                                    contentValues
                                                            .getAsLong("deviceWifiReceived")
                                                            .longValue();
                                            deviceInfo4.mStorageMobileTx =
                                                    contentValues
                                                            .getAsLong("deviceNetworkSent")
                                                            .longValue();
                                            deviceInfo4.mStorageMobileRx =
                                                    contentValues
                                                            .getAsLong("deviceNetworkReceived")
                                                            .longValue();
                                        } catch (NullPointerException unused) {
                                            Log.d(
                                                    "DeviceInfo",
                                                    "initializeStorageValues - Error reading from"
                                                        + " Device Storage");
                                            deviceInfo4.resetDataUsage(null);
                                        }
                                    }
                                    this.this$0.dataUsageTimerActivation(null);
                                    break;
                                }
                                break;
                            default:
                                if (this.this$0.isMMSCaptureEnabled(null)) {
                                    String action = intent.getAction();
                                    Bundle extras = intent.getExtras();
                                    if (action != null && extras != null) {
                                        if (!"com.samsung.mms.RECEIVED_MSG".equals(action)) {
                                            if (!"com.samsung.mms.SENT_MSG".equals(action)) {
                                                Log.d(
                                                        "DeviceInfo",
                                                        "Unexpected intent arrived at"
                                                            + " mMessagingReceiver");
                                                break;
                                            } else {
                                                z = false;
                                            }
                                        } else {
                                            z = true;
                                        }
                                        if ("mms".equals(extras.getString("msg_type"))) {
                                            String string = extras.getString("msg_address");
                                            long j = extras.getLong("date");
                                            String string2 = extras.getString("msg_subject");
                                            String string3 = extras.getString("msg_body");
                                            String string4 = extras.getString("content_location");
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(string4 == null ? "" : string4.concat(" "));
                                            sb.append(string2 == null ? "" : string2.concat(" "));
                                            if (string3 == null) {
                                                string3 = "";
                                            }
                                            sb.append(string3);
                                            this.this$0.storeMMS(
                                                    string, String.valueOf(j), sb.toString(), z);
                                            break;
                                        }
                                    } else {
                                        Log.d(
                                                "DeviceInfo",
                                                "No data arrived at mMessagingReceiver");
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        IntentFilter m =
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "android.intent.action.LOCKED_BOOT_COMPLETED",
                        "android.intent.action.ACTION_SHUTDOWN");
        this.mTelMgr = (TelephonyManager) context.getSystemService("phone");
        context.registerReceiver(broadcastReceiver2, m);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.mms.RECEIVED_MSG");
        intentFilter.addAction("com.samsung.mms.SENT_MSG");
        context.registerReceiver(
                broadcastReceiver3,
                intentFilter,
                "com.sec.mms.permission.RECEIVE_MESSAGES_INFORMATION",
                null,
                2);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.net.conn.DATA_ACTIVITY_CHANGE");
        context.registerReceiver(broadcastReceiver, intentFilter2, 2);
    }

    public static String getProcessorTypeinLine(String str) {
        if (!Pattern.matches("(?i:model)\\s*(?i:name).*:.*[a-zA-Z].*", str)
                && !Pattern.matches("(?i:processor).*:.*[a-zA-Z].*", str)) {
            return null;
        }
        String trim = str.trim();
        StringTokenizer stringTokenizer = new StringTokenizer(trim, ":");
        while (stringTokenizer.hasMoreTokens()) {
            trim = stringTokenizer.nextToken().trim();
        }
        if (Pattern.matches("^[0-9]+$", trim)) {
            return null;
        }
        return trim;
    }

    public static String getString(String str) {
        try {
            String str2 = SystemProperties.get(str, "unknown");
            if (str2.equalsIgnoreCase("unknown")) {
                return null;
            }
            return str2;
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not get property");
            return null;
        }
    }

    public static long getTrafficWifiRx() {
        long mobileRxBytes = TrafficStats.getMobileRxBytes();
        long totalRxBytes = TrafficStats.getTotalRxBytes();
        if (-1 == totalRxBytes) {
            totalRxBytes = 0;
        }
        if (-1 == mobileRxBytes) {
            mobileRxBytes = 0;
        }
        return totalRxBytes - mobileRxBytes;
    }

    public static long getTrafficWifiTx() {
        long mobileTxBytes = TrafficStats.getMobileTxBytes();
        long totalTxBytes = TrafficStats.getTotalTxBytes();
        if (-1 == totalTxBytes) {
            totalTxBytes = 0;
        }
        if (-1 == mobileTxBytes) {
            mobileTxBytes = 0;
        }
        return totalTxBytes - mobileTxBytes;
    }

    public static boolean isCorrectAdmin(int i, ContentValues contentValues, String str) {
        if (contentValues.get(str) == null) {
            return true;
        }
        for (String str2 : contentValues.get(str).toString().split(";")) {
            if (i == Integer.parseInt(str2)) {
                return true;
            }
        }
        return false;
    }

    public final boolean clearCallingLog(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission =
                enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return clearLog(
                    enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid,
                    "CallingLog",
                    "callingCaptureAdmin",
                    false);
        }
        return false;
    }

    public final boolean clearLog(int i, String str, String str2, boolean z) {
        try {
            Iterator it =
                    ((ArrayList) this.mEdmStorageProvider.getValues(str, null, null)).iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (contentValues.get(str2) != null) {
                    String[] split = contentValues.get(str2).toString().split(";");
                    if (split.length == 1 && i == Integer.parseInt(split[0])) {
                        if (this.mEdmStorageProvider.delete(str, contentValues) <= 0) {
                            return false;
                        }
                    } else if (split.length > 1) {
                        StringBuilder sb = new StringBuilder();
                        for (String str3 : split) {
                            if (i != Integer.parseInt(str3)) {
                                sb.append(str3);
                                sb.append(";");
                            }
                        }
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put(str2, sb.toString());
                        if (!this.mEdmStorageProvider.put(str, contentValues2, contentValues)) {
                            return false;
                        }
                    } else {
                        continue;
                    }
                } else if (!z && this.mEdmStorageProvider.delete(str, contentValues) <= 0) {
                    return false;
                }
            }
            return true;
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not write log edm database");
            return false;
        }
    }

    public final boolean clearMMSLog(ContextInfo contextInfo) {
        return clearLog(
                enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo).mCallerUid,
                "MMS",
                "mmsCaptureAdmin",
                false);
    }

    public final boolean clearSMSLog(ContextInfo contextInfo) {
        return clearLog(
                enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo).mCallerUid,
                "SMS",
                "smsCaptureAdmin",
                false);
    }

    public final void dataUsageTimerActivation(ContextInfo contextInfo) {
        boolean z =
                this.mWifiStatsEnabled
                        || this.mDataStatsEnabled
                        || this.mDataLogEnabled
                        || getEDM$8().getPhoneRestrictionPolicy().getDataCallLimitEnabled();
        if (!z || this.mDataUsageTimerActivated) {
            if (z || !this.mDataUsageTimerActivated) {
                return;
            }
            this.mDataUsageTimerActivated = false;
            this.mDataUsageEventsHandler.removeCallbacks(this.mDataStatisticsUpdateRun);
            return;
        }
        this.mDataUsageTimerActivated = true;
        this.mLastUpdateWifiTx = getTrafficWifiTx();
        this.mLastUpdateWifiRx = getTrafficWifiRx();
        this.mLastUpdateMobileTx = getTrafficMobileTx();
        this.mLastUpdateMobileRx = getTrafficMobileRx();
        this.mDataCallLogLastTime = 0L;
        this.mDataUsageEventsHandler.postDelayed(
                this.mDataStatisticsUpdateRun, this.mDataUsageTimer);
    }

    public final boolean enableCallingCapture(ContextInfo contextInfo, boolean z) {
        int i = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo).mCallerUid;
        if (this.mTelMgr.isVoiceCapable()) {
            return this.mEdmStorageProvider.putBoolean("MISC", i, z, 0, "CallingLogEnabled");
        }
        return false;
    }

    public final boolean enableMMSCapture(ContextInfo contextInfo, boolean z) {
        try {
            return this.mEdmStorageProvider.putBoolean(
                    "MISC",
                    enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo).mCallerUid,
                    z,
                    0,
                    "mmsLogEnabled");
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not enable mms capture");
            return false;
        }
    }

    public final boolean enableSMSCapture(ContextInfo contextInfo, boolean z) {
        try {
            return this.mEdmStorageProvider.putBoolean(
                    "MISC",
                    enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo).mCallerUid,
                    z,
                    0,
                    "smsLogEnabled");
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not enable sms capture");
            return false;
        }
    }

    public final ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission(ContextInfo contextInfo) {
        return getEDM$8()
                .enforceOwnerOnlyAndActiveAdminPermission(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_INVENTORY")));
    }

    public final long getAvailableCapacityExternal(ContextInfo contextInfo) {
        try {
            String externalSdCardPath =
                    ((StorageManagerAdapter)
                                    ((IStorageManagerAdapter)
                                            AdapterRegistry.mAdapterHandles.get(
                                                    IStorageManagerAdapter.class)))
                            .getExternalSdCardPath();
            File file = null;
            String volumeState =
                    externalSdCardPath == null
                            ? null
                            : StorageManagerAdapter.mStorageManager.getVolumeState(
                                    externalSdCardPath);
            if (!(volumeState == null ? false : volumeState.equals("mounted"))) {
                return -1L;
            }
            String externalSdCardPath2 =
                    ((StorageManagerAdapter)
                                    ((IStorageManagerAdapter)
                                            AdapterRegistry.mAdapterHandles.get(
                                                    IStorageManagerAdapter.class)))
                            .getExternalSdCardPath();
            if (externalSdCardPath2 != null) {
                file = new File(externalSdCardPath2);
            }
            if (file == null) {
                return -1L;
            }
            StatFs statFs = new StatFs(file.getPath());
            return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public final long getAvailableCapacityInternal(ContextInfo contextInfo) {
        String path;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            long availableBlocksLong = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
            ((StorageManagerAdapter)
                            ((IStorageManagerAdapter)
                                    AdapterRegistry.mAdapterHandles.get(
                                            IStorageManagerAdapter.class)))
                    .getClass();
            StorageVolume[] volumeList = StorageManagerAdapter.mStorageManager.getVolumeList();
            File file = null;
            if (volumeList == null || volumeList.length <= 0 || volumeList[0].getPath() == null) {
                path = null;
            } else {
                StorageVolume storageVolume = volumeList[0];
                path = storageVolume.getSubSystem().equals("fuse") ? "/" : storageVolume.getPath();
            }
            if (path != null) {
                file = new File(path);
            }
            if (file == null) {
                return -1L;
            }
            StatFs statFs2 = new StatFs(file.getPath());
            return (statFs2.getAvailableBlocksLong() * statFs2.getBlockSizeLong())
                    + availableBlocksLong;
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public final long getAvailableRamMemory(ContextInfo contextInfo) {
        long[] jArr = new long[2];
        Process.readProcLines("/proc/meminfo", new String[] {"MemFree:", "Cached:"}, jArr);
        for (int i = 0; i < 2; i++) {
            jArr[i] = jArr[i] * 1024;
        }
        return jArr[0] + jArr[1];
    }

    public final long getBytesReceivedNetwork(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        return this.mStorageMobileRx;
    }

    public final long getBytesReceivedWiFi(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        return this.mStorageWifiRx;
    }

    public final long getBytesSentNetwork(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        return this.mStorageMobileTx;
    }

    public final long getBytesSentWiFi(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        return this.mStorageWifiTx;
    }

    public final int getCallsCount(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return -1;
        }
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser(0, str);
        if (genericValueAsUser == null) {
            return 0;
        }
        try {
            return Integer.parseInt(genericValueAsUser);
        } catch (NumberFormatException unused) {
            Log.w("DeviceInfo", "could not parse integer ");
            return -1;
        }
    }

    public final String getCellTowerCID(ContextInfo contextInfo) {
        GsmCellLocation gsmCellLocation;
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        TelephonyManager telephonyManager = this.mTelMgr;
        return Integer.toHexString(
                (telephonyManager == null
                                || (gsmCellLocation =
                                                (GsmCellLocation)
                                                        telephonyManager.getCellLocation())
                                        == null)
                        ? -1
                        : gsmCellLocation.getCid());
    }

    public final String getCellTowerLAC(ContextInfo contextInfo) {
        GsmCellLocation gsmCellLocation;
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        TelephonyManager telephonyManager = this.mTelMgr;
        return Integer.toHexString(
                (telephonyManager == null
                                || (gsmCellLocation =
                                                (GsmCellLocation)
                                                        telephonyManager.getCellLocation())
                                        == null)
                        ? -1
                        : gsmCellLocation.getLac());
    }

    public final String getCellTowerPSC(ContextInfo contextInfo) {
        GsmCellLocation gsmCellLocation;
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        TelephonyManager telephonyManager = this.mTelMgr;
        return Integer.toString(
                (telephonyManager == null
                                || (gsmCellLocation =
                                                (GsmCellLocation)
                                                        telephonyManager.getCellLocation())
                                        == null)
                        ? -1
                        : gsmCellLocation.getPsc());
    }

    public final String getCellTowerRSSI(ContextInfo contextInfo) {
        int cid;
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        TelephonyManager telephonyManager = this.mTelMgr;
        int i = 99;
        if (telephonyManager != null) {
            if (telephonyManager.getPhoneType() == 2) {
                try {
                    mSignalStrength = Integer.toString(0) + " dBm " + Integer.toString(0) + " asu";
                } catch (RuntimeException e) {
                    Log.e("DeviceInfo", "updateSignalStrength: " + e.getMessage());
                }
                return mSignalStrength;
            }
            GsmCellLocation gsmCellLocation = (GsmCellLocation) this.mTelMgr.getCellLocation();
            if (gsmCellLocation != null && (cid = gsmCellLocation.getCid()) != -1) {
                List neighboringCellInfo = this.mTelMgr.getNeighboringCellInfo();
                if (neighboringCellInfo != null && neighboringCellInfo.size() > 0) {
                    Iterator it = neighboringCellInfo.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        NeighboringCellInfo neighboringCellInfo2 = (NeighboringCellInfo) it.next();
                        if (neighboringCellInfo2.getCid() == cid) {
                            i = neighboringCellInfo2.getRssi();
                            break;
                        }
                    }
                } else {
                    Log.w("DeviceInfo", "Could not retrieve NeighboringCellInfo");
                    return mSignalStrength;
                }
            }
        }
        return Integer.toString(i);
    }

    public final List getDataCallLog(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        List values =
                this.mEdmStorageProvider.getValues(
                        "DATACALLLOG",
                        new String[] {
                            "dataCallDate", "dataCallStatus", "dataCallNetType", "dataCallBytes"
                        },
                        str != null
                                ? AccountManagerService$$ExternalSyntheticOutline0.m(
                                        "dataCallDate>=?", str)
                                : null);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) values;
        if (!arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                arrayList.add(
                        contentValues.getAsString("dataCallDate")
                                + ";"
                                + contentValues.getAsString("dataCallStatus")
                                + ";"
                                + contentValues.getAsString("dataCallNetType")
                                + ";"
                                + contentValues.getAsString("dataCallBytes"));
            }
        }
        return arrayList;
    }

    public final boolean getDataCallLoggingEnabled(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "PHONERESTRICTION", "enableDataCallLogging")
                        .iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                return booleanValue;
            }
        }
        return false;
    }

    public final boolean getDataCallStatisticsEnabled(ContextInfo contextInfo) {
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(
                                0, "PHONERESTRICTION", "enableWifiDataCallDataStatistic")
                        .iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                return booleanValue;
            }
        }
        return false;
    }

    public final int getDataUsageTimer(ContextInfo contextInfo) {
        try {
            return this.mEdmStorageProvider.getInt(
                    contextInfo.mCallerUid, 0, "MISC", "miscDataStatisticTimer");
        } catch (SettingNotFoundException e) {
            Log.d("DeviceInfo", "getDataUsageTimer could not read database");
            e.printStackTrace();
            return -1;
        }
    }

    public final String getDeviceMaker(ContextInfo contextInfo) {
        return getString("ro.product.manufacturer");
    }

    public final String getDeviceName(ContextInfo contextInfo) {
        String string =
                Settings.System.getString(this.mContext.getContentResolver(), "device_name");
        return string == null
                ? Settings.Global.getString(this.mContext.getContentResolver(), "device_name")
                : string;
    }

    public final String getDeviceOS(ContextInfo contextInfo) {
        try {
            return System.getProperty("os.name");
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not get property");
            return null;
        }
    }

    public final String getDeviceOSVersion(ContextInfo contextInfo) {
        try {
            return System.getProperty("os.version");
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not get property");
            return null;
        }
    }

    public final String getDevicePlatform(ContextInfo contextInfo) {
        String string = getString("ro.build.version.release");
        if (string != null) {
            return "Android ".concat(string);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0039 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getDeviceProcessorSpeed(com.samsung.android.knox.ContextInfo r4) {
        /*
            r3 = this;
            r3 = 0
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L23
            java.io.FileReader r0 = new java.io.FileReader     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L23
            java.lang.String r1 = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L23
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L23
            java.lang.String r3 = r4.readLine()     // Catch: java.lang.Throwable -> L1a java.lang.Exception -> L1c
            r4.close()     // Catch: java.lang.Exception -> L15
            goto L19
        L15:
            r4 = move-exception
            r4.printStackTrace()
        L19:
            return r3
        L1a:
            r3 = move-exception
            goto L37
        L1c:
            r3 = move-exception
            goto L27
        L1e:
            r4 = move-exception
            r2 = r4
            r4 = r3
            r3 = r2
            goto L37
        L23:
            r4 = move-exception
            r2 = r4
            r4 = r3
            r3 = r2
        L27:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L1a
            if (r4 == 0) goto L34
            r4.close()     // Catch: java.lang.Exception -> L30
            goto L34
        L30:
            r3 = move-exception
            r3.printStackTrace()
        L34:
            java.lang.String r3 = ""
            return r3
        L37:
            if (r4 == 0) goto L41
            r4.close()     // Catch: java.lang.Exception -> L3d
            goto L41
        L3d:
            r4 = move-exception
            r4.printStackTrace()
        L41:
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.device.DeviceInfo.getDeviceProcessorSpeed(com.samsung.android.knox.ContextInfo):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x001d, code lost:

       r4 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x001e, code lost:

       r4.printStackTrace();
    */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0045 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getDeviceProcessorType(com.samsung.android.knox.ContextInfo r4) {
        /*
            r3 = this;
            r3 = 0
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L34
            java.io.FileReader r0 = new java.io.FileReader     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L34
            java.lang.String r1 = "/proc/cpuinfo"
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L34
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L34
        Ld:
            java.lang.String r3 = r4.readLine()     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
            if (r3 == 0) goto L26
            java.lang.String r3 = getProcessorTypeinLine(r3)     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
            if (r3 == 0) goto Ld
            r4.close()     // Catch: java.lang.Exception -> L1d
            goto L21
        L1d:
            r4 = move-exception
            r4.printStackTrace()
        L21:
            return r3
        L22:
            r3 = move-exception
            goto L43
        L24:
            r3 = move-exception
            goto L38
        L26:
            r4.close()     // Catch: java.lang.Exception -> L2a
            goto L40
        L2a:
            r3 = move-exception
            r3.printStackTrace()
            goto L40
        L2f:
            r4 = move-exception
            r2 = r4
            r4 = r3
            r3 = r2
            goto L43
        L34:
            r4 = move-exception
            r2 = r4
            r4 = r3
            r3 = r2
        L38:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L22
            if (r4 == 0) goto L40
            r4.close()     // Catch: java.lang.Exception -> L2a
        L40:
            java.lang.String r3 = ""
            return r3
        L43:
            if (r4 == 0) goto L4d
            r4.close()     // Catch: java.lang.Exception -> L49
            goto L4d
        L49:
            r4 = move-exception
            r4.printStackTrace()
        L4d:
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.device.DeviceInfo.getDeviceProcessorType(com.samsung.android.knox.ContextInfo):java.lang.String");
    }

    public final int getDroppedCallsCount(ContextInfo contextInfo) {
        return getCallsCount(contextInfo, "dropped");
    }

    public final EnterpriseDeviceManager getEDM$8() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final List getInboundMMSCaptured(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission =
                enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getDataByFields(
                                    "MMS", new String[] {"mmsType"}, new String[] {"1"}, null)
                            .iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (isCorrectAdmin(
                        enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid,
                        contentValues,
                        "mmsCaptureAdmin")) {
                    arrayList.add(
                            "From:"
                                    + contentValues.get("mmsAddress")
                                    + " - TimeStamp:"
                                    + contentValues.get("mmsTimeStamp")
                                    + " - Body:"
                                    + contentValues.get("mmsBody"));
                }
            }
            return arrayList;
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
            return arrayList;
        }
    }

    public final List getInboundSMSCaptured(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission =
                enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getDataByFields(
                                    "SMS", new String[] {"smsType"}, new String[] {"1"}, null)
                            .iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (isCorrectAdmin(
                        enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid,
                        contentValues,
                        "smsCaptureAdmin")) {
                    arrayList.add(
                            "From:"
                                    + contentValues.get("smsAddress")
                                    + " - TimeStamp:"
                                    + contentValues.get("smsTimeStamp")
                                    + " - Body:"
                                    + contentValues.get("smsBody"));
                }
            }
            return arrayList;
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
            return arrayList;
        }
    }

    public final List getIncomingCallingCaptured(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission =
                enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getDataByFields(
                                    "CallingLog",
                                    new String[] {"callingType"},
                                    new String[] {"1"},
                                    null)
                            .iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (isCorrectAdmin(
                        enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid,
                        contentValues,
                        "callingCaptureAdmin")) {
                    arrayList.add(
                            "From:"
                                    + contentValues.get("callingAddress")
                                    + " - TimeStamp:"
                                    + contentValues.get("callingTimeStamp")
                                    + " - Duration:"
                                    + contentValues.get("callingDuration")
                                    + " - Status:"
                                    + contentValues.get("callingStatus"));
                }
            }
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getKnoxServiceId(com.samsung.android.knox.ContextInfo r9) {
        /*
            r8 = this;
            int r9 = android.os.Binder.getCallingUid()
            android.content.Context r0 = r8.mContext
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            java.lang.String[] r0 = r0.getPackagesForUid(r9)
            java.lang.String r1 = "DeviceInfo"
            java.lang.String r2 = ""
            if (r0 == 0) goto Lb1
            int r3 = r0.length
            if (r3 != 0) goto L19
            goto Lb1
        L19:
            r3 = 0
            r0 = r0[r3]
            int r4 = android.os.UserHandle.getUserId(r9)
            android.content.pm.IPackageManager r5 = android.app.AppGlobals.getPackageManager()
            java.lang.String r6 = "com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION"
            int r4 = r5.checkPermission(r6, r0, r4)     // Catch: android.os.RemoteException -> L2f
            if (r4 != 0) goto L33
            r4 = 1
            goto L34
        L2f:
            r4 = move-exception
            r4.printStackTrace()
        L33:
            r4 = r3
        L34:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "callingPackage "
            r5.<init>(r6)
            r5.append(r0)
            java.lang.String r6 = ", isKnoxInternalApp = "
            r5.append(r6)
            r5.append(r4)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r1, r5)
            java.lang.String r1 = "KnoxServiceIdTable"
            java.lang.String r5 = "serviceId"
            if (r4 == 0) goto L6d
            android.content.ContentValues r0 = new android.content.ContentValues
            r0.<init>()
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
            java.lang.String r3 = "adminUid"
            r0.put(r3, r9)
            com.android.server.enterprise.storage.EdmStorageProvider r8 = r8.mEdmStorageProvider
            java.lang.String r8 = r8.getString(r0, r1, r5)
            if (r8 != 0) goto L6c
            return r2
        L6c:
            return r8
        L6d:
            com.android.server.enterprise.storage.EdmStorageProvider r8 = r8.mEdmStorageProvider
            r9 = 0
            java.util.List r8 = r8.getValues(r1, r9, r9)
            java.util.ArrayList r8 = (java.util.ArrayList) r8
            boolean r9 = r8.isEmpty()
            if (r9 != 0) goto Lb0
            java.util.Iterator r8 = r8.iterator()
        L80:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto Lb0
            java.lang.Object r9 = r8.next()
            android.content.ContentValues r9 = (android.content.ContentValues) r9
            java.lang.String r1 = "packageList"
            java.lang.String r1 = r9.getAsString(r1)
            if (r1 == 0) goto L80
            java.lang.String r4 = ","
            java.lang.String[] r1 = r1.split(r4)
            int r4 = r1.length
            r6 = r3
        L9d:
            if (r6 >= r4) goto L80
            r7 = r1[r6]
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto Lad
            java.lang.String r9 = r9.getAsString(r5)
            r2 = r9
            goto L80
        Lad:
            int r6 = r6 + 1
            goto L9d
        Lb0:
            return r2
        Lb1:
            java.lang.String r8 = "unable to find the packages for uid : "
            com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0.m(r9, r8, r1)
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.device.DeviceInfo.getKnoxServiceId(com.samsung.android.knox.ContextInfo):java.lang.String");
    }

    public final List getKnoxServicePackageList(ContextInfo contextInfo) {
        getEDM$8()
                .enforceActiveAdminPermissionByContext(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION")));
        int callingUid = Binder.getCallingUid();
        ArrayList arrayList = new ArrayList();
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(callingUid));
        ArrayList arrayList2 =
                (ArrayList)
                        this.mEdmStorageProvider.getValues(
                                "KnoxServiceIdTable", new String[] {"packageList"}, contentValues);
        if (!arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                String asString = ((ContentValues) it.next()).getAsString("packageList");
                if (!asString.isEmpty()) {
                    for (String str : asString.split(",")) {
                        arrayList.add(str);
                    }
                }
            }
        }
        return arrayList;
    }

    public final int getMissedCallsCount(ContextInfo contextInfo) {
        return getCallsCount(contextInfo, "missed");
    }

    public final String getModelName(ContextInfo contextInfo) {
        return getString("ro.product.name");
    }

    public final String getModelNumber(ContextInfo contextInfo) {
        return getString("ro.product.model");
    }

    public final String getModemFirmware(ContextInfo contextInfo) {
        return getString("gsm.version.baseband");
    }

    public final List getOutboundMMSCaptured(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission =
                enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getDataByFields(
                                    "MMS", new String[] {"mmsType"}, new String[] {"0"}, null)
                            .iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (isCorrectAdmin(
                        enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid,
                        contentValues,
                        "mmsCaptureAdmin")) {
                    arrayList.add(
                            "To:"
                                    + contentValues.get("mmsAddress")
                                    + " - TimeStamp:"
                                    + contentValues.get("mmsTimeStamp")
                                    + " - Body:"
                                    + contentValues.get("mmsBody"));
                }
            }
            return arrayList;
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
            return arrayList;
        }
    }

    public final List getOutboundSMSCaptured(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission =
                enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getDataByFields(
                                    "SMS", new String[] {"smsType"}, new String[] {"0"}, null)
                            .iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (isCorrectAdmin(
                        enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid,
                        contentValues,
                        "smsCaptureAdmin")) {
                    arrayList.add(
                            "To:"
                                    + contentValues.get("smsAddress")
                                    + " - TimeStamp:"
                                    + contentValues.get("smsTimeStamp")
                                    + " - Body:"
                                    + contentValues.get("smsBody"));
                }
            }
            return arrayList;
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
            return arrayList;
        }
    }

    public final List getOutgoingCallingCaptured(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission =
                enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getDataByFields(
                                    "CallingLog",
                                    new String[] {"callingType"},
                                    new String[] {"0"},
                                    null)
                            .iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (isCorrectAdmin(
                        enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid,
                        contentValues,
                        "callingCaptureAdmin")) {
                    arrayList.add(
                            "To:"
                                    + contentValues.get("callingAddress")
                                    + " - TimeStamp:"
                                    + contentValues.get("callingTimeStamp")
                                    + " - Duration:"
                                    + contentValues.get("callingDuration")
                                    + " - Status:"
                                    + contentValues.get("callingStatus"));
                }
            }
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not log edm database");
        }
        return arrayList;
    }

    public final int getPlatformSDK(ContextInfo contextInfo) {
        try {
            return SystemProperties.getInt("ro.build.version.sdk", -1);
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not get property");
            return -1;
        }
    }

    public final String getPlatformVersion(ContextInfo contextInfo) {
        return getString("ro.build.version.release");
    }

    public final String getPlatformVersionName(ContextInfo contextInfo) {
        return "UNKNOWN";
    }

    public final String getSalesCode(ContextInfo contextInfo) {
        String str = SystemProperties.get("ril.sales_code", "none");
        if ("none".equals(str)) {
            str = SystemProperties.get("ro.csc.sales_code", "BTU");
        }
        try {
            if ("MSK".contains(str)) {
                str = "SKT";
            } else if ("MKT/KTT".contains(str)) {
                str = "KT";
            } else if ("MLG/LGT".contains(str)) {
                str = "LG";
            } else if ("BST/SPR/VMU/XAS".contains(str)) {
                str = "SPR";
            } else if ("TFG".equals(str)) {
                String str2 = SystemProperties.get("ril.product_code", "none");
                if (!"none".equals(str2)) {
                    String substring = str2.substring(str2.length() - 3);
                    if ("TMM/UFN/UFU/COB/CHT/SAM/VMT/TGU/SAL/NBS/PBS/EBE/CRM".contains(substring)) {
                        try {
                            Log.d(
                                    "DeviceInfo",
                                    "SalesCode : Use product code as customerCode for Movistar"
                                        + " single binary(TFG)");
                            str = substring;
                        } catch (RuntimeException e) {
                            e = e;
                            str = substring;
                            Log.e(
                                    "DeviceInfo",
                                    "getSalesCode : RuntimeException : " + e.getMessage());
                            return str;
                        }
                    }
                }
            }
            Log.d("DeviceInfo", "SalesCode : return (" + str + ")");
        } catch (RuntimeException e2) {
            e = e2;
        }
        return str;
    }

    public final String getSerialNumber(ContextInfo contextInfo) {
        try {
            getEDM$8()
                    .enforceActiveAdminPermissionByContext(
                            contextInfo,
                            new ArrayList(
                                    Arrays.asList(
                                            "android.permission.READ_PRIVILEGED_PHONE_STATE")));
            String string = getString("ril.serialnumber");
            if (!TextUtils.isEmpty(string)) {
                if (string.equals("00000000000")) {}
                return string;
            }
            string = getString("ro.boot.serialno");
            return string;
        } catch (SecurityException unused) {
            return "00000000000";
        }
    }

    public final int getStrictDataUsageTimer() {
        int i = 0;
        Iterator it =
                this.mEdmStorageProvider
                        .getIntListAsUser(0, 0, "MISC", "miscDataStatisticTimer")
                        .iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (i == 0 || intValue < i) {
                i = intValue;
            }
        }
        if (i == 0) {
            return 3;
        }
        return i;
    }

    public final int getSuccessCallsCount(ContextInfo contextInfo) {
        return getCallsCount(contextInfo, "success");
    }

    public final long getTotalCapacityExternal(ContextInfo contextInfo) {
        try {
            String externalSdCardPath =
                    ((StorageManagerAdapter)
                                    ((IStorageManagerAdapter)
                                            AdapterRegistry.mAdapterHandles.get(
                                                    IStorageManagerAdapter.class)))
                            .getExternalSdCardPath();
            File file = null;
            String volumeState =
                    externalSdCardPath == null
                            ? null
                            : StorageManagerAdapter.mStorageManager.getVolumeState(
                                    externalSdCardPath);
            if (!(volumeState == null ? false : volumeState.equals("mounted"))) {
                return -1L;
            }
            String externalSdCardPath2 =
                    ((StorageManagerAdapter)
                                    ((IStorageManagerAdapter)
                                            AdapterRegistry.mAdapterHandles.get(
                                                    IStorageManagerAdapter.class)))
                            .getExternalSdCardPath();
            if (externalSdCardPath2 != null) {
                file = new File(externalSdCardPath2);
            }
            if (file == null) {
                return -1L;
            }
            StatFs statFs = new StatFs(file.getPath());
            return statFs.getBlockCountLong() * statFs.getBlockSizeLong();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public final long getTotalCapacityInternal(ContextInfo contextInfo) {
        String path;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            long blockCountLong = statFs.getBlockCountLong() * statFs.getBlockSizeLong();
            ((StorageManagerAdapter)
                            ((IStorageManagerAdapter)
                                    AdapterRegistry.mAdapterHandles.get(
                                            IStorageManagerAdapter.class)))
                    .getClass();
            StorageVolume[] volumeList = StorageManagerAdapter.mStorageManager.getVolumeList();
            File file = null;
            if (volumeList == null || volumeList.length <= 0 || volumeList[0].getPath() == null) {
                path = null;
            } else {
                StorageVolume storageVolume = volumeList[0];
                path = storageVolume.getSubSystem().equals("fuse") ? "/" : storageVolume.getPath();
            }
            if (path != null) {
                file = new File(path);
            }
            if (file == null) {
                return -1L;
            }
            StatFs statFs2 = new StatFs(file.getPath());
            return (statFs2.getBlockCountLong() * statFs2.getBlockSizeLong()) + blockCountLong;
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public final long getTotalRamMemory(ContextInfo contextInfo) {
        long[] jArr = {r0};
        Process.readProcLines("/proc/meminfo", new String[] {"MemTotal:"}, jArr);
        long j = jArr[0] * 1024;
        return j;
    }

    public final long getTrafficMobileRx() {
        long mobileRxBytes = TrafficStats.getMobileRxBytes();
        if (this.mLastUpdateMobileRx > 0 && !this.mTelMgr.isDataEnabled()) {
            mobileRxBytes = this.mLastUpdateMobileRx;
        }
        if (-1 == mobileRxBytes) {
            return 0L;
        }
        return mobileRxBytes;
    }

    public final long getTrafficMobileTx() {
        long mobileTxBytes = TrafficStats.getMobileTxBytes();
        if (this.mLastUpdateMobileTx > 0 && !this.mTelMgr.isDataEnabled()) {
            mobileTxBytes = this.mLastUpdateMobileTx;
        }
        if (-1 == mobileTxBytes) {
            return 0L;
        }
        return mobileTxBytes;
    }

    public final boolean getWifiStatisticEnabled(ContextInfo contextInfo) {
        Iterator it =
                this.mEdmStorageProvider
                        .getBooleanListAsUser(0, "MISC", "enableWifiDataStatistic")
                        .iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                return booleanValue;
            }
        }
        return false;
    }

    public final boolean isCallingCaptureEnabled(ContextInfo contextInfo) {
        if (Binder.getCallingUid() != 1001) {
            enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        }
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getBooleanListAsUser(0, "MISC", "CallingLogEnabled")
                            .iterator();
            while (it.hasNext()) {
                if (((Boolean) it.next()).booleanValue()) {
                    return true;
                }
            }
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
        }
        return false;
    }

    public final boolean isDeviceLocked(ContextInfo contextInfo) {
        try {
            return ((KeyguardManager) this.mContext.getSystemService("keyguard"))
                    .isKeyguardLocked();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isDeviceSecure(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                boolean isSecure =
                        new LockPatternUtils(this.mContext).isSecure(callingOrCurrentUserId);
                Log.d("DeviceInfo", "isDeviceSecure " + isSecure);
                return isSecure;
            } catch (Exception e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isMMSCaptureEnabled(ContextInfo contextInfo) {
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getBooleanListAsUser(0, "MISC", "mmsLogEnabled")
                            .iterator();
            while (it.hasNext()) {
                if (((Boolean) it.next()).booleanValue()) {
                    return true;
                }
            }
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
        }
        return false;
    }

    public final boolean isSMSCaptureEnabled(ContextInfo contextInfo) {
        if (Binder.getCallingUid() != 1001) {
            enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        }
        try {
            Iterator it =
                    this.mEdmStorageProvider
                            .getBooleanListAsUser(0, "MISC", "smsLogEnabled")
                            .iterator();
            while (it.hasNext()) {
                if (((Boolean) it.next()).booleanValue()) {
                    return true;
                }
            }
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
        }
        return false;
    }

    public final boolean isWifiStateEnabled() {
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService("wifi");
        }
        WifiManager wifiManager = this.mWifiManager;
        return wifiManager != null && wifiManager.getWifiState() == 3;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        ContextInfo contextInfo = new ContextInfo(Binder.getCallingUid());
        this.mWifiStatsEnabled = getWifiStatisticEnabled(contextInfo);
        this.mDataStatsEnabled = getDataCallStatisticsEnabled(contextInfo);
        this.mDataLogEnabled = getDataCallLoggingEnabled(contextInfo);
        dataUsageTimerActivation(contextInfo);
        clearLog(i, "CallingLog", "callingCaptureAdmin", true);
        clearLog(i, "SMS", "smsCaptureAdmin", true);
        clearLog(i, "MMS", "mmsCaptureAdmin", true);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {}

    public final boolean resetCallsCount(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        return this.mEdmStorageProvider.putGenericValueAsUser(0, "dropped", "0")
                & this.mEdmStorageProvider.putGenericValueAsUser(0, "success", "0")
                & this.mEdmStorageProvider.putGenericValueAsUser(0, "missed", "0");
    }

    public final boolean resetDataCallLogging(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        this.mDataCallLogLastTime = 0L;
        this.mDataCallLogLastValue = 0L;
        ContentValues m =
                str != null
                        ? AccountManagerService$$ExternalSyntheticOutline0.m("dataCallDate<=?", str)
                        : null;
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        SQLiteDatabase writableDatabase = edmStorageProvider.mEdmDbHelper.getWritableDatabase();
        if (m == null) {
            if (writableDatabase.delete("DATACALLLOG", "1", null) < 0) {
                return false;
            }
        } else if (edmStorageProvider.delete("DATACALLLOG", m) <= 0) {
            return false;
        }
        return true;
    }

    public final void resetDataUsage(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        this.mDataUsageEventsHandler.removeCallbacks(this.mDataStatisticsUpdateRun);
        this.mStorageWifiTx = 0L;
        this.mStorageWifiRx = 0L;
        this.mStorageMobileTx = 0L;
        this.mStorageMobileRx = 0L;
        ContentValues contentValues = new ContentValues();
        contentValues.put("deviceWifiSent", Long.valueOf(this.mStorageWifiTx));
        contentValues.put("deviceWifiReceived", Long.valueOf(this.mStorageWifiRx));
        contentValues.put("deviceNetworkSent", Long.valueOf(this.mStorageMobileTx));
        contentValues.put("deviceNetworkReceived", Long.valueOf(this.mStorageMobileRx));
        this.mEdmStorageProvider.putValues("DEVICE", contentValues);
        this.mLastUpdateWifiTx = getTrafficWifiTx();
        this.mLastUpdateWifiRx = getTrafficWifiRx();
        this.mLastUpdateMobileTx = getTrafficMobileTx();
        this.mLastUpdateMobileRx = getTrafficMobileRx();
        if (this.mDataUsageTimerActivated) {
            this.mDataUsageEventsHandler.postDelayed(this.mDataStatisticsUpdateRun, 0L);
        }
    }

    public final boolean setDataCallLoggingEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission =
                enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "PHONERESTRICTION",
                        enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid,
                        z,
                        0,
                        "enableDataCallLogging");
        if (putBoolean) {
            this.mDataLogEnabled =
                    getDataCallLoggingEnabled(enforceOwnerOnlyAndDeviceInventoryPermission);
            dataUsageTimerActivation(enforceOwnerOnlyAndDeviceInventoryPermission);
        }
        return putBoolean;
    }

    public final boolean setDataCallStatisticsEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission =
                enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "PHONERESTRICTION",
                        enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid,
                        z,
                        0,
                        "enableWifiDataCallDataStatistic");
        if (putBoolean) {
            this.mDataStatsEnabled =
                    getDataCallStatisticsEnabled(enforceOwnerOnlyAndDeviceInventoryPermission);
            dataUsageTimerActivation(enforceOwnerOnlyAndDeviceInventoryPermission);
        }
        return putBoolean;
    }

    public final boolean setDataUsageTimer(ContextInfo contextInfo, int i) {
        int i2 = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo).mCallerUid;
        this.mDataUsageEventsHandler.removeCallbacks(this.mDataStatisticsUpdateRun);
        if (i < 1 || i > 60) {
            i = 3;
        }
        boolean putInt =
                this.mEdmStorageProvider.putInt(i2, 0, i, "MISC", "miscDataStatisticTimer");
        if (putInt) {
            this.mDataUsageTimer = getStrictDataUsageTimer() * 1000;
        }
        if (this.mDataUsageTimerActivated) {
            this.mDataUsageEventsHandler.postDelayed(
                    this.mDataStatisticsUpdateRun, this.mDataUsageTimer);
        }
        return putInt;
    }

    public final boolean setKnoxServiceId(ContextInfo contextInfo, List list, String str) {
        getEDM$8()
                .enforceActiveAdminPermissionByContext(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION")));
        if (list == null || list.size() == 0 || TextUtils.isEmpty(str)) {
            Log.d("DeviceInfo", "packageList or serviceId is null");
            return false;
        }
        int callingUid = Binder.getCallingUid();
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getValues(
                                "KnoxServiceIdTable",
                                new String[] {"adminUid", "packageList"},
                                null);
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                String asString = contentValues.getAsString("packageList");
                int intValue = contentValues.getAsInteger("adminUid").intValue();
                if (asString != null) {
                    List asList = Arrays.asList(asString.split(","));
                    Iterator it2 = list.iterator();
                    while (it2.hasNext()) {
                        String str2 = (String) it2.next();
                        if (asList != null && asList.contains(str2) && intValue != callingUid) {
                            Log.i("DeviceInfo", str2 + " already stored by uid " + intValue);
                            return false;
                        }
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        Iterator it3 = list.iterator();
        while (it3.hasNext()) {
            String str3 = (String) it3.next();
            if (str3 != null && str3.length() > 0) {
                sb.append(str3.trim() + ",");
            }
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(callingUid));
        contentValues2.put("packageList", sb.toString());
        contentValues2.put("serviceId", str);
        ContentValues contentValues3 = new ContentValues();
        contentValues3.put("adminUid", Integer.valueOf(callingUid));
        if (this.mEdmStorageProvider.putValues(
                "KnoxServiceIdTable", contentValues2, contentValues3)) {
            return true;
        }
        Log.d("DeviceInfo", "setKnoxServiceId() fail");
        return false;
    }

    public final boolean setWifiStatisticEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission =
                enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        boolean putBoolean =
                this.mEdmStorageProvider.putBoolean(
                        "MISC",
                        enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid,
                        z,
                        0,
                        "enableWifiDataStatistic");
        if (putBoolean) {
            this.mWifiStatsEnabled =
                    getWifiStatisticEnabled(enforceOwnerOnlyAndDeviceInventoryPermission);
            dataUsageTimerActivation(enforceOwnerOnlyAndDeviceInventoryPermission);
        }
        return putBoolean;
    }

    public final void storeCalling(String str, String str2, String str3, String str4, boolean z) {
        if (Binder.getCallingUid() != 1001) {
            throw new SecurityException("Can only be called by internal phone");
        }
        if (this.mTelMgr.isVoiceCapable()) {
            ContentValues contentValues = new ContentValues();
            ContentValues contentValues2 = new ContentValues();
            StringBuilder sb = new StringBuilder();
            try {
                contentValues2.put("CallingLogEnabled", (Integer) 1);
                Iterator it =
                        ((ArrayList)
                                        this.mEdmStorageProvider.getValues(
                                                "MISC", new String[] {"adminUid"}, contentValues2))
                                .iterator();
                while (it.hasNext()) {
                    sb.append(((ContentValues) it.next()).getAsString("adminUid"));
                    sb.append(";");
                }
                if (sb.toString().isEmpty()) {
                    return;
                }
                contentValues.put("callingType", z ? "1" : "0");
                contentValues.put("callingStatus", str4);
                contentValues.put("callingAddress", str);
                contentValues.put("callingTimeStamp", str2);
                contentValues.put("callingDuration", str3);
                contentValues.put("callingCaptureAdmin", sb.toString());
                this.mEdmStorageProvider.insertConfiguration("CallingLog", contentValues);
            } catch (Exception unused) {
                Log.w("DeviceInfo", "could not write log edm database");
            }
        }
    }

    public final void storeMMS(String str, String str2, String str3, boolean z) {
        if (Binder.getCallingPid() == Process.myPid()) {
            ContentValues contentValues = new ContentValues();
            ContentValues contentValues2 = new ContentValues();
            StringBuilder sb = new StringBuilder();
            try {
                contentValues2.put("mmsLogEnabled", (Integer) 1);
                Iterator it =
                        ((ArrayList)
                                        this.mEdmStorageProvider.getValues(
                                                "MISC", new String[] {"adminUid"}, contentValues2))
                                .iterator();
                while (it.hasNext()) {
                    sb.append(((ContentValues) it.next()).getAsString("adminUid"));
                    sb.append(";");
                }
                if (sb.toString().isEmpty()) {
                    return;
                }
                contentValues.put("mmsType", z ? "1" : "0");
                contentValues.put("mmsAddress", str);
                contentValues.put("mmsTimeStamp", str2);
                contentValues.put("mmsBody", str3);
                contentValues.put("mmsCaptureAdmin", sb.toString());
                this.mEdmStorageProvider.insertConfiguration("MMS", contentValues);
            } catch (Exception unused) {
                Log.w("DeviceInfo", "could not write log edm database");
            }
        }
    }

    public final void storeSMS(String str, String str2, String str3, boolean z) {
        if (Binder.getCallingUid() != 1001) {
            throw new SecurityException("Can only be called by internal phone");
        }
        ContentValues contentValues = new ContentValues();
        ContentValues contentValues2 = new ContentValues();
        StringBuilder sb = new StringBuilder();
        try {
            contentValues2.put("smsLogEnabled", (Integer) 1);
            Iterator it =
                    ((ArrayList)
                                    this.mEdmStorageProvider.getValues(
                                            "MISC", new String[] {"adminUid"}, contentValues2))
                            .iterator();
            while (it.hasNext()) {
                sb.append(((ContentValues) it.next()).getAsString("adminUid"));
                sb.append(";");
            }
            if (sb.toString().isEmpty()) {
                return;
            }
            contentValues.put("smsType", z ? "1" : "0");
            contentValues.put("smsAddress", str);
            contentValues.put("smsTimeStamp", str2);
            contentValues.put("smsBody", str3);
            contentValues.put("smsCaptureAdmin", sb.toString());
            this.mEdmStorageProvider.insertConfiguration("SMS", contentValues);
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not write log edm database");
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}
}
