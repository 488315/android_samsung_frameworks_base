package androidx.compose.foundation.gestures;

import androidx.compose.foundation.gestures.DragEvent;
import androidx.compose.ui.geometry.Offset;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DraggableNode$drag$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $forEachDelta;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DraggableNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DraggableNode$drag$2(Function2 function2, DraggableNode draggableNode, Continuation continuation) {
        super(2, continuation);
        this.$forEachDelta = function2;
        this.this$0 = draggableNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DraggableNode$drag$2 draggableNode$drag$2 = new DraggableNode$drag$2(this.$forEachDelta, this.this$0, continuation);
        draggableNode$drag$2.L$0 = obj;
        return draggableNode$drag$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DraggableNode$drag$2) create((DragScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final DragScope dragScope = (DragScope) this.L$0;
            Function2 function2 = this.$forEachDelta;
            final DraggableNode draggableNode = this.this$0;
            Function1 function1 = new Function1() { // from class: androidx.compose.foundation.gestures.DraggableNode$drag$2.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    DragScope dragScope2 = DragScope.this;
                    DraggableNode draggableNode2 = draggableNode;
                    long m402timestuRUvjQ = Offset.m402timestuRUvjQ(draggableNode2.reverseDirection ? -1.0f : 1.0f, ((DragEvent.DragDelta) obj2).delta);
                    Orientation orientation = draggableNode.orientation;
                    Function3 function3 = DraggableKt.NoOpOnDragStarted;
                    dragScope2.dragBy(Float.intBitsToFloat((int) (orientation == Orientation.Vertical ? 4294967295L & m402timestuRUvjQ : m402timestuRUvjQ >> 32)));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (function2.invoke(function1, this) == coroutineSingletons) {
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
