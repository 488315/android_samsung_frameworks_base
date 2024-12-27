package com.samsung.android.camera;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.hardware.ICameraService;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.Display;
import android.view.IDisplayWindowListener;
import android.view.OrientationEventListener;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;

import com.samsung.android.camera.requestinjector.RequestInjectorService;
import com.samsung.android.camera.requestinjector.VtCameraProviderObserver;
import com.samsung.android.camera.scpm.ScpmList;
import com.samsung.android.camera.scpm.ScpmReceiver;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CameraServiceWorker extends SystemService
        implements Handler.Callback, IBinder.DeathRecipient {
    public static final boolean DEBUG;
    public static final String[] DEVICE_INJECTOR_TEST_PACKAGES;
    public static final String[] DEVICE_INJECTOR_TEST_PACKAGES_FOR_BLOCK;
    public static final String[] SAMSUNG_CAMERA_PACKAGES;
    public final ArrayMap mActiveCameraUsage;
    public boolean mBootCompleted;
    public ICameraService mCameraServiceRaw;
    public final AnonymousClass3 mCameraServiceWorker;
    public final Context mContext;
    public long mDeviceState;
    public final int mDisplayId;
    public final DisplayWindowListener mDisplayWindowListener;
    public final boolean mEnableSurveyMode;
    public final CountDownLatch mFoldStateLatch;
    public final Handler mHandler;
    public boolean mIsCameraOpened;
    public final Object mLock;
    public final ArrayMap mOpenCameraUsage;
    public WorkerOrientationListener mOrientationEventListener;
    public final Object mOrientationLock;
    public final RequestInjectorService mRequestInjectorService;
    public final ScpmReceiver mScpmReceiver;
    public final Object mServiceLock;
    public final ShakeEventListener mShakeEventListener;
    public final VtCameraProviderObserver mVtCameraProviderObserver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.camera.CameraServiceWorker$3, reason: invalid class name */
    public final class AnonymousClass3 extends ICameraServiceWorker.Stub {
        public AnonymousClass3() {}

        public final IBinder acquireRequestInjector() {
            if (UserHandle.getAppId(Binder.getCallingUid()) == 1000) {
                return CameraServiceWorker.this.mRequestInjectorService;
            }
            Slog.e(
                    "CameraService_worker",
                    "Only system user is allowed to call acquireRequestInjector");
            throw new SecurityException(
                    "Only system user is allowed to call acquireRequestInjector");
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x0065 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void dump(
                java.io.FileDescriptor r6, java.io.PrintWriter r7, java.lang.String[] r8) {
            /*
                Method dump skipped, instructions count: 301
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.camera.CameraServiceWorker.AnonymousClass3.dump(java.io.FileDescriptor,"
                        + " java.io.PrintWriter, java.lang.String[]):void");
        }

        public final boolean getDeviceInjectorOverride(String str, int i) {
            for (String str2 : CameraServiceWorker.DEVICE_INJECTOR_TEST_PACKAGES) {
                if (str2.equals(str)) {
                    return true;
                }
            }
            for (String str3 : CameraServiceWorker.DEVICE_INJECTOR_TEST_PACKAGES_FOR_BLOCK) {
                if (str3.equals(str)) {
                    return false;
                }
            }
            if (Log.isLoggable("CameraService_worker", 3)) {
                return true;
            }
            TaskInfo taskInfo = CameraServiceWorker.getTaskInfo(i, str);
            return (taskInfo == null || taskInfo.displayId == 0) ? false : true;
        }

        public final int getDeviceOrientationForDeviceInjector(String str, int i) {
            if (Binder.getCallingUid() != 1047) {
                Slog.e(
                        "CameraService_worker",
                        "Calling UID: "
                                + Binder.getCallingUid()
                                + " doesn't match expected camera service UID!");
                return 0;
            }
            TaskInfo taskInfo = CameraServiceWorker.getTaskInfo(i, str);
            if (taskInfo == null) {
                return 0;
            }
            DisplayManager displayManager =
                    (DisplayManager)
                            CameraServiceWorker.this.mContext.getSystemService(
                                    DisplayManager.class);
            if (displayManager == null) {
                Slog.e("CameraService_worker", "Failed to query display manager!");
                return 0;
            }
            if (displayManager.getDisplay(taskInfo.displayId) == null) {
                VaultKeeperService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Invalid display id: "),
                        taskInfo.displayId,
                        "CameraService_worker");
                return 0;
            }
            synchronized (CameraServiceWorker.this.mOrientationLock) {
                try {
                    WorkerOrientationListener workerOrientationListener =
                            CameraServiceWorker.this.mOrientationEventListener;
                    if (workerOrientationListener == null) {
                        return 0;
                    }
                    if (taskInfo.displayId != 0) {
                        return 0;
                    }
                    return workerOrientationListener.mLatestOrientation;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void notifyCameraSessionEvent(int i, String str) {
            if (Binder.getCallingUid() != 1047) {
                Slog.e(
                        "CameraService_worker",
                        "Calling UID: "
                                + Binder.getCallingUid()
                                + " doesn't match expected  camera service UID!");
                return;
            }
            if (CameraServiceWorker.DEBUG) {
                Slog.v("CameraService_worker", "event " + i + ", details : " + str);
            }
            synchronized (CameraServiceWorker.this.mLock) {
                try {
                    switch (i) {
                        case 1:
                            CameraServiceWorker.this.insertDMALog(null, "7503", str);
                            break;
                        case 2:
                            CameraServiceWorker.this.insertDMALog(null, "7504", str);
                            break;
                        case 3:
                            CameraServiceWorker.this.insertDMALog(null, "7505", str);
                            break;
                        case 4:
                            CameraServiceWorker.this.insertDMALog(null, "7506", str);
                            break;
                        case 5:
                            CameraServiceWorker.this.insertDMALog(null, "7507", str);
                            break;
                        case 6:
                            CameraServiceWorker.this.insertDMALog(null, "7508", str);
                            break;
                        default:
                            Slog.e(
                                    "CameraService_worker",
                                    "Non acceptable event type event " + i + ", details : " + str);
                            break;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void notifyCameraState(String str, int i, int i2, String str2, int i3) {
            FileOutputStream fileOutputStream;
            if (Binder.getCallingUid() != 1047) {
                Slog.e(
                        "CameraService_worker",
                        "Calling UID: "
                                + Binder.getCallingUid()
                                + " doesn't match expected  camera service UID!");
                return;
            }
            String str3 =
                    i != 0
                            ? i != 1
                                    ? i != 2
                                            ? i != 3
                                                    ? i != 100
                                                            ? i != 101
                                                                    ? "CAMERA_STATE_UNKNOWN"
                                                                    : "CAMERA_STATE_OPENING_FAILED"
                                                            : "CAMERA_STATE_OPENING"
                                                    : "CAMERA_STATE_CLOSED"
                                            : "CAMERA_STATE_IDLE"
                                    : "CAMERA_STATE_ACTIVE"
                            : "CAMERA_STATE_OPEN";
            String str4 =
                    i2 != 0
                            ? i2 != 1 ? i2 != 2 ? "UnknownCamera" : "ExternalCamera" : "FrontCamera"
                            : "BackCamera";
            boolean z = CameraServiceWorker.DEBUG;
            if (z) {
                StringBuilder m =
                        InitialConfiguration$$ExternalSyntheticOutline0.m(
                                "Camera ", str, " facing ", str4, " state now ");
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        m, str3, " for client ", str2, " API Level ");
                GmsAlarmManager$$ExternalSyntheticOutline0.m(m, i3, "CameraService_worker");
            }
            CameraServiceWorker cameraServiceWorker = CameraServiceWorker.this;
            synchronized (cameraServiceWorker.mLock) {
                try {
                    if (i == 0) {
                        if (cameraServiceWorker.mBootCompleted
                                && cameraServiceWorker.mEnableSurveyMode) {
                            String[] strArr = CameraServiceWorker.SAMSUNG_CAMERA_PACKAGES;
                            int length = strArr.length;
                            int i4 = 0;
                            while (true) {
                                if (i4 >= length) {
                                    cameraServiceWorker.insertDMALog(
                                            Long.valueOf(i2), "7501", str2);
                                    break;
                                } else if (strArr[i4].equals(str2)) {
                                    break;
                                } else {
                                    i4++;
                                }
                            }
                        }
                        cameraServiceWorker.mOpenCameraUsage.put(str, new CameraUsageEvent());
                        Logger.log(Logger.ID.CAMERA_EVENT, "Open camera(" + str + ") for " + str2);
                    } else if (i == 1) {
                        cameraServiceWorker.mActiveCameraUsage.put(str, new CameraUsageEvent());
                        if (new File("/sys/class/camera/rear/cam_wifi_info").exists()) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            WifiManager wifiManager =
                                    (WifiManager)
                                            cameraServiceWorker.mContext.getSystemService("wifi");
                            String str5 = "00000";
                            if (wifiManager.isWifiEnabled()) {
                                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                                if (connectionInfo != null) {
                                    str5 =
                                            String.format(
                                                    "%4d%1d",
                                                    Integer.valueOf(connectionInfo.getFrequency()),
                                                    Integer.valueOf(
                                                            connectionInfo.getWifiStandard()));
                                } else if (z) {
                                    Slog.e(
                                            "CameraService_worker",
                                            "wifiInfo is null So, can not save wifi info.");
                                }
                            }
                            try {
                                fileOutputStream =
                                        new FileOutputStream(
                                                "/sys/class/camera/rear/cam_wifi_info");
                            } catch (Exception e) {
                                if (CameraServiceWorker.DEBUG) {
                                    Slog.v("CameraService_worker", "Can't save wifi info : " + e);
                                }
                            }
                            try {
                                fileOutputStream.write(str5.getBytes(Charset.forName("UTF-8")));
                                fileOutputStream.close();
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            } finally {
                            }
                        }
                    } else if (i == 2) {
                        cameraServiceWorker.mActiveCameraUsage.remove(str);
                    } else if (i == 3) {
                        cameraServiceWorker.mOpenCameraUsage.remove(str);
                        Logger.log(Logger.ID.CAMERA_EVENT, "Close camera(" + str + ") for " + str2);
                        cameraServiceWorker.mActiveCameraUsage.remove(str);
                    } else if (i != 100 && i != 101) {
                        Slog.e("CameraService_worker", "Non acceptable state " + i);
                    }
                    cameraServiceWorker.mIsCameraOpened =
                            !cameraServiceWorker.mOpenCameraUsage.isEmpty();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void pingForUpdate() {
            if (Binder.getCallingUid() != 1047 && Binder.getCallingPid() != Process.myPid()) {
                Slog.e(
                        "CameraService_worker",
                        "Calling UID: "
                                + Binder.getCallingUid()
                                + " doesn't match expected  camera service UID!");
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                CameraServiceWorker.this.mVtCameraProviderObserver.updateCameraService();
                ScpmReceiver scpmReceiver = CameraServiceWorker.this.mScpmReceiver;
                scpmReceiver.getClass();
                Slog.d("CameraService/ScpmReceiver", "initialize");
                for (ScpmList.PolicyType policyType : ScpmList.PolicyType.values()) {
                    scpmReceiver.notifyParamChangeRetryLocked(30, policyType);
                }
                try {
                    CameraServiceWorker.this.mRequestInjectorService
                            .sendAllExtraRequestsToRequestInjector();
                } catch (UnsupportedOperationException e) {
                    Slog.e("CameraService_worker", "pingForUpdate exception happen " + e);
                }
                synchronized (CameraServiceWorker.this.mLock) {
                    CameraServiceWorker.this.notifyDeviceChangeRetryLocked(30);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x005c, code lost:

           r6 = r4.pkgList;
        */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x005e, code lost:

           if (r6 == null) goto L26;
        */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0062, code lost:

           if (r6.length != 1) goto L26;
        */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0064, code lost:

           android.util.Slog.i("CameraService_worker", "Package name = " + r4.pkgList[0]);
           r6 = r4.pkgList[0];
        */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0081, code lost:

           android.os.Binder.restoreCallingIdentity(r0);
        */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0084, code lost:

           return r6;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.String queryPackageName(int r7, int r8) {
            /*
                r6 = this;
                int r0 = android.os.Binder.getCallingUid()
                r1 = 1047(0x417, float:1.467E-42)
                java.lang.String r2 = ""
                java.lang.String r3 = "CameraService_worker"
                if (r0 == r1) goto L27
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                java.lang.String r7 = "Calling UID: "
                r6.<init>(r7)
                int r7 = android.os.Binder.getCallingUid()
                r6.append(r7)
                java.lang.String r7 = " doesn't match expected  camera service UID!"
                r6.append(r7)
                java.lang.String r6 = r6.toString()
                android.util.Slog.e(r3, r6)
                return r2
            L27:
                long r0 = android.os.Binder.clearCallingIdentity()
                com.samsung.android.camera.CameraServiceWorker r6 = com.samsung.android.camera.CameraServiceWorker.this     // Catch: java.lang.Throwable -> L42
                android.content.Context r6 = r6.mContext     // Catch: java.lang.Throwable -> L42
                java.lang.String r4 = "activity"
                java.lang.Object r6 = r6.getSystemService(r4)     // Catch: java.lang.Throwable -> L42
                android.app.ActivityManager r6 = (android.app.ActivityManager) r6     // Catch: java.lang.Throwable -> L42
                java.util.List r6 = r6.getRunningAppProcesses()     // Catch: java.lang.Throwable -> L42
                if (r6 != 0) goto L44
                java.util.List r6 = java.util.Collections.emptyList()     // Catch: java.lang.Throwable -> L42
                goto L44
            L42:
                r6 = move-exception
                goto L89
            L44:
                java.util.Iterator r6 = r6.iterator()     // Catch: java.lang.Throwable -> L42
            L48:
                boolean r4 = r6.hasNext()     // Catch: java.lang.Throwable -> L42
                if (r4 == 0) goto L85
                java.lang.Object r4 = r6.next()     // Catch: java.lang.Throwable -> L42
                android.app.ActivityManager$RunningAppProcessInfo r4 = (android.app.ActivityManager.RunningAppProcessInfo) r4     // Catch: java.lang.Throwable -> L42
                int r5 = r4.pid     // Catch: java.lang.Throwable -> L42
                if (r5 != r7) goto L48
                int r5 = r4.uid     // Catch: java.lang.Throwable -> L42
                if (r5 != r8) goto L48
                java.lang.String[] r6 = r4.pkgList     // Catch: java.lang.Throwable -> L42
                if (r6 == 0) goto L85
                int r6 = r6.length     // Catch: java.lang.Throwable -> L42
                r7 = 1
                if (r6 != r7) goto L85
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L42
                r6.<init>()     // Catch: java.lang.Throwable -> L42
                java.lang.String r7 = "Package name = "
                r6.append(r7)     // Catch: java.lang.Throwable -> L42
                java.lang.String[] r7 = r4.pkgList     // Catch: java.lang.Throwable -> L42
                r8 = 0
                r7 = r7[r8]     // Catch: java.lang.Throwable -> L42
                r6.append(r7)     // Catch: java.lang.Throwable -> L42
                java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L42
                android.util.Slog.i(r3, r6)     // Catch: java.lang.Throwable -> L42
                java.lang.String[] r6 = r4.pkgList     // Catch: java.lang.Throwable -> L42
                r6 = r6[r8]     // Catch: java.lang.Throwable -> L42
                android.os.Binder.restoreCallingIdentity(r0)
                return r6
            L85:
                android.os.Binder.restoreCallingIdentity(r0)
                return r2
            L89:
                android.os.Binder.restoreCallingIdentity(r0)
                throw r6
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.camera.CameraServiceWorker.AnonymousClass3.queryPackageName(int,"
                        + " int):java.lang.String");
        }

        public final void setDeviceOrientationListener(boolean z) {
            if (Binder.getCallingUid() != 1047) {
                Slog.e(
                        "CameraService_worker",
                        "Calling UID: "
                                + Binder.getCallingUid()
                                + " doesn't match expected camera service UID!");
                return;
            }
            synchronized (CameraServiceWorker.this.mOrientationLock) {
                try {
                    WorkerOrientationListener workerOrientationListener =
                            CameraServiceWorker.this.mOrientationEventListener;
                    if (workerOrientationListener == null) {
                        return;
                    }
                    if (z) {
                        workerOrientationListener.enable();
                    } else {
                        workerOrientationListener.disable();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void storeLoggingData(int i, String str) {
            if (Logger.ID.values().length <= i || i < 0) {
                Slog.e("CameraService_worker", "storeLoggingData : type has Inacceptable");
            } else {
                Logger.log(Logger.ID.values()[i], str);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BootCompleteReceiver extends BroadcastReceiver {
        public BootCompleteReceiver() {
            CameraServiceWorker.this.mContext.registerReceiver(
                    this,
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                            "android.intent.action.BOOT_COMPLETED",
                            "com.samsung.intent.action.LAZY_BOOT_COMPLETE"),
                    2);
        }

        /* JADX WARN: Can't wrap try/catch for region: R(10:3|(2:4|5)|(6:31|30|21|22|23|24)|12|(3:14|(2:16|(2:19|20)(1:18))|29)|30|21|22|23|24) */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x009f, code lost:

           r10 = move-exception;
        */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x00a0, code lost:

           android.util.Slog.e("CameraService_worker", "BootCompleteReceiver exception happen " + r10);
        */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r10, android.content.Intent r11) {
            /*
                r9 = this;
                java.lang.String r10 = r11.getAction()
                java.lang.String r0 = "android.intent.action.BOOT_COMPLETED"
                boolean r10 = r0.equals(r10)
                java.lang.String r0 = "ACTION***"
                java.lang.String r1 = "CameraService_worker"
                if (r10 == 0) goto Laa
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>(r0)
                java.lang.String r11 = r11.getAction()
                r10.append(r11)
                java.lang.String r10 = r10.toString()
                android.util.Slog.v(r1, r10)
                com.samsung.android.camera.CameraServiceWorker r10 = com.samsung.android.camera.CameraServiceWorker.this
                com.samsung.android.camera.requestinjector.VtCameraProviderObserver r10 = r10.mVtCameraProviderObserver
                com.samsung.android.camera.Logger$ID r11 = com.samsung.android.camera.Logger.ID.REQUEST_INJECTOR_SERVICE
                java.lang.String r0 = "VtCameraProviderObserver"
                android.content.Context r2 = r10.mContext
                android.content.pm.PackageManager r2 = r2.getPackageManager()
                r3 = 1
                java.lang.String r4 = "com.samsung.android.vtcamerasettings"
                r5 = 8
                android.content.pm.PackageInfo r2 = r2.getPackageInfo(r4, r5)     // Catch: java.lang.Exception -> L4b
                android.content.pm.ApplicationInfo r4 = r2.applicationInfo     // Catch: java.lang.Exception -> L4b
                if (r4 == 0) goto L83
                boolean r5 = r4.isSystemApp()     // Catch: java.lang.Exception -> L4b
                if (r5 != 0) goto L4d
                boolean r4 = r4.isUpdatedSystemApp()     // Catch: java.lang.Exception -> L4b
                if (r4 == 0) goto L83
                goto L4d
            L4b:
                r10 = move-exception
                goto L89
            L4d:
                android.content.pm.ProviderInfo[] r2 = r2.providers     // Catch: java.lang.Exception -> L4b
                if (r2 == 0) goto L8f
                int r4 = r2.length     // Catch: java.lang.Exception -> L4b
                r5 = 0
                r6 = r5
            L54:
                if (r6 >= r4) goto L8f
                r7 = r2[r6]     // Catch: java.lang.Exception -> L4b
                java.lang.String r8 = "com.samsung.android.vtcamerasettings.VsetInfoProvider"
                java.lang.String r7 = r7.authority     // Catch: java.lang.Exception -> L4b
                boolean r7 = r8.equals(r7)     // Catch: java.lang.Exception -> L4b
                if (r7 == 0) goto L80
                android.content.Context r2 = r10.mContext
                android.content.ContentResolver r2 = r2.getContentResolver()
                android.net.Uri r4 = com.samsung.android.camera.requestinjector.VtCameraProviderObserver.VT_CAMERA_SETTING_AUTHORITY_URI
                java.lang.String r6 = "AllowedAppInfo"
                android.net.Uri r4 = android.net.Uri.withAppendedPath(r4, r6)
                r6 = -1
                r2.registerContentObserver(r4, r5, r10, r6)
                java.lang.String r2 = "VT Camera provider exist. Observer registered."
                android.util.Slog.i(r0, r2)
                com.samsung.android.camera.Logger.log(r11, r2)
                r10.onChange(r3)
                goto L97
            L80:
                int r6 = r6 + 1
                goto L54
            L83:
                java.lang.String r10 = "Provider exist, but not (updated-)system app."
                android.util.Slog.e(r0, r10)     // Catch: java.lang.Exception -> L4b
                goto L8f
            L89:
                java.lang.String r2 = "providerExistOrNot "
                com.android.server.BootReceiver$$ExternalSyntheticOutline0.m(r10, r2, r0)
            L8f:
                java.lang.String r10 = "VT Camera provider does not exist. Skip observer register."
                android.util.Slog.i(r0, r10)
                com.samsung.android.camera.Logger.log(r11, r10)
            L97:
                com.samsung.android.camera.CameraServiceWorker r10 = com.samsung.android.camera.CameraServiceWorker.this     // Catch: android.os.RemoteException -> L9f
                com.samsung.android.camera.CameraServiceWorker$3 r10 = r10.mCameraServiceWorker     // Catch: android.os.RemoteException -> L9f
                r10.pingForUpdate()     // Catch: android.os.RemoteException -> L9f
                goto La5
            L9f:
                r10 = move-exception
                java.lang.String r11 = "BootCompleteReceiver exception happen "
                com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0.m$1(r11, r10, r1)
            La5:
                com.samsung.android.camera.CameraServiceWorker r9 = com.samsung.android.camera.CameraServiceWorker.this
                r9.mBootCompleted = r3
                goto Ld8
            Laa:
                java.lang.String r10 = "com.samsung.intent.action.LAZY_BOOT_COMPLETE"
                java.lang.String r2 = r11.getAction()
                boolean r10 = r10.equals(r2)
                if (r10 == 0) goto Ld8
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>(r0)
                java.lang.String r11 = r11.getAction()
                r10.append(r11)
                java.lang.String r10 = r10.toString()
                android.util.Slog.v(r1, r10)
                com.samsung.android.camera.CameraServiceWorker r9 = com.samsung.android.camera.CameraServiceWorker.this
                com.samsung.android.camera.scpm.ScpmReceiver r9 = r9.mScpmReceiver
                android.os.Handler r10 = r9.mHandler
                r11 = 2
                r10.removeMessages(r11)
                android.os.Handler r9 = r9.mHandler
                r9.sendEmptyMessage(r11)
            Ld8:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.camera.CameraServiceWorker.BootCompleteReceiver.onReceive(android.content.Context,"
                        + " android.content.Intent):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CPRCommandReceiver extends BroadcastReceiver {
        public final String ACTION_RESET_CAMERAPROVIDER = "com.samsung.cmh.action.cameraprovider";

        public CPRCommandReceiver() {
            CameraServiceWorker.this.mContext.registerReceiver(
                    this,
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "com.samsung.cmh.action.cameraprovider"),
                    null,
                    CameraServiceWorker.this.mHandler,
                    2);
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (this.ACTION_RESET_CAMERAPROVIDER.equals(intent.getAction())) {
                Slog.v("CameraService_worker", "ACTION***" + intent.getAction());
                CameraServiceWorker.this.notifyDeviceChangeLocked(17179869184L, false);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CameraUsageEvent {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceInjectorRequirementChecker extends BroadcastReceiver {
        public boolean mDexMode = false;
        public boolean mExternalCameraPresent = false;
        public boolean mRequirementMet = false;
        public final ArrayMap mExternalDeviceMap = new ArrayMap();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.samsung.android.camera.CameraServiceWorker$DeviceInjectorRequirementChecker$1, reason: invalid class name */
        public final class AnonymousClass1 extends CameraManager.AvailabilityCallback {
            public AnonymousClass1() {}

            public final void onSemCameraDeviceRawStatus(final String str, int i) {
                synchronized (DeviceInjectorRequirementChecker.this) {
                    try {
                        Slog.i(
                                "CameraService_worker",
                                "DeviceInjector, onSemCameraDeviceRawStatus " + str + " " + i);
                        if (i == 0) {
                            DeviceInjectorRequirementChecker.this.mExternalDeviceMap.remove(str);
                        } else if (i == 1) {
                            DeviceInjectorRequirementChecker.this.mExternalDeviceMap
                                    .computeIfAbsent(
                                            str,
                                            new Function() { // from class:
                                                             // com.samsung.android.camera.CameraServiceWorker$DeviceInjectorRequirementChecker$1$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    int i2;
                                                    CameraServiceWorker
                                                                    .DeviceInjectorRequirementChecker
                                                                    .AnonymousClass1
                                                            anonymousClass1 =
                                                                    CameraServiceWorker
                                                                            .DeviceInjectorRequirementChecker
                                                                            .AnonymousClass1.this;
                                                    String str2 = str;
                                                    try {
                                                        i2 =
                                                                ((Integer)
                                                                                ((CameraManager)
                                                                                                CameraServiceWorker
                                                                                                        .this
                                                                                                        .mContext
                                                                                                        .getSystemService(
                                                                                                                CameraManager
                                                                                                                        .class))
                                                                                        .getCameraCharacteristics(
                                                                                                str2)
                                                                                        .get(
                                                                                                CameraCharacteristics
                                                                                                        .LENS_FACING))
                                                                        .intValue();
                                                    } catch (Exception e) {
                                                        BootReceiver$$ExternalSyntheticOutline0.m(
                                                                e,
                                                                "DeviceInjector, Exception = ",
                                                                "CameraService_worker");
                                                        i2 = 0;
                                                    }
                                                    Slog.i(
                                                            "CameraService_worker",
                                                            "DeviceInjector, isExternalCamera = "
                                                                    + str2
                                                                    + " facing = "
                                                                    + i2);
                                                    return Boolean.valueOf(i2 == 2);
                                                }
                                            });
                        }
                        DeviceInjectorRequirementChecker deviceInjectorRequirementChecker =
                                DeviceInjectorRequirementChecker.this;
                        boolean z = deviceInjectorRequirementChecker.mExternalCameraPresent;
                        ArrayMap arrayMap = deviceInjectorRequirementChecker.mExternalDeviceMap;
                        Boolean bool = Boolean.TRUE;
                        if (z != arrayMap.containsValue(bool)) {
                            DeviceInjectorRequirementChecker deviceInjectorRequirementChecker2 =
                                    DeviceInjectorRequirementChecker.this;
                            boolean containsValue =
                                    deviceInjectorRequirementChecker2.mExternalDeviceMap
                                            .containsValue(bool);
                            Slog.i(
                                    "CameraService_worker",
                                    "DeviceInjector, updateExternalCameraPresentAndNotify :"
                                        + " mExternalCameraPresent = "
                                            + deviceInjectorRequirementChecker2
                                                    .mExternalCameraPresent
                                            + " isExternalCameraPresent() = "
                                            + containsValue);
                            deviceInjectorRequirementChecker2.mExternalCameraPresent =
                                    containsValue;
                            deviceInjectorRequirementChecker2
                                    .notifyDeviceInjectorAvailabilityChanged();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public DeviceInjectorRequirementChecker() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(UiModeManager.SEM_ACTION_ENTER_DESKTOP_MODE);
            intentFilter.addAction(UiModeManager.SEM_ACTION_EXIT_DESKTOP_MODE);
            CameraServiceWorker.this.mContext.registerReceiver(
                    this, intentFilter, null, CameraServiceWorker.this.mHandler, 2);
            ((CameraManager)
                            CameraServiceWorker.this.mContext.getSystemService(CameraManager.class))
                    .registerAvailabilityCallback(
                            new AnonymousClass1(), CameraServiceWorker.this.mHandler);
        }

        public final void notifyDeviceInjectorAvailabilityChanged() {
            StringBuilder sb = new StringBuilder("DeviceInjector requirement = ");
            boolean z = false;
            sb.append(this.mDexMode && this.mExternalCameraPresent);
            sb.append(" (Dex = ");
            sb.append(this.mDexMode);
            sb.append(", External = ");
            sb.append(this.mExternalCameraPresent);
            sb.append(")");
            Slog.i("CameraService_worker", sb.toString());
            if (this.mRequirementMet != (this.mDexMode && this.mExternalCameraPresent)) {
                Slog.i("CameraService_worker", "DeviceInjector requirement change. notify.");
                if (this.mDexMode && this.mExternalCameraPresent) {
                    z = true;
                }
                this.mRequirementMet = z;
                Intent m =
                        ExplicitHealthCheckController$$ExternalSyntheticOutline0.m(
                                "intentfilter.samsung.vtcamerasetting.deviceinjector.option",
                                "com.samsung.android.vtcamerasettings");
                m.putExtra("dex_and_camera", this.mRequirementMet);
                CameraServiceWorker.this.mContext.startServiceAsUser(m, UserHandle.CURRENT_OR_SELF);
            }
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            synchronized (this) {
                this.mDexMode =
                        UiModeManager.SEM_ACTION_ENTER_DESKTOP_MODE.equals(intent.getAction());
                Slog.i("CameraService_worker", "DeviceInjector, dex mode = " + this.mDexMode);
                notifyDeviceInjectorAvailabilityChanged();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceStateListener implements DeviceStateManager.DeviceStateCallback {
        public DeviceStateListener() {}

        public final void onDeviceStateChanged(DeviceState deviceState) {
            int identifier = deviceState.getIdentifier();
            synchronized (CameraServiceWorker.this.mLock) {
                Slog.i("CameraService_worker", "Fold state changed, " + deviceState);
                Logger.log(Logger.ID.FOLD_EVENT, "Fold state changed, " + deviceState);
            }
            if (identifier == 3 || identifier == 2) {
                CameraServiceWorker.this.notifyDeviceChange(0L);
            } else if (identifier == 0) {
                CameraServiceWorker.this.notifyDeviceChange(6L);
            } else if (identifier == 1) {
                CameraServiceWorker.this.notifyDeviceChange(6L);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayWindowListener extends IDisplayWindowListener.Stub {
        public DisplayWindowListener() {}

        public final void onDisplayAdded(int i) {}

        public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
            ICameraService cameraService = CameraServiceWorker.this.getCameraService();
            if (cameraService == null) {
                return;
            }
            try {
                cameraService.notifyDeviceInjectorOrientationChange();
            } catch (RemoteException e) {
                AccountManagerService$$ExternalSyntheticOutline0.m(
                        "Could not notify cameraserver, remote exception: ",
                        e,
                        "CameraService_worker");
            }
        }

        public final void onDisplayRemoved(int i) {}

        public final void onFixedRotationFinished(int i) {}

        public final void onFixedRotationStarted(int i, int i2) {}

        public final void onKeepClearAreasChanged(int i, List list, List list2) {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TaskInfo extends Record {
        public final int displayId;
        public final int frontTaskId;
        public final boolean isFixedOrientationLandscape;
        public final boolean isFixedOrientationPortrait;
        public final boolean isResizable;
        public final int userId;

        public TaskInfo(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
            this.frontTaskId = i;
            this.isResizable = z;
            this.isFixedOrientationLandscape = z2;
            this.isFixedOrientationPortrait = z3;
            this.displayId = i2;
            this.userId = i3;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return (boolean)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "equals",
                                    MethodType.methodType(
                                            Boolean.TYPE, TaskInfo.class, Object.class),
                                    TaskInfo.class,
                                    "frontTaskId;isResizable;isFixedOrientationLandscape;isFixedOrientationPortrait;displayId;userId",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->frontTaskId:I",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->isResizable:Z",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->isFixedOrientationLandscape:Z",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->isFixedOrientationPortrait:Z",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->displayId:I",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->userId:I")
                            .dynamicInvoker()
                            .invoke(this, obj) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "hashCode",
                                    MethodType.methodType(Integer.TYPE, TaskInfo.class),
                                    TaskInfo.class,
                                    "frontTaskId;isResizable;isFixedOrientationLandscape;isFixedOrientationPortrait;displayId;userId",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->frontTaskId:I",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->isResizable:Z",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->isFixedOrientationLandscape:Z",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->isFixedOrientationPortrait:Z",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->displayId:I",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->userId:I")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "toString",
                                    MethodType.methodType(String.class, TaskInfo.class),
                                    TaskInfo.class,
                                    "frontTaskId;isResizable;isFixedOrientationLandscape;isFixedOrientationPortrait;displayId;userId",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->frontTaskId:I",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->isResizable:Z",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->isFixedOrientationLandscape:Z",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->isFixedOrientationPortrait:Z",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->displayId:I",
                                    "FIELD:Lcom/samsung/android/camera/CameraServiceWorker$TaskInfo;->userId:I")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WorkerOrientationListener extends OrientationEventListener {
        public int mLatestOrientation;

        public WorkerOrientationListener(Context context) {
            super(context);
            DisplayManager displayManager =
                    (DisplayManager)
                            CameraServiceWorker.this.mContext.getSystemService(
                                    DisplayManager.class);
            if (displayManager != null) {
                Display display = displayManager.getDisplay(0);
                if (display == null) {
                    this.mLatestOrientation = 0;
                    return;
                }
                int rotation = display.getRotation();
                if (rotation == 1) {
                    this.mLatestOrientation =
                            FrameworkStatsLog
                                    .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_AI_CLEAR_ZOOM_MERGE_ZSL_ANCHOR_6;
                    return;
                }
                if (rotation == 2) {
                    this.mLatestOrientation = 180;
                } else if (rotation != 3) {
                    this.mLatestOrientation = 0;
                } else {
                    this.mLatestOrientation = 90;
                }
            }
        }

        @Override // android.view.OrientationEventListener
        public final void onOrientationChanged(int i) {
            int i2 = (((i + 45) / 90) * 90) % 360;
            if (this.mLatestOrientation != i2) {
                this.mLatestOrientation = i2;
                ICameraService cameraService = CameraServiceWorker.this.getCameraService();
                if (cameraService == null) {
                    return;
                }
                try {
                    cameraService.notifyDeviceInjectorOrientationChange();
                } catch (RemoteException e) {
                    AccountManagerService$$ExternalSyntheticOutline0.m(
                            "Could not notify cameraserver, remote exception: ",
                            e,
                            "CameraService_worker");
                }
            }
        }
    }

    static {
        DEBUG = !Build.TYPE.equals("user") || Debug.semIsProductDev();
        SAMSUNG_CAMERA_PACKAGES =
                new String[] {
                    "com.sec.android.app.camera",
                    "com.samsung.android.smartface",
                    "com.samsung.adaptivebrightnessgo",
                    "com.samsung.android.sead"
                };
        DEVICE_INJECTOR_TEST_PACKAGES =
                new String[] {"com.samsung.android.camera.test", "injector.test"};
        DEVICE_INJECTOR_TEST_PACKAGES_FOR_BLOCK = new String[] {"injector.test.phone"};
    }

    public CameraServiceWorker(Context context) {
        super(context);
        this.mLock = new Object();
        this.mActiveCameraUsage = new ArrayMap();
        this.mOpenCameraUsage = new ArrayMap();
        this.mIsCameraOpened = false;
        this.mServiceLock = new Object();
        this.mDisplayId = 0;
        this.mDeviceState = 0L;
        this.mFoldStateLatch = new CountDownLatch(0);
        this.mBootCompleted = false;
        this.mDisplayWindowListener = new DisplayWindowListener();
        this.mOrientationLock = new Object();
        this.mCameraServiceWorker = new AnonymousClass3();
        this.mContext = context;
        ServiceThread m = Watchdog$$ExternalSyntheticOutline0.m(-4, "CameraService_worker", false);
        Handler handler = new Handler(m.getLooper(), this);
        this.mHandler = handler;
        boolean z =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
        this.mEnableSurveyMode = z;
        if (DEBUG) {
            Slog.v("CameraService_worker", "enable survey mode is " + z);
        }
        this.mShakeEventListener = new ShakeEventListener(this, context, handler);
        this.mRequestInjectorService = new RequestInjectorService(this, context, handler);
        this.mVtCameraProviderObserver = new VtCameraProviderObserver(this, context, handler);
        this.mScpmReceiver = new ScpmReceiver(this, context, m.getLooper());
        new BootCompleteReceiver();
        new CPRCommandReceiver();
    }

    public static TaskInfo getTaskInfo(int i, String str) {
        TaskInfo taskInfo;
        try {
            ParceledListSlice recentTasks =
                    ActivityTaskManager.getService().getRecentTasks(2, 0, i);
            if (recentTasks == null || recentTasks.getList().isEmpty()) {
                Slog.e("CameraService_worker", "Recent task list is empty!");
                return null;
            }
            Iterator it = recentTasks.getList().iterator();
            while (true) {
                if (!it.hasNext()) {
                    taskInfo = null;
                    break;
                }
                ActivityManager.RecentTaskInfo recentTaskInfo =
                        (ActivityManager.RecentTaskInfo) it.next();
                ActivityInfo activityInfo = recentTaskInfo.topActivityInfo;
                if (activityInfo != null && Objects.equals(str, activityInfo.packageName)) {
                    int i2 = recentTaskInfo.taskId;
                    ActivityInfo activityInfo2 = recentTaskInfo.topActivityInfo;
                    taskInfo =
                            new TaskInfo(
                                    i2,
                                    recentTaskInfo.displayId,
                                    recentTaskInfo.userId,
                                    activityInfo2.resizeMode != 0,
                                    ActivityInfo.isFixedOrientationLandscape(
                                            activityInfo2.screenOrientation),
                                    ActivityInfo.isFixedOrientationPortrait(
                                            recentTaskInfo.topActivityInfo.screenOrientation));
                }
            }
            if (taskInfo != null) {
                return taskInfo;
            }
            BootReceiver$$ExternalSyntheticOutline0.m(
                    "Recent tasks don't include camera client package name: ",
                    str,
                    "CameraService_worker");
            return null;
        } catch (RemoteException unused) {
            Slog.e("CameraService_worker", "Failed to query recent tasks!");
            return null;
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.e("CameraService_worker", "Native camera service has died");
        synchronized (this.mLock) {
            synchronized (this.mServiceLock) {
                this.mCameraServiceRaw = null;
            }
            this.mActiveCameraUsage.clear();
            this.mOpenCameraUsage.clear();
            this.mIsCameraOpened = false;
            synchronized (this.mOrientationLock) {
                try {
                    WorkerOrientationListener workerOrientationListener =
                            this.mOrientationEventListener;
                    if (workerOrientationListener != null) {
                        workerOrientationListener.disable();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Logger.log(Logger.ID.CAMERA_EVENT, "Close all camera, camera service died");
            this.mFoldStateLatch.countDown();
        }
    }

    public final ICameraService getCameraService() {
        synchronized (this.mServiceLock) {
            if (this.mCameraServiceRaw == null) {
                IBinder binderService = getBinderService("media.camera");
                if (binderService == null) {
                    Slog.w(
                            "CameraService_worker",
                            "Could not notify mediaserver, camera service not available.");
                    return null;
                }
                try {
                    binderService.linkToDeath(this, 0);
                    this.mCameraServiceRaw = ICameraService.Stub.asInterface(binderService);
                } catch (RemoteException unused) {
                    Slog.w(
                            "CameraService_worker",
                            "Could not link to death of native camera service");
                    return null;
                }
            }
            return this.mCameraServiceRaw;
        }
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        if (message.what != 3) {
            VaultKeeperService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("CameraServiceWorker error, invalid message: "),
                    message.what,
                    "CameraService_worker");
            return true;
        }
        synchronized (this.mLock) {
            notifyDeviceChangeRetryLocked(message.arg1);
        }
        return true;
    }

    public final void insertDMALog(Long l, String str, String str2) {
        if (DEBUG) {
            StringBuilder m =
                    InitialConfiguration$$ExternalSyntheticOutline0.m(
                            "insertDMALog: trackingId=4K3-399-1014897, feature=",
                            str,
                            ", extra=",
                            str2,
                            ", value=");
            m.append(l);
            Slog.v("CameraService_worker", m.toString());
        }
        Bundle bundle = new Bundle();
        bundle.putString("tracking_id", "4K3-399-1014897");
        bundle.putString(LauncherConfigurationInternal.KEY_FEATURE_INT, str);
        if (str2 != null) {
            bundle.putString("extra", str2);
        }
        if (l != null) {
            bundle.putLong("value", l.longValue());
        }
        bundle.putString("type", "ev");
        bundle.putString("pkg_name", "com.samsung.android.camera");
        if (str.equals("7501") && l != null) {
            HashMap hashMap = new HashMap();
            Locale locale = Locale.UK;
            int intValue = l.intValue();
            hashMap.put(
                    "3PApp_Camera_Display",
                    str2
                            + "_"
                            + (intValue != 0
                                    ? intValue != 1
                                            ? intValue != 2 ? "UnknownCamera" : "ExternalCamera"
                                            : "FrontCamera"
                                    : "BackCamera")
                            + "_"
                            + (this.mDisplayId == 1 ? "SubDisplay" : "MainDisplay"));
            bundle.putSerializable("dimension", hashMap);
        }
        Intent intent = new Intent();
        intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
        intent.putExtras(bundle);
        intent.setPackage("com.sec.android.diagmonagent");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        this.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void notifyDeviceChange(long j) {
        synchronized (this.mLock) {
            try {
                if (this.mDeviceState != j) {
                    this.mDeviceState = j;
                    notifyDeviceChangeRetryLocked(30);
                } else {
                    Slog.v("CameraService_worker", "Same device state has coming. skip");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Pair notifyDeviceChangeLocked(long j, boolean z) {
        ICameraService cameraService = getCameraService();
        if (cameraService == null) {
            Pair pair =
                    new Pair(
                            Boolean.FALSE,
                            "Could not notify mediaserver, camera service not available.");
            Slog.w("CameraService_worker", (String) pair.second);
            return pair;
        }
        Slog.i(
                "CameraService_worker",
                String.format(
                        "NotifyDeviceStateChange 0x%X, sync(%b)",
                        Long.valueOf(j), Boolean.valueOf(z)));
        try {
            if (z) {
                cameraService.notifyDeviceStateChangeSync(j);
            } else {
                cameraService.notifyDeviceStateChange(j);
            }
            return new Pair(
                    Boolean.TRUE,
                    String.format("NotifyDeviceStateChange success: 0x%X", Long.valueOf(j)));
        } catch (RemoteException e) {
            Pair pair2 =
                    new Pair(
                            Boolean.FALSE,
                            "Could not notify device state change, remote exception: " + e);
            Slog.w("CameraService_worker", (String) pair2.second);
            return pair2;
        }
    }

    public final void notifyDeviceChangeRetryLocked(int i) {
        if (((Boolean) notifyDeviceChangeLocked(this.mDeviceState, false).first).booleanValue()) {
            i = 0;
        }
        if (i <= 0) {
            return;
        }
        Slog.i(
                "CameraService_worker",
                "Could not notify camera service of device state change, retrying...");
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(3, i - 1, 0, null), 20L);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0091, code lost:

       if (r2.isUpdatedSystemApp() != false) goto L36;
    */
    @Override // com.android.server.SystemService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBootPhase(int r6) {
        /*
            r5 = this;
            r0 = 500(0x1f4, float:7.0E-43)
            if (r6 != r0) goto L2c
            java.lang.String r6 = "window"
            android.os.IBinder r6 = android.os.ServiceManager.getService(r6)
            android.view.IWindowManager r6 = android.view.IWindowManager.Stub.asInterface(r6)
            com.android.server.wm.WindowManagerService r6 = (com.android.server.wm.WindowManagerService) r6
            android.content.Context r6 = r5.mContext
            java.lang.Class<android.hardware.devicestate.DeviceStateManager> r0 = android.hardware.devicestate.DeviceStateManager.class
            java.lang.Object r6 = r6.getSystemService(r0)
            android.hardware.devicestate.DeviceStateManager r6 = (android.hardware.devicestate.DeviceStateManager) r6
            android.os.HandlerExecutor r0 = new android.os.HandlerExecutor
            android.os.Handler r1 = r5.mHandler
            r0.<init>(r1)
            com.samsung.android.camera.CameraServiceWorker$DeviceStateListener r1 = new com.samsung.android.camera.CameraServiceWorker$DeviceStateListener
            r1.<init>()
            r6.registerCallback(r0, r1)
            goto Ldd
        L2c:
            r0 = 1000(0x3e8, float:1.401E-42)
            if (r6 != r0) goto L61
            android.view.IWindowManager r6 = android.view.WindowManagerGlobal.getWindowManagerService()     // Catch: android.os.RemoteException -> L48
            com.samsung.android.camera.CameraServiceWorker$DisplayWindowListener r0 = r5.mDisplayWindowListener     // Catch: android.os.RemoteException -> L48
            int[] r6 = r6.registerDisplayWindowListener(r0)     // Catch: android.os.RemoteException -> L48
            r0 = 0
        L3b:
            int r1 = r6.length     // Catch: android.os.RemoteException -> L48
            if (r0 >= r1) goto L4f
            com.samsung.android.camera.CameraServiceWorker$DisplayWindowListener r1 = r5.mDisplayWindowListener     // Catch: android.os.RemoteException -> L48
            r2 = r6[r0]     // Catch: android.os.RemoteException -> L48
            r1.getClass()     // Catch: android.os.RemoteException -> L48
            int r0 = r0 + 1
            goto L3b
        L48:
            java.lang.String r6 = "CameraService_worker"
            java.lang.String r0 = "Failed to register display window listener!"
            android.util.Slog.e(r6, r0)
        L4f:
            java.lang.Object r0 = r5.mOrientationLock
            monitor-enter(r0)
            com.samsung.android.camera.CameraServiceWorker$WorkerOrientationListener r6 = new com.samsung.android.camera.CameraServiceWorker$WorkerOrientationListener     // Catch: java.lang.Throwable -> L5e
            android.content.Context r1 = r5.mContext     // Catch: java.lang.Throwable -> L5e
            r6.<init>(r1)     // Catch: java.lang.Throwable -> L5e
            r5.mOrientationEventListener = r6     // Catch: java.lang.Throwable -> L5e
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5e
            goto Ldd
        L5e:
            r5 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5e
            throw r5
        L61:
            r0 = 600(0x258, float:8.41E-43)
            if (r6 != r0) goto Ldd
            com.samsung.android.camera.ShakeEventListener r6 = r5.mShakeEventListener
            monitor-enter(r6)
            monitor-exit(r6)
            java.lang.String r6 = "CameraService_worker"
            java.lang.String r0 = "Shake event is not supported."
            android.util.Slog.i(r6, r0)
            com.samsung.android.camera.requestinjector.RequestInjectorService r6 = r5.mRequestInjectorService
            com.samsung.android.camera.Logger$ID r0 = com.samsung.android.camera.Logger.ID.REQUEST_INJECTOR_SERVICE
            java.lang.String r1 = "RequestInjectorService"
            java.lang.String r2 = "com.samsung.android.vtcamerasettings"
            android.content.Context r3 = r6.mContext
            android.content.pm.PackageManager r3 = r3.getPackageManager()
            r4 = 4
            android.content.pm.PackageInfo r2 = r3.getPackageInfo(r2, r4)     // Catch: java.lang.Exception -> L94
            android.content.pm.ApplicationInfo r2 = r2.applicationInfo     // Catch: java.lang.Exception -> L94
            if (r2 == 0) goto Lba
            boolean r3 = r2.isSystemApp()     // Catch: java.lang.Exception -> L94
            if (r3 != 0) goto L96
            boolean r2 = r2.isUpdatedSystemApp()     // Catch: java.lang.Exception -> L94
            if (r2 == 0) goto Lba
            goto L96
        L94:
            r6 = move-exception
            goto Lb4
        L96:
            java.lang.String r2 = "VT Camera Setting exists. Register camera listener."
            android.util.Slog.i(r1, r2)
            com.samsung.android.camera.Logger.log(r0, r2)
            android.content.Context r0 = r6.mContext
            java.lang.String r1 = "camera"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.hardware.camera2.CameraManager r0 = (android.hardware.camera2.CameraManager) r0
            com.samsung.android.camera.requestinjector.RequestInjectorService$1 r1 = new com.samsung.android.camera.requestinjector.RequestInjectorService$1
            r1.<init>()
            android.os.Handler r6 = r6.mHandler
            r2 = 1
            r0.registerSemCameraDeviceStateCallback(r1, r6, r2)
            goto Lc2
        Lb4:
            java.lang.String r2 = "pkgInstalledOrNot "
            com.android.server.BootReceiver$$ExternalSyntheticOutline0.m(r6, r2, r1)
        Lba:
            java.lang.String r6 = "VT Camera Setting does not exist. Skip register camera listener."
            android.util.Slog.i(r1, r6)
            com.samsung.android.camera.Logger.log(r0, r6)
        Lc2:
            com.samsung.android.camera.scpm.ScpmReceiver r6 = r5.mScpmReceiver
            android.content.Context r0 = r6.mContext
            java.lang.String r1 = "camera"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.hardware.camera2.CameraManager r0 = (android.hardware.camera2.CameraManager) r0
            com.samsung.android.camera.scpm.ScpmReceiver$2 r1 = new com.samsung.android.camera.scpm.ScpmReceiver$2
            r1.<init>()
            android.os.Handler r6 = r6.mHandler
            r0.registerSemCameraDeviceStateCallback(r1, r6)
            com.samsung.android.camera.CameraServiceWorker$DeviceInjectorRequirementChecker r6 = new com.samsung.android.camera.CameraServiceWorker$DeviceInjectorRequirementChecker
            r6.<init>()
        Ldd:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.camera.CameraServiceWorker.onBootPhase(int):void");
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Slog.i("CameraService_worker", "CameraServiceWorker is started.");
        publishBinderService("media.camera.worker", this.mCameraServiceWorker);
        publishLocalService(CameraServiceWorker.class, this);
    }
}
