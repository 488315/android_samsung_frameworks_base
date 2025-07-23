package com.samsung.android.camera.manager;

import android.os.IBinder;
import android.util.Log;

/* loaded from: classes6.dex */
public class CameraServiceWorkerManager {
    private static final String TAG = "CameraServiceWorkerManager";
    private IBinder binder;

    public CameraServiceWorkerManager(IBinder iBinder) {
        if (iBinder == null) {
            Log.d(TAG, "binder is null");
        } else {
            this.binder = iBinder;
        }
    }

    public IBinder getMyBinder() {
        return this.binder;
    }
}
