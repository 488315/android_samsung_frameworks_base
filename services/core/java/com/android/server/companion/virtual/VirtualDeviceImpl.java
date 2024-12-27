package com.android.server.companion.virtual;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.companion.AssociationInfo;
import android.companion.CompanionDeviceManager;
import android.companion.virtual.IVirtualDevice;
import android.companion.virtual.IVirtualDeviceActivityListener;
import android.companion.virtual.IVirtualDeviceIntentInterceptor;
import android.companion.virtual.IVirtualDeviceSoundEffectListener;
import android.companion.virtual.VirtualDevice;
import android.companion.virtual.VirtualDeviceManager;
import android.companion.virtual.VirtualDeviceParams;
import android.companion.virtual.audio.IAudioConfigChangedCallback;
import android.companion.virtual.audio.IAudioRoutingCallback;
import android.companion.virtual.camera.VirtualCameraConfig;
import android.companion.virtual.sensor.VirtualSensor;
import android.companion.virtual.sensor.VirtualSensorEvent;
import android.companion.virtualcamera.IVirtualCameraService;
import android.content.AttributionSource;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.PointF;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.input.VirtualDpadConfig;
import android.hardware.input.VirtualKeyEvent;
import android.hardware.input.VirtualKeyboardConfig;
import android.hardware.input.VirtualMouseButtonEvent;
import android.hardware.input.VirtualMouseConfig;
import android.hardware.input.VirtualMouseRelativeEvent;
import android.hardware.input.VirtualMouseScrollEvent;
import android.hardware.input.VirtualNavigationTouchpadConfig;
import android.hardware.input.VirtualStylusButtonEvent;
import android.hardware.input.VirtualStylusConfig;
import android.hardware.input.VirtualStylusMotionEvent;
import android.hardware.input.VirtualTouchEvent;
import android.hardware.input.VirtualTouchscreenConfig;
import android.media.AudioManager;
import android.media.audiopolicy.AudioMix;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Looper;
import android.os.PermissionEnforcer;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.modules.expresslog.Counter;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.companion.virtual.audio.VirtualAudioController;
import com.android.server.companion.virtual.camera.VirtualCameraController;
import com.android.server.companion.virtual.camera.VirtualCameraController.CameraDescriptor;
import com.android.server.input.InputManagerService;
import com.android.server.inputmethod.InputMethodManagerInternal;

import dalvik.annotation.optimization.FastNative;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

final class VirtualDeviceImpl extends IVirtualDevice.Stub
        implements IBinder.DeathRecipient,
                GenericWindowPolicyController.RunningAppsChangedListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final IVirtualDeviceActivityListener mActivityListener;
    public final Set mActivityPolicyExemptions;
    public final IBinder mAppToken;
    public final AssociationInfo mAssociationInfo;
    public final AttributionSource mAttributionSource;
    public final int mBaseVirtualDisplayFlags;
    public final CameraAccessController mCameraAccessController;
    public final Context mContext;
    public boolean mDefaultShowPointerIcon;
    public final int mDeviceId;
    public final SparseIntArray mDevicePolicies;
    public final DisplayManagerGlobal mDisplayManager;
    public final DisplayManagerInternal mDisplayManagerInternal;
    public final InputController mInputController;
    public final Map mIntentInterceptors;
    public LocaleList mLocaleList;
    public final String mOwnerPackageName;
    public final int mOwnerUid;
    public final VirtualDeviceParams mParams;
    public final PendingTrampolineCallback mPendingTrampolineCallback;
    public final ComponentName mPermissionDialogComponent;
    public final String mPersistentDeviceId;
    public final VirtualDevice mPublicVirtualDeviceObject;
    public final Consumer mRunningAppsChangedCallback;
    public final SensorController mSensorController;
    public final VirtualDeviceManagerService mService;
    public final IVirtualDeviceSoundEffectListener mSoundEffectListener;
    public VirtualAudioController mVirtualAudioController;
    public final VirtualCameraController mVirtualCameraController;
    public final Object mVirtualDeviceLock;
    public final VirtualDeviceLog mVirtualDeviceLog;
    public final SparseArray mVirtualDisplays;

    public final class PendingTrampoline {
        public final int mDisplayId;
        public final PendingIntent mPendingIntent;
        public final ResultReceiver mResultReceiver;

        public PendingTrampoline(
                int i, PendingIntent pendingIntent, ResultReceiver resultReceiver) {
            this.mPendingIntent = pendingIntent;
            this.mResultReceiver = resultReceiver;
            this.mDisplayId = i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("PendingTrampoline{pendingIntent=");
            sb.append(this.mPendingIntent);
            sb.append(", resultReceiver=");
            sb.append(this.mResultReceiver);
            sb.append(", displayId=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mDisplayId, sb, "}");
        }
    }

    public interface PendingTrampolineCallback {}

    public final class VirtualDisplayWrapper {
        public final IVirtualDisplayCallback mToken;
        public final PowerManager.WakeLock mWakeLock;
        public final GenericWindowPolicyController mWindowPolicyController;

        public VirtualDisplayWrapper(
                IVirtualDisplayCallback iVirtualDisplayCallback,
                GenericWindowPolicyController genericWindowPolicyController,
                PowerManager.WakeLock wakeLock) {
            Objects.requireNonNull(iVirtualDisplayCallback);
            this.mToken = iVirtualDisplayCallback;
            this.mWindowPolicyController = genericWindowPolicyController;
            this.mWakeLock = wakeLock;
        }
    }

    public VirtualDeviceImpl(
            Context context,
            AssociationInfo associationInfo,
            VirtualDeviceManagerService virtualDeviceManagerService,
            VirtualDeviceLog virtualDeviceLog,
            IBinder iBinder,
            AttributionSource attributionSource,
            int i,
            CameraAccessController cameraAccessController,
            VirtualDeviceManagerService.VirtualDeviceManagerImpl.AnonymousClass1 anonymousClass1,
            IVirtualDeviceActivityListener iVirtualDeviceActivityListener,
            IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener,
            VirtualDeviceManagerService$VirtualDeviceManagerImpl$$ExternalSyntheticLambda0
                    virtualDeviceManagerService$VirtualDeviceManagerImpl$$ExternalSyntheticLambda0,
            VirtualDeviceParams virtualDeviceParams) {
        this(
                context,
                associationInfo,
                virtualDeviceManagerService,
                virtualDeviceLog,
                iBinder,
                attributionSource,
                i,
                null,
                cameraAccessController,
                anonymousClass1,
                iVirtualDeviceActivityListener,
                iVirtualDeviceSoundEffectListener,
                virtualDeviceManagerService$VirtualDeviceManagerImpl$$ExternalSyntheticLambda0,
                virtualDeviceParams,
                DisplayManagerGlobal.getInstance(),
                (android.companion.virtual.flags.Flags.virtualCamera()
                                && android.companion.virtualdevice.flags.Flags
                                        .virtualCameraServiceDiscovery()
                                && nativeVirtualCameraServiceBuildFlagEnabled())
                        ? new VirtualCameraController(
                                null, virtualDeviceParams.getDevicePolicy(5), i)
                        : null);
    }

    public VirtualDeviceImpl(
            Context context,
            AssociationInfo associationInfo,
            VirtualDeviceManagerService virtualDeviceManagerService,
            VirtualDeviceLog virtualDeviceLog,
            IBinder iBinder,
            AttributionSource attributionSource,
            int i,
            InputController inputController,
            CameraAccessController cameraAccessController,
            PendingTrampolineCallback pendingTrampolineCallback,
            IVirtualDeviceActivityListener iVirtualDeviceActivityListener,
            IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener,
            Consumer consumer,
            VirtualDeviceParams virtualDeviceParams,
            DisplayManagerGlobal displayManagerGlobal,
            VirtualCameraController virtualCameraController) {
        super(PermissionEnforcer.fromContext(context));
        int i2;
        String str;
        this.mVirtualDeviceLock = new Object();
        this.mVirtualDisplays = new SparseArray();
        this.mIntentInterceptors = new ArrayMap();
        this.mDefaultShowPointerIcon = true;
        this.mLocaleList = null;
        this.mVirtualDeviceLog = virtualDeviceLog;
        this.mOwnerPackageName = attributionSource.getPackageName();
        this.mAttributionSource = attributionSource;
        Context createContextAsUser =
                context.createContextAsUser(
                        UserHandle.getUserHandleForUid(attributionSource.getUid()), 0);
        this.mContext = createContextAsUser;
        this.mAssociationInfo = associationInfo;
        String m =
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        associationInfo.getId(), "companion:");
        this.mPersistentDeviceId = m;
        this.mService = virtualDeviceManagerService;
        this.mPendingTrampolineCallback = pendingTrampolineCallback;
        this.mActivityListener = iVirtualDeviceActivityListener;
        this.mSoundEffectListener = iVirtualDeviceSoundEffectListener;
        this.mRunningAppsChangedCallback = consumer;
        int uid = attributionSource.getUid();
        this.mOwnerUid = uid;
        this.mDeviceId = i;
        this.mAppToken = iBinder;
        this.mParams = virtualDeviceParams;
        this.mDevicePolicies = virtualDeviceParams.getDevicePolicies();
        this.mDisplayManager = displayManagerGlobal;
        this.mDisplayManagerInternal =
                (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
        if (inputController == null) {
            final Handler mainThreadHandler = context.getMainThreadHandler();
            i2 = uid;
            str = m;
            this.mInputController =
                    new InputController(
                            new InputController.NativeWrapper(),
                            mainThreadHandler,
                            (WindowManager) context.getSystemService(WindowManager.class),
                            attributionSource,
                            new InputController
                                    .DeviceCreationThreadVerifier() { // from class:
                                                                      // com.android.server.companion.virtual.InputController$$ExternalSyntheticLambda2
                                @Override // com.android.server.companion.virtual.InputController.DeviceCreationThreadVerifier
                                public final boolean isValidThread() {
                                    AtomicLong atomicLong = InputController.sNextPhysId;
                                    return !mainThreadHandler.getLooper().isCurrentThread();
                                }
                            });
        } else {
            i2 = uid;
            str = m;
            this.mInputController = inputController;
        }
        this.mSensorController =
                new SensorController(
                        this,
                        i,
                        attributionSource,
                        virtualDeviceParams.getVirtualSensorCallback(),
                        virtualDeviceParams.getVirtualSensorConfigs());
        this.mCameraAccessController = cameraAccessController;
        if (cameraAccessController != null) {
            synchronized (cameraAccessController.mObserverLock) {
                try {
                    if (cameraAccessController.mObserverCount == 0) {
                        cameraAccessController.mCameraManager.registerAvailabilityCallback(
                                cameraAccessController.mContext.getMainExecutor(),
                                cameraAccessController);
                    }
                    cameraAccessController.mObserverCount++;
                } finally {
                }
            }
        }
        if (android.companion.virtual.flags.Flags.streamPermissions()) {
            this.mPermissionDialogComponent = null;
        } else {
            Intent intent = new Intent("android.content.pm.action.REQUEST_PERMISSIONS");
            PackageManager packageManager = createContextAsUser.getPackageManager();
            intent.setPackage(packageManager.getPermissionControllerPackageName());
            this.mPermissionDialogComponent = intent.resolveActivity(packageManager);
        }
        this.mVirtualCameraController = virtualCameraController;
        try {
            iBinder.linkToDeath(this, 0);
            virtualDeviceLog.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Flags.dumpHistory();
                virtualDeviceLog.mLogEntries.push(
                        new VirtualDeviceLog.LogEntry(0, i, i2, System.currentTimeMillis()));
                if (virtualDeviceLog.mLogEntries.size() > 16) {
                    virtualDeviceLog.mLogEntries.removeLast();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (android.companion.virtual.flags.Flags.vdmPublicApis()) {
                    this.mPublicVirtualDeviceObject =
                            new VirtualDevice(
                                    this,
                                    i,
                                    str,
                                    virtualDeviceParams.getName(),
                                    associationInfo.getDisplayName());
                } else {
                    this.mPublicVirtualDeviceObject =
                            new VirtualDevice(this, i, str, virtualDeviceParams.getName());
                }
                if (android.companion.virtual.flags.Flags.dynamicPolicy()) {
                    this.mActivityPolicyExemptions =
                            new ArraySet(
                                    virtualDeviceParams.getDevicePolicy(3) == 0
                                            ? virtualDeviceParams.getBlockedActivities()
                                            : virtualDeviceParams.getAllowedActivities());
                } else {
                    this.mActivityPolicyExemptions =
                            virtualDeviceParams.getDefaultActivityPolicy() == 0
                                    ? virtualDeviceParams.getBlockedActivities()
                                    : virtualDeviceParams.getAllowedActivities();
                }
                int i3 =
                        !android.companion.virtual.flags.Flags.consistentDisplayFlags()
                                ? 25033
                                : 24896;
                this.mBaseVirtualDisplayFlags =
                        virtualDeviceParams.getLockState() == 1 ? i3 | 4096 : i3;
                if (!android.companion.virtual.flags.Flags.vdmCustomIme()
                        || virtualDeviceParams.getInputMethodComponent() == null) {
                    return;
                }
                String flattenToShortString =
                        virtualDeviceParams.getInputMethodComponent().flattenToShortString();
                Slog.d(
                        "VirtualDeviceImpl",
                        "Setting custom input method "
                                + flattenToShortString
                                + " as default for virtual device "
                                + i);
                InputMethodManagerInternal.get()
                        .setVirtualDeviceInputMethodForAllUsers(i, flattenToShortString);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int getTargetDisplayIdForInput(int i) {
        int displayIdToMirror;
        return (android.companion.virtual.flags.Flags.interactiveScreenMirror()
                        && (displayIdToMirror =
                                        ((DisplayManagerInternal)
                                                        LocalServices.getService(
                                                                DisplayManagerInternal.class))
                                                .getDisplayIdToMirror(i))
                                != -1)
                ? displayIdToMirror
                : i;
    }

    @FastNative
    private static native boolean nativeVirtualCameraServiceBuildFlagEnabled();

    public final void addActivityPolicyExemption(ComponentName componentName) {
        addActivityPolicyExemption_enforcePermission();
        synchronized (this.mVirtualDeviceLock) {
            try {
                if (this.mActivityPolicyExemptions.add(componentName)) {
                    for (int i = 0; i < this.mVirtualDisplays.size(); i++) {
                        GenericWindowPolicyController genericWindowPolicyController =
                                ((VirtualDisplayWrapper) this.mVirtualDisplays.valueAt(i))
                                        .mWindowPolicyController;
                        synchronized (
                                genericWindowPolicyController.mGenericWindowPolicyControllerLock) {
                            genericWindowPolicyController.mActivityPolicyExemptions.add(
                                    componentName);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        close();
    }

    public final void checkVirtualInputDeviceDisplayIdAssociation(int i) {
        if (this.mContext.checkCallingPermission("android.permission.INJECT_EVENTS") == 0) {
            return;
        }
        synchronized (this.mVirtualDeviceLock) {
            try {
                if (!this.mVirtualDisplays.contains(i)) {
                    throw new SecurityException(
                            "Cannot create a virtual input device for display "
                                    + i
                                    + " which not associated with this virtual device");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void close() {
        int size;
        VirtualDisplayWrapper[] virtualDisplayWrapperArr;
        close_enforcePermission();
        VirtualDeviceManagerService virtualDeviceManagerService = this.mService;
        int i = this.mDeviceId;
        synchronized (virtualDeviceManagerService.mVirtualDeviceManagerLock) {
            try {
                if (virtualDeviceManagerService.mVirtualDevices.contains(i)) {
                    virtualDeviceManagerService.mAppsOnVirtualDevices.remove(i);
                    virtualDeviceManagerService.mVirtualDevices.remove(i);
                    if (android.companion.virtual.flags.Flags.vdmPublicApis()) {
                        virtualDeviceManagerService.mVirtualDeviceListeners.broadcast(
                                new VirtualDeviceManagerService$$ExternalSyntheticLambda1(i, 0));
                    }
                    Intent intent =
                            new Intent("android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED");
                    intent.putExtra("android.companion.virtual.extra.VIRTUAL_DEVICE_ID", i);
                    intent.setFlags(1073741824);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        virtualDeviceManagerService
                                .getContext()
                                .sendBroadcastAsUser(intent, UserHandle.ALL);
                        if (!android.companion.virtual.flags.Flags.persistentDeviceIdApi()) {
                            synchronized (virtualDeviceManagerService.mVirtualDeviceManagerLock) {
                                if (virtualDeviceManagerService.mVirtualDevices.size() == 0) {
                                    ((CompanionDeviceManager)
                                                    virtualDeviceManagerService
                                                            .getContext()
                                                            .getSystemService(
                                                                    CompanionDeviceManager.class))
                                            .removeOnAssociationsChangedListener(
                                                    virtualDeviceManagerService
                                                            .mCdmAssociationListener);
                                }
                            }
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        VirtualDeviceLog virtualDeviceLog = this.mVirtualDeviceLog;
                        int i2 = this.mDeviceId;
                        int i3 = this.mOwnerUid;
                        virtualDeviceLog.getClass();
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            Flags.dumpHistory();
                            virtualDeviceLog.mLogEntries.push(
                                    new VirtualDeviceLog.LogEntry(
                                            1, i2, i3, System.currentTimeMillis()));
                            if (virtualDeviceLog.mLogEntries.size() > 16) {
                                virtualDeviceLog.mLogEntries.removeLast();
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                synchronized (this.mVirtualDeviceLock) {
                                    try {
                                        VirtualAudioController virtualAudioController =
                                                this.mVirtualAudioController;
                                        if (virtualAudioController != null) {
                                            virtualAudioController.stopListening();
                                            this.mVirtualAudioController = null;
                                        }
                                        this.mLocaleList = null;
                                        size = this.mVirtualDisplays.size();
                                        virtualDisplayWrapperArr = new VirtualDisplayWrapper[size];
                                        for (int i4 = 0; i4 < this.mVirtualDisplays.size(); i4++) {
                                            virtualDisplayWrapperArr[i4] =
                                                    (VirtualDisplayWrapper)
                                                            this.mVirtualDisplays.valueAt(i4);
                                        }
                                        this.mVirtualDisplays.clear();
                                    } finally {
                                    }
                                }
                                for (int i5 = 0; i5 < size; i5++) {
                                    VirtualDisplayWrapper virtualDisplayWrapper =
                                            virtualDisplayWrapperArr[i5];
                                    this.mDisplayManager.releaseVirtualDisplay(
                                            virtualDisplayWrapper.mToken);
                                    virtualDisplayWrapper.mWakeLock.release();
                                    virtualDisplayWrapper.mWindowPolicyController
                                            .unregisterRunningAppsChangedListener(this);
                                }
                                this.mAppToken.unlinkToDeath(this, 0);
                                CameraAccessController cameraAccessController =
                                        this.mCameraAccessController;
                                if (cameraAccessController != null) {
                                    synchronized (cameraAccessController.mObserverLock) {
                                        try {
                                            int i6 = cameraAccessController.mObserverCount - 1;
                                            cameraAccessController.mObserverCount = i6;
                                            if (i6 <= 0) {
                                                cameraAccessController.close();
                                            }
                                        } finally {
                                        }
                                    }
                                }
                                if (android.companion.virtual.flags.Flags.vdmCustomIme()
                                        && this.mParams.getInputMethodComponent() != null) {
                                    InputMethodManagerInternal.get()
                                            .setVirtualDeviceInputMethodForAllUsers(
                                                    this.mDeviceId, null);
                                }
                                this.mInputController.close();
                                this.mSensorController.close();
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                VirtualCameraController virtualCameraController =
                                        this.mVirtualCameraController;
                                if (virtualCameraController != null) {
                                    virtualCameraController.close();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        } finally {
                        }
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    public final PowerManager.WakeLock createAndAcquireWakeLockForDisplay(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PowerManager.WakeLock newWakeLock =
                    ((PowerManager) this.mContext.getSystemService(PowerManager.class))
                            .newWakeLock(10, "VirtualDeviceImpl:" + i, i);
            newWakeLock.acquire();
            return newWakeLock;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void createVirtualDpad(VirtualDpadConfig virtualDpadConfig, IBinder iBinder) {
        createVirtualDpad_enforcePermission();
        Objects.requireNonNull(virtualDpadConfig);
        checkVirtualInputDeviceDisplayIdAssociation(virtualDpadConfig.getAssociatedDisplayId());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                InputController inputController = this.mInputController;
                String inputDeviceName = virtualDpadConfig.getInputDeviceName();
                int vendorId = virtualDpadConfig.getVendorId();
                int productId = virtualDpadConfig.getProductId();
                int targetDisplayIdForInput =
                        getTargetDisplayIdForInput(virtualDpadConfig.getAssociatedDisplayId());
                inputController.getClass();
                String createPhys = InputController.createPhys("Dpad");
                inputController.createDeviceInternal(
                        4,
                        inputDeviceName,
                        vendorId,
                        productId,
                        iBinder,
                        targetDisplayIdForInput,
                        createPhys,
                        new InputController$$ExternalSyntheticLambda1(
                                inputController,
                                inputDeviceName,
                                vendorId,
                                productId,
                                createPhys,
                                2));
            } catch (InputController.DeviceCreationException e) {
                throw new IllegalArgumentException(e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void createVirtualKeyboard(
            VirtualKeyboardConfig virtualKeyboardConfig, IBinder iBinder) {
        createVirtualKeyboard_enforcePermission();
        Objects.requireNonNull(virtualKeyboardConfig);
        checkVirtualInputDeviceDisplayIdAssociation(virtualKeyboardConfig.getAssociatedDisplayId());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mInputController.createKeyboard(
                        virtualKeyboardConfig.getInputDeviceName(),
                        virtualKeyboardConfig.getVendorId(),
                        virtualKeyboardConfig.getProductId(),
                        iBinder,
                        getTargetDisplayIdForInput(virtualKeyboardConfig.getAssociatedDisplayId()),
                        virtualKeyboardConfig.getLanguageTag(),
                        virtualKeyboardConfig.getLayoutType());
                synchronized (this.mVirtualDeviceLock) {
                    this.mLocaleList =
                            LocaleList.forLanguageTags(virtualKeyboardConfig.getLanguageTag());
                }
            } catch (InputController.DeviceCreationException e) {
                throw new IllegalArgumentException(e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void createVirtualMouse(VirtualMouseConfig virtualMouseConfig, IBinder iBinder) {
        createVirtualMouse_enforcePermission();
        Objects.requireNonNull(virtualMouseConfig);
        checkVirtualInputDeviceDisplayIdAssociation(virtualMouseConfig.getAssociatedDisplayId());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                InputController inputController = this.mInputController;
                String inputDeviceName = virtualMouseConfig.getInputDeviceName();
                int vendorId = virtualMouseConfig.getVendorId();
                int productId = virtualMouseConfig.getProductId();
                int associatedDisplayId = virtualMouseConfig.getAssociatedDisplayId();
                inputController.getClass();
                String createPhys = InputController.createPhys("Mouse");
                inputController.createDeviceInternal(
                        2,
                        inputDeviceName,
                        vendorId,
                        productId,
                        iBinder,
                        associatedDisplayId,
                        createPhys,
                        new InputController$$ExternalSyntheticLambda1(
                                inputController,
                                inputDeviceName,
                                vendorId,
                                productId,
                                createPhys,
                                1));
            } catch (InputController.DeviceCreationException e) {
                throw new IllegalArgumentException(e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void createVirtualNavigationTouchpad(
            VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig, IBinder iBinder) {
        createVirtualNavigationTouchpad_enforcePermission();
        Objects.requireNonNull(virtualNavigationTouchpadConfig);
        checkVirtualInputDeviceDisplayIdAssociation(
                virtualNavigationTouchpadConfig.getAssociatedDisplayId());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mInputController.createNavigationTouchpad(
                        virtualNavigationTouchpadConfig.getVendorId(),
                        virtualNavigationTouchpadConfig.getProductId(),
                        getTargetDisplayIdForInput(
                                virtualNavigationTouchpadConfig.getAssociatedDisplayId()),
                        virtualNavigationTouchpadConfig.getHeight(),
                        virtualNavigationTouchpadConfig.getWidth(),
                        iBinder,
                        virtualNavigationTouchpadConfig.getInputDeviceName());
            } catch (InputController.DeviceCreationException e) {
                throw new IllegalArgumentException(e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void createVirtualStylus(
            VirtualStylusConfig virtualStylusConfig, IBinder iBinder) {
        createVirtualStylus_enforcePermission();
        Objects.requireNonNull(virtualStylusConfig);
        Objects.requireNonNull(iBinder);
        checkVirtualInputDeviceDisplayIdAssociation(virtualStylusConfig.getAssociatedDisplayId());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                InputController inputController = this.mInputController;
                String inputDeviceName = virtualStylusConfig.getInputDeviceName();
                int vendorId = virtualStylusConfig.getVendorId();
                int productId = virtualStylusConfig.getProductId();
                int associatedDisplayId = virtualStylusConfig.getAssociatedDisplayId();
                int height = virtualStylusConfig.getHeight();
                int width = virtualStylusConfig.getWidth();
                inputController.getClass();
                String createPhys = InputController.createPhys("Stylus");
                inputController.createDeviceInternal(
                        6,
                        inputDeviceName,
                        vendorId,
                        productId,
                        iBinder,
                        associatedDisplayId,
                        createPhys,
                        new InputController$$ExternalSyntheticLambda5(
                                inputController,
                                inputDeviceName,
                                vendorId,
                                productId,
                                createPhys,
                                height,
                                width,
                                2));
            } catch (InputController.DeviceCreationException e) {
                throw new IllegalArgumentException(e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void createVirtualTouchscreen(
            VirtualTouchscreenConfig virtualTouchscreenConfig, IBinder iBinder) {
        createVirtualTouchscreen_enforcePermission();
        Objects.requireNonNull(virtualTouchscreenConfig);
        checkVirtualInputDeviceDisplayIdAssociation(
                virtualTouchscreenConfig.getAssociatedDisplayId());
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                InputController inputController = this.mInputController;
                String inputDeviceName = virtualTouchscreenConfig.getInputDeviceName();
                int vendorId = virtualTouchscreenConfig.getVendorId();
                int productId = virtualTouchscreenConfig.getProductId();
                int associatedDisplayId = virtualTouchscreenConfig.getAssociatedDisplayId();
                int height = virtualTouchscreenConfig.getHeight();
                int width = virtualTouchscreenConfig.getWidth();
                inputController.getClass();
                String createPhys = InputController.createPhys("Touchscreen");
                inputController.createDeviceInternal(
                        3,
                        inputDeviceName,
                        vendorId,
                        productId,
                        iBinder,
                        associatedDisplayId,
                        createPhys,
                        new InputController$$ExternalSyntheticLambda5(
                                inputController,
                                inputDeviceName,
                                vendorId,
                                productId,
                                createPhys,
                                height,
                                width,
                                0));
            } catch (InputController.DeviceCreationException e) {
                throw new IllegalArgumentException(e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final GenericWindowPolicyController createWindowPolicyControllerLocked(Set set) {
        boolean z =
                !android.companion.virtual.flags.Flags.dynamicPolicy()
                        ? this.mParams.getDefaultActivityPolicy() != 0
                        : getDevicePolicy(3) != 0;
        boolean z2 = this.mParams.getDefaultNavigationPolicy() == 0;
        boolean z3 = getDevicePolicy(2) == 0;
        ComponentName homeComponent =
                android.companion.virtual.flags.Flags.vdmCustomHome()
                        ? this.mParams.getHomeComponent()
                        : null;
        AttributionSource attributionSource = this.mAttributionSource;
        ArraySet arraySet = new ArraySet();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            DevicePolicyManager devicePolicyManager =
                    (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
            for (UserHandle userHandle :
                    ((UserManager) this.mContext.getSystemService(UserManager.class))
                            .getAllProfiles()) {
                int nearbyAppStreamingPolicy =
                        devicePolicyManager.getNearbyAppStreamingPolicy(userHandle.getIdentifier());
                if (nearbyAppStreamingPolicy != 2 && nearbyAppStreamingPolicy != 0) {
                    if (nearbyAppStreamingPolicy == 3
                            && this.mParams.getUsersWithMatchingAccounts().contains(userHandle)) {
                        arraySet.add(userHandle);
                    }
                }
                arraySet.add(userHandle);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            GenericWindowPolicyController genericWindowPolicyController =
                    new GenericWindowPolicyController(
                            attributionSource,
                            arraySet,
                            z,
                            this.mActivityPolicyExemptions,
                            z2,
                            z2
                                    ? this.mParams.getBlockedCrossTaskNavigations()
                                    : this.mParams.getAllowedCrossTaskNavigations(),
                            this.mPermissionDialogComponent,
                            new VirtualDeviceManager
                                    .ActivityListener() { // from class:
                                                          // com.android.server.companion.virtual.VirtualDeviceImpl.1
                                public final void onDisplayEmpty(int i) {
                                    try {
                                        VirtualDeviceImpl.this.mActivityListener.onDisplayEmpty(i);
                                    } catch (RemoteException e) {
                                        Slog.w(
                                                "VirtualDeviceImpl",
                                                "Unable to call mActivityListener for display: "
                                                        + i,
                                                e);
                                    }
                                }

                                public final void onTopActivityChanged(
                                        int i, ComponentName componentName) {
                                    try {
                                        VirtualDeviceImpl.this.mActivityListener
                                                .onTopActivityChanged(i, componentName, -10000);
                                    } catch (RemoteException e) {
                                        Slog.w(
                                                "VirtualDeviceImpl",
                                                "Unable to call mActivityListener for display: "
                                                        + i,
                                                e);
                                    }
                                }

                                public final void onTopActivityChanged(
                                        int i, ComponentName componentName, int i2) {
                                    try {
                                        VirtualDeviceImpl.this.mActivityListener
                                                .onTopActivityChanged(i, componentName, i2);
                                    } catch (RemoteException e) {
                                        Slog.w(
                                                "VirtualDeviceImpl",
                                                "Unable to call mActivityListener for display: "
                                                        + i,
                                                e);
                                    }
                                }
                            },
                            new VirtualDeviceImpl$$ExternalSyntheticLambda2(this),
                            new VirtualDeviceImpl$$ExternalSyntheticLambda2(this),
                            new VirtualDeviceImpl$$ExternalSyntheticLambda2(this),
                            new VirtualDeviceImpl$$ExternalSyntheticLambda2(this),
                            set,
                            z3,
                            homeComponent);
            synchronized (genericWindowPolicyController.mGenericWindowPolicyControllerLock) {
                genericWindowPolicyController.mRunningAppsChangedListeners.add(this);
            }
            return genericWindowPolicyController;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                printWriter, "  VirtualDevice: ", "    mDeviceId: "),
                        this.mDeviceId,
                        printWriter,
                        "    mAssociationId: ");
        m.append(this.mAssociationInfo.getId());
        printWriter.println(m.toString());
        printWriter.println("    mOwnerPackageName: " + this.mOwnerPackageName);
        printWriter.println("    mParams: ");
        this.mParams.dump(printWriter, "        ");
        printWriter.println("    mVirtualDisplayIds: ");
        synchronized (this.mVirtualDeviceLock) {
            for (int i2 = 0; i2 < this.mVirtualDisplays.size(); i2++) {
                try {
                    printWriter.println("      " + this.mVirtualDisplays.keyAt(i2));
                } finally {
                }
            }
            printWriter.println("    mDevicePolicies: " + this.mDevicePolicies);
            printWriter.println("    mDefaultShowPointerIcon: " + this.mDefaultShowPointerIcon);
        }
        InputController inputController = this.mInputController;
        inputController.getClass();
        printWriter.println("    InputController: ");
        synchronized (inputController.mLock) {
            try {
                printWriter.println("      Active descriptors: ");
                for (i = 0; i < inputController.mInputDeviceDescriptors.size(); i++) {
                    InputController.InputDeviceDescriptor inputDeviceDescriptor =
                            (InputController.InputDeviceDescriptor)
                                    inputController.mInputDeviceDescriptors.valueAt(i);
                    printWriter.println("        ptr: " + inputDeviceDescriptor.mPtr);
                    printWriter.println("          displayId: " + inputDeviceDescriptor.mDisplayId);
                    printWriter.println(
                            "          creationOrder: "
                                    + inputDeviceDescriptor.mCreationOrderNumber);
                    printWriter.println("          type: " + inputDeviceDescriptor.mType);
                    printWriter.println("          phys: " + inputDeviceDescriptor.mPhys);
                    printWriter.println(
                            "          inputDeviceId: " + inputDeviceDescriptor.mInputDeviceId);
                }
            } finally {
            }
        }
        SensorController sensorController = this.mSensorController;
        sensorController.getClass();
        printWriter.println("    SensorController: ");
        synchronized (sensorController.mLock) {
            try {
                printWriter.println("      Active descriptors: ");
                for (SensorController.SensorDescriptor sensorDescriptor :
                        sensorController.mSensorDescriptors.values()) {
                    printWriter.println("        handle: " + sensorDescriptor.mHandle);
                    printWriter.println("          type: " + sensorDescriptor.mType);
                    printWriter.println("          name: " + sensorDescriptor.mName);
                }
            } finally {
            }
        }
        VirtualCameraController virtualCameraController = this.mVirtualCameraController;
        if (virtualCameraController != null) {
            virtualCameraController.getClass();
            printWriter.println("    VirtualCameraController:");
            synchronized (virtualCameraController.mCameras) {
                try {
                    printWriter.println(
                            "        Registered cameras: "
                                    + ((ArrayMap) virtualCameraController.mCameras).size());
                    Iterator it = ((ArrayMap) virtualCameraController.mCameras).values().iterator();
                    while (it.hasNext()) {
                        printWriter.println(
                                "         token: "
                                        + ((VirtualCameraController.CameraDescriptor) it.next())
                                                .mConfig);
                    }
                } finally {
                }
            }
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                new StringBuilder("    hasCustomAudioInputSupport: "),
                hasCustomAudioInputSupportInternal(),
                printWriter);
    }

    public final int getAssociationId() {
        return this.mAssociationInfo.getId();
    }

    public final PointF getCursorPosition(IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mInputController.getCursorPosition(iBinder);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getDeviceId() {
        return this.mDeviceId;
    }

    public final int getDevicePolicy(int i) {
        int i2;
        if (!android.companion.virtual.flags.Flags.dynamicPolicy()) {
            return this.mParams.getDevicePolicy(i);
        }
        synchronized (this.mVirtualDeviceLock) {
            i2 = this.mDevicePolicies.get(i, 0);
        }
        return i2;
    }

    public final int[] getDisplayIds() {
        int[] iArr;
        synchronized (this.mVirtualDeviceLock) {
            try {
                int size = this.mVirtualDisplays.size();
                iArr = new int[size];
                for (int i = 0; i < size; i++) {
                    iArr[i] = this.mVirtualDisplays.keyAt(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return iArr;
    }

    public GenericWindowPolicyController getDisplayWindowPolicyControllerForTest(int i) {
        VirtualDisplayWrapper virtualDisplayWrapper;
        synchronized (this.mVirtualDeviceLock) {
            virtualDisplayWrapper = (VirtualDisplayWrapper) this.mVirtualDisplays.get(i);
        }
        if (virtualDisplayWrapper != null) {
            return virtualDisplayWrapper.mWindowPolicyController;
        }
        return null;
    }

    public final int getInputDeviceId(IBinder iBinder) {
        int i;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            InputController inputController = this.mInputController;
            synchronized (inputController.mLock) {
                InputController.InputDeviceDescriptor inputDeviceDescriptor =
                        (InputController.InputDeviceDescriptor)
                                inputController.mInputDeviceDescriptors.get(iBinder);
                if (inputDeviceDescriptor == null) {
                    throw new IllegalArgumentException("Could not get device id for given token");
                }
                i = inputDeviceDescriptor.mInputDeviceId;
            }
            return i;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final String getPersistentDeviceId() {
        return this.mPersistentDeviceId;
    }

    public SensorController getSensorControllerForTest() {
        return this.mSensorController;
    }

    public VirtualAudioController getVirtualAudioControllerForTesting() {
        return this.mVirtualAudioController;
    }

    public final String getVirtualCameraId(VirtualCameraConfig virtualCameraConfig) {
        String cameraId;
        getVirtualCameraId_enforcePermission();
        Objects.requireNonNull(virtualCameraConfig);
        VirtualCameraController virtualCameraController = this.mVirtualCameraController;
        if (virtualCameraController == null) {
            throw new UnsupportedOperationException("Virtual camera controller is not available");
        }
        virtualCameraController.connectVirtualCameraServiceIfNeeded();
        try {
            synchronized (virtualCameraController.mServiceLock) {
                cameraId =
                        ((IVirtualCameraService.Stub.Proxy)
                                        virtualCameraController.mVirtualCameraService)
                                .getCameraId(virtualCameraConfig.getCallback().asBinder());
            }
            return cameraId;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final List getVirtualSensorList() {
        List list;
        getVirtualSensorList_enforcePermission();
        SensorController sensorController = this.mSensorController;
        synchronized (sensorController.mLock) {
            try {
                if (sensorController.mVirtualSensorList == null) {
                    sensorController.mVirtualSensorList =
                            new ArrayList(sensorController.mVirtualSensors.size());
                    for (int i = 0; i < sensorController.mVirtualSensors.size(); i++) {
                        sensorController.mVirtualSensorList.add(
                                (VirtualSensor) sensorController.mVirtualSensors.valueAt(i));
                    }
                    sensorController.mVirtualSensorList =
                            Collections.unmodifiableList(sensorController.mVirtualSensorList);
                }
                list = sensorController.mVirtualSensorList;
            } catch (Throwable th) {
                throw th;
            }
        }
        return list;
    }

    public final boolean hasCustomAudioInputSupport() {
        return hasCustomAudioInputSupportInternal();
    }

    public final boolean hasCustomAudioInputSupportInternal() {
        if (!android.companion.virtual.flags.Flags.vdmPublicApis()
                || !android.media.audiopolicy.Flags.audioMixTestApi()
                || !android.media.audiopolicy.Flags.recordAudioDeviceAwarePermission()) {
            return false;
        }
        if (getDevicePolicy(1) == 1) {
            return true;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            for (AudioMix audioMix :
                    ((AudioManager) this.mContext.getSystemService(AudioManager.class))
                            .getRegisteredPolicyMixes()) {
                if (audioMix.matchesVirtualDeviceId(this.mDeviceId) && audioMix.getMixType() == 1) {
                    return true;
                }
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isAppRunningOnVirtualDevice(int i) {
        boolean contains;
        synchronized (this.mVirtualDeviceLock) {
            for (int i2 = 0; i2 < this.mVirtualDisplays.size(); i2++) {
                GenericWindowPolicyController genericWindowPolicyController =
                        ((VirtualDisplayWrapper) this.mVirtualDisplays.valueAt(i2))
                                .mWindowPolicyController;
                synchronized (genericWindowPolicyController.mGenericWindowPolicyControllerLock) {
                    contains =
                            genericWindowPolicyController.mRunningUids.contains(Integer.valueOf(i));
                }
                if (contains) {
                    return true;
                }
            }
            return false;
        }
    }

    public final void launchPendingIntent(
            int i, PendingIntent pendingIntent, ResultReceiver resultReceiver) {
        Objects.requireNonNull(pendingIntent);
        synchronized (this.mVirtualDeviceLock) {
            if (!this.mVirtualDisplays.contains(i)) {
                throw new SecurityException(
                        "Display ID " + i + " not found for this virtual device");
            }
        }
        if (pendingIntent.isActivity()) {
            try {
                ActivityOptions launchDisplayId = ActivityOptions.makeBasic().setLaunchDisplayId(i);
                launchDisplayId.setPendingIntentBackgroundActivityLaunchAllowed(true);
                launchDisplayId.setPendingIntentBackgroundActivityLaunchAllowedByPermission(true);
                pendingIntent.send(
                        this.mContext, 0, null, null, null, null, launchDisplayId.toBundle());
                resultReceiver.send(0, null);
                return;
            } catch (PendingIntent.CanceledException e) {
                Slog.w("VirtualDeviceImpl", "Pending intent canceled", e);
                resultReceiver.send(1, null);
                return;
            }
        }
        final PendingTrampoline pendingTrampoline =
                new PendingTrampoline(i, pendingIntent, resultReceiver);
        VirtualDeviceManagerService.PendingTrampolineMap pendingTrampolineMap =
                VirtualDeviceManagerService.this.mPendingTrampolines;
        PendingTrampoline pendingTrampoline2 =
                (PendingTrampoline)
                        pendingTrampolineMap.mMap.put(
                                pendingIntent.getCreatorPackage(), pendingTrampoline);
        Handler handler = pendingTrampolineMap.mHandler;
        handler.removeCallbacksAndMessages(pendingTrampoline2);
        handler.postDelayed(
                new VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda0(
                        2, pendingTrampolineMap, pendingTrampoline),
                pendingTrampoline,
                5000L);
        if (pendingTrampoline2 != null) {
            pendingTrampoline2.mResultReceiver.send(2, null);
        }
        this.mContext
                .getMainThreadHandler()
                .postDelayed(
                        new Runnable() { // from class:
                                         // com.android.server.companion.virtual.VirtualDeviceImpl$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                VirtualDeviceImpl virtualDeviceImpl = VirtualDeviceImpl.this;
                                VirtualDeviceImpl.PendingTrampoline pendingTrampoline3 =
                                        pendingTrampoline;
                                int i2 = VirtualDeviceImpl.$r8$clinit;
                                virtualDeviceImpl.getClass();
                                pendingTrampoline3.mResultReceiver.send(2, null);
                                VirtualDeviceManagerService.PendingTrampolineMap
                                        pendingTrampolineMap2 =
                                                VirtualDeviceManagerService.this
                                                        .mPendingTrampolines;
                                pendingTrampolineMap2.mHandler.removeCallbacksAndMessages(
                                        (VirtualDeviceImpl.PendingTrampoline)
                                                pendingTrampolineMap2.mMap.remove(
                                                        pendingTrampoline3.mPendingIntent
                                                                .getCreatorPackage()));
                            }
                        },
                        5000L);
        try {
            ActivityOptions launchDisplayId2 = ActivityOptions.makeBasic().setLaunchDisplayId(i);
            launchDisplayId2.setPendingIntentBackgroundActivityLaunchAllowed(true);
            launchDisplayId2.setPendingIntentBackgroundActivityLaunchAllowedByPermission(true);
            pendingIntent.send(
                    this.mContext, 0, null, null, null, null, launchDisplayId2.toBundle());
        } catch (PendingIntent.CanceledException e2) {
            Slog.w("VirtualDeviceImpl", "Pending intent canceled", e2);
            resultReceiver.send(1, null);
            VirtualDeviceManagerService.PendingTrampolineMap pendingTrampolineMap2 =
                    VirtualDeviceManagerService.this.mPendingTrampolines;
            pendingTrampolineMap2.mHandler.removeCallbacksAndMessages(
                    (PendingTrampoline)
                            pendingTrampolineMap2.mMap.remove(
                                    pendingTrampoline.mPendingIntent.getCreatorPackage()));
        }
    }

    public final void onAudioSessionEnded() {
        onAudioSessionEnded_enforcePermission();
        synchronized (this.mVirtualDeviceLock) {
            try {
                VirtualAudioController virtualAudioController = this.mVirtualAudioController;
                if (virtualAudioController != null) {
                    virtualAudioController.stopListening();
                    this.mVirtualAudioController = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onAudioSessionStarting(
            int i,
            IAudioRoutingCallback iAudioRoutingCallback,
            IAudioConfigChangedCallback iAudioConfigChangedCallback) {
        onAudioSessionStarting_enforcePermission();
        synchronized (this.mVirtualDeviceLock) {
            try {
                if (!this.mVirtualDisplays.contains(i)) {
                    throw new SecurityException(
                            "Cannot start audio session for a display not associated with this"
                                + " virtual device");
                }
                if (this.mVirtualAudioController == null) {
                    this.mVirtualAudioController =
                            new VirtualAudioController(this.mContext, this.mAttributionSource);
                    this.mVirtualAudioController.startListening(
                            ((VirtualDisplayWrapper) this.mVirtualDisplays.get(i))
                                    .mWindowPolicyController,
                            iAudioRoutingCallback,
                            iAudioConfigChangedCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.companion.virtual.GenericWindowPolicyController.RunningAppsChangedListener
    public final void onRunningAppsChanged(ArraySet arraySet) {
        CameraAccessController cameraAccessController = this.mCameraAccessController;
        if (cameraAccessController != null) {
            synchronized (cameraAccessController.mLock) {
                for (int i = 0;
                        i < cameraAccessController.mAppsToBlockOnVirtualDevice.size();
                        i++) {
                    try {
                        String str =
                                (String)
                                        cameraAccessController.mAppsToBlockOnVirtualDevice.keyAt(i);
                        CameraAccessController.OpenCameraInfo openCameraInfo =
                                (CameraAccessController.OpenCameraInfo)
                                        cameraAccessController.mAppsToBlockOnVirtualDevice.get(str);
                        String str2 = openCameraInfo.packageName;
                        Iterator it = ((ArraySet) openCameraInfo.packageUids).iterator();
                        while (true) {
                            if (it.hasNext()) {
                                Integer num = (Integer) it.next();
                                int intValue = num.intValue();
                                if (arraySet.contains(num)) {
                                    if (((CameraAccessController.InjectionSessionData)
                                                    cameraAccessController.mPackageToSessionData
                                                            .get(str2))
                                            == null) {
                                        CameraAccessController.InjectionSessionData
                                                injectionSessionData =
                                                        new CameraAccessController
                                                                .InjectionSessionData();
                                        injectionSessionData.appUid = intValue;
                                        cameraAccessController.mPackageToSessionData.put(
                                                str2, injectionSessionData);
                                    }
                                    cameraAccessController.startBlocking(str2, str);
                                }
                            }
                        }
                    } finally {
                    }
                }
            }
        }
        this.mRunningAppsChangedCallback.accept(arraySet);
    }

    public final void registerIntentInterceptor(
            IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor,
            IntentFilter intentFilter) {
        registerIntentInterceptor_enforcePermission();
        Objects.requireNonNull(iVirtualDeviceIntentInterceptor);
        Objects.requireNonNull(intentFilter);
        synchronized (this.mVirtualDeviceLock) {
            ((ArrayMap) this.mIntentInterceptors)
                    .put(iVirtualDeviceIntentInterceptor.asBinder(), intentFilter);
        }
    }

    public final void registerVirtualCamera(VirtualCameraConfig virtualCameraConfig) {
        registerVirtualCamera_enforcePermission();
        Objects.requireNonNull(virtualCameraConfig);
        VirtualCameraController virtualCameraController = this.mVirtualCameraController;
        if (virtualCameraController == null) {
            throw new UnsupportedOperationException("Virtual camera controller is not available");
        }
        AttributionSource attributionSource = this.mAttributionSource;
        if (virtualCameraController.mCameraPolicy == 0) {
            throw new IllegalArgumentException(
                    "Cannot create virtual camera with DEVICE_POLICY_DEFAULT for"
                        + " POLICY_TYPE_CAMERA");
        }
        int lensFacing = virtualCameraConfig.getLensFacing();
        synchronized (virtualCameraController.mCameras) {
            Iterator it = ((ArrayMap) virtualCameraController.mCameras).values().iterator();
            while (it.hasNext()) {
                if (((VirtualCameraController.CameraDescriptor) it.next()).mConfig.getLensFacing()
                        == lensFacing) {
                    throw new IllegalArgumentException(
                            "Only a single virtual camera can be created with lens facing "
                                    + virtualCameraConfig.getLensFacing());
                }
            }
            virtualCameraController.connectVirtualCameraServiceIfNeeded();
            try {
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (!virtualCameraController.registerCameraWithService(virtualCameraConfig)) {
                throw new RuntimeException("Failed to register virtual camera.");
            }
            VirtualCameraController.CameraDescriptor cameraDescriptor =
                    virtualCameraController.new CameraDescriptor(virtualCameraConfig);
            IBinder asBinder = virtualCameraConfig.getCallback().asBinder();
            asBinder.linkToDeath(cameraDescriptor, 0);
            synchronized (virtualCameraController.mCameras) {
                ((ArrayMap) virtualCameraController.mCameras).put(asBinder, cameraDescriptor);
            }
            if (android.companion.virtualdevice.flags.Flags.metricsCollection()) {
                Counter.logIncrementWithUid(
                        "virtual_devices.value_virtual_camera_created_count",
                        attributionSource.getUid());
            }
        }
    }

    public final void removeActivityPolicyExemption(ComponentName componentName) {
        removeActivityPolicyExemption_enforcePermission();
        synchronized (this.mVirtualDeviceLock) {
            try {
                if (this.mActivityPolicyExemptions.remove(componentName)) {
                    for (int i = 0; i < this.mVirtualDisplays.size(); i++) {
                        GenericWindowPolicyController genericWindowPolicyController =
                                ((VirtualDisplayWrapper) this.mVirtualDisplays.valueAt(i))
                                        .mWindowPolicyController;
                        synchronized (
                                genericWindowPolicyController.mGenericWindowPolicyControllerLock) {
                            genericWindowPolicyController.mActivityPolicyExemptions.remove(
                                    componentName);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean sendButtonEvent(
            IBinder iBinder, VirtualMouseButtonEvent virtualMouseButtonEvent) {
        sendButtonEvent_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendButtonEvent(iBinder, virtualMouseButtonEvent);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean sendDpadKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) {
        sendDpadKeyEvent_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendDpadKeyEvent(iBinder, virtualKeyEvent);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean sendKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) {
        sendKeyEvent_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendKeyEvent(iBinder, virtualKeyEvent);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean sendRelativeEvent(
            IBinder iBinder, VirtualMouseRelativeEvent virtualMouseRelativeEvent) {
        sendRelativeEvent_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendRelativeEvent(iBinder, virtualMouseRelativeEvent);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean sendScrollEvent(
            IBinder iBinder, VirtualMouseScrollEvent virtualMouseScrollEvent) {
        sendScrollEvent_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendScrollEvent(iBinder, virtualMouseScrollEvent);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean sendSensorEvent(IBinder iBinder, VirtualSensorEvent virtualSensorEvent) {
        sendSensorEvent_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mSensorController.sendSensorEvent(iBinder, virtualSensorEvent);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean sendStylusButtonEvent(
            IBinder iBinder, VirtualStylusButtonEvent virtualStylusButtonEvent) {
        sendStylusButtonEvent_enforcePermission();
        Objects.requireNonNull(iBinder);
        Objects.requireNonNull(virtualStylusButtonEvent);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendStylusButtonEvent(iBinder, virtualStylusButtonEvent);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean sendStylusMotionEvent(
            IBinder iBinder, VirtualStylusMotionEvent virtualStylusMotionEvent) {
        sendStylusMotionEvent_enforcePermission();
        Objects.requireNonNull(iBinder);
        Objects.requireNonNull(virtualStylusMotionEvent);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendStylusMotionEvent(iBinder, virtualStylusMotionEvent);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean sendTouchEvent(IBinder iBinder, VirtualTouchEvent virtualTouchEvent) {
        sendTouchEvent_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mInputController.sendTouchEvent(iBinder, virtualTouchEvent);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x006e, code lost:

       r7 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0073, code lost:

       throw r7;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setDevicePolicy(int r8, int r9) {
        /*
            r7 = this;
            r7.setDevicePolicy_enforcePermission()
            boolean r0 = android.companion.virtual.flags.Flags.dynamicPolicy()
            if (r0 != 0) goto La
            return
        La:
            r0 = 2
            r1 = 1
            r2 = 0
            if (r8 == r0) goto L74
            r0 = 3
            if (r8 == r0) goto L37
            r0 = 4
            if (r8 != r0) goto L29
            boolean r0 = android.companion.virtual.flags.Flags.crossDeviceClipboard()
            if (r0 == 0) goto L9d
            java.lang.Object r0 = r7.mVirtualDeviceLock
            monitor-enter(r0)
            android.util.SparseIntArray r7 = r7.mDevicePolicies     // Catch: java.lang.Throwable -> L26
            r7.put(r8, r9)     // Catch: java.lang.Throwable -> L26
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L26
            goto L9d
        L26:
            r7 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L26
            throw r7
        L29:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r9 = "Device policy "
            java.lang.String r0 = " cannot be changed at runtime. "
            java.lang.String r8 = com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(r8, r9, r0)
            r7.<init>(r8)
            throw r7
        L37:
            java.lang.Object r0 = r7.mVirtualDeviceLock
            monitor-enter(r0)
            android.util.SparseIntArray r3 = r7.mDevicePolicies     // Catch: java.lang.Throwable -> L6e
            r3.put(r8, r9)     // Catch: java.lang.Throwable -> L6e
            r8 = r2
        L40:
            android.util.SparseArray r3 = r7.mVirtualDisplays     // Catch: java.lang.Throwable -> L6e
            int r3 = r3.size()     // Catch: java.lang.Throwable -> L6e
            if (r8 >= r3) goto L70
            android.util.SparseArray r3 = r7.mVirtualDisplays     // Catch: java.lang.Throwable -> L6e
            java.lang.Object r3 = r3.valueAt(r8)     // Catch: java.lang.Throwable -> L6e
            com.android.server.companion.virtual.VirtualDeviceImpl$VirtualDisplayWrapper r3 = (com.android.server.companion.virtual.VirtualDeviceImpl.VirtualDisplayWrapper) r3     // Catch: java.lang.Throwable -> L6e
            com.android.server.companion.virtual.GenericWindowPolicyController r3 = r3.mWindowPolicyController     // Catch: java.lang.Throwable -> L6e
            if (r9 != 0) goto L56
            r4 = r1
            goto L57
        L56:
            r4 = r2
        L57:
            java.lang.Object r5 = r3.mGenericWindowPolicyControllerLock     // Catch: java.lang.Throwable -> L6e
            monitor-enter(r5)     // Catch: java.lang.Throwable -> L6e
            boolean r6 = r3.mActivityLaunchAllowedByDefault     // Catch: java.lang.Throwable -> L64
            if (r6 == r4) goto L66
            java.util.Set r6 = r3.mActivityPolicyExemptions     // Catch: java.lang.Throwable -> L64
            r6.clear()     // Catch: java.lang.Throwable -> L64
            goto L66
        L64:
            r7 = move-exception
            goto L6c
        L66:
            r3.mActivityLaunchAllowedByDefault = r4     // Catch: java.lang.Throwable -> L64
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L64
            int r8 = r8 + 1
            goto L40
        L6c:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L64
            throw r7     // Catch: java.lang.Throwable -> L6e
        L6e:
            r7 = move-exception
            goto L72
        L70:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6e
            goto L9d
        L72:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6e
            throw r7
        L74:
            java.lang.Object r0 = r7.mVirtualDeviceLock
            monitor-enter(r0)
            android.util.SparseIntArray r3 = r7.mDevicePolicies     // Catch: java.lang.Throwable -> L9a
            r3.put(r8, r9)     // Catch: java.lang.Throwable -> L9a
            r8 = r2
        L7d:
            android.util.SparseArray r3 = r7.mVirtualDisplays     // Catch: java.lang.Throwable -> L9a
            int r3 = r3.size()     // Catch: java.lang.Throwable -> L9a
            if (r8 >= r3) goto L9c
            android.util.SparseArray r3 = r7.mVirtualDisplays     // Catch: java.lang.Throwable -> L9a
            java.lang.Object r3 = r3.valueAt(r8)     // Catch: java.lang.Throwable -> L9a
            com.android.server.companion.virtual.VirtualDeviceImpl$VirtualDisplayWrapper r3 = (com.android.server.companion.virtual.VirtualDeviceImpl.VirtualDisplayWrapper) r3     // Catch: java.lang.Throwable -> L9a
            com.android.server.companion.virtual.GenericWindowPolicyController r3 = r3.mWindowPolicyController     // Catch: java.lang.Throwable -> L9a
            if (r9 != 0) goto L93
            r4 = r1
            goto L94
        L93:
            r4 = r2
        L94:
            r3.setShowInHostDeviceRecents(r4)     // Catch: java.lang.Throwable -> L9a
            int r8 = r8 + 1
            goto L7d
        L9a:
            r7 = move-exception
            goto L9e
        L9c:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L9a
        L9d:
            return
        L9e:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L9a
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.companion.virtual.VirtualDeviceImpl.setDevicePolicy(int,"
                    + " int):void");
    }

    public final void setDisplayImePolicy(int i, int i2) {
        setDisplayImePolicy_enforcePermission();
        synchronized (this.mVirtualDeviceLock) {
            if (!this.mVirtualDisplays.contains(i)) {
                throw new SecurityException(
                        "Display ID " + i + " not found for this virtual device");
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mInputController.mWindowManager.setDisplayImePolicy(i, i2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setShowPointerIcon(boolean z) {
        setShowPointerIcon_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mVirtualDeviceLock) {
                this.mDefaultShowPointerIcon = z;
            }
            for (int i : getDisplayIds()) {
                InputManagerService.LocalService localService =
                        this.mInputController.mInputManagerInternal;
                localService.getClass();
                boolean z2 = InputManagerService.DEBUG;
                InputManagerService.this.setPointerIconVisible(z, i);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void showToastWhereUidIsRunning(int i, String str, Looper looper) {
        int i2;
        boolean contains;
        IntArray intArray = new IntArray();
        synchronized (this.mVirtualDeviceLock) {
            for (int i3 = 0; i3 < this.mVirtualDisplays.size(); i3++) {
                GenericWindowPolicyController genericWindowPolicyController =
                        ((VirtualDisplayWrapper) this.mVirtualDisplays.valueAt(i3))
                                .mWindowPolicyController;
                synchronized (genericWindowPolicyController.mGenericWindowPolicyControllerLock) {
                    contains =
                            genericWindowPolicyController.mRunningUids.contains(Integer.valueOf(i));
                }
                if (contains) {
                    intArray.add(this.mVirtualDisplays.keyAt(i3));
                }
            }
        }
        if (intArray.size() == 0) {
            return;
        }
        DisplayManager displayManager =
                (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
        for (i2 = 0; i2 < intArray.size(); i2++) {
            Display display = displayManager.getDisplay(intArray.get(i2));
            if (display != null && display.isValid()) {
                Toast.makeText(this.mContext.createDisplayContext(display), looper, str, 1).show();
            }
        }
    }

    public final void unregisterInputDevice(IBinder iBinder) {
        unregisterInputDevice_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mInputController.unregisterInputDevice(iBinder);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unregisterIntentInterceptor(
            IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor) {
        unregisterIntentInterceptor_enforcePermission();
        Objects.requireNonNull(iVirtualDeviceIntentInterceptor);
        synchronized (this.mVirtualDeviceLock) {
            ((ArrayMap) this.mIntentInterceptors)
                    .remove(iVirtualDeviceIntentInterceptor.asBinder());
        }
    }

    public final void unregisterVirtualCamera(VirtualCameraConfig virtualCameraConfig) {
        unregisterVirtualCamera_enforcePermission();
        Objects.requireNonNull(virtualCameraConfig);
        VirtualCameraController virtualCameraController = this.mVirtualCameraController;
        if (virtualCameraController == null) {
            throw new UnsupportedOperationException("Virtual camera controller is not available");
        }
        virtualCameraController.unregisterCamera(virtualCameraConfig);
    }
}
