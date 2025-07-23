package com.android.systemui.util.wrapper;

import com.android.internal.view.RotationPolicy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
