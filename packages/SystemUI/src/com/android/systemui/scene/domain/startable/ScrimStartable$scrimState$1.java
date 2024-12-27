package com.android.systemui.scene.domain.startable;

import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.DozeServiceHost$$ExternalSyntheticLambda0;
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

final class ScrimStartable$scrimState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ScrimStartable this$0;

    public ScrimStartable$scrimState$1(ScrimStartable scrimStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = scrimStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScrimStartable$scrimState$1 scrimStartable$scrimState$1 = new ScrimStartable$scrimState$1(this.this$0, continuation);
        scrimStartable$scrimState$1.L$0 = obj;
        return scrimStartable$scrimState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScrimStartable$scrimState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            DozeServiceHost.HasPendingScreenOffCallbackChangeListener hasPendingScreenOffCallbackChangeListener = new DozeServiceHost.HasPendingScreenOffCallbackChangeListener() { // from class: com.android.systemui.scene.domain.startable.ScrimStartable$scrimState$1$listener$1
                @Override // com.android.systemui.statusbar.phone.DozeServiceHost.HasPendingScreenOffCallbackChangeListener
                public final void onHasPendingScreenOffCallbackChanged(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            DozeServiceHost dozeServiceHost = this.this$0.dozeServiceHost;
            dozeServiceHost.mHasPendingScreenOffCallbackChangeListener = hasPendingScreenOffCallbackChangeListener;
            hasPendingScreenOffCallbackChangeListener.onHasPendingScreenOffCallbackChanged(dozeServiceHost.mPendingScreenOffCallback != null);
            final ScrimStartable scrimStartable = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.scene.domain.startable.ScrimStartable$scrimState$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DozeServiceHost dozeServiceHost2 = ScrimStartable.this.dozeServiceHost;
                    DozeServiceHost$$ExternalSyntheticLambda0 dozeServiceHost$$ExternalSyntheticLambda0 = dozeServiceHost2.mDefaultHasPendingScreenOffCallbackChangeListener;
                    dozeServiceHost2.mHasPendingScreenOffCallbackChangeListener = dozeServiceHost$$ExternalSyntheticLambda0;
                    dozeServiceHost$$ExternalSyntheticLambda0.onHasPendingScreenOffCallbackChanged(dozeServiceHost2.mPendingScreenOffCallback != null);
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
