package com.android.systemui.shade;

import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class ShadeExpansionStateManagerKt {
    public static final String TAG = Reflection.getOrCreateKotlinClass(ShadeExpansionStateManager.class).getSimpleName();

    public static final String panelStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? String.valueOf(i) : "OPEN" : "OPENING" : "CLOSED";
    }
}
