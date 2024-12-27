package com.android.systemui.qs.pipeline.data.repository;

import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Flow $realTiles$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ TileSpecSettingsRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1(Continuation continuation, TileSpecSettingsRepository tileSpecSettingsRepository, Flow flow) {
        super(3, continuation);
        this.this$0 = tileSpecSettingsRepository;
        this.$realTiles$inlined = flow;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1 tileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1 = new TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$realTiles$inlined);
        tileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        tileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1.L$1 = obj2;
        return tileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                this.this$0.logger.logUsingRetailTiles();
                flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2((List) this.this$0.retailModeTiles$delegate.getValue());
            } else {
                flow = this.$realTiles$inlined;
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
