package com.android.systemui.util.wrapper;

import com.android.internal.view.RotationPolicy;

public interface RotationPolicyWrapper {
    int getRotationLockOrientation();

    boolean isCameraRotationEnabled();

    boolean isRotationLockToggleVisible();

    boolean isRotationLocked();

    void registerRotationPolicyListener(RotationPolicy.RotationPolicyListener rotationPolicyListener, int i);

    void setRotationLock(boolean z, String str);

    void setRotationLockAtAngle(boolean z, int i, String str);

    void unregisterRotationPolicyListener(RotationPolicy.RotationPolicyListener rotationPolicyListener);
}
