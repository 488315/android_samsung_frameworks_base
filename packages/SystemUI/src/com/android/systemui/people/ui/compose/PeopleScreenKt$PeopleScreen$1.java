package com.android.systemui.people.ui.compose;

import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PeopleScreenKt$PeopleScreen$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $onResult;
    final /* synthetic */ PeopleViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeopleScreenKt$PeopleScreen$1(PeopleViewModel peopleViewModel, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = peopleViewModel;
        this.$onResult = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PeopleScreenKt$PeopleScreen$1(this.$viewModel, this.$onResult, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PeopleScreenKt$PeopleScreen$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final PeopleViewModel peopleViewModel = this.$viewModel;
            StateFlow stateFlow = peopleViewModel.result;
            final Function1 function1 = this.$onResult;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.people.ui.compose.PeopleScreenKt$PeopleScreen$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    PeopleViewModel.Result result = (PeopleViewModel.Result) obj2;
                    if (result != null) {
                        PeopleViewModel.this.clearResult.invoke();
                        function1.invoke(result);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (stateFlow.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
