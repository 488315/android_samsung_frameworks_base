package com.android.systemui.plugins.clocks;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ClockConfig {
    public static final int $stable = 0;
    private final String description;
    private final String id;
    private final String name;
    private final boolean useAlternateSmartspaceAODTransition;
    private final boolean useCustomClockScene;

    public ClockConfig(String str, String str2, String str3, boolean z, boolean z2) {
        this.id = str;
        this.name = str2;
        this.description = str3;
        this.useAlternateSmartspaceAODTransition = z;
        this.useCustomClockScene = z2;
    }

    public static /* synthetic */ ClockConfig copy$default(ClockConfig clockConfig, String str, String str2, String str3, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clockConfig.id;
        }
        if ((i & 2) != 0) {
            str2 = clockConfig.name;
        }
        if ((i & 4) != 0) {
            str3 = clockConfig.description;
        }
        if ((i & 8) != 0) {
            z = clockConfig.useAlternateSmartspaceAODTransition;
        }
        if ((i & 16) != 0) {
            z2 = clockConfig.useCustomClockScene;
        }
        boolean z3 = z2;
        String str4 = str3;
        return clockConfig.copy(str, str2, str4, z, z3);
    }

    public final String component1() {
        return this.id;
    }

    public final String component2() {
        return this.name;
    }

    public final String component3() {
        return this.description;
    }

    public final boolean component4() {
        return this.useAlternateSmartspaceAODTransition;
    }

    public final boolean component5() {
        return this.useCustomClockScene;
    }

    public final ClockConfig copy(String str, String str2, String str3, boolean z, boolean z2) {
        return new ClockConfig(str, str2, str3, z, z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockConfig)) {
            return false;
        }
        ClockConfig clockConfig = (ClockConfig) obj;
        return Intrinsics.areEqual(this.id, clockConfig.id) && Intrinsics.areEqual(this.name, clockConfig.name) && Intrinsics.areEqual(this.description, clockConfig.description) && this.useAlternateSmartspaceAODTransition == clockConfig.useAlternateSmartspaceAODTransition && this.useCustomClockScene == clockConfig.useCustomClockScene;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final boolean getUseAlternateSmartspaceAODTransition() {
        return this.useAlternateSmartspaceAODTransition;
    }

    public final boolean getUseCustomClockScene() {
        return this.useCustomClockScene;
    }

    public int hashCode() {
        return Boolean.hashCode(this.useCustomClockScene) + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name), 31, this.description), 31, this.useAlternateSmartspaceAODTransition);
    }

    public String toString() {
        String str = this.id;
        String str2 = this.name;
        String str3 = this.description;
        boolean z = this.useAlternateSmartspaceAODTransition;
        boolean z2 = this.useCustomClockScene;
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("ClockConfig(id=", str, ", name=", str2, ", description=");
        m.append(str3);
        m.append(", useAlternateSmartspaceAODTransition=");
        m.append(z);
        m.append(", useCustomClockScene=");
        return MoveResult$$ExternalSyntheticOutline0.m(m, z2, ")");
    }

    public /* synthetic */ ClockConfig(String str, String str2, String str3, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2);
    }
}
