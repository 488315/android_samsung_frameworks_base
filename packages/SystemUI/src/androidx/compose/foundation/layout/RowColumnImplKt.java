package androidx.compose.foundation.layout;

import androidx.compose.ui.layout.IntrinsicMeasurable;

/* loaded from: classes.dex */
public abstract class RowColumnImplKt {
    public static final RowColumnParentData getRowColumnParentData(IntrinsicMeasurable intrinsicMeasurable) {
        Object parentData = intrinsicMeasurable.getParentData();
        if (parentData instanceof RowColumnParentData) {
            return (RowColumnParentData) parentData;
        }
        return null;
    }

    public static final float getWeight(RowColumnParentData rowColumnParentData) {
        if (rowColumnParentData != null) {
            return rowColumnParentData.weight;
        }
        return 0.0f;
    }
}
