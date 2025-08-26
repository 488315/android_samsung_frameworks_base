package com.android.systemui.unfold.data.repository;

import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.FoldStateProvider;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class FoldStateRepositoryImpl$hingeAngle$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FoldStateRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
            FoldStateProvider.FoldUpdatesListener foldUpdatesListener = new FoldStateProvider.FoldUpdatesListener() { // from class: com.android.systemui.unfold.data.repository.FoldStateRepositoryImpl$hingeAngle$1$callback$1
                @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
                public final void onHingeAngleUpdate(float f) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Float.valueOf(f));
                }
            };
            ((DeviceFoldStateProvider) this.this$0.foldStateProvider).addCallback(foldUpdatesListener);
            FoldStateRepositoryImpl$foldUpdate$1$$ExternalSyntheticLambda0 foldStateRepositoryImpl$foldUpdate$1$$ExternalSyntheticLambda0 = new FoldStateRepositoryImpl$foldUpdate$1$$ExternalSyntheticLambda0(this.this$0, foldUpdatesListener, 1);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, foldStateRepositoryImpl$foldUpdate$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
