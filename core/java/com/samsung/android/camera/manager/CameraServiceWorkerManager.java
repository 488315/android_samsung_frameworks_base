package com.samsung.android.camera.manager;

import android.os.IBinder;
import android.util.Log;

/* loaded from: classes5.dex */
public class CameraServiceWorkerManager {
    private static final String TAG = "CameraServiceWorkerManager";
    private IBinder binder;

    public CameraServiceWorkerManager(IBinder binder) {
        if (binder == null) {
            Log.d(TAG, "binder is null");
        } else {
            this.binder = binder;
        }
    }

    public IBinder getMyBinder() {
        return this.binder;
    }
}
