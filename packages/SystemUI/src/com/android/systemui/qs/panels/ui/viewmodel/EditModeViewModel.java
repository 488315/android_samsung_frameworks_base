package com.android.systemui.qs.panels.ui.viewmodel;

import android.content.Context;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.qs.QSEditEvent;
import com.android.systemui.qs.panels.domain.interactor.EditTilesListInteractor;
import com.android.systemui.qs.panels.domain.interactor.GridLayoutTypeInteractor;
import com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor;
import com.android.systemui.qs.panels.shared.model.GridLayoutType;
import com.android.systemui.qs.panels.ui.compose.GridLayout;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.MinimumTilesInteractor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.TileSpecKt;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class EditModeViewModel {
    public final StateFlowImpl _isEditing;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final ConfigurationInteractor configurationInteractor;
    public final Context context;
    public final CurrentTilesInteractor currentTilesInteractor;
    public final GridLayout defaultGridLayout;
    public final EditTilesListInteractor editTilesListInteractor;
    public final ReadonlyStateFlow gridLayout;
    public final ReadonlyStateFlow isEditing;
    public final MinimumTilesInteractor minTilesInteractor;
    public final ChannelFlowTransformLatest tiles;
    public final TilesAvailabilityInteractor tilesAvailabilityInteractor;
    public final UiEventLogger uiEventLogger;

    /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$setTiles$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ List<TileSpec> $currentTiles;
        final /* synthetic */ List<TileSpec> $tileSpecs;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass1(List<? extends TileSpec> list, List<? extends TileSpec> list2, Continuation continuation) {
            super(2, continuation);
            this.$currentTiles = list;
            this.$tileSpecs = list2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditModeViewModel.this.new AnonymousClass1(this.$currentTiles, this.$tileSpecs, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            final EditModeViewModel editModeViewModel = EditModeViewModel.this;
            final List<TileSpec> list = this.$currentTiles;
            final List<TileSpec> list2 = this.$tileSpecs;
            editModeViewModel.getClass();
            DiffUtil.calculateDiff(new DiffCallback(list, list2)).dispatchUpdatesTo(new ListUpdateCallback() { // from class: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$calculateDiffsAndEmitUiEvents$1
                @Override // androidx.recyclerview.widget.ListUpdateCallback
                public final void onInserted(int i, int i2) {
                    TileSpec tileSpec = (TileSpec) CollectionsKt___CollectionsKt.getOrNull(i, list2);
                    if (tileSpec != null) {
                        editModeViewModel.uiEventLogger.logWithPosition(QSEditEvent.QS_EDIT_ADD, 0, TileSpecKt.getMetricSpec(tileSpec), i);
                    }
                }

                @Override // androidx.recyclerview.widget.ListUpdateCallback
                public final void onMoved(int i, int i2) {
                    TileSpec tileSpec = (TileSpec) CollectionsKt___CollectionsKt.getOrNull(i, list);
                    if (tileSpec != null) {
                        editModeViewModel.uiEventLogger.logWithPosition(QSEditEvent.QS_EDIT_MOVE, 0, TileSpecKt.getMetricSpec(tileSpec), i2);
                    }
                }

                @Override // androidx.recyclerview.widget.ListUpdateCallback
                public final void onRemoved(int i, int i2) {
                    TileSpec tileSpec = (TileSpec) CollectionsKt___CollectionsKt.getOrNull(i, list);
                    if (tileSpec != null) {
                        editModeViewModel.uiEventLogger.log(QSEditEvent.QS_EDIT_REMOVE, 0, TileSpecKt.getMetricSpec(tileSpec));
                    }
                }

                @Override // androidx.recyclerview.widget.ListUpdateCallback
                public final void onChanged(int i, int i2, Object obj2) {
                }
            });
            return Unit.INSTANCE;
        }
    }

    public EditModeViewModel(EditTilesListInteractor editTilesListInteractor, CurrentTilesInteractor currentTilesInteractor, TilesAvailabilityInteractor tilesAvailabilityInteractor, MinimumTilesInteractor minimumTilesInteractor, UiEventLogger uiEventLogger, ConfigurationInteractor configurationInteractor, Context context, GridLayout gridLayout, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, GridLayoutTypeInteractor gridLayoutTypeInteractor, final Map<GridLayoutType, GridLayout> map) {
        this.editTilesListInteractor = editTilesListInteractor;
        this.currentTilesInteractor = currentTilesInteractor;
        this.tilesAvailabilityInteractor = tilesAvailabilityInteractor;
        this.minTilesInteractor = minimumTilesInteractor;
        this.uiEventLogger = uiEventLogger;
        this.configurationInteractor = configurationInteractor;
        this.context = context;
        this.defaultGridLayout = gridLayout;
        this.applicationScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isEditing = stateFlowImplMutableStateFlow;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.isEditing = readonlyStateFlowAsStateFlow;
        final ChannelFlowTransformLatest channelFlowTransformLatest = gridLayoutTypeInteractor.layout;
        this.gridLayout = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Map $gridLayoutMap$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ EditModeViewModel this$0;

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        GridLayout gridLayout = (GridLayout) this.$gridLayoutMap$inlined.get((GridLayoutType) obj);
                        if (gridLayout == null) {
                            gridLayout = this.this$0.defaultGridLayout;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(gridLayout, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = channelFlowTransformLatest.collect(new AnonymousClass2(flowCollector, map, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), gridLayout);
        this.tiles = FlowKt.transformLatest(readonlyStateFlowAsStateFlow, new EditModeViewModel$special$$inlined$flatMapLatest$1(null, this));
    }

    public final void setTiles(List list) {
        CurrentTilesInteractor currentTilesInteractor = this.currentTilesInteractor;
        List currentTilesSpecs = currentTilesInteractor.getCurrentTilesSpecs();
        currentTilesInteractor.setTiles(list);
        BuildersKt.launch$default(this.applicationScope, this.bgDispatcher, null, new AnonymousClass1(currentTilesSpecs, list, null), 2);
    }

    public final void stopEditing() {
        if (((Boolean) this.isEditing.$$delegate_0.getValue()).booleanValue()) {
            this.uiEventLogger.log(QSEditEvent.QS_EDIT_CLOSED);
        }
        this._isEditing.updateState(null, Boolean.FALSE);
    }
}
