package com.android.systemui.plugins.clocks;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ClockFontAxis {
    public static final int $stable = 0;
    public static final Companion Companion = new Companion(null);
    private final float currentValue;
    private final String description;
    private final String key;
    private final float maxValue;
    private final float minValue;
    private final String name;
    private final AxisType type;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final List<ClockFontAxis> merge(List<ClockFontAxis> list, ClockAxisStyle clockAxisStyle) {
            ClockFontAxis clockFontAxisCopy$default;
            List<ClockFontAxis> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (ClockFontAxis clockFontAxis : list2) {
                Float f = clockAxisStyle.get(clockFontAxis.getKey());
                if (f != null && (clockFontAxisCopy$default = ClockFontAxis.copy$default(clockFontAxis, null, null, 0.0f, 0.0f, f.floatValue(), null, null, 111, null)) != null) {
                    clockFontAxis = clockFontAxisCopy$default;
                }
                arrayList.add(clockFontAxis);
            }
            return CollectionsKt___CollectionsKt.toList(arrayList);
        }

        private Companion() {
        }
    }

    public ClockFontAxis(String str, AxisType axisType, float f, float f2, float f3, String str2, String str3) {
        this.key = str;
        this.type = axisType;
        this.maxValue = f;
        this.minValue = f2;
        this.currentValue = f3;
        this.name = str2;
        this.description = str3;
    }

    public static /* synthetic */ ClockFontAxis copy$default(ClockFontAxis clockFontAxis, String str, AxisType axisType, float f, float f2, float f3, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clockFontAxis.key;
        }
        if ((i & 2) != 0) {
            axisType = clockFontAxis.type;
        }
        if ((i & 4) != 0) {
            f = clockFontAxis.maxValue;
        }
        if ((i & 8) != 0) {
            f2 = clockFontAxis.minValue;
        }
        if ((i & 16) != 0) {
            f3 = clockFontAxis.currentValue;
        }
        if ((i & 32) != 0) {
            str2 = clockFontAxis.name;
        }
        if ((i & 64) != 0) {
            str3 = clockFontAxis.description;
        }
        String str4 = str2;
        String str5 = str3;
        float f4 = f3;
        float f5 = f;
        return clockFontAxis.copy(str, axisType, f5, f2, f4, str4, str5);
    }

    public final String component1() {
        return this.key;
    }

    public final AxisType component2() {
        return this.type;
    }

    public final float component3() {
        return this.maxValue;
    }

    public final float component4() {
        return this.minValue;
    }

    public final float component5() {
        return this.currentValue;
    }

    public final String component6() {
        return this.name;
    }

    public final String component7() {
        return this.description;
    }

    public final ClockFontAxis copy(String str, AxisType axisType, float f, float f2, float f3, String str2, String str3) {
        return new ClockFontAxis(str, axisType, f, f2, f3, str2, str3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockFontAxis)) {
            return false;
        }
        ClockFontAxis clockFontAxis = (ClockFontAxis) obj;
        return Intrinsics.areEqual(this.key, clockFontAxis.key) && this.type == clockFontAxis.type && Float.compare(this.maxValue, clockFontAxis.maxValue) == 0 && Float.compare(this.minValue, clockFontAxis.minValue) == 0 && Float.compare(this.currentValue, clockFontAxis.currentValue) == 0 && Intrinsics.areEqual(this.name, clockFontAxis.name) && Intrinsics.areEqual(this.description, clockFontAxis.description);
    }

    public final float getCurrentValue() {
        return this.currentValue;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getKey() {
        return this.key;
    }

    public final float getMaxValue() {
        return this.maxValue;
    }

    public final float getMinValue() {
        return this.minValue;
    }

    public final String getName() {
        return this.name;
    }

    public final AxisType getType() {
        return this.type;
    }

    public int hashCode() {
        return this.description.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.currentValue, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.minValue, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.maxValue, (this.type.hashCode() + (this.key.hashCode() * 31)) * 31, 31), 31), 31), 31, this.name);
    }

    public String toString() {
        String str = this.key;
        AxisType axisType = this.type;
        float f = this.maxValue;
        float f2 = this.minValue;
        float f3 = this.currentValue;
        String str2 = this.name;
        String str3 = this.description;
        StringBuilder sb = new StringBuilder("ClockFontAxis(key=");
        sb.append(str);
        sb.append(", type=");
        sb.append(axisType);
        sb.append(", maxValue=");
        sb.append(f);
        sb.append(", minValue=");
        sb.append(f2);
        sb.append(", currentValue=");
        sb.append(f3);
        sb.append(", name=");
        sb.append(str2);
        sb.append(", description=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, str3, ")");
    }
}
