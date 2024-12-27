package com.android.systemui.wallet.controller;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WalletContextualSuggestionsController$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ WalletContextualSuggestionsController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WalletContextualSuggestionsController$special$$inlined$flatMapLatest$1(Continuation continuation, WalletContextualSuggestionsController walletContextualSuggestionsController) {
        super(3, continuation);
        this.this$0 = walletContextualSuggestionsController;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WalletContextualSuggestionsController$special$$inlined$flatMapLatest$1 walletContextualSuggestionsController$special$$inlined$flatMapLatest$1 = new WalletContextualSuggestionsController$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        walletContextualSuggestionsController$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        walletContextualSuggestionsController$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return walletContextualSuggestionsController$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
            WalletContextualSuggestionsController$allWalletCards$1$1 walletContextualSuggestionsController$allWalletCards$1$1 = new WalletContextualSuggestionsController$allWalletCards$1$1(this.this$0, null);
            conflatedCallbackFlow.getClass();
            Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(walletContextualSuggestionsController$allWalletCards$1$1);
            this.label = 1;
            if (FlowKt.emitAll(this, conflatedCallbackFlow2, flowCollector) == coroutineSingletons) {
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
