package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.content.Intent;
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
/* loaded from: classes2.dex */
final class CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1$receiver$1$onReceive$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
    final /* synthetic */ Intent $intent;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1$receiver$1$onReceive$1(ProducerScope producerScope, Intent intent, Continuation continuation) {
        super(2, continuation);
        this.$$this$conflatedCallbackFlow = producerScope;
        this.$intent = intent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1$receiver$1$onReceive$1(this.$$this$conflatedCallbackFlow, this.$intent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1$receiver$1$onReceive$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SendChannel sendChannel = this.$$this$conflatedCallbackFlow;
            Intent intent = this.$intent;
            this.label = 1;
            if (((ChannelCoroutine) sendChannel)._channel.send(intent, this) == coroutineSingletons) {
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
