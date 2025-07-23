package com.android.systemui.inputdevice.tutorial.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TutorialSchedulerInteractor$touchpadScheduleFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TutorialSchedulerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TutorialSchedulerInteractor$touchpadScheduleFlow$1(TutorialSchedulerInteractor tutorialSchedulerInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = tutorialSchedulerInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TutorialSchedulerInteractor$touchpadScheduleFlow$1 tutorialSchedulerInteractor$touchpadScheduleFlow$1 = new TutorialSchedulerInteractor$touchpadScheduleFlow$1(this.this$0, continuation);
        tutorialSchedulerInteractor$touchpadScheduleFlow$1.L$0 = obj;
        return tutorialSchedulerInteractor$touchpadScheduleFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TutorialSchedulerInteractor$touchpadScheduleFlow$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0068, code lost:
    
        if (r1.emit(r8, r7) == r0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x005a, code lost:
    
        if (com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.access$schedule(r8, r4, r7) == r0) goto L23;
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
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L2b
            if (r1 == r4) goto L23
            if (r1 == r3) goto L1b
            if (r1 != r2) goto L13
            kotlin.ResultKt.throwOnFailure(r8)
            goto L6b
        L13:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1b:
            java.lang.Object r1 = r7.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L5d
        L23:
            java.lang.Object r1 = r7.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L46
        L2b:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
            com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor r1 = r7.this$0
            com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository r1 = r1.repo
            com.android.systemui.inputdevice.tutorial.data.repository.DeviceType r5 = com.android.systemui.inputdevice.tutorial.data.repository.DeviceType.TOUCHPAD
            r7.L$0 = r8
            r7.label = r4
            java.lang.Object r1 = r1.isNotified(r5, r7)
            if (r1 != r0) goto L43
            goto L6a
        L43:
            r6 = r1
            r1 = r8
            r8 = r6
        L46:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L6b
            com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor r8 = r7.this$0
            com.android.systemui.inputdevice.tutorial.data.repository.DeviceType r4 = com.android.systemui.inputdevice.tutorial.data.repository.DeviceType.TOUCHPAD
            r7.L$0 = r1
            r7.label = r3
            java.lang.Object r8 = com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.access$schedule(r8, r4, r7)
            if (r8 != r0) goto L5d
            goto L6a
        L5d:
            com.android.systemui.inputdevice.tutorial.data.repository.DeviceType r8 = com.android.systemui.inputdevice.tutorial.data.repository.DeviceType.TOUCHPAD
            r3 = 0
            r7.L$0 = r3
            r7.label = r2
            java.lang.Object r7 = r1.emit(r8, r7)
            if (r7 != r0) goto L6b
        L6a:
            return r0
        L6b:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$touchpadScheduleFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
