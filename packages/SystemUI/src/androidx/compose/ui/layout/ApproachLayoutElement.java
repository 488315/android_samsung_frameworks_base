package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ApproachLayoutElement extends ModifierNodeElement<ApproachLayoutModifierNodeImpl> {
    public final Function3 approachMeasure;
    public final Function1 isMeasurementApproachInProgress;
    public final Function2 isPlacementApproachInProgress;

    public /* synthetic */ ApproachLayoutElement(Function3 function3, Function1 function1, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(function3, function1, (i & 4) != 0 ? LookaheadScopeKt.defaultPlacementApproachInProgress : function2);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new ApproachLayoutModifierNodeImpl(this.approachMeasure, this.isMeasurementApproachInProgress, this.isPlacementApproachInProgress);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ApproachLayoutElement)) {
            return false;
        }
        ApproachLayoutElement approachLayoutElement = (ApproachLayoutElement) obj;
        return this.approachMeasure == approachLayoutElement.approachMeasure && this.isMeasurementApproachInProgress == approachLayoutElement.isMeasurementApproachInProgress && this.isPlacementApproachInProgress == approachLayoutElement.isPlacementApproachInProgress;
    }

    public final int hashCode() {
        return this.isPlacementApproachInProgress.hashCode() + ((this.isMeasurementApproachInProgress.hashCode() + (this.approachMeasure.hashCode() * 31)) * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ApproachLayoutModifierNodeImpl approachLayoutModifierNodeImpl = (ApproachLayoutModifierNodeImpl) node;
        approachLayoutModifierNodeImpl.measureBlock = this.approachMeasure;
        approachLayoutModifierNodeImpl.isMeasurementApproachInProgress = this.isMeasurementApproachInProgress;
        approachLayoutModifierNodeImpl.isPlacementApproachInProgress = this.isPlacementApproachInProgress;
    }

    public ApproachLayoutElement(Function3 function3, Function1 function1, Function2 function2) {
        this.approachMeasure = function3;
        this.isMeasurementApproachInProgress = function1;
        this.isPlacementApproachInProgress = function2;
    }
}
