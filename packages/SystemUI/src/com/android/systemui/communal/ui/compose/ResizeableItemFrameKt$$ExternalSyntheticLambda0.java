package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridMeasureResult;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class ResizeableItemFrameKt$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ResizeableItemFrameKt$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ResizeableItemFrameViewModel resizeableItemFrameViewModel = (ResizeableItemFrameViewModel) this.f$0;
                float floatValue = ((SnapshotMutableFloatStateImpl) resizeableItemFrameViewModel.topDragState.offset$delegate).getFloatValue();
                Float fValueOf = Float.valueOf(floatValue);
                if ((Float.floatToRawIntBits(floatValue) & Integer.MAX_VALUE) >= 2139095040) {
                    fValueOf = null;
                }
                float fFloatValue = fValueOf != null ? fValueOf.floatValue() : 0.0f;
                float floatValue2 = ((SnapshotMutableFloatStateImpl) resizeableItemFrameViewModel.bottomDragState.offset$delegate).getFloatValue();
                Float fValueOf2 = (Float.floatToRawIntBits(floatValue2) & Integer.MAX_VALUE) < 2139095040 ? Float.valueOf(floatValue2) : null;
                return Boolean.valueOf(fFloatValue > 0.0f || (fValueOf2 != null ? fValueOf2.floatValue() : 0.0f) > 0.0f);
            case 1:
                return Integer.valueOf(((LazyGridMeasureResult) ((LazyGridState) this.f$0).getLayoutInfo()).slotsPerLine);
            default:
                return Integer.valueOf((int) (((LazyGridMeasureResult) ((LazyGridState) this.f$0).getLayoutInfo()).m161getViewportSizeYbymL2g() & 4294967295L));
        }
    }
}
