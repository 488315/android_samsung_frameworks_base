package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.AudioManager;
import java.util.List;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ProducerScope $$this$callbackFlow;
    final /* synthetic */ Pair<Context, AudioManager> $this_deviceStateChanges;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(ProducerScope producerScope, Pair<? extends Context, ? extends AudioManager> pair, Continuation continuation) {
        super(2, continuation);
        this.$$this$callbackFlow = producerScope;
        this.$this_deviceStateChanges = pair;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(this.$$this$callbackFlow, this.$this_deviceStateChanges, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SendChannel sendChannel = this.$$this$callbackFlow;
            List list = ArraysKt___ArraysKt.toList(((AudioManager) this.$this_deviceStateChanges.getSecond()).getDevices(2));
            this.label = 1;
            if (((ChannelCoroutine) sendChannel)._channel.send(list, this) == coroutineSingletons) {
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
