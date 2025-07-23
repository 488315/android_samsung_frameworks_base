package androidx.compose.material3;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.MutatorMutex;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SliderState$drag$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $block;
    final /* synthetic */ MutatePriority $dragPriority;
    int label;
    final /* synthetic */ SliderState this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SliderState$drag$2(SliderState sliderState, MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sliderState;
        this.$dragPriority = mutatePriority;
        this.$block = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SliderState$drag$2(this.this$0, this.$dragPriority, this.$block, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SliderState$drag$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ((SnapshotMutableStateImpl) this.this$0.isDragging$delegate).setValue(Boolean.TRUE);
            SliderState sliderState = this.this$0;
            MutatorMutex mutatorMutex = sliderState.scrollMutex;
            MutatePriority mutatePriority = this.$dragPriority;
            Function2 function2 = this.$block;
            this.label = 1;
            if (mutatorMutex.mutateWith(sliderState.dragScope, mutatePriority, function2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        ((SnapshotMutableStateImpl) this.this$0.isDragging$delegate).setValue(Boolean.FALSE);
        return Unit.INSTANCE;
    }
}
