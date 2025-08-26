package com.android.systemui.statusbar.chips.ui.model;

import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class MultipleOngoingActivityChipsModelLegacy {
    public final OngoingActivityChipModel primary;
    public final OngoingActivityChipModel secondary;

    /* JADX WARN: Multi-variable type inference failed */
    public MultipleOngoingActivityChipsModelLegacy() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MultipleOngoingActivityChipsModelLegacy)) {
            return false;
        }
        MultipleOngoingActivityChipsModelLegacy multipleOngoingActivityChipsModelLegacy = (MultipleOngoingActivityChipsModelLegacy) obj;
        return Intrinsics.areEqual(this.primary, multipleOngoingActivityChipsModelLegacy.primary) && Intrinsics.areEqual(this.secondary, multipleOngoingActivityChipsModelLegacy.secondary);
    }

    public final int hashCode() {
        return this.secondary.hashCode() + (this.primary.hashCode() * 31);
    }

    public final String toString() {
        return "MultipleOngoingActivityChipsModelLegacy(primary=" + this.primary + ", secondary=" + this.secondary + ")";
    }

    public MultipleOngoingActivityChipsModelLegacy(OngoingActivityChipModel ongoingActivityChipModel, OngoingActivityChipModel ongoingActivityChipModel2) {
        this.primary = ongoingActivityChipModel;
        this.secondary = ongoingActivityChipModel2;
        if ((ongoingActivityChipModel instanceof OngoingActivityChipModel.Inactive) && (ongoingActivityChipModel2 instanceof OngoingActivityChipModel.Active)) {
            throw new IllegalArgumentException("`secondary` cannot be Active if `primary` is Inactive");
        }
    }

    public /* synthetic */ MultipleOngoingActivityChipsModelLegacy(OngoingActivityChipModel ongoingActivityChipModel, OngoingActivityChipModel ongoingActivityChipModel2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new OngoingActivityChipModel.Inactive(false, null, 3, null) : ongoingActivityChipModel, (i & 2) != 0 ? new OngoingActivityChipModel.Inactive(false, null, 3, null) : ongoingActivityChipModel2);
    }
}
