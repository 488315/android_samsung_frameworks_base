package com.samsung.android.camera.requestinjector;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.hardware.ICameraService;
import android.hardware.camera2.CameraManager;
import android.os.Binder;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Slog;
import com.samsung.android.camera.CameraServiceWorker;
import com.samsung.android.camera.IRequestInjector;
import com.samsung.android.camera.Logger;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class RequestInjectorService extends IRequestInjector.Stub {
    public final CameraServiceWorker mCameraServiceWorker;
    public final Context mContext;
    public final Handler mHandler;
    public final Object mRequestInjectorLock = new Object();
    public final ArrayMap mExtraRequestMap = new ArrayMap();

    @Override // com.samsung.android.camera.IRequestInjector
    public final String getInterfaceHash() {
        return "b05ff96f01de43f1b43fef6b50a2aa6a578c5be3";
    }

    @Override // com.samsung.android.camera.IRequestInjector
    public final int getInterfaceVersion() {
        return 2;
    }

    public RequestInjectorService(CameraServiceWorker cameraServiceWorker, Context context, Handler handler) {
        this.mCameraServiceWorker = cameraServiceWorker;
        this.mContext = context;
        this.mHandler = handler;
    }

    public final boolean pkgInstalledOrNot(String str) {
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getPackageInfo(str, 4).applicationInfo;
            if (applicationInfo == null) {
                return false;
            }
            if (applicationInfo.isSystemApp()) {
                return true;
            }
            return applicationInfo.isUpdatedSystemApp();
        } catch (Exception e) {
            Slog.e("RequestInjectorService", "pkgInstalledOrNot " + e);
            return false;
        }
    }

    public void tryRegisterCameraOpenListener() {
        if (pkgInstalledOrNot("com.samsung.android.vtcamerasettings")) {
            Slog.i("RequestInjectorService", "VT Camera Setting exists. Register camera listener.");
            Logger.log(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, "VT Camera Setting exists. Register camera listener.");
            ((CameraManager) this.mContext.getSystemService("camera")).registerSemCameraDeviceStateCallback(new CameraManager.SemCameraDeviceStateCallback() { // from class: com.samsung.android.camera.requestinjector.RequestInjectorService.1
                public void onCameraDeviceStateChanged(String str, int i, int i2, String str2, int i3) {
                    if (RequestInjectorService.this.mCameraServiceWorker.isSamsungCameraApp(str2)) {
                        Slog.i("RequestInjectorService", "Samsung Camera is opened. ignore VT Camera Setting.");
                        return;
                    }
                    if (i2 == 0 || i2 == 1 || i2 == 2) {
                        return;
                    }
                    if (i2 != 3) {
                        if (i2 == 100) {
                            try {
                                CameraServiceWorker.TaskInfo taskInfo = CameraServiceWorker.getTaskInfo(str2, i3);
                                int i4 = taskInfo != null ? taskInfo.displayId : 0;
                                Intent intent = new Intent("intentfilter.samsung.vtcamerasetting.cameraobserver");
                                intent.setPackage("com.samsung.android.vtcamerasettings");
                                intent.putExtra("camera_open_package_name", str2);
                                intent.putExtra("camera_open_id", str);
                                intent.putExtra("display_id", i4);
                                Slog.i("RequestInjectorService", String.format("Camera is opening. Start VT Camera Setting. cameraId(%s), displayId(%d)", str, Integer.valueOf(i4)));
                                RequestInjectorService.this.mContext.startServiceAsUser(intent, UserHandle.CURRENT_OR_SELF);
                                return;
                            } catch (SecurityException e) {
                                Slog.w("RequestInjectorService", "VT Camera Setting does not exist", e);
                                Logger.log(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, "VT Camera Setting does not exist " + e);
                                return;
                            }
                        }
                        if (i2 != 101) {
                            Slog.e("RequestInjectorService", "Non acceptable state " + i2);
                            return;
                        }
                    }
                    try {
                        Intent intent2 = new Intent("intentfilter.samsung.vtcamerasetting.closecamera");
                        intent2.setPackage("com.samsung.android.vtcamerasettings");
                        intent2.putExtra("camera_close_package_name", str2);
                        intent2.putExtra("camera_open_id", str);
                        Slog.i("RequestInjectorService", String.format("Camera is closed. cameraId(%s), state(%d)", str, Integer.valueOf(i2)));
                        RequestInjectorService.this.mContext.startServiceAsUser(intent2, UserHandle.CURRENT_OR_SELF);
                    } catch (SecurityException e2) {
                        Slog.w("RequestInjectorService", "VT Camera Setting does not exist", e2);
                        Logger.log(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, "VT Camera Setting does not exist " + e2);
                    }
                }
            }, this.mHandler, true);
        } else {
            Slog.i("RequestInjectorService", "VT Camera Setting does not exist. Skip register camera listener.");
            Logger.log(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, "VT Camera Setting does not exist. Skip register camera listener.");
        }
    }

    @Override // com.samsung.android.camera.IRequestInjector
    public void applyRequests(PersistableBundle[] persistableBundleArr) {
        if (this.mContext.checkCallingPermission("android.permission.CAMERA_SEND_SYSTEM_EVENTS") != 0) {
            throw new SecurityException("Requires permission android.permission.CAMERA_SEND_SYSTEM_EVENTS");
        }
        if (persistableBundleArr == null || persistableBundleArr.length < 1) {
            Logger.log(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, "Invalid request, null or 0 size");
            throw new IllegalArgumentException("Invalid request, null or 0 size");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        Slog.i("RequestInjectorService", "applyRequests requests size = " + persistableBundleArr.length);
        Logger.log(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, "applyRequests requests size = " + persistableBundleArr.length);
        try {
            for (PersistableBundle persistableBundle : persistableBundleArr) {
                persistableBundle.getString("key.tagName");
                Slog.i("RequestInjectorService", "  " + persistableBundle);
                Logger.EnumC3085ID enumC3085ID = Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE;
                Logger.log(enumC3085ID, "  " + persistableBundle);
                if (persistableBundle.getString("key.tagName") == null) {
                    Logger.log(enumC3085ID, "Invalid request, no TAG_NAME");
                    throw new IllegalArgumentException("Invalid request, no TAG_NAME");
                }
                if (persistableBundle.getIntArray("key.i32") == null && persistableBundle.getIntArray("key.u8") == null) {
                    Logger.log(enumC3085ID, "Invalid request, no KEY_I32/KEY_U8");
                    throw new IllegalArgumentException("Invalid request, no KEY_I32/KEY_U8");
                }
            }
            sendExtraRequestsToRequestInjector(persistableBundleArr);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.samsung.android.camera.IRequestInjector
    public void clearRequests() {
        if (this.mContext.checkCallingPermission("android.permission.CAMERA_SEND_SYSTEM_EVENTS") != 0) {
            throw new SecurityException("Requires permission android.permission.CAMERA_SEND_SYSTEM_EVENTS");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mRequestInjectorLock) {
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
                sendExtraRequestsToRequestInjector((PersistableBundle[]) arrayList.toArray(new PersistableBundle[0]));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void sendAllExtraRequestsToRequestInjector() {
        Slog.i("RequestInjectorService", "sendAllExtraRequestsToRequestInjector");
        Logger.log(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, "sendAllExtraRequestsToRequestInjector");
        synchronized (this.mRequestInjectorLock) {
            sendExtraRequestsToRequestInjector((PersistableBundle[]) this.mExtraRequestMap.values().toArray(new PersistableBundle[0]));
        }
    }

    public final void sendExtraRequestsToRequestInjector(PersistableBundle[] persistableBundleArr) {
        Slog.i("RequestInjectorService", "sendExtraRequestsToRequestInjector updated size = " + persistableBundleArr.length);
        Logger.log(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, "sendExtraRequestsToRequestInjector updated size = " + persistableBundleArr.length);
        try {
            synchronized (this.mRequestInjectorLock) {
                int i = 0;
                for (PersistableBundle persistableBundle : persistableBundleArr) {
                    Slog.i("RequestInjectorService", "  " + persistableBundle);
                    Logger.log(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, "  " + persistableBundle);
                    if ((persistableBundle.getIntArray("key.i32") != null && persistableBundle.getIntArray("key.i32").length > 0) || (persistableBundle.getIntArray("key.u8") != null && persistableBundle.getIntArray("key.u8").length > 0)) {
                        this.mExtraRequestMap.put(persistableBundle.getString("key.tagName"), persistableBundle);
                    } else {
                        this.mExtraRequestMap.remove(persistableBundle.getString("key.tagName"));
                    }
                }
                ICameraService cameraService = this.mCameraServiceWorker.getCameraService();
                while (cameraService == null) {
                    int i2 = i + 1;
                    if (i >= 3) {
                        Slog.w("RequestInjectorService", "Native camera service not available.");
                        Logger.EnumC3085ID enumC3085ID = Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE;
                        Logger.log(enumC3085ID, "Native camera service not available.");
                        Slog.i("RequestInjectorService", "sendExtraRequestsToRequestInjector done");
                        Logger.log(enumC3085ID, "sendExtraRequestsToRequestInjector done");
                        return;
                    }
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException unused) {
                    }
                    i = i2;
                    cameraService = this.mCameraServiceWorker.getCameraService();
                }
                try {
                } catch (RemoteException e) {
                    Slog.e("RequestInjectorService", "Fail to apply ExtraRequests to Request Injector", e);
                    Logger.log(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, "Fail to apply ExtraRequests to Request Injector" + e);
                }
                if (cameraService.applyExtraRequestsToRequestInjector(persistableBundleArr)) {
                    return;
                }
                Slog.e("RequestInjectorService", "Fail to apply ExtraRequests to Request Injector, maybe type mismatch");
                Logger.log(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, "Fail to apply ExtraRequests to Request Injector, maybe type mismatch");
                throw new UnsupportedOperationException("Fail to apply request. Please check log");
            }
        } finally {
            Slog.i("RequestInjectorService", "sendExtraRequestsToRequestInjector done");
            Logger.log(Logger.EnumC3085ID.REQUEST_INJECTOR_SERVICE, "sendExtraRequestsToRequestInjector done");
        }
    }

    public synchronized void dump(PrintWriter printWriter) {
        synchronized (this.mRequestInjectorLock) {
            printWriter.println("\n\tDump of Request Injector Service Activity");
            printWriter.println("\t\tInterface v2 Hash:b05ff96f01de43f1b43fef6b50a2aa6a578c5be3");
            printWriter.println("\t\tTotal # of ExtraRequest: " + this.mExtraRequestMap.size());
            for (PersistableBundle persistableBundle : this.mExtraRequestMap.values()) {
                printWriter.println("\t\tKey = " + persistableBundle.getString("key.tagName") + ", i32 = " + ((String) Optional.ofNullable(persistableBundle.getIntArray("key.i32")).map(new Function() { // from class: com.samsung.android.camera.requestinjector.RequestInjectorService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return Arrays.toString((int[]) obj);
                    }
                }).orElse("null")) + ", u8 = " + ((String) Optional.ofNullable(persistableBundle.getIntArray("key.u8")).map(new Function() { // from class: com.samsung.android.camera.requestinjector.RequestInjectorService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return Arrays.toString((int[]) obj);
                    }
                }).orElse("null")));
            }
        }
    }
}
