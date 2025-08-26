package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.FlowCollector;

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
        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
        DeviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1 deviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1 = new DeviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1((Continuation) obj4);
        deviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1.L$0 = (FlowCollector) obj;
        deviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1.L$1 = (FingerprintSensorType) obj2;
        deviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1.Z$0 = zBooleanValue;
        return deviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0037, code lost:
    
        if (r8.emit(r1, r7) == r0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0044, code lost:
    
        if (r8.emit(r1, r7) == r0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0046, code lost:
    
        return r0;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            FingerprintSensorType fingerprintSensorType = (FingerprintSensorType) this.L$1;
            boolean z = this.Z$0;
            if (fingerprintSensorType == FingerprintSensorType.POWER_BUTTON) {
                Boolean boolValueOf = Boolean.valueOf(z);
                this.L$0 = null;
                this.label = 1;
            } else {
                Boolean bool = Boolean.FALSE;
                this.L$0 = null;
                this.label = 2;
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
