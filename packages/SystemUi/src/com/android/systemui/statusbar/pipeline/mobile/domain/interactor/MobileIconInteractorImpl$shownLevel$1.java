package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$shownLevel$1", m277f = "MobileIconInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconInteractorImpl$shownLevel$1 extends SuspendLambda implements Function5 {
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$shownLevel$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation<? super MobileIconInteractorImpl$shownLevel$1> continuation) {
        super(5, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int intValue = ((Number) obj).intValue();
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        MobileIconInteractorImpl$shownLevel$1 mobileIconInteractorImpl$shownLevel$1 = new MobileIconInteractorImpl$shownLevel$1(this.this$0, (Continuation) obj5);
        mobileIconInteractorImpl$shownLevel$1.I$0 = intValue;
        mobileIconInteractorImpl$shownLevel$1.Z$0 = booleanValue;
        mobileIconInteractorImpl$shownLevel$1.Z$1 = booleanValue2;
        mobileIconInteractorImpl$shownLevel$1.Z$2 = booleanValue3;
        return mobileIconInteractorImpl$shownLevel$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0026, code lost:
    
        if (r1 != false) goto L13;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        if (!z) {
            if (!z3) {
                MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
                if (mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.SIGNAL_BAR_WHEN_EMERGENCY, mobileIconInteractorImpl.slotId, new Object[0])) {
                }
            }
            i = 0;
        }
        return new Integer(i);
    }
}
