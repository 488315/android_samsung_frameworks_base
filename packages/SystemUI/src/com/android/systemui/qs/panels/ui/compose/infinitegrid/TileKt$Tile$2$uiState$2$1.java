package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import android.content.res.Resources;
import androidx.compose.runtime.ProduceStateScope;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.panels.ui.viewmodel.TileUiStateKt;
import com.android.systemui.qs.panels.ui.viewmodel.TileViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TileKt$Tile$2$uiState$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Resources $resources;
    final /* synthetic */ TileViewModel $tile;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TileKt$Tile$2$uiState$2$1(TileViewModel tileViewModel, Resources resources, Continuation continuation) {
        super(2, continuation);
        this.$tile = tileViewModel;
        this.$resources = resources;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TileKt$Tile$2$uiState$2$1 tileKt$Tile$2$uiState$2$1 = new TileKt$Tile$2$uiState$2$1(this.$tile, this.$resources, continuation);
        tileKt$Tile$2$uiState$2$1.L$0 = obj;
        return tileKt$Tile$2$uiState$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileKt$Tile$2$uiState$2$1) create((ProduceStateScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProduceStateScope produceStateScope = (ProduceStateScope) this.L$0;
            Flow flow = this.$tile.state;
            final Resources resources = this.$resources;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.panels.ui.compose.infinitegrid.TileKt$Tile$2$uiState$2$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    QSTile.State state = (QSTile.State) obj2;
                    state.getClass();
                    produceStateScope.setValue(TileUiStateKt.toUiState(state, resources));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
