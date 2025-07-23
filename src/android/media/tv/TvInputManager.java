package android.media.tv;

import android.annotation.SystemApi;
import android.content.AttributionSource;
import android.content.Intent;
import android.graphics.Rect;
import android.media.AudioDeviceInfo;
import android.media.AudioPresentation;
import android.media.PlaybackParams;
import android.media.tv.ITvInputClient;
import android.media.tv.ITvInputHardwareCallback;
import android.media.tv.ITvInputManagerCallback;
import android.media.tv.TvInputManager;
import android.media.tv.ad.TvAdManager;
import android.media.tv.interactive.TvInteractiveAppManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pools;
import android.util.SparseArray;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventSender;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class TvInputManager {
    public static final String ACTION_BLOCKED_RATINGS_CHANGED = "android.media.tv.action.BLOCKED_RATINGS_CHANGED";
    public static final String ACTION_PARENTAL_CONTROLS_ENABLED_CHANGED = "android.media.tv.action.PARENTAL_CONTROLS_ENABLED_CHANGED";
    public static final String ACTION_QUERY_CONTENT_RATING_SYSTEMS = "android.media.tv.action.QUERY_CONTENT_RATING_SYSTEMS";
    public static final String ACTION_SETUP_INPUTS = "android.media.tv.action.SETUP_INPUTS";
    public static final String ACTION_VIEW_RECORDING_SCHEDULES = "android.media.tv.action.VIEW_RECORDING_SCHEDULES";
    public static final int BROADCAST_INFO_STREAM_EVENT = 5;
    public static final int BROADCAST_INFO_TYPE_COMMAND = 7;
    public static final int BROADCAST_INFO_TYPE_DSMCC = 6;
    public static final int BROADCAST_INFO_TYPE_PES = 4;
    public static final int BROADCAST_INFO_TYPE_SECTION = 3;
    public static final int BROADCAST_INFO_TYPE_SIGNALING_DATA = 9;
    public static final int BROADCAST_INFO_TYPE_TABLE = 2;
    public static final int BROADCAST_INFO_TYPE_TIMELINE = 8;
    public static final int BROADCAST_INFO_TYPE_TS = 1;
    public static final int DVB_DEVICE_DEMUX = 0;
    public static final int DVB_DEVICE_DVR = 1;
    static final int DVB_DEVICE_END = 2;
    public static final int DVB_DEVICE_FRONTEND = 2;
    static final int DVB_DEVICE_START = 0;
    public static final int INPUT_STATE_CONNECTED = 0;
    public static final int INPUT_STATE_CONNECTED_STANDBY = 1;
    public static final int INPUT_STATE_DISCONNECTED = 2;
    public static final String META_DATA_CONTENT_RATING_SYSTEMS = "android.media.tv.metadata.CONTENT_RATING_SYSTEMS";
    static final int RECORDING_ERROR_END = 2;
    public static final int RECORDING_ERROR_INSUFFICIENT_SPACE = 1;
    public static final int RECORDING_ERROR_RESOURCE_BUSY = 2;
    static final int RECORDING_ERROR_START = 0;
    public static final int RECORDING_ERROR_UNKNOWN = 0;
    public static final String SESSION_DATA_KEY_AD_BUFFER = "ad_buffer";
    public static final String SESSION_DATA_KEY_AD_RESPONSE = "ad_response";
    public static final String SESSION_DATA_KEY_BROADCAST_INFO_RESPONSE = "broadcast_info_response";
    public static final String SESSION_DATA_KEY_CHANNEL_URI = "channel_uri";
    public static final String SESSION_DATA_KEY_TRACKS = "tracks";
    public static final String SESSION_DATA_KEY_TRACK_ID = "track_id";
    public static final String SESSION_DATA_KEY_TRACK_TYPE = "track_type";
    public static final String SESSION_DATA_KEY_TV_MESSAGE_TYPE = "tv_message_type";
    public static final String SESSION_DATA_KEY_VIDEO_UNAVAILABLE_REASON = "video_unavailable_reason";
    public static final String SESSION_DATA_TYPE_AD_BUFFER_CONSUMED = "ad_buffer_consumed";
    public static final String SESSION_DATA_TYPE_AD_RESPONSE = "ad_response";
    public static final String SESSION_DATA_TYPE_BROADCAST_INFO_RESPONSE = "broadcast_info_response";
    public static final String SESSION_DATA_TYPE_TRACKS_CHANGED = "tracks_changed";
    public static final String SESSION_DATA_TYPE_TRACK_SELECTED = "track_selected";
    public static final String SESSION_DATA_TYPE_TUNED = "tuned";
    public static final String SESSION_DATA_TYPE_TV_MESSAGE = "tv_message";
    public static final String SESSION_DATA_TYPE_VIDEO_AVAILABLE = "video_available";
    public static final String SESSION_DATA_TYPE_VIDEO_UNAVAILABLE = "video_unavailable";
    public static final int SIGNAL_STRENGTH_LOST = 1;
    public static final int SIGNAL_STRENGTH_STRONG = 3;
    public static final int SIGNAL_STRENGTH_WEAK = 2;
    private static final String TAG = "TvInputManager";
    public static final long TIME_SHIFT_INVALID_TIME = Long.MIN_VALUE;
    public static final int TIME_SHIFT_MODE_AUTO = 4;
    public static final int TIME_SHIFT_MODE_LOCAL = 2;
    public static final int TIME_SHIFT_MODE_NETWORK = 3;
    public static final int TIME_SHIFT_MODE_OFF = 1;
    public static final int TIME_SHIFT_STATUS_AVAILABLE = 3;
    public static final int TIME_SHIFT_STATUS_UNAVAILABLE = 2;
    public static final int TIME_SHIFT_STATUS_UNKNOWN = 0;
    public static final int TIME_SHIFT_STATUS_UNSUPPORTED = 1;
    public static final long TV_MESSAGE_GROUP_ID_NONE = -1;
    public static final String TV_MESSAGE_KEY_GROUP_ID = "android.media.tv.TvInputManager.group_id";
    public static final String TV_MESSAGE_KEY_RAW_DATA = "android.media.tv.TvInputManager.raw_data";
    public static final String TV_MESSAGE_KEY_STREAM_ID = "android.media.tv.TvInputManager.stream_id";
    public static final String TV_MESSAGE_KEY_SUBTYPE = "android.media.tv.TvInputManager.subtype";
    public static final String TV_MESSAGE_SUBTYPE_CC_608E = "CTA 608-E";
    public static final String TV_MESSAGE_SUBTYPE_WATERMARKING_A335 = "ATSC A/335";
    public static final int TV_MESSAGE_TYPE_CLOSED_CAPTION = 2;
    public static final int TV_MESSAGE_TYPE_OTHER = 1000;
    public static final int TV_MESSAGE_TYPE_WATERMARK = 1;
    public static final int UNKNOWN_CLIENT_PID = -1;
    public static final int UNKNOWN_CLIENT_USER_ID = -1;
    public static final int VIDEO_UNAVAILABLE_REASON_AUDIO_ONLY = 4;
    public static final int VIDEO_UNAVAILABLE_REASON_BUFFERING = 3;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_BLACKOUT = 16;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_CARD_INVALID = 15;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_CARD_MUTE = 14;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_INSUFFICIENT_OUTPUT_PROTECTION = 7;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_LICENSE_EXPIRED = 10;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_NEED_ACTIVATION = 11;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_NEED_PAIRING = 12;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_NO_CARD = 13;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_NO_LICENSE = 9;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_PVR_RECORDING_NOT_ALLOWED = 8;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_REBOOTING = 17;
    public static final int VIDEO_UNAVAILABLE_REASON_CAS_UNKNOWN = 18;
    static final int VIDEO_UNAVAILABLE_REASON_END = 18;
    public static final int VIDEO_UNAVAILABLE_REASON_INSUFFICIENT_RESOURCE = 6;
    public static final int VIDEO_UNAVAILABLE_REASON_NOT_CONNECTED = 5;
    static final int VIDEO_UNAVAILABLE_REASON_START = 0;
    public static final int VIDEO_UNAVAILABLE_REASON_STOPPED = 19;
    public static final int VIDEO_UNAVAILABLE_REASON_TUNING = 1;
    public static final int VIDEO_UNAVAILABLE_REASON_UNKNOWN = 0;
    public static final int VIDEO_UNAVAILABLE_REASON_WEAK_SIGNAL = 2;
    private final List<TvInputCallbackRecord> mCallbackRecords;
    private final ITvInputClient mClient;
    private final Object mLock;
    private int mNextSeq;
    private final ITvInputManager mService;
    private final SparseArray<SessionCallbackRecord> mSessionCallbackRecordMap;
    private final Map<String, Integer> mStateMap;
    private final int mUserId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BroadcastInfoType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DvbDeviceType {
    }

    @SystemApi
    public static abstract class HardwareCallback {
        public abstract void onReleased();

        public abstract void onStreamConfigChanged(TvStreamConfig[] tvStreamConfigArr);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InputState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RecordingError {
    }

    public static abstract class SessionCallback {
        public void onAitInfoUpdated(Session session, AitInfo aitInfo) {
        }

        public void onAudioPresentationSelected(Session session, int i, int i2) {
        }

        public void onAudioPresentationsChanged(Session session, List<AudioPresentation> list) {
        }

        public void onAvailableSpeeds(Session session, float[] fArr) {
        }

        public void onChannelRetuned(Session session, Uri uri) {
        }

        public void onContentAllowed(Session session) {
        }

        public void onContentBlocked(Session session, TvContentRating tvContentRating) {
        }

        public void onCueingMessageAvailability(Session session, boolean z) {
        }

        void onError(Session session, int i) {
        }

        public void onLayoutSurface(Session session, int i, int i2, int i3, int i4) {
        }

        void onRecordingStopped(Session session, Uri uri) {
        }

        public void onSessionCreated(Session session) {
        }

        public void onSessionEvent(Session session, String str, Bundle bundle) {
        }

        public void onSessionReleased(Session session) {
        }

        public void onSignalStrengthUpdated(Session session, int i) {
        }

        public void onTimeShiftCurrentPositionChanged(Session session, long j) {
        }

        public void onTimeShiftMode(Session session, int i) {
        }

        public void onTimeShiftStartPositionChanged(Session session, long j) {
        }

        public void onTimeShiftStatusChanged(Session session, int i) {
        }

        public void onTrackSelected(Session session, int i, String str) {
        }

        public void onTracksChanged(Session session, List<TvTrackInfo> list) {
        }

        public void onTuned(Session session, Uri uri) {
        }

        public void onTvMessage(Session session, int i, Bundle bundle) {
        }

        public void onVideoAvailable(Session session) {
        }

        public void onVideoFreezeUpdated(Session session, boolean z) {
        }

        public void onVideoSizeChanged(Session session, int i, int i2) {
        }

        public void onVideoUnavailable(Session session, int i) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionDataKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionDataType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SignalStrength {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeShiftMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeShiftStatus {
    }

    public static abstract class TvInputCallback {
        @SystemApi
        public void onCurrentTunedInfosUpdated(List<TunedInfo> list) {
        }

        public void onInputAdded(String str) {
        }

        public void onInputRemoved(String str) {
        }

        public void onInputStateChanged(String str, int i) {
        }

        public void onInputUpdated(String str) {
        }

        public void onTvInputInfoUpdated(TvInputInfo tvInputInfo) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TvMessageType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoUnavailableReason {
    }

    private boolean isValidUseCase(int i) {
        return i == 100 || i == 200 || i == 300 || i == 400 || i == 500;
    }

    private static final class SessionCallbackRecord {
        private final Handler mHandler;
        private Session mSession;
        private final SessionCallback mSessionCallback;

        SessionCallbackRecord(SessionCallback sessionCallback, Handler handler) {
            this.mSessionCallback = sessionCallback;
            this.mHandler = handler;
        }

        void postSessionCreated(final Session session) {
            this.mSession = session;
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionCreated(session);
                }
            });
        }

        void postSessionReleased() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionReleased(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postChannelRetuned(final Uri uri) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onChannelRetuned(SessionCallbackRecord.this.mSession, uri);
                }
            });
        }

        void postAudioPresentationsChanged(final List<AudioPresentation> list) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onAudioPresentationsChanged(SessionCallbackRecord.this.mSession, list);
                }
            });
        }

        void postAudioPresentationSelected(final int i, final int i2) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onAudioPresentationSelected(SessionCallbackRecord.this.mSession, i, i2);
                }
            });
        }

        void postTracksChanged(final List<TvTrackInfo> list) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.6
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTracksChanged(SessionCallbackRecord.this.mSession, list);
                    if (!SessionCallbackRecord.this.mSession.mIAppNotificationEnabled || SessionCallbackRecord.this.mSession.getInteractiveAppSession() == null) {
                        return;
                    }
                    SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyTracksChanged(list);
                }
            });
        }

        void postTrackSelected(final int i, final String str) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.7
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTrackSelected(SessionCallbackRecord.this.mSession, i, str);
                    if (!SessionCallbackRecord.this.mSession.mIAppNotificationEnabled || SessionCallbackRecord.this.mSession.getInteractiveAppSession() == null) {
                        return;
                    }
                    SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyTrackSelected(i, str);
                }
            });
        }

        void postVideoSizeChanged(final int i, final int i2) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.8
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onVideoSizeChanged(SessionCallbackRecord.this.mSession, i, i2);
                }
            });
        }

        void postVideoAvailable() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.9
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onVideoAvailable(SessionCallbackRecord.this.mSession);
                    if (!SessionCallbackRecord.this.mSession.mIAppNotificationEnabled || SessionCallbackRecord.this.mSession.getInteractiveAppSession() == null) {
                        return;
                    }
                    SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyVideoAvailable();
                }
            });
        }

        void postVideoUnavailable(final int i) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.10
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onVideoUnavailable(SessionCallbackRecord.this.mSession, i);
                    if (!SessionCallbackRecord.this.mSession.mIAppNotificationEnabled || SessionCallbackRecord.this.mSession.getInteractiveAppSession() == null) {
                        return;
                    }
                    SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyVideoUnavailable(i);
                }
            });
        }

        void postVideoFreezeUpdated(final boolean z) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.11
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onVideoFreezeUpdated(SessionCallbackRecord.this.mSession, z);
                    if (!SessionCallbackRecord.this.mSession.mIAppNotificationEnabled || SessionCallbackRecord.this.mSession.getInteractiveAppSession() == null) {
                        return;
                    }
                    SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyVideoFreezeUpdated(z);
                }
            });
        }

        void postContentAllowed() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.12
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onContentAllowed(SessionCallbackRecord.this.mSession);
                    if (!SessionCallbackRecord.this.mSession.mIAppNotificationEnabled || SessionCallbackRecord.this.mSession.getInteractiveAppSession() == null) {
                        return;
                    }
                    SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyContentAllowed();
                }
            });
        }

        void postContentBlocked(final TvContentRating tvContentRating) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.13
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onContentBlocked(SessionCallbackRecord.this.mSession, tvContentRating);
                    if (!SessionCallbackRecord.this.mSession.mIAppNotificationEnabled || SessionCallbackRecord.this.mSession.getInteractiveAppSession() == null) {
                        return;
                    }
                    SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyContentBlocked(tvContentRating);
                }
            });
        }

        void postLayoutSurface(final int i, final int i2, final int i3, final int i4) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.14
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onLayoutSurface(SessionCallbackRecord.this.mSession, i, i2, i3, i4);
                }
            });
        }

        void postSessionEvent(final String str, final Bundle bundle) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.15
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionEvent(SessionCallbackRecord.this.mSession, str, bundle);
                }
            });
        }

        void postTimeShiftStatusChanged(final int i) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.16
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTimeShiftStatusChanged(SessionCallbackRecord.this.mSession, i);
                }
            });
        }

        void postTimeShiftStartPositionChanged(final long j) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.17
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTimeShiftStartPositionChanged(SessionCallbackRecord.this.mSession, j);
                }
            });
        }

        void postTimeShiftCurrentPositionChanged(final long j) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.18
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTimeShiftCurrentPositionChanged(SessionCallbackRecord.this.mSession, j);
                }
            });
        }

        void postAitInfoUpdated(final AitInfo aitInfo) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.19
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onAitInfoUpdated(SessionCallbackRecord.this.mSession, aitInfo);
                }
            });
        }

        void postSignalStrength(final int i) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.20
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSignalStrengthUpdated(SessionCallbackRecord.this.mSession, i);
                    if (!SessionCallbackRecord.this.mSession.mIAppNotificationEnabled || SessionCallbackRecord.this.mSession.getInteractiveAppSession() == null) {
                        return;
                    }
                    SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifySignalStrength(i);
                }
            });
        }

        void postCueingMessageAvailability(final boolean z) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.21
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onCueingMessageAvailability(SessionCallbackRecord.this.mSession, z);
                }
            });
        }

        void postTimeShiftMode(final int i) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.22
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTimeShiftMode(SessionCallbackRecord.this.mSession, i);
                }
            });
        }

        void postAvailableSpeeds(final float[] fArr) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.23
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onAvailableSpeeds(SessionCallbackRecord.this.mSession, fArr);
                }
            });
        }

        void postTuned(final Uri uri) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.24
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTuned(SessionCallbackRecord.this.mSession, uri);
                    if (!SessionCallbackRecord.this.mSession.mIAppNotificationEnabled || SessionCallbackRecord.this.mSession.getInteractiveAppSession() == null) {
                        return;
                    }
                    SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyTuned(uri);
                }
            });
        }

        void postTvMessage(final int i, final Bundle bundle) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.25
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTvMessage(SessionCallbackRecord.this.mSession, i, bundle);
                    if (!SessionCallbackRecord.this.mSession.mIAppNotificationEnabled || SessionCallbackRecord.this.mSession.getInteractiveAppSession() == null) {
                        return;
                    }
                    SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyTvMessage(i, bundle);
                }
            });
        }

        void postRecordingStopped(final Uri uri) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.26
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRecordingStopped(SessionCallbackRecord.this.mSession, uri);
                }
            });
        }

        void postError(final int i) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.27
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onError(SessionCallbackRecord.this.mSession, i);
                }
            });
        }

        void postBroadcastInfoResponse(final BroadcastInfoResponse broadcastInfoResponse) {
            if (this.mSession.mIAppNotificationEnabled) {
                this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.28
                    @Override // java.lang.Runnable
                    public void run() {
                        if (SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                            SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyBroadcastInfoResponse(broadcastInfoResponse);
                        }
                    }
                });
            }
        }

        void postAdResponse(final AdResponse adResponse) {
            if (this.mSession.mIAppNotificationEnabled) {
                this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.29
                    @Override // java.lang.Runnable
                    public void run() {
                        if (SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                            SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyAdResponse(adResponse);
                        }
                    }
                });
            }
        }

        void postAdBufferConsumed(final AdBuffer adBuffer) {
            if (this.mSession.mIAppNotificationEnabled) {
                this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.30
                    @Override // java.lang.Runnable
                    public void run() {
                        if (SessionCallbackRecord.this.mSession.getInteractiveAppSession() != null) {
                            SessionCallbackRecord.this.mSession.getInteractiveAppSession().notifyAdBufferConsumed(adBuffer);
                        }
                    }
                });
            }
        }

        void postTvInputSessionData(final String str, final Bundle bundle) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.SessionCallbackRecord.31
                @Override // java.lang.Runnable
                public void run() {
                    if (SessionCallbackRecord.this.mSession.getAdSession() != null) {
                        SessionCallbackRecord.this.mSession.getAdSession().notifyTvInputSessionData(str, bundle);
                    }
                }
            });
        }
    }

    private static final class TvInputCallbackRecord {
        private final TvInputCallback mCallback;
        private final Handler mHandler;

        public TvInputCallbackRecord(TvInputCallback tvInputCallback, Handler handler) {
            this.mCallback = tvInputCallback;
            this.mHandler = handler;
        }

        public TvInputCallback getCallback() {
            return this.mCallback;
        }

        public void postInputAdded(final String str) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.TvInputCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    TvInputCallbackRecord.this.mCallback.onInputAdded(str);
                }
            });
        }

        public void postInputRemoved(final String str) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.TvInputCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    TvInputCallbackRecord.this.mCallback.onInputRemoved(str);
                }
            });
        }

        public void postInputUpdated(final String str) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.TvInputCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    TvInputCallbackRecord.this.mCallback.onInputUpdated(str);
                }
            });
        }

        public void postInputStateChanged(final String str, final int i) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.TvInputCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    TvInputCallbackRecord.this.mCallback.onInputStateChanged(str, i);
                }
            });
        }

        public void postTvInputInfoUpdated(final TvInputInfo tvInputInfo) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.TvInputCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    TvInputCallbackRecord.this.mCallback.onTvInputInfoUpdated(tvInputInfo);
                }
            });
        }

        public void postCurrentTunedInfosUpdated(final List<TunedInfo> list) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.TvInputManager.TvInputCallbackRecord.6
                @Override // java.lang.Runnable
                public void run() {
                    TvInputCallbackRecord.this.mCallback.onCurrentTunedInfosUpdated(list);
                }
            });
        }
    }

    public TvInputManager(ITvInputManager iTvInputManager, int i) {
        Object obj = new Object();
        this.mLock = obj;
        this.mCallbackRecords = new ArrayList();
        this.mStateMap = new ArrayMap();
        this.mSessionCallbackRecordMap = new SparseArray<>();
        this.mService = iTvInputManager;
        this.mUserId = i;
        this.mClient = new ITvInputClient.Stub() { // from class: android.media.tv.TvInputManager.1
            @Override // android.media.tv.ITvInputClient
            public void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i2) {
                Session session;
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for " + iBinder);
                    } else {
                        if (iBinder != null) {
                            session = new Session(iBinder, inputChannel, TvInputManager.this.mService, TvInputManager.this.mUserId, i2, TvInputManager.this.mSessionCallbackRecordMap);
                        } else {
                            TvInputManager.this.mSessionCallbackRecordMap.delete(i2);
                            session = null;
                        }
                        sessionCallbackRecord.postSessionCreated(session);
                    }
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onSessionReleased(int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    TvInputManager.this.mSessionCallbackRecordMap.delete(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq:" + i2);
                    } else {
                        sessionCallbackRecord.mSession.releaseInternal();
                        sessionCallbackRecord.postSessionReleased();
                    }
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onChannelRetuned(Uri uri, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postChannelRetuned(uri);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAudioPresentationsChanged(List<AudioPresentation> list, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                    } else {
                        if (sessionCallbackRecord.mSession.updateAudioPresentations(list)) {
                            sessionCallbackRecord.postAudioPresentationsChanged(list);
                        }
                    }
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAudioPresentationSelected(int i2, int i3, int i4) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i4);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i4);
                    } else {
                        if (sessionCallbackRecord.mSession.updateAudioPresentationSelection(i2, i3)) {
                            sessionCallbackRecord.postAudioPresentationSelected(i2, i3);
                        }
                    }
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTracksChanged(List<TvTrackInfo> list, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                    } else {
                        if (sessionCallbackRecord.mSession.updateTracks(list)) {
                            sessionCallbackRecord.postTracksChanged(list);
                            postVideoSizeChangedIfNeededLocked(sessionCallbackRecord);
                        }
                    }
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTrackSelected(int i2, String str, int i3) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i3);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i3);
                    } else {
                        if (sessionCallbackRecord.mSession.updateTrackSelection(i2, str)) {
                            sessionCallbackRecord.postTrackSelected(i2, str);
                            postVideoSizeChangedIfNeededLocked(sessionCallbackRecord);
                        }
                    }
                }
            }

            private void postVideoSizeChangedIfNeededLocked(SessionCallbackRecord sessionCallbackRecord) {
                TvTrackInfo videoTrackToNotify = sessionCallbackRecord.mSession.getVideoTrackToNotify();
                if (videoTrackToNotify != null) {
                    sessionCallbackRecord.postVideoSizeChanged(videoTrackToNotify.getVideoWidth(), videoTrackToNotify.getVideoHeight());
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onVideoAvailable(int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postVideoAvailable();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onVideoUnavailable(int i2, int i3) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i3);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i3);
                        return;
                    }
                    sessionCallbackRecord.postVideoUnavailable(i2);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onVideoFreezeUpdated(boolean z, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postVideoFreezeUpdated(z);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onContentAllowed(int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postContentAllowed();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onContentBlocked(String str, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postContentBlocked(TvContentRating.unflattenFromString(str));
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onLayoutSurface(int i2, int i3, int i4, int i5, int i6) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i6);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i6);
                        return;
                    }
                    sessionCallbackRecord.postLayoutSurface(i2, i3, i4, i5);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onSessionEvent(String str, Bundle bundle, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postSessionEvent(str, bundle);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftStatusChanged(int i2, int i3) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i3);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i3);
                        return;
                    }
                    sessionCallbackRecord.postTimeShiftStatusChanged(i2);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftStartPositionChanged(long j, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postTimeShiftStartPositionChanged(j);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftCurrentPositionChanged(long j, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postTimeShiftCurrentPositionChanged(j);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAitInfoUpdated(AitInfo aitInfo, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postAitInfoUpdated(aitInfo);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onSignalStrength(int i2, int i3) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i3);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i3);
                        return;
                    }
                    sessionCallbackRecord.postSignalStrength(i2);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onCueingMessageAvailability(boolean z, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postCueingMessageAvailability(z);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftMode(int i2, int i3) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i3);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i3);
                        return;
                    }
                    sessionCallbackRecord.postTimeShiftMode(i2);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAvailableSpeeds(float[] fArr, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postAvailableSpeeds(fArr);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTuned(Uri uri, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postTuned(uri);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTvMessage(int i2, Bundle bundle, int i3) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i3);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i3);
                        return;
                    }
                    sessionCallbackRecord.postTvMessage(i2, bundle);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onRecordingStopped(Uri uri, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postRecordingStopped(uri);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onError(int i2, int i3) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i3);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i3);
                        return;
                    }
                    sessionCallbackRecord.postError(i2);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postBroadcastInfoResponse(broadcastInfoResponse);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAdResponse(AdResponse adResponse, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postAdResponse(adResponse);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAdBufferConsumed(AdBuffer adBuffer, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postAdBufferConsumed(adBuffer);
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTvInputSessionData(String str, Bundle bundle, int i2) {
                synchronized (TvInputManager.this.mSessionCallbackRecordMap) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInputManager.this.mSessionCallbackRecordMap.get(i2);
                    if (sessionCallbackRecord == null) {
                        Log.e(TvInputManager.TAG, "Callback not found for seq " + i2);
                        return;
                    }
                    sessionCallbackRecord.postTvInputSessionData(str, bundle);
                }
            }
        };
        ITvInputManagerCallback.Stub stub = new ITvInputManagerCallback.Stub() { // from class: android.media.tv.TvInputManager.2
            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputAdded(String str) {
                synchronized (TvInputManager.this.mLock) {
                    TvInputManager.this.mStateMap.put(str, 0);
                    Iterator it = TvInputManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvInputCallbackRecord) it.next()).postInputAdded(str);
                    }
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputRemoved(String str) {
                synchronized (TvInputManager.this.mLock) {
                    TvInputManager.this.mStateMap.remove(str);
                    Iterator it = TvInputManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvInputCallbackRecord) it.next()).postInputRemoved(str);
                    }
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputUpdated(String str) {
                synchronized (TvInputManager.this.mLock) {
                    Iterator it = TvInputManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvInputCallbackRecord) it.next()).postInputUpdated(str);
                    }
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputStateChanged(String str, int i2) {
                synchronized (TvInputManager.this.mLock) {
                    TvInputManager.this.mStateMap.put(str, Integer.valueOf(i2));
                    Iterator it = TvInputManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvInputCallbackRecord) it.next()).postInputStateChanged(str, i2);
                    }
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onTvInputInfoUpdated(TvInputInfo tvInputInfo) {
                synchronized (TvInputManager.this.mLock) {
                    Iterator it = TvInputManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvInputCallbackRecord) it.next()).postTvInputInfoUpdated(tvInputInfo);
                    }
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onCurrentTunedInfosUpdated(List<TunedInfo> list) {
                synchronized (TvInputManager.this.mLock) {
                    Iterator it = TvInputManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvInputCallbackRecord) it.next()).postCurrentTunedInfosUpdated(list);
                    }
                }
            }
        };
        if (iTvInputManager != null) {
            try {
                iTvInputManager.registerCallback(stub, i);
                List<TvInputInfo> tvInputList = iTvInputManager.getTvInputList(i);
                synchronized (obj) {
                    Iterator<TvInputInfo> it = tvInputList.iterator();
                    while (it.hasNext()) {
                        String id = it.next().getId();
                        this.mStateMap.put(id, Integer.valueOf(this.mService.getTvInputState(id, this.mUserId)));
                    }
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<TvInputInfo> getTvInputList() {
        try {
            return this.mService.getTvInputList(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public TvInputInfo getTvInputInfo(String str) {
        Preconditions.checkNotNull(str);
        try {
            return this.mService.getTvInputInfo(str, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateTvInputInfo(TvInputInfo tvInputInfo) {
        Preconditions.checkNotNull(tvInputInfo);
        try {
            this.mService.updateTvInputInfo(tvInputInfo, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getInputState(String str) {
        Preconditions.checkNotNull(str);
        synchronized (this.mLock) {
            Integer num = this.mStateMap.get(str);
            if (num == null) {
                Log.w(TAG, "Unrecognized input ID: " + str);
                return 2;
            }
            return num.intValue();
        }
    }

    @SystemApi
    public List<String> getAvailableExtensionInterfaceNames(String str) {
        Preconditions.checkNotNull(str);
        try {
            return this.mService.getAvailableExtensionInterfaceNames(str, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public IBinder getExtensionInterface(String str, String str2) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(str2);
        try {
            return this.mService.getExtensionInterface(str, str2, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(TvInputCallback tvInputCallback, Handler handler) {
        Preconditions.checkNotNull(tvInputCallback);
        Preconditions.checkNotNull(handler);
        synchronized (this.mLock) {
            this.mCallbackRecords.add(new TvInputCallbackRecord(tvInputCallback, handler));
        }
    }

    public void unregisterCallback(TvInputCallback tvInputCallback) {
        Preconditions.checkNotNull(tvInputCallback);
        synchronized (this.mLock) {
            Iterator<TvInputCallbackRecord> it = this.mCallbackRecords.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getCallback() == tvInputCallback) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public boolean isParentalControlsEnabled() {
        try {
            return this.mService.isParentalControlsEnabled(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setParentalControlsEnabled(boolean z) {
        try {
            this.mService.setParentalControlsEnabled(z, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRatingBlocked(TvContentRating tvContentRating) {
        Preconditions.checkNotNull(tvContentRating);
        try {
            return this.mService.isRatingBlocked(tvContentRating.flattenToString(), this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<TvContentRating> getBlockedRatings() {
        try {
            ArrayList arrayList = new ArrayList();
            Iterator<String> it = this.mService.getBlockedRatings(this.mUserId).iterator();
            while (it.hasNext()) {
                arrayList.add(TvContentRating.unflattenFromString(it.next()));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void addBlockedRating(TvContentRating tvContentRating) {
        Preconditions.checkNotNull(tvContentRating);
        try {
            this.mService.addBlockedRating(tvContentRating.flattenToString(), this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void removeBlockedRating(TvContentRating tvContentRating) {
        Preconditions.checkNotNull(tvContentRating);
        try {
            this.mService.removeBlockedRating(tvContentRating.flattenToString(), this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<TvContentRatingSystemInfo> getTvContentRatingSystemList() {
        try {
            return this.mService.getTvContentRatingSystemList(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void notifyPreviewProgramBrowsableDisabled(String str, long j) {
        Intent intent = new Intent();
        intent.setAction(TvContract.ACTION_PREVIEW_PROGRAM_BROWSABLE_DISABLED);
        intent.putExtra(TvContract.EXTRA_PREVIEW_PROGRAM_ID, j);
        intent.setPackage(str);
        try {
            this.mService.sendTvInputNotifyIntent(intent, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void notifyWatchNextProgramBrowsableDisabled(String str, long j) {
        Intent intent = new Intent();
        intent.setAction(TvContract.ACTION_WATCH_NEXT_PROGRAM_BROWSABLE_DISABLED);
        intent.putExtra(TvContract.EXTRA_WATCH_NEXT_PROGRAM_ID, j);
        intent.setPackage(str);
        try {
            this.mService.sendTvInputNotifyIntent(intent, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void notifyPreviewProgramAddedToWatchNext(String str, long j, long j2) {
        Intent intent = new Intent();
        intent.setAction(TvContract.ACTION_PREVIEW_PROGRAM_ADDED_TO_WATCH_NEXT);
        intent.putExtra(TvContract.EXTRA_PREVIEW_PROGRAM_ID, j);
        intent.putExtra(TvContract.EXTRA_WATCH_NEXT_PROGRAM_ID, j2);
        intent.setPackage(str);
        try {
            this.mService.sendTvInputNotifyIntent(intent, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createSession(String str, AttributionSource attributionSource, SessionCallback sessionCallback, Handler handler) {
        createSessionInternal(str, attributionSource, false, sessionCallback, handler);
    }

    @SystemApi
    public int getClientPid(String str) {
        return getClientPidInternal(str);
    }

    @SystemApi
    public int getClientUserId(String str) {
        return getClientUserIdInternal(str);
    }

    @SystemApi
    public int getClientPriority(int i, String str) {
        Preconditions.checkNotNull(str);
        if (!isValidUseCase(i)) {
            throw new IllegalArgumentException("Invalid use case: " + i);
        }
        return getClientPriorityInternal(i, str);
    }

    @SystemApi
    public int getClientPriority(int i) {
        if (!isValidUseCase(i)) {
            throw new IllegalArgumentException("Invalid use case: " + i);
        }
        return getClientPriorityInternal(i, null);
    }

    public void createRecordingSession(String str, SessionCallback sessionCallback, Handler handler) {
        createSessionInternal(str, null, true, sessionCallback, handler);
    }

    private void createSessionInternal(String str, AttributionSource attributionSource, boolean z, SessionCallback sessionCallback, Handler handler) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(sessionCallback);
        Preconditions.checkNotNull(handler);
        SessionCallbackRecord sessionCallbackRecord = new SessionCallbackRecord(sessionCallback, handler);
        synchronized (this.mSessionCallbackRecordMap) {
            int i = this.mNextSeq;
            this.mNextSeq = i + 1;
            this.mSessionCallbackRecordMap.put(i, sessionCallbackRecord);
            try {
                this.mService.createSession(this.mClient, str, attributionSource, z, i, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private int getClientPidInternal(String str) {
        Preconditions.checkNotNull(str);
        try {
            return this.mService.getClientPid(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int getClientUserIdInternal(String str) {
        Preconditions.checkNotNull(str);
        try {
            return this.mService.getClientUserId(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int getClientPriorityInternal(int i, String str) {
        try {
            return this.mService.getClientPriority(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<TvStreamConfig> getAvailableTvStreamConfigList(String str) {
        try {
            return this.mService.getAvailableTvStreamConfigList(str, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean captureFrame(String str, Surface surface, TvStreamConfig tvStreamConfig) {
        try {
            return this.mService.captureFrame(str, surface, tvStreamConfig, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isSingleSessionActive() {
        try {
            return this.mService.isSingleSessionActive(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<TvInputHardwareInfo> getHardwareList() {
        try {
            return this.mService.getHardwareList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Hardware acquireTvInputHardware(int i, HardwareCallback hardwareCallback, TvInputInfo tvInputInfo) {
        return acquireTvInputHardware(i, tvInputInfo, hardwareCallback);
    }

    @SystemApi
    public Hardware acquireTvInputHardware(int i, TvInputInfo tvInputInfo, HardwareCallback hardwareCallback) {
        Preconditions.checkNotNull(tvInputInfo);
        Preconditions.checkNotNull(hardwareCallback);
        return acquireTvInputHardwareInternal(i, tvInputInfo, null, 400, new Executor(this) { // from class: android.media.tv.TvInputManager.3
            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                runnable.run();
            }
        }, hardwareCallback);
    }

    @SystemApi
    public Hardware acquireTvInputHardware(int i, TvInputInfo tvInputInfo, String str, int i2, Executor executor, HardwareCallback hardwareCallback) {
        Preconditions.checkNotNull(tvInputInfo);
        Preconditions.checkNotNull(hardwareCallback);
        return acquireTvInputHardwareInternal(i, tvInputInfo, str, i2, executor, hardwareCallback);
    }

    public void addHardwareDevice(int i) {
        try {
            this.mService.addHardwareDevice(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeHardwareDevice(int i) {
        try {
            this.mService.removeHardwareDevice(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.media.tv.TvInputManager$4, reason: invalid class name */
    class AnonymousClass4 extends ITvInputHardwareCallback.Stub {
        final /* synthetic */ HardwareCallback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass4(TvInputManager tvInputManager, Executor executor, HardwareCallback hardwareCallback) {
            this.val$executor = executor;
            this.val$callback = hardwareCallback;
        }

        @Override // android.media.tv.ITvInputHardwareCallback
        public void onReleased() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final HardwareCallback hardwareCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.media.tv.TvInputManager$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TvInputManager.HardwareCallback.this.onReleased();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.media.tv.ITvInputHardwareCallback
        public void onStreamConfigChanged(final TvStreamConfig[] tvStreamConfigArr) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final HardwareCallback hardwareCallback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.media.tv.TvInputManager$4$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        TvInputManager.HardwareCallback.this.onStreamConfigChanged(tvStreamConfigArr);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private Hardware acquireTvInputHardwareInternal(int i, TvInputInfo tvInputInfo, String str, int i2, Executor executor, HardwareCallback hardwareCallback) {
        try {
            ITvInputHardware acquireTvInputHardware = this.mService.acquireTvInputHardware(i, new AnonymousClass4(this, executor, hardwareCallback), tvInputInfo, this.mUserId, str, i2);
            if (acquireTvInputHardware == null) {
                return null;
            }
            return new Hardware(acquireTvInputHardware);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void releaseTvInputHardware(int i, Hardware hardware) {
        try {
            this.mService.releaseTvInputHardware(i, hardware.getInterface(), this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<DvbDeviceInfo> getDvbDeviceList() {
        try {
            return this.mService.getDvbDeviceList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public ParcelFileDescriptor openDvbDevice(DvbDeviceInfo dvbDeviceInfo, int i) {
        try {
            if (i < 0 || 2 < i) {
                throw new IllegalArgumentException("Invalid DVB device: " + i);
            }
            return this.mService.openDvbDevice(dvbDeviceInfo, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestChannelBrowsable(Uri uri) {
        try {
            this.mService.requestChannelBrowsable(uri, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<TunedInfo> getCurrentTunedInfos() {
        try {
            return this.mService.getCurrentTunedInfos(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final class Session {
        static final int DISPATCH_HANDLED = 1;
        static final int DISPATCH_IN_PROGRESS = -1;
        static final int DISPATCH_NOT_HANDLED = 0;
        private static final long INPUT_SESSION_NOT_RESPONDING_TIMEOUT = 2500;
        private TvAdManager.Session mAdSession;
        private final List<AudioPresentation> mAudioPresentations;
        private final List<TvTrackInfo> mAudioTracks;
        private InputChannel mChannel;
        private final InputEventHandler mHandler;
        private boolean mIAppNotificationEnabled;
        private TvInteractiveAppManager.Session mIAppSession;
        private final Object mMetadataLock;
        private final Pools.Pool<PendingEvent> mPendingEventPool;
        private final SparseArray<PendingEvent> mPendingEvents;
        private int mSelectedAudioPresentationId;
        private int mSelectedAudioProgramId;
        private String mSelectedAudioTrackId;
        private String mSelectedSubtitleTrackId;
        private String mSelectedVideoTrackId;
        private TvInputEventSender mSender;
        private final int mSeq;
        private final ITvInputManager mService;
        private final SparseArray<SessionCallbackRecord> mSessionCallbackRecordMap;
        private final List<TvTrackInfo> mSubtitleTracks;
        private IBinder mToken;
        private final int mUserId;
        private int mVideoHeight;
        private final List<TvTrackInfo> mVideoTracks;
        private int mVideoWidth;

        public interface FinishedInputEventCallback {
            void onFinishedInputEvent(Object obj, boolean z);
        }

        private Session(IBinder iBinder, InputChannel inputChannel, ITvInputManager iTvInputManager, int i, int i2, SparseArray<SessionCallbackRecord> sparseArray) {
            this.mHandler = new InputEventHandler(Looper.getMainLooper());
            this.mPendingEventPool = new Pools.SimplePool(20);
            this.mPendingEvents = new SparseArray<>(20);
            this.mMetadataLock = new Object();
            this.mAudioPresentations = new ArrayList();
            this.mAudioTracks = new ArrayList();
            this.mVideoTracks = new ArrayList();
            this.mSubtitleTracks = new ArrayList();
            this.mSelectedAudioProgramId = -1;
            this.mSelectedAudioPresentationId = -1;
            this.mIAppNotificationEnabled = false;
            this.mToken = iBinder;
            this.mChannel = inputChannel;
            this.mService = iTvInputManager;
            this.mUserId = i;
            this.mSeq = i2;
            this.mSessionCallbackRecordMap = sparseArray;
        }

        public TvInteractiveAppManager.Session getInteractiveAppSession() {
            return this.mIAppSession;
        }

        public void setInteractiveAppSession(TvInteractiveAppManager.Session session) {
            this.mIAppSession = session;
        }

        public TvAdManager.Session getAdSession() {
            return this.mAdSession;
        }

        public void setAdSession(TvAdManager.Session session) {
            this.mAdSession = session;
        }

        public void release() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.releaseSession(iBinder, this.mUserId);
                releaseInternal();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void setMain() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setMainSession(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setSurface(Surface surface) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setSurface(iBinder, surface, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void dispatchSurfaceChanged(int i, int i2, int i3) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.dispatchSurfaceChanged(iBinder, i, i2, i3, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setStreamVolume(float f) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                if (f < 0.0f || f > 1.0f) {
                    throw new IllegalArgumentException("volume should be between 0.0f and 1.0f");
                }
                this.mService.setVolume(iBinder, f, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void tune(Uri uri) {
            tune(uri, null);
        }

        public void tune(Uri uri, Bundle bundle) {
            Preconditions.checkNotNull(uri);
            if (this.mToken == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            synchronized (this.mMetadataLock) {
                this.mAudioPresentations.clear();
                this.mAudioTracks.clear();
                this.mVideoTracks.clear();
                this.mSubtitleTracks.clear();
                this.mSelectedAudioProgramId = -1;
                this.mSelectedAudioPresentationId = -1;
                this.mSelectedAudioTrackId = null;
                this.mSelectedVideoTrackId = null;
                this.mSelectedSubtitleTrackId = null;
                this.mVideoWidth = 0;
                this.mVideoHeight = 0;
            }
            try {
                this.mService.tune(this.mToken, uri, bundle, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setCaptionEnabled(boolean z) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setCaptionEnabled(iBinder, z, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void selectAudioPresentation(int i, int i2) {
            synchronized (this.mMetadataLock) {
                if (i != -1) {
                    if (!containsAudioPresentation(this.mAudioPresentations, i)) {
                        Log.w(TvInputManager.TAG, "Invalid audio presentation id: " + i);
                        return;
                    }
                }
                IBinder iBinder = this.mToken;
                if (iBinder == null) {
                    Log.w(TvInputManager.TAG, "The session has been already released");
                    return;
                }
                try {
                    this.mService.selectAudioPresentation(iBinder, i, i2, this.mUserId);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        private boolean containsAudioPresentation(List<AudioPresentation> list, int i) {
            synchronized (this.mMetadataLock) {
                Iterator<AudioPresentation> it = list.iterator();
                while (it.hasNext()) {
                    if (it.next().getPresentationId() == i) {
                        return true;
                    }
                }
                return false;
            }
        }

        public List<AudioPresentation> getAudioPresentations() {
            synchronized (this.mMetadataLock) {
                if (this.mAudioPresentations == null) {
                    return new ArrayList();
                }
                return new ArrayList(this.mAudioPresentations);
            }
        }

        public int getSelectedProgramId() {
            int i;
            synchronized (this.mMetadataLock) {
                i = this.mSelectedAudioProgramId;
            }
            return i;
        }

        public int getSelectedAudioPresentationId() {
            int i;
            synchronized (this.mMetadataLock) {
                i = this.mSelectedAudioPresentationId;
            }
            return i;
        }

        boolean updateAudioPresentations(List<AudioPresentation> list) {
            boolean z;
            synchronized (this.mMetadataLock) {
                this.mAudioPresentations.clear();
                Iterator<AudioPresentation> it = list.iterator();
                while (it.hasNext()) {
                    this.mAudioPresentations.add(it.next());
                }
                z = !this.mAudioPresentations.isEmpty();
            }
            return z;
        }

        boolean updateAudioPresentationSelection(int i, int i2) {
            synchronized (this.mMetadataLock) {
                if (i2 == this.mSelectedAudioProgramId && i == this.mSelectedAudioPresentationId) {
                    return false;
                }
                this.mSelectedAudioPresentationId = i;
                this.mSelectedAudioProgramId = i2;
                return true;
            }
        }

        public void selectTrack(int i, String str) {
            synchronized (this.mMetadataLock) {
                if (i == 0) {
                    if (str != null && !containsTrack(this.mAudioTracks, str)) {
                        Log.w(TvInputManager.TAG, "Invalid audio trackId: " + str);
                        return;
                    }
                } else if (i == 1) {
                    if (str != null && !containsTrack(this.mVideoTracks, str)) {
                        Log.w(TvInputManager.TAG, "Invalid video trackId: " + str);
                        return;
                    }
                } else if (i == 2) {
                    if (str != null && !containsTrack(this.mSubtitleTracks, str)) {
                        Log.w(TvInputManager.TAG, "Invalid subtitle trackId: " + str);
                        return;
                    }
                } else {
                    throw new IllegalArgumentException("invalid type: " + i);
                }
                IBinder iBinder = this.mToken;
                if (iBinder == null) {
                    Log.w(TvInputManager.TAG, "The session has been already released");
                    return;
                }
                try {
                    this.mService.selectTrack(iBinder, i, str, this.mUserId);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        private boolean containsTrack(List<TvTrackInfo> list, String str) {
            Iterator<TvTrackInfo> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().getId().equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public List<TvTrackInfo> getTracks(int i) {
            synchronized (this.mMetadataLock) {
                try {
                    if (i == 0) {
                        if (this.mAudioTracks == null) {
                            return null;
                        }
                        return new ArrayList(this.mAudioTracks);
                    }
                    if (i == 1) {
                        if (this.mVideoTracks == null) {
                            return null;
                        }
                        return new ArrayList(this.mVideoTracks);
                    }
                    if (i == 2) {
                        if (this.mSubtitleTracks == null) {
                            return null;
                        }
                        return new ArrayList(this.mSubtitleTracks);
                    }
                    throw new IllegalArgumentException("invalid type: " + i);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public String getSelectedTrack(int i) {
            synchronized (this.mMetadataLock) {
                try {
                    if (i == 0) {
                        return this.mSelectedAudioTrackId;
                    }
                    if (i == 1) {
                        return this.mSelectedVideoTrackId;
                    }
                    if (i == 2) {
                        return this.mSelectedSubtitleTrackId;
                    }
                    throw new IllegalArgumentException("invalid type: " + i);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void setInteractiveAppNotificationEnabled(boolean z) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setInteractiveAppNotificationEnabled(iBinder, z, this.mUserId);
                this.mIAppNotificationEnabled = z;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean updateTracks(List<TvTrackInfo> list) {
            boolean z;
            synchronized (this.mMetadataLock) {
                this.mAudioTracks.clear();
                this.mVideoTracks.clear();
                this.mSubtitleTracks.clear();
                Iterator<TvTrackInfo> it = list.iterator();
                while (true) {
                    z = true;
                    if (!it.hasNext()) {
                        break;
                    }
                    TvTrackInfo next = it.next();
                    if (next.getType() == 0) {
                        this.mAudioTracks.add(next);
                    } else if (next.getType() == 1) {
                        this.mVideoTracks.add(next);
                    } else if (next.getType() == 2) {
                        this.mSubtitleTracks.add(next);
                    }
                }
                if (this.mAudioTracks.isEmpty() && this.mVideoTracks.isEmpty() && this.mSubtitleTracks.isEmpty()) {
                    z = false;
                }
            }
            return z;
        }

        boolean updateTrackSelection(int i, String str) {
            synchronized (this.mMetadataLock) {
                if (i == 0) {
                    try {
                        if (!TextUtils.equals(str, this.mSelectedAudioTrackId)) {
                            this.mSelectedAudioTrackId = str;
                            return true;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (i == 1 && !TextUtils.equals(str, this.mSelectedVideoTrackId)) {
                    this.mSelectedVideoTrackId = str;
                    return true;
                }
                if (i != 2 || TextUtils.equals(str, this.mSelectedSubtitleTrackId)) {
                    return false;
                }
                this.mSelectedSubtitleTrackId = str;
                return true;
            }
        }

        TvTrackInfo getVideoTrackToNotify() {
            synchronized (this.mMetadataLock) {
                if (!this.mVideoTracks.isEmpty() && this.mSelectedVideoTrackId != null) {
                    for (TvTrackInfo tvTrackInfo : this.mVideoTracks) {
                        if (tvTrackInfo.getId().equals(this.mSelectedVideoTrackId)) {
                            int videoWidth = tvTrackInfo.getVideoWidth();
                            int videoHeight = tvTrackInfo.getVideoHeight();
                            if (this.mVideoWidth != videoWidth || this.mVideoHeight != videoHeight) {
                                this.mVideoWidth = videoWidth;
                                this.mVideoHeight = videoHeight;
                                return tvTrackInfo;
                            }
                        }
                    }
                }
                return null;
            }
        }

        void timeShiftPlay(Uri uri) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftPlay(iBinder, uri, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void timeShiftPause() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftPause(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void timeShiftResume() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftResume(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void timeShiftSeekTo(long j) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftSeekTo(iBinder, j, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void timeShiftSetPlaybackParams(PlaybackParams playbackParams) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftSetPlaybackParams(iBinder, playbackParams, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void timeShiftSetMode(int i) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftSetMode(iBinder, i, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void timeShiftEnablePositionTracking(boolean z) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftEnablePositionTracking(iBinder, z, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void stopPlayback(int i) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.stopPlayback(iBinder, i, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void resumePlayback() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.resumePlayback(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void setVideoFrozen(boolean z) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setVideoFrozen(iBinder, z, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTvMessage(int i, Bundle bundle) {
            try {
                this.mService.notifyTvMessage(this.mToken, i, bundle, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setTvMessageEnabled(int i, boolean z) {
            try {
                this.mService.setTvMessageEnabled(this.mToken, i, z, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void startRecording(Uri uri) {
            startRecording(uri, null);
        }

        void startRecording(Uri uri, Bundle bundle) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.startRecording(iBinder, uri, bundle, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void stopRecording() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.stopRecording(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void pauseRecording(Bundle bundle) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.pauseRecording(iBinder, bundle, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void resumeRecording(Bundle bundle) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.resumeRecording(iBinder, bundle, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void sendAppPrivateCommand(String str, Bundle bundle) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendAppPrivateCommand(iBinder, str, bundle, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void createOverlayView(View view, Rect rect) {
            Preconditions.checkNotNull(view);
            Preconditions.checkNotNull(rect);
            if (view.getWindowToken() == null) {
                throw new IllegalStateException("view must be attached to a window");
            }
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.createOverlayView(iBinder, view.getWindowToken(), rect, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void relayoutOverlayView(Rect rect) {
            Preconditions.checkNotNull(rect);
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.relayoutOverlayView(iBinder, rect, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void removeOverlayView() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.removeOverlayView(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void unblockContent(TvContentRating tvContentRating) {
            Preconditions.checkNotNull(tvContentRating);
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.unblockContent(iBinder, tvContentRating.flattenToString(), this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int dispatchInputEvent(InputEvent inputEvent, Object obj, FinishedInputEventCallback finishedInputEventCallback, Handler handler) {
            Preconditions.checkNotNull(inputEvent);
            Preconditions.checkNotNull(finishedInputEventCallback);
            Preconditions.checkNotNull(handler);
            synchronized (this.mHandler) {
                if (this.mChannel == null) {
                    return 0;
                }
                PendingEvent obtainPendingEventLocked = obtainPendingEventLocked(inputEvent, obj, finishedInputEventCallback, handler);
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    return sendInputEventOnMainLooperLocked(obtainPendingEventLocked);
                }
                Message obtainMessage = this.mHandler.obtainMessage(1, obtainPendingEventLocked);
                obtainMessage.setAsynchronous(true);
                this.mHandler.sendMessage(obtainMessage);
                return -1;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void sendInputEventAndReportResultOnMainLooper(PendingEvent pendingEvent) {
            synchronized (this.mHandler) {
                if (sendInputEventOnMainLooperLocked(pendingEvent) == -1) {
                    return;
                }
                invokeFinishedInputEventCallback(pendingEvent, false);
            }
        }

        private int sendInputEventOnMainLooperLocked(PendingEvent pendingEvent) {
            if (this.mChannel == null) {
                return 0;
            }
            if (this.mSender == null) {
                this.mSender = new TvInputEventSender(this.mChannel, this.mHandler.getLooper());
            }
            InputEvent inputEvent = pendingEvent.mEvent;
            int sequenceNumber = inputEvent.getSequenceNumber();
            if (this.mSender.sendInputEvent(sequenceNumber, inputEvent)) {
                this.mPendingEvents.put(sequenceNumber, pendingEvent);
                Message obtainMessage = this.mHandler.obtainMessage(2, pendingEvent);
                obtainMessage.setAsynchronous(true);
                this.mHandler.sendMessageDelayed(obtainMessage, INPUT_SESSION_NOT_RESPONDING_TIMEOUT);
                return -1;
            }
            Log.w(TvInputManager.TAG, "Unable to send input event to session: " + this.mToken + " dropping:" + inputEvent);
            return 0;
        }

        void finishedInputEvent(int i, boolean z, boolean z2) {
            synchronized (this.mHandler) {
                int indexOfKey = this.mPendingEvents.indexOfKey(i);
                if (indexOfKey < 0) {
                    return;
                }
                PendingEvent valueAt = this.mPendingEvents.valueAt(indexOfKey);
                this.mPendingEvents.removeAt(indexOfKey);
                if (z2) {
                    Log.w(TvInputManager.TAG, "Timeout waiting for session to handle input event after 2500 ms: " + this.mToken);
                } else {
                    this.mHandler.removeMessages(2, valueAt);
                }
                invokeFinishedInputEventCallback(valueAt, z);
            }
        }

        void invokeFinishedInputEventCallback(PendingEvent pendingEvent, boolean z) {
            pendingEvent.mHandled = z;
            if (pendingEvent.mEventHandler.getLooper().isCurrentThread()) {
                pendingEvent.run();
                return;
            }
            Message obtain = Message.obtain(pendingEvent.mEventHandler, pendingEvent);
            obtain.setAsynchronous(true);
            obtain.sendToTarget();
        }

        private void flushPendingEventsLocked() {
            this.mHandler.removeMessages(3);
            int size = this.mPendingEvents.size();
            for (int i = 0; i < size; i++) {
                Message obtainMessage = this.mHandler.obtainMessage(3, this.mPendingEvents.keyAt(i), 0);
                obtainMessage.setAsynchronous(true);
                obtainMessage.sendToTarget();
            }
        }

        private PendingEvent obtainPendingEventLocked(InputEvent inputEvent, Object obj, FinishedInputEventCallback finishedInputEventCallback, Handler handler) {
            PendingEvent acquire = this.mPendingEventPool.acquire();
            if (acquire == null) {
                acquire = new PendingEvent();
            }
            acquire.mEvent = inputEvent;
            acquire.mEventToken = obj;
            acquire.mCallback = finishedInputEventCallback;
            acquire.mEventHandler = handler;
            return acquire;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void recyclePendingEventLocked(PendingEvent pendingEvent) {
            pendingEvent.recycle();
            this.mPendingEventPool.release(pendingEvent);
        }

        IBinder getToken() {
            return this.mToken;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void releaseInternal() {
            this.mToken = null;
            synchronized (this.mHandler) {
                if (this.mChannel != null) {
                    if (this.mSender != null) {
                        flushPendingEventsLocked();
                        this.mSender.dispose();
                        this.mSender = null;
                    }
                    this.mChannel.dispose();
                    this.mChannel = null;
                }
            }
            synchronized (this.mSessionCallbackRecordMap) {
                this.mSessionCallbackRecordMap.delete(this.mSeq);
            }
        }

        public void requestBroadcastInfo(BroadcastInfoRequest broadcastInfoRequest) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.requestBroadcastInfo(iBinder, broadcastInfoRequest, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void removeBroadcastInfo(int i) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.removeBroadcastInfo(iBinder, i, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void requestAd(AdRequest adRequest) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.requestAd(iBinder, adRequest, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyAdBufferReady(AdBuffer adBuffer) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                try {
                    this.mService.notifyAdBufferReady(iBinder, adBuffer, this.mUserId);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                if (adBuffer != null) {
                    adBuffer.getSharedMemory().close();
                }
            }
        }

        public void notifyTvAdSessionData(String str, Bundle bundle) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTvAdSessionData(iBinder, str, bundle, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private final class InputEventHandler extends Handler {
            public static final int MSG_FLUSH_INPUT_EVENT = 3;
            public static final int MSG_SEND_INPUT_EVENT = 1;
            public static final int MSG_TIMEOUT_INPUT_EVENT = 2;

            InputEventHandler(Looper looper) {
                super(looper, null, true);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    Session.this.sendInputEventAndReportResultOnMainLooper((PendingEvent) message.obj);
                } else if (i == 2) {
                    Session.this.finishedInputEvent(message.arg1, false, true);
                } else {
                    if (i != 3) {
                        return;
                    }
                    Session.this.finishedInputEvent(message.arg1, false, false);
                }
            }
        }

        private final class TvInputEventSender extends InputEventSender {
            public TvInputEventSender(InputChannel inputChannel, Looper looper) {
                super(inputChannel, looper);
            }

            @Override // android.view.InputEventSender
            public void onInputEventFinished(int i, boolean z) {
                Session.this.finishedInputEvent(i, z, false);
            }
        }

        private final class PendingEvent implements Runnable {
            public FinishedInputEventCallback mCallback;
            public InputEvent mEvent;
            public Handler mEventHandler;
            public Object mEventToken;
            public boolean mHandled;

            private PendingEvent() {
            }

            public void recycle() {
                this.mEvent = null;
                this.mEventToken = null;
                this.mCallback = null;
                this.mEventHandler = null;
                this.mHandled = false;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.mCallback.onFinishedInputEvent(this.mEventToken, this.mHandled);
                synchronized (this.mEventHandler) {
                    Session.this.recyclePendingEventLocked(this);
                }
            }
        }
    }

    @SystemApi
    public static final class Hardware {
        private final ITvInputHardware mInterface;

        @SystemApi
        public boolean dispatchKeyEventToHdmi(KeyEvent keyEvent) {
            return false;
        }

        private Hardware(ITvInputHardware iTvInputHardware) {
            this.mInterface = iTvInputHardware;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public ITvInputHardware getInterface() {
            return this.mInterface;
        }

        public boolean setSurface(Surface surface, TvStreamConfig tvStreamConfig) {
            try {
                return this.mInterface.setSurface(surface, tvStreamConfig);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public void setStreamVolume(float f) {
            try {
                this.mInterface.setStreamVolume(f);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public void overrideAudioSink(int i, String str, int i2, int i3, int i4) {
            try {
                this.mInterface.overrideAudioSink(i, str, i2, i3, i4);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        public void overrideAudioSink(AudioDeviceInfo audioDeviceInfo, int i, int i2, int i3) {
            Objects.requireNonNull(audioDeviceInfo);
            try {
                this.mInterface.overrideAudioSink(AudioDeviceInfo.convertDeviceTypeToInternalDevice(audioDeviceInfo.getType()), audioDeviceInfo.getAddress(), i, i2, i3);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
