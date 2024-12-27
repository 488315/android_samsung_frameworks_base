package com.android.systemui.util.wrapper;

public abstract class UtilWrapperModule {
    public static final int $stable = 0;

    public abstract RotationPolicyWrapper bindRotationPolicyWrapper(RotationPolicyWrapperImpl rotationPolicyWrapperImpl);
}
