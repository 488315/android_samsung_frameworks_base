package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ QuickAccessWalletKeyguardQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

    /* JADX WARN: Code restructure failed: missing block: B:18:0x00a1, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.emitAll(r5, r1, r14) != r0) goto L26;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r15) {
        /*
            r14 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r14.label
            r2 = 0
            r3 = 1
            r4 = 2
            if (r1 == 0) goto L2a
            if (r1 == r3) goto L1a
            if (r1 != r4) goto L12
            kotlin.ResultKt.throwOnFailure(r15)
            goto La4
        L12:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r15)
            throw r14
        L1a:
            java.lang.Object r1 = r14.L$2
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig r1 = (com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig) r1
            java.lang.Object r3 = r14.L$1
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            java.lang.Object r5 = r14.L$0
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            kotlin.ResultKt.throwOnFailure(r15)
            goto L5b
        L2a:
            kotlin.ResultKt.throwOnFailure(r15)
            java.lang.Object r15 = r14.L$0
            r5 = r15
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
            java.lang.Object r15 = r14.L$1
            java.lang.Boolean r15 = (java.lang.Boolean) r15
            if (r15 != 0) goto L3b
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Hidden r15 = com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE
            goto L90
        L3b:
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig r1 = r14.this$0
            r14.L$0 = r5
            r14.L$1 = r15
            r14.L$2 = r1
            r14.label = r3
            int r3 = com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig.$r8$clinit
            r1.getClass()
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$isWalletAvailable$2 r3 = new com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$isWalletAvailable$2
            r3.<init>(r1, r2)
            kotlinx.coroutines.CoroutineDispatcher r6 = r1.backgroundDispatcher
            java.lang.Object r3 = kotlinx.coroutines.BuildersKt.withContext(r6, r3, r14)
            if (r3 != r0) goto L58
            goto La3
        L58:
            r13 = r3
            r3 = r15
            r15 = r13
        L5b:
            java.lang.Boolean r15 = (java.lang.Boolean) r15
            boolean r15 = r15.booleanValue()
            boolean r3 = r3.booleanValue()
            com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig r6 = r14.this$0
            com.android.systemui.wallet.controller.QuickAccessWalletController r6 = r6.walletController
            android.service.quickaccesswallet.QuickAccessWalletClient r6 = r6.mQuickAccessWalletClient
            android.graphics.drawable.Drawable r8 = r6.getTileIcon()
            r1.getClass()
            if (r15 == 0) goto L8e
            if (r3 == 0) goto L8e
            if (r8 == 0) goto L8e
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Visible r15 = new com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Visible
            com.android.systemui.common.shared.model.Icon$Loaded r7 = new com.android.systemui.common.shared.model.Icon$Loaded
            com.android.systemui.common.shared.model.ContentDescription$Resource r9 = new com.android.systemui.common.shared.model.ContentDescription$Resource
            r1 = 2131951979(0x7f13016b, float:1.9540388E38)
            r9.<init>(r1)
            r11 = 4
            r12 = 0
            r10 = 0
            r7.<init>(r8, r9, r10, r11, r12)
            r15.<init>(r7, r2, r4, r2)
            goto L90
        L8e:
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Hidden r15 = com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE
        L90:
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r1 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r1.<init>(r15)
            r14.L$0 = r2
            r14.L$1 = r2
            r14.L$2 = r2
            r14.label = r4
            java.lang.Object r14 = kotlinx.coroutines.flow.FlowKt.emitAll(r5, r1, r14)
            if (r14 != r0) goto La4
        La3:
            return r0
        La4:
            kotlin.Unit r14 = kotlin.Unit.INSTANCE
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
