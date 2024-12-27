package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import com.android.systemui.volume.domain.interactor.AudioOutputInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MediaOutputComponentInteractor$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ AudioOutputInteractor $audioOutputInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MediaOutputComponentInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaOutputComponentInteractor$special$$inlined$flatMapLatest$2(Continuation continuation, AudioOutputInteractor audioOutputInteractor, MediaOutputComponentInteractor mediaOutputComponentInteractor) {
        super(3, continuation);
        this.$audioOutputInteractor$inlined = audioOutputInteractor;
        this.this$0 = mediaOutputComponentInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MediaOutputComponentInteractor$special$$inlined$flatMapLatest$2 mediaOutputComponentInteractor$special$$inlined$flatMapLatest$2 = new MediaOutputComponentInteractor$special$$inlined$flatMapLatest$2((Continuation) obj3, this.$audioOutputInteractor$inlined, this.this$0);
        mediaOutputComponentInteractor$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        mediaOutputComponentInteractor$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return mediaOutputComponentInteractor$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(this.$audioOutputInteractor$inlined.isInAudioSharing, new MediaOutputComponentInteractor$mediaOutputModel$lambda$6$$inlined$flatMapLatest$1(null, ((Boolean) this.L$1).booleanValue(), this.this$0));
            this.label = 1;
            if (FlowKt.emitAll(this, transformLatest, flowCollector) == coroutineSingletons) {
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
