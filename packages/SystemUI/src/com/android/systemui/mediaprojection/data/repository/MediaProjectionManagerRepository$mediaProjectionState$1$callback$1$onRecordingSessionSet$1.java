package com.android.systemui.mediaprojection.data.repository;

import android.media.projection.MediaProjectionInfo;
import android.view.ContentRecordingSession;
import com.android.systemui.common.coroutine.ChannelExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class MediaProjectionManagerRepository$mediaProjectionState$1$callback$1$onRecordingSessionSet$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
    final /* synthetic */ MediaProjectionInfo $info;
    final /* synthetic */ ContentRecordingSession $session;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ MediaProjectionManagerRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaProjectionManagerRepository$mediaProjectionState$1$callback$1$onRecordingSessionSet$1(ProducerScope producerScope, MediaProjectionManagerRepository mediaProjectionManagerRepository, MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession, Continuation continuation) {
        super(2, continuation);
        this.$$this$conflatedCallbackFlow = producerScope;
        this.this$0 = mediaProjectionManagerRepository;
        this.$info = mediaProjectionInfo;
        this.$session = contentRecordingSession;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaProjectionManagerRepository$mediaProjectionState$1$callback$1$onRecordingSessionSet$1(this.$$this$conflatedCallbackFlow, this.this$0, this.$info, this.$session, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaProjectionManagerRepository$mediaProjectionState$1$callback$1$onRecordingSessionSet$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SendChannel sendChannel;
        ChannelExt channelExt;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ChannelExt channelExt2 = ChannelExt.INSTANCE;
            ProducerScope producerScope = this.$$this$conflatedCallbackFlow;
            MediaProjectionManagerRepository mediaProjectionManagerRepository = this.this$0;
            MediaProjectionInfo mediaProjectionInfo = this.$info;
            ContentRecordingSession contentRecordingSession = this.$session;
            this.L$0 = channelExt2;
            this.L$1 = producerScope;
            this.label = 1;
            Object access$stateForSession = MediaProjectionManagerRepository.access$stateForSession(mediaProjectionManagerRepository, mediaProjectionInfo, contentRecordingSession, this);
            if (access$stateForSession == coroutineSingletons) {
                return coroutineSingletons;
            }
            sendChannel = producerScope;
            obj = access$stateForSession;
            channelExt = channelExt2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            sendChannel = (SendChannel) this.L$1;
            channelExt = (ChannelExt) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, obj, "MediaProjectionMngrRepo");
        return Unit.INSTANCE;
    }
}
