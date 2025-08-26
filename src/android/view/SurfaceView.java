package android.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Resources;
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
import android.media.TtmlUtils;
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
    public /* synthetic */ boolean lambda$new$0() throws Throwable {
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
                        SurfaceView.SurfaceControlViewHostParent.lambda$updateParams$0(surfaceView);
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
                    SurfaceView.SurfaceControlViewHostParent.lambda$forwardBackKeyToParent$1(surfaceView, keyEvent);
                }
            });
        }

        static /* synthetic */ void lambda$forwardBackKeyToParent$1(SurfaceView surfaceView, KeyEvent keyEvent) {
            ViewRootImpl viewRootImpl;
            InputManager inputManager;
            if (!surfaceView.isAttachedToWindow() || keyEvent.getKeyCode() != 4 || (viewRootImpl = surfaceView.getViewRootImpl()) == null || (inputManager = (InputManager) surfaceView.mContext.getSystemService(InputManager.class)) == null) {
                return;
            }
            long jUptimeMillis = SystemClock.uptimeMillis() - keyEvent.getEventTime();
            if (jUptimeMillis > SurfaceView.FORWARD_BACK_KEY_TOLERANCE_MS) {
                Log.e(SurfaceView.TAG, "Ignore the input event that exceed the tolerance time, exceed " + jUptimeMillis + "ms");
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
            public final void onScrollChanged() throws Throwable {
                this.f$0.updateSurface();
            }
        };
        this.mDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                return this.f$0.lambda$new$0();
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

    private void setWindowStopped(boolean z) throws Throwable {
        this.mWindowStopped = z;
        updateRequestedVisibility();
        this.mUpdateSurfaceCalledBy = 1;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            Log.i(this.mTag, "windowStopped(" + z + ") " + this.mRequestedVisible + " " + this + " of " + viewRootImpl.getTag());
        }
        updateSurface();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setTag() {
        String str;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            String[] strArrSplit = viewRootImpl.mWindowAttributes.getTitle().toString().split("\\.");
            if (strArrSplit.length > 0) {
                str = " " + strArrSplit[strArrSplit.length - 1];
            } else {
                str = "";
            }
        }
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
    protected void onWindowVisibilityChanged(int i) throws Throwable {
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
    public void setVisibility(int i) throws Throwable {
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
    protected boolean onSetAlpha(int i) throws Throwable {
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
    protected void onDetachedFromWindow() throws Throwable {
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
    protected boolean setFrame(int i, int i2, int i3, int i4) throws Throwable {
        boolean frame = super.setFrame(i, i2, i3, i4);
        this.mUpdateSurfaceCalledBy = 5;
        updateSurface();
        return frame;
    }

    @Override // android.view.View
    public boolean gatherTransparentRegion(Region region) {
        boolean zGatherTransparentRegion;
        if (isAboveParent() || !this.mDrawFinished) {
            return super.gatherTransparentRegion(region);
        }
        if ((this.mPrivateFlags & 128) == 0) {
            zGatherTransparentRegion = super.gatherTransparentRegion(region);
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
            zGatherTransparentRegion = true;
        }
        if (PixelFormat.formatHasAlpha(this.mRequestedFormat)) {
            return false;
        }
        return zGatherTransparentRegion;
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

    public void setCompositionOrder(int i) throws Throwable {
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
    public void setZOrderOnTop(boolean z) throws Throwable {
        setZOrderedOnTop(z, getContext().getApplicationInfo().targetSdkVersion > 29);
    }

    public boolean isZOrderedOnTop() {
        return this.mRequestedSubLayer > 0;
    }

    @Deprecated
    public boolean setZOrderedOnTop(boolean z, boolean z2) throws Throwable {
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

    public void setSurfaceLifecycle(int i) throws Throwable {
        this.mRequestedSurfaceLifecycleStrategy = i;
        updateSurface();
    }

    public void setDesiredHdrHeadroom(float f) throws Throwable {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:269:0x05b0  */
    /* JADX WARN: Removed duplicated region for block: B:288:? A[RETURN, SYNTHETIC] */
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
    */
    protected void updateSurface() throws Throwable {
        boolean z;
        boolean z2;
        ?? r4;
        ?? r2;
        String str;
        String str2;
        SurfaceView surfaceView;
        boolean z3;
        boolean z4;
        int i;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        int i2;
        boolean z10;
        SyncBufferTransactionCallback syncBufferTransactionCallback;
        String str3;
        String str4;
        boolean z11;
        boolean z12;
        SurfaceHolder.Callback[] surfaceCallbacks;
        String str5;
        String str6;
        ?? r1 = this;
        if (!r1.mHaveFrame) {
            if (DEBUG) {
                Log.d(TAG, System.identityHashCode(r1) + " updateSurface: has no frame");
                return;
            }
            return;
        }
        ViewRootImpl viewRootImpl = r1.getViewRootImpl();
        if (viewRootImpl == null) {
            return;
        }
        if (viewRootImpl.mSurface == null || !viewRootImpl.mSurface.isValid()) {
            Log.d(r1.mTag, "updateSurface: surface is not valid");
            r1.notifySurfaceDestroyed();
            r1.releaseSurfaces(false);
            return;
        }
        CompatibilityInfo.Translator translator = viewRootImpl.mTranslator;
        if (translator != null) {
            r1.mSurface.setCompatibilityTranslator(translator);
        }
        int width = r1.mRequestedWidth;
        if (width <= 0) {
            width = r1.getWidth();
        }
        int height = r1.mRequestedHeight;
        if (height <= 0) {
            height = r1.getHeight();
        }
        float alpha = r1.getAlpha();
        boolean z13 = r1.mFormat != r1.mRequestedFormat;
        boolean z14 = r1.mVisible != r1.mRequestedVisible;
        boolean z15 = r1.mAlpha != alpha;
        boolean zRequiresSurfaceControlCreation = r1.requiresSurfaceControlCreation(z13, z14);
        boolean z16 = (r1.mSurfaceWidth == width && r1.mSurfaceHeight == height) ? false : true;
        boolean z17 = r1.mWindowVisibility != r1.mLastWindowVisibility;
        r1.getLocationInWindow(r1.mLocation);
        int i3 = r1.mWindowSpaceLeft;
        boolean z18 = z17;
        int[] iArr = r1.mLocation;
        boolean z19 = (i3 == iArr[0] && r1.mWindowSpaceTop == iArr[1]) ? false : true;
        boolean z20 = (r1.getWidth() == r1.mScreenRect.width() && r1.getHeight() == r1.mScreenRect.height()) ? false : true;
        boolean z21 = viewRootImpl.getBufferTransformHint() != r1.mTransformHint && r1.mRequestedVisible;
        boolean z22 = r1.mSubLayer != r1.mRequestedSubLayer;
        boolean z23 = r1.mSurfaceLifecycleStrategy != r1.mRequestedSurfaceLifecycleStrategy;
        boolean z24 = r1.mHdrHeadroom != r1.mRequestedHdrHeadroom;
        boolean zIsWindowOpaque = viewRootImpl.isWindowOpaque();
        boolean z25 = r1.mIsWindowOpaque != zIsWindowOpaque;
        if (z25) {
            r1.mIsWindowOpaque = zIsWindowOpaque;
        }
        if (zRequiresSurfaceControlCreation || z13 || z16 || z14 || z15 || z18 || z19 || z20 || z21 || z22 || !r1.mAttachedToWindow || z23 || z24 || z25) {
            boolean z26 = DEBUG;
            if (z26) {
                StringBuilder sb = new StringBuilder();
                z2 = z26;
                sb.append(System.identityHashCode(r1));
                sb.append(" Changes: creating=");
                sb.append(zRequiresSurfaceControlCreation);
                sb.append(" format=");
                sb.append(z13);
                sb.append(" size=");
                sb.append(z16);
                sb.append(" visible=");
                sb.append(z14);
                sb.append(" alpha=");
                sb.append(z15);
                sb.append(" hint=");
                sb.append(z21);
                sb.append(" left=");
                z = z21;
                sb.append(r1.mWindowSpaceLeft != r1.mLocation[0]);
                sb.append(" top=");
                sb.append(r1.mWindowSpaceTop != r1.mLocation[1]);
                sb.append(" z=");
                sb.append(z22);
                sb.append(" attached=");
                sb.append(r1.mAttachedToWindow);
                sb.append(" lifecycleStrategy=");
                sb.append(z23);
                Log.i(TAG, sb.toString());
            } else {
                z = z21;
                z2 = z26;
            }
            if (zRequiresSurfaceControlCreation || z13 || z16 || z14 || z20 || z22 || !r1.mAttachedToWindow || z23) {
                EventLog.writeEvent(60005, r1.mTag, Integer.valueOf(r1.mRequestedFormat), Integer.valueOf(width), Integer.valueOf(height), Integer.valueOf(r1.mRequestedSubLayer), r1.mRequestedWidth > 0 ? "setFixedSize" : TtmlUtils.TAG_LAYOUT, Integer.valueOf(r1.mAttachedToWindow ? 1 : 0), Integer.valueOf(r1.mRequestedSurfaceLifecycleStrategy), Integer.valueOf(r1.mRequestedVisible ? 1 : 0));
            }
            try {
                r1.mVisible = r1.mRequestedVisible;
                int[] iArr2 = r1.mLocation;
                r1.mWindowSpaceLeft = iArr2[0];
                r1.mWindowSpaceTop = iArr2[1];
                r1.mSurfaceWidth = width;
                r1.mSurfaceHeight = height;
                r1.mFormat = r1.mRequestedFormat;
                r1.mAlpha = alpha;
                r1.mLastWindowVisibility = r1.mWindowVisibility;
                r1.mTransformHint = viewRootImpl.getBufferTransformHint();
                r1.mSubLayer = r1.mRequestedSubLayer;
                int i4 = r1.mSurfaceLifecycleStrategy;
                r1.mSurfaceLifecycleStrategy = r1.mRequestedSurfaceLifecycleStrategy;
                r1.mHdrHeadroom = r1.mRequestedHdrHeadroom;
                r1.mScreenRect.left = r1.mWindowSpaceLeft;
                r1.mScreenRect.top = r1.mWindowSpaceTop;
                r1.mScreenRect.right = r1.mWindowSpaceLeft + r1.getWidth();
                r1.mScreenRect.bottom = r1.mWindowSpaceTop + r1.getHeight();
                if (translator != null) {
                    translator.translateRectInAppWindowToScreen(r1.mScreenRect);
                }
                ViewRootImpl viewRootImpl2 = viewRootImpl;
                Rect rect = viewRootImpl2.mWindowAttributes.surfaceInsets;
                r1.mScreenRect.offset(rect.left, rect.top);
                boolean z27 = z15;
                SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                if (zRequiresSurfaceControlCreation) {
                    r1.updateOpaqueFlag();
                    r1.createBlastSurfaceControls(viewRootImpl2, Integer.toHexString(System.identityHashCode(r1)) + " SurfaceView[" + viewRootImpl2.getTitle().toString() + "]@" + r1.mSurfaceCreatedCount, transaction);
                } else if (r1.mSurfaceControl == null) {
                    return;
                }
                boolean z28 = z16 || zRequiresSurfaceControlCreation || z || (r1.mVisible && !r1.mDrawFinished) || z27 || z22;
                boolean z29 = z28 && viewRootImpl2.wasRelayoutRequested() && viewRootImpl2.isInWMSRequestedSync();
                if (z29) {
                    try {
                        final SyncBufferTransactionCallback syncBufferTransactionCallback2 = new SyncBufferTransactionCallback();
                        r1.mBlastBufferQueue.syncNextTransaction(false, new Consumer() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                syncBufferTransactionCallback2.onTransactionReady((SurfaceControl.Transaction) obj);
                            }
                        });
                        z3 = z;
                        r2 = viewRootImpl2;
                        z4 = z28;
                        i = width;
                        r4 = zRequiresSurfaceControlCreation;
                        z5 = z13;
                        z6 = z22;
                        z7 = z14;
                        z8 = z24;
                        z9 = z29;
                        i2 = height;
                        z10 = z16;
                        syncBufferTransactionCallback = syncBufferTransactionCallback2;
                    } catch (Exception e) {
                        e = e;
                        r1 = this;
                        r4 = " h=";
                        r2 = " w=";
                        Log.e(TAG, "Exception configuring surface", e);
                        surfaceView = r1;
                        str2 = r2;
                        str = r4;
                        if (DEBUG) {
                        }
                    }
                } else {
                    z3 = z;
                    z4 = z28;
                    i = width;
                    r4 = zRequiresSurfaceControlCreation;
                    z5 = z13;
                    z6 = z22;
                    z7 = z14;
                    z8 = z24;
                    z9 = z29;
                    i2 = height;
                    z10 = z16;
                    syncBufferTransactionCallback = null;
                    r2 = viewRootImpl2;
                }
                r1 = this;
                try {
                    boolean zPerformSurfaceTransaction = r1.performSurfaceTransaction(r2, translator, r4, z10, z3, z6, z8, transaction);
                    try {
                        boolean z30 = r1.mSurfaceLifecycleStrategy != 2;
                        boolean z31 = z30 && (i4 == 2);
                        if (r1.mSurfaceCreated && (r4 != 0 || ((!z30 && !r1.mAttachedToWindow) || (z30 && !r1.mVisible && (z7 || z31))))) {
                            r1.mSurfaceCreated = false;
                            r1.notifySurfaceDestroyed();
                        }
                        r1.copySurface(r4, z10);
                        Log.i(r1.mTag, "updateSurface: mVisible = " + r1.mVisible + " mSurface.isValid() = " + r1.mSurface.isValid());
                        if (r1.surfaceShouldExist() && r1.mSurface.isValid()) {
                            Log.i(r1.mTag, "updateSurface: mSurfaceCreated = " + r1.mSurfaceCreated + " surfaceChanged = " + r4 + " visibleChanged = " + z7);
                            if (r1.mSurfaceCreated || (r4 == 0 && !(z30 && z7))) {
                                z11 = zPerformSurfaceTransaction;
                                z12 = r4;
                                surfaceCallbacks = null;
                            } else {
                                r1.mSurfaceCreated = true;
                                r1.mIsCreating = true;
                                if (z2) {
                                    Log.i(TAG, System.identityHashCode(r1) + " visibleChanged -- surfaceCreated");
                                }
                                EventLog.writeEvent(60006, r1.mTag, "surfaceCreated");
                                SurfaceHolder.Callback[] surfaceCallbacks2 = r1.getSurfaceCallbacks();
                                z11 = zPerformSurfaceTransaction;
                                z12 = r4;
                                Log.i(r1.mTag, "surfaceCreated " + r1.mCallbacks.size() + " #" + r1.mUpdateSurfaceCalledBy + " " + r1);
                                int length = surfaceCallbacks2.length;
                                int i5 = 0;
                                while (i5 < length) {
                                    surfaceCallbacks2[i5].surfaceCreated(r1.mSurfaceHolder);
                                    i5++;
                                    surfaceCallbacks2 = surfaceCallbacks2;
                                }
                                surfaceCallbacks = surfaceCallbacks2;
                            }
                            if (z12 || z5 || z10 || z3 || ((z30 && z7) || z11)) {
                                if (DEBUG) {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(System.identityHashCode(r1));
                                    sb2.append(" surfaceChanged -- format=");
                                    sb2.append(r1.mFormat);
                                    String str7 = " w=";
                                    try {
                                        sb2.append(str7);
                                        sb2.append(i);
                                        String str8 = " h=";
                                        try {
                                            sb2.append(str8);
                                            sb2.append(i2);
                                            Log.i(TAG, sb2.toString());
                                            str6 = str7;
                                            str5 = str8;
                                        } catch (Throwable th) {
                                            th = th;
                                            r1.mIsCreating = false;
                                            if (r1.mSurfaceControl != null && !r1.mSurfaceCreated) {
                                                r1.releaseSurfaces(false);
                                            }
                                            throw th;
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                    }
                                } else {
                                    str5 = " h=";
                                    str6 = " w=";
                                }
                                EventLog.writeEvent(60006, r1.mTag, "surfaceChanged -- format=" + r1.mFormat + str6 + i + str5 + i2);
                                if (surfaceCallbacks == null) {
                                    surfaceCallbacks = r1.getSurfaceCallbacks();
                                }
                                SurfaceHolder.Callback[] callbackArr = surfaceCallbacks;
                                Log.i(r1.mTag, "surfaceChanged (" + i + "," + i2 + ") " + r1.mCallbacks.size() + " #" + r1.mUpdateSurfaceCalledBy + " " + r1);
                                for (SurfaceHolder.Callback callback : callbackArr) {
                                    callback.surfaceChanged(r1.mSurfaceHolder, r1.mFormat, i, i2);
                                }
                                surfaceCallbacks = callbackArr;
                                str4 = str6;
                                str3 = str5;
                            } else {
                                str3 = " h=";
                                str4 = " w=";
                            }
                            if (z4) {
                                if (DEBUG) {
                                    Log.i(TAG, System.identityHashCode(r1) + " surfaceRedrawNeeded");
                                }
                                EventLog.writeEvent(60006, r1.mTag, "surfaceRedrawNeeded");
                                if (surfaceCallbacks == null) {
                                    surfaceCallbacks = r1.getSurfaceCallbacks();
                                }
                                SurfaceHolder.Callback[] callbackArr2 = surfaceCallbacks;
                                if (z9) {
                                    r1.handleSyncBufferCallback(callbackArr2, syncBufferTransactionCallback);
                                } else {
                                    r1.handleSyncNoBuffer(callbackArr2);
                                }
                            }
                        } else {
                            str3 = " h=";
                            str4 = " w=";
                        }
                        r1.mIsCreating = false;
                        SurfaceControl surfaceControl = r1.mSurfaceControl;
                        surfaceView = r1;
                        str2 = str4;
                        str = str3;
                        if (surfaceControl != null) {
                            boolean z32 = r1.mSurfaceCreated;
                            surfaceView = r1;
                            str2 = str4;
                            str = str3;
                            if (!z32) {
                                r1.releaseSurfaces(false);
                                surfaceView = r1;
                                str2 = str4;
                                str = str3;
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (Exception e2) {
                    e = e2;
                    Log.e(TAG, "Exception configuring surface", e);
                    surfaceView = r1;
                    str2 = r2;
                    str = r4;
                    if (DEBUG) {
                    }
                }
            } catch (Exception e3) {
                e = e3;
            }
            if (DEBUG) {
                Log.v(TAG, "Layout: x=" + surfaceView.mScreenRect.left + " y=" + surfaceView.mScreenRect.top + str2 + surfaceView.mScreenRect.width() + str + surfaceView.mScreenRect.height() + ", frame=" + surfaceView.mSurfaceFrame);
            }
        }
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
            public final void run() throws InterruptedException {
                this.f$0.lambda$handleSyncBufferCallback$1(syncBufferTransactionCallback, surfaceSyncGroup);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSyncBufferCallback$1(SyncBufferTransactionCallback syncBufferTransactionCallback, SurfaceSyncGroup surfaceSyncGroup) throws InterruptedException {
        SurfaceControl.Transaction transactionWaitForTransaction;
        BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            bLASTBufferQueue.stopContinuousSyncTransaction();
            transactionWaitForTransaction = syncBufferTransactionCallback.waitForTransaction();
        } else {
            transactionWaitForTransaction = null;
        }
        surfaceSyncGroup.addTransaction(transactionWaitForTransaction);
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
                this.f$0.lambda$handleSyncNoBuffer$2(surfaceSyncGroup);
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
                this.mSyncGroups.clear();
            } else {
                this.mSyncGroups.clear();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SyncBufferTransactionCallback {
        private final CountDownLatch mCountDownLatch;
        private SurfaceControl.Transaction mTransaction;

        private SyncBufferTransactionCallback() {
            this.mCountDownLatch = new CountDownLatch(1);
        }

        SurfaceControl.Transaction waitForTransaction() throws InterruptedException {
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
                this.f$0.performDrawFinished();
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
        public void setFormat(int i) throws Throwable {
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
                    this.f$0.lambda$setKeepScreenOn$0(z);
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

        private Canvas internalLockCanvas(Rect rect, boolean z) throws InterruptedException {
            Canvas canvasLockCanvas;
            SurfaceView.this.mSurfaceLock.lock();
            if (SurfaceView.DEBUG) {
                Log.i(SurfaceView.TAG, System.identityHashCode(this) + " Locking canvas... stopped=" + SurfaceView.this.mDrawingStopped + ", surfaceControl=" + SurfaceView.this.mSurfaceControl);
            }
            if (SurfaceView.this.mDrawingStopped || SurfaceView.this.mSurfaceControl == null) {
                canvasLockCanvas = null;
            } else {
                try {
                    if (z) {
                        canvasLockCanvas = SurfaceView.this.mSurface.lockHardwareCanvas();
                    } else {
                        canvasLockCanvas = SurfaceView.this.mSurface.lockCanvas(rect);
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Exception locking surface", e);
                }
            }
            if (SurfaceView.DEBUG) {
                Log.i(SurfaceView.TAG, System.identityHashCode(this) + " Returned canvas: " + canvasLockCanvas);
            }
            if (canvasLockCanvas != null) {
                SurfaceView.this.mLastLockTime = SystemClock.uptimeMillis();
                return canvasLockCanvas;
            }
            long jUptimeMillis = SystemClock.uptimeMillis();
            long j = SurfaceView.this.mLastLockTime + SurfaceView.FORWARD_BACK_KEY_TOLERANCE_MS;
            if (j > jUptimeMillis) {
                try {
                    Thread.sleep(j - jUptimeMillis);
                } catch (InterruptedException unused) {
                }
                jUptimeMillis = SystemClock.uptimeMillis();
            }
            SurfaceView.this.mLastLockTime = jUptimeMillis;
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
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return null;
        }
        return viewRootImpl.getInputToken();
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void surfaceCreated(SurfaceControl.Transaction transaction) throws Throwable {
        setWindowStopped(false);
    }

    @Override // android.view.ViewRootImpl.SurfaceChangedCallback
    public void surfaceDestroyed() throws Throwable {
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
    protected void onFocusChanged(boolean z, int i, Rect rect) throws Resources.NotFoundException {
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
