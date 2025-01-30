package com.android.systemui.biometrics;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Action {
    public final Runnable onPanelInteraction;

    public Action(Runnable runnable) {
        this.onPanelInteraction = runnable;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof Action) && Intrinsics.areEqual(this.onPanelInteraction, ((Action) obj).onPanelInteraction);
    }

    public final int hashCode() {
        return this.onPanelInteraction.hashCode();
    }

    public final String toString() {
        return "Action(onPanelInteraction=" + this.onPanelInteraction + ")";
    }
}
