package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl;
import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

public final class PromptIconViewModel$special$$inlined$flatMapLatest$4 extends SuspendLambda implements Function3 {
    final /* synthetic */ PromptSelectorInteractor $promptSelectorInteractor$inlined;
    final /* synthetic */ PromptViewModel $promptViewModel$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PromptIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$special$$inlined$flatMapLatest$4(Continuation continuation, PromptSelectorInteractor promptSelectorInteractor, PromptViewModel promptViewModel, PromptIconViewModel promptIconViewModel) {
        super(3, continuation);
        this.$promptSelectorInteractor$inlined = promptSelectorInteractor;
        this.$promptViewModel$inlined = promptViewModel;
        this.this$0 = promptIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptIconViewModel$special$$inlined$flatMapLatest$4 promptIconViewModel$special$$inlined$flatMapLatest$4 = new PromptIconViewModel$special$$inlined$flatMapLatest$4((Continuation) obj3, this.$promptSelectorInteractor$inlined, this.$promptViewModel$inlined, this.this$0);
        promptIconViewModel$special$$inlined$flatMapLatest$4.L$0 = (FlowCollector) obj;
        promptIconViewModel$special$$inlined$flatMapLatest$4.L$1 = obj2;
        return promptIconViewModel$special$$inlined$flatMapLatest$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow combine;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int i2 = PromptIconViewModel.WhenMappings.$EnumSwitchMapping$1[((PromptIconViewModel.AuthType) this.L$1).ordinal()];
            if (i2 == 1) {
                StateFlow stateFlow = ((PromptSelectorInteractorImpl) this.$promptSelectorInteractor$inlined).sensorType;
                PromptViewModel promptViewModel = this.$promptViewModel$inlined;
                combine = FlowKt.combine(stateFlow, promptViewModel.isAuthenticated, promptViewModel.isAuthenticating, promptViewModel.showingError, new PromptIconViewModel$shouldAnimateIconView$1$1(this.this$0, null));
            } else if (i2 == 2) {
                PromptViewModel promptViewModel2 = this.$promptViewModel$inlined;
                combine = FlowKt.combine(promptViewModel2.isAuthenticated, promptViewModel2.isAuthenticating, promptViewModel2.showingError, new PromptIconViewModel$shouldAnimateIconView$1$2(this.this$0, null));
            } else {
                if (i2 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                StateFlow stateFlow2 = ((PromptSelectorInteractorImpl) this.$promptSelectorInteractor$inlined).sensorType;
                PromptViewModel promptViewModel3 = this.$promptViewModel$inlined;
                combine = FlowKt.combine(stateFlow2, promptViewModel3.isAuthenticated, promptViewModel3.isAuthenticating, promptViewModel3.isPendingConfirmation, promptViewModel3.showingError, new PromptIconViewModel$shouldAnimateIconView$1$3(this.this$0, null));
            }
            this.label = 1;
            if (FlowKt.emitAll(this, combine, flowCollector) == coroutineSingletons) {
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
