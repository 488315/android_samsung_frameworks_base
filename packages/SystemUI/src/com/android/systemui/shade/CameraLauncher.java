package com.android.systemui.shade;

import com.android.systemui.camera.CameraGestureHelper;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import dagger.Lazy;

/* loaded from: classes3.dex */
public class CameraLauncher {
    public final CameraGestureHelper mCameraGestureHelper;
    public final KeyguardBypassController mKeyguardBypassController;

    public CameraLauncher(CameraGestureHelper cameraGestureHelper, KeyguardBypassController keyguardBypassController, Lazy lazy) {
        this.mCameraGestureHelper = cameraGestureHelper;
        this.mKeyguardBypassController = keyguardBypassController;
    }

    public final void launchCamera(int i, boolean z) {
        if (!z) {
            setLaunchingAffordance(true);
        }
        this.mCameraGestureHelper.launchCamera(i);
    }

    public final void setLaunchingAffordance(boolean z) {
        int i = SceneContainerFlag.$r8$clinit;
        this.mKeyguardBypassController.launchingAffordance = z;
    }
}
