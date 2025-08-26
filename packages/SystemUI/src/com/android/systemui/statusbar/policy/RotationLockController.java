package com.android.systemui.statusbar.policy;

/* loaded from: classes3.dex */
public interface RotationLockController extends CallbackController {

    public interface RotationLockControllerCallback {
        void onRotationLockStateChanged(boolean z, boolean z2);
    }

    int getRotationLockOrientation();

    boolean isCameraRotationEnabled();

    boolean isRotationLocked();

    void setRotationLocked(String str, boolean z);

    void setRotationLockedAtAngle(int i, String str, boolean z);
}
