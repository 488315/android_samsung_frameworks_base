package com.android.server.enterprise.device;

import android.app.AppGlobals;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.TrafficStats;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.Utils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.deviceinfo.IDeviceInfo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class DeviceInfo extends IDeviceInfo.Stub implements EnterpriseServiceCallback {
    public static String mSignalStrength = Integer.toString(99);
    public Context mContext;
    public EdmStorageProvider mEdmStorageProvider;
    public TelephonyManager mTelMgr;
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
    public Handler mDataUsageEventsHandler = new Handler();
    public long mDataCallLogLastTime = 0;
    public String mDataCallLogLastStatus = "";
    public String mDataCallLogLastNetType = "";
    public long mDataCallLogLastValue = 0;
    public boolean mDataCallConnected = false;
    public BroadcastReceiver mDataConnectionStateChangeReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.device.DeviceInfo.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.conn.DATA_ACTIVITY_CHANGE")) {
                if (intent.getBooleanExtra("isActive", false)) {
                    DeviceInfo.this.mDataCallConnected = true;
                    return;
                }
                DeviceInfo.this.mDataCallLogLastTime = 0L;
                DeviceInfo.this.mDataCallLogLastValue = 0L;
                DeviceInfo.this.mDataCallConnected = false;
            }
        }
    };
    public BroadcastReceiver mDataStatisticsReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.device.DeviceInfo.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.d("DeviceInfo", intent.getAction());
            if (intent.getAction().equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                DeviceInfo.this.dataUsageValuesInit();
                DeviceInfo.this.dataUsageTimerActivation(null);
            } else if (intent.getAction().equals("android.intent.action.ACTION_SHUTDOWN")) {
                DeviceInfo.this.mDataStatsCounter = 10;
                DeviceInfo.this.mDataUsageEventsHandler.removeCallbacks(DeviceInfo.this.mDataStatisticsUpdateRun);
                if (DeviceInfo.this.mDataUsageTimerActivated) {
                    DeviceInfo.this.mDataUsageEventsHandler.postDelayed(DeviceInfo.this.mDataStatisticsUpdateRun, 0L);
                }
            }
        }
    };
    public Runnable mDataStatisticsUpdateRun = new Runnable() { // from class: com.android.server.enterprise.device.DeviceInfo.3
        @Override // java.lang.Runnable
        public void run() {
            DeviceInfo.this.mDataUsageEventsHandler.removeCallbacks(this);
            DeviceInfo.this.updateDataStatisticsUsage();
            if (DeviceInfo.this.mDataUsageTimerActivated) {
                DeviceInfo.this.mDataUsageEventsHandler.postDelayed(this, DeviceInfo.this.mDataUsageTimer);
            }
        }
    };
    public EnterpriseDeviceManager mEDM = null;
    public BroadcastReceiver mMessagingReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.device.DeviceInfo.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            boolean z;
            String str;
            String str2;
            if (DeviceInfo.this.isMMSCaptureEnabled(null)) {
                String action = intent.getAction();
                Bundle extras = intent.getExtras();
                if (action == null || extras == null) {
                    Log.d("DeviceInfo", "No data arrived at mMessagingReceiver");
                    return;
                }
                if ("com.samsung.mms.RECEIVED_MSG".equals(action)) {
                    z = true;
                } else {
                    if (!"com.samsung.mms.SENT_MSG".equals(action)) {
                        Log.d("DeviceInfo", "Unexpected intent arrived at mMessagingReceiver");
                        return;
                    }
                    z = false;
                }
                if ("mms".equals(extras.getString("msg_type"))) {
                    String string = extras.getString("msg_address");
                    long j = extras.getLong("date");
                    String string2 = extras.getString("msg_subject");
                    String string3 = extras.getString("msg_body");
                    String string4 = extras.getString("content_location");
                    StringBuilder sb = new StringBuilder();
                    if (string4 == null) {
                        str = "";
                    } else {
                        str = string4 + " ";
                    }
                    sb.append(str);
                    if (string2 == null) {
                        str2 = "";
                    } else {
                        str2 = string2 + " ";
                    }
                    sb.append(str2);
                    if (string3 == null) {
                        string3 = "";
                    }
                    sb.append(string3);
                    DeviceInfo.this.storeMMS(string, String.valueOf(j), sb.toString(), z);
                }
            }
        }
    };

    public String getPlatformVersionName(ContextInfo contextInfo) {
        return "UNKNOWN";
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public DeviceInfo(Context context) {
        this.mEdmStorageProvider = null;
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        this.mTelMgr = (TelephonyManager) this.mContext.getSystemService("phone");
        this.mContext.registerReceiver(this.mDataStatisticsReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.samsung.mms.RECEIVED_MSG");
        intentFilter2.addAction("com.samsung.mms.SENT_MSG");
        this.mContext.registerReceiver(this.mMessagingReceiver, intentFilter2, "com.sec.mms.permission.RECEIVE_MESSAGES_INFORMATION", null);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.net.conn.DATA_ACTIVITY_CHANGE");
        this.mContext.registerReceiver(this.mDataConnectionStateChangeReceiver, intentFilter3);
    }

    public static void readProcLines(String str, String[] strArr, long[] jArr) {
        Process.readProcLines(str, strArr, jArr);
    }

    public boolean isDeviceSecure(ContextInfo contextInfo) {
        boolean z;
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                z = new LockPatternUtils(this.mContext).isSecure(callingOrCurrentUserId);
                Log.d("DeviceInfo", "isDeviceSecure " + z);
            } catch (Exception e) {
                e.printStackTrace();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = false;
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isDeviceLocked(ContextInfo contextInfo) {
        try {
            return ((KeyguardManager) this.mContext.getSystemService("keyguard")).isKeyguardLocked();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getTotalCapacityExternal(ContextInfo contextInfo) {
        return new DeviceStorageUtil(this.mContext).getTotalExternalMemorySize();
    }

    public long getAvailableCapacityExternal(ContextInfo contextInfo) {
        return new DeviceStorageUtil(this.mContext).getAvailableExternalMemorySize();
    }

    public long getTotalCapacityInternal(ContextInfo contextInfo) {
        return new DeviceStorageUtil(this.mContext).getTotalInternalMemorySize();
    }

    public long getAvailableCapacityInternal(ContextInfo contextInfo) {
        return new DeviceStorageUtil(this.mContext).getAvailableInternalMemorySize();
    }

    public String getModelName(ContextInfo contextInfo) {
        return getString("ro.product.name", false);
    }

    public String getModelNumber(ContextInfo contextInfo) {
        return getString("ro.product.model", false);
    }

    public String getDeviceName(ContextInfo contextInfo) {
        String string = Settings.System.getString(this.mContext.getContentResolver(), "device_name");
        return string == null ? Settings.Global.getString(this.mContext.getContentResolver(), "device_name") : string;
    }

    public String getSerialNumber(ContextInfo contextInfo) {
        try {
            enforceReadPrivilegedPhoneStatePermission(contextInfo);
            String string = getString("ril.serialnumber", false);
            if (!TextUtils.isEmpty(string) && !string.equals("00000000000")) {
                return string;
            }
            return getString("ro.boot.serialno", false);
        } catch (SecurityException unused) {
            return "00000000000";
        }
    }

    public final List getKnoxServiceIdData(String[] strArr, ContentValues contentValues) {
        return this.mEdmStorageProvider.getValues("KnoxServiceIdTable", strArr, contentValues);
    }

    public final boolean hasKnoxInternalExceptionPermission(String str, int i) {
        try {
            return AppGlobals.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION", str, i) == 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isDuplicatedPackage(List list, int i) {
        List<ContentValues> knoxServiceIdData = getKnoxServiceIdData(new String[]{"adminUid", "packageList"}, null);
        if (knoxServiceIdData == null || knoxServiceIdData.isEmpty()) {
            return false;
        }
        for (ContentValues contentValues : knoxServiceIdData) {
            String asString = contentValues.getAsString("packageList");
            int intValue = contentValues.getAsInteger("adminUid").intValue();
            if (asString != null) {
                List asList = Arrays.asList(asString.split(","));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    if (asList != null && asList.contains(str) && intValue != i) {
                        Log.i("DeviceInfo", str + " already stored by uid " + intValue);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean setKnoxServiceId(ContextInfo contextInfo, List list, String str) {
        enforceKnoxInternalExceptionPermission(contextInfo);
        if (list == null || list.size() == 0 || TextUtils.isEmpty(str)) {
            Log.d("DeviceInfo", "packageList or serviceId is null");
            return false;
        }
        int callingUid = Binder.getCallingUid();
        if (isDuplicatedPackage(list, callingUid)) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (str2 != null && str2.length() > 0) {
                sb.append(str2.trim() + ",");
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(callingUid));
        contentValues.put("packageList", sb.toString());
        contentValues.put("serviceId", str);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(callingUid));
        if (this.mEdmStorageProvider.putValues("KnoxServiceIdTable", contentValues, contentValues2)) {
            return true;
        }
        Log.d("DeviceInfo", "setKnoxServiceId() fail");
        return false;
    }

    public String getKnoxServiceId(ContextInfo contextInfo) {
        int callingUid = Binder.getCallingUid();
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(callingUid);
        String str = "";
        if (packagesForUid == null || packagesForUid.length == 0) {
            Log.d("DeviceInfo", "unable to find the packages for uid : " + callingUid);
            return "";
        }
        String str2 = packagesForUid[0];
        boolean hasKnoxInternalExceptionPermission = hasKnoxInternalExceptionPermission(str2, UserHandle.getUserId(callingUid));
        Log.d("DeviceInfo", "callingPackage " + str2 + ", isKnoxInternalApp = " + hasKnoxInternalExceptionPermission);
        if (hasKnoxInternalExceptionPermission) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("adminUid", Integer.valueOf(callingUid));
            String string = this.mEdmStorageProvider.getString("KnoxServiceIdTable", "serviceId", contentValues);
            return string == null ? "" : string;
        }
        List<ContentValues> knoxServiceIdData = getKnoxServiceIdData(null, null);
        if (knoxServiceIdData != null && !knoxServiceIdData.isEmpty()) {
            for (ContentValues contentValues2 : knoxServiceIdData) {
                String asString = contentValues2.getAsString("packageList");
                if (asString != null) {
                    String[] split = asString.split(",");
                    int length = split.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        if (split[i].equals(str2)) {
                            str = contentValues2.getAsString("serviceId");
                            break;
                        }
                        i++;
                    }
                }
            }
        }
        return str;
    }

    public List getKnoxServicePackageList(ContextInfo contextInfo) {
        enforceKnoxInternalExceptionPermission(contextInfo);
        int callingUid = Binder.getCallingUid();
        ArrayList arrayList = new ArrayList();
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(callingUid));
        List knoxServiceIdData = getKnoxServiceIdData(new String[]{"packageList"}, contentValues);
        if (knoxServiceIdData != null && !knoxServiceIdData.isEmpty()) {
            Iterator it = knoxServiceIdData.iterator();
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

    public String getModemFirmware(ContextInfo contextInfo) {
        return getString("gsm.version.baseband", false);
    }

    public int getPlatformSDK(ContextInfo contextInfo) {
        return getInt("ro.build.version.sdk");
    }

    public String getPlatformVersion(ContextInfo contextInfo) {
        return getString("ro.build.version.release", false);
    }

    public String getDeviceMaker(ContextInfo contextInfo) {
        return getString("ro.product.manufacturer", false);
    }

    public String getDeviceOS(ContextInfo contextInfo) {
        return getString("os.name", true);
    }

    public String getDeviceOSVersion(ContextInfo contextInfo) {
        return getString("os.version", true);
    }

    public String getDevicePlatform(ContextInfo contextInfo) {
        String string = getString("ro.build.version.release", false);
        if (string == null) {
            return null;
        }
        return "Android " + string;
    }

    public final int getCallsCount(ContextInfo contextInfo, String str) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return -1;
        }
        String genericValue = this.mEdmStorageProvider.getGenericValue(str);
        if (genericValue == null) {
            return 0;
        }
        try {
            return Integer.parseInt(genericValue);
        } catch (NumberFormatException unused) {
            Log.w("DeviceInfo", "could not parse integer ");
            return -1;
        }
    }

    public int getDroppedCallsCount(ContextInfo contextInfo) {
        return getCallsCount(contextInfo, "dropped");
    }

    public int getMissedCallsCount(ContextInfo contextInfo) {
        return getCallsCount(contextInfo, "missed");
    }

    public int getSuccessCallsCount(ContextInfo contextInfo) {
        return getCallsCount(contextInfo, "success");
    }

    public boolean resetCallsCount(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        return this.mEdmStorageProvider.putGenericValue("dropped", "0") & this.mEdmStorageProvider.putGenericValue("success", "0") & true & this.mEdmStorageProvider.putGenericValue("missed", "0");
    }

    public void addCallsCount(String str) {
        String genericValue = this.mEdmStorageProvider.getGenericValue(str);
        int i = 0;
        if (genericValue != null) {
            try {
                int parseInt = Integer.parseInt(genericValue);
                if (parseInt >= 0) {
                    i = parseInt;
                }
            } catch (NumberFormatException unused) {
                Log.w("DeviceInfo", "could not parse integer ");
            }
        }
        this.mEdmStorageProvider.putGenericValue(str, String.valueOf(i + 1));
    }

    public final String getProcessorTypeinLine(String str) {
        if (!Pattern.matches("(?i:model)\\s*(?i:name).*:.*[a-zA-Z].*", str) && !Pattern.matches("(?i:processor).*:.*[a-zA-Z].*", str)) {
            return null;
        }
        String trim = str.trim();
        StringTokenizer stringTokenizer = new StringTokenizer(trim, XmlUtils.STRING_ARRAY_SEPARATOR);
        while (stringTokenizer.hasMoreTokens()) {
            trim = stringTokenizer.nextToken().trim();
        }
        if (Pattern.matches("^[0-9]+$", trim)) {
            return null;
        }
        return trim;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x001d, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x001e, code lost:
    
        r3.printStackTrace();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String getDeviceProcessorType(ContextInfo contextInfo) {
        BufferedReader bufferedReader;
        String readLine;
        String processorTypeinLine;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"));
                } catch (Exception e) {
                    e = e;
                }
                do {
                    try {
                        readLine = bufferedReader.readLine();
                    } catch (Exception e2) {
                        e = e2;
                        bufferedReader2 = bufferedReader;
                        e.printStackTrace();
                        if (bufferedReader2 == null) {
                            return "";
                        }
                        bufferedReader2.close();
                        return "";
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader2 = bufferedReader;
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                    if (readLine == null) {
                        bufferedReader.close();
                        return "";
                    }
                    processorTypeinLine = getProcessorTypeinLine(readLine);
                } while (processorTypeinLine == null);
                bufferedReader.close();
                return processorTypeinLine;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            return "";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0038 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String getDeviceProcessorSpeed(ContextInfo contextInfo) {
        Throwable th;
        BufferedReader bufferedReader;
        Exception e;
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"));
                try {
                    String readLine = bufferedReader.readLine();
                    try {
                        bufferedReader.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    return readLine;
                } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                    if (bufferedReader == null) {
                        return "";
                    }
                    try {
                        bufferedReader.close();
                        return "";
                    } catch (Exception e4) {
                        e4.printStackTrace();
                        return "";
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (contextInfo != null) {
                    try {
                        contextInfo.close();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e6) {
            bufferedReader = null;
            e = e6;
        } catch (Throwable th3) {
            contextInfo = null;
            th = th3;
            if (contextInfo != null) {
            }
            throw th;
        }
    }

    public long getTotalRamMemory(ContextInfo contextInfo) {
        long[] jArr = {r0};
        readProcLines("/proc/meminfo", new String[]{"MemTotal:"}, jArr);
        long j = jArr[0] * 1024;
        return j;
    }

    public long getAvailableRamMemory(ContextInfo contextInfo) {
        long[] jArr = new long[2];
        readProcLines("/proc/meminfo", new String[]{"MemFree:", "Cached:"}, jArr);
        for (int i = 0; i < 2; i++) {
            jArr[i] = jArr[i] * 1024;
        }
        return jArr[0] + jArr[1];
    }

    public final String getString(String str, boolean z) {
        String str2 = null;
        try {
            if (z) {
                str2 = System.getProperty(str);
            } else {
                String str3 = SystemProperties.get(str, "unknown");
                if (!str3.equalsIgnoreCase("unknown")) {
                    str2 = str3;
                }
            }
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not get property");
        }
        return str2;
    }

    public final int getInt(String str) {
        try {
            return SystemProperties.getInt(str, -1);
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not get property");
            return -1;
        }
    }

    public boolean setWifiStatisticEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid, "MISC", "enableWifiDataStatistic", z);
        if (putBoolean) {
            this.mWifiStatsEnabled = getWifiStatisticEnabled(enforceOwnerOnlyAndDeviceInventoryPermission);
            dataUsageTimerActivation(enforceOwnerOnlyAndDeviceInventoryPermission);
        }
        return putBoolean;
    }

    public boolean getWifiStatisticEnabled(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanList("MISC", "enableWifiDataStatistic").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                return booleanValue;
            }
        }
        return false;
    }

    public boolean setDataCallStatisticsEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid, "PHONERESTRICTION", "enableWifiDataCallDataStatistic", z);
        if (putBoolean) {
            this.mDataStatsEnabled = getDataCallStatisticsEnabled(enforceOwnerOnlyAndDeviceInventoryPermission);
            dataUsageTimerActivation(enforceOwnerOnlyAndDeviceInventoryPermission);
        }
        return putBoolean;
    }

    public boolean getDataCallStatisticsEnabled(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", "enableWifiDataCallDataStatistic").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                return booleanValue;
            }
        }
        return false;
    }

    public long getBytesSentWiFi(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        return this.mStorageWifiTx;
    }

    public long getBytesReceivedWiFi(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        return this.mStorageWifiRx;
    }

    public long getBytesSentNetwork(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        return this.mStorageMobileTx;
    }

    public long getBytesReceivedNetwork(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        return this.mStorageMobileRx;
    }

    public void resetDataUsage(ContextInfo contextInfo) {
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

    public boolean setDataUsageTimer(ContextInfo contextInfo, int i) {
        int i2 = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo).mCallerUid;
        this.mDataUsageEventsHandler.removeCallbacks(this.mDataStatisticsUpdateRun);
        if (i < 1 || i > 60) {
            i = 3;
        }
        boolean putInt = this.mEdmStorageProvider.putInt(i2, "MISC", "miscDataStatisticTimer", i);
        if (putInt) {
            this.mDataUsageTimer = getStrictDataUsageTimer() * 1000;
        }
        if (this.mDataUsageTimerActivated) {
            this.mDataUsageEventsHandler.postDelayed(this.mDataStatisticsUpdateRun, this.mDataUsageTimer);
        }
        return putInt;
    }

    public int getDataUsageTimer(ContextInfo contextInfo) {
        try {
            return this.mEdmStorageProvider.getInt(contextInfo.mCallerUid, "MISC", "miscDataStatisticTimer");
        } catch (SettingNotFoundException e) {
            Log.d("DeviceInfo", "getDataUsageTimer could not read database");
            e.printStackTrace();
            return -1;
        }
    }

    public final int getStrictDataUsageTimer() {
        Iterator it = this.mEdmStorageProvider.getIntList("MISC", "miscDataStatisticTimer").iterator();
        int i = 0;
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

    public final long getTrafficWifiTx() {
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

    public final long getTrafficWifiRx() {
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

    public final void dataUsageValuesInit() {
        ContentValues contentValues;
        dataUsageValuesUpdate();
        int strictDataUsageTimer = getStrictDataUsageTimer();
        if (strictDataUsageTimer == 0) {
            strictDataUsageTimer = 3;
        }
        this.mDataUsageTimer = strictDataUsageTimer * 1000;
        this.mDataStatsEnabled = getDataCallStatisticsEnabled(null);
        this.mDataLogEnabled = getDataCallLoggingEnabled(null);
        this.mWifiStatsEnabled = getWifiStatisticEnabled(null);
        ArrayList dataByFields = this.mEdmStorageProvider.getDataByFields("DEVICE", null, null, new String[]{"deviceWifiSent", "deviceWifiReceived", "deviceNetworkSent", "deviceNetworkReceived"});
        if (dataByFields == null || dataByFields.isEmpty() || (contentValues = (ContentValues) dataByFields.get(0)) == null) {
            return;
        }
        try {
            this.mStorageWifiTx = contentValues.getAsLong("deviceWifiSent").longValue();
            this.mStorageWifiRx = contentValues.getAsLong("deviceWifiReceived").longValue();
            this.mStorageMobileTx = contentValues.getAsLong("deviceNetworkSent").longValue();
            this.mStorageMobileRx = contentValues.getAsLong("deviceNetworkReceived").longValue();
        } catch (NullPointerException unused) {
            Log.d("DeviceInfo", "initializeStorageValues - Error reading from Device Storage");
            resetDataUsage(null);
        }
    }

    public final void dataUsageValuesUpdate() {
        this.mLastUpdateWifiTx = getTrafficWifiTx();
        this.mLastUpdateWifiRx = getTrafficWifiRx();
        this.mLastUpdateMobileTx = getTrafficMobileTx();
        this.mLastUpdateMobileRx = getTrafficMobileRx();
        this.mDataCallLogLastTime = 0L;
    }

    public final boolean isWifiStateEnabled() {
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService("wifi");
        }
        WifiManager wifiManager = this.mWifiManager;
        return wifiManager != null && wifiManager.getWifiState() == 3;
    }

    public final long updateDataStatisticsUsage() {
        long j;
        long j2;
        long j3;
        long j4;
        this.mDataStatsCounter++;
        long trafficWifiTx = getTrafficWifiTx();
        long j5 = this.mLastUpdateWifiTx;
        if (trafficWifiTx > j5) {
            j = trafficWifiTx - j5;
            if (isWifiStateEnabled() && this.mWifiStatsEnabled) {
                this.mStorageWifiTx += j;
            }
        } else {
            j = 0;
        }
        this.mLastUpdateWifiTx = trafficWifiTx;
        long j6 = j + 0;
        long trafficWifiRx = getTrafficWifiRx();
        long j7 = this.mLastUpdateWifiRx;
        if (trafficWifiRx > j7) {
            j2 = trafficWifiRx - j7;
            if (isWifiStateEnabled() && this.mWifiStatsEnabled) {
                this.mStorageWifiRx += j2;
            }
        } else {
            j2 = 0;
        }
        this.mLastUpdateWifiRx = trafficWifiRx;
        long j8 = j6 + j2;
        long trafficMobileTx = getTrafficMobileTx();
        long j9 = this.mLastUpdateMobileTx;
        if (trafficMobileTx >= j9) {
            j3 = trafficMobileTx - j9;
            if (this.mDataStatsEnabled) {
                this.mStorageMobileTx += j3;
            }
        } else {
            this.mDataCallLogLastTime = 0L;
            this.mDataCallLogLastValue = 0L;
            j3 = 0;
        }
        this.mLastUpdateMobileTx = trafficMobileTx;
        long j10 = j3 + 0;
        long trafficMobileRx = getTrafficMobileRx();
        long j11 = this.mLastUpdateMobileRx;
        if (trafficMobileRx >= j11) {
            j4 = trafficMobileRx - j11;
            if (this.mDataStatsEnabled) {
                this.mStorageMobileRx += j4;
            }
        } else {
            this.mDataCallLogLastTime = 0L;
            this.mDataCallLogLastValue = 0L;
            j4 = 0;
        }
        this.mLastUpdateMobileRx = trafficMobileRx;
        long j12 = j10 + j4;
        if (j12 > 0) {
            logDataCall(j12);
        }
        getEDM().getPhoneRestrictionPolicy().updateDateAndDataCallCounters(j12);
        if (this.mDataStatsCounter >= 10) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("deviceWifiSent", Long.valueOf(this.mStorageWifiTx));
            contentValues.put("deviceWifiReceived", Long.valueOf(this.mStorageWifiRx));
            contentValues.put("deviceNetworkSent", Long.valueOf(this.mStorageMobileTx));
            contentValues.put("deviceNetworkReceived", Long.valueOf(this.mStorageMobileRx));
            this.mEdmStorageProvider.putValues("DEVICE", contentValues);
            this.mDataStatsCounter = 0;
        }
        return j8 + j12;
    }

    public void dataUsageTimerActivation(ContextInfo contextInfo) {
        boolean z = this.mWifiStatsEnabled || this.mDataStatsEnabled || this.mDataLogEnabled || getEDM().getPhoneRestrictionPolicy().getDataCallLimitEnabled();
        if (z && !this.mDataUsageTimerActivated) {
            this.mDataUsageTimerActivated = true;
            dataUsageValuesUpdate();
            this.mDataUsageEventsHandler.postDelayed(this.mDataStatisticsUpdateRun, this.mDataUsageTimer);
        } else {
            if (z || !this.mDataUsageTimerActivated) {
                return;
            }
            this.mDataUsageTimerActivated = false;
            this.mDataUsageEventsHandler.removeCallbacks(this.mDataStatisticsUpdateRun);
        }
    }

    public final void updateDataUsageState() {
        ContextInfo contextInfo = new ContextInfo(Binder.getCallingUid());
        this.mWifiStatsEnabled = getWifiStatisticEnabled(contextInfo);
        this.mDataStatsEnabled = getDataCallStatisticsEnabled(contextInfo);
        this.mDataLogEnabled = getDataCallLoggingEnabled(contextInfo);
        dataUsageTimerActivation(contextInfo);
    }

    public boolean setDataCallLoggingEnabled(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid, "PHONERESTRICTION", "enableDataCallLogging", z);
        if (putBoolean) {
            this.mDataLogEnabled = getDataCallLoggingEnabled(enforceOwnerOnlyAndDeviceInventoryPermission);
            dataUsageTimerActivation(enforceOwnerOnlyAndDeviceInventoryPermission);
        }
        return putBoolean;
    }

    public boolean getDataCallLoggingEnabled(ContextInfo contextInfo) {
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        Iterator it = this.mEdmStorageProvider.getBooleanList("PHONERESTRICTION", "enableDataCallLogging").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue) {
                return booleanValue;
            }
        }
        return false;
    }

    public boolean resetDataCallLogging(ContextInfo contextInfo, String str) {
        ContentValues contentValues;
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        this.mDataCallLogLastTime = 0L;
        this.mDataCallLogLastValue = 0L;
        if (str != null) {
            contentValues = new ContentValues();
            contentValues.put("dataCallDate<=?", str);
        } else {
            contentValues = null;
        }
        return this.mEdmStorageProvider.removeByFilterSmallerThan("DATACALLLOG", contentValues);
    }

    public List getDataCallLog(ContextInfo contextInfo, String str) {
        ContentValues contentValues;
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        String[] strArr = {"dataCallDate", "dataCallStatus", "dataCallNetType", "dataCallBytes"};
        if (str != null) {
            contentValues = new ContentValues();
            contentValues.put("dataCallDate>=?", str);
        } else {
            contentValues = null;
        }
        List<ContentValues> values = this.mEdmStorageProvider.getValues("DATACALLLOG", strArr, contentValues);
        if (values == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (!values.isEmpty()) {
            for (ContentValues contentValues2 : values) {
                arrayList.add(contentValues2.getAsString("dataCallDate") + KnoxVpnFirewallHelper.DELIMITER + contentValues2.getAsString("dataCallStatus") + KnoxVpnFirewallHelper.DELIMITER + contentValues2.getAsString("dataCallNetType") + KnoxVpnFirewallHelper.DELIMITER + contentValues2.getAsString("dataCallBytes"));
            }
        }
        return arrayList;
    }

    public boolean clearCallingLog(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        if (this.mTelMgr.isVoiceCapable()) {
            return clearLog(enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid, "CallingLog", "callingCaptureAdmin", false);
        }
        return false;
    }

    public final boolean logDataCall(long j) {
        if (!this.mDataLogEnabled) {
            Log.d("DeviceInfo", "Logging disabled");
            return false;
        }
        if (!this.mDataCallConnected) {
            Log.d("DeviceInfo", "Data Disconnected, don't log");
            return false;
        }
        if (j <= 0) {
            Log.d("DeviceInfo", "No bytes to log");
            return false;
        }
        TelephonyManager telephonyManager = this.mTelMgr;
        if (telephonyManager == null) {
            Log.d("DeviceInfo", "failed logDataCall because mTelMgr is null");
            return false;
        }
        String str = telephonyManager.isNetworkRoaming() ? "ROAMING" : "NORMAL";
        String networkTypeName = this.mTelMgr.getNetworkTypeName();
        if (!str.equals(this.mDataCallLogLastStatus) || !networkTypeName.equals(this.mDataCallLogLastNetType)) {
            this.mDataCallLogLastTime = 0L;
            this.mDataCallLogLastValue = 0L;
        }
        this.mDataCallLogLastStatus = str;
        if (!networkTypeName.equals("UNKNOWN")) {
            this.mDataCallLogLastNetType = networkTypeName;
        }
        this.mDataCallLogLastValue += j;
        ContentValues contentValues = new ContentValues();
        contentValues.put("dataCallDate", Long.valueOf(this.mDataCallLogLastTime));
        contentValues.put("dataCallStatus", this.mDataCallLogLastStatus);
        contentValues.put("dataCallNetType", this.mDataCallLogLastNetType);
        this.mDataCallLogLastTime = Calendar.getInstance().getTimeInMillis();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("dataCallDate", Long.valueOf(this.mDataCallLogLastTime));
        contentValues2.put("dataCallStatus", this.mDataCallLogLastStatus);
        contentValues2.put("dataCallNetType", this.mDataCallLogLastNetType);
        contentValues2.put("dataCallBytes", Long.valueOf(this.mDataCallLogLastValue));
        return this.mEdmStorageProvider.putValues("DATACALLLOG", contentValues2, contentValues);
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_INVENTORY")));
    }

    public final ContextInfo enforceReadPrivilegedPhoneStatePermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("android.permission.READ_PRIVILEGED_PHONE_STATE")));
    }

    public final void enforcePhone() {
        if (Binder.getCallingUid() != 1001) {
            throw new SecurityException("Can only be called by internal phone");
        }
    }

    public final void enforcePhoneAppOrOwnerAndDeviceInventoryPermission(ContextInfo contextInfo) {
        if (Binder.getCallingUid() != 1001) {
            enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        }
    }

    public final ContextInfo enforceKnoxInternalExceptionPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION")));
    }

    public final void clearDatabasesOnAdminRemoval(int i) {
        clearLog(i, "CallingLog", "callingCaptureAdmin", true);
        clearLog(i, "SMS", "smsCaptureAdmin", true);
        clearLog(i, "MMS", "mmsCaptureAdmin", true);
    }

    public final boolean clearLog(int i, String str, String str2, boolean z) {
        try {
            for (ContentValues contentValues : this.mEdmStorageProvider.getValues(str, (String[]) null, (ContentValues) null)) {
                if (contentValues.get(str2) != null) {
                    String[] split = contentValues.get(str2).toString().split(KnoxVpnFirewallHelper.DELIMITER);
                    if (split.length == 1 && i == Integer.parseInt(split[0])) {
                        if (this.mEdmStorageProvider.delete(str, contentValues) <= 0) {
                            return false;
                        }
                    } else if (split.length > 1) {
                        StringBuilder sb = new StringBuilder();
                        for (String str3 : split) {
                            if (i != Integer.parseInt(str3)) {
                                sb.append(str3);
                                sb.append(KnoxVpnFirewallHelper.DELIMITER);
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

    public final boolean isCorrectAdmin(int i, ContentValues contentValues, String str) {
        if (contentValues.get(str) == null) {
            return true;
        }
        for (String str2 : contentValues.get(str).toString().split(KnoxVpnFirewallHelper.DELIMITER)) {
            if (i == Integer.parseInt(str2)) {
                return true;
            }
        }
        return false;
    }

    public boolean enableSMSCapture(ContextInfo contextInfo, boolean z) {
        try {
            return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo).mCallerUid, "MISC", "smsLogEnabled", z);
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not enable sms capture");
            return false;
        }
    }

    public boolean isSMSCaptureEnabled(ContextInfo contextInfo) {
        enforcePhoneAppOrOwnerAndDeviceInventoryPermission(contextInfo);
        try {
            Iterator it = this.mEdmStorageProvider.getBooleanList("MISC", "smsLogEnabled").iterator();
            while (it.hasNext()) {
                if (((Boolean) it.next()).booleanValue()) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
            return false;
        }
    }

    public List getOutboundSMSCaptured(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it = this.mEdmStorageProvider.getDataByFields("SMS", new String[]{"smsType"}, new String[]{"0"}, null).iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (isCorrectAdmin(enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid, contentValues, "smsCaptureAdmin")) {
                    arrayList.add("To:" + contentValues.get("smsAddress") + " - TimeStamp:" + contentValues.get("smsTimeStamp") + " - Body:" + contentValues.get("smsBody"));
                }
            }
            return arrayList;
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
            return arrayList;
        }
    }

    public List getInboundSMSCaptured(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it = this.mEdmStorageProvider.getDataByFields("SMS", new String[]{"smsType"}, new String[]{"1"}, null).iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (isCorrectAdmin(enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid, contentValues, "smsCaptureAdmin")) {
                    arrayList.add("From:" + contentValues.get("smsAddress") + " - TimeStamp:" + contentValues.get("smsTimeStamp") + " - Body:" + contentValues.get("smsBody"));
                }
            }
            return arrayList;
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
            return arrayList;
        }
    }

    public void storeSMS(String str, String str2, String str3, boolean z) {
        enforcePhone();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValues2 = new ContentValues();
        StringBuilder sb = new StringBuilder();
        try {
            contentValues2.put("smsLogEnabled", (Integer) 1);
            Iterator it = this.mEdmStorageProvider.getValues("MISC", new String[]{"adminUid"}, contentValues2).iterator();
            while (it.hasNext()) {
                sb.append(((ContentValues) it.next()).getAsString("adminUid"));
                sb.append(KnoxVpnFirewallHelper.DELIMITER);
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

    public boolean clearSMSLog(ContextInfo contextInfo) {
        return clearLog(enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo).mCallerUid, "SMS", "smsCaptureAdmin", false);
    }

    public boolean enableCallingCapture(ContextInfo contextInfo, boolean z) {
        int i = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo).mCallerUid;
        if (this.mTelMgr.isVoiceCapable()) {
            return this.mEdmStorageProvider.putBoolean(i, "MISC", "CallingLogEnabled", z);
        }
        return false;
    }

    public boolean isCallingCaptureEnabled(ContextInfo contextInfo) {
        enforcePhoneAppOrOwnerAndDeviceInventoryPermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return false;
        }
        try {
            Iterator it = this.mEdmStorageProvider.getBooleanList("MISC", "CallingLogEnabled").iterator();
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

    public void storeCalling(String str, String str2, String str3, String str4, boolean z) {
        enforcePhone();
        if (this.mTelMgr.isVoiceCapable()) {
            ContentValues contentValues = new ContentValues();
            ContentValues contentValues2 = new ContentValues();
            StringBuilder sb = new StringBuilder();
            try {
                contentValues2.put("CallingLogEnabled", (Integer) 1);
                Iterator it = this.mEdmStorageProvider.getValues("MISC", new String[]{"adminUid"}, contentValues2).iterator();
                while (it.hasNext()) {
                    sb.append(((ContentValues) it.next()).getAsString("adminUid"));
                    sb.append(KnoxVpnFirewallHelper.DELIMITER);
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

    public List getOutgoingCallingCaptured(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it = this.mEdmStorageProvider.getDataByFields("CallingLog", new String[]{"callingType"}, new String[]{"0"}, null).iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (isCorrectAdmin(enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid, contentValues, "callingCaptureAdmin")) {
                    arrayList.add("To:" + contentValues.get("callingAddress") + " - TimeStamp:" + contentValues.get("callingTimeStamp") + " - Duration:" + contentValues.get("callingDuration") + " - Status:" + contentValues.get("callingStatus"));
                }
            }
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not log edm database");
        }
        return arrayList;
    }

    public List getIncomingCallingCaptured(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        if (!this.mTelMgr.isVoiceCapable()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it = this.mEdmStorageProvider.getDataByFields("CallingLog", new String[]{"callingType"}, new String[]{"1"}, null).iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (isCorrectAdmin(enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid, contentValues, "callingCaptureAdmin")) {
                    arrayList.add("From:" + contentValues.get("callingAddress") + " - TimeStamp:" + contentValues.get("callingTimeStamp") + " - Duration:" + contentValues.get("callingDuration") + " - Status:" + contentValues.get("callingStatus"));
                }
            }
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
        }
        return arrayList;
    }

    public String getCellTowerCID(ContextInfo contextInfo) {
        GsmCellLocation gsmCellLocation;
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        TelephonyManager telephonyManager = this.mTelMgr;
        return Integer.toHexString((telephonyManager == null || (gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation()) == null) ? -1 : gsmCellLocation.getCid());
    }

    public String getCellTowerLAC(ContextInfo contextInfo) {
        GsmCellLocation gsmCellLocation;
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        TelephonyManager telephonyManager = this.mTelMgr;
        return Integer.toHexString((telephonyManager == null || (gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation()) == null) ? -1 : gsmCellLocation.getLac());
    }

    public String getCellTowerPSC(ContextInfo contextInfo) {
        GsmCellLocation gsmCellLocation;
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        TelephonyManager telephonyManager = this.mTelMgr;
        return Integer.toString((telephonyManager == null || (gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation()) == null) ? -1 : gsmCellLocation.getPsc());
    }

    public String getCellTowerRSSI(ContextInfo contextInfo) {
        int cid;
        enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        TelephonyManager telephonyManager = this.mTelMgr;
        int i = 99;
        if (telephonyManager != null) {
            if (telephonyManager.getPhoneType() == 2) {
                updateSignalStrength();
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

    public final void updateSignalStrength() {
        try {
            mSignalStrength = Integer.toString(0) + " dBm " + Integer.toString(0) + " asu";
        } catch (RuntimeException e) {
            Log.e("DeviceInfo", "updateSignalStrength: " + e.getMessage());
        }
    }

    public boolean enableMMSCapture(ContextInfo contextInfo, boolean z) {
        try {
            return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo).mCallerUid, "MISC", "mmsLogEnabled", z);
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not enable mms capture");
            return false;
        }
    }

    public boolean isMMSCaptureEnabled(ContextInfo contextInfo) {
        try {
            Iterator it = this.mEdmStorageProvider.getBooleanList("MISC", "mmsLogEnabled").iterator();
            while (it.hasNext()) {
                if (((Boolean) it.next()).booleanValue()) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
            return false;
        }
    }

    public List getOutboundMMSCaptured(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it = this.mEdmStorageProvider.getDataByFields("MMS", new String[]{"mmsType"}, new String[]{"0"}, null).iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (isCorrectAdmin(enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid, contentValues, "mmsCaptureAdmin")) {
                    arrayList.add("To:" + contentValues.get("mmsAddress") + " - TimeStamp:" + contentValues.get("mmsTimeStamp") + " - Body:" + contentValues.get("mmsBody"));
                }
            }
            return arrayList;
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
            return arrayList;
        }
    }

    public List getInboundMMSCaptured(ContextInfo contextInfo) {
        ContextInfo enforceOwnerOnlyAndDeviceInventoryPermission = enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        try {
            Iterator it = this.mEdmStorageProvider.getDataByFields("MMS", new String[]{"mmsType"}, new String[]{"1"}, null).iterator();
            while (it.hasNext()) {
                ContentValues contentValues = (ContentValues) it.next();
                if (isCorrectAdmin(enforceOwnerOnlyAndDeviceInventoryPermission.mCallerUid, contentValues, "mmsCaptureAdmin")) {
                    arrayList.add("From:" + contentValues.get("mmsAddress") + " - TimeStamp:" + contentValues.get("mmsTimeStamp") + " - Body:" + contentValues.get("mmsBody"));
                }
            }
            return arrayList;
        } catch (Exception unused) {
            Log.w("DeviceInfo", "could not open edm database");
            return arrayList;
        }
    }

    public void storeMMS(String str, String str2, String str3, boolean z) {
        if (Binder.getCallingPid() == Process.myPid()) {
            ContentValues contentValues = new ContentValues();
            ContentValues contentValues2 = new ContentValues();
            StringBuilder sb = new StringBuilder();
            try {
                contentValues2.put("mmsLogEnabled", (Integer) 1);
                Iterator it = this.mEdmStorageProvider.getValues("MISC", new String[]{"adminUid"}, contentValues2).iterator();
                while (it.hasNext()) {
                    sb.append(((ContentValues) it.next()).getAsString("adminUid"));
                    sb.append(KnoxVpnFirewallHelper.DELIMITER);
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

    public boolean clearMMSLog(ContextInfo contextInfo) {
        return clearLog(enforceOwnerOnlyAndDeviceInventoryPermission(contextInfo).mCallerUid, "MMS", "mmsCaptureAdmin", false);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        updateDataUsageState();
        clearDatabasesOnAdminRemoval(i);
    }

    public String getSalesCode(ContextInfo contextInfo) {
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
                            Log.d("DeviceInfo", "SalesCode : Use product code as customerCode for Movistar single binary(TFG)");
                            str = substring;
                        } catch (RuntimeException e) {
                            e = e;
                            str = substring;
                            Log.e("DeviceInfo", "getSalesCode : RuntimeException : " + e.getMessage());
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
}
