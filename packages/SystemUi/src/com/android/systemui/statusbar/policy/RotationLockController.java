package com.android.systemui.statusbar.policy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface RotationLockController extends CallbackController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface RotationLockControllerCallback {
        void onRotationLockStateChanged(boolean z);
    }

    int getRotationLockOrientation();

    boolean isCameraRotationEnabled();

    boolean isRotationLocked();

    void setRotationLocked(boolean z);

    void setRotationLockedAtAngle(int i, boolean z);
}
