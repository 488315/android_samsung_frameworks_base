package com.android.systemui.media.controls.domain.pipeline;

import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.MergingSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

public final class MediaTimeoutListener implements MediaDataManager.Listener {
    public final MediaTimeoutLogger logger;
    public final DelayableExecutor mainExecutor;
    public final MediaControllerFactory mediaControllerFactory;
    public final MediaFlags mediaFlags;
    public final Map mediaListeners = new LinkedHashMap();
    public final Map recommendationListeners = new LinkedHashMap();
    public Function1 sessionCallback;
    public Function2 stateCallback;
    public final SystemClock systemClock;
    public Function2 timeoutCallback;

    public final class PlaybackStateListener extends MediaController.Callback {
        public Runnable cancellation;
        public boolean destroyed;
        public long expiration = Long.MAX_VALUE;
        public String key;
        public PlaybackState lastState;
        public MediaController mediaController;
        public Boolean resumption;

        public PlaybackStateListener(String str, MediaData mediaData) {
            this.key = str;
            setMediaData(mediaData);
        }

        public final void doTimeout() {
            this.cancellation = null;
            MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
            String str = this.key;
            mediaTimeoutLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaTimeoutLogger$logTimeout$2 mediaTimeoutLogger$logTimeout$2 = MediaTimeoutLogger$logTimeout$2.INSTANCE;
            LogBuffer logBuffer = mediaTimeoutLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logTimeout$2, null);
            ((LogMessageImpl) obtain).str1 = str;
            logBuffer.commit(obtain);
            this.expiration = Long.MAX_VALUE;
            Function2 function2 = MediaTimeoutListener.this.timeoutCallback;
            (function2 != null ? function2 : null).invoke(this.key, Boolean.TRUE);
        }

        public final void expireMediaTimeout(String str, String str2) {
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
                mediaTimeoutLogger.getClass();
                LogLevel logLevel = LogLevel.VERBOSE;
                MediaTimeoutLogger$logTimeoutCancelled$2 mediaTimeoutLogger$logTimeoutCancelled$2 = MediaTimeoutLogger$logTimeoutCancelled$2.INSTANCE;
                LogBuffer logBuffer = mediaTimeoutLogger.buffer;
                LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logTimeoutCancelled$2, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = str;
                logMessageImpl.str2 = str2;
                logBuffer.commit(obtain);
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
        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            processState(playbackState, true, this.resumption);
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionDestroyed() {
            MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
            String str = this.key;
            mediaTimeoutLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaTimeoutLogger$logSessionDestroyed$2 mediaTimeoutLogger$logSessionDestroyed$2 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutLogger$logSessionDestroyed$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("session destroyed ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = mediaTimeoutLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logSessionDestroyed$2, null);
            ((LogMessageImpl) obtain).str1 = str;
            logBuffer.commit(obtain);
            if (Intrinsics.areEqual(this.resumption, Boolean.TRUE)) {
                MediaController mediaController = this.mediaController;
                if (mediaController != null) {
                    mediaController.unregisterCallback(this);
                    return;
                }
                return;
            }
            Function1 function1 = MediaTimeoutListener.this.sessionCallback;
            (function1 != null ? function1 : null).invoke(this.key);
            MediaController mediaController2 = this.mediaController;
            if (mediaController2 != null) {
                mediaController2.unregisterCallback(this);
            }
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                runnable.run();
            }
            this.destroyed = true;
        }

        public final void processState(PlaybackState playbackState, boolean z, Boolean bool) {
            MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
            String str = this.key;
            mediaTimeoutLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            MediaTimeoutLogger$logPlaybackState$2 mediaTimeoutLogger$logPlaybackState$2 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutLogger$logPlaybackState$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return FontProvider$$ExternalSyntheticOutline0.m("state update: key=", logMessage.getStr1(), " state=", logMessage.getStr2());
                }
            };
            LogBuffer logBuffer = mediaTimeoutLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logPlaybackState$2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = str;
            logMessageImpl.str2 = playbackState != null ? playbackState.toString() : null;
            logBuffer.commit(obtain);
            boolean z2 = false;
            boolean z3 = playbackState != null && NotificationMediaManager.isPlayingState(playbackState.getState()) == isPlaying$1();
            PlaybackState playbackState2 = this.lastState;
            if (Intrinsics.areEqual(playbackState2 != null ? Long.valueOf(playbackState2.getActions()) : null, playbackState != null ? Long.valueOf(playbackState.getActions()) : null)) {
                MediaTimeoutListener mediaTimeoutListener = MediaTimeoutListener.this;
                PlaybackState playbackState3 = this.lastState;
                List<PlaybackState.CustomAction> customActions = playbackState3 != null ? playbackState3.getCustomActions() : null;
                List<PlaybackState.CustomAction> customActions2 = playbackState != null ? playbackState.getCustomActions() : null;
                mediaTimeoutListener.getClass();
                if (customActions != customActions2) {
                    if (customActions != null && customActions2 != null && customActions.size() == customActions2.size()) {
                        MergingSequence$iterator$1 mergingSequence$iterator$1 = new MergingSequence$iterator$1(SequencesKt___SequencesKt.zip(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(customActions), new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(customActions2)));
                        loop0: while (mergingSequence$iterator$1.hasNext()) {
                            Pair pair = (Pair) mergingSequence$iterator$1.next();
                            PlaybackState.CustomAction customAction = (PlaybackState.CustomAction) pair.component1();
                            PlaybackState.CustomAction customAction2 = (PlaybackState.CustomAction) pair.component2();
                            if (!Intrinsics.areEqual(customAction.getAction(), customAction2.getAction()) || !Intrinsics.areEqual(customAction.getName(), customAction2.getName()) || customAction.getIcon() != customAction2.getIcon()) {
                                break;
                            }
                            if ((customAction.getExtras() == null) != (customAction2.getExtras() == null)) {
                                break;
                            }
                            if (customAction.getExtras() != null) {
                                for (String str2 : customAction.getExtras().keySet()) {
                                    if (!Intrinsics.areEqual(customAction.getExtras().get(str2), customAction2.getExtras().get(str2))) {
                                        break loop0;
                                    }
                                }
                            }
                        }
                    }
                }
                z2 = true;
            }
            boolean areEqual = true ^ Intrinsics.areEqual(this.resumption, bool);
            this.lastState = playbackState;
            if ((!z2 || !z3) && playbackState != null && z) {
                MediaTimeoutLogger mediaTimeoutLogger2 = MediaTimeoutListener.this.logger;
                final String str3 = this.key;
                mediaTimeoutLogger2.getClass();
                LogLevel logLevel2 = LogLevel.VERBOSE;
                Function1 function1 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutLogger$logStateCallback$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("dispatching state update for ", str3);
                    }
                };
                LogBuffer logBuffer2 = mediaTimeoutLogger2.buffer;
                LogMessage obtain2 = logBuffer2.obtain("MediaTimeout", logLevel2, function1, null);
                ((LogMessageImpl) obtain2).str1 = str3;
                logBuffer2.commit(obtain2);
                Function2 function2 = MediaTimeoutListener.this.stateCallback;
                if (function2 == null) {
                    function2 = null;
                }
                function2.invoke(this.key, playbackState);
            }
            if (!z3 || areEqual) {
                this.resumption = bool;
                boolean isPlaying$1 = isPlaying$1();
                if (isPlaying$1) {
                    String str4 = this.key;
                    expireMediaTimeout(str4, "playback started - " + playbackState + ", " + str4);
                    if (z) {
                        Function2 function22 = MediaTimeoutListener.this.timeoutCallback;
                        (function22 != null ? function22 : null).invoke(this.key, Boolean.FALSE);
                        return;
                    }
                    return;
                }
                MediaTimeoutLogger mediaTimeoutLogger3 = MediaTimeoutListener.this.logger;
                String str5 = this.key;
                Boolean bool2 = this.resumption;
                Intrinsics.checkNotNull(bool2);
                boolean booleanValue = bool2.booleanValue();
                mediaTimeoutLogger3.getClass();
                LogLevel logLevel3 = LogLevel.DEBUG;
                MediaTimeoutLogger$logScheduleTimeout$2 mediaTimeoutLogger$logScheduleTimeout$2 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutLogger$logScheduleTimeout$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        String str1 = logMessage.getStr1();
                        boolean bool1 = logMessage.getBool1();
                        boolean bool22 = logMessage.getBool2();
                        StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("schedule timeout ", str1, ", playing=", " resumption=", bool1);
                        m.append(bool22);
                        return m.toString();
                    }
                };
                LogBuffer logBuffer3 = mediaTimeoutLogger3.buffer;
                LogMessage obtain3 = logBuffer3.obtain("MediaTimeout", logLevel3, mediaTimeoutLogger$logScheduleTimeout$2, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain3;
                logMessageImpl2.str1 = str5;
                logMessageImpl2.bool1 = isPlaying$1;
                logMessageImpl2.bool2 = booleanValue;
                logBuffer3.commit(obtain3);
                if (this.cancellation != null && !areEqual) {
                    MediaTimeoutLogger mediaTimeoutLogger4 = MediaTimeoutListener.this.logger;
                    String str6 = this.key;
                    mediaTimeoutLogger4.getClass();
                    MediaTimeoutLogger$logCancelIgnored$2 mediaTimeoutLogger$logCancelIgnored$2 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutLogger$logCancelIgnored$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("cancellation already exists for ", ((LogMessage) obj).getStr1());
                        }
                    };
                    LogBuffer logBuffer4 = mediaTimeoutLogger4.buffer;
                    LogMessage obtain4 = logBuffer4.obtain("MediaTimeout", logLevel3, mediaTimeoutLogger$logCancelIgnored$2, null);
                    ((LogMessageImpl) obtain4).str1 = str6;
                    logBuffer4.commit(obtain4);
                    return;
                }
                expireMediaTimeout(this.key, "PLAYBACK STATE CHANGED - " + playbackState + ", " + this.resumption);
                long j = Intrinsics.areEqual(bool, Boolean.TRUE) ? MediaTimeoutListenerKt.RESUME_MEDIA_TIMEOUT : MediaTimeoutListenerKt.PAUSED_MEDIA_TIMEOUT;
                this.expiration = MediaTimeoutListener.this.systemClock.elapsedRealtime() + j;
                this.cancellation = MediaTimeoutListener.this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener$PlaybackStateListener$processState$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaTimeoutListener.PlaybackStateListener.this.doTimeout();
                    }
                }, j);
            }
        }

        public final void setMediaData(MediaData mediaData) {
            this.destroyed = false;
            MediaController mediaController = this.mediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(this);
            }
            MediaSession.Token token = mediaData.token;
            MediaController create = token != null ? MediaTimeoutListener.this.mediaControllerFactory.create(token) : null;
            this.mediaController = create;
            if (create != null) {
                create.registerCallback(this);
            }
            MediaController mediaController2 = this.mediaController;
            processState(mediaController2 != null ? mediaController2.getPlaybackState() : null, false, Boolean.valueOf(mediaData.resumption));
        }
    }

    public final class RecommendationListener {
        public Runnable cancellation;
        public long expiration;
        public final String key;
        public final SmartspaceMediaData recommendationData;

        public RecommendationListener(String str, SmartspaceMediaData smartspaceMediaData) {
            this.key = str;
            this.expiration = Long.MAX_VALUE;
            this.recommendationData = smartspaceMediaData;
            long j = smartspaceMediaData.expiryTimeMs;
            if (j != Long.MAX_VALUE) {
                long j2 = j - smartspaceMediaData.headphoneConnectionTimeMillis;
                MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
                mediaTimeoutLogger.getClass();
                LogLevel logLevel = LogLevel.VERBOSE;
                MediaTimeoutLogger$logRecommendationTimeoutScheduled$2 mediaTimeoutLogger$logRecommendationTimeoutScheduled$2 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutLogger$logRecommendationTimeoutScheduled$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        return "recommendation timeout scheduled for " + logMessage.getStr1() + " in " + logMessage.getLong1() + " ms";
                    }
                };
                LogBuffer logBuffer = mediaTimeoutLogger.buffer;
                LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logRecommendationTimeoutScheduled$2, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = this.key;
                logMessageImpl.long1 = j2;
                logBuffer.commit(obtain);
                Runnable runnable = this.cancellation;
                if (runnable != null) {
                    runnable.run();
                }
                this.cancellation = MediaTimeoutListener.this.mainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener$RecommendationListener$processUpdate$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaTimeoutListener.RecommendationListener.this.doTimeout();
                    }
                }, j2);
                this.expiration = this.recommendationData.expiryTimeMs;
            }
        }

        public final void doTimeout() {
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                runnable.run();
            }
            this.cancellation = null;
            MediaTimeoutListener mediaTimeoutListener = MediaTimeoutListener.this;
            MediaTimeoutLogger mediaTimeoutLogger = mediaTimeoutListener.logger;
            String str = this.key;
            mediaTimeoutLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaTimeoutLogger$logTimeout$2 mediaTimeoutLogger$logTimeout$2 = MediaTimeoutLogger$logTimeout$2.INSTANCE;
            LogBuffer logBuffer = mediaTimeoutLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logTimeout$2, null);
            ((LogMessageImpl) obtain).str1 = str;
            logBuffer.commit(obtain);
            this.expiration = Long.MAX_VALUE;
            Function2 function2 = mediaTimeoutListener.timeoutCallback;
            (function2 != null ? function2 : null).invoke(str, Boolean.TRUE);
        }
    }

    public MediaTimeoutListener(MediaControllerFactory mediaControllerFactory, DelayableExecutor delayableExecutor, MediaTimeoutLogger mediaTimeoutLogger, SysuiStatusBarStateController sysuiStatusBarStateController, SystemClock systemClock, MediaFlags mediaFlags) {
        this.mediaControllerFactory = mediaControllerFactory;
        this.mainExecutor = delayableExecutor;
        this.logger = mediaTimeoutLogger;
        this.systemClock = systemClock;
        this.mediaFlags = mediaFlags;
        ((StatusBarStateControllerImpl) sysuiStatusBarStateController).addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                SystemClock systemClock2;
                if (z) {
                    return;
                }
                MediaTimeoutListener mediaTimeoutListener = MediaTimeoutListener.this;
                Iterator it = ((LinkedHashMap) mediaTimeoutListener.mediaListeners).entrySet().iterator();
                while (true) {
                    boolean hasNext = it.hasNext();
                    systemClock2 = mediaTimeoutListener.systemClock;
                    if (!hasNext) {
                        break;
                    }
                    Map.Entry entry = (Map.Entry) it.next();
                    String str = (String) entry.getKey();
                    PlaybackStateListener playbackStateListener = (PlaybackStateListener) entry.getValue();
                    if (playbackStateListener.cancellation != null && playbackStateListener.expiration <= systemClock2.elapsedRealtime()) {
                        playbackStateListener.expireMediaTimeout(str, "timeout happened while dozing");
                        playbackStateListener.doTimeout();
                    }
                }
                for (Map.Entry entry2 : ((LinkedHashMap) mediaTimeoutListener.recommendationListeners).entrySet()) {
                    String str2 = (String) entry2.getKey();
                    RecommendationListener recommendationListener = (RecommendationListener) entry2.getValue();
                    if (recommendationListener.cancellation != null && recommendationListener.expiration <= systemClock2.currentTimeMillis()) {
                        MediaTimeoutLogger mediaTimeoutLogger2 = mediaTimeoutListener.logger;
                        mediaTimeoutLogger2.getClass();
                        LogLevel logLevel = LogLevel.VERBOSE;
                        MediaTimeoutLogger$logTimeoutCancelled$2 mediaTimeoutLogger$logTimeoutCancelled$2 = MediaTimeoutLogger$logTimeoutCancelled$2.INSTANCE;
                        LogBuffer logBuffer = mediaTimeoutLogger2.buffer;
                        LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logTimeoutCancelled$2, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                        logMessageImpl.str1 = str2;
                        logMessageImpl.str2 = "Timed out while dozing";
                        logBuffer.commit(obtain);
                        recommendationListener.doTimeout();
                    }
                }
            }
        });
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(final String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        Object obj;
        PlaybackStateListener playbackStateListener = (PlaybackStateListener) ((LinkedHashMap) this.mediaListeners).get(str);
        MediaTimeoutLogger mediaTimeoutLogger = this.logger;
        if (playbackStateListener == null) {
            obj = null;
        } else {
            if (!playbackStateListener.destroyed) {
                return;
            }
            mediaTimeoutLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaTimeoutLogger$logReuseListener$2 mediaTimeoutLogger$logReuseListener$2 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutLogger$logReuseListener$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("reuse listener: ", ((LogMessage) obj2).getStr1());
                }
            };
            LogBuffer logBuffer = mediaTimeoutLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logReuseListener$2, null);
            ((LogMessageImpl) obtain).str1 = str;
            logBuffer.commit(obtain);
            obj = playbackStateListener;
        }
        if (str2 != null && !str.equals(str2)) {
            obj = TypeIntrinsics.asMutableMap(this.mediaListeners).remove(str2);
            boolean z3 = obj != null;
            mediaTimeoutLogger.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            MediaTimeoutLogger$logMigrateListener$2 mediaTimeoutLogger$logMigrateListener$2 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutLogger$logMigrateListener$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    LogMessage logMessage = (LogMessage) obj2;
                    String str1 = logMessage.getStr1();
                    String str22 = logMessage.getStr2();
                    boolean bool1 = logMessage.getBool1();
                    StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("migrate from ", str1, " to ", str22, ", had listener? ");
                    m.append(bool1);
                    return m.toString();
                }
            };
            LogBuffer logBuffer2 = mediaTimeoutLogger.buffer;
            LogMessage obtain2 = logBuffer2.obtain("MediaTimeout", logLevel2, mediaTimeoutLogger$logMigrateListener$2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain2;
            logMessageImpl.str1 = str2;
            logMessageImpl.str2 = str;
            logMessageImpl.bool1 = z3;
            logBuffer2.commit(obtain2);
        }
        PlaybackStateListener playbackStateListener2 = (PlaybackStateListener) obj;
        if (playbackStateListener2 == null) {
            this.mediaListeners.put(str, new PlaybackStateListener(str, mediaData));
            return;
        }
        boolean isPlaying$1 = playbackStateListener2.isPlaying$1();
        mediaTimeoutLogger.getClass();
        LogLevel logLevel3 = LogLevel.DEBUG;
        MediaTimeoutLogger$logUpdateListener$2 mediaTimeoutLogger$logUpdateListener$2 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutLogger$logUpdateListener$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                return "updating " + logMessage.getStr1() + ", was playing? " + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer3 = mediaTimeoutLogger.buffer;
        LogMessage obtain3 = logBuffer3.obtain("MediaTimeout", logLevel3, mediaTimeoutLogger$logUpdateListener$2, null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain3;
        logMessageImpl2.str1 = str;
        logMessageImpl2.bool1 = isPlaying$1;
        logBuffer3.commit(obtain3);
        playbackStateListener2.setMediaData(mediaData);
        playbackStateListener2.key = str;
        this.mediaListeners.put(str, playbackStateListener2);
        if (isPlaying$1 != playbackStateListener2.isPlaying$1()) {
            this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener$onMediaDataLoaded$2$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaTimeoutListener.PlaybackStateListener playbackStateListener3 = (MediaTimeoutListener.PlaybackStateListener) ((LinkedHashMap) MediaTimeoutListener.this.mediaListeners).get(str);
                    if (playbackStateListener3 == null || !playbackStateListener3.isPlaying$1()) {
                        return;
                    }
                    MediaTimeoutLogger mediaTimeoutLogger2 = MediaTimeoutListener.this.logger;
                    String str3 = str;
                    mediaTimeoutLogger2.getClass();
                    LogLevel logLevel4 = LogLevel.DEBUG;
                    MediaTimeoutLogger$logDelayedUpdate$2 mediaTimeoutLogger$logDelayedUpdate$2 = new Function1() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaTimeoutLogger$logDelayedUpdate$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("deliver delayed playback state for ", ((LogMessage) obj2).getStr1());
                        }
                    };
                    LogBuffer logBuffer4 = mediaTimeoutLogger2.buffer;
                    LogMessage obtain4 = logBuffer4.obtain("MediaTimeout", logLevel4, mediaTimeoutLogger$logDelayedUpdate$2, null);
                    ((LogMessageImpl) obtain4).str1 = str3;
                    logBuffer4.commit(obtain4);
                    Function2 function2 = MediaTimeoutListener.this.timeoutCallback;
                    (function2 != null ? function2 : null).invoke(str, Boolean.FALSE);
                }
            });
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataRemoved(String str, boolean z) {
        PlaybackStateListener playbackStateListener = (PlaybackStateListener) this.mediaListeners.remove(str);
        if (playbackStateListener != null) {
            MediaController mediaController = playbackStateListener.mediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(playbackStateListener);
            }
            Runnable runnable = playbackStateListener.cancellation;
            if (runnable != null) {
                runnable.run();
            }
            playbackStateListener.destroyed = true;
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        this.mediaFlags.isPersistentSsCardEnabled();
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        this.mediaFlags.isPersistentSsCardEnabled();
    }
}
