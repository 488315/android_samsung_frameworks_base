package androidx.compose.animation.core;

import androidx.compose.runtime.snapshots.SnapshotStateObserver;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TransitionKt$rememberTransition$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ TransitionState<Object> $transitionState;
    Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TransitionKt$rememberTransition$1$1(TransitionState<Object> transitionState, Continuation continuation) {
        super(2, continuation);
        this.$transitionState = transitionState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TransitionKt$rememberTransition$1$1(this.$transitionState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TransitionKt$rememberTransition$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        TransitionState<Object> transitionState;
        Mutex mutex;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SeekableTransitionState seekableTransitionState = (SeekableTransitionState) this.$transitionState;
            seekableTransitionState.getClass();
            ((SnapshotStateObserver) TransitionKt.SeekableStateObserver$delegate.getValue()).observeReads(seekableTransitionState, TransitionKt.SeekableTransitionStateTotalDurationChanged, seekableTransitionState.recalculateTotalDurationNanos);
            TransitionState<Object> transitionState2 = this.$transitionState;
            MutexImpl mutexImpl = ((SeekableTransitionState) transitionState2).compositionContinuationMutex;
            this.L$0 = mutexImpl;
            this.L$1 = transitionState2;
            this.label = 1;
            if (mutexImpl.lock(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            transitionState = transitionState2;
            mutex = mutexImpl;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            transitionState = (TransitionState) this.L$1;
            mutex = (Mutex) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        try {
            ((SeekableTransitionState) transitionState).composedTargetState = transitionState.getTargetState();
            CancellableContinuationImpl cancellableContinuationImpl = ((SeekableTransitionState) transitionState).compositionContinuation;
            if (cancellableContinuationImpl != null) {
                int i2 = Result.$r8$clinit;
                cancellableContinuationImpl.resumeWith(transitionState.getTargetState());
            }
            ((SeekableTransitionState) transitionState).compositionContinuation = null;
            Unit unit = Unit.INSTANCE;
            mutex.unlock(null);
            return Unit.INSTANCE;
        } catch (Throwable th) {
            mutex.unlock(null);
            throw th;
        }
    }
}
