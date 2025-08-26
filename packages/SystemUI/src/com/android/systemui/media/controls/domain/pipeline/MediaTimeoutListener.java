package com.android.systemui.media.controls.domain.pipeline;

import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class MediaTimeoutListener implements MediaDataManager.Listener {
    public final Executor bgExecutor;
    public final MediaTimeoutLogger logger;
    public final DelayableExecutor mainExecutor;
    public final MediaControllerFactory mediaControllerFactory;
    public final Map mediaListeners = new LinkedHashMap();
    public LegacyMediaDataManagerImpl$$ExternalSyntheticLambda2 sessionCallback;
    public LegacyMediaDataManagerImpl$$ExternalSyntheticLambda0 stateCallback;
    public final SystemClock systemClock;
    public LegacyMediaDataManagerImpl$$ExternalSyntheticLambda0 timeoutCallback;
    public final Executor uiExecutor;

    public final class PlaybackStateListener extends MediaController.Callback {
        public Runnable cancellation;
        public boolean destroyed;
        public long expiration = Long.MAX_VALUE;
        public String key;
        public PlaybackState lastState;
        public MediaController mediaController;
        public Boolean resumption;
        public boolean timedOut;

        public PlaybackStateListener(String str, final MediaData mediaData) {
            this.key = str;
            MediaTimeoutListener.this.logger.logCustomFromTimeout(str, "run PlaybackStateListener init " + str + " cancellation " + this.cancellation);
            MediaTimeoutListener.this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener.PlaybackStateListener.1
                @Override // java.lang.Runnable
                public final void run() {
                    PlaybackStateListener.this.setMediaData(mediaData);
                }
            });
        }

        public final void destroy() {
            MediaTimeoutListener.this.logger.logCustomFromTimeout(this.key, "run destroy cancellation " + this.cancellation);
            MediaTimeoutListener.this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener$PlaybackStateListener$destroy$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaTimeoutListener.PlaybackStateListener playbackStateListener = this.this$0;
                    MediaController mediaController = playbackStateListener.mediaController;
                    if (mediaController != null) {
                        mediaController.unregisterCallback(playbackStateListener);
                    }
                }
            });
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                runnable.run();
            }
            this.destroyed = true;
        }

        public final void doTimeout() {
            this.cancellation = null;
            MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
            String str = this.key;
            mediaTimeoutLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaTimeoutLogger$$ExternalSyntheticLambda0 mediaTimeoutLogger$$ExternalSyntheticLambda0 = new MediaTimeoutLogger$$ExternalSyntheticLambda0(3);
            LogBuffer logBuffer = mediaTimeoutLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = str;
            logBuffer.commit(logMessageObtain);
            this.timedOut = true;
            this.expiration = Long.MAX_VALUE;
            LegacyMediaDataManagerImpl$$ExternalSyntheticLambda0 legacyMediaDataManagerImpl$$ExternalSyntheticLambda0 = MediaTimeoutListener.this.timeoutCallback;
            (legacyMediaDataManagerImpl$$ExternalSyntheticLambda0 != null ? legacyMediaDataManagerImpl$$ExternalSyntheticLambda0 : null).invoke(this.key, Boolean.TRUE);
        }

        public final void expireMediaTimeout(String str, String str2) {
            MediaTimeoutListener.this.logger.logCustomFromTimeout(str, "run expireMediaTimeout cancellation " + this.cancellation);
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
                mediaTimeoutLogger.getClass();
                LogLevel logLevel = LogLevel.VERBOSE;
                MediaTimeoutLogger$$ExternalSyntheticLambda0 mediaTimeoutLogger$$ExternalSyntheticLambda0 = new MediaTimeoutLogger$$ExternalSyntheticLambda0(7);
                LogBuffer logBuffer = mediaTimeoutLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = str;
                logMessageImpl.str2 = str2;
                logBuffer.commit(logMessageObtain);
                runnable.run();
            }
            this.expiration = Long.MAX_VALUE;
            this.cancellation = null;
        }

        public final boolean isPlaying$1() {
            PlaybackState playbackState = this.lastState;
            if (playbackState != null) {
                return NotificationMediaManager.isPlayingState(playbackState.getState());
            }
            return false;
        }

        @Override // android.media.session.MediaController.Callback
        public final void onPlaybackStateChanged(final PlaybackState playbackState) {
            MediaTimeoutListener.this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener$PlaybackStateListener$onPlaybackStateChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaTimeoutListener.PlaybackStateListener playbackStateListener = this.this$0;
                    playbackStateListener.processState(playbackState, true, playbackStateListener.resumption);
                }
            });
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionDestroyed() {
            MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
            String str = this.key;
            mediaTimeoutLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaTimeoutLogger$$ExternalSyntheticLambda0 mediaTimeoutLogger$$ExternalSyntheticLambda0 = new MediaTimeoutLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer = mediaTimeoutLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = str;
            logBuffer.commit(logMessageObtain);
            if (Intrinsics.areEqual(this.resumption, Boolean.TRUE)) {
                MediaTimeoutListener.this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener$PlaybackStateListener$onSessionDestroyed$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaTimeoutListener.PlaybackStateListener playbackStateListener = this.this$0;
                        MediaController mediaController = playbackStateListener.mediaController;
                        if (mediaController != null) {
                            mediaController.unregisterCallback(playbackStateListener);
                        }
                    }
                });
                return;
            }
            LegacyMediaDataManagerImpl$$ExternalSyntheticLambda2 legacyMediaDataManagerImpl$$ExternalSyntheticLambda2 = MediaTimeoutListener.this.sessionCallback;
            (legacyMediaDataManagerImpl$$ExternalSyntheticLambda2 != null ? legacyMediaDataManagerImpl$$ExternalSyntheticLambda2 : null).mo781invoke(this.key);
            destroy();
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0067  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00a1  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void processState(final PlaybackState playbackState, boolean z, Boolean bool) {
            boolean z2;
            MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
            String str = this.key;
            mediaTimeoutLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            MediaTimeoutLogger$$ExternalSyntheticLambda0 mediaTimeoutLogger$$ExternalSyntheticLambda0 = new MediaTimeoutLogger$$ExternalSyntheticLambda0(6);
            LogBuffer logBuffer = mediaTimeoutLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = str;
            logMessageImpl.str2 = playbackState != null ? playbackState.toString() : null;
            logBuffer.commit(logMessageObtain);
            boolean z3 = true;
            boolean z4 = playbackState != null && NotificationMediaManager.isPlayingState(playbackState.getState()) == isPlaying$1();
            if (playbackState != null) {
                int state = playbackState.getState();
                HashSet hashSet = NotificationMediaManager.CONNECTING_MEDIA_STATES;
                boolean zContains = hashSet.contains(Integer.valueOf(state));
                PlaybackState playbackState2 = this.lastState;
                z2 = zContains == (playbackState2 != null ? hashSet.contains(Integer.valueOf(playbackState2.getState())) : false);
            }
            PlaybackState playbackState3 = this.lastState;
            if (Intrinsics.areEqual(playbackState3 != null ? Long.valueOf(playbackState3.getActions()) : null, playbackState != null ? Long.valueOf(playbackState.getActions()) : null)) {
                PlaybackState playbackState4 = this.lastState;
                if (!MediaProcessingHelperKt.areCustomActionListsEqual(playbackState4 != null ? playbackState4.getCustomActions() : null, playbackState != null ? playbackState.getCustomActions() : null)) {
                }
            } else {
                z3 = false;
            }
            boolean zAreEqual = Intrinsics.areEqual(this.resumption, bool);
            this.lastState = playbackState;
            if ((!z2 || !z3 || !z4) && playbackState != null && z) {
                MediaTimeoutLogger mediaTimeoutLogger2 = MediaTimeoutListener.this.logger;
                final String str2 = this.key;
                mediaTimeoutLogger2.getClass();
                Function1 function1 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutLogger$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        return "dispatching state update for " + str2;
                    }
                };
                LogBuffer logBuffer2 = mediaTimeoutLogger2.buffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("MediaTimeout", logLevel, function1, null);
                ((LogMessageImpl) logMessageObtain2).str1 = str2;
                logBuffer2.commit(logMessageObtain2);
                final MediaTimeoutListener mediaTimeoutListener = MediaTimeoutListener.this;
                mediaTimeoutListener.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener$PlaybackStateListener$processState$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LegacyMediaDataManagerImpl$$ExternalSyntheticLambda0 legacyMediaDataManagerImpl$$ExternalSyntheticLambda0 = mediaTimeoutListener.stateCallback;
                        if (legacyMediaDataManagerImpl$$ExternalSyntheticLambda0 == null) {
                            legacyMediaDataManagerImpl$$ExternalSyntheticLambda0 = null;
                        }
                        legacyMediaDataManagerImpl$$ExternalSyntheticLambda0.invoke(this.key, playbackState);
                    }
                });
            }
            if (z4 && zAreEqual) {
                MediaTimeoutListener.this.logger.logCustomFromTimeout(this.key, "playingStateSame && !resumptionChanged cancellation " + this.cancellation);
                return;
            }
            this.resumption = bool;
            boolean zIsPlaying$1 = isPlaying$1();
            if (zIsPlaying$1) {
                String str3 = this.key;
                expireMediaTimeout(str3, "playback started - " + playbackState + ", " + str3);
                this.timedOut = false;
                if (z) {
                    final MediaTimeoutListener mediaTimeoutListener2 = MediaTimeoutListener.this;
                    mediaTimeoutListener2.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener$PlaybackStateListener$processState$3
                        @Override // java.lang.Runnable
                        public final void run() {
                            LegacyMediaDataManagerImpl$$ExternalSyntheticLambda0 legacyMediaDataManagerImpl$$ExternalSyntheticLambda0 = mediaTimeoutListener2.timeoutCallback;
                            if (legacyMediaDataManagerImpl$$ExternalSyntheticLambda0 == null) {
                                legacyMediaDataManagerImpl$$ExternalSyntheticLambda0 = null;
                            }
                            MediaTimeoutListener.PlaybackStateListener playbackStateListener = this;
                            legacyMediaDataManagerImpl$$ExternalSyntheticLambda0.invoke(playbackStateListener.key, Boolean.valueOf(playbackStateListener.timedOut));
                        }
                    });
                    return;
                }
                return;
            }
            MediaTimeoutLogger mediaTimeoutLogger3 = MediaTimeoutListener.this.logger;
            String str4 = this.key;
            Boolean bool2 = this.resumption;
            bool2.getClass();
            boolean zBooleanValue = bool2.booleanValue();
            mediaTimeoutLogger3.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            MediaTimeoutLogger$$ExternalSyntheticLambda0 mediaTimeoutLogger$$ExternalSyntheticLambda02 = new MediaTimeoutLogger$$ExternalSyntheticLambda0(5);
            LogBuffer logBuffer3 = mediaTimeoutLogger3.buffer;
            LogMessage logMessageObtain3 = logBuffer3.obtain("MediaTimeout", logLevel2, mediaTimeoutLogger$$ExternalSyntheticLambda02, null);
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain3;
            logMessageImpl2.str1 = str4;
            logMessageImpl2.bool1 = zIsPlaying$1;
            logMessageImpl2.bool2 = zBooleanValue;
            logBuffer3.commit(logMessageObtain3);
            if (this.cancellation != null && zAreEqual) {
                MediaTimeoutLogger mediaTimeoutLogger4 = MediaTimeoutListener.this.logger;
                String str5 = this.key;
                mediaTimeoutLogger4.getClass();
                MediaTimeoutLogger$$ExternalSyntheticLambda0 mediaTimeoutLogger$$ExternalSyntheticLambda03 = new MediaTimeoutLogger$$ExternalSyntheticLambda0(8);
                LogBuffer logBuffer4 = mediaTimeoutLogger4.buffer;
                LogMessage logMessageObtain4 = logBuffer4.obtain("MediaTimeout", logLevel2, mediaTimeoutLogger$$ExternalSyntheticLambda03, null);
                ((LogMessageImpl) logMessageObtain4).str1 = str5;
                logBuffer4.commit(logMessageObtain4);
                return;
            }
            expireMediaTimeout(this.key, "PLAYBACK STATE CHANGED - " + playbackState + ", " + this.resumption);
            long j = Intrinsics.areEqual(bool, Boolean.TRUE) ? MediaTimeoutListenerKt.RESUME_MEDIA_TIMEOUT : MediaTimeoutListenerKt.PAUSED_MEDIA_TIMEOUT;
            this.expiration = MediaTimeoutListener.this.systemClock.elapsedRealtime() + j;
            this.cancellation = MediaTimeoutListener.this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener$PlaybackStateListener$processState$2
                @Override // java.lang.Runnable
                public final void run() {
                    this.this$0.doTimeout();
                }
            }, j);
        }

        public final void setMediaData(MediaData mediaData) {
            MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
            String str = this.key;
            mediaTimeoutLogger.logCustomFromTimeout(str, "run setMediaData " + str + " cancellation " + this.cancellation);
            mediaData.getClass();
            this.destroyed = false;
            MediaController mediaController = this.mediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(this);
            }
            MediaSession.Token token = mediaData.token;
            MediaController mediaControllerCreate = token != null ? MediaTimeoutListener.this.mediaControllerFactory.create(token) : null;
            this.mediaController = mediaControllerCreate;
            if (mediaControllerCreate != null) {
                mediaControllerCreate.registerCallback(this);
            }
            MediaController mediaController2 = this.mediaController;
            processState(mediaController2 != null ? mediaController2.getPlaybackState() : null, false, Boolean.valueOf(mediaData.resumption));
        }
    }

    public MediaTimeoutListener(MediaControllerFactory mediaControllerFactory, Executor executor, Executor executor2, DelayableExecutor delayableExecutor, MediaTimeoutLogger mediaTimeoutLogger, SysuiStatusBarStateController sysuiStatusBarStateController, SystemClock systemClock) {
        this.mediaControllerFactory = mediaControllerFactory;
        this.bgExecutor = executor;
        this.uiExecutor = executor2;
        this.mainExecutor = delayableExecutor;
        this.logger = mediaTimeoutLogger;
        this.systemClock = systemClock;
        sysuiStatusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                if (z) {
                    return;
                }
                MediaTimeoutListener mediaTimeoutListener = MediaTimeoutListener.this;
                for (Map.Entry entry : ((LinkedHashMap) mediaTimeoutListener.mediaListeners).entrySet()) {
                    String str = (String) entry.getKey();
                    PlaybackStateListener playbackStateListener = (PlaybackStateListener) entry.getValue();
                    if (playbackStateListener.cancellation != null && playbackStateListener.expiration <= mediaTimeoutListener.systemClock.elapsedRealtime()) {
                        playbackStateListener.expireMediaTimeout(str, "timeout happened while dozing");
                        playbackStateListener.doTimeout();
                    }
                }
            }
        });
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(final String str, String str2, final MediaData mediaData, boolean z) {
        Object objRemove;
        String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("run onMediaDataLoaded ", str);
        MediaTimeoutLogger mediaTimeoutLogger = this.logger;
        mediaTimeoutLogger.logCustomFromTimeout(str, strM);
        PlaybackStateListener playbackStateListener = (PlaybackStateListener) ((LinkedHashMap) this.mediaListeners).get(str);
        LogBuffer logBuffer = mediaTimeoutLogger.buffer;
        if (playbackStateListener == null) {
            objRemove = null;
        } else {
            if (!playbackStateListener.destroyed) {
                mediaTimeoutLogger.logCustomFromTimeout(str, "run !it.destroyed onMediaDataLoaded " + str + " ");
                return;
            }
            LogMessage logMessageObtain = logBuffer.obtain("MediaTimeout", LogLevel.DEBUG, new MediaTimeoutLogger$$ExternalSyntheticLambda0(9), null);
            ((LogMessageImpl) logMessageObtain).str1 = str;
            logBuffer.commit(logMessageObtain);
            objRemove = playbackStateListener;
        }
        if (str2 != null && !Intrinsics.areEqual(str, str2)) {
            objRemove = this.mediaListeners.remove(str2);
            boolean z2 = objRemove != null;
            LogMessage logMessageObtain2 = logBuffer.obtain("MediaTimeout", LogLevel.DEBUG, new MediaTimeoutLogger$$ExternalSyntheticLambda0(10), null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain2;
            logMessageImpl.str1 = str2;
            logMessageImpl.str2 = str;
            logMessageImpl.bool1 = z2;
            logBuffer.commit(logMessageObtain2);
        }
        final PlaybackStateListener playbackStateListener2 = (PlaybackStateListener) objRemove;
        if (playbackStateListener2 != null) {
            this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener$onMediaDataLoaded$2$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaTimeoutLogger mediaTimeoutLogger2 = this.this$0.logger;
                    String str3 = str;
                    mediaTimeoutLogger2.logCustomFromTimeout(str3, "run onMediaDataLoaded reusedListener is not null " + str3 + " ");
                    boolean zIsPlaying$1 = playbackStateListener2.isPlaying$1();
                    MediaTimeoutLogger mediaTimeoutLogger3 = this.this$0.logger;
                    String str4 = str;
                    mediaTimeoutLogger3.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    MediaTimeoutLogger$$ExternalSyntheticLambda0 mediaTimeoutLogger$$ExternalSyntheticLambda0 = new MediaTimeoutLogger$$ExternalSyntheticLambda0(2);
                    LogBuffer logBuffer2 = mediaTimeoutLogger3.buffer;
                    LogMessage logMessageObtain3 = logBuffer2.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain3;
                    logMessageImpl2.str1 = str4;
                    logMessageImpl2.bool1 = zIsPlaying$1;
                    logBuffer2.commit(logMessageObtain3);
                    playbackStateListener2.setMediaData(mediaData);
                    MediaTimeoutListener.PlaybackStateListener playbackStateListener3 = playbackStateListener2;
                    String str5 = str;
                    playbackStateListener3.key = str5;
                    this.this$0.mediaListeners.put(str5, playbackStateListener3);
                    if (zIsPlaying$1 != playbackStateListener2.isPlaying$1()) {
                        final MediaTimeoutListener mediaTimeoutListener = this.this$0;
                        DelayableExecutor delayableExecutor = mediaTimeoutListener.mainExecutor;
                        final String str6 = str;
                        delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener$onMediaDataLoaded$2$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaTimeoutListener.PlaybackStateListener playbackStateListener4 = (MediaTimeoutListener.PlaybackStateListener) ((LinkedHashMap) mediaTimeoutListener.mediaListeners).get(str6);
                                if (playbackStateListener4 == null || !playbackStateListener4.isPlaying$1()) {
                                    return;
                                }
                                MediaTimeoutLogger mediaTimeoutLogger4 = mediaTimeoutListener.logger;
                                String str7 = str6;
                                mediaTimeoutLogger4.getClass();
                                LogLevel logLevel2 = LogLevel.DEBUG;
                                MediaTimeoutLogger$$ExternalSyntheticLambda0 mediaTimeoutLogger$$ExternalSyntheticLambda02 = new MediaTimeoutLogger$$ExternalSyntheticLambda0(1);
                                LogBuffer logBuffer3 = mediaTimeoutLogger4.buffer;
                                LogMessage logMessageObtain4 = logBuffer3.obtain("MediaTimeout", logLevel2, mediaTimeoutLogger$$ExternalSyntheticLambda02, null);
                                ((LogMessageImpl) logMessageObtain4).str1 = str7;
                                logBuffer3.commit(logMessageObtain4);
                                LegacyMediaDataManagerImpl$$ExternalSyntheticLambda0 legacyMediaDataManagerImpl$$ExternalSyntheticLambda0 = mediaTimeoutListener.timeoutCallback;
                                (legacyMediaDataManagerImpl$$ExternalSyntheticLambda0 != null ? legacyMediaDataManagerImpl$$ExternalSyntheticLambda0 : null).invoke(str6, Boolean.FALSE);
                            }
                        });
                    }
                }
            });
        } else {
            this.mediaListeners.put(str, new PlaybackStateListener(str, mediaData));
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataRemoved(String str, boolean z) {
        this.logger.logCustomFromTimeout(str, "run onMediaDataRemoved mediaListeners[key] is null? " + (((LinkedHashMap) this.mediaListeners).get(str) == null));
        PlaybackStateListener playbackStateListener = (PlaybackStateListener) this.mediaListeners.remove(str);
        if (playbackStateListener != null) {
            playbackStateListener.destroy();
        }
    }
}
