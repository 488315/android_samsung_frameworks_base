package com.android.server.enterprise.nap;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerService;

import com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class NetworkAnalyticsDataDelivery {
    public static NetworkAnalyticsDataDelivery mInstance;
    public List dataEntry;
    public DataDeliveryHandler mHandler;
    public HandlerThread mHandlerThread;
    public List registeredDataRecipients;
    public long startTimer;
    public Object syncObject;
    public static final boolean DBG = NetworkAnalyticsService.DBG;
    public static final PackageManagerService.IPackageManagerImpl pmsImp =
            (PackageManagerService.IPackageManagerImpl) ServiceManager.getService("package");
    public static final Set appset = Collections.synchronizedSet(new HashSet());

    public final class AppInfoSet {
        public final String hash;
        public final String packageName;
        public final String processName;
        public final String truncatedProcessName;
        public final int uid;
        public final int userId;

        public AppInfoSet(int i, String str, String str2, int i2, String str3, String str4) {
            this.uid = i;
            this.packageName = str;
            this.processName = str2;
            this.truncatedProcessName = str3;
            this.hash = str4;
            this.userId = i2;
        }
    }

    public final class DataDeliveryHandler extends Handler {
        public DataDeliveryHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String str;
            List<DataDeliveryHelper> recipientList;
            if (message.what == 1 && (str = (String) message.obj) != null) {
                ((ArrayList) NetworkAnalyticsDataDelivery.this.dataEntry).add(str);
                if (((ArrayList) NetworkAnalyticsDataDelivery.this.dataEntry).size() >= 50
                        || System.currentTimeMillis() - NetworkAnalyticsDataDelivery.this.startTimer
                                > 10000) {
                    NetworkAnalyticsDataDelivery networkAnalyticsDataDelivery =
                            NetworkAnalyticsDataDelivery.this;
                    List list = networkAnalyticsDataDelivery.dataEntry;
                    if (list != null && ((ArrayList) list).size() > 0) {
                        synchronized (networkAnalyticsDataDelivery.syncObject) {
                            try {
                                recipientList = networkAnalyticsDataDelivery.getRecipientList();
                            } catch (Exception e) {
                                Log.e(
                                        "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                                        "deliverData: Exception ",
                                        e);
                            } catch (RemoteException e2) {
                                Log.e(
                                        "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                                        "deliverData: RemoteException ",
                                        e2);
                            } finally {
                            }
                            if (recipientList != null && recipientList.size() > 0) {
                                for (DataDeliveryHelper dataDeliveryHelper : recipientList) {
                                    NetworkAnalyticsService.NetworkAnalyticsServiceConnection
                                            networkAnalyticsServiceConnection =
                                                    dataDeliveryHelper.serviceConnection;
                                    INetworkAnalyticsService iNetworkAnalyticsService =
                                            networkAnalyticsServiceConnection != null
                                                    ? networkAnalyticsServiceConnection.napInterface
                                                    : null;
                                    String str2 = dataDeliveryHelper.profile.profileName;
                                    if (iNetworkAnalyticsService != null) {
                                        List augmentedData =
                                                NetworkAnalyticsDataDelivery.getAugmentedData(
                                                        dataDeliveryHelper, list);
                                        if (augmentedData != null && augmentedData.size() > 0) {
                                            iNetworkAnalyticsService.onDataAvailable(
                                                    str2, augmentedData);
                                        }
                                    } else if (NetworkAnalyticsDataDelivery.DBG) {
                                        Log.d(
                                                "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                                                "deliverData: service connection is null for entry:"
                                                        + dataDeliveryHelper.identifier);
                                    }
                                }
                            }
                            if (NetworkAnalyticsDataDelivery.DBG) {
                                Log.d(
                                        "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                                        "deliverData: No data delivery herlper entries.");
                            }
                        }
                    }
                    ((ArrayList) NetworkAnalyticsDataDelivery.this.dataEntry).clear();
                    NetworkAnalyticsDataDelivery.this.startTimer = System.currentTimeMillis();
                }
            }
        }
    }

    public static String checkIfProcessIsDaemon(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            Process exec = Runtime.getRuntime().exec("which " + str);
            exec.waitFor();
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(exec.getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuffer.append(readLine);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public static String checkSingleUidAndCalculateHash(int i, String str) {
        PackageManagerService.IPackageManagerImpl iPackageManagerImpl;
        String str2;
        AppInfoSet appInfoSet;
        try {
            iPackageManagerImpl = pmsImp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (iPackageManagerImpl == null) {
            return null;
        }
        String[] packagesForUid = iPackageManagerImpl.getPackagesForUid(i);
        if (packagesForUid == null) {
            Log.d(
                    "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                    "unable to find the packages for uid: " + i + " for processName: " + str);
            return null;
        }
        if (packagesForUid.length == 1) {
            Set set = appset;
            synchronized (set) {
                try {
                    Iterator it = set.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            str2 = null;
                            break;
                        }
                        AppInfoSet appInfoSet2 = (AppInfoSet) it.next();
                        if (appInfoSet2.uid == i) {
                            str2 = appInfoSet2.hash;
                        }
                    }
                } finally {
                }
            }
            if (str2 != null) {
                return str2;
            }
            String str3 = packagesForUid[0];
            ApplicationInfo applicationInfo =
                    pmsImp.getApplicationInfo(str3, 0L, UserHandle.getUserId(i));
            if (applicationInfo != null && applicationInfo.sourceDir != null) {
                File file = new File(applicationInfo.sourceDir);
                if (!file.exists()) {
                    Log.d(
                            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                            "unable to find the file location for the process:"
                                    + str
                                    + "for package "
                                    + str3
                                    + "for uid "
                                    + i);
                    return null;
                }
                String hash = getHash(file);
                if (hash != null) {
                    String str4 = applicationInfo.processName;
                    if (str4 != null) {
                        if (str4.length() > 15) {
                            String str5 = applicationInfo.processName;
                            appInfoSet =
                                    new AppInfoSet(
                                            i,
                                            str3,
                                            str5,
                                            UserHandle.getUserId(i),
                                            str5.substring(str5.length() - 15),
                                            hash);
                        } else {
                            String str6 = applicationInfo.processName;
                            appInfoSet =
                                    new AppInfoSet(
                                            i, str3, str6, UserHandle.getUserId(i), str6, hash);
                        }
                        insertHashIntoCache(appInfoSet);
                    }
                    return hash;
                }
            }
        }
        return null;
    }

    public static void clearHashCacheEntire() {
        Set set = appset;
        synchronized (set) {
            try {
                boolean z = DBG;
                if (z) {
                    Log.d(
                            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                            "clearHashCacheEntire Called : cache size: " + set.size());
                }
                set.clear();
                if (z) {
                    Log.d(
                            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                            "DataDelivery hash cache entire deletion : cache size: " + set.size());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static String compareProcessNamesAndCalculateHash(
            int i, String str, String str2, String str3, ApplicationInfo applicationInfo) {
        if (str != null && str2 != null && str3 != null && applicationInfo != null) {
            try {
                if (str3.length() > 15) {
                    if (!str3.substring(str3.length() - 15).equalsIgnoreCase(str)) {
                        if (str3.equalsIgnoreCase(str)) {}
                    }
                    if (applicationInfo.sourceDir != null) {
                        File file = new File(applicationInfo.sourceDir);
                        if (!file.exists()) {
                            Log.d(
                                    "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                                    "unable to find the file location for the process:"
                                            + str
                                            + "for package "
                                            + str2
                                            + "for uid "
                                            + i);
                            return null;
                        }
                        String hash = getHash(file);
                        if (hash != null) {
                            insertHashIntoCache(
                                    new AppInfoSet(
                                            i,
                                            str2,
                                            str3,
                                            UserHandle.getUserId(i),
                                            str3.substring(str3.length() - 15),
                                            hash));
                            return hash;
                        }
                    }
                } else if (str3.equalsIgnoreCase(str) && applicationInfo.sourceDir != null) {
                    File file2 = new File(applicationInfo.sourceDir);
                    if (!file2.exists()) {
                        Log.d(
                                "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                                "unable to find the file location for the process:"
                                        + str
                                        + "for package "
                                        + str2
                                        + "for uid "
                                        + i);
                        return null;
                    }
                    String hash2 = getHash(file2);
                    if (hash2 != null) {
                        insertHashIntoCache(
                                new AppInfoSet(
                                        i, str2, str3, UserHandle.getUserId(i), str3, hash2));
                        return hash2;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:114:0x02ba, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x02bb, code lost:

       r4 = r17;
    */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0304, code lost:

       android.util.Log.e(r4, "processData: JSONException", r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0302, code lost:

       r0 = r9;
    */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x02b8, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x02fa, code lost:

       r4 = r17;
       android.util.Log.e(r4, "processData: Exception", r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x02c3, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x02c4, code lost:

       r2 = r18;
    */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x02c6, code lost:

       r9 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x02be, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x02bf, code lost:

       r2 = r18;
    */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x00b4, code lost:

       r9 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x02cd, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x02ce, code lost:

       r9 = r3;
       r2 = r18;
    */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x02c8, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x02c9, code lost:

       r9 = r3;
       r2 = r18;
    */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x028a, code lost:

       r5 = "dnsuid";
    */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x02ea, code lost:

       r9 = r1;
       r2 = r18;
       r1 = r20;
    */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x02df, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x02e0, code lost:

       r9 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x02e1, code lost:

       r2 = r18;
       r1 = r20;
    */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x02d8, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x02d9, code lost:

       r9 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x02da, code lost:

       r2 = r18;
       r1 = r20;
    */
    /* JADX WARN: Code restructure failed: missing block: B:250:0x00ab, code lost:

       if (r9 != r10) goto L30;
    */
    /* JADX WARN: Code restructure failed: missing block: B:252:0x00c2, code lost:

       if (r9 != r10) goto L30;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List getAugmentedData(
            com.android.server.enterprise.nap.DataDeliveryHelper r25, java.util.List r26) {
        /*
            Method dump skipped, instructions count: 802
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.nap.NetworkAnalyticsDataDelivery.getAugmentedData(com.android.server.enterprise.nap.DataDeliveryHelper,"
                    + " java.util.List):java.util.List");
    }

    public static String getFileLocationFromProcessNameAndCalculateHash(int i, String str) {
        PackageManagerService.IPackageManagerImpl iPackageManagerImpl;
        String[] packagesForUid;
        ActivityInfo[] activityInfoArr;
        String str2;
        String compareProcessNamesAndCalculateHash;
        ActivityInfo[] activityInfoArr2;
        String str3;
        String compareProcessNamesAndCalculateHash2;
        ProviderInfo[] providerInfoArr;
        String str4;
        String compareProcessNamesAndCalculateHash3;
        ServiceInfo[] serviceInfoArr;
        String str5;
        String compareProcessNamesAndCalculateHash4;
        String str6;
        String compareProcessNamesAndCalculateHash5;
        try {
            iPackageManagerImpl = pmsImp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (iPackageManagerImpl == null
                || (packagesForUid = iPackageManagerImpl.getPackagesForUid(i)) == null) {
            return null;
        }
        for (String str7 : packagesForUid) {
            ApplicationInfo applicationInfo =
                    pmsImp.getApplicationInfo(str7, 0L, UserHandle.getUserId(i));
            if (applicationInfo != null
                    && (str6 = applicationInfo.processName) != null
                    && str != null
                    && (compareProcessNamesAndCalculateHash5 =
                                    compareProcessNamesAndCalculateHash(
                                            i, str, str7, str6, applicationInfo))
                            != null) {
                return compareProcessNamesAndCalculateHash5;
            }
            PackageInfo packageInfo = pmsImp.getPackageInfo(str7, 4L, UserHandle.getUserId(i));
            if (packageInfo != null && (serviceInfoArr = packageInfo.services) != null) {
                for (ServiceInfo serviceInfo : serviceInfoArr) {
                    if (serviceInfo != null
                            && (str5 = serviceInfo.processName) != null
                            && str != null
                            && applicationInfo != null
                            && (compareProcessNamesAndCalculateHash4 =
                                            compareProcessNamesAndCalculateHash(
                                                    i, str, str7, str5, applicationInfo))
                                    != null) {
                        return compareProcessNamesAndCalculateHash4;
                    }
                }
            }
            PackageInfo packageInfo2 = pmsImp.getPackageInfo(str7, 8L, UserHandle.getUserId(i));
            if (packageInfo2 != null && (providerInfoArr = packageInfo2.providers) != null) {
                for (ProviderInfo providerInfo : providerInfoArr) {
                    if (providerInfo != null
                            && (str4 = providerInfo.processName) != null
                            && str != null
                            && applicationInfo != null
                            && (compareProcessNamesAndCalculateHash3 =
                                            compareProcessNamesAndCalculateHash(
                                                    i, str, str7, str4, applicationInfo))
                                    != null) {
                        return compareProcessNamesAndCalculateHash3;
                    }
                }
            }
            PackageInfo packageInfo3 = pmsImp.getPackageInfo(str7, 2L, UserHandle.getUserId(i));
            if (packageInfo3 != null && (activityInfoArr2 = packageInfo3.receivers) != null) {
                for (ActivityInfo activityInfo : activityInfoArr2) {
                    if (activityInfo != null
                            && (str3 = activityInfo.processName) != null
                            && str != null
                            && applicationInfo != null
                            && (compareProcessNamesAndCalculateHash2 =
                                            compareProcessNamesAndCalculateHash(
                                                    i, str, str7, str3, applicationInfo))
                                    != null) {
                        return compareProcessNamesAndCalculateHash2;
                    }
                }
            }
            PackageInfo packageInfo4 = pmsImp.getPackageInfo(str7, 1L, UserHandle.getUserId(i));
            if (packageInfo4 != null && (activityInfoArr = packageInfo4.activities) != null) {
                for (ActivityInfo activityInfo2 : activityInfoArr) {
                    if (activityInfo2 != null
                            && (str2 = activityInfo2.processName) != null
                            && str != null
                            && applicationInfo != null
                            && (compareProcessNamesAndCalculateHash =
                                            compareProcessNamesAndCalculateHash(
                                                    i, str, str7, str2, applicationInfo))
                                    != null) {
                        return compareProcessNamesAndCalculateHash;
                    }
                }
            }
        }
        return null;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getHash(java.io.File r8) {
        /*
            java.lang.String r0 = "close FileInputStream: IOException"
            java.lang.String r1 = "NetworkAnalytics:NetworkAnalyticsDataDelivery"
            r2 = 0
            java.lang.String r3 = "SHA-256"
            java.security.MessageDigest r3 = java.security.MessageDigest.getInstance(r3)     // Catch: java.lang.Throwable -> L62 java.io.IOException -> L64 java.io.FileNotFoundException -> L68 java.security.NoSuchAlgorithmException -> L6c
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L62 java.io.IOException -> L64 java.io.FileNotFoundException -> L68 java.security.NoSuchAlgorithmException -> L6c
            r4.<init>(r8)     // Catch: java.lang.Throwable -> L62 java.io.IOException -> L64 java.io.FileNotFoundException -> L68 java.security.NoSuchAlgorithmException -> L6c
            r8 = 1024(0x400, float:1.435E-42)
            byte[] r8 = new byte[r8]     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L25 java.io.FileNotFoundException -> L28 java.security.NoSuchAlgorithmException -> L2b
        L15:
            int r5 = r4.read(r8)     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L25 java.io.FileNotFoundException -> L28 java.security.NoSuchAlgorithmException -> L2b
            r6 = -1
            r7 = 0
            if (r5 == r6) goto L2f
            r3.update(r8, r7, r5)     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L25 java.io.FileNotFoundException -> L28 java.security.NoSuchAlgorithmException -> L2b
            goto L15
        L21:
            r8 = move-exception
            r2 = r4
            goto L9b
        L25:
            r8 = move-exception
            r3 = r2
            goto L70
        L28:
            r8 = move-exception
            r3 = r2
            goto L7c
        L2b:
            r8 = move-exception
            r3 = r2
            goto L88
        L2f:
            byte[] r8 = r3.digest()     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L25 java.io.FileNotFoundException -> L28 java.security.NoSuchAlgorithmException -> L2b
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L25 java.io.FileNotFoundException -> L28 java.security.NoSuchAlgorithmException -> L2b
            r3.<init>()     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L25 java.io.FileNotFoundException -> L28 java.security.NoSuchAlgorithmException -> L2b
        L38:
            int r5 = r8.length     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L53 java.io.FileNotFoundException -> L55 java.security.NoSuchAlgorithmException -> L57
            if (r7 >= r5) goto L59
            java.lang.String r5 = "%02X"
            r6 = r8[r7]     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L53 java.io.FileNotFoundException -> L55 java.security.NoSuchAlgorithmException -> L57
            r6 = r6 & 255(0xff, float:3.57E-43)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L53 java.io.FileNotFoundException -> L55 java.security.NoSuchAlgorithmException -> L57
            java.lang.Object[] r6 = new java.lang.Object[]{r6}     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L53 java.io.FileNotFoundException -> L55 java.security.NoSuchAlgorithmException -> L57
            java.lang.String r5 = java.lang.String.format(r5, r6)     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L53 java.io.FileNotFoundException -> L55 java.security.NoSuchAlgorithmException -> L57
            r3.append(r5)     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L53 java.io.FileNotFoundException -> L55 java.security.NoSuchAlgorithmException -> L57
            int r7 = r7 + 1
            goto L38
        L53:
            r8 = move-exception
            goto L70
        L55:
            r8 = move-exception
            goto L7c
        L57:
            r8 = move-exception
            goto L88
        L59:
            r4.close()     // Catch: java.io.IOException -> L5d
            goto L93
        L5d:
            r8 = move-exception
            android.util.Log.e(r1, r0, r8)
            goto L93
        L62:
            r8 = move-exception
            goto L9b
        L64:
            r8 = move-exception
            r3 = r2
            r4 = r3
            goto L70
        L68:
            r8 = move-exception
            r3 = r2
            r4 = r3
            goto L7c
        L6c:
            r8 = move-exception
            r3 = r2
            r4 = r3
            goto L88
        L70:
            java.lang.String r5 = "getHash: IOException"
            android.util.Log.e(r1, r5, r8)     // Catch: java.lang.Throwable -> L21
            if (r4 == 0) goto L93
            r4.close()     // Catch: java.io.IOException -> L5d
            goto L93
        L7c:
            java.lang.String r5 = "getHash: FileNotFoundException"
            android.util.Log.e(r1, r5, r8)     // Catch: java.lang.Throwable -> L21
            if (r4 == 0) goto L93
            r4.close()     // Catch: java.io.IOException -> L5d
            goto L93
        L88:
            java.lang.String r5 = "getHash: NoSuchAlgorithmException"
            android.util.Log.e(r1, r5, r8)     // Catch: java.lang.Throwable -> L21
            if (r4 == 0) goto L93
            r4.close()     // Catch: java.io.IOException -> L5d
        L93:
            if (r3 != 0) goto L96
            return r2
        L96:
            java.lang.String r8 = r3.toString()
            return r8
        L9b:
            if (r2 == 0) goto La5
            r2.close()     // Catch: java.io.IOException -> La1
            goto La5
        La1:
            r2 = move-exception
            android.util.Log.e(r1, r0, r2)
        La5:
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.nap.NetworkAnalyticsDataDelivery.getHash(java.io.File):java.lang.String");
    }

    public static String getHashFromCache(int i, String str) {
        Set<AppInfoSet> set = appset;
        synchronized (set) {
            try {
                for (AppInfoSet appInfoSet : set) {
                    if (appInfoSet.uid != i
                            || (!appInfoSet.processName.equalsIgnoreCase(str)
                                    && !appInfoSet.truncatedProcessName.equalsIgnoreCase(str))) {}
                    return appInfoSet.hash;
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static NetworkAnalyticsDataDelivery getInstance() {
        if (mInstance == null) {
            NetworkAnalyticsDataDelivery networkAnalyticsDataDelivery =
                    new NetworkAnalyticsDataDelivery();
            networkAnalyticsDataDelivery.dataEntry = null;
            networkAnalyticsDataDelivery.startTimer = 0L;
            networkAnalyticsDataDelivery.registeredDataRecipients = null;
            networkAnalyticsDataDelivery.syncObject = new Object();
            networkAnalyticsDataDelivery.mHandlerThread = null;
            networkAnalyticsDataDelivery.mHandler = null;
            networkAnalyticsDataDelivery.dataEntry = new ArrayList();
            networkAnalyticsDataDelivery.startTimer = System.currentTimeMillis();
            mInstance = networkAnalyticsDataDelivery;
        }
        return mInstance;
    }

    public static String getPackageHash(int i, String str) {
        String hashFromCache;
        try {
            hashFromCache = getHashFromCache(i, str);
        } catch (Exception e) {
            Log.e("NetworkAnalytics:NetworkAnalyticsDataDelivery", "getPackageHash: Exception", e);
        }
        if (hashFromCache != null) {
            return hashFromCache;
        }
        String checkIfProcessIsDaemon = checkIfProcessIsDaemon(str);
        if (checkIfProcessIsDaemon == null
                || checkIfProcessIsDaemon.isEmpty()
                || checkIfProcessIsDaemon.equals("null")) {
            String checkSingleUidAndCalculateHash = checkSingleUidAndCalculateHash(i, str);
            if (checkSingleUidAndCalculateHash != null) {
                return checkSingleUidAndCalculateHash;
            }
            String fileLocationFromProcessNameAndCalculateHash =
                    getFileLocationFromProcessNameAndCalculateHash(i, str);
            if (fileLocationFromProcessNameAndCalculateHash != null) {
                return fileLocationFromProcessNameAndCalculateHash;
            }
            String packageNameFromPathAndCalculateHash =
                    getPackageNameFromPathAndCalculateHash(i, str);
            if (packageNameFromPathAndCalculateHash != null) {
                return packageNameFromPathAndCalculateHash;
            }
        } else {
            File file = new File(checkIfProcessIsDaemon);
            if (!file.exists()) {
                return null;
            }
            String hash = getHash(file);
            if (hash != null) {
                return hash;
            }
        }
        return null;
    }

    public static String getPackageNameFromPathAndCalculateHash(int i, String str) {
        String[] packagesForUid;
        ApplicationInfo applicationInfo;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pmsImp == null) {
            return null;
        }
        String[] split = str.split("/");
        if (split.length >= 4) {
            String str2 =
                    split[2].equals("data")
                            ? split[3]
                            : (split.length >= 5
                                            && split[2].equals("user")
                                            && Integer.toString(UserHandle.getUserId(i))
                                                    .equals(split[3]))
                                    ? split[4]
                                    : null;
            if (str2 == null || (packagesForUid = pmsImp.getPackagesForUid(i)) == null) {
                return null;
            }
            for (String str3 : packagesForUid) {
                if (str3.equalsIgnoreCase(str2)
                        && (applicationInfo =
                                        pmsImp.getApplicationInfo(
                                                str3, 0L, UserHandle.getUserId(i)))
                                != null
                        && applicationInfo.sourceDir != null) {
                    File file = new File(applicationInfo.sourceDir);
                    if (!file.exists()) {
                        Log.d(
                                "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                                "unable to find the file location for the deamon path:"
                                        + str
                                        + " for uid "
                                        + i);
                        return null;
                    }
                    String hash = getHash(file);
                    if (hash != null) {
                        return hash;
                    }
                }
            }
        }
        return null;
    }

    public static void insertHashIntoCache(AppInfoSet appInfoSet) {
        boolean z = DBG;
        if (z) {
            Log.d("NetworkAnalytics:NetworkAnalyticsDataDelivery", "insertHashIntoCache Called");
        }
        Set set = appset;
        synchronized (set) {
            if (z) {
                try {
                    Log.d(
                            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                            "DataDelivery hash cache insertion uid:"
                                    + appInfoSet.uid
                                    + " pacName:"
                                    + appInfoSet.packageName
                                    + " procName:"
                                    + appInfoSet.processName
                                    + " trunProcName:"
                                    + appInfoSet.truncatedProcessName
                                    + " hash:"
                                    + appInfoSet.hash);
                } catch (Throwable th) {
                    throw th;
                }
            }
            set.add(appInfoSet);
        }
    }

    public final void addNAPDataRecipient(DataDeliveryHelper dataDeliveryHelper) {
        if ((dataDeliveryHelper.profile == null || dataDeliveryHelper.serviceConnection == null)
                && DBG) {
            VpnManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("adding recipient failed for recipient: "),
                    dataDeliveryHelper.identifier,
                    "NetworkAnalytics:NetworkAnalyticsDataDelivery");
        }
        String str = dataDeliveryHelper.identifier;
        boolean z = DBG;
        if (z) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "adding recipient for data collection:",
                    str,
                    "NetworkAnalytics:NetworkAnalyticsDataDelivery");
        }
        synchronized (this.syncObject) {
            try {
                if (isDataRecipientPresent(
                                NetworkAnalyticsService.getCidFromTransformedName(str),
                                str.substring(0, str.indexOf("__")))
                        < 0) {
                    getRecipientList().add(dataDeliveryHelper);
                    if (z) {
                        Log.d(
                                "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                                "added recipient for data collection:".concat(str));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getRecipientList() {
        List list;
        synchronized (this.syncObject) {
            try {
                if (this.registeredDataRecipients == null) {
                    this.registeredDataRecipients = new ArrayList();
                }
                list = this.registeredDataRecipients;
            } catch (Throwable th) {
                throw th;
            }
        }
        return list;
    }

    public final void initializeHandlerThread() {
        HandlerThread handlerThread = new HandlerThread("DataDeliveryHandler", 10);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new DataDeliveryHandler(this.mHandlerThread.getLooper());
    }

    public final int isDataRecipientPresent(int i, String str) {
        List recipientList = getRecipientList();
        for (int i2 = 0; i2 < recipientList.size(); i2++) {
            if (((DataDeliveryHelper) recipientList.get(i2))
                    .identifier.equals(NetworkAnalyticsService.getTransformedVendorName(i, str))) {
                if (!DBG) {
                    return i2;
                }
                Log.d(
                        "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                        "isDataRecipientPresent: found recipient:"
                                + NetworkAnalyticsService.getTransformedVendorName(i, str));
                return i2;
            }
        }
        return -1;
    }

    public final void removeDataRecipientsForPackage(int i, String str) {
        synchronized (this.syncObject) {
            try {
                Iterator it = getRecipientList().iterator();
                while (it.hasNext()) {
                    DataDeliveryHelper dataDeliveryHelper = (DataDeliveryHelper) it.next();
                    if (dataDeliveryHelper.profile.packageName.equals(str)) {
                        NetworkAnalyticsConfigStore.NAPConfigProfile nAPConfigProfile =
                                dataDeliveryHelper.profile;
                        if (nAPConfigProfile.userId == i) {
                            Log.d(
                                    "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                                    "removeDataRecipientsForPackage: removing recipient for"
                                        + " package:"
                                            + str
                                            + i
                                            + nAPConfigProfile.profileName);
                            it.remove();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeNAPDataRecipient(int i, String str) {
        synchronized (this.syncObject) {
            try {
                int isDataRecipientPresent = isDataRecipientPresent(i, str);
                if (isDataRecipientPresent < 0) {
                    return;
                }
                if (DBG) {
                    Log.d(
                            "NetworkAnalytics:NetworkAnalyticsDataDelivery",
                            "removeNAPDataRecipient: removing recipient for data collection:"
                                    + NetworkAnalyticsService.getTransformedVendorName(i, str));
                }
                getRecipientList().remove(isDataRecipientPresent);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
