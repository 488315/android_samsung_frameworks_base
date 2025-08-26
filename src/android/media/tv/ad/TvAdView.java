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

    public static abstract class TvAdCallback {
        public void onRequestCurrentChannelUri(String str) {
        }

        public void onRequestCurrentTvInputId(String str) {
        }

        public void onRequestCurrentVideoBounds(String str) {
        }

        public void onRequestSigning(String str, String str2, String str3, String str4, byte[] bArr) {
        }

        public void onRequestTrackInfoList(String str) {
        }

        public void onStateChanged(String str, int i, int i2) {
        }
    }

    public boolean onUnhandledInputEvent(InputEvent inputEvent) {
        return false;
    }

    public TvAdView(Context context) {
        this(context, null, 0);
    }

    public TvAdView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TvAdView(Context context, AttributeSet attributeSet, int i) throws Throwable {
        super(context, attributeSet, i);
        this.mHandler = new Handler();
        this.mCallbackLock = new Object();
        this.mSurfaceHolderCallback = new SurfaceHolder.Callback() { // from class: android.media.tv.ad.TvAdView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                TvAdView.this.mSurfaceFormat = i2;
                TvAdView.this.mSurfaceWidth = i3;
                TvAdView.this.mSurfaceHeight = i4;
                TvAdView.this.mSurfaceChanged = true;
                TvAdView tvAdView = TvAdView.this;
                tvAdView.dispatchSurfaceChanged(tvAdView.mSurfaceFormat, TvAdView.this.mSurfaceWidth, TvAdView.this.mSurfaceHeight);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                TvAdView.this.mSurface = surfaceHolder.getSurface();
                TvAdView tvAdView = TvAdView.this;
                tvAdView.setSessionSurface(tvAdView.mSurface);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                TvAdView.this.mSurface = null;
                TvAdView.this.mSurfaceChanged = false;
                TvAdView.this.setSessionSurface(null);
            }
        };
        this.mFinishedInputEventCallback = new TvAdManager.Session.FinishedInputEventCallback() { // from class: android.media.tv.ad.TvAdView.3
            @Override // android.media.tv.ad.TvAdManager.Session.FinishedInputEventCallback
            public void onFinishedInputEvent(Object obj, boolean z) {
                ViewRootImpl viewRootImpl;
                if (z) {
                    return;
                }
                InputEvent inputEvent = (InputEvent) obj;
                if (TvAdView.this.dispatchUnhandledInputEvent(inputEvent) || (viewRootImpl = TvAdView.this.getViewRootImpl()) == null) {
                    return;
                }
                viewRootImpl.dispatchUnhandledInputEvent(inputEvent);
            }
        };
        int attributeSetSourceResId = Resources.getAttributeSetSourceResId(attributeSet);
        if (attributeSetSourceResId != 0) {
            Log.d(TAG, "Build local AttributeSet");
            XmlResourceParser xml = context.getResources().getXml(attributeSetSourceResId);
            this.mParser = xml;
            this.mAttrs = Xml.asAttributeSet(xml);
        } else {
            Log.d(TAG, "Use passed in AttributeSet");
            this.mParser = null;
            this.mAttrs = attributeSet;
        }
        this.mDefStyleAttr = i;
        resetSurfaceView();
        this.mTvAdManager = (TvAdManager) getContext().getSystemService(Context.TV_AD_SERVICE);
    }

    public boolean setTvView(TvView tvView) {
        TvAdManager.Session session;
        if (tvView == null) {
            return unsetTvView();
        }
        TvInputManager.Session inputSession = tvView.getInputSession();
        if (inputSession == null || (session = this.mSession) == null) {
            return false;
        }
        session.setInputSession(inputSession);
        inputSession.setAdSession(this.mSession);
        return true;
    }

    private boolean unsetTvView() {
        TvAdManager.Session session = this.mSession;
        if (session == null || session.getInputSession() == null) {
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
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mUseRequestedSurfaceLayout) {
            this.mSurfaceView.layout(this.mSurfaceViewLeft, this.mSurfaceViewTop, this.mSurfaceViewRight, this.mSurfaceViewBottom);
        } else {
            this.mSurfaceView.layout(0, 0, i3 - i, i4 - i2);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        this.mSurfaceView.measure(i, i2);
        int measuredWidth = this.mSurfaceView.getMeasuredWidth();
        int measuredHeight = this.mSurfaceView.getMeasuredHeight();
        int measuredState = this.mSurfaceView.getMeasuredState();
        setMeasuredDimension(resolveSizeAndState(measuredWidth, i, measuredState), resolveSizeAndState(measuredHeight, i2, measuredState << 16));
    }

    @Override // android.view.View
    public void onVisibilityChanged(View view, int i) throws Throwable {
        super.onVisibilityChanged(view, i);
        this.mSurfaceView.setVisibility(i);
        if (i == 0) {
            createSessionMediaView();
        } else {
            removeSessionMediaView();
        }
    }

    public void setZOrderMediaOverlay(boolean z) throws Throwable {
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.setZOrderOnTop(false);
            this.mSurfaceView.setZOrderMediaOverlay(z);
        }
    }

    public void setZOrderOnTop(boolean z) throws Throwable {
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.setZOrderMediaOverlay(false);
            this.mSurfaceView.setZOrderOnTop(z);
        }
    }

    private void resetSurfaceView() throws Throwable {
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.getHolder().removeCallback(this.mSurfaceHolderCallback);
            removeView(this.mSurfaceView);
        }
        this.mSurface = null;
        SurfaceView surfaceView2 = new SurfaceView(getContext(), this.mAttrs, this.mDefStyleAttr) { // from class: android.media.tv.ad.TvAdView.2
            @Override // android.view.SurfaceView
            protected void updateSurface() throws Throwable {
                super.updateSurface();
                TvAdView.this.relayoutSessionMediaView();
            }
        };
        this.mSurfaceView = surfaceView2;
        surfaceView2.setSecure(true);
        this.mSurfaceView.getHolder().addCallback(this.mSurfaceHolderCallback);
        this.mSurfaceView.getHolder().setFormat(-3);
        this.mSurfaceView.setZOrderOnTop(false);
        this.mSurfaceView.setZOrderMediaOverlay(true);
        addView(this.mSurfaceView);
    }

    public void reset() throws Throwable {
        resetInternal();
    }

    private void resetInternal() throws Throwable {
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
        Rect viewFrameOnScreen = getViewFrameOnScreen();
        this.mMediaViewFrame = viewFrameOnScreen;
        this.mSession.createMediaView(this, viewFrameOnScreen);
        this.mMediaViewCreated = true;
    }

    private void removeSessionMediaView() {
        TvAdManager.Session session = this.mSession;
        if (session == null || !this.mMediaViewCreated) {
            return;
        }
        session.removeMediaView();
        this.mMediaViewCreated = false;
        this.mMediaViewFrame = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void relayoutSessionMediaView() {
        if (this.mSession != null && isAttachedToWindow() && this.mMediaViewCreated) {
            Rect viewFrameOnScreen = getViewFrameOnScreen();
            if (viewFrameOnScreen.equals(this.mMediaViewFrame)) {
                return;
            }
            this.mSession.relayoutMediaView(viewFrameOnScreen);
            this.mMediaViewFrame = viewFrameOnScreen;
        }
    }

    private Rect getViewFrameOnScreen() {
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        RectF rectF = new RectF(rect);
        getMatrix().mapRect(rectF);
        rectF.round(rect);
        return rect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSessionSurface(Surface surface) {
        TvAdManager.Session session = this.mSession;
        if (session == null) {
            return;
        }
        session.setSurface(surface);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchSurfaceChanged(int i, int i2, int i3) {
        TvAdManager.Session session = this.mSession;
        if (session == null) {
            return;
        }
        session.dispatchSurfaceChanged(i, i2, i3);
    }

    public boolean dispatchUnhandledInputEvent(InputEvent inputEvent) {
        OnUnhandledInputEventListener onUnhandledInputEventListener = this.mOnUnhandledInputEventListener;
        if (onUnhandledInputEventListener == null || !onUnhandledInputEventListener.onUnhandledInputEvent(inputEvent)) {
            return onUnhandledInputEvent(inputEvent);
        }
        return true;
    }

    public void setOnUnhandledInputEventListener(OnUnhandledInputEventListener onUnhandledInputEventListener) {
        this.mOnUnhandledInputEventListener = onUnhandledInputEventListener;
    }

    public OnUnhandledInputEventListener getOnUnhandledInputEventListener() {
        return this.mOnUnhandledInputEventListener;
    }

    public void clearOnUnhandledInputEventListener() {
        this.mOnUnhandledInputEventListener = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (super.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        KeyEvent keyEventCopy = keyEvent.copy();
        return this.mSession.dispatchInputEvent(keyEventCopy, keyEventCopy, this.mFinishedInputEventCallback, this.mHandler) != 0;
    }

    public void prepareAdService(String str, String str2) {
        MySessionCallback mySessionCallback = new MySessionCallback(str);
        this.mSessionCallback = mySessionCallback;
        TvAdManager tvAdManager = this.mTvAdManager;
        if (tvAdManager != null) {
            tvAdManager.createSession(str, str2, mySessionCallback, this.mHandler);
        }
    }

    public void startAdService() {
        TvAdManager.Session session = this.mSession;
        if (session != null) {
            session.startAdService();
        }
    }

    public void stopAdService() {
        TvAdManager.Session session = this.mSession;
        if (session != null) {
            session.stopAdService();
        }
    }

    public void resetAdService() {
        TvAdManager.Session session = this.mSession;
        if (session != null) {
            session.resetAdService();
        }
    }

    public void sendCurrentVideoBounds(Rect rect) {
        TvAdManager.Session session = this.mSession;
        if (session != null) {
            session.sendCurrentVideoBounds(rect);
        }
    }

    public void sendCurrentChannelUri(Uri uri) {
        TvAdManager.Session session = this.mSession;
        if (session != null) {
            session.sendCurrentChannelUri(uri);
        }
    }

    public void sendTrackInfoList(List<TvTrackInfo> list) {
        TvAdManager.Session session = this.mSession;
        if (session != null) {
            session.sendTrackInfoList(list);
        }
    }

    public void sendCurrentTvInputId(String str) {
        TvAdManager.Session session = this.mSession;
        if (session != null) {
            session.sendCurrentTvInputId(str);
        }
    }

    public void sendSigningResult(String str, byte[] bArr) {
        TvAdManager.Session session = this.mSession;
        if (session != null) {
            session.sendSigningResult(str, bArr);
        }
    }

    public void notifyError(String str, Bundle bundle) {
        TvAdManager.Session session = this.mSession;
        if (session != null) {
            session.notifyError(str, bundle);
        }
    }

    public void notifyTvMessage(int i, Bundle bundle) {
        TvAdManager.Session session = this.mSession;
        if (session != null) {
            session.notifyTvMessage(i, bundle);
        }
    }

    public void setCallback(Executor executor, TvAdCallback tvAdCallback) {
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) tvAdCallback);
        synchronized (this.mCallbackLock) {
            this.mCallbackExecutor = executor;
            this.mCallback = tvAdCallback;
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

        MySessionCallback(String str) {
            this.mServiceId = str;
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
                    TvAdView tvAdView = TvAdView.this;
                    tvAdView.setSessionSurface(tvAdView.mSurface);
                    if (TvAdView.this.mSurfaceChanged) {
                        TvAdView tvAdView2 = TvAdView.this;
                        tvAdView2.dispatchSurfaceChanged(tvAdView2.mSurfaceFormat, TvAdView.this.mSurfaceWidth, TvAdView.this.mSurfaceHeight);
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
        public void onLayoutSurface(TvAdManager.Session session, int i, int i2, int i3, int i4) {
            if (this != TvAdView.this.mSessionCallback) {
                Log.w(TvAdView.TAG, "onLayoutSurface - session not created");
                return;
            }
            TvAdView.this.mSurfaceViewLeft = i;
            TvAdView.this.mSurfaceViewTop = i2;
            TvAdView.this.mSurfaceViewRight = i3;
            TvAdView.this.mSurfaceViewBottom = i4;
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
                            this.f$0.lambda$onRequestCurrentVideoBounds$0();
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
                            this.f$0.lambda$onRequestCurrentChannelUri$1();
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
                            this.f$0.lambda$onRequestTrackInfoList$2();
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
                            this.f$0.lambda$onRequestCurrentTvInputId$3();
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
        public void onRequestSigning(TvAdManager.Session session, final String str, final String str2, final String str3, final byte[] bArr) {
            if (this != TvAdView.this.mSessionCallback) {
                Log.w(TvAdView.TAG, "onRequestSigning - session not created");
                return;
            }
            synchronized (TvAdView.this.mCallbackLock) {
                if (TvAdView.this.mCallbackExecutor != null) {
                    TvAdView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.ad.TvAdView$MySessionCallback$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onRequestSigning$4(str, str2, str3, bArr);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestSigning$4(String str, String str2, String str3, byte[] bArr) {
            synchronized (TvAdView.this.mCallbackLock) {
                if (TvAdView.this.mCallback != null) {
                    TvAdView.this.mCallback.onRequestSigning(this.mServiceId, str, str2, str3, bArr);
                }
            }
        }
    }
}
