package com.android.systemui.shade;

import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ShadeExpansionStateManagerKt {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        Reflection.getOrCreateKotlinClass(ShadeExpansionStateManager.class).getSimpleName();
    }

    public static final String panelStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? String.valueOf(i) : "OPEN" : "OPENING" : "CLOSED";
    }
}
