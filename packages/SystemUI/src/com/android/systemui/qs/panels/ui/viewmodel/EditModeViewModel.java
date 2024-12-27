package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.qs.panels.domain.interactor.EditTilesListInteractor;
import com.android.systemui.qs.panels.domain.interactor.GridLayoutTypeInteractor;
import com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor;
import com.android.systemui.qs.panels.shared.model.GridLayoutType;
import com.android.systemui.qs.panels.ui.compose.GridLayout;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.MinimumTilesInteractor;
import java.util.Map;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EditModeViewModel {
    public final StateFlowImpl _isEditing;
    public final CurrentTilesInteractor currentTilesInteractor;
    public final GridLayout defaultGridLayout;
    public final EditTilesListInteractor editTilesListInteractor;
    public final ReadonlyStateFlow gridLayout;
    public final ReadonlyStateFlow isEditing;
    public final MinimumTilesInteractor minTilesInteractor;
    public final ChannelFlowTransformLatest tiles;
    public final TilesAvailabilityInteractor tilesAvailabilityInteractor;

    public EditModeViewModel(EditTilesListInteractor editTilesListInteractor, CurrentTilesInteractor currentTilesInteractor, TilesAvailabilityInteractor tilesAvailabilityInteractor, MinimumTilesInteractor minimumTilesInteractor, GridLayout gridLayout, CoroutineScope coroutineScope, GridLayoutTypeInteractor gridLayoutTypeInteractor, final Map<GridLayoutType, GridLayout> map) {
        this.editTilesListInteractor = editTilesListInteractor;
        this.currentTilesInteractor = currentTilesInteractor;
        this.tilesAvailabilityInteractor = tilesAvailabilityInteractor;
        this.minTilesInteractor = minimumTilesInteractor;
        this.defaultGridLayout = gridLayout;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(Boolean.FALSE));
        final ReadonlyStateFlow readonlyStateFlow = gridLayoutTypeInteractor.layout;
        FlowKt.stateIn(new Flow() { // from class: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Map $gridLayoutMap$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ EditModeViewModel this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, Map map, EditModeViewModel editModeViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$gridLayoutMap$inlined = map;
                    this.this$0 = editModeViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.qs.panels.shared.model.GridLayoutType r5 = (com.android.systemui.qs.panels.shared.model.GridLayoutType) r5
                        java.util.Map r6 = r4.$gridLayoutMap$inlined
                        java.lang.Object r5 = r6.get(r5)
                        com.android.systemui.qs.panels.ui.compose.GridLayout r5 = (com.android.systemui.qs.panels.ui.compose.GridLayout) r5
                        if (r5 != 0) goto L42
                        com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel r5 = r4.this$0
                        com.android.systemui.qs.panels.ui.compose.GridLayout r5 = r5.defaultGridLayout
                    L42:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, map, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), gridLayout);
        FlowKt.transformLatest(asStateFlow, new EditModeViewModel$special$$inlined$flatMapLatest$1(null, this));
    }
}
