package androidx.compose.foundation.gestures;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AnchoredDraggableState$anchoredDragScope$1 implements AnchoredDragScope {
    public float distance = Float.NaN;
    public Object leftBound;
    public Object rightBound;
    public final /* synthetic */ AnchoredDraggableState this$0;

    public AnchoredDraggableState$anchoredDragScope$1(AnchoredDraggableState<Object> anchoredDraggableState) {
        this.this$0 = anchoredDraggableState;
    }

    public final void dragTo(float f, float f2) {
        AnchoredDraggableState anchoredDraggableState = this.this$0;
        float floatValue = ((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue();
        ((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).setFloatValue(f);
        ((SnapshotMutableFloatStateImpl) anchoredDraggableState.lastVelocity$delegate).setFloatValue(f2);
        if (Float.isNaN(floatValue)) {
            return;
        }
        boolean z = f >= floatValue;
        DraggableAnchors anchors = anchoredDraggableState.getAnchors();
        MutableState mutableState = anchoredDraggableState.currentValue$delegate;
        SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) mutableState;
        float positionOf = ((DefaultDraggableAnchors) anchors).positionOf(snapshotMutableStateImpl.getValue());
        MutableFloatState mutableFloatState = anchoredDraggableState.offset$delegate;
        SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) mutableFloatState;
        if (snapshotMutableFloatStateImpl.getFloatValue() == positionOf) {
            Object closestAnchor = ((DefaultDraggableAnchors) anchoredDraggableState.getAnchors()).closestAnchor(snapshotMutableFloatStateImpl.getFloatValue() + (z ? 1.0f : -1.0f), z);
            if (closestAnchor == null) {
                closestAnchor = snapshotMutableStateImpl.getValue();
            }
            if (z) {
                this.leftBound = snapshotMutableStateImpl.getValue();
                this.rightBound = closestAnchor;
            } else {
                this.leftBound = closestAnchor;
                this.rightBound = snapshotMutableStateImpl.getValue();
            }
        } else {
            Object closestAnchor2 = ((DefaultDraggableAnchors) anchoredDraggableState.getAnchors()).closestAnchor(snapshotMutableFloatStateImpl.getFloatValue(), false);
            if (closestAnchor2 == null) {
                closestAnchor2 = snapshotMutableStateImpl.getValue();
            }
            Object closestAnchor3 = ((DefaultDraggableAnchors) anchoredDraggableState.getAnchors()).closestAnchor(snapshotMutableFloatStateImpl.getFloatValue(), true);
            if (closestAnchor3 == null) {
                closestAnchor3 = snapshotMutableStateImpl.getValue();
            }
            this.leftBound = closestAnchor2;
            this.rightBound = closestAnchor3;
        }
        DraggableAnchors anchors2 = anchoredDraggableState.getAnchors();
        Object obj = this.leftBound;
        obj.getClass();
        float positionOf2 = ((DefaultDraggableAnchors) anchors2).positionOf(obj);
        DraggableAnchors anchors3 = anchoredDraggableState.getAnchors();
        Object obj2 = this.rightBound;
        obj2.getClass();
        this.distance = Math.abs(positionOf2 - ((DefaultDraggableAnchors) anchors3).positionOf(obj2));
        if (Math.abs(((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue() - ((DefaultDraggableAnchors) anchoredDraggableState.getAnchors()).positionOf(((SnapshotMutableStateImpl) mutableState).getValue())) >= this.distance / 2.0f) {
            Object obj3 = z ? this.rightBound : this.leftBound;
            if (obj3 == null) {
                obj3 = ((SnapshotMutableStateImpl) mutableState).getValue();
            }
            if (((Boolean) anchoredDraggableState.confirmValueChange.mo779invoke(obj3)).booleanValue()) {
                anchoredDraggableState.setCurrentValue(obj3);
            }
        }
    }
}
