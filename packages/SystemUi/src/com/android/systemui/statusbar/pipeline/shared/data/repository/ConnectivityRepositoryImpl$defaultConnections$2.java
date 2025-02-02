package com.android.systemui.statusbar.pipeline.shared.data.repository;

import com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$defaultConnections$2", m277f = "ConnectivityRepository.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class ConnectivityRepositoryImpl$defaultConnections$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConnectivityInputLogger $logger;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConnectivityRepositoryImpl$defaultConnections$2(ConnectivityInputLogger connectivityInputLogger, Continuation<? super ConnectivityRepositoryImpl$defaultConnections$2> continuation) {
        super(2, continuation);
        this.$logger = connectivityInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ConnectivityRepositoryImpl$defaultConnections$2 connectivityRepositoryImpl$defaultConnections$2 = new ConnectivityRepositoryImpl$defaultConnections$2(this.$logger, continuation);
        connectivityRepositoryImpl$defaultConnections$2.L$0 = obj;
        return connectivityRepositoryImpl$defaultConnections$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConnectivityRepositoryImpl$defaultConnections$2) create((DefaultConnectionModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$logger.logDefaultConnectionsChanged((DefaultConnectionModel) this.L$0);
        return Unit.INSTANCE;
    }
}
