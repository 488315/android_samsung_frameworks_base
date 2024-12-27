package android.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.CompatibilityInfo;
import android.graphics.BLASTBufferQueue;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RenderNode;
import android.hardware.input.InputManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.secutil.Slog;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityEmbeddedConnection;
import android.window.SurfaceSyncGroup;

import com.android.graphics.hwui.flags.Flags;
import com.android.internal.view.SurfaceCallbackHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class SurfaceView extends View implements ViewRootImpl.SurfaceChangedCallback {
    private static final boolean DEBUG = true;
    private static final boolean DEBUG_POSITION = true;
    private static final long FORWARD_BACK_KEY_TOLERANCE_MS = 100;
    public static final int SURFACE_LIFECYCLE_DEFAULT = 0;
    public static final int SURFACE_LIFECYCLE_FOLLOWS_ATTACHMENT = 2;
    public static final int SURFACE_LIFECYCLE_FOLLOWS_VISIBILITY = 1;
    private static final String TAG = "SurfaceView";
    private static final int UPDATESURFACE_CALLED_BY_DETACHEDFROMWINDOW = 4;
    private static final int UPDATESURFACE_CALLED_BY_PREDRAW = 8;
    private static final int UPDATESURFACE_CALLED_BY_SCROLLCHANGED = 7;
    private static final int UPDATESURFACE_CALLED_BY_SETFORMAT = 6;
    private static final int UPDATESURFACE_CALLED_BY_SETFRAME = 5;
    private static final int UPDATESURFACE_CALLED_BY_SETVISIBILITY = 3;
    private static final int UPDATESURFACE_CALLED_BY_WINDOWSTOPPED = 1;
    private static final int UPDATESURFACE_CALLED_BY_WINDOWVISIBILITYCHANGED = 2;
    float mAlpha;
    private boolean mAttachedToWindow;
    int mBackgroundColor;
    SurfaceControl mBackgroundControl;
    private BLASTBufferQueue mBlastBufferQueue;
    private SurfaceControl mBlastSurfaceControl;
    final ArrayList<SurfaceHolder.Callback> mCallbacks;
    boolean mClipSurfaceToBounds;
    float mCornerRadius;
    private boolean mDisableBackgroundLayer;
    boolean mDrawFinished;
    private final ViewTreeObserver.OnPreDrawListener mDrawListener;
    boolean mDrawingStopped;
    private final ConcurrentLinkedQueue<WindowManager.LayoutParams> mEmbeddedWindowParams;
    int mFormat;
    private final SurfaceControl.Transaction mFrameCallbackTransaction;
    private boolean mGlobalListenersAdded;
    boolean mHaveFrame;
    private float mHdrHeadroom;
    boolean mIsCreating;
    private boolean mIsWindowOpaque;
    long mLastLockTime;
    int mLastSurfaceHeight;
    int mLastSurfaceWidth;
    boolean mLastWindowVisibility;
    private final boolean mLimitedHdrEnabled;
    final int[] mLocation;
    private int mParentSurfaceSequenceId;
    private SurfaceViewPositionUpdateListener mPositionListener;
    private final Rect mRTLastReportedPosition;
    private final Rect mRTLastSetCrop;
    private RemoteAccessibilityController mRemoteAccessibilityController;
    int mRequestedFormat;
    private float mRequestedHdrHeadroom;
    int mRequestedHeight;
    int mRequestedSubLayer;
    private int mRequestedSurfaceLifecycleStrategy;
    boolean mRequestedVisible;
    int mRequestedWidth;
    Paint mRoundedViewportPaint;
    private final boolean mRtDrivenClipping;
    private final SurfaceControl.Transaction mRtTransaction;
    final Rect mScreenRect;
    private final ViewTreeObserver.OnScrollChangedListener mScrollChangedListener;
    int mSubLayer;
    final Surface mSurface;

    @ViewDebug.ExportedProperty(category = TAG)
    SurfaceControl mSurfaceControl;

    final Object mSurfaceControlLock;
    private final ISurfaceControlViewHostParent mSurfaceControlViewHostParent;
    boolean mSurfaceCreated;
    private int mSurfaceCreatedCount;
    private int mSurfaceFlags;
    final Rect mSurfaceFrame;
    int mSurfaceHeight;
    private final SurfaceHolder mSurfaceHolder;
    private int mSurfaceLifecycleStrategy;
    final ReentrantLock mSurfaceLock;
    SurfaceControlViewHost.SurfacePackage mSurfacePackage;
    private final SurfaceSession mSurfaceSession;
    int mSurfaceWidth;
    private final ArraySet<SurfaceSyncGroup> mSyncGroups;
    private String mTag;
    private final Matrix mTmpMatrix;
    final Rect mTmpRect;
    int mTransformHint;
    private int mUpdateSurfaceCalledBy;
    boolean mViewVisibility;
    boolean mVisible;
    int mWindowSpaceLeft;
    int mWindowSpaceTop;
    boolean mWindowStopped;
    boolean mWindowVisibility;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SurfaceLifecycleStrategy {}

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0() {
        this.mHaveFrame = getWidth() > 0 && getHeight() > 0;
        this.mUpdateSurfaceCalledBy = 8;
        updateSurface();
        return true;
    }

    /* renamed from: android.view.SurfaceView$1, reason: invalid class name */
    class AnonymousClass1 extends ISurfaceControlViewHostParent.Stub {
        AnonymousClass1() {}

        @Override // android.view.ISurfaceControlViewHostParent
        public void updateParams(WindowManager.LayoutParams[] childAttrs) {
            SurfaceView.this.mEmbeddedWindowParams.clear();
            SurfaceView.this.mEmbeddedWindowParams.addAll(Arrays.asList(childAttrs));
            if (SurfaceView.this.isAttachedToWindow()) {
                SurfaceView.this.runOnUiThread(
                        new Runnable() { // from class:
                                         // android.view.SurfaceView$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SurfaceView.AnonymousClass1.this.lambda$updateParams$0();
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateParams$0() {
            if (SurfaceView.this.mParent != null) {
                SurfaceView.this.mParent.recomputeViewAttributes(SurfaceView.this);
            }
        }

        @Override // android.view.ISurfaceControlViewHostParent
        public void forwardBackKeyToParent(final KeyEvent keyEvent) {
            SurfaceView.this.runOnUiThread(
                    new Runnable() { // from class:
                                     // android.view.SurfaceView$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SurfaceView.AnonymousClass1.this.lambda$forwardBackKeyToParent$1(
                                    keyEvent);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$forwardBackKeyToParent$1(KeyEvent keyEvent) {
            ViewRootImpl vri;
            InputManager inputManager;
            if (!SurfaceView.this.isAttachedToWindow()
                    || keyEvent.getKeyCode() != 4
                    || (vri = SurfaceView.this.getViewRootImpl()) == null
                    || (inputManager =
                                    (InputManager)
                                            SurfaceView.this.mContext.getSystemService(
                                                    InputManager.class))
                            == null) {
                return;
            }
            long timeDiff = SystemClock.uptimeMillis() - keyEvent.getEventTime();
            if (timeDiff > SurfaceView.FORWARD_BACK_KEY_TOLERANCE_MS) {
                Log.e(
                        SurfaceView.TAG,
                        "Ignore the input event that exceed the tolerance time, exceed "
                                + timeDiff
                                + "ms");
                return;
            }
            if (inputManager.verifyInputEvent(keyEvent) == null) {
                Log.e(SurfaceView.TAG, "Received invalid input event");
                return;
            }
            try {
                vri.processingBackKey(true);
                vri.enqueueInputEvent(keyEvent, null, 0, true);
            } finally {
                vri.processingBackKey(false);
            }
        }
    }

    public SurfaceView(Context context) {
        this(context, null);
    }

    public SurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr, defStyleRes, false);
    }

    public SurfaceView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes,
            boolean disableBackgroundLayer) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mCallbacks = new ArrayList<>();
        this.mLocation = new int[2];
        this.mSurfaceLock = new ReentrantLock();
        this.mSurface = new Surface();
        this.mDrawingStopped = true;
        this.mDrawFinished = false;
        this.mScreenRect = new Rect();
        this.mSurfaceSession = new SurfaceSession();
        this.mLimitedHdrEnabled = Flags.limitedHdr();
        this.mDisableBackgroundLayer = false;
        this.mRequestedSurfaceLifecycleStrategy = 0;
        this.mSurfaceLifecycleStrategy = 0;
        this.mRequestedHdrHeadroom = 0.0f;
        this.mHdrHeadroom = 0.0f;
        this.mSurfaceControlLock = new Object();
        this.mTmpRect = new Rect();
        this.mSubLayer = -2;
        this.mRequestedSubLayer = -2;
        this.mIsCreating = false;
        this.mUpdateSurfaceCalledBy = 0;
        this.mSurfaceCreatedCount = 0;
        this.mTag = TAG;
        this.mScrollChangedListener =
                new ViewTreeObserver
                        .OnScrollChangedListener() { // from class:
                                                     // android.view.SurfaceView$$ExternalSyntheticLambda0
                    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
                    public final void onScrollChanged() {
                        SurfaceView.this.updateSurface();
                    }
                };
        this.mDrawListener =
                new ViewTreeObserver
                        .OnPreDrawListener() { // from class:
                                               // android.view.SurfaceView$$ExternalSyntheticLambda1
                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
                    public final boolean onPreDraw() {
                        boolean lambda$new$0;
                        lambda$new$0 = SurfaceView.this.lambda$new$0();
                        return lambda$new$0;
                    }
                };
        this.mRequestedVisible = false;
        this.mWindowVisibility = false;
        this.mLastWindowVisibility = false;
        this.mViewVisibility = false;
        this.mWindowStopped = false;
        this.mRequestedWidth = -1;
        this.mRequestedHeight = -1;
        this.mRequestedFormat = 4;
        this.mAlpha = 1.0f;
        this.mBackgroundColor = -16777216;
        this.mHaveFrame = false;
        this.mSurfaceCreated = false;
        this.mLastLockTime = 0L;
        this.mVisible = false;
        this.mWindowSpaceLeft = -1;
        this.mWindowSpaceTop = -1;
        this.mSurfaceWidth = -1;
        this.mSurfaceHeight = -1;
        this.mFormat = -1;
        this.mSurfaceFrame = new Rect();
        this.mLastSurfaceWidth = -1;
        this.mLastSurfaceHeight = -1;
        this.mTransformHint = 0;
        this.mSurfaceFlags = 4;
        this.mSyncGroups = new ArraySet<>();
        this.mRtTransaction = new SurfaceControl.Transaction();
        this.mFrameCallbackTransaction = new SurfaceControl.Transaction();
        this.mRemoteAccessibilityController = new RemoteAccessibilityController(this);
        this.mTmpMatrix = new Matrix();
        this.mIsWindowOpaque = true;
        this.mEmbeddedWindowParams = new ConcurrentLinkedQueue<>();
        this.mSurfaceControlViewHostParent = new AnonymousClass1();
        this.mRtDrivenClipping = Flags.clipSurfaceviews();
        this.mRTLastReportedPosition = new Rect();
        this.mRTLastSetCrop = new Rect();
        this.mPositionListener = null;
        this.mSurfaceHolder = new AnonymousClass2();
        setWillNotDraw(true);
        this.mDisableBackgroundLayer = disableBackgroundLayer;
        this.mTag = "SurfaceView@" + Integer.toHexString(System.identityHashCode(this));
    }

    public SurfaceHolder getHolder() {
        return this.mSurfaceHolder;
    }

    private void updateRequestedVisibility() {
        this.mRequestedVisible =
                this.mViewVisibility && this.mWindowVisibility && !this.mWindowStopped;
    }

    private void setWindowStopped(boolean stopped) {
        this.mWindowStopped = stopped;
        updateRequestedVisibility();
        this.mUpdateSurfaceCalledBy = 1;
        ViewRootImpl viewRoot = getViewRootImpl();
        if (viewRoot != null) {
            Log.i(
                    this.mTag,
                    "windowStopped("
                            + stopped
                            + ") "
                            + this.mRequestedVisible
                            + " "
                            + this
                            + " of "
                            + viewRoot.getTag());
        }
        updateSurface();
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewRootImpl().addSurfaceChangedCallback(this);
        this.mWindowStopped = false;
        this.mViewVisibility = getVisibility() == 0;
        updateRequestedVisibility();
        this.mAttachedToWindow = true;
        this.mParent.requestTransparentRegion(this);
        if (!this.mGlobalListenersAdded) {
            ViewTreeObserver observer = getViewTreeObserver();
            observer.addOnScrollChangedListener(this.mScrollChangedListener);
            observer.addOnPreDrawListener(this.mDrawListener);
            this.mGlobalListenersAdded = true;
        }
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        this.mWindowVisibility = visibility == 0;
        updateRequestedVisibility();
        this.mUpdateSurfaceCalledBy = 2;
        ViewRootImpl viewRoot = getViewRootImpl();
        if (viewRoot != null) {
            Log.i(
                    this.mTag,
                    "onWindowVisibilityChanged("
                            + visibility
                            + ") "
                            + this.mRequestedVisible
                            + " "
                            + this
                            + " of "
                            + viewRoot.getTag());
        }
        updateSurface();
    }

    @Override // android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        this.mViewVisibility = visibility == 0;
        boolean newRequestedVisible =
                this.mWindowVisibility && this.mViewVisibility && !this.mWindowStopped;
        if (newRequestedVisible != this.mRequestedVisible) {
            requestLayout();
        }
        this.mRequestedVisible = newRequestedVisible;
        this.mUpdateSurfaceCalledBy = 3;
        updateSurface();
    }

    public void setUseAlpha() {}

    @Override // android.view.View
    public void setAlpha(float alpha) {
        if (DEBUG) {
            Log.d(TAG, System.identityHashCode(this) + " setAlpha: alpha=" + alpha);
        }
        super.setAlpha(alpha);
    }

    @Override // android.view.View
    protected boolean onSetAlpha(int alpha) {
        if (Math.round(this.mAlpha * 255.0f) != alpha) {
            updateSurface();
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performDrawFinished() {
        this.mDrawFinished = true;
        if (this.mAttachedToWindow) {
            this.mParent.requestTransparentRegion(this);
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        ViewRootImpl viewRoot = getViewRootImpl();
        if (viewRoot != null) {
            viewRoot.removeSurfaceChangedCallback(this);
        }
        this.mAttachedToWindow = false;
        if (this.mGlobalListenersAdded) {
            ViewTreeObserver observer = getViewTreeObserver();
            observer.removeOnScrollChangedListener(this.mScrollChangedListener);
            observer.removeOnPreDrawListener(this.mDrawListener);
            this.mGlobalListenersAdded = false;
        }
        if (DEBUG) {
            Log.i(TAG, System.identityHashCode(this) + " Detaching SV");
        }
        this.mRequestedVisible = false;
        this.mUpdateSurfaceCalledBy = 4;
        updateSurface();
        Log.i(this.mTag, "onDetachedFromWindow: tryReleaseSurfaces()");
        releaseSurfaces(true);
        this.mHaveFrame = false;
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        if (this.mRequestedWidth >= 0) {
            width = resolveSizeAndState(this.mRequestedWidth, widthMeasureSpec, 0);
        } else {
            width = getDefaultSize(0, widthMeasureSpec);
        }
        if (this.mRequestedHeight >= 0) {
            height = resolveSizeAndState(this.mRequestedHeight, heightMeasureSpec, 0);
        } else {
            height = getDefaultSize(0, heightMeasureSpec);
        }
        setMeasuredDimension(width, height);
    }

    @Override // android.view.View
    protected boolean setFrame(int left, int top, int right, int bottom) {
        boolean result = super.setFrame(left, top, right, bottom);
        this.mUpdateSurfaceCalledBy = 5;
        updateSurface();
        return result;
    }

    @Override // android.view.View
    public boolean gatherTransparentRegion(Region region) {
        if (isAboveParent() || !this.mDrawFinished) {
            return super.gatherTransparentRegion(region);
        }
        boolean opaque = true;
        if ((this.mPrivateFlags & 128) == 0) {
            opaque = super.gatherTransparentRegion(region);
        } else if (region != null) {
            int w = getWidth();
            int h = getHeight();
            if (w > 0 && h > 0) {
                getLocationInWindow(this.mLocation);
                int l = this.mLocation[0];
                int t = this.mLocation[1];
                region.op(l, t, l + w, t + h, Region.Op.UNION);
            }
        }
        if (PixelFormat.formatHasAlpha(this.mRequestedFormat)) {
            return false;
        }
        return opaque;
    }

    protected boolean gatherTransparentRegionWhenStartTaskView(Region region) {
        return super.gatherTransparentRegion(region);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (this.mDrawFinished && !isAboveParent() && (this.mPrivateFlags & 128) == 0) {
            clearSurfaceViewPort(canvas);
        }
        super.draw(canvas);
    }

    @Override // android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.mDrawFinished && !isAboveParent() && (this.mPrivateFlags & 128) == 128) {
            clearSurfaceViewPort(canvas);
        }
        super.dispatchDraw(canvas);
    }

    public void setEnableSurfaceClipping(boolean enabled) {
        this.mClipSurfaceToBounds = enabled;
        invalidate();
    }

    @Override // android.view.View
    public void setClipBounds(Rect clipBounds) {
        super.setClipBounds(clipBounds);
        if ((this.mRtDrivenClipping && isHardwareAccelerated())
                || !this.mClipSurfaceToBounds
                || this.mSurfaceControl == null) {
            return;
        }
        if (this.mCornerRadius > 0.0f && !isAboveParent()) {
            invalidate();
        }
        if (this.mClipBounds != null) {
            this.mTmpRect.set(this.mClipBounds);
        } else {
            this.mTmpRect.set(0, 0, this.mSurfaceWidth, this.mSurfaceHeight);
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.setWindowCrop(this.mSurfaceControl, this.mTmpRect);
        applyTransactionOnVriDraw(transaction);
        invalidate();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    private void clearSurfaceViewPort(Canvas canvas) {
        float alpha = getAlpha();
        if (this.mCornerRadius > 0.0f) {
            canvas.getClipBounds(this.mTmpRect);
            if (this.mClipSurfaceToBounds && this.mClipBounds != null) {
                this.mTmpRect.intersect(this.mClipBounds);
            }
            canvas.punchHole(
                    this.mTmpRect.left,
                    this.mTmpRect.top,
                    this.mTmpRect.right,
                    this.mTmpRect.bottom,
                    this.mCornerRadius,
                    this.mCornerRadius,
                    alpha);
            return;
        }
        canvas.punchHole(0.0f, 0.0f, getWidth(), getHeight(), 0.0f, 0.0f, alpha);
    }

    public void setCornerRadius(float cornerRadius) {
        this.mCornerRadius = cornerRadius;
        if (this.mCornerRadius > 0.0f && this.mRoundedViewportPaint == null) {
            this.mRoundedViewportPaint = new Paint(1);
            this.mRoundedViewportPaint.setBlendMode(BlendMode.CLEAR);
            this.mRoundedViewportPaint.setColor(0);
        }
        invalidate();
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public void setZOrderMediaOverlay(boolean isMediaOverlay) {
        this.mRequestedSubLayer = isMediaOverlay ? -1 : -2;
    }

    public void setZOrderOnTop(boolean onTop) {
        boolean allowDynamicChange = getContext().getApplicationInfo().targetSdkVersion > 29;
        setZOrderedOnTop(onTop, allowDynamicChange);
    }

    public boolean isZOrderedOnTop() {
        return this.mRequestedSubLayer > 0;
    }

    public boolean setZOrderedOnTop(boolean onTop, boolean allowDynamicChange) {
        int subLayer;
        if (onTop) {
            subLayer = 1;
        } else {
            subLayer = -2;
        }
        if (this.mRequestedSubLayer == subLayer) {
            return false;
        }
        this.mRequestedSubLayer = subLayer;
        if (!allowDynamicChange) {
            return false;
        }
        if (this.mSurfaceControl == null) {
            return true;
        }
        ViewRootImpl viewRoot = getViewRootImpl();
        if (viewRoot == null) {
            return true;
        }
        updateSurface();
        invalidate();
        return true;
    }

    public void setSecure(boolean isSecure) {
        if (isSecure) {
            this.mSurfaceFlags |= 128;
        } else {
            this.mSurfaceFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        }
    }

    public void setSurfaceLifecycle(int lifecycleStrategy) {
        this.mRequestedSurfaceLifecycleStrategy = lifecycleStrategy;
        updateSurface();
    }

    public void setDesiredHdrHeadroom(float desiredHeadroom) {
        if (!Float.isFinite(desiredHeadroom)) {
            throw new IllegalArgumentException(
                    "desiredHeadroom must be finite: " + desiredHeadroom);
        }
        if (desiredHeadroom != 0.0f && (desiredHeadroom < 1.0f || desiredHeadroom > 10000.0f)) {
            throw new IllegalArgumentException(
                    "desiredHeadroom must be 0.0 or in the range [1.0, 10000.0f], received: "
                            + desiredHeadroom);
        }
        this.mRequestedHdrHeadroom = desiredHeadroom;
        updateSurface();
        invalidate();
    }

    private void updateOpaqueFlag() {
        if (!PixelFormat.formatHasAlpha(this.mRequestedFormat)) {
            this.mSurfaceFlags |= 1024;
        } else {
            this.mSurfaceFlags &= -1025;
        }
    }

    private void updateBackgroundVisibility(SurfaceControl.Transaction t) {
        if (this.mBackgroundControl == null) {
            return;
        }
        if (this.mSubLayer < 0
                && (this.mSurfaceFlags & 1024) != 0
                && !this.mDisableBackgroundLayer) {
            if (!this.mIsWindowOpaque
                    && getResources().getConfiguration().windowConfiguration.getWindowingMode()
                            == 5) {
                t.hide(this.mBackgroundControl);
                return;
            } else {
                t.show(this.mBackgroundControl);
                return;
            }
        }
        t.hide(this.mBackgroundControl);
    }

    private SurfaceControl.Transaction updateBackgroundColor(SurfaceControl.Transaction t) {
        float[] colorComponents = {
            Color.red(this.mBackgroundColor) / 255.0f,
            Color.green(this.mBackgroundColor) / 255.0f,
            Color.blue(this.mBackgroundColor) / 255.0f
        };
        t.setColor(this.mBackgroundControl, colorComponents);
        return t;
    }

    private void releaseSurfaces(boolean releaseSurfacePackage) {
        this.mAlpha = 1.0f;
        this.mSurface.destroy();
        synchronized (this.mSurfaceControlLock) {
            if (this.mBlastBufferQueue != null) {
                this.mBlastBufferQueue.destroy();
                this.mBlastBufferQueue = null;
            }
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            ViewRootImpl viewRoot = getViewRootImpl();
            Log.i(
                    this.mTag,
                    "releaseSurfaces: viewRoot = "
                            + (viewRoot != null ? viewRoot.getTag() : "null"));
            if (this.mSurfaceControl != null) {
                transaction.remove(this.mSurfaceControl);
                this.mSurfaceControl = null;
            }
            if (this.mBackgroundControl != null) {
                transaction.remove(this.mBackgroundControl);
                this.mBackgroundControl = null;
            }
            if (this.mBlastSurfaceControl != null) {
                transaction.remove(this.mBlastSurfaceControl);
                this.mBlastSurfaceControl = null;
            }
            if (this.mSurfacePackage != null) {
                try {
                    this.mSurfacePackage.getRemoteInterface().attachParentInterface(null);
                    this.mEmbeddedWindowParams.clear();
                } catch (RemoteException e) {
                    Log.d(
                            TAG,
                            "Failed to remove parent interface from SCVH. Likely SCVH is already"
                                + " dead");
                }
                if (releaseSurfacePackage) {
                    this.mSurfacePackage.release();
                    this.mSurfacePackage = null;
                }
            }
            applyTransactionOnVriDraw(transaction);
        }
    }

    private void replacePositionUpdateListener(int surfaceWidth, int surfaceHeight) {
        if (this.mPositionListener != null) {
            this.mRenderNode.removePositionUpdateListener(this.mPositionListener);
        }
        this.mPositionListener = new SurfaceViewPositionUpdateListener(surfaceWidth, surfaceHeight);
        this.mRenderNode.addPositionUpdateListener(this.mPositionListener);
    }

    private boolean performSurfaceTransaction(
            ViewRootImpl viewRoot,
            CompatibilityInfo.Translator translator,
            boolean creating,
            boolean sizeChanged,
            boolean hintChanged,
            boolean relativeZChanged,
            boolean hdrHeadroomChanged,
            SurfaceControl.Transaction surfaceUpdateTransaction) {
        this.mSurfaceLock.lock();
        try {
            boolean z = true;
            this.mDrawingStopped = !surfaceShouldExist();
            if (DEBUG) {
                Log.i(TAG, System.identityHashCode(this) + " Cur surface: " + this.mSurface);
            }
            if (creating) {
                updateRelativeZ(surfaceUpdateTransaction);
                if (this.mSurfacePackage != null) {
                    reparentSurfacePackage(surfaceUpdateTransaction, this.mSurfacePackage);
                }
            }
            this.mParentSurfaceSequenceId = viewRoot.getSurfaceSequenceId();
            if (!isHardwareAccelerated()) {
                if (this.mViewVisibility) {
                    surfaceUpdateTransaction.show(this.mSurfaceControl);
                } else {
                    surfaceUpdateTransaction.hide(this.mSurfaceControl);
                }
            }
            updateBackgroundVisibility(surfaceUpdateTransaction);
            updateBackgroundColor(surfaceUpdateTransaction);
            if (this.mLimitedHdrEnabled && (hdrHeadroomChanged || creating)) {
                surfaceUpdateTransaction.setDesiredHdrHeadroom(
                        this.mBlastSurfaceControl, this.mHdrHeadroom);
            }
            if (isAboveParent()) {
                float alpha = getAlpha();
                surfaceUpdateTransaction.setAlpha(this.mSurfaceControl, alpha);
            }
            if (relativeZChanged) {
                if (!isAboveParent()) {
                    surfaceUpdateTransaction.setAlpha(this.mSurfaceControl, 1.0f);
                }
                updateRelativeZ(surfaceUpdateTransaction);
            }
            surfaceUpdateTransaction.setCornerRadius(this.mSurfaceControl, this.mCornerRadius);
            if ((sizeChanged || hintChanged) && !creating) {
                setBufferSize(surfaceUpdateTransaction);
            }
            if (sizeChanged || creating || !isHardwareAccelerated()) {
                if (!this.mRtDrivenClipping || !isHardwareAccelerated()) {
                    if (!this.mClipSurfaceToBounds || this.mClipBounds == null) {
                        surfaceUpdateTransaction.setWindowCrop(
                                this.mSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight);
                    } else {
                        surfaceUpdateTransaction.setWindowCrop(
                                this.mSurfaceControl, this.mClipBounds);
                    }
                    Log.i(
                            this.mTag,
                            "pST: sr = "
                                    + this.mScreenRect
                                    + " sw = "
                                    + this.mSurfaceWidth
                                    + " sh = "
                                    + this.mSurfaceHeight);
                }
                surfaceUpdateTransaction.setDesintationFrame(
                        this.mBlastSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight);
                if (isHardwareAccelerated()) {
                    replacePositionUpdateListener(this.mSurfaceWidth, this.mSurfaceHeight);
                } else {
                    onSetSurfacePositionAndScale(
                            surfaceUpdateTransaction,
                            this.mSurfaceControl,
                            this.mScreenRect.left,
                            this.mScreenRect.top,
                            this.mScreenRect.width() / this.mSurfaceWidth,
                            this.mScreenRect.height() / this.mSurfaceHeight);
                }
                if (DEBUG_POSITION) {
                    Log.d(
                            TAG,
                            TextUtils.formatSimple(
                                    "%d performSurfaceTransaction %s position = [%d, %d, %d, %d]"
                                        + " surfaceSize = %dx%d",
                                    Integer.valueOf(System.identityHashCode(this)),
                                    isHardwareAccelerated() ? "RenderWorker" : "UI Thread",
                                    Integer.valueOf(this.mScreenRect.left),
                                    Integer.valueOf(this.mScreenRect.top),
                                    Integer.valueOf(this.mScreenRect.right),
                                    Integer.valueOf(this.mScreenRect.bottom),
                                    Integer.valueOf(this.mSurfaceWidth),
                                    Integer.valueOf(this.mSurfaceHeight)));
                }
            }
            applyTransactionOnVriDraw(surfaceUpdateTransaction);
            updateEmbeddedAccessibilityMatrix(false);
            this.mSurfaceFrame.left = 0;
            this.mSurfaceFrame.top = 0;
            if (translator == null) {
                this.mSurfaceFrame.right = this.mSurfaceWidth;
                this.mSurfaceFrame.bottom = this.mSurfaceHeight;
            } else {
                float appInvertedScale = translator.applicationInvertedScale;
                this.mSurfaceFrame.right = (int) ((this.mSurfaceWidth * appInvertedScale) + 0.5f);
                this.mSurfaceFrame.bottom = (int) ((this.mSurfaceHeight * appInvertedScale) + 0.5f);
            }
            int surfaceWidth = this.mSurfaceFrame.right;
            int surfaceHeight = this.mSurfaceFrame.bottom;
            if (this.mLastSurfaceWidth == surfaceWidth
                    && this.mLastSurfaceHeight == surfaceHeight) {
                z = false;
            }
            boolean realSizeChanged = z;
            this.mLastSurfaceWidth = surfaceWidth;
            this.mLastSurfaceHeight = surfaceHeight;
            return realSizeChanged;
        } finally {
            this.mSurfaceLock.unlock();
        }
    }

    private boolean requiresSurfaceControlCreation(boolean formatChanged, boolean visibleChanged) {
        return this.mSurfaceLifecycleStrategy == 2
                ? (this.mSurfaceControl == null || formatChanged) && this.mAttachedToWindow
                : (this.mSurfaceControl == null || formatChanged || visibleChanged)
                        && this.mRequestedVisible;
    }

    private boolean surfaceShouldExist() {
        boolean respectVisibility = this.mSurfaceLifecycleStrategy != 2;
        if (this.mVisible) {
            return true;
        }
        return !respectVisibility && this.mAttachedToWindow;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(68:19|(1:21)|22|(1:24)|25|(1:27)|28|(1:30)(1:358)|31|(1:33)(1:357)|34|(1:36)(1:356)|37|(1:355)(1:41)|42|(1:44)(1:354)|45|(1:353)(1:49)|50|(1:352)(1:54)|55|(1:351)(1:59)|60|(1:62)(1:350)|63|(1:65)(1:349)|66|(1:68)(1:348)|69|(1:71)(1:347)|72|(1:74)|(1:346)(2:77|(2:91|92))|93|(5:95|(1:97)(1:344)|98|(1:100)(1:343)|101)(1:345)|102|103|(5:329|330|331|332|333)(1:105)|106|107|108|109|110|111|112|113|114|(2:319|320)(2:116|(1:118))|(11:(29:129|130|(2:134|(24:136|137|(9:295|296|297|298|299|300|301|302|303)(1:139)|140|141|142|143|144|145|(1:147)(1:287)|(1:149)(1:286)|(1:285)(1:152)|153|(2:(2:(2:162|163)|(2:158|(1:(0))))|173)|175|176|177|(1:281)(14:181|182|(1:(9:266|267|268|(1:270)(1:277)|271|(2:273|274)|276|(8:231|(11:244|245|246|247|248|249|250|251|252|253|254)(1:233)|(2:235|236)|237|238|239|(2:241|242)|243)(1:196)|(6:198|(1:200)|(2:202|203)|207|(2:209|210)(1:225)|211)(1:230))(1:187))(1:280)|188|(0)|231|(0)(0)|(0)|237|238|239|(0)|243|(0)(0))|212|213|(1:217)|218|219|(2:221|222)(1:223)))|316|137|(0)(0)|140|141|142|143|144|145|(0)(0)|(0)(0)|(0)|285|153|(0)|175|176|177|(1:179)|281|212|213|(2:215|217)|218|219|(0)(0))|176|177|(0)|281|212|213|(0)|218|219|(0)(0))|317|130|(3:132|134|(0))|316|137|(0)(0)|140|141|142|143|144|145|(0)(0)|(0)(0)|(0)|285|153|(0)|175) */
    /* JADX WARN: Can't wrap try/catch for region: R(78:19|(1:21)|22|(1:24)|25|(1:27)|28|(1:30)(1:358)|31|(1:33)(1:357)|34|(1:36)(1:356)|37|(1:355)(1:41)|42|(1:44)(1:354)|45|(1:353)(1:49)|50|(1:352)(1:54)|55|(1:351)(1:59)|60|(1:62)(1:350)|63|(1:65)(1:349)|66|(1:68)(1:348)|69|(1:71)(1:347)|72|(1:74)|(1:346)(2:77|(2:91|92))|93|(5:95|(1:97)(1:344)|98|(1:100)(1:343)|101)(1:345)|102|103|(5:329|330|331|332|333)(1:105)|106|107|108|109|110|111|112|113|114|(2:319|320)(2:116|(1:118))|(29:129|130|(2:134|(24:136|137|(9:295|296|297|298|299|300|301|302|303)(1:139)|140|141|142|143|144|145|(1:147)(1:287)|(1:149)(1:286)|(1:285)(1:152)|153|(2:(2:(2:162|163)|(2:158|(1:(0))))|173)|175|176|177|(1:281)(14:181|182|(1:(9:266|267|268|(1:270)(1:277)|271|(2:273|274)|276|(8:231|(11:244|245|246|247|248|249|250|251|252|253|254)(1:233)|(2:235|236)|237|238|239|(2:241|242)|243)(1:196)|(6:198|(1:200)|(2:202|203)|207|(2:209|210)(1:225)|211)(1:230))(1:187))(1:280)|188|(0)|231|(0)(0)|(0)|237|238|239|(0)|243|(0)(0))|212|213|(1:217)|218|219|(2:221|222)(1:223)))|316|137|(0)(0)|140|141|142|143|144|145|(0)(0)|(0)(0)|(0)|285|153|(0)|175|176|177|(1:179)|281|212|213|(2:215|217)|218|219|(0)(0))|317|130|(3:132|134|(0))|316|137|(0)(0)|140|141|142|143|144|145|(0)(0)|(0)(0)|(0)|285|153|(0)|175|176|177|(0)|281|212|213|(0)|218|219|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0441, code lost:

       if (r6 != false) goto L204;
    */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0437, code lost:

       if (r43.mAttachedToWindow != false) goto L199;
    */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x06ce, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x06d1, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x06d2, code lost:

       r5 = " h=";
       r3 = " w=";
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0359  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x03da  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x041e  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0423  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0428 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0431  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x06c4 A[Catch: Exception -> 0x06ce, TryCatch #2 {Exception -> 0x06ce, blocks: (B:167:0x06bd, B:169:0x06c4, B:171:0x06c8, B:172:0x06cd, B:213:0x0694, B:215:0x069a, B:217:0x069e), top: B:144:0x0419 }] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x048f A[Catch: all -> 0x06a4, TryCatch #5 {all -> 0x06a4, blocks: (B:177:0x045b, B:179:0x048f, B:181:0x0497, B:231:0x0583), top: B:176:0x045b }] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0643 A[Catch: all -> 0x067b, TRY_ENTER, TRY_LEAVE, TryCatch #21 {all -> 0x067b, blocks: (B:198:0x0643, B:238:0x05e8), top: B:237:0x05e8 }] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x069a A[Catch: Exception -> 0x06ce, TryCatch #2 {Exception -> 0x06ce, blocks: (B:167:0x06bd, B:169:0x06c4, B:171:0x06c8, B:172:0x06cd, B:213:0x0694, B:215:0x069a, B:217:0x069e), top: B:144:0x0419 }] */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0761  */
    /* JADX WARN: Removed duplicated region for block: B:223:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0678  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x05d3  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x05dd A[Catch: all -> 0x05e3, TRY_LEAVE, TryCatch #4 {all -> 0x05e3, blocks: (B:200:0x0647, B:202:0x0663, B:254:0x05b4, B:235:0x05dd, B:241:0x0635), top: B:253:0x05b4 }] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0635 A[Catch: all -> 0x05e3, TRY_ENTER, TRY_LEAVE, TryCatch #4 {all -> 0x05e3, blocks: (B:200:0x0647, B:202:0x0663, B:254:0x05b4, B:235:0x05dd, B:241:0x0635), top: B:253:0x05b4 }] */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0587 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:286:0x0425  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x0420  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0362 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r3v25, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v19, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void updateSurface() {
        /*
            Method dump skipped, instructions count: 1988
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.view.SurfaceView.updateSurface():void");
    }

    public String getName() {
        ViewRootImpl viewRoot = getViewRootImpl();
        String viewRootName = viewRoot == null ? "detached" : viewRoot.getTitle().toString();
        return "SurfaceView[" + viewRootName + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private void handleSyncBufferCallback(
            SurfaceHolder.Callback[] callbacks,
            final SyncBufferTransactionCallback syncBufferTransactionCallback) {
        final SurfaceSyncGroup surfaceSyncGroup = new SurfaceSyncGroup(getName());
        getViewRootImpl().addToSync(surfaceSyncGroup);
        redrawNeededAsync(
                callbacks,
                new Runnable() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceView.this.lambda$handleSyncBufferCallback$1(
                                syncBufferTransactionCallback, surfaceSyncGroup);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSyncBufferCallback$1(
            SyncBufferTransactionCallback syncBufferTransactionCallback,
            SurfaceSyncGroup surfaceSyncGroup) {
        SurfaceControl.Transaction t = null;
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.stopContinuousSyncTransaction();
            t = syncBufferTransactionCallback.waitForTransaction();
        }
        surfaceSyncGroup.addTransaction(t);
        surfaceSyncGroup.markSyncReady();
        onDrawFinished();
    }

    private void handleSyncNoBuffer(SurfaceHolder.Callback[] callbacks) {
        final SurfaceSyncGroup surfaceSyncGroup = new SurfaceSyncGroup(getName());
        synchronized (this.mSyncGroups) {
            this.mSyncGroups.add(surfaceSyncGroup);
        }
        redrawNeededAsync(
                callbacks,
                new Runnable() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceView.this.lambda$handleSyncNoBuffer$2(surfaceSyncGroup);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSyncNoBuffer$2(SurfaceSyncGroup surfaceSyncGroup) {
        synchronized (this.mSyncGroups) {
            this.mSyncGroups.remove(surfaceSyncGroup);
        }
        surfaceSyncGroup.markSyncReady();
        onDrawFinished();
    }

    private void redrawNeededAsync(
            SurfaceHolder.Callback[] callbacks, Runnable callbacksCollected) {
        SurfaceCallbackHelper sch = new SurfaceCallbackHelper(callbacksCollected);
        sch.dispatchSurfaceRedrawNeededAsync(this.mSurfaceHolder, callbacks);
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void vriDrawStarted(boolean isWmSync) {
        ViewRootImpl viewRoot = getViewRootImpl();
        synchronized (this.mSyncGroups) {
            if (isWmSync && viewRoot != null) {
                Iterator<SurfaceSyncGroup> it = this.mSyncGroups.iterator();
                while (it.hasNext()) {
                    SurfaceSyncGroup syncGroup = it.next();
                    viewRoot.addToSync(syncGroup);
                }
            }
            this.mSyncGroups.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SyncBufferTransactionCallback {
        private final CountDownLatch mCountDownLatch;
        private SurfaceControl.Transaction mTransaction;

        private SyncBufferTransactionCallback() {
            this.mCountDownLatch = new CountDownLatch(1);
        }

        SurfaceControl.Transaction waitForTransaction() {
            try {
                this.mCountDownLatch.await();
            } catch (InterruptedException e) {
            }
            return this.mTransaction;
        }

        void onTransactionReady(SurfaceControl.Transaction t) {
            this.mTransaction = t;
            this.mCountDownLatch.countDown();
        }
    }

    private void copySurface(boolean surfaceControlCreated, boolean bufferSizeChanged) {
        if (surfaceControlCreated) {
            this.mSurface.copyFrom(this.mBlastBufferQueue);
        }
        if (bufferSizeChanged
                && getContext().getApplicationInfo().targetSdkVersion < 26
                && this.mBlastBufferQueue != null) {
            this.mSurface.transferFrom(this.mBlastBufferQueue.createSurfaceWithHandle());
        }
    }

    private void setBufferSize(SurfaceControl.Transaction transaction) {
        this.mBlastSurfaceControl.setTransformHint(this.mTransformHint);
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.update(
                    this.mBlastSurfaceControl,
                    this.mSurfaceWidth,
                    this.mSurfaceHeight,
                    this.mFormat);
        }
    }

    private void createBlastSurfaceControls(
            ViewRootImpl viewRoot,
            String name,
            SurfaceControl.Transaction surfaceUpdateTransaction) {
        if (this.mSurfaceControl == null) {
            this.mSurfaceControl =
                    new SurfaceControl.Builder(this.mSurfaceSession)
                            .setName(name)
                            .setLocalOwnerView(this)
                            .setParent(viewRoot.updateAndGetBoundsLayer(surfaceUpdateTransaction))
                            .setCallsite("SurfaceView.updateSurface")
                            .setContainerLayer()
                            .build();
        }
        if (this.mBlastSurfaceControl == null) {
            this.mBlastSurfaceControl =
                    new SurfaceControl.Builder(this.mSurfaceSession)
                            .setName(name + "(BLAST)")
                            .setLocalOwnerView(this)
                            .setParent(this.mSurfaceControl)
                            .setFlags(this.mSurfaceFlags)
                            .setHidden(false)
                            .setBLASTLayer()
                            .setCallsite("SurfaceView.updateSurface")
                            .build();
        } else {
            surfaceUpdateTransaction
                    .setOpaque(this.mBlastSurfaceControl, (this.mSurfaceFlags & 1024) != 0)
                    .setSecure(this.mBlastSurfaceControl, (this.mSurfaceFlags & 128) != 0)
                    .show(this.mBlastSurfaceControl);
        }
        if (this.mBackgroundControl == null) {
            this.mBackgroundControl =
                    new SurfaceControl.Builder(this.mSurfaceSession)
                            .setName("Background for " + name)
                            .setLocalOwnerView(this)
                            .setOpaque(true)
                            .setColorLayer()
                            .setParent(this.mSurfaceControl)
                            .setCallsite("SurfaceView.updateSurface")
                            .build();
        }
        if (this.mBlastBufferQueue != null) {
            this.mBlastBufferQueue.destroy();
        }
        this.mTransformHint = viewRoot.getBufferTransformHint();
        this.mBlastSurfaceControl.setTransformHint(this.mTransformHint);
        this.mBlastBufferQueue = new BLASTBufferQueue(name, false);
        this.mBlastBufferQueue.update(
                this.mBlastSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight, this.mFormat);
        this.mBlastBufferQueue.setTransactionHangCallback(ViewRootImpl.sTransactionHangCallback);
    }

    private void onDrawFinished() {
        if (DEBUG) {
            Log.i(TAG, System.identityHashCode(this) + " finishedDrawing");
        }
        runOnUiThread(
                new Runnable() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceView.this.performDrawFinished();
                    }
                });
    }

    protected void onSetSurfacePositionAndScale(
            SurfaceControl.Transaction transaction,
            SurfaceControl surface,
            int positionLeft,
            int positionTop,
            float postScaleX,
            float postScaleY) {
        Log.i(
                this.mTag,
                "onSSPAndSRT: pl = "
                        + positionLeft
                        + " pt = "
                        + positionTop
                        + " sx = "
                        + postScaleX
                        + " sy = "
                        + postScaleY);
        transaction.setPosition(surface, positionLeft, positionTop);
        transaction.setMatrix(surface, postScaleX, 0.0f, 0.0f, postScaleY);
    }

    public void requestUpdateSurfacePositionAndScale() {
        if (this.mSurfaceControl == null) {
            return;
        }
        Log.i(
                this.mTag,
                "rUSPAndS: sr = "
                        + this.mScreenRect
                        + " sw = "
                        + this.mSurfaceWidth
                        + " sh = "
                        + this.mSurfaceHeight);
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        onSetSurfacePositionAndScale(
                transaction,
                this.mSurfaceControl,
                this.mScreenRect.left,
                this.mScreenRect.top,
                this.mScreenRect.width() / this.mSurfaceWidth,
                this.mScreenRect.height() / this.mSurfaceHeight);
        applyTransactionOnVriDraw(transaction);
        invalidate();
    }

    public Rect getSurfaceRenderPosition() {
        return this.mRTLastReportedPosition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyOrMergeTransaction(SurfaceControl.Transaction t, long frameNumber) {
        ViewRootImpl viewRoot = getViewRootImpl();
        if (viewRoot != null) {
            Log.i(
                    this.mTag,
                    "aOrMT: "
                            + viewRoot.getTag()
                            + " t = "
                            + t
                            + " fN = "
                            + frameNumber
                            + " "
                            + Debug.getCallers(3));
            viewRoot.mergeWithNextTransaction(t, frameNumber);
        } else {
            Log.i(this.mTag, "aOrMT: t.apply");
            t.apply();
        }
    }

    public void semResetRenderNodePosition() {}

    private class SurfaceViewPositionUpdateListener implements RenderNode.PositionUpdateListener {
        private final SurfaceControl.Transaction mPositionChangedTransaction =
                new SurfaceControl.Transaction();
        private final int mRtSurfaceHeight;
        private final int mRtSurfaceWidth;

        SurfaceViewPositionUpdateListener(int surfaceWidth, int surfaceHeight) {
            this.mRtSurfaceWidth = surfaceWidth;
            this.mRtSurfaceHeight = surfaceHeight;
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(long frameNumber, int left, int top, int right, int bottom) {
            try {
                if (SurfaceView.DEBUG_POSITION) {
                    Log.d(
                            SurfaceView.TAG,
                            String.format(
                                    "%d updateSurfacePosition RenderWorker, frameNr = %d, position"
                                        + " = [%d, %d, %d, %d] surfaceSize = %dx%d",
                                    Integer.valueOf(System.identityHashCode(SurfaceView.this)),
                                    Long.valueOf(frameNumber),
                                    Integer.valueOf(left),
                                    Integer.valueOf(top),
                                    Integer.valueOf(right),
                                    Integer.valueOf(bottom),
                                    Integer.valueOf(this.mRtSurfaceWidth),
                                    Integer.valueOf(this.mRtSurfaceHeight)));
                }
                try {
                    synchronized (SurfaceView.this.mSurfaceControlLock) {
                        try {
                            if (SurfaceView.this.mSurfaceControl == null) {
                                return;
                            }
                            try {
                                SurfaceView.this.mRTLastReportedPosition.set(
                                        left, top, right, bottom);
                                Log.i(
                                        SurfaceView.this.mTag,
                                        "uSP: rtp = "
                                                + SurfaceView.this.mRTLastReportedPosition
                                                + " rtsw = "
                                                + this.mRtSurfaceWidth
                                                + " rtsh = "
                                                + this.mRtSurfaceHeight);
                                SurfaceView.this.onSetSurfacePositionAndScale(
                                        this.mPositionChangedTransaction,
                                        SurfaceView.this.mSurfaceControl,
                                        SurfaceView.this.mRTLastReportedPosition.left,
                                        SurfaceView.this.mRTLastReportedPosition.top,
                                        SurfaceView.this.mRTLastReportedPosition.width()
                                                / this.mRtSurfaceWidth,
                                        SurfaceView.this.mRTLastReportedPosition.height()
                                                / this.mRtSurfaceHeight);
                                this.mPositionChangedTransaction.show(
                                        SurfaceView.this.mSurfaceControl);
                                try {
                                    SurfaceView.this.applyOrMergeTransaction(
                                            this.mPositionChangedTransaction, frameNumber);
                                } catch (Exception e) {
                                    ex = e;
                                    Log.e(SurfaceView.TAG, "Exception from repositionChild", ex);
                                }
                            } catch (Throwable th) {
                                th = th;
                                while (true) {
                                    try {
                                        throw th;
                                    } catch (Throwable th2) {
                                        th = th2;
                                    }
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    }
                } catch (Exception e2) {
                    ex = e2;
                }
            } catch (Exception e3) {
                ex = e3;
            }
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(
                long frameNumber,
                int left,
                int top,
                int right,
                int bottom,
                int clipLeft,
                int clipTop,
                int clipRight,
                int clipBottom) {
            try {
                if (SurfaceView.DEBUG_POSITION) {
                    Log.d(
                            SurfaceView.TAG,
                            String.format(
                                    "%d updateSurfacePosition RenderWorker, frameNr = %d, position"
                                        + " = [%d, %d, %d, %d] clip = [%d, %d, %d, %d] surfaceSize"
                                        + " = %dx%d",
                                    Integer.valueOf(System.identityHashCode(SurfaceView.this)),
                                    Long.valueOf(frameNumber),
                                    Integer.valueOf(left),
                                    Integer.valueOf(top),
                                    Integer.valueOf(right),
                                    Integer.valueOf(bottom),
                                    Integer.valueOf(clipLeft),
                                    Integer.valueOf(clipTop),
                                    Integer.valueOf(clipRight),
                                    Integer.valueOf(clipBottom),
                                    Integer.valueOf(this.mRtSurfaceWidth),
                                    Integer.valueOf(this.mRtSurfaceHeight)));
                }
                try {
                    synchronized (SurfaceView.this.mSurfaceControlLock) {
                        try {
                            if (SurfaceView.this.mSurfaceControl == null) {
                                return;
                            }
                            try {
                                SurfaceView.this.mRTLastReportedPosition.set(
                                        left, top, right, bottom);
                                float postScaleX =
                                        SurfaceView.this.mRTLastReportedPosition.width()
                                                / this.mRtSurfaceWidth;
                                float postScaleY =
                                        SurfaceView.this.mRTLastReportedPosition.height()
                                                / this.mRtSurfaceHeight;
                                SurfaceView.this.onSetSurfacePositionAndScale(
                                        this.mPositionChangedTransaction,
                                        SurfaceView.this.mSurfaceControl,
                                        SurfaceView.this.mRTLastReportedPosition.left,
                                        SurfaceView.this.mRTLastReportedPosition.top,
                                        postScaleX,
                                        postScaleY);
                                try {
                                } catch (Throwable th) {
                                    th = th;
                                    while (true) {
                                        try {
                                            throw th;
                                        } catch (Throwable th2) {
                                            th = th2;
                                        }
                                    }
                                }
                                try {
                                    SurfaceView.this.mRTLastSetCrop.set(
                                            (int) (clipLeft / postScaleX),
                                            (int) (clipTop / postScaleY),
                                            (int) Math.ceil(clipRight / postScaleX),
                                            (int) Math.ceil(clipBottom / postScaleY));
                                    if (SurfaceView.DEBUG_POSITION) {
                                        Log.d(
                                                SurfaceView.TAG,
                                                String.format(
                                                        "Setting layer crop = [%d, %d, %d, %d] from"
                                                            + " scale %f, %f",
                                                        Integer.valueOf(
                                                                SurfaceView.this
                                                                        .mRTLastSetCrop
                                                                        .left),
                                                        Integer.valueOf(
                                                                SurfaceView.this
                                                                        .mRTLastSetCrop
                                                                        .top),
                                                        Integer.valueOf(
                                                                SurfaceView.this
                                                                        .mRTLastSetCrop
                                                                        .right),
                                                        Integer.valueOf(
                                                                SurfaceView.this
                                                                        .mRTLastSetCrop
                                                                        .bottom),
                                                        Float.valueOf(postScaleX),
                                                        Float.valueOf(postScaleY)));
                                    }
                                    this.mPositionChangedTransaction.setCrop(
                                            SurfaceView.this.mSurfaceControl,
                                            SurfaceView.this.mRTLastSetCrop);
                                    if (SurfaceView.this.mRTLastSetCrop.isEmpty()) {
                                        this.mPositionChangedTransaction.hide(
                                                SurfaceView.this.mSurfaceControl);
                                    } else {
                                        this.mPositionChangedTransaction.show(
                                                SurfaceView.this.mSurfaceControl);
                                    }
                                    try {
                                        SurfaceView.this.applyOrMergeTransaction(
                                                this.mPositionChangedTransaction, frameNumber);
                                    } catch (Exception e) {
                                        ex = e;
                                        Log.e(
                                                SurfaceView.TAG,
                                                "Exception from repositionChild",
                                                ex);
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    while (true) {
                                        throw th;
                                    }
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                while (true) {
                                    throw th;
                                }
                            }
                        } catch (Throwable th5) {
                            th = th5;
                        }
                    }
                } catch (Exception e2) {
                    ex = e2;
                }
            } catch (Exception e3) {
                ex = e3;
            }
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void applyStretch(
                long frameNumber,
                float width,
                float height,
                float vecX,
                float vecY,
                float maxStretchX,
                float maxStretchY,
                float childRelativeLeft,
                float childRelativeTop,
                float childRelativeRight,
                float childRelativeBottom) {
            SurfaceView.this.mRtTransaction.setStretchEffect(
                    SurfaceView.this.mSurfaceControl,
                    width,
                    height,
                    vecX,
                    vecY,
                    maxStretchX,
                    maxStretchY,
                    childRelativeLeft,
                    childRelativeTop,
                    childRelativeRight,
                    childRelativeBottom);
            SurfaceView.this.applyOrMergeTransaction(SurfaceView.this.mRtTransaction, frameNumber);
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionLost(long frameNumber) {
            if (SurfaceView.DEBUG_POSITION) {
                Log.d(
                        SurfaceView.TAG,
                        String.format(
                                "%d windowPositionLost, frameNr = %d",
                                Integer.valueOf(System.identityHashCode(this)),
                                Long.valueOf(frameNumber)));
            }
            SurfaceView.this.mRTLastReportedPosition.setEmpty();
            synchronized (SurfaceView.this.mSurfaceControlLock) {
                if (SurfaceView.this.mSurfaceControl == null) {
                    return;
                }
                SurfaceView.this.mRtTransaction.hide(SurfaceView.this.mSurfaceControl);
                SurfaceView.this.applyOrMergeTransaction(
                        SurfaceView.this.mRtTransaction, frameNumber);
            }
        }
    }

    private SurfaceHolder.Callback[] getSurfaceCallbacks() {
        SurfaceHolder.Callback[] callbacks;
        synchronized (this.mCallbacks) {
            callbacks = new SurfaceHolder.Callback[this.mCallbacks.size()];
            this.mCallbacks.toArray(callbacks);
        }
        return callbacks;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runOnUiThread(Runnable runnable) {
        Handler handler = getHandler();
        if (handler != null && handler.getLooper() != Looper.myLooper()) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public boolean isFixedSize() {
        return (this.mRequestedWidth == -1 && this.mRequestedHeight == -1) ? false : true;
    }

    private boolean isAboveParent() {
        return this.mSubLayer >= 0;
    }

    public void setResizeBackgroundColor(int bgColor) {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        setResizeBackgroundColor(transaction, bgColor);
        applyTransactionOnVriDraw(transaction);
        invalidate();
    }

    public void setResizeBackgroundColor(SurfaceControl.Transaction t, int bgColor) {
        if (this.mBackgroundControl == null) {
            return;
        }
        this.mBackgroundColor = bgColor;
        updateBackgroundColor(t);
    }

    /* renamed from: android.view.SurfaceView$2, reason: invalid class name */
    class AnonymousClass2 implements SurfaceHolder {
        private static final String LOG_TAG = "SurfaceHolder";

        AnonymousClass2() {}

        @Override // android.view.SurfaceHolder
        public boolean isCreating() {
            return SurfaceView.this.mIsCreating;
        }

        @Override // android.view.SurfaceHolder
        public void addCallback(SurfaceHolder.Callback callback) {
            synchronized (SurfaceView.this.mCallbacks) {
                if (!SurfaceView.this.mCallbacks.contains(callback)) {
                    SurfaceView.this.mCallbacks.add(callback);
                }
            }
        }

        @Override // android.view.SurfaceHolder
        public void removeCallback(SurfaceHolder.Callback callback) {
            synchronized (SurfaceView.this.mCallbacks) {
                SurfaceView.this.mCallbacks.remove(callback);
            }
        }

        @Override // android.view.SurfaceHolder
        public void setFixedSize(int width, int height) {
            if (SurfaceView.this.mRequestedWidth != width
                    || SurfaceView.this.mRequestedHeight != height) {
                if (SurfaceView.DEBUG_POSITION) {
                    Log.d(
                            SurfaceView.TAG,
                            String.format(
                                    "%d setFixedSize %dx%d -> %dx%d",
                                    Integer.valueOf(System.identityHashCode(this)),
                                    Integer.valueOf(SurfaceView.this.mRequestedWidth),
                                    Integer.valueOf(SurfaceView.this.mRequestedHeight),
                                    Integer.valueOf(width),
                                    Integer.valueOf(height)));
                }
                SurfaceView.this.mRequestedWidth = width;
                SurfaceView.this.mRequestedHeight = height;
                SurfaceView.this.requestLayout();
            }
        }

        @Override // android.view.SurfaceHolder
        public void setSizeFromLayout() {
            if (SurfaceView.this.mRequestedWidth != -1 || SurfaceView.this.mRequestedHeight != -1) {
                if (SurfaceView.DEBUG_POSITION) {
                    Log.d(
                            SurfaceView.TAG,
                            String.format(
                                    "%d setSizeFromLayout was %dx%d",
                                    Integer.valueOf(System.identityHashCode(this)),
                                    Integer.valueOf(SurfaceView.this.mRequestedWidth),
                                    Integer.valueOf(SurfaceView.this.mRequestedHeight)));
                }
                SurfaceView surfaceView = SurfaceView.this;
                SurfaceView.this.mRequestedHeight = -1;
                surfaceView.mRequestedWidth = -1;
                SurfaceView.this.requestLayout();
            }
        }

        @Override // android.view.SurfaceHolder
        public void setFormat(int format) {
            if (format == -1) {
                format = 4;
            }
            SurfaceView.this.mRequestedFormat = format;
            if (SurfaceView.this.mSurfaceControl != null) {
                SurfaceView.this.mUpdateSurfaceCalledBy = 6;
                SurfaceView.this.updateSurface();
            }
        }

        @Override // android.view.SurfaceHolder
        @Deprecated
        public void setType(int type) {}

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setKeepScreenOn$0(boolean screenOn) {
            SurfaceView.this.setKeepScreenOn(screenOn);
        }

        @Override // android.view.SurfaceHolder
        public void setKeepScreenOn(final boolean screenOn) {
            SurfaceView.this.runOnUiThread(
                    new Runnable() { // from class:
                                     // android.view.SurfaceView$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SurfaceView.AnonymousClass2.this.lambda$setKeepScreenOn$0(screenOn);
                        }
                    });
        }

        @Override // android.view.SurfaceHolder
        public Canvas lockCanvas() {
            return internalLockCanvas(null, false);
        }

        @Override // android.view.SurfaceHolder
        public Canvas lockCanvas(Rect inOutDirty) {
            return internalLockCanvas(inOutDirty, false);
        }

        @Override // android.view.SurfaceHolder
        public Canvas lockHardwareCanvas() {
            return internalLockCanvas(null, true);
        }

        private Canvas internalLockCanvas(Rect dirty, boolean hardware) {
            SurfaceView.this.mSurfaceLock.lock();
            if (SurfaceView.DEBUG) {
                Log.i(
                        SurfaceView.TAG,
                        System.identityHashCode(this)
                                + " Locking canvas... stopped="
                                + SurfaceView.this.mDrawingStopped
                                + ", surfaceControl="
                                + SurfaceView.this.mSurfaceControl);
            }
            Canvas c = null;
            if (!SurfaceView.this.mDrawingStopped && SurfaceView.this.mSurfaceControl != null) {
                try {
                    if (hardware) {
                        c = SurfaceView.this.mSurface.lockHardwareCanvas();
                    } else {
                        c = SurfaceView.this.mSurface.lockCanvas(dirty);
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Exception locking surface", e);
                }
            }
            if (SurfaceView.DEBUG) {
                Log.i(SurfaceView.TAG, System.identityHashCode(this) + " Returned canvas: " + c);
            }
            if (c != null) {
                SurfaceView.this.mLastLockTime = SystemClock.uptimeMillis();
                return c;
            }
            long now = SystemClock.uptimeMillis();
            long nextTime =
                    SurfaceView.this.mLastLockTime + SurfaceView.FORWARD_BACK_KEY_TOLERANCE_MS;
            if (nextTime > now) {
                try {
                    Thread.sleep(nextTime - now);
                } catch (InterruptedException e2) {
                }
                now = SystemClock.uptimeMillis();
            }
            SurfaceView.this.mLastLockTime = now;
            SurfaceView.this.mSurfaceLock.unlock();
            return null;
        }

        @Override // android.view.SurfaceHolder
        public void unlockCanvasAndPost(Canvas canvas) {
            try {
                SurfaceView.this.mSurface.unlockCanvasAndPost(canvas);
            } finally {
                SurfaceView.this.mSurfaceLock.unlock();
            }
        }

        @Override // android.view.SurfaceHolder
        public Surface getSurface() {
            return SurfaceView.this.mSurface;
        }

        @Override // android.view.SurfaceHolder
        public Rect getSurfaceFrame() {
            return SurfaceView.this.mSurfaceFrame;
        }
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    @Deprecated
    public IBinder getHostToken() {
        ViewRootImpl viewRoot = getViewRootImpl();
        if (viewRoot == null) {
            return null;
        }
        return viewRoot.getInputToken();
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void surfaceCreated(SurfaceControl.Transaction t) {
        setWindowStopped(false);
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void surfaceDestroyed() {
        setWindowStopped(true);
        this.mRemoteAccessibilityController.disassosciateHierarchy();
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void surfaceReplaced(SurfaceControl.Transaction t) {
        if (this.mSurfaceControl != null && this.mBackgroundControl != null) {
            updateRelativeZ(t);
        }
    }

    private void updateRelativeZ(SurfaceControl.Transaction t) {
        ViewRootImpl viewRoot = getViewRootImpl();
        if (viewRoot == null) {
            return;
        }
        SurfaceControl viewRootControl = viewRoot.getSurfaceControl();
        t.setRelativeLayer(this.mBackgroundControl, viewRootControl, Integer.MIN_VALUE);
        t.setRelativeLayer(this.mSurfaceControl, viewRootControl, this.mSubLayer);
    }

    public void setChildSurfacePackage(SurfaceControlViewHost.SurfacePackage p) {
        SurfaceControl lastSc =
                this.mSurfacePackage != null ? this.mSurfacePackage.getSurfaceControl() : null;
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        if (this.mSurfaceControl != null) {
            if (lastSc != null) {
                transaction.reparent(lastSc, null);
                this.mSurfacePackage.release();
            }
            reparentSurfacePackage(transaction, p);
            applyTransactionOnVriDraw(transaction);
        }
        this.mSurfacePackage = p;
        try {
            this.mSurfacePackage
                    .getRemoteInterface()
                    .attachParentInterface(this.mSurfaceControlViewHostParent);
        } catch (RemoteException e) {
            Log.d(TAG, "Failed to attach parent interface to SCVH. Likely SCVH is already dead.");
        }
        if (isFocused()) {
            requestEmbeddedFocus(true);
        }
        invalidate();
    }

    private void reparentSurfacePackage(
            SurfaceControl.Transaction t, SurfaceControlViewHost.SurfacePackage p) {
        SurfaceControl sc = p.getSurfaceControl();
        if (sc == null || !sc.isValid()) {
            return;
        }
        initEmbeddedHierarchyForAccessibility(p);
        t.reparent(sc, this.mBlastSurfaceControl).show(sc);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        if (!this.mRemoteAccessibilityController.connected()) {
            return;
        }
        info.addChild(this.mRemoteAccessibilityController.getLeashToken());
    }

    @Override // android.view.View
    public int getImportantForAccessibility() {
        int mode = super.getImportantForAccessibility();
        if ((this.mRemoteAccessibilityController != null
                        && !this.mRemoteAccessibilityController.connected())
                || mode != 0) {
            return mode;
        }
        return 1;
    }

    private void initEmbeddedHierarchyForAccessibility(SurfaceControlViewHost.SurfacePackage p) {
        IAccessibilityEmbeddedConnection connection = p.getAccessibilityEmbeddedConnection();
        if (this.mRemoteAccessibilityController.alreadyAssociated(connection)) {
            return;
        }
        this.mRemoteAccessibilityController.assosciateHierarchy(
                connection, getViewRootImpl().mLeashToken, getAccessibilityViewId());
        updateEmbeddedAccessibilityMatrix(true);
    }

    private void notifySurfaceDestroyed() {
        if (this.mSurface.isValid()) {
            if (DEBUG) {
                Log.i(TAG, System.identityHashCode(this) + " surfaceDestroyed");
            }
            SurfaceHolder.Callback[] callbacks = getSurfaceCallbacks();
            Log.i(
                    this.mTag,
                    "surfaceDestroyed callback.size "
                            + this.mCallbacks.size()
                            + " #"
                            + this.mUpdateSurfaceCalledBy
                            + " "
                            + this);
            for (SurfaceHolder.Callback c : callbacks) {
                c.surfaceDestroyed(this.mSurfaceHolder);
            }
            if (this.mSurface.isValid()) {
                this.mSurface.forceScopedDisconnect();
            }
        }
    }

    void updateEmbeddedAccessibilityMatrix(boolean force) {
        if (!this.mRemoteAccessibilityController.connected()) {
            return;
        }
        getBoundsOnScreen(this.mTmpRect);
        this.mTmpRect.offset(-this.mAttachInfo.mWindowLeft, -this.mAttachInfo.mWindowTop);
        this.mTmpMatrix.reset();
        this.mTmpMatrix.setTranslate(this.mTmpRect.left, this.mTmpRect.top);
        this.mTmpMatrix.postScale(
                this.mScreenRect.width() / this.mSurfaceWidth,
                this.mScreenRect.height() / this.mSurfaceHeight);
        this.mRemoteAccessibilityController.setWindowMatrix(this.mTmpMatrix, force);
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        requestEmbeddedFocus(gainFocus);
    }

    private void requestEmbeddedFocus(boolean gainFocus) {
        ViewRootImpl viewRoot = getViewRootImpl();
        if (this.mSurfacePackage == null || viewRoot == null) {
            return;
        }
        if (viewRoot.mWindowAttributes.type == 3 && (viewRoot.mWindowAttributes.flags & 8) != 0) {
            Slog.d(TAG, "requestEmbeddedFocus: caller=" + Debug.getCallers(4));
            gainFocus = false;
        }
        try {
            viewRoot.mWindowSession.grantEmbeddedWindowFocus(
                    viewRoot.mWindow, this.mSurfacePackage.getInputTransferToken(), gainFocus);
        } catch (Exception e) {
            Log.e(
                    TAG,
                    System.identityHashCode(this) + "Exception requesting focus on embedded window",
                    e);
        }
    }

    private void applyTransactionOnVriDraw(SurfaceControl.Transaction t) {
        ViewRootImpl viewRoot = getViewRootImpl();
        if (viewRoot != null) {
            viewRoot.applyTransactionOnDraw(t);
        } else {
            t.apply();
        }
    }

    public void syncNextFrame(Consumer<SurfaceControl.Transaction> t) {
        this.mBlastBufferQueue.syncNextTransaction(t);
    }

    public void applyTransactionToFrame(SurfaceControl.Transaction transaction) {
        synchronized (this.mSurfaceControlLock) {
            if (this.mBlastBufferQueue == null) {
                throw new IllegalStateException("Surface does not exist!");
            }
            long frameNumber = this.mBlastBufferQueue.getLastAcquiredFrameNum() + 1;
            this.mBlastBufferQueue.mergeWithNextTransaction(transaction, frameNumber);
        }
    }

    @Override // android.view.View
    void performCollectViewAttributes(View.AttachInfo attachInfo, int visibility) {
        super.performCollectViewAttributes(attachInfo, visibility);
        if (this.mEmbeddedWindowParams.isEmpty()) {
            return;
        }
        Iterator<WindowManager.LayoutParams> it = this.mEmbeddedWindowParams.iterator();
        while (it.hasNext()) {
            WindowManager.LayoutParams embeddedWindowAttr = it.next();
            if ((embeddedWindowAttr.flags & 128) == 128) {
                attachInfo.mKeepScreenOn = true;
                return;
            }
        }
    }
}
