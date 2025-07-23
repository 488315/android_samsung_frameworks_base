package androidx.compose.material3.internal;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.gestures.DragScope;
import androidx.compose.foundation.gestures.DraggableState;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AnchoredDraggableState$draggableState$1 implements DraggableState {
    public final AnchoredDraggableState$draggableState$1$dragScope$1 dragScope;
    public final /* synthetic */ AnchoredDraggableState this$0;

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.compose.material3.internal.AnchoredDraggableState$draggableState$1$dragScope$1] */
    public AnchoredDraggableState$draggableState$1(final AnchoredDraggableState<Object> anchoredDraggableState) {
        this.this$0 = anchoredDraggableState;
        this.dragScope = new DragScope() { // from class: androidx.compose.material3.internal.AnchoredDraggableState$draggableState$1$dragScope$1
            @Override // androidx.compose.foundation.gestures.DragScope
            public final void dragBy(float f) {
                AnchoredDraggableState anchoredDraggableState2 = anchoredDraggableState;
                AnchoredDraggableState$anchoredDragScope$1 anchoredDraggableState$anchoredDragScope$1 = anchoredDraggableState2.anchoredDragScope;
                float newOffsetForDelta$material3_release = anchoredDraggableState2.newOffsetForDelta$material3_release(f);
                anchoredDraggableState$anchoredDragScope$1.getClass();
                int i = AnchoredDraggableState.$r8$clinit;
                AnchoredDraggableState anchoredDraggableState3 = anchoredDraggableState$anchoredDragScope$1.this$0;
                ((SnapshotMutableFloatStateImpl) anchoredDraggableState3.offset$delegate).setFloatValue(newOffsetForDelta$material3_release);
                ((SnapshotMutableFloatStateImpl) anchoredDraggableState3.lastVelocity$delegate).setFloatValue(0.0f);
            }
        };
    }

    @Override // androidx.compose.foundation.gestures.DraggableState
    public final Object drag(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
        Object anchoredDrag = this.this$0.anchoredDrag(mutatePriority, new AnchoredDraggableState$draggableState$1$drag$2(this, function2, null), (ContinuationImpl) continuation);
        return anchoredDrag == CoroutineSingletons.COROUTINE_SUSPENDED ? anchoredDrag : Unit.INSTANCE;
    }
}
