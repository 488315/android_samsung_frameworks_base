package androidx.compose.ui.input.pointer;

import androidx.collection.LongSparseArray;
import java.util.List;

/* loaded from: classes.dex */
public final class InternalPointerEvent {
    public final LongSparseArray changes;
    public final PointerInputEvent pointerInputEvent;
    public boolean suppressMovementConsumption;

    public InternalPointerEvent(LongSparseArray longSparseArray, PointerInputEvent pointerInputEvent) {
        this.changes = longSparseArray;
        this.pointerInputEvent = pointerInputEvent;
    }

    /* renamed from: activeHoverEvent-0FcD4WY, reason: not valid java name */
    public final boolean m590activeHoverEvent0FcD4WY(long j) {
        Object obj;
        List list = this.pointerInputEvent.pointers;
        int size = list.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                obj = null;
                break;
            }
            obj = list.get(i);
            if (PointerId.m593equalsimpl0(((PointerInputEventData) obj).id, j)) {
                break;
            }
            i++;
        }
        PointerInputEventData pointerInputEventData = (PointerInputEventData) obj;
        if (pointerInputEventData != null) {
            return pointerInputEventData.activeHover;
        }
        return false;
    }
}
