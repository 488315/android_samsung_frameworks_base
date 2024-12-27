package com.android.systemui.people.ui.viewmodel;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

final /* synthetic */ class PeopleViewModelKt$PeopleViewModel$2 extends FunctionReferenceImpl implements Function1 {
    final /* synthetic */ MutableStateFlow $appWidgetId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeopleViewModelKt$PeopleViewModel$2(MutableStateFlow mutableStateFlow) {
        super(1, Intrinsics.Kotlin.class, "onWidgetIdChanged", "PeopleViewModel$onWidgetIdChanged(Lkotlinx/coroutines/flow/MutableStateFlow;I)V", 0);
        this.$appWidgetId = mutableStateFlow;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        int intValue = ((Number) obj).intValue();
        ((StateFlowImpl) this.$appWidgetId).updateState(null, Integer.valueOf(intValue));
        return Unit.INSTANCE;
    }
}
