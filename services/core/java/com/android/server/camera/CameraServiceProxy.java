package com.android.server.camera;

import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.hardware.CameraExtensionSessionStats;
import android.hardware.CameraFeatureCombinationStats;
import android.hardware.CameraSessionStats;
import android.hardware.CameraStreamStats;
import android.hardware.ICameraService;
import android.hardware.ICameraServiceProxy;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.usb.UsbDevice;
import android.media.AudioManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserManager;
import android.stats.camera.nano.CameraProtos;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Range;
import android.util.Slog;
import android.view.IDisplayWindowListener;
import android.view.WindowManagerGlobal;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.internal.camera.flags.Flags;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CameraServiceProxy extends SystemService implements Handler.Callback, IBinder.DeathRecipient {
    public static final String[] NFC_SERVICE_ALLOW_IN_LOCK_SCREEN_LIST;
    public static final String[] NFC_SERVICE_ALLOW_LIST;
    public static final String[] REFRESH_RATE_CONTROL_BLOCK_LIST;
    public final ArrayMap mActiveCameraUsage;
    public final List mCameraEventHistory;
    public final AnonymousClass2 mCameraServiceProxy;
    public ICameraService mCameraServiceRaw;
    public final Context mContext;
    public int mDeviceState;
    public final DisplayWindowListener mDisplayWindowListener;
    public Set mEnabledCameraUsers;
    public final DeviceStateManager.FoldStateListener mFoldStateListener;
    public final Handler mHandler;
    public final AnonymousClass1 mIntentReceiver;
    public int mLastReportedDeviceState;
    public int mLastUser;
    public final Object mLock;
    public final ScheduledThreadPoolExecutor mLogWriterService;
    public final ArrayMap mNfcBlockCameraUsage;
    public final boolean mNotifyNfc;
    public UserManager mUserManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface CameraEvent {
        void logSelf();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CameraFeatureCombinationQueryEvent implements CameraEvent {
        public CameraFeatureCombinationStats mFeatureCombinationStats;

        /* JADX WARN: Removed duplicated region for block: B:12:0x0027  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0016  */
        @Override // com.android.server.camera.CameraServiceProxy.CameraEvent
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void logSelf() {
            /*
                r11 = this;
                android.hardware.CameraFeatureCombinationStats r0 = r11.mFeatureCombinationStats
                int r1 = r0.mStatus
                r2 = -1
                if (r1 == 0) goto L12
                r3 = 3
                if (r1 == r3) goto L10
                r3 = 10
                if (r1 == r3) goto L10
                r10 = r2
                goto L14
            L10:
                r10 = r3
                goto L14
            L12:
                r3 = 0
                goto L10
            L14:
                if (r10 != r2) goto L27
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "Unknown feature combination query status code: "
                r0.<init>(r1)
                android.hardware.CameraFeatureCombinationStats r11 = r11.mFeatureCombinationStats
                int r11 = r11.mStatus
                java.lang.String r1 = "CameraService_proxy"
                com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0.m(r0, r11, r1)
                return
            L27:
                int r5 = r0.mUid
                java.lang.String r6 = r0.mCameraId
                int r7 = r0.mQueryType
                long r8 = r0.mFeatureCombination
                r4 = 900(0x384, float:1.261E-42)
                com.android.internal.util.FrameworkStatsLog.write(r4, r5, r6, r7, r8, r10)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.camera.CameraServiceProxy.CameraFeatureCombinationQueryEvent.logSelf():void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CameraUsageEvent implements CameraEvent {
        public final int mAPILevel;
        public final int mAction;
        public final int mCameraFacing;
        public final String mCameraId;
        public final String mClientName;
        public boolean mDeviceError;
        public int mInternalReconfigure;
        public final boolean mIsNdk;
        public final int mLatencyMs;
        public final long mLogId;
        public final int mOperatingMode;
        public long mRequestCount;
        public long mResultErrorCount;
        public final int mSessionIndex;
        public List mStreamStats;
        public boolean mUsedUltraWide;
        public boolean mUsedZoomOverride;
        public String mUserTag;
        public int mVideoStabilizationMode;
        public CameraExtensionSessionStats mExtSessionStats = null;
        public long mDurationOrStartTimeMs = SystemClock.elapsedRealtime();
        public boolean mCompleted = false;
        public Range mMostRequestedFpsRange = new Range(0, 0);

        public CameraUsageEvent(String str, int i, String str2, int i2, boolean z, int i3, int i4, int i5, boolean z2, long j, int i6) {
            this.mCameraId = str;
            this.mCameraFacing = i;
            this.mClientName = str2;
            this.mAPILevel = i2;
            this.mIsNdk = z;
            this.mAction = i3;
            this.mLatencyMs = i4;
            this.mOperatingMode = i5;
            this.mDeviceError = z2;
            this.mLogId = j;
            this.mSessionIndex = i6;
        }

        @Override // com.android.server.camera.CameraServiceProxy.CameraEvent
        public final void logSelf() {
            int i;
            boolean z;
            int i2;
            int i3 = this.mCameraFacing;
            if (i3 == 0) {
                i = 1;
            } else if (i3 == 1) {
                i = 2;
            } else if (i3 != 2) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i3, "Unknown camera facing: ", "CameraService_proxy");
                i = 0;
            } else {
                i = 3;
            }
            CameraExtensionSessionStats cameraExtensionSessionStats = this.mExtSessionStats;
            int i4 = -1;
            if (cameraExtensionSessionStats != null) {
                int i5 = cameraExtensionSessionStats.type;
                if (i5 == 0) {
                    i4 = 0;
                } else if (i5 == 1) {
                    i4 = 1;
                } else if (i5 == 2) {
                    i4 = 2;
                } else if (i5 == 3) {
                    i4 = 3;
                } else if (i5 != 4) {
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("Unknown extension type: "), this.mExtSessionStats.type, "CameraService_proxy");
                } else {
                    i4 = 4;
                }
                boolean z2 = this.mExtSessionStats.isAdvanced;
                if (Flags.analytics24q3()) {
                    z = z2;
                    i2 = this.mExtSessionStats.captureFormat;
                } else {
                    z = z2;
                    i2 = 0;
                }
            } else {
                z = false;
                i2 = 0;
            }
            int i6 = i4;
            List list = this.mStreamStats;
            int size = list != null ? list.size() : 0;
            CameraProtos.CameraStreamProto[] cameraStreamProtoArr = new CameraProtos.CameraStreamProto[5];
            for (int i7 = 0; i7 < 5; i7++) {
                cameraStreamProtoArr[i7] = new CameraProtos.CameraStreamProto();
                if (i7 < size) {
                    CameraStreamStats cameraStreamStats = (CameraStreamStats) this.mStreamStats.get(i7);
                    cameraStreamProtoArr[i7].width = cameraStreamStats.getWidth();
                    cameraStreamProtoArr[i7].height = cameraStreamStats.getHeight();
                    cameraStreamProtoArr[i7].format = cameraStreamStats.getFormat();
                    cameraStreamProtoArr[i7].dataSpace = cameraStreamStats.getDataSpace();
                    cameraStreamProtoArr[i7].usage = cameraStreamStats.getUsage();
                    cameraStreamProtoArr[i7].requestCount = cameraStreamStats.getRequestCount();
                    cameraStreamProtoArr[i7].errorCount = cameraStreamStats.getErrorCount();
                    cameraStreamProtoArr[i7].firstCaptureLatencyMillis = cameraStreamStats.getStartLatencyMs();
                    cameraStreamProtoArr[i7].maxHalBuffers = cameraStreamStats.getMaxHalBuffers();
                    cameraStreamProtoArr[i7].maxAppBuffers = cameraStreamStats.getMaxAppBuffers();
                    cameraStreamProtoArr[i7].histogramType = cameraStreamStats.getHistogramType();
                    cameraStreamProtoArr[i7].histogramBins = cameraStreamStats.getHistogramBins();
                    cameraStreamProtoArr[i7].histogramCounts = cameraStreamStats.getHistogramCounts();
                    cameraStreamProtoArr[i7].dynamicRangeProfile = cameraStreamStats.getDynamicRangeProfile();
                    cameraStreamProtoArr[i7].streamUseCase = cameraStreamStats.getStreamUseCase();
                    cameraStreamProtoArr[i7].colorSpace = cameraStreamStats.getColorSpace();
                }
            }
            FrameworkStatsLog.write(FrameworkStatsLog.CAMERA_ACTION_EVENT, this.mCompleted ? this.mDurationOrStartTimeMs : 0L, this.mAPILevel, this.mClientName, i, this.mCameraId, this.mAction, this.mIsNdk, this.mLatencyMs, this.mOperatingMode, this.mInternalReconfigure, this.mRequestCount, this.mResultErrorCount, this.mDeviceError, size, MessageNano.toByteArray(cameraStreamProtoArr[0]), MessageNano.toByteArray(cameraStreamProtoArr[1]), MessageNano.toByteArray(cameraStreamProtoArr[2]), MessageNano.toByteArray(cameraStreamProtoArr[3]), MessageNano.toByteArray(cameraStreamProtoArr[4]), this.mUserTag, this.mVideoStabilizationMode, this.mLogId, this.mSessionIndex, i6, z, this.mUsedUltraWide, this.mUsedZoomOverride, ((Integer) this.mMostRequestedFpsRange.getLower()).intValue(), ((Integer) this.mMostRequestedFpsRange.getUpper()).intValue(), i2);
        }

        public final void markCompleted(int i, long j, long j2, boolean z, List list, String str, int i2, boolean z2, boolean z3, Range range, CameraExtensionSessionStats cameraExtensionSessionStats) {
            if (this.mCompleted) {
                return;
            }
            this.mCompleted = true;
            this.mDurationOrStartTimeMs = SystemClock.elapsedRealtime() - this.mDurationOrStartTimeMs;
            this.mInternalReconfigure = i;
            this.mRequestCount = j;
            this.mResultErrorCount = j2;
            this.mDeviceError = z;
            this.mStreamStats = list;
            this.mUserTag = str;
            this.mVideoStabilizationMode = i2;
            this.mUsedUltraWide = z2;
            this.mUsedZoomOverride = z3;
            this.mExtSessionStats = cameraExtensionSessionStats;
            this.mMostRequestedFpsRange = range;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayWindowListener extends IDisplayWindowListener.Stub {
        public DisplayWindowListener() {
        }

        public final void onDisplayAdded(int i) {
        }

        public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
            ICameraService cameraServiceRawLocked = CameraServiceProxy.this.getCameraServiceRawLocked();
            if (cameraServiceRawLocked == null) {
                return;
            }
            try {
                cameraServiceRawLocked.notifyDisplayConfigurationChange();
            } catch (RemoteException e) {
                AccountManagerService$$ExternalSyntheticOutline0.m("Could not notify cameraserver, remote exception: ", e, "CameraService_proxy");
            }
        }

        public final void onDisplayRemoved(int i) {
        }

        public final void onFixedRotationFinished(int i) {
        }

        public final void onFixedRotationStarted(int i, int i2) {
        }

        public final void onKeepClearAreasChanged(int i, List list, List list2) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EventWriterTask implements Runnable {
        public List mEventList;

        @Override // java.lang.Runnable
        public final void run() {
            List list = this.mEventList;
            if (list != null) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ((CameraEvent) it.next()).logSelf();
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException unused) {
                    }
                }
                this.mEventList.clear();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TaskInfo {
        public int displayId;
        public boolean isFixedOrientationLandscape;
        public boolean isFixedOrientationPortrait;
        public boolean isResizeable;
        public int userId;
    }

    static {
        new Binder();
        NFC_SERVICE_ALLOW_LIST = new String[]{"com.samsung.android.smartface", "com.samsung.adaptivebrightnessgo", "com.samsung.android.visionintelligence", "com.samsung.android.visionarapps", "com.samsung.android.sead"};
        NFC_SERVICE_ALLOW_IN_LOCK_SCREEN_LIST = new String[]{"client.pid<"};
        REFRESH_RATE_CONTROL_BLOCK_LIST = new String[]{"com.samsung.android.smartface", "com.samsung.adaptivebrightnessgo", "client.pid<", "com.samsung.android.sead"};
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.server.camera.CameraServiceProxy$1] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.server.camera.CameraServiceProxy$2] */
    public CameraServiceProxy(Context context) {
        super(context);
        this.mLock = new Object();
        this.mActiveCameraUsage = new ArrayMap();
        this.mNfcBlockCameraUsage = new ArrayMap();
        this.mCameraEventHistory = new ArrayList();
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        this.mLogWriterService = scheduledThreadPoolExecutor;
        this.mDisplayWindowListener = new DisplayWindowListener();
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.camera.CameraServiceProxy.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action == null) {
                    return;
                }
                switch (action) {
                    case "android.hardware.usb.action.USB_DEVICE_ATTACHED":
                    case "android.hardware.usb.action.USB_DEVICE_DETACHED":
                        synchronized (CameraServiceProxy.this.mLock) {
                            try {
                                UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device", UsbDevice.class);
                                if (usbDevice != null) {
                                    CameraServiceProxy cameraServiceProxy = CameraServiceProxy.this;
                                    boolean equals = action.equals("android.hardware.usb.action.USB_DEVICE_ATTACHED");
                                    cameraServiceProxy.getClass();
                                    if (usbDevice.getHasVideoCapture()) {
                                        if (cameraServiceProxy.getCameraServiceRawLocked() == null) {
                                            Slog.w("CameraService_proxy", "Could not notify cameraserver, camera service not available.");
                                        } else {
                                            cameraServiceProxy.mCameraServiceRaw.notifySystemEvent(equals ? 2 : 3, new int[]{usbDevice.getDeviceId()});
                                        }
                                    }
                                }
                            } catch (RemoteException e) {
                                AccountManagerService$$ExternalSyntheticOutline0.m("Could not notify cameraserver, remote exception: ", e, "CameraService_proxy");
                            } finally {
                            }
                        }
                        return;
                    case "android.intent.action.USER_REMOVED":
                    case "android.intent.action.MANAGED_PROFILE_ADDED":
                    case "android.intent.action.USER_INFO_CHANGED":
                    case "android.intent.action.MANAGED_PROFILE_REMOVED":
                    case "android.intent.action.USER_ADDED":
                        synchronized (CameraServiceProxy.this.mLock) {
                            try {
                                CameraServiceProxy cameraServiceProxy2 = CameraServiceProxy.this;
                                if (cameraServiceProxy2.mEnabledCameraUsers == null) {
                                    return;
                                }
                                cameraServiceProxy2.switchUserLocked(cameraServiceProxy2.mLastUser);
                                return;
                            } finally {
                            }
                        }
                    default:
                        return;
                }
            }
        };
        this.mCameraServiceProxy = new ICameraServiceProxy.Stub() { // from class: com.android.server.camera.CameraServiceProxy.2

            /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
            /* renamed from: com.android.server.camera.CameraServiceProxy$2$CSPShellCmd */
            public final class CSPShellCmd extends ShellCommand {
                public final CameraServiceProxy mCameraServiceProxy;

                public CSPShellCmd(CameraServiceProxy cameraServiceProxy) {
                    this.mCameraServiceProxy = cameraServiceProxy;
                }

                public final int onCommand(String str) {
                    int size;
                    if (str == null) {
                        return handleDefaultCommands(str);
                    }
                    PrintWriter outPrintWriter = getOutPrintWriter();
                    try {
                        String replace = str.replace('-', '_');
                        if (replace.hashCode() == -1224390204 && replace.equals("dump_events")) {
                            CameraServiceProxy cameraServiceProxy = this.mCameraServiceProxy;
                            synchronized (cameraServiceProxy.mLock) {
                                size = ((ArrayList) cameraServiceProxy.mCameraEventHistory).size();
                            }
                            this.mCameraServiceProxy.dumpCameraEvents();
                            outPrintWriter.println("Camera usage events dumped: " + size);
                            return 0;
                        }
                        return handleDefaultCommands(str);
                    } catch (Exception e) {
                        Slog.e("CameraService_proxy", "Error running shell command", e);
                        return 1;
                    }
                }

                public final void onHelp() {
                    getOutPrintWriter().println("usage: cmd media.camera.proxy SUBCMD [args]\n\nSUBCMDs:\n    dump_events: Write out all collected camera usage events to statsd.\n        Does not print to terminal.\n    help: You're reading it.\n");
                }
            }

            public final int getAutoframingOverride(String str) {
                return 0;
            }

            /* JADX WARN: Code restructure failed: missing block: B:84:0x0128, code lost:
            
                if (r9.getPackageManager().getPackageInfo(r10, 0).applicationInfo.targetSdkVersion <= 23) goto L55;
             */
            /* JADX WARN: Removed duplicated region for block: B:57:0x0152  */
            /* JADX WARN: Removed duplicated region for block: B:65:0x017f  */
            /* JADX WARN: Removed duplicated region for block: B:77:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:80:0x0164  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final int getRotateAndCropOverride(java.lang.String r10, int r11, int r12) {
                /*
                    Method dump skipped, instructions count: 418
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.camera.CameraServiceProxy.AnonymousClass2.getRotateAndCropOverride(java.lang.String, int, int):int");
            }

            public final boolean isCameraDisabled(int i) {
                if (Binder.getCallingUid() != 1047) {
                    Slog.e("CameraService_proxy", "Calling UID: " + Binder.getCallingUid() + " doesn't match expected camera service UID!");
                    return false;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    DevicePolicyManager devicePolicyManager = (DevicePolicyManager) CameraServiceProxy.this.mContext.getSystemService(DevicePolicyManager.class);
                    if (devicePolicyManager != null) {
                        return devicePolicyManager.getCameraDisabled(null, i);
                    }
                    Slog.e("CameraService_proxy", "Failed to get the device policy manager service");
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public final void notifyCameraState(CameraSessionStats cameraSessionStats) {
                boolean z;
                Object obj;
                boolean z2;
                int i;
                if (Binder.getCallingUid() != 1047) {
                    Slog.e("CameraService_proxy", "Calling UID: " + Binder.getCallingUid() + " doesn't match expected  camera service UID!");
                    return;
                }
                cameraSessionStats.getNewCameraState();
                String[] strArr = CameraServiceProxy.NFC_SERVICE_ALLOW_LIST;
                cameraSessionStats.getFacing();
                CameraServiceProxy cameraServiceProxy = CameraServiceProxy.this;
                cameraServiceProxy.getClass();
                String cameraId = cameraSessionStats.getCameraId();
                int newCameraState = cameraSessionStats.getNewCameraState();
                int facing = cameraSessionStats.getFacing();
                String clientName = cameraSessionStats.getClientName();
                int apiLevel = cameraSessionStats.getApiLevel();
                boolean isNdk = cameraSessionStats.isNdk();
                int sessionType = cameraSessionStats.getSessionType();
                int internalReconfigureCount = cameraSessionStats.getInternalReconfigureCount();
                int latencyMs = cameraSessionStats.getLatencyMs();
                long requestCount = cameraSessionStats.getRequestCount();
                long resultErrorCount = cameraSessionStats.getResultErrorCount();
                boolean deviceErrorFlag = cameraSessionStats.getDeviceErrorFlag();
                List streamStats = cameraSessionStats.getStreamStats();
                String userTag = cameraSessionStats.getUserTag();
                int videoStabilizationMode = cameraSessionStats.getVideoStabilizationMode();
                boolean usedUltraWide = Flags.logUltrawideUsage() ? cameraSessionStats.getUsedUltraWide() : false;
                boolean usedZoomOverride = Flags.logZoomOverrideUsage() ? cameraSessionStats.getUsedZoomOverride() : false;
                long logId = cameraSessionStats.getLogId();
                int sessionIndex = cameraSessionStats.getSessionIndex();
                CameraExtensionSessionStats extensionSessionStats = cameraSessionStats.getExtensionSessionStats();
                Range mostRequestedFpsRange = Flags.analytics24q3() ? cameraSessionStats.getMostRequestedFpsRange() : new Range(0, 0);
                Object obj2 = cameraServiceProxy.mLock;
                synchronized (obj2) {
                    try {
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        boolean isEmpty = cameraServiceProxy.mNfcBlockCameraUsage.isEmpty();
                        if (newCameraState == 0) {
                            z = isEmpty;
                            obj = obj2;
                            AudioManager audioManager = (AudioManager) cameraServiceProxy.getContext().getSystemService(AudioManager.class);
                            if (audioManager != null) {
                                audioManager.setParameters("cameraFacing=".concat(facing == 0 ? "back" : "front"));
                            }
                            CameraUsageEvent cameraUsageEvent = new CameraUsageEvent(cameraId, facing, clientName, apiLevel, isNdk, 1, latencyMs, sessionType, deviceErrorFlag, logId, sessionIndex);
                            ((ArrayList) cameraServiceProxy.mCameraEventHistory).add(cameraUsageEvent);
                            if (cameraServiceProxy.canNotifyToNfcService(clientName)) {
                                cameraServiceProxy.mNfcBlockCameraUsage.put(cameraId, cameraUsageEvent);
                            }
                        } else if (newCameraState != 1) {
                            if (newCameraState != 2) {
                                if (newCameraState != 3) {
                                    z = isEmpty;
                                    obj = obj2;
                                } else {
                                    cameraServiceProxy.mNfcBlockCameraUsage.remove(cameraId);
                                }
                            }
                            CameraUsageEvent cameraUsageEvent2 = (CameraUsageEvent) cameraServiceProxy.mActiveCameraUsage.remove(cameraId);
                            if (cameraUsageEvent2 != null) {
                                cameraUsageEvent2.markCompleted(internalReconfigureCount, requestCount, resultErrorCount, deviceErrorFlag, streamStats, userTag, videoStabilizationMode, usedUltraWide, usedZoomOverride, mostRequestedFpsRange, extensionSessionStats);
                                ((ArrayList) cameraServiceProxy.mCameraEventHistory).add(cameraUsageEvent2);
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= cameraServiceProxy.mActiveCameraUsage.size()) {
                                        if (clientName != null) {
                                            for (String str : CameraServiceProxy.REFRESH_RATE_CONTROL_BLOCK_LIST) {
                                                if (clientName.startsWith(str)) {
                                                    break;
                                                }
                                            }
                                        }
                                        WindowManagerInternal windowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
                                        Slog.i("CameraService_proxy", "wmi.removeRefreshRateRangeForPackage clientName = " + clientName);
                                        windowManagerInternal.removeRefreshRateRangeForPackage(clientName);
                                    } else if (((CameraUsageEvent) cameraServiceProxy.mActiveCameraUsage.valueAt(i2)).mClientName.equals(clientName)) {
                                        break;
                                    } else {
                                        i2++;
                                    }
                                }
                                i = 3;
                                deviceErrorFlag = false;
                            } else {
                                i = 3;
                            }
                            if (newCameraState == i) {
                                obj = obj2;
                                z = isEmpty;
                                ((ArrayList) cameraServiceProxy.mCameraEventHistory).add(new CameraUsageEvent(cameraId, facing, clientName, apiLevel, isNdk, 2, latencyMs, sessionType, deviceErrorFlag, logId, sessionIndex));
                            } else {
                                z = isEmpty;
                                obj = obj2;
                            }
                            if (((ArrayList) cameraServiceProxy.mCameraEventHistory).size() > 20) {
                                cameraServiceProxy.dumpCameraEvents();
                            }
                        } else {
                            z = isEmpty;
                            obj = obj2;
                            int i3 = 0;
                            while (true) {
                                if (i3 >= cameraServiceProxy.mActiveCameraUsage.size()) {
                                    if (clientName != null) {
                                        for (String str2 : CameraServiceProxy.REFRESH_RATE_CONTROL_BLOCK_LIST) {
                                            if (clientName.startsWith(str2)) {
                                                break;
                                            }
                                        }
                                    }
                                    WindowManagerInternal windowManagerInternal2 = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
                                    float max = Math.max(Math.min(cameraSessionStats.getMaxPreviewFps(), 60.0f), 30.0f);
                                    Slog.i("CameraService_proxy", "wmi.addRefreshRateRangeForPackage minFPS = " + max + ", maxFPS = 60.0, clientName = " + clientName);
                                    windowManagerInternal2.addRefreshRateRangeForPackage(clientName, max, 60.0f);
                                } else if (((CameraUsageEvent) cameraServiceProxy.mActiveCameraUsage.valueAt(i3)).mClientName.equals(clientName)) {
                                    break;
                                } else {
                                    i3++;
                                }
                            }
                            CameraUsageEvent cameraUsageEvent3 = (CameraUsageEvent) cameraServiceProxy.mActiveCameraUsage.put(cameraId, new CameraUsageEvent(cameraId, facing, clientName, apiLevel, isNdk, 3, latencyMs, sessionType, deviceErrorFlag, logId, sessionIndex));
                            if (cameraUsageEvent3 != null) {
                                Slog.w("CameraService_proxy", "Camera " + cameraId + " was already marked as active");
                                cameraUsageEvent3.markCompleted(0, 0L, 0L, false, streamStats, "", -1, false, false, new Range(0, 0), new CameraExtensionSessionStats());
                                ((ArrayList) cameraServiceProxy.mCameraEventHistory).add(cameraUsageEvent3);
                            }
                        }
                        boolean isEmpty2 = cameraServiceProxy.mNfcBlockCameraUsage.isEmpty();
                        if (cameraServiceProxy.mNotifyNfc && (z2 = z) != isEmpty2) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Notify nfc service : camera open was(");
                            sb.append(!z2);
                            sb.append(") -> now(");
                            sb.append(true ^ isEmpty2);
                            sb.append(") = polling(");
                            sb.append(isEmpty2);
                            sb.append(")");
                            Slog.i("CameraService_proxy", sb.toString());
                            cameraServiceProxy.notifyNfcService(isEmpty2);
                        }
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                throw th;
            }

            public final void notifyFeatureCombinationStats(CameraFeatureCombinationStats cameraFeatureCombinationStats) {
                if (Flags.analytics24q3()) {
                    if (Binder.getCallingUid() != 1047) {
                        Slog.e("CameraService_proxy", "Calling UID: " + Binder.getCallingUid() + " doesn't match expected  camera service UID!");
                        return;
                    }
                    CameraServiceProxy cameraServiceProxy = CameraServiceProxy.this;
                    synchronized (cameraServiceProxy.mLock) {
                        try {
                            CameraFeatureCombinationQueryEvent cameraFeatureCombinationQueryEvent = new CameraFeatureCombinationQueryEvent();
                            cameraFeatureCombinationQueryEvent.mFeatureCombinationStats = cameraFeatureCombinationStats;
                            ((ArrayList) cameraServiceProxy.mCameraEventHistory).add(cameraFeatureCombinationQueryEvent);
                            if (((ArrayList) cameraServiceProxy.mCameraEventHistory).size() > 20) {
                                cameraServiceProxy.dumpCameraEvents();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
                new CSPShellCmd(CameraServiceProxy.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }

            public final void pingForUserUpdate() {
                if (Binder.getCallingUid() != 1047) {
                    Slog.e("CameraService_proxy", "Calling UID: " + Binder.getCallingUid() + " doesn't match expected  camera service UID!");
                    return;
                }
                CameraServiceProxy cameraServiceProxy = CameraServiceProxy.this;
                synchronized (cameraServiceProxy.mLock) {
                    cameraServiceProxy.notifySwitchWithRetriesLocked(60);
                }
                CameraServiceProxy cameraServiceProxy2 = CameraServiceProxy.this;
                synchronized (cameraServiceProxy2.mLock) {
                    cameraServiceProxy2.notifyDeviceStateWithRetriesLocked(60);
                }
            }
        };
        this.mContext = context;
        this.mHandler = new Handler(Watchdog$$ExternalSyntheticOutline0.m(-4, "CameraService_proxy", false).getLooper(), this);
        this.mNotifyNfc = SystemProperties.getInt("ro.camera.notify_nfc", 0) > 0;
        scheduledThreadPoolExecutor.setKeepAliveTime(1L, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.allowCoreThreadTimeOut(true);
        this.mFoldStateListener = new DeviceStateManager.FoldStateListener(context, new Consumer() { // from class: com.android.server.camera.CameraServiceProxy$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CameraServiceProxy cameraServiceProxy = CameraServiceProxy.this;
                cameraServiceProxy.getClass();
                if (((Boolean) obj).booleanValue()) {
                    synchronized (cameraServiceProxy.mLock) {
                        try {
                            cameraServiceProxy.mHandler.removeMessages(2);
                            int i = cameraServiceProxy.mDeviceState | 4;
                            cameraServiceProxy.mDeviceState = i;
                            if (i != cameraServiceProxy.mLastReportedDeviceState) {
                                cameraServiceProxy.notifyDeviceStateWithRetriesLocked(60);
                            }
                        } finally {
                        }
                    }
                    return;
                }
                synchronized (cameraServiceProxy.mLock) {
                    try {
                        cameraServiceProxy.mHandler.removeMessages(2);
                        int i2 = cameraServiceProxy.mDeviceState & (-5);
                        cameraServiceProxy.mDeviceState = i2;
                        if (i2 != cameraServiceProxy.mLastReportedDeviceState) {
                            cameraServiceProxy.notifyDeviceStateWithRetriesLocked(60);
                        }
                    } finally {
                    }
                }
            }
        });
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.e("CameraService_proxy", "Native camera service has died");
        synchronized (this.mLock) {
            try {
                this.mCameraServiceRaw = null;
                boolean isEmpty = this.mNfcBlockCameraUsage.isEmpty();
                this.mNfcBlockCameraUsage.clear();
                this.mActiveCameraUsage.clear();
                if (this.mNotifyNfc && !isEmpty) {
                    Slog.i("CameraService_proxy", "Notify nfc service : camera service has died. start polling.");
                    notifyNfcService(true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean canNotifyToNfcService(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (str == null) {
            return true;
        }
        try {
            for (String str2 : NFC_SERVICE_ALLOW_LIST) {
                if (str2.equals(str)) {
                    return false;
                }
            }
            if (((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceLocked(-2)) {
                for (String str3 : NFC_SERVICE_ALLOW_IN_LOCK_SCREEN_LIST) {
                    if (str.startsWith(str3)) {
                        return false;
                    }
                }
            }
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void dumpCameraEvents() {
        synchronized (this.mLock) {
            Collections.shuffle(this.mCameraEventHistory);
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = this.mLogWriterService;
            ArrayList arrayList = new ArrayList(this.mCameraEventHistory);
            EventWriterTask eventWriterTask = new EventWriterTask();
            eventWriterTask.mEventList = arrayList;
            scheduledThreadPoolExecutor.execute(eventWriterTask);
            ((ArrayList) this.mCameraEventHistory).clear();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            CameraStatsJobService.schedule(this.mContext);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ICameraService getCameraServiceRawLocked() {
        if (this.mCameraServiceRaw == null) {
            IBinder binderService = getBinderService("media.camera");
            if (binderService == null) {
                return null;
            }
            try {
                binderService.linkToDeath(this, 0);
                this.mCameraServiceRaw = ICameraService.Stub.asInterface(binderService);
            } catch (RemoteException unused) {
                Slog.w("CameraService_proxy", "Could not link to death of native camera service");
                return null;
            }
        }
        return this.mCameraServiceRaw;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            int i2 = message.arg1;
            synchronized (this.mLock) {
                notifySwitchWithRetriesLocked(i2);
            }
        } else if (i != 2) {
            VaultKeeperService$$ExternalSyntheticOutline0.m(new StringBuilder("CameraServiceProxy error, invalid message: "), message.what, "CameraService_proxy");
        } else {
            int i3 = message.arg1;
            synchronized (this.mLock) {
                notifyDeviceStateWithRetriesLocked(i3);
            }
        }
        return true;
    }

    public final void notifyDeviceStateWithRetriesLocked(int i) {
        int i2 = this.mDeviceState;
        if (getCameraServiceRawLocked() != null) {
            this.mLastReportedDeviceState = i2;
            return;
        }
        Slog.w("CameraService_proxy", "Could not notify cameraserver, camera service not available.");
        if (i <= 0) {
            return;
        }
        Slog.i("CameraService_proxy", "Could not notify camera service of device state change, retrying...");
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(2, i - 1, 0, null), 20L);
    }

    public final void notifyNfcService(boolean z) {
        NfcManager nfcManager = (NfcManager) this.mContext.getSystemService(NfcManager.class);
        if (nfcManager == null) {
            Slog.w("CameraService_proxy", "Could not connect to NFC service to notify it of camera state");
            return;
        }
        NfcAdapter defaultAdapter = nfcManager.getDefaultAdapter();
        if (defaultAdapter == null) {
            Slog.w("CameraService_proxy", "Could not connect to NFC service to notify it of camera state");
        } else {
            defaultAdapter.setReaderModePollingEnabled(z);
        }
    }

    public final void notifySwitchWithRetriesLocked(int i) {
        Set set = this.mEnabledCameraUsers;
        if (set == null) {
            return;
        }
        if (getCameraServiceRawLocked() == null) {
            Slog.w("CameraService_proxy", "Could not notify cameraserver, camera service not available.");
        } else {
            try {
                ICameraService iCameraService = this.mCameraServiceRaw;
                int[] iArr = new int[set.size()];
                Iterator it = set.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    iArr[i2] = ((Integer) it.next()).intValue();
                    i2++;
                }
                iCameraService.notifySystemEvent(1, iArr);
                i = 0;
            } catch (RemoteException e) {
                AccountManagerService$$ExternalSyntheticOutline0.m("Could not notify cameraserver, remote exception: ", e, "CameraService_proxy");
            }
        }
        if (i <= 0) {
            return;
        }
        Slog.i("CameraService_proxy", "Could not notify camera service of user switch, retrying...");
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(1, i - 1, 0, null), 20L);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            CameraStatsJobService.schedule(this.mContext);
            try {
                for (int i2 : WindowManagerGlobal.getWindowManagerService().registerDisplayWindowListener(this.mDisplayWindowListener)) {
                    this.mDisplayWindowListener.getClass();
                }
            } catch (RemoteException unused) {
                Log.e("CameraService_proxy", "Failed to register display window listener!");
            }
            ((DeviceStateManager) this.mContext.getSystemService(DeviceStateManager.class)).registerCallback(new HandlerExecutor(this.mHandler), this.mFoldStateListener);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Slog.i("CameraService_proxy", "CameraServiceProxy is started.");
        UserManager userManager = UserManager.get(this.mContext);
        this.mUserManager = userManager;
        if (userManager == null) {
            throw new IllegalStateException("UserManagerService must start before CameraServiceProxy!");
        }
        IntentFilter m = VcnManagementService$$ExternalSyntheticOutline0.m("android.intent.action.USER_ADDED", "android.intent.action.USER_REMOVED", "android.intent.action.USER_INFO_CHANGED", DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED, DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED);
        m.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        m.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        this.mContext.registerReceiver(this.mIntentReceiver, m);
        publishBinderService("media.camera.proxy", this.mCameraServiceProxy);
        publishLocalService(CameraServiceProxy.class, this);
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabledCameraUsers == null) {
                    switchUserLocked(targetUser.getUserIdentifier());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        synchronized (this.mLock) {
            switchUserLocked(targetUser2.getUserIdentifier());
        }
    }

    public final void switchUserLocked(int i) {
        int[] enabledProfileIds = this.mUserManager.getEnabledProfileIds(i);
        ArraySet arraySet = new ArraySet(enabledProfileIds.length);
        for (int i2 : enabledProfileIds) {
            arraySet.add(Integer.valueOf(i2));
        }
        if (Flags.cameraHsumPermission() && UserManager.isHeadlessSystemUserMode() && this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            arraySet.add(0);
        }
        this.mLastUser = i;
        Set set = this.mEnabledCameraUsers;
        if (set == null || !set.equals(arraySet)) {
            this.mEnabledCameraUsers = arraySet;
            notifySwitchWithRetriesLocked(60);
        }
    }
}
