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
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.Display;
import android.view.IDisplayWindowListener;
import android.view.IWindowManager;
import android.view.OrientationEventListener;
import android.view.WindowManagerGlobal;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.camera.CameraServiceWorker;
import com.samsung.android.camera.ICameraServiceWorker;
import com.samsung.android.camera.Logger;
import com.samsung.android.camera.requestinjector.RequestInjectorService;
import com.samsung.android.camera.requestinjector.VtCameraProviderObserver;
import com.samsung.android.camera.scpm.ScpmReceiver;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class CameraServiceWorker extends SystemService implements Handler.Callback, IBinder.DeathRecipient {
    public static final boolean DEBUG;
    public static final String[] DEVICE_INJECTOR_TEST_PACKAGES;
    public static final String[] DEVICE_INJECTOR_TEST_PACKAGES_FOR_BLOCK;
    public static final String[] SAMSUNG_CAMERA_PACKAGES;
    public final ArrayMap mActiveCameraUsage;
    public BootCompleteReceiver mBootCompleteReceiver;
    public boolean mBootCompleted;
    public CPRCommandReceiver mCPRCommandReceiver;
    public ICameraService mCameraServiceRaw;
    public final ICameraServiceWorker.Stub mCameraServiceWorker;
    public final Context mContext;
    public DeviceInjectorRequirementChecker mDeviceInjectorRequirementChecker;
    public long mDeviceState;
    public int mDisplayId;
    public final DisplayManager.DisplayListener mDisplayListener;
    public DisplayManager mDisplayManager;
    public final DisplayManagerInternal.DisplayStateListener mDisplayStateListener;
    public final DisplayWindowListener mDisplayWindowListener;
    public final boolean mEnableSurveyMode;
    public CountDownLatch mFoldStateLatch;
    public final Handler mHandler;
    public final ServiceThread mHandlerThread;
    public boolean mIsCameraOpened;
    public int mLastDisplayRotation;
    public final Object mLock;
    public final ArrayMap mOpenCameraUsage;
    public WorkerOrientationListener mOrientationEventListener;
    public final Object mOrientationLock;
    public RequestInjectorService mRequestInjectorService;
    public ScpmReceiver mScpmReceiver;
    public final Object mServiceLock;
    public ShakeEventListener mShakeEventListener;
    public VtCameraProviderObserver mVtCameraProviderObserver;
    public WindowManagerService mWindowManagerService;

    public final class TaskInfo {
        public int displayId;
        public int frontTaskId;
        public boolean isFixedOrientationLandscape;
        public boolean isFixedOrientationPortrait;
        public boolean isResizable;
        public int userId;
    }

    public static String cameraFacingToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "UnknownCamera" : "ExternalCamera" : "FrontCamera" : "BackCamera";
    }

    public static String cameraStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 100 ? i != 101 ? "CAMERA_STATE_UNKNOWN" : "CAMERA_STATE_OPENING_FAILED" : "CAMERA_STATE_OPENING" : "CAMERA_STATE_CLOSED" : "CAMERA_STATE_IDLE" : "CAMERA_STATE_ACTIVE" : "CAMERA_STATE_OPEN";
    }

    static {
        DEBUG = !Build.TYPE.equals("user") || Debug.semIsProductDev();
        SAMSUNG_CAMERA_PACKAGES = new String[]{"com.sec.android.app.camera", "com.samsung.android.smartface", "com.samsung.adaptivebrightnessgo", "com.samsung.android.sead"};
        DEVICE_INJECTOR_TEST_PACKAGES = new String[]{"com.samsung.android.camera.test", "injector.test"};
        DEVICE_INJECTOR_TEST_PACKAGES_FOR_BLOCK = new String[]{"injector.test.phone"};
    }

    public boolean isEnableSurveyMode() {
        return this.mEnableSurveyMode;
    }

    public boolean isCameraOpened() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsCameraOpened;
        }
        return z;
    }

    public class CameraUsageEvent {
        public final int mAPILevel;
        public final int mCameraFacing;
        public final String mClientName;

        public CameraUsageEvent(int i, String str, int i2) {
            this.mCameraFacing = i;
            this.mClientName = str;
            this.mAPILevel = i2;
        }
    }

    public final class DisplayWindowListener extends IDisplayWindowListener.Stub {
        public void onDisplayAdded(int i) {
        }

        public void onDisplayRemoved(int i) {
        }

        public void onFixedRotationFinished(int i) {
        }

        public void onFixedRotationStarted(int i, int i2) {
        }

        public void onKeepClearAreasChanged(int i, List list, List list2) {
        }

        public DisplayWindowListener() {
        }

        public void onDisplayConfigurationChanged(int i, Configuration configuration) {
            ICameraService cameraService = CameraServiceWorker.this.getCameraService();
            if (cameraService == null) {
                return;
            }
            try {
                cameraService.notifyDeviceInjectorOrientationChange();
            } catch (RemoteException e) {
                Slog.w("CameraService_worker", "Could not notify cameraserver, remote exception: " + e);
            }
        }
    }

    public final class WorkerOrientationListener extends OrientationEventListener {
        public int mLatestOrientation;

        public WorkerOrientationListener(Context context) {
            super(context);
            DisplayManager displayManager = (DisplayManager) CameraServiceWorker.this.mContext.getSystemService(DisplayManager.class);
            if (displayManager != null) {
                Display display = displayManager.getDisplay(0);
                if (display == null) {
                    this.mLatestOrientation = 0;
                    return;
                }
                int rotation = display.getRotation();
                if (rotation == 1) {
                    this.mLatestOrientation = FrameworkStatsLog.f392x1636cc72;
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

        public int getLatestOrientation() {
            return this.mLatestOrientation;
        }

        @Override // android.view.OrientationEventListener
        public void onOrientationChanged(int i) {
            int roundOrientation;
            if (i == -1 || this.mLatestOrientation == (roundOrientation = roundOrientation(i))) {
                return;
            }
            this.mLatestOrientation = roundOrientation;
            ICameraService cameraService = CameraServiceWorker.this.getCameraService();
            if (cameraService == null) {
                return;
            }
            try {
                cameraService.notifyDeviceInjectorOrientationChange();
            } catch (RemoteException e) {
                Slog.w("CameraService_worker", "Could not notify cameraserver, remote exception: " + e);
            }
        }

        public final int roundOrientation(int i) {
            return (((i + 45) / 90) * 90) % 360;
        }
    }

    public class BootCompleteReceiver extends BroadcastReceiver {
        public BootCompleteReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
            intentFilter.addAction("com.samsung.intent.action.LAZY_BOOT_COMPLETE");
            CameraServiceWorker.this.mContext.registerReceiver(this, intentFilter);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                Slog.v("CameraService_worker", "ACTION***" + intent.getAction());
                CameraServiceWorker.this.mVtCameraProviderObserver.tryRegisterContentObserver();
                try {
                    CameraServiceWorker.this.mCameraServiceWorker.pingForUpdate();
                } catch (RemoteException e) {
                    Slog.e("CameraService_worker", "BootCompleteReceiver exception happen " + e);
                }
                CameraServiceWorker.this.mBootCompleted = true;
                return;
            }
            if ("com.samsung.intent.action.LAZY_BOOT_COMPLETE".equals(intent.getAction())) {
                Slog.v("CameraService_worker", "ACTION***" + intent.getAction());
                CameraServiceWorker.this.mScpmReceiver.tryRegisterSCPMServer();
            }
        }
    }

    public class DeviceStateListener implements DeviceStateManager.DeviceStateCallback {
        public DeviceStateListener() {
        }

        public void onStateChanged(int i) {
            synchronized (CameraServiceWorker.this.mLock) {
                Slog.i("CameraService_worker", "Fold state changed, " + i);
                Logger.log(Logger.EnumC3085ID.FOLD_EVENT, "Fold state changed, " + i);
            }
            if (i == 0) {
                CameraServiceWorker.this.notifyDeviceChange(6L);
                return;
            }
            if (i == 1) {
                CameraServiceWorker.this.notifyDeviceChange(6L);
            } else if (i == 2 || i == 3) {
                CameraServiceWorker.this.notifyDeviceChange(0L);
            }
        }
    }

    public class CPRCommandReceiver extends BroadcastReceiver {
        public String ACTION_RESET_CAMERAPROVIDER = "com.samsung.cmh.action.cameraprovider";

        public CPRCommandReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(this.ACTION_RESET_CAMERAPROVIDER);
            CameraServiceWorker.this.mContext.registerReceiver(this, intentFilter);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (this.ACTION_RESET_CAMERAPROVIDER.equals(intent.getAction())) {
                Slog.v("CameraService_worker", "ACTION***" + intent.getAction());
                CameraServiceWorker.this.notifyDeviceChangeLocked(17179869184L);
            }
        }
    }

    public class DeviceInjectorRequirementChecker extends BroadcastReceiver {
        public boolean mDexMode = false;
        public boolean mExternalCameraPresent = false;
        public boolean mRequirementMet = false;
        public ArrayMap mExternalDeviceMap = new ArrayMap();

        public DeviceInjectorRequirementChecker() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(UiModeManager.SEM_ACTION_ENTER_DESKTOP_MODE);
            intentFilter.addAction(UiModeManager.SEM_ACTION_EXIT_DESKTOP_MODE);
            CameraServiceWorker.this.mContext.registerReceiver(this, intentFilter, null, CameraServiceWorker.this.mHandler);
            registerExternalCameraCallback();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            synchronized (this) {
                this.mDexMode = UiModeManager.SEM_ACTION_ENTER_DESKTOP_MODE.equals(intent.getAction());
                Slog.i("CameraService_worker", "DeviceInjector, dex mode = " + this.mDexMode);
                notifyDeviceInjectorAvailabilityChanged();
            }
        }

        /* renamed from: com.samsung.android.camera.CameraServiceWorker$DeviceInjectorRequirementChecker$1 */
        public class C30841 extends CameraManager.AvailabilityCallback {
            public C30841() {
            }

            public void onSemCameraDeviceRawStatus(final String str, int i) {
                synchronized (DeviceInjectorRequirementChecker.this) {
                    Slog.i("CameraService_worker", "DeviceInjector, onSemCameraDeviceRawStatus " + str + " " + i);
                    if (i == 0) {
                        DeviceInjectorRequirementChecker.this.mExternalDeviceMap.remove(str);
                    } else if (i == 1) {
                        DeviceInjectorRequirementChecker.this.mExternalDeviceMap.computeIfAbsent(str, new Function() { // from class: com.samsung.android.camera.CameraServiceWorker$DeviceInjectorRequirementChecker$1$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                Boolean lambda$onSemCameraDeviceRawStatus$0;
                                lambda$onSemCameraDeviceRawStatus$0 = CameraServiceWorker.DeviceInjectorRequirementChecker.C30841.this.lambda$onSemCameraDeviceRawStatus$0(str, (String) obj);
                                return lambda$onSemCameraDeviceRawStatus$0;
                            }
                        });
                    }
                    if (DeviceInjectorRequirementChecker.this.mExternalCameraPresent != DeviceInjectorRequirementChecker.this.isExternalCameraPresent()) {
                        DeviceInjectorRequirementChecker deviceInjectorRequirementChecker = DeviceInjectorRequirementChecker.this;
                        deviceInjectorRequirementChecker.updateExternalCameraPresentAndNotify(deviceInjectorRequirementChecker.isExternalCameraPresent());
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ Boolean lambda$onSemCameraDeviceRawStatus$0(String str, String str2) {
                return Boolean.valueOf(DeviceInjectorRequirementChecker.this.isExternalCamera(str));
            }
        }

        public final void registerExternalCameraCallback() {
            ((CameraManager) CameraServiceWorker.this.mContext.getSystemService(CameraManager.class)).registerAvailabilityCallback(new C30841(), CameraServiceWorker.this.mHandler);
        }

        public final boolean isExternalCameraPresent() {
            return this.mExternalDeviceMap.containsValue(Boolean.TRUE);
        }

        public final boolean isExternalCamera(String str) {
            int i;
            try {
                i = ((Integer) ((CameraManager) CameraServiceWorker.this.mContext.getSystemService(CameraManager.class)).getCameraCharacteristics(str).get(CameraCharacteristics.LENS_FACING)).intValue();
            } catch (Exception e) {
                Slog.e("CameraService_worker", "DeviceInjector, Exception = " + e);
                i = 0;
            }
            Slog.i("CameraService_worker", "DeviceInjector, isExternalCamera = " + str + " facing = " + i);
            return i == 2;
        }

        public final void updateExternalCameraPresentAndNotify(boolean z) {
            Slog.i("CameraService_worker", "DeviceInjector, updateExternalCameraPresentAndNotify : mExternalCameraPresent = " + this.mExternalCameraPresent + " isExternalCameraPresent() = " + z);
            this.mExternalCameraPresent = z;
            notifyDeviceInjectorAvailabilityChanged();
        }

        public final void notifyDeviceInjectorAvailabilityChanged() {
            StringBuilder sb = new StringBuilder();
            sb.append("DeviceInjector requirement = ");
            sb.append(this.mDexMode && this.mExternalCameraPresent);
            sb.append(" (Dex = ");
            sb.append(this.mDexMode);
            sb.append(", External = ");
            sb.append(this.mExternalCameraPresent);
            sb.append(")");
            Slog.i("CameraService_worker", sb.toString());
            if (this.mRequirementMet != (this.mDexMode && this.mExternalCameraPresent)) {
                Slog.i("CameraService_worker", "DeviceInjector requirement change. notify.");
                this.mRequirementMet = this.mDexMode && this.mExternalCameraPresent;
                Intent intent = new Intent("intentfilter.samsung.vtcamerasetting.deviceinjector.option");
                intent.setPackage("com.samsung.android.vtcamerasettings");
                intent.putExtra("dex_and_camera", this.mRequirementMet);
                CameraServiceWorker.this.mContext.startServiceAsUser(intent, UserHandle.CURRENT_OR_SELF);
            }
        }
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
        this.mDisplayStateListener = new DisplayManagerInternal.DisplayStateListener() { // from class: com.samsung.android.camera.CameraServiceWorker.1
            public void onStart(int i, int i2, int i3) {
                if (i == 2 && i3 == 1 && CameraServiceWorker.this.mFoldStateLatch.getCount() > 0) {
                    try {
                        Slog.v("CameraService_worker", "mFoldStateLatch E");
                        CountDownLatch countDownLatch = CameraServiceWorker.this.mFoldStateLatch;
                        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                        if (!countDownLatch.await(500L, timeUnit)) {
                            Slog.e("CameraService_worker", "mDisplayStateListener onStart - can't wait for close camera is done for 500 millisec");
                            if (CameraServiceWorker.this.mDeviceState != 0) {
                                Slog.e("CameraService_worker", "mDisplayStateListener onStart - but device state does not changed yet. wait 500ms more");
                                CameraServiceWorker.this.mFoldStateLatch.await(500L, timeUnit);
                            }
                        }
                        Slog.v("CameraService_worker", "mFoldStateLatch X");
                    } catch (InterruptedException unused) {
                        Slog.e("CameraService_worker", "mDisplayStateListener onStart - getting interrupt during wait for close camera is done");
                    }
                }
            }
        };
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.samsung.android.camera.CameraServiceWorker.2
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
                CameraServiceWorker.this.mDisplayId = i;
                if (i != 1) {
                    return;
                }
                int rotation = CameraServiceWorker.this.mDisplayManager.getDisplay(1).getRotation();
                synchronized (CameraServiceWorker.this.mLock) {
                    Slog.v("CameraService_worker", "onDisplayChanged rotation : " + rotation + " current mDeviceState : " + CameraServiceWorker.this.mDeviceState);
                    if (CameraServiceWorker.this.mLastDisplayRotation != rotation) {
                        if (2 == rotation && (CameraServiceWorker.this.mDeviceState & 6) != 0 && CameraServiceWorker.this.isCoverFlexRotateApp()) {
                            CameraServiceWorker cameraServiceWorker = CameraServiceWorker.this;
                            cameraServiceWorker.notifyDeviceChange(cameraServiceWorker.mDeviceState | 34359738368L);
                        } else {
                            CameraServiceWorker cameraServiceWorker2 = CameraServiceWorker.this;
                            cameraServiceWorker2.notifyDeviceChange(cameraServiceWorker2.mDeviceState & (-34359738369L));
                        }
                        CameraServiceWorker.this.mLastDisplayRotation = rotation;
                    }
                }
            }
        };
        this.mCameraServiceWorker = new ICameraServiceWorker.Stub() { // from class: com.samsung.android.camera.CameraServiceWorker.3
            @Override // com.samsung.android.camera.ICameraServiceWorker
            public final String getInterfaceHash() {
                return "d9fcda68f4b826972b6433fa34b53cce41b2d3f6";
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public final int getInterfaceVersion() {
                return 4;
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void pingForUpdate() {
                if (Binder.getCallingUid() != 1047 && Binder.getCallingPid() != Process.myPid()) {
                    Slog.e("CameraService_worker", "Calling UID: " + Binder.getCallingUid() + " doesn't match expected  camera service UID!");
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    CameraServiceWorker.this.mVtCameraProviderObserver.updateCameraService();
                    CameraServiceWorker.this.mScpmReceiver.initialize();
                    try {
                        CameraServiceWorker.this.mRequestInjectorService.sendAllExtraRequestsToRequestInjector();
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

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void notifyCameraState(String str, int i, int i2, String str2, int i3) {
                if (Binder.getCallingUid() != 1047) {
                    Slog.e("CameraService_worker", "Calling UID: " + Binder.getCallingUid() + " doesn't match expected  camera service UID!");
                    return;
                }
                String cameraStateToString = CameraServiceWorker.cameraStateToString(i);
                String cameraFacingToString = CameraServiceWorker.cameraFacingToString(i2);
                if (CameraServiceWorker.DEBUG) {
                    Slog.v("CameraService_worker", "Camera " + str + " facing " + cameraFacingToString + " state now " + cameraStateToString + " for client " + str2 + " API Level " + i3);
                }
                CameraServiceWorker.this.updateActivityCount(str, i, i2, str2, i3);
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void notifyCameraSessionEvent(int i, String str) {
                if (Binder.getCallingUid() != 1047) {
                    Slog.e("CameraService_worker", "Calling UID: " + Binder.getCallingUid() + " doesn't match expected  camera service UID!");
                    return;
                }
                if (CameraServiceWorker.DEBUG) {
                    Slog.v("CameraService_worker", "event " + i + ", details : " + str);
                }
                synchronized (CameraServiceWorker.this.mLock) {
                    try {
                        switch (i) {
                            case 1:
                                CameraServiceWorker.this.insertDMALog("7503", str, null);
                                break;
                            case 2:
                                CameraServiceWorker.this.insertDMALog("7504", str, null);
                                break;
                            case 3:
                                CameraServiceWorker.this.insertDMALog("7505", str, null);
                                break;
                            case 4:
                                CameraServiceWorker.this.insertDMALog("7506", str, null);
                                break;
                            case 5:
                                CameraServiceWorker.this.insertDMALog("7507", str, null);
                                break;
                            case 6:
                                CameraServiceWorker.this.insertDMALog("7508", str, null);
                                break;
                            default:
                                Slog.e("CameraService_worker", "Non acceptable event type event " + i + ", details : " + str);
                                break;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            /* JADX WARN: Code restructure failed: missing block: B:20:0x005e, code lost:
            
                r6 = r4.pkgList;
             */
            /* JADX WARN: Code restructure failed: missing block: B:21:0x0060, code lost:
            
                if (r6 == null) goto L24;
             */
            /* JADX WARN: Code restructure failed: missing block: B:23:0x0064, code lost:
            
                if (r6.length != 1) goto L24;
             */
            /* JADX WARN: Code restructure failed: missing block: B:24:0x0066, code lost:
            
                android.util.Slog.i("CameraService_worker", "Package name = " + r4.pkgList[0]);
             */
            /* JADX WARN: Code restructure failed: missing block: B:26:0x0086, code lost:
            
                return r4.pkgList[0];
             */
            @Override // com.samsung.android.camera.ICameraServiceWorker
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public String queryPackageName(int i, int i2) {
                if (Binder.getCallingUid() != 1047) {
                    Slog.e("CameraService_worker", "Calling UID: " + Binder.getCallingUid() + " doesn't match expected  camera service UID!");
                    return "";
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) CameraServiceWorker.this.mContext.getSystemService("activity")).getRunningAppProcesses();
                    if (runningAppProcesses == null) {
                        runningAppProcesses = Collections.emptyList();
                    }
                    Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ActivityManager.RunningAppProcessInfo next = it.next();
                        if (next.pid == i && next.uid == i2) {
                            break;
                        }
                    }
                    return "";
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public IBinder acquireRequestInjector() {
                if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
                    Slog.e("CameraService_worker", "Only system user is allowed to call acquireRequestInjector");
                    throw new SecurityException("Only system user is allowed to call acquireRequestInjector");
                }
                return CameraServiceWorker.this.mRequestInjectorService;
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public int getDeviceOrientationForDeviceInjector(String str, int i) {
                if (Binder.getCallingUid() != 1047) {
                    Slog.e("CameraService_worker", "Calling UID: " + Binder.getCallingUid() + " doesn't match expected camera service UID!");
                    return 0;
                }
                TaskInfo taskInfo = CameraServiceWorker.getTaskInfo(str, i);
                if (taskInfo == null) {
                    return 0;
                }
                DisplayManager displayManager = (DisplayManager) CameraServiceWorker.this.mContext.getSystemService(DisplayManager.class);
                if (displayManager != null) {
                    Display display = displayManager.getDisplay(taskInfo.displayId);
                    if (display == null) {
                        Slog.e("CameraService_worker", "Invalid display id: " + taskInfo.displayId);
                        return 0;
                    }
                    display.getRotation();
                    synchronized (CameraServiceWorker.this.mOrientationLock) {
                        if (CameraServiceWorker.this.mOrientationEventListener == null) {
                            return 0;
                        }
                        if (taskInfo.displayId != 0) {
                            return 0;
                        }
                        return CameraServiceWorker.this.mOrientationEventListener.getLatestOrientation();
                    }
                }
                Slog.e("CameraService_worker", "Failed to query display manager!");
                return 0;
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void setDeviceOrientationListener(boolean z) {
                if (Binder.getCallingUid() != 1047) {
                    Slog.e("CameraService_worker", "Calling UID: " + Binder.getCallingUid() + " doesn't match expected camera service UID!");
                    return;
                }
                synchronized (CameraServiceWorker.this.mOrientationLock) {
                    if (CameraServiceWorker.this.mOrientationEventListener == null) {
                        return;
                    }
                    if (z) {
                        CameraServiceWorker.this.mOrientationEventListener.enable();
                    } else {
                        CameraServiceWorker.this.mOrientationEventListener.disable();
                    }
                }
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public boolean getDeviceInjectorOverride(String str, int i) {
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
                TaskInfo taskInfo = CameraServiceWorker.getTaskInfo(str, i);
                return (taskInfo == null || taskInfo.displayId == 0) ? false : true;
            }

            @Override // com.samsung.android.camera.ICameraServiceWorker
            public void storeLoggingData(int i, String str) {
                if (Logger.EnumC3085ID.values().length <= i || i < 0) {
                    Slog.e("CameraService_worker", "storeLoggingData : type has Inacceptable");
                } else {
                    Logger.log(Logger.EnumC3085ID.values()[i], str);
                }
            }

            @Override // android.os.Binder
            public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
                printWriter.println("CameraService_worker is up and running");
                printWriter.println("\tInterface v4 Hash:d9fcda68f4b826972b6433fa34b53cce41b2d3f6");
                printWriter.println("\tCamera is opened: " + CameraServiceWorker.this.mIsCameraOpened);
                Logger.dumpLog(Logger.EnumC3085ID.CAMERA_EVENT, printWriter);
                printWriter.println("\n\tFold Event");
                printWriter.println("\t\tLast fold state: " + CameraServiceWorker.this.mDeviceState);
                Logger.dumpLog(Logger.EnumC3085ID.FOLD_EVENT, printWriter);
                CameraServiceWorker.this.mShakeEventListener.dump(printWriter);
                CameraServiceWorker.this.mRequestInjectorService.dump(printWriter);
                CameraServiceWorker.this.mVtCameraProviderObserver.dump(printWriter);
                CameraServiceWorker.this.mScpmReceiver.dump(printWriter);
                Logger.dumpLog(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, printWriter);
                Logger.dumpLog(Logger.EnumC3085ID.POST_PROCESS_EVENT, printWriter);
                Logger.dumpLog(Logger.EnumC3085ID.CAMERA_APPLICATION_EVENT, printWriter);
                Logger.dumpLog(Logger.EnumC3085ID.DATABASE_EVENT, printWriter);
            }
        };
        this.mContext = context;
        ServiceThread serviceThread = new ServiceThread("CameraService_worker", -4, false);
        this.mHandlerThread = serviceThread;
        serviceThread.start();
        Handler handler = new Handler(serviceThread.getLooper(), this);
        this.mHandler = handler;
        this.mEnableSurveyMode = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
        if (DEBUG) {
            Slog.v("CameraService_worker", "enable survey mode is " + isEnableSurveyMode());
        }
        this.mShakeEventListener = new ShakeEventListener(this, context, handler);
        this.mRequestInjectorService = new RequestInjectorService(this, context, handler);
        this.mVtCameraProviderObserver = new VtCameraProviderObserver(this, context, handler);
        this.mScpmReceiver = new ScpmReceiver(this, context, serviceThread.getLooper());
        this.mBootCompleteReceiver = new BootCompleteReceiver();
        this.mCPRCommandReceiver = new CPRCommandReceiver();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what == 3) {
            synchronized (this.mLock) {
                notifyDeviceChangeRetryLocked(message.arg1);
            }
            return true;
        }
        Slog.e("CameraService_worker", "CameraServiceWorker error, invalid message: " + message.what);
        return true;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        Slog.i("CameraService_worker", "CameraServiceWorker is started.");
        publishBinderService("media.camera.worker", this.mCameraServiceWorker);
        publishLocalService(CameraServiceWorker.class, this);
    }

    public ICameraService getCameraService() {
        synchronized (this.mServiceLock) {
            if (this.mCameraServiceRaw == null) {
                IBinder binderService = getBinderService("media.camera");
                if (binderService == null) {
                    Slog.w("CameraService_worker", "Could not notify mediaserver, camera service not available.");
                    return null;
                }
                try {
                    binderService.linkToDeath(this, 0);
                    this.mCameraServiceRaw = ICameraService.Stub.asInterface(binderService);
                } catch (RemoteException unused) {
                    Slog.w("CameraService_worker", "Could not link to death of native camera service");
                    return null;
                }
            }
            return this.mCameraServiceRaw;
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        Slog.e("CameraService_worker", "Native camera service has died");
        synchronized (this.mLock) {
            synchronized (this.mServiceLock) {
                this.mCameraServiceRaw = null;
            }
            this.mActiveCameraUsage.clear();
            this.mOpenCameraUsage.clear();
            this.mIsCameraOpened = false;
            synchronized (this.mOrientationLock) {
                WorkerOrientationListener workerOrientationListener = this.mOrientationEventListener;
                if (workerOrientationListener != null) {
                    workerOrientationListener.disable();
                }
            }
            Logger.log(Logger.EnumC3085ID.CAMERA_EVENT, "Close all camera, camera service died");
            this.mFoldStateLatch.countDown();
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            this.mWindowManagerService = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            ((DeviceStateManager) this.mContext.getSystemService(DeviceStateManager.class)).registerCallback(new HandlerExecutor(this.mHandler), new DeviceStateListener());
            return;
        }
        if (i == 1000) {
            try {
                for (int i2 : WindowManagerGlobal.getWindowManagerService().registerDisplayWindowListener(this.mDisplayWindowListener)) {
                    this.mDisplayWindowListener.onDisplayAdded(i2);
                }
            } catch (RemoteException unused) {
                Slog.e("CameraService_worker", "Failed to register display window listener!");
            }
            synchronized (this.mOrientationLock) {
                this.mOrientationEventListener = new WorkerOrientationListener(this.mContext);
            }
            return;
        }
        if (i == 600) {
            if (this.mShakeEventListener.isSupported()) {
                Slog.i("CameraService_worker", "Shake event is supported. Register listener.");
                this.mShakeEventListener.start();
            } else {
                Slog.i("CameraService_worker", "Shake event is not supported.");
            }
            this.mRequestInjectorService.tryRegisterCameraOpenListener();
            this.mScpmReceiver.tryRegisterCameraOpenListener();
            this.mDeviceInjectorRequirementChecker = new DeviceInjectorRequirementChecker();
        }
    }

    public void notifyDeviceChange(long j) {
        synchronized (this.mLock) {
            if (this.mDeviceState != j) {
                this.mDeviceState = j;
                notifyDeviceChangeRetryLocked(30);
            } else {
                Slog.v("CameraService_worker", "Same device state has coming. skip");
            }
        }
    }

    public final void notifyDeviceChangeRetryLocked(int i) {
        if (((Boolean) notifyDeviceChangeLocked(this.mDeviceState).first).booleanValue()) {
            i = 0;
        }
        if (i <= 0) {
            return;
        }
        Slog.i("CameraService_worker", "Could not notify camera service of device state change, retrying...");
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(3, i - 1, 0, null), 20L);
    }

    public Pair notifyDeviceChangeLocked(long j) {
        return notifyDeviceChangeLocked(j, false);
    }

    public Pair notifyDeviceChangeLocked(long j, boolean z) {
        ICameraService cameraService = getCameraService();
        if (cameraService == null) {
            Pair pair = new Pair(Boolean.FALSE, "Could not notify mediaserver, camera service not available.");
            Slog.w("CameraService_worker", (String) pair.second);
            return pair;
        }
        Slog.i("CameraService_worker", String.format("NotifyDeviceStateChange 0x%X, sync(%b)", Long.valueOf(j), Boolean.valueOf(z)));
        try {
            if (z) {
                cameraService.notifyDeviceStateChangeSync(j);
            } else {
                cameraService.notifyDeviceStateChange(j);
            }
            return new Pair(Boolean.TRUE, String.format("NotifyDeviceStateChange success: 0x%X", Long.valueOf(j)));
        } catch (RemoteException e) {
            Pair pair2 = new Pair(Boolean.FALSE, "Could not notify device state change, remote exception: " + e);
            Slog.w("CameraService_worker", (String) pair2.second);
            return pair2;
        }
    }

    public final void updateActivityCount(String str, int i, int i2, String str2, int i3) {
        synchronized (this.mLock) {
            if (i == 0) {
                if (this.mBootCompleted && isEnableSurveyMode() && !isSamsungCameraApp(str2)) {
                    insertDMALog("7501", str2, Long.valueOf(i2));
                }
                this.mOpenCameraUsage.put(str, new CameraUsageEvent(i2, str2, i3));
                Logger.log(Logger.EnumC3085ID.CAMERA_EVENT, String.format("Open camera(%s) for %s", str, str2));
            } else if (i == 1) {
                this.mActiveCameraUsage.put(str, new CameraUsageEvent(i2, str2, i3));
                if (new File("/sys/class/camera/rear/cam_wifi_info").exists()) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    WifiManager wifiManager = (WifiManager) this.mContext.getSystemService("wifi");
                    String str3 = "00000";
                    if (wifiManager.isWifiEnabled()) {
                        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                        if (connectionInfo != null) {
                            str3 = String.format("%4d%1d", Integer.valueOf(connectionInfo.getFrequency()), Integer.valueOf(connectionInfo.getWifiStandard()));
                        } else if (DEBUG) {
                            Slog.e("CameraService_worker", "wifiInfo is null So, can not save wifi info.");
                        }
                    }
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream("/sys/class/camera/rear/cam_wifi_info");
                        try {
                            fileOutputStream.write(str3.getBytes(Charset.forName("UTF-8")));
                            fileOutputStream.close();
                        } catch (Throwable th) {
                            try {
                                fileOutputStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (Exception e) {
                        if (DEBUG) {
                            Slog.v("CameraService_worker", "Can't save wifi info : " + e);
                        }
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } else if (i == 2) {
                this.mActiveCameraUsage.remove(str);
            } else if (i == 3) {
                if (this.mBootCompleted && isEnableSurveyMode() && !isSamsungCameraApp(str2)) {
                    insertDMALog("7502", str2, Long.valueOf(i2));
                }
                this.mOpenCameraUsage.remove(str);
                Logger.log(Logger.EnumC3085ID.CAMERA_EVENT, String.format("Close camera(%s) for %s", str, str2));
                this.mActiveCameraUsage.remove(str);
            } else if (i != 100 && i != 101) {
                Slog.e("CameraService_worker", "Non acceptable state " + i);
            }
            this.mIsCameraOpened = this.mOpenCameraUsage.isEmpty() ? false : true;
        }
    }

    public void insertDMALog(String str, String str2, Long l) {
        if (DEBUG) {
            Slog.v("CameraService_worker", "insertDMALog: trackingId=4K3-399-1014897, feature=" + str + ", extra=" + str2 + ", value=" + l);
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
            Object[] objArr = new Object[3];
            objArr[0] = str2;
            objArr[1] = cameraFacingToString(l.intValue());
            objArr[2] = this.mDisplayId == 1 ? "SubDisplay" : "MainDisplay";
            hashMap.put("3PApp_Camera_Display", String.format(locale, "%s_%s_%s", objArr));
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

    public boolean isSamsungCameraApp(String str) {
        for (String str2 : SAMSUNG_CAMERA_PACKAGES) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCoverFlexRotateApp() {
        for (int i = 0; i < this.mOpenCameraUsage.size(); i++) {
            Iterator it = this.mScpmReceiver.getCoverFlexRotatePkgList().iterator();
            while (it.hasNext()) {
                if (((CameraUsageEvent) this.mOpenCameraUsage.valueAt(i)).mClientName.equals((String) it.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static TaskInfo getTaskInfo(String str, int i) {
        TaskInfo taskInfo;
        try {
            ParceledListSlice recentTasks = ActivityTaskManager.getService().getRecentTasks(2, 0, i);
            if (recentTasks != null && !recentTasks.getList().isEmpty()) {
                Iterator it = recentTasks.getList().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        taskInfo = null;
                        break;
                    }
                    ActivityManager.RecentTaskInfo recentTaskInfo = (ActivityManager.RecentTaskInfo) it.next();
                    ActivityInfo activityInfo = recentTaskInfo.topActivityInfo;
                    if (activityInfo != null && Objects.equals(str, activityInfo.packageName)) {
                        taskInfo = new TaskInfo();
                        taskInfo.frontTaskId = recentTaskInfo.taskId;
                        ActivityInfo activityInfo2 = recentTaskInfo.topActivityInfo;
                        taskInfo.isResizable = activityInfo2.resizeMode != 0;
                        taskInfo.displayId = recentTaskInfo.displayId;
                        taskInfo.userId = recentTaskInfo.userId;
                        taskInfo.isFixedOrientationLandscape = ActivityInfo.isFixedOrientationLandscape(activityInfo2.screenOrientation);
                        taskInfo.isFixedOrientationPortrait = ActivityInfo.isFixedOrientationPortrait(recentTaskInfo.topActivityInfo.screenOrientation);
                    }
                }
                if (taskInfo != null) {
                    return taskInfo;
                }
                Slog.e("CameraService_worker", "Recent tasks don't include camera client package name: " + str);
                return null;
            }
            Slog.e("CameraService_worker", "Recent task list is empty!");
            return null;
        } catch (RemoteException unused) {
            Slog.e("CameraService_worker", "Failed to query recent tasks!");
            return null;
        }
    }
}
