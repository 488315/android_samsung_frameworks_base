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
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import libcore.io.IoUtils;

public final class SemUnionMainServiceImpl extends IUnionManager.Stub {
    public final Context mContext;
    public boolean mIsBootCompleted = false;
    public final AnonymousClass1 mSemUnionManagerLocal = new SemUnionManagerLocal() { // from class: com.android.server.sepunion.SemUnionMainServiceImpl.1
        public final void accessoryStateChanged(boolean z, byte[] bArr, byte[] bArr2) {
        }

        public final void createSemSystemService(String str) {
            synchronized (SemUnionMainServiceImpl.sLock) {
                try {
                    HashMap hashMap = SemUnionMainServiceImpl.sSemSystemServiceMap;
                    if (!hashMap.containsKey(str) || hashMap.get(str) == null) {
                        SemUnionMainServiceImpl semUnionMainServiceImpl = SemUnionMainServiceImpl.this;
                        SemUnionMainServiceImpl.m869$$Nest$maddSepUnionServiceMapInternal(semUnionMainServiceImpl, str, semUnionMainServiceImpl.mContext);
                    } else {
                        Log.w("SemUnionMainServiceImpl", "Already existing service : " + str);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final IBinder getSemSystemService(String str, Bundle bundle) {
            return SemUnionMainServiceImpl.this.getSemSystemService(str, bundle);
        }

        public final void notifyCoverSwitchStateChanged(long j, boolean z) {
            HashMap hashMap = SemUnionMainServiceImpl.sConstructorMap;
            Log.d("SemUnionMainServiceImpl", "notifyCoverSwitchStateChanged");
            SemPluginManagerService semPluginManagerService = (SemPluginManagerService) SemUnionMainServiceImpl.getSemSystemService();
            if (semPluginManagerService != null) {
                semPluginManagerService.notifyCoverSwitchStateChanged(j, z);
            } else {
                Log.e("SemUnionMainServiceImpl", "notifyCoverSwitchStateChanged : there is no system");
            }
        }

        public final void notifySmartCoverAttachStateChanged(long j, boolean z, CoverState coverState) {
            HashMap hashMap = SemUnionMainServiceImpl.sConstructorMap;
            Log.d("SemUnionMainServiceImpl", "notifySmartCoverAttachStateChanged");
            SemPluginManagerService semPluginManagerService = (SemPluginManagerService) SemUnionMainServiceImpl.getSemSystemService();
            if (semPluginManagerService == null) {
                createSemSystemService("plugin");
                semPluginManagerService = (SemPluginManagerService) SemUnionMainServiceImpl.getSemSystemService();
            }
            if (semPluginManagerService != null) {
                semPluginManagerService.notifySmartCoverAttachStateChanged(j, z, coverState);
            } else {
                Log.e("SemUnionMainServiceImpl", "notifySmartCoverAttachStateChanged : create system fail");
            }
        }

        public final void screenTurnedOff() {
        }
    };
    public static final HashMap sConstructorMap = new HashMap();
    public static final HashMap sSemSystemServiceMap = new HashMap();
    public static final HashMap sUnionIbinderMap = new HashMap();
    public static final HashMap sDumpInformationMap = new HashMap();
    public static final Map sPendingSepUnionServiceCreators = new ArrayMap();
    public static final Object sLock = new Object();

    public final class SepUnionServiceCreator {
        public final String mName;

        public SepUnionServiceCreator(String str) {
            this.mName = str;
        }
    }

    /* renamed from: -$$Nest$maddSepUnionServiceMapInternal, reason: not valid java name */
    public static void m869$$Nest$maddSepUnionServiceMapInternal(SemUnionMainServiceImpl semUnionMainServiceImpl, String str, Context context) {
        semUnionMainServiceImpl.getClass();
        Log.d("SemUnionMainServiceImpl", "addSepUnionServiceMapInternal : " + str);
        synchronized (sLock) {
            try {
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
                            Log.e("SemUnionMainServiceImpl", "addSepUnionServiceMapInternal : obj is NULL");
                        }
                    } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.sepunion.SemUnionMainServiceImpl$1] */
    public SemUnionMainServiceImpl(Context context) {
        Constructor<?> constructor;
        this.mContext = context;
        new Handler(context.getMainLooper());
        synchronized (sLock) {
            try {
                for (Map.Entry entry : UnionConstants.sClassPathForService.entrySet()) {
                    try {
                        constructor = Class.forName((String) entry.getValue()).getConstructor(Context.class);
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        e.printStackTrace();
                        constructor = null;
                    }
                    if (constructor != null) {
                        String str = (String) entry.getKey();
                        sConstructorMap.put(str, constructor);
                        sSemSystemServiceMap.put(str, null);
                        sUnionIbinderMap.put(str, null);
                    }
                }
            } finally {
            }
        }
        for (Map.Entry entry2 : UnionConstants.sServiceStartType.entrySet()) {
            String str2 = (String) entry2.getKey();
            if (((Integer) entry2.getValue()).intValue() == 0) {
                createSemSystemService(str2);
            } else {
                synchronized (sLock) {
                    ((ArrayMap) sPendingSepUnionServiceCreators).put(str2, new SepUnionServiceCreator(str2));
                }
            }
        }
        File file = new File("/data/log/sepunion/");
        if (file.exists() || !file.mkdir()) {
            return;
        }
        FileUtils.setPermissions(file, 511, -1, -1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12, types: [java.io.InputStreamReader, java.io.Reader] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v10, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v8 */
    public static void dumpFromFile(PrintWriter printWriter, String str, String str2) {
        AutoCloseable autoCloseable;
        AutoCloseable autoCloseable2;
        AutoCloseable autoCloseable3;
        AutoCloseable autoCloseable4;
        ?? file = new File(str2);
        if (!file.exists()) {
            Log.d("SemUnionMainServiceImpl", "This log file does not exist : " + str2);
            return;
        }
        ?? r7 = "\n##### DUMP OF ";
        printWriter.println(XmlUtils$$ExternalSyntheticOutline0.m("\n##### DUMP OF ", str, " #####\n##### (dumpsys sepunion ", str, ") #####\n"));
        BufferedReader bufferedReader = null;
        try {
            try {
                r7 = new FileInputStream((File) file);
                try {
                    file = new InputStreamReader((InputStream) r7, "UTF-8");
                    try {
                        BufferedReader bufferedReader2 = new BufferedReader(file);
                        try {
                            StringBuilder sb = new StringBuilder();
                            int i = 0;
                            while (true) {
                                String readLine = bufferedReader2.readLine();
                                if (readLine == null || i > 500) {
                                    break;
                                }
                                sb.append(readLine);
                                sb.append('\n');
                                i++;
                            }
                            printWriter.print(sb.toString());
                            IoUtils.closeQuietly(bufferedReader2);
                            autoCloseable4 = file;
                            autoCloseable3 = r7;
                        } catch (FileNotFoundException e) {
                            e = e;
                            bufferedReader = bufferedReader2;
                            e.printStackTrace();
                            autoCloseable2 = file;
                            autoCloseable = r7;
                            IoUtils.closeQuietly(bufferedReader);
                            autoCloseable4 = autoCloseable2;
                            autoCloseable3 = autoCloseable;
                            IoUtils.closeQuietly(autoCloseable4);
                            IoUtils.closeQuietly(autoCloseable3);
                        } catch (IOException e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                            e.printStackTrace();
                            autoCloseable2 = file;
                            autoCloseable = r7;
                            IoUtils.closeQuietly(bufferedReader);
                            autoCloseable4 = autoCloseable2;
                            autoCloseable3 = autoCloseable;
                            IoUtils.closeQuietly(autoCloseable4);
                            IoUtils.closeQuietly(autoCloseable3);
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            IoUtils.closeQuietly(bufferedReader);
                            IoUtils.closeQuietly((AutoCloseable) file);
                            IoUtils.closeQuietly((AutoCloseable) r7);
                            throw th;
                        }
                    } catch (FileNotFoundException e3) {
                        e = e3;
                    } catch (IOException e4) {
                        e = e4;
                    }
                } catch (FileNotFoundException e5) {
                    e = e5;
                    file = 0;
                } catch (IOException e6) {
                    e = e6;
                    file = 0;
                } catch (Throwable th2) {
                    th = th2;
                    file = 0;
                }
            } catch (FileNotFoundException e7) {
                e = e7;
                r7 = 0;
                file = 0;
            } catch (IOException e8) {
                e = e8;
                r7 = 0;
                file = 0;
            } catch (Throwable th3) {
                th = th3;
                r7 = 0;
                file = 0;
            }
            IoUtils.closeQuietly(autoCloseable4);
            IoUtils.closeQuietly(autoCloseable3);
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public static void dumpHistoryLog(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, String str) {
        printWriter.println(XmlUtils$$ExternalSyntheticOutline0.m("\n##### DUMP OF ", str, " #####\n##### (dumpsys sepunion ", str, ") #####\n"));
        Log.dump(str, fileDescriptor, printWriter, strArr);
    }

    public static void dumpInternal(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("\n##### SEP UNION Main SERVICE #####\n##### (dumpsys sepunion) #####\n");
        dumpServiceList(printWriter);
        synchronized (sLock) {
            try {
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
                        dumpFromFile(printWriter, str, str2);
                    } else {
                        dumpHistoryLog(fileDescriptor, printWriter, strArr, str);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void dumpServiceList(PrintWriter printWriter) {
        int i;
        String str = "";
        String str2 = "";
        synchronized (sLock) {
            try {
                i = 0;
                for (Map.Entry entry : sSemSystemServiceMap.entrySet()) {
                    if (entry.getValue() != null) {
                        i++;
                        str = str + ((String) entry.getKey()) + " ";
                    }
                }
                for (Map.Entry entry2 : ((ArrayMap) sPendingSepUnionServiceCreators).entrySet()) {
                    if (entry2.getValue() != null) {
                        str2 = str2 + ((String) entry2.getKey()) + " ";
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println("Number of SEP Union service = " + i);
        printWriter.println("SEP Union service list(activated) : " + str);
        BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(printWriter, "SEP Union service list(pending) : ", str2);
    }

    public static AbsSemSystemService getSemSystemService() {
        AbsSemSystemService absSemSystemService;
        synchronized (sLock) {
            try {
                HashMap hashMap = sSemSystemServiceMap;
                if (!hashMap.containsKey("plugin") || (absSemSystemService = (AbsSemSystemService) hashMap.get("plugin")) == null) {
                    return null;
                }
                return absSemSystemService;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        HashMap hashMap;
        boolean z;
        boolean z2;
        if (DumpUtils.checkDumpPermission(this.mContext, "SemUnionMainServiceImpl", printWriter)) {
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
                    try {
                        AbsSemSystemService absSemSystemService = (AbsSemSystemService) hashMap.get(strArr[0]);
                        if (absSemSystemService != null) {
                            if (strArr.length == 1) {
                                absSemSystemService.dump(fileDescriptor, printWriter, null);
                            } else {
                                absSemSystemService.dump(fileDescriptor, printWriter, (String[]) Arrays.copyOfRange(strArr, 1, strArr.length));
                            }
                        }
                    } finally {
                    }
                }
                return;
            }
            if (!z2) {
                if (strArr[0].equals("servicelist")) {
                    dumpServiceList(printWriter);
                    return;
                } else {
                    dumpInternal(fileDescriptor, printWriter, strArr);
                    return;
                }
            }
            synchronized (obj) {
                try {
                    HashMap hashMap2 = sDumpInformationMap;
                    if (hashMap2.get(strArr[0]) != null) {
                        String str = strArr[0];
                        dumpFromFile(printWriter, str, (String) hashMap2.get(str));
                    } else {
                        dumpHistoryLog(fileDescriptor, printWriter, strArr, strArr[0]);
                    }
                } finally {
                }
            }
        }
    }

    public final IBinder getSemSystemService(String str, Bundle bundle) {
        Log.d("SemUnionMainServiceImpl", "getSemSystemService - " + str);
        synchronized (sLock) {
            try {
                IBinder iBinder = (IBinder) sUnionIbinderMap.get(str);
                if (iBinder != null) {
                    return iBinder;
                }
                if (!this.mIsBootCompleted) {
                    Log.d("SemUnionMainServiceImpl", "getSemSystemService : boot is not completed");
                    return null;
                }
                IBinder sepUnionService = getSepUnionService(str);
                Log.d("SemUnionMainServiceImpl", "getSemSystemService - " + str + " : " + sepUnionService);
                return sepUnionService;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final IBinder getSepUnionService(String str) {
        synchronized (sLock) {
            try {
                Map map = sPendingSepUnionServiceCreators;
                SepUnionServiceCreator sepUnionServiceCreator = (SepUnionServiceCreator) ((ArrayMap) map).get(str);
                if (sepUnionServiceCreator == null) {
                    return null;
                }
                ((ArrayMap) map).remove(str);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Context context = this.mContext;
                    SemUnionMainServiceImpl semUnionMainServiceImpl = SemUnionMainServiceImpl.this;
                    String str2 = sepUnionServiceCreator.mName;
                    m869$$Nest$maddSepUnionServiceMapInternal(semUnionMainServiceImpl, str2, context);
                    return (IBinder) sUnionIbinderMap.get(str2);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        AbsSemSystemService absSemSystemService;
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 2000) {
            resultReceiver.send(-1, null);
            throw new SecurityException("Shell commands are only callable by ADB");
        }
        synchronized (sLock) {
            try {
                if (strArr.length <= 0 || (absSemSystemService = (AbsSemSystemService) sSemSystemServiceMap.get(strArr[0])) == null) {
                    super.onShellCommand(fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
                } else {
                    absSemSystemService.onShellCommand(fileDescriptor, fileDescriptor2, fileDescriptor3, strArr.length != 1 ? (String[]) Arrays.copyOfRange(strArr, 1, strArr.length) : null, shellCallback, resultReceiver);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setDumpEnabled(String str, String str2) {
        int callingUid = Binder.getCallingUid();
        if (this.mContext.getPackageManager().checkSignatures(1000, callingUid) != 0) {
            String str3 = "Permission denied: setDumpEnabled callingUid=" + callingUid + ", you need system uid of to be signed with the system certificate.";
            Log.d("SemUnionMainServiceImpl", str3);
            throw new SecurityException(str3);
        }
        synchronized (sLock) {
            try {
                HashMap hashMap = sDumpInformationMap;
                String str4 = (String) hashMap.get(str);
                if (str4 != null) {
                    if (!str4.equals(str2)) {
                    }
                }
                hashMap.put(str, str2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
