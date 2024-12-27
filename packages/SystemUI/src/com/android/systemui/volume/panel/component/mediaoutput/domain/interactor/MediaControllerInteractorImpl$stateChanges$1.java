package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.media.session.MediaController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class MediaControllerInteractorImpl$stateChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaController $mediaController;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaControllerInteractorImpl this$0;

    public MediaControllerInteractorImpl$stateChanges$1(MediaController mediaController, MediaControllerInteractorImpl mediaControllerInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.$mediaController = mediaController;
        this.this$0 = mediaControllerInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaControllerInteractorImpl$stateChanges$1 mediaControllerInteractorImpl$stateChanges$1 = new MediaControllerInteractorImpl$stateChanges$1(this.$mediaController, this.this$0, continuation);
        mediaControllerInteractorImpl$stateChanges$1.L$0 = obj;
        return mediaControllerInteractorImpl$stateChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaControllerInteractorImpl$stateChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final MediaControllerCallbackProducer mediaControllerCallbackProducer = new MediaControllerCallbackProducer(producerScope);
            this.$mediaController.registerCallback(mediaControllerCallbackProducer, this.this$0.backgroundHandler);
            final MediaController mediaController = this.$mediaController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaControllerInteractorImpl$stateChanges$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    mediaController.unregisterCallback(mediaControllerCallbackProducer);
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
