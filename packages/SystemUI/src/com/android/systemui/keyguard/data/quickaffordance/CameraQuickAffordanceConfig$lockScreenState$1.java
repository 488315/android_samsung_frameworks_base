package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CameraQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CameraQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CameraQuickAffordanceConfig$lockScreenState$1(CameraQuickAffordanceConfig cameraQuickAffordanceConfig, Continuation continuation) {
        super(2, continuation);
        this.this$0 = cameraQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CameraQuickAffordanceConfig$lockScreenState$1 cameraQuickAffordanceConfig$lockScreenState$1 = new CameraQuickAffordanceConfig$lockScreenState$1(this.this$0, continuation);
        cameraQuickAffordanceConfig$lockScreenState$1.L$0 = obj;
        return cameraQuickAffordanceConfig$lockScreenState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CameraQuickAffordanceConfig$lockScreenState$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0074, code lost:
    
        if (r1.emit(r8, r7) == r0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0076, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0049, code lost:
    
        if (r8 == r0) goto L22;
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
            r2 = 0
            r3 = 1
            r4 = 2
            if (r1 == 0) goto L21
            if (r1 == r3) goto L19
            if (r1 != r4) goto L11
            kotlin.ResultKt.throwOnFailure(r8)
            goto L77
        L11:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L19:
            java.lang.Object r1 = r7.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L4c
        L21:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            r1 = r8
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig r8 = r7.this$0
            r7.L$0 = r1
            r7.label = r3
            int r3 = com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig.$r8$clinit
            android.content.pm.PackageManager r3 = r8.packageManager
            java.lang.String r5 = "android.hardware.camera.any"
            boolean r3 = r3.hasSystemFeature(r5)
            if (r3 == 0) goto L47
            com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig$isLaunchable$2 r3 = new com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig$isLaunchable$2
            r3.<init>(r8, r2)
            kotlinx.coroutines.CoroutineDispatcher r8 = r8.backgroundDispatcher
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r8, r3, r7)
            goto L49
        L47:
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
        L49:
            if (r8 != r0) goto L4c
            goto L76
        L4c:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L6a
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Visible r8 = new com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Visible
            com.android.systemui.common.shared.model.Icon$Resource r3 = new com.android.systemui.common.shared.model.Icon$Resource
            com.android.systemui.common.shared.model.ContentDescription$Resource r5 = new com.android.systemui.common.shared.model.ContentDescription$Resource
            r6 = 2131951722(0x7f13006a, float:1.9539867E38)
            r5.<init>(r6)
            r6 = 2131232870(0x7f080866, float:1.8081861E38)
            r3.<init>(r6, r5)
            r8.<init>(r3, r2, r4, r2)
            goto L6c
        L6a:
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig$LockScreenState$Hidden r8 = com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE
        L6c:
            r7.L$0 = r2
            r7.label = r4
            java.lang.Object r7 = r1.emit(r8, r7)
            if (r7 != r0) goto L77
        L76:
            return r0
        L77:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig$lockScreenState$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
