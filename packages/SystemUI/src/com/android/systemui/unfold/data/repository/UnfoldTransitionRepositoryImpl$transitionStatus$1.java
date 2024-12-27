package com.android.systemui.unfold.data.repository;

import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.data.repository.UnfoldTransitionStatus;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class UnfoldTransitionRepositoryImpl$transitionStatus$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ UnfoldTransitionProgressProvider $provider;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnfoldTransitionRepositoryImpl$transitionStatus$1(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, Continuation continuation) {
        super(2, continuation);
        this.$provider = unfoldTransitionProgressProvider;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UnfoldTransitionRepositoryImpl$transitionStatus$1 unfoldTransitionRepositoryImpl$transitionStatus$1 = new UnfoldTransitionRepositoryImpl$transitionStatus$1(this.$provider, continuation);
        unfoldTransitionRepositoryImpl$transitionStatus$1.L$0 = obj;
        return unfoldTransitionRepositoryImpl$transitionStatus$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UnfoldTransitionRepositoryImpl$transitionStatus$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.unfold.data.repository.UnfoldTransitionRepositoryImpl$transitionStatus$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new UnfoldTransitionProgressProvider.TransitionProgressListener() { // from class: com.android.systemui.unfold.data.repository.UnfoldTransitionRepositoryImpl$transitionStatus$1$callback$1
                @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
                public final void onTransitionFinished() {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(UnfoldTransitionStatus.TransitionFinished.INSTANCE);
                }

                @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
                public final void onTransitionProgress(float f) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(new UnfoldTransitionStatus.TransitionInProgress(f));
                }

                @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
                public final void onTransitionStarted() {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(UnfoldTransitionStatus.TransitionStarted.INSTANCE);
                }
            };
            this.$provider.addCallback(r1);
            final UnfoldTransitionProgressProvider unfoldTransitionProgressProvider = this.$provider;
            Function0 function0 = new Function0() { // from class: com.android.systemui.unfold.data.repository.UnfoldTransitionRepositoryImpl$transitionStatus$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    UnfoldTransitionProgressProvider.this.removeCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
