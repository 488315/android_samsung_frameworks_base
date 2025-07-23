package androidx.compose.foundation.gestures;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.gestures.DragEvent;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
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
final class AnchoredDraggableNode$drag$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ Function2 $forEachDelta;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AnchoredDraggableNode<Object> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnchoredDraggableNode$drag$2(Function2 function2, AnchoredDraggableNode<Object> anchoredDraggableNode, Continuation continuation) {
        super(3, continuation);
        this.$forEachDelta = function2;
        this.this$0 = anchoredDraggableNode;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AnchoredDraggableNode$drag$2 anchoredDraggableNode$drag$2 = new AnchoredDraggableNode$drag$2(this.$forEachDelta, this.this$0, (Continuation) obj3);
        anchoredDraggableNode$drag$2.L$0 = (AnchoredDragScope) obj;
        return anchoredDraggableNode$drag$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final AnchoredDragScope anchoredDragScope = (AnchoredDragScope) this.L$0;
            Function2 function2 = this.$forEachDelta;
            final AnchoredDraggableNode<Object> anchoredDraggableNode = this.this$0;
            Function1 function1 = new Function1() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableNode$drag$2.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    AnchoredDraggableNode<Object> anchoredDraggableNode2 = anchoredDraggableNode;
                    long m402timestuRUvjQ = Offset.m402timestuRUvjQ(anchoredDraggableNode2.isReverseDirection() ? -1.0f : 1.0f, ((DragEvent.DragDelta) obj2).delta);
                    float intBitsToFloat = Float.intBitsToFloat((int) (anchoredDraggableNode2.orientation == Orientation.Vertical ? m402timestuRUvjQ & 4294967295L : m402timestuRUvjQ >> 32));
                    AnchoredDraggableNode<Object> anchoredDraggableNode3 = anchoredDraggableNode;
                    OverscrollEffect overscrollEffect = anchoredDraggableNode3.overscrollEffect;
                    if (overscrollEffect == null) {
                        ((AnchoredDraggableState$anchoredDragScope$1) anchoredDragScope).dragTo(anchoredDraggableNode3.state.newOffsetForDelta$foundation_release(intBitsToFloat), 0.0f);
                    } else {
                        long m61access$toOffsettuRUvjQ = AnchoredDraggableNode.m61access$toOffsettuRUvjQ(anchoredDraggableNode3, intBitsToFloat);
                        NestedScrollSource.Companion.getClass();
                        int i2 = NestedScrollSource.UserInput;
                        final AnchoredDraggableNode<Object> anchoredDraggableNode4 = anchoredDraggableNode;
                        final AnchoredDragScope anchoredDragScope2 = anchoredDragScope;
                        overscrollEffect.mo20applyToScrollRhakbz0(i2, m61access$toOffsettuRUvjQ, new Function1() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableNode.drag.2.1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj3) {
                                long j = ((Offset) obj3).packedValue;
                                AnchoredDraggableNode<Object> anchoredDraggableNode5 = anchoredDraggableNode4;
                                float newOffsetForDelta$foundation_release = anchoredDraggableNode5.state.newOffsetForDelta$foundation_release(Float.intBitsToFloat((int) (anchoredDraggableNode5.orientation == Orientation.Vertical ? j & 4294967295L : j >> 32)));
                                AnchoredDraggableNode<Object> anchoredDraggableNode6 = anchoredDraggableNode4;
                                long m61access$toOffsettuRUvjQ2 = AnchoredDraggableNode.m61access$toOffsettuRUvjQ(anchoredDraggableNode6, newOffsetForDelta$foundation_release - anchoredDraggableNode6.state.requireOffset());
                                ((AnchoredDraggableState$anchoredDragScope$1) anchoredDragScope2).dragTo(newOffsetForDelta$foundation_release, 0.0f);
                                return Offset.m393boximpl(m61access$toOffsettuRUvjQ2);
                            }
                        });
                    }
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
