package com.android.systemui.plugins;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ClockConfig {
    private final boolean isReactiveToTone;
    private final boolean useAlternateSmartspaceAODTransition;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ClockConfig() {
        this(r2, r2, 3, null);
        boolean z = false;
    }

    public static /* synthetic */ ClockConfig copy$default(ClockConfig clockConfig, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = clockConfig.useAlternateSmartspaceAODTransition;
        }
        if ((i & 2) != 0) {
            z2 = clockConfig.isReactiveToTone;
        }
        return clockConfig.copy(z, z2);
    }

    public final boolean component1() {
        return this.useAlternateSmartspaceAODTransition;
    }

    public final boolean component2() {
        return this.isReactiveToTone;
    }

    public final ClockConfig copy(boolean z, boolean z2) {
        return new ClockConfig(z, z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockConfig)) {
            return false;
        }
        ClockConfig clockConfig = (ClockConfig) obj;
        return this.useAlternateSmartspaceAODTransition == clockConfig.useAlternateSmartspaceAODTransition && this.isReactiveToTone == clockConfig.isReactiveToTone;
    }

    public final boolean getUseAlternateSmartspaceAODTransition() {
        return this.useAlternateSmartspaceAODTransition;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public int hashCode() {
        boolean z = this.useAlternateSmartspaceAODTransition;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        boolean z2 = this.isReactiveToTone;
        return i + (z2 ? 1 : z2 ? 1 : 0);
    }

    public final boolean isReactiveToTone() {
        return this.isReactiveToTone;
    }

    public String toString() {
        return "ClockConfig(useAlternateSmartspaceAODTransition=" + this.useAlternateSmartspaceAODTransition + ", isReactiveToTone=" + this.isReactiveToTone + ")";
    }

    public ClockConfig(boolean z, boolean z2) {
        this.useAlternateSmartspaceAODTransition = z;
        this.isReactiveToTone = z2;
    }

    public /* synthetic */ ClockConfig(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? true : z2);
    }
}
