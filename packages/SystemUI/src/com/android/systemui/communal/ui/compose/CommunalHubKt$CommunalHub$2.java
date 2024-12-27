package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputScope;
import com.android.systemui.communal.ui.compose.extensions.LazyGridStateExtKt;
import com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

final class CommunalHubKt$CommunalHub$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ContentListState $contentListState;
    final /* synthetic */ long $contentOffset;
    final /* synthetic */ LazyGridState $gridState;
    final /* synthetic */ BaseCommunalViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalHubKt$CommunalHub$2(BaseCommunalViewModel baseCommunalViewModel, long j, LazyGridState lazyGridState, ContentListState contentListState, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = baseCommunalViewModel;
        this.$contentOffset = j;
        this.$gridState = lazyGridState;
        this.$contentListState = contentListState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalHubKt$CommunalHub$2 communalHubKt$CommunalHub$2 = new CommunalHubKt$CommunalHub$2(this.$viewModel, this.$contentOffset, this.$gridState, this.$contentListState, continuation);
        communalHubKt$CommunalHub$2.L$0 = obj;
        return communalHubKt$CommunalHub$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalHubKt$CommunalHub$2) create((PointerInputScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
            if (!this.$viewModel.isEditMode()) {
                return Unit.INSTANCE;
            }
            final long j = this.$contentOffset;
            final LazyGridState lazyGridState = this.$gridState;
            final BaseCommunalViewModel baseCommunalViewModel = this.$viewModel;
            final ContentListState contentListState = this.$contentListState;
            Function1 function1 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$2.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Object obj3;
                    long m328minusMKHz9U = Offset.m328minusMKHz9U(((Offset) obj2).packedValue, j);
                    Iterator it = lazyGridState.getLayoutInfo().visibleItemsInfo.iterator();
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
                    Integer valueOf = lazyGridItemInfo != null ? Integer.valueOf(((LazyGridMeasuredItem) lazyGridItemInfo).index) : null;
                    baseCommunalViewModel.setSelectedKey(valueOf != null ? CommunalHubKt.access$keyAtIndexIfEditable(valueOf.intValue(), contentListState.list) : null);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (PointerInputScopeExtKt.observeTaps$default(pointerInputScope, false, function1, this, 3) == coroutineSingletons) {
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
