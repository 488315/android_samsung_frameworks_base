package com.android.systemui.wallet.controller;

import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;

final class WalletContextualSuggestionsController$allWalletCards$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WalletContextualSuggestionsController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WalletContextualSuggestionsController$allWalletCards$2(WalletContextualSuggestionsController walletContextualSuggestionsController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = walletContextualSuggestionsController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WalletContextualSuggestionsController$allWalletCards$2 walletContextualSuggestionsController$allWalletCards$2 = new WalletContextualSuggestionsController$allWalletCards$2(this.this$0, continuation);
        walletContextualSuggestionsController$allWalletCards$2.L$0 = obj;
        return walletContextualSuggestionsController$allWalletCards$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WalletContextualSuggestionsController$allWalletCards$2) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        WalletContextualSuggestionsController walletContextualSuggestionsController = this.this$0;
        int i = WalletContextualSuggestionsController.$r8$clinit;
        walletContextualSuggestionsController.getClass();
        BuildersKt.launch$default(walletContextualSuggestionsController.applicationCoroutineScope, null, null, new WalletContextualSuggestionsController$notifyCallbacks$1(walletContextualSuggestionsController, list, null), 3);
        return Unit.INSTANCE;
    }
}
