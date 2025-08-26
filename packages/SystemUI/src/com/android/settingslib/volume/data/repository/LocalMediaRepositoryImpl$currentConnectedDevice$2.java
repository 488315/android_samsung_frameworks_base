package com.android.settingslib.volume.data.repository;

import com.android.settingslib.media.MediaDevice;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes.dex */
final class LocalMediaRepositoryImpl$currentConnectedDevice$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ LocalMediaRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalMediaRepositoryImpl$currentConnectedDevice$2(LocalMediaRepositoryImpl localMediaRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = localMediaRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LocalMediaRepositoryImpl$currentConnectedDevice$2 localMediaRepositoryImpl$currentConnectedDevice$2 = new LocalMediaRepositoryImpl$currentConnectedDevice$2(this.this$0, continuation);
        localMediaRepositoryImpl$currentConnectedDevice$2.L$0 = obj;
        return localMediaRepositoryImpl$currentConnectedDevice$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LocalMediaRepositoryImpl$currentConnectedDevice$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            MediaDevice currentConnectedDevice = this.this$0.localMediaManager.getCurrentConnectedDevice();
            this.label = 1;
            if (flowCollector.emit(currentConnectedDevice, this) == coroutineSingletons) {
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
