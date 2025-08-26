package androidx.compose.foundation.gestures;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;

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
        float fPositionOf = ((DefaultDraggableAnchors) anchors).positionOf(snapshotMutableStateImpl.getValue());
        MutableFloatState mutableFloatState = anchoredDraggableState.offset$delegate;
        SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) mutableFloatState;
        if (snapshotMutableFloatStateImpl.getFloatValue() == fPositionOf) {
            Object objClosestAnchor = ((DefaultDraggableAnchors) anchoredDraggableState.getAnchors()).closestAnchor(snapshotMutableFloatStateImpl.getFloatValue() + (z ? 1.0f : -1.0f), z);
            if (objClosestAnchor == null) {
                objClosestAnchor = snapshotMutableStateImpl.getValue();
            }
            if (z) {
                this.leftBound = snapshotMutableStateImpl.getValue();
                this.rightBound = objClosestAnchor;
            } else {
                this.leftBound = objClosestAnchor;
                this.rightBound = snapshotMutableStateImpl.getValue();
            }
        } else {
            Object objClosestAnchor2 = ((DefaultDraggableAnchors) anchoredDraggableState.getAnchors()).closestAnchor(snapshotMutableFloatStateImpl.getFloatValue(), false);
            if (objClosestAnchor2 == null) {
                objClosestAnchor2 = snapshotMutableStateImpl.getValue();
            }
            Object objClosestAnchor3 = ((DefaultDraggableAnchors) anchoredDraggableState.getAnchors()).closestAnchor(snapshotMutableFloatStateImpl.getFloatValue(), true);
            if (objClosestAnchor3 == null) {
                objClosestAnchor3 = snapshotMutableStateImpl.getValue();
            }
            this.leftBound = objClosestAnchor2;
            this.rightBound = objClosestAnchor3;
        }
        DraggableAnchors anchors2 = anchoredDraggableState.getAnchors();
        Object obj = this.leftBound;
        obj.getClass();
        float fPositionOf2 = ((DefaultDraggableAnchors) anchors2).positionOf(obj);
        DraggableAnchors anchors3 = anchoredDraggableState.getAnchors();
        Object obj2 = this.rightBound;
        obj2.getClass();
        this.distance = Math.abs(fPositionOf2 - ((DefaultDraggableAnchors) anchors3).positionOf(obj2));
        if (Math.abs(((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue() - ((DefaultDraggableAnchors) anchoredDraggableState.getAnchors()).positionOf(((SnapshotMutableStateImpl) mutableState).getValue())) >= this.distance / 2.0f) {
            Object value = z ? this.rightBound : this.leftBound;
            if (value == null) {
                value = ((SnapshotMutableStateImpl) mutableState).getValue();
            }
            if (((Boolean) anchoredDraggableState.confirmValueChange.mo781invoke(value)).booleanValue()) {
                anchoredDraggableState.setCurrentValue(value);
            }
        }
    }
}
