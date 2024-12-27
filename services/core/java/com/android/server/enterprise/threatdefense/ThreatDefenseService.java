package com.android.server.enterprise.threatdefense;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.SemSystemProperties;
import android.os.UserHandle;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.OomAdjuster$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.threatdefense.IThreatDefenseService;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Timer;
import java.util.stream.Collectors;
import org.json.JSONException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ThreatDefenseService extends IThreatDefenseService.Stub implements EnterpriseServiceCallback {
    static final String BRAKET_END_STRING = "\\)";
    static final String BRAKET_START_STRING = "\\(";
    public final Context mContext;
    public EnterpriseDeviceManager mEnterpriseDeviceManager;
    public KnoxAnalyticsThread mKnoxAnalytics;
    public final AnonymousClass2 mReceiver;
    public final Timer mTimer;
    public final AnonymousClass1 mTimerTask;
    static final int[] SENSTIVE_PROCESS_PROC_POSITION = {28, 29, 30, 45, 46, 47, 48, 49, 50, 51};
    static final String[] SENSITIVE_PROCESS_PROC_LIST = {"stat"};
    static final int[] SENSITIVE_PROCESS_PROC_LEN_LIST = {52};
    static final String[] RESTRICTED_CHAR_LIST = {";", "*", "."};
    public static final Hashtable sAllowedProcRules = new Hashtable();
    public static final Hashtable sAllowedProcessProcRules = new Hashtable();
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final SparseArray sProcessIds = new SparseArray();
    public static final Object sLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.threatdefense.ThreatDefenseService$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Uri data;
            if (!"android.intent.action.PACKAGE_REMOVED".equals(intent.getAction()) || (data = intent.getData()) == null) {
                return;
            }
            String schemeSpecificPart = data.getSchemeSpecificPart();
            try {
                Hashtable hashtable = ThreatDefenseService.sAllowedProcRules;
                if (hashtable.containsKey(schemeSpecificPart)) {
                    hashtable.remove(schemeSpecificPart);
                    Log.i("ThreatDefenseService", schemeSpecificPart + " rules are removed");
                }
                Hashtable hashtable2 = ThreatDefenseService.sAllowedProcessProcRules;
                if (hashtable2.containsKey(schemeSpecificPart)) {
                    hashtable2.remove(schemeSpecificPart);
                    Log.i("ThreatDefenseService", schemeSpecificPart + " process rules are removed");
                }
                if (hashtable.size() == 0 && hashtable2.size() == 0) {
                    SemSystemProperties.set("sys.mtdl.start", "false");
                }
            } catch (NullPointerException e) {
                Hashtable hashtable3 = ThreatDefenseService.sAllowedProcRules;
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("pkg=", schemeSpecificPart, ", ");
                m.append(e.getMessage());
                Log.w("ThreatDefenseService", m.toString());
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0068, code lost:
    
        if (r5 < 60000) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ThreatDefenseService(android.content.Context r11) {
        /*
            r10 = this;
            r10.<init>()
            java.util.Timer r0 = new java.util.Timer
            r0.<init>()
            r10.mTimer = r0
            com.android.server.enterprise.threatdefense.ThreatDefenseService$1 r2 = new com.android.server.enterprise.threatdefense.ThreatDefenseService$1
            r2.<init>()
            com.android.server.enterprise.threatdefense.ThreatDefenseService$2 r0 = new com.android.server.enterprise.threatdefense.ThreatDefenseService$2
            r0.<init>()
            java.lang.String r7 = "ThreatDefenseService"
            java.lang.String r1 = "Start ThreatDefenseService"
            android.util.Log.d(r7, r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "pid = "
            r1.<init>(r3)
            int r3 = android.os.Binder.getCallingPid()
            r1.append(r3)
            java.lang.String r3 = ", uid = "
            r1.append(r3)
            int r3 = android.os.Binder.getCallingUid()
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r7, r1)
            r10.mContext = r11
            java.util.Timer r1 = r10.mTimer
            if (r1 != 0) goto L4c
            java.util.Timer r1 = new java.util.Timer
            java.lang.String r3 = "MTDL_Timer"
            r1.<init>(r3)
            r10.mTimer = r1
        L4c:
            boolean r1 = com.android.server.enterprise.threatdefense.ThreatDefenseService.DEBUG
            r3 = 86400000(0x5265c00, double:4.2687272E-316)
            if (r1 == 0) goto L6a
            java.lang.String r1 = "sys.mtdl.interval"
            long r5 = android.os.SemSystemProperties.getLong(r1, r3)
            int r1 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r1 == 0) goto L6c
            java.lang.String r1 = "Custom interval applied"
            android.util.Log.d(r7, r1)
            r3 = 60000(0xea60, double:2.9644E-319)
            int r1 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r1 >= 0) goto L6c
        L6a:
            r8 = r3
            goto L6d
        L6c:
            r8 = r5
        L6d:
            java.util.Timer r1 = r10.mTimer
            r3 = 0
            r5 = r8
            r1.scheduleAtFixedRate(r2, r3, r5)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r1 = "Timer Scheduled : "
            r10.<init>(r1)
            r1 = 1000(0x3e8, double:4.94E-321)
            long r8 = r8 / r1
            r10.append(r8)
            java.lang.String r1 = "s"
            r10.append(r1)
            java.lang.String r10 = r10.toString()
            android.util.Log.i(r7, r10)
            android.content.IntentFilter r10 = new android.content.IntentFilter
            java.lang.String r1 = "android.intent.action.PACKAGE_REMOVED"
            r10.<init>(r1)
            r1 = 1000(0x3e8, float:1.401E-42)
            r10.setPriority(r1)
            java.lang.String r1 = "package"
            r10.addDataScheme(r1)
            r11.registerReceiver(r0, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.threatdefense.ThreatDefenseService.<init>(android.content.Context):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x008c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0097 A[ADDED_TO_REGION, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void updateProcessIds() {
        /*
            long r0 = java.lang.System.currentTimeMillis()
            java.io.File r2 = new java.io.File
            java.lang.String r3 = "/proc/"
            r2.<init>(r3)
            java.lang.Object r3 = com.android.server.enterprise.threatdefense.ThreatDefenseService.sLock
            monitor-enter(r3)
            android.util.SparseArray r4 = com.android.server.enterprise.threatdefense.ThreatDefenseService.sProcessIds     // Catch: java.lang.Throwable -> L53
            r4.clear()     // Catch: java.lang.Throwable -> L53
            java.io.File[] r4 = r2.listFiles()     // Catch: java.lang.Throwable -> L53
            if (r4 == 0) goto L9a
            java.io.File[] r2 = r2.listFiles()     // Catch: java.lang.Throwable -> L53
            int r4 = r2.length     // Catch: java.lang.Throwable -> L53
            r5 = 0
            r6 = 0
            r7 = r5
        L21:
            if (r7 >= r4) goto L9a
            r8 = r2[r7]     // Catch: java.lang.Throwable -> L53
            java.lang.String r8 = r8.getName()     // Catch: java.lang.Throwable -> L53
            java.lang.String r9 = "^[0-9]+"
            boolean r9 = r8.matches(r9)     // Catch: java.lang.Throwable -> L53
            if (r9 == 0) goto L97
            int r9 = java.lang.Integer.parseInt(r8)     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L59 java.lang.NumberFormatException -> L72
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55 java.lang.NumberFormatException -> L57
            r10.<init>()     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55 java.lang.NumberFormatException -> L57
            java.lang.String r11 = "/proc/"
            r10.append(r11)     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55 java.lang.NumberFormatException -> L57
            r10.append(r8)     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55 java.lang.NumberFormatException -> L57
            java.lang.String r8 = r10.toString()     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55 java.lang.NumberFormatException -> L57
            java.lang.String[] r10 = new java.lang.String[r5]     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55 java.lang.NumberFormatException -> L57
            java.nio.file.Path r8 = java.nio.file.Paths.get(r8, r10)     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55 java.lang.NumberFormatException -> L57
            java.nio.file.LinkOption[] r10 = new java.nio.file.LinkOption[r5]     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55 java.lang.NumberFormatException -> L57
            java.nio.file.attribute.UserPrincipal r6 = java.nio.file.Files.getOwner(r8, r10)     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L55 java.lang.NumberFormatException -> L57
            goto L8a
        L53:
            r0 = move-exception
            goto Lba
        L55:
            r8 = move-exception
            goto L5b
        L57:
            r8 = move-exception
            goto L74
        L59:
            r8 = move-exception
            r9 = r5
        L5b:
            java.lang.String r10 = "ThreatDefenseService"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L53
            r11.<init>()     // Catch: java.lang.Throwable -> L53
            java.lang.String r12 = "IOException"
            r11.append(r12)     // Catch: java.lang.Throwable -> L53
            r11.append(r8)     // Catch: java.lang.Throwable -> L53
            java.lang.String r8 = r11.toString()     // Catch: java.lang.Throwable -> L53
            android.util.Log.w(r10, r8)     // Catch: java.lang.Throwable -> L53
            goto L8a
        L72:
            r8 = move-exception
            r9 = r5
        L74:
            java.lang.String r10 = "ThreatDefenseService"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L53
            r11.<init>()     // Catch: java.lang.Throwable -> L53
            java.lang.String r12 = "NumberFormatException"
            r11.append(r12)     // Catch: java.lang.Throwable -> L53
            r11.append(r8)     // Catch: java.lang.Throwable -> L53
            java.lang.String r8 = r11.toString()     // Catch: java.lang.Throwable -> L53
            android.util.Log.w(r10, r8)     // Catch: java.lang.Throwable -> L53
        L8a:
            if (r9 <= 0) goto L97
            if (r6 == 0) goto L97
            android.util.SparseArray r8 = com.android.server.enterprise.threatdefense.ThreatDefenseService.sProcessIds     // Catch: java.lang.Throwable -> L53
            java.lang.String r10 = r6.getName()     // Catch: java.lang.Throwable -> L53
            r8.put(r9, r10)     // Catch: java.lang.Throwable -> L53
        L97:
            int r7 = r7 + 1
            goto L21
        L9a:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L53
            long r2 = java.lang.System.currentTimeMillis()
            long r2 = r2 - r0
            java.lang.String r0 = "ThreatDefenseService"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r4 = "Update PIDs took "
            r1.<init>(r4)
            r1.append(r2)
            java.lang.String r2 = "ms"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.i(r0, r1)
            return
        Lba:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L53
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.threatdefense.ThreatDefenseService.updateProcessIds():void");
    }

    public final void enforceThreatDefensePermission(ContextInfo contextInfo) {
        if (this.mEnterpriseDeviceManager == null) {
            this.mEnterpriseDeviceManager = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        this.mEnterpriseDeviceManager.enforcePermissionByContext(contextInfo, "com.samsung.android.knox.permission.KNOX_MOBILE_THREAT_DEFENSE");
    }

    public final int[] getProcessId(ContextInfo contextInfo, String str) {
        int i;
        if (contextInfo == null) {
            Log.e("ThreatDefenseService", "ContextInfo is null");
            return null;
        }
        enforceThreatDefensePermission(contextInfo);
        this.mKnoxAnalytics.countApiCall(2, this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid));
        if (str == null || str.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        synchronized (sLock) {
            try {
                updateProcessIds();
                if (!"all".equals(str)) {
                    int i2 = 0;
                    while (true) {
                        SparseArray sparseArray = sProcessIds;
                        if (i2 >= sparseArray.size()) {
                            break;
                        }
                        if (str.equals(sparseArray.valueAt(i2))) {
                            arrayList.add(Integer.valueOf(sparseArray.keyAt(i2)));
                        }
                        i2++;
                    }
                } else {
                    int i3 = 0;
                    while (true) {
                        SparseArray sparseArray2 = sProcessIds;
                        if (i3 >= sparseArray2.size()) {
                            break;
                        }
                        arrayList.add(Integer.valueOf(sparseArray2.keyAt(i3)));
                        i3++;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (i = 0; i < size; i++) {
            iArr[i] = ((Integer) arrayList.get(i)).intValue();
        }
        return iArr;
    }

    public final boolean hasPackageRules(ContextInfo contextInfo) {
        enforceThreatDefensePermission(contextInfo);
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        if (nameForUid == null) {
            Log.e("ThreatDefenseService", "Get package error");
            return false;
        }
        try {
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (sAllowedProcRules.containsKey(nameForUid)) {
            return true;
        }
        return sAllowedProcessProcRules.containsKey(nameForUid);
    }

    public boolean hasRestrictCharacter(String str) {
        for (String str2 : RESTRICTED_CHAR_LIST) {
            if (str.contains(str2)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasValidSignature(String str, String str2) {
        int callingUid = Binder.getCallingUid();
        PackageManagerAdapter packageManagerAdapter = PackageManagerAdapter.getInstance(this.mContext);
        try {
            int userId = UserHandle.getUserId(callingUid);
            packageManagerAdapter.getClass();
            SigningInfo signingInfo = PackageManagerAdapter.getPackageInfo(134217728, userId, str).signingInfo;
            if (signingInfo.hasMultipleSigners()) {
                AudioDeviceInventory$$ExternalSyntheticOutline0.m("package : ", str, " hasMultipleSigners", "ThreatDefenseService");
            }
            Signature[] apkContentsSigners = signingInfo.getApkContentsSigners();
            if (apkContentsSigners == null) {
                Log.e("ThreatDefenseService", "getApkContentsSigners() failed");
                return false;
            }
            String charsString = apkContentsSigners[0].toCharsString();
            if (charsString == null || str2 == null) {
                Log.e("ThreatDefenseService", "Get package signature failed");
                return false;
            }
            if (charsString.equals(str2)) {
                return true;
            }
            Log.e("ThreatDefenseService", "Signature check failed");
            return false;
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(e, new StringBuilder("Invalid package : "), "ThreatDefenseService");
            return false;
        }
    }

    public int isAllowedProc(String str, String str2, boolean z) {
        int i = KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID;
        if (str == null) {
            Log.e("ThreatDefenseService", "Get package name failed");
            return KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID;
        }
        int i2 = -103;
        if (hasRestrictCharacter(str2)) {
            Log.e("ThreatDefenseService", "Denied proc = " + str2 + " reason : Restrict Character");
            return -103;
        }
        if (z) {
            try {
                if (Files.isSymbolicLink(Paths.get(str2, new String[0]))) {
                    Log.e("ThreatDefenseService", "Denied proc = " + str2 + " reason : Symbolic Link");
                    return -105;
                }
            } catch (InvalidPathException e) {
                Log.e("ThreatDefenseService", "Invalid path p = " + str2 + ", err = " + e);
                return -105;
            }
        }
        Hashtable hashtable = new Hashtable();
        try {
            if (z) {
                hashtable.putAll(sAllowedProcessProcRules);
            } else {
                hashtable.putAll(sAllowedProcRules);
            }
        } catch (ClassCastException e2) {
            e2.printStackTrace();
            i = -104;
        } catch (NullPointerException e3) {
            e3.printStackTrace();
        }
        if (hashtable.containsKey(str)) {
            ArrayList arrayList = (ArrayList) hashtable.get(str);
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    if (((String) it.next()).equals(str2)) {
                        if (hasRestrictCharacter(str2)) {
                            Log.e("ThreatDefenseService", "Not allowed proc : " + str2);
                        } else {
                            i2 = 0;
                        }
                    }
                }
            }
            Log.d("ThreatDefenseService", "No rules : " + str + "|" + str2);
            i = -102;
            i2 = i;
        } else {
            Log.i("ThreatDefenseService", "Please set package rules first : ".concat(str));
            i2 = -102;
        }
        if (i2 < 0) {
            Log.e("ThreatDefenseService", "Denied proc : " + str2 + ", errno=" + i2);
        }
        return i2;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.server.enterprise.threatdefense.KnoxAnalyticsThread] */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.server.enterprise.threatdefense.ThreatDefenseService] */
    /* JADX WARN: Type inference failed for: r6v10, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v11, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.io.IOException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9 */
    /* JADX WARN: Type inference failed for: r7v10, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v11, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.io.InputStream] */
    public final String procReader(ContextInfo contextInfo, String str) {
        String str2 = null;
        r2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        BufferedReader bufferedReader = null;
        if (contextInfo == null) {
            Log.e("ThreatDefenseService", "ContextInfo is null");
            return null;
        }
        enforceThreatDefensePermission(contextInfo);
        ?? nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        this.mKnoxAnalytics.countApiCall(0, nameForUid);
        if (isAllowedProc(nameForUid, str, false) < 0) {
            return null;
        }
        ?? e = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("/proc/", str);
        try {
        } catch (IOException e2) {
            e = e2;
            Log.e("ThreatDefenseService", "IOException", e);
        }
        try {
            try {
                nameForUid = new FileInputStream((String) e);
                try {
                    e = new BufferedReader(new InputStreamReader((InputStream) nameForUid, StandardCharsets.UTF_8));
                } catch (FileNotFoundException e3) {
                    e = e3;
                    e = 0;
                } catch (SecurityException e4) {
                    e = e4;
                    e = 0;
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e5) {
                            Log.e("ThreatDefenseService", "IOException", e5);
                            throw th;
                        }
                    }
                    if (nameForUid != 0) {
                        nameForUid.close();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e6) {
                e = e6;
                e = 0;
                nameForUid = 0;
            } catch (SecurityException e7) {
                e = e7;
                e = 0;
                nameForUid = 0;
            } catch (Throwable th2) {
                th = th2;
                nameForUid = 0;
            }
            try {
                String str3 = (String) e.lines().collect(Collectors.joining(System.lineSeparator()));
                try {
                    e.close();
                    nameForUid.close();
                    e = e;
                } catch (IOException e8) {
                    Log.e("ThreatDefenseService", "IOException", e8);
                    e = e8;
                }
                str2 = str3;
            } catch (FileNotFoundException e9) {
                e = e9;
                Log.e("ThreatDefenseService", "FileNotFoundException : " + e.getMessage());
                if (e != 0) {
                    e.close();
                }
                if (nameForUid != 0) {
                    nameForUid.close();
                }
                return str2;
            } catch (SecurityException e10) {
                e = e10;
                Log.e("ThreatDefenseService", "SecurityException", e);
                if (e != 0) {
                    e.close();
                }
                if (nameForUid != 0) {
                    nameForUid.close();
                }
                return str2;
            }
            return str2;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = e;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v10 */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v13 */
    /* JADX WARN: Type inference failed for: r11v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v6 */
    /* JADX WARN: Type inference failed for: r11v7, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r11v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r11v9, types: [java.io.FileInputStream, java.io.InputStream] */
    public final String processProcReader(ContextInfo contextInfo, String str, int i) {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        if (contextInfo == null) {
            Log.e("ThreatDefenseService", "ContextInfo is null");
            return null;
        }
        enforceThreatDefensePermission(contextInfo);
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        this.mKnoxAnalytics.countApiCall(1, nameForUid);
        if (isAllowedProc(nameForUid, str, true) < 0) {
            return null;
        }
        String[] strArr = SENSITIVE_PROCESS_PROC_LIST;
        int length = strArr.length;
        boolean z = false;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            if (strArr[i2].equals(str)) {
                z = true;
                break;
            }
            i3++;
            i2++;
        }
        if (i3 > SENSITIVE_PROCESS_PROC_LIST.length) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i3, "Get sensitive proc failed : ", "ThreatDefenseService");
            return null;
        }
        if (i < 1) {
            return null;
        }
        String valueOf = String.valueOf(i);
        String str2 = "/proc/";
        try {
        } catch (IOException e) {
            Log.e("ThreatDefenseService", "IOException", e);
        }
        try {
            try {
                str = new FileInputStream(BootReceiver$$ExternalSyntheticOutline0.m("/proc/", valueOf, "/", (String) str));
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader((InputStream) str, StandardCharsets.UTF_8));
                } catch (FileNotFoundException e2) {
                    e = e2;
                    str2 = null;
                    str = str;
                } catch (SecurityException e3) {
                    e = e3;
                    str2 = null;
                    str = str;
                }
            } catch (FileNotFoundException e4) {
                e = e4;
                str = 0;
                str2 = null;
            } catch (SecurityException e5) {
                e = e5;
                str = 0;
                str2 = null;
            } catch (Throwable th) {
                th = th;
                str = 0;
            }
            try {
                try {
                    str2 = (String) bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
                    if (z) {
                        try {
                            str2 = removeSensitiveProc(str2, i3);
                        } catch (FileNotFoundException e6) {
                            e = e6;
                            bufferedReader2 = bufferedReader;
                            str = str;
                            Log.e("ThreatDefenseService", "FileNotFoundException : " + e.getMessage());
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            if (str != 0) {
                                str.close();
                            }
                            return str2;
                        } catch (SecurityException e7) {
                            e = e7;
                            bufferedReader2 = bufferedReader;
                            str = str;
                            Log.e("ThreatDefenseService", "SecurityException", e);
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            if (str != 0) {
                                str.close();
                            }
                            return str2;
                        }
                    }
                    bufferedReader.close();
                    str.close();
                } catch (FileNotFoundException e8) {
                    e = e8;
                    str2 = null;
                } catch (SecurityException e9) {
                    e = e9;
                    str2 = null;
                }
                return str2;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e10) {
                        Log.e("ThreatDefenseService", "IOException", e10);
                        throw th;
                    }
                }
                if (str != 0) {
                    str.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public String removeSensitiveProc(String str, int i) {
        String[] strArr;
        if (str == null || !str.contains(" ") || !str.contains("(") || !str.contains(")")) {
            StorageManagerService$$ExternalSyntheticOutline0.m("Invalid argument : ", str, "ThreatDefenseService");
            return null;
        }
        int[] iArr = SENSITIVE_PROCESS_PROC_LEN_LIST;
        int i2 = iArr[i];
        String[] strArr2 = new String[i2];
        String[] split = str.split(BRAKET_START_STRING);
        int i3 = 2;
        if (split.length != 2) {
            Log.e("ThreatDefenseService", "Invalid format line=".concat(str));
            return null;
        }
        strArr2[0] = split[0].trim();
        String[] split2 = split[1].split(BRAKET_END_STRING);
        if (split2.length == 2) {
            strArr2[1] = split2[0].trim();
            strArr = split2[1].trim().split(" ");
        } else {
            strArr = null;
        }
        if (strArr == null || strArr.length != iArr[i] - 2) {
            Log.e("ThreatDefenseService", "Remove sensitive data failed, pid=" + strArr2[0] + ", name=" + strArr2[1]);
            if (DEBUG) {
                Log.e("ThreatDefenseService", "Sensitive data result=".concat(str));
            }
            if (strArr != null) {
                Log.e("ThreatDefenseService", "Remained len=" + strArr.length);
            }
            return null;
        }
        int length = strArr.length;
        int i4 = 0;
        while (i4 < length) {
            strArr2[i3] = strArr[i4];
            i4++;
            i3++;
        }
        for (int i5 : SENSTIVE_PROCESS_PROC_POSITION) {
            int i6 = i5 - 1;
            if (i6 == i2) {
                Log.e("ThreatDefenseService", "Invalid length " + i6 + "|" + i2);
            } else {
                strArr2[i6] = "";
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i7 = 0; i7 < i2; i7++) {
            String str2 = strArr2[i7];
            if (!"".equals(str2)) {
                sb.append(str2);
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    public final int setPackageRules(ContextInfo contextInfo, String str) {
        if (str == null || str.isEmpty()) {
            return -106;
        }
        enforceThreatDefensePermission(contextInfo);
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        if (nameForUid == null) {
            return KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID;
        }
        if (!SemSystemProperties.getBoolean("sys.mtdl.start", false)) {
            SemSystemProperties.set("sys.mtdl.start", "true");
            if (!SemSystemProperties.getBoolean("sys.mtdl.start", false)) {
                Log.e("ThreatDefenseService", "set system property failed");
            }
        }
        try {
            String verifiedData = new MTDSignature(str).getVerifiedData();
            if (verifiedData == null) {
                Log.e("ThreatDefenseService", "Signature verification failed");
                return -108;
            }
            RuleParser ruleParser = new RuleParser(verifiedData);
            if (!hasValidSignature(nameForUid, ruleParser.mPacakagePublicSignature)) {
                return -108;
            }
            RUFSPolicy rUFSPolicy = new RUFSPolicy(nameForUid);
            int parseInt = Integer.parseInt(ruleParser.mPolicyVersion);
            int i = rUFSPolicy.mPolicyVersion;
            Log.i("ThreatDefenseService", "app/rufs version : " + parseInt + "/" + i);
            if (parseInt < i) {
                return -100;
            }
            if (!nameForUid.equals(ruleParser.mPackageName)) {
                Log.e("ThreatDefenseService", "Invalid package rules. " + nameForUid + "|" + ruleParser.mPackageName);
                return KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID;
            }
            Hashtable hashtable = sAllowedProcRules;
            if (hashtable.containsKey(nameForUid)) {
                Log.i("ThreatDefenseService", "Replace proc rules : ".concat(nameForUid));
                hashtable.remove(nameForUid);
            }
            Hashtable hashtable2 = sAllowedProcessProcRules;
            if (hashtable2.containsKey(nameForUid)) {
                Log.i("ThreatDefenseService", "Replace process proc rules : ".concat(nameForUid));
                hashtable2.remove(nameForUid);
            }
            hashtable.put(nameForUid, ruleParser.getProcList());
            hashtable2.put(nameForUid, ruleParser.getProcessProcList());
            return 0;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return -104;
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            return -108;
        } catch (JSONException e4) {
            Log.w("ThreatDefenseService", "Initialize json object failed : " + e4.getMessage());
            e4.printStackTrace();
            return -107;
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
    }
}
