package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0037, code lost:
    
        if (r8.emit(r1, r7) == r0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0046, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0044, code lost:
    
        if (r8.emit(r1, r7) == r0) goto L17;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L19
            if (r1 == r3) goto L15
            if (r1 != r2) goto Ld
            goto L15
        Ld:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L15:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L47
        L19:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
            java.lang.Object r1 = r7.L$1
            com.android.systemui.biometrics.shared.model.FingerprintSensorType r1 = (com.android.systemui.biometrics.shared.model.FingerprintSensorType) r1
            boolean r4 = r7.Z$0
            com.android.systemui.biometrics.shared.model.FingerprintSensorType r5 = com.android.systemui.biometrics.shared.model.FingerprintSensorType.POWER_BUTTON
            r6 = 0
            if (r1 != r5) goto L3a
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r4)
            r7.L$0 = r6
            r7.label = r3
            java.lang.Object r7 = r8.emit(r1, r7)
            if (r7 != r0) goto L47
            goto L46
        L3a:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            r7.L$0 = r6
            r7.label = r2
            java.lang.Object r7 = r8.emit(r1, r7)
            if (r7 != r0) goto L47
        L46:
            return r0
        L47:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor$powerButtonSideFpsEnrolled$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
