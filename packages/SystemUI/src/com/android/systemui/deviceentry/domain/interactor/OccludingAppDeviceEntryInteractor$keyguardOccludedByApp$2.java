package com.android.systemui.deviceentry.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class OccludingAppDeviceEntryInteractor$keyguardOccludedByApp$2 extends SuspendLambda implements Function6 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    /* synthetic */ boolean Z$4;
    int label;

    public OccludingAppDeviceEntryInteractor$keyguardOccludedByApp$2(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        boolean booleanValue4 = ((Boolean) obj4).booleanValue();
        boolean booleanValue5 = ((Boolean) obj5).booleanValue();
        OccludingAppDeviceEntryInteractor$keyguardOccludedByApp$2 occludingAppDeviceEntryInteractor$keyguardOccludedByApp$2 = new OccludingAppDeviceEntryInteractor$keyguardOccludedByApp$2((Continuation) obj6);
        occludingAppDeviceEntryInteractor$keyguardOccludedByApp$2.Z$0 = booleanValue;
        occludingAppDeviceEntryInteractor$keyguardOccludedByApp$2.Z$1 = booleanValue2;
        occludingAppDeviceEntryInteractor$keyguardOccludedByApp$2.Z$2 = booleanValue3;
        occludingAppDeviceEntryInteractor$keyguardOccludedByApp$2.Z$3 = booleanValue4;
        occludingAppDeviceEntryInteractor$keyguardOccludedByApp$2.Z$4 = booleanValue5;
        return occludingAppDeviceEntryInteractor$keyguardOccludedByApp$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf((!this.Z$0 || !this.Z$1 || this.Z$2 || this.Z$3 || this.Z$4) ? false : true);
    }
}
