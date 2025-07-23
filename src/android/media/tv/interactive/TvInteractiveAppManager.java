package android.media.tv.interactive;

import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.AdBuffer;
import android.media.tv.AdRequest;
import android.media.tv.AdResponse;
import android.media.tv.BroadcastInfoRequest;
import android.media.tv.BroadcastInfoResponse;
import android.media.tv.TvContentRating;
import android.media.tv.TvInputManager;
import android.media.tv.TvRecordingInfo;
import android.media.tv.TvTrackInfo;
import android.media.tv.interactive.ITvInteractiveAppClient;
import android.media.tv.interactive.ITvInteractiveAppManagerCallback;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pools;
import android.util.SparseArray;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventSender;
import android.view.Surface;
import android.view.View;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class TvInteractiveAppManager {
    public static final String ACTION_APP_LINK_COMMAND = "android.media.tv.interactive.action.APP_LINK_COMMAND";
    public static final String APP_LINK_KEY_BACK_URI = "back_uri";
    public static final String APP_LINK_KEY_CLASS_NAME = "class_name";
    public static final String APP_LINK_KEY_COMMAND_TYPE = "command_type";
    public static final String APP_LINK_KEY_PACKAGE_NAME = "package_name";
    public static final String APP_LINK_KEY_SERVICE_ID = "service_id";
    public static final int ERROR_BLOCKED = 5;
    public static final int ERROR_ENCRYPTED = 6;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_SUPPORTED = 2;
    public static final int ERROR_RESOURCE_UNAVAILABLE = 4;
    public static final int ERROR_UNKNOWN = 1;
    public static final int ERROR_UNKNOWN_CHANNEL = 7;
    public static final int ERROR_WEAK_SIGNAL = 3;
    public static final String INTENT_KEY_BI_INTERACTIVE_APP_TYPE = "bi_interactive_app_type";
    public static final String INTENT_KEY_BI_INTERACTIVE_APP_URI = "bi_interactive_app_uri";
    public static final String INTENT_KEY_CHANNEL_URI = "channel_uri";
    public static final String INTENT_KEY_COMMAND_TYPE = "command_type";
    public static final String INTENT_KEY_INTERACTIVE_APP_SERVICE_ID = "interactive_app_id";
    public static final String INTENT_KEY_TV_INPUT_ID = "tv_input_id";
    public static final int INTERACTIVE_APP_STATE_ERROR = 3;
    public static final int INTERACTIVE_APP_STATE_RUNNING = 2;
    public static final int INTERACTIVE_APP_STATE_STOPPED = 1;
    public static final int SERVICE_STATE_ERROR = 4;
    public static final int SERVICE_STATE_PREPARING = 2;
    public static final int SERVICE_STATE_READY = 3;
    public static final int SERVICE_STATE_UNREALIZED = 1;
    private static final String TAG = "TvInteractiveAppManager";
    public static final int TELETEXT_APP_STATE_ERROR = 3;
    public static final int TELETEXT_APP_STATE_HIDE = 2;
    public static final int TELETEXT_APP_STATE_SHOW = 1;
    private int mNextSeq;
    private final ITvInteractiveAppManager mService;
    private final int mUserId;
    private final SparseArray<SessionCallbackRecord> mSessionCallbackRecordMap = new SparseArray<>();
    private final List<TvInteractiveAppCallbackRecord> mCallbackRecords = new ArrayList();
    private final Object mLock = new Object();
    private final ITvInteractiveAppClient mClient = new ITvInteractiveAppClient.Stub() { // from class: android.media.tv.interactive.TvInteractiveAppManager.1
        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) {
            Session session;
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for " + iBinder);
                } else {
                    if (iBinder != null) {
                        session = new Session(iBinder, inputChannel, TvInteractiveAppManager.this.mService, TvInteractiveAppManager.this.mUserId, i, TvInteractiveAppManager.this.mSessionCallbackRecordMap);
                    } else {
                        TvInteractiveAppManager.this.mSessionCallbackRecordMap.delete(i);
                        session = null;
                    }
                    sessionCallbackRecord.postSessionCreated(session);
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionReleased(int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                TvInteractiveAppManager.this.mSessionCallbackRecordMap.delete(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq:" + i);
                } else {
                    sessionCallbackRecord.mSession.releaseInternal();
                    sessionCallbackRecord.postSessionReleased();
                }
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i5);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i5);
                    return;
                }
                sessionCallbackRecord.postLayoutSurface(i, i2, i3, i4);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onBroadcastInfoRequest(BroadcastInfoRequest broadcastInfoRequest, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postBroadcastInfoRequest(broadcastInfoRequest);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRemoveBroadcastInfo(int i, int i2) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i2);
                    return;
                }
                sessionCallbackRecord.postRemoveBroadcastInfo(i);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onCommandRequest(String str, Bundle bundle, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postCommandRequest(str, bundle);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onTimeShiftCommandRequest(String str, Bundle bundle, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postTimeShiftCommandRequest(str, bundle);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSetVideoBounds(Rect rect, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postSetVideoBounds(rect);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onAdRequest(AdRequest adRequest, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postAdRequest(adRequest);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentVideoBounds(int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestCurrentVideoBounds();
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentChannelUri(int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestCurrentChannelUri();
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentChannelLcn(int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestCurrentChannelLcn();
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStreamVolume(int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestStreamVolume();
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTrackInfoList(int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestTrackInfoList();
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSelectedTrackInfo(int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestSelectedTrackInfo();
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentTvInputId(int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestCurrentTvInputId();
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTimeShiftMode(int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestTimeShiftMode();
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestAvailableSpeeds(int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestAvailableSpeeds();
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStartRecording(String str, Uri uri, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestStartRecording(str, uri);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStopRecording(String str, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestStopRecording(str);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestScheduleRecording(String str, String str2, Uri uri, Uri uri2, Bundle bundle, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestScheduleRecording(str, str2, uri, uri2, bundle);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestScheduleRecording2(String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle, int i2) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i2);
                    return;
                }
                sessionCallbackRecord.postRequestScheduleRecording(str, str2, uri, j, j2, i, bundle);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSetTvRecordingInfo(String str, TvRecordingInfo tvRecordingInfo, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postSetTvRecordingInfo(str, tvRecordingInfo);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTvRecordingInfo(String str, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestTvRecordingInfo(str);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTvRecordingInfoList(int i, int i2) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i2);
                    return;
                }
                sessionCallbackRecord.postRequestTvRecordingInfoList(i);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSigning(String str, String str2, String str3, byte[] bArr, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestSigning(str, str2, str3, bArr);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSigning2(String str, String str2, String str3, int i, byte[] bArr, int i2) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i2);
                    return;
                }
                sessionCallbackRecord.postRequestSigning(str, str2, str3, i, bArr);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCertificate(String str, int i, int i2) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i2);
                    return;
                }
                sessionCallbackRecord.postRequestCertificate(str, i);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionStateChanged(int i, int i2, int i3) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i3);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i3);
                    return;
                }
                sessionCallbackRecord.postSessionStateChanged(i, i2);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onBiInteractiveAppCreated(Uri uri, String str, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postBiInteractiveAppCreated(uri, str);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onTeletextAppStateChanged(int i, int i2) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i2);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i2);
                    return;
                }
                sessionCallbackRecord.postTeletextAppStateChanged(i);
            }
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onAdBufferReady(AdBuffer adBuffer, int i) {
            synchronized (TvInteractiveAppManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvInteractiveAppManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvInteractiveAppManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postAdBufferReady(adBuffer);
            }
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InteractiveAppState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceState {
    }

    public static abstract class SessionCallback {
        public void onBiInteractiveAppCreated(Session session, Uri uri, String str) {
        }

        public void onCommandRequest(Session session, String str, Bundle bundle) {
        }

        public void onLayoutSurface(Session session, int i, int i2, int i3, int i4) {
        }

        public void onRequestAvailableSpeeds(Session session) {
        }

        public void onRequestCertificate(Session session, String str, int i) {
        }

        public void onRequestCurrentChannelLcn(Session session) {
        }

        public void onRequestCurrentChannelUri(Session session) {
        }

        public void onRequestCurrentTvInputId(Session session) {
        }

        public void onRequestCurrentVideoBounds(Session session) {
        }

        public void onRequestScheduleRecording(Session session, String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle) {
        }

        public void onRequestScheduleRecording(Session session, String str, String str2, Uri uri, Uri uri2, Bundle bundle) {
        }

        public void onRequestSelectedTrackInfo(Session session) {
        }

        public void onRequestSigning(Session session, String str, String str2, String str3, int i, byte[] bArr) {
        }

        public void onRequestSigning(Session session, String str, String str2, String str3, byte[] bArr) {
        }

        public void onRequestStartRecording(Session session, String str, Uri uri) {
        }

        public void onRequestStopRecording(Session session, String str) {
        }

        public void onRequestStreamVolume(Session session) {
        }

        public void onRequestTimeShiftMode(Session session) {
        }

        public void onRequestTrackInfoList(Session session) {
        }

        public void onRequestTvRecordingInfo(Session session, String str) {
        }

        public void onRequestTvRecordingInfoList(Session session, int i) {
        }

        public void onSessionCreated(Session session) {
        }

        public void onSessionReleased(Session session) {
        }

        public void onSessionStateChanged(Session session, int i, int i2) {
        }

        public void onSetTvRecordingInfo(Session session, String str, TvRecordingInfo tvRecordingInfo) {
        }

        public void onSetVideoBounds(Session session, Rect rect) {
        }

        public void onTeletextAppStateChanged(Session session, int i) {
        }

        public void onTimeShiftCommandRequest(Session session, String str, Bundle bundle) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TeletextAppState {
    }

    public static abstract class TvInteractiveAppCallback {
        public void onInteractiveAppServiceAdded(String str) {
        }

        public void onInteractiveAppServiceRemoved(String str) {
        }

        public void onInteractiveAppServiceUpdated(String str) {
        }

        public void onTvInteractiveAppServiceInfoUpdated(TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) {
        }

        public void onTvInteractiveAppServiceStateChanged(String str, int i, int i2, int i3) {
        }
    }

    public TvInteractiveAppManager(ITvInteractiveAppManager iTvInteractiveAppManager, int i) {
        this.mService = iTvInteractiveAppManager;
        this.mUserId = i;
        ITvInteractiveAppManagerCallback.Stub stub = new ITvInteractiveAppManagerCallback.Stub() { // from class: android.media.tv.interactive.TvInteractiveAppManager.2
            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceAdded(String str) {
                synchronized (TvInteractiveAppManager.this.mLock) {
                    Iterator it = TvInteractiveAppManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvInteractiveAppCallbackRecord) it.next()).postInteractiveAppServiceAdded(str);
                    }
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceRemoved(String str) {
                synchronized (TvInteractiveAppManager.this.mLock) {
                    Iterator it = TvInteractiveAppManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvInteractiveAppCallbackRecord) it.next()).postInteractiveAppServiceRemoved(str);
                    }
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onInteractiveAppServiceUpdated(String str) {
                synchronized (TvInteractiveAppManager.this.mLock) {
                    Iterator it = TvInteractiveAppManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvInteractiveAppCallbackRecord) it.next()).postInteractiveAppServiceUpdated(str);
                    }
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onTvInteractiveAppServiceInfoUpdated(TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) {
                synchronized (TvInteractiveAppManager.this.mLock) {
                    Iterator it = TvInteractiveAppManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvInteractiveAppCallbackRecord) it.next()).postTvInteractiveAppServiceInfoUpdated(tvInteractiveAppServiceInfo);
                    }
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManagerCallback
            public void onStateChanged(String str, int i2, int i3, int i4) {
                synchronized (TvInteractiveAppManager.this.mLock) {
                    Iterator it = TvInteractiveAppManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvInteractiveAppCallbackRecord) it.next()).postStateChanged(str, i2, i3, i4);
                    }
                }
            }
        };
        if (iTvInteractiveAppManager != null) {
            try {
                iTvInteractiveAppManager.registerCallback(stub, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private static final class TvInteractiveAppCallbackRecord {
        private final TvInteractiveAppCallback mCallback;
        private final Executor mExecutor;

        TvInteractiveAppCallbackRecord(TvInteractiveAppCallback tvInteractiveAppCallback, Executor executor) {
            this.mCallback = tvInteractiveAppCallback;
            this.mExecutor = executor;
        }

        public TvInteractiveAppCallback getCallback() {
            return this.mCallback;
        }

        public void postInteractiveAppServiceAdded(final String str) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    TvInteractiveAppCallbackRecord.this.mCallback.onInteractiveAppServiceAdded(str);
                }
            });
        }

        public void postInteractiveAppServiceRemoved(final String str) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    TvInteractiveAppCallbackRecord.this.mCallback.onInteractiveAppServiceRemoved(str);
                }
            });
        }

        public void postInteractiveAppServiceUpdated(final String str) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    TvInteractiveAppCallbackRecord.this.mCallback.onInteractiveAppServiceUpdated(str);
                }
            });
        }

        public void postTvInteractiveAppServiceInfoUpdated(final TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    TvInteractiveAppCallbackRecord.this.mCallback.onTvInteractiveAppServiceInfoUpdated(tvInteractiveAppServiceInfo);
                }
            });
        }

        public void postStateChanged(final String str, final int i, final int i2, final int i3) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.TvInteractiveAppCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    TvInteractiveAppCallbackRecord.this.mCallback.onTvInteractiveAppServiceStateChanged(str, i, i2, i3);
                }
            });
        }
    }

    public void createSession(String str, int i, SessionCallback sessionCallback, Handler handler) {
        createSessionInternal(str, i, sessionCallback, handler);
    }

    private void createSessionInternal(String str, int i, SessionCallback sessionCallback, Handler handler) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(sessionCallback);
        Preconditions.checkNotNull(handler);
        SessionCallbackRecord sessionCallbackRecord = new SessionCallbackRecord(sessionCallback, handler);
        synchronized (this.mSessionCallbackRecordMap) {
            int i2 = this.mNextSeq;
            this.mNextSeq = i2 + 1;
            this.mSessionCallbackRecordMap.put(i2, sessionCallbackRecord);
            try {
                this.mService.createSession(this.mClient, str, i, i2, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<TvInteractiveAppServiceInfo> getTvInteractiveAppServiceList() {
        try {
            return this.mService.getTvInteractiveAppServiceList(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<AppLinkInfo> getAppLinkInfoList() {
        try {
            return this.mService.getAppLinkInfoList(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerAppLinkInfo(String str, AppLinkInfo appLinkInfo) {
        try {
            this.mService.registerAppLinkInfo(str, appLinkInfo, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterAppLinkInfo(String str, AppLinkInfo appLinkInfo) {
        try {
            this.mService.unregisterAppLinkInfo(str, appLinkInfo, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendAppLinkCommand(String str, Bundle bundle) {
        try {
            this.mService.sendAppLinkCommand(str, bundle, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(Executor executor, TvInteractiveAppCallback tvInteractiveAppCallback) {
        Preconditions.checkNotNull(tvInteractiveAppCallback);
        Preconditions.checkNotNull(executor);
        synchronized (this.mLock) {
            this.mCallbackRecords.add(new TvInteractiveAppCallbackRecord(tvInteractiveAppCallback, executor));
        }
    }

    public void unregisterCallback(TvInteractiveAppCallback tvInteractiveAppCallback) {
        Preconditions.checkNotNull(tvInteractiveAppCallback);
        synchronized (this.mLock) {
            Iterator<TvInteractiveAppCallbackRecord> it = this.mCallbackRecords.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getCallback() == tvInteractiveAppCallback) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public static final class Session {
        static final int DISPATCH_HANDLED = 1;
        static final int DISPATCH_IN_PROGRESS = -1;
        static final int DISPATCH_NOT_HANDLED = 0;
        private static final long INPUT_SESSION_NOT_RESPONDING_TIMEOUT = 2500;
        private final InputEventHandler mHandler;
        private InputChannel mInputChannel;
        private TvInputManager.Session mInputSession;
        private final Pools.Pool<PendingEvent> mPendingEventPool;
        private final SparseArray<PendingEvent> mPendingEvents;
        private TvInputEventSender mSender;
        private final int mSeq;
        private final ITvInteractiveAppManager mService;
        private final SparseArray<SessionCallbackRecord> mSessionCallbackRecordMap;
        private IBinder mToken;
        private final int mUserId;

        public interface FinishedInputEventCallback {
            void onFinishedInputEvent(Object obj, boolean z);
        }

        private Session(IBinder iBinder, InputChannel inputChannel, ITvInteractiveAppManager iTvInteractiveAppManager, int i, int i2, SparseArray<SessionCallbackRecord> sparseArray) {
            this.mHandler = new InputEventHandler(Looper.getMainLooper());
            this.mPendingEventPool = new Pools.SimplePool(20);
            this.mPendingEvents = new SparseArray<>(20);
            this.mToken = iBinder;
            this.mInputChannel = inputChannel;
            this.mService = iTvInteractiveAppManager;
            this.mUserId = i;
            this.mSeq = i2;
            this.mSessionCallbackRecordMap = sparseArray;
        }

        public TvInputManager.Session getInputSession() {
            return this.mInputSession;
        }

        public void setInputSession(TvInputManager.Session session) {
            this.mInputSession = session;
        }

        void startInteractiveApp() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.startInteractiveApp(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void stopInteractiveApp() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.stopInteractiveApp(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void resetInteractiveApp() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.resetInteractiveApp(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void createBiInteractiveApp(Uri uri, Bundle bundle) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.createBiInteractiveApp(iBinder, uri, bundle, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void destroyBiInteractiveApp(String str) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.destroyBiInteractiveApp(iBinder, str, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void setTeletextAppEnabled(boolean z) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setTeletextAppEnabled(iBinder, z, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentVideoBounds(Rect rect) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentVideoBounds(iBinder, rect, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentChannelUri(Uri uri) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentChannelUri(iBinder, uri, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentChannelLcn(int i) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentChannelLcn(iBinder, i, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendStreamVolume(float f) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendStreamVolume(iBinder, f, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTrackInfoList(List<TvTrackInfo> list) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTrackInfoList(iBinder, list, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendSelectedTrackInfo(List<TvTrackInfo> list) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendSelectedTrackInfo(iBinder, list, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentTvInputId(String str) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentTvInputId(iBinder, str, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTimeShiftMode(int i) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTimeShiftMode(iBinder, i, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendAvailableSpeeds(float[] fArr) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendAvailableSpeeds(iBinder, fArr, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTvRecordingInfo(TvRecordingInfo tvRecordingInfo) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTvRecordingInfo(iBinder, tvRecordingInfo, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTvRecordingInfoList(List<TvRecordingInfo> list) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTvRecordingInfoList(iBinder, list, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingStarted(String str, String str2) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingStarted(iBinder, str, str2, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingStopped(String str) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingStopped(iBinder, str, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendSigningResult(String str, byte[] bArr) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendSigningResult(iBinder, str, bArr, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCertificate(String str, int i, SslCertificate sslCertificate) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCertificate(iBinder, str, i, SslCertificate.saveState(sslCertificate), this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyError(String str, Bundle bundle) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyError(iBinder, str, bundle, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyTimeShiftPlaybackParams(PlaybackParams playbackParams) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTimeShiftPlaybackParams(iBinder, playbackParams, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyTimeShiftStatusChanged(String str, int i) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTimeShiftStatusChanged(iBinder, str, i, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyTimeShiftStartPositionChanged(String str, long j) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTimeShiftStartPositionChanged(iBinder, str, j, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyTimeShiftCurrentPositionChanged(String str, long j) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTimeShiftCurrentPositionChanged(iBinder, str, j, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingConnectionFailed(String str, String str2) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingConnectionFailed(iBinder, str, str2, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingDisconnected(String str, String str2) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingDisconnected(iBinder, str, str2, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingTuned(String str, Uri uri) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingTuned(iBinder, str, uri, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingError(String str, int i) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingError(iBinder, str, i, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyRecordingScheduled(String str, String str2) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyRecordingScheduled(iBinder, str, str2, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setSurface(Surface surface) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setSurface(iBinder, surface, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void createMediaView(View view, Rect rect) {
            Preconditions.checkNotNull(view);
            Preconditions.checkNotNull(rect);
            if (view.getWindowToken() == null) {
                throw new IllegalStateException("view must be attached to a window");
            }
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.createMediaView(iBinder, view.getWindowToken(), rect, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void relayoutMediaView(Rect rect) {
            Preconditions.checkNotNull(rect);
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.relayoutMediaView(iBinder, rect, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void removeMediaView() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.removeMediaView(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void dispatchSurfaceChanged(int i, int i2, int i3) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.dispatchSurfaceChanged(iBinder, i, i2, i3, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int dispatchInputEvent(InputEvent inputEvent, Object obj, FinishedInputEventCallback finishedInputEventCallback, Handler handler) {
            Preconditions.checkNotNull(inputEvent);
            Preconditions.checkNotNull(finishedInputEventCallback);
            Preconditions.checkNotNull(handler);
            synchronized (this.mHandler) {
                if (this.mInputChannel == null) {
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

        public void notifyBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyBroadcastInfoResponse(iBinder, broadcastInfoResponse, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyAdResponse(AdResponse adResponse) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyAdResponse(iBinder, adResponse, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyAdBufferConsumed(AdBuffer adBuffer) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                try {
                    this.mService.notifyAdBufferConsumed(iBinder, adBuffer, this.mUserId);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                if (adBuffer != null) {
                    adBuffer.getSharedMemory().close();
                }
            }
        }

        public void release() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.releaseSession(iBinder, this.mUserId);
                releaseInternal();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTuned(Uri uri) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTuned(iBinder, uri, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTrackSelected(int i, String str) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTrackSelected(iBinder, i, str, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTracksChanged(List<TvTrackInfo> list) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTracksChanged(iBinder, list, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyVideoAvailable() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyVideoAvailable(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyVideoUnavailable(int i) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyVideoUnavailable(iBinder, i, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyVideoFreezeUpdated(boolean z) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyVideoFreezeUpdated(iBinder, z, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyContentAllowed() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyContentAllowed(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyContentBlocked(TvContentRating tvContentRating) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyContentBlocked(iBinder, tvContentRating.flattenToString(), this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifySignalStrength(int i) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifySignalStrength(iBinder, i, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTvMessage(int i, Bundle bundle) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInteractiveAppManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTvMessage(iBinder, i, bundle, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
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

        /* JADX INFO: Access modifiers changed from: private */
        public void releaseInternal() {
            this.mToken = null;
            synchronized (this.mHandler) {
                if (this.mInputChannel != null) {
                    if (this.mSender != null) {
                        flushPendingEventsLocked();
                        this.mSender.dispose();
                        this.mSender = null;
                    }
                    this.mInputChannel.dispose();
                    this.mInputChannel = null;
                }
            }
            synchronized (this.mSessionCallbackRecordMap) {
                this.mSessionCallbackRecordMap.delete(this.mSeq);
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
            if (this.mInputChannel == null) {
                return 0;
            }
            if (this.mSender == null) {
                this.mSender = new TvInputEventSender(this.mInputChannel, this.mHandler.getLooper());
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
            Log.w(TvInteractiveAppManager.TAG, "Unable to send input event to session: " + this.mToken + " dropping:" + inputEvent);
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
                    Log.w(TvInteractiveAppManager.TAG, "Timeout waiting for session to handle input event after 2500 ms: " + this.mToken);
                } else {
                    this.mHandler.removeMessages(2, valueAt);
                }
                invokeFinishedInputEventCallback(valueAt, z);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void recyclePendingEventLocked(PendingEvent pendingEvent) {
            pendingEvent.recycle();
            this.mPendingEventPool.release(pendingEvent);
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
            TvInputEventSender(InputChannel inputChannel, Looper looper) {
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
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionCreated(session);
                }
            });
        }

        void postSessionReleased() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionReleased(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postLayoutSurface(final int i, final int i2, final int i3, final int i4) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onLayoutSurface(SessionCallbackRecord.this.mSession, i, i2, i3, i4);
                }
            });
        }

        void postBroadcastInfoRequest(final BroadcastInfoRequest broadcastInfoRequest) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    if (SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        SessionCallbackRecord.this.mSession.getInputSession().requestBroadcastInfo(broadcastInfoRequest);
                    }
                }
            });
        }

        void postRemoveBroadcastInfo(final int i) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    if (SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        SessionCallbackRecord.this.mSession.getInputSession().removeBroadcastInfo(i);
                    }
                }
            });
        }

        void postCommandRequest(final String str, final Bundle bundle) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.6
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onCommandRequest(SessionCallbackRecord.this.mSession, str, bundle);
                }
            });
        }

        void postTimeShiftCommandRequest(final String str, final Bundle bundle) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.7
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTimeShiftCommandRequest(SessionCallbackRecord.this.mSession, str, bundle);
                }
            });
        }

        void postSetVideoBounds(final Rect rect) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.8
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSetVideoBounds(SessionCallbackRecord.this.mSession, rect);
                }
            });
        }

        void postRequestCurrentVideoBounds() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.9
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCurrentVideoBounds(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentChannelUri() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.10
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCurrentChannelUri(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentChannelLcn() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.11
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCurrentChannelLcn(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestStreamVolume() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.12
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestStreamVolume(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestTrackInfoList() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.13
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestTrackInfoList(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestSelectedTrackInfo() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.14
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestSelectedTrackInfo(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentTvInputId() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.15
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCurrentTvInputId(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestTimeShiftMode() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.16
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestTimeShiftMode(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestAvailableSpeeds() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.17
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestAvailableSpeeds(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestStartRecording(final String str, final Uri uri) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.18
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestStartRecording(SessionCallbackRecord.this.mSession, str, uri);
                }
            });
        }

        void postRequestStopRecording(final String str) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.19
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestStopRecording(SessionCallbackRecord.this.mSession, str);
                }
            });
        }

        void postRequestScheduleRecording(final String str, final String str2, final Uri uri, final Uri uri2, final Bundle bundle) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.20
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestScheduleRecording(SessionCallbackRecord.this.mSession, str, str2, uri, uri2, bundle);
                }
            });
        }

        void postRequestScheduleRecording(final String str, final String str2, final Uri uri, final long j, final long j2, final int i, final Bundle bundle) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.21
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestScheduleRecording(SessionCallbackRecord.this.mSession, str, str2, uri, j, j2, i, bundle);
                }
            });
        }

        void postRequestSigning(final String str, final String str2, final String str3, final byte[] bArr) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.22
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestSigning(SessionCallbackRecord.this.mSession, str, str2, str3, bArr);
                }
            });
        }

        void postRequestSigning(final String str, final String str2, final String str3, final int i, final byte[] bArr) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.23
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestSigning(SessionCallbackRecord.this.mSession, str, str2, str3, i, bArr);
                }
            });
        }

        void postRequestCertificate(final String str, final int i) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.24
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCertificate(SessionCallbackRecord.this.mSession, str, i);
                }
            });
        }

        void postRequestTvRecordingInfo(final String str) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.25
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestTvRecordingInfo(SessionCallbackRecord.this.mSession, str);
                }
            });
        }

        void postRequestTvRecordingInfoList(final int i) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.26
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestTvRecordingInfoList(SessionCallbackRecord.this.mSession, i);
                }
            });
        }

        void postSetTvRecordingInfo(final String str, final TvRecordingInfo tvRecordingInfo) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.27
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSetTvRecordingInfo(SessionCallbackRecord.this.mSession, str, tvRecordingInfo);
                }
            });
        }

        void postAdRequest(final AdRequest adRequest) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.28
                @Override // java.lang.Runnable
                public void run() {
                    if (SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        SessionCallbackRecord.this.mSession.getInputSession().requestAd(adRequest);
                    }
                }
            });
        }

        void postSessionStateChanged(final int i, final int i2) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.29
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionStateChanged(SessionCallbackRecord.this.mSession, i, i2);
                }
            });
        }

        void postBiInteractiveAppCreated(final Uri uri, final String str) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.30
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onBiInteractiveAppCreated(SessionCallbackRecord.this.mSession, uri, str);
                }
            });
        }

        void postTeletextAppStateChanged(final int i) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.31
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTeletextAppStateChanged(SessionCallbackRecord.this.mSession, i);
                }
            });
        }

        void postAdBufferReady(final AdBuffer adBuffer) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppManager.SessionCallbackRecord.32
                @Override // java.lang.Runnable
                public void run() {
                    if (SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        SessionCallbackRecord.this.mSession.getInputSession().notifyAdBufferReady(adBuffer);
                    }
                }
            });
        }
    }
}
