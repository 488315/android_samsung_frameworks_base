package com.android.systemui.wallet.controller;

import android.service.quickaccesswallet.GetWalletCardsError;
import android.service.quickaccesswallet.GetWalletCardsResponse;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class WalletContextualSuggestionsController$allWalletCards$1$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WalletContextualSuggestionsController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WalletContextualSuggestionsController$allWalletCards$1$1(WalletContextualSuggestionsController walletContextualSuggestionsController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = walletContextualSuggestionsController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WalletContextualSuggestionsController$allWalletCards$1$1 walletContextualSuggestionsController$allWalletCards$1$1 = new WalletContextualSuggestionsController$allWalletCards$1$1(this.this$0, continuation);
        walletContextualSuggestionsController$allWalletCards$1$1.L$0 = obj;
        return walletContextualSuggestionsController$allWalletCards$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WalletContextualSuggestionsController$allWalletCards$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback = new QuickAccessWalletClient.OnWalletCardsRetrievedCallback() { // from class: com.android.systemui.wallet.controller.WalletContextualSuggestionsController$allWalletCards$1$1$callback$1
                public final void onWalletCardRetrievalError(GetWalletCardsError getWalletCardsError) {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, EmptyList.INSTANCE, "WalletSuggestions");
                }

                public final void onWalletCardsRetrieved(GetWalletCardsResponse getWalletCardsResponse) {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, getWalletCardsResponse.getWalletCards(), "WalletSuggestions");
                }
            };
            this.this$0.walletController.setupWalletChangeObservers(onWalletCardsRetrievedCallback, QuickAccessWalletController.WalletChangeEvent.WALLET_PREFERENCE_CHANGE, QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE, QuickAccessWalletController.WalletChangeEvent.DEFAULT_WALLET_APP_CHANGE);
            this.this$0.walletController.updateWalletPreference();
            this.this$0.walletController.queryWalletCards(onWalletCardsRetrievedCallback, 50);
            final WalletContextualSuggestionsController walletContextualSuggestionsController = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.wallet.controller.WalletContextualSuggestionsController$allWalletCards$1$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    WalletContextualSuggestionsController.this.walletController.unregisterWalletChangeObservers(QuickAccessWalletController.WalletChangeEvent.WALLET_PREFERENCE_CHANGE, QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE, QuickAccessWalletController.WalletChangeEvent.DEFAULT_WALLET_APP_CHANGE);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
