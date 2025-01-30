package android.view;

import android.content.Context;
import android.content.p002pm.PackageManager;
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
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Debug;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.SystemClock;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.secutil.Slog;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewDebug;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityEmbeddedConnection;
import android.window.SurfaceSyncGroup;
import com.android.internal.view.SurfaceCallbackHelper;
import com.samsung.android.ims.options.SemCapabilities;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class SurfaceView extends View implements ViewRootImpl.SurfaceChangedCallback {
    private static final boolean DEBUG = true;
    private static final boolean DEBUG_POSITION = true;
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
    int mFormat;
    private final SurfaceControl.Transaction mFrameCallbackTransaction;
    private boolean mGlobalListenersAdded;
    boolean mHaveFrame;
    boolean mIsCreating;
    private boolean mIsWindowOpaque;
    long mLastLockTime;
    int mLastSurfaceHeight;
    int mLastSurfaceWidth;
    boolean mLastWindowVisibility;
    final int[] mLocation;
    private int mParentSurfaceSequenceId;
    private SurfaceViewPositionUpdateListener mPositionListener;
    private final Rect mRTLastReportedPosition;
    private RemoteAccessibilityController mRemoteAccessibilityController;
    int mRequestedFormat;
    int mRequestedHeight;
    int mRequestedSubLayer;
    private int mRequestedSurfaceLifecycleStrategy;
    boolean mRequestedVisible;
    int mRequestedWidth;
    Paint mRoundedViewportPaint;
    private final SurfaceControl.Transaction mRtTransaction;
    final Rect mScreenRect;
    private final ViewTreeObserver.OnScrollChangedListener mScrollChangedListener;
    int mSubLayer;
    final Surface mSurface;

    @ViewDebug.ExportedProperty(category = TAG)
    SurfaceControl mSurfaceControl;
    final Object mSurfaceControlLock;
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
    public @interface SurfaceLifecycleStrategy {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0() {
        this.mHaveFrame = getWidth() > 0 && getHeight() > 0;
        this.mUpdateSurfaceCalledBy = 8;
        updateSurface();
        return true;
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

    public SurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean disableBackgroundLayer) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mCallbacks = new ArrayList<>();
        this.mLocation = new int[2];
        this.mSurfaceLock = new ReentrantLock();
        this.mSurface = new Surface();
        this.mDrawingStopped = true;
        this.mDrawFinished = false;
        this.mScreenRect = new Rect();
        this.mSurfaceSession = new SurfaceSession();
        this.mDisableBackgroundLayer = false;
        this.mRequestedSurfaceLifecycleStrategy = 0;
        this.mSurfaceLifecycleStrategy = 0;
        this.mSurfaceControlLock = new Object();
        this.mTmpRect = new Rect();
        this.mSubLayer = -2;
        this.mRequestedSubLayer = -2;
        this.mIsCreating = false;
        this.mUpdateSurfaceCalledBy = 0;
        this.mSurfaceCreatedCount = 0;
        this.mTag = TAG;
        this.mScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                SurfaceView.this.updateSurface();
            }
        };
        this.mDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda2
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
        this.mRTLastReportedPosition = new Rect();
        this.mPositionListener = null;
        this.mSurfaceHolder = new SurfaceHolderC36911();
        setWillNotDraw(true);
        this.mDisableBackgroundLayer = disableBackgroundLayer;
        this.mTag = "SurfaceView@" + Integer.toHexString(System.identityHashCode(this));
    }

    public SurfaceHolder getHolder() {
        return this.mSurfaceHolder;
    }

    private void updateRequestedVisibility() {
        this.mRequestedVisible = this.mViewVisibility && this.mWindowVisibility && !this.mWindowStopped;
    }

    private void setWindowStopped(boolean stopped) {
        this.mWindowStopped = stopped;
        updateRequestedVisibility();
        this.mUpdateSurfaceCalledBy = 1;
        ViewRootImpl viewRoot = getViewRootImpl();
        if (viewRoot != null) {
            Log.m98i(this.mTag, "windowStopped(" + stopped + ") " + this.mRequestedVisible + " " + this + " of " + viewRoot.getTag());
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
            Log.m98i(this.mTag, "onWindowVisibilityChanged(" + visibility + ") " + this.mRequestedVisible + " " + this + " of " + viewRoot.getTag());
        }
        updateSurface();
    }

    @Override // android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        boolean z = visibility == 0;
        this.mViewVisibility = z;
        boolean newRequestedVisible = this.mWindowVisibility && z && !this.mWindowStopped;
        if (newRequestedVisible != this.mRequestedVisible) {
            requestLayout();
        }
        this.mRequestedVisible = newRequestedVisible;
        this.mUpdateSurfaceCalledBy = 3;
        updateSurface();
    }

    public void setUseAlpha() {
    }

    @Override // android.view.View
    public void setAlpha(float alpha) {
        if (DEBUG) {
            Log.m94d(this.mTag, System.identityHashCode(this) + " setAlpha: alpha=" + alpha);
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
            Log.m98i(TAG, System.identityHashCode(this) + " Detaching SV");
        }
        this.mRequestedVisible = false;
        this.mUpdateSurfaceCalledBy = 4;
        updateSurface();
        Log.m98i(this.mTag, "onDetachedFromWindow: tryReleaseSurfaces()");
        releaseSurfaces(true);
        this.mHaveFrame = false;
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        int i = this.mRequestedWidth;
        if (i >= 0) {
            width = resolveSizeAndState(i, widthMeasureSpec, 0);
        } else {
            width = getDefaultSize(0, widthMeasureSpec);
        }
        int i2 = this.mRequestedHeight;
        if (i2 >= 0) {
            height = resolveSizeAndState(i2, heightMeasureSpec, 0);
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
                int[] iArr = this.mLocation;
                int l = iArr[0];
                int t = iArr[1];
                region.m20op(l, t, l + w, t + h, Region.EnumC0836Op.UNION);
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
        if (!this.mClipSurfaceToBounds || this.mSurfaceControl == null) {
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

    public void setCornerRadius(float cornerRadius) {
        this.mCornerRadius = cornerRadius;
        if (cornerRadius > 0.0f && this.mRoundedViewportPaint == null) {
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

    private void updateOpaqueFlag() {
        if (!PixelFormat.formatHasAlpha(this.mRequestedFormat)) {
            this.mSurfaceFlags |= 1024;
        } else {
            this.mSurfaceFlags &= -1025;
        }
    }

    private void updateBackgroundVisibility(SurfaceControl.Transaction t) {
        SurfaceControl surfaceControl = this.mBackgroundControl;
        if (surfaceControl == null) {
            return;
        }
        if (this.mSubLayer < 0 && (this.mSurfaceFlags & 1024) != 0 && !this.mDisableBackgroundLayer) {
            if (!this.mIsWindowOpaque && getResources().getConfiguration().windowConfiguration.getWindowingMode() == 5) {
                t.hide(this.mBackgroundControl);
                return;
            } else {
                t.show(this.mBackgroundControl);
                return;
            }
        }
        t.hide(surfaceControl);
    }

    private SurfaceControl.Transaction updateBackgroundColor(SurfaceControl.Transaction t) {
        float[] colorComponents = {Color.red(this.mBackgroundColor) / 255.0f, Color.green(this.mBackgroundColor) / 255.0f, Color.blue(this.mBackgroundColor) / 255.0f};
        t.setColor(this.mBackgroundControl, colorComponents);
        return t;
    }

    private void releaseSurfaces(boolean releaseSurfacePackage) {
        SurfaceControlViewHost.SurfacePackage surfacePackage;
        this.mAlpha = 1.0f;
        this.mSurface.destroy();
        synchronized (this.mSurfaceControlLock) {
            BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
            if (bLASTBufferQueue != null) {
                bLASTBufferQueue.destroy();
                this.mBlastBufferQueue = null;
            }
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            ViewRootImpl viewRoot = getViewRootImpl();
            Log.m98i(this.mTag, "releaseSurfaces: viewRoot = " + (viewRoot != null ? viewRoot.getTag() : SemCapabilities.FEATURE_TAG_NULL));
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
            if (releaseSurfacePackage && (surfacePackage = this.mSurfacePackage) != null) {
                surfacePackage.release();
                this.mSurfacePackage = null;
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

    private boolean performSurfaceTransaction(ViewRootImpl viewRoot, CompatibilityInfo.Translator translator, boolean creating, boolean sizeChanged, boolean hintChanged, boolean relativeZChanged, SurfaceControl.Transaction surfaceUpdateTransaction) {
        this.mSurfaceLock.lock();
        try {
            boolean z = true;
            this.mDrawingStopped = !surfaceShouldExist();
            if (DEBUG) {
                Log.m98i(this.mTag, System.identityHashCode(this) + " Cur surface: " + this.mSurface);
            }
            if (creating) {
                updateRelativeZ(surfaceUpdateTransaction);
                SurfaceControlViewHost.SurfacePackage surfacePackage = this.mSurfacePackage;
                if (surfacePackage != null) {
                    reparentSurfacePackage(surfaceUpdateTransaction, surfacePackage);
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
                if (!this.mClipSurfaceToBounds || this.mClipBounds == null) {
                    surfaceUpdateTransaction.setWindowCrop(this.mSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight);
                } else {
                    surfaceUpdateTransaction.setWindowCrop(this.mSurfaceControl, this.mClipBounds);
                }
                Log.m98i(this.mTag, "pST: sr = " + this.mScreenRect + " sw = " + this.mSurfaceWidth + " sh = " + this.mSurfaceHeight);
                surfaceUpdateTransaction.setDesintationFrame(this.mBlastSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight);
                if (isHardwareAccelerated()) {
                    replacePositionUpdateListener(this.mSurfaceWidth, this.mSurfaceHeight);
                } else {
                    onSetSurfacePositionAndScale(surfaceUpdateTransaction, this.mSurfaceControl, this.mScreenRect.left, this.mScreenRect.top, this.mScreenRect.width() / this.mSurfaceWidth, this.mScreenRect.height() / this.mSurfaceHeight);
                }
                if (DEBUG_POSITION) {
                    String str = this.mTag;
                    Object[] objArr = new Object[8];
                    objArr[0] = Integer.valueOf(System.identityHashCode(this));
                    objArr[1] = isHardwareAccelerated() ? "RenderWorker" : "UI Thread";
                    objArr[2] = Integer.valueOf(this.mScreenRect.left);
                    objArr[3] = Integer.valueOf(this.mScreenRect.top);
                    objArr[4] = Integer.valueOf(this.mScreenRect.right);
                    objArr[5] = Integer.valueOf(this.mScreenRect.bottom);
                    objArr[6] = Integer.valueOf(this.mSurfaceWidth);
                    objArr[7] = Integer.valueOf(this.mSurfaceHeight);
                    Log.m94d(str, String.format("%d performSurfaceTransaction %s position = [%d, %d, %d, %d] surfaceSize = %dx%d", objArr));
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
            if (this.mLastSurfaceWidth == surfaceWidth && this.mLastSurfaceHeight == surfaceHeight) {
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
        return this.mSurfaceLifecycleStrategy == 2 ? (this.mSurfaceControl == null || formatChanged) && this.mAttachedToWindow : (this.mSurfaceControl == null || formatChanged || visibleChanged) && this.mRequestedVisible;
    }

    private boolean surfaceShouldExist() {
        boolean respectVisibility = this.mSurfaceLifecycleStrategy != 2;
        if (this.mVisible) {
            return true;
        }
        return !respectVisibility && this.mAttachedToWindow;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:(11:(31:122|123|(2:127|(26:129|130|(9:291|292|293|294|295|296|297|298|299)(1:132)|133|134|135|136|137|138|(1:140)(1:283)|141|(1:143)(1:282)|144|(1:281)(1:147)|148|(2:(2:(2:157|158)|(2:153|(1:(0))))|168)|170|171|172|(1:277)(5:176|177|(1:275)(1:(11:253|254|255|(2:268|269)(1:257)|258|259|260|261|(2:263|264)|265|266)(1:182))|(12:213|214|(7:216|217|218|219|220|221|222)(1:249)|(2:224|225)(1:240)|226|227|228|229|230|(1:232)|233|234)(1:190)|(4:192|(1:194)|(1:196)(1:200)|(1:198)(1:199)))|201|202|(1:206)|207|208|(2:210|211)(1:212)))|312|130|(0)(0)|133|134|135|136|137|138|(0)(0)|141|(0)(0)|144|(0)|281|148|(0)|170|171|172|(1:174)|277|201|202|(2:204|206)|207|208|(0)(0))|171|172|(0)|277|201|202|(0)|207|208|(0)(0))|137|138|(0)(0)|141|(0)(0)|144|(0)|281|148|(0)|170) */
    /* JADX WARN: Can't wrap try/catch for region: R(14:89|(5:91|(1:93)(1:337)|94|(1:96)(1:336)|97)(1:338)|(4:98|99|(5:322|323|324|325|326)(1:101)|(4:102|103|104|(4:105|106|107|(2:315|316)(2:109|(1:111)))))|(22:(31:122|123|(2:127|(26:129|130|(9:291|292|293|294|295|296|297|298|299)(1:132)|133|134|135|136|137|138|(1:140)(1:283)|141|(1:143)(1:282)|144|(1:281)(1:147)|148|(2:(2:(2:157|158)|(2:153|(1:(0))))|168)|170|171|172|(1:277)(5:176|177|(1:275)(1:(11:253|254|255|(2:268|269)(1:257)|258|259|260|261|(2:263|264)|265|266)(1:182))|(12:213|214|(7:216|217|218|219|220|221|222)(1:249)|(2:224|225)(1:240)|226|227|228|229|230|(1:232)|233|234)(1:190)|(4:192|(1:194)|(1:196)(1:200)|(1:198)(1:199)))|201|202|(1:206)|207|208|(2:210|211)(1:212)))|312|130|(0)(0)|133|134|135|136|137|138|(0)(0)|141|(0)(0)|144|(0)|281|148|(0)|170|171|172|(1:174)|277|201|202|(2:204|206)|207|208|(0)(0))|137|138|(0)(0)|141|(0)(0)|144|(0)|281|148|(0)|170|171|172|(0)|277|201|202|(0)|207|208|(0)(0))|313|123|(3:125|127|(0))|312|130|(0)(0)|133|134|135|136) */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0402, code lost:
    
        if (r6 != false) goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x03f8, code lost:
    
        if (r42.mAttachedToWindow != false) goto L193;
     */
    /* JADX WARN: Code restructure failed: missing block: B:284:0x068d, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x0690, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x0691, code lost:
    
        r6 = " h=";
        r3 = " w=";
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0328  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x039f  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x03e3  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x03e9 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0683 A[Catch: Exception -> 0x068d, TryCatch #5 {Exception -> 0x068d, blocks: (B:162:0x067c, B:164:0x0683, B:166:0x0687, B:167:0x068c, B:202:0x0655, B:204:0x065b, B:206:0x065f), top: B:137:0x03d8 }] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0456 A[Catch: all -> 0x0665, TryCatch #6 {all -> 0x0665, blocks: (B:172:0x041e, B:174:0x0456, B:176:0x045e), top: B:171:0x041e }] */
    /* JADX WARN: Removed duplicated region for block: B:204:0x065b A[Catch: Exception -> 0x068d, TryCatch #5 {Exception -> 0x068d, blocks: (B:162:0x067c, B:164:0x0683, B:166:0x0687, B:167:0x068c, B:202:0x0655, B:204:0x065b, B:206:0x065f), top: B:137:0x03d8 }] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x06f2  */
    /* JADX WARN: Removed duplicated region for block: B:212:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:282:0x03e5  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x03df  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x0330 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void updateSurface() {
        boolean z;
        boolean z2;
        CompatibilityInfo.Translator translator;
        ?? r3;
        String str;
        String str2;
        int i;
        CompatibilityInfo.Translator translator2;
        SurfaceControl.Transaction transaction;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        SyncBufferTransactionCallback syncBufferTransactionCallback;
        String str3;
        String str4;
        String str5;
        String str6;
        SurfaceHolder.Callback[] surfaceCallbacks;
        if (!this.mHaveFrame) {
            if (DEBUG) {
                Log.m94d(this.mTag, System.identityHashCode(this) + " updateSurface: has no frame");
                return;
            }
            return;
        }
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return;
        }
        if (viewRootImpl.mSurface != null && viewRootImpl.mSurface.isValid()) {
            CompatibilityInfo.Translator translator3 = viewRootImpl.mTranslator;
            if (translator3 != null) {
                this.mSurface.setCompatibilityTranslator(translator3);
            }
            int i2 = this.mRequestedWidth;
            if (i2 <= 0) {
                i2 = getWidth();
            }
            int i3 = i2;
            int i4 = this.mRequestedHeight;
            if (i4 <= 0) {
                i4 = getHeight();
            }
            int i5 = i4;
            float alpha = getAlpha();
            boolean z9 = this.mFormat != this.mRequestedFormat;
            boolean z10 = this.mVisible != this.mRequestedVisible;
            boolean z11 = this.mAlpha != alpha;
            boolean requiresSurfaceControlCreation = requiresSurfaceControlCreation(z9, z10);
            boolean z12 = (this.mSurfaceWidth == i3 && this.mSurfaceHeight == i5) ? false : true;
            boolean z13 = this.mWindowVisibility != this.mLastWindowVisibility;
            boolean isWindowOpaque = viewRootImpl.isWindowOpaque();
            boolean z14 = this.mIsWindowOpaque != isWindowOpaque;
            if (z14) {
                this.mIsWindowOpaque = isWindowOpaque;
            }
            getLocationInWindow(this.mLocation);
            int i6 = this.mWindowSpaceLeft;
            int[] iArr = this.mLocation;
            boolean z15 = (i6 == iArr[0] && this.mWindowSpaceTop == iArr[1]) ? false : true;
            boolean z16 = (getWidth() == this.mScreenRect.width() && getHeight() == this.mScreenRect.height()) ? false : true;
            ?? r6 = viewRootImpl.getBufferTransformHint() != this.mTransformHint && this.mRequestedVisible;
            boolean z17 = this.mSubLayer != this.mRequestedSubLayer;
            boolean z18 = this.mSurfaceLifecycleStrategy != this.mRequestedSurfaceLifecycleStrategy;
            if (!requiresSurfaceControlCreation && !z9 && !z12 && !z10 && !z11 && !z13 && !z15 && !z16 && r6 == 0 && !z17 && this.mAttachedToWindow && !z18 && !z14) {
                return;
            }
            boolean z19 = DEBUG;
            if (z19) {
                z = z19;
                translator = translator3;
                z2 = z9;
                Log.m98i(TAG, System.identityHashCode(this) + " Changes: creating=" + requiresSurfaceControlCreation + " format=" + z9 + " size=" + z12 + " visible=" + z10 + " alpha=" + z11 + " hint=" + ((boolean) r6) + " visible=" + z10 + " left=" + (this.mWindowSpaceLeft != this.mLocation[0]) + " top=" + (this.mWindowSpaceTop != this.mLocation[1]) + " z=" + z17 + " attached=" + this.mAttachedToWindow + " lifecycleStrategy=" + z18);
            } else {
                z = z19;
                z2 = z9;
                translator = translator3;
            }
            try {
                this.mVisible = this.mRequestedVisible;
                int[] iArr2 = this.mLocation;
                this.mWindowSpaceLeft = iArr2[0];
                this.mWindowSpaceTop = iArr2[1];
                this.mSurfaceWidth = i3;
                this.mSurfaceHeight = i5;
                this.mFormat = this.mRequestedFormat;
                this.mAlpha = alpha;
                this.mLastWindowVisibility = this.mWindowVisibility;
                this.mTransformHint = viewRootImpl.getBufferTransformHint();
                this.mSubLayer = this.mRequestedSubLayer;
                i = this.mSurfaceLifecycleStrategy;
                this.mSurfaceLifecycleStrategy = this.mRequestedSurfaceLifecycleStrategy;
                this.mScreenRect.left = this.mWindowSpaceLeft;
                this.mScreenRect.top = this.mWindowSpaceTop;
                this.mScreenRect.right = this.mWindowSpaceLeft + getWidth();
                this.mScreenRect.bottom = this.mWindowSpaceTop + getHeight();
                if (translator != null) {
                    try {
                        translator2 = translator;
                    } catch (Exception e) {
                        e = e;
                        r3 = " w=";
                        r6 = " h=";
                    }
                    try {
                        translator2.translateRectInAppWindowToScreen(this.mScreenRect);
                    } catch (Exception e2) {
                        e = e2;
                        r3 = " w=";
                        r6 = " h=";
                        Log.m97e(this.mTag, "Exception configuring surface", e);
                        str2 = r3;
                        str = r6;
                        if (DEBUG) {
                        }
                    }
                } else {
                    translator2 = translator;
                }
                try {
                    Rect rect = viewRootImpl.mWindowAttributes.surfaceInsets;
                    try {
                        this.mScreenRect.offset(rect.left, rect.top);
                        transaction = new SurfaceControl.Transaction();
                        if (requiresSurfaceControlCreation) {
                            try {
                                updateOpaqueFlag();
                                createBlastSurfaceControls(viewRootImpl, "SurfaceView[" + viewRootImpl.getTitle().toString() + "]@" + this.mSurfaceCreatedCount, transaction);
                            } catch (Exception e3) {
                                e = e3;
                                r3 = " w=";
                                r6 = " h=";
                                Log.m97e(this.mTag, "Exception configuring surface", e);
                                str2 = r3;
                                str = r6;
                                if (DEBUG) {
                                }
                            }
                        } else if (this.mSurfaceControl == null) {
                            return;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        r3 = " w=";
                        r6 = " h=";
                    }
                } catch (Exception e5) {
                    e = e5;
                    r3 = " w=";
                    r6 = " h=";
                }
            } catch (Exception e6) {
                e = e6;
                r3 = " w=";
                r6 = " h=";
            }
            try {
                try {
                    if (!z12 && !requiresSurfaceControlCreation && r6 == 0 && ((!this.mVisible || this.mDrawFinished) && !z11 && !z17)) {
                        z3 = false;
                        z4 = z3;
                        if (z4 && viewRootImpl.wasRelayoutRequested()) {
                            if (viewRootImpl.isInWMSRequestedSync()) {
                                z5 = true;
                                z6 = z5;
                                if (z6) {
                                    try {
                                        final SyncBufferTransactionCallback syncBufferTransactionCallback2 = new SyncBufferTransactionCallback();
                                        BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
                                        Objects.requireNonNull(syncBufferTransactionCallback2);
                                        z7 = z12;
                                        try {
                                            z8 = requiresSurfaceControlCreation;
                                            try {
                                                bLASTBufferQueue.syncNextTransaction(false, new Consumer() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda4
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        SurfaceView.SyncBufferTransactionCallback.this.onTransactionReady((SurfaceControl.Transaction) obj);
                                                    }
                                                });
                                                syncBufferTransactionCallback = syncBufferTransactionCallback2;
                                            } catch (Exception e7) {
                                                e = e7;
                                                r6 = " h=";
                                                r3 = " w=";
                                                Log.m97e(this.mTag, "Exception configuring surface", e);
                                                str2 = r3;
                                                str = r6;
                                                if (DEBUG) {
                                                }
                                            }
                                        } catch (Exception e8) {
                                            e = e8;
                                            r3 = " w=";
                                            r6 = " h=";
                                        }
                                    } catch (Exception e9) {
                                        e = e9;
                                        r3 = " w=";
                                        r6 = " h=";
                                    }
                                } else {
                                    z7 = z12;
                                    z8 = requiresSurfaceControlCreation;
                                    syncBufferTransactionCallback = null;
                                }
                                boolean z20 = z8;
                                SyncBufferTransactionCallback syncBufferTransactionCallback3 = syncBufferTransactionCallback;
                                boolean performSurfaceTransaction = performSurfaceTransaction(viewRootImpl, translator2, z20, z7, r6, z17, transaction);
                                r3 = z20;
                                boolean z21 = this.mSurfaceLifecycleStrategy != 2;
                                boolean z22 = !z21 && (i == 2);
                                if (this.mSurfaceCreated) {
                                    if (r3 == 0) {
                                        if (!z21) {
                                            try {
                                            } catch (Throwable th) {
                                                th = th;
                                                this.mIsCreating = false;
                                                if (this.mSurfaceControl != null) {
                                                }
                                                throw th;
                                            }
                                        }
                                        if (z21) {
                                            if (!this.mVisible) {
                                                if (!z10) {
                                                }
                                            }
                                        }
                                    }
                                    this.mSurfaceCreated = false;
                                    notifySurfaceDestroyed();
                                }
                                boolean z23 = z7;
                                copySurface(z20, z23);
                                SurfaceHolder.Callback[] callbackArr = null;
                                Log.m98i(this.mTag, "updateSurface: mVisible = " + this.mVisible + " mSurface.isValid() = " + this.mSurface.isValid());
                                if (surfaceShouldExist() || !this.mSurface.isValid()) {
                                    str3 = " h=";
                                    str4 = " w=";
                                } else {
                                    Log.m98i(this.mTag, "updateSurface: mSurfaceCreated = " + this.mSurfaceCreated + " surfaceChanged = " + ((boolean) r3) + " visibleChanged = " + z10);
                                    if (!this.mSurfaceCreated) {
                                        if (r3 != 0 || (z21 && z10)) {
                                            try {
                                                this.mSurfaceCreated = true;
                                                this.mIsCreating = true;
                                                if (z) {
                                                    try {
                                                        Log.m98i(TAG, System.identityHashCode(this) + " visibleChanged -- surfaceCreated");
                                                    } catch (Throwable th2) {
                                                        th = th2;
                                                        this.mIsCreating = false;
                                                        if (this.mSurfaceControl != null) {
                                                        }
                                                        throw th;
                                                    }
                                                }
                                                surfaceCallbacks = getSurfaceCallbacks();
                                            } catch (Throwable th3) {
                                                th = th3;
                                            }
                                            try {
                                                Log.m98i(this.mTag, "surfaceCreated " + this.mCallbacks.size() + " #" + this.mUpdateSurfaceCalledBy + " " + this);
                                                int length = surfaceCallbacks.length;
                                                int i7 = 0;
                                                while (i7 < length) {
                                                    SurfaceHolder.Callback[] callbackArr2 = surfaceCallbacks;
                                                    surfaceCallbacks[i7].surfaceCreated(this.mSurfaceHolder);
                                                    i7++;
                                                    surfaceCallbacks = callbackArr2;
                                                }
                                                callbackArr = surfaceCallbacks;
                                            } catch (Throwable th4) {
                                                th = th4;
                                                this.mIsCreating = false;
                                                if (this.mSurfaceControl != null) {
                                                }
                                                throw th;
                                            }
                                        }
                                    }
                                    if (z20 || z2 || z23 || r6 != 0 || ((z21 && z10) || performSurfaceTransaction)) {
                                        try {
                                            if (DEBUG) {
                                                String str7 = " w=";
                                                try {
                                                    String str8 = " h=";
                                                    try {
                                                        Log.m98i(TAG, System.identityHashCode(this) + " surfaceChanged -- format=" + this.mFormat + str7 + i3 + str8 + i5);
                                                        str6 = str7;
                                                        str5 = str8;
                                                    } catch (Throwable th5) {
                                                        th = th5;
                                                        this.mIsCreating = false;
                                                        if (this.mSurfaceControl != null && !this.mSurfaceCreated) {
                                                            releaseSurfaces(false);
                                                        }
                                                        throw th;
                                                    }
                                                } catch (Throwable th6) {
                                                    th = th6;
                                                    this.mIsCreating = false;
                                                    if (this.mSurfaceControl != null) {
                                                        releaseSurfaces(false);
                                                    }
                                                    throw th;
                                                }
                                            } else {
                                                str5 = " h=";
                                                str6 = " w=";
                                            }
                                            SurfaceHolder.Callback[] surfaceCallbacks2 = callbackArr == null ? getSurfaceCallbacks() : callbackArr;
                                            try {
                                                try {
                                                    Log.m98i(this.mTag, "surfaceChanged (" + i3 + "," + i5 + ") " + this.mCallbacks.size() + " #" + this.mUpdateSurfaceCalledBy + " " + this);
                                                    for (SurfaceHolder.Callback callback : surfaceCallbacks2) {
                                                        callback.surfaceChanged(this.mSurfaceHolder, this.mFormat, i3, i5);
                                                    }
                                                    callbackArr = surfaceCallbacks2;
                                                    str4 = str6;
                                                    str3 = str5;
                                                } catch (Throwable th7) {
                                                    th = th7;
                                                    this.mIsCreating = false;
                                                    if (this.mSurfaceControl != null) {
                                                    }
                                                    throw th;
                                                }
                                            } catch (Throwable th8) {
                                                th = th8;
                                                this.mIsCreating = false;
                                                if (this.mSurfaceControl != null) {
                                                }
                                                throw th;
                                            }
                                        } catch (Throwable th9) {
                                            th = th9;
                                        }
                                    } else {
                                        str3 = " h=";
                                        str4 = " w=";
                                    }
                                    if (z4) {
                                        if (DEBUG) {
                                            Log.m98i(TAG, System.identityHashCode(this) + " surfaceRedrawNeeded");
                                        }
                                        SurfaceHolder.Callback[] surfaceCallbacks3 = callbackArr == null ? getSurfaceCallbacks() : callbackArr;
                                        if (z6) {
                                            handleSyncBufferCallback(surfaceCallbacks3, syncBufferTransactionCallback3);
                                        } else {
                                            handleSyncNoBuffer(surfaceCallbacks3);
                                        }
                                    }
                                }
                                this.mIsCreating = false;
                                if (this.mSurfaceControl != null && !this.mSurfaceCreated) {
                                    releaseSurfaces(false);
                                }
                                str2 = str4;
                                str = str3;
                                if (DEBUG) {
                                    Log.m100v(this.mTag, "Layout: x=" + this.mScreenRect.left + " y=" + this.mScreenRect.top + str2 + this.mScreenRect.width() + str + this.mScreenRect.height() + ", frame=" + this.mSurfaceFrame);
                                    return;
                                }
                                return;
                            }
                        }
                        z5 = false;
                        z6 = z5;
                        if (z6) {
                        }
                        boolean z202 = z8;
                        SyncBufferTransactionCallback syncBufferTransactionCallback32 = syncBufferTransactionCallback;
                        boolean performSurfaceTransaction2 = performSurfaceTransaction(viewRootImpl, translator2, z202, z7, r6, z17, transaction);
                        r3 = z202;
                        boolean z212 = this.mSurfaceLifecycleStrategy != 2;
                        if (z212) {
                        }
                        if (this.mSurfaceCreated) {
                        }
                        boolean z232 = z7;
                        copySurface(z202, z232);
                        SurfaceHolder.Callback[] callbackArr3 = null;
                        Log.m98i(this.mTag, "updateSurface: mVisible = " + this.mVisible + " mSurface.isValid() = " + this.mSurface.isValid());
                        if (surfaceShouldExist()) {
                        }
                        str3 = " h=";
                        str4 = " w=";
                        this.mIsCreating = false;
                        if (this.mSurfaceControl != null) {
                            releaseSurfaces(false);
                        }
                        str2 = str4;
                        str = str3;
                        if (DEBUG) {
                        }
                    }
                    copySurface(z202, z232);
                    SurfaceHolder.Callback[] callbackArr32 = null;
                    Log.m98i(this.mTag, "updateSurface: mVisible = " + this.mVisible + " mSurface.isValid() = " + this.mSurface.isValid());
                    if (surfaceShouldExist()) {
                    }
                    str3 = " h=";
                    str4 = " w=";
                    this.mIsCreating = false;
                    if (this.mSurfaceControl != null) {
                    }
                    str2 = str4;
                    str = str3;
                    if (DEBUG) {
                    }
                } catch (Throwable th10) {
                    th = th10;
                }
                boolean z2122 = this.mSurfaceLifecycleStrategy != 2;
                if (z2122) {
                }
                if (this.mSurfaceCreated) {
                }
                boolean z2322 = z7;
            } catch (Throwable th11) {
                th = th11;
            }
            z3 = true;
            z4 = z3;
            if (z4) {
                if (viewRootImpl.isInWMSRequestedSync()) {
                }
            }
            z5 = false;
            z6 = z5;
            if (z6) {
            }
            boolean z2022 = z8;
            SyncBufferTransactionCallback syncBufferTransactionCallback322 = syncBufferTransactionCallback;
            boolean performSurfaceTransaction22 = performSurfaceTransaction(viewRootImpl, translator2, z2022, z7, r6, z17, transaction);
            r3 = z2022;
        }
        Log.m94d(this.mTag, "updateSurface: surface is not valid");
        notifySurfaceDestroyed();
        releaseSurfaces(false);
    }

    public String getName() {
        ViewRootImpl viewRoot = getViewRootImpl();
        String viewRootName = viewRoot == null ? "detached" : viewRoot.getTitle().toString();
        return "SurfaceView[" + viewRootName + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private void handleSyncBufferCallback(SurfaceHolder.Callback[] callbacks, final SyncBufferTransactionCallback syncBufferTransactionCallback) {
        final SurfaceSyncGroup surfaceSyncGroup = new SurfaceSyncGroup(getName());
        getViewRootImpl().addToSync(surfaceSyncGroup);
        redrawNeededAsync(callbacks, new Runnable() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                SurfaceView.this.lambda$handleSyncBufferCallback$1(syncBufferTransactionCallback, surfaceSyncGroup);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSyncBufferCallback$1(SyncBufferTransactionCallback syncBufferTransactionCallback, SurfaceSyncGroup surfaceSyncGroup) {
        SurfaceControl.Transaction t = null;
        BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            bLASTBufferQueue.stopContinuousSyncTransaction();
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
        redrawNeededAsync(callbacks, new Runnable() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda0
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

    private void redrawNeededAsync(SurfaceHolder.Callback[] callbacks, Runnable callbacksCollected) {
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
        BLASTBufferQueue bLASTBufferQueue;
        if (surfaceControlCreated) {
            this.mSurface.copyFrom(this.mBlastBufferQueue);
        }
        if (bufferSizeChanged && getContext().getApplicationInfo().targetSdkVersion < 26 && (bLASTBufferQueue = this.mBlastBufferQueue) != null) {
            this.mSurface.transferFrom(bLASTBufferQueue.createSurfaceWithHandle());
        }
    }

    private void setBufferSize(SurfaceControl.Transaction transaction) {
        this.mBlastSurfaceControl.setTransformHint(this.mTransformHint);
        BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            bLASTBufferQueue.update(this.mBlastSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight, this.mFormat);
        }
    }

    private void createBlastSurfaceControls(ViewRootImpl viewRoot, String name, SurfaceControl.Transaction surfaceUpdateTransaction) {
        if (this.mSurfaceControl == null) {
            this.mSurfaceControl = new SurfaceControl.Builder(this.mSurfaceSession).setName(name).setLocalOwnerView(this).setParent(viewRoot.getBoundsLayer()).setCallsite("SurfaceView.updateSurface").setContainerLayer().build();
        }
        SurfaceControl surfaceControl = this.mBlastSurfaceControl;
        if (surfaceControl == null) {
            this.mBlastSurfaceControl = new SurfaceControl.Builder(this.mSurfaceSession).setName(name + "(BLAST)").setLocalOwnerView(this).setParent(this.mSurfaceControl).setFlags(this.mSurfaceFlags).setHidden(false).setBLASTLayer().setCallsite("SurfaceView.updateSurface").build();
        } else {
            surfaceUpdateTransaction.setOpaque(surfaceControl, (this.mSurfaceFlags & 1024) != 0).setSecure(this.mBlastSurfaceControl, (this.mSurfaceFlags & 128) != 0).show(this.mBlastSurfaceControl);
        }
        if (this.mBackgroundControl == null) {
            this.mBackgroundControl = new SurfaceControl.Builder(this.mSurfaceSession).setName("Background for " + name).setLocalOwnerView(this).setOpaque(true).setColorLayer().setParent(this.mSurfaceControl).setCallsite("SurfaceView.updateSurface").build();
        }
        BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
        if (bLASTBufferQueue != null) {
            bLASTBufferQueue.destroy();
        }
        int bufferTransformHint = viewRoot.getBufferTransformHint();
        this.mTransformHint = bufferTransformHint;
        this.mBlastSurfaceControl.setTransformHint(bufferTransformHint);
        BLASTBufferQueue bLASTBufferQueue2 = new BLASTBufferQueue(name, false);
        this.mBlastBufferQueue = bLASTBufferQueue2;
        bLASTBufferQueue2.update(this.mBlastSurfaceControl, this.mSurfaceWidth, this.mSurfaceHeight, this.mFormat);
        this.mBlastBufferQueue.setTransactionHangCallback(ViewRootImpl.sTransactionHangCallback);
    }

    private void onDrawFinished() {
        if (DEBUG) {
            Log.m98i(TAG, System.identityHashCode(this) + " finishedDrawing");
        }
        runOnUiThread(new Runnable() { // from class: android.view.SurfaceView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                SurfaceView.this.performDrawFinished();
            }
        });
    }

    protected void onSetSurfacePositionAndScale(SurfaceControl.Transaction transaction, SurfaceControl surface, int positionLeft, int positionTop, float postScaleX, float postScaleY) {
        Log.m98i(this.mTag, "onSSPAndSRT: pl = " + positionLeft + " pt = " + positionTop + " sx = " + postScaleX + " sy = " + postScaleY);
        transaction.setPosition(surface, positionLeft, positionTop);
        transaction.setMatrix(surface, postScaleX, 0.0f, 0.0f, postScaleY);
    }

    public void requestUpdateSurfacePositionAndScale() {
        if (this.mSurfaceControl == null) {
            return;
        }
        Log.m98i(this.mTag, "rUSPAndS: sr = " + this.mScreenRect + " sw = " + this.mSurfaceWidth + " sh = " + this.mSurfaceHeight);
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        onSetSurfacePositionAndScale(transaction, this.mSurfaceControl, this.mScreenRect.left, this.mScreenRect.top, this.mScreenRect.width() / this.mSurfaceWidth, this.mScreenRect.height() / this.mSurfaceHeight);
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
            Log.m98i(this.mTag, "aOrMT: " + viewRoot.getTag() + " t = " + t + " fN = " + frameNumber + " " + Debug.getCallers(3));
            viewRoot.mergeWithNextTransaction(t, frameNumber);
        } else {
            Log.m98i(this.mTag, "aOrMT: t.apply");
            t.apply();
        }
    }

    public void semResetRenderNodePosition() {
    }

    private class SurfaceViewPositionUpdateListener implements RenderNode.PositionUpdateListener {
        private final SurfaceControl.Transaction mPositionChangedTransaction = new SurfaceControl.Transaction();
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
                    Log.m94d(SurfaceView.TAG, String.format("%d updateSurfacePosition RenderWorker, frameNr = %d, position = [%d, %d, %d, %d] surfaceSize = %dx%d", Integer.valueOf(System.identityHashCode(SurfaceView.this)), Long.valueOf(frameNumber), Integer.valueOf(left), Integer.valueOf(top), Integer.valueOf(right), Integer.valueOf(bottom), Integer.valueOf(this.mRtSurfaceWidth), Integer.valueOf(this.mRtSurfaceHeight)));
                }
                try {
                    synchronized (SurfaceView.this.mSurfaceControlLock) {
                        try {
                            if (SurfaceView.this.mSurfaceControl == null) {
                                return;
                            }
                            try {
                                SurfaceView.this.mRTLastReportedPosition.set(left, top, right, bottom);
                                Log.m98i(SurfaceView.this.mTag, "uSP: rtp = " + SurfaceView.this.mRTLastReportedPosition + " rtsw = " + this.mRtSurfaceWidth + " rtsh = " + this.mRtSurfaceHeight);
                                SurfaceView surfaceView = SurfaceView.this;
                                surfaceView.onSetSurfacePositionAndScale(this.mPositionChangedTransaction, surfaceView.mSurfaceControl, SurfaceView.this.mRTLastReportedPosition.left, SurfaceView.this.mRTLastReportedPosition.top, SurfaceView.this.mRTLastReportedPosition.width() / this.mRtSurfaceWidth, SurfaceView.this.mRTLastReportedPosition.height() / this.mRtSurfaceHeight);
                                this.mPositionChangedTransaction.show(SurfaceView.this.mSurfaceControl);
                                try {
                                    SurfaceView.this.applyOrMergeTransaction(this.mPositionChangedTransaction, frameNumber);
                                } catch (Exception e) {
                                    ex = e;
                                    Log.m97e(SurfaceView.this.mTag, "Exception from repositionChild", ex);
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
        public void applyStretch(long frameNumber, float width, float height, float vecX, float vecY, float maxStretchX, float maxStretchY, float childRelativeLeft, float childRelativeTop, float childRelativeRight, float childRelativeBottom) {
            SurfaceView.this.mRtTransaction.setStretchEffect(SurfaceView.this.mSurfaceControl, width, height, vecX, vecY, maxStretchX, maxStretchY, childRelativeLeft, childRelativeTop, childRelativeRight, childRelativeBottom);
            SurfaceView surfaceView = SurfaceView.this;
            surfaceView.applyOrMergeTransaction(surfaceView.mRtTransaction, frameNumber);
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionLost(long frameNumber) {
            if (SurfaceView.DEBUG_POSITION) {
                Log.m94d(SurfaceView.TAG, String.format("%d windowPositionLost, frameNr = %d", Integer.valueOf(System.identityHashCode(this)), Long.valueOf(frameNumber)));
            }
            SurfaceView.this.mRTLastReportedPosition.setEmpty();
            synchronized (SurfaceView.this.mSurfaceControlLock) {
                if (SurfaceView.this.mSurfaceControl == null) {
                    return;
                }
                SurfaceView.this.mRtTransaction.hide(SurfaceView.this.mSurfaceControl);
                SurfaceView surfaceView = SurfaceView.this;
                surfaceView.applyOrMergeTransaction(surfaceView.mRtTransaction, frameNumber);
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

    /* renamed from: android.view.SurfaceView$1 */
    class SurfaceHolderC36911 implements SurfaceHolder {
        private static final String LOG_TAG = "SurfaceHolder";

        SurfaceHolderC36911() {
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
        public void setFixedSize(int width, int height) {
            if (SurfaceView.this.mRequestedWidth != width || SurfaceView.this.mRequestedHeight != height) {
                if (SurfaceView.DEBUG_POSITION) {
                    Log.m94d(SurfaceView.TAG, String.format("%d setFixedSize %dx%d -> %dx%d", Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(SurfaceView.this.mRequestedWidth), Integer.valueOf(SurfaceView.this.mRequestedHeight), Integer.valueOf(width), Integer.valueOf(height)));
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
                    Log.m94d(SurfaceView.TAG, String.format("%d setSizeFromLayout was %dx%d", Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(SurfaceView.this.mRequestedWidth), Integer.valueOf(SurfaceView.this.mRequestedHeight)));
                }
                SurfaceView surfaceView = SurfaceView.this;
                surfaceView.mRequestedHeight = -1;
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
        public void setType(int type) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setKeepScreenOn$0(boolean screenOn) {
            SurfaceView.this.setKeepScreenOn(screenOn);
        }

        @Override // android.view.SurfaceHolder
        public void setKeepScreenOn(final boolean screenOn) {
            SurfaceView.this.runOnUiThread(new Runnable() { // from class: android.view.SurfaceView$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SurfaceView.SurfaceHolderC36911.this.lambda$setKeepScreenOn$0(screenOn);
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
                Log.m98i(SurfaceView.TAG, System.identityHashCode(this) + " Locking canvas... stopped=" + SurfaceView.this.mDrawingStopped + ", surfaceControl=" + SurfaceView.this.mSurfaceControl);
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
                    Log.m97e(LOG_TAG, "Exception locking surface", e);
                }
            }
            if (SurfaceView.DEBUG) {
                Log.m98i(SurfaceView.TAG, System.identityHashCode(this) + " Returned canvas: " + c);
            }
            if (c != null) {
                SurfaceView.this.mLastLockTime = SystemClock.uptimeMillis();
                return c;
            }
            long now = SystemClock.uptimeMillis();
            long nextTime = SurfaceView.this.mLastLockTime + 100;
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
        SurfaceControlViewHost.SurfacePackage surfacePackage = this.mSurfacePackage;
        SurfaceControl lastSc = surfacePackage != null ? surfacePackage.getSurfaceControl() : null;
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
        if (isFocused()) {
            requestEmbeddedFocus(true);
        }
        invalidate();
    }

    private void reparentSurfacePackage(SurfaceControl.Transaction t, SurfaceControlViewHost.SurfacePackage p) {
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
        RemoteAccessibilityController remoteAccessibilityController = this.mRemoteAccessibilityController;
        if ((remoteAccessibilityController != null && !remoteAccessibilityController.connected()) || mode != 0) {
            return mode;
        }
        return 1;
    }

    private void initEmbeddedHierarchyForAccessibility(SurfaceControlViewHost.SurfacePackage p) {
        IAccessibilityEmbeddedConnection connection = p.getAccessibilityEmbeddedConnection();
        if (this.mRemoteAccessibilityController.alreadyAssociated(connection)) {
            return;
        }
        this.mRemoteAccessibilityController.assosciateHierarchy(connection, getViewRootImpl().mLeashToken, getAccessibilityViewId());
        updateEmbeddedAccessibilityMatrix(true);
    }

    private void notifySurfaceDestroyed() {
        if (this.mSurface.isValid()) {
            if (DEBUG) {
                Log.m98i(TAG, System.identityHashCode(this) + " surfaceDestroyed");
            }
            SurfaceHolder.Callback[] callbacks = getSurfaceCallbacks();
            Log.m98i(this.mTag, "surfaceDestroyed callback.size " + this.mCallbacks.size() + " #" + this.mUpdateSurfaceCalledBy + " " + this);
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
        this.mTmpMatrix.postScale(this.mScreenRect.width() / this.mSurfaceWidth, this.mScreenRect.height() / this.mSurfaceHeight);
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
            Slog.m136d(TAG, "requestEmbeddedFocus: caller=" + Debug.getCallers(4));
            gainFocus = false;
        }
        try {
            viewRoot.mWindowSession.grantEmbeddedWindowFocus(viewRoot.mWindow, this.mSurfacePackage.getInputToken(), gainFocus);
        } catch (Exception e) {
            Log.m97e(TAG, System.identityHashCode(this) + "Exception requesting focus on embedded window", e);
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
            BLASTBufferQueue bLASTBufferQueue = this.mBlastBufferQueue;
            if (bLASTBufferQueue == null) {
                throw new IllegalStateException("Surface does not exist!");
            }
            long frameNumber = bLASTBufferQueue.getLastAcquiredFrameNum() + 1;
            this.mBlastBufferQueue.mergeWithNextTransaction(transaction, frameNumber);
        }
    }
}
