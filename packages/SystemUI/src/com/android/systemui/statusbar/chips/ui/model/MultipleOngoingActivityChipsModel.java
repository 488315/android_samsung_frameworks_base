package com.android.systemui.statusbar.chips.ui.model;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultipleOngoingActivityChipsModel {
    public final List active;
    public final List inactive;
    public final List overflow;

    public MultipleOngoingActivityChipsModel() {
        this(null, null, null, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MultipleOngoingActivityChipsModel)) {
            return false;
        }
        MultipleOngoingActivityChipsModel multipleOngoingActivityChipsModel = (MultipleOngoingActivityChipsModel) obj;
        return Intrinsics.areEqual(this.active, multipleOngoingActivityChipsModel.active) && Intrinsics.areEqual(this.overflow, multipleOngoingActivityChipsModel.overflow) && Intrinsics.areEqual(this.inactive, multipleOngoingActivityChipsModel.inactive);
    }

    public final int hashCode() {
        return this.inactive.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.overflow, this.active.hashCode() * 31, 31);
    }

    public final String toString() {
        return "MultipleOngoingActivityChipsModel(active=" + this.active + ", overflow=" + this.overflow + ", inactive=" + this.inactive + ")";
    }

    public MultipleOngoingActivityChipsModel(List<? extends OngoingActivityChipModel.Active> list, List<? extends OngoingActivityChipModel.Active> list2, List<OngoingActivityChipModel.Inactive> list3) {
        this.active = list;
        this.overflow = list2;
        this.inactive = list3;
    }

    public MultipleOngoingActivityChipsModel(List list, List list2, List list3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? EmptyList.INSTANCE : list, (i & 2) != 0 ? EmptyList.INSTANCE : list2, (i & 4) != 0 ? EmptyList.INSTANCE : list3);
    }
}
