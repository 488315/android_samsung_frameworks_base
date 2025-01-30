package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class CameraManagerWrapper {
    private static final CameraManagerWrapper sInstance = new CameraManagerWrapper();
    private static final CameraManager mCameraManager = (CameraManager) AppGlobals.getInitialApplication().getSystemService("camera");

    private CameraManagerWrapper() {
    }

    public static CameraManagerWrapper getInstance() {
        return sInstance;
    }

    public void setTorchMode(String str, boolean z, int i) {
        try {
            mCameraManager.setTorchMode(str, z);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
