package com.android.server.enterprise.threatdefense;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.SemSystemProperties;
import android.os.UserHandle;
import android.util.Log;
import android.util.SparseArray;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.threatdefense.IThreatDefenseService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import org.json.JSONException;

/* loaded from: classes2.dex */
public final class ThreatDefenseService extends IThreatDefenseService.Stub implements EnterpriseServiceCallback {
    static final String BRAKET_END_STRING = "\\)";
    static final String BRAKET_START_STRING = "\\(";
    public final Context mContext;
    public EnterpriseDeviceManager mEnterpriseDeviceManager;
    public KnoxAnalyticsThread mKnoxAnalytics;
    public static final String TAG = ThreatDefenseService.class.getSimpleName();
    static final int[] SENSTIVE_PROCESS_PROC_POSITION = {28, 29, 30, 45, 46, 47, 48, 49, 50, 51};
    static final String[] SENSITIVE_PROCESS_PROC_LIST = {"stat"};
    static final int[] SENSITIVE_PROCESS_PROC_LEN_LIST = {52};
    static final String[] RESTRICTED_CHAR_LIST = {KnoxVpnFirewallHelper.DELIMITER, "*", "."};
    public static final Hashtable sAllowedProcRules = new Hashtable();
    public static final Hashtable sAllowedProcessProcRules = new Hashtable();
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static SparseArray sProcessIds = new SparseArray();
    public static final Object sLock = new Object();
    public Timer mTimer = new Timer();
    public final TimerTask mTimerTask = new TimerTask() { // from class: com.android.server.enterprise.threatdefense.ThreatDefenseService.1
        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (ThreatDefenseService.this.mKnoxAnalytics == null) {
                ThreatDefenseService.this.mKnoxAnalytics = new KnoxAnalyticsThread();
            }
            ThreatDefenseService.this.mKnoxAnalytics.schedule();
            ThreatDefenseService.this.resetPackageRules();
        }
    };
    public BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.threatdefense.ThreatDefenseService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Uri data;
            if (!"android.intent.action.PACKAGE_REMOVED".equals(intent.getAction()) || (data = intent.getData()) == null) {
                return;
            }
            String schemeSpecificPart = data.getSchemeSpecificPart();
            try {
                if (ThreatDefenseService.sAllowedProcRules.containsKey(schemeSpecificPart)) {
                    ThreatDefenseService.sAllowedProcRules.remove(schemeSpecificPart);
                    Log.i(ThreatDefenseService.TAG, schemeSpecificPart + " rules are removed");
                }
                if (ThreatDefenseService.sAllowedProcessProcRules.containsKey(schemeSpecificPart)) {
                    ThreatDefenseService.sAllowedProcessProcRules.remove(schemeSpecificPart);
                    Log.i(ThreatDefenseService.TAG, schemeSpecificPart + " process rules are removed");
                }
                if (ThreatDefenseService.sAllowedProcRules.size() == 0 && ThreatDefenseService.sAllowedProcessProcRules.size() == 0) {
                    SemSystemProperties.set("sys.mtdl.start", "false");
                }
            } catch (NullPointerException e) {
                Log.w(ThreatDefenseService.TAG, "pkg=" + schemeSpecificPart + ", " + e.getMessage());
            }
        }
    };

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
    }

    public ThreatDefenseService(Context context) {
        String str = TAG;
        Log.d(str, "Start ThreatDefenseService");
        Log.d(str, "pid = " + Binder.getCallingPid() + ", uid = " + Binder.getCallingUid());
        this.mContext = context;
        initIntervalTasks();
        initReceiver();
    }

    public final void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
        intentFilter.setPriority(1000);
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x002b, code lost:
    
        if (r3 < 60000) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initIntervalTasks() {
        if (this.mTimer == null) {
            this.mTimer = new Timer("MTDL_Timer");
        }
        boolean z = DEBUG;
        long j = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        if (z) {
            long j2 = SemSystemProperties.getLong("sys.mtdl.interval", BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
            if (j2 != BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) {
                Log.d(TAG, "Custom interval applied");
                j = 60000;
            }
            j = j2;
        }
        this.mTimer.scheduleAtFixedRate(this.mTimerTask, 0L, j);
        Log.i(TAG, "Timer Scheduled : " + (j / 1000) + "s");
    }

    public final EnterpriseDeviceManager getEnterpriseDeviceManagerService() {
        if (this.mEnterpriseDeviceManager == null) {
            this.mEnterpriseDeviceManager = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEnterpriseDeviceManager;
    }

    public final void enforceThreatDefensePermission(ContextInfo contextInfo) {
        getEnterpriseDeviceManagerService().enforcePermissionByContext(contextInfo, "com.samsung.android.knox.permission.KNOX_MOBILE_THREAT_DEFENSE");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v10, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r5v11, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.io.IOException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v13, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v14, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9 */
    public String procReader(ContextInfo contextInfo, String str) {
        String str2 = null;
        r1 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        BufferedReader bufferedReader = null;
        if (contextInfo == null) {
            Log.e(TAG, "ContextInfo is null");
            return null;
        }
        enforceThreatDefensePermission(contextInfo);
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        this.mKnoxAnalytics.countApiCall(nameForUid, 0);
        if (isAllowedProc(nameForUid, str, false) < 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        ?? r6 = "/proc/";
        sb.append("/proc/");
        sb.append(str);
        ?? e = sb.toString();
        try {
            try {
                try {
                    r6 = new FileInputStream((String) e);
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = e;
                }
                try {
                    e = new BufferedReader(new InputStreamReader((InputStream) r6, StandardCharsets.UTF_8));
                } catch (FileNotFoundException e2) {
                    e = e2;
                    e = 0;
                } catch (SecurityException e3) {
                    e = e3;
                    e = 0;
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e4) {
                            Log.e(TAG, "IOException", e4);
                            throw th;
                        }
                    }
                    if (r6 != 0) {
                        r6.close();
                    }
                    throw th;
                }
                try {
                    String str3 = (String) e.lines().collect(Collectors.joining(System.lineSeparator()));
                    try {
                        e.close();
                        r6.close();
                        e = e;
                        r6 = r6;
                    } catch (IOException e5) {
                        String str4 = TAG;
                        Log.e(str4, "IOException", e5);
                        e = e5;
                        r6 = str4;
                    }
                    str2 = str3;
                } catch (FileNotFoundException e6) {
                    e = e6;
                    Log.e(TAG, "FileNotFoundException : " + e.getMessage());
                    if (e != 0) {
                        e.close();
                    }
                    if (r6 != 0) {
                        r6.close();
                    }
                    return str2;
                } catch (SecurityException e7) {
                    e = e7;
                    Log.e(TAG, "SecurityException", e);
                    if (e != 0) {
                        e.close();
                    }
                    if (r6 != 0) {
                        r6.close();
                    }
                    return str2;
                }
            } catch (FileNotFoundException e8) {
                e = e8;
                e = 0;
                r6 = 0;
            } catch (SecurityException e9) {
                e = e9;
                e = 0;
                r6 = 0;
            } catch (Throwable th3) {
                th = th3;
                r6 = 0;
            }
        } catch (IOException e10) {
            e = e10;
            r6 = TAG;
            Log.e(r6, "IOException", e);
        }
        return str2;
    }

    public int[] getProcessId(ContextInfo contextInfo, String str) {
        int i;
        if (contextInfo == null) {
            Log.e(TAG, "ContextInfo is null");
            return null;
        }
        enforceThreatDefensePermission(contextInfo);
        this.mKnoxAnalytics.countApiCall(this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid), 2);
        if (str == null || str.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        synchronized (sLock) {
            updateProcessIds();
            if ("all".equals(str)) {
                for (int i2 = 0; i2 < sProcessIds.size(); i2++) {
                    arrayList.add(Integer.valueOf(sProcessIds.keyAt(i2)));
                }
            } else {
                for (int i3 = 0; i3 < sProcessIds.size(); i3++) {
                    if (str.equals(sProcessIds.valueAt(i3))) {
                        arrayList.add(Integer.valueOf(sProcessIds.keyAt(i3)));
                    }
                }
            }
        }
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (i = 0; i < size; i++) {
            iArr[i] = ((Integer) arrayList.get(i)).intValue();
        }
        return iArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v10, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r10v5 */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v7, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r11v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v20 */
    /* JADX WARN: Type inference failed for: r11v21 */
    /* JADX WARN: Type inference failed for: r11v22 */
    /* JADX WARN: Type inference failed for: r11v23 */
    /* JADX WARN: Type inference failed for: r11v24 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v6, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r11v8 */
    public String processProcReader(ContextInfo contextInfo, String str, int i) {
        String str2;
        BufferedReader bufferedReader = null;
        if (contextInfo == null) {
            Log.e(TAG, "ContextInfo is null");
            return null;
        }
        enforceThreatDefensePermission(contextInfo);
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        this.mKnoxAnalytics.countApiCall(nameForUid, 1);
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
            Log.e(TAG, "Get sensitive proc failed : " + i3);
            return null;
        }
        if (i < 1) {
            return null;
        }
        String valueOf = String.valueOf(i);
        ?? sb = new StringBuilder();
        sb.append("/proc/");
        sb.append(valueOf);
        sb.append("/");
        sb.append(str);
        try {
            try {
                try {
                    str = new FileInputStream(sb.toString());
                    try {
                        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader((InputStream) str, StandardCharsets.UTF_8));
                        try {
                            try {
                                str2 = (String) bufferedReader2.lines().collect(Collectors.joining(System.lineSeparator()));
                                sb = str2;
                                if (z) {
                                    try {
                                        sb = removeSensitiveProc(str2, i3);
                                    } catch (FileNotFoundException e) {
                                        e = e;
                                        bufferedReader = bufferedReader2;
                                        str = str;
                                        sb = str2;
                                        Log.e(TAG, "FileNotFoundException : " + e.getMessage());
                                        if (bufferedReader != null) {
                                            bufferedReader.close();
                                        }
                                        if (str != 0) {
                                            str.close();
                                        }
                                        return sb;
                                    } catch (SecurityException e2) {
                                        e = e2;
                                        bufferedReader = bufferedReader2;
                                        str = str;
                                        sb = str2;
                                        Log.e(TAG, "SecurityException", e);
                                        if (bufferedReader != null) {
                                            bufferedReader.close();
                                        }
                                        if (str != 0) {
                                            str.close();
                                        }
                                        return sb;
                                    }
                                }
                                bufferedReader2.close();
                                str.close();
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader = bufferedReader2;
                                if (bufferedReader != null) {
                                    try {
                                        bufferedReader.close();
                                    } catch (IOException e3) {
                                        Log.e(TAG, "IOException", e3);
                                        throw th;
                                    }
                                }
                                if (str != 0) {
                                    str.close();
                                }
                                throw th;
                            }
                        } catch (FileNotFoundException e4) {
                            e = e4;
                            str2 = null;
                        } catch (SecurityException e5) {
                            e = e5;
                            str2 = null;
                        }
                    } catch (FileNotFoundException e6) {
                        e = e6;
                        sb = 0;
                        str = str;
                    } catch (SecurityException e7) {
                        e = e7;
                        sb = 0;
                        str = str;
                    }
                } catch (IOException e8) {
                    Log.e(TAG, "IOException", e8);
                }
            } catch (FileNotFoundException e9) {
                e = e9;
                str = 0;
                sb = 0;
            } catch (SecurityException e10) {
                e = e10;
                str = 0;
                sb = 0;
            } catch (Throwable th2) {
                th = th2;
                str = 0;
            }
            return sb;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public int setPackageRules(ContextInfo contextInfo, String str) {
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
                Log.e(TAG, "set system property failed");
            }
        }
        try {
            String verifiedData = new MTDSignature(str).getVerifiedData();
            if (verifiedData == null) {
                Log.e(TAG, "Signature verification failed");
                return -108;
            }
            RuleParser ruleParser = new RuleParser(verifiedData);
            if (!hasValidSignature(nameForUid, ruleParser.getPackagePublicSignature())) {
                return -108;
            }
            RUFSPolicy rUFSPolicy = new RUFSPolicy(nameForUid);
            int parseInt = Integer.parseInt(ruleParser.getVersion());
            int policyVersion = rUFSPolicy.getPolicyVersion();
            String str2 = TAG;
            Log.i(str2, "app/rufs version : " + parseInt + "/" + policyVersion);
            if (parseInt < policyVersion) {
                return -100;
            }
            if (!nameForUid.equals(ruleParser.getPackageName())) {
                Log.e(str2, "Invalid package rules. " + nameForUid + "|" + ruleParser.getPackageName());
                return KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID;
            }
            Hashtable hashtable = sAllowedProcRules;
            if (hashtable.containsKey(nameForUid)) {
                Log.i(str2, "Replace proc rules : " + nameForUid);
                hashtable.remove(nameForUid);
            }
            Hashtable hashtable2 = sAllowedProcessProcRules;
            if (hashtable2.containsKey(nameForUid)) {
                Log.i(str2, "Replace process proc rules : " + nameForUid);
                hashtable2.remove(nameForUid);
            }
            hashtable.put(nameForUid, ruleParser.getProcList());
            hashtable2.put(nameForUid, ruleParser.getProcessProcList());
            return 0;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -108;
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            return KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID;
        } catch (NumberFormatException e3) {
            e3.printStackTrace();
            return -104;
        } catch (JSONException e4) {
            Log.w(TAG, "Initialize json object failed : " + e4.getMessage());
            e4.printStackTrace();
            return -107;
        }
    }

    public boolean hasPackageRules(ContextInfo contextInfo) {
        enforceThreatDefensePermission(contextInfo);
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        if (nameForUid == null) {
            Log.e(TAG, "Get package error");
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

    public final int isAllowedProcRules(String str, String str2, boolean z) {
        Hashtable hashtable = new Hashtable();
        try {
            if (z) {
                hashtable.putAll(sAllowedProcessProcRules);
            } else {
                hashtable.putAll(sAllowedProcRules);
            }
            if (!hashtable.containsKey(str)) {
                Log.i(TAG, "Please set package rules first : " + str);
                return -102;
            }
            ArrayList arrayList = (ArrayList) hashtable.get(str);
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    if (((String) it.next()).equals(str2)) {
                        if (!hasRestrictCharacter(str2)) {
                            return 0;
                        }
                        Log.e(TAG, "Not allowed proc : " + str2);
                        return -103;
                    }
                }
            }
            Log.d(TAG, "No rules : " + str + "|" + str2);
            return -102;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return -104;
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            return KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID;
        }
    }

    public String removeSensitiveProc(String str, int i) {
        String[] strArr;
        if (str == null || !str.contains(" ") || !str.contains("(") || !str.contains(")")) {
            Log.e(TAG, "Invalid argument : " + str);
            return null;
        }
        int[] iArr = SENSITIVE_PROCESS_PROC_LEN_LIST;
        int i2 = iArr[i];
        String[] strArr2 = new String[i2];
        String[] split = str.split(BRAKET_START_STRING);
        int i3 = 2;
        if (split.length == 2) {
            strArr2[0] = split[0].trim();
            String[] split2 = split[1].split(BRAKET_END_STRING);
            if (split2.length == 2) {
                strArr2[1] = split2[0].trim();
                strArr = split2[1].trim().split(" ");
            } else {
                strArr = null;
            }
            if (strArr != null && strArr.length == iArr[i] - 2) {
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
                        Log.e(TAG, "Invalid length " + i6 + "|" + i2);
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
            String str3 = TAG;
            Log.e(str3, "Remove sensitive data failed, pid=" + strArr2[0] + ", name=" + strArr2[1]);
            if (DEBUG) {
                Log.e(str3, "Sensitive data result=" + str);
            }
            if (strArr != null) {
                Log.e(str3, "Remained len=" + strArr.length);
            }
            return null;
        }
        Log.e(TAG, "Invalid format line=" + str);
        return null;
    }

    public int isAllowedProc(String str, String str2, boolean z) {
        if (str == null) {
            Log.e(TAG, "Get package name failed");
            return KnoxCustomManagerService.DOCK_SHORTCUT_CONTAINER_ID;
        }
        if (hasRestrictCharacter(str2)) {
            Log.e(TAG, "Denied proc = " + str2 + " reason : Restrict Character");
            return -103;
        }
        if (z) {
            try {
                if (Files.isSymbolicLink(Paths.get(str2, new String[0]))) {
                    Log.e(TAG, "Denied proc = " + str2 + " reason : Symbolic Link");
                    return -105;
                }
            } catch (InvalidPathException e) {
                Log.e(TAG, "Invalid path p = " + str2 + ", err = " + e);
                return -105;
            }
        }
        int isAllowedProcRules = isAllowedProcRules(str, str2, z);
        if (isAllowedProcRules < 0) {
            Log.e(TAG, "Denied proc : " + str2 + ", errno=" + isAllowedProcRules);
        }
        return isAllowedProcRules;
    }

    public boolean hasRestrictCharacter(String str) {
        for (String str2 : RESTRICTED_CHAR_LIST) {
            if (str.contains(str2)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x008a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0095 A[ADDED_TO_REGION, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateProcessIds() {
        int i;
        long currentTimeMillis = System.currentTimeMillis();
        File file = new File("/proc/");
        synchronized (sLock) {
            sProcessIds.clear();
            if (file.listFiles() != null) {
                UserPrincipal userPrincipal = null;
                for (File file2 : file.listFiles()) {
                    String name = file2.getName();
                    if (name.matches("^[0-9]+")) {
                        try {
                            i = Integer.parseInt(name);
                            try {
                                userPrincipal = Files.getOwner(Paths.get("/proc/" + name, new String[0]), new LinkOption[0]);
                            } catch (IOException e) {
                                e = e;
                                Log.w(TAG, "IOException" + e);
                                if (i <= 0) {
                                    sProcessIds.put(i, userPrincipal.getName());
                                }
                            } catch (NumberFormatException e2) {
                                e = e2;
                                Log.w(TAG, "NumberFormatException" + e);
                                if (i <= 0) {
                                }
                            }
                        } catch (IOException e3) {
                            e = e3;
                            i = 0;
                        } catch (NumberFormatException e4) {
                            e = e4;
                            i = 0;
                        }
                        if (i <= 0 && userPrincipal != null) {
                            sProcessIds.put(i, userPrincipal.getName());
                        }
                    }
                }
            }
        }
        Log.i(TAG, "Update PIDs took " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
    }

    public boolean hasValidSignature(String str, String str2) {
        try {
            SigningInfo signingInfo = PackageManagerAdapter.getInstance(this.mContext).getPackageInfo(str, 134217728, UserHandle.getUserId(Binder.getCallingUid())).signingInfo;
            if (signingInfo.hasMultipleSigners()) {
                Log.i(TAG, "package : " + str + " hasMultipleSigners");
            }
            Signature[] apkContentsSigners = signingInfo.getApkContentsSigners();
            if (apkContentsSigners == null) {
                Log.e(TAG, "getApkContentsSigners() failed");
                return false;
            }
            String charsString = apkContentsSigners[0].toCharsString();
            if (charsString == null || str2 == null) {
                Log.e(TAG, "Get package signature failed");
                return false;
            }
            if (charsString.equals(str2)) {
                return true;
            }
            Log.e(TAG, "Signature check failed");
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Invalid package : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public final void resetPackageRules() {
        Hashtable hashtable = sAllowedProcRules;
        int size = hashtable.size();
        Hashtable hashtable2 = sAllowedProcessProcRules;
        if (size + hashtable2.size() < 1) {
            if (DEBUG) {
                Log.d(TAG, "Skip!! No rules");
            }
        } else {
            hashtable.clear();
            hashtable2.clear();
            notifyToPackages();
        }
    }

    public final void notifyToPackages() {
        Log.i(TAG, "Send broadcast");
        this.mContext.sendBroadcast(new Intent("com.samsung.android.knox.intent.action.MTDL_PACKAGE_RULES_REMOVED"));
    }
}
