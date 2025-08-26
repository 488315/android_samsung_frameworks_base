package android.media.tv.ad;

import android.graphics.Rect;
import android.media.tv.TvInputManager;
import android.media.tv.TvTrackInfo;
import android.media.tv.ad.ITvAdClient;
import android.media.tv.ad.ITvAdManagerCallback;
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

/* loaded from: classes3.dex */
public final class TvAdManager {
    public static final String ACTION_APP_LINK_COMMAND = "android.media.tv.ad.action.APP_LINK_COMMAND";
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
    public static final String SESSION_DATA_TYPE_REMOVE_BROADCAST_INFO_REQUEST = "remove_broadcast_info_request";
    public static final int SESSION_STATE_ERROR = 3;
    public static final int SESSION_STATE_RUNNING = 2;
    public static final int SESSION_STATE_STOPPED = 1;
    private static final String TAG = "TvAdManager";
    private int mNextSeq;
    private final ITvAdManager mService;
    private final int mUserId;
    private final SparseArray<SessionCallbackRecord> mSessionCallbackRecordMap = new SparseArray<>();
    private final List<TvAdServiceCallbackRecord> mCallbackRecords = new ArrayList();
    private final Object mLock = new Object();
    private final ITvAdClient mClient = new ITvAdClient.Stub() { // from class: android.media.tv.ad.TvAdManager.1
        @Override // android.media.tv.ad.ITvAdClient
        public void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) {
            Session session;
            synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvAdManager.TAG, "Callback not found for " + iBinder);
                } else {
                    if (iBinder != null) {
                        session = new Session(iBinder, inputChannel, TvAdManager.this.mService, TvAdManager.this.mUserId, i, TvAdManager.this.mSessionCallbackRecordMap);
                    } else {
                        TvAdManager.this.mSessionCallbackRecordMap.delete(i);
                        session = null;
                    }
                    sessionCallbackRecord.postSessionCreated(session);
                }
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onSessionReleased(int i) {
            synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvAdManager.this.mSessionCallbackRecordMap.get(i);
                TvAdManager.this.mSessionCallbackRecordMap.delete(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvAdManager.TAG, "Callback not found for seq:" + i);
                } else {
                    sessionCallbackRecord.mSession.releaseInternal();
                    sessionCallbackRecord.postSessionReleased();
                }
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) {
            synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvAdManager.this.mSessionCallbackRecordMap.get(i5);
                if (sessionCallbackRecord == null) {
                    Log.e(TvAdManager.TAG, "Callback not found for seq " + i5);
                    return;
                }
                sessionCallbackRecord.postLayoutSurface(i, i2, i3, i4);
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentVideoBounds(int i) {
            synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvAdManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestCurrentVideoBounds();
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentChannelUri(int i) {
            synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvAdManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestCurrentChannelUri();
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestTrackInfoList(int i) {
            synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvAdManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestTrackInfoList();
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentTvInputId(int i) {
            synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvAdManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestCurrentTvInputId();
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestSigning(String str, String str2, String str3, byte[] bArr, int i) {
            synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvAdManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postRequestSigning(str, str2, str3, bArr);
            }
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onTvAdSessionData(String str, Bundle bundle, int i) {
            synchronized (TvAdManager.this.mSessionCallbackRecordMap) {
                SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord) TvAdManager.this.mSessionCallbackRecordMap.get(i);
                if (sessionCallbackRecord == null) {
                    Log.e(TvAdManager.TAG, "Callback not found for seq " + i);
                    return;
                }
                sessionCallbackRecord.postTvAdSessionData(str, bundle);
            }
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    public static abstract class SessionCallback {
        public void onLayoutSurface(Session session, int i, int i2, int i3, int i4) {
        }

        public void onRequestCurrentChannelUri(Session session) {
        }

        public void onRequestCurrentTvInputId(Session session) {
        }

        public void onRequestCurrentVideoBounds(Session session) {
        }

        public void onRequestSigning(Session session, String str, String str2, String str3, byte[] bArr) {
        }

        public void onRequestTrackInfoList(Session session) {
        }

        public void onSessionCreated(Session session) {
        }

        public void onSessionReleased(Session session) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionDataKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionDataType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionState {
    }

    public static abstract class TvAdServiceCallback {
        public void onAdServiceAdded(String str) {
        }

        public void onAdServiceRemoved(String str) {
        }

        public void onAdServiceUpdated(String str) {
        }
    }

    public TvAdManager(ITvAdManager iTvAdManager, int i) {
        this.mService = iTvAdManager;
        this.mUserId = i;
        ITvAdManagerCallback.Stub stub = new ITvAdManagerCallback.Stub() { // from class: android.media.tv.ad.TvAdManager.2
            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceAdded(String str) {
                synchronized (TvAdManager.this.mLock) {
                    Iterator it = TvAdManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvAdServiceCallbackRecord) it.next()).postAdServiceAdded(str);
                    }
                }
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceRemoved(String str) {
                synchronized (TvAdManager.this.mLock) {
                    Iterator it = TvAdManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvAdServiceCallbackRecord) it.next()).postAdServiceRemoved(str);
                    }
                }
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceUpdated(String str) {
                synchronized (TvAdManager.this.mLock) {
                    Iterator it = TvAdManager.this.mCallbackRecords.iterator();
                    while (it.hasNext()) {
                        ((TvAdServiceCallbackRecord) it.next()).postAdServiceUpdated(str);
                    }
                }
            }
        };
        if (iTvAdManager != null) {
            try {
                iTvAdManager.registerCallback(stub, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public List<TvAdServiceInfo> getTvAdServiceList() {
        try {
            return this.mService.getTvAdServiceList(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createSession(String str, String str2, SessionCallback sessionCallback, Handler handler) {
        createSessionInternal(str, str2, sessionCallback, handler);
    }

    private void createSessionInternal(String str, String str2, SessionCallback sessionCallback, Handler handler) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(str2);
        Preconditions.checkNotNull(sessionCallback);
        Preconditions.checkNotNull(handler);
        SessionCallbackRecord sessionCallbackRecord = new SessionCallbackRecord(sessionCallback, handler);
        synchronized (this.mSessionCallbackRecordMap) {
            int i = this.mNextSeq;
            this.mNextSeq = i + 1;
            this.mSessionCallbackRecordMap.put(i, sessionCallbackRecord);
            try {
                this.mService.createSession(this.mClient, str, str2, i, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void sendAppLinkCommand(String str, Bundle bundle) {
        try {
            this.mService.sendAppLinkCommand(str, bundle, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(Executor executor, TvAdServiceCallback tvAdServiceCallback) {
        Preconditions.checkNotNull(tvAdServiceCallback);
        Preconditions.checkNotNull(executor);
        synchronized (this.mLock) {
            this.mCallbackRecords.add(new TvAdServiceCallbackRecord(tvAdServiceCallback, executor));
        }
    }

    public void unregisterCallback(TvAdServiceCallback tvAdServiceCallback) {
        Preconditions.checkNotNull(tvAdServiceCallback);
        synchronized (this.mLock) {
            Iterator<TvAdServiceCallbackRecord> it = this.mCallbackRecords.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().getCallback() == tvAdServiceCallback) {
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

        private Session(IBinder iBinder, InputChannel inputChannel, ITvAdManager iTvAdManager, int i, int i2, SparseArray<SessionCallbackRecord> sparseArray) {
            this.mHandler = new InputEventHandler(Looper.getMainLooper());
            this.mPendingEventPool = new Pools.SimplePool(20);
            this.mPendingEvents = new SparseArray<>(20);
            this.mToken = iBinder;
            this.mInputChannel = inputChannel;
            this.mService = iTvAdManager;
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

        public void release() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.releaseSession(iBinder, this.mUserId);
                releaseInternal();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setSurface(Surface surface) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
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
                Log.w(TvAdManager.TAG, "The session has been already released");
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
                Log.w(TvAdManager.TAG, "The session has been already released");
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
                Log.w(TvAdManager.TAG, "The session has been already released");
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
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.dispatchSurfaceChanged(iBinder, i, i2, i3, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private void flushPendingEventsLocked() {
            this.mHandler.removeMessages(3);
            int size = this.mPendingEvents.size();
            for (int i = 0; i < size; i++) {
                Message messageObtainMessage = this.mHandler.obtainMessage(3, this.mPendingEvents.keyAt(i), 0);
                messageObtainMessage.setAsynchronous(true);
                messageObtainMessage.sendToTarget();
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

        void startAdService() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.startAdService(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void stopAdService() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.stopAdService(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void resetAdService() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.resetAdService(iBinder, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentVideoBounds(Rect rect) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
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
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentChannelUri(iBinder, uri, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendTrackInfoList(List<TvTrackInfo> list) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendTrackInfoList(iBinder, list, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendCurrentTvInputId(String str) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendCurrentTvInputId(iBinder, str, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendSigningResult(String str, byte[] bArr) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendSigningResult(iBinder, str, bArr, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void notifyError(String str, Bundle bundle) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyError(iBinder, str, bundle, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTvMessage(int i, Bundle bundle) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTvMessage(iBinder, i, bundle, this.mUserId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void notifyTvInputSessionData(String str, Bundle bundle) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvAdManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.notifyTvInputSessionData(iBinder, str, bundle, this.mUserId);
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
                PendingEvent pendingEventObtainPendingEventLocked = obtainPendingEventLocked(inputEvent, obj, finishedInputEventCallback, handler);
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    return sendInputEventOnMainLooperLocked(pendingEventObtainPendingEventLocked);
                }
                Message messageObtainMessage = this.mHandler.obtainMessage(1, pendingEventObtainPendingEventLocked);
                messageObtainMessage.setAsynchronous(true);
                this.mHandler.sendMessage(messageObtainMessage);
                return -1;
            }
        }

        private PendingEvent obtainPendingEventLocked(InputEvent inputEvent, Object obj, FinishedInputEventCallback finishedInputEventCallback, Handler handler) {
            PendingEvent pendingEventAcquire = this.mPendingEventPool.acquire();
            if (pendingEventAcquire == null) {
                pendingEventAcquire = new PendingEvent();
            }
            pendingEventAcquire.mEvent = inputEvent;
            pendingEventAcquire.mEventToken = obj;
            pendingEventAcquire.mCallback = finishedInputEventCallback;
            pendingEventAcquire.mEventHandler = handler;
            return pendingEventAcquire;
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

        void invokeFinishedInputEventCallback(PendingEvent pendingEvent, boolean z) {
            pendingEvent.mHandled = z;
            if (pendingEvent.mEventHandler.getLooper().isCurrentThread()) {
                pendingEvent.run();
                return;
            }
            Message messageObtain = Message.obtain(pendingEvent.mEventHandler, pendingEvent);
            messageObtain.setAsynchronous(true);
            messageObtain.sendToTarget();
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
                Message messageObtainMessage = this.mHandler.obtainMessage(2, pendingEvent);
                messageObtainMessage.setAsynchronous(true);
                this.mHandler.sendMessageDelayed(messageObtainMessage, INPUT_SESSION_NOT_RESPONDING_TIMEOUT);
                return -1;
            }
            Log.w(TvAdManager.TAG, "Unable to send input event to session: " + this.mToken + " dropping:" + inputEvent);
            return 0;
        }

        void finishedInputEvent(int i, boolean z, boolean z2) {
            synchronized (this.mHandler) {
                int iIndexOfKey = this.mPendingEvents.indexOfKey(i);
                if (iIndexOfKey < 0) {
                    return;
                }
                PendingEvent pendingEventValueAt = this.mPendingEvents.valueAt(iIndexOfKey);
                this.mPendingEvents.removeAt(iIndexOfKey);
                if (z2) {
                    Log.w(TvAdManager.TAG, "Timeout waiting for session to handle input event after 2500 ms: " + this.mToken);
                } else {
                    this.mHandler.removeMessages(2, pendingEventValueAt);
                }
                invokeFinishedInputEventCallback(pendingEventValueAt, z);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void recyclePendingEventLocked(PendingEvent pendingEvent) {
            pendingEvent.recycle();
            this.mPendingEventPool.release(pendingEvent);
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
            this.mHandler.post(new Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionCreated(session);
                }
            });
        }

        void postSessionReleased() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionReleased(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postLayoutSurface(final int i, final int i2, final int i3, final int i4) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onLayoutSurface(SessionCallbackRecord.this.mSession, i, i2, i3, i4);
                }
            });
        }

        void postRequestCurrentVideoBounds() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.4
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCurrentVideoBounds(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentChannelUri() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.5
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCurrentChannelUri(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestTrackInfoList() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.6
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestTrackInfoList(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestCurrentTvInputId() {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.7
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestCurrentTvInputId(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postRequestSigning(final String str, final String str2, final String str3, final byte[] bArr) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.8
                @Override // java.lang.Runnable
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRequestSigning(SessionCallbackRecord.this.mSession, str, str2, str3, bArr);
                }
            });
        }

        void postTvAdSessionData(final String str, final Bundle bundle) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.ad.TvAdManager.SessionCallbackRecord.9
                @Override // java.lang.Runnable
                public void run() {
                    if (SessionCallbackRecord.this.mSession.getInputSession() != null) {
                        SessionCallbackRecord.this.mSession.getInputSession().notifyTvAdSessionData(str, bundle);
                    }
                }
            });
        }
    }

    private static final class TvAdServiceCallbackRecord {
        private final TvAdServiceCallback mCallback;
        private final Executor mExecutor;

        TvAdServiceCallbackRecord(TvAdServiceCallback tvAdServiceCallback, Executor executor) {
            this.mCallback = tvAdServiceCallback;
            this.mExecutor = executor;
        }

        public TvAdServiceCallback getCallback() {
            return this.mCallback;
        }

        public void postAdServiceAdded(final String str) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    TvAdServiceCallbackRecord.this.mCallback.onAdServiceAdded(str);
                }
            });
        }

        public void postAdServiceRemoved(final String str) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord.2
                @Override // java.lang.Runnable
                public void run() {
                    TvAdServiceCallbackRecord.this.mCallback.onAdServiceRemoved(str);
                }
            });
        }

        public void postAdServiceUpdated(final String str) {
            this.mExecutor.execute(new Runnable() { // from class: android.media.tv.ad.TvAdManager.TvAdServiceCallbackRecord.3
                @Override // java.lang.Runnable
                public void run() {
                    TvAdServiceCallbackRecord.this.mCallback.onAdServiceUpdated(str);
                }
            });
        }
    }
}
