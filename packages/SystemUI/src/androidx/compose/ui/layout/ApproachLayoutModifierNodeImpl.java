package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ApproachLayoutModifierNodeImpl extends Modifier.Node implements ApproachLayoutModifierNode {
    public Function1 isMeasurementApproachInProgress;
    public Function2 isPlacementApproachInProgress;
    public Function3 measureBlock;

    public ApproachLayoutModifierNodeImpl(Function3 function3, Function1 function1, Function2 function2) {
        this.measureBlock = function3;
        this.isMeasurementApproachInProgress = function1;
        this.isPlacementApproachInProgress = function2;
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode
    /* renamed from: approachMeasure-3p2s80s */
    public final MeasureResult mo603approachMeasure3p2s80s(ApproachMeasureScope approachMeasureScope, Measurable measurable, long j) {
        return (MeasureResult) this.measureBlock.invoke(approachMeasureScope, measurable, Constraints.m813boximpl(j));
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode
    /* renamed from: isMeasurementApproachInProgress-ozmzZPI */
    public final boolean mo604isMeasurementApproachInProgressozmzZPI(long j) {
        return ((Boolean) this.isMeasurementApproachInProgress.mo779invoke(IntSize.m859boximpl(j))).booleanValue();
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode
    public final boolean isPlacementApproachInProgress(Placeable.PlacementScope placementScope, LayoutCoordinates layoutCoordinates) {
        return ((Boolean) this.isPlacementApproachInProgress.invoke(placementScope, layoutCoordinates)).booleanValue();
    }
}
