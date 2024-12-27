package android.media.tv.ad;

import android.annotation.NonNull;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.tv.TvInputManager;
import android.media.tv.TvTrackInfo;
import android.media.tv.TvView;
import android.media.tv.ad.TvAdManager;
import android.media.tv.ad.TvAdView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import com.android.internal.util.AnnotationValidations;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class TvAdView extends ViewGroup {
    private static final boolean DEBUG = false;
    public static final String ERROR_KEY_ERROR_CODE = "error_code";
    public static final String ERROR_KEY_METHOD_NAME = "method_name";
    private static final String TAG = "TvAdView";
    private final AttributeSet mAttrs;
    private TvAdCallback mCallback;
    private Executor mCallbackExecutor;
    private final Object mCallbackLock;
    private final int mDefStyleAttr;
    private final TvAdManager.Session.FinishedInputEventCallback mFinishedInputEventCallback;
    private final Handler mHandler;
    private boolean mMediaViewCreated;
    private Rect mMediaViewFrame;
    private OnUnhandledInputEventListener mOnUnhandledInputEventListener;
    private final XmlResourceParser mParser;
    private TvAdManager.Session mSession;
    private MySessionCallback mSessionCallback;
    private Surface mSurface;
    private boolean mSurfaceChanged;
    private int mSurfaceFormat;
    private int mSurfaceHeight;
    private final SurfaceHolder.Callback mSurfaceHolderCallback;
    private SurfaceView mSurfaceView;
    private int mSurfaceViewBottom;
    private int mSurfaceViewLeft;
    private int mSurfaceViewRight;
    private int mSurfaceViewTop;
    private int mSurfaceWidth;
    private final TvAdManager mTvAdManager;
    private boolean mUseRequestedSurfaceLayout;

    public interface OnUnhandledInputEventListener {
        boolean onUnhandledInputEvent(InputEvent inputEvent);
    }

    public TvAdView(Context context) {
        this(context, null, 0);
    }

    public TvAdView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TvAdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mHandler = new Handler();
        this.mCallbackLock = new Object();
        this.mSurfaceHolderCallback = new SurfaceHolder.Callback() { // from class: android.media.tv.ad.TvAdView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                TvAdView.this.mSurfaceFormat = format;
                TvAdView.this.mSurfaceWidth = width;
                TvAdView.this.mSurfaceHeight = height;
                TvAdView.this.mSurfaceChanged = true;
                TvAdView.this.dispatchSurfaceChanged(TvAdView.this.mSurfaceFormat, TvAdView.this.mSurfaceWidth, TvAdView.this.mSurfaceHeight);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder holder) {
                TvAdView.this.mSurface = holder.getSurface();
                TvAdView.this.setSessionSurface(TvAdView.this.mSurface);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder holder) {
                TvAdView.this.mSurface = null;
                TvAdView.this.mSurfaceChanged = false;
                TvAdView.this.setSessionSurface(null);
            }
        };
        this.mFinishedInputEventCallback = new TvAdManager.Session.FinishedInputEventCallback() { // from class: android.media.tv.ad.TvAdView.3
            @Override // android.media.tv.ad.TvAdManager.Session.FinishedInputEventCallback
            public void onFinishedInputEvent(Object token, boolean handled) {
                ViewRootImpl viewRootImpl;
                if (handled) {
                    return;
                }
                InputEvent event = (InputEvent) token;
                if (!TvAdView.this.dispatchUnhandledInputEvent(event) && (viewRootImpl = TvAdView.this.getViewRootImpl()) != null) {
                    viewRootImpl.dispatchUnhandledInputEvent(event);
                }
            }
        };
        int sourceResId = Resources.getAttributeSetSourceResId(attrs);
        if (sourceResId != 0) {
            Log.d(TAG, "Build local AttributeSet");
            this.mParser = context.getResources().getXml(sourceResId);
            this.mAttrs = Xml.asAttributeSet(this.mParser);
        } else {
            Log.d(TAG, "Use passed in AttributeSet");
            this.mParser = null;
            this.mAttrs = attrs;
        }
        this.mDefStyleAttr = defStyleAttr;
        resetSurfaceView();
        this.mTvAdManager = (TvAdManager) getContext().getSystemService(Context.TV_AD_SERVICE);
    }

    public boolean setTvView(TvView tvView) {
        if (tvView == null) {
            return unsetTvView();
        }
        TvInputManager.Session inputSession = tvView.getInputSession();
        if (inputSession == null || this.mSession == null) {
            return false;
        }
        this.mSession.setInputSession(inputSession);
        inputSession.setAdSession(this.mSession);
        return true;
    }

    private boolean unsetTvView() {
        if (this.mSession == null || this.mSession.getInputSession() == null) {
            return false;
        }
        this.mSession.getInputSession().setAdSession(null);
        this.mSession.setInputSession(null);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        createSessionMediaView();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        removeSessionMediaView();
        super.onDetachedFromWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (this.mUseRequestedSurfaceLayout) {
            this.mSurfaceView.layout(this.mSurfaceViewLeft, this.mSurfaceViewTop, this.mSurfaceViewRight, this.mSurfaceViewBottom);
        } else {
            this.mSurfaceView.layout(0, 0, right - left, bottom - top);
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mSurfaceView.measure(widthMeasureSpec, heightMeasureSpec);
        int width = this.mSurfaceView.getMeasuredWidth();
        int height = this.mSurfaceView.getMeasuredHeight();
        int childState = this.mSurfaceView.getMeasuredState();
        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, childState), resolveSizeAndState(height, heightMeasureSpec, childState << 16));
    }

    @Override // android.view.View
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        this.mSurfaceView.setVisibility(visibility);
        if (visibility == 0) {
            createSessionMediaView();
        } else {
            removeSessionMediaView();
        }
    }

    private void resetSurfaceView() {
        if (this.mSurfaceView != null) {
            this.mSurfaceView.getHolder().removeCallback(this.mSurfaceHolderCallback);
            removeView(this.mSurfaceView);
        }
        this.mSurface = null;
        this.mSurfaceView = new SurfaceView(getContext(), this.mAttrs, this.mDefStyleAttr) { // from class: android.media.tv.ad.TvAdView.2
            @Override // android.view.SurfaceView
            protected void updateSurface() {
                super.updateSurface();
                TvAdView.this.relayoutSessionMediaView();
            }
        };
        this.mSurfaceView.setSecure(true);
        this.mSurfaceView.getHolder().addCallback(this.mSurfaceHolderCallback);
        this.mSurfaceView.getHolder().setFormat(-3);
        this.mSurfaceView.setZOrderOnTop(false);
        this.mSurfaceView.setZOrderMediaOverlay(true);
        addView(this.mSurfaceView);
    }

    public void reset() {
        resetInternal();
    }

    private void resetInternal() {
        this.mSessionCallback = null;
        if (this.mSession != null) {
            setSessionSurface(null);
            removeSessionMediaView();
            this.mUseRequestedSurfaceLayout = false;
            this.mSession.release();
            this.mSession = null;
            resetSurfaceView();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createSessionMediaView() {
        if (this.mSession == null || !isAttachedToWindow() || this.mMediaViewCreated) {
            return;
        }
        this.mMediaViewFrame = getViewFrameOnScreen();
        this.mSession.createMediaView(this, this.mMediaViewFrame);
        this.mMediaViewCreated = true;
    }

    private void removeSessionMediaView() {
        if (this.mSession == null || !this.mMediaViewCreated) {
            return;
        }
        this.mSession.removeMediaView();
        this.mMediaViewCreated = false;
        this.mMediaViewFrame = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void relayoutSessionMediaView() {
        if (this.mSession == null || !isAttachedToWindow() || !this.mMediaViewCreated) {
            return;
        }
        Rect viewFrame = getViewFrameOnScreen();
        if (viewFrame.equals(this.mMediaViewFrame)) {
            return;
        }
        this.mSession.relayoutMediaView(viewFrame);
        this.mMediaViewFrame = viewFrame;
    }

    private Rect getViewFrameOnScreen() {
        Rect frame = new Rect();
        getGlobalVisibleRect(frame);
        RectF frameF = new RectF(frame);
        getMatrix().mapRect(frameF);
        frameF.round(frame);
        return frame;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSessionSurface(Surface surface) {
        if (this.mSession == null) {
            return;
        }
        this.mSession.setSurface(surface);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchSurfaceChanged(int format, int width, int height) {
        if (this.mSession == null) {
            return;
        }
        this.mSession.dispatchSurfaceChanged(format, width, height);
    }

    public boolean dispatchUnhandledInputEvent(InputEvent event) {
        if (this.mOnUnhandledInputEventListener != null && this.mOnUnhandledInputEventListener.onUnhandledInputEvent(event)) {
            return true;
        }
        return onUnhandledInputEvent(event);
    }

    public boolean onUnhandledInputEvent(InputEvent event) {
        return false;
    }

    public void setOnUnhandledInputEventListener(OnUnhandledInputEventListener listener) {
        this.mOnUnhandledInputEventListener = listener;
    }

    public OnUnhandledInputEventListener getOnUnhandledInputEventListener() {
        return this.mOnUnhandledInputEventListener;
    }

    public void clearOnUnhandledInputEventListener() {
        this.mOnUnhandledInputEventListener = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (super.dispatchKeyEvent(event)) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        InputEvent copiedEvent = event.copy();
        int ret = this.mSession.dispatchInputEvent(copiedEvent, copiedEvent, this.mFinishedInputEventCallback, this.mHandler);
        return ret != 0;
    }

    public void prepareAdService(String serviceId, String type) {
        this.mSessionCallback = new MySessionCallback(serviceId);
        if (this.mTvAdManager != null) {
            this.mTvAdManager.createSession(serviceId, type, this.mSessionCallback, this.mHandler);
        }
    }

    public void startAdService() {
        if (this.mSession != null) {
            this.mSession.startAdService();
        }
    }

    public void stopAdService() {
        if (this.mSession != null) {
            this.mSession.stopAdService();
        }
    }

    public void resetAdService() {
        if (this.mSession != null) {
            this.mSession.resetAdService();
        }
    }

    public void sendCurrentVideoBounds(Rect bounds) {
        if (this.mSession != null) {
            this.mSession.sendCurrentVideoBounds(bounds);
        }
    }

    public void sendCurrentChannelUri(Uri channelUri) {
        if (this.mSession != null) {
            this.mSession.sendCurrentChannelUri(channelUri);
        }
    }

    public void sendTrackInfoList(List<TvTrackInfo> tracks) {
        if (this.mSession != null) {
            this.mSession.sendTrackInfoList(tracks);
        }
    }

    public void sendCurrentTvInputId(String inputId) {
        if (this.mSession != null) {
            this.mSession.sendCurrentTvInputId(inputId);
        }
    }

    public void sendSigningResult(String signingId, byte[] result) {
        if (this.mSession != null) {
            this.mSession.sendSigningResult(signingId, result);
        }
    }

    public void notifyError(String errMsg, Bundle params) {
        if (this.mSession != null) {
            this.mSession.notifyError(errMsg, params);
        }
    }

    public void notifyTvMessage(int type, Bundle data) {
        if (this.mSession != null) {
            this.mSession.notifyTvMessage(type, data);
        }
    }

    public void setCallback(Executor executor, TvAdCallback callback) {
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) callback);
        synchronized (this.mCallbackLock) {
            this.mCallbackExecutor = executor;
            this.mCallback = callback;
        }
    }

    public void clearCallback() {
        synchronized (this.mCallbackLock) {
            this.mCallback = null;
            this.mCallbackExecutor = null;
        }
    }

    public TvAdManager.Session getAdSession() {
        return this.mSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class MySessionCallback extends TvAdManager.SessionCallback {
        final String mServiceId;

        MySessionCallback(String serviceId) {
            this.mServiceId = serviceId;
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onSessionCreated(TvAdManager.Session session) {
            if (this != TvAdView.this.mSessionCallback) {
                Log.w(TvAdView.TAG, "onSessionCreated - session already created");
                if (session != null) {
                    session.release();
                    return;
                }
                return;
            }
            TvAdView.this.mSession = session;
            if (session != null) {
                if (TvAdView.this.mSurface != null) {
                    TvAdView.this.setSessionSurface(TvAdView.this.mSurface);
                    if (TvAdView.this.mSurfaceChanged) {
                        TvAdView.this.dispatchSurfaceChanged(TvAdView.this.mSurfaceFormat, TvAdView.this.mSurfaceWidth, TvAdView.this.mSurfaceHeight);
                    }
                }
                TvAdView.this.createSessionMediaView();
                return;
            }
            TvAdView.this.mSessionCallback = null;
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onSessionReleased(TvAdManager.Session session) {
            if (this != TvAdView.this.mSessionCallback) {
                Log.w(TvAdView.TAG, "onSessionReleased - session not created");
                return;
            }
            TvAdView.this.mMediaViewCreated = false;
            TvAdView.this.mMediaViewFrame = null;
            TvAdView.this.mSessionCallback = null;
            TvAdView.this.mSession = null;
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onLayoutSurface(TvAdManager.Session session, int left, int top, int right, int bottom) {
            if (this != TvAdView.this.mSessionCallback) {
                Log.w(TvAdView.TAG, "onLayoutSurface - session not created");
                return;
            }
            TvAdView.this.mSurfaceViewLeft = left;
            TvAdView.this.mSurfaceViewTop = top;
            TvAdView.this.mSurfaceViewRight = right;
            TvAdView.this.mSurfaceViewBottom = bottom;
            TvAdView.this.mUseRequestedSurfaceLayout = true;
            TvAdView.this.requestLayout();
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onRequestCurrentVideoBounds(TvAdManager.Session session) {
            if (this != TvAdView.this.mSessionCallback) {
                Log.w(TvAdView.TAG, "onRequestCurrentVideoBounds - session not created");
                return;
            }
            synchronized (TvAdView.this.mCallbackLock) {
                if (TvAdView.this.mCallbackExecutor != null) {
                    TvAdView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.ad.TvAdView$MySessionCallback$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvAdView.MySessionCallback.this.lambda$onRequestCurrentVideoBounds$0();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentVideoBounds$0() {
            synchronized (TvAdView.this.mCallbackLock) {
                if (TvAdView.this.mCallback != null) {
                    TvAdView.this.mCallback.onRequestCurrentVideoBounds(this.mServiceId);
                }
            }
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onRequestCurrentChannelUri(TvAdManager.Session session) {
            if (this != TvAdView.this.mSessionCallback) {
                Log.w(TvAdView.TAG, "onRequestCurrentChannelUri - session not created");
                return;
            }
            synchronized (TvAdView.this.mCallbackLock) {
                if (TvAdView.this.mCallbackExecutor != null) {
                    TvAdView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.ad.TvAdView$MySessionCallback$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvAdView.MySessionCallback.this.lambda$onRequestCurrentChannelUri$1();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentChannelUri$1() {
            synchronized (TvAdView.this.mCallbackLock) {
                if (TvAdView.this.mCallback != null) {
                    TvAdView.this.mCallback.onRequestCurrentChannelUri(this.mServiceId);
                }
            }
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onRequestTrackInfoList(TvAdManager.Session session) {
            if (this != TvAdView.this.mSessionCallback) {
                Log.w(TvAdView.TAG, "onRequestTrackInfoList - session not created");
                return;
            }
            synchronized (TvAdView.this.mCallbackLock) {
                if (TvAdView.this.mCallbackExecutor != null) {
                    TvAdView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.ad.TvAdView$MySessionCallback$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvAdView.MySessionCallback.this.lambda$onRequestTrackInfoList$2();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestTrackInfoList$2() {
            synchronized (TvAdView.this.mCallbackLock) {
                if (TvAdView.this.mCallback != null) {
                    TvAdView.this.mCallback.onRequestTrackInfoList(this.mServiceId);
                }
            }
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onRequestCurrentTvInputId(TvAdManager.Session session) {
            if (this != TvAdView.this.mSessionCallback) {
                Log.w(TvAdView.TAG, "onRequestCurrentTvInputId - session not created");
                return;
            }
            synchronized (TvAdView.this.mCallbackLock) {
                if (TvAdView.this.mCallbackExecutor != null) {
                    TvAdView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.ad.TvAdView$MySessionCallback$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvAdView.MySessionCallback.this.lambda$onRequestCurrentTvInputId$3();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentTvInputId$3() {
            synchronized (TvAdView.this.mCallbackLock) {
                if (TvAdView.this.mCallback != null) {
                    TvAdView.this.mCallback.onRequestCurrentTvInputId(this.mServiceId);
                }
            }
        }

        @Override // android.media.tv.ad.TvAdManager.SessionCallback
        public void onRequestSigning(TvAdManager.Session session, final String id, final String algorithm, final String alias, final byte[] data) {
            if (this != TvAdView.this.mSessionCallback) {
                Log.w(TvAdView.TAG, "onRequestSigning - session not created");
                return;
            }
            synchronized (TvAdView.this.mCallbackLock) {
                if (TvAdView.this.mCallbackExecutor != null) {
                    TvAdView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.ad.TvAdView$MySessionCallback$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvAdView.MySessionCallback.this.lambda$onRequestSigning$4(id, algorithm, alias, data);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestSigning$4(String id, String algorithm, String alias, byte[] data) {
            synchronized (TvAdView.this.mCallbackLock) {
                if (TvAdView.this.mCallback != null) {
                    TvAdView.this.mCallback.onRequestSigning(this.mServiceId, id, algorithm, alias, data);
                }
            }
        }
    }

    public static abstract class TvAdCallback {
        public void onRequestCurrentVideoBounds(String serviceId) {
        }

        public void onRequestCurrentChannelUri(String serviceId) {
        }

        public void onRequestTrackInfoList(String serviceId) {
        }

        public void onRequestCurrentTvInputId(String serviceId) {
        }

        public void onRequestSigning(String serviceId, String signingId, String algorithm, String alias, byte[] data) {
        }

        public void onStateChanged(String serviceId, int state, int err) {
        }
    }
}
