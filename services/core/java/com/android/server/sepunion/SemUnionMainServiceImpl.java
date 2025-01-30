package com.android.server.sepunion;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.util.ArrayMap;
import com.android.internal.util.DumpUtils;
import com.samsung.android.cover.CoverState;
import com.samsung.android.sepunion.IUnionManager;
import com.samsung.android.sepunion.Log;
import com.samsung.android.sepunion.SemUnionManagerLocal;
import com.samsung.android.sepunion.UnionConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public class SemUnionMainServiceImpl extends IUnionManager.Stub {
    public final Context mContext;
    public final Handler mHandler;
    public boolean mIsBootCompleted = false;
    public final SemUnionManagerLocal mSemUnionManagerLocal = new SemUnionManagerLocal() { // from class: com.android.server.sepunion.SemUnionMainServiceImpl.1
        public void accessoryStateChanged(boolean z, byte[] bArr, byte[] bArr2) {
        }

        public void screenTurnedOff() {
        }

        public void notifySmartCoverAttachStateChanged(long j, boolean z, CoverState coverState) {
            Log.d(SemUnionMainServiceImpl.TAG, "notifySmartCoverAttachStateChanged");
            SemPluginManagerService semPluginManagerService = (SemPluginManagerService) SemUnionMainServiceImpl.getSemSystemService("plugin");
            if (semPluginManagerService == null) {
                SemUnionMainServiceImpl.this.mSemUnionManagerLocal.createSemSystemService("plugin");
                semPluginManagerService = (SemPluginManagerService) SemUnionMainServiceImpl.getSemSystemService("plugin");
            }
            if (semPluginManagerService != null) {
                semPluginManagerService.notifySmartCoverAttachStateChanged(j, z, coverState);
            } else {
                Log.e(SemUnionMainServiceImpl.TAG, "notifySmartCoverAttachStateChanged : create system fail");
            }
        }

        public void notifyCoverSwitchStateChanged(long j, boolean z) {
            Log.d(SemUnionMainServiceImpl.TAG, "notifyCoverSwitchStateChanged");
            SemPluginManagerService semPluginManagerService = (SemPluginManagerService) SemUnionMainServiceImpl.getSemSystemService("plugin");
            if (semPluginManagerService != null) {
                semPluginManagerService.notifyCoverSwitchStateChanged(j, z);
            } else {
                Log.e(SemUnionMainServiceImpl.TAG, "notifyCoverSwitchStateChanged : there is no system");
            }
        }

        public IBinder getSemSystemService(String str, Bundle bundle) {
            return SemUnionMainServiceImpl.this.getSemSystemService(str, bundle);
        }

        public void createSemSystemService(String str) {
            synchronized (SemUnionMainServiceImpl.sLock) {
                if (SemUnionMainServiceImpl.sSemSystemServiceMap.containsKey(str) && SemUnionMainServiceImpl.sSemSystemServiceMap.get(str) != null) {
                    Log.w(SemUnionMainServiceImpl.TAG, "Already existing service : " + str);
                    return;
                }
                SemUnionMainServiceImpl semUnionMainServiceImpl = SemUnionMainServiceImpl.this;
                semUnionMainServiceImpl.addSepUnionServiceMapInternal(str, semUnionMainServiceImpl.mContext);
            }
        }
    };
    public static final String TAG = SemUnionMainServiceImpl.class.getSimpleName();
    public static final HashMap sConstructorMap = new HashMap();
    public static final HashMap sSemSystemServiceMap = new HashMap();
    public static final HashMap sUnionIbinderMap = new HashMap();
    public static final HashMap sDumpInformationMap = new HashMap();
    public static final Map sPendingSepUnionServiceCreators = new ArrayMap();
    public static final Object sLock = new Object();

    public SemUnionMainServiceImpl(Context context) {
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper());
        initializeSystemMapData();
        createServiceByStartType();
        makeDirectoryForAppLog();
    }

    public final void initializeSystemMapData() {
        synchronized (sLock) {
            for (Map.Entry entry : UnionConstants.sClassPathForService.entrySet()) {
                Constructor constructor = getConstructor((String) entry.getValue());
                if (constructor != null) {
                    String str = (String) entry.getKey();
                    sConstructorMap.put(str, constructor);
                    sSemSystemServiceMap.put(str, null);
                    sUnionIbinderMap.put(str, null);
                }
            }
        }
    }

    public final void createServiceByStartType() {
        for (Map.Entry entry : UnionConstants.sServiceStartType.entrySet()) {
            String str = (String) entry.getKey();
            if (((Integer) entry.getValue()).intValue() == 0) {
                this.mSemUnionManagerLocal.createSemSystemService(str);
            } else {
                createPendingSepUnionService(str);
            }
        }
    }

    public final Constructor getConstructor(String str) {
        try {
            return Class.forName(str).getConstructor(Context.class);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static AbsSemSystemService getSemSystemService(String str) {
        AbsSemSystemService absSemSystemService;
        synchronized (sLock) {
            HashMap hashMap = sSemSystemServiceMap;
            if (!hashMap.containsKey(str) || (absSemSystemService = (AbsSemSystemService) hashMap.get(str)) == null) {
                return null;
            }
            return absSemSystemService;
        }
    }

    public final void createPendingSepUnionService(String str) {
        synchronized (sLock) {
            sPendingSepUnionServiceCreators.put(str, new SepUnionServiceCreator(str));
        }
    }

    public final IBinder getSepUnionService(String str) {
        synchronized (sLock) {
            Map map = sPendingSepUnionServiceCreators;
            SepUnionServiceCreator sepUnionServiceCreator = (SepUnionServiceCreator) map.get(str);
            if (sepUnionServiceCreator == null) {
                return null;
            }
            map.remove(str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return sepUnionServiceCreator.createService(this.mContext);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void addSepUnionServiceMapInternal(String str, Context context) {
        String str2 = TAG;
        Log.d(str2, "addSepUnionServiceMapInternal : " + str);
        synchronized (sLock) {
            HashMap hashMap = sConstructorMap;
            if (hashMap.containsKey(str)) {
                try {
                    Object newInstance = ((Constructor) hashMap.get(str)).newInstance(context);
                    if (newInstance != null) {
                        AbsSemSystemService absSemSystemService = (AbsSemSystemService) newInstance;
                        sSemSystemServiceMap.put(str, absSemSystemService);
                        sUnionIbinderMap.put(str, (IBinder) absSemSystemService);
                        absSemSystemService.onCreate(new Bundle());
                    } else {
                        Log.e(str2, "addSepUnionServiceMapInternal : obj is NULL");
                    }
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class SepUnionServiceCreator {
        public final String mName;

        public SepUnionServiceCreator(String str) {
            this.mName = str;
        }

        public IBinder createService(Context context) {
            SemUnionMainServiceImpl.this.addSepUnionServiceMapInternal(this.mName, context);
            return (IBinder) SemUnionMainServiceImpl.sUnionIbinderMap.get(this.mName);
        }
    }

    public IBinder getSemSystemService(String str, Bundle bundle) {
        String str2 = TAG;
        Log.d(str2, "getSemSystemService - " + str);
        synchronized (sLock) {
            IBinder iBinder = (IBinder) sUnionIbinderMap.get(str);
            if (iBinder != null) {
                return iBinder;
            }
            if (!this.mIsBootCompleted) {
                Log.d(str2, "getSemSystemService : boot is not completed");
                return null;
            }
            IBinder sepUnionService = getSepUnionService(str);
            Log.d(str2, "getSemSystemService - " + str + " : " + sepUnionService);
            return sepUnionService;
        }
    }

    public void setDumpEnabled(String str, String str2) {
        String str3 = TAG;
        Log.d(str3, "setDumpEnabled = " + str);
        enforeCallingPermission("setDumpEnabled");
        synchronized (sLock) {
            HashMap hashMap = sDumpInformationMap;
            if (hashMap.keySet().contains(str)) {
                Log.d(str3, "setDumpEnabled Already Exist");
            } else {
                hashMap.put(str, str2);
            }
        }
    }

    public void onBootPhase(int i) {
        synchronized (sLock) {
            Iterator it = sSemSystemServiceMap.entrySet().iterator();
            while (it.hasNext()) {
                AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                if (absSemSystemService != null) {
                    absSemSystemService.onBootPhase(i);
                }
            }
            if (i == 1000 && !this.mIsBootCompleted) {
                this.mIsBootCompleted = true;
            }
        }
    }

    public void onUserStarting(int i) {
        Log.d(TAG, "onUserStarting");
        synchronized (sLock) {
            Iterator it = sSemSystemServiceMap.entrySet().iterator();
            while (it.hasNext()) {
                AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                if (absSemSystemService != null && (absSemSystemService instanceof AbsSemSystemServiceForS)) {
                    ((AbsSemSystemServiceForS) absSemSystemService).onUserStarting(i);
                }
            }
        }
    }

    public void onUserUnlocking(int i) {
        Log.d(TAG, "onUserUnlocking");
        synchronized (sLock) {
            Iterator it = sSemSystemServiceMap.entrySet().iterator();
            while (it.hasNext()) {
                AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                if (absSemSystemService != null && (absSemSystemService instanceof AbsSemSystemServiceForS)) {
                    ((AbsSemSystemServiceForS) absSemSystemService).onUserUnlocking(i);
                }
            }
        }
    }

    public void onUserUnlocked(int i) {
        Log.d(TAG, "onUserUnlocked");
        synchronized (sLock) {
            Iterator it = sSemSystemServiceMap.entrySet().iterator();
            while (it.hasNext()) {
                AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                if (absSemSystemService != null && (absSemSystemService instanceof AbsSemSystemServiceForS)) {
                    ((AbsSemSystemServiceForS) absSemSystemService).onUserUnlocked(i);
                }
            }
        }
    }

    public void onUserSwitching(int i, int i2) {
        Log.d(TAG, "onUserSwitching");
        synchronized (sLock) {
            Iterator it = sSemSystemServiceMap.entrySet().iterator();
            while (it.hasNext()) {
                AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                if (absSemSystemService != null && (absSemSystemService instanceof AbsSemSystemServiceForS)) {
                    ((AbsSemSystemServiceForS) absSemSystemService).onUserSwitching(i, i2);
                }
            }
        }
    }

    public void onUserStopping(int i) {
        Log.d(TAG, "onUserStopping");
        synchronized (sLock) {
            Iterator it = sSemSystemServiceMap.entrySet().iterator();
            while (it.hasNext()) {
                AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                if (absSemSystemService != null && (absSemSystemService instanceof AbsSemSystemServiceForS)) {
                    ((AbsSemSystemServiceForS) absSemSystemService).onUserStopping(i);
                }
            }
        }
    }

    public void onUserStopped(int i) {
        Log.d(TAG, "onUserStopped");
        synchronized (sLock) {
            Iterator it = sSemSystemServiceMap.entrySet().iterator();
            while (it.hasNext()) {
                AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                if (absSemSystemService != null && (absSemSystemService instanceof AbsSemSystemServiceForS)) {
                    ((AbsSemSystemServiceForS) absSemSystemService).onUserStopped(i);
                }
            }
        }
    }

    public final void makeDirectoryForAppLog() {
        File file = new File("/data/log/sepunion/");
        if (file.exists() || !file.mkdir()) {
            return;
        }
        FileUtils.setPermissions(file, 511, -1, -1);
    }

    public final void enforeCallingPermission(String str) {
        int callingUid = Binder.getCallingUid();
        if (this.mContext.getPackageManager().checkSignatures(1000, callingUid) == 0) {
            return;
        }
        String str2 = "Permission denied: " + str + " callingUid=" + callingUid + ", you need system uid of to be signed with the system certificate.";
        Log.d(TAG, str2);
        throw new SecurityException(str2);
    }

    public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        AbsSemSystemService absSemSystemService;
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 2000) {
            resultReceiver.send(-1, null);
            throw new SecurityException("Shell commands are only callable by ADB");
        }
        synchronized (sLock) {
            if (strArr.length > 0 && (absSemSystemService = (AbsSemSystemService) sSemSystemServiceMap.get(strArr[0])) != null) {
                absSemSystemService.onShellCommand(fileDescriptor, fileDescriptor2, fileDescriptor3, strArr.length != 1 ? (String[]) Arrays.copyOfRange(strArr, 1, strArr.length) : null, shellCallback, resultReceiver);
            } else {
                super.onShellCommand(fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        HashMap hashMap;
        boolean z;
        boolean z2;
        if (DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            if (strArr.length <= 0) {
                dumpInternal(fileDescriptor, printWriter, strArr);
                return;
            }
            Object obj = sLock;
            synchronized (obj) {
                hashMap = sSemSystemServiceMap;
                if (hashMap.keySet().contains(strArr[0])) {
                    z2 = false;
                    z = true;
                } else if (sDumpInformationMap.keySet().contains(strArr[0])) {
                    z = false;
                    z2 = true;
                } else {
                    z = false;
                    z2 = false;
                }
            }
            if (z) {
                synchronized (obj) {
                    AbsSemSystemService absSemSystemService = (AbsSemSystemService) hashMap.get(strArr[0]);
                    if (absSemSystemService != null) {
                        if (strArr.length == 1) {
                            absSemSystemService.dump(fileDescriptor, printWriter, null);
                        } else {
                            absSemSystemService.dump(fileDescriptor, printWriter, (String[]) Arrays.copyOfRange(strArr, 1, strArr.length));
                        }
                    }
                }
                return;
            }
            if (z2) {
                synchronized (obj) {
                    HashMap hashMap2 = sDumpInformationMap;
                    if (hashMap2.get(strArr[0]) != null) {
                        String str = strArr[0];
                        dumpFromFile(str, (String) hashMap2.get(str), printWriter);
                    } else {
                        dumpHistoryLog(strArr[0], fileDescriptor, printWriter, strArr);
                    }
                }
                return;
            }
            if (strArr[0].equals("servicelist")) {
                dumpServiceList(printWriter);
            } else {
                dumpInternal(fileDescriptor, printWriter, strArr);
            }
        }
    }

    public final void dumpInternal(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("\n##### SEP UNION Main SERVICE #####\n##### (dumpsys sepunion) #####\n");
        dumpServiceList(printWriter);
        synchronized (sLock) {
            Iterator it = sSemSystemServiceMap.entrySet().iterator();
            while (it.hasNext()) {
                AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                if (absSemSystemService != null) {
                    absSemSystemService.dump(fileDescriptor, printWriter, strArr);
                }
            }
            for (Map.Entry entry : sDumpInformationMap.entrySet()) {
                String str = (String) entry.getKey();
                String str2 = (String) entry.getValue();
                if (str2 != null) {
                    dumpFromFile(str, str2, printWriter);
                } else {
                    dumpHistoryLog(str, fileDescriptor, printWriter, strArr);
                }
            }
        }
    }

    public final void dumpServiceList(PrintWriter printWriter) {
        int i;
        String str = "";
        String str2 = "";
        synchronized (sLock) {
            i = 0;
            for (Map.Entry entry : sSemSystemServiceMap.entrySet()) {
                if (entry.getValue() != null) {
                    i++;
                    str = str + ((String) entry.getKey()) + " ";
                }
            }
            for (Map.Entry entry2 : sPendingSepUnionServiceCreators.entrySet()) {
                if (entry2.getValue() != null) {
                    str2 = str2 + ((String) entry2.getKey()) + " ";
                }
            }
        }
        printWriter.println("Number of SEP Union service = " + i);
        printWriter.println("SEP Union service list(activated) : " + str);
        printWriter.println("SEP Union service list(pending) : " + str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v18, types: [java.io.InputStreamReader, java.io.Reader] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v12, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r6v9 */
    public final void dumpFromFile(String str, String str2, PrintWriter printWriter) {
        BufferedReader bufferedReader;
        IOException e;
        FileNotFoundException e2;
        AutoCloseable autoCloseable;
        AutoCloseable autoCloseable2;
        ?? file = new File(str2);
        if (!file.exists()) {
            Log.d(TAG, "This log file does not exist : " + str2);
            return;
        }
        ?? sb = new StringBuilder();
        sb.append("\n##### DUMP OF ");
        sb.append(str);
        ?? r0 = " #####\n##### (dumpsys sepunion ";
        sb.append(" #####\n##### (dumpsys sepunion ");
        sb.append(str);
        sb.append(") #####\n");
        printWriter.println(sb.toString());
        try {
            try {
                sb = new FileInputStream((File) file);
            } catch (Throwable th) {
                th = th;
            }
            try {
                file = new InputStreamReader(sb);
            } catch (FileNotFoundException e3) {
                e = e3;
                bufferedReader = null;
                sb = sb;
                e2 = e;
                file = bufferedReader;
                e2.printStackTrace();
                autoCloseable2 = file;
                autoCloseable = sb;
                IoUtils.closeQuietly(bufferedReader);
                IoUtils.closeQuietly(autoCloseable2);
                IoUtils.closeQuietly(autoCloseable);
            } catch (IOException e4) {
                e = e4;
                bufferedReader = null;
                sb = sb;
                e = e;
                file = bufferedReader;
                e.printStackTrace();
                autoCloseable2 = file;
                autoCloseable = sb;
                IoUtils.closeQuietly(bufferedReader);
                IoUtils.closeQuietly(autoCloseable2);
                IoUtils.closeQuietly(autoCloseable);
            } catch (Throwable th2) {
                th = th2;
                r0 = 0;
                sb = sb;
                th = th;
                file = r0;
                IoUtils.closeQuietly((AutoCloseable) r0);
                IoUtils.closeQuietly((AutoCloseable) file);
                IoUtils.closeQuietly((AutoCloseable) sb);
                throw th;
            }
            try {
                bufferedReader = new BufferedReader(file);
                try {
                    StringBuilder sb2 = new StringBuilder();
                    int i = 0;
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null || i > 500) {
                            break;
                        }
                        sb2.append(readLine);
                        sb2.append('\n');
                        i++;
                    }
                    printWriter.print(sb2.toString());
                    autoCloseable2 = file;
                    autoCloseable = sb;
                } catch (FileNotFoundException e5) {
                    e2 = e5;
                    e2.printStackTrace();
                    autoCloseable2 = file;
                    autoCloseable = sb;
                    IoUtils.closeQuietly(bufferedReader);
                    IoUtils.closeQuietly(autoCloseable2);
                    IoUtils.closeQuietly(autoCloseable);
                } catch (IOException e6) {
                    e = e6;
                    e.printStackTrace();
                    autoCloseable2 = file;
                    autoCloseable = sb;
                    IoUtils.closeQuietly(bufferedReader);
                    IoUtils.closeQuietly(autoCloseable2);
                    IoUtils.closeQuietly(autoCloseable);
                }
            } catch (FileNotFoundException e7) {
                bufferedReader = null;
                e2 = e7;
            } catch (IOException e8) {
                bufferedReader = null;
                e = e8;
            } catch (Throwable th3) {
                r0 = 0;
                th = th3;
                IoUtils.closeQuietly((AutoCloseable) r0);
                IoUtils.closeQuietly((AutoCloseable) file);
                IoUtils.closeQuietly((AutoCloseable) sb);
                throw th;
            }
        } catch (FileNotFoundException e9) {
            e = e9;
            sb = 0;
            bufferedReader = null;
        } catch (IOException e10) {
            e = e10;
            sb = 0;
            bufferedReader = null;
        } catch (Throwable th4) {
            th = th4;
            sb = 0;
            r0 = 0;
        }
        IoUtils.closeQuietly(bufferedReader);
        IoUtils.closeQuietly(autoCloseable2);
        IoUtils.closeQuietly(autoCloseable);
    }

    public final void dumpHistoryLog(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("\n##### DUMP OF " + str + " #####\n##### (dumpsys sepunion " + str + ") #####\n");
        Log.dump(str, fileDescriptor, printWriter, strArr);
    }
}
