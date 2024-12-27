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
import android.view.AttachedSurfaceControl;
import android.view.Display;
import android.view.IWindowSession;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IMagnificationConnectionCallback;
import android.widget.Button;
import android.widget.ImageView;
import androidx.core.math.MathUtils;
import com.android.internal.accessibility.common.MagnificationConstants;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.internal.view.TooltipPopup;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.accessibility.Magnification;
import com.android.systemui.accessibility.MagnificationGestureDetector;
import com.android.systemui.model.SysUiState;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.widget.SemTipPopup;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.function.Supplier;

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
    public ImageView mCursorView;
    public float mCursorX;
    public float mCursorY;
    public final int mDisplayId;
    public ImageView mDragView;
    public boolean mEditSizeEnable;
    public final MagnificationGestureDetector mGestureDetector;
    public final Handler mHandler;
    public final WindowMagnificationController$$ExternalSyntheticLambda0 mHideTootipRunnable;
    public boolean mIsDragging;
    public boolean mIsTwoFingerDragMove;
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
    public final WindowMagnificationController$$ExternalSyntheticLambda4 mMirrorSurfaceViewLayoutChangeListener;
    public View mMirrorView;
    public final Rect mMirrorViewBounds;
    public final WindowMagnificationController$$ExternalSyntheticLambda4 mMirrorViewLayoutChangeListener;
    public final AnonymousClass1 mMirrorViewRunnable;
    public final MirrorWindowControl mMirrorWindowControl;
    public int mOuterBorderSize;
    public boolean mOverlapWithGestureInsets;
    public NumberFormat mPercentFormat;
    public int mPreviousMagnificationSize;
    public final Resources mResources;
    public View mRightDrag;
    int mRotation;
    public float mScale;
    public final Supplier mScvhSupplier;
    public final Rect mSourceBounds;
    public SurfaceControlViewHost mSurfaceControlViewHost;
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
    WindowMagnificationFrameSizePrefs mWindowMagnificationFrameSizePrefs;
    public final WindowMagnifierCallback mWindowMagnifierCallback;
    public final WindowManager mWm;

    /* renamed from: com.android.systemui.accessibility.WindowMagnificationController$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final Rect mPreviousBounds = new Rect();

        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            IMagnificationConnectionCallback iMagnificationConnectionCallback;
            if (WindowMagnificationController.this.mMirrorView != null) {
                if (this.mPreviousBounds.width() != WindowMagnificationController.this.mMirrorViewBounds.width() || this.mPreviousBounds.height() != WindowMagnificationController.this.mMirrorViewBounds.height()) {
                    WindowMagnificationController.this.mMirrorView.setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, 0, WindowMagnificationController.this.mMirrorViewBounds.width(), WindowMagnificationController.this.mMirrorViewBounds.height())));
                    this.mPreviousBounds.set(WindowMagnificationController.this.mMirrorViewBounds);
                }
                WindowMagnificationController.this.updateSysUIState(false);
                WindowMagnificationController windowMagnificationController = WindowMagnificationController.this;
                WindowMagnifierCallback windowMagnifierCallback = windowMagnificationController.mWindowMagnifierCallback;
                int i = windowMagnificationController.mDisplayId;
                Rect rect = windowMagnificationController.mMirrorViewBounds;
                MagnificationConnectionImpl magnificationConnectionImpl = Magnification.this.mMagnificationConnectionImpl;
                if (magnificationConnectionImpl == null || (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) == null) {
                    return;
                }
                try {
                    iMagnificationConnectionCallback.onWindowMagnifierBoundsChanged(i, rect);
                } catch (RemoteException e) {
                    Log.e("WindowMagnificationConnectionImpl", "Failed to inform bounds changed", e);
                }
            }
        }
    }

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

    public final class MirrorWindowA11yDelegate extends View.AccessibilityDelegate {
        public /* synthetic */ MirrorWindowA11yDelegate(WindowMagnificationController windowMagnificationController, int i) {
            this();
        }

        @Override // android.view.View.AccessibilityDelegate
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_zoom_in, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_zoom_in)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_zoom_out, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_zoom_out)));
            WindowMagnificationController windowMagnificationController = WindowMagnificationController.this;
            if (!windowMagnificationController.mEditSizeEnable) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_up, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_move_up)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_down, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_move_down)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_left, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_move_left)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_right, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_move_right)));
                return;
            }
            int width = windowMagnificationController.mMagnificationFrame.width();
            WindowMagnificationController windowMagnificationController2 = WindowMagnificationController.this;
            if ((windowMagnificationController2.mMirrorSurfaceMargin * 2) + width < windowMagnificationController2.mWindowBounds.width()) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_increase_window_width, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_increase_window_width)));
            }
            int height = WindowMagnificationController.this.mMagnificationFrame.height();
            WindowMagnificationController windowMagnificationController3 = WindowMagnificationController.this;
            if ((windowMagnificationController3.mMirrorSurfaceMargin * 2) + height < windowMagnificationController3.mWindowBounds.height()) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_increase_window_height, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_increase_window_height)));
            }
            int width2 = WindowMagnificationController.this.mMagnificationFrame.width();
            WindowMagnificationController windowMagnificationController4 = WindowMagnificationController.this;
            if ((windowMagnificationController4.mMirrorSurfaceMargin * 2) + width2 > windowMagnificationController4.mMinWindowSize) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_decrease_window_width, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_decrease_window_width)));
            }
            int height2 = WindowMagnificationController.this.mMagnificationFrame.height();
            WindowMagnificationController windowMagnificationController5 = WindowMagnificationController.this;
            if ((windowMagnificationController5.mMirrorSurfaceMargin * 2) + height2 > windowMagnificationController5.mMinWindowSize) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_decrease_window_height, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_decrease_window_height)));
            }
        }

        @Override // android.view.View.AccessibilityDelegate
        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            IMagnificationConnectionCallback iMagnificationConnectionCallback;
            float fraction = WindowMagnificationController.this.mContext.getResources().getFraction(R.fraction.magnification_resize_window_size_amount, 1, 1);
            if (i == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                WindowMagnificationController windowMagnificationController = WindowMagnificationController.this;
                if (windowMagnificationController.mEditSizeEnable) {
                    windowMagnificationController.setEditMagnifierSizeMode(false);
                } else {
                    windowMagnificationController.handleSingleTap(windowMagnificationController.mDragView);
                }
            } else if (i == R.id.accessibility_action_zoom_in) {
                performScale(WindowMagnificationController.this.mScale + 1.0f);
            } else if (i == R.id.accessibility_action_zoom_out) {
                performScale(WindowMagnificationController.this.mScale - 1.0f);
            } else if (i == R.id.accessibility_action_move_up) {
                WindowMagnificationController windowMagnificationController2 = WindowMagnificationController.this;
                windowMagnificationController2.move(0, -windowMagnificationController2.mSourceBounds.height());
            } else if (i == R.id.accessibility_action_move_down) {
                WindowMagnificationController windowMagnificationController3 = WindowMagnificationController.this;
                windowMagnificationController3.move(0, windowMagnificationController3.mSourceBounds.height());
            } else if (i == R.id.accessibility_action_move_left) {
                WindowMagnificationController windowMagnificationController4 = WindowMagnificationController.this;
                windowMagnificationController4.move(-windowMagnificationController4.mSourceBounds.width(), 0);
            } else if (i == R.id.accessibility_action_move_right) {
                WindowMagnificationController windowMagnificationController5 = WindowMagnificationController.this;
                windowMagnificationController5.move(windowMagnificationController5.mSourceBounds.width(), 0);
            } else if (i == R.id.accessibility_action_increase_window_width) {
                WindowMagnificationController windowMagnificationController6 = WindowMagnificationController.this;
                WindowMagnificationController.m882$$Nest$msetMagnificationFrameSize(windowMagnificationController6, (int) ((fraction + 1.0f) * WindowMagnificationController.this.mMagnificationFrame.width()), windowMagnificationController6.mMagnificationFrame.height());
            } else if (i == R.id.accessibility_action_increase_window_height) {
                WindowMagnificationController windowMagnificationController7 = WindowMagnificationController.this;
                WindowMagnificationController.m882$$Nest$msetMagnificationFrameSize(windowMagnificationController7, windowMagnificationController7.mMagnificationFrame.width(), (int) ((fraction + 1.0f) * WindowMagnificationController.this.mMagnificationFrame.height()));
            } else if (i == R.id.accessibility_action_decrease_window_width) {
                WindowMagnificationController windowMagnificationController8 = WindowMagnificationController.this;
                WindowMagnificationController.m882$$Nest$msetMagnificationFrameSize(windowMagnificationController8, (int) ((1.0f - fraction) * WindowMagnificationController.this.mMagnificationFrame.width()), windowMagnificationController8.mMagnificationFrame.height());
            } else {
                if (i != R.id.accessibility_action_decrease_window_height) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                WindowMagnificationController windowMagnificationController9 = WindowMagnificationController.this;
                WindowMagnificationController.m882$$Nest$msetMagnificationFrameSize(windowMagnificationController9, windowMagnificationController9.mMagnificationFrame.width(), (int) ((1.0f - fraction) * WindowMagnificationController.this.mMagnificationFrame.height()));
            }
            WindowMagnificationController windowMagnificationController10 = WindowMagnificationController.this;
            WindowMagnifierCallback windowMagnifierCallback = windowMagnificationController10.mWindowMagnifierCallback;
            int i2 = windowMagnificationController10.mDisplayId;
            MagnificationConnectionImpl magnificationConnectionImpl = Magnification.this.mMagnificationConnectionImpl;
            if (magnificationConnectionImpl != null && (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) != null) {
                try {
                    iMagnificationConnectionCallback.onAccessibilityActionPerformed(i2);
                } catch (RemoteException e) {
                    Log.e("WindowMagnificationConnectionImpl", "Failed to inform an accessibility action is already performed", e);
                }
            }
            return true;
        }

        public final void performScale(float f) {
            IMagnificationConnectionCallback iMagnificationConnectionCallback;
            float floatValue = ((Float) WindowMagnificationController.A11Y_ACTION_SCALE_RANGE.clamp(Float.valueOf(f))).floatValue();
            WindowMagnificationController windowMagnificationController = WindowMagnificationController.this;
            WindowMagnifierCallback windowMagnifierCallback = windowMagnificationController.mWindowMagnifierCallback;
            int i = windowMagnificationController.mDisplayId;
            MagnificationConnectionImpl magnificationConnectionImpl = Magnification.this.mMagnificationConnectionImpl;
            if (magnificationConnectionImpl == null || (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) == null) {
                return;
            }
            try {
                iMagnificationConnectionCallback.onPerformScaleAction(i, floatValue, true);
            } catch (RemoteException e) {
                Log.e("WindowMagnificationConnectionImpl", "Failed to inform performing scale action", e);
            }
        }

        private MirrorWindowA11yDelegate() {
        }
    }

    /* renamed from: -$$Nest$msetMagnificationFrameSize, reason: not valid java name */
    public static void m882$$Nest$msetMagnificationFrameSize(WindowMagnificationController windowMagnificationController, int i, int i2) {
        int i3 = windowMagnificationController.mMirrorSurfaceMargin * 2;
        windowMagnificationController.setWindowSizeAndCenter(i + i3, Float.NaN, Float.NaN, i3 + i2);
    }

    static {
        DEBUG = Log.isLoggable("WindowMagnificationController", 3) || Build.IS_DEBUGGABLE;
        A11Y_ACTION_SCALE_RANGE = new Range(Float.valueOf(1.0f), Float.valueOf(MagnificationConstants.SCALE_MAX_VALUE));
        HORIZONTAL_LOCK_BASE = Math.tan(Math.toRadians(50.0d));
    }

    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda4] */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda4] */
    public WindowMagnificationController(Context context, Handler handler, WindowMagnificationAnimationController windowMagnificationAnimationController, MirrorWindowControl mirrorWindowControl, SurfaceControl.Transaction transaction, WindowMagnifierCallback windowMagnifierCallback, SysUiState sysUiState, SecureSettings secureSettings, Supplier<SurfaceControlViewHost> supplier, SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider, Supplier<IWindowSession> supplier2) {
        Size parseSize;
        SparseArray sparseArray = new SparseArray();
        this.mMagnificationSizeScaleOptions = sparseArray;
        this.mMagnificationFrame = new Rect();
        this.mTmpRect = new Rect();
        this.mMirrorViewBounds = new Rect();
        this.mSourceBounds = new Rect();
        this.mMagnificationFrameOffsetX = 0;
        this.mMagnificationFrameOffsetY = 0;
        this.mMagnificationFrameBoundary = new Rect();
        this.mSystemGestureTop = -1;
        this.mAllowDiagonalScrolling = false;
        this.mEditSizeEnable = false;
        this.mIsTwoFingerDragMove = false;
        this.mTipPopup = null;
        this.mTipPopupCnt = 0;
        this.mPreviousMagnificationSize = 0;
        this.mHideTootipRunnable = new WindowMagnificationController$$ExternalSyntheticLambda0(this, 1);
        this.mContext = context;
        this.mHandler = handler;
        this.mAnimationController = windowMagnificationAnimationController;
        windowMagnificationAnimationController.mOnAnimationEndRunnable = new WindowMagnificationController$$ExternalSyntheticLambda0(this, 2);
        windowMagnificationAnimationController.mController = this;
        this.mWindowMagnifierCallback = windowMagnifierCallback;
        this.mSysUiState = sysUiState;
        this.mScvhSupplier = supplier;
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
        this.mWindowMagnificationFrameSizePrefs = new WindowMagnificationFrameSizePrefs(context);
        context.setTheme(android.R.style.Theme.DeviceDefault.DayNight);
        Display display = context.getDisplay();
        this.mDisplayId = context.getDisplayId();
        this.mRotation = display.getRotation();
        WindowManager windowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mWm = windowManager;
        Rect rect = new Rect(windowManager.getCurrentWindowMetrics().getBounds());
        this.mWindowBounds = rect;
        int i = context.getResources().getConfiguration().semDisplayDeviceType;
        Math.min(rect.width(), rect.height());
        Resources resources = context.getResources();
        this.mResources = resources;
        this.mScale = secureSettings.getFloatForUser("accessibility_display_magnification_scale", resources.getInteger(R.integer.magnification_default_scale), -2);
        this.mAllowDiagonalScrolling = secureSettings.getIntForUser("accessibility_allow_diagonal_scrolling", 1, -2) == 1;
        sparseArray.clear();
        sparseArray.put(1, Float.valueOf(1.4f));
        sparseArray.put(2, Float.valueOf(1.8f));
        sparseArray.put(3, Float.valueOf(2.5f));
        this.mBounceEffectDuration = resources.getInteger(android.R.integer.config_shortAnimTime);
        updateDimensions();
        WindowMagnificationFrameSizePrefs windowMagnificationFrameSizePrefs = this.mWindowMagnificationFrameSizePrefs;
        if (windowMagnificationFrameSizePrefs.mWindowMagnificationSizePreferences.contains(String.valueOf(windowMagnificationFrameSizePrefs.mContext.getResources().getConfiguration().smallestScreenWidthDp))) {
            WindowMagnificationFrameSizePrefs windowMagnificationFrameSizePrefs2 = this.mWindowMagnificationFrameSizePrefs;
            parseSize = Size.parseSize(windowMagnificationFrameSizePrefs2.mWindowMagnificationSizePreferences.getString(String.valueOf(windowMagnificationFrameSizePrefs2.mContext.getResources().getConfiguration().smallestScreenWidthDp), null));
        } else {
            int magnificationWindowSizeFromIndex = getMagnificationWindowSizeFromIndex(2) - (this.mMirrorSurfaceMargin * 2);
            parseSize = new Size(magnificationWindowSizeFromIndex, magnificationWindowSizeFromIndex);
        }
        setMagnificationFrame(parseSize.getWidth(), parseSize.getHeight(), rect.width() / 2, rect.height() / 2);
        computeBounceAnimationScale();
        this.mMirrorWindowControl = mirrorWindowControl;
        this.mTransaction = transaction;
        this.mGestureDetector = new MagnificationGestureDetector(context, handler, this);
        this.mWindowInsetChangeRunnable = new WindowMagnificationController$$ExternalSyntheticLambda0(this, 3);
        Flags.createWindowlessWindowMagnifier();
        this.mMirrorViewRunnable = new AnonymousClass1();
        final int i2 = 0;
        this.mMirrorSurfaceViewLayoutChangeListener = new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda4
            public final /* synthetic */ WindowMagnificationController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                int i11 = i2;
                WindowMagnificationController windowMagnificationController = this.f$0;
                switch (i11) {
                    case 0:
                        windowMagnificationController.mMirrorView.post(new WindowMagnificationController$$ExternalSyntheticLambda0(windowMagnificationController, 6));
                        break;
                    default:
                        if (!windowMagnificationController.mHandler.hasCallbacks(windowMagnificationController.mMirrorViewRunnable)) {
                            windowMagnificationController.mHandler.post(windowMagnificationController.mMirrorViewRunnable);
                            break;
                        }
                        break;
                }
            }
        };
        final int i3 = 1;
        this.mMirrorViewLayoutChangeListener = new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda4
            public final /* synthetic */ WindowMagnificationController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i32, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                int i11 = i3;
                WindowMagnificationController windowMagnificationController = this.f$0;
                switch (i11) {
                    case 0:
                        windowMagnificationController.mMirrorView.post(new WindowMagnificationController$$ExternalSyntheticLambda0(windowMagnificationController, 6));
                        break;
                    default:
                        if (!windowMagnificationController.mHandler.hasCallbacks(windowMagnificationController.mMirrorViewRunnable)) {
                            windowMagnificationController.mHandler.post(windowMagnificationController.mMirrorViewRunnable);
                            break;
                        }
                        break;
                }
            }
        };
        this.mUpdateStateDescriptionRunnable = new WindowMagnificationController$$ExternalSyntheticLambda0(this, 4);
        this.mTooltipPopup = new TooltipPopup(context);
    }

    public static boolean isRTL(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        return configuration != null && (configuration.screenLayout & 192) == 128;
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
        Flags.createWindowlessWindowMagnifier();
        if (this.mMirrorView == null) {
            return;
        }
        AttachedSurfaceControl rootSurfaceControl = this.mSurfaceControlViewHost.getRootSurfaceControl();
        Region region = new Region(0, 0, this.mMirrorView.getWidth(), this.mMirrorView.getHeight());
        int i = this.mBorderDragSize;
        region.op(new Region(i, i, this.mMirrorView.getWidth() - this.mBorderDragSize, this.mMirrorView.getHeight() - this.mBorderDragSize), Region.Op.DIFFERENCE);
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
        Region.Op op = Region.Op.UNION;
        region.op(rect, op);
        region.op(rect2, op);
        region.op(rect3, op);
        region.op(rect4, op);
        region.op(rect5, op);
        region.op(rect6, op);
        rootSurfaceControl.setTouchableRegion(region);
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
        isRTL(this.mContext);
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

    public final void computeBounceAnimationScale() {
        float width = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.width();
        this.mBounceEffectAnimationScale = Math.min(width / (width - (this.mOuterBorderSize * 2)), 1.05f);
    }

    public final void deleteWindowMagnification$1() {
        IMagnificationConnectionCallback iMagnificationConnectionCallback;
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
                Flags.createWindowlessWindowMagnifier();
                this.mMirrorView = null;
            }
            MirrorWindowControl mirrorWindowControl = this.mMirrorWindowControl;
            if (mirrorWindowControl != null && (view = mirrorWindowControl.mControlsView) != null) {
                mirrorWindowControl.mWindowManager.removeView(view);
                mirrorWindowControl.mControlsView = null;
            }
            SurfaceControlViewHost surfaceControlViewHost = this.mSurfaceControlViewHost;
            if (surfaceControlViewHost != null) {
                surfaceControlViewHost.release();
                this.mSurfaceControlViewHost = null;
            }
            this.mMirrorViewBounds.setEmpty();
            this.mSourceBounds.setEmpty();
            updateSysUIState(false);
            setEditMagnifierSizeMode(false);
            this.mContext.unregisterComponentCallbacks(this);
            WindowMagnifierCallback windowMagnifierCallback = this.mWindowMagnifierCallback;
            int i = this.mDisplayId;
            Rect rect = new Rect();
            MagnificationConnectionImpl magnificationConnectionImpl = Magnification.this.mMagnificationConnectionImpl;
            if (magnificationConnectionImpl == null || (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) == null) {
                return;
            }
            try {
                iMagnificationConnectionCallback.onSourceBoundsChanged(i, rect);
            } catch (RemoteException e) {
                Log.e("WindowMagnificationConnectionImpl", "Failed to inform source bounds changed", e);
            }
        }
    }

    public final int getMagnificationWindowSizeFromIndex(int i) {
        int min = (int) ((Math.min(this.mWindowBounds.width(), this.mWindowBounds.height()) / 3) * ((Float) this.mMagnificationSizeScaleOptions.get(i, Float.valueOf(1.0f))).floatValue());
        return min - (min % 2);
    }

    public final void handleSingleTap(View view) {
        int i = 1;
        int id = view.getId();
        if (id == R.id.drag_handle) {
            Magnification.AnonymousClass3 anonymousClass3 = (Magnification.AnonymousClass3) this.mWindowMagnifierCallback;
            Magnification.this.mHandler.post(new Magnification$$ExternalSyntheticLambda2(anonymousClass3, this.mDisplayId, i));
        } else {
            if (id == R.id.close_button) {
                setEditMagnifierSizeMode(false);
                return;
            }
            View view2 = this.mMirrorView;
            if (view2 == null) {
                return;
            }
            ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view2, PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, 1.0f, this.mBounceEffectAnimationScale, 1.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, 1.0f, this.mBounceEffectAnimationScale, 1.0f));
            ofPropertyValuesHolder.setDuration(this.mBounceEffectDuration);
            ofPropertyValuesHolder.start();
        }
    }

    public final boolean isActivated() {
        return this.mMirrorView != null;
    }

    public boolean isDiagonalScrollingEnabled() {
        return this.mAllowDiagonalScrolling;
    }

    public final void modifyWindowMagnification(boolean z) {
        IMagnificationConnectionCallback iMagnificationConnectionCallback;
        Flags.createWindowlessWindowMagnifier();
        if (isActivated() && this.mMirrorSurface != null) {
            Rect rect = this.mMagnificationFrame;
            float f = this.mScale;
            this.mTmpRect.set(this.mSourceBounds);
            int width = rect.width() / 2;
            int height = rect.height() / 2;
            int i = width - ((int) (width / f));
            int i2 = height - ((int) (height / f));
            this.mSourceBounds.set(rect.left + i, rect.top + i2, rect.right - i, rect.bottom - i2);
            this.mSourceBounds.offset(-this.mMagnificationFrameOffsetX, -this.mMagnificationFrameOffsetY);
            Rect rect2 = this.mSourceBounds;
            if (rect2.left < 0) {
                rect2.offsetTo(0, rect2.top);
            } else if (rect2.right > this.mWindowBounds.width()) {
                this.mSourceBounds.offsetTo(this.mWindowBounds.width() - this.mSourceBounds.width(), this.mSourceBounds.top);
            }
            Rect rect3 = this.mSourceBounds;
            if (rect3.top < 0) {
                rect3.offsetTo(rect3.left, 0);
            } else if (rect3.bottom > this.mWindowBounds.height()) {
                Rect rect4 = this.mSourceBounds;
                rect4.offsetTo(rect4.left, this.mWindowBounds.height() - this.mSourceBounds.height());
            }
            if (!this.mSourceBounds.equals(r3)) {
                this.mTmpRect.set(0, 0, this.mMagnificationFrame.width(), this.mMagnificationFrame.height());
                this.mTransaction.setGeometry(this.mMirrorSurface, this.mSourceBounds, this.mTmpRect, 0);
                if (!this.mAnimationController.mValueAnimator.isRunning()) {
                    WindowMagnifierCallback windowMagnifierCallback = this.mWindowMagnifierCallback;
                    int i3 = this.mDisplayId;
                    Rect rect5 = this.mSourceBounds;
                    MagnificationConnectionImpl magnificationConnectionImpl = Magnification.this.mMagnificationConnectionImpl;
                    if (magnificationConnectionImpl != null && (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) != null) {
                        try {
                            iMagnificationConnectionCallback.onSourceBoundsChanged(i3, rect5);
                        } catch (RemoteException e) {
                            Log.e("WindowMagnificationConnectionImpl", "Failed to inform source bounds changed", e);
                        }
                    }
                }
            }
        }
        if (isActivated()) {
            int width2 = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.width();
            int height2 = this.mMagnificationFrame.height();
            int i4 = this.mMirrorSurfaceMargin;
            int i5 = (i4 * 2) + height2;
            int i6 = this.mOuterBorderSize;
            int clamp = MathUtils.clamp(this.mMagnificationFrame.left - i4, -i6, (this.mWindowBounds.right - width2) + i6);
            int i7 = this.mOuterBorderSize;
            int clamp2 = MathUtils.clamp(this.mMagnificationFrame.top - this.mMirrorSurfaceMargin, -i7, (this.mWindowBounds.bottom - i5) + i7);
            if (z) {
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mMirrorView.getLayoutParams();
                layoutParams.width = width2;
                layoutParams.height = i5;
                this.mSurfaceControlViewHost.relayout(layoutParams);
                this.mTransaction.setCrop(this.mSurfaceControlViewHost.getSurfacePackage().getSurfaceControl(), new Rect(0, 0, width2, i5));
            }
            this.mMirrorViewBounds.set(clamp, clamp2, width2 + clamp, i5 + clamp2);
            this.mTransaction.setPosition(this.mSurfaceControlViewHost.getSurfacePackage().getSurfaceControl(), clamp, clamp2);
            if (z) {
                this.mSurfaceControlViewHost.getRootSurfaceControl().applyTransactionOnDraw(this.mTransaction);
            } else {
                this.mTransaction.apply();
            }
            if (!this.mIsDragging) {
                this.mMirrorView.post(new WindowMagnificationController$$ExternalSyntheticLambda0(this, 5));
            }
            this.mMirrorViewRunnable.run();
        }
    }

    public final void move(int i, int i2) {
        IMagnificationConnectionCallback iMagnificationConnectionCallback;
        moveWindowMagnifier(i, i2);
        WindowMagnifierCallback windowMagnifierCallback = this.mWindowMagnifierCallback;
        int i3 = this.mDisplayId;
        MagnificationConnectionImpl magnificationConnectionImpl = Magnification.this.mMagnificationConnectionImpl;
        if (magnificationConnectionImpl == null || (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) == null) {
            return;
        }
        try {
            iMagnificationConnectionCallback.onMove(i3);
        } catch (RemoteException e) {
            Log.e("WindowMagnificationConnectionImpl", "Failed to inform taking control by a user", e);
        }
    }

    public final void moveWindowMagnifier(float f, float f2) {
        if (this.mAnimationController.mValueAnimator.isRunning() || this.mMirrorSurfaceView == null) {
            return;
        }
        if (!this.mAllowDiagonalScrolling) {
            if (Math.abs(f2) / Math.abs(f) <= HORIZONTAL_LOCK_BASE) {
                f2 = 0.0f;
            } else {
                f = 0.0f;
            }
        }
        float width = this.mCursorView.getWidth() / 4;
        this.mCursorX = this.mCursorView.getX() + f;
        this.mCursorY = this.mCursorView.getY() + f2;
        float f3 = this.mCursorX;
        if (f3 < 0.0f) {
            this.mCursorX = 0.0f;
        } else if (f3 > this.mMagnificationFrame.width() - width) {
            this.mCursorX = this.mMagnificationFrame.width() - width;
        }
        float f4 = this.mCursorY;
        if (f4 < 0.0f) {
            this.mCursorY = 0.0f;
        } else if (f4 > this.mMagnificationFrame.height() - width) {
            this.mCursorY = this.mMagnificationFrame.height() - width;
        }
        Rect rect = this.mMagnificationFrame;
        if (!rect.contains(((int) this.mCursorX) + rect.left, ((int) this.mCursorY) + rect.top)) {
            this.mCursorX = this.mMagnificationFrame.width() / 2;
            this.mCursorY = this.mMagnificationFrame.height() / 2;
        }
        if (updateMagnificationFramePosition((int) f, (int) f2)) {
            modifyWindowMagnification(false);
            if (this.mIsDragging || this.mIsTwoFingerDragMove) {
                this.mCursorX = this.mMagnificationFrame.width() / 2;
                this.mCursorY = this.mMagnificationFrame.height() / 2;
                this.mCursorView.setX(this.mCursorX);
                this.mCursorView.setY(this.mCursorY);
                return;
            }
            Rect rect2 = this.mMagnificationFrame;
            if (rect2.left <= 0 || rect2.right >= this.mWindowBounds.right) {
                this.mCursorView.setX(this.mCursorX);
            }
            Rect rect3 = this.mMagnificationFrame;
            if (rect3.top <= 0 || rect3.bottom >= this.mWindowBounds.bottom) {
                this.mCursorView.setY(this.mCursorY);
            }
        } else {
            this.mCursorView.setX(this.mCursorX);
            this.mCursorView.setY(this.mCursorY);
        }
        this.mIsTwoFingerDragMove = false;
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z;
        int height;
        float f;
        int i;
        float f2;
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
            float width = (this.mWindowBounds.width() * this.mMagnificationFrame.centerX()) / rect.width();
            float height2 = (this.mWindowBounds.height() * this.mMagnificationFrame.centerY()) / rect.height();
            if (this.mMagnificationFrame.width() > bounds.width()) {
                i = bounds.width();
                height = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.height();
                f2 = bounds.width() / 2;
            } else {
                if (this.mMagnificationFrame.height() > bounds.height()) {
                    height = bounds.height();
                    f = width;
                    i = this.mMagnificationFrame.width() + (this.mMirrorSurfaceMargin * 2);
                    height2 = bounds.height() / 2;
                } else {
                    int width2 = this.mMagnificationFrame.width() + (this.mMirrorSurfaceMargin * 2);
                    height = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.height();
                    f = width;
                    i = width2;
                }
                f2 = f;
            }
            calculateMagnificationFrameBoundary();
            setWindowSizeAndCenter(i, (int) f2, (int) height2, height);
        }
        if ((diff & 4) != 0 && isActivated()) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mMirrorView.getLayoutParams();
            layoutParams.accessibilityTitle = this.mResources.getString(R.string.accessibility_magnification_title);
            Flags.createWindowlessWindowMagnifier();
            this.mSurfaceControlViewHost.relayout(layoutParams);
        }
        if ((diff & 4096) != 0) {
            updateDimensions();
            computeBounceAnimationScale();
            z = true;
        } else {
            z = false;
        }
        if (isActivated() && z) {
            deleteWindowMagnification$1();
            updateWindowMagnificationInternal(Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
        }
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final boolean onDrag(View view, float f, float f2) {
        if (!this.mEditSizeEnable) {
            this.mIsTwoFingerDragMove = true;
            move((int) f, (int) f2);
            return true;
        }
        if (view == this.mLeftDrag) {
            if (isRTL(this.mContext)) {
                changeMagnificationFrameSize(0.0f, 0.0f, f, 0.0f);
                return true;
            }
            changeMagnificationFrameSize(f, 0.0f, 0.0f, 0.0f);
            return true;
        }
        if (view == this.mRightDrag) {
            if (isRTL(this.mContext)) {
                changeMagnificationFrameSize(f, 0.0f, 0.0f, 0.0f);
                return true;
            }
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
        if (z) {
            return;
        }
        WindowMagnificationFrameSizePrefs windowMagnificationFrameSizePrefs = this.mWindowMagnificationFrameSizePrefs;
        windowMagnificationFrameSizePrefs.mWindowMagnificationSizePreferences.edit().putString(String.valueOf(windowMagnificationFrameSizePrefs.mContext.getResources().getConfiguration().smallestScreenWidthDp), new Size(this.mMagnificationFrame.width(), this.mMagnificationFrame.height()).toString()).apply();
    }

    public final void setMagnificationFrame(int i, int i2, int i3, int i4) {
        WindowMagnificationFrameSizePrefs windowMagnificationFrameSizePrefs = this.mWindowMagnificationFrameSizePrefs;
        windowMagnificationFrameSizePrefs.mWindowMagnificationSizePreferences.edit().putString(String.valueOf(windowMagnificationFrameSizePrefs.mContext.getResources().getConfiguration().smallestScreenWidthDp), new Size(i, i2).toString()).apply();
        int i5 = i3 - (i / 2);
        int i6 = i4 - (i2 / 2);
        this.mMagnificationFrame.set(i5, i6, i + i5, i2 + i6);
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
        int i3 = this.mMirrorSurfaceMargin;
        setMagnificationFrame(clamp - (i3 * 2), clamp2 - (i3 * 2), (int) f, (int) f2);
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
        this.mMinWindowSize = this.mResources.getDimensionPixelSize(android.R.dimen.action_bar_content_inset_material);
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

    public final void updateWindowMagnificationInternal(float f, float f2, float f3, float f4, float f5) {
        View view;
        float f6 = f;
        if (Float.compare(f6, 1.0f) < 0) {
            deleteWindowMagnification$1();
            return;
        }
        if (!isActivated()) {
            onConfigurationChanged(this.mResources.getConfiguration());
            this.mContext.registerComponentCallbacks(this);
        }
        this.mWindowBounds.set(this.mWm.getCurrentWindowMetrics().getBounds());
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_change_magnification_size", 3, -2);
        if (this.mPreviousMagnificationSize != intForUser && this.mMagnificationSizeScaleOptions.contains(intForUser)) {
            int magnificationWindowSizeFromIndex = getMagnificationWindowSizeFromIndex(intForUser);
            setWindowSizeAndCenter(magnificationWindowSizeFromIndex, Float.NaN, Float.NaN, magnificationWindowSizeFromIndex);
            this.mPreviousMagnificationSize = intForUser;
        }
        this.mMagnificationFrameOffsetX = Float.isNaN(f4) ? this.mMagnificationFrameOffsetX : (int) ((this.mMagnificationFrame.width() / 2) * f4);
        int height = Float.isNaN(f5) ? this.mMagnificationFrameOffsetY : (int) ((this.mMagnificationFrame.height() / 2) * f5);
        this.mMagnificationFrameOffsetY = height;
        float f7 = f3 + height;
        float exactCenterX = Float.isNaN(f2) ? 0.0f : (f2 + this.mMagnificationFrameOffsetX) - this.mMagnificationFrame.exactCenterX();
        float exactCenterY = Float.isNaN(f3) ? 0.0f : f7 - this.mMagnificationFrame.exactCenterY();
        if (Float.isNaN(f)) {
            f6 = this.mScale;
        }
        this.mScale = f6;
        calculateMagnificationFrameBoundary();
        updateMagnificationFramePosition((int) exactCenterX, (int) exactCenterY);
        if (isActivated()) {
            modifyWindowMagnification(false);
        } else {
            Flags.createWindowlessWindowMagnifier();
            int width = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.width();
            int height2 = this.mMagnificationFrame.height() + (this.mMirrorSurfaceMargin * 2);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(width, height2, 2039, 40, -2);
            layoutParams.receiveInsetsIgnoringZOrder = true;
            layoutParams.setTitle(this.mContext.getString(R.string.magnification_window_title));
            layoutParams.accessibilityTitle = this.mResources.getString(R.string.accessibility_magnification_title);
            layoutParams.setTrustedOverlay();
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.window_magnifier_view, (ViewGroup) null);
            this.mMirrorView = inflate;
            this.mMirrorSurfaceView = (SurfaceView) inflate.findViewById(R.id.surface_view);
            this.mMirrorBorderView = this.mMirrorView.findViewById(R.id.magnification_inner_border);
            this.mMirrorSurfaceView.addOnLayoutChangeListener(this.mMirrorSurfaceViewLayoutChangeListener);
            this.mMirrorView.addOnLayoutChangeListener(this.mMirrorViewLayoutChangeListener);
            this.mMirrorView.setAccessibilityDelegate(new MirrorWindowA11yDelegate(this, r3));
            this.mMirrorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda9
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view2, WindowInsets windowInsets) {
                    WindowMagnificationController windowMagnificationController = WindowMagnificationController.this;
                    if (!windowMagnificationController.mHandler.hasCallbacks(windowMagnificationController.mWindowInsetChangeRunnable)) {
                        windowMagnificationController.mHandler.post(windowMagnificationController.mWindowInsetChangeRunnable);
                    }
                    return view2.onApplyWindowInsets(windowInsets);
                }
            });
            SurfaceControlViewHost surfaceControlViewHost = (SurfaceControlViewHost) this.mScvhSupplier.get();
            this.mSurfaceControlViewHost = surfaceControlViewHost;
            surfaceControlViewHost.setView(this.mMirrorView, layoutParams);
            SurfaceControl surfaceControl = this.mSurfaceControlViewHost.getSurfacePackage().getSurfaceControl();
            Rect rect = this.mMagnificationFrame;
            int i = rect.left;
            int i2 = this.mMirrorSurfaceMargin;
            int i3 = i - i2;
            int i4 = rect.top - i2;
            if (this.mTipPopupCnt <= 1) {
                this.mTransaction.setCrop(surfaceControl, new Rect()).setPosition(surfaceControl, i3, i4).setLayer(surfaceControl, Integer.MAX_VALUE).show(surfaceControl).apply();
            } else {
                this.mTransaction.setCrop(surfaceControl, new Rect(0, 0, width, height2)).setPosition(surfaceControl, i3, i4).setLayer(surfaceControl, Integer.MAX_VALUE).show(surfaceControl).apply();
            }
            this.mMirrorViewBounds.set(i3, i4, width + i3, height2 + i4);
            ((AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class)).attachAccessibilityOverlayToDisplay(this.mDisplayId, surfaceControl);
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
            ImageView imageView = (ImageView) this.mMirrorView.findViewById(R.id.focus_point);
            this.mCursorView = imageView;
            imageView.setScaleX(0.8f);
            this.mCursorView.setScaleY(0.8f);
            String string = this.mContext.getString(R.string.accessibility_magnification_left_handle);
            String string2 = this.mContext.getString(R.string.accessibility_magnification_top_handle);
            String string3 = this.mContext.getString(R.string.accessibility_magnification_right_handle);
            String string4 = this.mContext.getString(R.string.accessibility_magnification_bottom_handle);
            String string5 = this.mContext.getString(R.string.accessibility_magnification_top_left_handle);
            String string6 = this.mContext.getString(R.string.accessibility_magnification_top_right_handle);
            String string7 = this.mContext.getString(R.string.accessibility_magnification_bottom_left_handle);
            String string8 = this.mContext.getString(R.string.accessibility_magnification_bottom_right_handle);
            String format = String.format(this.mContext.getResources().getString(R.string.accessibility_magnification_window_and_hold_to), this.mContext.getString(R.string.accessibility_magnification_window_double_tap), this.mContext.getString(R.string.accessibility_magnification_window_move));
            if (isRTL(this.mContext)) {
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
            this.mDragView.setAccessibilityDelegate(new DragHandleA11yDelegate(this, r3));
            this.mCloseView.setContentDescription(this.mContext.getString(R.string.accessibility_magnification_done_resizing));
            this.mCloseView.setAccessibilityDelegate(new DragHandleA11yDelegate(this, r3));
            MirrorWindowControl mirrorWindowControl = this.mMirrorWindowControl;
            if (mirrorWindowControl != null) {
                if (mirrorWindowControl.mControlsView != null) {
                    Log.w("MirrorWindowControl", "control view is visible");
                } else {
                    Point point = mirrorWindowControl.mTmpPoint;
                    LayoutInflater.from(mirrorWindowControl.mContext);
                    mirrorWindowControl.mControlsView = mirrorWindowControl.onCreateView();
                    WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
                    int dimensionPixelSize = mirrorWindowControl.mContext.getResources().getDimensionPixelSize(R.dimen.magnification_controls_size);
                    int i5 = point.x;
                    if (i5 <= 0) {
                        i5 = dimensionPixelSize;
                    }
                    layoutParams2.width = i5;
                    int i6 = point.y;
                    if (i6 > 0) {
                        dimensionPixelSize = i6;
                    }
                    layoutParams2.height = dimensionPixelSize;
                    layoutParams2.gravity = 51;
                    layoutParams2.flags = 40;
                    layoutParams2.type = 2039;
                    layoutParams2.format = 1;
                    layoutParams2.setTitle(mirrorWindowControl.getWindowTitle());
                    Point point2 = mirrorWindowControl.mTmpPoint;
                    mirrorWindowControl.mContext.getDisplay().getSize(point2);
                    int i7 = point2.x - layoutParams2.width;
                    layoutParams2.x = i7;
                    int i8 = point2.y - layoutParams2.height;
                    layoutParams2.y = i8;
                    mirrorWindowControl.mControlPosition.set(i7, i8);
                    mirrorWindowControl.mWindowManager.addView(mirrorWindowControl.mControlsView, layoutParams2);
                    int i9 = layoutParams2.width;
                    int i10 = layoutParams2.height;
                    Point point3 = mirrorWindowControl.mTmpPoint;
                    mirrorWindowControl.mContext.getDisplay().getSize(point3);
                    mirrorWindowControl.mDraggableBound.set(0, 0, point3.x - i9, point3.y - i10);
                    if (MirrorWindowControl.DBG) {
                        Log.d("MirrorWindowControl", "updateDraggableBound :" + mirrorWindowControl.mDraggableBound);
                    }
                }
            }
            if (this.mTipPopupCnt <= 1 && (view = this.mMirrorView) != null) {
                view.post(new WindowMagnificationController$$ExternalSyntheticLambda0(this, 0));
            }
            applyResourcesValues();
        }
        if (Float.isNaN(f2) && Float.isNaN(f3) && this.mScale > 1.0f) {
            return;
        }
        Rect rect2 = this.mMagnificationFrame;
        this.mCursorX = (f2 - rect2.left) - (this.mScale <= 1.0f ? rect2.width() / 2 : 0);
        Rect rect3 = this.mMagnificationFrame;
        this.mCursorY = (f3 - rect3.top) - (this.mScale <= 1.0f ? rect3.height() / 2 : 0);
        ImageView imageView2 = this.mCursorView;
        if (imageView2 != null) {
            imageView2.setX(this.mCursorX);
            this.mCursorView.setY(this.mCursorY);
            this.mCursorX = this.mCursorView.getX();
            this.mCursorY = this.mCursorView.getY();
        }
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }
}
