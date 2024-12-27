package com.android.systemui.util.wrapper;

import com.android.internal.view.RotationPolicy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
