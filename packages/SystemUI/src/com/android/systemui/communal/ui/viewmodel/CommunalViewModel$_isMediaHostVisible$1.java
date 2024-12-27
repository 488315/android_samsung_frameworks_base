package com.android.systemui.communal.ui.viewmodel;

import com.android.systemui.media.controls.ui.view.MediaHost;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class CommunalViewModel$_isMediaHostVisible$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaHost $mediaHost;
    private /* synthetic */ Object L$0;
    int label;

    public CommunalViewModel$_isMediaHostVisible$1(MediaHost mediaHost, Continuation continuation) {
        super(2, continuation);
        this.$mediaHost = mediaHost;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalViewModel$_isMediaHostVisible$1 communalViewModel$_isMediaHostVisible$1 = new CommunalViewModel$_isMediaHostVisible$1(this.$mediaHost, continuation);
        communalViewModel$_isMediaHostVisible$1.L$0 = obj;
        return communalViewModel$_isMediaHostVisible$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalViewModel$_isMediaHostVisible$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Function1 function1 = new Function1() { // from class: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$_isMediaHostVisible$1$callback$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Boolean bool = (Boolean) obj2;
                    bool.booleanValue();
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(bool);
                    return Unit.INSTANCE;
                }
            };
            this.$mediaHost.visibleChangedListeners.add(function1);
            final MediaHost mediaHost = this.$mediaHost;
            Function0 function0 = new Function0() { // from class: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$_isMediaHostVisible$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    MediaHost mediaHost2 = MediaHost.this;
                    mediaHost2.visibleChangedListeners.remove(function1);
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
