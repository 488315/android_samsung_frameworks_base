package com.android.systemui.accessibility;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.util.Property;
import android.util.Range;
import android.util.Size;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Choreographer;
import android.view.Display;
import android.view.IWindow;
import android.view.IWindowSession;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.A11yLogger;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IWindowMagnificationConnectionCallback;
import android.widget.Button;
import android.widget.ImageView;
import androidx.core.math.MathUtils;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.internal.view.TooltipPopup;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationGestureDetector;
import com.android.systemui.accessibility.WindowMagnification;
import com.android.systemui.model.SysUiState;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.widget.SemTipPopup;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WindowMagnificationController implements View.OnTouchListener, SurfaceHolder.Callback, MagnificationGestureDetector.OnGestureListener, ComponentCallbacks {
    public static final Range A11Y_ACTION_SCALE_RANGE;
    public static final boolean DEBUG;
    static final double HORIZONTAL_LOCK_BASE;
    public boolean mAllowDiagonalScrolling;
    public final WindowMagnificationAnimationController mAnimationController;
    public int mBorderDragSize;
    public View mBottomDrag;
    public ImageView mBottomLeftCornerView;
    public ImageView mBottomRightCornerView;
    public float mBounceEffectAnimationScale;
    public final int mBounceEffectDuration;
    public ImageView mCloseView;
    public final Configuration mConfiguration;
    public final Context mContext;
    public final int mDisplayId;
    public ImageView mDragView;
    public boolean mEditSizeEnable;
    public final MagnificationGestureDetector mGestureDetector;
    public final Supplier mGlobalWindowSessionSupplier;
    public final Handler mHandler;
    public final WindowMagnificationController$$ExternalSyntheticLambda0 mHideTootipRunnable;
    public boolean mIsDragging;
    public View mLeftDrag;
    public Locale mLocale;
    public final Rect mMagnificationFrame;
    public final Rect mMagnificationFrameBoundary;
    public int mMagnificationFrameOffsetX;
    public int mMagnificationFrameOffsetY;
    public final SparseArray mMagnificationSizeScaleOptions;
    public int mMinWindowSize;
    public View mMirrorBorderView;
    public SurfaceControl mMirrorSurface;
    public int mMirrorSurfaceMargin;
    public SurfaceView mMirrorSurfaceView;
    public final WindowMagnificationController$$ExternalSyntheticLambda2 mMirrorSurfaceViewLayoutChangeListener;
    public View mMirrorView;
    public final Rect mMirrorViewBounds;
    public final WindowMagnificationController$$ExternalSyntheticLambda3 mMirrorViewGeometryVsyncCallback;
    public final WindowMagnificationController$$ExternalSyntheticLambda2 mMirrorViewLayoutChangeListener;
    public final WindowMagnificationController$$ExternalSyntheticLambda0 mMirrorViewRunnable;
    public final MirrorWindowControl mMirrorWindowControl;
    public int mOuterBorderSize;
    public boolean mOverlapWithGestureInsets;
    public NumberFormat mPercentFormat;
    public int mPreviousMagnificationSize;
    public final Resources mResources;
    public View mRightDrag;
    int mRotation;
    public float mScale;
    public final SfVsyncFrameCallbackProvider mSfVsyncFrameProvider;
    public boolean mShownSettingPanel;
    public final Rect mSourceBounds;
    public final SysUiState mSysUiState;
    public int mSystemGestureTop;
    public SemTipPopup mTipPopup;
    public int mTipPopupCnt;
    public final Rect mTmpRect;
    public final TooltipPopup mTooltipPopup;
    public View mTopDrag;
    public ImageView mTopLeftCornerView;
    public ImageView mTopRightCornerView;
    public final SurfaceControl.Transaction mTransaction;
    public final WindowMagnificationController$$ExternalSyntheticLambda0 mUpdateStateDescriptionRunnable;
    public final Rect mWindowBounds;
    public final WindowMagnificationController$$ExternalSyntheticLambda0 mWindowInsetChangeRunnable;
    public final WindowMagnifierCallback mWindowMagnifierCallback;
    public final WindowManager mWm;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DragHandleA11yDelegate extends View.AccessibilityDelegate {
        public /* synthetic */ DragHandleA11yDelegate(WindowMagnificationController windowMagnificationController, int i) {
            this(windowMagnificationController);
        }

        @Override // android.view.View.AccessibilityDelegate
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(Button.class.getName());
        }

        private DragHandleA11yDelegate(WindowMagnificationController windowMagnificationController) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MirrorWindowA11yDelegate extends View.AccessibilityDelegate {
        public /* synthetic */ MirrorWindowA11yDelegate(WindowMagnificationController windowMagnificationController, int i) {
            this();
        }

        @Override // android.view.View.AccessibilityDelegate
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_zoom_in, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_zoom_in)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_zoom_out, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_zoom_out)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_up, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_move_up)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_down, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_move_down)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_left, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_move_left)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_right, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_move_right)));
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x00e5 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:17:0x00e6  */
        @Override // android.view.View.AccessibilityDelegate
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            boolean z;
            IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback;
            IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback2;
            IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback3;
            if (i == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                WindowMagnificationController windowMagnificationController = WindowMagnificationController.this;
                windowMagnificationController.handleSingleTap(windowMagnificationController.mDragView);
            } else if (i == R.id.accessibility_action_zoom_in) {
                WindowMagnificationController windowMagnificationController2 = WindowMagnificationController.this;
                float f = windowMagnificationController2.mScale + 1.0f;
                WindowMagnifierCallback windowMagnifierCallback = windowMagnificationController2.mWindowMagnifierCallback;
                int i2 = windowMagnificationController2.mDisplayId;
                float floatValue = ((Float) WindowMagnificationController.A11Y_ACTION_SCALE_RANGE.clamp(Float.valueOf(f))).floatValue();
                WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = WindowMagnification.this.mWindowMagnificationConnectionImpl;
                if (windowMagnificationConnectionImpl != null && (iWindowMagnificationConnectionCallback2 = windowMagnificationConnectionImpl.mConnectionCallback) != null) {
                    try {
                        iWindowMagnificationConnectionCallback2.onPerformScaleAction(i2, floatValue);
                    } catch (RemoteException e) {
                        Log.e("WindowMagnificationConnectionImpl", "Failed to inform performing scale action", e);
                    }
                }
            } else if (i == R.id.accessibility_action_zoom_out) {
                WindowMagnificationController windowMagnificationController3 = WindowMagnificationController.this;
                float f2 = windowMagnificationController3.mScale - 1.0f;
                WindowMagnifierCallback windowMagnifierCallback2 = windowMagnificationController3.mWindowMagnifierCallback;
                int i3 = windowMagnificationController3.mDisplayId;
                float floatValue2 = ((Float) WindowMagnificationController.A11Y_ACTION_SCALE_RANGE.clamp(Float.valueOf(f2))).floatValue();
                WindowMagnificationConnectionImpl windowMagnificationConnectionImpl2 = WindowMagnification.this.mWindowMagnificationConnectionImpl;
                if (windowMagnificationConnectionImpl2 != null && (iWindowMagnificationConnectionCallback = windowMagnificationConnectionImpl2.mConnectionCallback) != null) {
                    try {
                        iWindowMagnificationConnectionCallback.onPerformScaleAction(i3, floatValue2);
                    } catch (RemoteException e2) {
                        Log.e("WindowMagnificationConnectionImpl", "Failed to inform performing scale action", e2);
                    }
                }
            } else {
                z = false;
                if (i == R.id.accessibility_action_move_up) {
                    WindowMagnificationController windowMagnificationController4 = WindowMagnificationController.this;
                    windowMagnificationController4.move(0, -windowMagnificationController4.mSourceBounds.height());
                } else if (i == R.id.accessibility_action_move_down) {
                    WindowMagnificationController windowMagnificationController5 = WindowMagnificationController.this;
                    windowMagnificationController5.move(0, windowMagnificationController5.mSourceBounds.height());
                } else {
                    if (i != R.id.accessibility_action_move_left) {
                        if (i == R.id.accessibility_action_move_right) {
                            WindowMagnificationController windowMagnificationController6 = WindowMagnificationController.this;
                            windowMagnificationController6.move(windowMagnificationController6.mSourceBounds.width(), 0);
                        }
                        if (z) {
                            return super.performAccessibilityAction(view, i, bundle);
                        }
                        return true;
                    }
                    WindowMagnificationController windowMagnificationController7 = WindowMagnificationController.this;
                    windowMagnificationController7.move(-windowMagnificationController7.mSourceBounds.width(), 0);
                }
            }
            WindowMagnificationController windowMagnificationController8 = WindowMagnificationController.this;
            WindowMagnifierCallback windowMagnifierCallback3 = windowMagnificationController8.mWindowMagnifierCallback;
            int i4 = windowMagnificationController8.mDisplayId;
            WindowMagnificationConnectionImpl windowMagnificationConnectionImpl3 = WindowMagnification.this.mWindowMagnificationConnectionImpl;
            if (windowMagnificationConnectionImpl3 != null && (iWindowMagnificationConnectionCallback3 = windowMagnificationConnectionImpl3.mConnectionCallback) != null) {
                try {
                    iWindowMagnificationConnectionCallback3.onAccessibilityActionPerformed(i4);
                } catch (RemoteException e3) {
                    Log.e("WindowMagnificationConnectionImpl", "Failed to inform an accessibility action is already performed", e3);
                }
            }
            z = true;
            if (z) {
            }
        }

        private MirrorWindowA11yDelegate() {
        }
    }

    static {
        DEBUG = Log.isLoggable("WindowMagnificationController", 3) || Build.IS_DEBUGGABLE;
        A11Y_ACTION_SCALE_RANGE = new Range(Float.valueOf(1.0f), Float.valueOf(8.0f));
        HORIZONTAL_LOCK_BASE = Math.tan(Math.toRadians(50.0d));
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda3] */
    public WindowMagnificationController(Context context, Handler handler, WindowMagnificationAnimationController windowMagnificationAnimationController, SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider, MirrorWindowControl mirrorWindowControl, SurfaceControl.Transaction transaction, WindowMagnifierCallback windowMagnifierCallback, SysUiState sysUiState, Supplier<IWindowSession> supplier, SecureSettings secureSettings) {
        SparseArray sparseArray = new SparseArray();
        this.mMagnificationSizeScaleOptions = sparseArray;
        Rect rect = new Rect();
        this.mMagnificationFrame = rect;
        this.mTmpRect = new Rect();
        this.mMirrorViewBounds = new Rect();
        this.mSourceBounds = new Rect();
        final int i = 0;
        this.mMagnificationFrameOffsetX = 0;
        this.mMagnificationFrameOffsetY = 0;
        this.mMagnificationFrameBoundary = new Rect();
        this.mSystemGestureTop = -1;
        final int i2 = 1;
        this.mAllowDiagonalScrolling = true;
        this.mEditSizeEnable = false;
        this.mTipPopup = null;
        this.mTipPopupCnt = 0;
        this.mPreviousMagnificationSize = 0;
        this.mShownSettingPanel = false;
        this.mHideTootipRunnable = new WindowMagnificationController$$ExternalSyntheticLambda0(this, 2);
        this.mContext = context;
        this.mHandler = handler;
        this.mAnimationController = windowMagnificationAnimationController;
        this.mGlobalWindowSessionSupplier = supplier;
        windowMagnificationAnimationController.mController = this;
        this.mSfVsyncFrameProvider = sfVsyncFrameCallbackProvider;
        this.mWindowMagnifierCallback = windowMagnifierCallback;
        this.mSysUiState = sysUiState;
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
        context.setTheme(android.R.style.Theme.DeviceDefault.DayNight);
        Display display = context.getDisplay();
        this.mDisplayId = context.getDisplayId();
        this.mRotation = display.getRotation();
        WindowManager windowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mWm = windowManager;
        Rect rect2 = new Rect(windowManager.getCurrentWindowMetrics().getBounds());
        this.mWindowBounds = rect2;
        int i3 = context.getResources().getConfiguration().semDisplayDeviceType;
        int min = ((int) ((Math.min(rect2.width(), rect2.height()) / 3) * new float[]{1.0f, 1.4f, 1.8f, 2.5f}[0])) % 2;
        Resources resources = context.getResources();
        this.mResources = resources;
        this.mScale = secureSettings.getFloatForUser("accessibility_display_magnification_scale", -2, resources.getInteger(R.integer.magnification_default_scale));
        sparseArray.clear();
        sparseArray.put(1, Float.valueOf(1.4f));
        sparseArray.put(2, Float.valueOf(1.8f));
        sparseArray.put(3, Float.valueOf(2.5f));
        this.mBounceEffectDuration = resources.getInteger(android.R.integer.config_shortAnimTime);
        updateDimensions();
        int min2 = (this.mMirrorSurfaceMargin * 2) + Math.min(resources.getDimensionPixelSize(R.dimen.magnification_max_frame_size), Math.min(rect2.width(), rect2.height()) / 2);
        Size size = new Size(min2, min2);
        int width = size.getWidth();
        int height = size.getHeight();
        int width2 = (rect2.width() / 2) - (width / 2);
        int height2 = (rect2.height() / 2) - (height / 2);
        rect.set(width2, height2, width + width2, height + height2);
        computeBounceAnimationScale();
        this.mMirrorWindowControl = mirrorWindowControl;
        this.mTransaction = transaction;
        this.mGestureDetector = new MagnificationGestureDetector(context, handler, this);
        this.mMirrorViewRunnable = new WindowMagnificationController$$ExternalSyntheticLambda0(this, 3);
        this.mMirrorViewLayoutChangeListener = new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda2
            public final /* synthetic */ WindowMagnificationController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                switch (i) {
                    case 0:
                        WindowMagnificationController windowMagnificationController = this.f$0;
                        if (!windowMagnificationController.mHandler.hasCallbacks(windowMagnificationController.mMirrorViewRunnable)) {
                            windowMagnificationController.mHandler.post(windowMagnificationController.mMirrorViewRunnable);
                            break;
                        }
                        break;
                    default:
                        WindowMagnificationController windowMagnificationController2 = this.f$0;
                        windowMagnificationController2.mMirrorView.post(new WindowMagnificationController$$ExternalSyntheticLambda0(windowMagnificationController2, 6));
                        break;
                }
            }
        };
        this.mMirrorSurfaceViewLayoutChangeListener = new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda2
            public final /* synthetic */ WindowMagnificationController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                switch (i2) {
                    case 0:
                        WindowMagnificationController windowMagnificationController = this.f$0;
                        if (!windowMagnificationController.mHandler.hasCallbacks(windowMagnificationController.mMirrorViewRunnable)) {
                            windowMagnificationController.mHandler.post(windowMagnificationController.mMirrorViewRunnable);
                            break;
                        }
                        break;
                    default:
                        WindowMagnificationController windowMagnificationController2 = this.f$0;
                        windowMagnificationController2.mMirrorView.post(new WindowMagnificationController$$ExternalSyntheticLambda0(windowMagnificationController2, 6));
                        break;
                }
            }
        };
        this.mMirrorViewGeometryVsyncCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda3
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback;
                WindowMagnificationController windowMagnificationController = WindowMagnificationController.this;
                if (!windowMagnificationController.isActivated() || windowMagnificationController.mMirrorSurface == null) {
                    return;
                }
                Rect rect3 = windowMagnificationController.mMagnificationFrame;
                float f = windowMagnificationController.mScale;
                windowMagnificationController.mTmpRect.set(windowMagnificationController.mSourceBounds);
                int width3 = rect3.width() / 2;
                int height3 = rect3.height() / 2;
                int i4 = width3 - ((int) (width3 / f));
                int i5 = height3 - ((int) (height3 / f));
                windowMagnificationController.mSourceBounds.set(rect3.left + i4, rect3.top + i5, rect3.right - i4, rect3.bottom - i5);
                windowMagnificationController.mSourceBounds.offset(-windowMagnificationController.mMagnificationFrameOffsetX, -windowMagnificationController.mMagnificationFrameOffsetY);
                Rect rect4 = windowMagnificationController.mSourceBounds;
                if (rect4.left < 0) {
                    rect4.offsetTo(0, rect4.top);
                } else if (rect4.right > windowMagnificationController.mWindowBounds.width()) {
                    windowMagnificationController.mSourceBounds.offsetTo(windowMagnificationController.mWindowBounds.width() - windowMagnificationController.mSourceBounds.width(), windowMagnificationController.mSourceBounds.top);
                }
                Rect rect5 = windowMagnificationController.mSourceBounds;
                if (rect5.top < 0) {
                    rect5.offsetTo(rect5.left, 0);
                } else if (rect5.bottom > windowMagnificationController.mWindowBounds.height()) {
                    Rect rect6 = windowMagnificationController.mSourceBounds;
                    rect6.offsetTo(rect6.left, windowMagnificationController.mWindowBounds.height() - windowMagnificationController.mSourceBounds.height());
                }
                if (!windowMagnificationController.mSourceBounds.equals(r0)) {
                    windowMagnificationController.mTmpRect.set(0, 0, windowMagnificationController.mMagnificationFrame.width(), windowMagnificationController.mMagnificationFrame.height());
                    windowMagnificationController.mTransaction.setGeometry(windowMagnificationController.mMirrorSurface, windowMagnificationController.mSourceBounds, windowMagnificationController.mTmpRect, 0).apply();
                    if (windowMagnificationController.mAnimationController.mValueAnimator.isRunning()) {
                        return;
                    }
                    WindowMagnifierCallback windowMagnifierCallback2 = windowMagnificationController.mWindowMagnifierCallback;
                    int i6 = windowMagnificationController.mDisplayId;
                    Rect rect7 = windowMagnificationController.mSourceBounds;
                    WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = WindowMagnification.this.mWindowMagnificationConnectionImpl;
                    if (windowMagnificationConnectionImpl == null || (iWindowMagnificationConnectionCallback = windowMagnificationConnectionImpl.mConnectionCallback) == null) {
                        return;
                    }
                    try {
                        iWindowMagnificationConnectionCallback.onSourceBoundsChanged(i6, rect7);
                    } catch (RemoteException e) {
                        Log.e("WindowMagnificationConnectionImpl", "Failed to inform source bounds changed", e);
                    }
                }
            }
        };
        this.mUpdateStateDescriptionRunnable = new WindowMagnificationController$$ExternalSyntheticLambda0(this, 4);
        this.mWindowInsetChangeRunnable = new WindowMagnificationController$$ExternalSyntheticLambda0(this, 5);
        this.mTooltipPopup = new TooltipPopup(context);
    }

    public final void applyResourcesValues() {
        float applyDimension;
        this.mMirrorBorderView.setBackground(this.mResources.getDrawable(R.drawable.accessibility_window_magnification_background));
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            applyDimension = TypedValue.applyDimension(1, this.mEditSizeEnable ? 14.0f : 25.0f, this.mContext.getResources().getDisplayMetrics());
        } else {
            applyDimension = TypedValue.applyDimension(1, this.mEditSizeEnable ? 16.0f : 28.0f, this.mContext.getResources().getDisplayMetrics());
        }
        this.mMirrorSurfaceView.setCornerRadius(applyDimension);
        if (this.mEditSizeEnable) {
            this.mDragView.setVisibility(8);
            this.mCloseView.setVisibility(0);
            this.mTopRightCornerView.setVisibility(0);
            this.mTopLeftCornerView.setVisibility(0);
            this.mBottomRightCornerView.setVisibility(0);
            this.mBottomLeftCornerView.setVisibility(0);
            return;
        }
        this.mDragView.setVisibility(0);
        this.mCloseView.setVisibility(8);
        this.mTopRightCornerView.setVisibility(8);
        this.mTopLeftCornerView.setVisibility(8);
        this.mBottomRightCornerView.setVisibility(8);
        this.mBottomLeftCornerView.setVisibility(8);
    }

    public final void applyTapExcludeRegion() {
        if (isActivated() && this.mMirrorView != null) {
            int i = this.mBorderDragSize;
            Region region = new Region(i, i, this.mMirrorView.getWidth() - this.mBorderDragSize, this.mMirrorView.getHeight() - this.mBorderDragSize);
            Region region2 = new Region();
            Rect rect = new Rect();
            this.mDragView.getHitRect(rect);
            Rect rect2 = new Rect();
            this.mTopLeftCornerView.getHitRect(rect2);
            Rect rect3 = new Rect();
            this.mTopRightCornerView.getHitRect(rect3);
            Rect rect4 = new Rect();
            this.mBottomLeftCornerView.getHitRect(rect4);
            Rect rect5 = new Rect();
            this.mBottomRightCornerView.getHitRect(rect5);
            Rect rect6 = new Rect();
            this.mCloseView.getHitRect(rect6);
            region2.op(rect, Region.Op.UNION);
            region2.op(rect2, Region.Op.UNION);
            region2.op(rect3, Region.Op.UNION);
            region2.op(rect4, Region.Op.UNION);
            region2.op(rect5, Region.Op.UNION);
            region2.op(rect6, Region.Op.UNION);
            region.op(region2, Region.Op.DIFFERENCE);
            try {
                ((IWindowSession) this.mGlobalWindowSessionSupplier.get()).updateTapExcludeRegion(IWindow.Stub.asInterface(this.mMirrorView.getWindowToken()), region);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void calculateMagnificationFrameBoundary() {
        int width = this.mMagnificationFrame.width() / 2;
        int height = this.mMagnificationFrame.height() / 2;
        float f = this.mScale;
        int i = width - ((int) (width / f));
        int i2 = height - ((int) (height / f));
        this.mMagnificationFrameBoundary.set(-Math.max(i - this.mMagnificationFrameOffsetX, 0), -Math.max(i2 - this.mMagnificationFrameOffsetY, 0), this.mWindowBounds.width() + Math.max(i + this.mMagnificationFrameOffsetX, 0), this.mWindowBounds.height() + Math.max(i2 + this.mMagnificationFrameOffsetY, 0));
    }

    public final void changeMagnificationFrameSize(float f, float f2, float f3, float f4) {
        this.mContext.getResources().getConfiguration();
        int min = Math.min(this.mWindowBounds.width(), this.mWindowBounds.height()) / 3;
        int height = this.mWindowBounds.height() - (this.mMirrorSurfaceMargin * 2);
        int width = this.mWindowBounds.width() - (this.mMirrorSurfaceMargin * 2);
        Rect rect = new Rect();
        rect.set(this.mMagnificationFrame);
        rect.right += (int) f3;
        rect.left += (int) f;
        rect.top += (int) f2;
        rect.bottom += (int) f4;
        if (rect.width() < min || rect.height() < min || rect.width() > width || rect.height() > height) {
            return;
        }
        this.mMagnificationFrame.set(rect);
        computeBounceAnimationScale();
        calculateMagnificationFrameBoundary();
        modifyWindowMagnification(true);
    }

    public final void changeMagnificationSize(int i) {
        if (this.mMagnificationSizeScaleOptions.contains(i)) {
            int min = (int) ((Math.min(this.mWindowBounds.width(), this.mWindowBounds.height()) / 3) * ((Float) this.mMagnificationSizeScaleOptions.get(i, Float.valueOf(1.0f))).floatValue());
            setWindowSizeAndCenter(min, Float.NaN, Float.NaN, min);
            this.mPreviousMagnificationSize = i;
        }
    }

    public final void computeBounceAnimationScale() {
        float width = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.width();
        this.mBounceEffectAnimationScale = Math.min(width / (width - (this.mOuterBorderSize * 2)), 1.05f);
    }

    public final void deleteWindowMagnification$1() {
        IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback;
        View view;
        if (isActivated()) {
            SurfaceControl surfaceControl = this.mMirrorSurface;
            if (surfaceControl != null) {
                this.mTransaction.remove(surfaceControl).apply();
                this.mMirrorSurface = null;
            }
            SurfaceView surfaceView = this.mMirrorSurfaceView;
            if (surfaceView != null) {
                surfaceView.removeOnLayoutChangeListener(this.mMirrorSurfaceViewLayoutChangeListener);
            }
            if (this.mMirrorView != null) {
                this.mHandler.removeCallbacks(this.mMirrorViewRunnable);
                this.mMirrorView.removeOnLayoutChangeListener(this.mMirrorViewLayoutChangeListener);
                this.mWm.removeView(this.mMirrorView);
                this.mMirrorView = null;
            }
            MirrorWindowControl mirrorWindowControl = this.mMirrorWindowControl;
            if (mirrorWindowControl != null && (view = mirrorWindowControl.mControlsView) != null) {
                mirrorWindowControl.mWindowManager.removeView(view);
                mirrorWindowControl.mControlsView = null;
            }
            this.mMirrorViewBounds.setEmpty();
            this.mSourceBounds.setEmpty();
            updateSysUIState(false);
            setEditMagnifierSizeMode(false);
            this.mContext.unregisterComponentCallbacks(this);
            WindowMagnifierCallback windowMagnifierCallback = this.mWindowMagnifierCallback;
            int i = this.mDisplayId;
            Rect rect = new Rect();
            WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = WindowMagnification.this.mWindowMagnificationConnectionImpl;
            if (windowMagnificationConnectionImpl == null || (iWindowMagnificationConnectionCallback = windowMagnificationConnectionImpl.mConnectionCallback) == null) {
                return;
            }
            try {
                iWindowMagnificationConnectionCallback.onSourceBoundsChanged(i, rect);
            } catch (RemoteException e) {
                Log.e("WindowMagnificationConnectionImpl", "Failed to inform source bounds changed", e);
            }
        }
    }

    public final void enableWindowMagnificationInternal(float f) {
        enableWindowMagnificationInternal(f, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
    }

    public final void handleSingleTap(View view) {
        int id = view.getId();
        int i = 1;
        if (id == R.id.drag_handle) {
            WindowMagnification.C09992 c09992 = (WindowMagnification.C09992) this.mWindowMagnifierCallback;
            WindowMagnification.this.mHandler.post(new WindowMagnification$$ExternalSyntheticLambda2(this.mDisplayId, i, c09992));
            A11yLogger.insertLog(this.mContext, "A11Y3190");
            return;
        }
        if (id == R.id.close_button) {
            setEditMagnifierSizeMode(false);
            A11yLogger.insertLog(this.mContext, "A11Y3187");
        } else {
            ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this.mMirrorView, PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, 1.0f, this.mBounceEffectAnimationScale, 1.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, 1.0f, this.mBounceEffectAnimationScale, 1.0f));
            ofPropertyValuesHolder.setDuration(this.mBounceEffectDuration);
            ofPropertyValuesHolder.start();
        }
    }

    public final boolean isActivated() {
        return this.mMirrorView != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x007e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void modifyWindowMagnification(boolean z) {
        float f;
        int min;
        int i;
        int min2;
        this.mSfVsyncFrameProvider.postFrameCallback(this.mMirrorViewGeometryVsyncCallback);
        if (!isActivated()) {
            return;
        }
        int width = this.mWindowBounds.width() - this.mMirrorView.getWidth();
        int height = this.mWindowBounds.height() - this.mMirrorView.getHeight();
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mMirrorView.getLayoutParams();
        Rect rect = this.mMagnificationFrame;
        int i2 = rect.left;
        int i3 = this.mMirrorSurfaceMargin;
        layoutParams.x = i2 - i3;
        layoutParams.y = rect.top - i3;
        if (z) {
            layoutParams.width = (this.mMirrorSurfaceMargin * 2) + rect.width();
            layoutParams.height = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.height();
        }
        int i4 = layoutParams.x;
        float f2 = 0.0f;
        if (i4 < 0) {
            min = Math.max(i4, -this.mOuterBorderSize);
        } else if (i4 > width) {
            min = Math.min(i4 - width, this.mOuterBorderSize);
        } else {
            f = 0.0f;
            i = layoutParams.y;
            if (i < 0) {
                if (i > height) {
                    min2 = Math.min(i - height, this.mOuterBorderSize);
                }
                this.mMirrorView.setTranslationX(f);
                this.mMirrorView.setTranslationY(f2);
                this.mWm.updateViewLayout(this.mMirrorView, layoutParams);
                if (this.mIsDragging) {
                    return;
                }
                this.mMirrorView.post(new WindowMagnificationController$$ExternalSyntheticLambda0(this, 1));
                return;
            }
            min2 = Math.max(i, -this.mOuterBorderSize);
            f2 = min2;
            this.mMirrorView.setTranslationX(f);
            this.mMirrorView.setTranslationY(f2);
            this.mWm.updateViewLayout(this.mMirrorView, layoutParams);
            if (this.mIsDragging) {
            }
        }
        f = min;
        i = layoutParams.y;
        if (i < 0) {
        }
        f2 = min2;
        this.mMirrorView.setTranslationX(f);
        this.mMirrorView.setTranslationY(f2);
        this.mWm.updateViewLayout(this.mMirrorView, layoutParams);
        if (this.mIsDragging) {
        }
    }

    public final void move(int i, int i2) {
        IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback;
        moveWindowMagnifier(i, i2);
        WindowMagnifierCallback windowMagnifierCallback = this.mWindowMagnifierCallback;
        int i3 = this.mDisplayId;
        WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = WindowMagnification.this.mWindowMagnificationConnectionImpl;
        if (windowMagnificationConnectionImpl == null || (iWindowMagnificationConnectionCallback = windowMagnificationConnectionImpl.mConnectionCallback) == null) {
            return;
        }
        try {
            iWindowMagnificationConnectionCallback.onMove(i3);
        } catch (RemoteException e) {
            Log.e("WindowMagnificationConnectionImpl", "Failed to inform taking control by a user", e);
        }
    }

    public final void moveWindowMagnifier(float f, float f2) {
        if (this.mAnimationController.mValueAnimator.isRunning() || this.mMirrorSurfaceView == null) {
            return;
        }
        if (!this.mAllowDiagonalScrolling) {
            if (((double) (Math.abs(f2) / Math.abs(f))) <= HORIZONTAL_LOCK_BASE) {
                f2 = 0.0f;
            } else {
                f = 0.0f;
            }
        }
        if (updateMagnificationFramePosition((int) f, (int) f2)) {
            modifyWindowMagnification(false);
        }
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z;
        int width;
        int height;
        int diff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        if (DEBUG) {
            Log.d("WindowMagnificationController", "onConfigurationChanged = " + Configuration.configurationDiffToString(diff));
        }
        if (diff == 0) {
            return;
        }
        if ((diff & 128) != 0 && isActivated()) {
            Rect rect = new Rect(this.mWindowBounds);
            Rect bounds = this.mWm.getCurrentWindowMetrics().getBounds();
            this.mWindowBounds.set(bounds);
            float width2 = (this.mWindowBounds.width() * this.mMagnificationFrame.centerX()) / rect.width();
            float height2 = (this.mWindowBounds.height() * this.mMagnificationFrame.centerY()) / rect.height();
            if (this.mMagnificationFrame.width() > bounds.width()) {
                int width3 = bounds.width();
                height = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.height();
                width2 = bounds.width() / 2;
                width = width3;
            } else if (this.mMagnificationFrame.height() > bounds.height()) {
                height = bounds.height();
                int width4 = this.mMagnificationFrame.width() + (this.mMirrorSurfaceMargin * 2);
                float height3 = bounds.height() / 2;
                width = width4;
                height2 = height3;
            } else {
                width = this.mMagnificationFrame.width() + (this.mMirrorSurfaceMargin * 2);
                height = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.height();
            }
            calculateMagnificationFrameBoundary();
            setWindowSizeAndCenter(width, (int) width2, (int) height2, height);
        }
        if ((diff & 4) != 0 && isActivated()) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mMirrorView.getLayoutParams();
            layoutParams.accessibilityTitle = this.mResources.getString(R.string.accessibility_magnification_title);
            this.mWm.updateViewLayout(this.mMirrorView, layoutParams);
        }
        if ((diff & 4096) != 0) {
            updateDimensions();
            computeBounceAnimationScale();
            z = true;
        } else {
            z = false;
        }
        if ((diff & 512) != 0) {
            updateDragHandleResourcesIfNeeded(this.mShownSettingPanel);
        }
        if (isActivated() && z) {
            deleteWindowMagnification$1();
            enableWindowMagnificationInternal(Float.NaN);
        }
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final boolean onDrag(View view, float f, float f2) {
        if (!this.mEditSizeEnable) {
            move((int) f, (int) f2);
            return true;
        }
        if (view == this.mLeftDrag) {
            changeMagnificationFrameSize(f, 0.0f, 0.0f, 0.0f);
            return true;
        }
        if (view == this.mRightDrag) {
            changeMagnificationFrameSize(0.0f, 0.0f, f, 0.0f);
            return true;
        }
        if (view == this.mTopDrag) {
            changeMagnificationFrameSize(0.0f, f2, 0.0f, 0.0f);
            return true;
        }
        if (view == this.mBottomDrag) {
            changeMagnificationFrameSize(0.0f, 0.0f, 0.0f, f2);
            return true;
        }
        if (view == this.mTopLeftCornerView) {
            changeMagnificationFrameSize(f, f2, 0.0f, 0.0f);
            return true;
        }
        if (view == this.mTopRightCornerView) {
            changeMagnificationFrameSize(0.0f, f2, f, 0.0f);
            return true;
        }
        if (view == this.mBottomLeftCornerView) {
            changeMagnificationFrameSize(f, 0.0f, 0.0f, f2);
            return true;
        }
        if (view != this.mBottomRightCornerView) {
            return false;
        }
        changeMagnificationFrameSize(0.0f, 0.0f, f, f2);
        return true;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final boolean onFinish() {
        this.mIsDragging = false;
        return false;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final void onLongPressed(View view) {
        TooltipPopup tooltipPopup;
        if (view.getId() != R.id.close_button || (tooltipPopup = this.mTooltipPopup) == null) {
            return;
        }
        tooltipPopup.show(view, this.mCloseView.getTooltipPositionX(), this.mCloseView.getTooltipPositionY(), false, this.mContext.getString(R.string.accessibility_magnification_done_resizing));
        this.mCloseView.removeCallbacks(this.mHideTootipRunnable);
        this.mCloseView.postDelayed(this.mHideTootipRunnable, 1500L);
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final void onSingleTap(View view) {
        handleSingleTap(view);
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final void onStart() {
        this.mIsDragging = true;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (view == this.mDragView || view == this.mLeftDrag || view == this.mTopDrag || view == this.mRightDrag || view == this.mBottomDrag || view == this.mTopLeftCornerView || view == this.mTopRightCornerView || view == this.mBottomLeftCornerView || view == this.mBottomRightCornerView || view == this.mCloseView) {
            return this.mGestureDetector.onTouch(view, motionEvent);
        }
        return false;
    }

    public final void setEditMagnifierSizeMode(boolean z) {
        this.mEditSizeEnable = z;
        applyResourcesValues();
        if (this.mEditSizeEnable) {
            this.mLeftDrag.performAccessibilityAction(64, null);
        }
        if (isActivated()) {
            updateDimensions();
            applyTapExcludeRegion();
        }
    }

    public final void setWindowSizeAndCenter(int i, float f, float f2, int i2) {
        int clamp = MathUtils.clamp(i, this.mMinWindowSize, this.mWindowBounds.width());
        int clamp2 = MathUtils.clamp(i2, this.mMinWindowSize, this.mWindowBounds.height());
        if (Float.isNaN(f)) {
            f = this.mMagnificationFrame.centerX();
        }
        if (Float.isNaN(f2)) {
            f2 = this.mMagnificationFrame.centerY();
        }
        int i3 = this.mMirrorSurfaceMargin * 2;
        int i4 = clamp - i3;
        int i5 = clamp2 - i3;
        int i6 = ((int) f) - (i4 / 2);
        int i7 = ((int) f2) - (i5 / 2);
        this.mMagnificationFrame.set(i6, i7, i4 + i6, i5 + i7);
        calculateMagnificationFrameBoundary();
        updateMagnificationFramePosition(0, 0);
        modifyWindowMagnification(true);
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        SurfaceControl surfaceControl;
        int i = this.mDisplayId;
        try {
            surfaceControl = new SurfaceControl();
            WindowManagerGlobal.getWindowManagerService().mirrorDisplay(i, surfaceControl);
        } catch (RemoteException e) {
            Log.e("WindowMagnificationController", "Unable to reach window manager", e);
            surfaceControl = null;
        }
        this.mMirrorSurface = surfaceControl;
        if (surfaceControl.isValid()) {
            this.mTransaction.show(this.mMirrorSurface).reparent(this.mMirrorSurface, this.mMirrorSurfaceView.getSurfaceControl());
            this.mTransaction.setTrustedOverlay(this.mMirrorSurface, true);
            modifyWindowMagnification(false);
        }
    }

    public final void updateDimensions() {
        this.mMirrorSurfaceMargin = this.mResources.getDimensionPixelSize(R.dimen.magnification_mirror_surface_margin);
        this.mBorderDragSize = this.mResources.getDimensionPixelSize(R.dimen.magnification_border_drag_size);
        this.mOuterBorderSize = this.mResources.getDimensionPixelSize(R.dimen.magnification_outer_border_margin);
        this.mResources.getDimensionPixelSize(R.dimen.magnification_button_reposition_threshold_from_edge);
        this.mMinWindowSize = this.mResources.getDimensionPixelSize(android.R.dimen.accessibility_window_magnifier_min_size);
    }

    public final void updateDragHandleResourcesIfNeeded(boolean z) {
        if (isActivated()) {
            this.mDragView.setImageResource(z ? R.drawable.ic_move_setting_magnification_change : R.drawable.ic_move_setting_magnification);
            if (z) {
                this.mDragView.performAccessibilityAction(128, null);
            }
            this.mShownSettingPanel = z;
        }
    }

    public final boolean updateMagnificationFramePosition(int i, int i2) {
        this.mTmpRect.set(this.mMagnificationFrame);
        this.mTmpRect.offset(i, i2);
        Rect rect = this.mTmpRect;
        int i3 = rect.left;
        Rect rect2 = this.mMagnificationFrameBoundary;
        int i4 = rect2.left;
        if (i3 < i4) {
            rect.offsetTo(i4, rect.top);
        } else {
            int i5 = rect.right;
            int i6 = rect2.right;
            if (i5 > i6) {
                int width = i6 - this.mMagnificationFrame.width();
                Rect rect3 = this.mTmpRect;
                rect3.offsetTo(width, rect3.top);
            }
        }
        Rect rect4 = this.mTmpRect;
        int i7 = rect4.top;
        Rect rect5 = this.mMagnificationFrameBoundary;
        int i8 = rect5.top;
        if (i7 < i8) {
            rect4.offsetTo(rect4.left, i8);
        } else {
            int i9 = rect4.bottom;
            int i10 = rect5.bottom;
            if (i9 > i10) {
                int height = i10 - this.mMagnificationFrame.height();
                Rect rect6 = this.mTmpRect;
                rect6.offsetTo(rect6.left, height);
            }
        }
        if (this.mTmpRect.equals(this.mMagnificationFrame)) {
            return false;
        }
        this.mMagnificationFrame.set(this.mTmpRect);
        return true;
    }

    public final void updateSysUIState(boolean z) {
        int i;
        boolean z2 = isActivated() && (i = this.mSystemGestureTop) > 0 && this.mMirrorViewBounds.bottom > i;
        if (z || z2 != this.mOverlapWithGestureInsets) {
            this.mOverlapWithGestureInsets = z2;
            SysUiState sysUiState = this.mSysUiState;
            sysUiState.setFlag(524288L, z2);
            sysUiState.commitUpdate(this.mDisplayId);
        }
    }

    public final void enableWindowMagnificationInternal(float f, float f2, float f3, float f4, float f5) {
        if (Float.compare(f, 1.0f) < 0) {
            deleteWindowMagnification$1();
            return;
        }
        if (!isActivated()) {
            onConfigurationChanged(this.mResources.getConfiguration());
            this.mContext.registerComponentCallbacks(this);
        }
        this.mWindowBounds.set(this.mWm.getCurrentWindowMetrics().getBounds());
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_change_magnification_size", 3, -2);
        if (this.mPreviousMagnificationSize != intForUser) {
            changeMagnificationSize(intForUser);
        }
        this.mMagnificationFrameOffsetX = Float.isNaN(f4) ? this.mMagnificationFrameOffsetX : (int) ((this.mMagnificationFrame.width() / 2) * f4);
        int height = Float.isNaN(f5) ? this.mMagnificationFrameOffsetY : (int) ((this.mMagnificationFrame.height() / 2) * f5);
        this.mMagnificationFrameOffsetY = height;
        float f6 = f3 + height;
        float exactCenterX = Float.isNaN(f2) ? 0.0f : (f2 + this.mMagnificationFrameOffsetX) - this.mMagnificationFrame.exactCenterX();
        float exactCenterY = Float.isNaN(f3) ? 0.0f : f6 - this.mMagnificationFrame.exactCenterY();
        this.mScale = Float.isNaN(f) ? this.mScale : f;
        calculateMagnificationFrameBoundary();
        updateMagnificationFramePosition((int) exactCenterX, (int) exactCenterY);
        int i = 0;
        if (isActivated()) {
            modifyWindowMagnification(false);
        } else {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams((this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.width(), (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.height(), 2039, 40, -2);
            layoutParams.gravity = 51;
            Rect rect = this.mMagnificationFrame;
            int i2 = rect.left;
            int i3 = this.mMirrorSurfaceMargin;
            layoutParams.x = i2 - i3;
            layoutParams.y = rect.top - i3;
            layoutParams.layoutInDisplayCutoutMode = 1;
            layoutParams.receiveInsetsIgnoringZOrder = true;
            layoutParams.setTitle(this.mContext.getString(R.string.magnification_window_title));
            layoutParams.accessibilityTitle = this.mResources.getString(R.string.accessibility_magnification_title);
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.window_magnifier_view, (ViewGroup) null);
            this.mMirrorView = inflate;
            this.mMirrorSurfaceView = (SurfaceView) inflate.findViewById(R.id.surface_view);
            this.mMirrorBorderView = this.mMirrorView.findViewById(R.id.magnification_inner_border);
            this.mMirrorSurfaceView.addOnLayoutChangeListener(this.mMirrorSurfaceViewLayoutChangeListener);
            this.mMirrorView.setSystemUiVisibility(5894);
            this.mMirrorView.addOnLayoutChangeListener(this.mMirrorViewLayoutChangeListener);
            this.mMirrorView.setAccessibilityDelegate(new MirrorWindowA11yDelegate(this, i));
            this.mMirrorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda1
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    WindowMagnificationController windowMagnificationController = WindowMagnificationController.this;
                    if (!windowMagnificationController.mHandler.hasCallbacks(windowMagnificationController.mWindowInsetChangeRunnable)) {
                        windowMagnificationController.mHandler.post(windowMagnificationController.mWindowInsetChangeRunnable);
                    }
                    return view.onApplyWindowInsets(windowInsets);
                }
            });
            this.mWm.addView(this.mMirrorView, layoutParams);
            SurfaceHolder holder = this.mMirrorSurfaceView.getHolder();
            holder.addCallback(this);
            holder.setFormat(1);
            this.mDragView = (ImageView) this.mMirrorView.findViewById(R.id.drag_handle);
            this.mLeftDrag = this.mMirrorView.findViewById(R.id.left_handle);
            this.mTopDrag = this.mMirrorView.findViewById(R.id.top_handle);
            this.mRightDrag = this.mMirrorView.findViewById(R.id.right_handle);
            this.mBottomDrag = this.mMirrorView.findViewById(R.id.bottom_handle);
            this.mCloseView = (ImageView) this.mMirrorView.findViewById(R.id.close_button);
            this.mTopRightCornerView = (ImageView) this.mMirrorView.findViewById(R.id.top_right_corner);
            this.mTopLeftCornerView = (ImageView) this.mMirrorView.findViewById(R.id.top_left_corner);
            this.mBottomRightCornerView = (ImageView) this.mMirrorView.findViewById(R.id.bottom_right_corner);
            this.mBottomLeftCornerView = (ImageView) this.mMirrorView.findViewById(R.id.bottom_left_corner);
            this.mDragView.setOnTouchListener(this);
            this.mLeftDrag.setOnTouchListener(this);
            this.mTopDrag.setOnTouchListener(this);
            this.mRightDrag.setOnTouchListener(this);
            this.mBottomDrag.setOnTouchListener(this);
            this.mCloseView.setOnTouchListener(this);
            this.mTopLeftCornerView.setOnTouchListener(this);
            this.mTopRightCornerView.setOnTouchListener(this);
            this.mBottomLeftCornerView.setOnTouchListener(this);
            this.mBottomRightCornerView.setOnTouchListener(this);
            String string = this.mContext.getString(R.string.accessibility_magnification_left_handle);
            String string2 = this.mContext.getString(R.string.accessibility_magnification_top_handle);
            String string3 = this.mContext.getString(R.string.accessibility_magnification_right_handle);
            String string4 = this.mContext.getString(R.string.accessibility_magnification_bottom_handle);
            String string5 = this.mContext.getString(R.string.accessibility_magnification_top_left_handle);
            String string6 = this.mContext.getString(R.string.accessibility_magnification_top_right_handle);
            String string7 = this.mContext.getString(R.string.accessibility_magnification_bottom_left_handle);
            String string8 = this.mContext.getString(R.string.accessibility_magnification_bottom_right_handle);
            String format = String.format(this.mContext.getResources().getString(R.string.accessibility_magnification_window_and_hold_to), this.mContext.getString(R.string.accessibility_magnification_window_double_tap), this.mContext.getString(R.string.accessibility_magnification_window_move));
            Configuration configuration = this.mContext.getResources().getConfiguration();
            if (configuration != null && (configuration.screenLayout & 192) == 128) {
                this.mLeftDrag.setContentDescription(string3 + " " + format);
                this.mRightDrag.setContentDescription(string + " " + format);
                this.mTopLeftCornerView.setContentDescription(string6 + " " + format);
                this.mTopRightCornerView.setContentDescription(string5 + " " + format);
                this.mBottomLeftCornerView.setContentDescription(string8 + " " + format);
                this.mBottomRightCornerView.setContentDescription(string7 + " " + format);
            } else {
                this.mLeftDrag.setContentDescription(string + " " + format);
                this.mRightDrag.setContentDescription(string3 + " " + format);
                this.mTopLeftCornerView.setContentDescription(string5 + " " + format);
                this.mTopRightCornerView.setContentDescription(string6 + " " + format);
                this.mBottomLeftCornerView.setContentDescription(string7 + " " + format);
                this.mBottomRightCornerView.setContentDescription(string8 + " " + format);
            }
            this.mTopDrag.setContentDescription(string2 + " " + format);
            this.mBottomDrag.setContentDescription(string4 + " " + format);
            this.mDragView.setContentDescription(this.mContext.getString(R.string.accessibility_magnification_handle) + " " + format);
            this.mDragView.setAccessibilityDelegate(new DragHandleA11yDelegate(this, i));
            this.mCloseView.setContentDescription(this.mContext.getString(R.string.accessibility_magnification_done_resizing));
            this.mCloseView.setAccessibilityDelegate(new DragHandleA11yDelegate(this, i));
            MirrorWindowControl mirrorWindowControl = this.mMirrorWindowControl;
            if (mirrorWindowControl != null) {
                if (mirrorWindowControl.mControlsView != null) {
                    Log.w("MirrorWindowControl", "control view is visible");
                } else {
                    Point point = mirrorWindowControl.mTmpPoint;
                    Context context = mirrorWindowControl.mContext;
                    LayoutInflater.from(context);
                    mirrorWindowControl.mControlsView = mirrorWindowControl.onCreateView();
                    WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
                    int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.magnification_controls_size);
                    int i4 = point.x;
                    if (i4 <= 0) {
                        i4 = dimensionPixelSize;
                    }
                    layoutParams2.width = i4;
                    int i5 = point.y;
                    if (i5 > 0) {
                        dimensionPixelSize = i5;
                    }
                    layoutParams2.height = dimensionPixelSize;
                    layoutParams2.gravity = 51;
                    layoutParams2.flags = 40;
                    layoutParams2.type = 2039;
                    layoutParams2.format = 1;
                    layoutParams2.setTitle(mirrorWindowControl.getWindowTitle());
                    context.getDisplay().getSize(point);
                    int i6 = point.x - layoutParams2.width;
                    layoutParams2.x = i6;
                    int i7 = point.y - layoutParams2.height;
                    layoutParams2.y = i7;
                    mirrorWindowControl.mControlPosition.set(i6, i7);
                    mirrorWindowControl.mWindowManager.addView(mirrorWindowControl.mControlsView, layoutParams2);
                    int i8 = layoutParams2.width;
                    int i9 = layoutParams2.height;
                    context.getDisplay().getSize(point);
                    int i10 = point.x - i8;
                    int i11 = point.y - i9;
                    Rect rect2 = mirrorWindowControl.mDraggableBound;
                    rect2.set(0, 0, i10, i11);
                    if (MirrorWindowControl.DBG) {
                        Log.d("MirrorWindowControl", "updateDraggableBound :" + rect2);
                    }
                }
            }
            if (this.mTipPopupCnt <= 1) {
                this.mMirrorView.post(new WindowMagnificationController$$ExternalSyntheticLambda0(this, i));
            }
            applyResourcesValues();
        }
        this.mAllowDiagonalScrolling = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_allow_diagonal_scrolling", 1, -2) == 1;
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }
}
