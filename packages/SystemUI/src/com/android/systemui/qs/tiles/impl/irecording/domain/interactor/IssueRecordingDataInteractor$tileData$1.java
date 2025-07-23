package com.android.systemui.qs.tiles.impl.irecording.domain.interactor;

import com.android.systemui.qs.tiles.impl.irecording.data.model.IssueRecordingModel;
import com.android.systemui.recordissue.IssueRecordingState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class IssueRecordingDataInteractor$tileData$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ IssueRecordingDataInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IssueRecordingDataInteractor$tileData$1(IssueRecordingDataInteractor issueRecordingDataInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = issueRecordingDataInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        IssueRecordingDataInteractor$tileData$1 issueRecordingDataInteractor$tileData$1 = new IssueRecordingDataInteractor$tileData$1(this.this$0, continuation);
        issueRecordingDataInteractor$tileData$1.L$0 = obj;
        return issueRecordingDataInteractor$tileData$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((IssueRecordingDataInteractor$tileData$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.tiles.impl.irecording.domain.interactor.IssueRecordingDataInteractor$tileData$1$listener$1, java.lang.Runnable] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final IssueRecordingDataInteractor issueRecordingDataInteractor = this.this$0;
            final ?? r1 = new Runnable() { // from class: com.android.systemui.qs.tiles.impl.irecording.domain.interactor.IssueRecordingDataInteractor$tileData$1$listener$1
                @Override // java.lang.Runnable
                public final void run() {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(IssueRecordingModel.m2914boximpl(issueRecordingDataInteractor.state.isRecording));
                }
            };
            this.this$0.state.addListener(r1);
            final IssueRecordingDataInteractor issueRecordingDataInteractor2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.irecording.domain.interactor.IssueRecordingDataInteractor$tileData$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    IssueRecordingDataInteractor$tileData$1$listener$1 issueRecordingDataInteractor$tileData$1$listener$1 = r1;
                    IssueRecordingState issueRecordingState = IssueRecordingDataInteractor.this.state;
                    issueRecordingState.listeners.remove(issueRecordingDataInteractor$tileData$1$listener$1);
                    if (issueRecordingState.listeners.isEmpty()) {
                        issueRecordingState.resolver.unregisterContentObserver(issueRecordingState.onRecordingChangeListener);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
