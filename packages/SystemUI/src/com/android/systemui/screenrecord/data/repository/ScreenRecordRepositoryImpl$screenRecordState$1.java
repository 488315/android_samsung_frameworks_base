package com.android.systemui.screenrecord.data.repository;

import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.data.model.ScreenRecordModel;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class ScreenRecordRepositoryImpl$screenRecordState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ScreenRecordRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenRecordRepositoryImpl$screenRecordState$1(ScreenRecordRepositoryImpl screenRecordRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenRecordRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScreenRecordRepositoryImpl$screenRecordState$1 screenRecordRepositoryImpl$screenRecordState$1 = new ScreenRecordRepositoryImpl$screenRecordState$1(this.this$0, continuation);
        screenRecordRepositoryImpl$screenRecordState$1.L$0 = obj;
        return screenRecordRepositoryImpl$screenRecordState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenRecordRepositoryImpl$screenRecordState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.screenrecord.data.repository.ScreenRecordRepositoryImpl$screenRecordState$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ScreenRecordRepositoryImpl screenRecordRepositoryImpl = this.this$0;
            final ?? r1 = new RecordingController.RecordingStateChangeCallback() { // from class: com.android.systemui.screenrecord.data.repository.ScreenRecordRepositoryImpl$screenRecordState$1$callback$1
                @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
                public final void onCountdown(long j) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(new ScreenRecordModel.Starting(j));
                }

                @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
                public final void onCountdownEnd() {
                    ScreenRecordRepositoryImpl screenRecordRepositoryImpl2 = screenRecordRepositoryImpl;
                    if (screenRecordRepositoryImpl2.recordingController.isRecording() || screenRecordRepositoryImpl2.recordingController.mIsStarting) {
                        return;
                    }
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(ScreenRecordModel.DoingNothing.INSTANCE);
                }

                @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
                public final void onRecordingEnd() {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(ScreenRecordModel.DoingNothing.INSTANCE);
                }

                @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
                public final void onRecordingStart() {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(ScreenRecordModel.Recording.INSTANCE);
                }
            };
            this.this$0.recordingController.mListeners.add(r1);
            final ScreenRecordRepositoryImpl screenRecordRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.screenrecord.data.repository.ScreenRecordRepositoryImpl$screenRecordState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    RecordingController recordingController = ScreenRecordRepositoryImpl.this.recordingController;
                    recordingController.mListeners.remove(r1);
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
