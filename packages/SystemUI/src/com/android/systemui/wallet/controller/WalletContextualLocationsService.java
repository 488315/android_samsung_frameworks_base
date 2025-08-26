package com.android.systemui.wallet.controller;

import android.content.Intent;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.util.Log;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleService;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import java.util.List;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class WalletContextualLocationsService extends LifecycleService {
    public final CoroutineDispatcher backgroundDispatcher;
    public final WalletContextualLocationsService$binder$1 binder;
    public final WalletContextualSuggestionsController controller;
    public final FeatureFlags featureFlags;
    public IWalletCardsUpdatedListener listener;
    public final CoroutineScope scope;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.wallet.controller.WalletContextualLocationsService$onBind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return WalletContextualLocationsService.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final WalletContextualLocationsService walletContextualLocationsService = WalletContextualLocationsService.this;
                ReadonlyStateFlow readonlyStateFlow = walletContextualLocationsService.controller.allWalletCards;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.wallet.controller.WalletContextualLocationsService.onBind.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        List list = (List) obj2;
                        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(list.size(), "Number of cards registered ", "WalletContextualLocationsService");
                        try {
                            IWalletCardsUpdatedListener iWalletCardsUpdatedListener = walletContextualLocationsService.listener;
                            if (iWalletCardsUpdatedListener != null) {
                                ((IWalletCardsUpdatedListener$Stub$Proxy) iWalletCardsUpdatedListener).registerNewWalletCards(list);
                            }
                        } catch (DeadObjectException unused) {
                            Log.e("WalletContextualLocationsService", "Failed to register wallet cards because IWalletCardsUpdatedListener is dead");
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    static {
        new Companion(null);
    }

    public WalletContextualLocationsService(CoroutineDispatcher coroutineDispatcher, WalletContextualSuggestionsController walletContextualSuggestionsController, FeatureFlags featureFlags) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.controller = walletContextualSuggestionsController;
        this.featureFlags = featureFlags;
        this.scope = LifecycleKt.getCoroutineScope(this.dispatcher.registry);
        this.binder = new WalletContextualLocationsService$binder$1(this);
    }

    public final void addWalletCardsUpdatedListenerInternal(IWalletCardsUpdatedListener iWalletCardsUpdatedListener) {
        if (((FeatureFlagsClassicRelease) this.featureFlags).isEnabled(Flags.ENABLE_WALLET_CONTEXTUAL_LOYALTY_CARDS)) {
            this.listener = iWalletCardsUpdatedListener;
            List list = (List) this.controller.allWalletCards.$$delegate_0.getValue();
            if (list.isEmpty()) {
                return;
            }
            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(list.size(), "Number of cards registered ", "WalletContextualLocationsService");
            ((IWalletCardsUpdatedListener$Stub$Proxy) iWalletCardsUpdatedListener).registerNewWalletCards(list);
        }
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public final IBinder onBind(Intent intent) {
        super.onBind(intent);
        CoroutineTracingKt.launchTraced$default(this.scope, this.backgroundDispatcher, null, new AnonymousClass1(null), 5);
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
