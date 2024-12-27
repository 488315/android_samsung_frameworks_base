package com.android.systemui.media.mediaoutput.controller.device;

import android.media.MediaRouter2Manager;
import java.util.List;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class RouteDeviceController$Companion$routeDeviceChanges$1$updateRoutes$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ProducerScope $$this$callbackFlow;
    final /* synthetic */ MediaRouter2Manager $this_routeDeviceChanges;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RouteDeviceController$Companion$routeDeviceChanges$1$updateRoutes$1(ProducerScope producerScope, MediaRouter2Manager mediaRouter2Manager, Continuation continuation) {
        super(2, continuation);
        this.$$this$callbackFlow = producerScope;
        this.$this_routeDeviceChanges = mediaRouter2Manager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RouteDeviceController$Companion$routeDeviceChanges$1$updateRoutes$1(this.$$this$callbackFlow, this.$this_routeDeviceChanges, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RouteDeviceController$Companion$routeDeviceChanges$1$updateRoutes$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SendChannel sendChannel = this.$$this$callbackFlow;
            List remoteSessions = this.$this_routeDeviceChanges.getRemoteSessions();
            this.label = 1;
            if (((ChannelCoroutine) sendChannel)._channel.send(remoteSessions, this) == coroutineSingletons) {
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
