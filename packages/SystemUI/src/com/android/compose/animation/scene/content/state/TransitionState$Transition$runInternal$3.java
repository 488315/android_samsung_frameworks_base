package com.android.compose.animation.scene.content.state;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import com.android.compose.animation.scene.content.state.TransitionState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class TransitionState$Transition$runInternal$3 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TransitionState.Transition this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TransitionState$Transition$runInternal$3(TransitionState.Transition transition, Continuation continuation) {
        super(2, continuation);
        this.this$0 = transition;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TransitionState$Transition$runInternal$3 transitionState$Transition$runInternal$3 = new TransitionState$Transition$runInternal$3(this.this$0, continuation);
        transitionState$Transition$runInternal$3.L$0 = obj;
        return transitionState$Transition$runInternal$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TransitionState$Transition$runInternal$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.lang.Object, kotlin.Unit] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                TransitionState.Transition transition = this.this$0;
                transition._coroutineScope = coroutineScope;
                this.label = 1;
                if (transition.run(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            ((SnapshotMutableStateImpl) this.this$0.isProgressStable$delegate).setValue(Boolean.TRUE);
            this = Unit.INSTANCE;
            return this;
        } catch (Throwable th) {
            ((SnapshotMutableStateImpl) this.this$0.isProgressStable$delegate).setValue(Boolean.TRUE);
            throw th;
        }
    }
}
