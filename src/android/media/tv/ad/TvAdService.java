package android.media.tv.ad;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.tv.TvTrackInfo;
import android.media.tv.ad.ITvAdService;
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
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class TvAdService extends Service {
    private static final boolean DEBUG = false;
    private static final int DETACH_MEDIA_VIEW_TIMEOUT_MS = 5000;
    public static final String SERVICE_INTERFACE = "android.media.tv.ad.TvAdService";
    public static final String SERVICE_META_DATA = "android.media.tv.ad.service";
    private static final String TAG = "TvAdService";
    private final Handler mServiceHandler = new ServiceHandler();
    private final RemoteCallbackList<ITvAdServiceCallback> mCallbacks = new RemoteCallbackList<>();

    public void onAppLinkCommand(Bundle bundle) {
    }

    public abstract Session onCreateSession(String str, String str2);

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new ITvAdService.Stub() { // from class: android.media.tv.ad.TvAdService.1
            @Override // android.media.tv.ad.ITvAdService
            public void registerCallback(ITvAdServiceCallback iTvAdServiceCallback) {
                if (iTvAdServiceCallback != null) {
                    TvAdService.this.mCallbacks.register(iTvAdServiceCallback);
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void unregisterCallback(ITvAdServiceCallback iTvAdServiceCallback) {
                if (iTvAdServiceCallback != null) {
                    TvAdService.this.mCallbacks.unregister(iTvAdServiceCallback);
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void createSession(InputChannel inputChannel, ITvAdSessionCallback iTvAdSessionCallback, String str, String str2) {
                if (iTvAdSessionCallback == null) {
                    return;
                }
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = inputChannel;
                obtain.arg2 = iTvAdSessionCallback;
                obtain.arg3 = str;
                obtain.arg4 = str2;
                TvAdService.this.mServiceHandler.obtainMessage(1, obtain).sendToTarget();
            }

            @Override // android.media.tv.ad.ITvAdService
            public void sendAppLinkCommand(Bundle bundle) {
                TvAdService.this.onAppLinkCommand(bundle);
            }
        };
    }

    public static abstract class Session implements KeyEvent.Callback {
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

        public View onCreateMediaView() {
            return null;
        }

        public void onCurrentChannelUri(Uri uri) {
        }

        public void onCurrentTvInputId(String str) {
        }

        public void onCurrentVideoBounds(Rect rect) {
        }

        public void onError(String str, Bundle bundle) {
        }

        public boolean onGenericMotionEvent(MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyDown(int i, KeyEvent keyEvent) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyUp(int i, KeyEvent keyEvent) {
            return false;
        }

        public void onMediaViewSizeChanged(int i, int i2) {
        }

        public abstract void onRelease();

        public void onResetAdService() {
        }

        public abstract boolean onSetSurface(Surface surface);

        public void onSigningResult(String str, byte[] bArr) {
        }

        public void onStartAdService() {
        }

        public void onStopAdService() {
        }

        public void onSurfaceChanged(int i, int i2, int i3) {
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        public void onTrackInfoList(List<TvTrackInfo> list) {
        }

        public boolean onTrackballEvent(MotionEvent motionEvent) {
            return false;
        }

        public void onTvInputSessionData(String str, Bundle bundle) {
        }

        public void onTvMessage(int i, Bundle bundle) {
        }

        public Session(Context context) {
            this.mContext = context;
            this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            this.mHandler = new Handler(context.getMainLooper());
        }

        public void setMediaViewEnabled(final boolean z) {
            this.mHandler.post(new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.1
                @Override // java.lang.Runnable
                public void run() {
                    if (z == Session.this.mMediaViewEnabled) {
                        return;
                    }
                    Session.this.mMediaViewEnabled = z;
                    if (z) {
                        if (Session.this.mWindowToken != null) {
                            Session session = Session.this;
                            session.createMediaView(session.mWindowToken, Session.this.mMediaFrame);
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
            Surface surface = this.mSurface;
            if (surface != null) {
                surface.release();
                this.mSurface = null;
            }
            synchronized (this.mLock) {
                this.mSessionCallback = null;
                this.mPendingActions.clear();
            }
            removeMediaView(true);
        }

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
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.2
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
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.3
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
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.4
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
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.5
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

        public void requestSigning(final String str, final String str2, final String str3, final byte[] bArr) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onRequestSigning(str, str2, str3, bArr);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvAdService.TAG, "error in requestSigning", e);
                    }
                }
            });
        }

        public void layoutSurface(final int i, final int i2, final int i3, final int i4) {
            if (i > i3 || i2 > i4) {
                throw new IllegalArgumentException("Invalid parameter");
            }
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onLayoutSurface(i, i2, i3, i4);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvAdService.TAG, "error in layoutSurface", e);
                    }
                }
            });
        }

        public void sendTvAdSessionData(final String str, final Bundle bundle) {
            executeOrPostRunnableOnMainThread(new Runnable() { // from class: android.media.tv.ad.TvAdService.Session.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTvAdSessionData(str, bundle);
                        }
                    } catch (RemoteException e) {
                        Log.w(TvAdService.TAG, "error in sendTvAdSessionData", e);
                    }
                }
            });
        }

        public void notifySessionStateChanged(final int i, final int i2) {
            executeOrPostRunnableOnMainThread(new Runnable(this) { // from class: android.media.tv.ad.TvAdService.Session.9
                @Override // java.lang.Runnable
                public void run() {
                }
            });
        }

        int dispatchInputEvent(InputEvent inputEvent, InputEventReceiver inputEventReceiver) {
            if (inputEvent instanceof KeyEvent) {
                return ((KeyEvent) inputEvent).dispatch(this, this.mDispatcherState, this) ? 1 : 0;
            }
            if (!(inputEvent instanceof MotionEvent)) {
                return 0;
            }
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            return motionEvent.isTouchEvent() ? onTouchEvent(motionEvent) ? 1 : 0 : (motionEvent.getSource() & 4) != 0 ? onTrackballEvent(motionEvent) ? 1 : 0 : onGenericMotionEvent(motionEvent) ? 1 : 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize(ITvAdSessionCallback iTvAdSessionCallback) {
            synchronized (this.mLock) {
                this.mSessionCallback = iTvAdSessionCallback;
                Iterator<Runnable> it = this.mPendingActions.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
                this.mPendingActions.clear();
            }
        }

        void setSurface(Surface surface) {
            onSetSurface(surface);
            Surface surface2 = this.mSurface;
            if (surface2 != null) {
                surface2.release();
            }
            this.mSurface = surface;
        }

        void dispatchSurfaceChanged(int i, int i2, int i3) {
            onSurfaceChanged(i, i2, i3);
        }

        void sendCurrentVideoBounds(Rect rect) {
            onCurrentVideoBounds(rect);
        }

        void sendCurrentChannelUri(Uri uri) {
            onCurrentChannelUri(uri);
        }

        void sendTrackInfoList(List<TvTrackInfo> list) {
            onTrackInfoList(list);
        }

        void sendCurrentTvInputId(String str) {
            onCurrentTvInputId(str);
        }

        void sendSigningResult(String str, byte[] bArr) {
            onSigningResult(str, bArr);
        }

        void notifyError(String str, Bundle bundle) {
            onError(str, bundle);
        }

        void notifyTvMessage(int i, Bundle bundle) {
            onTvMessage(i, bundle);
        }

        void notifyTvInputSessionData(String str, Bundle bundle) {
            onTvInputSessionData(str, bundle);
        }

        private void executeOrPostRunnableOnMainThread(Runnable runnable) {
            synchronized (this.mLock) {
                if (this.mSessionCallback == null) {
                    this.mPendingActions.add(runnable);
                } else if (this.mHandler.getLooper().isCurrentThread()) {
                    runnable.run();
                } else {
                    this.mHandler.post(runnable);
                }
            }
        }

        void createMediaView(IBinder iBinder, Rect rect) {
            if (this.mMediaViewContainer != null) {
                removeMediaView(false);
            }
            this.mWindowToken = iBinder;
            this.mMediaFrame = rect;
            onMediaViewSizeChanged(rect.right - rect.left, rect.bottom - rect.top);
            if (this.mMediaViewEnabled) {
                View onCreateMediaView = onCreateMediaView();
                this.mMediaView = onCreateMediaView;
                if (onCreateMediaView == null) {
                    return;
                }
                MediaViewCleanUpTask mediaViewCleanUpTask = this.mMediaViewCleanUpTask;
                if (mediaViewCleanUpTask != null) {
                    mediaViewCleanUpTask.cancel(true);
                    this.mMediaViewCleanUpTask = null;
                }
                FrameLayout frameLayout = new FrameLayout(this.mContext.getApplicationContext());
                this.mMediaViewContainer = frameLayout;
                frameLayout.addView(this.mMediaView);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(rect.right - rect.left, rect.bottom - rect.top, rect.left, rect.top, 1001, ActivityManager.isHighEndGfx() ? 16777752 : 536, -2);
                this.mWindowParams = layoutParams;
                layoutParams.privateFlags |= 64;
                this.mWindowParams.gravity = 8388659;
                this.mWindowParams.token = iBinder;
                this.mWindowManager.addView(this.mMediaViewContainer, this.mWindowParams);
            }
        }

        void relayoutMediaView(Rect rect) {
            Rect rect2 = this.mMediaFrame;
            if (rect2 == null || rect2.width() != rect.width() || this.mMediaFrame.height() != rect.height()) {
                onMediaViewSizeChanged(rect.right - rect.left, rect.bottom - rect.top);
            }
            this.mMediaFrame = rect;
            if (!this.mMediaViewEnabled || this.mMediaViewContainer == null) {
                return;
            }
            this.mWindowParams.x = rect.left;
            this.mWindowParams.y = rect.top;
            this.mWindowParams.width = rect.right - rect.left;
            this.mWindowParams.height = rect.bottom - rect.top;
            this.mWindowManager.updateViewLayout(this.mMediaViewContainer, this.mWindowParams);
        }

        void removeMediaView(boolean z) {
            if (z) {
                this.mWindowToken = null;
                this.mMediaFrame = null;
            }
            FrameLayout frameLayout = this.mMediaViewContainer;
            if (frameLayout != null) {
                frameLayout.removeView(this.mMediaView);
                this.mMediaView = null;
                this.mWindowManager.removeView(this.mMediaViewContainer);
                this.mMediaViewContainer = null;
                this.mWindowParams = null;
            }
        }

        void scheduleMediaViewCleanup() {
            FrameLayout frameLayout = this.mMediaViewContainer;
            if (frameLayout != null) {
                MediaViewCleanUpTask mediaViewCleanUpTask = new MediaViewCleanUpTask();
                this.mMediaViewCleanUpTask = mediaViewCleanUpTask;
                mediaViewCleanUpTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, frameLayout);
            }
        }
    }

    private static final class MediaViewCleanUpTask extends AsyncTask<View, Void, Void> {
        private MediaViewCleanUpTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(View... viewArr) {
            View view = viewArr[0];
            try {
                Thread.sleep(5000L);
                if (!isCancelled() && view.isAttachedToWindow()) {
                    Log.e(TvAdService.TAG, "Time out on releasing media view. Killing " + view.getContext().getPackageName());
                    Process.killProcess(Process.myPid());
                }
            } catch (InterruptedException unused) {
            }
            return null;
        }
    }

    private final class ServiceHandler extends Handler {
        private static final int DO_CREATE_SESSION = 1;
        private static final int DO_NOTIFY_SESSION_CREATED = 2;

        private ServiceHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i == 2) {
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    Session session = (Session) someArgs.arg1;
                    ITvAdSession iTvAdSession = (ITvAdSession) someArgs.arg2;
                    ITvAdSessionCallback iTvAdSessionCallback = (ITvAdSessionCallback) someArgs.arg3;
                    try {
                        iTvAdSessionCallback.onSessionCreated(iTvAdSession);
                    } catch (RemoteException e) {
                        Log.e(TvAdService.TAG, "error in onSessionCreated", e);
                    }
                    if (session != null) {
                        session.initialize(iTvAdSessionCallback);
                    }
                    someArgs.recycle();
                    return;
                }
                Log.w(TvAdService.TAG, "Unhandled message code: " + message.what);
                return;
            }
            SomeArgs someArgs2 = (SomeArgs) message.obj;
            InputChannel inputChannel = (InputChannel) someArgs2.arg1;
            ITvAdSessionCallback iTvAdSessionCallback2 = (ITvAdSessionCallback) someArgs2.arg2;
            String str = (String) someArgs2.arg3;
            String str2 = (String) someArgs2.arg4;
            someArgs2.recycle();
            Session onCreateSession = TvAdService.this.onCreateSession(str, str2);
            if (onCreateSession == null) {
                try {
                    iTvAdSessionCallback2.onSessionCreated(null);
                    return;
                } catch (RemoteException e2) {
                    Log.e(TvAdService.TAG, "error in onSessionCreated", e2);
                    return;
                }
            }
            ITvAdSessionWrapper iTvAdSessionWrapper = new ITvAdSessionWrapper(TvAdService.this, onCreateSession, inputChannel);
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = onCreateSession;
            obtain.arg2 = iTvAdSessionWrapper;
            obtain.arg3 = iTvAdSessionCallback2;
            TvAdService.this.mServiceHandler.obtainMessage(2, obtain).sendToTarget();
        }
    }
}
