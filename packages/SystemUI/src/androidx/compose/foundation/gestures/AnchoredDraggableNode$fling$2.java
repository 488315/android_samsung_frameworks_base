package androidx.compose.foundation.gestures;

import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$FloatRef;

/* loaded from: classes.dex */
final class AnchoredDraggableNode$fling$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ Ref$FloatRef $leftoverVelocity;
    final /* synthetic */ float $velocity;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AnchoredDraggableNode<Object> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnchoredDraggableNode$fling$2(AnchoredDraggableNode<Object> anchoredDraggableNode, Ref$FloatRef ref$FloatRef, float f, Continuation continuation) {
        super(3, continuation);
        this.this$0 = anchoredDraggableNode;
        this.$leftoverVelocity = ref$FloatRef;
        this.$velocity = f;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AnchoredDraggableNode$fling$2 anchoredDraggableNode$fling$2 = new AnchoredDraggableNode$fling$2(this.this$0, this.$leftoverVelocity, this.$velocity, (Continuation) obj3);
        anchoredDraggableNode$fling$2.L$0 = (AnchoredDragScope) obj;
        return anchoredDraggableNode$fling$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Ref$FloatRef ref$FloatRef;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final AnchoredDragScope anchoredDragScope = (AnchoredDragScope) this.L$0;
            final AnchoredDraggableNode<Object> anchoredDraggableNode = this.this$0;
            ScrollScope scrollScope = new ScrollScope() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableNode$fling$2$scrollScope$1
                @Override // androidx.compose.foundation.gestures.ScrollScope
                public final float scrollBy(float f) {
                    AnchoredDraggableNode anchoredDraggableNode2 = anchoredDraggableNode;
                    float fNewOffsetForDelta$foundation_release = anchoredDraggableNode2.state.newOffsetForDelta$foundation_release(f);
                    float floatValue = fNewOffsetForDelta$foundation_release - ((SnapshotMutableFloatStateImpl) anchoredDraggableNode2.state.offset$delegate).getFloatValue();
                    ((AnchoredDraggableState$anchoredDragScope$1) anchoredDragScope).dragTo(fNewOffsetForDelta$foundation_release, 0.0f);
                    return floatValue;
                }
            };
            FlingBehavior flingBehavior = this.this$0.resolvedFlingBehavior;
            if (flingBehavior == null) {
                flingBehavior = null;
            }
            Ref$FloatRef ref$FloatRef2 = this.$leftoverVelocity;
            float f = this.$velocity;
            this.L$0 = ref$FloatRef2;
            this.label = 1;
            obj = flingBehavior.performFling(scrollScope, f, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
            ref$FloatRef = ref$FloatRef2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ref$FloatRef = (Ref$FloatRef) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        ref$FloatRef.element = ((Number) obj).floatValue();
        return Unit.INSTANCE;
    }
}
