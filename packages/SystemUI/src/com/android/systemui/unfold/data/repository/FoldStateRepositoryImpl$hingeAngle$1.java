package com.android.systemui.unfold.data.repository;

import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.FoldStateProvider;
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

final class FoldStateRepositoryImpl$hingeAngle$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FoldStateRepositoryImpl this$0;

    public FoldStateRepositoryImpl$hingeAngle$1(FoldStateRepositoryImpl foldStateRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = foldStateRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FoldStateRepositoryImpl$hingeAngle$1 foldStateRepositoryImpl$hingeAngle$1 = new FoldStateRepositoryImpl$hingeAngle$1(this.this$0, continuation);
        foldStateRepositoryImpl$hingeAngle$1.L$0 = obj;
        return foldStateRepositoryImpl$hingeAngle$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FoldStateRepositoryImpl$hingeAngle$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new FoldStateProvider.FoldUpdatesListener() { // from class: com.android.systemui.unfold.data.repository.FoldStateRepositoryImpl$hingeAngle$1$callback$1
                @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
                public final void onHingeAngleUpdate(float f) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Float.valueOf(f));
                }
            };
            ((DeviceFoldStateProvider) this.this$0.foldStateProvider).addCallback(r1);
            final FoldStateRepositoryImpl foldStateRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.unfold.data.repository.FoldStateRepositoryImpl$hingeAngle$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((DeviceFoldStateProvider) FoldStateRepositoryImpl.this.foldStateProvider).removeCallback(r1);
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
