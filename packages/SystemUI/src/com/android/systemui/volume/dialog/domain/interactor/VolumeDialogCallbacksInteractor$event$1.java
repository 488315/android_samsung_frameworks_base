package com.android.systemui.volume.dialog.domain.interactor;

import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogCallbacksInteractor;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogCallbacksInteractor.VolumeDialogEventModelProducer;
import com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class VolumeDialogCallbacksInteractor$event$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ VolumeDialogCallbacksInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogCallbacksInteractor$event$1(VolumeDialogCallbacksInteractor volumeDialogCallbacksInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = volumeDialogCallbacksInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogCallbacksInteractor$event$1 volumeDialogCallbacksInteractor$event$1 = new VolumeDialogCallbacksInteractor$event$1(this.this$0, continuation);
        volumeDialogCallbacksInteractor$event$1.L$0 = obj;
        return volumeDialogCallbacksInteractor$event$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogCallbacksInteractor$event$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0062, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r4, r6) == r0) goto L16;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        final VolumeDialogCallbacksInteractor.VolumeDialogEventModelProducer volumeDialogEventModelProducer;
        ProducerScope producerScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope2 = (ProducerScope) this.L$0;
            volumeDialogEventModelProducer = this.this$0.new VolumeDialogEventModelProducer(producerScope2);
            VolumeDialogCallbacksInteractor volumeDialogCallbacksInteractor = this.this$0;
            volumeDialogCallbacksInteractor.volumeDialogController.addCallback(volumeDialogEventModelProducer, volumeDialogCallbacksInteractor.bgHandler);
            VolumeDialogEventModel.SubscribedToEvents subscribedToEvents = VolumeDialogEventModel.SubscribedToEvents.INSTANCE;
            this.L$0 = producerScope2;
            this.L$1 = volumeDialogEventModelProducer;
            this.label = 1;
            if (((ChannelCoroutine) producerScope2)._channel.send(subscribedToEvents, this) != coroutineSingletons) {
                producerScope = producerScope2;
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        volumeDialogEventModelProducer = (VolumeDialogCallbacksInteractor.VolumeDialogEventModelProducer) this.L$1;
        producerScope = (ProducerScope) this.L$0;
        ResultKt.throwOnFailure(obj);
        final VolumeDialogCallbacksInteractor volumeDialogCallbacksInteractor2 = this.this$0;
        Function0 function0 = new Function0() { // from class: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogCallbacksInteractor$event$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                volumeDialogCallbacksInteractor2.volumeDialogController.removeCallback(volumeDialogEventModelProducer);
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
    }
}
