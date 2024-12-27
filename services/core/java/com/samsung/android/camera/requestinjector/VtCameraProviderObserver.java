package com.samsung.android.camera.requestinjector;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.hardware.ICameraService;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Slog;

import com.samsung.android.camera.CameraServiceWorker;
import com.samsung.android.camera.Logger;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class VtCameraProviderObserver extends ContentObserver {
    public static final Uri VT_CAMERA_SETTING_AUTHORITY_URI =
            Uri.parse("content://com.samsung.android.vtcamerasettings.VsetInfoProvider");
    public final List mAllowedPackageList;
    public final CameraServiceWorker mCameraServiceWorker;
    public final Context mContext;
    public final Object mListMapLock;

    public VtCameraProviderObserver(
            CameraServiceWorker cameraServiceWorker, Context context, Handler handler) {
        super(handler);
        this.mListMapLock = new Object();
        this.mAllowedPackageList = new ArrayList();
        this.mCameraServiceWorker = cameraServiceWorker;
        this.mContext = context;
    }

    public final void dump(PrintWriter printWriter) {
        synchronized (this.mListMapLock) {
            try {
                printWriter.println("\n\tDump of Request Injector Allow List");
                printWriter.println(
                        "\t\tTotal # of allow list: "
                                + ((ArrayList) this.mAllowedPackageList).size());
                Iterator it = ((ArrayList) this.mAllowedPackageList).iterator();
                while (it.hasNext()) {
                    printWriter.println("\t\t" + ((String) it.next()));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        Slog.i("VtCameraProviderObserver", "VtCameraProviderObserver.onChange(" + z + ")");
        Logger.log(
                Logger.ID.REQUEST_INJECTOR_SERVICE, "VtCameraProviderObserver.onChange(" + z + ")");
        synchronized (this.mListMapLock) {
            try {
                ((ArrayList) this.mAllowedPackageList).clear();
                Cursor query =
                        this.mContext
                                .getContentResolver()
                                .query(
                                        Uri.withAppendedPath(
                                                VT_CAMERA_SETTING_AUTHORITY_URI, "AllowedAppInfo"),
                                        null,
                                        null,
                                        null,
                                        null);
                if (query != null) {
                    try {
                        if (query.getCount() > 0) {
                            while (query.moveToNext()) {
                                int i = query.getInt(0);
                                String string = query.getString(1);
                                if (string != null && !string.isEmpty()) {
                                    Slog.i(
                                            "VtCameraProviderObserver",
                                            "Adding " + string + "(" + i + ") to allow list");
                                    Logger.log(
                                            Logger.ID.REQUEST_INJECTOR_SERVICE,
                                            "onChange: Adding "
                                                    + string
                                                    + "("
                                                    + i
                                                    + ") to allow list");
                                    ((ArrayList) this.mAllowedPackageList).add(string);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            query.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e) {
                Slog.e(
                        "VtCameraProviderObserver",
                        "Unable to query from VT Camera setting provider",
                        e);
                Logger.log(
                        Logger.ID.REQUEST_INJECTOR_SERVICE,
                        "Unable to query from VT Camera setting provider, " + e);
            } finally {
            }
        }
        updateCameraService();
    }

    public final void updateCameraService() {
        ICameraService cameraService = this.mCameraServiceWorker.getCameraService();
        int i = 0;
        while (cameraService == null) {
            int i2 = i + 1;
            if (i >= 3) {
                Slog.w("VtCameraProviderObserver", "Native camera service not available.");
                Logger.log(
                        Logger.ID.REQUEST_INJECTOR_SERVICE, "Native camera service not available.");
                return;
            } else {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException unused) {
                }
                i = i2;
                cameraService = this.mCameraServiceWorker.getCameraService();
            }
        }
        synchronized (this.mListMapLock) {
            try {
                cameraService.updateRequestInjectorAllowedList(
                        (String[]) ((ArrayList) this.mAllowedPackageList).toArray(new String[0]));
                Slog.i(
                        "VtCameraProviderObserver",
                        "Updating allowed package list for request injector is done");
                Logger.log(
                        Logger.ID.REQUEST_INJECTOR_SERVICE,
                        "Updating allowed package list for request injector is done");
            } catch (RemoteException e) {
                Slog.e(
                        "VtCameraProviderObserver",
                        "Fail to update allowed package list for Request Injector",
                        e);
                Logger.log(
                        Logger.ID.REQUEST_INJECTOR_SERVICE,
                        "Fail to update allowed package list for Request Injector" + e);
            }
        }
    }
}
