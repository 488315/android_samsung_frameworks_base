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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class WalletContextualSuggestionsController$notifyCallbacks$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<WalletCard> $cards;
    int label;
    final /* synthetic */ WalletContextualSuggestionsController this$0;

    public WalletContextualSuggestionsController$notifyCallbacks$1(WalletContextualSuggestionsController walletContextualSuggestionsController, List<WalletCard> list, Continuation continuation) {
        super(2, continuation);
        this.this$0 = walletContextualSuggestionsController;
        this.$cards = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WalletContextualSuggestionsController$notifyCallbacks$1(this.this$0, this.$cards, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WalletContextualSuggestionsController$notifyCallbacks$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Set<Function1> set = this.this$0.cardsReceivedCallbacks;
        List<WalletCard> list = this.$cards;
        for (Function1 function1 : set) {
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : list) {
                if (((WalletCard) obj2).getCardType() == 2) {
                    arrayList.add(obj2);
                }
            }
            function1.invoke(arrayList);
        }
        return Unit.INSTANCE;
    }
}
