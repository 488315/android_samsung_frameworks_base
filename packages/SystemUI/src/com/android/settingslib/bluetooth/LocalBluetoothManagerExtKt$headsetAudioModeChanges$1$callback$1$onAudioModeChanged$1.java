package com.android.settingslib.bluetooth;

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
/* loaded from: classes.dex */
final class LocalBluetoothManagerExtKt$headsetAudioModeChanges$1$callback$1$onAudioModeChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ProducerScope $$this$callbackFlow;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalBluetoothManagerExtKt$headsetAudioModeChanges$1$callback$1$onAudioModeChanged$1(ProducerScope producerScope, Continuation continuation) {
        super(2, continuation);
        this.$$this$callbackFlow = producerScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LocalBluetoothManagerExtKt$headsetAudioModeChanges$1$callback$1$onAudioModeChanged$1(this.$$this$callbackFlow, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LocalBluetoothManagerExtKt$headsetAudioModeChanges$1$callback$1$onAudioModeChanged$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SendChannel sendChannel = this.$$this$callbackFlow;
            Unit unit = Unit.INSTANCE;
            this.label = 1;
            if (((ChannelCoroutine) sendChannel)._channel.send(unit, this) == coroutineSingletons) {
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
