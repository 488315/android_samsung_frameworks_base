package com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModel$vmProvider$1", m277f = "MobileIconViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileIconViewModel$vmProvider$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ MobileIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconViewModel$vmProvider$1(MobileIconViewModel mobileIconViewModel, Continuation<? super MobileIconViewModel$vmProvider$1> continuation) {
        super(2, continuation);
        this.this$0 = mobileIconViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileIconViewModel$vmProvider$1 mobileIconViewModel$vmProvider$1 = new MobileIconViewModel$vmProvider$1(this.this$0, continuation);
        mobileIconViewModel$vmProvider$1.Z$0 = ((Boolean) obj).booleanValue();
        return mobileIconViewModel$vmProvider$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileIconViewModel$vmProvider$1) create(Boolean.valueOf(((Boolean) obj).booleanValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.Z$0 ? (CarrierBasedSatelliteViewModelImpl) this.this$0.satelliteProvider$delegate.getValue() : (CellularIconViewModel) this.this$0.cellProvider$delegate.getValue();
    }
}
