package com.android.systemui.qs.tiles.impl.irecording.domain.interactor;

import com.android.systemui.qs.tiles.impl.irecording.data.model.IssueRecordingModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class IssueRecordingDataInteractor$tileData$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ IssueRecordingDataInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IssueRecordingDataInteractor$tileData$2(IssueRecordingDataInteractor issueRecordingDataInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = issueRecordingDataInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        IssueRecordingDataInteractor$tileData$2 issueRecordingDataInteractor$tileData$2 = new IssueRecordingDataInteractor$tileData$2(this.this$0, continuation);
        issueRecordingDataInteractor$tileData$2.L$0 = obj;
        return issueRecordingDataInteractor$tileData$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((IssueRecordingDataInteractor$tileData$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            IssueRecordingModel m2914boximpl = IssueRecordingModel.m2914boximpl(this.this$0.state.isRecording);
            this.label = 1;
            if (flowCollector.emit(m2914boximpl, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
