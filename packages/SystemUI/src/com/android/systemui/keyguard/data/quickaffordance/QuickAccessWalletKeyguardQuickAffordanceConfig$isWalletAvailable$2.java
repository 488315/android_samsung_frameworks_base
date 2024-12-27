package com.android.systemui.keyguard.data.quickaffordance;

import android.service.quickaccesswallet.QuickAccessWalletClient;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class QuickAccessWalletKeyguardQuickAffordanceConfig$isWalletAvailable$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ QuickAccessWalletKeyguardQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QuickAccessWalletKeyguardQuickAffordanceConfig$isWalletAvailable$2(QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig, Continuation continuation) {
        super(2, continuation);
        this.this$0 = quickAccessWalletKeyguardQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new QuickAccessWalletKeyguardQuickAffordanceConfig$isWalletAvailable$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QuickAccessWalletKeyguardQuickAffordanceConfig$isWalletAvailable$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        QuickAccessWalletClient quickAccessWalletClient = this.this$0.walletController.mQuickAccessWalletClient;
        return Boolean.valueOf(quickAccessWalletClient.isWalletServiceAvailable() && quickAccessWalletClient.isWalletFeatureAvailable());
    }
}
