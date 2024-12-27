package com.android.server;

import android.app.ActivityManager;
import android.content.Context;
import android.content.IRCPInterface;
import android.content.pm.UserInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.ContainerStateReceiver;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.util.Slog;
import android.view.KeyEvent;

import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.inputmethod.InputConnectionCommandHeader;
import com.android.server.bridge.BridgeProxy;
import com.android.server.bridge.operations.RCPDumpState;
import com.android.server.input.InputManagerService;
import com.android.server.inputmethod.InputMethodManagerService;
import com.android.server.knox.dar.EnterprisePartitionManager;
import com.android.server.pm.Installer;

import com.samsung.android.knox.ISemRemoteContentManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class RCPManagerService extends ISemRemoteContentManager.Stub {
    public static Context sContext;
    public KnoxCaptureInputFilter knoxCaptureInputFilter;
    public final Context mContext;
    public final HashMap mRCPInterfaceMap = new HashMap();
    public final HashMap mBridgeProxyAliveList = new HashMap();
    public final boolean KNOX_DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public UserManager mUm = null;
    public SemPersonaManager mPm = null;
    public boolean mIsInitialized = false;
    public InputManagerService mInputManagerService = null;
    public Handler mBridgeHandler = null;
    public final AnonymousClass1 mContainerstateReceiver =
            new ContainerStateReceiver() { // from class: com.android.server.RCPManagerService.1
                public final void onContainerCreated(Context context, int i, Bundle bundle) {
                    try {
                        RCPManagerService.this.scanAndStartBridgeProxy(i);
                    } catch (Exception e) {
                        Context context2 = RCPManagerService.sContext;
                        Log.d("RCPManagerService", "Exception", e);
                    }
                }

                public final void onContainerRemoved(Context context, int i, Bundle bundle) {
                    RCPManagerService.checkCallerPermissionFor("onRemovePersona");
                    Log.d("RCPManagerService", " onRemovePersona called for  " + i);
                    RCPManagerService.this.deleteAllPersonaData(i);
                }

                public final void onContainerReset(Context context, int i, Bundle bundle) {
                    RCPManagerService.checkCallerPermissionFor("onResetPersona");
                    Log.d("RCPManagerService", " onResetPersona called for  " + i);
                    RCPManagerService.this.deleteAllPersonaData(i);
                }

                public final void onContainerRunning(Context context, int i, Bundle bundle) {
                    RCPManagerService.checkCallerPermissionFor("onPersonaActive");
                    Log.d("RCPManagerService", " onPersonaActive called for  " + i);
                }
            };
    public final AnonymousClass2 mBridgeRunnable =
            new Runnable() { // from class: com.android.server.RCPManagerService.2
                @Override // java.lang.Runnable
                public final void run() {
                    ArrayList<UserInfo> arrayList;
                    try {
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        long j = elapsedRealtime / 1000;
                        Context context = RCPManagerService.sContext;
                        Log.d(
                                "RCPManagerService",
                                " RCPManagerService elapsedRealtime in milliseconds: "
                                        + elapsedRealtime
                                        + " , inSeconds : "
                                        + j
                                        + " , inMinutes : "
                                        + (j / 60));
                        RCPManagerService.this.scanAndStartBridgeProxy(0);
                        List<UserInfo> users = RCPManagerService.this.mUm.getUsers(true);
                        if (users == null || users.size() <= 0) {
                            arrayList = null;
                        } else {
                            arrayList = new ArrayList();
                            for (UserInfo userInfo : users) {
                                if (userInfo.isManagedProfile()) {
                                    arrayList.add(userInfo);
                                }
                            }
                        }
                        if (arrayList == null || arrayList.size() <= 0) {
                            Context context2 = RCPManagerService.sContext;
                            Log.d(
                                    "RCPManagerService",
                                    "RCPManagerService :  PersonaInfoList is null or empty ");
                            return;
                        }
                        Context context3 = RCPManagerService.sContext;
                        Log.d(
                                "RCPManagerService",
                                "RCPManagerService : No of Personas = " + arrayList.size());
                        for (UserInfo userInfo2 : arrayList) {
                            Context context4 = RCPManagerService.sContext;
                            Log.d(
                                    "RCPManagerService",
                                    "RCPManagerService : scanAndStartBridgeProxy called for"
                                        + " PersonaId : "
                                            + userInfo2.id);
                            RCPManagerService.this.scanAndStartBridgeProxy(userInfo2.id);
                        }
                    } catch (Exception e) {
                        Context context5 = RCPManagerService.sContext;
                        RCPManagerService$$ExternalSyntheticOutline0.m(
                                e,
                                new StringBuilder(
                                        " RCPManagerService : Exception while"
                                            + " scanAndStartBridgeProxy() for users "),
                                "RCPManagerService");
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.RCPManagerService$1] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.server.RCPManagerService$2] */
    public RCPManagerService(Context context) {
        Log.d("RCPManagerService", "start ");
        if (context == null) {
            Log.d("RCPManagerService", "Context is null(). Failed to start service");
            return;
        }
        this.mContext = context;
        sContext = context;
        initService();
    }

    public static void checkCallerPermissionFor(String str) {
        if (ServiceKeeper.isAuthorized(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        sContext,
                        "RCPManagerService",
                        str)
                == 0) {
            return;
        }
        SecurityException securityException =
                new SecurityException(
                        "Security Exception Occurred while pid["
                                + Binder.getCallingPid()
                                + "] with uid["
                                + Binder.getCallingUid()
                                + "] trying to access methodName ["
                                + str
                                + "] in [RCPManagerService] service");
        securityException.printStackTrace();
        throw securityException;
    }

    public static boolean isPackageDataRelatedPath(String str) {
        if (str == null) {
            return false;
        }
        if (str.startsWith("/data/data")
                || str.startsWith("/data/user")
                || str.startsWith("/data/user_de")) {
            Log.d("RCPManagerService", "package path detected: ".concat(str));
            return true;
        }
        Log.d("RCPManagerService", "not a package path: ".concat(str));
        return false;
    }

    public static void processImeRequest(Bundle bundle) {
        try {
            if (bundle.containsKey("commitText")) {
                CharSequence charSequence = bundle.getCharSequence("commitText");
                IRemoteInputConnection iRemoteInputConnection =
                        InputMethodManagerService.mCurInputConnectionForKnox;
                if (iRemoteInputConnection != null) {
                    try {
                        iRemoteInputConnection.commitText(
                                new InputConnectionCommandHeader(9999), charSequence, 1);
                    } catch (RemoteException e) {
                        Slog.w(
                                "InputMethodManagerService",
                                "commitText failed due to remote exception",
                                e);
                    }
                }
            }
            if (bundle.containsKey("keyEvent")) {
                KeyEvent keyEvent = (KeyEvent) bundle.getParcelable("keyEvent");
                IRemoteInputConnection iRemoteInputConnection2 =
                        InputMethodManagerService.mCurInputConnectionForKnox;
                if (iRemoteInputConnection2 == null) {
                    return;
                }
                try {
                    iRemoteInputConnection2.sendKeyEvent(
                            new InputConnectionCommandHeader(9999), keyEvent);
                } catch (RemoteException e2) {
                    Slog.w(
                            "InputMethodManagerService",
                            "sendKeyEvent failed due to remote exception",
                            e2);
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public final void cancelCopyChunks(long j) {
        boolean copyKnoxCancel;
        if (!initService()) {
            Log.e("RCPManagerService", "failed to cancelCopyChunks");
            return;
        }
        checkCallerPermissionFor("cancelCopyChunks");
        Log.d("RCPManagerService", "P_OS_RCP cancelCopyChunks");
        Log.d("RCPManagerService", "cancelCopyChunks() sessionId=" + j);
        EnterprisePartitionManager enterprisePartitionManager =
                EnterprisePartitionManager.getInstance(this.mContext);
        enterprisePartitionManager.checkCallerPermissionFor("cancelCopyChunks");
        String str = (String) enterprisePartitionManager.mSessionIdDstPath.get(Long.valueOf(j));
        if (EnterprisePartitionManager.mPackageTasker == null || str == null || str.isEmpty()) {
            return;
        }
        synchronized (EnterprisePartitionManager.mInstallLock) {
            try {
                try {
                    Installer installer = EnterprisePartitionManager.mPackageTasker;
                    if (installer.checkBeforeRemote()) {
                        try {
                            copyKnoxCancel = installer.mInstalld.copyKnoxCancel(str, j);
                        } catch (Exception e) {
                            Installer.InstallerException.from(e);
                            throw null;
                        }
                    } else {
                        copyKnoxCancel = false;
                    }
                    if (copyKnoxCancel) {
                        enterprisePartitionManager.mSessionIdDstPath.remove(Long.valueOf(j));
                    }
                } catch (Installer.InstallerException unused) {
                }
            } finally {
            }
        }
    }

    public final int copyChunks(
            int i, String str, int i2, String str2, long j, int i3, long j2, boolean z) {
        String str3;
        EnterprisePartitionManager enterprisePartitionManager;
        int copyKnoxChunks;
        String str4 = str;
        int i4 = -1;
        if (!initService()) {
            Log.e("RCPManagerService", "failed to copyChunks");
            return -1;
        }
        checkCallerPermissionFor("copyChunks");
        Log.d("RCPManagerService", "P_OS_RCP copyChunks");
        StringBuilder sb = new StringBuilder("copyChunks() srcContainerId=");
        sb.append(i);
        sb.append("; srcFilePath=");
        sb.append(str4);
        sb.append("; destContainerId=");
        sb.append(i2);
        RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, "; destFilePath=", str2, "; offset=");
        sb.append(j);
        sb.append("; length=");
        sb.append(i3);
        BootReceiver$$ExternalSyntheticOutline0.m(sb, "; sessionId=", j2, "; deleteSrc=");
        RCPManagerService$$ExternalSyntheticOutline0.m("RCPManagerService", sb, z);
        EnterprisePartitionManager enterprisePartitionManager2 =
                EnterprisePartitionManager.getInstance(this.mContext);
        enterprisePartitionManager2.checkCallerPermissionFor("copyChunks");
        if (EnterprisePartitionManager.mPackageTasker == null) {
            return -19;
        }
        if (str4 == null
                || str.isEmpty()
                || str2 == null
                || str2.isEmpty()
                || !enterprisePartitionManager2.isUserUnlocked(i)
                || !enterprisePartitionManager2.isUserUnlocked(i2)) {
            return -2;
        }
        if (i != 0 && str4.startsWith("/storage/emulated")) {
            str4 = str4.replaceFirst("/storage", "/mnt/user/" + i);
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "srcRealPath : ", str4, "EnterprisePartitionManager");
        }
        String str5 = str4;
        if (i2 == 0 || !str2.startsWith("/storage/emulated")) {
            str3 = str2;
        } else {
            String replaceFirst = str2.replaceFirst("/storage", "/mnt/user/" + i2);
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "dstRealPath : ", replaceFirst, "EnterprisePartitionManager");
            str3 = replaceFirst;
        }
        if (!enterprisePartitionManager2.mSessionIdDstPath.containsKey(Long.valueOf(j2))) {
            enterprisePartitionManager2.mSessionIdDstPath.put(Long.valueOf(j2), str3);
        }
        int i5 = z ? 36 : 32;
        synchronized (EnterprisePartitionManager.mInstallLock) {
            try {
                try {
                    Installer installer = EnterprisePartitionManager.mPackageTasker;
                    long j3 = i3;
                    if (installer.checkBeforeRemote()) {
                        try {
                            enterprisePartitionManager = enterprisePartitionManager2;
                            copyKnoxChunks =
                                    installer.mInstalld.copyKnoxChunks(
                                            str5, i, str3, i2, i5, j, j3, j2);
                        } catch (Exception e) {
                            Installer.InstallerException.from(e);
                            throw null;
                        }
                    } else {
                        enterprisePartitionManager = enterprisePartitionManager2;
                        copyKnoxChunks = -1;
                    }
                } catch (Installer.InstallerException unused) {
                }
                if (copyKnoxChunks != 201) {
                    if (copyKnoxChunks == 200) {
                        enterprisePartitionManager.mSessionIdDstPath.remove(Long.valueOf(j2));
                    } else {
                        i4 = copyKnoxChunks;
                    }
                }
                i4 = 0;
            } finally {
            }
        }
        return i4;
    }

    public final int copyFile(int i, String str, int i2, String str2) {
        if (!initService()) {
            Log.e("RCPManagerService", "failed to copyFile");
            return -1;
        }
        checkCallerPermissionFor("copyFile");
        IRCPInterface rCPInterface = getRCPInterface();
        if (rCPInterface != null) {
            Log.d("RCPManagerService", "copyFile  getRCPInterface not NULL ");
            return rCPInterface.copyFile(i, str, i2, str2);
        }
        Log.d("RCPManagerService", "copyFile  getRCPInterface NULL ");
        return -1;
    }

    public final int copyFileInternal(int i, String str, int i2, String str2) {
        String str3;
        if (!initService()) {
            Log.e("RCPManagerService", "failed to copyFileInternal");
            return -1;
        }
        checkCallerPermissionFor("copyFile");
        Log.d(
                "RCPManagerService",
                "copyFile() srcContainerId="
                        + i
                        + "; srcFilePath="
                        + str
                        + "; destContainerId="
                        + i2
                        + "; destFilePath="
                        + str2);
        int callingPid = Binder.getCallingPid();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
                ((ActivityManager) this.mContext.getSystemService("activity"))
                        .getRunningAppProcesses();
        if (runningAppProcesses != null && !runningAppProcesses.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo :
                    runningAppProcesses) {
                if (runningAppProcessInfo.pid == callingPid) {
                    str3 = runningAppProcessInfo.processName;
                    break;
                }
            }
        }
        str3 = "";
        if ("com.samsung.android.bbc.bbcagent".equals(str3)) {
            if (initService()) {
                Log.d("RCPManagerService", "copyPackageData");
                return EnterprisePartitionManager.getInstance(this.mContext)
                        .copy(i, i2, 3, str, str2);
            }
            Log.e("RCPManagerService", "failed to copyPackageData");
            return -1;
        }
        if (!isPackageDataRelatedPath(str) && !isPackageDataRelatedPath(str2)) {
            EnterprisePartitionManager enterprisePartitionManager =
                    EnterprisePartitionManager.getInstance(this.mContext);
            enterprisePartitionManager.checkCallerPermissionFor("copy");
            return enterprisePartitionManager.copy(i, i2, 1, str, str2);
        }
        Log.d("TAG", "Package data related copy; calling proper delegation method");
        if (initService()) {
            Log.d("RCPManagerService", "copyPackageData");
            return EnterprisePartitionManager.getInstance(this.mContext).copy(i, i2, 3, str, str2);
        }
        Log.e("RCPManagerService", "failed to copyPackageData");
        return -1;
    }

    public final void deleteAllPersonaData(int i) {
        if (this.KNOX_DEBUG) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i, "----- unregisterBridgeProxy : for user - ", " -----", "RCPManagerService");
        }
        if (((BridgeProxy) this.mBridgeProxyAliveList.get(Integer.valueOf(i))) != null) {
            Log.d("BridgeProxy", "----- stop called -----");
        }
        this.mRCPInterfaceMap.remove(Integer.valueOf(i));
        this.mBridgeProxyAliveList.remove(Integer.valueOf(i));
    }

    public final boolean deleteFile(String str, int i) {
        boolean z = false;
        if (!initService()) {
            Log.e("RCPManagerService", "failed to deleteFile");
            return false;
        }
        checkCallerPermissionFor("deleteFile");
        Log.d("RCPManagerService", "deleteFile() containerId=" + i + "; path=" + str);
        EnterprisePartitionManager.getInstance(this.mContext)
                .checkCallerPermissionFor("deleteFile");
        if (EnterprisePartitionManager.mPackageTasker != null && str != null && !str.isEmpty()) {
            synchronized (EnterprisePartitionManager.mInstallLock) {
                try {
                    Installer installer = EnterprisePartitionManager.mPackageTasker;
                    if (installer.checkBeforeRemote()) {
                        try {
                            z = installer.mInstalld.deleteKnoxFile(str);
                        } catch (Exception e) {
                            Installer.InstallerException.from(e);
                            throw null;
                        }
                    }
                } catch (Installer.InstallerException unused) {
                }
            }
        }
        return z;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Log.d("RCPManagerService", "RCP DumpState Started");
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println(
                    "Permission Denial: can't dump RCPManagerService from from pid="
                            + Binder.getCallingPid()
                            + ", uid="
                            + Binder.getCallingUid()
                            + " without permission android.permission.DUMP");
            return;
        }
        List users = this.mUm.getUsers(false);
        if (users == null || users.isEmpty()) {
            printWriter.println("No of Personas : Zero");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            printWriter.println("No of Personas : " + users.size());
            Context context = this.mContext;
            if (RCPDumpState.mRCPDumpState == null) {
                RCPDumpState rCPDumpState = new RCPDumpState();
                rCPDumpState.mContext = context;
                context.getContentResolver();
                RCPDumpState.mRCPDumpState = rCPDumpState;
            }
            RCPDumpState rCPDumpState2 = RCPDumpState.mRCPDumpState;
            Iterator it = users.iterator();
            while (it.hasNext()) {
                int i = ((UserInfo) it.next()).id;
                boolean isKnoxId = SemPersonaManager.isKnoxId(i);
                boolean isUserRunning = this.mUm.isUserRunning(new UserHandle(i));
                printWriter.println(
                        "PersonaId : "
                                + i
                                + " , isKnoxId : "
                                + isKnoxId
                                + " , isUserRunning : "
                                + isUserRunning);
                if (!SemPersonaManager.isSecureFolderId(i)
                        && (i == 0 || (isKnoxId && isUserRunning))) {
                    printWriter.println(
                            "++++++++++++++++FileOpsTable of " + i + "++++++++++++++++");
                    rCPDumpState2.dumpStateFileOpsTable(i, printWriter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final synchronized Bundle exchangeData(String str, int i, Bundle bundle) {
        if (!initService()) {
            Log.e("RCPManagerService", "failed to exchangeData");
            return null;
        }
        checkCallerPermissionFor("exchangeData");
        if (str != null && i >= 0) {
            if (bundle != null && bundle.getString("action") != null) {
                String string = bundle.getString("action");
                if (Binder.getCallingUid() == 1000) {
                    if ("ImeReq".equals(string)) {
                        processImeRequest(bundle);
                        return null;
                    }
                    if ("updateKnoxCaptureFilter".equals(string)) {
                        this.mInputManagerService =
                                (InputManagerService) ServiceManager.getService("input");
                        if (bundle.getBoolean("add", false)) {
                            this.mInputManagerService.setInputFilter(this.knoxCaptureInputFilter);
                        } else {
                            this.mInputManagerService.setInputFilter(null);
                        }
                        return null;
                    }
                    if ("updateInputDeviceId".equals(string)) {
                        if (bundle.getBoolean("add", false)) {
                            KnoxCaptureInputFilter knoxCaptureInputFilter =
                                    this.knoxCaptureInputFilter;
                            int i2 = bundle.getInt("deviceId", 0);
                            if (KnoxCaptureInputFilter.DEBUG) {
                                knoxCaptureInputFilter.getClass();
                                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                        i2,
                                        "markInputDeviceAsScanner, inputDevice: ",
                                        "KnoxCaptureInputFilter");
                            }
                            if (!((HashSet) knoxCaptureInputFilter.scannerDevices)
                                    .contains(Integer.valueOf(i2))) {
                                ((HashSet) knoxCaptureInputFilter.scannerDevices)
                                        .add(Integer.valueOf(i2));
                            }
                        } else {
                            KnoxCaptureInputFilter knoxCaptureInputFilter2 =
                                    this.knoxCaptureInputFilter;
                            int i3 = bundle.getInt("deviceId", 0);
                            if (KnoxCaptureInputFilter.DEBUG) {
                                knoxCaptureInputFilter2.getClass();
                                Slog.d(
                                        "KnoxCaptureInputFilter",
                                        "unmarkInputDeviceAsScanner, inputDevice: " + i3);
                            }
                            ((HashSet) knoxCaptureInputFilter2.scannerDevices)
                                    .remove(Integer.valueOf(i3));
                        }
                        return null;
                    }
                }
            }
            Log.d(
                    "RCPManagerService",
                    "ERROR | exchange Data | from " + str + ", to user id : " + i);
            return null;
        }
        Log.d("RCPManagerService", "exchangeData() return false for input param is not valid" + i);
        return null;
    }

    public final Bundle getFileInfo(String str, int i) {
        long[] jArr;
        int i2;
        long j;
        long j2;
        long[] knoxFileInfo;
        boolean z = true;
        if (!initService()) {
            Log.e("RCPManagerService", "failed to getFileInfo");
            return new Bundle();
        }
        checkCallerPermissionFor("getFileInfo");
        Log.d("RCPManagerService", "P_OS_RCP getFileInfo");
        StringBuilder sb = new StringBuilder("getFileInfo() containerId=");
        sb.append(i);
        RCPManagerService$$ExternalSyntheticOutline0.m(sb, "; path=", str, "RCPManagerService");
        EnterprisePartitionManager.getInstance(this.mContext)
                .checkCallerPermissionFor("getFileInfo");
        Bundle bundle = new Bundle();
        if (EnterprisePartitionManager.mPackageTasker == null || str == null || str.isEmpty()) {
            bundle.putInt(KnoxCustomManagerService.SPCM_KEY_RESULT, -2);
        } else {
            if (i != 0 && str.startsWith("/storage/emulated")) {
                str = str.replaceFirst("/storage", "/mnt/user/" + i);
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "getFileInfo - realath : ", str, "EnterprisePartitionManager");
            }
            synchronized (EnterprisePartitionManager.mInstallLock) {
                jArr = null;
                try {
                    try {
                        Installer installer = EnterprisePartitionManager.mPackageTasker;
                        if (installer.checkBeforeRemote()) {
                            try {
                                knoxFileInfo = installer.mInstalld.getKnoxFileInfo(str);
                            } catch (Exception e) {
                                Installer.InstallerException.from(e);
                                throw null;
                            }
                        } else {
                            knoxFileInfo = new long[] {-1};
                        }
                        jArr = knoxFileInfo;
                        i2 = (int) jArr[0];
                    } catch (Installer.InstallerException unused) {
                        i2 = -1;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (jArr == null || i2 != 0) {
                j = 0;
                z = false;
                j2 = 0;
            } else {
                j = jArr[1] * 1000;
                j2 = jArr[2];
                if (jArr[3] != 1) {
                    z = false;
                }
            }
            bundle.putInt(KnoxCustomManagerService.SPCM_KEY_RESULT, i2);
            bundle.putLong("last_modified_date", j);
            bundle.putLong("file_size", j2);
            bundle.putBoolean("is_dir", z);
        }
        return bundle;
    }

    public final List getFiles(String str, int i) {
        boolean z;
        if (!initService()) {
            Log.e("RCPManagerService", "failed to getFiles");
            return new ArrayList();
        }
        checkCallerPermissionFor("getFiles");
        Log.d("RCPManagerService", "P_OS_RCP getFiles");
        StringBuilder sb = new StringBuilder("getFiles() containerId=");
        sb.append(i);
        RCPManagerService$$ExternalSyntheticOutline0.m(sb, "; path=", str, "RCPManagerService");
        EnterprisePartitionManager.getInstance(this.mContext).checkCallerPermissionFor("getFiles");
        if (EnterprisePartitionManager.mPackageTasker == null || str == null || str.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (EnterprisePartitionManager.mInstallLock) {
            z = false;
            try {
                Installer installer = EnterprisePartitionManager.mPackageTasker;
                if (installer.checkBeforeRemote()) {
                    try {
                        z = installer.mInstalld.getKnoxScanDir(str, currentTimeMillis, arrayList);
                    } catch (Exception e) {
                        Installer.InstallerException.from(e);
                        throw null;
                    }
                }
            } catch (Installer.InstallerException unused) {
            }
        }
        if (z) {
            return arrayList;
        }
        return null;
    }

    public final IRCPInterface getRCPInterface() {
        checkCallerPermissionFor("getRCPInterface");
        Log.d("RCPManagerService", "getRCPInterface My Context is " + this);
        int callingUserId = UserHandle.getCallingUserId();
        Integer valueOf = Integer.valueOf(callingUserId);
        Log.d("RCPManagerService", "getRCPInterface getting User Id : " + valueOf.toString());
        if (this.mRCPInterfaceMap.get(valueOf) != null) {
            return (IRCPInterface) this.mRCPInterfaceMap.get(valueOf);
        }
        Log.d(
                "RCPManagerService",
                "getRCPInterfaceMap.get(userId) is null. Calling scanAndStartBridgeProxy");
        try {
            scanAndStartBridgeProxy(callingUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (IRCPInterface) this.mRCPInterfaceMap.get(valueOf);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean initService() {
        /*
            Method dump skipped, instructions count: 275
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.RCPManagerService.initService():boolean");
    }

    public final boolean isFileExist(String str, int i) {
        long[] knoxFileInfo;
        boolean z = true;
        boolean z2 = false;
        if (!initService()) {
            Log.e("RCPManagerService", "failed to isFileExist");
            return false;
        }
        checkCallerPermissionFor("isFileExist");
        Log.d("RCPManagerService", "P_OS_RCP isFileExist");
        StringBuilder sb = new StringBuilder("isFileExist() containerId=");
        sb.append(i);
        RCPManagerService$$ExternalSyntheticOutline0.m(sb, "; path=", str, "RCPManagerService");
        EnterprisePartitionManager.getInstance(this.mContext)
                .checkCallerPermissionFor("isFileExist");
        if (EnterprisePartitionManager.mPackageTasker != null && str != null && !str.isEmpty()) {
            if (i != 0 && str.startsWith("/storage/emulated")) {
                str = str.replaceFirst("/storage", "/mnt/user/" + i);
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "getFileInfo - realath : ", str, "EnterprisePartitionManager");
            }
            synchronized (EnterprisePartitionManager.mInstallLock) {
                try {
                    try {
                        Installer installer = EnterprisePartitionManager.mPackageTasker;
                        if (installer.checkBeforeRemote()) {
                            try {
                                knoxFileInfo = installer.mInstalld.getKnoxFileInfo(str);
                            } catch (Exception e) {
                                Installer.InstallerException.from(e);
                                throw null;
                            }
                        } else {
                            knoxFileInfo = new long[] {-1};
                        }
                        if (knoxFileInfo[0] != 0) {
                            z = false;
                        }
                        z2 = z;
                    } catch (Installer.InstallerException unused) {
                    }
                } finally {
                }
            }
        }
        return z2;
    }

    public final int moveFile(int i, String str, int i2, String str2) {
        boolean copyKnoxAppData;
        int i3 = -1;
        if (!initService()) {
            Log.e("RCPManagerService", "failed to moveFile");
            return -1;
        }
        checkCallerPermissionFor("moveFile");
        Log.d("RCPManagerService", "P_OS_RCP moveFile");
        StringBuilder sb = new StringBuilder("moveFile() srcContainerId=");
        sb.append(i);
        sb.append("; srcFilePath=");
        sb.append(str);
        sb.append("; destContainerId=");
        sb.append(i2);
        RCPManagerService$$ExternalSyntheticOutline0.m(
                sb, "; destFilePath=", str2, "RCPManagerService");
        EnterprisePartitionManager enterprisePartitionManager =
                EnterprisePartitionManager.getInstance(this.mContext);
        enterprisePartitionManager.checkCallerPermissionFor("move");
        enterprisePartitionManager.checkCallerPermissionFor("move");
        if (EnterprisePartitionManager.mPackageTasker == null) {
            return -19;
        }
        if (str == null
                || str.isEmpty()
                || str2 == null
                || str2.isEmpty()
                || !enterprisePartitionManager.isUserUnlocked(i)
                || !enterprisePartitionManager.isUserUnlocked(i2)) {
            return -2;
        }
        synchronized (EnterprisePartitionManager.mInstallLock) {
            try {
                Installer installer = EnterprisePartitionManager.mPackageTasker;
                if (installer.checkBeforeRemote()) {
                    try {
                        copyKnoxAppData = installer.mInstalld.copyKnoxAppData(str, i, str2, i2, 36);
                    } catch (Exception e) {
                        Installer.InstallerException.from(e);
                        throw null;
                    }
                } else {
                    copyKnoxAppData = false;
                }
                if (copyKnoxAppData) {
                    i3 = 0;
                }
            } catch (Installer.InstallerException unused) {
            }
        }
        return i3;
    }

    public final long moveFilesForAppEx(int i, List list, List list2, int i2) {
        if (!initService()) {
            Log.e("RCPManagerService", "failed to moveFilesForAppEx");
            return -1L;
        }
        checkCallerPermissionFor("moveFilesForApp");
        IRCPInterface rCPInterface = getRCPInterface();
        if (rCPInterface != null) {
            Log.d("RCPManagerService", "moveFilesForAppEx  getRCPInterface not NULL ");
            return rCPInterface.moveFilesForAppEx(i, list, list2, i2);
        }
        Log.d("RCPManagerService", "moveFilesForAppEx  getRCPInterface NULL ");
        return -1L;
    }

    public final long moveUnlimitedFiles(int i, Uri uri, int i2, int i3) {
        if (!initService()) {
            Log.e("RCPManagerService", "failed to moveUnlimitedFiles");
            return -1L;
        }
        IRCPInterface rCPInterface = getRCPInterface();
        if (rCPInterface != null) {
            Log.d(
                    "RCPManagerService",
                    "moveFilesForAppEx moveFiles(>500) getRCPInterface not NULL ");
            return rCPInterface.moveUnlimitedFilesForApp(i, uri, i2, i3);
        }
        Log.d("RCPManagerService", "moveFiles(>500) getRCPInterface NULL ");
        return -1L;
    }

    public final void registerRCPInterface(IRCPInterface iRCPInterface, int i) {
        if (!initService()) {
            Log.e("RCPManagerService", "failed to registerRCPInterface");
            return;
        }
        checkCallerPermissionFor("registerRCPInterface");
        if (this.KNOX_DEBUG) {
            Log.d("RCPManagerService", "registerRCPInterface My Context is " + this);
            Log.d("RCPManagerService", "registerRCPInterface  User calling is " + i);
        }
        this.mRCPInterfaceMap.put(Integer.valueOf(i), iRCPInterface);
    }

    public final void scanAndStartBridgeProxy(int i) {
        if (this.KNOX_DEBUG) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    i, " scanAndStartBridgeProxy called for ", "RCPManagerService");
        }
        SemPersonaManager semPersonaManager = this.mPm;
        if (semPersonaManager == null || !semPersonaManager.exists(i)) {
            UserInfo profileParent = this.mUm.getProfileParent(i);
            if (profileParent == null || profileParent.id == i) {
                List users = this.mUm.getUsers(true);
                if (users == null || users.size() == 0) {
                    if (this.KNOX_DEBUG) {
                        NetworkScoreService$$ExternalSyntheticOutline0.m(
                                i,
                                "scanAndStartBridgeProxy: NOT starting Bridge Proxy for user = ",
                                "; because it doesn't have personas or it is a guest!",
                                "RCPManagerService");
                        return;
                    }
                    return;
                } else if (this.KNOX_DEBUG) {
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                            i,
                            "scanAndStartBridgeProxy : starting BridgeProxy for owner - ",
                            "RCPManagerService");
                }
            } else if (this.KNOX_DEBUG) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        i,
                        "scanAndStartBridgeProxy : starting BridgeProxy for persona - ",
                        "RCPManagerService");
            }
        } else if (this.KNOX_DEBUG) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    i,
                    "scanAndStartBridgeProxy : starting BridgeProxy for persona - ",
                    "RCPManagerService");
        }
        try {
            synchronized (this.mBridgeProxyAliveList) {
                try {
                    if (this.mBridgeProxyAliveList.containsKey(Integer.valueOf(i))) {
                        if (this.KNOX_DEBUG) {
                            Log.d(
                                    "RCPManagerService",
                                    " Returning...BridgeProxy already active for user - " + i);
                        }
                        return;
                    }
                    BridgeProxy bridgeProxy = new BridgeProxy(i);
                    bridgeProxy.start(this.mContext);
                    this.mBridgeProxyAliveList.put(Integer.valueOf(i), bridgeProxy);
                    if (this.KNOX_DEBUG) {
                        Log.d(
                                "RCPManagerService",
                                "bindToBridgeProxy : started BridgeProxy for user - " + i);
                    }
                } finally {
                }
            }
        } catch (Exception e) {
            Log.d("RCPManagerService", "No need to start BridgeProxy for user " + i);
            e.printStackTrace();
        }
    }
}
