package com.android.systemui.qs.tiles.impl.sensorprivacy.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SensorPrivacyToggleTileDataInteractor$availability$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SensorPrivacyToggleTileDataInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SensorPrivacyToggleTileDataInteractor$availability$1(SensorPrivacyToggleTileDataInteractor sensorPrivacyToggleTileDataInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sensorPrivacyToggleTileDataInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SensorPrivacyToggleTileDataInteractor$availability$1 sensorPrivacyToggleTileDataInteractor$availability$1 = new SensorPrivacyToggleTileDataInteractor$availability$1(this.this$0, continuation);
        sensorPrivacyToggleTileDataInteractor$availability$1.L$0 = obj;
        return sensorPrivacyToggleTileDataInteractor$availability$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SensorPrivacyToggleTileDataInteractor$availability$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0056, code lost:
    
        if (r1.emit(r7, r6) == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0058, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004b, code lost:
    
        if (r7 == r0) goto L18;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L21
            if (r1 == r4) goto L19
            if (r1 != r3) goto L11
            kotlin.ResultKt.throwOnFailure(r7)
            goto L59
        L11:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L19:
            java.lang.Object r1 = r6.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4e
        L21:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            r1 = r7
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            com.android.systemui.qs.tiles.impl.sensorprivacy.domain.interactor.SensorPrivacyToggleTileDataInteractor r7 = r6.this$0
            r6.L$0 = r1
            r6.label = r4
            com.android.systemui.statusbar.policy.IndividualSensorPrivacyController r4 = r7.privacyController
            com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl r4 = (com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl) r4
            android.hardware.SensorPrivacyManager r4 = r4.mSensorPrivacyManager
            int r5 = r7.sensorId
            boolean r4 = r4.supportsSensorToggle(r5)
            if (r4 == 0) goto L49
            com.android.systemui.qs.tiles.impl.sensorprivacy.domain.interactor.SensorPrivacyToggleTileDataInteractor$isSensorDeviceConfigSet$2 r4 = new com.android.systemui.qs.tiles.impl.sensorprivacy.domain.interactor.SensorPrivacyToggleTileDataInteractor$isSensorDeviceConfigSet$2
            r4.<init>(r7, r2)
            kotlin.coroutines.CoroutineContext r7 = r7.bgCoroutineContext
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r4, r6)
            goto L4b
        L49:
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
        L4b:
            if (r7 != r0) goto L4e
            goto L58
        L4e:
            r6.L$0 = r2
            r6.label = r3
            java.lang.Object r6 = r1.emit(r7, r6)
            if (r6 != r0) goto L59
        L58:
            return r0
        L59:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.sensorprivacy.domain.interactor.SensorPrivacyToggleTileDataInteractor$availability$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
