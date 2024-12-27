package com.android.systemui.wallet.controller;

import android.content.IntentFilter;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WalletContextualSuggestionsController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _suggestionCardIds;
    public final ReadonlyStateFlow allWalletCards;
    public final CoroutineScope applicationCoroutineScope;
    public final Set cardsReceivedCallbacks = new LinkedHashSet();
    public final ReadonlyStateFlow contextualSuggestionsCardIds;
    public final QuickAccessWalletController walletController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public WalletContextualSuggestionsController(CoroutineScope coroutineScope, QuickAccessWalletController quickAccessWalletController, BroadcastDispatcher broadcastDispatcher, FeatureFlags featureFlags) {
        ReadonlyStateFlow asStateFlow;
        this.applicationCoroutineScope = coroutineScope;
        this.walletController = quickAccessWalletController;
        if (((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.ENABLE_WALLET_CONTEXTUAL_LOYALTY_CARDS)) {
            FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.transformLatest(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.SCREEN_ON"), null, 0, null, 14), new WalletContextualSuggestionsController$special$$inlined$flatMapLatest$1(null, this)), new WalletContextualSuggestionsController$allWalletCards$2(this, null));
            SharingStarted.Companion.getClass();
            asStateFlow = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.Eagerly, EmptyList.INSTANCE);
        } else {
            asStateFlow = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(EmptyList.INSTANCE));
        }
        this.allWalletCards = asStateFlow;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptySet.INSTANCE);
        this._suggestionCardIds = MutableStateFlow;
        FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(asStateFlow, FlowKt.asStateFlow(MutableStateFlow), new WalletContextualSuggestionsController$contextualSuggestionCards$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), EmptyList.INSTANCE);
    }
}
