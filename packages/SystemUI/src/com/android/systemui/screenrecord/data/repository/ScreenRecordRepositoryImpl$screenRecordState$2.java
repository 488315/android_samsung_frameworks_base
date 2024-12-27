package com.android.systemui.screenrecord.data.repository;

import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.data.model.ScreenRecordModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class ScreenRecordRepositoryImpl$screenRecordState$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ScreenRecordRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenRecordRepositoryImpl$screenRecordState$2(ScreenRecordRepositoryImpl screenRecordRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenRecordRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScreenRecordRepositoryImpl$screenRecordState$2 screenRecordRepositoryImpl$screenRecordState$2 = new ScreenRecordRepositoryImpl$screenRecordState$2(this.this$0, continuation);
        screenRecordRepositoryImpl$screenRecordState$2.L$0 = obj;
        return screenRecordRepositoryImpl$screenRecordState$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenRecordRepositoryImpl$screenRecordState$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            RecordingController recordingController = this.this$0.recordingController;
            Object starting = recordingController.isRecording() ? ScreenRecordModel.Recording.INSTANCE : recordingController.mIsStarting ? new ScreenRecordModel.Starting(0L) : ScreenRecordModel.DoingNothing.INSTANCE;
            this.label = 1;
            if (flowCollector.emit(starting, this) == coroutineSingletons) {
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
