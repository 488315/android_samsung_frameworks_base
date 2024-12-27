package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BurnInScaleViewModel {
    public final float scale;
    public final boolean scaleClockOnly;

    public BurnInScaleViewModel() {
        this(0.0f, false, 3, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BurnInScaleViewModel)) {
            return false;
        }
        BurnInScaleViewModel burnInScaleViewModel = (BurnInScaleViewModel) obj;
        return Float.compare(this.scale, burnInScaleViewModel.scale) == 0 && this.scaleClockOnly == burnInScaleViewModel.scaleClockOnly;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.scaleClockOnly) + (Float.hashCode(this.scale) * 31);
    }

    public final String toString() {
        return "BurnInScaleViewModel(scale=" + this.scale + ", scaleClockOnly=" + this.scaleClockOnly + ")";
    }

    public BurnInScaleViewModel(float f, boolean z) {
        this.scale = f;
        this.scaleClockOnly = z;
    }

    public /* synthetic */ BurnInScaleViewModel(float f, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 1.0f : f, (i & 2) != 0 ? false : z);
    }
}
