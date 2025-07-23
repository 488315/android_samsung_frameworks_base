package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class VideoCameraQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VideoCameraQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VideoCameraQuickAffordanceConfig$lockScreenState$1(VideoCameraQuickAffordanceConfig videoCameraQuickAffordanceConfig, Continuation continuation) {
        super(2, continuation);
        this.this$0 = videoCameraQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VideoCameraQuickAffordanceConfig$lockScreenState$1 videoCameraQuickAffordanceConfig$lockScreenState$1 = new VideoCameraQuickAffordanceConfig$lockScreenState$1(this.this$0, continuation);
        videoCameraQuickAffordanceConfig$lockScreenState$1.L$0 = obj;
        return videoCameraQuickAffordanceConfig$lockScreenState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VideoCameraQuickAffordanceConfig$lockScreenState$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0060, code lost:
    
        if (r1.emit(r8, r7) == r0) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0062, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0034, code lost:
    
        if (r8 == r0) goto L19;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 1
            r3 = 2
            if (r1 == 0) goto L20
            if (r1 == r2) goto L18
            if (r1 != r3) goto L10
            kotlin.ResultKt.throwOnFailure(r8)
            goto L63
        L10:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L18:
            java.lang.Object r1 = r7.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L37
        L20:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            r1 = r8
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig r8 = r7.this$0
            r7.L$0 = r1
            r7.label = r2
            int r2 = com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig.$r8$clinit
            java.lang.Object r8 = r8.isLaunchable$1(r7)
            if (r8 != r0) goto L37
            goto L62
        L37:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            r2 = 0
            if (r8 == 0) goto L56
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Visible r8 = new com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Visible
            com.android.systemui.common.shared.model.Icon$Resource r4 = new com.android.systemui.common.shared.model.Icon$Resource
            com.android.systemui.common.shared.model.ContentDescription$Resource r5 = new com.android.systemui.common.shared.model.ContentDescription$Resource
            r6 = 2131957374(0x7f13167e, float:1.955133E38)
            r5.<init>(r6)
            r6 = 2131233674(0x7f080b8a, float:1.8083492E38)
            r4.<init>(r6, r5)
            r8.<init>(r4, r2, r3, r2)
            goto L58
        L56:
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Hidden r8 = com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE
        L58:
            r7.L$0 = r2
            r7.label = r3
            java.lang.Object r7 = r1.emit(r8, r7)
            if (r7 != r0) goto L63
        L62:
            return r0
        L63:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.VideoCameraQuickAffordanceConfig$lockScreenState$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
