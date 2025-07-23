package androidx.compose.material3.internal;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.SuspendAnimationKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Ref$FloatRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AnchoredDraggableKt$animateTo$2 extends SuspendLambda implements Function4 {
    final /* synthetic */ AnchoredDraggableState<Object> $this_animateTo;
    final /* synthetic */ float $velocity;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnchoredDraggableKt$animateTo$2(AnchoredDraggableState<Object> anchoredDraggableState, float f, Continuation continuation) {
        super(4, continuation);
        this.$this_animateTo = anchoredDraggableState;
        this.$velocity = f;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        AnchoredDraggableKt$animateTo$2 anchoredDraggableKt$animateTo$2 = new AnchoredDraggableKt$animateTo$2(this.$this_animateTo, this.$velocity, (Continuation) obj4);
        anchoredDraggableKt$animateTo$2.L$0 = (AnchoredDragScope) obj;
        anchoredDraggableKt$animateTo$2.L$1 = (DraggableAnchors) obj2;
        anchoredDraggableKt$animateTo$2.L$2 = obj3;
        return anchoredDraggableKt$animateTo$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final AnchoredDragScope anchoredDragScope = (AnchoredDragScope) this.L$0;
            float positionOf = ((MapDraggableAnchors) ((DraggableAnchors) this.L$1)).positionOf(this.L$2);
            if (!Float.isNaN(positionOf)) {
                final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
                float floatValue = Float.isNaN(((SnapshotMutableFloatStateImpl) this.$this_animateTo.offset$delegate).getFloatValue()) ? 0.0f : ((SnapshotMutableFloatStateImpl) this.$this_animateTo.offset$delegate).getFloatValue();
                ref$FloatRef.element = floatValue;
                float f = this.$velocity;
                AnimationSpec animationSpec = (AnimationSpec) this.$this_animateTo.animationSpec.invoke();
                Function2 function2 = new Function2() { // from class: androidx.compose.material3.internal.AnchoredDraggableKt$animateTo$2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        float floatValue2 = ((Number) obj2).floatValue();
                        float floatValue3 = ((Number) obj3).floatValue();
                        AnchoredDraggableState$anchoredDragScope$1 anchoredDraggableState$anchoredDragScope$1 = (AnchoredDraggableState$anchoredDragScope$1) AnchoredDragScope.this;
                        anchoredDraggableState$anchoredDragScope$1.getClass();
                        int i2 = AnchoredDraggableState.$r8$clinit;
                        AnchoredDraggableState anchoredDraggableState = anchoredDraggableState$anchoredDragScope$1.this$0;
                        ((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).setFloatValue(floatValue2);
                        ((SnapshotMutableFloatStateImpl) anchoredDraggableState.lastVelocity$delegate).setFloatValue(floatValue3);
                        ref$FloatRef.element = floatValue2;
                        return Unit.INSTANCE;
                    }
                };
                this.L$0 = null;
                this.L$1 = null;
                this.label = 1;
                if (SuspendAnimationKt.animate(floatValue, positionOf, f, animationSpec, function2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
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
