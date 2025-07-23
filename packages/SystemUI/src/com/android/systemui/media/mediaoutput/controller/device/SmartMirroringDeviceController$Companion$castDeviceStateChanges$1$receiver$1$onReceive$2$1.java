package com.android.systemui.media.mediaoutput.controller.device;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SmartMirroringDeviceController$Companion$castDeviceStateChanges$1$receiver$1$onReceive$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ProducerScope $$this$callbackFlow;
    final /* synthetic */ int $it;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartMirroringDeviceController$Companion$castDeviceStateChanges$1$receiver$1$onReceive$2$1(ProducerScope producerScope, int i, Continuation continuation) {
        super(2, continuation);
        this.$$this$callbackFlow = producerScope;
        this.$it = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SmartMirroringDeviceController$Companion$castDeviceStateChanges$1$receiver$1$onReceive$2$1(this.$$this$callbackFlow, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SmartMirroringDeviceController$Companion$castDeviceStateChanges$1$receiver$1$onReceive$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SendChannel sendChannel = this.$$this$callbackFlow;
            Integer num = new Integer(this.$it);
            this.label = 1;
            if (((ChannelCoroutine) sendChannel)._channel.send(num, this) == coroutineSingletons) {
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
