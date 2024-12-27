package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.plugins.qs.QSTile;
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

final class TileViewModel$state$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TileViewModel this$0;

    public TileViewModel$state$1(TileViewModel tileViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = tileViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TileViewModel$state$1 tileViewModel$state$1 = new TileViewModel$state$1(this.this$0, continuation);
        tileViewModel$state$1.L$0 = obj;
        return tileViewModel$state$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileViewModel$state$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final QSTile.Callback callback = new QSTile.Callback() { // from class: com.android.systemui.qs.panels.ui.viewmodel.TileViewModel$state$1$callback$1
                @Override // com.android.systemui.plugins.qs.QSTile.Callback
                public final void onStateChanged(QSTile.State state) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(state.copy());
                }
            };
            this.this$0.tile.addCallback(callback);
            final TileViewModel tileViewModel = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.panels.ui.viewmodel.TileViewModel$state$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    TileViewModel.this.tile.removeCallback(callback);
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
