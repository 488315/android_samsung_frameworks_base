package com.android.systemui.shade;

import com.android.systemui.camera.CameraGestureHelper;
import com.android.systemui.statusbar.phone.KeyguardBypassController;

public final class CameraLauncher {
    public final CameraGestureHelper mCameraGestureHelper;
    public final KeyguardBypassController mKeyguardBypassController;

    public CameraLauncher(CameraGestureHelper cameraGestureHelper, KeyguardBypassController keyguardBypassController) {
        this.mCameraGestureHelper = cameraGestureHelper;
        this.mKeyguardBypassController = keyguardBypassController;
    }

    public final void launchCamera(int i, boolean z) {
        if (!z) {
            this.mKeyguardBypassController.launchingAffordance = true;
        }
        this.mCameraGestureHelper.launchCamera(i);
    }
}
