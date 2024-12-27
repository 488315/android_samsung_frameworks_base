package com.android.server.devicestate;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.IProcessObserver;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateInfo;
import android.hardware.devicestate.DeviceStateManagerInternal;
import android.hardware.devicestate.IDeviceStateManager;
import android.hardware.devicestate.IDeviceStateManagerCallback;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.statusbar.IStatusBar;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.DisplayThread;
import com.android.server.DssController$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.policy.DeviceStatePolicyImpl;
import com.android.server.policy.DeviceStateProviderImpl;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowProcessController;

import com.samsung.android.rune.CoreRune;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DeviceStateManagerService extends SystemService {
    public static final DeviceState INVALID_DEVICE_STATE =
            new DeviceState(new DeviceState.Configuration.Builder(-1, "INVALID").build());
    public Optional mActiveBaseStateOverride;
    public Optional mActiveOverride;
    public ActivityTaskManagerInternal mActivityTaskManagerInternal;
    public Optional mBaseState;
    public final BinderService mBinderService;
    public Optional mCommittedState;
    public final DeviceStateNotificationController mDeviceStateNotificationController;
    public final DeviceStatePolicy mDeviceStatePolicy;
    public final DeviceStateProviderListener mDeviceStateProviderListener;
    public final SparseArray mDeviceStates;
    public final Set mDeviceStatesAvailableForAppRequests;
    public boolean mDisplayEnabled;
    public Set mFoldedDeviceStates;
    public final Handler mHandler;
    public boolean mIsPolicyWaitingForState;
    public boolean mIsWirelessPowerSharing;
    public final Object mLock;
    public final OverrideRequestController mOverrideRequestController;
    ActivityTaskManagerInternal.ScreenObserver mOverrideRequestScreenObserver;
    public Optional mPendingState;
    final IProcessObserver mProcessObserver;
    public final SparseArray mProcessRecords;
    public OverrideRequest mRearDisplayPendingOverrideRequest;
    public DeviceState mRearDisplayState;
    public final SystemPropertySetter mSystemPropertySetter;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderService extends IDeviceStateManager.Stub {
        public BinderService() {}

        public final void cancelBaseStateOverride() {
            int callingPid = Binder.getCallingPid();
            DeviceStateManagerService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.CONTROL_DEVICE_STATE",
                            "Permission required to control base state of device.");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceStateManagerService.m426$$Nest$mcancelBaseStateOverrideInternal(
                        DeviceStateManagerService.this, callingPid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void cancelStateRequest() {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            DeviceStateManagerService deviceStateManagerService = DeviceStateManagerService.this;
            WindowProcessController windowProcessController =
                    ActivityTaskManagerService.this.mTopApp;
            boolean z =
                    windowProcessController != null && windowProcessController.mPid == callingPid;
            boolean isForegroundApp =
                    DeviceStateManagerService.isForegroundApp(callingPid, callingUid);
            if (!z || !isForegroundApp) {
                deviceStateManagerService
                        .getContext()
                        .enforceCallingOrSelfPermission(
                                "android.permission.CONTROL_DEVICE_STATE",
                                "Permission required to request device state, or the call must come"
                                    + " from the top app.");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceStateManagerService.m427$$Nest$mcancelStateRequestInternal(
                        DeviceStateManagerService.this, callingPid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    DeviceStateManagerService.this.getContext(),
                    "DeviceStateManagerService",
                    printWriter)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    DeviceStateManagerService.m428$$Nest$mdumpInternal(
                            DeviceStateManagerService.this, printWriter);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final DeviceStateInfo getDeviceStateInfo() {
            DeviceStateInfo deviceStateInfoLocked;
            synchronized (DeviceStateManagerService.this.mLock) {
                deviceStateInfoLocked = DeviceStateManagerService.this.getDeviceStateInfoLocked();
            }
            return deviceStateInfoLocked;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new DeviceStateManagerShellCommand(DeviceStateManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void onStateRequestOverlayDismissed(boolean z) {
            onStateRequestOverlayDismissed_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceStateManagerService.this.onStateRequestOverlayDismissedInternal(z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerCallback(
                IDeviceStateManagerCallback iDeviceStateManagerCallback) {
            if (iDeviceStateManagerCallback == null) {
                throw new IllegalArgumentException("Device state callback must not be null.");
            }
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceStateManagerService.m429$$Nest$mregisterProcess(
                        DeviceStateManagerService.this, callingPid, iDeviceStateManagerCallback);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestBaseStateOverride(IBinder iBinder, int i, int i2) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            DeviceStateManagerService.this
                    .getContext()
                    .enforceCallingOrSelfPermission(
                            "android.permission.CONTROL_DEVICE_STATE",
                            "Permission required to control base state of device.");
            if (iBinder == null) {
                throw new IllegalArgumentException("Request token must not be null.");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceStateManagerService.m430$$Nest$mrequestBaseStateOverrideInternal(
                        DeviceStateManagerService.this, i, i2, callingPid, callingUid, iBinder);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestState(IBinder iBinder, int i, int i2) {
            boolean contains;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            DeviceStateManagerService deviceStateManagerService = DeviceStateManagerService.this;
            WindowProcessController windowProcessController =
                    ActivityTaskManagerService.this.mTopApp;
            boolean z =
                    windowProcessController != null && windowProcessController.mPid == callingPid;
            boolean isForegroundApp =
                    DeviceStateManagerService.isForegroundApp(callingPid, callingUid);
            synchronized (deviceStateManagerService.mLock) {
                contains =
                        ((HashSet) deviceStateManagerService.mDeviceStatesAvailableForAppRequests)
                                .contains(Integer.valueOf(i));
            }
            if (!z || !isForegroundApp || !contains) {
                deviceStateManagerService
                        .getContext()
                        .enforceCallingOrSelfPermission(
                                "android.permission.CONTROL_DEVICE_STATE",
                                "Permission required to request device state, or the call must come"
                                    + " from the top app and be a device state that is available"
                                    + " for apps to request.");
            }
            if (iBinder == null) {
                throw new IllegalArgumentException("Request token must not be null.");
            }
            boolean z2 =
                    DeviceStateManagerService.this
                                    .getContext()
                                    .checkCallingOrSelfPermission(
                                            "android.permission.CONTROL_DEVICE_STATE")
                            == 0;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                DeviceStateManagerService.m431$$Nest$mrequestStateInternal(
                        DeviceStateManagerService.this, i, i2, callingPid, callingUid, iBinder, z2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceStateProviderListener {
        public int mCurrentBaseState;

        public DeviceStateProviderListener() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends DeviceStateManagerInternal {
        public LocalService() {}

        public final void displayEnabled() {
            synchronized (DeviceStateManagerService.this.mLock) {
                DeviceStateManagerService deviceStateManagerService =
                        DeviceStateManagerService.this;
                deviceStateManagerService.mDisplayEnabled = true;
                deviceStateManagerService.updatePendingStateLocked();
            }
            if (CoreRune.FW_FLEXIBLE_CONTROL_FOLDING_SENSOR) {
                DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(
                        DeviceStateManagerService.this.mDeviceStatePolicy);
                boolean z = DeviceStateManagerService.this.mDisplayEnabled;
                throw null;
            }
            DeviceStateManagerService deviceStateManagerService2 = DeviceStateManagerService.this;
            deviceStateManagerService2.mHandler.post(
                    new DeviceStateManagerService$$ExternalSyntheticLambda6(
                            deviceStateManagerService2, 3));
        }

        public final int[] getSupportedStateIdentifiers() {
            int[] supportedStateIdentifiersLocked;
            synchronized (DeviceStateManagerService.this.mLock) {
                supportedStateIdentifiersLocked =
                        DeviceStateManagerService.this.getSupportedStateIdentifiersLocked();
            }
            return supportedStateIdentifiersLocked;
        }

        public final void requestTentModeIfNeeded() {
            DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(
                    DeviceStateManagerService.this.mDeviceStatePolicy);
            throw null;
        }

        public final void setTableModeEnabled(boolean z) {
            DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(
                    DeviceStateManagerService.this.mDeviceStatePolicy);
            throw null;
        }

        public final void updateFoldingSensorState(boolean z) {
            DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(
                    DeviceStateManagerService.this.mDeviceStatePolicy);
            throw null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OverrideRequestScreenObserver
            implements ActivityTaskManagerInternal.ScreenObserver {
        public OverrideRequestScreenObserver() {}

        @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
        public final void onAwakeStateChanged(boolean z) {
            if (CoreRune.FW_FLEXIBLE_CONTROL_FOLDING_SENSOR) {
                DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(
                        DeviceStateManagerService.this.mDeviceStatePolicy);
                throw null;
            }
            synchronized (DeviceStateManagerService.this.mLock) {
                if (!z) {
                    try {
                        if (DeviceStateManagerService.this
                                .shouldCancelOverrideRequestWhenRequesterNotOnTop()) {
                            DeviceStateManagerService deviceStateManagerService =
                                    DeviceStateManagerService.this;
                            deviceStateManagerService.mOverrideRequestController.cancelRequest(
                                    (OverrideRequest)
                                            deviceStateManagerService.mActiveOverride.get());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
        public final void onKeyguardStateChanged(boolean z) {
            synchronized (DeviceStateManagerService.this.mLock) {
                if (z) {
                    try {
                        if (DeviceStateManagerService.this
                                .shouldCancelOverrideRequestWhenRequesterNotOnTop()) {
                            DeviceStateManagerService deviceStateManagerService =
                                    DeviceStateManagerService.this;
                            deviceStateManagerService.mOverrideRequestController.cancelRequest(
                                    (OverrideRequest)
                                            deviceStateManagerService.mActiveOverride.get());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProcessRecord implements IBinder.DeathRecipient {
        public final IDeviceStateManagerCallback mCallback;
        public final DeviceStateManagerService$$ExternalSyntheticLambda1 mDeathListener;
        public final Handler mHandler;
        public final WeakHashMap mLastNotifiedStatus = new WeakHashMap();
        public final int mPid;

        public ProcessRecord(
                IDeviceStateManagerCallback iDeviceStateManagerCallback,
                int i,
                DeviceStateManagerService$$ExternalSyntheticLambda1
                        deviceStateManagerService$$ExternalSyntheticLambda1,
                Handler handler) {
            this.mCallback = iDeviceStateManagerCallback;
            this.mPid = i;
            this.mDeathListener = deviceStateManagerService$$ExternalSyntheticLambda1;
            this.mHandler = handler;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            DeviceStateManagerService deviceStateManagerService = this.mDeathListener.f$0;
            synchronized (deviceStateManagerService.mLock) {
                try {
                    deviceStateManagerService.mProcessRecords.remove(this.mPid);
                    OverrideRequestController overrideRequestController =
                            deviceStateManagerService.mOverrideRequestController;
                    int i = this.mPid;
                    OverrideRequest overrideRequest = overrideRequestController.mBaseStateRequest;
                    if (overrideRequest != null && overrideRequest.mPid == i) {
                        overrideRequestController.cancelCurrentBaseStateRequestLocked(0);
                    }
                    OverrideRequest overrideRequest2 = overrideRequestController.mRequest;
                    if (overrideRequest2 != null && overrideRequest2.mPid == i) {
                        if (overrideRequest2.mRequestedState.hasProperty(5)) {
                            overrideRequestController.cancelCurrentRequestLocked(0);
                        } else if (overrideRequestController.mStickyRequestsAllowed) {
                            overrideRequestController.mStickyRequest = true;
                        } else {
                            overrideRequestController.cancelCurrentRequestLocked(0);
                        }
                    }
                    if (deviceStateManagerService
                            .shouldCancelOverrideRequestWhenRequesterNotOnTop()) {
                        deviceStateManagerService.mOverrideRequestController.cancelRequest(
                                (OverrideRequest) deviceStateManagerService.mActiveOverride.get());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void notifyRequestActiveAsync(IBinder iBinder) {
            Integer num = (Integer) this.mLastNotifiedStatus.get(iBinder);
            if (num == null || !(num.intValue() == 0 || num.intValue() == 2)) {
                this.mLastNotifiedStatus.put(iBinder, 0);
                this.mHandler.post(
                        new DeviceStateManagerService$ProcessRecord$$ExternalSyntheticLambda0(
                                this, iBinder, 0));
            }
        }

        public final void notifyRequestCanceledAsync(IBinder iBinder) {
            Integer num = (Integer) this.mLastNotifiedStatus.get(iBinder);
            if (num == null || num.intValue() != 2) {
                this.mLastNotifiedStatus.put(iBinder, 2);
                this.mHandler.post(
                        new DeviceStateManagerService$ProcessRecord$$ExternalSyntheticLambda0(
                                this, iBinder, 1));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface SystemPropertySetter {
        void setDebugTracingDeviceStateProperty(String str);
    }

    /* renamed from: -$$Nest$mcancelBaseStateOverrideInternal, reason: not valid java name */
    public static void m426$$Nest$mcancelBaseStateOverrideInternal(
            DeviceStateManagerService deviceStateManagerService, int i) {
        synchronized (deviceStateManagerService.mLock) {
            try {
                if (((ProcessRecord) deviceStateManagerService.mProcessRecords.get(i)) == null) {
                    throw new IllegalStateException(
                            "Process " + i + " has no registered callback.");
                }
                deviceStateManagerService.setBaseState(
                        deviceStateManagerService.mDeviceStateProviderListener.mCurrentBaseState);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mcancelStateRequestInternal, reason: not valid java name */
    public static void m427$$Nest$mcancelStateRequestInternal(
            DeviceStateManagerService deviceStateManagerService, int i) {
        synchronized (deviceStateManagerService.mLock) {
            try {
                if (((ProcessRecord) deviceStateManagerService.mProcessRecords.get(i)) == null) {
                    throw new IllegalStateException(
                            "Process " + i + " has no registered callback.");
                }
                Optional optional = deviceStateManagerService.mActiveOverride;
                OverrideRequestController overrideRequestController =
                        deviceStateManagerService.mOverrideRequestController;
                Objects.requireNonNull(overrideRequestController);
                optional.ifPresent(
                        new DeviceStateManagerService$$ExternalSyntheticLambda3(
                                overrideRequestController));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mdumpInternal, reason: not valid java name */
    public static void m428$$Nest$mdumpInternal(
            DeviceStateManagerService deviceStateManagerService, PrintWriter printWriter) {
        deviceStateManagerService.getClass();
        printWriter.println("DEVICE STATE MANAGER (dumpsys device_state)");
        synchronized (deviceStateManagerService.mLock) {
            try {
                printWriter.println(
                        "  mCommittedState=" + deviceStateManagerService.mCommittedState);
                printWriter.println("  mPendingState=" + deviceStateManagerService.mPendingState);
                printWriter.println("  mBaseState=" + deviceStateManagerService.mBaseState);
                printWriter.println(
                        "  mOverrideState=" + deviceStateManagerService.getOverrideState());
                int size = deviceStateManagerService.mProcessRecords.size();
                printWriter.println();
                printWriter.println("Registered processes: size=" + size);
                for (int i = 0; i < size; i++) {
                    printWriter.println(
                            "  "
                                    + i
                                    + ": mPid="
                                    + ((ProcessRecord)
                                                    deviceStateManagerService.mProcessRecords
                                                            .valueAt(i))
                                            .mPid);
                }
                deviceStateManagerService.mOverrideRequestController.dumpInternal(printWriter);
                printWriter.println();
                ((DeviceStatePolicyImpl) deviceStateManagerService.mDeviceStatePolicy)
                        .dump(printWriter, null);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mregisterProcess, reason: not valid java name */
    public static void m429$$Nest$mregisterProcess(
            DeviceStateManagerService deviceStateManagerService,
            int i,
            IDeviceStateManagerCallback iDeviceStateManagerCallback) {
        synchronized (deviceStateManagerService.mLock) {
            try {
                if (deviceStateManagerService.mProcessRecords.contains(i)) {
                    throw new SecurityException(
                            "The calling process has already registered an"
                                + " IDeviceStateManagerCallback.");
                }
                ProcessRecord processRecord =
                        new ProcessRecord(
                                iDeviceStateManagerCallback,
                                i,
                                new DeviceStateManagerService$$ExternalSyntheticLambda1(
                                        deviceStateManagerService),
                                deviceStateManagerService.mHandler);
                try {
                    iDeviceStateManagerCallback.asBinder().linkToDeath(processRecord, 0);
                    deviceStateManagerService.mProcessRecords.put(i, processRecord);
                    DeviceStateInfo deviceStateInfoLocked =
                            deviceStateManagerService.mCommittedState.isPresent()
                                    ? deviceStateManagerService.getDeviceStateInfoLocked()
                                    : null;
                    if (deviceStateInfoLocked != null) {
                        processRecord.mHandler.post(
                                new DeviceStateManagerService$ProcessRecord$$ExternalSyntheticLambda0(
                                        processRecord, deviceStateInfoLocked, 2));
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mrequestBaseStateOverrideInternal, reason: not valid java name */
    public static void m430$$Nest$mrequestBaseStateOverrideInternal(
            DeviceStateManagerService deviceStateManagerService,
            int i,
            int i2,
            int i3,
            int i4,
            IBinder iBinder) {
        synchronized (deviceStateManagerService.mLock) {
            try {
                Optional stateLocked = deviceStateManagerService.getStateLocked(i);
                if (!stateLocked.isPresent()) {
                    throw new IllegalArgumentException(
                            "Requested state: " + i + " is not supported.");
                }
                if (((ProcessRecord) deviceStateManagerService.mProcessRecords.get(i3)) == null) {
                    throw new IllegalStateException(
                            "Process " + i3 + " has no registered callback.");
                }
                if (deviceStateManagerService.mOverrideRequestController.hasRequest(1, iBinder)) {
                    throw new IllegalStateException(
                            "Request has already been made for the supplied token: " + iBinder);
                }
                OverrideRequest overrideRequest =
                        new OverrideRequest(
                                iBinder, i3, i4, (DeviceState) stateLocked.get(), i2, 1);
                OverrideRequestController overrideRequestController =
                        deviceStateManagerService.mOverrideRequestController;
                OverrideRequest overrideRequest2 = overrideRequestController.mBaseStateRequest;
                overrideRequestController.mBaseStateRequest = overrideRequest;
                overrideRequestController.mListener.onStatusChanged(overrideRequest, 1, 0);
                if (overrideRequest2 != null) {
                    overrideRequestController.cancelRequestLocked(overrideRequest2, 0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mrequestStateInternal, reason: not valid java name */
    public static void m431$$Nest$mrequestStateInternal(
            DeviceStateManagerService deviceStateManagerService,
            int i,
            int i2,
            int i3,
            int i4,
            IBinder iBinder,
            boolean z) {
        DeviceState deviceState;
        synchronized (deviceStateManagerService.mLock) {
            if (((ProcessRecord) deviceStateManagerService.mProcessRecords.get(i3)) == null) {
                throw new IllegalStateException("Process " + i3 + " has no registered callback.");
            }
            if (deviceStateManagerService.mOverrideRequestController.hasRequest(0, iBinder)) {
                throw new IllegalStateException(
                        "Request has already been made for the supplied token: " + iBinder);
            }
            Optional stateLocked = deviceStateManagerService.getStateLocked(i);
            if (!stateLocked.isPresent()) {
                throw new IllegalArgumentException("Requested state: " + i + " is not supported.");
            }
            OverrideRequest overrideRequest =
                    new OverrideRequest(iBinder, i3, i4, (DeviceState) stateLocked.get(), i2, 0);
            if (!z
                    && (deviceState = deviceStateManagerService.mRearDisplayState) != null
                    && i == deviceState.getIdentifier()) {
                deviceStateManagerService.mRearDisplayPendingOverrideRequest = overrideRequest;
                StatusBarManagerInternal statusBarManagerInternal =
                        (StatusBarManagerInternal)
                                LocalServices.getService(StatusBarManagerInternal.class);
                if (statusBarManagerInternal != null) {
                    int identifier =
                            ((DeviceState) deviceStateManagerService.mBaseState.get())
                                    .getIdentifier();
                    IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
                    if (iStatusBar != null) {
                        try {
                            iStatusBar.showRearDisplayDialog(identifier);
                        } catch (RemoteException unused) {
                        }
                    }
                }
            }
            OverrideRequestController overrideRequestController =
                    deviceStateManagerService.mOverrideRequestController;
            OverrideRequest overrideRequest2 = overrideRequestController.mRequest;
            overrideRequestController.mRequest = overrideRequest;
            overrideRequestController.mListener.onStatusChanged(overrideRequest, 1, 0);
            if (overrideRequest2 != null) {
                overrideRequestController.cancelRequestLocked(overrideRequest2, 0);
            }
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DeviceStateManagerService(Context context) {
        this(
                context,
                new DeviceStatePolicyImpl(context),
                new DeviceStateManagerService$$ExternalSyntheticLambda0());
        DeviceStatePolicy.DefaultProvider defaultProvider;
        String string =
                context.getResources()
                        .getString(R.string.display_manager_overlay_display_secure_suffix);
        if (TextUtils.isEmpty(string)) {
            defaultProvider = new DeviceStatePolicy.DefaultProvider();
        } else {
            try {
                defaultProvider =
                        (DeviceStatePolicy.DefaultProvider) Class.forName(string).newInstance();
            } catch (ClassCastException | ReflectiveOperationException e) {
                throw new IllegalStateException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Couldn't instantiate class ",
                                string,
                                " for config_deviceSpecificDeviceStatePolicyProvider: make sure it"
                                    + " has a public zero-argument constructor and implements"
                                    + " DeviceStatePolicy.Provider"),
                        e);
            }
        }
        defaultProvider.getClass();
    }

    public DeviceStateManagerService(
            Context context,
            DeviceStatePolicy deviceStatePolicy,
            SystemPropertySetter systemPropertySetter) {
        super(context);
        this.mLock = new Object();
        this.mDeviceStates = new SparseArray();
        this.mCommittedState = Optional.empty();
        this.mPendingState = Optional.empty();
        this.mIsPolicyWaitingForState = false;
        this.mBaseState = Optional.empty();
        this.mActiveOverride = Optional.empty();
        this.mActiveBaseStateOverride = Optional.empty();
        this.mProcessRecords = new SparseArray();
        this.mDeviceStatesAvailableForAppRequests = new HashSet();
        this.mProcessObserver =
                new IProcessObserver
                        .Stub() { // from class:
                                  // com.android.server.devicestate.DeviceStateManagerService.1
                    public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
                        synchronized (DeviceStateManagerService.this.mLock) {
                            try {
                                if (DeviceStateManagerService.this
                                        .shouldCancelOverrideRequestWhenRequesterNotOnTop()) {
                                    OverrideRequest overrideRequest =
                                            (OverrideRequest)
                                                    DeviceStateManagerService.this.mActiveOverride
                                                            .get();
                                    if (i == overrideRequest.mPid && i2 == overrideRequest.mUid) {
                                        if (!z) {
                                            DeviceStateManagerService.this
                                                    .mOverrideRequestController.cancelRequest(
                                                    overrideRequest);
                                        }
                                    }
                                }
                            } finally {
                            }
                        }
                    }

                    public final void onForegroundServicesChanged(int i, int i2, int i3) {}

                    public final void onProcessDied(int i, int i2) {}

                    public final void onProcessStarted(
                            int i, int i2, int i3, String str, String str2) {}
                };
        this.mOverrideRequestScreenObserver = new OverrideRequestScreenObserver();
        this.mDisplayEnabled = false;
        this.mIsWirelessPowerSharing = false;
        new BroadcastReceiver() { // from class:
                                  // com.android.server.devicestate.DeviceStateManagerService.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                DeviceStateManagerService.this.mIsWirelessPowerSharing =
                        intent.getIntExtra("tx_event", 0) == 3;
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "DeviceStateManagerService",
                        new StringBuilder("update mIsWirelessPowerSharing="),
                        DeviceStateManagerService.this.mIsWirelessPowerSharing);
            }
        };
        this.mSystemPropertySetter = systemPropertySetter;
        Handler handler = new Handler(DisplayThread.get().getLooper());
        this.mHandler = handler;
        this.mOverrideRequestController =
                new OverrideRequestController(
                        new DeviceStateManagerService$$ExternalSyntheticLambda1(this));
        this.mDeviceStatePolicy = deviceStatePolicy;
        DeviceStateProviderListener deviceStateProviderListener = new DeviceStateProviderListener();
        this.mDeviceStateProviderListener = deviceStateProviderListener;
        DeviceStateProviderImpl deviceStateProviderImpl =
                ((DeviceStatePolicyImpl) deviceStatePolicy).mProvider;
        synchronized (deviceStateProviderImpl.mLock) {
            if (deviceStateProviderImpl.mListener != null) {
                throw new RuntimeException("Provider already has a listener set.");
            }
            deviceStateProviderImpl.mListener = deviceStateProviderListener;
        }
        deviceStateProviderImpl.notifySupportedStatesChanged(1);
        deviceStateProviderImpl.notifyDeviceStateChangedIfNeeded();
        this.mBinderService = new BinderService();
        this.mActivityTaskManagerInternal =
                (ActivityTaskManagerInternal)
                        LocalServices.getService(ActivityTaskManagerInternal.class);
        this.mDeviceStateNotificationController =
                new DeviceStateNotificationController(
                        context,
                        handler,
                        new DeviceStateManagerService$$ExternalSyntheticLambda6(this, 1),
                        new DeviceStateNotificationController.NotificationInfoProvider(context),
                        context.getPackageManager(),
                        (NotificationManager) context.getSystemService(NotificationManager.class));
    }

    public static boolean isForegroundApp(int i, int i2) {
        try {
            List runningAppProcesses = ActivityManager.getService().getRunningAppProcesses();
            for (int i3 = 0; i3 < runningAppProcesses.size(); i3++) {
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo =
                        (ActivityManager.RunningAppProcessInfo) runningAppProcesses.get(i3);
                if (runningAppProcessInfo.pid == i
                        && runningAppProcessInfo.uid == i2
                        && runningAppProcessInfo.importance <= 100) {
                    return true;
                }
            }
        } catch (RemoteException e) {
            Slog.w("DeviceStateManagerService", "am.getRunningAppProcesses() failed", e);
        }
        return false;
    }

    public IDeviceStateManager getBinderService() {
        return this.mBinderService;
    }

    public final DeviceStateInfo getDeviceStateInfoLocked() {
        List supportedStatesLocked = getSupportedStatesLocked();
        Optional optional = this.mBaseState;
        DeviceState deviceState = INVALID_DEVICE_STATE;
        DeviceState deviceState2 = (DeviceState) optional.orElse(deviceState);
        DeviceState deviceState3 = (DeviceState) this.mCommittedState.orElse(deviceState);
        ArrayList arrayList = new ArrayList(supportedStatesLocked);
        if (!deviceState3.equals(deviceState)) {
            Set systemProperties = deviceState3.getConfiguration().getSystemProperties();
            deviceState =
                    new DeviceState(
                            new DeviceState.Configuration.Builder(
                                            deviceState3.getIdentifier(), deviceState3.getName())
                                    .setSystemProperties(systemProperties)
                                    .setPhysicalProperties(
                                            deviceState2.getConfiguration().getPhysicalProperties())
                                    .build());
        }
        return new DeviceStateInfo(arrayList, deviceState2, deviceState);
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public final Optional getOverrideState() {
        synchronized (this.mLock) {
            try {
                if (this.mActiveOverride.isPresent()) {
                    return getStateLocked(
                            ((OverrideRequest) this.mActiveOverride.get())
                                    .mRequestedState.getIdentifier());
                }
                return Optional.empty();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Optional getPendingState() {
        Optional optional;
        synchronized (this.mLock) {
            optional = this.mPendingState;
        }
        return optional;
    }

    public final Optional getStateLocked(int i) {
        return Optional.ofNullable((DeviceState) this.mDeviceStates.get(i));
    }

    public final int[] getSupportedStateIdentifiersLocked() {
        int size = this.mDeviceStates.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = ((DeviceState) this.mDeviceStates.valueAt(i)).getIdentifier();
        }
        return iArr;
    }

    public final List getSupportedStatesLocked() {
        ArrayList arrayList = new ArrayList(this.mDeviceStates.size());
        for (int i = 0; i < this.mDeviceStates.size(); i++) {
            arrayList.add(i, (DeviceState) this.mDeviceStates.valueAt(i));
        }
        return arrayList;
    }

    public final void notifyDeviceStateInfoChangedAsync() {
        synchronized (this.mLock) {
            try {
                if (this.mPendingState.isPresent()) {
                    Slog.i(
                            "DeviceStateManagerService",
                            "Cannot notify device state info change when pending state is"
                                + " present.");
                    return;
                }
                if (this.mBaseState.isPresent() && this.mCommittedState.isPresent()) {
                    if (this.mProcessRecords.size() == 0) {
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < this.mProcessRecords.size(); i++) {
                        arrayList.add((ProcessRecord) this.mProcessRecords.valueAt(i));
                    }
                    DeviceStateInfo deviceStateInfoLocked = getDeviceStateInfoLocked();
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        ProcessRecord processRecord = (ProcessRecord) arrayList.get(i2);
                        processRecord.mHandler.post(
                                new DeviceStateManagerService$ProcessRecord$$ExternalSyntheticLambda0(
                                        processRecord, deviceStateInfoLocked, 2));
                    }
                    return;
                }
                Slog.e(
                        "DeviceStateManagerService",
                        "Cannot notify device state info change before the initial state has been"
                            + " committed.");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyPolicyIfNeeded() {
        if (Thread.holdsLock(this.mLock)) {
            Throwable th =
                    new Throwable("Attempting to notify DeviceStatePolicy with service lock held");
            th.fillInStackTrace();
            Slog.w("DeviceStateManagerService", th);
        }
        synchronized (this.mLock) {
            try {
                if (this.mIsPolicyWaitingForState) {
                    this.mIsPolicyWaitingForState = false;
                    ((DeviceState) this.mPendingState.get()).getIdentifier();
                    DeviceStatePolicy deviceStatePolicy = this.mDeviceStatePolicy;
                    DeviceStateManagerService$$ExternalSyntheticLambda6
                            deviceStateManagerService$$ExternalSyntheticLambda6 =
                                    new DeviceStateManagerService$$ExternalSyntheticLambda6(
                                            this, 0);
                    ((DeviceStatePolicyImpl) deviceStatePolicy).getClass();
                    deviceStateManagerService$$ExternalSyntheticLambda6.run();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("device_state", this.mBinderService);
        publishLocalService(DeviceStateManagerInternal.class, new LocalService());
        synchronized (this.mLock) {
            readStatesAvailableForRequestFromApps();
            HashSet hashSet = new HashSet();
            for (int i :
                    getContext()
                            .getResources()
                            .getIntArray(R.array.preloaded_freeform_multi_window_drawables)) {
                hashSet.add(Integer.valueOf(i));
            }
            this.mFoldedDeviceStates = hashSet;
        }
        this.mActivityTaskManagerInternal.registerScreenObserver(
                this.mOverrideRequestScreenObserver);
        ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class))
                .registerProcessObserver(this.mProcessObserver);
    }

    public final void onStateRequestOverlayDismissedInternal(boolean z) {
        synchronized (this.mLock) {
            try {
                OverrideRequest overrideRequest = this.mRearDisplayPendingOverrideRequest;
                if (overrideRequest != null) {
                    if (z) {
                        ((ProcessRecord) this.mProcessRecords.get(overrideRequest.mPid))
                                .notifyRequestCanceledAsync(
                                        this.mRearDisplayPendingOverrideRequest.mToken);
                    } else {
                        OverrideRequestController overrideRequestController =
                                this.mOverrideRequestController;
                        OverrideRequest overrideRequest2 = overrideRequestController.mRequest;
                        overrideRequestController.mRequest = overrideRequest;
                        overrideRequestController.mListener.onStatusChanged(overrideRequest, 1, 0);
                        if (overrideRequest2 != null) {
                            overrideRequestController.cancelRequestLocked(overrideRequest2, 0);
                        }
                    }
                    this.mRearDisplayPendingOverrideRequest = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void readStatesAvailableForRequestFromApps() {
        for (String str :
                getContext()
                        .getResources()
                        .getStringArray(R.array.config_supported_cellular_usage_settings)) {
            int integer =
                    getContext()
                            .getResources()
                            .getInteger(
                                    getContext()
                                            .getResources()
                                            .getIdentifier(str, "integer", "android"));
            int i = 0;
            while (true) {
                if (i >= this.mDeviceStates.size()) {
                    NandswapManager$$ExternalSyntheticOutline0.m(
                            integer,
                            "Invalid device state was found in the configuration file. State id: ",
                            "DeviceStateManagerService");
                    break;
                } else {
                    if (integer == ((DeviceState) this.mDeviceStates.valueAt(i)).getIdentifier()) {
                        ((HashSet) this.mDeviceStatesAvailableForAppRequests)
                                .add(Integer.valueOf(integer));
                        break;
                    }
                    i++;
                }
            }
        }
    }

    public final void setBaseState(final int i) {
        synchronized (this.mLock) {
            try {
                Optional stateLocked = getStateLocked(i);
                if (stateLocked.isEmpty()) {
                    throw new IllegalArgumentException("Base state is not supported");
                }
                DeviceState deviceState = (DeviceState) stateLocked.get();
                if (this.mBaseState.isPresent()
                        && ((DeviceState) this.mBaseState.get()).equals(deviceState)) {
                    return;
                }
                if (this.mRearDisplayPendingOverrideRequest != null
                        && this.mBaseState
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda7
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                DeviceStateManagerService
                                                        deviceStateManagerService =
                                                                DeviceStateManagerService.this;
                                                int i2 = i;
                                                if (((HashSet)
                                                                deviceStateManagerService
                                                                        .mFoldedDeviceStates)
                                                        .contains(
                                                                Integer.valueOf(
                                                                        ((DeviceState) obj)
                                                                                .getIdentifier()))) {
                                                    if (!((HashSet)
                                                                    deviceStateManagerService
                                                                            .mFoldedDeviceStates)
                                                            .contains(Integer.valueOf(i2))) {
                                                        return true;
                                                    }
                                                }
                                                return false;
                                            }
                                        })
                                .isPresent()) {
                    onStateRequestOverlayDismissedInternal(false);
                }
                this.mBaseState = Optional.of(deviceState);
                if (deviceState.hasProperty(4)) {
                    this.mOverrideRequestController.cancelCurrentRequestLocked(0);
                }
                OverrideRequestController overrideRequestController =
                        this.mOverrideRequestController;
                OverrideRequest overrideRequest = overrideRequestController.mBaseStateRequest;
                if (overrideRequest != null
                        && i != overrideRequest.mRequestedState.getIdentifier()) {
                    overrideRequestController.cancelCurrentBaseStateRequestLocked(0);
                }
                OverrideRequest overrideRequest2 = overrideRequestController.mRequest;
                if (overrideRequest2 != null && (overrideRequest2.mFlags & 1) != 0) {
                    overrideRequestController.cancelCurrentRequestLocked(0);
                }
                updatePendingStateLocked();
                notifyDeviceStateInfoChangedAsync();
                this.mHandler.post(
                        new DeviceStateManagerService$$ExternalSyntheticLambda6(this, 2));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean shouldCancelOverrideRequestWhenRequesterNotOnTop() {
        if (this.mActiveOverride.isEmpty()) {
            return false;
        }
        return ((DeviceState)
                        this.mDeviceStates.get(
                                ((OverrideRequest) this.mActiveOverride.get())
                                        .mRequestedState.getIdentifier()))
                .hasProperty(5);
    }

    public final boolean updatePendingStateLocked() {
        DeviceState deviceState;
        if (this.mPendingState.isPresent()) {
            return false;
        }
        if (this.mActiveOverride.isPresent()) {
            deviceState =
                    (DeviceState)
                            getStateLocked(
                                            ((OverrideRequest) this.mActiveOverride.get())
                                                    .mRequestedState.getIdentifier())
                                    .get();
        } else {
            if (this.mBaseState.isPresent()) {
                if (this.mDeviceStates.contains(
                        ((DeviceState) this.mBaseState.get()).getIdentifier())) {
                    deviceState = (DeviceState) this.mBaseState.get();
                }
            }
            deviceState = null;
        }
        if (deviceState == null) {
            return false;
        }
        if (this.mCommittedState.isPresent() && deviceState.equals(this.mCommittedState.get())) {
            return false;
        }
        this.mPendingState = Optional.of(deviceState);
        this.mIsPolicyWaitingForState = true;
        return true;
    }
}
