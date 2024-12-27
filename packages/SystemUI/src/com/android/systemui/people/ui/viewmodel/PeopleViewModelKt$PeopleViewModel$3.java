package com.android.systemui.people.ui.viewmodel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

final /* synthetic */ class PeopleViewModelKt$PeopleViewModel$3 extends FunctionReferenceImpl implements Function0 {
    final /* synthetic */ MutableStateFlow $result;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeopleViewModelKt$PeopleViewModel$3(MutableStateFlow mutableStateFlow) {
        super(0, Intrinsics.Kotlin.class, "clearResult", "PeopleViewModel$clearResult(Lkotlinx/coroutines/flow/MutableStateFlow;)V", 0);
        this.$result = mutableStateFlow;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((StateFlowImpl) this.$result).setValue(null);
        return Unit.INSTANCE;
    }
}
