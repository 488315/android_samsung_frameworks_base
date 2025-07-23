package androidx.compose.ui.node;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.layout.MeasurePolicy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class IntrinsicsPolicy {
    public final LayoutNode layoutNode;
    public final MutableState measurePolicyState$delegate;

    public IntrinsicsPolicy(LayoutNode layoutNode, MeasurePolicy measurePolicy) {
        this.layoutNode = layoutNode;
        this.measurePolicyState$delegate = SnapshotStateKt.mutableStateOf$default(measurePolicy);
    }

    public final MeasurePolicy getMeasurePolicyState() {
        return (MeasurePolicy) ((SnapshotMutableStateImpl) this.measurePolicyState$delegate).getValue();
    }
}
