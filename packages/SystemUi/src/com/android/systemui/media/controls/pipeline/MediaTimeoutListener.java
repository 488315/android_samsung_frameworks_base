package com.android.systemui.media.controls.pipeline;

import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.pipeline.MediaTimeoutListener;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PlaybackStateListener extends MediaController.Callback {
        public ExecutorImpl.ExecutionToken cancellation;
        public boolean destroyed;
        public long expiration = Long.MAX_VALUE;
        public String key;
        public PlaybackState lastState;
        public MediaController mediaController;
        public Boolean resumption;
        public boolean timedOut;

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
            obtain.setStr1(str);
            logBuffer.commit(obtain);
            this.timedOut = true;
            this.expiration = Long.MAX_VALUE;
            Function2 function2 = MediaTimeoutListener.this.timeoutCallback;
            (function2 != null ? function2 : null).invoke(this.key, true);
        }

        public final void expireMediaTimeout(String str, String str2) {
            ExecutorImpl.ExecutionToken executionToken = this.cancellation;
            if (executionToken != null) {
                MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
                mediaTimeoutLogger.getClass();
                LogLevel logLevel = LogLevel.VERBOSE;
                MediaTimeoutLogger$logTimeoutCancelled$2 mediaTimeoutLogger$logTimeoutCancelled$2 = MediaTimeoutLogger$logTimeoutCancelled$2.INSTANCE;
                LogBuffer logBuffer = mediaTimeoutLogger.buffer;
                LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logTimeoutCancelled$2, null);
                CarrierTextManagerLogger$$ExternalSyntheticOutline0.m83m(obtain, str, str2, logBuffer, obtain);
                executionToken.run();
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
            MediaTimeoutLogger$logSessionDestroyed$2 mediaTimeoutLogger$logSessionDestroyed$2 = new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logSessionDestroyed$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("session destroyed ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = mediaTimeoutLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logSessionDestroyed$2, null);
            obtain.setStr1(str);
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
            ExecutorImpl.ExecutionToken executionToken = this.cancellation;
            if (executionToken != null) {
                executionToken.run();
            }
            this.destroyed = true;
        }

        /* JADX WARN: Removed duplicated region for block: B:56:0x012b A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:57:? A[LOOP:0: B:28:0x009a->B:57:?, LOOP_END, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:65:0x012e  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x017a  */
        /* JADX WARN: Removed duplicated region for block: B:86:0x0208  */
        /* JADX WARN: Removed duplicated region for block: B:98:0x0165  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void processState(PlaybackState playbackState, boolean z, Boolean bool) {
            boolean z2;
            Function2 function2;
            boolean isPlaying$1;
            boolean z3;
            boolean z4;
            MediaTimeoutLogger mediaTimeoutLogger = MediaTimeoutListener.this.logger;
            String str = this.key;
            mediaTimeoutLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            MediaTimeoutLogger$logPlaybackState$2 mediaTimeoutLogger$logPlaybackState$2 = new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logPlaybackState$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return FontProvider$$ExternalSyntheticOutline0.m32m("state update: key=", logMessage.getStr1(), " state=", logMessage.getStr2());
                }
            };
            LogBuffer logBuffer = mediaTimeoutLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logPlaybackState$2, null);
            obtain.setStr1(str);
            obtain.setStr2(playbackState != null ? playbackState.toString() : null);
            logBuffer.commit(obtain);
            boolean z5 = playbackState != null && NotificationMediaManager.isPlayingState(playbackState.getState()) == isPlaying$1();
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
                        while (mergingSequence$iterator$1.hasNext()) {
                            Pair pair = (Pair) mergingSequence$iterator$1.next();
                            PlaybackState.CustomAction customAction = (PlaybackState.CustomAction) pair.component1();
                            PlaybackState.CustomAction customAction2 = (PlaybackState.CustomAction) pair.component2();
                            if (Intrinsics.areEqual(customAction.getAction(), customAction2.getAction()) && Intrinsics.areEqual(customAction.getName(), customAction2.getName()) && customAction.getIcon() == customAction2.getIcon()) {
                                if ((customAction.getExtras() == null) == (customAction2.getExtras() == null)) {
                                    if (customAction.getExtras() != null) {
                                        for (String str2 : customAction.getExtras().keySet()) {
                                            if (!Intrinsics.areEqual(customAction.getExtras().get(str2), customAction2.getExtras().get(str2))) {
                                            }
                                        }
                                    }
                                    z4 = true;
                                    if (z4) {
                                    }
                                }
                            }
                            z4 = false;
                            if (z4) {
                            }
                        }
                    }
                    z3 = false;
                    if (z3) {
                        z2 = true;
                        boolean areEqual = true ^ Intrinsics.areEqual(this.resumption, bool);
                        this.lastState = playbackState;
                        if ((z2 || !z5) && playbackState != null && z) {
                            MediaTimeoutLogger mediaTimeoutLogger2 = MediaTimeoutListener.this.logger;
                            final String str3 = this.key;
                            mediaTimeoutLogger2.getClass();
                            LogLevel logLevel2 = LogLevel.VERBOSE;
                            Function1 function1 = new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logStateCallback$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("dispatching state update for ", str3);
                                }
                            };
                            LogBuffer logBuffer2 = mediaTimeoutLogger2.buffer;
                            LogMessage obtain2 = logBuffer2.obtain("MediaTimeout", logLevel2, function1, null);
                            obtain2.setStr1(str3);
                            logBuffer2.commit(obtain2);
                            function2 = MediaTimeoutListener.this.stateCallback;
                            if (function2 == null) {
                                function2 = null;
                            }
                            function2.invoke(this.key, playbackState);
                        }
                        if (z5 || areEqual) {
                            this.resumption = bool;
                            isPlaying$1 = isPlaying$1();
                            if (!isPlaying$1) {
                                String str4 = this.key;
                                expireMediaTimeout(str4, "playback started - " + playbackState + ", " + str4);
                                this.timedOut = false;
                                if (z) {
                                    Function2 function22 = MediaTimeoutListener.this.timeoutCallback;
                                    (function22 != null ? function22 : null).invoke(this.key, false);
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
                            MediaTimeoutLogger$logScheduleTimeout$2 mediaTimeoutLogger$logScheduleTimeout$2 = new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logScheduleTimeout$2
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    LogMessage logMessage = (LogMessage) obj;
                                    return "schedule timeout " + logMessage.getStr1() + ", playing=" + logMessage.getBool1() + " resumption=" + logMessage.getBool2();
                                }
                            };
                            LogBuffer logBuffer3 = mediaTimeoutLogger3.buffer;
                            LogMessage obtain3 = logBuffer3.obtain("MediaTimeout", logLevel3, mediaTimeoutLogger$logScheduleTimeout$2, null);
                            obtain3.setStr1(str5);
                            obtain3.setBool1(isPlaying$1);
                            obtain3.setBool2(booleanValue);
                            logBuffer3.commit(obtain3);
                            if (this.cancellation != null && !areEqual) {
                                MediaTimeoutLogger mediaTimeoutLogger4 = MediaTimeoutListener.this.logger;
                                String str6 = this.key;
                                mediaTimeoutLogger4.getClass();
                                MediaTimeoutLogger$logCancelIgnored$2 mediaTimeoutLogger$logCancelIgnored$2 = new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logCancelIgnored$2
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("cancellation already exists for ", ((LogMessage) obj).getStr1());
                                    }
                                };
                                LogBuffer logBuffer4 = mediaTimeoutLogger4.buffer;
                                LogMessage obtain4 = logBuffer4.obtain("MediaTimeout", logLevel3, mediaTimeoutLogger$logCancelIgnored$2, null);
                                obtain4.setStr1(str6);
                                logBuffer4.commit(obtain4);
                                return;
                            }
                            expireMediaTimeout(this.key, "PLAYBACK STATE CHANGED - " + playbackState + ", " + this.resumption);
                            long j = Intrinsics.areEqual(bool, Boolean.TRUE) ? MediaTimeoutListenerKt.RESUME_MEDIA_TIMEOUT : MediaTimeoutListenerKt.PAUSED_MEDIA_TIMEOUT;
                            ((SystemClockImpl) MediaTimeoutListener.this.systemClock).getClass();
                            this.expiration = android.os.SystemClock.elapsedRealtime() + j;
                            this.cancellation = MediaTimeoutListener.this.mainExecutor.executeDelayed(j, new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutListener$PlaybackStateListener$processState$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MediaTimeoutListener.PlaybackStateListener.this.doTimeout();
                                }
                            });
                            return;
                        }
                        return;
                    }
                }
                z3 = true;
                if (z3) {
                }
            }
            z2 = false;
            boolean areEqual2 = true ^ Intrinsics.areEqual(this.resumption, bool);
            this.lastState = playbackState;
            if (z2) {
            }
            MediaTimeoutLogger mediaTimeoutLogger22 = MediaTimeoutListener.this.logger;
            final String str32 = this.key;
            mediaTimeoutLogger22.getClass();
            LogLevel logLevel22 = LogLevel.VERBOSE;
            Function1 function12 = new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logStateCallback$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("dispatching state update for ", str32);
                }
            };
            LogBuffer logBuffer22 = mediaTimeoutLogger22.buffer;
            LogMessage obtain22 = logBuffer22.obtain("MediaTimeout", logLevel22, function12, null);
            obtain22.setStr1(str32);
            logBuffer22.commit(obtain22);
            function2 = MediaTimeoutListener.this.stateCallback;
            if (function2 == null) {
            }
            function2.invoke(this.key, playbackState);
            if (z5) {
            }
            this.resumption = bool;
            isPlaying$1 = isPlaying$1();
            if (!isPlaying$1) {
            }
        }

        public final void setMediaData(MediaData mediaData) {
            MediaController mediaController;
            this.destroyed = false;
            MediaController mediaController2 = this.mediaController;
            if (mediaController2 != null) {
                mediaController2.unregisterCallback(this);
            }
            MediaSession.Token token = mediaData.token;
            if (token != null) {
                MediaControllerFactory mediaControllerFactory = MediaTimeoutListener.this.mediaControllerFactory;
                mediaControllerFactory.getClass();
                mediaController = new MediaController(mediaControllerFactory.mContext, token);
            } else {
                mediaController = null;
            }
            this.mediaController = mediaController;
            if (mediaController != null) {
                mediaController.registerCallback(this);
            }
            MediaController mediaController3 = this.mediaController;
            processState(mediaController3 != null ? mediaController3.getPlaybackState() : null, false, Boolean.valueOf(mediaData.resumption));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RecommendationListener {
        public ExecutorImpl.ExecutionToken cancellation;
        public long expiration;
        public final String key;
        public SmartspaceMediaData recommendationData;
        public boolean timedOut;

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
                MediaTimeoutLogger$logRecommendationTimeoutScheduled$2 mediaTimeoutLogger$logRecommendationTimeoutScheduled$2 = new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logRecommendationTimeoutScheduled$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        return "recommendation timeout scheduled for " + logMessage.getStr1() + " in " + logMessage.getLong1() + " ms";
                    }
                };
                LogBuffer logBuffer = mediaTimeoutLogger.buffer;
                LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logRecommendationTimeoutScheduled$2, null);
                obtain.setStr1(str);
                obtain.setLong1(j2);
                logBuffer.commit(obtain);
                ExecutorImpl.ExecutionToken executionToken = this.cancellation;
                if (executionToken != null) {
                    executionToken.run();
                }
                this.cancellation = MediaTimeoutListener.this.mainExecutor.executeDelayed(j2, new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutListener$RecommendationListener$processUpdate$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaTimeoutListener.RecommendationListener.this.doTimeout();
                    }
                });
                this.expiration = this.recommendationData.expiryTimeMs;
            }
        }

        public final void doTimeout() {
            ExecutorImpl.ExecutionToken executionToken = this.cancellation;
            if (executionToken != null) {
                executionToken.run();
            }
            this.cancellation = null;
            MediaTimeoutListener mediaTimeoutListener = MediaTimeoutListener.this;
            MediaTimeoutLogger mediaTimeoutLogger = mediaTimeoutListener.logger;
            mediaTimeoutLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaTimeoutLogger$logTimeout$2 mediaTimeoutLogger$logTimeout$2 = MediaTimeoutLogger$logTimeout$2.INSTANCE;
            LogBuffer logBuffer = mediaTimeoutLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logTimeout$2, null);
            String str = this.key;
            obtain.setStr1(str);
            logBuffer.commit(obtain);
            this.timedOut = true;
            this.expiration = Long.MAX_VALUE;
            Function2 function2 = mediaTimeoutListener.timeoutCallback;
            (function2 != null ? function2 : null).invoke(str, true);
        }
    }

    public MediaTimeoutListener(MediaControllerFactory mediaControllerFactory, DelayableExecutor delayableExecutor, MediaTimeoutLogger mediaTimeoutLogger, SysuiStatusBarStateController sysuiStatusBarStateController, SystemClock systemClock, MediaFlags mediaFlags) {
        this.mediaControllerFactory = mediaControllerFactory;
        this.mainExecutor = delayableExecutor;
        this.logger = mediaTimeoutLogger;
        this.systemClock = systemClock;
        this.mediaFlags = mediaFlags;
        ((StatusBarStateControllerImpl) sysuiStatusBarStateController).addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutListener.1
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
                    if (playbackStateListener.cancellation != null) {
                        long j = playbackStateListener.expiration;
                        ((SystemClockImpl) systemClock2).getClass();
                        if (j <= android.os.SystemClock.elapsedRealtime()) {
                            playbackStateListener.expireMediaTimeout(str, "timeout happened while dozing");
                            playbackStateListener.doTimeout();
                        }
                    }
                }
                for (Map.Entry entry2 : ((LinkedHashMap) mediaTimeoutListener.recommendationListeners).entrySet()) {
                    String str2 = (String) entry2.getKey();
                    RecommendationListener recommendationListener = (RecommendationListener) entry2.getValue();
                    if (recommendationListener.cancellation != null) {
                        long j2 = recommendationListener.expiration;
                        ((SystemClockImpl) systemClock2).getClass();
                        if (j2 <= System.currentTimeMillis()) {
                            MediaTimeoutLogger mediaTimeoutLogger2 = mediaTimeoutListener.logger;
                            mediaTimeoutLogger2.getClass();
                            LogLevel logLevel = LogLevel.VERBOSE;
                            MediaTimeoutLogger$logTimeoutCancelled$2 mediaTimeoutLogger$logTimeoutCancelled$2 = MediaTimeoutLogger$logTimeoutCancelled$2.INSTANCE;
                            LogBuffer logBuffer = mediaTimeoutLogger2.buffer;
                            LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logTimeoutCancelled$2, null);
                            CarrierTextManagerLogger$$ExternalSyntheticOutline0.m83m(obtain, str2, "Timed out while dozing", logBuffer, obtain);
                            recommendationListener.doTimeout();
                        }
                    }
                }
            }
        });
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(final String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        Object obj;
        Map map = this.mediaListeners;
        PlaybackStateListener playbackStateListener = (PlaybackStateListener) ((LinkedHashMap) map).get(str);
        MediaTimeoutLogger mediaTimeoutLogger = this.logger;
        if (playbackStateListener == null) {
            obj = null;
        } else {
            if (!playbackStateListener.destroyed) {
                return;
            }
            mediaTimeoutLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaTimeoutLogger$logReuseListener$2 mediaTimeoutLogger$logReuseListener$2 = new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logReuseListener$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("reuse listener: ", ((LogMessage) obj2).getStr1());
                }
            };
            LogBuffer logBuffer = mediaTimeoutLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaTimeout", logLevel, mediaTimeoutLogger$logReuseListener$2, null);
            obtain.setStr1(str);
            logBuffer.commit(obtain);
            obj = playbackStateListener;
        }
        if ((str2 == null || Intrinsics.areEqual(str, str2)) ? false : true) {
            obj = TypeIntrinsics.asMutableMap(map).remove(str2);
            boolean z3 = obj != null;
            mediaTimeoutLogger.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            MediaTimeoutLogger$logMigrateListener$2 mediaTimeoutLogger$logMigrateListener$2 = new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logMigrateListener$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    LogMessage logMessage = (LogMessage) obj2;
                    String str1 = logMessage.getStr1();
                    String str22 = logMessage.getStr2();
                    boolean bool1 = logMessage.getBool1();
                    StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("migrate from ", str1, " to ", str22, ", had listener? ");
                    m87m.append(bool1);
                    return m87m.toString();
                }
            };
            LogBuffer logBuffer2 = mediaTimeoutLogger.buffer;
            LogMessage obtain2 = logBuffer2.obtain("MediaTimeout", logLevel2, mediaTimeoutLogger$logMigrateListener$2, null);
            obtain2.setStr1(str2);
            obtain2.setStr2(str);
            obtain2.setBool1(z3);
            logBuffer2.commit(obtain2);
        }
        PlaybackStateListener playbackStateListener2 = (PlaybackStateListener) obj;
        if (playbackStateListener2 == null) {
            map.put(str, new PlaybackStateListener(str, mediaData));
            return;
        }
        boolean isPlaying$1 = playbackStateListener2.isPlaying$1();
        mediaTimeoutLogger.getClass();
        LogLevel logLevel3 = LogLevel.DEBUG;
        MediaTimeoutLogger$logUpdateListener$2 mediaTimeoutLogger$logUpdateListener$2 = new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logUpdateListener$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                return "updating " + logMessage.getStr1() + ", was playing? " + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer3 = mediaTimeoutLogger.buffer;
        LogMessage obtain3 = logBuffer3.obtain("MediaTimeout", logLevel3, mediaTimeoutLogger$logUpdateListener$2, null);
        obtain3.setStr1(str);
        obtain3.setBool1(isPlaying$1);
        logBuffer3.commit(obtain3);
        playbackStateListener2.setMediaData(mediaData);
        playbackStateListener2.key = str;
        map.put(str, playbackStateListener2);
        if (isPlaying$1 != playbackStateListener2.isPlaying$1()) {
            ((ExecutorImpl) this.mainExecutor).execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutListener$onMediaDataLoaded$2$1
                /* JADX WARN: Code restructure failed: missing block: B:4:0x0015, code lost:
                
                    if (r0.isPlaying$1() == true) goto L8;
                 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    MediaTimeoutListener.PlaybackStateListener playbackStateListener3 = (MediaTimeoutListener.PlaybackStateListener) ((LinkedHashMap) MediaTimeoutListener.this.mediaListeners).get(str);
                    boolean z4 = playbackStateListener3 != null;
                    if (z4) {
                        MediaTimeoutLogger mediaTimeoutLogger2 = MediaTimeoutListener.this.logger;
                        String str3 = str;
                        mediaTimeoutLogger2.getClass();
                        LogLevel logLevel4 = LogLevel.DEBUG;
                        MediaTimeoutLogger$logDelayedUpdate$2 mediaTimeoutLogger$logDelayedUpdate$2 = new Function1() { // from class: com.android.systemui.media.controls.pipeline.MediaTimeoutLogger$logDelayedUpdate$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                return KeyAttributes$$ExternalSyntheticOutline0.m21m("deliver delayed playback state for ", ((LogMessage) obj2).getStr1());
                            }
                        };
                        LogBuffer logBuffer4 = mediaTimeoutLogger2.buffer;
                        LogMessage obtain4 = logBuffer4.obtain("MediaTimeout", logLevel4, mediaTimeoutLogger$logDelayedUpdate$2, null);
                        obtain4.setStr1(str3);
                        logBuffer4.commit(obtain4);
                        Function2 function2 = MediaTimeoutListener.this.timeoutCallback;
                        (function2 != null ? function2 : null).invoke(str, Boolean.FALSE);
                    }
                }
            });
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onMediaDataRemoved(String str) {
        PlaybackStateListener playbackStateListener = (PlaybackStateListener) this.mediaListeners.remove(str);
        if (playbackStateListener != null) {
            MediaController mediaController = playbackStateListener.mediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(playbackStateListener);
            }
            ExecutorImpl.ExecutionToken executionToken = playbackStateListener.cancellation;
            if (executionToken != null) {
                executionToken.run();
            }
            playbackStateListener.destroyed = true;
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        this.mediaFlags.isPersistentSsCardEnabled();
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        this.mediaFlags.isPersistentSsCardEnabled();
    }
}
