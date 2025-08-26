package com.android.systemui.mediaprojection.data.repository;

import android.media.projection.MediaProjectionInfo;
import android.view.ContentRecordingSession;
import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class MediaProjectionManagerRepository$mediaProjectionState$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaProjectionManagerRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaProjectionManagerRepository$mediaProjectionState$2(MediaProjectionManagerRepository mediaProjectionManagerRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaProjectionManagerRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaProjectionManagerRepository$mediaProjectionState$2 mediaProjectionManagerRepository$mediaProjectionState$2 = new MediaProjectionManagerRepository$mediaProjectionState$2(this.this$0, continuation);
        mediaProjectionManagerRepository$mediaProjectionState$2.L$0 = obj;
        return mediaProjectionManagerRepository$mediaProjectionState$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaProjectionManagerRepository$mediaProjectionState$2) create((MediaProjectionManagerRepository.CallbackEvent) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MediaProjectionManagerRepository.CallbackEvent callbackEvent = (MediaProjectionManagerRepository.CallbackEvent) this.L$0;
            if (callbackEvent instanceof MediaProjectionManagerRepository.CallbackEvent.OnStart) {
                MediaProjectionInfo mediaProjectionInfo = ((MediaProjectionManagerRepository.CallbackEvent.OnStart) callbackEvent).info;
                return mediaProjectionInfo != null ? new MediaProjectionState.Projecting.NoScreen(mediaProjectionInfo.getPackageName(), null, 2, null) : MediaProjectionState.NotProjecting.INSTANCE;
            }
            if (callbackEvent instanceof MediaProjectionManagerRepository.CallbackEvent.OnStop) {
                return MediaProjectionState.NotProjecting.INSTANCE;
            }
            if (!(callbackEvent instanceof MediaProjectionManagerRepository.CallbackEvent.OnRecordingSessionSet)) {
                if (callbackEvent instanceof MediaProjectionManagerRepository.CallbackEvent.OnMediaProjectionEvent) {
                    throw new IllegalStateException("Unexpected OnMediaProjectionEvent in mediaProjectionState flow. It should have been filtered out.");
                }
                throw new NoWhenBranchMatchedException();
            }
            MediaProjectionManagerRepository mediaProjectionManagerRepository = this.this$0;
            MediaProjectionManagerRepository.CallbackEvent.OnRecordingSessionSet onRecordingSessionSet = (MediaProjectionManagerRepository.CallbackEvent.OnRecordingSessionSet) callbackEvent;
            MediaProjectionInfo mediaProjectionInfo2 = onRecordingSessionSet.info;
            ContentRecordingSession contentRecordingSession = onRecordingSessionSet.session;
            this.label = 1;
            obj = MediaProjectionManagerRepository.access$stateForSession(mediaProjectionManagerRepository, mediaProjectionInfo2, contentRecordingSession, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return (MediaProjectionState) obj;
    }
}
