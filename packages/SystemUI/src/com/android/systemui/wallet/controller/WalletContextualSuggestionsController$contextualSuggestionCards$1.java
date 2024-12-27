package com.android.systemui.wallet.controller;

import android.service.quickaccesswallet.WalletCard;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class WalletContextualSuggestionsController$contextualSuggestionCards$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public WalletContextualSuggestionsController$contextualSuggestionCards$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WalletContextualSuggestionsController$contextualSuggestionCards$1 walletContextualSuggestionsController$contextualSuggestionCards$1 = new WalletContextualSuggestionsController$contextualSuggestionCards$1((Continuation) obj3);
        walletContextualSuggestionsController$contextualSuggestionCards$1.L$0 = (List) obj;
        walletContextualSuggestionsController$contextualSuggestionCards$1.L$1 = (Set) obj2;
        return walletContextualSuggestionsController$contextualSuggestionCards$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        Set set = (Set) this.L$1;
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : list) {
            WalletCard walletCard = (WalletCard) obj2;
            if (walletCard.getCardType() == 2 && set.contains(walletCard.getCardId())) {
                arrayList.add(obj2);
            }
        }
        return arrayList;
    }
}
