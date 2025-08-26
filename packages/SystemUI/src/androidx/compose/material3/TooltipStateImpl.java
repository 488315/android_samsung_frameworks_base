package androidx.compose.material3;

import androidx.compose.animation.core.MutableTransitionState;
import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.MutatorMutex;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class TooltipStateImpl implements TooltipState {
    public final boolean isPersistent;
    public CancellableContinuationImpl job;
    public final MutatorMutex mutatorMutex;
    public final MutableTransitionState transition;

    /* renamed from: androidx.compose.material3.TooltipStateImpl$show$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function1 {
        final /* synthetic */ Function1 $cancellableShow;
        final /* synthetic */ MutatePriority $mutatePriority;
        int label;

        /* renamed from: androidx.compose.material3.TooltipStateImpl$show$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function1 $cancellableShow;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Function1 function1, Continuation continuation) {
                super(2, continuation);
                this.$cancellableShow = function1;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$cancellableShow, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Function1 function1 = this.$cancellableShow;
                    this.label = 1;
                    if (function1.mo781invoke(this) == coroutineSingletons) {
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
        public AnonymousClass2(Function1 function1, MutatePriority mutatePriority, Continuation continuation) {
            super(1, continuation);
            this.$cancellableShow = function1;
            this.$mutatePriority = mutatePriority;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Continuation continuation) {
            return TooltipStateImpl.this.new AnonymousClass2(this.$cancellableShow, this.$mutatePriority, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((AnonymousClass2) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x003e, code lost:
        
            if (kotlinx.coroutines.TimeoutKt.withTimeout(1500, r5, r4) == r0) goto L20;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    if (TooltipStateImpl.this.isPersistent) {
                        Function1 function1 = this.$cancellableShow;
                        this.label = 1;
                        if (function1.mo781invoke(this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$cancellableShow, null);
                        this.label = 2;
                    }
                } else {
                    if (i != 1 && i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                if (this.$mutatePriority != MutatePriority.PreventUserInput) {
                    TooltipStateImpl.this.dismiss();
                }
                return Unit.INSTANCE;
            } finally {
                if (this.$mutatePriority != MutatePriority.PreventUserInput) {
                    TooltipStateImpl.this.dismiss();
                }
            }
        }
    }

    public TooltipStateImpl(boolean z, boolean z2, MutatorMutex mutatorMutex) {
        this.isPersistent = z2;
        this.mutatorMutex = mutatorMutex;
        this.transition = new MutableTransitionState(Boolean.valueOf(z));
    }

    @Override // androidx.compose.material3.TooltipState
    public final void dismiss() {
        this.transition.setTargetState(Boolean.FALSE);
    }

    @Override // androidx.compose.material3.TooltipState
    public final boolean isVisible() {
        MutableTransitionState mutableTransitionState = this.transition;
        return ((Boolean) ((SnapshotMutableStateImpl) mutableTransitionState.currentState$delegate).getValue()).booleanValue() || ((Boolean) ((SnapshotMutableStateImpl) mutableTransitionState.targetState$delegate).getValue()).booleanValue();
    }

    @Override // androidx.compose.material3.TooltipState
    public final void onDispose() {
        CancellableContinuationImpl cancellableContinuationImpl = this.job;
        if (cancellableContinuationImpl != null) {
            cancellableContinuationImpl.cancel(null);
        }
    }

    @Override // androidx.compose.material3.TooltipState
    public final Object show(MutatePriority mutatePriority, SuspendLambda suspendLambda) {
        Object objMutate = this.mutatorMutex.mutate(mutatePriority, new AnonymousClass2(new TooltipStateImpl$show$cancellableShow$1(this, null), mutatePriority, null), suspendLambda);
        return objMutate == CoroutineSingletons.COROUTINE_SUSPENDED ? objMutate : Unit.INSTANCE;
    }
}
