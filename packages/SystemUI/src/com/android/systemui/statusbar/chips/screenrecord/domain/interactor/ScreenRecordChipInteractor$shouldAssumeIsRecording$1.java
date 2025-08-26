package com.android.systemui.statusbar.chips.screenrecord.domain.interactor;

import com.android.systemui.screenrecord.data.model.ScreenRecordModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x003f, code lost:
    
        if (r1.emit(r10, r9) == r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0063, code lost:
    
        if (r1.emit(r10, r9) != r0) goto L27;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    flowCollector = (FlowCollector) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    Boolean bool = Boolean.TRUE;
                    this.L$0 = null;
                    this.label = 3;
                } else if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        flowCollector = (FlowCollector) this.L$0;
        ScreenRecordModel screenRecordModel = (ScreenRecordModel) this.L$1;
        if (!(screenRecordModel instanceof ScreenRecordModel.DoingNothing)) {
            if (!(screenRecordModel instanceof ScreenRecordModel.Starting)) {
                if (!(screenRecordModel instanceof ScreenRecordModel.Recording)) {
                    throw new NoWhenBranchMatchedException();
                }
                return Unit.INSTANCE;
            }
            long j = ((ScreenRecordModel.Starting) screenRecordModel).millisUntilStarted - 50;
            this.L$0 = flowCollector;
            this.label = 2;
            if (DelayKt.delay(j, this) != coroutineSingletons) {
                Boolean bool2 = Boolean.TRUE;
                this.L$0 = null;
                this.label = 3;
            }
            return coroutineSingletons;
        }
        Boolean bool3 = Boolean.FALSE;
        this.L$0 = null;
        this.label = 1;
    }
}
