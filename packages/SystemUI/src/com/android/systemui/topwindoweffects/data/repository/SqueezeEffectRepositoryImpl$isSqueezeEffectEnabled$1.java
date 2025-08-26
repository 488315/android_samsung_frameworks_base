package com.android.systemui.topwindoweffects.data.repository;

import android.database.ContentObserver;
import android.os.Handler;
import com.android.systemui.common.coroutine.ChannelExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class SqueezeEffectRepositoryImpl$isSqueezeEffectEnabled$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SqueezeEffectRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SqueezeEffectRepositoryImpl$isSqueezeEffectEnabled$1(SqueezeEffectRepositoryImpl squeezeEffectRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = squeezeEffectRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SqueezeEffectRepositoryImpl$isSqueezeEffectEnabled$1 squeezeEffectRepositoryImpl$isSqueezeEffectEnabled$1 = new SqueezeEffectRepositoryImpl$isSqueezeEffectEnabled$1(this.this$0, continuation);
        squeezeEffectRepositoryImpl$isSqueezeEffectEnabled$1.L$0 = obj;
        return squeezeEffectRepositoryImpl$isSqueezeEffectEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SqueezeEffectRepositoryImpl$isSqueezeEffectEnabled$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.database.ContentObserver, com.android.systemui.topwindoweffects.data.repository.SqueezeEffectRepositoryImpl$isSqueezeEffectEnabled$1$observer$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Handler handler = this.this$0.bgHandler;
            final SqueezeEffectRepositoryImpl squeezeEffectRepositoryImpl = this.this$0;
            final ?? r3 = new ContentObserver(handler) { // from class: com.android.systemui.topwindoweffects.data.repository.SqueezeEffectRepositoryImpl$isSqueezeEffectEnabled$1$observer$1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    ProducerScope producerScope2 = producerScope;
                    SqueezeEffectRepositoryImpl squeezeEffectRepositoryImpl2 = squeezeEffectRepositoryImpl;
                    int i2 = SqueezeEffectRepositoryImpl.$r8$clinit;
                    squeezeEffectRepositoryImpl2.getClass();
                    Boolean bool = Boolean.FALSE;
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(producerScope2, bool, "SqueezeEffectRepository", "updated isSqueezeEffectEnabled");
                }
            };
            ChannelExt channelExt = ChannelExt.INSTANCE;
            this.this$0.getClass();
            Boolean bool = Boolean.FALSE;
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, bool, "SqueezeEffectRepository", "init isSqueezeEffectEnabled");
            this.this$0.globalSettings.registerContentObserverAsync("power_button_long_press", (ContentObserver) r3);
            final SqueezeEffectRepositoryImpl squeezeEffectRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.topwindoweffects.data.repository.SqueezeEffectRepositoryImpl$isSqueezeEffectEnabled$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    squeezeEffectRepositoryImpl2.globalSettings.unregisterContentObserverAsync(r3);
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
