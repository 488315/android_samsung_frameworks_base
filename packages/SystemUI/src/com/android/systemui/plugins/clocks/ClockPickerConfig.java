package com.android.systemui.plugins.clocks;

import android.graphics.drawable.Drawable;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ClockPickerConfig {
    public static final int $stable = 8;
    private final List<ClockFontAxis> axes;
    private final String description;
    private final String id;
    private final boolean isReactiveToTone;
    private final String name;
    private final AxisPresetConfig presetConfig;
    private final Drawable thumbnail;

    public ClockPickerConfig(String str, String str2, String str3, Drawable drawable) {
        this(str, str2, str3, drawable, false, null, null, 112, null);
    }

    public static /* synthetic */ ClockPickerConfig copy$default(ClockPickerConfig clockPickerConfig, String str, String str2, String str3, Drawable drawable, boolean z, List list, AxisPresetConfig axisPresetConfig, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clockPickerConfig.id;
        }
        if ((i & 2) != 0) {
            str2 = clockPickerConfig.name;
        }
        if ((i & 4) != 0) {
            str3 = clockPickerConfig.description;
        }
        if ((i & 8) != 0) {
            drawable = clockPickerConfig.thumbnail;
        }
        if ((i & 16) != 0) {
            z = clockPickerConfig.isReactiveToTone;
        }
        if ((i & 32) != 0) {
            list = clockPickerConfig.axes;
        }
        if ((i & 64) != 0) {
            axisPresetConfig = clockPickerConfig.presetConfig;
        }
        List list2 = list;
        AxisPresetConfig axisPresetConfig2 = axisPresetConfig;
        boolean z2 = z;
        String str4 = str3;
        return clockPickerConfig.copy(str, str2, str4, drawable, z2, list2, axisPresetConfig2);
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

    public final Drawable component4() {
        return this.thumbnail;
    }

    public final boolean component5() {
        return this.isReactiveToTone;
    }

    public final List<ClockFontAxis> component6() {
        return this.axes;
    }

    public final AxisPresetConfig component7() {
        return this.presetConfig;
    }

    public final ClockPickerConfig copy(String str, String str2, String str3, Drawable drawable, boolean z, List<ClockFontAxis> list, AxisPresetConfig axisPresetConfig) {
        return new ClockPickerConfig(str, str2, str3, drawable, z, list, axisPresetConfig);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockPickerConfig)) {
            return false;
        }
        ClockPickerConfig clockPickerConfig = (ClockPickerConfig) obj;
        return Intrinsics.areEqual(this.id, clockPickerConfig.id) && Intrinsics.areEqual(this.name, clockPickerConfig.name) && Intrinsics.areEqual(this.description, clockPickerConfig.description) && Intrinsics.areEqual(this.thumbnail, clockPickerConfig.thumbnail) && this.isReactiveToTone == clockPickerConfig.isReactiveToTone && Intrinsics.areEqual(this.axes, clockPickerConfig.axes) && Intrinsics.areEqual(this.presetConfig, clockPickerConfig.presetConfig);
    }

    public final List<ClockFontAxis> getAxes() {
        return this.axes;
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

    public final AxisPresetConfig getPresetConfig() {
        return this.presetConfig;
    }

    public final Drawable getThumbnail() {
        return this.thumbnail;
    }

    public int hashCode() {
        int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.axes, TransitionData$$ExternalSyntheticOutline0.m((this.thumbnail.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name), 31, this.description)) * 31, 31, this.isReactiveToTone), 31);
        AxisPresetConfig axisPresetConfig = this.presetConfig;
        return iM + (axisPresetConfig == null ? 0 : axisPresetConfig.hashCode());
    }

    public final boolean isReactiveToTone() {
        return this.isReactiveToTone;
    }

    public String toString() {
        String str = this.id;
        String str2 = this.name;
        String str3 = this.description;
        Drawable drawable = this.thumbnail;
        boolean z = this.isReactiveToTone;
        List<ClockFontAxis> list = this.axes;
        AxisPresetConfig axisPresetConfig = this.presetConfig;
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("ClockPickerConfig(id=", str, ", name=", str2, ", description=");
        sbM.append(str3);
        sbM.append(", thumbnail=");
        sbM.append(drawable);
        sbM.append(", isReactiveToTone=");
        sbM.append(z);
        sbM.append(", axes=");
        sbM.append(list);
        sbM.append(", presetConfig=");
        sbM.append(axisPresetConfig);
        sbM.append(")");
        return sbM.toString();
    }

    public ClockPickerConfig(String str, String str2, String str3, Drawable drawable, boolean z) {
        this(str, str2, str3, drawable, z, null, null, 96, null);
    }

    public ClockPickerConfig(String str, String str2, String str3, Drawable drawable, boolean z, List<ClockFontAxis> list) {
        this(str, str2, str3, drawable, z, list, null, 64, null);
    }

    public ClockPickerConfig(String str, String str2, String str3, Drawable drawable, boolean z, List<ClockFontAxis> list, AxisPresetConfig axisPresetConfig) {
        this.id = str;
        this.name = str2;
        this.description = str3;
        this.thumbnail = drawable;
        this.isReactiveToTone = z;
        this.axes = list;
        this.presetConfig = axisPresetConfig;
    }

    public ClockPickerConfig(String str, String str2, String str3, Drawable drawable, boolean z, List list, AxisPresetConfig axisPresetConfig, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, drawable, (i & 16) != 0 ? true : z, (i & 32) != 0 ? EmptyList.INSTANCE : list, (i & 64) != 0 ? null : axisPresetConfig);
    }
}
