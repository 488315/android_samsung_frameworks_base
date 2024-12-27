package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.gestures.DragGestureDetectorKt;
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.unit.IntOffset;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.ui.compose.extensions.LazyGridStateExtKt;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class GridDragDropStateKt$dragContainer$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $contentOffset;
    final /* synthetic */ GridDragDropState $dragDropState;
    final /* synthetic */ BaseCommunalViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GridDragDropStateKt$dragContainer$1(GridDragDropState gridDragDropState, long j, BaseCommunalViewModel baseCommunalViewModel, Continuation continuation) {
        super(2, continuation);
        this.$dragDropState = gridDragDropState;
        this.$contentOffset = j;
        this.$viewModel = baseCommunalViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        GridDragDropStateKt$dragContainer$1 gridDragDropStateKt$dragContainer$1 = new GridDragDropStateKt$dragContainer$1(this.$dragDropState, this.$contentOffset, this.$viewModel, continuation);
        gridDragDropStateKt$dragContainer$1.L$0 = obj;
        return gridDragDropStateKt$dragContainer$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GridDragDropStateKt$dragContainer$1) create((PointerInputScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
            final GridDragDropState gridDragDropState = this.$dragDropState;
            final long j = this.$contentOffset;
            final BaseCommunalViewModel baseCommunalViewModel = this.$viewModel;
            Function1 function1 = new Function1() { // from class: com.android.systemui.communal.ui.compose.GridDragDropStateKt$dragContainer$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Object obj3;
                    long j2 = ((Offset) obj2).packedValue;
                    GridDragDropState gridDragDropState2 = GridDragDropState.this;
                    long j3 = j;
                    List list = gridDragDropState2.state.getLayoutInfo().visibleItemsInfo;
                    ArrayList arrayList = new ArrayList();
                    for (Object obj4 : list) {
                        CommunalContentModel communalContentModel = (CommunalContentModel) gridDragDropState2.contentListState.list.get(((LazyGridMeasuredItem) ((LazyGridItemInfo) obj4)).index);
                        communalContentModel.getClass();
                        if (communalContentModel instanceof CommunalContentModel.WidgetContent) {
                            arrayList.add(obj4);
                        }
                    }
                    long m328minusMKHz9U = Offset.m328minusMKHz9U(j2, j3);
                    Iterator it = arrayList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            obj3 = null;
                            break;
                        }
                        obj3 = it.next();
                        if (LazyGridStateExtKt.m944isItemAtOffsetUv8p0NA((LazyGridItemInfo) obj3, m328minusMKHz9U)) {
                            break;
                        }
                    }
                    LazyGridItemInfo lazyGridItemInfo = (LazyGridItemInfo) obj3;
                    if (lazyGridItemInfo != null) {
                        LazyGridMeasuredItem lazyGridMeasuredItem = (LazyGridMeasuredItem) lazyGridItemInfo;
                        long j4 = lazyGridMeasuredItem.offset;
                        IntOffset.Companion companion = IntOffset.Companion;
                        gridDragDropState2.dragStartPointerOffset$delegate.setValue(Offset.m320boximpl(Offset.m328minusMKHz9U(j2, OffsetKt.Offset((int) (j4 >> 32), (int) (j4 & 4294967295L)))));
                        gridDragDropState2.draggingItemIndex$delegate.setValue(Integer.valueOf(lazyGridMeasuredItem.index));
                        long j5 = lazyGridMeasuredItem.offset;
                        gridDragDropState2.draggingItemInitialOffset$delegate.setValue(Offset.m320boximpl(OffsetKt.Offset((int) (j5 >> 32), (int) (j5 & 4294967295L))));
                    }
                    baseCommunalViewModel.onReorderWidgetStart();
                    return Unit.INSTANCE;
                }
            };
            final GridDragDropState gridDragDropState2 = this.$dragDropState;
            final BaseCommunalViewModel baseCommunalViewModel2 = this.$viewModel;
            Function0 function0 = new Function0() { // from class: com.android.systemui.communal.ui.compose.GridDragDropStateKt$dragContainer$1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    GridDragDropState.this.onDragInterrupted$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                    baseCommunalViewModel2.onReorderWidgetEnd();
                    return Unit.INSTANCE;
                }
            };
            final GridDragDropState gridDragDropState3 = this.$dragDropState;
            final BaseCommunalViewModel baseCommunalViewModel3 = this.$viewModel;
            Function0 function02 = new Function0() { // from class: com.android.systemui.communal.ui.compose.GridDragDropStateKt$dragContainer$1.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    GridDragDropState.this.onDragInterrupted$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                    baseCommunalViewModel3.onReorderWidgetCancel();
                    return Unit.INSTANCE;
                }
            };
            final GridDragDropState gridDragDropState4 = this.$dragDropState;
            Function2 function2 = new Function2() { // from class: com.android.systemui.communal.ui.compose.GridDragDropStateKt$dragContainer$1.4
                {
                    super(2);
                }

                /* JADX WARN: Removed duplicated region for block: B:16:0x00df  */
                /* JADX WARN: Removed duplicated region for block: B:18:0x00ee  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r13, java.lang.Object r14) {
                    /*
                        Method dump skipped, instructions count: 405
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.GridDragDropStateKt$dragContainer$1.AnonymousClass4.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            };
            this.label = 1;
            if (DragGestureDetectorKt.detectDragGesturesAfterLongPress(pointerInputScope, function1, function0, function02, function2, this) == coroutineSingletons) {
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
