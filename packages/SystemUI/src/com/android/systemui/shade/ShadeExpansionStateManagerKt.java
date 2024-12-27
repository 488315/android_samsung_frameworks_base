package com.android.systemui.shade;

import kotlin.jvm.internal.Reflection;

public abstract class ShadeExpansionStateManagerKt {
    public static final String TAG = Reflection.getOrCreateKotlinClass(ShadeExpansionStateManager.class).getSimpleName();

    public static final String panelStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? String.valueOf(i) : "OPEN" : "OPENING" : "CLOSED";
    }
}
