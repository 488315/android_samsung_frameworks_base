package com.android.systemui.battery.unified;

import android.graphics.drawable.Drawable;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BatteryDrawableState {
    public static final Companion Companion = new Companion(null);
    public static final BatteryDrawableState DefaultInitialState = new BatteryDrawableState(50, false, null, null, 4, null);
    public final Drawable attribution;
    public final ColorProfile color;
    public final int level;
    public final boolean showPercent;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BatteryDrawableState(int i, boolean z, ColorProfile colorProfile, Drawable drawable) {
        this.level = i;
        this.showPercent = z;
        this.color = colorProfile;
        this.attribution = drawable;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BatteryDrawableState)) {
            return false;
        }
        BatteryDrawableState batteryDrawableState = (BatteryDrawableState) obj;
        return this.level == batteryDrawableState.level && this.showPercent == batteryDrawableState.showPercent && this.color == batteryDrawableState.color && Intrinsics.areEqual(this.attribution, batteryDrawableState.attribution);
    }

    public final int hashCode() {
        int hashCode = (this.color.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(Integer.hashCode(this.level) * 31, 31, this.showPercent)) * 31;
        Drawable drawable = this.attribution;
        return hashCode + (drawable == null ? 0 : drawable.hashCode());
    }

    public final String toString() {
        return "BatteryDrawableState(level=" + this.level + ", showPercent=" + this.showPercent + ", color=" + this.color + ", attribution=" + this.attribution + ")";
    }

    public /* synthetic */ BatteryDrawableState(int i, boolean z, ColorProfile colorProfile, Drawable drawable, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, z, (i2 & 4) != 0 ? ColorProfile.None : colorProfile, drawable);
    }
}
