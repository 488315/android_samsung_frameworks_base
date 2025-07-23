package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QuickAccessWalletKeyguardQuickAffordanceConfig this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1 $callback;
        int label;
        final /* synthetic */ QuickAccessWalletKeyguardQuickAffordanceConfig this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig, QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1 quickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1, Continuation continuation) {
            super(2, continuation);
            this.this$0 = quickAccessWalletKeyguardQuickAffordanceConfig;
            this.$callback = quickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.this$0.walletController.updateWalletPreference();
            this.this$0.walletController.queryWalletCards(this.$callback, 1);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1(QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig, Continuation continuation) {
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0062, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r4, r9) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0064, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0050, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r6, r7, r9) == r0) goto L15;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v2, types: [android.service.quickaccesswallet.QuickAccessWalletClient$OnWalletCardsRetrievedCallback, com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L21
            if (r1 == r4) goto L19
            if (r1 != r3) goto L11
            kotlin.ResultKt.throwOnFailure(r10)
            goto L65
        L11:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L19:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L53
        L21:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            r1 = r10
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1 r10 = new com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1
            r10.<init>()
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig r5 = r9.this$0
            com.android.systemui.wallet.controller.QuickAccessWalletController r5 = r5.walletController
            com.android.systemui.wallet.controller.QuickAccessWalletController$WalletChangeEvent r6 = com.android.systemui.wallet.controller.QuickAccessWalletController.WalletChangeEvent.WALLET_PREFERENCE_CHANGE
            com.android.systemui.wallet.controller.QuickAccessWalletController$WalletChangeEvent r7 = com.android.systemui.wallet.controller.QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE
            com.android.systemui.wallet.controller.QuickAccessWalletController$WalletChangeEvent r8 = com.android.systemui.wallet.controller.QuickAccessWalletController.WalletChangeEvent.DEFAULT_WALLET_APP_CHANGE
            com.android.systemui.wallet.controller.QuickAccessWalletController$WalletChangeEvent[] r6 = new com.android.systemui.wallet.controller.QuickAccessWalletController.WalletChangeEvent[]{r6, r7, r8}
            r5.setupWalletChangeObservers(r10, r6)
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig r5 = r9.this$0
            kotlinx.coroutines.CoroutineDispatcher r6 = r5.backgroundDispatcher
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$1 r7 = new com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$1
            r7.<init>(r5, r10, r2)
            r9.L$0 = r1
            r9.label = r4
            java.lang.Object r10 = kotlinx.coroutines.BuildersKt.withContext(r6, r7, r9)
            if (r10 != r0) goto L53
            goto L64
        L53:
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig r10 = r9.this$0
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$$ExternalSyntheticLambda0 r4 = new com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1$$ExternalSyntheticLambda0
            r4.<init>()
            r9.L$0 = r2
            r9.label = r3
            java.lang.Object r9 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r4, r9)
            if (r9 != r0) goto L65
        L64:
            return r0
        L65:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
