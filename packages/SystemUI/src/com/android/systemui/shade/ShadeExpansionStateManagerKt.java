package com.android.systemui.shade;

import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ShadeExpansionStateManagerKt {
    public static final String TAG = Reflection.getOrCreateKotlinClass(ShadeExpansionStateManager.class).getSimpleName();

    public static final String panelStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? String.valueOf(i) : "OPEN" : "OPENING" : "CLOSED";
    }
}
