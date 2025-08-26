package com.android.systemui.statusbar.pipeline.shared.ui.model;

import com.android.systemui.statusbar.chips.ui.model.MultipleOngoingActivityChipsModel;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class ChipsVisibilityModel {
    public final boolean areChipsAllowed;
    public final MultipleOngoingActivityChipsModel chips;

    public ChipsVisibilityModel(MultipleOngoingActivityChipsModel multipleOngoingActivityChipsModel, boolean z) {
        this.chips = multipleOngoingActivityChipsModel;
        this.areChipsAllowed = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChipsVisibilityModel)) {
            return false;
        }
        ChipsVisibilityModel chipsVisibilityModel = (ChipsVisibilityModel) obj;
        return Intrinsics.areEqual(this.chips, chipsVisibilityModel.chips) && this.areChipsAllowed == chipsVisibilityModel.areChipsAllowed;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.areChipsAllowed) + (this.chips.hashCode() * 31);
    }

    public final String toString() {
        return "ChipsVisibilityModel(chips=" + this.chips + ", areChipsAllowed=" + this.areChipsAllowed + ")";
    }
}
