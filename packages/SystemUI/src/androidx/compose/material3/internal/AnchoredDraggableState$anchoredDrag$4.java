package androidx.compose.material3.internal;

import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AnchoredDraggableState$anchoredDrag$4 extends SuspendLambda implements Function1 {
    final /* synthetic */ Function4 $block;
    final /* synthetic */ Object $targetValue;
    int label;
    final /* synthetic */ AnchoredDraggableState<Object> this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$4$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function4 $block;
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ AnchoredDraggableState<Object> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Function4 function4, AnchoredDraggableState<Object> anchoredDraggableState, Continuation continuation) {
            super(2, continuation);
            this.$block = function4;
            this.this$0 = anchoredDraggableState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$block, this.this$0, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Pair pair = (Pair) this.L$0;
                DraggableAnchors draggableAnchors = (DraggableAnchors) pair.component1();
                Object component2 = pair.component2();
                Function4 function4 = this.$block;
                AnchoredDraggableState$anchoredDragScope$1 anchoredDraggableState$anchoredDragScope$1 = this.this$0.anchoredDragScope;
                this.label = 1;
                if (function4.invoke(anchoredDraggableState$anchoredDragScope$1, draggableAnchors, component2, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnchoredDraggableState$anchoredDrag$4(AnchoredDraggableState<Object> anchoredDraggableState, Object obj, Function4 function4, Continuation continuation) {
        super(1, continuation);
        this.this$0 = anchoredDraggableState;
        this.$targetValue = obj;
        this.$block = function4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new AnchoredDraggableState$anchoredDrag$4(this.this$0, this.$targetValue, this.$block, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((AnchoredDraggableState$anchoredDrag$4) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnchoredDraggableState<Object> anchoredDraggableState = this.this$0;
            Object obj2 = this.$targetValue;
            int i2 = AnchoredDraggableState.$r8$clinit;
            anchoredDraggableState.setDragTarget(obj2);
            final AnchoredDraggableState<Object> anchoredDraggableState2 = this.this$0;
            Function0 function0 = new Function0() { // from class: androidx.compose.material3.internal.AnchoredDraggableState$anchoredDrag$4.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new Pair(anchoredDraggableState2.getAnchors(), anchoredDraggableState2.targetValue$delegate.getValue());
                }
            };
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$block, this.this$0, null);
            this.label = 1;
            if (AnchoredDraggableKt.access$restartable(function0, anonymousClass2, this) == coroutineSingletons) {
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
