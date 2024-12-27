package com.android.systemui.shade.data.repository;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

final class SecPanelExpansionStateRepository$panelState$1 extends SuspendLambda implements Function5 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    int label;

    public SecPanelExpansionStateRepository$panelState$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        float floatValue = ((Number) obj).floatValue();
        float floatValue2 = ((Number) obj2).floatValue();
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        int intValue = ((Number) obj4).intValue();
        SecPanelExpansionStateRepository$panelState$1 secPanelExpansionStateRepository$panelState$1 = new SecPanelExpansionStateRepository$panelState$1((Continuation) obj5);
        secPanelExpansionStateRepository$panelState$1.F$0 = floatValue;
        secPanelExpansionStateRepository$panelState$1.F$1 = floatValue2;
        secPanelExpansionStateRepository$panelState$1.Z$0 = booleanValue;
        secPanelExpansionStateRepository$panelState$1.I$0 = intValue;
        return secPanelExpansionStateRepository$panelState$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0025, code lost:
    
        if (r7 == 1.0f) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0027, code lost:
    
        r2 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0029, code lost:
    
        r2 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002d, code lost:
    
        if (r7 < 1.0f) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0037, code lost:
    
        if (r0 == 1.0f) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r6.label
            if (r0 != 0) goto L40
            kotlin.ResultKt.throwOnFailure(r7)
            float r7 = r6.F$0
            float r0 = r6.F$1
            boolean r1 = r6.Z$0
            int r6 = r6.I$0
            r2 = 0
            if (r1 == 0) goto L15
            goto L3a
        L15:
            r1 = 0
            r3 = 1065353216(0x3f800000, float:1.0)
            r4 = 2
            r5 = 1
            if (r6 == r5) goto L30
            if (r6 == r4) goto L2b
            int r6 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r6 != 0) goto L23
            goto L3a
        L23:
            int r6 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r6 != 0) goto L29
        L27:
            r2 = r4
            goto L3a
        L29:
            r2 = r5
            goto L3a
        L2b:
            int r6 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r6 >= 0) goto L27
            goto L29
        L30:
            int r6 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r6 != 0) goto L35
            goto L3a
        L35:
            int r6 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r6 != 0) goto L29
            goto L27
        L3a:
            java.lang.Integer r6 = new java.lang.Integer
            r6.<init>(r2)
            return r6
        L40:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.data.repository.SecPanelExpansionStateRepository$panelState$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
