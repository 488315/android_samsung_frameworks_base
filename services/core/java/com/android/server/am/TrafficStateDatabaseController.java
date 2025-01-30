package com.android.server.am;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.IIpConnectivityMetrics;
import android.net.INetdEventCallback;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.telephony.CellIdentityNr;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthNr;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.TelephonyFeatures;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.net.BaseNetdEventCallback;
import com.android.server.am.TrafficStateDatabaseController;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class TrafficStateDatabaseController {
    public static Context mContext;
    public static IIpConnectivityMetrics mIpConnectivityMetrics;
    public static SubscriptionManager mSubscriptionManager;
    public static TrafficStatsTelephonyCallback mTelephonyCallback;
    public static TelephonyManager mTelephonyManager;
    public static List objTrafficStats;
    public static Handler sAsyncHandler;
    public static HandlerThread sAsyncHandlerThread;
    public static final Uri CONTENT_URI = Uri.parse("content://com.tmobile.oem.echolocate.system.provider/trafficstats");
    public static final String[] DELETE_QUERY_VALUE = {"0"};
    public static final Signature ECHO_APP_SIG = new Signature("308203623082024aa00302010202044df1bf45300d06092a864886f70d01010505003073310b3009060355040613025553310b30090603550408130257413111300f0603550407130842656c6c657675653111300f060355040a1308542d4d6f62696c6531133011060355040b130a546563686e6f6c6f6779311c301a0603550403131350726f64756374205265616c697a6174696f6e301e170d3131303631303036353235335a170d3338313032363036353235335a3073310b3009060355040613025553310b30090603550408130257413111300f0603550407130842656c6c657675653111300f060355040a1308542d4d6f62696c6531133011060355040b130a546563686e6f6c6f6779311c301a0603550403131350726f64756374205265616c697a6174696f6e30820122300d06092a864886f70d01010105000382010f003082010a0282010100c1456176d31c8989df7e0b30569da5c9b782380d3ff28fb48b4a17c8a125f40ba14862518397800f7a1030bf7cc188b9296d84af5cc5dc37752a1ca2c33d654258a3fdd29d19f2a0dd4e24b328b03bfef8c17bb8da11a25fdae10c1e1e288e3c1f47ee47617972382b0854474da1d6b526b9787d9a2f8e00600a4e436bfa790d04a0376fd7bd5c6ee78a6e522bbaa969d63667d17ca8fd90087fcc4acf2a2676d341a8e19dc46beb82bb1990710bd4101df8943ef8a3f2d7cb0bac6677ae69f9f3d25c134c08dfeb82000f44dea4164f90a65e352387fdd203c3479cfb380a2f8af5af3219a726ba9d82d72229a8d32979ce84be52006f4b71fe75011e8e2d090203010001300d06092a864886f70d01010505000382010100188d18ea72a49334736e118e766744489c7a5c47543cc35cc62a8cce35e84dfd426af3595fe55192dcb2a54c594a8d0de064dad96d72969fbc873c7a9fe7e14b11aed16c6d4bf90c1911b7d8a054c0c34c7a58c4a434d46e72f6142b654af24d461089c4633aa21cead0b154efac0aec4d68403c51bceab76c33a819857531c6a459a266f495f810417e9583d71f3f53a533f1e7013007253e9ed3466432a21977837669cff2b6b20612c055ff09b44ca15ca6830cdb289398d290852d3b0204deecbb00292194cc7533e5ae593e0d355883ea8022eb6fe5e807d6c059b3f6d6f637cd4014da425742f21b54ec37c6f55d3f0b8b6ced1cbc09376e8ea023396f");
    public static boolean mShouldReport = false;
    public static TrafficStateDatabaseController sInstance = null;
    public static volatile boolean mInsertList = true;
    public static volatile boolean mInsertDb = true;
    public static String bandNumber = "-1";
    public static String primaryBandwidth = "-1";
    public static final String[] Support_List = {"SM-S711U", "SM-S921U", "SM-S926U", "SM-S928U", "SM-S901U", "SM-S906U", "SM-S908U", "SM-S911U", "SM-S916U", "SM-S918U", "SM-F711U", "SM-F926U", "SM-F721U", "SM-F936U", "SM-F731U", "SM-F946U", "SM-A546U", "SM-X828U"};
    public static final INetdEventCallback mNetdEventCallback = new BaseNetdEventCallback() { // from class: com.android.server.am.TrafficStateDatabaseController.1
        public void onConnectEvent(String str, int i, long j, int i2) {
        }

        public void onDnsEvent(int i, int i2, int i3, String str, String[] strArr, int i4, long j, int i5) {
            String str2 = "-1";
            try {
                if (TrafficStateDatabaseController.objTrafficStats.size() >= 200 || !TrafficStateDatabaseController.ismInsertList() || !TrafficStateDatabaseController.isTMONetworkCode()) {
                    TrafficStateDatabaseController.setmInsertList(false);
                    TrafficStateDatabaseController.log("onDnsEvent(): Max List Size= " + TrafficStateDatabaseController.objTrafficStats.size() + ", mInsertList= " + TrafficStateDatabaseController.ismInsertList() + "mInsertDb= " + TrafficStateDatabaseController.ismInsertDb());
                    if (TrafficStateDatabaseController.ismInsertDb()) {
                        TrafficStateDatabaseController.setmInsertDb(false);
                        TrafficStateDatabaseController.insertValues();
                        return;
                    }
                    return;
                }
                if (i4 > 0) {
                    TrafficStateDatabaseController.log("onDnsEvent():  objTrafficStats.size= " + TrafficStateDatabaseController.objTrafficStats.size() + ", mInsertList= " + TrafficStateDatabaseController.ismInsertList());
                    TrafficStateDatabaseController.setmInsertDb(true);
                    String arrays = strArr != null ? Arrays.toString(strArr) : "000.000.00.00";
                    long cellId = TrafficStateDatabaseController.getCellId();
                    int rsrp = TrafficStateDatabaseController.getRsrp();
                    String techType = TrafficStateDatabaseController.getTechType();
                    long currentTimeMillis = System.currentTimeMillis();
                    String ipdns = TrafficStateDatabaseController.getIPDNS(arrays);
                    String mD5Hash = TrafficStateDatabaseController.getMD5Hash(arrays);
                    if (TrafficStateDatabaseController.isWifiConnected()) {
                        TrafficStateDatabaseController.log("Wifi Connected");
                        TrafficStateDatabaseController.bandNumber = "-1";
                    } else {
                        str2 = techType;
                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("cellId", "" + cellId);
                    contentValues.put("rsrp", Integer.valueOf(rsrp));
                    contentValues.put("techType", str2);
                    contentValues.put("bandNumber", TrafficStateDatabaseController.bandNumber);
                    contentValues.put("timestamp", "" + currentTimeMillis);
                    contentValues.put("transactionID", "" + i);
                    contentValues.put("DNSType", "" + ipdns);
                    contentValues.put("IPAddress", mD5Hash);
                    contentValues.put("responseTime", "" + j);
                    contentValues.put("responseCode", "" + i3);
                    contentValues.put("consumed", "0");
                    TrafficStateDatabaseController.objTrafficStats.add(contentValues);
                }
            } catch (Exception e) {
                e.printStackTrace();
                TrafficStateDatabaseController.log("onDnsEvent(): Exception in Insertion" + e);
            }
        }
    };

    public static void logD(String str) {
    }

    public static synchronized void enable(Context context) {
        synchronized (TrafficStateDatabaseController.class) {
            if (sInstance == null) {
                log("TrafficStateDatabaseController enable");
                mContext = context;
                context.enforceCallingOrSelfPermission("com.tmobile.oem.echolocate.system.provider.trafficstats.TrafficStatsContentProvider.READ_TRAFFIC_STATS", "readTrafficStateProvider");
                mContext.enforceCallingOrSelfPermission("com.tmobile.oem.echolocate.system.provider.trafficstats.TrafficStatsContentProvider.WRITE_TRAFFIC_STATS", "WriteTrafficStateProvider");
                mTelephonyManager = (TelephonyManager) mContext.getSystemService("phone");
                mSubscriptionManager = (SubscriptionManager) mContext.getSystemService("telephony_subscription_service");
                sInstance = new TrafficStateDatabaseController();
                objTrafficStats = new CopyOnWriteArrayList();
                mTelephonyCallback = new TrafficStatsTelephonyCallback();
                if (ShouldReport()) {
                    IIpConnectivityMetrics asInterface = IIpConnectivityMetrics.Stub.asInterface(ServiceManager.getService("connmetrics"));
                    mIpConnectivityMetrics = asInterface;
                    try {
                        if (asInterface.addNetdEventCallback(4, mNetdEventCallback)) {
                            log("registerDnsCallback - added");
                        }
                    } catch (RemoteException e) {
                        log("registerDnsCallback - RemoteException: " + e);
                    } catch (Exception e2) {
                        log("registerReceiverException: " + e2);
                    }
                    TelephonyManager telephonyManager = mTelephonyManager;
                    if (telephonyManager != null && mShouldReport) {
                        telephonyManager.registerTelephonyCallback(mContext.getMainExecutor(), mTelephonyCallback);
                    }
                }
            } else {
                log("already enabled.");
            }
        }
    }

    public static boolean ShouldReport() {
        boolean isModelSupported = isModelSupported();
        boolean equals = "TMB".equals(TelephonyFeatures.getSalesCode());
        boolean isEchoAppSigPresent = isEchoAppSigPresent();
        if (equals) {
            mShouldReport = isModelSupported && isEchoAppSigPresent;
            log("TrafficState(): isModelSupported=" + isModelSupported + ", isTmoSalesCode=" + equals + ", isEchoAppSigPresent=" + isEchoAppSigPresent + ", mShouldReport=" + mShouldReport);
        } else {
            log("isTmoSalesCode= false");
        }
        return mShouldReport;
    }

    public static boolean isEchoAppSigPresent() {
        try {
            Signature[] signatureArr = mContext.getPackageManager().getPackageInfo("com.tmobile.echolocate", 64).signatures;
            if (signatureArr == null) {
                return false;
            }
            boolean z = false;
            for (Signature signature : signatureArr) {
                int i = 0;
                while (true) {
                    if (i >= signatureArr.length) {
                        break;
                    }
                    if (signature.equals(ECHO_APP_SIG)) {
                        z = true;
                        break;
                    }
                    i++;
                }
            }
            return z;
        } catch (PackageManager.NameNotFoundException unused) {
            log("isEchoAppSigPresent(): package is not installed");
            return false;
        }
    }

    public static boolean isModelSupported() {
        boolean z = false;
        for (String str : Support_List) {
            if (str.contains(SystemProperties.get("ro.product.model"))) {
                z = true;
            }
        }
        return z;
    }

    public static boolean isTMONetworkCode() {
        return "TMB".equals(TelephonyFeatures.getNetworkCode(SubscriptionManager.getActiveDataSubscriptionId() != -1 ? SubscriptionManager.getPhoneId(SubscriptionManager.getActiveDataSubscriptionId()) : 0));
    }

    public class AlarmReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null || !intent.getAction().equals("com.samsung.intent.ACTION_INSERT_TRAFFIC_STATE_DB")) {
                return;
            }
            TrafficStateDatabaseController.log("onReceive(): ACTION_DELETE_TRAFFIC_STATE_DB");
            final BroadcastReceiver.PendingResult goAsync = goAsync();
            TrafficStateDatabaseController.getAsyncHandler().post(new Runnable() { // from class: com.android.server.am.TrafficStateDatabaseController$AlarmReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TrafficStateDatabaseController.AlarmReceiver.lambda$onReceive$0(goAsync);
                }
            });
        }

        public static /* synthetic */ void lambda$onReceive$0(BroadcastReceiver.PendingResult pendingResult) {
            TrafficStateDatabaseController.deleteValues();
            pendingResult.finish();
        }
    }

    public static synchronized Handler getAsyncHandler() {
        Handler handler;
        synchronized (TrafficStateDatabaseController.class) {
            if (sAsyncHandlerThread == null) {
                HandlerThread handlerThread = new HandlerThread("sAsyncHandlerThread", 10);
                sAsyncHandlerThread = handlerThread;
                handlerThread.start();
                sAsyncHandler = new Handler(sAsyncHandlerThread.getLooper());
            }
            handler = sAsyncHandler;
        }
        return handler;
    }

    public static synchronized void disable() {
        TrafficStatsTelephonyCallback trafficStatsTelephonyCallback;
        synchronized (TrafficStateDatabaseController.class) {
            sInstance = null;
            log("disable()");
            setmInsertList(false);
            setmInsertDb(false);
            if (ShouldReport()) {
                TelephonyManager telephonyManager = mTelephonyManager;
                if (telephonyManager != null && (trafficStatsTelephonyCallback = mTelephonyCallback) != null) {
                    telephonyManager.unregisterTelephonyCallback(trafficStatsTelephonyCallback);
                }
                try {
                    IIpConnectivityMetrics iIpConnectivityMetrics = mIpConnectivityMetrics;
                    if (iIpConnectivityMetrics != null && iIpConnectivityMetrics.removeNetdEventCallback(4)) {
                        log("disable(): unregisterDnsCallback - removed");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                    log("disable(): unregisterDnsCallback - RemoteException: " + e);
                }
            }
        }
    }

    public class TrafficStatsTelephonyCallback extends TelephonyCallback implements TelephonyCallback.PhysicalChannelConfigListener {
        @Override // android.telephony.TelephonyCallback.PhysicalChannelConfigListener
        public void onPhysicalChannelConfigChanged(List list) {
            if (list == null || !TrafficStateDatabaseController.isTMONetworkCode()) {
                return;
            }
            TrafficStateDatabaseController.log("PhysicalChannelConfigChanged");
            TrafficStateDatabaseController.bandNumber = TrafficStateDatabaseController.bandFromCP();
        }
    }

    public static void insertValues() {
        try {
            if (objTrafficStats != null) {
                log("insertValues()");
                Iterator it = objTrafficStats.iterator();
                while (it.hasNext()) {
                    mContext.getContentResolver().insert(CONTENT_URI, (ContentValues) it.next());
                }
                objTrafficStats.clear();
                setmInsertList(true);
                log("after Clear objTrafficStats.Size = " + objTrafficStats.size() + ", mInsertList= " + ismInsertList());
                return;
            }
            setmInsertList(true);
            log("insertValues(): List is null can't insert.");
        } catch (Exception e) {
            e.printStackTrace();
            setmInsertList(true);
            log("Can't insert exception ocuurred: message" + e);
        }
    }

    public static boolean ismInsertList() {
        return mInsertList;
    }

    public static void setmInsertList(boolean z) {
        if (mInsertList != z) {
            mInsertList = z;
        }
    }

    public static boolean ismInsertDb() {
        return mInsertDb;
    }

    public static void setmInsertDb(boolean z) {
        if (mInsertDb != z) {
            mInsertDb = z;
        }
    }

    public static void deleteValues() {
        log("deleteValues()");
        try {
            mContext.getContentResolver().delete(CONTENT_URI, "consumed=?", DELETE_QUERY_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
            log("Can't delete exception ocuurred: message" + e);
        }
    }

    public static String getIPDNS(String str) {
        return str.contains(XmlUtils.STRING_ARRAY_SEPARATOR) ? "AAAA" : str.contains(".") ? "A" : "-1";
    }

    public static boolean isWifiConnected() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService("connectivity");
        return connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 1;
    }

    public static String getTechType() {
        TelephonyManager telephonyManager = mTelephonyManager;
        String str = "-2";
        if (telephonyManager != null && telephonyManager.getServiceState() != null) {
            if (mTelephonyManager.getServiceState().getDataRegState() == 0) {
                int dataNetworkType = mTelephonyManager.getDataNetworkType();
                String str2 = "2G";
                if (dataNetworkType != 1 && dataNetworkType != 2) {
                    if (dataNetworkType == 3) {
                        str2 = "3G";
                    } else if (dataNetworkType == 13) {
                        str2 = "LTE";
                    } else if (dataNetworkType != 16) {
                        if (dataNetworkType != 20) {
                            logD("getTechType(): currNetwork= " + dataNetworkType);
                            str2 = "-1";
                        } else {
                            str2 = "NR";
                        }
                    }
                }
                str = str2;
            } else {
                logD("getTechType(): currServiceState is invalid");
            }
        } else {
            logD("getTechType(): mTelephonyManager is null");
        }
        logD("getTechType(): techType= " + str);
        return str;
    }

    public static String getMD5Hash(String str) {
        try {
            String bigInteger = new BigInteger(1, MessageDigest.getInstance("MD5").digest(str.getBytes(Charset.forName("UTF-8")))).toString(16);
            while (bigInteger.length() < 32) {
                bigInteger = "0" + bigInteger;
            }
            return bigInteger;
        } catch (NoSuchAlgorithmException unused) {
            logD("getMD5Hash: NoSuchAlgorithmException");
            return str;
        }
    }

    public static String getTechTypeFromCp(String str) {
        String techType;
        if (str != null) {
            if (str.equals("1")) {
                logD("cpTechType: ret=NR");
                return "NR";
            }
            if (str.equals("2")) {
                logD("cpTechType: ret=LTE");
                return "LTE";
            }
            techType = getTechType();
            logD("getCurrentNetworkType(): ret=" + techType);
        } else {
            techType = getTechType();
            logD("getCurrentNetworkType(): ret=" + techType);
        }
        return techType;
    }

    public static byte[] getCommandsToCp(int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            try {
                dataOutputStream.writeByte(17);
                dataOutputStream.writeByte(146);
                dataOutputStream.writeShort(5);
                dataOutputStream.writeByte(i);
            } catch (IOException unused) {
                log("getCommandToCp(): ");
            }
            return byteArrayOutputStream.toByteArray();
        } finally {
            try {
                dataOutputStream.close();
            } catch (IOException unused2) {
                log("getCommandToCp(): failed to close dos ");
            }
        }
    }

    public static String[] getResultsFromBytes(int i, byte[] bArr) {
        if (i <= 0) {
            log("getResultsFromBytes(): length is less than or equal to zero!");
            return null;
        }
        String str = new String(bArr, 0, i, Charset.forName("UTF-8"));
        logD("getResultsFromBytes(): resultString=" + str);
        String[] split = str.split("\\|", -1);
        for (int i2 = 0; i2 < split.length; i2++) {
            String str2 = split[i2];
            if (str2 == null || "".equals(str2) || str2.isEmpty()) {
                split[i2] = "-2";
            }
        }
        return split;
    }

    public static String[] invokeOemRilRequestRaw(int i) {
        if (mSubscriptionManager != null && mTelephonyManager != null) {
            byte[] bArr = new byte[512];
            int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
            logD("invokeOemRilRequestRaw: DefaultDataSubId= " + defaultDataSubscriptionId);
            int invokeOemRilRequestRawForSubscriber = mTelephonyManager.invokeOemRilRequestRawForSubscriber(defaultDataSubscriptionId, getCommandsToCp(i), bArr);
            logD("invokeOemRilRequestRaw: resp= " + invokeOemRilRequestRawForSubscriber);
            String[] resultsFromBytes = getResultsFromBytes(invokeOemRilRequestRawForSubscriber, bArr);
            logD("invokeOemRilRequestRaw: msg= " + i + ", resultFromCp= " + Arrays.toString(resultsFromBytes));
            return resultsFromBytes;
        }
        logD("invokeOemRilRequestRaw: mTelephonyManager= null & resultFromCp = null");
        return null;
    }

    public static String bandFromCP() {
        String[] invokeOemRilRequestRaw = invokeOemRilRequestRaw(4);
        primaryBandwidth = "-1";
        if (invokeOemRilRequestRaw != null && (getTechTypeFromCp(invokeOemRilRequestRaw[1]).equals("NR") || getTechTypeFromCp(invokeOemRilRequestRaw[1]).equals("LTE"))) {
            primaryBandwidth = invokeOemRilRequestRaw[4];
        }
        return primaryBandwidth;
    }

    public static long getCellId() {
        int cid;
        String techType = getTechType();
        logD("getCellId(): techType: " + techType);
        if (!"0".equals(techType) && !"-2".equals(techType)) {
            TelephonyManager telephonyManager = mTelephonyManager;
            if (telephonyManager != null && telephonyManager.getAllCellInfo() != null) {
                List<CellInfo> allCellInfo = mTelephonyManager.getAllCellInfo();
                logD("getCellId(): info.size: " + allCellInfo.size());
                while (true) {
                    long j = -1;
                    for (CellInfo cellInfo : allCellInfo) {
                        if (cellInfo.isRegistered()) {
                            if (cellInfo instanceof CellInfoGsm) {
                                cid = ((CellInfoGsm) cellInfo).getCellIdentity().getCid();
                            } else if (cellInfo instanceof CellInfoNr) {
                                j = ((CellIdentityNr) ((CellInfoNr) cellInfo).getCellIdentity()).getNci();
                            } else if (cellInfo instanceof CellInfoLte) {
                                cid = ((CellInfoLte) cellInfo).getCellIdentity().getCi();
                            } else if (cellInfo instanceof CellInfoWcdma) {
                                cid = ((CellInfoWcdma) cellInfo).getCellIdentity().getCid();
                            }
                            j = cid;
                        }
                    }
                    return j;
                    logD("getCellId(): CellInfo is not one of GSM/LTE/WCDMA");
                }
            } else {
                logD("getCellId(): mTelephonyManager is null");
                return -2L;
            }
        } else {
            logD("getCellId(): techType is not available");
            return -2L;
        }
    }

    public static int getRsrp() {
        TelephonyManager telephonyManager = mTelephonyManager;
        int i = -150;
        if (telephonyManager != null && telephonyManager.getSignalStrength() != null) {
            SignalStrength signalStrength = mTelephonyManager.getSignalStrength();
            if (signalStrength.getCellSignalStrengths() != null) {
                List<CellSignalStrength> cellSignalStrengths = signalStrength.getCellSignalStrengths();
                logD("getRsrp(): objStrengthList.size= " + cellSignalStrengths.size());
                CellSignalStrengthLte cellSignalStrengthLte = null;
                CellSignalStrengthNr cellSignalStrengthNr = null;
                loop0: while (true) {
                    i = -999;
                    for (CellSignalStrength cellSignalStrength : cellSignalStrengths) {
                        if (cellSignalStrength instanceof CellSignalStrengthLte) {
                            cellSignalStrengthLte = (CellSignalStrengthLte) cellSignalStrength;
                        }
                        if (cellSignalStrength instanceof CellSignalStrengthNr) {
                            cellSignalStrengthNr = (CellSignalStrengthNr) cellSignalStrength;
                        }
                        if (cellSignalStrengthLte != null && cellSignalStrengthLte.isValid()) {
                            i = cellSignalStrengthLte.getRsrp();
                            logD("getRsrp(): cellSignalLte.rsrp= " + i);
                        } else if (cellSignalStrengthNr != null && cellSignalStrengthNr.isValid()) {
                            i = cellSignalStrengthNr.getSsRsrp();
                            logD("getRsrp(): cellSignalNR.rsrp= " + i);
                        } else {
                            logD("getRsrp(): Default.rsrp= -999");
                        }
                    }
                    break loop0;
                }
            } else {
                logD(" getRsrp(): objStrengthList is null ");
            }
        } else {
            logD(" getRsrp(): telephonyManager is null ");
        }
        logD(" getRsrp(): rsrp= " + i);
        return i;
    }

    public static void log(String str) {
        Log.d("TrafficStateDatabaseController", str);
    }
}
