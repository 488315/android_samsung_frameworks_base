package com.android.systemui.statusbar.phone;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.phone.CarrierHomeLogoViewController$simStateChanged$1", m277f = "CarrierHomeLogoViewController.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class CarrierHomeLogoViewController$simStateChanged$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CarrierHomeLogoViewController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CarrierHomeLogoViewController$simStateChanged$1(CarrierHomeLogoViewController carrierHomeLogoViewController, Continuation<? super CarrierHomeLogoViewController$simStateChanged$1> continuation) {
        super(2, continuation);
        this.this$0 = carrierHomeLogoViewController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CarrierHomeLogoViewController$simStateChanged$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CarrierHomeLogoViewController$simStateChanged$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CarrierHomeLogoViewController.access$updateSimTypes(this.this$0);
        return Unit.INSTANCE;
    }
}
