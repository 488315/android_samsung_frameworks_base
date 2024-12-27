package android.media.tv.ad;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.tv.TvTrackInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.android.internal.os.SomeArgs;

import java.util.ArrayList;
import java.util.List;

public abstract class TvAdService extends Service {
    private static final boolean DEBUG = false;
    private static final int DETACH_MEDIA_VIEW_TIMEOUT_MS = 5000;
    public static final String SERVICE_INTERFACE = "android.media.tv.ad.TvAdService";
    public static final String SERVICE_META_DATA = "android.media.tv.ad.service";
    private static final String TAG = "TvAdService";
    private final Handler mServiceHandler = new ServiceHandler();
    private final RemoteCallbackList<ITvAdServiceCallback> mCallbacks = new RemoteCallbackList<>();

    public abstract Session onCreateSession(String str, String str2);

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        ITvAdService.Stub tvAdServiceBinder =
                new ITvAdService.Stub() { // from class: android.media.tv.ad.TvAdService.1
                    @Override // android.media.tv.ad.ITvAdService
                    public void registerCallback(ITvAdServiceCallback cb) {
                        if (cb != null) {
                            TvAdService.this.mCallbacks.register(cb);
                        }
                    }

                    @Override // android.media.tv.ad.ITvAdService
                    public void unregisterCallback(ITvAdServiceCallback cb) {
                        if (cb != null) {
                            TvAdService.this.mCallbacks.unregister(cb);
                        }
                    }

                    @Override // android.media.tv.ad.ITvAdService
                    public void createSession(
                            InputChannel channel,
                            ITvAdSessionCallback cb,
                            String serviceId,
                            String type) {
                        if (cb == null) {
                            return;
                        }
                        SomeArgs args = SomeArgs.obtain();
                        args.arg1 = channel;
                        args.arg2 = cb;
                        args.arg3 = serviceId;
                        args.arg4 = type;
                        TvAdService.this.mServiceHandler.obtainMessage(1, args).sendToTarget();
                    }

                    @Override // android.media.tv.ad.ITvAdService
                    public void sendAppLinkCommand(Bundle command) {
                        TvAdService.this.onAppLinkCommand(command);
                    }
                };
        return tvAdServiceBinder;
    }

    public void onAppLinkCommand(Bundle command) {}

    public abstract static class Session implements KeyEvent.Callback {
        private final Context mContext;
        final Handler mHandler;
        private Rect mMediaFrame;
        private View mMediaView;
        private MediaViewCleanUpTask mMediaViewCleanUpTask;
        private FrameLayout mMediaViewContainer;
        private boolean mMediaViewEnabled;
        private ITvAdSessionCallback mSessionCallback;
        private Surface mSurface;
        private final WindowManager mWindowManager;
        private WindowManager.LayoutParams mWindowParams;
        private IBinder mWindowToken;
        private final KeyEvent.DispatcherState mDispatcherState = new KeyEvent.DispatcherState();
        private final Object mLock = new Object();
        private final List<Runnable> mPendingActions = new ArrayList();

        public abstract void onRelease();

        public abstract boolean onSetSurface(Surface surface);

        public Session(Context context) {
            this.mContext = context;
            this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            this.mHandler = new Handler(context.getMainLooper());
        }

        public void setMediaViewEnabled(final boolean enable) {
            this.mHandler.post(
                    new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (enable == Session.this.mMediaViewEnabled) {
                                return;
                            }
                            Session.this.mMediaViewEnabled = enable;
                            if (enable) {
                                if (Session.this.mWindowToken != null) {
                                    Session.this.createMediaView(
                                            Session.this.mWindowToken, Session.this.mMediaFrame);
                                    return;
                                }
                                return;
                            }
                            Session.this.removeMediaView(false);
                        }
                    });
        }

        public boolean isMediaViewEnabled() {
            return this.mMediaViewEnabled;
        }

        void release() {
            onRelease();
            if (this.mSurface != null) {
                this.mSurface.release();
                this.mSurface = null;
            }
            synchronized (this.mLock) {
                this.mSessionCallback = null;
                this.mPendingActions.clear();
            }
            removeMediaView(true);
        }

        public void onStartAdService() {}

        public void onStopAdService() {}

        public void onResetAdService() {}

        void startAdService() {
            onStartAdService();
        }

        void stopAdService() {
            onStopAdService();
        }

        void resetAdService() {
            onResetAdService();
        }

        public void requestCurrentVideoBounds() {
            executeOrPostRunnableOnMainThread(
                    new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.2
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (Session.this.mSessionCallback != null) {
                                    Session.this.mSessionCallback.onRequestCurrentVideoBounds();
                                }
                            } catch (RemoteException e) {
                                Log.w(TvAdService.TAG, "error in requestCurrentVideoBounds", e);
                            }
                        }
                    });
        }

        public void requestCurrentChannelUri() {
            executeOrPostRunnableOnMainThread(
                    new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.3
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (Session.this.mSessionCallback != null) {
                                    Session.this.mSessionCallback.onRequestCurrentChannelUri();
                                }
                            } catch (RemoteException e) {
                                Log.w(TvAdService.TAG, "error in requestCurrentChannelUri", e);
                            }
                        }
                    });
        }

        public void requestTrackInfoList() {
            executeOrPostRunnableOnMainThread(
                    new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.4
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (Session.this.mSessionCallback != null) {
                                    Session.this.mSessionCallback.onRequestTrackInfoList();
                                }
                            } catch (RemoteException e) {
                                Log.w(TvAdService.TAG, "error in requestTrackInfoList", e);
                            }
                        }
                    });
        }

        public void requestCurrentTvInputId() {
            executeOrPostRunnableOnMainThread(
                    new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.5
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (Session.this.mSessionCallback != null) {
                                    Session.this.mSessionCallback.onRequestCurrentTvInputId();
                                }
                            } catch (RemoteException e) {
                                Log.w(TvAdService.TAG, "error in requestCurrentTvInputId", e);
                            }
                        }
                    });
        }

        public void requestSigning(
                final String signingId,
                final String algorithm,
                final String alias,
                final byte[] data) {
            executeOrPostRunnableOnMainThread(
                    new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.6
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (Session.this.mSessionCallback != null) {
                                    Session.this.mSessionCallback.onRequestSigning(
                                            signingId, algorithm, alias, data);
                                }
                            } catch (RemoteException e) {
                                Log.w(TvAdService.TAG, "error in requestSigning", e);
                            }
                        }
                    });
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyLongPress(int keyCode, KeyEvent event) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyUp(int keyCode, KeyEvent event) {
            return false;
        }

        public boolean onTouchEvent(MotionEvent event) {
            return false;
        }

        public boolean onTrackballEvent(MotionEvent event) {
            return false;
        }

        public boolean onGenericMotionEvent(MotionEvent event) {
            return false;
        }

        public void layoutSurface(
                final int left, final int top, final int right, final int bottom) {
            if (left > right || top > bottom) {
                throw new IllegalArgumentException("Invalid parameter");
            }
            executeOrPostRunnableOnMainThread(
                    new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.7
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (Session.this.mSessionCallback != null) {
                                    Session.this.mSessionCallback.onLayoutSurface(
                                            left, top, right, bottom);
                                }
                            } catch (RemoteException e) {
                                Log.w(TvAdService.TAG, "error in layoutSurface", e);
                            }
                        }
                    });
        }

        public void onSurfaceChanged(int format, int width, int height) {}

        public void onCurrentVideoBounds(Rect bounds) {}

        public void onCurrentChannelUri(Uri channelUri) {}

        public void onTrackInfoList(List<TvTrackInfo> tracks) {}

        public void onCurrentTvInputId(String inputId) {}

        public void onSigningResult(String signingId, byte[] result) {}

        public void onError(String errMsg, Bundle params) {}

        public void onTvMessage(int type, Bundle data) {}

        public void onTvInputSessionData(String type, Bundle data) {}

        public void onMediaViewSizeChanged(int width, int height) {}

        public View onCreateMediaView() {
            return null;
        }

        public void sendTvAdSessionData(final String type, final Bundle data) {
            executeOrPostRunnableOnMainThread(
                    new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.8
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (Session.this.mSessionCallback != null) {
                                    Session.this.mSessionCallback.onTvAdSessionData(type, data);
                                }
                            } catch (RemoteException e) {
                                Log.w(TvAdService.TAG, "error in sendTvAdSessionData", e);
                            }
                        }
                    });
        }

        public void notifySessionStateChanged(final int state, final int err) {
            executeOrPostRunnableOnMainThread(
                    new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.9
                        @Override // java.lang.Runnable
                        public void run() {}
                    });
        }

        int dispatchInputEvent(InputEvent event, InputEventReceiver receiver) {
            if (event instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) event;
                return keyEvent.dispatch(this, this.mDispatcherState, this) ? 1 : 0;
            }
            if (event instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent) event;
                int source = motionEvent.getSource();
                return motionEvent.isTouchEvent()
                        ? onTouchEvent(motionEvent) ? 1 : 0
                        : (source & 4) != 0
                                ? onTrackballEvent(motionEvent) ? 1 : 0
                                : onGenericMotionEvent(motionEvent) ? 1 : 0;
            }
            return 0;
        }

        public void initialize(ITvAdSessionCallback callback) {
            synchronized (this.mLock) {
                this.mSessionCallback = callback;
                for (Runnable runnable : this.mPendingActions) {
                    runnable.run();
                }
                this.mPendingActions.clear();
            }
        }

        void setSurface(Surface surface) {
            onSetSurface(surface);
            if (this.mSurface != null) {
                this.mSurface.release();
            }
            this.mSurface = surface;
        }

        void dispatchSurfaceChanged(int format, int width, int height) {
            onSurfaceChanged(format, width, height);
        }

        void sendCurrentVideoBounds(Rect bounds) {
            onCurrentVideoBounds(bounds);
        }

        void sendCurrentChannelUri(Uri channelUri) {
            onCurrentChannelUri(channelUri);
        }

        void sendTrackInfoList(List<TvTrackInfo> tracks) {
            onTrackInfoList(tracks);
        }

        void sendCurrentTvInputId(String inputId) {
            onCurrentTvInputId(inputId);
        }

        void sendSigningResult(String signingId, byte[] result) {
            onSigningResult(signingId, result);
        }

        void notifyError(String errMsg, Bundle params) {
            onError(errMsg, params);
        }

        void notifyTvMessage(int type, Bundle data) {
            onTvMessage(type, data);
        }

        void notifyTvInputSessionData(String type, Bundle data) {
            onTvInputSessionData(type, data);
        }

        private void executeOrPostRunnableOnMainThread(Runnable action) {
            synchronized (this.mLock) {
                if (this.mSessionCallback == null) {
                    this.mPendingActions.add(action);
                } else if (this.mHandler.getLooper().isCurrentThread()) {
                    action.run();
                } else {
                    this.mHandler.post(action);
                }
            }
        }

        void createMediaView(IBinder windowToken, Rect frame) {
            if (this.mMediaViewContainer != null) {
                removeMediaView(false);
            }
            this.mWindowToken = windowToken;
            this.mMediaFrame = frame;
            onMediaViewSizeChanged(frame.right - frame.left, frame.bottom - frame.top);
            if (!this.mMediaViewEnabled) {
                return;
            }
            this.mMediaView = onCreateMediaView();
            if (this.mMediaView == null) {
                return;
            }
            if (this.mMediaViewCleanUpTask != null) {
                this.mMediaViewCleanUpTask.cancel(true);
                this.mMediaViewCleanUpTask = null;
            }
            this.mMediaViewContainer = new FrameLayout(this.mContext.getApplicationContext());
            this.mMediaViewContainer.addView(this.mMediaView);
            int flags = ActivityManager.isHighEndGfx() ? 536 | 16777216 : 536;
            this.mWindowParams =
                    new WindowManager.LayoutParams(
                            frame.right - frame.left,
                            frame.bottom - frame.top,
                            frame.left,
                            frame.top,
                            1001,
                            flags,
                            -2);
            this.mWindowParams.privateFlags |= 64;
            this.mWindowParams.gravity = 8388659;
            this.mWindowParams.token = windowToken;
            this.mWindowManager.addView(this.mMediaViewContainer, this.mWindowParams);
        }

        void relayoutMediaView(Rect frame) {
            if (this.mMediaFrame == null
                    || this.mMediaFrame.width() != frame.width()
                    || this.mMediaFrame.height() != frame.height()) {
                onMediaViewSizeChanged(frame.right - frame.left, frame.bottom - frame.top);
            }
            this.mMediaFrame = frame;
            if (!this.mMediaViewEnabled || this.mMediaViewContainer == null) {
                return;
            }
            this.mWindowParams.x = frame.left;
            this.mWindowParams.y = frame.top;
            this.mWindowParams.width = frame.right - frame.left;
            this.mWindowParams.height = frame.bottom - frame.top;
            this.mWindowManager.updateViewLayout(this.mMediaViewContainer, this.mWindowParams);
        }

        void removeMediaView(boolean clearWindowToken) {
            if (clearWindowToken) {
                this.mWindowToken = null;
                this.mMediaFrame = null;
            }
            if (this.mMediaViewContainer != null) {
                this.mMediaViewContainer.removeView(this.mMediaView);
                this.mMediaView = null;
                this.mWindowManager.removeView(this.mMediaViewContainer);
                this.mMediaViewContainer = null;
                this.mWindowParams = null;
            }
        }

        void scheduleMediaViewCleanup() {
            View mediaViewParent = this.mMediaViewContainer;
            if (mediaViewParent != null) {
                this.mMediaViewCleanUpTask = new MediaViewCleanUpTask();
                this.mMediaViewCleanUpTask.executeOnExecutor(
                        AsyncTask.THREAD_POOL_EXECUTOR, mediaViewParent);
            }
        }
    }

    private static final class MediaViewCleanUpTask extends AsyncTask<View, Void, Void> {
        private MediaViewCleanUpTask() {}

        @Override // android.os.AsyncTask
        public Void doInBackground(View... views) {
            View mediaViewParent = views[0];
            try {
                Thread.sleep(5000L);
                if (!isCancelled() && mediaViewParent.isAttachedToWindow()) {
                    Log.e(
                            TvAdService.TAG,
                            "Time out on releasing media view. Killing "
                                    + mediaViewParent.getContext().getPackageName());
                    Process.killProcess(Process.myPid());
                }
                return null;
            } catch (InterruptedException e) {
                return null;
            }
        }
    }

    private final class ServiceHandler extends Handler {
        private static final int DO_CREATE_SESSION = 1;
        private static final int DO_NOTIFY_SESSION_CREATED = 2;

        private ServiceHandler() {}

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SomeArgs args = (SomeArgs) msg.obj;
                    InputChannel channel = (InputChannel) args.arg1;
                    ITvAdSessionCallback cb = (ITvAdSessionCallback) args.arg2;
                    String serviceId = (String) args.arg3;
                    String type = (String) args.arg4;
                    args.recycle();
                    Session sessionImpl = TvAdService.this.onCreateSession(serviceId, type);
                    if (sessionImpl == null) {
                        try {
                            cb.onSessionCreated(null);
                            break;
                        } catch (RemoteException e) {
                            Log.e(TvAdService.TAG, "error in onSessionCreated", e);
                            return;
                        }
                    } else {
                        ITvAdSession stub =
                                new ITvAdSessionWrapper(TvAdService.this, sessionImpl, channel);
                        SomeArgs someArgs = SomeArgs.obtain();
                        someArgs.arg1 = sessionImpl;
                        someArgs.arg2 = stub;
                        someArgs.arg3 = cb;
                        TvAdService.this.mServiceHandler.obtainMessage(2, someArgs).sendToTarget();
                        break;
                    }
                case 2:
                    SomeArgs args2 = (SomeArgs) msg.obj;
                    Session sessionImpl2 = (Session) args2.arg1;
                    ITvAdSession stub2 = (ITvAdSession) args2.arg2;
                    ITvAdSessionCallback cb2 = (ITvAdSessionCallback) args2.arg3;
                    try {
                        cb2.onSessionCreated(stub2);
                    } catch (RemoteException e2) {
                        Log.e(TvAdService.TAG, "error in onSessionCreated", e2);
                    }
                    if (sessionImpl2 != null) {
                        sessionImpl2.initialize(cb2);
                    }
                    args2.recycle();
                    break;
                default:
                    Log.w(TvAdService.TAG, "Unhandled message code: " + msg.what);
                    break;
            }
        }
    }
}
