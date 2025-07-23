package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserHandle $user;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CustomTilePackageUpdatesRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1(CustomTilePackageUpdatesRepositoryImpl customTilePackageUpdatesRepositoryImpl, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customTilePackageUpdatesRepositoryImpl;
        this.$user = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1 customTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1 = new CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1(this.this$0, this.$user, continuation);
        customTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1.L$0 = obj;
        return customTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.content.BroadcastReceiver, com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1$receiver$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r4 = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    ProducerScope producerScope2 = ProducerScope.this;
                    CoroutineTracingKt.launchTraced$default(producerScope2, null, null, new CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1$receiver$1$onReceive$1(producerScope2, intent, null), 7);
                }
            };
            Context context = this.this$0.context;
            UserHandle userHandle = this.$user;
            CustomTilePackageUpdatesRepositoryImpl.Companion.getClass();
            context.registerReceiverAsUser(r4, userHandle, CustomTilePackageUpdatesRepositoryImpl.INTENT_FILTER, null, null);
            final CustomTilePackageUpdatesRepositoryImpl customTilePackageUpdatesRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl$createPackageChangesFlowForUser$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    CustomTilePackageUpdatesRepositoryImpl.this.context.unregisterReceiver(r4);
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
