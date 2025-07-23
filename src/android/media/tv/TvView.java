package android.media.tv;

import android.Manifest;
import android.annotation.SystemApi;
import android.content.AttributionSource;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.media.AudioPresentation;
import android.media.PlaybackParams;
import android.media.tv.TvInputManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.Xml;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/* loaded from: classes3.dex */
public class TvView extends ViewGroup {
    private static final boolean DEBUG = false;
    private static final WeakReference<TvView> NULL_TV_VIEW;
    private static final String TAG = "TvView";
    private static final int ZORDER_MEDIA = 0;
    private static final int ZORDER_MEDIA_OVERLAY = 1;
    private static final int ZORDER_ON_TOP = 2;
    private static WeakReference<TvView> sMainTvView;
    private static final Object sMainTvViewLock;
    private final AttributeSet mAttrs;
    private TvInputCallback mCallback;
    private Boolean mCaptionEnabled;
    private final int mDefStyleAttr;
    private final TvInputManager.Session.FinishedInputEventCallback mFinishedInputEventCallback;
    private Handler mHandler;
    private OnUnhandledInputEventListener mOnUnhandledInputEventListener;
    private boolean mOverlayViewCreated;
    private Rect mOverlayViewFrame;
    private final XmlResourceParser mParser;
    private final Queue<Pair<String, Bundle>> mPendingAppPrivateCommands;
    private TvInputManager.Session mSession;
    private MySessionCallback mSessionCallback;
    private Float mStreamVolume;
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
    private TimeShiftPositionCallback mTimeShiftPositionCallback;
    private AttributionSource mTvAppAttributionSource;
    private final TvInputManager mTvInputManager;
    private boolean mUseRequestedSurfaceLayout;
    private int mWindowZOrder;

    public interface OnUnhandledInputEventListener {
        boolean onUnhandledInputEvent(InputEvent inputEvent);
    }

    public static abstract class TimeShiftPositionCallback {
        public void onTimeShiftCurrentPositionChanged(String str, long j) {
        }

        public void onTimeShiftStartPositionChanged(String str, long j) {
        }
    }

    public static abstract class TvInputCallback {
        public void onAitInfoUpdated(String str, AitInfo aitInfo) {
        }

        public void onAudioPresentationSelected(String str, int i, int i2) {
        }

        public void onAudioPresentationsChanged(String str, List<AudioPresentation> list) {
        }

        public void onAvailableSpeeds(String str, float[] fArr) {
        }

        public void onChannelRetuned(String str, Uri uri) {
        }

        public void onConnectionFailed(String str) {
        }

        public void onContentAllowed(String str) {
        }

        public void onContentBlocked(String str, TvContentRating tvContentRating) {
        }

        public void onCueingMessageAvailability(String str, boolean z) {
        }

        public void onDisconnected(String str) {
        }

        @SystemApi
        public void onEvent(String str, String str2, Bundle bundle) {
        }

        public void onSignalStrengthUpdated(String str, int i) {
        }

        public void onTimeShiftMode(String str, int i) {
        }

        public void onTimeShiftStatusChanged(String str, int i) {
        }

        public void onTrackSelected(String str, int i, String str2) {
        }

        public void onTracksChanged(String str, List<TvTrackInfo> list) {
        }

        public void onTuned(String str, Uri uri) {
        }

        public void onTvMessage(String str, int i, Bundle bundle) {
        }

        public void onVideoAvailable(String str) {
        }

        public void onVideoFreezeUpdated(String str, boolean z) {
        }

        public void onVideoSizeChanged(String str, int i, int i2) {
        }

        public void onVideoUnavailable(String str, int i) {
        }
    }

    public boolean onUnhandledInputEvent(InputEvent inputEvent) {
        return false;
    }

    static {
        WeakReference<TvView> weakReference = new WeakReference<>(null);
        NULL_TV_VIEW = weakReference;
        sMainTvViewLock = new Object();
        sMainTvView = weakReference;
    }

    public TvView(Context context) {
        this(context, null, 0);
    }

    public TvView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TvView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHandler = new Handler();
        this.mPendingAppPrivateCommands = new ArrayDeque();
        this.mSurfaceHolderCallback = new SurfaceHolder.Callback() { // from class: android.media.tv.TvView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                TvView.this.mSurfaceFormat = i2;
                TvView.this.mSurfaceWidth = i3;
                TvView.this.mSurfaceHeight = i4;
                TvView.this.mSurfaceChanged = true;
                TvView tvView = TvView.this;
                tvView.dispatchSurfaceChanged(tvView.mSurfaceFormat, TvView.this.mSurfaceWidth, TvView.this.mSurfaceHeight);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                TvView.this.mSurface = surfaceHolder.getSurface();
                TvView tvView = TvView.this;
                tvView.setSessionSurface(tvView.mSurface);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                TvView.this.mSurface = null;
                TvView.this.mSurfaceChanged = false;
                TvView.this.setSessionSurface(null);
            }
        };
        this.mFinishedInputEventCallback = new TvInputManager.Session.FinishedInputEventCallback() { // from class: android.media.tv.TvView.2
            @Override // android.media.tv.TvInputManager.Session.FinishedInputEventCallback
            public void onFinishedInputEvent(Object obj, boolean z) {
                ViewRootImpl viewRootImpl;
                if (z) {
                    return;
                }
                InputEvent inputEvent = (InputEvent) obj;
                if (TvView.this.dispatchUnhandledInputEvent(inputEvent) || (viewRootImpl = TvView.this.getViewRootImpl()) == null) {
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
        this.mTvInputManager = (TvInputManager) getContext().getSystemService(Context.TV_INPUT_SERVICE);
        this.mTvAppAttributionSource = getContext().getAttributionSource();
    }

    public void setCallback(TvInputCallback tvInputCallback) {
        this.mCallback = tvInputCallback;
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    public TvInputManager.Session getInputSession() {
        return this.mSession;
    }

    @SystemApi
    public void setMain() {
        TvInputManager.Session session;
        synchronized (sMainTvViewLock) {
            sMainTvView = new WeakReference<>(this);
            if (hasWindowFocus() && (session = this.mSession) != null) {
                session.setMain();
            }
        }
    }

    public void setZOrderMediaOverlay(boolean z) {
        if (z) {
            this.mWindowZOrder = 1;
            removeSessionOverlayView();
        } else {
            this.mWindowZOrder = 0;
            createSessionOverlayView();
        }
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.setZOrderOnTop(false);
            this.mSurfaceView.setZOrderMediaOverlay(z);
        }
    }

    public void setZOrderOnTop(boolean z) {
        if (z) {
            this.mWindowZOrder = 2;
            removeSessionOverlayView();
        } else {
            this.mWindowZOrder = 0;
            createSessionOverlayView();
        }
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.setZOrderMediaOverlay(false);
            this.mSurfaceView.setZOrderOnTop(z);
        }
    }

    public void setStreamVolume(float f) {
        this.mStreamVolume = Float.valueOf(f);
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return;
        }
        session.setStreamVolume(f);
    }

    public void overrideTvAppAttributionSource(AttributionSource attributionSource) {
        if (attributionSource != null) {
            this.mTvAppAttributionSource = attributionSource;
        }
    }

    public void tune(String str, Uri uri) {
        tune(str, uri, null);
    }

    public void tune(String str, Uri uri, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("inputId cannot be null or an empty string");
        }
        synchronized (sMainTvViewLock) {
            if (sMainTvView.get() == null) {
                sMainTvView = new WeakReference<>(this);
            }
        }
        MySessionCallback mySessionCallback = this.mSessionCallback;
        if (mySessionCallback != null && TextUtils.equals(mySessionCallback.mInputId, str)) {
            TvInputManager.Session session = this.mSession;
            if (session != null) {
                session.tune(uri, bundle);
                return;
            } else {
                this.mSessionCallback.mChannelUri = uri;
                this.mSessionCallback.mTuneParams = bundle;
                return;
            }
        }
        resetInternal();
        MySessionCallback mySessionCallback2 = new MySessionCallback(str, uri, bundle);
        this.mSessionCallback = mySessionCallback2;
        TvInputManager tvInputManager = this.mTvInputManager;
        if (tvInputManager != null) {
            tvInputManager.createSession(str, this.mTvAppAttributionSource, mySessionCallback2, this.mHandler);
        }
    }

    public void reset() {
        synchronized (sMainTvViewLock) {
            if (this == sMainTvView.get()) {
                sMainTvView = NULL_TV_VIEW;
            }
        }
        resetInternal();
    }

    private void resetInternal() {
        this.mSessionCallback = null;
        synchronized (this.mPendingAppPrivateCommands) {
            this.mPendingAppPrivateCommands.clear();
        }
        if (this.mSession != null) {
            setSessionSurface(null);
            removeSessionOverlayView();
            this.mUseRequestedSurfaceLayout = false;
            this.mSession.release();
            this.mSession = null;
            resetSurfaceView();
        }
    }

    public void requestUnblockContent(TvContentRating tvContentRating) {
        unblockContent(tvContentRating);
    }

    @SystemApi
    public void unblockContent(TvContentRating tvContentRating) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.unblockContent(tvContentRating);
        }
    }

    public void setCaptionEnabled(boolean z) {
        this.mCaptionEnabled = Boolean.valueOf(z);
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.setCaptionEnabled(z);
        }
    }

    public void selectAudioPresentation(int i, int i2) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.selectAudioPresentation(i, i2);
        }
    }

    public List<AudioPresentation> getAudioPresentations() {
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return new ArrayList();
        }
        return session.getAudioPresentations();
    }

    public void selectTrack(int i, String str) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.selectTrack(i, str);
        }
    }

    public List<TvTrackInfo> getTracks(int i) {
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return null;
        }
        return session.getTracks(i);
    }

    public String getSelectedTrack(int i) {
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return null;
        }
        return session.getSelectedTrack(i);
    }

    public void setInteractiveAppNotificationEnabled(boolean z) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.setInteractiveAppNotificationEnabled(z);
        }
    }

    public void timeShiftPlay(String str, Uri uri) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("inputId cannot be null or an empty string");
        }
        synchronized (sMainTvViewLock) {
            if (sMainTvView.get() == null) {
                sMainTvView = new WeakReference<>(this);
            }
        }
        MySessionCallback mySessionCallback = this.mSessionCallback;
        if (mySessionCallback != null && TextUtils.equals(mySessionCallback.mInputId, str)) {
            TvInputManager.Session session = this.mSession;
            if (session != null) {
                session.timeShiftPlay(uri);
                return;
            } else {
                this.mSessionCallback.mRecordedProgramUri = uri;
                return;
            }
        }
        resetInternal();
        MySessionCallback mySessionCallback2 = new MySessionCallback(str, uri);
        this.mSessionCallback = mySessionCallback2;
        TvInputManager tvInputManager = this.mTvInputManager;
        if (tvInputManager != null) {
            tvInputManager.createSession(str, this.mTvAppAttributionSource, mySessionCallback2, this.mHandler);
        }
    }

    public void timeShiftPause() {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.timeShiftPause();
        }
    }

    public void timeShiftResume() {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.timeShiftResume();
        }
    }

    public void timeShiftSeekTo(long j) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.timeShiftSeekTo(j);
        }
    }

    public void timeShiftSetPlaybackParams(PlaybackParams playbackParams) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.timeShiftSetPlaybackParams(playbackParams);
        }
    }

    public void timeShiftSetMode(int i) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.timeShiftSetMode(i);
        }
    }

    public void stopPlayback(int i) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.stopPlayback(i);
        }
    }

    public void resumePlayback() {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.resumePlayback();
        }
    }

    public void setVideoFrozen(boolean z) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.setVideoFrozen(z);
        }
    }

    public void notifyTvMessage(int i, Bundle bundle) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.notifyTvMessage(i, bundle);
        }
    }

    public void setTimeShiftPositionCallback(TimeShiftPositionCallback timeShiftPositionCallback) {
        this.mTimeShiftPositionCallback = timeShiftPositionCallback;
        ensurePositionTracking();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensurePositionTracking() {
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return;
        }
        session.timeShiftEnablePositionTracking(this.mTimeShiftPositionCallback != null);
    }

    public void sendAppPrivateCommand(String str, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("action cannot be null or an empty string");
        }
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.sendAppPrivateCommand(str, bundle);
            return;
        }
        Log.w(TAG, "sendAppPrivateCommand - session not yet created (action \"" + str + "\" pending)");
        synchronized (this.mPendingAppPrivateCommands) {
            this.mPendingAppPrivateCommands.add(Pair.create(str, bundle));
        }
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

    public void setTvMessageEnabled(int i, boolean z) {
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.setTvMessageEnabled(i, z);
        }
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

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (super.dispatchTouchEvent(motionEvent)) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        MotionEvent copy = motionEvent.copy();
        return this.mSession.dispatchInputEvent(copy, copy, this.mFinishedInputEventCallback, this.mHandler) != 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        if (super.dispatchTrackballEvent(motionEvent)) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        MotionEvent copy = motionEvent.copy();
        return this.mSession.dispatchInputEvent(copy, copy, this.mFinishedInputEventCallback, this.mHandler) != 0;
    }

    @Override // android.view.View
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (super.dispatchGenericMotionEvent(motionEvent)) {
            return true;
        }
        if (this.mSession == null) {
            return false;
        }
        MotionEvent copy = motionEvent.copy();
        return this.mSession.dispatchInputEvent(copy, copy, this.mFinishedInputEventCallback, this.mHandler) != 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchWindowFocusChanged(boolean z) {
        super.dispatchWindowFocusChanged(z);
        synchronized (sMainTvViewLock) {
            if (z) {
                if (this == sMainTvView.get() && this.mSession != null && checkChangeHdmiCecActiveSourcePermission()) {
                    this.mSession.setMain();
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        createSessionOverlayView();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        removeSessionOverlayView();
        super.onDetachedFromWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mUseRequestedSurfaceLayout) {
            this.mSurfaceView.layout(this.mSurfaceViewLeft, this.mSurfaceViewTop, this.mSurfaceViewRight, this.mSurfaceViewBottom);
        } else {
            this.mSurfaceView.layout(0, 0, i3 - i, i4 - i2);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        this.mSurfaceView.measure(i, i2);
        int measuredWidth = this.mSurfaceView.getMeasuredWidth();
        int measuredHeight = this.mSurfaceView.getMeasuredHeight();
        int measuredState = this.mSurfaceView.getMeasuredState();
        setMeasuredDimension(resolveSizeAndState(measuredWidth, i, measuredState), resolveSizeAndState(measuredHeight, i2, measuredState << 16));
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean gatherTransparentRegion(Region region) {
        Region region2;
        if (this.mWindowZOrder != 2 && region != null) {
            int width = getWidth();
            int height = getHeight();
            if (width > 0 && height > 0) {
                int[] iArr = new int[2];
                getLocationInWindow(iArr);
                int i = iArr[0];
                int i2 = iArr[1];
                region2 = region;
                region2.op(i, i2, i + width, i2 + height, Region.Op.UNION);
                return super.gatherTransparentRegion(region2);
            }
        }
        region2 = region;
        return super.gatherTransparentRegion(region2);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (this.mWindowZOrder != 2) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        }
        super.draw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mWindowZOrder != 2) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        this.mSurfaceView.setVisibility(i);
        if (i == 0) {
            createSessionOverlayView();
        } else {
            removeSessionOverlayView();
        }
    }

    private void resetSurfaceView() {
        SurfaceView surfaceView = this.mSurfaceView;
        if (surfaceView != null) {
            surfaceView.getHolder().removeCallback(this.mSurfaceHolderCallback);
            removeView(this.mSurfaceView);
        }
        this.mSurface = null;
        SurfaceView surfaceView2 = new SurfaceView(getContext(), this.mAttrs, this.mDefStyleAttr) { // from class: android.media.tv.TvView.3
            @Override // android.view.SurfaceView
            protected void updateSurface() {
                super.updateSurface();
                TvView.this.relayoutSessionOverlayView();
            }
        };
        this.mSurfaceView = surfaceView2;
        surfaceView2.setSecure(true);
        this.mSurfaceView.getHolder().addCallback(this.mSurfaceHolderCallback);
        int i = this.mWindowZOrder;
        if (i == 1) {
            this.mSurfaceView.setZOrderMediaOverlay(true);
        } else if (i == 2) {
            this.mSurfaceView.setZOrderOnTop(true);
        }
        addView(this.mSurfaceView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSessionSurface(Surface surface) {
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return;
        }
        session.setSurface(surface);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchSurfaceChanged(int i, int i2, int i3) {
        TvInputManager.Session session = this.mSession;
        if (session == null) {
            return;
        }
        session.dispatchSurfaceChanged(i, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createSessionOverlayView() {
        if (this.mSession == null || !isAttachedToWindow() || this.mOverlayViewCreated || this.mWindowZOrder != 0) {
            return;
        }
        Rect viewFrameOnScreen = getViewFrameOnScreen();
        this.mOverlayViewFrame = viewFrameOnScreen;
        this.mSession.createOverlayView(this, viewFrameOnScreen);
        this.mOverlayViewCreated = true;
    }

    private void removeSessionOverlayView() {
        TvInputManager.Session session = this.mSession;
        if (session == null || !this.mOverlayViewCreated) {
            return;
        }
        session.removeOverlayView();
        this.mOverlayViewCreated = false;
        this.mOverlayViewFrame = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void relayoutSessionOverlayView() {
        if (this.mSession != null && isAttachedToWindow() && this.mOverlayViewCreated && this.mWindowZOrder == 0) {
            Rect viewFrameOnScreen = getViewFrameOnScreen();
            if (viewFrameOnScreen.equals(this.mOverlayViewFrame)) {
                return;
            }
            this.mSession.relayoutOverlayView(viewFrameOnScreen);
            this.mOverlayViewFrame = viewFrameOnScreen;
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
    public boolean checkChangeHdmiCecActiveSourcePermission() {
        return getContext().checkSelfPermission(Manifest.permission.CHANGE_HDMI_CEC_ACTIVE_SOURCE) == 0;
    }

    private class MySessionCallback extends TvInputManager.SessionCallback {
        Uri mChannelUri;
        final String mInputId;
        Uri mRecordedProgramUri;
        Bundle mTuneParams;

        MySessionCallback(String str, Uri uri, Bundle bundle) {
            this.mInputId = str;
            this.mChannelUri = uri;
            this.mTuneParams = bundle;
        }

        MySessionCallback(String str, Uri uri) {
            this.mInputId = str;
            this.mRecordedProgramUri = uri;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionCreated(TvInputManager.Session session) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onSessionCreated - session already created");
                if (session != null) {
                    session.release();
                    return;
                }
                return;
            }
            TvView.this.mSession = session;
            if (session != null) {
                synchronized (TvView.this.mPendingAppPrivateCommands) {
                    for (Pair pair : TvView.this.mPendingAppPrivateCommands) {
                        TvView.this.mSession.sendAppPrivateCommand((String) pair.first, (Bundle) pair.second);
                    }
                    TvView.this.mPendingAppPrivateCommands.clear();
                }
                synchronized (TvView.sMainTvViewLock) {
                    if (TvView.this.hasWindowFocus() && TvView.this == TvView.sMainTvView.get() && TvView.this.checkChangeHdmiCecActiveSourcePermission()) {
                        TvView.this.mSession.setMain();
                    }
                }
                if (TvView.this.mSurface != null) {
                    TvView tvView = TvView.this;
                    tvView.setSessionSurface(tvView.mSurface);
                    if (TvView.this.mSurfaceChanged) {
                        TvView tvView2 = TvView.this;
                        tvView2.dispatchSurfaceChanged(tvView2.mSurfaceFormat, TvView.this.mSurfaceWidth, TvView.this.mSurfaceHeight);
                    }
                }
                TvView.this.createSessionOverlayView();
                if (TvView.this.mStreamVolume != null) {
                    TvView.this.mSession.setStreamVolume(TvView.this.mStreamVolume.floatValue());
                }
                if (TvView.this.mCaptionEnabled != null) {
                    TvView.this.mSession.setCaptionEnabled(TvView.this.mCaptionEnabled.booleanValue());
                }
                if (this.mChannelUri != null) {
                    TvView.this.mSession.tune(this.mChannelUri, this.mTuneParams);
                } else {
                    TvView.this.mSession.timeShiftPlay(this.mRecordedProgramUri);
                }
                TvView.this.ensurePositionTracking();
                return;
            }
            TvView.this.mSessionCallback = null;
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onConnectionFailed(this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionReleased(TvInputManager.Session session) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onSessionReleased - session not created");
                return;
            }
            TvView.this.mOverlayViewCreated = false;
            TvView.this.mOverlayViewFrame = null;
            TvView.this.mSessionCallback = null;
            TvView.this.mSession = null;
            if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onDisconnected(this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onChannelRetuned(TvInputManager.Session session, Uri uri) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onChannelRetuned - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onChannelRetuned(this.mInputId, uri);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onAudioPresentationsChanged(TvInputManager.Session session, List<AudioPresentation> list) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onAudioPresentationsChanged - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onAudioPresentationsChanged(this.mInputId, list);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onAudioPresentationSelected(TvInputManager.Session session, int i, int i2) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onAudioPresentationSelected - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onAudioPresentationSelected(this.mInputId, i, i2);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTracksChanged(TvInputManager.Session session, List<TvTrackInfo> list) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTracksChanged - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onTracksChanged(this.mInputId, list);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTrackSelected(TvInputManager.Session session, int i, String str) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTrackSelected - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onTrackSelected(this.mInputId, i, str);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onVideoSizeChanged(TvInputManager.Session session, int i, int i2) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onVideoSizeChanged - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onVideoSizeChanged(this.mInputId, i, i2);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onVideoAvailable(TvInputManager.Session session) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onVideoAvailable - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onVideoAvailable(this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onVideoUnavailable(TvInputManager.Session session, int i) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onVideoUnavailable - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onVideoUnavailable(this.mInputId, i);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onContentAllowed(TvInputManager.Session session) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onContentAllowed - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onContentAllowed(this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onContentBlocked(TvInputManager.Session session, TvContentRating tvContentRating) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onContentBlocked - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onContentBlocked(this.mInputId, tvContentRating);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onLayoutSurface(TvInputManager.Session session, int i, int i2, int i3, int i4) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onLayoutSurface - session not created");
                return;
            }
            TvView.this.mSurfaceViewLeft = i;
            TvView.this.mSurfaceViewTop = i2;
            TvView.this.mSurfaceViewRight = i3;
            TvView.this.mSurfaceViewBottom = i4;
            TvView.this.mUseRequestedSurfaceLayout = true;
            TvView.this.requestLayout();
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionEvent(TvInputManager.Session session, String str, Bundle bundle) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onSessionEvent - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onEvent(this.mInputId, str, bundle);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTimeShiftStatusChanged(TvInputManager.Session session, int i) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTimeShiftStatusChanged - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onTimeShiftStatusChanged(this.mInputId, i);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTimeShiftStartPositionChanged(TvInputManager.Session session, long j) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTimeShiftStartPositionChanged - session not created");
            } else if (TvView.this.mTimeShiftPositionCallback != null) {
                TvView.this.mTimeShiftPositionCallback.onTimeShiftStartPositionChanged(this.mInputId, j);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTimeShiftCurrentPositionChanged(TvInputManager.Session session, long j) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTimeShiftCurrentPositionChanged - session not created");
            } else if (TvView.this.mTimeShiftPositionCallback != null) {
                TvView.this.mTimeShiftPositionCallback.onTimeShiftCurrentPositionChanged(this.mInputId, j);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onAitInfoUpdated(TvInputManager.Session session, AitInfo aitInfo) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onAitInfoUpdated - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onAitInfoUpdated(this.mInputId, aitInfo);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSignalStrengthUpdated(TvInputManager.Session session, int i) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onSignalStrengthUpdated - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onSignalStrengthUpdated(this.mInputId, i);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onCueingMessageAvailability(TvInputManager.Session session, boolean z) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onCueingMessageAvailability - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onCueingMessageAvailability(this.mInputId, z);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTimeShiftMode(TvInputManager.Session session, int i) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTimeShiftMode - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onTimeShiftMode(this.mInputId, i);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onAvailableSpeeds(TvInputManager.Session session, float[] fArr) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onAvailableSpeeds - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onAvailableSpeeds(this.mInputId, fArr);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTuned(TvInputManager.Session session, Uri uri) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTuned - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onTuned(this.mInputId, uri);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTvMessage(TvInputManager.Session session, int i, Bundle bundle) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onTvMessage - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onTvMessage(this.mInputId, i, bundle);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onVideoFreezeUpdated(TvInputManager.Session session, boolean z) {
            if (this != TvView.this.mSessionCallback) {
                Log.w(TvView.TAG, "onVideoFreezeUpdated - session not created");
            } else if (TvView.this.mCallback != null) {
                TvView.this.mCallback.onVideoFreezeUpdated(this.mInputId, z);
            }
        }
    }
}
