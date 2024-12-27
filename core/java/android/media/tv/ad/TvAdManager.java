package android.media.tv.ad;

import android.graphics.Rect;
import android.media.tv.TvInputManager;
import android.media.tv.TvTrackInfo;
import android.net.Uri;
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

public final class TvAdManager {
    public static final String ACTION_APP_LINK_COMMAND =
            "android.media.tv.ad.action.APP_LINK_COMMAND";
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
    public static final String INTENT_KEY_AD_SERVICE_ID = "ad_service_id";
    public static final String INTENT_KEY_CHANNEL_URI = "channel_uri";
    public static final String INTENT_KEY_COMMAND_TYPE = "command_type";
    public static final String INTENT_KEY_TV_INPUT_ID = "tv_input_id";
    public static final String SESSION_DATA_KEY_AD_BUFFER = "ad_buffer";
    public static final String SESSION_DATA_KEY_AD_REQUEST = "ad_request";
    public static final String SESSION_DATA_KEY_BROADCAST_INFO_REQUEST = "broadcast_info_request";
    public static final String SESSION_DATA_KEY_REQUEST_ID = "request_id";
    public static final String SESSION_DATA_TYPE_AD_BUFFER_READY = "ad_buffer_ready";
    public static final String SESSION_DATA_TYPE_AD_REQUEST = "ad_request";
    public static final String SESSION_DATA_TYPE_BROADCAST_INFO_REQUEST = "broadcast_info_request";
    public static final String SESSION_DATA_TYPE_REMOVE_BROADCAST_INFO_REQUEST =
            "remove_broadcast_info_request";
    public static final int SESSION_STATE_ERROR = 3;
    public static final int SESSION_STATE_RUNNING = 2;
    public static final int SESSION_STATE_STOPPED = 1;
    private static final String TAG = "TvAdManager";
    private int mNextSeq;
    private final ITvAdManager mService;
    private final int mUserId;
    private final SparseArray<SessionCallbackRecord> mSessionCallbackRecordMap =
            new SparseArray<>();
    private final List<TvAdServiceCallbackRecord> mCallbackRecords = new ArrayList();
    private final Object mLock = new Object();
    private final ITvAdClient mClient =
            new ITvAdClient.Stub() { // from class: android.media.tv.ad.TvAdManager.1
                @Override // android.media.tv.ad.ITvAdClient
                public void onSessionCreated(
                        String serviceId, IBinder token, InputChannel channel, int seq) {
                    synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                        SessionCallbackRecord record =
                                (SessionCallbackRecord)
                                        TvAdManager.this.mSessionCallbackRecordMap.get(seq);
                        if (record == null) {
                            Log.e(TvAdManager.TAG, "Callback not found for " + token);
                            return;
                        }
                        Session session = null;
                        if (token != null) {
                            session =
                                    new Session(
                                            token,
                                            channel,
                                            TvAdManager.this.mService,
                                            TvAdManager.this.mUserId,
                                            seq,
                                            TvAdManager.this.mSessionCallbackRecordMap);
                        } else {
                            TvAdManager.this.mSessionCallbackRecordMap.delete(seq);
                        }
                        record.postSessionCreated(session);
                    }
                }

                @Override // android.media.tv.ad.ITvAdClient
                public void onSessionReleased(int seq) {
                    synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                        SessionCallbackRecord record =
                                (SessionCallbackRecord)
                                        TvAdManager.this.mSessionCallbackRecordMap.get(seq);
                        TvAdManager.this.mSessionCallbackRecordMap.delete(seq);
                        if (record == null) {
                            Log.e(TvAdManager.TAG, "Callback not found for seq:" + seq);
                        } else {
                            record.mSession.releaseInternal();
                            record.postSessionReleased();
                        }
                    }
                }

                @Override // android.media.tv.ad.ITvAdClient
                public void onLayoutSurface(int left, int top, int right, int bottom, int seq) {
                    synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                        SessionCallbackRecord record =
                                (SessionCallbackRecord)
                                        TvAdManager.this.mSessionCallbackRecordMap.get(seq);
                        if (record == null) {
                            Log.e(TvAdManager.TAG, "Callback not found for seq " + seq);
                        } else {
                            record.postLayoutSurface(left, top, right, bottom);
                        }
                    }
                }

                @Override // android.media.tv.ad.ITvAdClient
                public void onRequestCurrentVideoBounds(int seq) {
                    synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                        SessionCallbackRecord record =
                                (SessionCallbackRecord)
                                        TvAdManager.this.mSessionCallbackRecordMap.get(seq);
                        if (record == null) {
                            Log.e(TvAdManager.TAG, "Callback not found for seq " + seq);
                        } else {
                            record.postRequestCurrentVideoBounds();
                        }
                    }
                }

                @Override // android.media.tv.ad.ITvAdClient
                public void onRequestCurrentChannelUri(int seq) {
                    synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                        SessionCallbackRecord record =
                                (SessionCallbackRecord)
                                        TvAdManager.this.mSessionCallbackRecordMap.get(seq);
                        if (record == null) {
                            Log.e(TvAdManager.TAG, "Callback not found for seq " + seq);
                        } else {
                            record.postRequestCurrentChannelUri();
                        }
                    }
                }

                @Override // android.media.tv.ad.ITvAdClient
                public void onRequestTrackInfoList(int seq) {
                    synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                        SessionCallbackRecord record =
                                (SessionCallbackRecord)
                                        TvAdManager.this.mSessionCallbackRecordMap.get(seq);
                        if (record == null) {
                            Log.e(TvAdManager.TAG, "Callback not found for seq " + seq);
                        } else {
                            record.postRequestTrackInfoList();
                        }
                    }
                }

                @Override // android.media.tv.ad.ITvAdClient
                public void onRequestCurrentTvInputId(int seq) {
                    synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                        SessionCallbackRecord record =
                                (SessionCallbackRecord)
                                        TvAdManager.this.mSessionCallbackRecordMap.get(seq);
                        if (record == null) {
                            Log.e(TvAdManager.TAG, "Callback not found for seq " + seq);
                        } else {
                            record.postRequestCurrentTvInputId();
                        }
                    }
                }

                @Override // android.media.tv.ad.ITvAdClient
                public void onRequestSigning(
                        String id, String algorithm, String alias, byte[] data, int seq) {
                    synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                        SessionCallbackRecord record =
                                (SessionCallbackRecord)
                                        TvAdManager.this.mSessionCallbackRecordMap.get(seq);
                        if (record == null) {
                            Log.e(TvAdManager.TAG, "Callback not found for seq " + seq);
                        } else {
                            record.postRequestSigning(id, algorithm, alias, data);
                        }
                    }
                }

                @Override // android.media.tv.ad.ITvAdClient
                public void onTvAdSessionData(String type, Bundle data, int seq) {
                    synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                        SessionCallbackRecord record =
                                (SessionCallbackRecord)
                                        TvAdManager.this.mSessionCallbackRecordMap.get(seq);
                        if (record == null) {
                            Log.e(TvAdManager.TAG, "Callback not found for seq " + seq);
                        } else {
                            record.postTvAdSessionData(type, data);
                        }
                    }
                }
            };

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionDataKey {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionDataType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionState {}

    public TvAdManager(ITvAdManager service, int userId) {
        this.mService = service;
        this.mUserId = userId;
        ITvAdManagerCallback managerCallback =
                new ITvAdManagerCallback.Stub() { // from class: android.media.tv.ad.TvAdManager.2
                    @Override // android.media.tv.ad.ITvAdManagerCallback
                    public void onAdServiceAdded(String serviceId) {
                        synchronized (TvAdManager.this.mLock) {
                            for (TvAdServiceCallbackRecord record :
                                    TvAdManager.this.mCallbackRecords) {
                                record.postAdServiceAdded(serviceId);
                            }
                        }
                    }

                    @Override // android.media.tv.ad.ITvAdManagerCallback
                    public void onAdServiceRemoved(String serviceId) {
                        synchronized (TvAdManager.this.mLock) {
                            for (TvAdServiceCallbackRecord record :
                                    TvAdManager.this.mCallbackRecords) {
                                record.postAdServiceRemoved(serviceId);
                            }
                        }
                    }

                    @Override // android.media.tv.ad.ITvAdManagerCallback
                    public void onAdServiceUpdated(String serviceId) {
                        synchronized (TvAdManager.this.mLock) {
                            for (TvAdServiceCallbackRecord record :
                                    TvAdManager.this.mCallbackRecords) {
                                record.postAdServiceUpdated(serviceId);
                            }
                        }
                    }
                };
        try {
            if (this.mService != null) {
                this.mService.registerCallback(managerCallback, this.mUserId);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<TvAdServiceInfo> getTvAdServiceList() {
        try {
            return this.mService.getTvAdServiceList(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createSession(
            String serviceId, String type, SessionCallback callback, Handler handler) {
        createSessionInternal(serviceId, type, callback, handler);
    }

    private void createSessionInternal(
            String serviceId, String type, SessionCallback callback, Handler handler) {
        Preconditions.checkNotNull(serviceId);
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(callback);
        Preconditions.checkNotNull(handler);
        SessionCallbackRecord record = new SessionCallbackRecord(callback, handler);
        synchronized (this.mSessionCallbackRecordMap) {
            int seq = this.mNextSeq;
            this.mNextSeq = seq + 1;
            this.mSessionCallbackRecordMap.put(seq, record);
            try {
                this.mService.createSession(this.mClient, serviceId, type, seq, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void sendAppLinkCommand(String serviceId, Bundle command) {
        try {
            this.mService.sendAppLinkCommand(serviceId, command, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(Executor executor, TvAdServiceCallback callback) {
        Preconditions.checkNotNull(callback);
        Preconditions.checkNotNull(executor);
        synchronized (this.mLock) {
            this.mCallbackRecords.add(new TvAdServiceCallbackRecord(callback, executor));
        }
    }

    public void unregisterCallback(TvAdServiceCallback callback) {
        Preconditions.checkNotNull(callback);
        synchronized (this.mLock) {
            Iterator<TvAdServiceCallbackRecord> it = this.mCallbackRecords.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                TvAdServiceCallbackRecord record = it.next();
                if (record.getCallback() == callback) {
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
        private final ITvAdManager mService;
        private final SparseArray<SessionCallbackRecord> mSessionCallbackRecordMap;
        private IBinder mToken;
        private final int mUserId;

        public interface FinishedInputEventCallback {
            void onFinishedInputEvent(Object obj, boolean z);
        }

        private Session(
                IBinder token,
                InputChannel channel,
                ITvAdManager service,
                int userId,
                int seq,
                SparseArray<SessionCallbackRecord> sessionCallbackRecordMap) {
            this.mHandler = new InputEventHandler(Looper.getMainLooper());
            this.mPendingEventPool = new Pools.SimplePool(20);
            this.mPendingEvents = new SparseArray<>(20);
            this.mToken = token;
            this.mInputChannel = channel;
            this.mService = service;
            this.mUserId = userId;
            this.mSeq = seq;
            this.mSessionCallbackRecordMap = sessionCallbackRecordMap;
        }

        public TvInputManager.Session getInputSession() {
            return this.mInputSession;
        }

        public void setInputSession(TvInputManager.Session inputSession) {
            this.mInputSession = inputSession;
        }

        public void release() {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.releaseSession(this.mToken, this.mUserId);
                releaseInternal();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setSurface(Surface surface) {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setSurface(this.mToken, surface, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void createMediaView(View view, Rect frame) {
            Preconditions.checkNotNull(view);
            Preconditions.checkNotNull(frame);
            if (view.getWindowToken() == null) {
                throw new IllegalStateException("view must be attached to a window");
            }
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.createMediaView(
                        this.mToken, view.getWindowToken(), frame, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void relayoutMediaView(Rect frame) {
            Preconditions.checkNotNull(frame);
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.relayoutMediaView(this.mToken, frame, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void removeMediaView() {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.removeMediaView(this.mToken, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void dispatchSurfaceChanged(int format, int width, int height) {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.dispatchSurfaceChanged(
                        this.mToken, format, width, height, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private void flushPendingEventsLocked() {
            this.mHandler.removeMessages(3);
            int count = this.mPendingEvents.size();
            for (int i = 0; i < count; i++) {
                int seq = this.mPendingEvents.keyAt(i);
                Message msg = this.mHandler.obtainMessage(3, seq, 0);
                msg.setAsynchronous(true);
                msg.sendToTarget();
            }
        }

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

        void startAdService() {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.startAdService(this.mToken, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void stopAdService() {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.stopAdService(this.mToken, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void resetAdService() {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.resetAdService(this.mToken, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentVideoBounds(Rect bounds) {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentVideoBounds(this.mToken, bounds, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentChannelUri(Uri channelUri) {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentChannelUri(this.mToken, channelUri, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTrackInfoList(List<TvTrackInfo> tracks) {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTrackInfoList(this.mToken, tracks, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentTvInputId(String inputId) {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentTvInputId(this.mToken, inputId, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendSigningResult(String signingId, byte[] result) {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendSigningResult(this.mToken, signingId, result, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyError(String errMsg, Bundle params) {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyError(this.mToken, errMsg, params, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTvMessage(int type, Bundle data) {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTvMessage(this.mToken, type, data, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTvInputSessionData(String type, Bundle data) {
            if (this.mToken == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTvInputSessionData(this.mToken, type, data, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int dispatchInputEvent(
                InputEvent event,
                Object token,
                FinishedInputEventCallback callback,
                Handler handler) {
            Preconditions.checkNotNull(event);
            Preconditions.checkNotNull(callback);
            Preconditions.checkNotNull(handler);
            synchronized (this.mHandler) {
                if (this.mInputChannel == null) {
                    return 0;
                }
                PendingEvent p = obtainPendingEventLocked(event, token, callback, handler);
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    return sendInputEventOnMainLooperLocked(p);
                }
                Message msg = this.mHandler.obtainMessage(1, p);
                msg.setAsynchronous(true);
                this.mHandler.sendMessage(msg);
                return -1;
            }
        }

        private PendingEvent obtainPendingEventLocked(
                InputEvent event,
                Object token,
                FinishedInputEventCallback callback,
                Handler handler) {
            PendingEvent p = this.mPendingEventPool.acquire();
            if (p == null) {
                p = new PendingEvent();
            }
            p.mEvent = event;
            p.mEventToken = token;
            p.mCallback = callback;
            p.mEventHandler = handler;
            return p;
        }

        private final class InputEventHandler extends Handler {
            public static final int MSG_FLUSH_INPUT_EVENT = 3;
            public static final int MSG_SEND_INPUT_EVENT = 1;
            public static final int MSG_TIMEOUT_INPUT_EVENT = 2;

            InputEventHandler(Looper looper) {
                super(looper, null, true);
            }

            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Session.this.sendInputEventAndReportResultOnMainLooper(
                                (PendingEvent) msg.obj);
                        break;
                    case 2:
                        Session.this.finishedInputEvent(msg.arg1, false, true);
                        break;
                    case 3:
                        Session.this.finishedInputEvent(msg.arg1, false, false);
                        break;
                }
            }
        }

        void invokeFinishedInputEventCallback(PendingEvent p, boolean handled) {
            p.mHandled = handled;
            if (p.mEventHandler.getLooper().isCurrentThread()) {
                p.run();
                return;
            }
            Message msg = Message.obtain(p.mEventHandler, p);
            msg.setAsynchronous(true);
            msg.sendToTarget();
        }

        public void sendInputEventAndReportResultOnMainLooper(PendingEvent p) {
            synchronized (this.mHandler) {
                int result = sendInputEventOnMainLooperLocked(p);
                if (result == -1) {
                    return;
                }
                invokeFinishedInputEventCallback(p, false);
            }
        }

        private int sendInputEventOnMainLooperLocked(PendingEvent p) {
            if (this.mInputChannel != null) {
                if (this.mSender == null) {
                    this.mSender =
                            new TvInputEventSender(this.mInputChannel, this.mHandler.getLooper());
                }
                InputEvent event = p.mEvent;
                int seq = event.getSequenceNumber();
                if (this.mSender.sendInputEvent(seq, event)) {
                    this.mPendingEvents.put(seq, p);
                    Message msg = this.mHandler.obtainMessage(2, p);
                    msg.setAsynchronous(true);
                    this.mHandler.sendMessageDelayed(msg, INPUT_SESSION_NOT_RESPONDING_TIMEOUT);
                    return -1;
                }
                Log.w(
                        TvAdManager.TAG,
                        "Unable to send input event to session: "
                                + this.mToken
                                + " dropping:"
                                + event);
                return 0;
            }
            return 0;
        }

        void finishedInputEvent(int seq, boolean handled, boolean timeout) {
            synchronized (this.mHandler) {
                int index = this.mPendingEvents.indexOfKey(seq);
                if (index < 0) {
                    return;
                }
                PendingEvent p = this.mPendingEvents.valueAt(index);
                this.mPendingEvents.removeAt(index);
                if (timeout) {
                    Log.w(
                            TvAdManager.TAG,
                            "Timeout waiting for session to handle input event after 2500 ms: "
                                    + this.mToken);
                } else {
                    this.mHandler.removeMessages(2, p);
                }
                invokeFinishedInputEventCallback(p, handled);
            }
        }

        public void recyclePendingEventLocked(PendingEvent p) {
            p.recycle();
            this.mPendingEventPool.release(p);
        }

        private final class TvInputEventSender extends InputEventSender {
            TvInputEventSender(InputChannel inputChannel, Looper looper) {
                super(inputChannel, looper);
            }

            @Override // android.view.InputEventSender
            public void onInputEventFinished(int seq, boolean handled) {
                Session.this.finishedInputEvent(seq, handled, false);
            }
        }

        private final class PendingEvent implements Runnable {
            public FinishedInputEventCallback mCallback;
            public InputEvent mEvent;
            public Handler mEventHandler;
            public Object mEventToken;
            public boolean mHandled;

            private PendingEvent() {}

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

    public abstract static class SessionCallback {
        public void onSessionCreated(Session session) {}

        public void onSessionReleased(Session session) {}

        public void onLayoutSurface(Session session, int left, int top, int right, int bottom) {}

        public void onRequestCurrentVideoBounds(Session session) {}

        public void onRequestCurrentChannelUri(Session session) {}

        public void onRequestTrackInfoList(Session session) {}

        public void onRequestCurrentTvInputId(Session session) {}

        public void onRequestSigning(
                Session session, String signingId, String algorithm, String alias, byte[] data) {}
    }

    public abstract static class TvAdServiceCallback {
        public void onAdServiceAdded(String serviceId) {}

        public void onAdServiceRemoved(String serviceId) {}

        public void onAdServiceUpdated(String serviceId) {}
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
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.media.tv.ad.TvAdManager.SessionCallbackRecord.1
                        @Override // java.lang.Runnable
                        public void run() {
                            SessionCallbackRecord.this.mSessionCallback.onSessionCreated(session);
                        }
                    });
        }

        void postSessionReleased() {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.media.tv.ad.TvAdManager.SessionCallbackRecord.2
                        @Override // java.lang.Runnable
                        public void run() {
                            SessionCallbackRecord.this.mSessionCallback.onSessionReleased(
                                    SessionCallbackRecord.this.mSession);
                        }
                    });
        }

        void postLayoutSurface(final int left, final int top, final int right, final int bottom) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.media.tv.ad.TvAdManager.SessionCallbackRecord.3
                        @Override // java.lang.Runnable
                        public void run() {
                            SessionCallbackRecord.this.mSessionCallback.onLayoutSurface(
                                    SessionCallbackRecord.this.mSession, left, top, right, bottom);
                        }
                    });
        }

        void postRequestCurrentVideoBounds() {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.media.tv.ad.TvAdManager.SessionCallbackRecord.4
                        @Override // java.lang.Runnable
                        public void run() {
                            SessionCallbackRecord.this.mSessionCallback.onRequestCurrentVideoBounds(
                                    SessionCallbackRecord.this.mSession);
                        }
                    });
        }

        void postRequestCurrentChannelUri() {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.media.tv.ad.TvAdManager.SessionCallbackRecord.5
                        @Override // java.lang.Runnable
                        public void run() {
                            SessionCallbackRecord.this.mSessionCallback.onRequestCurrentChannelUri(
                                    SessionCallbackRecord.this.mSession);
                        }
                    });
        }

        void postRequestTrackInfoList() {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.media.tv.ad.TvAdManager.SessionCallbackRecord.6
                        @Override // java.lang.Runnable
                        public void run() {
                            SessionCallbackRecord.this.mSessionCallback.onRequestTrackInfoList(
                                    SessionCallbackRecord.this.mSession);
                        }
                    });
        }

        void postRequestCurrentTvInputId() {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.media.tv.ad.TvAdManager.SessionCallbackRecord.7
                        @Override // java.lang.Runnable
                        public void run() {
                            SessionCallbackRecord.this.mSessionCallback.onRequestCurrentTvInputId(
                                    SessionCallbackRecord.this.mSession);
                        }
                    });
        }

        void postRequestSigning(
                final String id, final String algorithm, final String alias, final byte[] data) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.media.tv.ad.TvAdManager.SessionCallbackRecord.8
                        @Override // java.lang.Runnable
                        public void run() {
                            SessionCallbackRecord.this.mSessionCallback.onRequestSigning(
                                    SessionCallbackRecord.this.mSession,
                                    id,
                                    algorithm,
                                    alias,
                                    data);
                        }
                    });
        }

        void postTvAdSessionData(final String type, final Bundle data) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.media.tv.ad.TvAdManager.SessionCallbackRecord.9
                        @Override // java.lang.Runnable
                        public void run() {
                            if (SessionCallbackRecord.this.mSession.getInputSession() != null) {
                                SessionCallbackRecord.this
                                        .mSession
                                        .getInputSession()
                                        .notifyTvAdSessionData(type, data);
                            }
                        }
                    });
        }
    }

    private static final class TvAdServiceCallbackRecord {
        private final TvAdServiceCallback mCallback;
        private final Executor mExecutor;

        TvAdServiceCallbackRecord(TvAdServiceCallback callback, Executor executor) {
            this.mCallback = callback;
            this.mExecutor = executor;
        }

        public TvAdServiceCallback getCallback() {
            return this.mCallback;
        }

        public void postAdServiceAdded(final String serviceId) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TvAdServiceCallbackRecord.this.mCallback.onAdServiceAdded(serviceId);
                        }
                    });
        }

        public void postAdServiceRemoved(final String serviceId) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord.2
                        @Override // java.lang.Runnable
                        public void run() {
                            TvAdServiceCallbackRecord.this.mCallback.onAdServiceRemoved(serviceId);
                        }
                    });
        }

        public void postAdServiceUpdated(final String serviceId) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord.3
                        @Override // java.lang.Runnable
                        public void run() {
                            TvAdServiceCallbackRecord.this.mCallback.onAdServiceUpdated(serviceId);
                        }
                    });
        }
    }
}
