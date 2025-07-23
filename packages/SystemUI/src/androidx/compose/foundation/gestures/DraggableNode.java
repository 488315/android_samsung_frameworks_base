package androidx.compose.foundation.gestures;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DraggableNode extends DragGestureNode {
    public Function3 onDragStarted;
    public Function3 onDragStopped;
    public Orientation orientation;
    public boolean reverseDirection;
    public boolean startDragImmediately;
    public DraggableState state;

    public DraggableNode(DraggableState draggableState, Function1 function1, Orientation orientation, boolean z, MutableInteractionSource mutableInteractionSource, boolean z2, Function3 function3, Function3 function32, boolean z3) {
        super(function1, z, mutableInteractionSource, orientation);
        this.state = draggableState;
        this.orientation = orientation;
        this.startDragImmediately = z2;
        this.onDragStarted = function3;
        this.onDragStopped = function32;
        this.reverseDirection = z3;
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    public final Object drag(Function2 function2, Continuation continuation) {
        Object drag = this.state.drag(MutatePriority.UserInput, new DraggableNode$drag$2(function2, this, null), continuation);
        return drag == CoroutineSingletons.COROUTINE_SUSPENDED ? drag : Unit.INSTANCE;
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    /* renamed from: onDragStarted-k-4lQ0M */
    public final void mo62onDragStartedk4lQ0M(long j) {
        if (!this.isAttached || Intrinsics.areEqual(this.onDragStarted, DraggableKt.NoOpOnDragStarted)) {
            return;
        }
        BuildersKt.launch$default(getCoroutineScope(), null, CoroutineStart.UNDISPATCHED, new DraggableNode$onDragStarted$1(this, j, null), 1);
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    /* renamed from: onDragStopped-TH1AsA0 */
    public final void mo63onDragStoppedTH1AsA0(long j) {
        if (!this.isAttached || Intrinsics.areEqual(this.onDragStopped, DraggableKt.NoOpOnDragStopped)) {
            return;
        }
        BuildersKt.launch$default(getCoroutineScope(), null, CoroutineStart.UNDISPATCHED, new DraggableNode$onDragStopped$1(this, j, null), 1);
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    public final boolean startDragImmediately() {
        return this.startDragImmediately;
    }
}
