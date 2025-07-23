package com.android.systemui.display.domain.interactor;

import com.android.systemui.display.data.repository.DeviceStateRepository;
import java.util.Set;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class RearDisplayStateInteractorImpl$state$1 extends SuspendLambda implements Function4 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    public RearDisplayStateInteractorImpl$state$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        RearDisplayStateInteractorImpl$state$1 rearDisplayStateInteractorImpl$state$1 = new RearDisplayStateInteractorImpl$state$1((Continuation) obj4);
        rearDisplayStateInteractorImpl$state$1.L$0 = (FlowCollector) obj;
        rearDisplayStateInteractorImpl$state$1.L$1 = (DeviceStateRepository.DeviceState) obj2;
        rearDisplayStateInteractorImpl$state$1.L$2 = (Set) obj3;
        return rearDisplayStateInteractorImpl$state$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0058, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006e, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x006c, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L25;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L19
            if (r1 == r3) goto L15
            if (r1 != r2) goto Ld
            goto L15
        Ld:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L15:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L6f
        L19:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
            java.lang.Object r1 = r8.L$1
            com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r1 = (com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState) r1
            java.lang.Object r4 = r8.L$2
            java.util.Set r4 = (java.util.Set) r4
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            java.util.Iterator r4 = r4.iterator()
        L2e:
            boolean r5 = r4.hasNext()
            r6 = 0
            if (r5 == 0) goto L45
            java.lang.Object r5 = r4.next()
            r7 = r5
            android.view.Display r7 = (android.view.Display) r7
            int r7 = r7.getFlags()
            r7 = r7 & 8192(0x2000, float:1.148E-41)
            if (r7 == 0) goto L2e
            goto L46
        L45:
            r5 = r6
        L46:
            android.view.Display r5 = (android.view.Display) r5
            com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r4 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.REAR_DISPLAY_OUTER_DEFAULT
            if (r1 == r4) goto L5b
            com.android.systemui.display.domain.interactor.RearDisplayStateInteractor$State$Disabled r1 = com.android.systemui.display.domain.interactor.RearDisplayStateInteractor.State.Disabled.INSTANCE
            r8.L$0 = r6
            r8.L$1 = r6
            r8.label = r3
            java.lang.Object r8 = r9.emit(r1, r8)
            if (r8 != r0) goto L6f
            goto L6e
        L5b:
            if (r5 == 0) goto L6f
            com.android.systemui.display.domain.interactor.RearDisplayStateInteractor$State$Enabled r1 = new com.android.systemui.display.domain.interactor.RearDisplayStateInteractor$State$Enabled
            r1.<init>(r5)
            r8.L$0 = r6
            r8.L$1 = r6
            r8.label = r2
            java.lang.Object r8 = r9.emit(r1, r8)
            if (r8 != r0) goto L6f
        L6e:
            return r0
        L6f:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.domain.interactor.RearDisplayStateInteractorImpl$state$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
