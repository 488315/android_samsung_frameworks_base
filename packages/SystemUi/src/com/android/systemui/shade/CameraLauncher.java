package com.android.systemui.shade;

import com.android.systemui.camera.CameraGestureHelper;
import com.android.systemui.statusbar.phone.KeyguardBypassController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        CameraGestureHelper cameraGestureHelper = this.mCameraGestureHelper;
        cameraGestureHelper.launchCamera(i, cameraGestureHelper.getStartCameraIntent());
    }
}
