package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

public final class QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ QuickAccessWalletKeyguardQuickAffordanceConfig this$0;

    public QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1(Continuation continuation, QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig) {
        super(3, continuation);
        this.this$0 = quickAccessWalletKeyguardQuickAffordanceConfig;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1 quickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1 = new QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        quickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        quickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return quickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

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
            r3 = 1
            r4 = 2
            if (r1 == 0) goto L2a
            if (r1 == r3) goto L1a
            if (r1 != r4) goto L12
            kotlin.ResultKt.throwOnFailure(r10)
            goto La1
        L12:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L1a:
            java.lang.Object r1 = r9.L$2
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig r1 = (com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig) r1
            java.lang.Object r3 = r9.L$1
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            java.lang.Object r5 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            kotlin.ResultKt.throwOnFailure(r10)
            goto L5b
        L2a:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            r5 = r10
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            java.lang.Object r10 = r9.L$1
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            if (r10 != 0) goto L3b
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Hidden r10 = com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE
            goto L8d
        L3b:
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig r1 = r9.this$0
            r9.L$0 = r5
            r9.L$1 = r10
            r9.L$2 = r1
            r9.label = r3
            int r3 = com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig.$r8$clinit
            r1.getClass()
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$isWalletAvailable$2 r3 = new com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$isWalletAvailable$2
            r3.<init>(r1, r2)
            kotlinx.coroutines.CoroutineDispatcher r6 = r1.backgroundDispatcher
            java.lang.Object r3 = kotlinx.coroutines.BuildersKt.withContext(r6, r3, r9)
            if (r3 != r0) goto L58
            return r0
        L58:
            r8 = r3
            r3 = r10
            r10 = r8
        L5b:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            boolean r3 = r3.booleanValue()
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig r6 = r9.this$0
            com.android.systemui.wallet.controller.QuickAccessWalletController r6 = r6.walletController
            android.service.quickaccesswallet.QuickAccessWalletClient r6 = r6.mQuickAccessWalletClient
            android.graphics.drawable.Drawable r6 = r6.getTileIcon()
            r1.getClass()
            if (r10 == 0) goto L8b
            if (r3 == 0) goto L8b
            if (r6 == 0) goto L8b
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Visible r10 = new com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Visible
            com.android.systemui.common.shared.model.Icon$Loaded r1 = new com.android.systemui.common.shared.model.Icon$Loaded
            com.android.systemui.common.shared.model.ContentDescription$Resource r3 = new com.android.systemui.common.shared.model.ContentDescription$Resource
            r7 = 2131951948(0x7f13014c, float:1.9540325E38)
            r3.<init>(r7)
            r1.<init>(r6, r3)
            r10.<init>(r1, r2, r4, r2)
            goto L8d
        L8b:
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Hidden r10 = com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE
        L8d:
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r1 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r1.<init>(r10)
            r9.L$0 = r2
            r9.L$1 = r2
            r9.L$2 = r2
            r9.label = r4
            java.lang.Object r9 = kotlinx.coroutines.flow.FlowKt.emitAll(r9, r1, r5)
            if (r9 != r0) goto La1
            return r0
        La1:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
