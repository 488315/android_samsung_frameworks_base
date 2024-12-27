package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import androidx.compose.ui.input.pointer.PointerInputScope;
import com.android.systemui.communal.ui.compose.extensions.PointerInputScopeExtKt;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

final class CommunalHubKt$CommunalHub$3$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State $communalContent$delegate;
    final /* synthetic */ long $contentOffset;
    final /* synthetic */ MutableState $gridCoordinates$delegate;
    final /* synthetic */ LazyGridState $gridState;
    final /* synthetic */ BaseCommunalViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalHubKt$CommunalHub$3$1(BaseCommunalViewModel baseCommunalViewModel, MutableState mutableState, long j, LazyGridState lazyGridState, State state, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = baseCommunalViewModel;
        this.$gridCoordinates$delegate = mutableState;
        this.$contentOffset = j;
        this.$gridState = lazyGridState;
        this.$communalContent$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalHubKt$CommunalHub$3$1 communalHubKt$CommunalHub$3$1 = new CommunalHubKt$CommunalHub$3$1(this.$viewModel, this.$gridCoordinates$delegate, this.$contentOffset, this.$gridState, this.$communalContent$delegate, continuation);
        communalHubKt$CommunalHub$3$1.L$0 = obj;
        return communalHubKt$CommunalHub$3$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalHubKt$CommunalHub$3$1) create((PointerInputScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
            final BaseCommunalViewModel baseCommunalViewModel = this.$viewModel;
            final MutableState mutableState = this.$gridCoordinates$delegate;
            final long j = this.$contentOffset;
            final LazyGridState lazyGridState = this.$gridState;
            final State state = this.$communalContent$delegate;
            Function1 function1 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$3$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Code restructure failed: missing block: B:18:0x008c, code lost:
                
                    if ((((java.util.List) r6.getValue()).get(r8.intValue()) instanceof com.android.systemui.communal.domain.model.CommunalContentModel.CtaTileInViewMode) == false) goto L24;
                 */
                /* JADX WARN: Removed duplicated region for block: B:15:0x0061  */
                /* JADX WARN: Removed duplicated region for block: B:20:0x0095  */
                @Override // kotlin.jvm.functions.Function1
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r8) {
                    /*
                        r7 = this;
                        androidx.compose.ui.geometry.Offset r8 = (androidx.compose.ui.geometry.Offset) r8
                        long r0 = r8.packedValue
                        androidx.compose.runtime.MutableState r8 = r2
                        java.lang.Object r8 = r8.getValue()
                        androidx.compose.ui.layout.LayoutCoordinates r8 = (androidx.compose.ui.layout.LayoutCoordinates) r8
                        r2 = 0
                        if (r8 == 0) goto L29
                        long r3 = r3
                        androidx.compose.ui.geometry.Offset$Companion r5 = androidx.compose.ui.geometry.Offset.Companion
                        r5.getClass()
                        r5 = 0
                        long r5 = r8.mo533localToWindowMKHz9U(r5)
                        long r0 = androidx.compose.ui.geometry.Offset.m328minusMKHz9U(r0, r5)
                        long r0 = androidx.compose.ui.geometry.Offset.m328minusMKHz9U(r0, r3)
                        androidx.compose.ui.geometry.Offset r8 = androidx.compose.ui.geometry.Offset.m320boximpl(r0)
                        goto L2a
                    L29:
                        r8 = r2
                    L2a:
                        if (r8 == 0) goto L5e
                        androidx.compose.foundation.lazy.grid.LazyGridState r0 = r5
                        androidx.compose.foundation.lazy.grid.LazyGridMeasureResult r0 = r0.getLayoutInfo()
                        java.util.List r0 = r0.visibleItemsInfo
                        java.lang.Iterable r0 = (java.lang.Iterable) r0
                        java.util.Iterator r0 = r0.iterator()
                    L3a:
                        boolean r1 = r0.hasNext()
                        if (r1 == 0) goto L50
                        java.lang.Object r1 = r0.next()
                        r3 = r1
                        androidx.compose.foundation.lazy.grid.LazyGridItemInfo r3 = (androidx.compose.foundation.lazy.grid.LazyGridItemInfo) r3
                        long r4 = r8.packedValue
                        boolean r3 = com.android.systemui.communal.ui.compose.extensions.LazyGridStateExtKt.m944isItemAtOffsetUv8p0NA(r3, r4)
                        if (r3 == 0) goto L3a
                        goto L51
                    L50:
                        r1 = r2
                    L51:
                        androidx.compose.foundation.lazy.grid.LazyGridItemInfo r1 = (androidx.compose.foundation.lazy.grid.LazyGridItemInfo) r1
                        if (r1 == 0) goto L5e
                        androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem r1 = (androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem) r1
                        int r8 = r1.index
                        java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
                        goto L5f
                    L5e:
                        r8 = r2
                    L5f:
                        if (r8 == 0) goto L8e
                        androidx.compose.runtime.State r0 = r6
                        java.lang.Object r0 = r0.getValue()
                        java.util.List r0 = (java.util.List) r0
                        int r1 = r8.intValue()
                        java.lang.Object r0 = r0.get(r1)
                        com.android.systemui.communal.domain.model.CommunalContentModel r0 = (com.android.systemui.communal.domain.model.CommunalContentModel) r0
                        r0.getClass()
                        boolean r0 = r0 instanceof com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent
                        if (r0 != 0) goto L8e
                        androidx.compose.runtime.State r0 = r6
                        java.lang.Object r0 = r0.getValue()
                        java.util.List r0 = (java.util.List) r0
                        int r1 = r8.intValue()
                        java.lang.Object r0 = r0.get(r1)
                        boolean r0 = r0 instanceof com.android.systemui.communal.domain.model.CommunalContentModel.CtaTileInViewMode
                        if (r0 == 0) goto L93
                    L8e:
                        com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel r0 = com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel.this
                        r0.onShowCustomizeWidgetButton()
                    L93:
                        if (r8 == 0) goto La5
                        androidx.compose.runtime.State r0 = r6
                        java.lang.Object r0 = r0.getValue()
                        java.util.List r0 = (java.util.List) r0
                        int r8 = r8.intValue()
                        java.lang.String r2 = com.android.systemui.communal.ui.compose.CommunalHubKt.access$keyAtIndexIfEditable(r8, r0)
                    La5:
                        com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel r7 = com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel.this
                        r7.setSelectedKey(r2)
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHub$3$1.AnonymousClass1.invoke(java.lang.Object):java.lang.Object");
                }
            };
            this.label = 1;
            if (PointerInputScopeExtKt.detectLongPressGesture$default(pointerInputScope, function1, this) == coroutineSingletons) {
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
