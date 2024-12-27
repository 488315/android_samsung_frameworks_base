package com.android.systemui.plugins.clocks;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ClockConfig {
    private final String description;
    private final String id;
    private final boolean isReactiveToTone;
    private final String name;
    private final boolean useAlternateSmartspaceAODTransition;
    private final boolean useCustomClockScene;

    public ClockConfig(String str, String str2, String str3, boolean z, boolean z2, boolean z3) {
        this.id = str;
        this.name = str2;
        this.description = str3;
        this.useAlternateSmartspaceAODTransition = z;
        this.isReactiveToTone = z2;
        this.useCustomClockScene = z3;
    }

    public static /* synthetic */ ClockConfig copy$default(ClockConfig clockConfig, String str, String str2, String str3, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clockConfig.id;
        }
        if ((i & 2) != 0) {
            str2 = clockConfig.name;
        }
        String str4 = str2;
        if ((i & 4) != 0) {
            str3 = clockConfig.description;
        }
        String str5 = str3;
        if ((i & 8) != 0) {
            z = clockConfig.useAlternateSmartspaceAODTransition;
        }
        boolean z4 = z;
        if ((i & 16) != 0) {
            z2 = clockConfig.isReactiveToTone;
        }
        boolean z5 = z2;
        if ((i & 32) != 0) {
            z3 = clockConfig.useCustomClockScene;
        }
        return clockConfig.copy(str, str4, str5, z4, z5, z3);
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
        return this.isReactiveToTone;
    }

    public final boolean component6() {
        return this.useCustomClockScene;
    }

    public final ClockConfig copy(String str, String str2, String str3, boolean z, boolean z2, boolean z3) {
        return new ClockConfig(str, str2, str3, z, z2, z3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockConfig)) {
            return false;
        }
        ClockConfig clockConfig = (ClockConfig) obj;
        return Intrinsics.areEqual(this.id, clockConfig.id) && Intrinsics.areEqual(this.name, clockConfig.name) && Intrinsics.areEqual(this.description, clockConfig.description) && this.useAlternateSmartspaceAODTransition == clockConfig.useAlternateSmartspaceAODTransition && this.isReactiveToTone == clockConfig.isReactiveToTone && this.useCustomClockScene == clockConfig.useCustomClockScene;
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
        return Boolean.hashCode(this.useCustomClockScene) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name), 31, this.description), 31, this.useAlternateSmartspaceAODTransition), 31, this.isReactiveToTone);
    }

    public final boolean isReactiveToTone() {
        return this.isReactiveToTone;
    }

    public String toString() {
        String str = this.id;
        String str2 = this.name;
        String str3 = this.description;
        boolean z = this.useAlternateSmartspaceAODTransition;
        boolean z2 = this.isReactiveToTone;
        boolean z3 = this.useCustomClockScene;
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("ClockConfig(id=", str, ", name=", str2, ", description=");
        m.append(str3);
        m.append(", useAlternateSmartspaceAODTransition=");
        m.append(z);
        m.append(", isReactiveToTone=");
        m.append(z2);
        m.append(", useCustomClockScene=");
        m.append(z3);
        m.append(")");
        return m.toString();
    }

    public /* synthetic */ ClockConfig(String str, String str2, String str3, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, (i & 8) != 0 ? false : z, (i & 16) != 0 ? true : z2, (i & 32) != 0 ? false : z3);
    }
}
