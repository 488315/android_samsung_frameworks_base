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

/* loaded from: classes2.dex */
public final class QQSTileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Flow $realTiles$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ QQSTileSpecSettingsRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QQSTileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1(Continuation continuation, QQSTileSpecSettingsRepository qQSTileSpecSettingsRepository, Flow flow) {
        super(3, continuation);
        this.this$0 = qQSTileSpecSettingsRepository;
        this.$realTiles$inlined = flow;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QQSTileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1 qQSTileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1 = new QQSTileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$realTiles$inlined);
        qQSTileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        qQSTileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1.L$1 = obj2;
        return qQSTileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                this.this$0.logger.logUsingRetailTiles();
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2((List) this.this$0.retailModeTiles$delegate.getValue());
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = this.$realTiles$inlined;
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
