package androidx.compose.foundation.gestures;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AnchoredDraggableKt$snapTo$2 extends SuspendLambda implements Function4 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    public AnchoredDraggableKt$snapTo$2(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        AnchoredDraggableKt$snapTo$2 anchoredDraggableKt$snapTo$2 = new AnchoredDraggableKt$snapTo$2((Continuation) obj4);
        anchoredDraggableKt$snapTo$2.L$0 = (AnchoredDragScope) obj;
        anchoredDraggableKt$snapTo$2.L$1 = (DraggableAnchors) obj2;
        anchoredDraggableKt$snapTo$2.L$2 = obj3;
        return anchoredDraggableKt$snapTo$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AnchoredDragScope anchoredDragScope = (AnchoredDragScope) this.L$0;
        float positionOf = ((DefaultDraggableAnchors) ((DraggableAnchors) this.L$1)).positionOf(this.L$2);
        if (!Float.isNaN(positionOf)) {
            ((AnchoredDraggableState$anchoredDragScope$1) anchoredDragScope).dragTo(positionOf, 0.0f);
        }
        return Unit.INSTANCE;
    }
}
