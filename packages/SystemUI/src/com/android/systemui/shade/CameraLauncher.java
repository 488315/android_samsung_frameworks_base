package com.android.systemui.shade;

import com.android.systemui.camera.CameraGestureHelper;
import com.android.systemui.statusbar.phone.KeyguardBypassController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        this.mCameraGestureHelper.launchCamera(i);
    }
}
