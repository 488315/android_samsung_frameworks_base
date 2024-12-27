package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CellularIconViewModel$isVisible$1 extends SuspendLambda implements Function6 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    /* synthetic */ boolean Z$4;
    int label;
    final /* synthetic */ CellularIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CellularIconViewModel$isVisible$1(CellularIconViewModel cellularIconViewModel, Continuation continuation) {
        super(6, continuation);
        this.this$0 = cellularIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        boolean booleanValue4 = ((Boolean) obj4).booleanValue();
        boolean booleanValue5 = ((Boolean) obj5).booleanValue();
        CellularIconViewModel$isVisible$1 cellularIconViewModel$isVisible$1 = new CellularIconViewModel$isVisible$1(this.this$0, (Continuation) obj6);
        cellularIconViewModel$isVisible$1.Z$0 = booleanValue;
        cellularIconViewModel$isVisible$1.Z$1 = booleanValue2;
        cellularIconViewModel$isVisible$1.Z$2 = booleanValue3;
        cellularIconViewModel$isVisible$1.Z$3 = booleanValue4;
        cellularIconViewModel$isVisible$1.Z$4 = booleanValue5;
        return cellularIconViewModel$isVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002b, code lost:
    
        if (r2 != false) goto L18;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r5.label
            if (r0 != 0) goto L32
            kotlin.ResultKt.throwOnFailure(r6)
            boolean r6 = r5.Z$0
            boolean r0 = r5.Z$1
            boolean r1 = r5.Z$2
            boolean r2 = r5.Z$3
            boolean r3 = r5.Z$4
            r4 = 0
            if (r1 == 0) goto L18
        L16:
            r0 = r4
            goto L2d
        L18:
            if (r6 == 0) goto L1b
            goto L2d
        L1b:
            com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel r5 = r5.this$0
            int r5 = r5.slotId
            r0 = 1
            if (r5 == 0) goto L23
            goto L2b
        L23:
            if (r3 == 0) goto L2a
            if (r2 == 0) goto L28
            goto L2a
        L28:
            r2 = r4
            goto L2b
        L2a:
            r2 = r0
        L2b:
            if (r2 == 0) goto L16
        L2d:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r0)
            return r5
        L32:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$isVisible$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
