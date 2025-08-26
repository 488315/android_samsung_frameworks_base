package com.android.systemui.dreams.homecontrols.service;

import com.android.systemui.dreams.DreamLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.util.service.PersistentConnectionManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class RemoteHomeControlsDataSourceDelegator$proxyState$1$3 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ RemoteHomeControlsDataSourceDelegator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RemoteHomeControlsDataSourceDelegator$proxyState$1$3(RemoteHomeControlsDataSourceDelegator remoteHomeControlsDataSourceDelegator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = remoteHomeControlsDataSourceDelegator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RemoteHomeControlsDataSourceDelegator$proxyState$1$3 remoteHomeControlsDataSourceDelegator$proxyState$1$3 = new RemoteHomeControlsDataSourceDelegator$proxyState$1$3(this.this$0, continuation);
        remoteHomeControlsDataSourceDelegator$proxyState$1$3.Z$0 = ((Boolean) obj).booleanValue();
        return remoteHomeControlsDataSourceDelegator$proxyState$1$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((RemoteHomeControlsDataSourceDelegator$proxyState$1$3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        DreamLogger dreamLogger = this.this$0.logger;
        RemoteHomeControlsDataSourceDelegator$callback$1$$ExternalSyntheticLambda0 remoteHomeControlsDataSourceDelegator$callback$1$$ExternalSyntheticLambda0 = new RemoteHomeControlsDataSourceDelegator$callback$1$$ExternalSyntheticLambda0(1);
        LogMessage logMessageObtain = dreamLogger.getBuffer().obtain(dreamLogger.getTag(), LogLevel.DEBUG, remoteHomeControlsDataSourceDelegator$callback$1$$ExternalSyntheticLambda0, null);
        logMessageObtain.setBool1(z);
        dreamLogger.getBuffer().commit(logMessageObtain);
        if (z) {
            ((PersistentConnectionManager) this.this$0.connectionManager$delegate.getValue()).start();
        } else {
            ((PersistentConnectionManager) this.this$0.connectionManager$delegate.getValue()).stop();
        }
        return Unit.INSTANCE;
    }
}
