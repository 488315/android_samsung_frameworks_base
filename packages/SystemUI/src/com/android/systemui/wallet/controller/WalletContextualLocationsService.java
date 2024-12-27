package com.android.systemui.wallet.controller;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleService;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;

public final class WalletContextualLocationsService extends LifecycleService {
    public final CoroutineDispatcher backgroundDispatcher;
    public final WalletContextualLocationsService$binder$1 binder;
    public final WalletContextualSuggestionsController controller;
    public final FeatureFlags featureFlags;
    public IWalletCardsUpdatedListener listener;
    public final CoroutineScope scope;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public WalletContextualLocationsService(CoroutineDispatcher coroutineDispatcher, WalletContextualSuggestionsController walletContextualSuggestionsController, FeatureFlags featureFlags) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.controller = walletContextualSuggestionsController;
        this.featureFlags = featureFlags;
        this.scope = LifecycleKt.getCoroutineScope(getLifecycle());
        this.binder = new WalletContextualLocationsService$binder$1(this);
    }

    public final void addWalletCardsUpdatedListenerInternal(IWalletCardsUpdatedListener iWalletCardsUpdatedListener) {
        if (((FeatureFlagsClassicRelease) this.featureFlags).isEnabled(Flags.ENABLE_WALLET_CONTEXTUAL_LOYALTY_CARDS)) {
            this.listener = iWalletCardsUpdatedListener;
            List list = (List) this.controller.allWalletCards.$$delegate_0.getValue();
            if (list.isEmpty()) {
                return;
            }
            KeyguardSecPatternView$$ExternalSyntheticOutline0.m(list.size(), "Number of cards registered ", "WalletContextualLocationsService");
            ((IWalletCardsUpdatedListener$Stub$Proxy) iWalletCardsUpdatedListener).registerNewWalletCards(list);
        }
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public final IBinder onBind(Intent intent) {
        super.onBind(intent);
        com.android.systemui.Flags.FEATURE_FLAGS.getClass();
        BuildersKt.launch$default(this.scope, this.backgroundDispatcher, null, new WalletContextualLocationsService$onBind$1(this, null), 2);
        return this.binder;
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        this.listener = null;
    }

    public final void onWalletContextualLocationsStateUpdatedInternal(List<String> list) {
        Object value;
        if (((FeatureFlagsClassicRelease) this.featureFlags).isEnabled(Flags.ENABLE_WALLET_CONTEXTUAL_LOYALTY_CARDS)) {
            Log.i("WalletContextualLocationsService", "Entered store " + list);
            WalletContextualSuggestionsController walletContextualSuggestionsController = this.controller;
            Set set = CollectionsKt___CollectionsKt.toSet(list);
            StateFlowImpl stateFlowImpl = walletContextualSuggestionsController._suggestionCardIds;
            do {
                value = stateFlowImpl.getValue();
            } while (!stateFlowImpl.compareAndSet(value, set));
        }
    }

    public WalletContextualLocationsService(CoroutineDispatcher coroutineDispatcher, WalletContextualSuggestionsController walletContextualSuggestionsController, FeatureFlags featureFlags, CoroutineScope coroutineScope) {
        this(coroutineDispatcher, walletContextualSuggestionsController, featureFlags);
        this.scope = coroutineScope;
    }
}
