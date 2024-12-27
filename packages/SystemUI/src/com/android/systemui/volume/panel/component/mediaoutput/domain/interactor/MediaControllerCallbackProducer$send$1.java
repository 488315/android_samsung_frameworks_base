package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaControllerChangeModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.SendChannel;

final class MediaControllerCallbackProducer$send$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaControllerChangeModel $change;
    int label;
    final /* synthetic */ MediaControllerCallbackProducer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaControllerCallbackProducer$send$1(MediaControllerCallbackProducer mediaControllerCallbackProducer, MediaControllerChangeModel mediaControllerChangeModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaControllerCallbackProducer;
        this.$change = mediaControllerChangeModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaControllerCallbackProducer$send$1(this.this$0, this.$change, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaControllerCallbackProducer$send$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SendChannel sendChannel = this.this$0.producingScope;
            MediaControllerChangeModel mediaControllerChangeModel = this.$change;
            this.label = 1;
            if (((ChannelCoroutine) sendChannel)._channel.send(mediaControllerChangeModel, this) == coroutineSingletons) {
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
