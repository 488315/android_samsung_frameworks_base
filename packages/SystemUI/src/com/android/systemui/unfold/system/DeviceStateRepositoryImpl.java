package com.android.systemui.unfold.system;

import com.android.systemui.unfold.updates.FoldProvider;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final class DeviceStateRepositoryImpl implements DeviceStateRepository {
    public final Executor executor;
    public final FoldProvider foldProvider;

    /* renamed from: com.android.systemui.unfold.system.DeviceStateRepositoryImpl$isFolded$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = DeviceStateRepositoryImpl.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.unfold.system.DeviceStateRepositoryImpl$isFolded$1$callback$1, com.android.systemui.unfold.updates.FoldProvider$FoldCallback] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final ?? r1 = new FoldProvider.FoldCallback() { // from class: com.android.systemui.unfold.system.DeviceStateRepositoryImpl$isFolded$1$callback$1
                    @Override // com.android.systemui.unfold.updates.FoldProvider.FoldCallback
                    public final void onFoldUpdated(boolean z) {
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(z));
                    }
                };
                DeviceStateRepositoryImpl deviceStateRepositoryImpl = DeviceStateRepositoryImpl.this;
                deviceStateRepositoryImpl.foldProvider.registerCallback(r1, deviceStateRepositoryImpl.executor);
                final DeviceStateRepositoryImpl deviceStateRepositoryImpl2 = DeviceStateRepositoryImpl.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.unfold.system.DeviceStateRepositoryImpl$isFolded$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        deviceStateRepositoryImpl2.foldProvider.unregisterCallback(r1);
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

    public DeviceStateRepositoryImpl(FoldProvider foldProvider, Executor executor) {
        this.foldProvider = foldProvider;
        this.executor = executor;
    }

    public final Flow isFolded() {
        return FlowKt.buffer$default(FlowKt.callbackFlow(new AnonymousClass1(null)), -1, 2);
    }
}
