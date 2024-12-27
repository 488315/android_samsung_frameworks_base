package com.android.systemui.keyguard.shared.model;

public abstract class AuthenticationFlagsKt {
    public static final boolean access$containsFlag(int i, int i2) {
        return (i & i2) != 0;
    }
}
