package com.android.systemui.mediaprojection.data.repository;

import android.media.projection.MediaProjectionInfo;
import android.media.projection.MediaProjectionManager;
import android.util.Log;
import android.view.ContentRecordingSession;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class MediaProjectionManagerRepository$mediaProjectionState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaProjectionManagerRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaProjectionManagerRepository$mediaProjectionState$1(MediaProjectionManagerRepository mediaProjectionManagerRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaProjectionManagerRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaProjectionManagerRepository$mediaProjectionState$1 mediaProjectionManagerRepository$mediaProjectionState$1 = new MediaProjectionManagerRepository$mediaProjectionState$1(this.this$0, continuation);
        mediaProjectionManagerRepository$mediaProjectionState$1.L$0 = obj;
        return mediaProjectionManagerRepository$mediaProjectionState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaProjectionManagerRepository$mediaProjectionState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.media.projection.MediaProjectionManager$Callback, com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$mediaProjectionState$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MediaProjectionManagerRepository mediaProjectionManagerRepository = this.this$0;
            final ?? r1 = new MediaProjectionManager.Callback() { // from class: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$mediaProjectionState$1$callback$1
                public final void onRecordingSessionSet(MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) {
                    Log.d("MediaProjectionMngrRepo", "MediaProjectionManager.Callback#onSessionStarted: " + contentRecordingSession);
                    ProducerScope producerScope2 = ProducerScope.this;
                    BuildersKt.launch$default(producerScope2, null, null, new MediaProjectionManagerRepository$mediaProjectionState$1$callback$1$onRecordingSessionSet$1(producerScope2, mediaProjectionManagerRepository, mediaProjectionInfo, contentRecordingSession, null), 3);
                }

                public final void onStart(MediaProjectionInfo mediaProjectionInfo) {
                    Log.d("MediaProjectionMngrRepo", "MediaProjectionManager.Callback#onStart");
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, MediaProjectionState.NotProjecting.INSTANCE, "MediaProjectionMngrRepo");
                }

                public final void onStop(MediaProjectionInfo mediaProjectionInfo) {
                    Log.d("MediaProjectionMngrRepo", "MediaProjectionManager.Callback#onStop");
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, MediaProjectionState.NotProjecting.INSTANCE, "MediaProjectionMngrRepo");
                }
            };
            MediaProjectionManagerRepository mediaProjectionManagerRepository2 = this.this$0;
            mediaProjectionManagerRepository2.mediaProjectionManager.addCallback(r1, mediaProjectionManagerRepository2.handler);
            final MediaProjectionManagerRepository mediaProjectionManagerRepository3 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$mediaProjectionState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    MediaProjectionManagerRepository.this.mediaProjectionManager.removeCallback(r1);
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
