package androidx.compose.foundation.layout;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RowColumnParentData {
    public CrossAxisAlignment crossAxisAlignment;
    public boolean fill;
    public final FlowLayoutData flowLayoutData;
    public float weight;

    public RowColumnParentData() {
        this(0.0f, false, null, null, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RowColumnParentData)) {
            return false;
        }
        RowColumnParentData rowColumnParentData = (RowColumnParentData) obj;
        return Float.compare(this.weight, rowColumnParentData.weight) == 0 && this.fill == rowColumnParentData.fill && Intrinsics.areEqual(this.crossAxisAlignment, rowColumnParentData.crossAxisAlignment) && Intrinsics.areEqual(this.flowLayoutData, rowColumnParentData.flowLayoutData);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(Float.hashCode(this.weight) * 31, 31, this.fill);
        CrossAxisAlignment crossAxisAlignment = this.crossAxisAlignment;
        int hashCode = (m + (crossAxisAlignment == null ? 0 : crossAxisAlignment.hashCode())) * 31;
        FlowLayoutData flowLayoutData = this.flowLayoutData;
        return hashCode + (flowLayoutData != null ? Float.hashCode(flowLayoutData.fillCrossAxisFraction) : 0);
    }

    public final String toString() {
        return "RowColumnParentData(weight=" + this.weight + ", fill=" + this.fill + ", crossAxisAlignment=" + this.crossAxisAlignment + ", flowLayoutData=" + this.flowLayoutData + ')';
    }

    public RowColumnParentData(float f, boolean z, CrossAxisAlignment crossAxisAlignment, FlowLayoutData flowLayoutData) {
        this.weight = f;
        this.fill = z;
        this.crossAxisAlignment = crossAxisAlignment;
        this.flowLayoutData = flowLayoutData;
    }

    public /* synthetic */ RowColumnParentData(float f, boolean z, CrossAxisAlignment crossAxisAlignment, FlowLayoutData flowLayoutData, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? true : z, (i & 4) != 0 ? null : crossAxisAlignment, (i & 8) != 0 ? null : flowLayoutData);
    }
}
