package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class DeviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1 extends SuspendLambda implements Function4 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    public DeviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        DeviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1 deviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1 = new DeviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1((Continuation) obj4);
        deviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1.L$0 = (FlowCollector) obj;
        deviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1.L$1 = (FingerprintSensorType) obj2;
        deviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1.Z$0 = booleanValue;
        return deviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            FingerprintSensorType fingerprintSensorType = (FingerprintSensorType) this.L$1;
            boolean z = this.Z$0;
            if (fingerprintSensorType == FingerprintSensorType.POWER_BUTTON) {
                Boolean valueOf = Boolean.valueOf(z);
                this.L$0 = null;
                this.label = 1;
                if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                Boolean bool = Boolean.FALSE;
                this.L$0 = null;
                this.label = 2;
                if (flowCollector.emit(bool, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1 && i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
