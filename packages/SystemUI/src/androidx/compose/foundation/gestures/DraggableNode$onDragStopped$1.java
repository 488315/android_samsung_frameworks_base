package androidx.compose.foundation.gestures;

import androidx.compose.ui.unit.Velocity;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DraggableNode$onDragStopped$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $velocity;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DraggableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DraggableNode$onDragStopped$1(DraggableNode draggableNode, long j, Continuation continuation) {
        super(2, continuation);
        this.this$0 = draggableNode;
        this.$velocity = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DraggableNode$onDragStopped$1 draggableNode$onDragStopped$1 = new DraggableNode$onDragStopped$1(this.this$0, this.$velocity, continuation);
        draggableNode$onDragStopped$1.L$0 = obj;
        return draggableNode$onDragStopped$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DraggableNode$onDragStopped$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            DraggableNode draggableNode = this.this$0;
            Function3 function3 = draggableNode.onDragStopped;
            long m882timesadjELrA = Velocity.m882timesadjELrA(draggableNode.reverseDirection ? -1.0f : 1.0f, this.$velocity);
            Orientation orientation = this.this$0.orientation;
            Function3 function32 = DraggableKt.NoOpOnDragStarted;
            Float f = new Float(orientation == Orientation.Vertical ? Velocity.m879getYimpl(m882timesadjELrA) : Velocity.m878getXimpl(m882timesadjELrA));
            this.label = 1;
            if (function3.invoke(coroutineScope, f, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
