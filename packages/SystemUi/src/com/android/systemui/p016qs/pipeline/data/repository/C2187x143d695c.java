package com.android.systemui.p016qs.pipeline.data.repository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$1", m277f = "InstalledTilesComponentRepository.kt", m278l = {74}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$1 */
/* loaded from: classes2.dex */
final class C2187x143d695c extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ InstalledTilesComponentRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2187x143d695c(InstalledTilesComponentRepositoryImpl installedTilesComponentRepositoryImpl, int i, Continuation<? super C2187x143d695c> continuation) {
        super(2, continuation);
        this.this$0 = installedTilesComponentRepositoryImpl;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        C2187x143d695c c2187x143d695c = new C2187x143d695c(this.this$0, this.$userId, continuation);
        c2187x143d695c.L$0 = obj;
        return c2187x143d695c;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C2187x143d695c) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.content.BroadcastReceiver, com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$1$receiver$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    ((ChannelCoroutine) ProducerScope.this).mo2872trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            this.this$0.applicationContext.registerReceiverAsUser(r1, UserHandle.of(this.$userId), InstalledTilesComponentRepositoryImpl.INTENT_FILTER, null, null);
            final InstalledTilesComponentRepositoryImpl installedTilesComponentRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl$getInstalledTilesComponents$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    InstalledTilesComponentRepositoryImpl.this.applicationContext.unregisterReceiver(r1);
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
