package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

final class MobileIconInteractorImpl$roamingId$1 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    public MobileIconInteractorImpl$roamingId$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(6, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        MobileIconInteractorImpl$roamingId$1 mobileIconInteractorImpl$roamingId$1 = new MobileIconInteractorImpl$roamingId$1(this.this$0, (Continuation) obj6);
        mobileIconInteractorImpl$roamingId$1.Z$0 = booleanValue;
        mobileIconInteractorImpl$roamingId$1.Z$1 = booleanValue2;
        mobileIconInteractorImpl$roamingId$1.L$0 = (MobileServiceState) obj3;
        mobileIconInteractorImpl$roamingId$1.Z$2 = booleanValue3;
        return mobileIconInteractorImpl$roamingId$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0089, code lost:
    
        if (r11.substring(0, 3).equals(r12.substring(0, 3)) != false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x008b, code lost:
    
        r11 = true;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            Method dump skipped, instructions count: 298
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$roamingId$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
