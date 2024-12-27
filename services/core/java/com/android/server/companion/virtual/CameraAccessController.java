package com.android.server.companion.virtual;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraInjectionSession;
import android.hardware.camera2.CameraManager;
import android.os.Looper;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CameraAccessController extends CameraManager.AvailabilityCallback
        implements AutoCloseable {
    public final VirtualDeviceManagerService$$ExternalSyntheticLambda2 mBlockedCallback;
    public final CameraManager mCameraManager;
    public final Context mContext;
    public final PackageManager mPackageManager;
    public final UserManager mUserManager;
    public final VirtualDeviceManagerInternal mVirtualDeviceManagerInternal;
    public final Object mLock = new Object();
    public final Object mObserverLock = new Object();
    public int mObserverCount = 0;
    public final ArrayMap mPackageToSessionData = new ArrayMap();
    public final ArrayMap mAppsToBlockOnVirtualDevice = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InjectionSessionData {
        public int appUid;
        public final ArrayMap cameraIdToSession = new ArrayMap();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OpenCameraInfo {
        public String packageName;
        public Set packageUids;
    }

    public CameraAccessController(
            Context context,
            VirtualDeviceManagerInternal virtualDeviceManagerInternal,
            VirtualDeviceManagerService$$ExternalSyntheticLambda2
                    virtualDeviceManagerService$$ExternalSyntheticLambda2) {
        this.mContext = context;
        this.mVirtualDeviceManagerInternal = virtualDeviceManagerInternal;
        this.mBlockedCallback = virtualDeviceManagerService$$ExternalSyntheticLambda2;
        this.mCameraManager = (CameraManager) context.getSystemService(CameraManager.class);
        this.mPackageManager = context.getPackageManager();
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        synchronized (this.mObserverLock) {
            try {
                int i = this.mObserverCount;
                if (i < 0) {
                    Slog.wtf(
                            "CameraAccessController",
                            "Unexpected negative mObserverCount: " + this.mObserverCount);
                } else if (i > 0) {
                    Slog.w(
                            "CameraAccessController",
                            "Unexpected close with observers remaining: " + this.mObserverCount);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mCameraManager.unregisterAvailabilityCallback(this);
    }

    public final void onCameraClosed(String str) {
        synchronized (this.mLock) {
            try {
                this.mAppsToBlockOnVirtualDevice.remove(str);
                for (int size = this.mPackageToSessionData.size() - 1; size >= 0; size--) {
                    InjectionSessionData injectionSessionData =
                            (InjectionSessionData) this.mPackageToSessionData.valueAt(size);
                    CameraInjectionSession cameraInjectionSession =
                            (CameraInjectionSession)
                                    injectionSessionData.cameraIdToSession.get(str);
                    if (cameraInjectionSession != null) {
                        cameraInjectionSession.close();
                        injectionSessionData.cameraIdToSession.remove(str);
                        if (injectionSessionData.cameraIdToSession.isEmpty()) {
                            this.mPackageToSessionData.removeAt(size);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onCameraOpened(String str, String str2) {
        int i;
        synchronized (this.mLock) {
            try {
                InjectionSessionData injectionSessionData =
                        (InjectionSessionData) this.mPackageToSessionData.get(str2);
                List aliveUsers = this.mUserManager.getAliveUsers();
                ArraySet arraySet = new ArraySet();
                Iterator it = aliveUsers.iterator();
                while (it.hasNext()) {
                    try {
                        i =
                                this.mPackageManager.getApplicationInfoAsUser(
                                                str2,
                                                1,
                                                ((UserInfo) it.next())
                                                        .getUserHandle()
                                                        .getIdentifier())
                                        .uid;
                    } catch (PackageManager.NameNotFoundException e) {
                        Slog.w(
                                "CameraAccessController",
                                "queryUidFromPackageName - unknown package " + str2,
                                e);
                        i = -1;
                    }
                    if (this.mVirtualDeviceManagerInternal.isAppRunningOnAnyVirtualDevice(i)) {
                        if (injectionSessionData == null) {
                            injectionSessionData = new InjectionSessionData();
                            injectionSessionData.appUid = i;
                            this.mPackageToSessionData.put(str2, injectionSessionData);
                        }
                        if (injectionSessionData.cameraIdToSession.containsKey(str)) {
                            return;
                        }
                        startBlocking(str2, str);
                        return;
                    }
                    if (i != -1) {
                        arraySet.add(Integer.valueOf(i));
                    }
                }
                OpenCameraInfo openCameraInfo = new OpenCameraInfo();
                openCameraInfo.packageName = str2;
                openCameraInfo.packageUids = arraySet;
                this.mAppsToBlockOnVirtualDevice.put(str, openCameraInfo);
                CameraInjectionSession cameraInjectionSession =
                        injectionSessionData != null
                                ? (CameraInjectionSession)
                                        injectionSessionData.cameraIdToSession.get(str)
                                : null;
                if (cameraInjectionSession != null) {
                    cameraInjectionSession.close();
                    injectionSessionData.cameraIdToSession.remove(str);
                    if (injectionSessionData.cameraIdToSession.isEmpty()) {
                        this.mPackageToSessionData.remove(str2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startBlocking(final String str, final String str2) {
        try {
            Slog.d(
                    "CameraAccessController",
                    "startBlocking() cameraId: " + str2 + " packageName: " + str);
            this.mCameraManager.injectCamera(
                    str,
                    str2,
                    "",
                    this.mContext.getMainExecutor(),
                    new CameraInjectionSession.InjectionStatusCallback() { // from class:
                        // com.android.server.companion.virtual.CameraAccessController.1
                        public final void onInjectionError(int i) {
                            CameraAccessController cameraAccessController =
                                    CameraAccessController.this;
                            String str3 = str2;
                            String str4 = str;
                            if (i != 2) {
                                cameraAccessController.getClass();
                                Slog.e(
                                        "CameraAccessController",
                                        "Unexpected injection error code:"
                                                + i
                                                + " for camera:"
                                                + str3
                                                + " and package:"
                                                + str4);
                                return;
                            }
                            synchronized (cameraAccessController.mLock) {
                                try {
                                    InjectionSessionData injectionSessionData =
                                            (InjectionSessionData)
                                                    cameraAccessController.mPackageToSessionData
                                                            .get(str4);
                                    if (injectionSessionData != null) {
                                        VirtualDeviceManagerService$$ExternalSyntheticLambda2
                                                virtualDeviceManagerService$$ExternalSyntheticLambda2 =
                                                        cameraAccessController.mBlockedCallback;
                                        int i2 = injectionSessionData.appUid;
                                        VirtualDeviceManagerService virtualDeviceManagerService =
                                                virtualDeviceManagerService$$ExternalSyntheticLambda2
                                                        .f$0;
                                        ArrayList virtualDevicesSnapshot =
                                                virtualDeviceManagerService
                                                        .getVirtualDevicesSnapshot();
                                        for (int i3 = 0; i3 < virtualDevicesSnapshot.size(); i3++) {
                                            VirtualDeviceImpl virtualDeviceImpl =
                                                    (VirtualDeviceImpl)
                                                            virtualDevicesSnapshot.get(i3);
                                            virtualDeviceImpl.showToastWhereUidIsRunning(
                                                    i2,
                                                    virtualDeviceManagerService
                                                            .getContext()
                                                            .getString(
                                                                    17043437,
                                                                    virtualDeviceImpl
                                                                            .mAssociationInfo
                                                                            .getDisplayName()),
                                                    Looper.myLooper());
                                        }
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }

                        public final void onInjectionSucceeded(
                                CameraInjectionSession cameraInjectionSession) {
                            CameraAccessController cameraAccessController =
                                    CameraAccessController.this;
                            String str3 = str2;
                            String str4 = str;
                            synchronized (cameraAccessController.mLock) {
                                try {
                                    InjectionSessionData injectionSessionData =
                                            (InjectionSessionData)
                                                    cameraAccessController.mPackageToSessionData
                                                            .get(str4);
                                    if (injectionSessionData == null) {
                                        Slog.e(
                                                "CameraAccessController",
                                                "onInjectionSucceeded didn't find expected entry"
                                                    + " for package "
                                                        + str4);
                                        cameraInjectionSession.close();
                                        return;
                                    }
                                    CameraInjectionSession cameraInjectionSession2 =
                                            (CameraInjectionSession)
                                                    injectionSessionData.cameraIdToSession.put(
                                                            str3, cameraInjectionSession);
                                    if (cameraInjectionSession2 != null) {
                                        Slog.e(
                                                "CameraAccessController",
                                                "onInjectionSucceeded found unexpected existing"
                                                    + " session for camera "
                                                        + str3);
                                        cameraInjectionSession2.close();
                                    }
                                } finally {
                                }
                            }
                        }
                    });
        } catch (CameraAccessException e) {
            Slog.e(
                    "CameraAccessController",
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "Failed to injectCamera for cameraId:", str2, " package:", str),
                    e);
        }
    }
}
