package com.android.systemui.qs.tiles.impl.saver.domain.interactor;

import com.android.systemui.qs.tiles.impl.saver.domain.model.DataSaverTileModel;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DataSaverControllerImpl;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class DataSaverTileDataInteractor$tileData$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DataSaverTileDataInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataSaverTileDataInteractor$tileData$1(DataSaverTileDataInteractor dataSaverTileDataInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataSaverTileDataInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DataSaverTileDataInteractor$tileData$1 dataSaverTileDataInteractor$tileData$1 = new DataSaverTileDataInteractor$tileData$1(this.this$0, continuation);
        dataSaverTileDataInteractor$tileData$1.L$0 = obj;
        return dataSaverTileDataInteractor$tileData$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataSaverTileDataInteractor$tileData$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(DataSaverTileModel.m2095boximpl(((DataSaverControllerImpl) this.this$0.dataSaverController).isDataSaverEnabled$1()));
            final DataSaverController.Listener listener = new DataSaverController.Listener() { // from class: com.android.systemui.qs.tiles.impl.saver.domain.interactor.DataSaverTileDataInteractor$tileData$1$callback$1
                @Override // com.android.systemui.statusbar.policy.DataSaverController.Listener
                public final void onDataSaverChanged(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(DataSaverTileModel.m2095boximpl(z));
                }
            };
            ((DataSaverControllerImpl) this.this$0.dataSaverController).addCallback(listener);
            final DataSaverTileDataInteractor dataSaverTileDataInteractor = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.saver.domain.interactor.DataSaverTileDataInteractor$tileData$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((DataSaverControllerImpl) DataSaverTileDataInteractor.this.dataSaverController).removeCallback(listener);
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
