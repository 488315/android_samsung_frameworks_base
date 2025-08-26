package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.compose.runtime.State;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.qs.panels.domain.interactor.GridLayoutTypeInteractor;
import com.android.systemui.qs.panels.shared.model.GridLayoutType;
import com.android.systemui.qs.panels.ui.compose.GridLayout;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class TileGridViewModel extends ExclusiveActivatable {
    public final State gridLayout$delegate;
    public final Hydrator hydrator;
    public final State tileModels$delegate;
    public final CurrentTilesInteractor tilesInteractor;

    public interface Factory {
        TileGridViewModel create();
    }

    /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TileGridViewModel.this.onActivated(this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TileGridViewModel(GridLayoutTypeInteractor gridLayoutTypeInteractor, final Map<GridLayoutType, GridLayout> map, CurrentTilesInteractor currentTilesInteractor, final GridLayout gridLayout) {
        this.tilesInteractor = currentTilesInteractor;
        Hydrator hydrator = new Hydrator("TileGridViewModel", null, 2, 0 == true ? 1 : 0);
        this.hydrator = hydrator;
        final ChannelFlowTransformLatest channelFlowTransformLatest = gridLayoutTypeInteractor.layout;
        this.gridLayout$delegate = hydrator.hydratedStateOf("gridLayout", gridLayout, new Flow() { // from class: com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ GridLayout $defaultGridLayout$inlined;
                public final /* synthetic */ Map $gridLayoutMap$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, Map map, GridLayout gridLayout) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$gridLayoutMap$inlined = map;
                    this.$defaultGridLayout$inlined = gridLayout;
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
                            gridLayout = this.$defaultGridLayout$inlined;
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
                Object objCollect = channelFlowTransformLatest.collect(new AnonymousClass2(flowCollector, map, gridLayout), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.tileModels$delegate = hydrator.hydratedStateOf(currentTilesInteractor.getCurrentTiles(), "tileModels");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
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
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            anonymousClass1.label = 1;
            if (this.hydrator.activate(anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
