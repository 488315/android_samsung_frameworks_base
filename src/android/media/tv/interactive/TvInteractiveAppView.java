package android.media.tv.interactive;

import android.annotation.NonNull;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.PlaybackParams;
import android.media.tv.TvInputManager;
import android.media.tv.TvRecordingInfo;
import android.media.tv.TvTrackInfo;
import android.media.tv.TvView;
import android.media.tv.interactive.TvInteractiveAppManager;
import android.media.tv.interactive.TvInteractiveAppView;
import android.net.Uri;
import android.net.http.SslCertificate;
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
import com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.Flags;
import com.android.internal.util.AnnotationValidations;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class TvInteractiveAppView extends ViewGroup {
    public static final String BI_INTERACTIVE_APP_KEY_ALIAS = "alias";
    public static final String BI_INTERACTIVE_APP_KEY_CERTIFICATE = "certificate";
    public static final String BI_INTERACTIVE_APP_KEY_HTTP_ADDITIONAL_HEADERS = "http_additional_headers";
    public static final String BI_INTERACTIVE_APP_KEY_HTTP_USER_AGENT = "http_user_agent";
    public static final String BI_INTERACTIVE_APP_KEY_PRIVATE_KEY = "private_key";
    private static final boolean DEBUG = false;
    public static final String ERROR_KEY_METHOD_NAME = "method_name";
    private static final int SET_TVVIEW_FAIL = 2;
    private static final int SET_TVVIEW_SUCCESS = 1;
    private static final String TAG = "TvInteractiveAppView";
    private static final int UNSET_TVVIEW_FAIL = 4;
    private static final int UNSET_TVVIEW_SUCCESS = 3;
    private final AttributeSet mAttrs;
    private TvInteractiveAppCallback mCallback;
    private Executor mCallbackExecutor;
    private final Object mCallbackLock;
    private final int mDefStyleAttr;
    private final TvInteractiveAppManager.Session.FinishedInputEventCallback mFinishedInputEventCallback;
    private final Handler mHandler;
    private boolean mMediaViewCreated;
    private Rect mMediaViewFrame;
    private OnUnhandledInputEventListener mOnUnhandledInputEventListener;
    private final XmlResourceParser mParser;
    private TvInteractiveAppManager.Session mSession;
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
    private final TvInteractiveAppManager mTvInteractiveAppManager;
    private boolean mUseRequestedSurfaceLayout;

    public interface OnUnhandledInputEventListener {
        boolean onUnhandledInputEvent(InputEvent inputEvent);
    }

    public static abstract class TvInteractiveAppCallback {
        public void onBiInteractiveAppCreated(String str, Uri uri, String str2) {
        }

        public void onPlaybackCommandRequest(String str, String str2, Bundle bundle) {
        }

        public void onRequestAvailableSpeeds(String str) {
        }

        public void onRequestCertificate(String str, String str2, int i) {
        }

        public void onRequestCurrentChannelLcn(String str) {
        }

        public void onRequestCurrentChannelUri(String str) {
        }

        public void onRequestCurrentTvInputId(String str) {
        }

        public void onRequestCurrentVideoBounds(String str) {
        }

        public void onRequestScheduleRecording(String str, String str2, String str3, Uri uri, long j, long j2, int i, Bundle bundle) {
        }

        public void onRequestScheduleRecording(String str, String str2, String str3, Uri uri, Uri uri2, Bundle bundle) {
        }

        public void onRequestSelectedTrackInfo(String str) {
        }

        public void onRequestSigning(String str, String str2, String str3, String str4, int i, byte[] bArr) {
        }

        public void onRequestSigning(String str, String str2, String str3, String str4, byte[] bArr) {
        }

        public void onRequestStartRecording(String str, String str2, Uri uri) {
        }

        public void onRequestStopRecording(String str, String str2) {
        }

        public void onRequestStreamVolume(String str) {
        }

        public void onRequestTimeShiftMode(String str) {
        }

        public void onRequestTrackInfoList(String str) {
        }

        public void onRequestTvRecordingInfo(String str, String str2) {
        }

        public void onRequestTvRecordingInfoList(String str, int i) {
        }

        public void onSetTvRecordingInfo(String str, String str2, TvRecordingInfo tvRecordingInfo) {
        }

        public void onSetVideoBounds(String str, Rect rect) {
        }

        public void onStateChanged(String str, int i, int i2) {
        }

        public void onTeletextAppStateChanged(String str, int i) {
        }

        public void onTimeShiftCommandRequest(String str, String str2, Bundle bundle) {
        }
    }

    public boolean onUnhandledInputEvent(InputEvent inputEvent) {
        return false;
    }

    public TvInteractiveAppView(Context context) {
        this(context, null, 0);
    }

    public TvInteractiveAppView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TvInteractiveAppView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHandler = new Handler();
        this.mCallbackLock = new Object();
        this.mSurfaceHolderCallback = new SurfaceHolder.Callback() { // from class: android.media.tv.interactive.TvInteractiveAppView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                TvInteractiveAppView.this.mSurfaceFormat = i2;
                TvInteractiveAppView.this.mSurfaceWidth = i3;
                TvInteractiveAppView.this.mSurfaceHeight = i4;
                TvInteractiveAppView.this.mSurfaceChanged = true;
                TvInteractiveAppView tvInteractiveAppView = TvInteractiveAppView.this;
                tvInteractiveAppView.dispatchSurfaceChanged(tvInteractiveAppView.mSurfaceFormat, TvInteractiveAppView.this.mSurfaceWidth, TvInteractiveAppView.this.mSurfaceHeight);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                TvInteractiveAppView.this.mSurface = surfaceHolder.getSurface();
                TvInteractiveAppView tvInteractiveAppView = TvInteractiveAppView.this;
                tvInteractiveAppView.setSessionSurface(tvInteractiveAppView.mSurface);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                TvInteractiveAppView.this.mSurface = null;
                TvInteractiveAppView.this.mSurfaceChanged = false;
                TvInteractiveAppView.this.setSessionSurface(null);
            }
        };
        this.mFinishedInputEventCallback = new TvInteractiveAppManager.Session.FinishedInputEventCallback() { // from class: android.media.tv.interactive.TvInteractiveAppView.3
            @Override // android.media.tv.interactive.TvInteractiveAppManager.Session.FinishedInputEventCallback
            public void onFinishedInputEvent(Object obj, boolean z) {
                ViewRootImpl viewRootImpl;
                if (z) {
                    return;
                }
                InputEvent inputEvent = (InputEvent) obj;
                if (TvInteractiveAppView.this.dispatchUnhandledInputEvent(inputEvent) || (viewRootImpl = TvInteractiveAppView.this.getViewRootImpl()) == null) {
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
        this.mTvInteractiveAppManager = (TvInteractiveAppManager) getContext().getSystemService(Context.TV_INTERACTIVE_APP_SERVICE);
    }

    public void setCallback(Executor executor, TvInteractiveAppCallback tvInteractiveAppCallback) {
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) tvInteractiveAppCallback);
        synchronized (this.mCallbackLock) {
            this.mCallbackExecutor = executor;
            this.mCallback = tvInteractiveAppCallback;
        }
    }

    public void clearCallback() {
        synchronized (this.mCallbackLock) {
            this.mCallback = null;
            this.mCallbackExecutor = null;
        }
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
    public void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        this.mSurfaceView.setVisibility(i);
        if (i == 0) {
            createSessionMediaView();
        } else {
            removeSessionMediaView();
        }
    }

    public void setZOrderMediaOverlay(boolean z) {
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.setZOrderOnTop(false);
            this.mSurfaceView.setZOrderMediaOverlay(z);
        }
    }

    public void setZOrderOnTop(boolean z) {
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.setZOrderMediaOverlay(false);
            this.mSurfaceView.setZOrderOnTop(z);
        }
    }

    private void resetSurfaceView() {
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.getHolder().removeCallback(this.mSurfaceHolderCallback);
            removeView(this.mSurfaceView);
        }
        this.mSurface = null;
        SurfaceView surfaceView2 = new SurfaceView(getContext(), this.mAttrs, this.mDefStyleAttr) { // from class: android.media.tv.interactive.TvInteractiveAppView.2
            @Override // android.view.SurfaceView
            protected void updateSurface() {
                super.updateSurface();
                TvInteractiveAppView.this.relayoutSessionMediaView();
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

    public void reset() {
        resetInternal();
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
        TvInteractiveAppManager.Session session = this.mSession;
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
        TvInteractiveAppManager.Session session = this.mSession;
        if (session == null) {
            return;
        }
        session.setSurface(surface);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchSurfaceChanged(int i, int i2, int i3) {
        TvInteractiveAppManager.Session session = this.mSession;
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

    public void setOnUnhandledInputEventListener(Executor executor, OnUnhandledInputEventListener onUnhandledInputEventListener) {
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
        KeyEvent copy = keyEvent.copy();
        return this.mSession.dispatchInputEvent(copy, copy, this.mFinishedInputEventCallback, this.mHandler) != 0;
    }

    public void prepareInteractiveApp(String str, int i) {
        MySessionCallback mySessionCallback = new MySessionCallback(str, i);
        this.mSessionCallback = mySessionCallback;
        TvInteractiveAppManager tvInteractiveAppManager = this.mTvInteractiveAppManager;
        if (tvInteractiveAppManager != null) {
            tvInteractiveAppManager.createSession(str, i, mySessionCallback, this.mHandler);
        }
    }

    public void startInteractiveApp() {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.startInteractiveApp();
        }
    }

    public void stopInteractiveApp() {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.stopInteractiveApp();
        }
    }

    public void resetInteractiveApp() {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.resetInteractiveApp();
        }
    }

    public void sendCurrentVideoBounds(Rect rect) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.sendCurrentVideoBounds(rect);
        }
    }

    public void sendCurrentChannelUri(Uri uri) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.sendCurrentChannelUri(uri);
        }
    }

    public void sendCurrentChannelLcn(int i) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.sendCurrentChannelLcn(i);
        }
    }

    public void sendStreamVolume(float f) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.sendStreamVolume(f);
        }
    }

    public void sendTrackInfoList(List<TvTrackInfo> list) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.sendTrackInfoList(list);
        }
    }

    public void sendSelectedTrackInfo(List<TvTrackInfo> list) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.sendSelectedTrackInfo(list);
        }
    }

    public void sendCurrentTvInputId(String str) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.sendCurrentTvInputId(str);
        }
    }

    public void sendTimeShiftMode(int i) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.sendTimeShiftMode(i);
        }
    }

    public void sendAvailableSpeeds(float[] fArr) {
        if (this.mSession != null) {
            Arrays.sort(fArr);
            this.mSession.sendAvailableSpeeds(fArr);
        }
    }

    public void sendTvRecordingInfo(TvRecordingInfo tvRecordingInfo) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.sendTvRecordingInfo(tvRecordingInfo);
        }
    }

    public void sendTvRecordingInfoList(List<TvRecordingInfo> list) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.sendTvRecordingInfoList(list);
        }
    }

    public void notifyRecordingStarted(String str, String str2) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyRecordingStarted(str, str2);
        }
    }

    public void notifyRecordingStopped(String str) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyRecordingStopped(str);
        }
    }

    public void notifyVideoFreezeUpdated(boolean z) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyVideoFreezeUpdated(z);
        }
    }

    public void sendSigningResult(String str, byte[] bArr) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.sendSigningResult(str, bArr);
        }
    }

    public void sendCertificate(String str, int i, SslCertificate sslCertificate) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.sendCertificate(str, i, sslCertificate);
        }
    }

    public void notifyError(String str, Bundle bundle) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyError(str, bundle);
        }
    }

    public void notifyTimeShiftPlaybackParams(PlaybackParams playbackParams) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyTimeShiftPlaybackParams(playbackParams);
        }
    }

    public void notifyTimeShiftStatusChanged(String str, int i) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyTimeShiftStatusChanged(str, i);
        }
    }

    public void notifyTimeShiftStartPositionChanged(String str, long j) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyTimeShiftStartPositionChanged(str, j);
        }
    }

    public void notifyTimeShiftCurrentPositionChanged(String str, long j) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyTimeShiftCurrentPositionChanged(str, j);
        }
    }

    public void notifyRecordingConnectionFailed(String str, String str2) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyRecordingConnectionFailed(str, str2);
        }
    }

    public void notifyRecordingDisconnected(String str, String str2) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyRecordingDisconnected(str, str2);
        }
    }

    public void notifyRecordingTuned(String str, Uri uri) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyRecordingTuned(str, uri);
        }
    }

    public void notifyRecordingError(String str, int i) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyRecordingError(str, i);
        }
    }

    public void notifyRecordingScheduled(String str, String str2) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyRecordingScheduled(str, str2);
        }
    }

    public void notifyTvMessage(int i, Bundle bundle) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.notifyTvMessage(i, bundle);
        }
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

    public void createBiInteractiveApp(Uri uri, Bundle bundle) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.createBiInteractiveApp(uri, bundle);
        }
    }

    public void destroyBiInteractiveApp(String str) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.destroyBiInteractiveApp(str);
        }
    }

    public TvInteractiveAppManager.Session getInteractiveAppSession() {
        return this.mSession;
    }

    public int setTvView(TvView tvView) {
        TvInteractiveAppManager.Session session;
        if (tvView == null) {
            return unsetTvView();
        }
        TvInputManager.Session inputSession = tvView.getInputSession();
        if (inputSession == null || (session = this.mSession) == null) {
            return 2;
        }
        session.setInputSession(inputSession);
        inputSession.setInteractiveAppSession(this.mSession);
        return 1;
    }

    private int unsetTvView() {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session == null || session.getInputSession() == null) {
            return 4;
        }
        this.mSession.getInputSession().setInteractiveAppSession(null);
        this.mSession.setInputSession(null);
        return 3;
    }

    public void setTeletextAppEnabled(boolean z) {
        TvInteractiveAppManager.Session session = this.mSession;
        if (session != null) {
            session.setTeletextAppEnabled(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class MySessionCallback extends TvInteractiveAppManager.SessionCallback {
        final String mIAppServiceId;
        int mType;

        MySessionCallback(String str, int i) {
            this.mIAppServiceId = str;
            this.mType = i;
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSessionCreated(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onSessionCreated - session already created");
                if (session != null) {
                    session.release();
                    return;
                }
                return;
            }
            TvInteractiveAppView.this.mSession = session;
            if (session != null) {
                if (TvInteractiveAppView.this.mSurface != null) {
                    TvInteractiveAppView tvInteractiveAppView = TvInteractiveAppView.this;
                    tvInteractiveAppView.setSessionSurface(tvInteractiveAppView.mSurface);
                    if (TvInteractiveAppView.this.mSurfaceChanged) {
                        TvInteractiveAppView tvInteractiveAppView2 = TvInteractiveAppView.this;
                        tvInteractiveAppView2.dispatchSurfaceChanged(tvInteractiveAppView2.mSurfaceFormat, TvInteractiveAppView.this.mSurfaceWidth, TvInteractiveAppView.this.mSurfaceHeight);
                    }
                }
                TvInteractiveAppView.this.createSessionMediaView();
                return;
            }
            TvInteractiveAppView.this.mSessionCallback = null;
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSessionReleased(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onSessionReleased - session not created");
                return;
            }
            TvInteractiveAppView.this.mMediaViewCreated = false;
            TvInteractiveAppView.this.mMediaViewFrame = null;
            TvInteractiveAppView.this.mSessionCallback = null;
            TvInteractiveAppView.this.mSession = null;
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onLayoutSurface(TvInteractiveAppManager.Session session, int i, int i2, int i3, int i4) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onLayoutSurface - session not created");
                return;
            }
            TvInteractiveAppView.this.mSurfaceViewLeft = i;
            TvInteractiveAppView.this.mSurfaceViewTop = i2;
            TvInteractiveAppView.this.mSurfaceViewRight = i3;
            TvInteractiveAppView.this.mSurfaceViewBottom = i4;
            TvInteractiveAppView.this.mUseRequestedSurfaceLayout = true;
            TvInteractiveAppView.this.requestLayout();
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onCommandRequest(TvInteractiveAppManager.Session session, final String str, final Bundle bundle) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onCommandRequest - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onCommandRequest$0(str, bundle);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCommandRequest$0(String str, Bundle bundle) {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onPlaybackCommandRequest(this.mIAppServiceId, str, bundle);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onTimeShiftCommandRequest(TvInteractiveAppManager.Session session, final String str, final Bundle bundle) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onTimeShiftCommandRequest - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onTimeShiftCommandRequest$1(str, bundle);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTimeShiftCommandRequest$1(String str, Bundle bundle) {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onTimeShiftCommandRequest(this.mIAppServiceId, str, bundle);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSessionStateChanged(TvInteractiveAppManager.Session session, final int i, final int i2) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onSessionStateChanged - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onSessionStateChanged$2(i, i2);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSessionStateChanged$2(int i, int i2) {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onStateChanged(this.mIAppServiceId, i, i2);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onBiInteractiveAppCreated(TvInteractiveAppManager.Session session, final Uri uri, final String str) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onBiInteractiveAppCreated - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onBiInteractiveAppCreated$3(uri, str);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBiInteractiveAppCreated$3(Uri uri, String str) {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onBiInteractiveAppCreated(this.mIAppServiceId, uri, str);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onTeletextAppStateChanged(TvInteractiveAppManager.Session session, int i) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onTeletextAppStateChanged - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onTeletextAppStateChanged(this.mIAppServiceId, i);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSetVideoBounds(TvInteractiveAppManager.Session session, final Rect rect) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onSetVideoBounds - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onSetVideoBounds$4(rect);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSetVideoBounds$4(Rect rect) {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onSetVideoBounds(this.mIAppServiceId, rect);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCurrentVideoBounds(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestCurrentVideoBounds - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onRequestCurrentVideoBounds$5();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentVideoBounds$5() {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onRequestCurrentVideoBounds(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCurrentChannelUri(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestCurrentChannelUri - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onRequestCurrentChannelUri$6();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentChannelUri$6() {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onRequestCurrentChannelUri(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCurrentChannelLcn(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestCurrentChannelLcn - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onRequestCurrentChannelLcn$7();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestCurrentChannelLcn$7() {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onRequestCurrentChannelLcn(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestStreamVolume(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestStreamVolume - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onRequestStreamVolume$8();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestStreamVolume$8() {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onRequestStreamVolume(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestTrackInfoList(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestTrackInfoList - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onRequestTrackInfoList$9();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestTrackInfoList$9() {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onRequestTrackInfoList(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestSelectedTrackInfo(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestSelectedTrackInfo - session not created");
                return;
            }
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallbackExecutor != null) {
                    TvInteractiveAppView.this.mCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppView$MySessionCallback$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInteractiveAppView.MySessionCallback.this.lambda$onRequestSelectedTrackInfo$10();
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestSelectedTrackInfo$10() {
            synchronized (TvInteractiveAppView.this.mCallbackLock) {
                if (TvInteractiveAppView.this.mCallback != null) {
                    TvInteractiveAppView.this.mCallback.onRequestSelectedTrackInfo(this.mIAppServiceId);
                }
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCurrentTvInputId(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestCurrentTvInputId - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestCurrentTvInputId(this.mIAppServiceId);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestTimeShiftMode(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestTimeShiftMode - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestTimeShiftMode(this.mIAppServiceId);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestAvailableSpeeds(TvInteractiveAppManager.Session session) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestAvailableSpeeds - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestAvailableSpeeds(this.mIAppServiceId);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestStartRecording(TvInteractiveAppManager.Session session, String str, Uri uri) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestStartRecording - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestStartRecording(this.mIAppServiceId, str, uri);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestStopRecording(TvInteractiveAppManager.Session session, String str) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestStopRecording - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestStopRecording(this.mIAppServiceId, str);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onSetTvRecordingInfo(TvInteractiveAppManager.Session session, String str, TvRecordingInfo tvRecordingInfo) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onSetRecordingInfo - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onSetTvRecordingInfo(this.mIAppServiceId, str, tvRecordingInfo);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestScheduleRecording(TvInteractiveAppManager.Session session, String str, String str2, Uri uri, Uri uri2, Bundle bundle) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestScheduleRecording - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestScheduleRecording(this.mIAppServiceId, str, str2, uri, uri2, bundle);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestScheduleRecording(TvInteractiveAppManager.Session session, String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestScheduleRecording - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestScheduleRecording(this.mIAppServiceId, str, str2, uri, j, j2, i, bundle);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestTvRecordingInfo(TvInteractiveAppManager.Session session, String str) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestRecordingInfo - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestTvRecordingInfo(this.mIAppServiceId, str);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestTvRecordingInfoList(TvInteractiveAppManager.Session session, int i) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestRecordingInfoList - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestTvRecordingInfoList(this.mIAppServiceId, i);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestSigning(TvInteractiveAppManager.Session session, String str, String str2, String str3, byte[] bArr) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestSigning - session not created");
            } else if (TvInteractiveAppView.this.mCallback != null) {
                TvInteractiveAppView.this.mCallback.onRequestSigning(this.mIAppServiceId, str, str2, str3, bArr);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestSigning(TvInteractiveAppManager.Session session, String str, String str2, String str3, int i, byte[] bArr) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestSigning - session not created");
            } else {
                if (TvInteractiveAppView.this.mCallback == null || !Flags.tiafVApis()) {
                    return;
                }
                TvInteractiveAppView.this.mCallback.onRequestSigning(this.mIAppServiceId, str, str2, str3, i, bArr);
            }
        }

        @Override // android.media.tv.interactive.TvInteractiveAppManager.SessionCallback
        public void onRequestCertificate(TvInteractiveAppManager.Session session, String str, int i) {
            if (this != TvInteractiveAppView.this.mSessionCallback) {
                Log.w(TvInteractiveAppView.TAG, "onRequestCertificate - session not created");
            } else {
                if (TvInteractiveAppView.this.mCallback == null || !Flags.tiafVApis()) {
                    return;
                }
                TvInteractiveAppView.this.mCallback.onRequestCertificate(this.mIAppServiceId, str, i);
            }
        }
    }
}
