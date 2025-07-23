package com.android.systemui.mediaprojection.data.repository;

import android.media.projection.MediaProjectionEvent;
import android.media.projection.MediaProjectionInfo;
import android.media.projection.MediaProjectionManager;
import android.view.ContentRecordingSession;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaProjectionManagerRepository$callbackEventsFlow$1$callback$1 extends MediaProjectionManager.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
    public final /* synthetic */ MediaProjectionManagerRepository this$0;

    public MediaProjectionManagerRepository$callbackEventsFlow$1$callback$1(MediaProjectionManagerRepository mediaProjectionManagerRepository, ProducerScope producerScope) {
        this.this$0 = mediaProjectionManagerRepository;
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    public final void onMediaProjectionEvent(MediaProjectionEvent mediaProjectionEvent, MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) {
        LogBuffer logBuffer = this.this$0.logger;
        LogMessage obtain = logBuffer.obtain("MediaProjectionMngrRepo", LogLevel.DEBUG, new MediaProjectionManagerRepository$stopProjecting$2$$ExternalSyntheticLambda0(3), null);
        ((LogMessageImpl) obtain).str1 = mediaProjectionEvent.toString();
        logBuffer.commit(obtain);
        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, this.$$this$conflatedCallbackFlow, new MediaProjectionManagerRepository.CallbackEvent.OnMediaProjectionEvent(mediaProjectionEvent), "MediaProjectionMngrRepo");
    }

    public final void onRecordingSessionSet(MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) {
        LogBuffer logBuffer = this.this$0.logger;
        LogMessage obtain = logBuffer.obtain("MediaProjectionMngrRepo", LogLevel.DEBUG, new MediaProjectionManagerRepository$stopProjecting$2$$ExternalSyntheticLambda0(4), null);
        ((LogMessageImpl) obtain).str1 = String.valueOf(contentRecordingSession);
        logBuffer.commit(obtain);
        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, this.$$this$conflatedCallbackFlow, new MediaProjectionManagerRepository.CallbackEvent.OnRecordingSessionSet(mediaProjectionInfo, contentRecordingSession), "MediaProjectionMngrRepo");
    }

    public final void onStart(MediaProjectionInfo mediaProjectionInfo) {
        LogBuffer logBuffer = this.this$0.logger;
        logBuffer.commit(logBuffer.obtain("MediaProjectionMngrRepo", LogLevel.DEBUG, new MediaProjectionManagerRepository$stopProjecting$2$$ExternalSyntheticLambda0(2), null));
        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, this.$$this$conflatedCallbackFlow, new MediaProjectionManagerRepository.CallbackEvent.OnStart(mediaProjectionInfo), "MediaProjectionMngrRepo");
    }

    public final void onStop(MediaProjectionInfo mediaProjectionInfo) {
        LogBuffer logBuffer = this.this$0.logger;
        logBuffer.commit(logBuffer.obtain("MediaProjectionMngrRepo", LogLevel.DEBUG, new MediaProjectionManagerRepository$stopProjecting$2$$ExternalSyntheticLambda0(1), null));
        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, this.$$this$conflatedCallbackFlow, MediaProjectionManagerRepository.CallbackEvent.OnStop.INSTANCE, "MediaProjectionMngrRepo");
    }
}
