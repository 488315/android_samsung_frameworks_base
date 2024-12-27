package com.android.systemui.people.ui.viewmodel;

import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

final /* synthetic */ class PeopleViewModelKt$PeopleViewModel$5 extends FunctionReferenceImpl implements Function0 {
    final /* synthetic */ MutableStateFlow $result;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeopleViewModelKt$PeopleViewModel$5(MutableStateFlow mutableStateFlow) {
        super(0, Intrinsics.Kotlin.class, "onUserJourneyCancelled", "PeopleViewModel$onUserJourneyCancelled(Lkotlinx/coroutines/flow/MutableStateFlow;)V", 0);
        this.$result = mutableStateFlow;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((StateFlowImpl) this.$result).setValue(PeopleViewModel.Result.Cancelled.INSTANCE);
        return Unit.INSTANCE;
    }
}
