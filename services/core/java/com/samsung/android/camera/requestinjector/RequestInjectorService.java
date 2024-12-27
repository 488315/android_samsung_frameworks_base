package com.samsung.android.camera.requestinjector;

import android.content.Context;
import android.hardware.ICameraService;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;

import com.samsung.android.camera.CameraServiceWorker;
import com.samsung.android.camera.IRequestInjector;
import com.samsung.android.camera.IRequestInjectorCallback;
import com.samsung.android.camera.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public final class RequestInjectorService extends IRequestInjector.Stub {
    public final CameraServiceWorker mCameraServiceWorker;
    public final Context mContext;
    public final Handler mHandler;
    public final Object mRequestInjectorLock = new Object();
    public final ArrayMap mExtraRequestMap = new ArrayMap();
    public final CopyOnWriteArrayList mCallbackList = new CopyOnWriteArrayList();
    public final RequestInjectorCallbackProxy mRequestInjectorCallbackProxy =
            new RequestInjectorCallbackProxy();

    public final class RequestInjectorCallbackProxy extends IRequestInjectorCallback.Stub {
        public RequestInjectorCallbackProxy() {}

        public final IBinder asBinder() {
            return this;
        }

        public final void onCaptureResult(
                CameraMetadataNative cameraMetadataNative, String str, String str2, int i, long j) {
            Iterator it = RequestInjectorService.this.mCallbackList.iterator();
            while (it.hasNext()) {
                IBinder iBinder = (IBinder) it.next();
                try {
                    IRequestInjectorCallback.Stub.asInterface(iBinder)
                            .onCaptureResult(cameraMetadataNative, str, str2, i, j);
                } catch (DeadObjectException unused) {
                    Slog.i(
                            "RequestInjectorService",
                            "onCaptureResult: Callback has removed by DeadObjectException"
                                + " clientName = "
                                    + str);
                    RequestInjectorService.this.unregisterCallback(iBinder);
                }
            }
        }
    }

    public RequestInjectorService(
            CameraServiceWorker cameraServiceWorker, Context context, Handler handler) {
        this.mCameraServiceWorker = cameraServiceWorker;
        this.mContext = context;
        this.mHandler = handler;
    }

    public final void applyRequests(PersistableBundle[] persistableBundleArr) {
        if (this.mContext.checkCallingPermission("android.permission.CAMERA_SEND_SYSTEM_EVENTS")
                != 0) {
            throw new SecurityException(
                    "Requires permission android.permission.CAMERA_SEND_SYSTEM_EVENTS");
        }
        Logger.ID id = Logger.ID.REQUEST_INJECTOR_SERVICE;
        if (persistableBundleArr == null || persistableBundleArr.length < 1) {
            Logger.log(id, "Invalid request, null or 0 size");
            throw new IllegalArgumentException("Invalid request, null or 0 size");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Slog.i(
                "RequestInjectorService",
                "applyRequests requests size = " + persistableBundleArr.length);
        Logger.log(id, "applyRequests requests size = " + persistableBundleArr.length);
        try {
            for (PersistableBundle persistableBundle : persistableBundleArr) {
                persistableBundle.getString("key.tagName");
                Slog.i("RequestInjectorService", "  " + persistableBundle);
                Logger.log(id, "  " + persistableBundle);
                if (persistableBundle.getString("key.tagName") == null) {
                    Logger.log(id, "Invalid request, no TAG_NAME");
                    throw new IllegalArgumentException("Invalid request, no TAG_NAME");
                }
                if (persistableBundle.getIntArray("key.i32") == null
                        && persistableBundle.getIntArray("key.u8") == null) {
                    Logger.log(id, "Invalid request, no KEY_I32/KEY_U8");
                    throw new IllegalArgumentException("Invalid request, no KEY_I32/KEY_U8");
                }
            }
            sendExtraRequestsToRequestInjector(persistableBundleArr);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void clearRequests() {
        if (this.mContext.checkCallingPermission("android.permission.CAMERA_SEND_SYSTEM_EVENTS")
                != 0) {
            throw new SecurityException(
                    "Requires permission android.permission.CAMERA_SEND_SYSTEM_EVENTS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mRequestInjectorLock) {
                try {
                    Collection values = this.mExtraRequestMap.values();
                    ArrayList arrayList = new ArrayList();
                    Iterator it = values.iterator();
                    while (it.hasNext()) {
                        PersistableBundle deepCopy = ((PersistableBundle) it.next()).deepCopy();
                        if (deepCopy.getIntArray("key.i32") != null) {
                            deepCopy.putIntArray("key.i32", new int[0]);
                        }
                        if (deepCopy.getIntArray("key.u8") != null) {
                            deepCopy.putIntArray("key.u8", new int[0]);
                        }
                        arrayList.add(deepCopy);
                    }
                    sendExtraRequestsToRequestInjector(
                            (PersistableBundle[]) arrayList.toArray(new PersistableBundle[0]));
                } finally {
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ICameraService getICameraService() {
        ICameraService cameraService = this.mCameraServiceWorker.getCameraService();
        int i = 0;
        while (cameraService == null) {
            int i2 = i + 1;
            if (i >= 3) {
                Slog.w("RequestInjectorService", "Native camera service not available.");
                Logger.log(
                        Logger.ID.REQUEST_INJECTOR_SERVICE, "Native camera service not available.");
                return null;
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException unused) {
            }
            i = i2;
            cameraService = this.mCameraServiceWorker.getCameraService();
        }
        return cameraService;
    }

    public final synchronized void registerCallback(IBinder iBinder) {
        this.mCallbackList.add(iBinder);
        setRequestInjectorCallback();
    }

    public final void sendAllExtraRequestsToRequestInjector() {
        Slog.i("RequestInjectorService", "sendAllExtraRequestsToRequestInjector");
        Logger.log(Logger.ID.REQUEST_INJECTOR_SERVICE, "sendAllExtraRequestsToRequestInjector");
        synchronized (this.mRequestInjectorLock) {
            sendExtraRequestsToRequestInjector(
                    (PersistableBundle[])
                            this.mExtraRequestMap.values().toArray(new PersistableBundle[0]));
        }
    }

    public final void sendExtraRequestsToRequestInjector(PersistableBundle[] persistableBundleArr) {
        SystemServiceManager$$ExternalSyntheticOutline0.m(
                new StringBuilder("sendExtraRequestsToRequestInjector updated size = "),
                persistableBundleArr.length,
                "RequestInjectorService");
        Logger.log(
                Logger.ID.REQUEST_INJECTOR_SERVICE,
                "sendExtraRequestsToRequestInjector updated size = " + persistableBundleArr.length);
        try {
            synchronized (this.mRequestInjectorLock) {
                try {
                    for (PersistableBundle persistableBundle : persistableBundleArr) {
                        Slog.i("RequestInjectorService", "  " + persistableBundle);
                        Logger.log(Logger.ID.REQUEST_INJECTOR_SERVICE, "  " + persistableBundle);
                        if (persistableBundle.getIntArray("key.i32") != null) {
                            if (persistableBundle.getIntArray("key.i32").length <= 0) {}
                            this.mExtraRequestMap.put(
                                    persistableBundle.getString("key.tagName"), persistableBundle);
                        }
                        if (persistableBundle.getIntArray("key.u8") == null
                                || persistableBundle.getIntArray("key.u8").length <= 0) {
                            this.mExtraRequestMap.remove(
                                    persistableBundle.getString("key.tagName"));
                        }
                        this.mExtraRequestMap.put(
                                persistableBundle.getString("key.tagName"), persistableBundle);
                    }
                    ICameraService iCameraService = getICameraService();
                    if (iCameraService == null) {
                        return;
                    }
                    try {
                    } catch (RemoteException e) {
                        Slog.e(
                                "RequestInjectorService",
                                "Fail to apply ExtraRequests to Request Injector",
                                e);
                        Logger.log(
                                Logger.ID.REQUEST_INJECTOR_SERVICE,
                                "Fail to apply ExtraRequests to Request Injector" + e);
                    }
                    if (iCameraService.applyExtraRequestsToRequestInjector(persistableBundleArr)) {
                        return;
                    }
                    Slog.e(
                            "RequestInjectorService",
                            "Fail to apply ExtraRequests to Request Injector, maybe type mismatch");
                    Logger.log(
                            Logger.ID.REQUEST_INJECTOR_SERVICE,
                            "Fail to apply ExtraRequests to Request Injector, maybe type mismatch");
                    throw new UnsupportedOperationException(
                            "Fail to apply request. Please check log");
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
            Slog.i("RequestInjectorService", "sendExtraRequestsToRequestInjector done");
            Logger.log(
                    Logger.ID.REQUEST_INJECTOR_SERVICE, "sendExtraRequestsToRequestInjector done");
        }
    }

    public final synchronized void setRequestInjectorCallback() {
        ICameraService iCameraService = getICameraService();
        if (iCameraService == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int i = 0;
        while (!iCameraService.setRequestInjectorCallback(this.mRequestInjectorCallbackProxy)) {
            try {
                try {
                    int i2 = i + 1;
                    if (i >= 3) {
                        Slog.w(
                                "RequestInjectorService",
                                "Can not register request injector callback. return null");
                        Logger.log(
                                Logger.ID.REQUEST_INJECTOR_SERVICE,
                                "Can not register request injector callback.");
                        return;
                    }
                    try {
                        Slog.w(
                                "RequestInjectorService",
                                "Can not register request injector callback. wait 300ms retry count"
                                    + " : "
                                        + i2);
                        Thread.sleep(300L);
                    } catch (InterruptedException unused) {
                    }
                    i = i2;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final synchronized void unregisterCallback(IBinder iBinder) {
        this.mCallbackList.remove(iBinder);
        if (this.mCallbackList.isEmpty()) {
            synchronized (this) {
                ICameraService iCameraService = getICameraService();
                if (iCameraService != null) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            iCameraService.removeRequestInjectorCallback();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        }
    }
}
