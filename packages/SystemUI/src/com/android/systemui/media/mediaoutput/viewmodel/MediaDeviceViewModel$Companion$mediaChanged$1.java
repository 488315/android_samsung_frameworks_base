package com.android.systemui.media.mediaoutput.viewmodel;

import android.util.Log;
import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
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

final class MediaDeviceViewModel$Companion$mediaChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SmartThingsMediaSdkManager $this_mediaChanged;
    private /* synthetic */ Object L$0;
    int label;

    public MediaDeviceViewModel$Companion$mediaChanged$1(SmartThingsMediaSdkManager smartThingsMediaSdkManager, Continuation continuation) {
        super(2, continuation);
        this.$this_mediaChanged = smartThingsMediaSdkManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaDeviceViewModel$Companion$mediaChanged$1 mediaDeviceViewModel$Companion$mediaChanged$1 = new MediaDeviceViewModel$Companion$mediaChanged$1(this.$this_mediaChanged, continuation);
        mediaDeviceViewModel$Companion$mediaChanged$1.L$0 = obj;
        return mediaDeviceViewModel$Companion$mediaChanged$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaDeviceViewModel$Companion$mediaChanged$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ProducerScope producerScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope2 = (ProducerScope) this.L$0;
            Unit unit = Unit.INSTANCE;
            this.L$0 = producerScope2;
            this.label = 1;
            Object send = ((ChannelCoroutine) producerScope2)._channel.send(unit, this);
            producerScope = producerScope2;
            if (send == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ProducerScope producerScope3 = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            producerScope = producerScope3;
        }
        final MediaDeviceViewModel$Companion$mediaChanged$1$mediaDeviceControlCallback$1 mediaDeviceViewModel$Companion$mediaChanged$1$mediaDeviceControlCallback$1 = new MediaDeviceViewModel$Companion$mediaChanged$1$mediaDeviceControlCallback$1(producerScope);
        this.$this_mediaChanged.mediaSdkOperationManager.deviceControlOperationImpl.getClass();
        final MediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1 mediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1 = new MediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1(producerScope);
        this.$this_mediaChanged.mediaSdkOperationManager.mediaContentOperationImpl.addContentChangeCallback(mediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1);
        final MediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1 mediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1 = new MediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1(producerScope);
        this.$this_mediaChanged.mediaSdkOperationManager.deviceStatusOperationImpl.addDeviceStatusChangeCallback(mediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1);
        final SmartThingsMediaSdkManager smartThingsMediaSdkManager = this.$this_mediaChanged;
        Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$Companion$mediaChanged$1.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Log.d("MediaDeviceViewModel", "removeCallback");
                SmartThingsMediaSdkManager.this.mediaSdkOperationManager.deviceControlOperationImpl.getClass();
                SmartThingsMediaSdkManager smartThingsMediaSdkManager2 = SmartThingsMediaSdkManager.this;
                smartThingsMediaSdkManager2.mediaSdkOperationManager.mediaContentOperationImpl.removeContentChangeCallback(mediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1);
                SmartThingsMediaSdkManager smartThingsMediaSdkManager3 = SmartThingsMediaSdkManager.this;
                smartThingsMediaSdkManager3.mediaSdkOperationManager.deviceStatusOperationImpl.removeDeviceStatusChangeCallback(mediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1);
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.label = 2;
        if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
