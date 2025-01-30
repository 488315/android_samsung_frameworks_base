package com.android.systemui.keyguard.data.quickaffordance;

import android.graphics.drawable.Drawable;
import android.service.quickaccesswallet.GetWalletCardsError;
import android.service.quickaccesswallet.GetWalletCardsResponse;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.service.quickaccesswallet.WalletCard;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1", m277f = "QuickAccessWalletKeyguardQuickAffordanceConfig.kt", m278l = {91}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QuickAccessWalletKeyguardQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1(QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig, Continuation<? super QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1> continuation) {
        super(2, continuation);
        this.this$0 = quickAccessWalletKeyguardQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1 quickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1 = new QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1(this.this$0, continuation);
        quickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1.L$0 = obj;
        return quickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig = this.this$0;
            QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback = new QuickAccessWalletClient.OnWalletCardsRetrievedCallback() { // from class: com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1
                public final void onWalletCardRetrievalError(GetWalletCardsError getWalletCardsError) {
                    Log.e("QuickAccessWalletKeyguardQuickAffordanceConfig", "Wallet card retrieval error, message: \"" + ((Object) (getWalletCardsError != null ? getWalletCardsError.getMessage() : null)) + "\"");
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE, "QuickAccessWalletKeyguardQuickAffordanceConfig");
                }

                public final void onWalletCardsRetrieved(GetWalletCardsResponse getWalletCardsResponse) {
                    List<WalletCard> walletCards;
                    boolean z = (getWalletCardsResponse == null || (walletCards = getWalletCardsResponse.getWalletCards()) == null || !(walletCards.isEmpty() ^ true)) ? false : true;
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    ProducerScope producerScope2 = ProducerScope.this;
                    QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig2 = quickAccessWalletKeyguardQuickAffordanceConfig;
                    int i2 = QuickAccessWalletKeyguardQuickAffordanceConfig.$r8$clinit;
                    QuickAccessWalletClient quickAccessWalletClient = quickAccessWalletKeyguardQuickAffordanceConfig2.walletController.mQuickAccessWalletClient;
                    boolean z2 = quickAccessWalletClient.isWalletServiceAvailable() && quickAccessWalletClient.isWalletFeatureAvailable();
                    Drawable tileIcon = quickAccessWalletKeyguardQuickAffordanceConfig.walletController.mQuickAccessWalletClient.getTileIcon();
                    ChannelExt.trySendWithFailureLogging$default(channelExt, producerScope2, (z2 && z && tileIcon != null) ? new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(tileIcon, new ContentDescription.Resource(R.string.accessibility_wallet_button)), null, 2, null) : KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE, "QuickAccessWalletKeyguardQuickAffordanceConfig");
                }
            };
            this.this$0.walletController.setupWalletChangeObservers(onWalletCardsRetrievedCallback, QuickAccessWalletController.WalletChangeEvent.WALLET_PREFERENCE_CHANGE, QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE);
            this.this$0.walletController.updateWalletPreference();
            this.this$0.walletController.queryWalletCards(onWalletCardsRetrievedCallback);
            final QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    QuickAccessWalletKeyguardQuickAffordanceConfig.this.walletController.unregisterWalletChangeObservers(QuickAccessWalletController.WalletChangeEvent.WALLET_PREFERENCE_CHANGE, QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE);
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
