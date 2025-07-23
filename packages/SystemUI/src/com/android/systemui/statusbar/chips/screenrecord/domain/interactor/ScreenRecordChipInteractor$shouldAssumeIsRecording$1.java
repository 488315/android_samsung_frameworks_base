package com.android.systemui.statusbar.chips.screenrecord.domain.interactor;

import com.android.systemui.screenrecord.data.model.ScreenRecordModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ScreenRecordChipInteractor$shouldAssumeIsRecording$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public ScreenRecordChipInteractor$shouldAssumeIsRecording$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ScreenRecordChipInteractor$shouldAssumeIsRecording$1 screenRecordChipInteractor$shouldAssumeIsRecording$1 = new ScreenRecordChipInteractor$shouldAssumeIsRecording$1((Continuation) obj3);
        screenRecordChipInteractor$shouldAssumeIsRecording$1.L$0 = (FlowCollector) obj;
        screenRecordChipInteractor$shouldAssumeIsRecording$1.L$1 = (ScreenRecordModel) obj2;
        return screenRecordChipInteractor$shouldAssumeIsRecording$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0063, code lost:
    
        if (r1.emit(r10, r9) == r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x003f, code lost:
    
        if (r1.emit(r10, r9) == r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0056, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r5, r9) == r0) goto L24;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 0
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == 0) goto L25
            if (r1 == r5) goto L21
            if (r1 == r4) goto L19
            if (r1 != r3) goto L11
            goto L21
        L11:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L19:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L59
        L21:
            kotlin.ResultKt.throwOnFailure(r10)
            goto L6a
        L25:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            r1 = r10
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            java.lang.Object r10 = r9.L$1
            com.android.systemui.screenrecord.data.model.ScreenRecordModel r10 = (com.android.systemui.screenrecord.data.model.ScreenRecordModel) r10
            boolean r6 = r10 instanceof com.android.systemui.screenrecord.data.model.ScreenRecordModel.DoingNothing
            if (r6 == 0) goto L42
            java.lang.Boolean r10 = java.lang.Boolean.FALSE
            r9.L$0 = r2
            r9.label = r5
            java.lang.Object r9 = r1.emit(r10, r9)
            if (r9 != r0) goto L6a
            goto L65
        L42:
            boolean r5 = r10 instanceof com.android.systemui.screenrecord.data.model.ScreenRecordModel.Starting
            if (r5 == 0) goto L66
            com.android.systemui.screenrecord.data.model.ScreenRecordModel$Starting r10 = (com.android.systemui.screenrecord.data.model.ScreenRecordModel.Starting) r10
            long r5 = r10.millisUntilStarted
            r10 = 50
            long r7 = (long) r10
            long r5 = r5 - r7
            r9.L$0 = r1
            r9.label = r4
            java.lang.Object r10 = kotlinx.coroutines.DelayKt.delay(r5, r9)
            if (r10 != r0) goto L59
            goto L65
        L59:
            java.lang.Boolean r10 = java.lang.Boolean.TRUE
            r9.L$0 = r2
            r9.label = r3
            java.lang.Object r9 = r1.emit(r10, r9)
            if (r9 != r0) goto L6a
        L65:
            return r0
        L66:
            boolean r9 = r10 instanceof com.android.systemui.screenrecord.data.model.ScreenRecordModel.Recording
            if (r9 == 0) goto L6d
        L6a:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        L6d:
            kotlin.NoWhenBranchMatchedException r9 = new kotlin.NoWhenBranchMatchedException
            r9.<init>()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.screenrecord.domain.interactor.ScreenRecordChipInteractor$shouldAssumeIsRecording$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
