package com.android.systemui.plugins.clocks;

import android.content.Context;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.internal.policy.SystemBarUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ClockPreviewConfig {
    public static final int $stable = 8;
    private final Context context;
    private final boolean isSceneContainerFlagEnabled;
    private final boolean isShadeLayoutWide;
    private final Integer lockId;
    private final Float udfpsTop;

    public ClockPreviewConfig(Context context, boolean z, boolean z2, Integer num, Float f) {
        this.context = context;
        this.isShadeLayoutWide = z;
        this.isSceneContainerFlagEnabled = z2;
        this.lockId = num;
        this.udfpsTop = f;
    }

    public static /* synthetic */ ClockPreviewConfig copy$default(ClockPreviewConfig clockPreviewConfig, Context context, boolean z, boolean z2, Integer num, Float f, int i, Object obj) {
        if ((i & 1) != 0) {
            context = clockPreviewConfig.context;
        }
        if ((i & 2) != 0) {
            z = clockPreviewConfig.isShadeLayoutWide;
        }
        if ((i & 4) != 0) {
            z2 = clockPreviewConfig.isSceneContainerFlagEnabled;
        }
        if ((i & 8) != 0) {
            num = clockPreviewConfig.lockId;
        }
        if ((i & 16) != 0) {
            f = clockPreviewConfig.udfpsTop;
        }
        Float f2 = f;
        boolean z3 = z2;
        return clockPreviewConfig.copy(context, z, z3, num, f2);
    }

    public static /* synthetic */ int getSmallClockTopPadding$default(ClockPreviewConfig clockPreviewConfig, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = SystemBarUtils.getStatusBarHeight(clockPreviewConfig.context);
        }
        return clockPreviewConfig.getSmallClockTopPadding(i);
    }

    public final Context component1() {
        return this.context;
    }

    public final boolean component2() {
        return this.isShadeLayoutWide;
    }

    public final boolean component3() {
        return this.isSceneContainerFlagEnabled;
    }

    public final Integer component4() {
        return this.lockId;
    }

    public final Float component5() {
        return this.udfpsTop;
    }

    public final ClockPreviewConfig copy(Context context, boolean z, boolean z2, Integer num, Float f) {
        return new ClockPreviewConfig(context, z, z2, num, f);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockPreviewConfig)) {
            return false;
        }
        ClockPreviewConfig clockPreviewConfig = (ClockPreviewConfig) obj;
        return Intrinsics.areEqual(this.context, clockPreviewConfig.context) && this.isShadeLayoutWide == clockPreviewConfig.isShadeLayoutWide && this.isSceneContainerFlagEnabled == clockPreviewConfig.isSceneContainerFlagEnabled && Intrinsics.areEqual(this.lockId, clockPreviewConfig.lockId) && Intrinsics.areEqual(this.udfpsTop, clockPreviewConfig.udfpsTop);
    }

    public final Context getContext() {
        return this.context;
    }

    public final Integer getLockId() {
        return this.lockId;
    }

    public final int getSmallClockTopPadding(int i) {
        if (this.isShadeLayoutWide) {
            int dimen = ContextExt.INSTANCE.getDimen(this.context, "keyguard_split_shade_top_margin");
            if (!this.isSceneContainerFlagEnabled) {
                i = 0;
            }
            return dimen - i;
        }
        int dimen2 = ContextExt.INSTANCE.getDimen(this.context, "keyguard_clock_top_margin");
        if (this.isSceneContainerFlagEnabled) {
            i = 0;
        }
        return dimen2 + i;
    }

    public final Float getUdfpsTop() {
        return this.udfpsTop;
    }

    public int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.context.hashCode() * 31, 31, this.isShadeLayoutWide), 31, this.isSceneContainerFlagEnabled);
        Integer num = this.lockId;
        int hashCode = (m + (num == null ? 0 : num.hashCode())) * 31;
        Float f = this.udfpsTop;
        return hashCode + (f != null ? f.hashCode() : 0);
    }

    public final boolean isSceneContainerFlagEnabled() {
        return this.isSceneContainerFlagEnabled;
    }

    public final boolean isShadeLayoutWide() {
        return this.isShadeLayoutWide;
    }

    public String toString() {
        return "ClockPreviewConfig(context=" + this.context + ", isShadeLayoutWide=" + this.isShadeLayoutWide + ", isSceneContainerFlagEnabled=" + this.isSceneContainerFlagEnabled + ", lockId=" + this.lockId + ", udfpsTop=" + this.udfpsTop + ")";
    }

    public /* synthetic */ ClockPreviewConfig(Context context, boolean z, boolean z2, Integer num, Float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, z, (i & 4) != 0 ? false : z2, (i & 8) != 0 ? null : num, (i & 16) != 0 ? null : f);
    }
}
