package com.android.systemui.plugins.clocks;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ClockFaceConfig {
    private final boolean hasCustomPositionUpdatedAnimation;
    private final boolean hasCustomWeatherDataDisplay;
    private final ClockTickRate tickRate;
    private final boolean useCustomClockScene;

    public ClockFaceConfig() {
        this(null, false, false, false, 15, null);
    }

    public static /* synthetic */ ClockFaceConfig copy$default(ClockFaceConfig clockFaceConfig, ClockTickRate clockTickRate, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 1) != 0) {
            clockTickRate = clockFaceConfig.tickRate;
        }
        if ((i & 2) != 0) {
            z = clockFaceConfig.hasCustomWeatherDataDisplay;
        }
        if ((i & 4) != 0) {
            z2 = clockFaceConfig.hasCustomPositionUpdatedAnimation;
        }
        if ((i & 8) != 0) {
            z3 = clockFaceConfig.useCustomClockScene;
        }
        return clockFaceConfig.copy(clockTickRate, z, z2, z3);
    }

    public final ClockTickRate component1() {
        return this.tickRate;
    }

    public final boolean component2() {
        return this.hasCustomWeatherDataDisplay;
    }

    public final boolean component3() {
        return this.hasCustomPositionUpdatedAnimation;
    }

    public final boolean component4() {
        return this.useCustomClockScene;
    }

    public final ClockFaceConfig copy(ClockTickRate clockTickRate, boolean z, boolean z2, boolean z3) {
        return new ClockFaceConfig(clockTickRate, z, z2, z3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockFaceConfig)) {
            return false;
        }
        ClockFaceConfig clockFaceConfig = (ClockFaceConfig) obj;
        return this.tickRate == clockFaceConfig.tickRate && this.hasCustomWeatherDataDisplay == clockFaceConfig.hasCustomWeatherDataDisplay && this.hasCustomPositionUpdatedAnimation == clockFaceConfig.hasCustomPositionUpdatedAnimation && this.useCustomClockScene == clockFaceConfig.useCustomClockScene;
    }

    public final boolean getHasCustomPositionUpdatedAnimation() {
        return this.hasCustomPositionUpdatedAnimation;
    }

    public final boolean getHasCustomWeatherDataDisplay() {
        return this.hasCustomWeatherDataDisplay;
    }

    public final ClockTickRate getTickRate() {
        return this.tickRate;
    }

    public final boolean getUseCustomClockScene() {
        return this.useCustomClockScene;
    }

    public int hashCode() {
        return Boolean.hashCode(this.useCustomClockScene) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.tickRate.hashCode() * 31, 31, this.hasCustomWeatherDataDisplay), 31, this.hasCustomPositionUpdatedAnimation);
    }

    public String toString() {
        return "ClockFaceConfig(tickRate=" + this.tickRate + ", hasCustomWeatherDataDisplay=" + this.hasCustomWeatherDataDisplay + ", hasCustomPositionUpdatedAnimation=" + this.hasCustomPositionUpdatedAnimation + ", useCustomClockScene=" + this.useCustomClockScene + ")";
    }

    public ClockFaceConfig(ClockTickRate clockTickRate, boolean z, boolean z2, boolean z3) {
        this.tickRate = clockTickRate;
        this.hasCustomWeatherDataDisplay = z;
        this.hasCustomPositionUpdatedAnimation = z2;
        this.useCustomClockScene = z3;
    }

    public /* synthetic */ ClockFaceConfig(ClockTickRate clockTickRate, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? ClockTickRate.PER_MINUTE : clockTickRate, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2, (i & 8) != 0 ? false : z3);
    }
}
