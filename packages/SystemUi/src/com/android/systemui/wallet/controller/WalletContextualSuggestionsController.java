package com.android.systemui.wallet.controller;

import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WalletContextualSuggestionsController {
    public final StateFlowImpl _suggestionCardIds;
    public final ReadonlyStateFlow allWalletCards;
    public final CoroutineScope applicationCoroutineScope;
    public final Set cardsReceivedCallbacks = new LinkedHashSet();
    public final ReadonlyStateFlow contextualSuggestionsCardIds;
    public final QuickAccessWalletController walletController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.applicationCoroutineScope = coroutineScope;
        this.walletController = quickAccessWalletController;
        Flags flags = Flags.INSTANCE;
        EmptyList emptyList = EmptyList.INSTANCE;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(emptyList));
        this.allWalletCards = asStateFlow;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptySet.INSTANCE);
        this._suggestionCardIds = MutableStateFlow;
        ReadonlyStateFlow asStateFlow2 = FlowKt.asStateFlow(MutableStateFlow);
        this.contextualSuggestionsCardIds = asStateFlow2;
        FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(asStateFlow, asStateFlow2, new C3652x376f2cd8(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion), emptyList);
    }
}
