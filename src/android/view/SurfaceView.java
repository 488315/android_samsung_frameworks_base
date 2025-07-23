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
import android.graphics.RectF;
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
import android.util.EventLog;
import android.util.Log;
import android.util.secutil.Slog;
import android.view.ISurfaceControlViewHostParent;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityEmbeddedConnection;
import android.window.SurfaceSyncGroup;
import com.android.graphics.hwui.flags.Flags;
import com.android.internal.protolog.PerfettoProtoLogImpl;
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
    private static final int LOGTAG_SURFACEVIEW_CALLBACK = 60006;
    private static final int LOGTAG_SURFACEVIEW_LAYOUT = 60005;
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
    private final RectF mRTLastSetCrop;
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
    private final SurfaceControlViewHostParent mSurfaceControlViewHostParent;
    boolean mSurfaceCreated;
    private int mSurfaceCreatedCount;
    private int mSurfaceFlags;
    final Rect mSurfaceFrame;
    int mSurfaceHeight;
    private final SurfaceHolder mSurfaceHolder;
    private int mSurfaceLifecycleStrategy;
    final ReentrantLock mSurfaceLock;
    SurfaceControlViewHost.SurfacePackage mSurfacePackage;
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
    public @interface SurfaceLifecycleStrategy {
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void semResetRenderNodePosition() {
    }

    public void setUseAlpha() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0() {
        this.mHaveFrame = getWidth() > 0 && getHeight() > 0;
        this.mUpdateSurfaceCalledBy = 8;
        updateSurface();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SurfaceControlViewHostParent extends ISurfaceControlViewHostParent.Stub {
        private SurfaceView mSurfaceView;

        private SurfaceControlViewHostParent() {
        }

        void attach(SurfaceView surfaceView) {
            synchronized (this) {
                try {
                    surfaceView.mSurfacePackage.getRemoteInterface().attachParentInterface(this);
                    this.mSurfaceView = surfaceView;
                } catch (RemoteException unused) {
                    Log.d(SurfaceView.TAG, "Failed to attach parent interface to SCVH. Likely SCVH is alraedy dead.");
                }
            }
        }

        void detach() {
            synchronized (this) {
                SurfaceView surfaceView = this.mSurfaceView;
                if (surfaceView == null) {
                    return;
                }
                try {
                    surfaceView.mSurfacePackage.getRemoteInterface().attachParentInterface(null);
                } catch (RemoteException unused) {
                    Log.d(SurfaceView.TAG, "Failed to remove parent interface from SCVH. Likely SCVH is already dead");
                }
                this.mSurfaceView = null;
            }
        }

        @Override // android.view.ISurfaceControlViewHostParent
        public void updateParams(WindowManager.LayoutParams[] layoutParamsArr) {
            final SurfaceView surfaceView;
            synchronized (this) {
                surfaceView = this.mSurfaceView;
            }
            if (surfaceView == null) {
                return;
            }
            surfaceView.mEmbeddedWindowParams.clear();
            surfaceView.mEmbeddedWindowParams.addAll(Arrays.asList(layoutParamsArr));
            if (surfaceView.isAttachedToWindow()) {
                surfaceView.runOnUiThread(new Runnable() { // from class: android.view.SurfaceView$SurfaceControlViewHostParent$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceView.SurfaceControlViewHostParent.lambda$updateParams$0(SurfaceView.this);
                    }
                });
            }
        }

        static /* synthetic */ void lambda$updateParams$0(SurfaceView surfaceView) {
            if (surfaceView.mParent != null) {
                surfaceView.mParent.recomputeViewAttributes(surfaceView);
            }
        }

        @Override // android.view.ISurfaceControlViewHostParent
        public void forwardBackKeyToParent(final KeyEvent keyEvent) {
            final SurfaceView surfaceView;
            synchronized (this) {
                surfaceView = this.mSurfaceView;
            }
            if (surfaceView == null) {
                return;
            }
            surfaceView.runOnUiThread(new Runnable() { // from class: android.view.SurfaceView$SurfaceControlViewHostParent$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SurfaceView.SurfaceControlViewHostParent.lambda$forwardBackKeyToParent$1(SurfaceView.this, keyEvent);
                }
            });
        }

        static /* synthetic */ void lambda$forwardBackKeyToParent$1(SurfaceView surfaceView, KeyEvent keyEvent) {
            ViewRootImpl viewRootImpl;
            InputManager inputManager;
            if (!surfaceView.isAttachedToWindow() || keyEvent.getKeyCode() != 4 || (viewRootImpl = surfaceView.getViewRootImpl()) == null || (inputManager = (InputManager) surfaceView.mContext.getSystemService(InputManager.class)) == null) {
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis() - keyEvent.getEventTime();
            if (uptimeMillis > SurfaceView.FORWARD_BACK_KEY_TOLERANCE_MS) {
                Log.e(SurfaceView.TAG, "Ignore the input event that exceed the tolerance time, exceed " + uptimeMillis + "ms");
                return;
            }
            if (inputManager.verifyInputEvent(keyEvent) == null) {
                Log.e(SurfaceView.TAG, "Received invalid input event");
            } else {
                viewRootImpl.enqueueInputEvent(keyEvent, null, 0, true);
            }
        }
    }

    public SurfaceView(Context context) {
        this(context, null);
    }

    public SurfaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SurfaceView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SurfaceView(Context context, AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i, i2, false);
    }

    public SurfaceView(Context context, AttributeSet attributeSet, int i, int i2, boolean z) {
        super(context, attributeSet, i, i2);
        this.mCallbacks = new ArrayList<>();
        this.mLocation = new int[2];
        this.mSurfaceLock = new ReentrantLock();
        this.mSurface = new Surface();
        this.mDrawingStopped = true;
        this.mDrawFinished = false;
        this.mScreenRect = new Rect();
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
        this.mScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                SurfaceView.this.updateSurface();
            }
        };
        this.mDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda1
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
        this.mTag = TAG;
        this.mSurfaceControlViewHostParent = new SurfaceControlViewHostParent();
        this.mRtDrivenClipping = Flags.clipSurfaceviews();
        this.mRTLastReportedPosition = new Rect();
        this.mRTLastSetCrop = new RectF();
        this.mPositionListener = null;
        this.mSurfaceHolder = new AnonymousClass1();
        setWillNotDraw(true);
        this.mDisableBackgroundLayer = z;
        this.mTag = "SurfaceView@" + Integer.toHexString(System.identityHashCode(this));
    }

    public SurfaceHolder getHolder() {
        return this.mSurfaceHolder;
    }

    private void updateRequestedVisibility() {
        this.mRequestedVisible = this.mViewVisibility && this.mWindowVisibility && !this.mWindowStopped;
    }

    private void setWindowStopped(boolean z) {
        this.mWindowStopped = z;
        updateRequestedVisibility();
        this.mUpdateSurfaceCalledBy = 1;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            Log.i(this.mTag, "windowStopped(" + z + ") " + this.mRequestedVisible + " " + this + " of " + viewRootImpl.getTag());
        }
        updateSurface();
    }

    private void setTag() {
        String str;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            String[] split = viewRootImpl.mWindowAttributes.getTitle().toString().split("\\.");
            if (split.length > 0) {
                str = " " + split[split.length - 1];
                this.mTag = "SV[" + System.identityHashCode(this) + str + NavigationBarInflaterView.SIZE_MOD_END;
            }
        }
        str = "";
        this.mTag = "SV[" + System.identityHashCode(this) + str + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setTag();
        getViewRootImpl().addSurfaceChangedCallback(this);
        this.mWindowStopped = false;
        this.mViewVisibility = getVisibility() == 0;
        updateRequestedVisibility();
        this.mAttachedToWindow = true;
        this.mParent.requestTransparentRegion(this);
        if (this.mGlobalListenersAdded) {
            return;
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnScrollChangedListener(this.mScrollChangedListener);
        viewTreeObserver.addOnPreDrawListener(this.mDrawListener);
        this.mGlobalListenersAdded = true;
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.mWindowVisibility = i == 0;
        updateRequestedVisibility();
        this.mUpdateSurfaceCalledBy = 2;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            Log.i(this.mTag, "onWindowVisibilityChanged(" + i + ") " + this.mRequestedVisible + " " + this + " of " + viewRootImpl.getTag());
        }
        updateSurface();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        this.mViewVisibility = z;
        boolean z2 = this.mWindowVisibility && z && !this.mWindowStopped;
        if (z2 != this.mRequestedVisible) {
            requestLayout();
        }
        this.mRequestedVisible = z2;
        this.mUpdateSurfaceCalledBy = 3;
        updateSurface();
    }

    @Override // android.view.View
    public void setAlpha(float f) {
        if (DEBUG) {
            Log.d(TAG, System.identityHashCode(this) + " setAlpha: alpha=" + f);
        }
        super.setAlpha(f);
    }

    @Override // android.view.View
    protected boolean onSetAlpha(int i) {
        if (Math.round(this.mAlpha * 255.0f) == i) {
            return true;
        }
        updateSurface();
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
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.removeSurfaceChangedCallback(this);
        }
        this.mAttachedToWindow = false;
        if (this.mGlobalListenersAdded) {
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            viewTreeObserver.removeOnScrollChangedListener(this.mScrollChangedListener);
            viewTreeObserver.removeOnPreDrawListener(this.mDrawListener);
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
    protected void onMeasure(int i, int i2) {
        int defaultSize;
        int defaultSize2;
        int i3 = this.mRequestedWidth;
        if (i3 >= 0) {
            defaultSize = resolveSizeAndState(i3, i, 0);
        } else {
            defaultSize = getDefaultSize(0, i);
        }
        int i4 = this.mRequestedHeight;
        if (i4 >= 0) {
            defaultSize2 = resolveSizeAndState(i4, i2, 0);
        } else {
            defaultSize2 = getDefaultSize(0, i2);
        }
        setMeasuredDimension(defaultSize, defaultSize2);
    }

    @Override // android.view.View
    protected boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        this.mUpdateSurfaceCalledBy = 5;
        updateSurface();
        return frame;
    }

    @Override // android.view.View
    public boolean gatherTransparentRegion(Region region) {
        boolean z;
        if (isAboveParent() || !this.mDrawFinished) {
            return super.gatherTransparentRegion(region);
        }
        if ((this.mPrivateFlags & 128) == 0) {
            z = super.gatherTransparentRegion(region);
        } else {
            if (region != null) {
                int width = getWidth();
                int height = getHeight();
                if (width > 0 && height > 0) {
                    getLocationInWindow(this.mLocation);
                    int[] iArr = this.mLocation;
                    int i = iArr[0];
                    int i2 = iArr[1];
                    region.op(i, i2, i + width, i2 + height, Region.Op.UNION);
                }
            }
            z = true;
        }
        if (PixelFormat.formatHasAlpha(this.mRequestedFormat)) {
            return false;
        }
        return z;
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

    public void setEnableSurfaceClipping(boolean z) {
        this.mClipSurfaceToBounds = z;
        invalidate();
    }

    @Override // android.view.View
    public void setClipBounds(Rect rect) {
        super.setClipBounds(rect);
        if ((this.mRtDrivenClipping && isHardwareAccelerated()) || !this.mClipSurfaceToBounds || this.mSurfaceControl == null) {
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

    private void clearSurfaceViewPort(Canvas canvas) {
        float alpha = getAlpha();
        if (this.mCornerRadius > 0.0f) {
            canvas.getClipBounds(this.mTmpRect);
            if (this.mClipSurfaceToBounds && this.mClipBounds != null) {
                this.mTmpRect.intersect(this.mClipBounds);
            }
            float f = this.mTmpRect.left;
            float f2 = this.mTmpRect.top;
            float f3 = this.mTmpRect.right;
            float f4 = this.mTmpRect.bottom;
            float f5 = this.mCornerRadius;
            canvas.punchHole(f, f2, f3, f4, f5, f5, alpha);
            return;
        }
        canvas.punchHole(0.0f, 0.0f, getWidth(), getHeight(), 0.0f, 0.0f, alpha);
    }

    public void setCornerRadius(float f) {
        this.mCornerRadius = f;
        if (f > 0.0f && this.mRoundedViewportPaint == null) {
            Paint paint = new Paint(1);
            this.mRoundedViewportPaint = paint;
            paint.setBlendMode(BlendMode.CLEAR);
            this.mRoundedViewportPaint.setColor(0);
        }
        invalidate();
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public void setCompositionOrder(int i) {
        this.mRequestedSubLayer = i;
        if (this.mSubLayer != i) {
            updateSurface();
        }
    }

    public int getCompositionOrder() {
        return this.mRequestedSubLayer;
    }

    @Deprecated
    public void setZOrderMediaOverlay(boolean z) {
        this.mRequestedSubLayer = z ? -1 : -2;
    }

    @Deprecated
    public void setZOrderOnTop(boolean z) {
        setZOrderedOnTop(z, getContext().getApplicationInfo().targetSdkVersion > 29);
    }

    public boolean isZOrderedOnTop() {
        return this.mRequestedSubLayer > 0;
    }

    @Deprecated
    public boolean setZOrderedOnTop(boolean z, boolean z2) {
        int i = z ? 1 : -2;
        if (this.mRequestedSubLayer == i) {
            return false;
        }
        this.mRequestedSubLayer = i;
        if (!z2) {
            return false;
        }
        if (this.mSurfaceControl == null || getViewRootImpl() == null) {
            return true;
        }
        updateSurface();
        invalidate();
        return true;
    }

    public void setSecure(boolean z) {
        if (z) {
            this.mSurfaceFlags |= 128;
        } else {
            this.mSurfaceFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        }
    }

    public void setSurfaceLifecycle(int i) {
        this.mRequestedSurfaceLifecycleStrategy = i;
        updateSurface();
    }

    public void setDesiredHdrHeadroom(float f) {
        if (!Float.isFinite(f)) {
            throw new IllegalArgumentException("desiredHeadroom must be finite: " + f);
        }
        if (f != 0.0f && (f < 1.0f || f > 10000.0f)) {
            throw new IllegalArgumentException("desiredHeadroom must be 0.0 or in the range [1.0, 10000.0f], received: " + f);
        }
        this.mRequestedHdrHeadroom = f;
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

    private void updateBackgroundVisibility(SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl = this.mBackgroundControl;
        if (surfaceControl == null) {
            return;
        }
        if (this.mSubLayer < 0 && (this.mSurfaceFlags & 1024) != 0 && !this.mDisableBackgroundLayer) {
            if (!this.mIsWindowOpaque && getResources().getConfiguration().windowConfiguration.getWindowingMode() == 5) {
                transaction.hide(this.mBackgroundControl);
                return;
            } else {
                transaction.show(this.mBackgroundControl);
                return;
            }
        }
        transaction.hide(surfaceControl);
    }

    private SurfaceControl.Transaction updateBackgroundColor(SurfaceControl.Transaction transaction) {
        transaction.setColor(this.mBackgroundControl, new float[]{Color.red(this.mBackgroundColor) / 255.0f, Color.green(this.mBackgroundColor) / 255.0f, Color.blue(this.mBackgroundColor) / 255.0f});
        return transaction;
    }

    private void releaseSurfaces(boolean z) {
        this.mAlpha = 1.0f;
        this.mSurface.destroy();
        synchronized (this.mSurfaceControlLock) {
            BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
            if (bLASTBufferQueue != null) {
                bLASTBufferQueue.destroy();
                this.mBlastBufferQueue = null;
            }
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            ViewRootImpl viewRootImpl = getViewRootImpl();
            String str = this.mTag;
            StringBuilder sb = new StringBuilder("releaseSurfaces: viewRoot = ");
            sb.append(viewRootImpl != null ? viewRootImpl.getTag() : PerfettoProtoLogImpl.NULL_STRING);
            Log.i(str, sb.toString());
            SurfaceControl surfaceControl = this.mSurfaceControl;
            if (surfaceControl != null) {
                transaction.remove(surfaceControl);
                this.mSurfaceControl = null;
            }
            SurfaceControl surfaceControl2 = this.mBackgroundControl;
            if (surfaceControl2 != null) {
                transaction.remove(surfaceControl2);
                this.mBackgroundControl = null;
            }
            SurfaceControl surfaceControl3 = this.mBlastSurfaceControl;
            if (surfaceControl3 != null) {
                transaction.remove(surfaceControl3);
                this.mBlastSurfaceControl = null;
            }
            if (this.mSurfacePackage != null) {
                this.mEmbeddedWindowParams.clear();
                if (z) {
                    this.mSurfaceControlViewHostParent.detach();
                    this.mSurfacePackage.release();
                    this.mSurfacePackage = null;
                }
            }
            applyTransactionOnVriDraw(transaction);
        }
    }

    private void replacePositionUpdateListener(int i, int i2) {
        if (this.mPositionListener != null) {
            this.mRenderNode.removePositionUpdateListener(this.mPositionListener);
        }
        this.mPositionListener = new SurfaceViewPositionUpdateListener(i, i2);
        this.mRenderNode.addPositionUpdateListener(this.mPositionListener);
    }

    private boolean performSurfaceTransaction(ViewRootImpl viewRootImpl, CompatibilityInfo.Translator translator, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, SurfaceControl.Transaction transaction) {
        this.mSurfaceLock.lock();
        try {
            boolean z6 = true;
            this.mDrawingStopped = !surfaceShouldExist();
            if (DEBUG) {
                Log.i(TAG, System.identityHashCode(this) + " Cur surface: " + this.mSurface);
            }
            if (z) {
                updateRelativeZ(transaction);
                SurfaceControlViewHost.SurfacePackage surfacePackage = this.mSurfacePackage;
                if (surfacePackage != null) {
                    reparentSurfacePackage(transaction, surfacePackage);
                }
            }
            this.mParentSurfaceSequenceId = viewRootImpl.getSurfaceSequenceId();
            if (!isHardwareAccelerated()) {
                if (this.mViewVisibility) {
                    transaction.show(this.mSurfaceControl);
                } else {
                    transaction.hide(this.mSurfaceControl);
                }
            }
            updateBackgroundVisibility(transaction);
            updateBackgroundColor(transaction);
            if (this.mLimitedHdrEnabled && (z5 || z)) {
                transaction.setDesiredHdrHeadroom(this.mBlastSurfaceControl, this.mHdrHeadroom);
            }
            if (isAboveParent()) {
                transaction.setAlpha(this.mSurfaceControl, getAlpha());
            }
            if (z4) {
                if (!isAboveParent()) {
                    transaction.setAlpha(this.mSurfaceControl, 1.0f);
                }
                updateRelativeZ(transaction);
            }
            transaction.setCornerRadius(this.mSurfaceControl, this.mCornerRadius);
            if ((z2 || z3) && !z) {
                setBufferSize(transaction);
            }
            if (z2 || z || !isHardwareAccelerated()) {
                if (!this.mRtDrivenClipping || !isHardwareAccelerated()) {
                    if (this.mClipSurfaceToBounds && this.mClipBounds != null) {
                        transaction.setWindowCrop(this.mSurfaceControl, this.mClipBounds);
                    } else {
                        transaction.setWindowCrop(this.mSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight);
                    }
                    Log.i(this.mTag, "pST: sr = " + this.mScreenRect + " sw = " + this.mSurfaceWidth + " sh = " + this.mSurfaceHeight);
                }
                transaction.setDestinationFrame(this.mBlastSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight);
                if (isHardwareAccelerated()) {
                    replacePositionUpdateListener(this.mSurfaceWidth, this.mSurfaceHeight);
                } else {
                    onSetSurfacePositionAndScale(transaction, this.mSurfaceControl, this.mScreenRect.left, this.mScreenRect.top, this.mScreenRect.width() / this.mSurfaceWidth, this.mScreenRect.height() / this.mSurfaceHeight);
                }
                if (DEBUG_POSITION) {
                    Log.d(TAG, TextUtils.formatSimple("%d performSurfaceTransaction %s position = [%d, %d, %d, %d] surfaceSize = %dx%d", Integer.valueOf(System.identityHashCode(this)), isHardwareAccelerated() ? "RenderWorker" : "UI Thread", Integer.valueOf(this.mScreenRect.left), Integer.valueOf(this.mScreenRect.top), Integer.valueOf(this.mScreenRect.right), Integer.valueOf(this.mScreenRect.bottom), Integer.valueOf(this.mSurfaceWidth), Integer.valueOf(this.mSurfaceHeight)));
                }
            }
            applyTransactionOnVriDraw(transaction);
            updateEmbeddedAccessibilityMatrix(false);
            this.mSurfaceFrame.left = 0;
            this.mSurfaceFrame.top = 0;
            if (translator == null) {
                this.mSurfaceFrame.right = this.mSurfaceWidth;
                this.mSurfaceFrame.bottom = this.mSurfaceHeight;
            } else {
                float f = translator.applicationInvertedScale;
                this.mSurfaceFrame.right = (int) ((this.mSurfaceWidth * f) + 0.5f);
                this.mSurfaceFrame.bottom = (int) ((this.mSurfaceHeight * f) + 0.5f);
            }
            int i = this.mSurfaceFrame.right;
            int i2 = this.mSurfaceFrame.bottom;
            if (this.mLastSurfaceWidth == i && this.mLastSurfaceHeight == i2) {
                z6 = false;
            }
            this.mLastSurfaceWidth = i;
            this.mLastSurfaceHeight = i2;
            return z6;
        } finally {
            this.mSurfaceLock.unlock();
        }
    }

    private boolean requiresSurfaceControlCreation(boolean z, boolean z2) {
        return this.mSurfaceLifecycleStrategy == 2 ? (this.mSurfaceControl == null || z) && this.mAttachedToWindow : (this.mSurfaceControl == null || z || z2) && this.mRequestedVisible;
    }

    private boolean surfaceShouldExist() {
        return this.mVisible || (!(this.mSurfaceLifecycleStrategy != 2) && this.mAttachedToWindow);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(33:86|(5:88|(1:90)(1:265)|91|(1:93)(1:264)|94)(1:266)|(3:259|(1:261)(1:263)|262)|104|105|(1:107)|108|(1:110)(2:254|(2:256|257))|(21:121|(1:252)(1:127)|128|(4:130|131|132|133)(1:251)|134|135|136|137|138|(1:140)(1:241)|(1:142)(1:240)|(1:239)(1:145)|146|(1:157)|158|(1:238)(13:162|163|(1:237)(7:168|169|(1:171)|172|(1:174)|175|176)|(2:185|(6:187|(1:189)|190|(1:192)|193|(1:195)(1:196)))|208|(7:210|211|212|213|214|215|216)(1:236)|217|(1:219)|220|(1:222)|223|224|(0))|197|198|(2:200|(1:202))|203|(2:205|206)(1:207))|253|(1:123)|252|128|(0)(0)|134|135|136|137|138|(0)(0)|(0)(0)|(0)|239|146|(1:157)|158|(1:160)|238|197|198|(0)|203|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x05a0, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0312  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x035d  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0362  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x053a A[Catch: all -> 0x0577, TryCatch #2 {all -> 0x0577, blocks: (B:187:0x053a, B:189:0x053e, B:190:0x0556, B:192:0x0567, B:195:0x056f, B:196:0x0573, B:216:0x04a0, B:217:0x04b7, B:219:0x04e5, B:220:0x04e9, B:222:0x052a), top: B:215:0x04a0 }] */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0584 A[Catch: Exception -> 0x05a0, TryCatch #0 {Exception -> 0x05a0, blocks: (B:198:0x057e, B:200:0x0584, B:202:0x0588, B:226:0x0591, B:228:0x0598, B:230:0x059c, B:231:0x059f), top: B:135:0x0354 }] */
    /* JADX WARN: Removed duplicated region for block: B:205:0x05b0  */
    /* JADX WARN: Removed duplicated region for block: B:207:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0364  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0340  */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.view.SurfaceView, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v6, types: [android.view.SurfaceView, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v32, types: [android.view.ViewRootImpl] */
    /* JADX WARN: Type inference failed for: r2v45, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r2v50 */
    /* JADX WARN: Type inference failed for: r2v56 */
    /* JADX WARN: Type inference failed for: r3v18, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v16, types: [java.lang.StringBuilder] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void updateSurface() {
        /*
            Method dump skipped, instructions count: 1540
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.SurfaceView.updateSurface():void");
    }

    public String getName() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        return "SurfaceView[" + (viewRootImpl == null ? "detached" : viewRootImpl.getTitle().toString()) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private void handleSyncBufferCallback(SurfaceHolder.Callback[] callbackArr, final SyncBufferTransactionCallback syncBufferTransactionCallback) {
        final SurfaceSyncGroup surfaceSyncGroup = new SurfaceSyncGroup(getName());
        getViewRootImpl().addToSync(surfaceSyncGroup);
        redrawNeededAsync(callbackArr, new Runnable() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                SurfaceView.this.lambda$handleSyncBufferCallback$1(syncBufferTransactionCallback, surfaceSyncGroup);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSyncBufferCallback$1(SyncBufferTransactionCallback syncBufferTransactionCallback, SurfaceSyncGroup surfaceSyncGroup) {
        SurfaceControl.Transaction transaction;
        BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            bLASTBufferQueue.stopContinuousSyncTransaction();
            transaction = syncBufferTransactionCallback.waitForTransaction();
        } else {
            transaction = null;
        }
        surfaceSyncGroup.addTransaction(transaction);
        surfaceSyncGroup.markSyncReady();
        onDrawFinished();
    }

    private void handleSyncNoBuffer(SurfaceHolder.Callback[] callbackArr) {
        final SurfaceSyncGroup surfaceSyncGroup = new SurfaceSyncGroup(getName());
        synchronized (this.mSyncGroups) {
            this.mSyncGroups.add(surfaceSyncGroup);
        }
        redrawNeededAsync(callbackArr, new Runnable() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda2
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

    private void redrawNeededAsync(SurfaceHolder.Callback[] callbackArr, Runnable runnable) {
        new SurfaceCallbackHelper(runnable, this.mTag).dispatchSurfaceRedrawNeededAsync(this.mSurfaceHolder, callbackArr);
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void vriDrawStarted(boolean z) {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        synchronized (this.mSyncGroups) {
            if (z && viewRootImpl != null) {
                Iterator<SurfaceSyncGroup> it = this.mSyncGroups.iterator();
                while (it.hasNext()) {
                    viewRootImpl.addToSync(it.next());
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
            } catch (InterruptedException unused) {
            }
            return this.mTransaction;
        }

        void onTransactionReady(SurfaceControl.Transaction transaction) {
            this.mTransaction = transaction;
            this.mCountDownLatch.countDown();
        }
    }

    private void copySurface(boolean z, boolean z2) {
        BLASTBufferQueue bLASTBufferQueue;
        if (z) {
            this.mSurface.copyFrom(this.mBlastBufferQueue);
        }
        if (!z2 || getContext().getApplicationInfo().targetSdkVersion >= 26 || (bLASTBufferQueue = this.mBlastBufferQueue) == null) {
            return;
        }
        this.mSurface.transferFrom(bLASTBufferQueue.createSurfaceWithHandle());
    }

    private void setBufferSize(SurfaceControl.Transaction transaction) {
        this.mBlastSurfaceControl.setTransformHint(this.mTransformHint);
        BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            bLASTBufferQueue.update(this.mBlastSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight, this.mFormat);
        }
    }

    private void createBlastSurfaceControls(ViewRootImpl viewRootImpl, String str, SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl == null) {
            this.mSurfaceControl = new SurfaceControl.Builder().setName(str).setLocalOwnerView(this).setParent(viewRootImpl.updateAndGetBoundsLayer(transaction)).setCallsite("SurfaceView.updateSurface").setContainerLayer().build();
        }
        SurfaceControl surfaceControl = this.mBlastSurfaceControl;
        if (surfaceControl == null) {
            this.mBlastSurfaceControl = new SurfaceControl.Builder().setName(str + "(BLAST)").setLocalOwnerView(this).setParent(this.mSurfaceControl).setFlags(this.mSurfaceFlags).setHidden(false).setBLASTLayer().setCallsite("SurfaceView.updateSurface").build();
        } else {
            transaction.setOpaque(surfaceControl, (this.mSurfaceFlags & 1024) != 0).setSecure(this.mBlastSurfaceControl, (this.mSurfaceFlags & 128) != 0).show(this.mBlastSurfaceControl);
        }
        if (this.mBackgroundControl == null) {
            this.mBackgroundControl = new SurfaceControl.Builder().setName("Background for " + str).setLocalOwnerView(this).setOpaque(true).setColorLayer().setParent(this.mSurfaceControl).setCallsite("SurfaceView.updateSurface").build();
        }
        BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            bLASTBufferQueue.destroy();
        }
        int bufferTransformHint = viewRootImpl.getBufferTransformHint();
        this.mTransformHint = bufferTransformHint;
        this.mBlastSurfaceControl.setTransformHint(bufferTransformHint);
        BLASTBufferQueue bLASTBufferQueue2 = new BLASTBufferQueue(str, false);
        this.mBlastBufferQueue = bLASTBufferQueue2;
        bLASTBufferQueue2.update(this.mBlastSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight, this.mFormat);
        this.mBlastBufferQueue.setTransactionHangCallback(ViewRootImpl.sTransactionHangCallback);
    }

    private void onDrawFinished() {
        if (DEBUG) {
            Log.i(TAG, System.identityHashCode(this) + " finishedDrawing");
        }
        runOnUiThread(new Runnable() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                SurfaceView.this.performDrawFinished();
            }
        });
    }

    protected void onSetSurfacePositionAndScale(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i, int i2, float f, float f2) {
        Log.i(this.mTag, "onSSPAndSRT: pl = " + i + " pt = " + i2 + " sx = " + f + " sy = " + f2);
        transaction.setPosition(surfaceControl, (float) i, (float) i2);
        transaction.setMatrix(surfaceControl, f, 0.0f, 0.0f, f2);
    }

    public void requestUpdateSurfacePositionAndScale() {
        if (this.mSurfaceControl == null) {
            return;
        }
        Log.i(this.mTag, "rUSPAndS: sr = " + this.mScreenRect + " sw = " + this.mSurfaceWidth + " sh = " + this.mSurfaceHeight);
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        onSetSurfacePositionAndScale(transaction, this.mSurfaceControl, this.mScreenRect.left, this.mScreenRect.top, ((float) this.mScreenRect.width()) / ((float) this.mSurfaceWidth), ((float) this.mScreenRect.height()) / ((float) this.mSurfaceHeight));
        applyTransactionOnVriDraw(transaction);
        invalidate();
    }

    public Rect getSurfaceRenderPosition() {
        return this.mRTLastReportedPosition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyOrMergeTransaction(SurfaceControl.Transaction transaction, long j) {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            Log.i(this.mTag, "aOrMT: " + viewRootImpl.getTag() + " t = " + transaction + " fN = " + j + " " + Debug.getCallers(3));
            viewRootImpl.mergeWithNextTransaction(transaction, j);
            return;
        }
        Log.i(this.mTag, "aOrMT: t.apply");
        transaction.apply();
    }

    private class SurfaceViewPositionUpdateListener implements RenderNode.PositionUpdateListener {
        private final SurfaceControl.Transaction mPositionChangedTransaction = new SurfaceControl.Transaction();
        private final int mRtSurfaceHeight;
        private final int mRtSurfaceWidth;

        SurfaceViewPositionUpdateListener(int i, int i2) {
            this.mRtSurfaceWidth = i;
            this.mRtSurfaceHeight = i2;
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(long j, int i, int i2, int i3, int i4) {
            try {
                if (SurfaceView.DEBUG_POSITION) {
                    Log.d(SurfaceView.TAG, String.format("%d updateSurfacePosition RenderWorker, frameNr = %d, position = [%d, %d, %d, %d] surfaceSize = %dx%d", Integer.valueOf(System.identityHashCode(SurfaceView.this)), Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(this.mRtSurfaceWidth), Integer.valueOf(this.mRtSurfaceHeight)));
                }
                synchronized (SurfaceView.this.mSurfaceControlLock) {
                    if (SurfaceView.this.mSurfaceControl == null) {
                        return;
                    }
                    SurfaceView.this.mRTLastReportedPosition.set(i, i2, i3, i4);
                    Log.i(SurfaceView.this.mTag, "uSP: rtp = " + SurfaceView.this.mRTLastReportedPosition + " rtsw = " + this.mRtSurfaceWidth + " rtsh = " + this.mRtSurfaceHeight);
                    SurfaceView surfaceView = SurfaceView.this;
                    surfaceView.onSetSurfacePositionAndScale(this.mPositionChangedTransaction, surfaceView.mSurfaceControl, SurfaceView.this.mRTLastReportedPosition.left, SurfaceView.this.mRTLastReportedPosition.top, ((float) SurfaceView.this.mRTLastReportedPosition.width()) / ((float) this.mRtSurfaceWidth), ((float) SurfaceView.this.mRTLastReportedPosition.height()) / ((float) this.mRtSurfaceHeight));
                    this.mPositionChangedTransaction.show(SurfaceView.this.mSurfaceControl);
                    SurfaceView.this.applyOrMergeTransaction(this.mPositionChangedTransaction, j);
                }
            } catch (Exception e) {
                Log.e(SurfaceView.TAG, "Exception from repositionChild", e);
            }
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
            try {
                if (SurfaceView.DEBUG_POSITION) {
                    Log.d(SurfaceView.TAG, String.format("%d updateSurfacePosition RenderWorker, frameNr = %d, position = [%d, %d, %d, %d] clip = [%d, %d, %d, %d] surfaceSize = %dx%d renderNodeSize = %d%d", Integer.valueOf(System.identityHashCode(SurfaceView.this)), Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8), Integer.valueOf(this.mRtSurfaceWidth), Integer.valueOf(this.mRtSurfaceHeight), Integer.valueOf(i9), Integer.valueOf(i10)));
                }
                synchronized (SurfaceView.this.mSurfaceControlLock) {
                    if (SurfaceView.this.mSurfaceControl == null) {
                        return;
                    }
                    SurfaceView.this.mRTLastReportedPosition.set(i, i2, i3, i4);
                    SurfaceView surfaceView = SurfaceView.this;
                    surfaceView.onSetSurfacePositionAndScale(this.mPositionChangedTransaction, surfaceView.mSurfaceControl, SurfaceView.this.mRTLastReportedPosition.left, SurfaceView.this.mRTLastReportedPosition.top, SurfaceView.this.mRTLastReportedPosition.width() / this.mRtSurfaceWidth, SurfaceView.this.mRTLastReportedPosition.height() / this.mRtSurfaceHeight);
                    float f = this.mRtSurfaceWidth / i9;
                    float f2 = this.mRtSurfaceHeight / i10;
                    SurfaceView.this.mRTLastSetCrop.set(i5 * f, i6 * f2, i7 * f, i8 * f2);
                    if (SurfaceView.DEBUG_POSITION) {
                        Log.d(SurfaceView.TAG, String.format("Setting layer crop = [%f, %f, %f, %f] from scale %f, %f", Float.valueOf(SurfaceView.this.mRTLastSetCrop.left), Float.valueOf(SurfaceView.this.mRTLastSetCrop.top), Float.valueOf(SurfaceView.this.mRTLastSetCrop.right), Float.valueOf(SurfaceView.this.mRTLastSetCrop.bottom), Float.valueOf(f), Float.valueOf(f2)));
                    }
                    this.mPositionChangedTransaction.setCrop(SurfaceView.this.mSurfaceControl, SurfaceView.this.mRTLastSetCrop.left, SurfaceView.this.mRTLastSetCrop.top, SurfaceView.this.mRTLastSetCrop.right, SurfaceView.this.mRTLastSetCrop.bottom);
                    if (SurfaceView.this.mRTLastSetCrop.isEmpty()) {
                        this.mPositionChangedTransaction.hide(SurfaceView.this.mSurfaceControl);
                    } else {
                        this.mPositionChangedTransaction.show(SurfaceView.this.mSurfaceControl);
                    }
                    SurfaceView.this.applyOrMergeTransaction(this.mPositionChangedTransaction, j);
                }
            } catch (Exception e) {
                Log.e(SurfaceView.TAG, "Exception from repositionChild", e);
            }
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void applyStretch(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            SurfaceView.this.mRtTransaction.setStretchEffect(SurfaceView.this.mSurfaceControl, f, f2, f3, f4, f5, f6, f7, f8, f9, f10);
            SurfaceView surfaceView = SurfaceView.this;
            surfaceView.applyOrMergeTransaction(surfaceView.mRtTransaction, j);
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionLost(long j) {
            if (SurfaceView.DEBUG_POSITION) {
                Log.d(SurfaceView.TAG, String.format("%d windowPositionLost, frameNr = %d", Integer.valueOf(System.identityHashCode(this)), Long.valueOf(j)));
            }
            SurfaceView.this.mRTLastReportedPosition.setEmpty();
            synchronized (SurfaceView.this.mSurfaceControlLock) {
                if (SurfaceView.this.mSurfaceControl == null) {
                    return;
                }
                SurfaceView.this.mRtTransaction.hide(SurfaceView.this.mSurfaceControl);
                SurfaceView surfaceView = SurfaceView.this;
                surfaceView.applyOrMergeTransaction(surfaceView.mRtTransaction, j);
            }
        }
    }

    private SurfaceHolder.Callback[] getSurfaceCallbacks() {
        SurfaceHolder.Callback[] callbackArr;
        synchronized (this.mCallbacks) {
            callbackArr = new SurfaceHolder.Callback[this.mCallbacks.size()];
            this.mCallbacks.toArray(callbackArr);
        }
        return callbackArr;
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

    public void setResizeBackgroundColor(int i) {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        setResizeBackgroundColor(transaction, i);
        applyTransactionOnVriDraw(transaction);
        invalidate();
    }

    public void setResizeBackgroundColor(SurfaceControl.Transaction transaction, int i) {
        if (this.mBackgroundControl == null) {
            return;
        }
        this.mBackgroundColor = i;
        updateBackgroundColor(transaction);
    }

    /* renamed from: android.view.SurfaceView$1, reason: invalid class name */
    class AnonymousClass1 implements SurfaceHolder {
        private static final String LOG_TAG = "SurfaceHolder";

        @Override // android.view.SurfaceHolder
        @Deprecated
        public void setType(int i) {
        }

        AnonymousClass1() {
        }

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
        public void setFixedSize(int i, int i2) {
            if (SurfaceView.this.mRequestedWidth == i && SurfaceView.this.mRequestedHeight == i2) {
                return;
            }
            if (SurfaceView.DEBUG_POSITION) {
                Log.d(SurfaceView.TAG, String.format("%d setFixedSize %dx%d -> %dx%d", Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(SurfaceView.this.mRequestedWidth), Integer.valueOf(SurfaceView.this.mRequestedHeight), Integer.valueOf(i), Integer.valueOf(i2)));
            }
            SurfaceView.this.mRequestedWidth = i;
            SurfaceView.this.mRequestedHeight = i2;
            SurfaceView.this.requestLayout();
        }

        @Override // android.view.SurfaceHolder
        public void setSizeFromLayout() {
            if (SurfaceView.this.mRequestedWidth == -1 && SurfaceView.this.mRequestedHeight == -1) {
                return;
            }
            if (SurfaceView.DEBUG_POSITION) {
                Log.d(SurfaceView.TAG, String.format("%d setSizeFromLayout was %dx%d", Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(SurfaceView.this.mRequestedWidth), Integer.valueOf(SurfaceView.this.mRequestedHeight)));
            }
            SurfaceView surfaceView = SurfaceView.this;
            surfaceView.mRequestedHeight = -1;
            surfaceView.mRequestedWidth = -1;
            SurfaceView.this.requestLayout();
        }

        @Override // android.view.SurfaceHolder
        public void setFormat(int i) {
            if (i == -1) {
                i = 4;
            }
            SurfaceView.this.mRequestedFormat = i;
            if (SurfaceView.this.mSurfaceControl != null) {
                SurfaceView.this.mUpdateSurfaceCalledBy = 6;
                SurfaceView.this.updateSurface();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setKeepScreenOn$0(boolean z) {
            SurfaceView.this.setKeepScreenOn(z);
        }

        @Override // android.view.SurfaceHolder
        public void setKeepScreenOn(final boolean z) {
            SurfaceView.this.runOnUiThread(new Runnable() { // from class: android.view.SurfaceView$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SurfaceView.AnonymousClass1.this.lambda$setKeepScreenOn$0(z);
                }
            });
        }

        @Override // android.view.SurfaceHolder
        public Canvas lockCanvas() {
            return internalLockCanvas(null, false);
        }

        @Override // android.view.SurfaceHolder
        public Canvas lockCanvas(Rect rect) {
            return internalLockCanvas(rect, false);
        }

        @Override // android.view.SurfaceHolder
        public Canvas lockHardwareCanvas() {
            return internalLockCanvas(null, true);
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x006a  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0087  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0090  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private android.graphics.Canvas internalLockCanvas(android.graphics.Rect r6, boolean r7) {
            /*
                r5 = this;
                android.view.SurfaceView r0 = android.view.SurfaceView.this
                java.util.concurrent.locks.ReentrantLock r0 = r0.mSurfaceLock
                r0.lock()
                boolean r0 = android.view.SurfaceView.m5915$$Nest$sfgetDEBUG()
                java.lang.String r1 = "SurfaceView"
                if (r0 == 0) goto L3a
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                int r2 = java.lang.System.identityHashCode(r5)
                r0.append(r2)
                java.lang.String r2 = " Locking canvas... stopped="
                r0.append(r2)
                android.view.SurfaceView r2 = android.view.SurfaceView.this
                boolean r2 = r2.mDrawingStopped
                r0.append(r2)
                java.lang.String r2 = ", surfaceControl="
                r0.append(r2)
                android.view.SurfaceView r2 = android.view.SurfaceView.this
                android.view.SurfaceControl r2 = r2.mSurfaceControl
                r0.append(r2)
                java.lang.String r0 = r0.toString()
                android.util.Log.i(r1, r0)
            L3a:
                android.view.SurfaceView r0 = android.view.SurfaceView.this
                boolean r0 = r0.mDrawingStopped
                r2 = 0
                if (r0 != 0) goto L63
                android.view.SurfaceView r0 = android.view.SurfaceView.this
                android.view.SurfaceControl r0 = r0.mSurfaceControl
                if (r0 == 0) goto L63
                if (r7 == 0) goto L52
                android.view.SurfaceView r6 = android.view.SurfaceView.this     // Catch: java.lang.Exception -> L5b
                android.view.Surface r6 = r6.mSurface     // Catch: java.lang.Exception -> L5b
                android.graphics.Canvas r6 = r6.lockHardwareCanvas()     // Catch: java.lang.Exception -> L5b
                goto L64
            L52:
                android.view.SurfaceView r7 = android.view.SurfaceView.this     // Catch: java.lang.Exception -> L5b
                android.view.Surface r7 = r7.mSurface     // Catch: java.lang.Exception -> L5b
                android.graphics.Canvas r6 = r7.lockCanvas(r6)     // Catch: java.lang.Exception -> L5b
                goto L64
            L5b:
                r6 = move-exception
                java.lang.String r7 = "SurfaceHolder"
                java.lang.String r0 = "Exception locking surface"
                android.util.Log.e(r7, r0, r6)
            L63:
                r6 = r2
            L64:
                boolean r7 = android.view.SurfaceView.m5915$$Nest$sfgetDEBUG()
                if (r7 == 0) goto L85
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                int r0 = java.lang.System.identityHashCode(r5)
                r7.append(r0)
                java.lang.String r0 = " Returned canvas: "
                r7.append(r0)
                r7.append(r6)
                java.lang.String r7 = r7.toString()
                android.util.Log.i(r1, r7)
            L85:
                if (r6 == 0) goto L90
                android.view.SurfaceView r5 = android.view.SurfaceView.this
                long r0 = android.os.SystemClock.uptimeMillis()
                r5.mLastLockTime = r0
                return r6
            L90:
                long r6 = android.os.SystemClock.uptimeMillis()
                android.view.SurfaceView r0 = android.view.SurfaceView.this
                long r0 = r0.mLastLockTime
                r3 = 100
                long r0 = r0 + r3
                int r3 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
                if (r3 <= 0) goto La7
                long r0 = r0 - r6
                java.lang.Thread.sleep(r0)     // Catch: java.lang.InterruptedException -> La3
            La3:
                long r6 = android.os.SystemClock.uptimeMillis()
            La7:
                android.view.SurfaceView r0 = android.view.SurfaceView.this
                r0.mLastLockTime = r6
                android.view.SurfaceView r5 = android.view.SurfaceView.this
                java.util.concurrent.locks.ReentrantLock r5 = r5.mSurfaceLock
                r5.unlock()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.SurfaceView.AnonymousClass1.internalLockCanvas(android.graphics.Rect, boolean):android.graphics.Canvas");
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
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return null;
        }
        return viewRootImpl.getInputToken();
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void surfaceCreated(SurfaceControl.Transaction transaction) {
        setWindowStopped(false);
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void surfaceDestroyed() {
        setWindowStopped(true);
        this.mRemoteAccessibilityController.disassosciateHierarchy();
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void surfaceReplaced(SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl == null || this.mBackgroundControl == null) {
            return;
        }
        updateRelativeZ(transaction);
    }

    private void updateRelativeZ(SurfaceControl.Transaction transaction) {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return;
        }
        SurfaceControl surfaceControl = viewRootImpl.getSurfaceControl();
        transaction.setRelativeLayer(this.mBackgroundControl, surfaceControl, Integer.MIN_VALUE);
        transaction.setRelativeLayer(this.mSurfaceControl, surfaceControl, this.mSubLayer);
    }

    public void setChildSurfacePackage(SurfaceControlViewHost.SurfacePackage surfacePackage) {
        SurfaceControlViewHost.SurfacePackage surfacePackage2 = this.mSurfacePackage;
        SurfaceControl surfaceControl = surfacePackage2 != null ? surfacePackage2.getSurfaceControl() : null;
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        if (this.mSurfaceControl != null) {
            if (surfaceControl != null) {
                transaction.reparent(surfaceControl, null);
                this.mSurfacePackage.release();
            }
            reparentSurfacePackage(transaction, surfacePackage);
            applyTransactionOnVriDraw(transaction);
        }
        this.mSurfacePackage = surfacePackage;
        this.mSurfaceControlViewHostParent.attach(this);
        if (isFocused()) {
            requestEmbeddedFocus(true);
        }
        invalidate();
    }

    public SurfaceControlViewHost.SurfacePackage getChildSurfacePackage() {
        return this.mSurfacePackage;
    }

    public void clearChildSurfacePackage() {
        if (this.mSurfacePackage != null) {
            this.mSurfaceControlViewHostParent.detach();
            this.mEmbeddedWindowParams.clear();
            SurfaceControl surfaceControl = this.mSurfacePackage.getSurfaceControl();
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.reparent(surfaceControl, null);
            this.mSurfacePackage.release();
            applyTransactionOnVriDraw(transaction);
            this.mSurfacePackage = null;
            invalidate();
        }
    }

    private void reparentSurfacePackage(SurfaceControl.Transaction transaction, SurfaceControlViewHost.SurfacePackage surfacePackage) {
        SurfaceControl surfaceControl = surfacePackage.getSurfaceControl();
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        initEmbeddedHierarchyForAccessibility(surfacePackage);
        transaction.reparent(surfaceControl, this.mBlastSurfaceControl).show(surfaceControl);
    }

    @Override // android.view.View
    protected void onProvideStructure(ViewStructure viewStructure, int i, int i2) {
        super.onProvideStructure(viewStructure, i, i2);
        if (!android.app.contextualsearch.flags.Flags.reportSecureSurfacesInAssistStructure() || (this.mSurfaceFlags & 128) == 0) {
            return;
        }
        viewStructure.getExtras().putBoolean(ViewStructure.EXTRA_CONTAINS_SECURE_LAYERS, true);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.mRemoteAccessibilityController.connected()) {
            accessibilityNodeInfo.addChild(this.mRemoteAccessibilityController.getLeashToken());
        }
    }

    @Override // android.view.View
    public int getImportantForAccessibility() {
        int importantForAccessibility = super.getImportantForAccessibility();
        RemoteAccessibilityController remoteAccessibilityController = this.mRemoteAccessibilityController;
        if ((remoteAccessibilityController == null || remoteAccessibilityController.connected()) && importantForAccessibility == 0) {
            return 1;
        }
        return importantForAccessibility;
    }

    private void initEmbeddedHierarchyForAccessibility(SurfaceControlViewHost.SurfacePackage surfacePackage) {
        IAccessibilityEmbeddedConnection accessibilityEmbeddedConnection = surfacePackage.getAccessibilityEmbeddedConnection();
        if (this.mRemoteAccessibilityController.alreadyAssociated(accessibilityEmbeddedConnection)) {
            return;
        }
        this.mRemoteAccessibilityController.assosciateHierarchy(accessibilityEmbeddedConnection, getViewRootImpl().mLeashToken, getAccessibilityViewId());
        updateEmbeddedAccessibilityMatrix(true);
    }

    private void notifySurfaceDestroyed() {
        if (this.mSurface.isValid()) {
            if (DEBUG) {
                Log.i(TAG, System.identityHashCode(this) + " surfaceDestroyed");
            }
            EventLog.writeEvent(60006, this.mTag, "surfaceDestroyed");
            SurfaceHolder.Callback[] surfaceCallbacks = getSurfaceCallbacks();
            Log.i(this.mTag, "surfaceDestroyed callback.size " + this.mCallbacks.size() + " #" + this.mUpdateSurfaceCalledBy + " " + this);
            for (SurfaceHolder.Callback callback : surfaceCallbacks) {
                callback.surfaceDestroyed(this.mSurfaceHolder);
            }
            if (this.mSurface.isValid()) {
                this.mSurface.forceScopedDisconnect();
            }
        }
    }

    void updateEmbeddedAccessibilityMatrix(boolean z) {
        if (this.mRemoteAccessibilityController.connected()) {
            getBoundsOnScreen(this.mTmpRect);
            this.mTmpRect.offset(-this.mAttachInfo.mWindowLeft, -this.mAttachInfo.mWindowTop);
            this.mTmpMatrix.reset();
            this.mTmpMatrix.setTranslate(this.mTmpRect.left, this.mTmpRect.top);
            this.mTmpMatrix.postScale(this.mScreenRect.width() / this.mSurfaceWidth, this.mScreenRect.height() / this.mSurfaceHeight);
            this.mRemoteAccessibilityController.setWindowMatrix(this.mTmpMatrix, z);
        }
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        requestEmbeddedFocus(z);
    }

    private void requestEmbeddedFocus(boolean z) {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (this.mSurfacePackage == null || viewRootImpl == null) {
            return;
        }
        if (viewRootImpl.mWindowAttributes.type == 3 && (viewRootImpl.mWindowAttributes.flags & 8) != 0) {
            Slog.d(TAG, "requestEmbeddedFocus: caller=" + Debug.getCallers(4));
            z = false;
        }
        try {
            viewRootImpl.mWindowSession.grantEmbeddedWindowFocus(viewRootImpl.mWindow, this.mSurfacePackage.getInputTransferToken(), z);
        } catch (Exception e) {
            Log.e(TAG, System.identityHashCode(this) + "Exception requesting focus on embedded window", e);
        }
    }

    private void applyTransactionOnVriDraw(SurfaceControl.Transaction transaction) {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.applyTransactionOnDraw(transaction);
        } else {
            transaction.apply();
        }
    }

    public void syncNextFrame(Consumer<SurfaceControl.Transaction> consumer) {
        this.mBlastBufferQueue.syncNextTransaction(consumer);
    }

    public void applyTransactionToFrame(SurfaceControl.Transaction transaction) {
        synchronized (this.mSurfaceControlLock) {
            BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
            if (bLASTBufferQueue == null) {
                throw new IllegalStateException("Surface does not exist!");
            }
            this.mBlastBufferQueue.mergeWithNextTransaction(transaction, bLASTBufferQueue.getLastAcquiredFrameNum() + 1);
        }
    }

    @Override // android.view.View
    void performCollectViewAttributes(View.AttachInfo attachInfo, int i) {
        super.performCollectViewAttributes(attachInfo, i);
        if (this.mEmbeddedWindowParams.isEmpty()) {
            return;
        }
        Iterator<WindowManager.LayoutParams> it = this.mEmbeddedWindowParams.iterator();
        while (it.hasNext()) {
            if ((it.next().flags & 128) == 128) {
                attachInfo.mKeepScreenOn = true;
                return;
            }
        }
    }

    @Override // android.view.View
    public CharSequence getAccessibilityClassName() {
        return SurfaceView.class.getName();
    }
}
