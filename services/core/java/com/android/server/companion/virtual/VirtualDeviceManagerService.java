package com.android.server.companion.virtual;

import android.R;
import android.app.ActivityOptions;
import android.companion.AssociationInfo;
import android.companion.CompanionDeviceManager;
import android.companion.virtual.IVirtualDevice;
import android.companion.virtual.IVirtualDeviceListener;
import android.companion.virtual.IVirtualDeviceManager;
import android.companion.virtual.VirtualDevice;
import android.companion.virtual.sensor.VirtualSensor;
import android.companion.virtualnative.IVirtualDeviceManagerNative;
import android.content.Context;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.VirtualDisplayConfig;
import android.os.Binder;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.ExceptionUtils;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.DumpUtils;
import com.android.modules.expresslog.Counter;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda4;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.input.InputManagerService;
import com.android.server.wm.ActivityInterceptorCallback;
import com.android.server.wm.ActivityTaskManagerInternal;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class VirtualDeviceManagerService extends SystemService {
    public static final List VIRTUAL_DEVICE_COMPANION_DEVICE_PROFILES =
            Arrays.asList(
                    "android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION",
                    "android.app.role.COMPANION_DEVICE_APP_STREAMING",
                    "android.app.role.COMPANION_DEVICE_NEARBY_DEVICE_STREAMING");
    public static final AtomicInteger sNextUniqueIndex = new AtomicInteger(1);
    public ArrayMap mActiveAssociations;
    public final AnonymousClass2 mActivityInterceptorCallback;
    public final SparseArray mAppsOnVirtualDevices;
    public final AnonymousClass1 mCdmAssociationListener;
    public final Handler mHandler;
    public final VirtualDeviceManagerImpl mImpl;
    public final LocalService mLocalService;
    public final VirtualDeviceManagerNativeImpl mNativeImpl;
    public final PendingTrampolineMap mPendingTrampolines;
    public final RemoteCallbackList mVirtualDeviceListeners;
    public final VirtualDeviceLog mVirtualDeviceLog;
    public final Object mVirtualDeviceManagerLock;
    public final SparseArray mVirtualDevices;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends VirtualDeviceManagerInternal {
        public final ArrayList mAppsOnVirtualDeviceListeners = new ArrayList();
        public final ArrayList mPersistentDeviceIdRemovedListeners = new ArrayList();
        public final ArraySet mAllUidsOnVirtualDevice = new ArraySet();

        public LocalService() {}

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public final Set getAllPersistentDeviceIds() {
            Set copyOf;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                copyOf = Set.copyOf(VirtualDeviceManagerService.this.mActiveAssociations.keySet());
            }
            return copyOf;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public final int getBaseVirtualDisplayFlags(IVirtualDevice iVirtualDevice) {
            return ((VirtualDeviceImpl) iVirtualDevice).mBaseVirtualDisplayFlags;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public final ArraySet getDeviceIdsForUid(int i) {
            ArrayList virtualDevicesSnapshot =
                    VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
            ArraySet arraySet = new ArraySet();
            for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
                VirtualDeviceImpl virtualDeviceImpl =
                        (VirtualDeviceImpl) virtualDevicesSnapshot.get(i2);
                if (virtualDeviceImpl.isAppRunningOnVirtualDevice(i)) {
                    arraySet.add(Integer.valueOf(virtualDeviceImpl.mDeviceId));
                }
            }
            return arraySet;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public final int getDeviceOwnerUid(int i) {
            VirtualDeviceImpl virtualDeviceImpl;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl =
                        (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
            }
            if (virtualDeviceImpl != null) {
                return virtualDeviceImpl.mOwnerUid;
            }
            return -1;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public final ArraySet getDisplayIdsForDevice(int i) {
            VirtualDeviceImpl virtualDeviceImpl;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl =
                        (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
            }
            return virtualDeviceImpl == null
                    ? new ArraySet()
                    : (ArraySet)
                            Arrays.stream(virtualDeviceImpl.getDisplayIds())
                                    .boxed()
                                    .collect(
                                            Collectors.toCollection(
                                                    new VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda1()));
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public final String getPersistentIdForDevice(int i) {
            VirtualDeviceImpl virtualDeviceImpl;
            if (i == 0) {
                return "default:0";
            }
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl =
                        (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
            }
            if (virtualDeviceImpl == null) {
                return null;
            }
            return virtualDeviceImpl.mPersistentDeviceId;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public final LocaleList getPreferredLocaleListForUid(int i) {
            LocaleList localeList;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                for (int i2 = 0;
                        i2 < VirtualDeviceManagerService.this.mAppsOnVirtualDevices.size();
                        i2++) {
                    if (((ArraySet)
                                    VirtualDeviceManagerService.this.mAppsOnVirtualDevices.valueAt(
                                            i2))
                            .contains(Integer.valueOf(i))) {
                        VirtualDeviceImpl virtualDeviceImpl =
                                (VirtualDeviceImpl)
                                        VirtualDeviceManagerService.this.mVirtualDevices.get(
                                                VirtualDeviceManagerService.this
                                                        .mAppsOnVirtualDevices.keyAt(i2));
                        synchronized (virtualDeviceImpl.mVirtualDeviceLock) {
                            localeList = virtualDeviceImpl.mLocaleList;
                        }
                        return localeList;
                    }
                }
                return null;
            }
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public final VirtualSensor getVirtualSensor(int i, int i2) {
            VirtualDeviceImpl virtualDeviceImpl;
            VirtualSensor virtualSensor;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl =
                        (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
            }
            if (virtualDeviceImpl == null) {
                return null;
            }
            SensorController sensorController = virtualDeviceImpl.mSensorController;
            synchronized (sensorController.mLock) {
                virtualSensor = (VirtualSensor) sensorController.mVirtualSensors.get(i2);
            }
            return virtualSensor;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public final boolean isAppRunningOnAnyVirtualDevice(int i) {
            ArrayList virtualDevicesSnapshot =
                    VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
            for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
                if (((VirtualDeviceImpl) virtualDevicesSnapshot.get(i2))
                        .isAppRunningOnVirtualDevice(i)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public final boolean isInputDeviceOwnedByVirtualDevice(final int i) {
            ArrayList virtualDevicesSnapshot =
                    VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
            for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
                if (((VirtualDeviceImpl) virtualDevicesSnapshot.get(i2))
                        .mInputController.getInputDeviceDescriptors().values().stream()
                                .anyMatch(
                                        new Predicate() { // from class:
                                                          // com.android.server.companion.virtual.VirtualDeviceImpl$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                int i3 = i;
                                                int i4 = VirtualDeviceImpl.$r8$clinit;
                                                return ((InputController.InputDeviceDescriptor) obj)
                                                                .mInputDeviceId
                                                        == i3;
                                            }
                                        })) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public final void onAuthenticationPrompt(int i) {
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                for (int i2 = 0;
                        i2 < VirtualDeviceManagerService.this.mVirtualDevices.size();
                        i2++) {
                    try {
                        VirtualDeviceImpl virtualDeviceImpl =
                                (VirtualDeviceImpl)
                                        VirtualDeviceManagerService.this.mVirtualDevices.valueAt(
                                                i2);
                        virtualDeviceImpl.showToastWhereUidIsRunning(
                                i,
                                virtualDeviceImpl.mContext.getString(
                                        R.string.config_accountTypeToKeepFirstAccount),
                                Looper.getMainLooper());
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal
        public final void onVirtualDisplayRemoved(IVirtualDevice iVirtualDevice, int i) {
            VirtualDeviceImpl virtualDeviceImpl;
            VirtualDeviceImpl.VirtualDisplayWrapper virtualDisplayWrapper;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl =
                        (VirtualDeviceImpl)
                                VirtualDeviceManagerService.this.mVirtualDevices.get(
                                        ((VirtualDeviceImpl) iVirtualDevice).mDeviceId);
            }
            if (virtualDeviceImpl != null) {
                synchronized (virtualDeviceImpl.mVirtualDeviceLock) {
                    virtualDisplayWrapper =
                            (VirtualDeviceImpl.VirtualDisplayWrapper)
                                    virtualDeviceImpl.mVirtualDisplays.removeReturnOld(i);
                }
                if (virtualDisplayWrapper == null) {
                    Slog.w(
                            "VirtualDeviceImpl",
                            "Virtual device "
                                    + virtualDeviceImpl.mDeviceId
                                    + " doesn't have a virtual display with ID "
                                    + i);
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    virtualDisplayWrapper.mWakeLock.release();
                    virtualDisplayWrapper.mWindowPolicyController
                            .unregisterRunningAppsChangedListener(virtualDeviceImpl);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PendingTrampolineMap {
        public final Handler mHandler;
        public final ConcurrentHashMap mMap = new ConcurrentHashMap();

        public PendingTrampolineMap(Handler handler) {
            this.mHandler = handler;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VirtualDeviceManagerImpl extends IVirtualDeviceManager.Stub {
        public final AnonymousClass1 mPendingTrampolineCallback = new AnonymousClass1();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.companion.virtual.VirtualDeviceManagerService$VirtualDeviceManagerImpl$1, reason: invalid class name */
        public final class AnonymousClass1 implements VirtualDeviceImpl.PendingTrampolineCallback {
            public AnonymousClass1() {}
        }

        public VirtualDeviceManagerImpl() {}

        /* JADX WARN: Removed duplicated region for block: B:28:0x012b  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x0136 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r2v6, types: [com.android.server.companion.virtual.VirtualDeviceManagerService$VirtualDeviceManagerImpl$$ExternalSyntheticLambda0] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.companion.virtual.IVirtualDevice createVirtualDevice(
                android.os.IBinder r22,
                android.content.AttributionSource r23,
                int r24,
                android.companion.virtual.VirtualDeviceParams r25,
                android.companion.virtual.IVirtualDeviceActivityListener r26,
                android.companion.virtual.IVirtualDeviceSoundEffectListener r27) {
            /*
                Method dump skipped, instructions count: 448
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.companion.virtual.VirtualDeviceManagerService.VirtualDeviceManagerImpl.createVirtualDevice(android.os.IBinder,"
                        + " android.content.AttributionSource, int,"
                        + " android.companion.virtual.VirtualDeviceParams,"
                        + " android.companion.virtual.IVirtualDeviceActivityListener,"
                        + " android.companion.virtual.IVirtualDeviceSoundEffectListener):android.companion.virtual.IVirtualDevice");
        }

        public final int createVirtualDisplay(
                VirtualDisplayConfig virtualDisplayConfig,
                IVirtualDisplayCallback iVirtualDisplayCallback,
                IVirtualDevice iVirtualDevice,
                String str) {
            VirtualDeviceImpl virtualDeviceImpl;
            GenericWindowPolicyController createWindowPolicyControllerLocked;
            boolean z;
            Objects.requireNonNull(virtualDisplayConfig);
            int callingUid = IVirtualDeviceManager.Stub.getCallingUid();
            if (!PermissionUtils.validateCallingPackageName(
                    VirtualDeviceManagerService.this.getContext(), str)) {
                throw new SecurityException(
                        SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0
                                .m(
                                        callingUid,
                                        "Package name ",
                                        str,
                                        " does not belong to calling uid "));
            }
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl =
                        (VirtualDeviceImpl)
                                VirtualDeviceManagerService.this.mVirtualDevices.get(
                                        iVirtualDevice.getDeviceId());
                if (virtualDeviceImpl == null) {
                    throw new SecurityException(
                            "Invalid VirtualDevice (deviceId = "
                                    + iVirtualDevice.getDeviceId()
                                    + ")");
                }
            }
            if (virtualDeviceImpl.mOwnerUid != callingUid) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                callingUid,
                                "uid ",
                                " is not the owner of the supplied VirtualDevice (deviceId = ");
                m.append(iVirtualDevice.getDeviceId());
                m.append(")");
                throw new SecurityException(m.toString());
            }
            synchronized (virtualDeviceImpl.mVirtualDeviceLock) {
                createWindowPolicyControllerLocked =
                        virtualDeviceImpl.createWindowPolicyControllerLocked(
                                virtualDisplayConfig.getDisplayCategories());
            }
            int createVirtualDisplay =
                    virtualDeviceImpl.mDisplayManagerInternal.createVirtualDisplay(
                            virtualDisplayConfig,
                            iVirtualDisplayCallback,
                            virtualDeviceImpl,
                            createWindowPolicyControllerLocked,
                            str);
            boolean z2 =
                    android.companion.virtual.flags.Flags.interactiveScreenMirror()
                            && virtualDeviceImpl.mDisplayManagerInternal.getDisplayIdToMirror(
                                            createVirtualDisplay)
                                    != -1;
            createWindowPolicyControllerLocked.mDisplayId = createVirtualDisplay;
            createWindowPolicyControllerLocked.mIsMirrorDisplay = z2;
            createWindowPolicyControllerLocked.mDisplayIdSetLatch.countDown();
            synchronized (virtualDeviceImpl.mVirtualDeviceLock) {
                if (virtualDeviceImpl.mVirtualDisplays.contains(createVirtualDisplay)) {
                    createWindowPolicyControllerLocked.unregisterRunningAppsChangedListener(
                            virtualDeviceImpl);
                    throw new IllegalStateException(
                            "Virtual device already has a virtual display with ID "
                                    + createVirtualDisplay);
                }
                virtualDeviceImpl.mVirtualDisplays.put(
                        createVirtualDisplay,
                        new VirtualDeviceImpl.VirtualDisplayWrapper(
                                iVirtualDisplayCallback,
                                createWindowPolicyControllerLocked,
                                virtualDeviceImpl.createAndAcquireWakeLockForDisplay(
                                        createVirtualDisplay)));
                z = virtualDeviceImpl.mDefaultShowPointerIcon;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                InputManagerService.LocalService localService =
                        virtualDeviceImpl.mInputController.mInputManagerInternal;
                localService.getClass();
                boolean z3 = InputManagerService.DEBUG;
                InputManagerService.this.setPointerIconVisible(z, createVirtualDisplay);
                InputManagerService.LocalService localService2 =
                        virtualDeviceImpl.mInputController.mInputManagerInternal;
                localService2.getClass();
                InputManagerService.this.setMousePointerAccelerationEnabled(
                        false, createVirtualDisplay);
                InputManagerService.LocalService localService3 =
                        virtualDeviceImpl.mInputController.mInputManagerInternal;
                localService3.getClass();
                InputManagerService.this.setDisplayEligibilityForPointerCapture(
                        createVirtualDisplay, false);
                if ((virtualDeviceImpl.mDisplayManagerInternal.getDisplayInfo(createVirtualDisplay)
                                        .flags
                                & 128)
                        == 128) {
                    virtualDeviceImpl.mInputController.mWindowManager.setDisplayImePolicy(
                            createVirtualDisplay, 0);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (android.companion.virtualdevice.flags.Flags.metricsCollection()) {
                    Counter.logIncrementWithUid(
                            "virtual_devices.value_virtual_display_created_count",
                            virtualDeviceImpl.mAttributionSource.getUid());
                }
                return createVirtualDisplay;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpAndUsageStatsPermission(
                    VirtualDeviceManagerService.this.getContext(),
                    "VirtualDeviceManagerService",
                    printWriter)) {
                printWriter.println("Created virtual devices: ");
                ArrayList virtualDevicesSnapshot =
                        VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
                for (int i = 0; i < virtualDevicesSnapshot.size(); i++) {
                    ((VirtualDeviceImpl) virtualDevicesSnapshot.get(i))
                            .dump(fileDescriptor, printWriter, strArr);
                }
                VirtualDeviceLog virtualDeviceLog =
                        VirtualDeviceManagerService.this.mVirtualDeviceLog;
                virtualDeviceLog.getClass();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Flags.dumpHistory();
                    printWriter.println("VirtualDevice Log:");
                    VirtualDeviceLog.UidToPackageNameCache uidToPackageNameCache =
                            new VirtualDeviceLog.UidToPackageNameCache(
                                    virtualDeviceLog.mContext.getPackageManager());
                    Iterator it = virtualDeviceLog.mLogEntries.iterator();
                    while (it.hasNext()) {
                        ((VirtualDeviceLog.LogEntry) it.next())
                                .dump(printWriter, uidToPackageNameCache);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final List getAllPersistentDeviceIds() {
            return new ArrayList(
                    VirtualDeviceManagerService.this.mLocalService.getAllPersistentDeviceIds());
        }

        public final int getAudioPlaybackSessionId(int i) {
            int audioPlaybackSessionId;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                VirtualDeviceImpl virtualDeviceImpl =
                        (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
                audioPlaybackSessionId =
                        virtualDeviceImpl != null
                                ? virtualDeviceImpl.mParams.getAudioPlaybackSessionId()
                                : 0;
            }
            return audioPlaybackSessionId;
        }

        public final int getAudioRecordingSessionId(int i) {
            int audioRecordingSessionId;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                VirtualDeviceImpl virtualDeviceImpl =
                        (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
                audioRecordingSessionId =
                        virtualDeviceImpl != null
                                ? virtualDeviceImpl.mParams.getAudioRecordingSessionId()
                                : 0;
            }
            return audioRecordingSessionId;
        }

        public final int getDeviceIdForDisplayId(int i) {
            boolean contains;
            if (i != -1 && i != 0) {
                ArrayList virtualDevicesSnapshot =
                        VirtualDeviceManagerService.this.getVirtualDevicesSnapshot();
                for (int i2 = 0; i2 < virtualDevicesSnapshot.size(); i2++) {
                    VirtualDeviceImpl virtualDeviceImpl =
                            (VirtualDeviceImpl) virtualDevicesSnapshot.get(i2);
                    synchronized (virtualDeviceImpl.mVirtualDeviceLock) {
                        contains = virtualDeviceImpl.mVirtualDisplays.contains(i);
                    }
                    if (contains) {
                        return virtualDeviceImpl.mDeviceId;
                    }
                }
            }
            return 0;
        }

        public final int getDevicePolicy(int i, int i2) {
            int devicePolicy;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                try {
                    VirtualDeviceImpl virtualDeviceImpl =
                            (VirtualDeviceImpl)
                                    VirtualDeviceManagerService.this.mVirtualDevices.get(i);
                    devicePolicy =
                            virtualDeviceImpl != null ? virtualDeviceImpl.getDevicePolicy(i2) : 0;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return devicePolicy;
        }

        public final CharSequence getDisplayNameForPersistentDeviceId(String str) {
            AssociationInfo associationInfo;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                associationInfo =
                        (AssociationInfo)
                                VirtualDeviceManagerService.this.mActiveAssociations.get(str);
            }
            if (associationInfo == null) {
                return null;
            }
            return associationInfo.getDisplayName();
        }

        public final VirtualDevice getVirtualDevice(int i) {
            VirtualDeviceImpl virtualDeviceImpl;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl =
                        (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
            }
            if (virtualDeviceImpl == null) {
                return null;
            }
            return virtualDeviceImpl.mPublicVirtualDeviceObject;
        }

        public final List getVirtualDevices() {
            ArrayList arrayList = new ArrayList();
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                for (int i = 0; i < VirtualDeviceManagerService.this.mVirtualDevices.size(); i++) {
                    try {
                        arrayList.add(
                                ((VirtualDeviceImpl)
                                                VirtualDeviceManagerService.this.mVirtualDevices
                                                        .valueAt(i))
                                        .mPublicVirtualDeviceObject);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            return arrayList;
        }

        public final boolean isValidVirtualDeviceId(int i) {
            boolean contains;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                contains = VirtualDeviceManagerService.this.mVirtualDevices.contains(i);
            }
            return contains;
        }

        public final boolean isVirtualDeviceOwnedMirrorDisplay(int i) {
            return (getDeviceIdForDisplayId(i) == 0
                            || ((DisplayManagerInternal)
                                                    LocalServices.getService(
                                                            DisplayManagerInternal.class))
                                            .getDisplayIdToMirror(i)
                                    == -1)
                    ? false
                    : true;
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (Throwable th) {
                Slog.e("VirtualDeviceManagerService", "Error during IPC", th);
                throw ExceptionUtils.propagate(th, RemoteException.class);
            }
        }

        public final void playSoundEffect(int i, int i2) {
            VirtualDeviceImpl virtualDeviceImpl;
            synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                virtualDeviceImpl =
                        (VirtualDeviceImpl) VirtualDeviceManagerService.this.mVirtualDevices.get(i);
            }
            if (virtualDeviceImpl != null) {
                try {
                    virtualDeviceImpl.mSoundEffectListener.onPlaySoundEffect(i2);
                } catch (RemoteException e) {
                    Slog.w("VirtualDeviceImpl", "Unable to invoke sound effect listener", e);
                }
            }
        }

        public final void registerVirtualDeviceListener(
                IVirtualDeviceListener iVirtualDeviceListener) {
            VirtualDeviceManagerService.this.mVirtualDeviceListeners.register(
                    iVirtualDeviceListener);
        }

        public final void unregisterVirtualDeviceListener(
                IVirtualDeviceListener iVirtualDeviceListener) {
            VirtualDeviceManagerService.this.mVirtualDeviceListeners.unregister(
                    iVirtualDeviceListener);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VirtualDeviceManagerNativeImpl extends IVirtualDeviceManagerNative.Stub {
        public VirtualDeviceManagerNativeImpl() {}

        public final int[] getDeviceIdsForUid(int i) {
            return VirtualDeviceManagerService.this.mLocalService.getDeviceIdsForUid(i).stream()
                    .mapToInt(new AudioService$$ExternalSyntheticLambda1(2))
                    .toArray();
        }

        public final int getDevicePolicy(int i, int i2) {
            return VirtualDeviceManagerService.this.mImpl.getDevicePolicy(i, i2);
        }
    }

    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.server.companion.virtual.VirtualDeviceManagerService$1] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.server.companion.virtual.VirtualDeviceManagerService$2] */
    public VirtualDeviceManagerService(Context context) {
        super(context);
        this.mVirtualDeviceManagerLock = new Object();
        this.mVirtualDeviceLog = new VirtualDeviceLog(getContext());
        Handler handler = new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        this.mPendingTrampolines = new PendingTrampolineMap(handler);
        this.mActiveAssociations = new ArrayMap();
        this.mCdmAssociationListener =
                new CompanionDeviceManager
                        .OnAssociationsChangedListener() { // from class:
                                                           // com.android.server.companion.virtual.VirtualDeviceManagerService.1
                    public final void onAssociationsChanged(List list) {
                        VirtualDeviceManagerService virtualDeviceManagerService =
                                VirtualDeviceManagerService.this;
                        virtualDeviceManagerService.getClass();
                        HashSet hashSet = new HashSet();
                        synchronized (virtualDeviceManagerService.mVirtualDeviceManagerLock) {
                            try {
                                if (virtualDeviceManagerService.mVirtualDevices.size() == 0) {
                                    return;
                                }
                                HashSet hashSet2 = new HashSet(list.size());
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    hashSet2.add(
                                            Integer.valueOf(((AssociationInfo) it.next()).getId()));
                                }
                                for (int i = 0;
                                        i < virtualDeviceManagerService.mVirtualDevices.size();
                                        i++) {
                                    VirtualDeviceImpl virtualDeviceImpl =
                                            (VirtualDeviceImpl)
                                                    virtualDeviceManagerService.mVirtualDevices
                                                            .valueAt(i);
                                    if (!hashSet2.contains(
                                            Integer.valueOf(
                                                    virtualDeviceImpl.mAssociationInfo.getId()))) {
                                        hashSet.add(virtualDeviceImpl);
                                    }
                                }
                                Iterator it2 = hashSet.iterator();
                                while (it2.hasNext()) {
                                    ((VirtualDeviceImpl) it2.next()).close();
                                }
                            } finally {
                            }
                        }
                    }
                };
        this.mVirtualDeviceListeners = new RemoteCallbackList();
        this.mVirtualDevices = new SparseArray();
        this.mAppsOnVirtualDevices = new SparseArray();
        this.mActivityInterceptorCallback =
                new ActivityInterceptorCallback() { // from class:
                                                    // com.android.server.companion.virtual.VirtualDeviceManagerService.2
                    @Override // com.android.server.wm.ActivityInterceptorCallback
                    public final ActivityInterceptorCallback.ActivityInterceptResult
                            onInterceptActivityLaunch(
                                    ActivityInterceptorCallback.ActivityInterceptorInfo
                                            activityInterceptorInfo) {
                        if (activityInterceptorInfo.getCallingPackage() == null) {
                            return null;
                        }
                        PendingTrampolineMap pendingTrampolineMap =
                                VirtualDeviceManagerService.this.mPendingTrampolines;
                        VirtualDeviceImpl.PendingTrampoline pendingTrampoline =
                                (VirtualDeviceImpl.PendingTrampoline)
                                        pendingTrampolineMap.mMap.remove(
                                                activityInterceptorInfo.getCallingPackage());
                        pendingTrampolineMap.mHandler.removeCallbacksAndMessages(pendingTrampoline);
                        if (pendingTrampoline == null) {
                            return null;
                        }
                        pendingTrampoline.mResultReceiver.send(0, null);
                        ActivityOptions checkedOptions =
                                activityInterceptorInfo.getCheckedOptions();
                        if (checkedOptions == null) {
                            checkedOptions = ActivityOptions.makeBasic();
                        }
                        return new ActivityInterceptorCallback.ActivityInterceptResult(
                                activityInterceptorInfo.getIntent(),
                                checkedOptions.setLaunchDisplayId(pendingTrampoline.mDisplayId));
                    }
                };
        this.mImpl = new VirtualDeviceManagerImpl();
        this.mNativeImpl =
                android.companion.virtual.flags.Flags.enableNativeVdm()
                        ? new VirtualDeviceManagerNativeImpl()
                        : null;
        this.mLocalService = new LocalService();
    }

    public void addVirtualDevice(VirtualDeviceImpl virtualDeviceImpl) {
        synchronized (this.mVirtualDeviceManagerLock) {
            this.mVirtualDevices.put(virtualDeviceImpl.mDeviceId, virtualDeviceImpl);
        }
    }

    public VirtualDeviceManagerInternal getLocalServiceInstance() {
        return this.mLocalService;
    }

    public final ArrayList getVirtualDevicesSnapshot() {
        ArrayList arrayList;
        synchronized (this.mVirtualDeviceManagerLock) {
            try {
                arrayList = new ArrayList(this.mVirtualDevices.size());
                for (int i = 0; i < this.mVirtualDevices.size(); i++) {
                    arrayList.add((VirtualDeviceImpl) this.mVirtualDevices.valueAt(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public void notifyRunningAppsChanged(int i, ArraySet arraySet) {
        ProxyManager$$ExternalSyntheticLambda4[] proxyManager$$ExternalSyntheticLambda4Arr;
        synchronized (this.mVirtualDeviceManagerLock) {
            try {
                if (!this.mVirtualDevices.contains(i)) {
                    Slog.e(
                            "VirtualDeviceManagerService",
                            "notifyRunningAppsChanged called for unknown deviceId:"
                                    + i
                                    + " (maybe it was recently closed?)");
                    return;
                }
                this.mAppsOnVirtualDevices.put(i, arraySet);
                LocalService localService = this.mLocalService;
                localService.getClass();
                ArraySet arraySet2 = new ArraySet();
                synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                    try {
                        int size = VirtualDeviceManagerService.this.mAppsOnVirtualDevices.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            arraySet2.addAll(
                                    (ArraySet)
                                            VirtualDeviceManagerService.this.mAppsOnVirtualDevices
                                                    .valueAt(i2));
                        }
                        if (localService.mAllUidsOnVirtualDevice.equals(arraySet2)) {
                            proxyManager$$ExternalSyntheticLambda4Arr = null;
                        } else {
                            localService.mAllUidsOnVirtualDevice.clear();
                            localService.mAllUidsOnVirtualDevice.addAll(arraySet2);
                            proxyManager$$ExternalSyntheticLambda4Arr =
                                    (ProxyManager$$ExternalSyntheticLambda4[])
                                            localService.mAppsOnVirtualDeviceListeners.toArray(
                                                    new ProxyManager$$ExternalSyntheticLambda4[0]);
                        }
                    } finally {
                    }
                }
                if (proxyManager$$ExternalSyntheticLambda4Arr != null) {
                    VirtualDeviceManagerService.this.mHandler.post(
                            new VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda0(
                                    0, proxyManager$$ExternalSyntheticLambda4Arr, arraySet2));
                }
            } finally {
            }
        }
    }

    public final void onCdmAssociationsChanged(List list) {
        Set keySet;
        List copyOf;
        ArrayMap arrayMap = new ArrayMap();
        for (int i = 0; i < list.size(); i++) {
            AssociationInfo associationInfo = (AssociationInfo) list.get(i);
            if (VIRTUAL_DEVICE_COMPANION_DEVICE_PROFILES.contains(
                            associationInfo.getDeviceProfile())
                    && !associationInfo.isRevoked()) {
                int id = associationInfo.getId();
                int i2 = VirtualDeviceImpl.$r8$clinit;
                arrayMap.put("companion:" + id, associationInfo);
            }
        }
        HashSet hashSet = new HashSet();
        synchronized (this.mVirtualDeviceManagerLock) {
            try {
                keySet = this.mActiveAssociations.keySet();
                keySet.removeAll(arrayMap.keySet());
                this.mActiveAssociations = arrayMap;
                for (int i3 = 0; i3 < this.mVirtualDevices.size(); i3++) {
                    VirtualDeviceImpl virtualDeviceImpl =
                            (VirtualDeviceImpl) this.mVirtualDevices.valueAt(i3);
                    if (keySet.contains(virtualDeviceImpl.mPersistentDeviceId)) {
                        hashSet.add(virtualDeviceImpl);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            ((VirtualDeviceImpl) it.next()).close();
        }
        if (keySet.isEmpty()) {
            return;
        }
        LocalService localService = this.mLocalService;
        synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
            copyOf = List.copyOf(localService.mPersistentDeviceIdRemovedListeners);
        }
        VirtualDeviceManagerService.this.mHandler.post(
                new VirtualDeviceManagerService$LocalService$$ExternalSyntheticLambda0(
                        keySet, copyOf));
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("virtualdevice", this.mImpl);
        if (android.companion.virtual.flags.Flags.enableNativeVdm()) {
            publishBinderService("virtualdevice_native", this.mNativeImpl);
        }
        publishLocalService(VirtualDeviceManagerInternal.class, this.mLocalService);
        ((ActivityTaskManagerInternal) getLocalService(ActivityTaskManagerInternal.class))
                .registerActivityStartInterceptor(3, this.mActivityInterceptorCallback);
        if (android.companion.virtual.flags.Flags.persistentDeviceIdApi()) {
            CompanionDeviceManager companionDeviceManager =
                    (CompanionDeviceManager)
                            getContext().getSystemService(CompanionDeviceManager.class);
            if (companionDeviceManager == null) {
                Slog.e(
                        "VirtualDeviceManagerService",
                        "Failed to find CompanionDeviceManager. No CDM association info  will be"
                            + " available.");
            } else {
                onCdmAssociationsChanged(companionDeviceManager.getAllAssociations(-1));
                companionDeviceManager.addOnAssociationsChangedListener(
                        getContext().getMainExecutor(),
                        new CompanionDeviceManager
                                .OnAssociationsChangedListener() { // from class:
                                                                   // com.android.server.companion.virtual.VirtualDeviceManagerService$$ExternalSyntheticLambda0
                            public final void onAssociationsChanged(List list) {
                                VirtualDeviceManagerService.this.onCdmAssociationsChanged(list);
                            }
                        },
                        -1);
            }
        }
    }
}
