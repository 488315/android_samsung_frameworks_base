package com.android.server.companion.virtual;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraInjectionSession;
import android.hardware.camera2.CameraManager;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class CameraAccessController extends CameraManager.AvailabilityCallback implements AutoCloseable {
    public final CameraAccessBlockedCallback mBlockedCallback;
    public final CameraManager mCameraManager;
    public final Context mContext;
    public final PackageManager mPackageManager;
    public final UserManager mUserManager;
    public final VirtualDeviceManagerInternal mVirtualDeviceManagerInternal;
    public final Object mLock = new Object();
    public final Object mObserverLock = new Object();
    public int mObserverCount = 0;
    public ArrayMap mPackageToSessionData = new ArrayMap();
    public ArrayMap mAppsToBlockOnVirtualDevice = new ArrayMap();

    public interface CameraAccessBlockedCallback {
        void onCameraAccessBlocked(int i);
    }

    public class InjectionSessionData {
        public int appUid;
        public ArrayMap cameraIdToSession = new ArrayMap();
    }

    public class OpenCameraInfo {
        public String packageName;
        public Set packageUids;
    }

    public CameraAccessController(Context context, VirtualDeviceManagerInternal virtualDeviceManagerInternal, CameraAccessBlockedCallback cameraAccessBlockedCallback) {
        this.mContext = context;
        this.mVirtualDeviceManagerInternal = virtualDeviceManagerInternal;
        this.mBlockedCallback = cameraAccessBlockedCallback;
        this.mCameraManager = (CameraManager) context.getSystemService(CameraManager.class);
        this.mPackageManager = context.getPackageManager();
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    public int getUserId() {
        return this.mContext.getUserId();
    }

    public void startObservingIfNeeded() {
        synchronized (this.mObserverLock) {
            if (this.mObserverCount == 0) {
                this.mCameraManager.registerAvailabilityCallback(this.mContext.getMainExecutor(), this);
            }
            this.mObserverCount++;
        }
    }

    public void stopObservingIfNeeded() {
        synchronized (this.mObserverLock) {
            int i = this.mObserverCount - 1;
            this.mObserverCount = i;
            if (i <= 0) {
                close();
            }
        }
    }

    public void blockCameraAccessIfNeeded(Set set) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mAppsToBlockOnVirtualDevice.size(); i++) {
                String str = (String) this.mAppsToBlockOnVirtualDevice.keyAt(i);
                OpenCameraInfo openCameraInfo = (OpenCameraInfo) this.mAppsToBlockOnVirtualDevice.get(str);
                String str2 = openCameraInfo.packageName;
                Iterator it = openCameraInfo.packageUids.iterator();
                while (true) {
                    if (it.hasNext()) {
                        int intValue = ((Integer) it.next()).intValue();
                        if (set.contains(Integer.valueOf(intValue))) {
                            if (((InjectionSessionData) this.mPackageToSessionData.get(str2)) == null) {
                                InjectionSessionData injectionSessionData = new InjectionSessionData();
                                injectionSessionData.appUid = intValue;
                                this.mPackageToSessionData.put(str2, injectionSessionData);
                            }
                            startBlocking(str2, str);
                        }
                    }
                }
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mObserverLock) {
            int i = this.mObserverCount;
            if (i < 0) {
                Slog.wtf("CameraAccessController", "Unexpected negative mObserverCount: " + this.mObserverCount);
            } else if (i > 0) {
                Slog.w("CameraAccessController", "Unexpected close with observers remaining: " + this.mObserverCount);
            }
        }
        this.mCameraManager.unregisterAvailabilityCallback(this);
    }

    public void onCameraOpened(String str, String str2) {
        synchronized (this.mLock) {
            InjectionSessionData injectionSessionData = (InjectionSessionData) this.mPackageToSessionData.get(str2);
            List aliveUsers = this.mUserManager.getAliveUsers();
            ArraySet arraySet = new ArraySet();
            Iterator it = aliveUsers.iterator();
            while (it.hasNext()) {
                int queryUidFromPackageName = queryUidFromPackageName(((UserInfo) it.next()).getUserHandle().getIdentifier(), str2);
                if (this.mVirtualDeviceManagerInternal.isAppRunningOnAnyVirtualDevice(queryUidFromPackageName)) {
                    if (injectionSessionData == null) {
                        injectionSessionData = new InjectionSessionData();
                        injectionSessionData.appUid = queryUidFromPackageName;
                        this.mPackageToSessionData.put(str2, injectionSessionData);
                    }
                    if (injectionSessionData.cameraIdToSession.containsKey(str)) {
                        return;
                    }
                    startBlocking(str2, str);
                    return;
                }
                if (queryUidFromPackageName != -1) {
                    arraySet.add(Integer.valueOf(queryUidFromPackageName));
                }
            }
            OpenCameraInfo openCameraInfo = new OpenCameraInfo();
            openCameraInfo.packageName = str2;
            openCameraInfo.packageUids = arraySet;
            this.mAppsToBlockOnVirtualDevice.put(str, openCameraInfo);
            CameraInjectionSession cameraInjectionSession = injectionSessionData != null ? (CameraInjectionSession) injectionSessionData.cameraIdToSession.get(str) : null;
            if (cameraInjectionSession != null) {
                cameraInjectionSession.close();
                injectionSessionData.cameraIdToSession.remove(str);
                if (injectionSessionData.cameraIdToSession.isEmpty()) {
                    this.mPackageToSessionData.remove(str2);
                }
            }
        }
    }

    public void onCameraClosed(String str) {
        synchronized (this.mLock) {
            this.mAppsToBlockOnVirtualDevice.remove(str);
            for (int size = this.mPackageToSessionData.size() - 1; size >= 0; size--) {
                InjectionSessionData injectionSessionData = (InjectionSessionData) this.mPackageToSessionData.valueAt(size);
                CameraInjectionSession cameraInjectionSession = (CameraInjectionSession) injectionSessionData.cameraIdToSession.get(str);
                if (cameraInjectionSession != null) {
                    cameraInjectionSession.close();
                    injectionSessionData.cameraIdToSession.remove(str);
                    if (injectionSessionData.cameraIdToSession.isEmpty()) {
                        this.mPackageToSessionData.removeAt(size);
                    }
                }
            }
        }
    }

    public final void startBlocking(final String str, final String str2) {
        try {
            Slog.d("CameraAccessController", "startBlocking() cameraId: " + str2 + " packageName: " + str);
            this.mCameraManager.injectCamera(str, str2, "", this.mContext.getMainExecutor(), new CameraInjectionSession.InjectionStatusCallback() { // from class: com.android.server.companion.virtual.CameraAccessController.1
                public void onInjectionSucceeded(CameraInjectionSession cameraInjectionSession) {
                    CameraAccessController.this.onInjectionSucceeded(str2, str, cameraInjectionSession);
                }

                public void onInjectionError(int i) {
                    CameraAccessController.this.onInjectionError(str2, str, i);
                }
            });
        } catch (CameraAccessException e) {
            Slog.e("CameraAccessController", "Failed to injectCamera for cameraId:" + str2 + " package:" + str, e);
        }
    }

    public final void onInjectionSucceeded(String str, String str2, CameraInjectionSession cameraInjectionSession) {
        synchronized (this.mLock) {
            InjectionSessionData injectionSessionData = (InjectionSessionData) this.mPackageToSessionData.get(str2);
            if (injectionSessionData == null) {
                Slog.e("CameraAccessController", "onInjectionSucceeded didn't find expected entry for package " + str2);
                cameraInjectionSession.close();
                return;
            }
            CameraInjectionSession cameraInjectionSession2 = (CameraInjectionSession) injectionSessionData.cameraIdToSession.put(str, cameraInjectionSession);
            if (cameraInjectionSession2 != null) {
                Slog.e("CameraAccessController", "onInjectionSucceeded found unexpected existing session for camera " + str);
                cameraInjectionSession2.close();
            }
        }
    }

    public final void onInjectionError(String str, String str2, int i) {
        if (i != 2) {
            Slog.e("CameraAccessController", "Unexpected injection error code:" + i + " for camera:" + str + " and package:" + str2);
            return;
        }
        synchronized (this.mLock) {
            InjectionSessionData injectionSessionData = (InjectionSessionData) this.mPackageToSessionData.get(str2);
            if (injectionSessionData != null) {
                this.mBlockedCallback.onCameraAccessBlocked(injectionSessionData.appUid);
            }
        }
    }

    public final int queryUidFromPackageName(int i, String str) {
        try {
            return this.mPackageManager.getApplicationInfoAsUser(str, 1, i).uid;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.w("CameraAccessController", "queryUidFromPackageName - unknown package " + str, e);
            return -1;
        }
    }
}
