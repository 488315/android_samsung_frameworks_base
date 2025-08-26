package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes.dex */
final class AnchoredDraggableKt$animateTo$4 extends SuspendLambda implements Function4 {
    final /* synthetic */ AnimationSpec<Float> $animationSpec;
    final /* synthetic */ AnchoredDraggableState<Object> $this_animateTo;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnchoredDraggableKt$animateTo$4(AnchoredDraggableState<Object> anchoredDraggableState, AnimationSpec<Float> animationSpec, Continuation continuation) {
        super(4, continuation);
        this.$this_animateTo = anchoredDraggableState;
        this.$animationSpec = animationSpec;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        AnchoredDraggableKt$animateTo$4 anchoredDraggableKt$animateTo$4 = new AnchoredDraggableKt$animateTo$4(this.$this_animateTo, this.$animationSpec, (Continuation) obj4);
        anchoredDraggableKt$animateTo$4.L$0 = (AnchoredDragScope) obj;
        anchoredDraggableKt$animateTo$4.L$1 = (DraggableAnchors) obj2;
        anchoredDraggableKt$animateTo$4.L$2 = obj3;
        return anchoredDraggableKt$animateTo$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnchoredDragScope anchoredDragScope = (AnchoredDragScope) this.L$0;
            DraggableAnchors draggableAnchors = (DraggableAnchors) this.L$1;
            Object obj2 = this.L$2;
            AnchoredDraggableState<Object> anchoredDraggableState = this.$this_animateTo;
            float floatValue = ((SnapshotMutableFloatStateImpl) anchoredDraggableState.lastVelocity$delegate).getFloatValue();
            AnimationSpec<Float> animationSpec = this.$animationSpec;
            this.L$0 = null;
            this.L$1 = null;
            this.label = 1;
            if (AnchoredDraggableKt.access$animateTo(anchoredDraggableState, floatValue, anchoredDragScope, draggableAnchors, obj2, animationSpec, this) == coroutineSingletons) {
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
