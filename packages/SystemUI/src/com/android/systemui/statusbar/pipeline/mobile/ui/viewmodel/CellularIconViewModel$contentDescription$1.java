package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class CellularIconViewModel$contentDescription$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CellularIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CellularIconViewModel$contentDescription$1(CellularIconViewModel cellularIconViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = cellularIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CellularIconViewModel$contentDescription$1 cellularIconViewModel$contentDescription$1 = new CellularIconViewModel$contentDescription$1(this.this$0, (Continuation) obj3);
        cellularIconViewModel$contentDescription$1.L$0 = (SignalIconModel) obj;
        cellularIconViewModel$contentDescription$1.L$1 = (NetworkNameModel) obj2;
        return cellularIconViewModel$contentDescription$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0039, code lost:
    
        if (r5 != 6) goto L27;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r4.label
            if (r0 != 0) goto L5b
            kotlin.ResultKt.throwOnFailure(r5)
            java.lang.Object r5 = r4.L$0
            com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel r5 = (com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel) r5
            java.lang.Object r0 = r4.L$1
            com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel r0 = (com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel) r0
            boolean r1 = r5 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel.Cellular
            if (r1 == 0) goto L59
            com.android.systemui.statusbar.pipeline.mobile.ui.model.MobileContentDescription$Cellular r1 = new com.android.systemui.statusbar.pipeline.mobile.ui.model.MobileContentDescription$Cellular
            java.lang.String r0 = r0.getName()
            com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel r4 = r4.this$0
            com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel$Cellular r5 = (com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel.Cellular) r5
            r4.getClass()
            int r4 = r5.level
            if (r4 == 0) goto L52
            r2 = 1
            if (r4 == r2) goto L4e
            r2 = 2
            if (r4 == r2) goto L4a
            r2 = 3
            if (r4 == r2) goto L46
            r2 = 4
            r3 = 6
            int r5 = r5.numberOfLevels
            if (r4 == r2) goto L3c
            r2 = 5
            if (r4 == r2) goto L39
            goto L52
        L39:
            if (r5 != r3) goto L52
            goto L42
        L3c:
            if (r5 != r3) goto L42
            r4 = 2131951814(0x7f1300c6, float:1.9540053E38)
            goto L55
        L42:
            r4 = 2131951946(0x7f13014a, float:1.954032E38)
            goto L55
        L46:
            r4 = 2131951970(0x7f130162, float:1.954037E38)
            goto L55
        L4a:
            r4 = 2131951973(0x7f130165, float:1.9540376E38)
            goto L55
        L4e:
            r4 = 2131951879(0x7f130107, float:1.9540185E38)
            goto L55
        L52:
            r4 = 2131951874(0x7f130102, float:1.9540175E38)
        L55:
            r1.<init>(r0, r4)
            return r1
        L59:
            r4 = 0
            return r4
        L5b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$contentDescription$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
