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
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
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
import android.view.AttachedSurfaceControl;
import android.view.Display;
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
import android.view.accessibility.A11yRune;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IMagnificationConnectionCallback;
import android.widget.Button;
import android.widget.ImageView;
import androidx.core.math.MathUtils;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.internal.accessibility.common.MagnificationConstants;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.view.TooltipPopup;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationGestureDetector;
import com.android.systemui.accessibility.MagnificationImpl;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.widget.SemTipPopup;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class WindowMagnificationController implements View.OnTouchListener, SurfaceHolder.Callback, MagnificationGestureDetector.OnGestureListener, ComponentCallbacks {
    public static final Range A11Y_ACTION_SCALE_RANGE;
    public static final float[] COLOR_BLACK_ARRAY;
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
    public final DeviceStateManager mDeviceStateManager;
    public final int mDisplayId;
    public final AnonymousClass2 mDisplayStateCallback;
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
    public final WindowMagnificationController$$ExternalSyntheticLambda3 mMirrorSurfaceViewLayoutChangeListener;
    public View mMirrorView;
    public final Rect mMirrorViewBounds;
    public final WindowMagnificationController$$ExternalSyntheticLambda3 mMirrorViewLayoutChangeListener;
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
    public int mSettingsButtonIndex;
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
    public class AnonymousClass1 implements Runnable {
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
                MagnificationConnectionImpl magnificationConnectionImpl = MagnificationImpl.this.mMagnificationConnectionImpl;
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

    public class DragHandleA11yDelegate extends View.AccessibilityDelegate {
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

    public class MirrorWindowA11yDelegate extends View.AccessibilityDelegate {
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
            int iWidth = windowMagnificationController.mMagnificationFrame.width();
            WindowMagnificationController windowMagnificationController2 = WindowMagnificationController.this;
            if ((windowMagnificationController2.mMirrorSurfaceMargin * 2) + iWidth < windowMagnificationController2.mWindowBounds.width()) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_increase_window_width, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_increase_window_width)));
            }
            int iHeight = WindowMagnificationController.this.mMagnificationFrame.height();
            WindowMagnificationController windowMagnificationController3 = WindowMagnificationController.this;
            if ((windowMagnificationController3.mMirrorSurfaceMargin * 2) + iHeight < windowMagnificationController3.mWindowBounds.height()) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_increase_window_height, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_increase_window_height)));
            }
            int iWidth2 = WindowMagnificationController.this.mMagnificationFrame.width();
            WindowMagnificationController windowMagnificationController4 = WindowMagnificationController.this;
            if ((windowMagnificationController4.mMirrorSurfaceMargin * 2) + iWidth2 > windowMagnificationController4.mMinWindowSize) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_decrease_window_width, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_decrease_window_width)));
            }
            int iHeight2 = WindowMagnificationController.this.mMagnificationFrame.height();
            WindowMagnificationController windowMagnificationController5 = WindowMagnificationController.this;
            if ((windowMagnificationController5.mMirrorSurfaceMargin * 2) + iHeight2 > windowMagnificationController5.mMinWindowSize) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_decrease_window_height, WindowMagnificationController.this.mContext.getString(R.string.accessibility_control_decrease_window_height)));
            }
        }

        @Override // android.view.View.AccessibilityDelegate
        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) throws Resources.NotFoundException {
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
                WindowMagnificationController.m1001$$Nest$msetMagnificationFrameSize(windowMagnificationController6, (int) ((fraction + 1.0f) * WindowMagnificationController.this.mMagnificationFrame.width()), windowMagnificationController6.mMagnificationFrame.height());
            } else if (i == R.id.accessibility_action_increase_window_height) {
                WindowMagnificationController windowMagnificationController7 = WindowMagnificationController.this;
                WindowMagnificationController.m1001$$Nest$msetMagnificationFrameSize(windowMagnificationController7, windowMagnificationController7.mMagnificationFrame.width(), (int) ((fraction + 1.0f) * WindowMagnificationController.this.mMagnificationFrame.height()));
            } else if (i == R.id.accessibility_action_decrease_window_width) {
                WindowMagnificationController windowMagnificationController8 = WindowMagnificationController.this;
                WindowMagnificationController.m1001$$Nest$msetMagnificationFrameSize(windowMagnificationController8, (int) ((1.0f - fraction) * WindowMagnificationController.this.mMagnificationFrame.width()), windowMagnificationController8.mMagnificationFrame.height());
            } else {
                if (i != R.id.accessibility_action_decrease_window_height) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                WindowMagnificationController windowMagnificationController9 = WindowMagnificationController.this;
                WindowMagnificationController.m1001$$Nest$msetMagnificationFrameSize(windowMagnificationController9, windowMagnificationController9.mMagnificationFrame.width(), (int) ((1.0f - fraction) * WindowMagnificationController.this.mMagnificationFrame.height()));
            }
            WindowMagnificationController windowMagnificationController10 = WindowMagnificationController.this;
            WindowMagnifierCallback windowMagnifierCallback = windowMagnificationController10.mWindowMagnifierCallback;
            int i2 = windowMagnificationController10.mDisplayId;
            MagnificationConnectionImpl magnificationConnectionImpl = MagnificationImpl.this.mMagnificationConnectionImpl;
            if (magnificationConnectionImpl != null && (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) != null) {
                try {
                    iMagnificationConnectionCallback.onAccessibilityActionPerformed(i2);
                    return true;
                } catch (RemoteException e) {
                    Log.e("WindowMagnificationConnectionImpl", "Failed to inform an accessibility action is already performed", e);
                }
            }
            return true;
        }

        public final void performScale(float f) {
            IMagnificationConnectionCallback iMagnificationConnectionCallback;
            float fFloatValue = ((Float) WindowMagnificationController.A11Y_ACTION_SCALE_RANGE.clamp(Float.valueOf(f))).floatValue();
            WindowMagnificationController windowMagnificationController = WindowMagnificationController.this;
            WindowMagnifierCallback windowMagnifierCallback = windowMagnificationController.mWindowMagnifierCallback;
            int i = windowMagnificationController.mDisplayId;
            MagnificationConnectionImpl magnificationConnectionImpl = MagnificationImpl.this.mMagnificationConnectionImpl;
            if (magnificationConnectionImpl == null || (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) == null) {
                return;
            }
            try {
                iMagnificationConnectionCallback.onPerformScaleAction(i, fFloatValue, true);
            } catch (RemoteException e) {
                Log.e("WindowMagnificationConnectionImpl", "Failed to inform performing scale action", e);
            }
        }

        private MirrorWindowA11yDelegate() {
        }
    }

    /* renamed from: -$$Nest$msetMagnificationFrameSize, reason: not valid java name */
    public static void m1001$$Nest$msetMagnificationFrameSize(WindowMagnificationController windowMagnificationController, int i, int i2) {
        int i3 = windowMagnificationController.mMirrorSurfaceMargin * 2;
        windowMagnificationController.setWindowSizeAndCenter(i + i3, Float.NaN, Float.NaN, i3 + i2);
    }

    static {
        DEBUG = Log.isLoggable("WindowMagnificationController", 3) || Build.IS_DEBUGGABLE;
        A11Y_ACTION_SCALE_RANGE = new Range(Float.valueOf(1.0f), Float.valueOf(MagnificationConstants.SCALE_MAX_VALUE));
        COLOR_BLACK_ARRAY = new float[]{0.0f, 0.0f, 0.0f};
        HORIZONTAL_LOCK_BASE = Math.tan(Math.toRadians(50.0d));
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r14v3, types: [com.android.systemui.accessibility.WindowMagnificationController$2] */
    public WindowMagnificationController(Context context, Handler handler, WindowMagnificationAnimationController windowMagnificationAnimationController, MirrorWindowControl mirrorWindowControl, SurfaceControl.Transaction transaction, WindowMagnifierCallback windowMagnifierCallback, SysUiState sysUiState, SecureSettings secureSettings, Supplier<SurfaceControlViewHost> supplier, WindowManager windowManager) throws Resources.NotFoundException {
        Size size;
        SparseArray sparseArray = new SparseArray();
        this.mMagnificationSizeScaleOptions = sparseArray;
        int i = 2;
        this.mSettingsButtonIndex = 2;
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
        this.mHideTootipRunnable = new WindowMagnificationController$$ExternalSyntheticLambda0(this, 0);
        this.mContext = context;
        this.mHandler = handler;
        this.mAnimationController = windowMagnificationAnimationController;
        windowMagnificationAnimationController.mOnAnimationEndRunnable = new WindowMagnificationController$$ExternalSyntheticLambda0(this, 1);
        windowMagnificationAnimationController.mController = this;
        this.mWindowMagnifierCallback = windowMagnifierCallback;
        this.mSysUiState = sysUiState;
        this.mScvhSupplier = supplier;
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
        this.mWindowMagnificationFrameSizePrefs = new WindowMagnificationFrameSizePrefs(context);
        context.setTheme(android.R.style.Theme.DeviceDefault.DayNight);
        Display display = context.getDisplay();
        int displayId = context.getDisplayId();
        this.mDisplayId = displayId;
        this.mRotation = display.getRotation();
        this.mWm = windowManager;
        Rect rect = new Rect(windowManager.getCurrentWindowMetrics().getBounds());
        this.mWindowBounds = rect;
        int i2 = context.getResources().getConfiguration().semDisplayDeviceType;
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
        if (windowMagnificationFrameSizePrefs.mWindowMagnificationSizePreferences.contains(windowMagnificationFrameSizePrefs.getKey())) {
            int indexForCurrentDensity = this.mWindowMagnificationFrameSizePrefs.getIndexForCurrentDensity();
            this.mSettingsButtonIndex = indexForCurrentDensity;
            if (isActivated()) {
                MagnificationImpl.AnonymousClass3 anonymousClass3 = (MagnificationImpl.AnonymousClass3) windowMagnifierCallback;
                MagnificationImpl.this.mHandler.post(new MagnificationImpl$3$$ExternalSyntheticLambda0(anonymousClass3, displayId, indexForCurrentDensity));
            }
            if (indexForCurrentDensity == 0) {
                WindowMagnificationFrameSizePrefs windowMagnificationFrameSizePrefs2 = this.mWindowMagnificationFrameSizePrefs;
                size = WindowMagnificationFrameSpec.deserialize(windowMagnificationFrameSizePrefs2.mWindowMagnificationSizePreferences.getString(windowMagnificationFrameSizePrefs2.getKey(), null)).size;
            } else {
                int magnificationWindowSizeFromIndex = getMagnificationWindowSizeFromIndex(indexForCurrentDensity) - (this.mMirrorSurfaceMargin * 2);
                size = new Size(magnificationWindowSizeFromIndex, magnificationWindowSizeFromIndex);
            }
        } else {
            this.mSettingsButtonIndex = 2;
            if (isActivated()) {
                MagnificationImpl.AnonymousClass3 anonymousClass32 = (MagnificationImpl.AnonymousClass3) windowMagnifierCallback;
                MagnificationImpl.this.mHandler.post(new MagnificationImpl$3$$ExternalSyntheticLambda0(anonymousClass32, displayId, i));
            }
            int magnificationWindowSizeFromIndex2 = getMagnificationWindowSizeFromIndex(2) - (this.mMirrorSurfaceMargin * 2);
            size = new Size(magnificationWindowSizeFromIndex2, magnificationWindowSizeFromIndex2);
        }
        setMagnificationFrame(size.getWidth(), size.getHeight(), rect.width() / 2, rect.height() / 2);
        computeBounceAnimationScale();
        this.mMirrorWindowControl = mirrorWindowControl;
        this.mTransaction = transaction;
        this.mGestureDetector = new MagnificationGestureDetector(context, handler, this);
        WindowMagnificationController$$ExternalSyntheticLambda0 windowMagnificationController$$ExternalSyntheticLambda0 = new WindowMagnificationController$$ExternalSyntheticLambda0(this, 2);
        this.mWindowInsetChangeRunnable = windowMagnificationController$$ExternalSyntheticLambda0;
        windowMagnificationController$$ExternalSyntheticLambda0.run();
        this.mMirrorViewRunnable = new AnonymousClass1();
        final int i3 = 0;
        this.mMirrorSurfaceViewLayoutChangeListener = new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda3
            public final /* synthetic */ WindowMagnificationController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                int i12 = i3;
                WindowMagnificationController windowMagnificationController = this.f$0;
                switch (i12) {
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
        final int i4 = 1;
        this.mMirrorViewLayoutChangeListener = new View.OnLayoutChangeListener(this) { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda3
            public final /* synthetic */ WindowMagnificationController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i42, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                int i12 = i4;
                WindowMagnificationController windowMagnificationController = this.f$0;
                switch (i12) {
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
        this.mUpdateStateDescriptionRunnable = new WindowMagnificationController$$ExternalSyntheticLambda0(this, 3);
        this.mTooltipPopup = new TooltipPopup(context);
        this.mDeviceStateManager = (DeviceStateManager) context.getSystemService(DeviceStateManager.class);
        this.mDisplayStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.accessibility.WindowMagnificationController.2
            public final void onDeviceStateChanged(DeviceState deviceState) {
                View view;
                if (A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP && deviceState.getIdentifier() == 3) {
                    WindowMagnificationController windowMagnificationController = WindowMagnificationController.this;
                    boolean z = WindowMagnificationController.DEBUG;
                    if (windowMagnificationController.mTipPopupCnt > 1 || (view = windowMagnificationController.mMirrorView) == null) {
                        return;
                    }
                    view.post(new WindowMagnificationController$$ExternalSyntheticLambda0(windowMagnificationController, 4));
                }
            }
        };
    }

    public static boolean isRTL(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        return configuration != null && (configuration.screenLayout & 192) == 128;
    }

    public final void applyResourcesValues() {
        float fM;
        this.mMirrorBorderView.setBackground(this.mResources.getDrawable(R.drawable.accessibility_window_magnification_background));
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            fM = ActionRow$$ExternalSyntheticOutline0.m(this.mContext, 1, this.mEditSizeEnable ? 14.0f : 25.0f);
        } else {
            fM = ActionRow$$ExternalSyntheticOutline0.m(this.mContext, 1, this.mEditSizeEnable ? 16.0f : 28.0f);
        }
        this.mMirrorSurfaceView.setCornerRadius(fM);
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

    public final void applyTouchableRegion() {
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
        int iWidth = this.mMagnificationFrame.width() / 2;
        int iHeight = this.mMagnificationFrame.height() / 2;
        float f = this.mScale;
        int i = iWidth - ((int) (iWidth / f));
        int i2 = iHeight - ((int) (iHeight / f));
        this.mMagnificationFrameBoundary.set(-Math.max(i - this.mMagnificationFrameOffsetX, 0), -Math.max(i2 - this.mMagnificationFrameOffsetY, 0), this.mWindowBounds.width() + Math.max(i + this.mMagnificationFrameOffsetX, 0), this.mWindowBounds.height() + Math.max(i2 + this.mMagnificationFrameOffsetY, 0));
    }

    public final void changeMagnificationFrameSize(float f, float f2, float f3, float f4) {
        isRTL(this.mContext);
        int iMin = Math.min(this.mWindowBounds.width(), this.mWindowBounds.height()) / 3;
        int iHeight = this.mWindowBounds.height() - (this.mMirrorSurfaceMargin * 2);
        int iWidth = this.mWindowBounds.width() - (this.mMirrorSurfaceMargin * 2);
        Rect rect = new Rect();
        rect.set(this.mMagnificationFrame);
        rect.right += (int) f3;
        rect.left += (int) f;
        rect.top += (int) f2;
        rect.bottom += (int) f4;
        if (rect.width() < iMin || rect.height() < iMin || rect.width() > iWidth || rect.height() > iHeight) {
            return;
        }
        this.mMagnificationFrame.set(rect);
        computeBounceAnimationScale();
        calculateMagnificationFrameBoundary();
        modifyWindowMagnification(true);
    }

    public final void computeBounceAnimationScale() {
        float fWidth = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.width();
        this.mBounceEffectAnimationScale = Math.min(fWidth / (fWidth - (this.mOuterBorderSize * 2)), 1.05f);
    }

    public final void deleteWindowMagnification$1() throws Resources.NotFoundException {
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
            MagnificationConnectionImpl magnificationConnectionImpl = MagnificationImpl.this.mMagnificationConnectionImpl;
            if (magnificationConnectionImpl != null && (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) != null) {
                try {
                    iMagnificationConnectionCallback.onSourceBoundsChanged(i, rect);
                } catch (RemoteException e) {
                    Log.e("WindowMagnificationConnectionImpl", "Failed to inform source bounds changed", e);
                }
            }
            this.mDeviceStateManager.unregisterCallback(this.mDisplayStateCallback);
        }
    }

    public final int getMagnificationWindowSizeFromIndex(int i) {
        int iMin = (int) ((Math.min(this.mWindowBounds.width(), this.mWindowBounds.height()) / 3) * ((Float) this.mMagnificationSizeScaleOptions.get(i, Float.valueOf(1.0f))).floatValue());
        return iMin - (iMin % 2);
    }

    public final void handleSingleTap(View view) throws Resources.NotFoundException {
        int id = view.getId();
        if (id == R.id.drag_handle) {
            MagnificationImpl.AnonymousClass3 anonymousClass3 = (MagnificationImpl.AnonymousClass3) this.mWindowMagnifierCallback;
            MagnificationImpl.this.mHandler.post(new MagnificationImpl$3$$ExternalSyntheticLambda0(anonymousClass3, this.mDisplayId));
        } else {
            if (id == R.id.close_button) {
                setEditMagnifierSizeMode(false);
                return;
            }
            View view2 = this.mMirrorView;
            if (view2 == null) {
                return;
            }
            ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view2, PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, 1.0f, this.mBounceEffectAnimationScale, 1.0f), PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, 1.0f, this.mBounceEffectAnimationScale, 1.0f));
            objectAnimatorOfPropertyValuesHolder.setDuration(this.mBounceEffectDuration);
            objectAnimatorOfPropertyValuesHolder.start();
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
        if (isActivated() && this.mMirrorSurface != null) {
            Rect rect = this.mMagnificationFrame;
            float f = this.mScale;
            Rect rect2 = this.mTmpRect;
            rect2.set(this.mSourceBounds);
            int iWidth = rect.width() / 2;
            int iHeight = rect.height() / 2;
            int i = iWidth - ((int) (iWidth / f));
            int i2 = iHeight - ((int) (iHeight / f));
            this.mSourceBounds.set(rect.left + i, rect.top + i2, rect.right - i, rect.bottom - i2);
            this.mSourceBounds.offset(-this.mMagnificationFrameOffsetX, -this.mMagnificationFrameOffsetY);
            Rect rect3 = this.mSourceBounds;
            if (rect3.left < 0) {
                rect3.offsetTo(0, rect3.top);
            } else if (rect3.right > this.mWindowBounds.width()) {
                this.mSourceBounds.offsetTo(this.mWindowBounds.width() - this.mSourceBounds.width(), this.mSourceBounds.top);
            }
            Rect rect4 = this.mSourceBounds;
            if (rect4.top < 0) {
                rect4.offsetTo(rect4.left, 0);
            } else if (rect4.bottom > this.mWindowBounds.height()) {
                Rect rect5 = this.mSourceBounds;
                rect5.offsetTo(rect5.left, this.mWindowBounds.height() - this.mSourceBounds.height());
            }
            if (!this.mSourceBounds.equals(rect2)) {
                this.mTmpRect.set(0, 0, this.mMagnificationFrame.width(), this.mMagnificationFrame.height());
                this.mTransaction.setGeometry(this.mMirrorSurface, this.mSourceBounds, this.mTmpRect, 0);
                if (!this.mAnimationController.mValueAnimator.isRunning()) {
                    WindowMagnifierCallback windowMagnifierCallback = this.mWindowMagnifierCallback;
                    int i3 = this.mDisplayId;
                    Rect rect6 = this.mSourceBounds;
                    MagnificationConnectionImpl magnificationConnectionImpl = MagnificationImpl.this.mMagnificationConnectionImpl;
                    if (magnificationConnectionImpl != null && (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) != null) {
                        try {
                            iMagnificationConnectionCallback.onSourceBoundsChanged(i3, rect6);
                        } catch (RemoteException e) {
                            Log.e("WindowMagnificationConnectionImpl", "Failed to inform source bounds changed", e);
                        }
                    }
                }
            }
        }
        if (isActivated()) {
            int iWidth2 = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.width();
            int iHeight2 = this.mMagnificationFrame.height();
            int i4 = this.mMirrorSurfaceMargin;
            int i5 = (i4 * 2) + iHeight2;
            int i6 = this.mOuterBorderSize;
            int iClamp = MathUtils.clamp(this.mMagnificationFrame.left - i4, -i6, (this.mWindowBounds.right - iWidth2) + i6);
            int i7 = this.mOuterBorderSize;
            int iClamp2 = MathUtils.clamp(this.mMagnificationFrame.top - this.mMirrorSurfaceMargin, -i7, (this.mWindowBounds.bottom - i5) + i7);
            if (z) {
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mMirrorView.getLayoutParams();
                layoutParams.width = iWidth2;
                layoutParams.height = i5;
                this.mSurfaceControlViewHost.relayout(layoutParams);
                this.mTransaction.setCrop(this.mSurfaceControlViewHost.getSurfacePackage().getSurfaceControl(), new Rect(0, 0, iWidth2, i5));
            }
            this.mMirrorViewBounds.set(iClamp, iClamp2, iWidth2 + iClamp, i5 + iClamp2);
            this.mTransaction.setPosition(this.mSurfaceControlViewHost.getSurfacePackage().getSurfaceControl(), iClamp, iClamp2);
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
        MagnificationConnectionImpl magnificationConnectionImpl = MagnificationImpl.this.mMagnificationConnectionImpl;
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
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        int iHeight;
        float f;
        int iWidth;
        float fWidth;
        int iDiff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        if (DEBUG) {
            Log.d("WindowMagnificationController", "onConfigurationChanged = " + Configuration.configurationDiffToString(iDiff));
        }
        if (iDiff == 0) {
            return;
        }
        boolean z = true;
        if ((iDiff & 128) != 0 && isActivated()) {
            Rect rect = new Rect(this.mWindowBounds);
            Rect bounds = this.mWm.getCurrentWindowMetrics().getBounds();
            this.mWindowBounds.set(bounds);
            float fWidth2 = (this.mWindowBounds.width() * this.mMagnificationFrame.centerX()) / rect.width();
            float fHeight = (this.mWindowBounds.height() * this.mMagnificationFrame.centerY()) / rect.height();
            if (this.mMagnificationFrame.width() > bounds.width()) {
                iWidth = bounds.width();
                iHeight = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.height();
                fWidth = bounds.width() / 2;
            } else {
                if (this.mMagnificationFrame.height() > bounds.height()) {
                    iHeight = bounds.height();
                    f = fWidth2;
                    iWidth = this.mMagnificationFrame.width() + (this.mMirrorSurfaceMargin * 2);
                    fHeight = bounds.height() / 2;
                } else {
                    int iWidth2 = this.mMagnificationFrame.width() + (this.mMirrorSurfaceMargin * 2);
                    iHeight = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.height();
                    f = fWidth2;
                    iWidth = iWidth2;
                }
                fWidth = f;
            }
            calculateMagnificationFrameBoundary();
            setWindowSizeAndCenter(iWidth, (int) fWidth, (int) fHeight, iHeight);
            SemTipPopup semTipPopup = this.mTipPopup;
            if (semTipPopup != null && semTipPopup.isShowing()) {
                this.mTipPopup.dismiss(true);
            }
        }
        if ((iDiff & 4) != 0 && isActivated()) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.mMirrorView.getLayoutParams();
            layoutParams.accessibilityTitle = this.mResources.getString(R.string.accessibility_magnification_title);
            this.mSurfaceControlViewHost.relayout(layoutParams);
        }
        if ((iDiff & 4096) != 0) {
            updateDimensions();
            computeBounceAnimationScale();
        } else {
            z = false;
        }
        if (isActivated() && z) {
            deleteWindowMagnification$1();
            updateWindowMagnificationInternal(Float.NaN);
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
    public final void onSingleTap(View view) throws Resources.NotFoundException {
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

    public final void setEditMagnifierSizeMode(boolean z) throws Resources.NotFoundException {
        this.mEditSizeEnable = z;
        applyResourcesValues();
        if (this.mEditSizeEnable) {
            this.mLeftDrag.performAccessibilityAction(64, null);
        }
        if (isActivated()) {
            updateDimensions();
            applyTouchableRegion();
        }
        if (z) {
            this.mSettingsButtonIndex = 0;
        } else {
            this.mWindowMagnificationFrameSizePrefs.saveIndexAndSizeForCurrentDensity(this.mSettingsButtonIndex, new Size(this.mMagnificationFrame.width(), this.mMagnificationFrame.height()));
        }
    }

    public final void setMagnificationFrame(int i, int i2, int i3, int i4) {
        this.mWindowMagnificationFrameSizePrefs.saveIndexAndSizeForCurrentDensity(this.mSettingsButtonIndex, new Size(i, i2));
        int i5 = i3 - (i / 2);
        int i6 = i4 - (i2 / 2);
        this.mMagnificationFrame.set(i5, i6, i + i5, i2 + i6);
    }

    public final void setWindowSizeAndCenter(int i, float f, float f2, int i2) {
        int iClamp = MathUtils.clamp(i, this.mMinWindowSize, this.mWindowBounds.width());
        int iClamp2 = MathUtils.clamp(i2, this.mMinWindowSize, this.mWindowBounds.height());
        if (Float.isNaN(f)) {
            f = this.mMagnificationFrame.centerX();
        }
        if (Float.isNaN(f2)) {
            f2 = this.mMagnificationFrame.centerY();
        }
        int i3 = this.mMirrorSurfaceMargin;
        setMagnificationFrame(iClamp - (i3 * 2), iClamp2 - (i3 * 2), (int) f, (int) f2);
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
            this.mTransaction.setColor(this.mMirrorSurfaceView.getSurfaceControl(), COLOR_BLACK_ARRAY);
            this.mTransaction.show(this.mMirrorSurface).reparent(this.mMirrorSurface, this.mMirrorSurfaceView.getSurfaceControl());
            this.mTransaction.setTrustedOverlay(this.mMirrorSurface, true);
            modifyWindowMagnification(false);
        }
    }

    public final void updateDimensions() throws Resources.NotFoundException {
        this.mMirrorSurfaceMargin = this.mResources.getDimensionPixelSize(R.dimen.magnification_mirror_surface_margin);
        this.mBorderDragSize = this.mResources.getDimensionPixelSize(R.dimen.magnification_border_drag_size);
        this.mOuterBorderSize = this.mResources.getDimensionPixelSize(R.dimen.magnification_outer_border_margin);
        this.mResources.getDimensionPixelSize(R.dimen.magnification_button_reposition_threshold_from_edge);
        this.mMinWindowSize = this.mResources.getDimensionPixelSize(android.R.dimen.action_bar_default_padding_start_material);
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
                int iWidth = i6 - this.mMagnificationFrame.width();
                Rect rect3 = this.mTmpRect;
                rect3.offsetTo(iWidth, rect3.top);
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
                int iHeight = i10 - this.mMagnificationFrame.height();
                Rect rect6 = this.mTmpRect;
                rect6.offsetTo(rect6.left, iHeight);
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
            ((SysUiStateImpl) this.mSysUiState.setFlag(524288L, z2)).commitUpdate();
        }
    }

    public final void updateWindowMagnificationInternal(float f) throws Resources.NotFoundException {
        updateWindowMagnificationInternal(f, Float.NaN, Float.NaN, Float.NaN, Float.NaN);
    }

    public final void updateWindowMagnificationInternal(float f, float f2, float f3, float f4, float f5) throws Resources.NotFoundException {
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
            this.mSettingsButtonIndex = intForUser;
            int magnificationWindowSizeFromIndex = getMagnificationWindowSizeFromIndex(intForUser);
            setWindowSizeAndCenter(magnificationWindowSizeFromIndex, Float.NaN, Float.NaN, magnificationWindowSizeFromIndex);
            this.mPreviousMagnificationSize = intForUser;
        }
        this.mMagnificationFrameOffsetX = Float.isNaN(f4) ? this.mMagnificationFrameOffsetX : (int) ((this.mMagnificationFrame.width() / 2) * f4);
        int iHeight = Float.isNaN(f5) ? this.mMagnificationFrameOffsetY : (int) ((this.mMagnificationFrame.height() / 2) * f5);
        this.mMagnificationFrameOffsetY = iHeight;
        float f7 = f3 + iHeight;
        float fExactCenterX = Float.isNaN(f2) ? 0.0f : (f2 + this.mMagnificationFrameOffsetX) - this.mMagnificationFrame.exactCenterX();
        float fExactCenterY = Float.isNaN(f3) ? 0.0f : f7 - this.mMagnificationFrame.exactCenterY();
        if (Float.isNaN(f6)) {
            f6 = this.mScale;
        }
        this.mScale = f6;
        calculateMagnificationFrameBoundary();
        updateMagnificationFramePosition((int) fExactCenterX, (int) fExactCenterY);
        if (isActivated()) {
            modifyWindowMagnification(false);
        } else {
            int iWidth = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.width();
            int iHeight2 = (this.mMirrorSurfaceMargin * 2) + this.mMagnificationFrame.height();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(iWidth, iHeight2, 2039, 40, -2);
            layoutParams.receiveInsetsIgnoringZOrder = true;
            layoutParams.setTitle(this.mContext.getString(R.string.magnification_window_title));
            layoutParams.accessibilityTitle = this.mResources.getString(R.string.accessibility_magnification_title);
            layoutParams.setTrustedOverlay();
            View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.window_magnifier_view, (ViewGroup) null);
            this.mMirrorView = viewInflate;
            this.mMirrorSurfaceView = (SurfaceView) viewInflate.findViewById(R.id.surface_view);
            this.mMirrorBorderView = this.mMirrorView.findViewById(R.id.magnification_inner_border);
            this.mMirrorSurfaceView.addOnLayoutChangeListener(this.mMirrorSurfaceViewLayoutChangeListener);
            this.mMirrorView.addOnLayoutChangeListener(this.mMirrorViewLayoutChangeListener);
            this.mMirrorView.setAccessibilityDelegate(new MirrorWindowA11yDelegate(this, i));
            this.mMirrorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.accessibility.WindowMagnificationController$$ExternalSyntheticLambda7
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view2, WindowInsets windowInsets) {
                    WindowMagnificationController windowMagnificationController = this.f$0;
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
                this.mTransaction.setCrop(surfaceControl, new Rect(0, 0, iWidth, iHeight2)).setPosition(surfaceControl, i3, i4).setLayer(surfaceControl, Integer.MAX_VALUE).show(surfaceControl).apply();
            }
            this.mMirrorViewBounds.set(i3, i4, iWidth + i3, iHeight2 + i4);
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
            String str = String.format(this.mContext.getResources().getString(R.string.accessibility_magnification_window_and_hold_to), this.mContext.getString(R.string.accessibility_magnification_window_double_tap), this.mContext.getString(R.string.accessibility_magnification_window_move));
            if (isRTL(this.mContext)) {
                this.mLeftDrag.setContentDescription(string3 + " " + str);
                this.mRightDrag.setContentDescription(string + " " + str);
                this.mTopLeftCornerView.setContentDescription(string6 + " " + str);
                this.mTopRightCornerView.setContentDescription(string5 + " " + str);
                this.mBottomLeftCornerView.setContentDescription(string8 + " " + str);
                this.mBottomRightCornerView.setContentDescription(string7 + " " + str);
            } else {
                this.mLeftDrag.setContentDescription(string + " " + str);
                this.mRightDrag.setContentDescription(string3 + " " + str);
                this.mTopLeftCornerView.setContentDescription(string5 + " " + str);
                this.mTopRightCornerView.setContentDescription(string6 + " " + str);
                this.mBottomLeftCornerView.setContentDescription(string7 + " " + str);
                this.mBottomRightCornerView.setContentDescription(string8 + " " + str);
            }
            this.mTopDrag.setContentDescription(string2 + " " + str);
            this.mBottomDrag.setContentDescription(string4 + " " + str);
            this.mDragView.setContentDescription(this.mContext.getString(R.string.accessibility_magnification_handle) + " " + str);
            this.mDragView.setAccessibilityDelegate(new DragHandleA11yDelegate(this, i));
            this.mCloseView.setContentDescription(this.mContext.getString(R.string.accessibility_magnification_done_resizing));
            this.mCloseView.setAccessibilityDelegate(new DragHandleA11yDelegate(this, i));
            this.mDeviceStateManager.registerCallback(this.mContext.getMainExecutor(), this.mDisplayStateCallback);
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
            if (!A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP && this.mTipPopupCnt <= 1 && (view = this.mMirrorView) != null) {
                view.post(new WindowMagnificationController$$ExternalSyntheticLambda0(this, 4));
            }
            applyResourcesValues();
        }
        if (Float.isNaN(f2) && Float.isNaN(f3) && this.mScale >= 1.0f) {
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
