package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.hardware.input.InputManager;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.InsetsSource;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SemBlurInfo;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.view.inputmethod.InputMethodManager;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.common.DismissButtonManager;
import com.android.wm.shell.common.DismissButtonManager$$ExternalSyntheticLambda0;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.FreeformDragPositioningController;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip.PipUtils;
import com.android.wm.shell.shortcut.DexCompatRestartDialogUtils;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.wm.shell.windowdecor.WindowMenuPopupPresenter;
import com.android.wm.shell.windowdecor.animation.CaptionAnimationUtils;
import com.android.wm.shell.windowdecor.widget.HandleView;
import com.android.wm.shell.windowdecor.widget.OutlineView;
import com.android.systemui.R;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import com.samsung.android.util.SemViewUtils;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultitaskingWindowDecoration extends WindowDecoration implements DisplayInsetsController.OnInsetsChangedListener {
    public final AdjustState mAdjustState;
    public float mAlpha;
    public CharSequence mAppName;
    public int mCaptionColor;
    public WindowMenuCaptionPresenter mCaptionMenuPresenter;
    public int mCaptionType;
    public final Choreographer mChoreographer;
    public boolean mDestroyed;
    public final DexCompatRestartDialogUtils mDexCompatRestartDialogUtils;
    public final int mDisplayIdForInsets;
    public final DisplayInsetsController mDisplayInsetsController;
    public DragDetector mDragDetector;
    public DragPositioningCallback mDragPositioningCallback;
    public DragResizeInputListener mDragResizeListener;
    public ObjectAnimator mDragShadowAnimator;
    public boolean mElevationAnimationShow;
    public EventReceiver mEventReceiver;
    public FreeformOutlineWrapper mFreeformOutlineWrapper;
    public FreeformDimInputListener mFreeformStashDimInputListener;
    public final FreeformStashState mFreeformStashState;
    public HandleAutoHide mHandleAutoHide;
    public WindowDecoration.AdditionalWindow mHandleMenu;
    public final Handler mHandler;
    public ImmersiveCaptionBehavior mImmersiveCaptionBehavior;
    public InputMonitor mInputMonitor;
    public final InsetsState mInsetsState;
    public boolean mIsAdditionalDisplayAdded;
    public final boolean mIsBlurSupported;
    public boolean mIsDexDockingEnabled;
    public boolean mIsFullScreenCaptionState;
    public boolean mIsHandleMenuShowing;
    public boolean mIsHandleVisibleState;
    public boolean mIsImmersiveMode;
    public boolean mIsKeepScreenOn;
    public boolean mIsKeyguardShowing;
    public boolean mIsNightMode;
    public boolean mIsPopupWindowPinned;
    public boolean mIsSliderPopupShowing;
    public boolean mIsSplitImmersiveEnabled;
    public int mLastDockingState;
    public final Rect mLastStableBounds;
    public final MultiTaskingHelpController mMultiTaskingHelpController;
    public MultitaskingWindowDecorViewModel.CaptionTouchEventListener mOnCaptionButtonClickListener;
    public MultitaskingWindowDecorViewModel.CaptionTouchEventListener mOnCaptionTouchListener;
    public Animator mOverflowMenuAnim;
    public final Pip mPipController;
    public WindowMenuPopupPresenter mPopupMenuPresenter;
    public final WindowDecoration.RelayoutParams mRelayoutParams;
    public final WindowDecoration.RelayoutResult mResult;
    public ObjectAnimator mShadowAnimator;
    public WindowDecoration.AdditionalWindow mSliderPopup;
    public final SplitScreenController mSplitScreenController;
    public final SyncTransactionQueue mSyncQueue;
    public TaskPositioner mTaskPositioner;
    public final Rect mTmpRect;
    public float mWindowElevation;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AdjustState {
        public boolean mAnimating;
        public boolean mIsAdjusted;
        public final Rect mOriginBounds = new Rect();
        public final Rect mAdjustingBounds = new Rect();

        public AdjustState() {
        }

        public final void adjustConfig(WindowContainerToken windowContainerToken, int i) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            MultitaskingWindowDecoration multitaskingWindowDecoration = MultitaskingWindowDecoration.this;
            multitaskingWindowDecoration.mTmpRect.set(this.mOriginBounds);
            multitaskingWindowDecoration.mTmpRect.offset(0, i);
            windowContainerTransaction.setBounds(windowContainerToken, multitaskingWindowDecoration.mTmpRect);
            Rect rect = i != 0 ? multitaskingWindowDecoration.mTmpRect : null;
            Rect rect2 = this.mAdjustingBounds;
            if (rect == null) {
                rect2.setEmpty();
            } else {
                rect2.set(rect);
            }
            multitaskingWindowDecoration.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        }

        public final void moveSurface(int i) {
            MultitaskingWindowDecoration multitaskingWindowDecoration = MultitaskingWindowDecoration.this;
            multitaskingWindowDecoration.mTmpRect.set(this.mOriginBounds);
            multitaskingWindowDecoration.mTmpRect.offset(0, i);
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
            SurfaceControl surfaceControl = multitaskingWindowDecoration.mTaskSurface;
            Rect rect = multitaskingWindowDecoration.mTmpRect;
            transaction.setPosition(surfaceControl, rect.left, rect.top);
            transaction.apply();
        }

        public final void reset() {
            setOriginBounds(null);
            if (this.mIsAdjusted) {
                this.mIsAdjusted = false;
            }
            this.mAdjustingBounds.setEmpty();
            this.mAnimating = false;
        }

        public final void setOriginBounds(Rect rect) {
            Rect rect2 = this.mOriginBounds;
            if (rect == null) {
                rect2.setEmpty();
            } else {
                rect2.set(rect);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EventReceiver extends InputEventReceiver {
        public EventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        public final void onInputEvent(InputEvent inputEvent) {
            boolean z;
            HandleAutoHide handleAutoHide;
            if (inputEvent instanceof MotionEvent) {
                MultitaskingWindowDecoration multitaskingWindowDecoration = MultitaskingWindowDecoration.this;
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                Rect bounds = multitaskingWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds();
                if (motionEvent.getAction() == 0 && bounds.contains((int) motionEvent.getX(), (int) motionEvent.getY()) && (handleAutoHide = multitaskingWindowDecoration.mHandleAutoHide) != null && !handleAutoHide.mIsShowing) {
                    if (handleAutoHide.mHide.isRunning()) {
                        handleAutoHide.mHide.end();
                    }
                    handleAutoHide.mShow.start();
                }
                z = true;
            } else {
                z = false;
            }
            finishInputEvent(inputEvent, z);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FreeformOutlineWrapper {
        public WindowDecoration.AdditionalWindow mFreeformOutline;
        public final int mLayoutId;
        public final String mNamePrefix;

        public FreeformOutlineWrapper(MultitaskingWindowDecoration multitaskingWindowDecoration) {
            this("Freeform Outline", R.layout.sec_decor_freeform_outline);
        }

        public final float calculateElevation() {
            int i;
            MultitaskingWindowDecoration multitaskingWindowDecoration = MultitaskingWindowDecoration.this;
            if (!multitaskingWindowDecoration.mTaskInfo.getConfiguration().isDexMode()) {
                i = R.dimen.sec_freeform_decor_shadow_thickness;
            } else {
                if (multitaskingWindowDecoration.mTaskInfo.getConfiguration().dexCompatEnabled == 2 && multitaskingWindowDecoration.mTaskInfo.configuration.windowConfiguration.getWindowingMode() == 1) {
                    return 0.0f;
                }
                i = R.dimen.sec_dex_decor_shadow_thickness;
            }
            Resources resources = multitaskingWindowDecoration.mDecorWindowContext.getResources();
            if (i == 0) {
                return 0.0f;
            }
            return resources.getDimension(i);
        }

        public final Rect getFreeformOutlineFrame() {
            MultitaskingWindowDecoration multitaskingWindowDecoration = MultitaskingWindowDecoration.this;
            Rect bounds = multitaskingWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds();
            int freeformThickness$1 = multitaskingWindowDecoration.getFreeformThickness$1();
            int i = -freeformThickness$1;
            return new Rect(i, i, bounds.width() + freeformThickness$1, multitaskingWindowDecoration.getCaptionVisibleHeight() + bounds.height() + freeformThickness$1);
        }

        public final OutlineView getOutlineView() {
            SurfaceControlViewHost surfaceControlViewHost;
            WindowDecoration.AdditionalWindow additionalWindow = this.mFreeformOutline;
            if (additionalWindow == null || (surfaceControlViewHost = additionalWindow.mWindowViewHost) == null || !(surfaceControlViewHost.getView() instanceof OutlineView)) {
                return null;
            }
            return (OutlineView) this.mFreeformOutline.mWindowViewHost.getView();
        }

        public final void updateOutlineView(boolean z) {
            OutlineView outlineView = getOutlineView();
            if (outlineView != null) {
                MultitaskingWindowDecoration multitaskingWindowDecoration = MultitaskingWindowDecoration.this;
                int loadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_freeform_outline_frame_radius, multitaskingWindowDecoration.mDecorWindowContext.getResources());
                int captionVisibleHeight = multitaskingWindowDecoration.getCaptionVisibleHeight();
                float freeformThickness$1 = multitaskingWindowDecoration.getFreeformThickness$1();
                outlineView.mStrokePaint.setStrokeWidth(freeformThickness$1);
                outlineView.mClearPaint.setStrokeWidth(freeformThickness$1);
                if (multitaskingWindowDecoration.mIsDexDockingEnabled) {
                    outlineView.mFillColor = multitaskingWindowDecoration.mCaptionColor;
                    outlineView.mStrokeColor = -11645362;
                    outlineView.mRadius = 0;
                    outlineView.mRadiusForShadow = (int) (0 * 1.2f);
                    outlineView.setElevation(0.0f);
                } else {
                    outlineView.mRadius = loadDimensionPixelSize;
                    outlineView.mRadiusForShadow = (int) (loadDimensionPixelSize * 1.2f);
                    int i = multitaskingWindowDecoration.mCaptionColor;
                    if (OutlineView.DEBUG) {
                        outlineView.mStrokeColor = 1727987712;
                        outlineView.mFillColor = 1711276287;
                    } else {
                        outlineView.mStrokeColor = i;
                        outlineView.mFillColor = i;
                    }
                    outlineView.setElevation(calculateElevation());
                }
                outlineView.mCaptionHeight = captionVisibleHeight;
                if (z) {
                    outlineView.invalidate();
                }
            }
        }

        public FreeformOutlineWrapper(String str, int i) {
            this.mNamePrefix = str;
            this.mLayoutId = i;
        }
    }

    public MultitaskingWindowDecoration(Context context, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Handler handler, Choreographer choreographer, SyncTransactionQueue syncTransactionQueue, DisplayInsetsController displayInsetsController, DexCompatRestartDialogUtils dexCompatRestartDialogUtils, Pip pip, SplitScreenController splitScreenController) {
        super(context, displayController, shellTaskOrganizer, runningTaskInfo, surfaceControl);
        this.mRelayoutParams = new WindowDecoration.RelayoutParams();
        this.mResult = new WindowDecoration.RelayoutResult();
        this.mIsHandleVisibleState = false;
        this.mDisplayIdForInsets = -1;
        InsetsState insetsState = new InsetsState();
        this.mInsetsState = insetsState;
        this.mIsKeyguardShowing = false;
        this.mIsBlurSupported = CoreRune.FW_WINDOW_BLUR_SUPPORTED;
        this.mIsSliderPopupShowing = false;
        this.mTmpRect = new Rect();
        FreeformStashState freeformStashState = new FreeformStashState();
        this.mFreeformStashState = freeformStashState;
        this.mLastStableBounds = new Rect();
        this.mAdjustState = new AdjustState();
        this.mHandler = handler;
        this.mChoreographer = choreographer;
        this.mSyncQueue = syncTransactionQueue;
        this.mIsNightMode = MultiWindowUtils.isNightMode(runningTaskInfo);
        if (CoreRune.MW_CAPTION_SHELL) {
            this.mIsNightMode = MultiWindowUtils.isNightMode(runningTaskInfo);
            this.mCaptionColor = context.getResources().getColor(this.mIsNightMode ? android.R.color.system_on_shade_active_dark : android.R.color.system_on_shade_active_light);
            insetsState.set(displayController.getInsetsState(0));
            this.mDisplayInsetsController = displayInsetsController;
            int i = runningTaskInfo.displayId;
            this.mDisplayIdForInsets = i;
            displayInsetsController.addInsetsChangedListener(i, this);
            int dexTaskDockingState = runningTaskInfo.getConfiguration().windowConfiguration.getDexTaskDockingState();
            this.mLastDockingState = dexTaskDockingState;
            this.mIsDexDockingEnabled = WindowConfiguration.isDexTaskDocking(dexTaskDockingState);
        }
        this.mSplitScreenController = splitScreenController;
        if (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE) {
            this.mCaptionType = (this.mIsDexMode || this.mIsNewDexMode) ? 1 : this.mTaskOrganizer.getFreeformCaptionType(this.mTaskInfo);
        }
        int freeformThickness$1 = getFreeformThickness$1();
        freeformStashState.mAnimType = -1;
        freeformStashState.mAnimating = false;
        freeformStashState.setStashed(-1);
        freeformStashState.mFreeformThickness = freeformThickness$1;
        if (CoreRune.MW_CAPTION_SHELL_OPACITY) {
            this.mAlpha = 1.0f;
        }
        this.mDexCompatRestartDialogUtils = dexCompatRestartDialogUtils;
        this.mIsSplitImmersiveEnabled = MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED;
        if (CoreRune.MW_CAPTION_SHELL_POPUP_HELP) {
            this.mMultiTaskingHelpController = new MultiTaskingHelpController(this.mContext, this.mTaskInfo.getWindowingMode());
        }
        if (CoreRune.MW_CAPTION_SHELL_SHADOW_ANIM) {
            this.mElevationAnimationShow = true;
        }
        if (CoreRune.MW_CAPTION_SHELL_OVERFLOW_MENU) {
            this.mIsPopupWindowPinned = false;
        }
        if (CoreRune.MW_CAPTION_SHELL_FULL_SCREEN) {
            this.mIsFullScreenCaptionState = runningTaskInfo.isFullScreenCaptionState && CaptionGlobalState.FULLSCREEN_HANDLER_ENABLED;
        }
        if (CoreRune.MW_CAPTION_SHELL_MOVE_DISPLAY) {
            this.mIsAdditionalDisplayAdded = false;
        }
        this.mPipController = CoreRune.MT_NEW_DEX_PIP ? pip : null;
    }

    public final WindowDecoration.AdditionalWindow addWindow(int i, String str, SurfaceControl.Transaction transaction, int i2, int i3, int i4, int i5, int i6, int i7, float f, boolean z) {
        return addWindow(i, str, transaction, i2, i3, i4, i5, i6, i7, f, z, null);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration, java.lang.AutoCloseable
    public final void close() {
        DragResizeInputListener dragResizeInputListener = this.mDragResizeListener;
        if (dragResizeInputListener != null) {
            dragResizeInputListener.close();
            this.mDragResizeListener = null;
        }
        if (CoreRune.MW_CAPTION_SHELL_POPUP) {
            closeHandleMenu(true);
        }
        FreeformDimInputListener freeformDimInputListener = this.mFreeformStashDimInputListener;
        if (freeformDimInputListener != null) {
            freeformDimInputListener.close();
            this.mFreeformStashDimInputListener = null;
        }
        if (CoreRune.MW_CAPTION_SHELL) {
            this.mDisplayInsetsController.removeInsetsChangedListener(this.mDisplayIdForInsets, this);
            this.mDestroyed = true;
        }
        if (CoreRune.MW_CAPTION_SHELL_KEEP_SCREEN_ON) {
            this.mHandleAutoHide = null;
            removeAutoHideInputChannel();
        }
        this.mAdjustState.reset();
        boolean z = CoreRune.MW_CAPTION_SHELL_MOVE_DISPLAY;
        super.close();
    }

    public final void closeHandleMenu(boolean z) {
        WindowMenuPopupPresenter windowMenuPopupPresenter;
        if (!isHandleMenuActive() || (windowMenuPopupPresenter = this.mPopupMenuPresenter) == null) {
            return;
        }
        if (z) {
            releaseHandleMenu();
            return;
        }
        AnimatorSet animatorSet = windowMenuPopupPresenter.mHideAnimation;
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecoration.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                WindowDecoration.AdditionalWindow additionalWindow = MultitaskingWindowDecoration.this.mHandleMenu;
                if (additionalWindow != null) {
                    ((SurfaceControl.Transaction) additionalWindow.mTransactionSupplier.get()).hide(additionalWindow.mWindowSurface).apply();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                HandleView handleView;
                MultitaskingWindowDecoration multitaskingWindowDecoration = MultitaskingWindowDecoration.this;
                multitaskingWindowDecoration.mIsHandleMenuShowing = false;
                if (multitaskingWindowDecoration.mIsBlurSupported || (handleView = multitaskingWindowDecoration.getHandleView()) == null) {
                    return;
                }
                handleView.setVisibility(0);
            }
        });
        animatorSet.start();
    }

    public final void closeMoreMenu() {
        if (!isHandleMenuActive() || this.mCaptionType == 0) {
            return;
        }
        Animator createSurfaceAlphaAnimator = CaptionAnimationUtils.createSurfaceAlphaAnimator(this.mHandleMenu.mWindowSurface, false, 250L, InterpolatorUtils.ONE_EASING);
        this.mOverflowMenuAnim = createSurfaceAlphaAnimator;
        createSurfaceAlphaAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecoration.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                WindowDecoration.AdditionalWindow additionalWindow = MultitaskingWindowDecoration.this.mHandleMenu;
                if (additionalWindow != null) {
                    additionalWindow.releaseView();
                }
                MultitaskingWindowDecoration.this.mHandleMenu = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                MultitaskingWindowDecoration.this.mIsHandleMenuShowing = false;
            }
        });
        this.mOverflowMenuAnim.start();
    }

    public final void closeSliderPopup() {
        WindowDecoration.AdditionalWindow additionalWindow = this.mSliderPopup;
        if (additionalWindow == null) {
            return;
        }
        Animator createSurfaceAlphaAnimator = CaptionAnimationUtils.createSurfaceAlphaAnimator(additionalWindow.mWindowSurface, false, 350L, InterpolatorUtils.SINE_OUT_60);
        createSurfaceAlphaAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecoration.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                WindowDecoration.AdditionalWindow additionalWindow2 = MultitaskingWindowDecoration.this.mSliderPopup;
                if (additionalWindow2 != null) {
                    additionalWindow2.releaseView();
                    MultitaskingWindowDecoration.this.mSliderPopup = null;
                }
                MultitaskingWindowDecoration.this.mIsSliderPopupShowing = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        createSurfaceAlphaAnimator.start();
    }

    public final CharSequence getAppName() {
        ComponentName componentName;
        CharSequence charSequence = this.mAppName;
        if (charSequence != null) {
            return charSequence;
        }
        try {
            PackageManager packageManager = this.mContext.getApplicationContext().getPackageManager();
            ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
            String packageName = (runningTaskInfo == null || (componentName = runningTaskInfo.realActivity) == null) ? null : componentName.getPackageName();
            if (packageName == null) {
                return "";
            }
            this.mAppName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(0L)));
            if (CoreRune.SAFE_DEBUG) {
                Log.d("MultitaskingWindowDecoration", "getAppName: " + ((Object) this.mAppName) + ", this=" + this);
            }
            return this.mAppName;
        } catch (Exception e) {
            Log.w("MultitaskingWindowDecoration", "getAppName: error ", e);
            return "";
        }
    }

    public final int getCaptionVisibleHeight() {
        return this.mCaptionType == 0 ? WindowDecoration.loadDimensionPixelSize(android.R.dimen.toast_elevation, this.mDecorWindowContext.getResources()) : this.mResult.mCaptionHeight;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final Configuration getConfigurationWithOverrides(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (!CoreRune.MT_NEW_DEX_PIP || !MultitaskingWindowDecorViewModel.isExitingPipTask(this.mPipController, runningTaskInfo)) {
            if (CoreRune.MW_CAPTION_SHELL && !runningTaskInfo.getConfiguration().isDexMode() && runningTaskInfo.getDisplayId() == 0 && MultiWindowUtils.hasCustomDensity() && runningTaskInfo.configuration.densityDpi != this.mContext.getResources().getConfiguration().densityDpi) {
                runningTaskInfo.configuration.densityDpi = this.mContext.getResources().getConfiguration().densityDpi;
            }
            return runningTaskInfo.getConfiguration();
        }
        Configuration configuration = new Configuration(runningTaskInfo.getConfiguration());
        int windowingMode = runningTaskInfo.getWindowingMode();
        int i = windowingMode != 1 ? 6 : 1;
        configuration.windowConfiguration.setWindowingMode(i);
        StringBuilder sb = new StringBuilder("getConfigurationWithOverrides: override windowing mode(");
        sb.append(windowingMode);
        sb.append("->");
        sb.append(i);
        sb.append(") t #");
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m71m(sb, runningTaskInfo.taskId, ", reason=exiting_pip", "MultitaskingWindowDecoration");
        return configuration;
    }

    public final int getFreeformThickness$1() {
        int loadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(android.R.dimen.tooltip_horizontal_padding, this.mDecorWindowContext.getResources());
        return loadDimensionPixelSize % 2 == 0 ? loadDimensionPixelSize : loadDimensionPixelSize + 1;
    }

    public final HandleView getHandleView() {
        View view = this.mResult.mRootView;
        if (view == null) {
            return null;
        }
        return (HandleView) ((WindowDecorLinearLayout) view).findViewById(R.id.caption_handle);
    }

    public final int getHandleViewWidth() {
        HandleView handleView = getHandleView();
        if (handleView == null) {
            return 0;
        }
        if (handleView.isAttachedToWindow()) {
            return handleView.getWidth();
        }
        Resources resources = this.mDecorWindowContext.getResources();
        return WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_handle_width, resources) - ((handleView.getPaddingLeft() == 0 ? WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_handle_padding_horizontal, resources) : 0) * 2);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final String getTag() {
        return "MultitaskingWindowDecoration";
    }

    public final Rect getVisibleTaskBounds() {
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId);
        Rect rect = new Rect();
        if (displayLayout != null) {
            rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
            rect.intersect(this.mTaskInfo.getConfiguration().windowConfiguration.getBounds());
        }
        return rect;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002e, code lost:
    
        if (r5 != false) goto L19;
     */
    @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void insetsChanged(InsetsState insetsState) {
        this.mInsetsState.set(insetsState);
        InsetsSource peekSource = insetsState.peekSource(InsetsSource.ID_IME);
        boolean z = true;
        boolean z2 = peekSource != null && peekSource.isVisible();
        if (this.mIsSplitImmersiveEnabled == MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED) {
            if (this.mIsNewDexMode) {
                ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
                if (runningTaskInfo.isFocused) {
                    if (runningTaskInfo.getWindowingMode() == 1) {
                    }
                }
            }
            z = false;
        }
        if (z) {
            relayout(this.mTaskInfo, false);
        }
    }

    @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
    public final void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) {
        insetsChanged(insetsState);
    }

    public final boolean isHandleMenuActive() {
        return this.mHandleMenu != null && this.mIsHandleMenuShowing;
    }

    public final boolean isMotionOrBoundsAnimating() {
        if (this.mTaskPositioner.mTaskMotionController.isMotionAnimating()) {
            return true;
        }
        PhysicsAnimator physicsAnimator = this.mTaskPositioner.mTaskMotionController.mTemporaryBoundsPhysicsAnimator;
        return physicsAnimator != null && physicsAnimator.isRunning();
    }

    public final PointF offsetCaptionLocation(MotionEvent motionEvent) {
        PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
        WindowDecoration.RelayoutParams relayoutParams = this.mRelayoutParams;
        pointF.offset(-relayoutParams.mCaptionX, -relayoutParams.mCaptionY);
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskOrganizer.getRunningTaskInfo(this.mTaskInfo.taskId);
        if (runningTaskInfo != null) {
            Point point = runningTaskInfo.positionInParent;
            pointF.offset(-point.x, -point.y);
        }
        return pointF;
    }

    public final void onDisplayAdded(boolean z) {
        this.mIsAdditionalDisplayAdded = true;
        if (z) {
            WindowMenuCaptionPresenter windowMenuCaptionPresenter = this.mCaptionMenuPresenter;
            if (windowMenuCaptionPresenter != null) {
                windowMenuCaptionPresenter.mIsDisplayAdded = true;
                windowMenuCaptionPresenter.setupAddDisplayButton(true);
            }
            relayout(this.mTaskInfo, false);
        }
    }

    public final void onTaskClosing() {
        TaskPositioner taskPositioner;
        FreeformDragPositioningController.FreeformDragListener freeformDragListener;
        OutlineView outlineView;
        FreeformOutlineWrapper freeformOutlineWrapper = this.mFreeformOutlineWrapper;
        if (freeformOutlineWrapper != null && (outlineView = freeformOutlineWrapper.getOutlineView()) != null) {
            outlineView.mIsClosing = true;
            outlineView.invalidate();
            if (OutlineView.SAFE_DEBUG) {
                Log.d("OutlineView", "onTaskClosing: " + outlineView);
            }
        }
        boolean z = CoreRune.MW_FREEFORM_DISMISS_VIEW;
        if (!z || (taskPositioner = this.mTaskPositioner) == null || !z || taskPositioner.mWindowDecoration.mIsDexMode || taskPositioner.mResizing || (freeformDragListener = taskPositioner.mDragPositioningListener) == null || taskPositioner.isDexSnappingInNonFreeform()) {
            return;
        }
        DismissButtonManager dismissButtonManager = freeformDragListener.mDismissButtonManager;
        Objects.requireNonNull(dismissButtonManager);
        dismissButtonManager.hide(new DismissButtonManager$$ExternalSyntheticLambda0(dismissButtonManager));
    }

    public final void onTaskOpening() {
        OutlineView outlineView;
        FreeformOutlineWrapper freeformOutlineWrapper = this.mFreeformOutlineWrapper;
        if (freeformOutlineWrapper == null || (outlineView = freeformOutlineWrapper.getOutlineView()) == null) {
            return;
        }
        outlineView.mIsOpening = true;
        outlineView.invalidate();
        if (OutlineView.SAFE_DEBUG) {
            Log.d("OutlineView", "onTaskOpening: " + outlineView);
        }
    }

    public final void playShadowAnimation(Rect rect, boolean z) {
        FreeformOutlineWrapper freeformOutlineWrapper;
        OutlineView outlineView;
        float f;
        ViewRootImpl viewRootImpl;
        ObjectAnimator objectAnimator = this.mShadowAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (z == this.mElevationAnimationShow || (freeformOutlineWrapper = this.mFreeformOutlineWrapper) == null || (outlineView = freeformOutlineWrapper.getOutlineView()) == null) {
            return;
        }
        if (z) {
            f = outlineView.getElevation();
        } else {
            float elevation = outlineView.getElevation();
            this.mWindowElevation = elevation;
            f = elevation / 3.0f;
        }
        if (z) {
            this.mShadowAnimator = ObjectAnimator.ofFloat(outlineView, "elevation", f, this.mWindowElevation);
            if (rect != null && (viewRootImpl = outlineView.getViewRootImpl()) != null) {
                viewRootImpl.updateLightCenter(rect);
            }
        } else {
            this.mShadowAnimator = ObjectAnimator.ofFloat(outlineView, "elevation", this.mWindowElevation, f);
        }
        this.mShadowAnimator.setDuration(100L).start();
        this.mShadowAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecoration.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                OutlineView outlineView2;
                super.onAnimationCancel(animator);
                FreeformOutlineWrapper freeformOutlineWrapper2 = MultitaskingWindowDecoration.this.mFreeformOutlineWrapper;
                if (freeformOutlineWrapper2 == null || (outlineView2 = freeformOutlineWrapper2.getOutlineView()) == null) {
                    return;
                }
                MultitaskingWindowDecoration multitaskingWindowDecoration = MultitaskingWindowDecoration.this;
                float f2 = multitaskingWindowDecoration.mElevationAnimationShow ? multitaskingWindowDecoration.mWindowElevation : multitaskingWindowDecoration.mWindowElevation / 3.0f;
                if (outlineView2.getElevation() != f2) {
                    outlineView2.setElevation(f2);
                }
            }
        });
        this.mElevationAnimationShow = z;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo) {
        relayout(runningTaskInfo, false);
    }

    public final void releaseHandleMenu() {
        HandleView handleView;
        if (!this.mIsBlurSupported && (handleView = getHandleView()) != null) {
            handleView.setVisibility(0);
        }
        this.mIsHandleMenuShowing = false;
        WindowDecoration.AdditionalWindow additionalWindow = this.mHandleMenu;
        if (additionalWindow != null) {
            additionalWindow.releaseView();
            this.mHandleMenu = null;
        }
        this.mPopupMenuPresenter = null;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final void releaseViews() {
        FreeformOutlineWrapper freeformOutlineWrapper = this.mFreeformOutlineWrapper;
        if (freeformOutlineWrapper != null) {
            WindowDecoration.AdditionalWindow additionalWindow = freeformOutlineWrapper.mFreeformOutline;
            if (additionalWindow != null) {
                additionalWindow.releaseView();
                freeformOutlineWrapper.mFreeformOutline = null;
            }
            this.mFreeformOutlineWrapper = null;
        }
        if (this.mCaptionMenuPresenter != null) {
            this.mCaptionMenuPresenter = null;
        }
        WindowDecoration.AdditionalWindow additionalWindow2 = this.mHandleMenu;
        if (additionalWindow2 != null) {
            additionalWindow2.releaseView();
            this.mHandleMenu = null;
        }
        this.mFreeformStashState.destroyStashDimOverlay();
        if (CoreRune.MW_CAPTION_SHELL_OPACITY) {
            this.mAlpha = 1.0f;
        }
        if (CoreRune.MW_CAPTION_SHELL_IMMERSIVE_MODE) {
            this.mImmersiveCaptionBehavior = null;
        }
        if (CoreRune.MW_CAPTION_SHELL_KEEP_SCREEN_ON) {
            this.mHandleAutoHide = null;
            removeAutoHideInputChannel();
        }
        super.releaseViews();
    }

    public final void removeAutoHideInputChannel() {
        InputMonitor inputMonitor = this.mInputMonitor;
        if (inputMonitor != null) {
            inputMonitor.dispose();
            this.mInputMonitor = null;
        }
        EventReceiver eventReceiver = this.mEventReceiver;
        if (eventReceiver != null) {
            eventReceiver.dispose();
            this.mEventReceiver = null;
        }
    }

    public final void resetDockingMargins() {
        View findViewById = ((WindowDecorLinearLayout) this.mResult.mRootView).findViewById(R.id.caption);
        if (findViewById != null) {
            int i = this.mLastDockingState;
            if (i == 1 || i == 2) {
                View findViewById2 = findViewById.findViewById(R.id.primary_button_set);
                if (findViewById2 != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) findViewById2.getLayoutParams();
                    marginLayoutParams.setMargins(0, marginLayoutParams.topMargin, 0, marginLayoutParams.bottomMargin);
                    findViewById2.setLayoutParams(marginLayoutParams);
                }
                WindowMenuItemView windowMenuItemView = (WindowMenuItemView) findViewById.findViewById(R.id.back_window);
                if (windowMenuItemView != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) windowMenuItemView.getLayoutParams();
                    marginLayoutParams2.setMargins(0, marginLayoutParams2.topMargin, 0, marginLayoutParams2.bottomMargin);
                    windowMenuItemView.setLayoutParams(marginLayoutParams2);
                }
            }
        }
    }

    public final void setCaptionColor$1() {
        View findViewById;
        VectorDrawable vectorDrawable;
        VectorDrawable vectorDrawable2;
        VectorDrawable vectorDrawable3;
        VectorDrawable vectorDrawable4;
        View view = this.mResult.mRootView;
        if (view == null || (findViewById = ((WindowDecorLinearLayout) view).findViewById(R.id.caption)) == null) {
            return;
        }
        if (CoreRune.MW_CAPTION_SHELL) {
            if (CoreRune.MW_CAPTION_SHELL_IMMERSIVE_MODE && this.mIsDexEnabled && this.mCaptionType == 1 && this.mTaskInfo.getWindowingMode() != 5) {
                if (this.mIsNewDexMode) {
                    setNewDexImmersiveCaptionBackground(this.mIsImmersiveMode);
                    return;
                } else {
                    findViewById.setBackgroundColor(this.mCaptionColor);
                    return;
                }
            }
            return;
        }
        ColorStateList colorStateList = findViewById.getResources().getColorStateList(((double) Color.valueOf(this.mCaptionColor).luminance()) < 0.5d ? R.color.decor_button_light_color : R.color.decor_button_dark_color, null);
        View findViewById2 = findViewById.findViewById(R.id.back_button);
        if (findViewById2 != null && (vectorDrawable4 = (VectorDrawable) findViewById2.getBackground()) != null) {
            vectorDrawable4.setTintList(colorStateList);
        }
        View findViewById3 = findViewById.findViewById(R.id.minimize_window);
        if (findViewById3 != null && (vectorDrawable3 = (VectorDrawable) findViewById3.getBackground()) != null) {
            vectorDrawable3.setTintList(colorStateList);
        }
        View findViewById4 = findViewById.findViewById(R.id.maximize_window);
        if (findViewById4 != null && (vectorDrawable2 = (VectorDrawable) findViewById4.getBackground()) != null) {
            vectorDrawable2.setTintList(colorStateList);
        }
        View findViewById5 = findViewById.findViewById(R.id.close_window);
        if (findViewById5 == null || (vectorDrawable = (VectorDrawable) findViewById5.getBackground()) == null) {
            return;
        }
        vectorDrawable.setTintList(colorStateList);
    }

    public final void setDecorationOpacity(float f) {
        OutlineView outlineView;
        this.mAlpha = f;
        WindowMenuCaptionPresenter windowMenuCaptionPresenter = this.mCaptionMenuPresenter;
        if (windowMenuCaptionPresenter != null) {
            windowMenuCaptionPresenter.mAlpha = f;
        }
        WindowMenuPopupPresenter windowMenuPopupPresenter = this.mPopupMenuPresenter;
        if (windowMenuPopupPresenter != null) {
            windowMenuPopupPresenter.mAlpha = f;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        SurfaceControl surfaceControl = this.mCaptionContainerSurface;
        if (surfaceControl != null) {
            transaction.setAlpha(surfaceControl, f);
        }
        FreeformOutlineWrapper freeformOutlineWrapper = this.mFreeformOutlineWrapper;
        if (freeformOutlineWrapper != null && (outlineView = freeformOutlineWrapper.getOutlineView()) != null && outlineView.mAlpha != f) {
            outlineView.mAlpha = f;
            if (outlineView.getBackground() != null) {
                outlineView.getBackground().setAlpha((int) (255.0f * f));
            }
            outlineView.invalidate();
            if (OutlineView.SAFE_DEBUG) {
                Log.d("OutlineView", "onDecorationOpacityChanged: alpha=" + f + ", " + outlineView);
            }
        }
        this.mSyncQueue.runInSync(new MultitaskingWindowDecoration$$ExternalSyntheticLambda1(3, transaction));
    }

    public final void setHandleAutoHideEnabled(boolean z) {
        HandleView handleView = getHandleView();
        if (this.mHandleAutoHide == null && handleView != null) {
            this.mHandleAutoHide = new HandleAutoHide(this.mHandler, handleView);
        }
        HandleAutoHide handleAutoHide = this.mHandleAutoHide;
        if (handleAutoHide != null && handleAutoHide.mEnabled != z) {
            handleAutoHide.mEnabled = z;
            HandleAutoHide$$ExternalSyntheticLambda0 handleAutoHide$$ExternalSyntheticLambda0 = handleAutoHide.mHideRunnable;
            Handler handler = handleAutoHide.mHandler;
            if (z) {
                handler.postDelayed(handleAutoHide$$ExternalSyntheticLambda0, 2000L);
            } else {
                handler.removeCallbacks(handleAutoHide$$ExternalSyntheticLambda0);
                if (!handleAutoHide.mIsShowing) {
                    if (handleAutoHide.mHide.isRunning()) {
                        handleAutoHide.mHide.end();
                    }
                    handleAutoHide.mShow.start();
                }
            }
        }
        if (!z) {
            removeAutoHideInputChannel();
            return;
        }
        if (this.mInputMonitor == null) {
            this.mInputMonitor = InputManager.getInstance().monitorGestureInput("caption-touch", this.mContext.getDisplayId());
        }
        if (this.mEventReceiver == null) {
            this.mEventReceiver = new EventReceiver(this.mInputMonitor.getInputChannel(), Looper.myLooper());
        }
    }

    public final void setImmersiveMode(boolean z) {
        String str;
        View view;
        boolean z2 = this.mIsImmersiveMode;
        if (z2 != z || (z2 && this.mImmersiveCaptionBehavior == null)) {
            this.mIsImmersiveMode = z;
            StringBuilder sb = new StringBuilder("setImmersiveMode: ");
            sb.append(z);
            sb.append(", ");
            sb.append(this);
            if (CoreRune.SAFE_DEBUG) {
                str = ", Callers=" + Debug.getCallers(3);
            } else {
                str = "";
            }
            ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str, "MultitaskingWindowDecoration");
            if (this.mDestroyed || (view = this.mResult.mRootView) == null || !this.mIsDexEnabled) {
                return;
            }
            if (!((WindowDecorLinearLayout) view).isAttachedToWindow()) {
                Log.d("MultitaskingWindowDecoration", "setImmersiveMode: pending, reason=not_attached_yet");
                return;
            }
            if (this.mImmersiveCaptionBehavior == null) {
                ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
                Handler handler = this.mHandler;
                WindowDecoration.RelayoutResult relayoutResult = this.mResult;
                this.mImmersiveCaptionBehavior = new ImmersiveCaptionBehavior(runningTaskInfo, handler, relayoutResult.mRootView, relayoutResult.mCaptionHeight);
            }
            if (this.mIsImmersiveMode) {
                ImmersiveCaptionBehavior immersiveCaptionBehavior = this.mImmersiveCaptionBehavior;
                immersiveCaptionBehavior.setShownState(true);
                immersiveCaptionBehavior.mIsPaused = false;
                immersiveCaptionBehavior.hide();
            } else {
                this.mImmersiveCaptionBehavior.pause();
            }
            Rect rect = this.mCaptionInsetsRect;
            if (z) {
                if (rect == null || rect.isEmpty()) {
                    return;
                }
                relayout(this.mTaskInfo, false);
                return;
            }
            if (rect == null || rect.isEmpty()) {
                relayout(this.mTaskInfo, false);
            }
        }
    }

    public final void setNewDexImmersiveCaptionBackground(boolean z) {
        View findViewById;
        View view = this.mResult.mRootView;
        if (view == null) {
            return;
        }
        if (!z) {
            View findViewById2 = ((WindowDecorLinearLayout) view).findViewById(R.id.caption);
            if (findViewById2 != null) {
                findViewById2.setBackgroundColor(this.mCaptionColor);
            }
        } else if (view != null && (findViewById = ((WindowDecorLinearLayout) view).findViewById(R.id.caption)) != null) {
            findViewById.setBackgroundColor(this.mDecorWindowContext.getResources().getColor(17171516, null));
        }
        WindowMenuCaptionPresenter windowMenuCaptionPresenter = this.mCaptionMenuPresenter;
        if (windowMenuCaptionPresenter == null) {
            return;
        }
        ColorStateList colorStateList = z ? windowMenuCaptionPresenter.mContext.getColorStateList(R.color.sec_decor_icon_color_dark) : windowMenuCaptionPresenter.getButtonTintColor();
        int i = 0;
        while (true) {
            SparseArray sparseArray = windowMenuCaptionPresenter.mButtons;
            if (i >= sparseArray.size()) {
                return;
            }
            WindowMenuItemView windowMenuItemView = (WindowMenuItemView) sparseArray.valueAt(i);
            if (windowMenuItemView != null) {
                windowMenuItemView.setImageTintList(colorStateList);
                if (!windowMenuItemView.isEnabled()) {
                    windowMenuItemView.setAlpha(0.4f);
                }
            }
            i++;
        }
    }

    public final void setOpacitySlider() {
        WindowMenuPopupPresenter windowMenuPopupPresenter;
        if (this.mCaptionType == 0 && (windowMenuPopupPresenter = this.mPopupMenuPresenter) != null) {
            WindowDecorSlider windowDecorSlider = windowMenuPopupPresenter.mSlider;
            if (windowDecorSlider != null) {
                windowDecorSlider.setControllable(true);
                windowMenuPopupPresenter.mSlider.setProgress((int) ((1.0f - windowMenuPopupPresenter.mAlpha) * 100.0f));
                return;
            }
            return;
        }
        WindowMenuCaptionPresenter windowMenuCaptionPresenter = this.mCaptionMenuPresenter;
        if (windowMenuCaptionPresenter != null) {
            WindowDecorSlider windowDecorSlider2 = windowMenuCaptionPresenter.mSlider;
            if (windowDecorSlider2 != null) {
                windowDecorSlider2.setControllable(true);
                windowMenuCaptionPresenter.mSlider.setProgress((int) ((1.0f - windowMenuCaptionPresenter.mAlpha) * 100.0f));
            }
            closeMoreMenu();
        }
    }

    public final void setupCaptionColor() {
        boolean isNightMode = MultiWindowUtils.isNightMode(this.mTaskInfo);
        if (this.mIsNightMode != isNightMode) {
            this.mIsNightMode = isNightMode;
            this.mCaptionColor = this.mContext.getResources().getColor(this.mIsNightMode ? android.R.color.system_on_shade_active_dark : android.R.color.system_on_shade_active_light);
            FreeformOutlineWrapper freeformOutlineWrapper = this.mFreeformOutlineWrapper;
            if (freeformOutlineWrapper != null) {
                freeformOutlineWrapper.updateOutlineView(true);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x019a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void showHandleMenu() {
        int i;
        MultiTaskingHelpController multiTaskingHelpController;
        int i2;
        int i3;
        int i4;
        boolean z;
        int i5;
        int i6;
        View view;
        SurfaceControl surfaceControl;
        View view2;
        if (this.mFreeformStashState.isStashed()) {
            return;
        }
        WindowMenuPopupPresenter windowMenuPopupPresenter = this.mPopupMenuPresenter;
        if (windowMenuPopupPresenter != null && windowMenuPopupPresenter.needRecreateHandleMenu(this.mTaskInfo)) {
            releaseHandleMenu();
        }
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mSurfaceControlTransactionSupplier.get();
        WindowDecoration.AdditionalWindow additionalWindow = this.mHandleMenu;
        if (additionalWindow == null) {
            Resources resources = this.mDecorWindowContext.getResources();
            HandleView handleView = getHandleView();
            if (handleView == null) {
                i = 2;
            } else {
                int loadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_button_size, resources);
                int loadDimensionPixelSize2 = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_button_padding, resources);
                int windowingMode = this.mTaskInfo.getWindowingMode();
                int loadDimensionPixelSize3 = windowingMode == 5 ? WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_divider_width, resources) : 0;
                if (CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE && this.mIsNewDexMode) {
                    i2 = 0;
                    for (int i7 : windowingMode == 5 ? new int[]{R.id.caption_pin_window, R.id.window_pin_window, R.id.split_window, R.id.opacity_window, R.id.minimize_window, R.id.maximize_window, R.id.close_window} : windowingMode == 1 ? new int[]{R.id.minimize_window, R.id.split_window, R.id.freeform_window, R.id.close_window} : new int[]{R.id.apps_window, R.id.freeform_window, R.id.maximize_window, R.id.close_window}) {
                        if (WindowMenuPresenter.isButtonVisible(i7, windowingMode, false, false)) {
                            i2++;
                        }
                    }
                } else {
                    i2 = 0;
                    for (int i8 : windowingMode == 5 ? new int[]{R.id.caption_pin_window, R.id.split_window, R.id.opacity_window, R.id.minimize_window, R.id.maximize_window, R.id.close_window} : windowingMode == 1 ? new int[]{R.id.freeform_window, R.id.split_window, R.id.close_window} : new int[]{R.id.apps_window, R.id.freeform_window, R.id.maximize_window, R.id.close_window}) {
                        if (WindowMenuPresenter.isButtonVisible(i8, windowingMode, false, false)) {
                            i2++;
                        }
                    }
                }
                int i9 = (loadDimensionPixelSize2 * 2) + (i2 * loadDimensionPixelSize) + loadDimensionPixelSize3;
                Rect bounds = this.mTaskInfo.configuration.windowConfiguration.getBounds();
                int i10 = this.mContext.getResources().getDisplayMetrics().widthPixels;
                int width = (bounds.width() - i9) / 2;
                int windowingMode2 = this.mTaskInfo.getWindowingMode();
                if (windowingMode2 == 5) {
                    int i11 = bounds.left;
                    if (i11 + width < 0) {
                        width = i11 * (-1);
                    } else if (bounds.right - width > i10) {
                        width = (i10 - i11) - i9;
                    }
                    i3 = 6;
                } else {
                    i3 = 6;
                    if (windowingMode2 == 6) {
                        width += this.mRelayoutParams.mHorizontalInset;
                    }
                }
                int windowingMode3 = this.mTaskInfo.getWindowingMode();
                if (windowingMode3 == i3 && this.mTaskInfo.getConfiguration().windowConfiguration.getStagePosition() == 64) {
                    i6 = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_decor_handle_menu_split_bottom_margin);
                } else {
                    if (windowingMode3 != 5) {
                        i6 = this.mRelayoutParams.mCaptionY;
                        if (this.mTaskInfo.getConfiguration().windowConfiguration.isSplitScreen()) {
                            i5 = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_decor_handle_menu_split_bottom_margin) + i6;
                            z = false;
                            i4 = 2;
                        }
                    } else {
                        i4 = 2;
                        int height = this.mRelayoutParams.mCaptionY - ((loadDimensionPixelSize - handleView.getHeight()) / 2);
                        this.mTmpRect.set(this.mInsetsState.getDisplayFrame());
                        z = false;
                        if (bounds.top + height < this.mInsetsState.calculateInsets(this.mTmpRect, WindowInsets.Type.displayCutout() | WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars(), false).top) {
                            i6 = this.mRelayoutParams.mCaptionY;
                            i5 = i6;
                        } else {
                            i5 = height;
                        }
                    }
                    i = i4;
                    WindowDecoration.AdditionalWindow addWindow = addWindow(windowingMode != 5 ? this.mIsNewDexMode ? R.layout.sec_caption_handle_menu_new_dex : R.layout.sec_caption_handle_menu : windowingMode == 1 ? this.mIsNewDexMode ? R.layout.sec_caption_handle_menu_new_dex_fullscreen : R.layout.sec_caption_handle_menu_fullscreen : this.mIsNewDexMode ? R.layout.sec_caption_handle_menu_new_dex_split : R.layout.sec_caption_handle_menu_split, "Caption Menu", transaction, width, i5, i9, loadDimensionPixelSize, 2, 262152, WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_popup_elevation, resources), true);
                    this.mHandleMenu = addWindow;
                    view = addWindow.mWindowViewHost.getView();
                    if (view != null) {
                        view.setOnTouchListener(new MultitaskingWindowDecoration$$ExternalSyntheticLambda0(i, this));
                    }
                    surfaceControl = this.mDragResizeInputSurface;
                    if (surfaceControl != null && windowingMode == 5) {
                        transaction.setRelativeLayer(this.mHandleMenu.mWindowSurface, surfaceControl, 1);
                    }
                    this.mPopupMenuPresenter = new WindowMenuPopupPresenter(this.mDecorWindowContext, this.mTaskInfo, view, this.mOnCaptionTouchListener, this.mAlpha, i9, this.mIsAdditionalDisplayAdded);
                    view2 = this.mHandleMenu.mWindowViewHost.getView();
                    if (view2 != null) {
                        View findViewById = this.mTaskInfo.getWindowingMode() != 5 ? view2.findViewById(R.id.button_container) : view2.findViewById(R.id.background_container);
                        if (findViewById != null) {
                            ((GradientDrawable) view2.getBackground()).setColor(this.mCaptionColor);
                            SemBlurInfo build = new SemBlurInfo.Builder(0).setRadius(125).setBackgroundColor(Color.argb(204, Color.red(this.mCaptionColor), Color.green(this.mCaptionColor), Color.blue(this.mCaptionColor))).setBackgroundCornerRadius(this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_decor_handle_menu_background_corner_radius)).build();
                            findViewById.setClipToOutline(true);
                            findViewById.semSetBlurInfo(build);
                        }
                    }
                    if (CoreRune.MW_CAPTION_SHELL_FULL_SCREEN && this.mIsKeyguardShowing) {
                        this.mPopupMenuPresenter.setFreeformButtonEnabled(false);
                    }
                }
                z = false;
                i4 = 2;
                i5 = i6;
                i = i4;
                WindowDecoration.AdditionalWindow addWindow2 = addWindow(windowingMode != 5 ? this.mIsNewDexMode ? R.layout.sec_caption_handle_menu_new_dex : R.layout.sec_caption_handle_menu : windowingMode == 1 ? this.mIsNewDexMode ? R.layout.sec_caption_handle_menu_new_dex_fullscreen : R.layout.sec_caption_handle_menu_fullscreen : this.mIsNewDexMode ? R.layout.sec_caption_handle_menu_new_dex_split : R.layout.sec_caption_handle_menu_split, "Caption Menu", transaction, width, i5, i9, loadDimensionPixelSize, 2, 262152, WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_popup_elevation, resources), true);
                this.mHandleMenu = addWindow2;
                view = addWindow2.mWindowViewHost.getView();
                if (view != null) {
                }
                surfaceControl = this.mDragResizeInputSurface;
                if (surfaceControl != null) {
                    transaction.setRelativeLayer(this.mHandleMenu.mWindowSurface, surfaceControl, 1);
                }
                this.mPopupMenuPresenter = new WindowMenuPopupPresenter(this.mDecorWindowContext, this.mTaskInfo, view, this.mOnCaptionTouchListener, this.mAlpha, i9, this.mIsAdditionalDisplayAdded);
                view2 = this.mHandleMenu.mWindowViewHost.getView();
                if (view2 != null) {
                }
                if (CoreRune.MW_CAPTION_SHELL_FULL_SCREEN) {
                    this.mPopupMenuPresenter.setFreeformButtonEnabled(false);
                }
            }
        } else {
            i = 2;
            ((SurfaceControl.Transaction) additionalWindow.mTransactionSupplier.get()).show(additionalWindow.mWindowSurface).apply();
        }
        WindowMenuPopupPresenter windowMenuPopupPresenter2 = this.mPopupMenuPresenter;
        if (windowMenuPopupPresenter2 != null) {
            if (CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE && this.mIsNewDexMode) {
                boolean z2 = this.mIsPopupWindowPinned;
                WindowMenuItemView windowMenuItemView = (WindowMenuItemView) windowMenuPopupPresenter2.mRootView.findViewById(R.id.window_pin_window);
                if (windowMenuItemView != null && windowMenuItemView.mShowIconBackground != z2) {
                    windowMenuItemView.mShowIconBackground = z2;
                    windowMenuItemView.invalidate();
                }
            }
            AnimatorSet animatorSet = this.mPopupMenuPresenter.mShowAnimation;
            if (animatorSet != null) {
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecoration.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        View view3;
                        WindowMenuPopupPresenter.RunnableC41911 runnableC41911;
                        MultitaskingWindowDecoration multitaskingWindowDecoration = MultitaskingWindowDecoration.this;
                        multitaskingWindowDecoration.mIsHandleMenuShowing = true;
                        MultitaskingWindowDecorViewModel.CaptionTouchEventListener captionTouchEventListener = multitaskingWindowDecoration.mOnCaptionTouchListener;
                        if (captionTouchEventListener != null) {
                            captionTouchEventListener.schedulePopupDismiss();
                        }
                        WindowMenuPopupPresenter windowMenuPopupPresenter3 = MultitaskingWindowDecoration.this.mPopupMenuPresenter;
                        if (windowMenuPopupPresenter3 == null || (view3 = windowMenuPopupPresenter3.mRootView) == null || (runnableC41911 = windowMenuPopupPresenter3.mPinAnimRunnable) == null) {
                            return;
                        }
                        view3.postDelayed(runnableC41911, 50L);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        HandleView handleView2;
                        MultitaskingWindowDecoration multitaskingWindowDecoration = MultitaskingWindowDecoration.this;
                        if (multitaskingWindowDecoration.mIsBlurSupported || (handleView2 = multitaskingWindowDecoration.getHandleView()) == null) {
                            return;
                        }
                        handleView2.setVisibility(8);
                    }
                });
                animatorSet.start();
            }
            if (this.mIsAdditionalDisplayAdded) {
                this.mPopupMenuPresenter.getClass();
            }
        }
        final InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
        if (inputMethodManager != null && inputMethodManager.isInputMethodShown()) {
            this.mHandler.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecoration$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    inputMethodManager.semForceHideSoftInput();
                    Log.i("MultitaskingWindowDecoration", "Hide the Ime to use the multi-window handler.");
                }
            });
        }
        this.mSyncQueue.runInSync(new MultitaskingWindowDecoration$$ExternalSyntheticLambda1(i, transaction));
        if (!CoreRune.MW_CAPTION_SHELL_POPUP_HELP || (multiTaskingHelpController = this.mMultiTaskingHelpController) == null) {
            return;
        }
        int i12 = multiTaskingHelpController.mWindowingMode;
        if (i12 == 6 ? MultiTaskingHelpController.SPLIT_HANDLER_HELP_POPUP_ENABLED : i12 == 5 ? MultiTaskingHelpController.FREEFORM_HANDLER_HELP_POPUP_ENABLED : false) {
            int windowingMode4 = this.mTaskInfo.getWindowingMode();
            Context context = multiTaskingHelpController.mContext;
            if (windowingMode4 == 6) {
                Settings.Global.putInt(context.getContentResolver(), "multi_split_quick_options_help_count", 1);
                MultiTaskingHelpController.SPLIT_HANDLER_HELP_POPUP_ENABLED = false;
            } else if (windowingMode4 == 5) {
                Settings.Global.putInt(context.getContentResolver(), "freeform_handler_help_popup_count", 1);
                MultiTaskingHelpController.FREEFORM_HANDLER_HELP_POPUP_ENABLED = false;
            }
            if (CoreRune.SAFE_DEBUG) {
                Slog.d("MultiTaskingHelpController", "helpPopupFinished: windowingMode=" + windowingMode4);
            }
        }
    }

    public final String toString() {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(AbstractC0000x2c234b15.m1m("MultitaskingWindowDecoration{#", runningTaskInfo != null ? runningTaskInfo.taskId : -1, " immersive="), this.mIsImmersiveMode, "}");
    }

    public final void updateDimensions(DisplayMetrics displayMetrics) {
        TaskMotionController taskMotionController = this.mTaskPositioner.mTaskMotionController;
        taskMotionController.getClass();
        taskMotionController.mScreenEdgeInset = PipUtils.dpToPx(displayMetrics, 13.0f);
        taskMotionController.mMinVisibleWidth = PipUtils.dpToPx(displayMetrics, 32.0f);
        taskMotionController.mScaledFreeformHeight = PipUtils.dpToPx(displayMetrics, 220.0f);
        taskMotionController.mStashMoveThreshold = PipUtils.dpToPx(displayMetrics, 10.0f);
    }

    public final void updateNonFreeformCaptionVisibility() {
        HandleView handleView = getHandleView();
        if (handleView == null) {
            return;
        }
        boolean z = (CoreRune.MW_CAPTION_SHELL_FULL_SCREEN && this.mTaskInfo.getWindowingMode() == 1 && !MultitaskingWindowDecorViewModel.canUseFullscreenHandler(this.mTaskInfo, false)) ? false : !shouldHideHandlerByAppRequest(this.mTaskInfo);
        handleView.setVisibility(z ? 0 : 8);
        this.mIsHandleVisibleState = z;
        handleView.setTaskFocusState(WindowDecoration.hasBarFocus(this.mTaskInfo));
        if (CoreRune.MW_CAPTION_SHELL_KEEP_SCREEN_ON && z && this.mIsKeepScreenOn) {
            setHandleAutoHideEnabled(true);
        }
    }

    public final void updateRoundedCornerForSplit() {
        View findViewById;
        View view = this.mResult.mRootView;
        if (view == null || (findViewById = ((WindowDecorLinearLayout) view).findViewById(R.id.caption)) == null || findViewById.getContext() == null || this.mCaptionType == 0 || !this.mIsNewDexMode || !this.mTaskInfo.getConfiguration().windowConfiguration.isSplitScreen()) {
            return;
        }
        int roundedCornerRadius = MultiWindowUtils.getRoundedCornerRadius(findViewById.getContext());
        int roundedCornerColor = MultiWindowUtils.getRoundedCornerColor(findViewById.getContext());
        findViewById.semSetRoundedCorners(3, roundedCornerRadius);
        findViewById.semSetRoundedCornerColor(3, roundedCornerColor);
    }

    public final WindowDecoration.AdditionalWindow addWindow(int i, String str, SurfaceControl.Transaction transaction, int i2, int i3, int i4, int i5, int i6, int i7, float f, boolean z, View view) {
        View inflate;
        float f2;
        SurfaceControl.Builder builder = (SurfaceControl.Builder) this.mSurfaceControlBuilderSupplier.get();
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, " of Task=");
        m2m.append(this.mTaskInfo.taskId);
        SurfaceControl build = builder.setName(m2m.toString()).setContainerLayer().setParent(this.mDecorationContainerSurface).build();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i4, i5, i6, i7, -2);
        layoutParams.setTitle("Additional window of Task=" + this.mTaskInfo.taskId + " (" + str + ")");
        layoutParams.setTrustedOverlay();
        if (z) {
            layoutParams.multiwindowFlags = 2;
        }
        WindowlessWindowManager windowlessWindowManager = new WindowlessWindowManager(this.mTaskInfo.configuration, build, (IBinder) null, z ? this.mTaskInfo.token : null);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this.mDecorWindowContext, android.R.style.Theme.DeviceDefault.DayNight);
        WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory = this.mSurfaceControlViewHostFactory;
        Display display = this.mDisplay;
        surfaceControlViewHostFactory.getClass();
        SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(contextThemeWrapper, display, windowlessWindowManager, "WindowDecoration");
        if (view != null) {
            f2 = f;
            inflate = view;
        } else {
            inflate = LayoutInflater.from(contextThemeWrapper).inflate(i, (ViewGroup) null);
            f2 = f;
        }
        inflate.setElevation(f2);
        layoutParams.setSurfaceInsets(inflate, true, false);
        surfaceControlViewHost.setView(inflate, layoutParams);
        Rect rect = layoutParams.surfaceInsets;
        transaction.setPosition(build, i2 - rect.left, i3 - rect.top).show(build);
        return new WindowDecoration.AdditionalWindow(build, surfaceControlViewHost, this.mSurfaceControlTransactionSupplier);
    }

    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        relayout(runningTaskInfo, transaction, transaction, true, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x01e5, code lost:
    
        if ((r0.getStagePosition() & 64) == 0) goto L156;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x02de A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x03d9  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x03de  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x03e0  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x03db  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x04b8  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0507  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x056e  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x059d  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x05c5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:255:0x05c6  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x0a8b  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x0ab5  */
    /* JADX WARN: Removed duplicated region for block: B:436:0x0a91  */
    /* JADX WARN: Removed duplicated region for block: B:499:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:549:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:556:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:558:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:560:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0212  */
    /* JADX WARN: Type inference failed for: r1v100 */
    /* JADX WARN: Type inference failed for: r1v101, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v118 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void relayout(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, boolean z, boolean z2) {
        int i;
        int i2;
        int i3;
        boolean z3;
        String str;
        WindowContainerTransaction windowContainerTransaction;
        WindowDecorLinearLayout windowDecorLinearLayout;
        boolean z4;
        SurfaceControl surfaceControl;
        boolean z5;
        boolean z6;
        DisplayLayout displayLayout;
        View view;
        int i4;
        boolean z7;
        int i5;
        DisplayLayout displayLayout2;
        int width;
        SurfaceControlViewHost surfaceControlViewHost;
        HandleView handleView;
        boolean z8;
        WindowMenuCaptionPresenter windowMenuCaptionPresenter;
        WindowMenuItemView rotationButton;
        HandleView handleView2;
        ?? r1;
        boolean z9;
        int i6;
        MultiTaskingHelpController multiTaskingHelpController;
        boolean z10;
        HandleView handleView3;
        FreeformOutlineWrapper freeformOutlineWrapper;
        DisplayCutout displayCutout;
        FreeformOutlineWrapper freeformOutlineWrapper2;
        WindowMenuCaptionPresenter windowMenuCaptionPresenter2;
        WindowMenuPopupPresenter windowMenuPopupPresenter;
        int i7;
        int i8;
        int i9;
        int i10;
        View findViewById;
        View view2;
        int i11;
        int i12;
        boolean z11;
        boolean z12;
        Configuration configurationWithOverrides = getConfigurationWithOverrides(runningTaskInfo);
        int windowingMode = configurationWithOverrides.windowConfiguration.getWindowingMode();
        int i13 = runningTaskInfo.isFocused ? R.dimen.freeform_decor_shadow_focused_thickness : R.dimen.freeform_decor_shadow_unfocused_thickness;
        boolean z13 = windowingMode == 5;
        boolean z14 = CoreRune.MD_DEX_COMPAT_CAPTION_SHELL && this.mTaskInfo.getConfiguration().dexCompatUiMode == 3;
        boolean z15 = (CoreRune.MT_SUPPORT_SIZE_COMPAT_DRAG && z13 && SizeCompatInfo.isDragResizable(runningTaskInfo.sizeCompatInfo)) ? true : z13 && runningTaskInfo.isResizeable;
        boolean isDexMode = configurationWithOverrides.isDexMode();
        boolean isNewDexMode = configurationWithOverrides.isNewDexMode();
        WindowDecorLinearLayout windowDecorLinearLayout2 = (WindowDecorLinearLayout) this.mResult.mRootView;
        SurfaceControl surfaceControl2 = this.mDecorationContainerSurface;
        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
        this.mRelayoutParams.reset();
        this.mRelayoutParams.mRunningTaskInfo = runningTaskInfo;
        int freeformCaptionType = (isDexMode || isNewDexMode) ? 1 : this.mTaskOrganizer.getFreeformCaptionType(runningTaskInfo);
        this.mCaptionType = freeformCaptionType;
        int i14 = 6;
        if (isDexMode) {
            WindowDecoration.RelayoutParams relayoutParams = this.mRelayoutParams;
            relayoutParams.mLayoutResId = (!z13 || z14) ? R.layout.sec_window_decoration_desktop : R.layout.sec_window_decoration_desktop_freeform;
            relayoutParams.mCaptionHeightId = z13 ? R.dimen.sec_decor_caption_height_desktop_freeform : R.dimen.sec_decor_caption_height_desktop_fullscreen;
        } else {
            int i15 = R.layout.sec_decor_handle_layout_split_bottom;
            if (isNewDexMode) {
                if (freeformCaptionType == 1) {
                    WindowDecoration.RelayoutParams relayoutParams2 = this.mRelayoutParams;
                    if (z13) {
                        i2 = R.layout.sec_window_decoration_new_dex_freeform;
                    } else {
                        i2 = runningTaskInfo.getWindowingMode() == 1 ? R.layout.sec_window_decoration_new_dex_fullscreen : R.layout.sec_window_decoration_new_dex_split;
                    }
                    relayoutParams2.mLayoutResId = i2;
                } else {
                    this.mRelayoutParams.mLayoutResId = (runningTaskInfo.getWindowingMode() == 6 && runningTaskInfo.getConfiguration().windowConfiguration.getStagePosition() == 64) ? R.layout.sec_decor_handle_layout_split_bottom : R.layout.sec_decor_handle_layout;
                }
                WindowDecoration.RelayoutParams relayoutParams3 = this.mRelayoutParams;
                relayoutParams3.mCaptionHeightId = this.mCaptionType == 0 ? android.R.dimen.tooltip_margin : z13 ? android.R.dimen.toast_y_offset : R.dimen.sec_decor_caption_height_new_dex_fullscreen;
                relayoutParams3.mCaptionWidthId = SemViewUtils.isTablet() ? R.dimen.sec_decor_handle_width_tablet : R.dimen.sec_decor_handle_width;
            } else {
                WindowDecoration.RelayoutParams relayoutParams4 = this.mRelayoutParams;
                if (z13 && CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE) {
                    if (freeformCaptionType == 1) {
                        i15 = R.layout.sec_window_decoration;
                        i = i15;
                        relayoutParams4.mLayoutResId = i;
                        WindowDecoration.RelayoutParams relayoutParams5 = this.mRelayoutParams;
                        relayoutParams5.mCaptionHeightId = z13 ? (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE && this.mCaptionType == 0) ? android.R.dimen.tooltip_margin : android.R.dimen.timepicker_time_label_size : R.dimen.sec_decor_caption_height_fullscreen;
                        relayoutParams5.mCaptionWidthId = SemViewUtils.isTablet() ? R.dimen.sec_decor_handle_width_tablet : R.dimen.sec_decor_handle_width;
                    } else {
                        i14 = 6;
                    }
                }
                if (windowingMode != i14 || configurationWithOverrides.windowConfiguration.getStagePosition() != 64) {
                    i = R.layout.sec_decor_handle_layout;
                    relayoutParams4.mLayoutResId = i;
                    WindowDecoration.RelayoutParams relayoutParams52 = this.mRelayoutParams;
                    relayoutParams52.mCaptionHeightId = z13 ? (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE && this.mCaptionType == 0) ? android.R.dimen.tooltip_margin : android.R.dimen.timepicker_time_label_size : R.dimen.sec_decor_caption_height_fullscreen;
                    relayoutParams52.mCaptionWidthId = SemViewUtils.isTablet() ? R.dimen.sec_decor_handle_width_tablet : R.dimen.sec_decor_handle_width;
                }
                i = i15;
                relayoutParams4.mLayoutResId = i;
                WindowDecoration.RelayoutParams relayoutParams522 = this.mRelayoutParams;
                relayoutParams522.mCaptionHeightId = z13 ? (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE && this.mCaptionType == 0) ? android.R.dimen.tooltip_margin : android.R.dimen.timepicker_time_label_size : R.dimen.sec_decor_caption_height_fullscreen;
                relayoutParams522.mCaptionWidthId = SemViewUtils.isTablet() ? R.dimen.sec_decor_handle_width_tablet : R.dimen.sec_decor_handle_width;
            }
        }
        WindowDecoration.RelayoutParams relayoutParams6 = this.mRelayoutParams;
        relayoutParams6.mShadowRadiusId = i13;
        relayoutParams6.mApplyStartTransactionOnDraw = z;
        if (CoreRune.MW_CAPTION_SHELL) {
            boolean z16 = runningTaskInfo.isFullScreenCaptionState;
            if (z16 != this.mIsFullScreenCaptionState) {
                this.mIsFullScreenCaptionState = z16 && CaptionGlobalState.FULLSCREEN_HANDLER_ENABLED;
            }
            DisplayLayout displayLayout3 = this.mDisplayController.getDisplayLayout(runningTaskInfo.displayId);
            if (displayLayout3 != null) {
                if (windowingMode == 6) {
                    Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
                    int displayCutout2 = WindowInsets.Type.displayCutout();
                    if (!this.mIsSplitImmersiveEnabled) {
                        displayCutout2 |= WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars();
                    }
                    Insets calculateInsets = this.mInsetsState.calculateInsets(bounds, displayCutout2, false);
                    i3 = calculateInsets.top;
                    this.mRelayoutParams.mHorizontalInset = (calculateInsets.left - calculateInsets.right) / 2;
                } else if (windowingMode != 1 || !this.mIsFullScreenCaptionState) {
                    i3 = displayLayout3.stableInsets(false).top;
                }
                if (!CoreRune.MW_CAPTION_SHELL && !isDexMode) {
                    WindowConfiguration windowConfiguration = runningTaskInfo.getConfiguration().windowConfiguration;
                    z3 = true;
                    if (windowingMode != 1) {
                        if (windowingMode == 6 && !isNewDexMode) {
                            if (windowConfiguration.getStagePosition() == 64) {
                                i3 = this.mDecorWindowContext.getResources().getDimensionPixelSize(R.dimen.sec_decor_handle_margin_top);
                            }
                        } else if (!CoreRune.MT_NEW_DEX_PIP || !isNewDexMode || windowingMode != 2) {
                            if (z13 && CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE && this.mCaptionType == 0) {
                                i3 = -WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_handle_yoffset_freeform, this.mDecorWindowContext.getResources());
                            }
                        }
                    }
                    if (CoreRune.MW_CAPTION_SHELL) {
                        boolean z17 = this.mIsSplitImmersiveEnabled;
                        boolean z18 = MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED;
                        if (z17 != z18) {
                            this.mIsSplitImmersiveEnabled = z18;
                            Log.d("MultitaskingWindowDecoration", "relayout: need recreate, reason=split_immersive(" + this.mIsSplitImmersiveEnabled + ")");
                            z11 = z3;
                        } else {
                            z11 = false;
                        }
                        if (this.mTaskInfo.isCaptionHandlerHidden != runningTaskInfo.isCaptionHandlerHidden && runningTaskInfo.isRunning) {
                            Log.d("MultitaskingWindowDecoration", "relayout: need recreate, reason=caption_hidden(" + runningTaskInfo.isCaptionHandlerHidden + ")");
                            z11 = z3;
                        }
                        if (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE) {
                            WindowDecoration.RelayoutParams relayoutParams7 = this.mRelayoutParams;
                            int i16 = this.mCaptionType;
                            if (relayoutParams7.mCaptionType != i16) {
                                relayoutParams7.mCaptionType = i16;
                                z12 = true;
                            } else {
                                z12 = false;
                            }
                            z11 |= z12;
                            if (z12) {
                                KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m71m(new StringBuilder("relayout: need recreate, reason=caption_type_changed("), this.mCaptionType, ")", "MultitaskingWindowDecoration");
                            }
                        } else {
                            z12 = false;
                        }
                        z5 = z12;
                        if (this.mIsNightMode != MultiWindowUtils.isNightMode(runningTaskInfo)) {
                            z11 = true;
                        }
                        WindowDecoration.RelayoutParams relayoutParams8 = this.mRelayoutParams;
                        relayoutParams8.mCaptionX = 0;
                        relayoutParams8.mCaptionY = i3;
                        str = "MultitaskingWindowDecoration";
                        windowContainerTransaction = windowContainerTransaction2;
                        z4 = z15;
                        surfaceControl = surfaceControl2;
                        boolean z19 = z11;
                        windowDecorLinearLayout = windowDecorLinearLayout2;
                        relayout(relayoutParams8, transaction, transaction2, windowContainerTransaction2, windowDecorLinearLayout2, this.mResult, z19, z2);
                        z6 = false;
                    } else {
                        str = "MultitaskingWindowDecoration";
                        windowContainerTransaction = windowContainerTransaction2;
                        windowDecorLinearLayout = windowDecorLinearLayout2;
                        z4 = z15;
                        surfaceControl = surfaceControl2;
                        z5 = false;
                        relayout(this.mRelayoutParams, transaction, transaction2, windowContainerTransaction, windowDecorLinearLayout, this.mResult, false, false);
                        z6 = false;
                    }
                    displayLayout = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId);
                    if (CoreRune.MW_CAPTION_SHELL && displayLayout != null) {
                        displayLayout.getStableBounds(this.mLastStableBounds, z6);
                        displayCutout = displayLayout.mCutout;
                        if (displayCutout != null) {
                            Rect safeInsets = displayCutout.getSafeInsets();
                            Rect rect = this.mLastStableBounds;
                            rect.set(rect.left - safeInsets.left, rect.top, rect.right + safeInsets.right, rect.bottom);
                        }
                        if (this.mResult.mRootView != null) {
                            int dexTaskDockingState = runningTaskInfo.getConfiguration().windowConfiguration.getDexTaskDockingState();
                            boolean isDexTaskDocking = WindowConfiguration.isDexTaskDocking(dexTaskDockingState);
                            if (this.mIsDexDockingEnabled != isDexTaskDocking) {
                                this.mIsDexDockingEnabled = isDexTaskDocking;
                                Log.d(str, "updateDexDockingEnabled: " + isDexTaskDocking + ", " + this);
                                FreeformOutlineWrapper freeformOutlineWrapper3 = this.mFreeformOutlineWrapper;
                                if (freeformOutlineWrapper3 != null) {
                                    freeformOutlineWrapper3.updateOutlineView(true);
                                }
                            }
                            View findViewById2 = ((WindowDecorLinearLayout) this.mResult.mRootView).findViewById(R.id.caption);
                            DisplayCutout displayCutout3 = displayLayout.mCutout;
                            if (findViewById2 != null && displayCutout3 != null && !displayCutout3.isEmpty()) {
                                Rect boundingRectTop = displayCutout3.getBoundingRectTop();
                                Rect bounds2 = this.mTaskInfo.getConfiguration().windowConfiguration.getBounds();
                                Rect rect2 = new Rect();
                                findViewById2.getBoundsOnScreen(rect2);
                                rect2.offset(bounds2.left, bounds2.top - rect2.height());
                                boolean isLayoutRtl = findViewById2.isLayoutRtl();
                                WindowMenuCaptionPresenter windowMenuCaptionPresenter3 = this.mCaptionMenuPresenter;
                                if (windowMenuCaptionPresenter3 == null) {
                                    i10 = 2;
                                } else {
                                    ViewGroup[] viewGroupArr = windowMenuCaptionPresenter3.mButtonSet;
                                    ViewGroup viewGroup = viewGroupArr[z6 ? 1 : 0];
                                    if (viewGroup != null) {
                                        i7 = viewGroup.getWidth() + (z6 ? 1 : 0);
                                    } else {
                                        i7 = z6 ? 1 : 0;
                                    }
                                    ViewGroup viewGroup2 = viewGroupArr[1];
                                    if (viewGroup2 != null) {
                                        i7 += viewGroup2.getWidth();
                                    }
                                    WindowMenuItemView windowMenuItemView = this.mCaptionMenuPresenter.mBackButton;
                                    if (windowMenuItemView != null) {
                                        i8 = windowMenuItemView.getWidth();
                                    } else {
                                        i8 = z6 ? 1 : 0;
                                    }
                                    if (dexTaskDockingState == 1) {
                                        z6 = true;
                                    }
                                    i9 = 2;
                                    boolean z20 = dexTaskDockingState == 2;
                                    if ((!isLayoutRtl || !z20) && (isLayoutRtl || !z6)) {
                                        if (!(isLayoutRtl && z6) && (isLayoutRtl || !z20)) {
                                            i10 = 2;
                                        } else {
                                            i7 = i8;
                                        }
                                    }
                                    findViewById = ((WindowDecorLinearLayout) this.mResult.mRootView).findViewById(R.id.caption);
                                    if (findViewById != null && i7 != 0) {
                                        boolean z21 = dexTaskDockingState != 1;
                                        boolean z22 = dexTaskDockingState != i9;
                                        if ((!isLayoutRtl && z22) || (!isLayoutRtl && z21)) {
                                            view2 = findViewById.findViewById(R.id.primary_button_set);
                                        } else if ((isLayoutRtl && z21) || (!isLayoutRtl && z22)) {
                                            view2 = findViewById.findViewById(R.id.back_window);
                                        }
                                        if (view2 != null) {
                                            if (dexTaskDockingState == 1) {
                                                if (this.mLastDockingState == i9) {
                                                    resetDockingMargins();
                                                }
                                                int i17 = rect2.right;
                                                Rect rect3 = new Rect(i17 - i7, rect2.top, i17, rect2.bottom);
                                                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
                                                int i18 = boundingRectTop.left;
                                                int i19 = rect3.right;
                                                if (i18 < i19 && rect3.left < boundingRectTop.right) {
                                                    int i20 = i19 - i18;
                                                    if (marginLayoutParams.rightMargin != i20) {
                                                        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, i20, marginLayoutParams.bottomMargin);
                                                        view2.setLayoutParams(marginLayoutParams);
                                                    }
                                                } else if (marginLayoutParams.rightMargin != 0) {
                                                    marginLayoutParams.setMargins(0, marginLayoutParams.topMargin, 0, marginLayoutParams.bottomMargin);
                                                    view2.setLayoutParams(marginLayoutParams);
                                                }
                                            } else if (dexTaskDockingState == i9) {
                                                if (this.mLastDockingState == 1) {
                                                    resetDockingMargins();
                                                }
                                                int i21 = rect2.left;
                                                Rect rect4 = new Rect(i21, rect2.top, view2.getWidth() + i21, rect2.bottom);
                                                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
                                                if (boundingRectTop.left < rect4.right && (i11 = rect4.left) < (i12 = boundingRectTop.right)) {
                                                    int i22 = i12 - i11;
                                                    if (marginLayoutParams2.leftMargin != i22) {
                                                        marginLayoutParams2.setMargins(i22, marginLayoutParams2.topMargin, marginLayoutParams2.rightMargin, marginLayoutParams2.bottomMargin);
                                                        view2.setLayoutParams(marginLayoutParams2);
                                                    }
                                                } else if (marginLayoutParams2.leftMargin != 0) {
                                                    marginLayoutParams2.setMargins(0, marginLayoutParams2.topMargin, 0, marginLayoutParams2.bottomMargin);
                                                    view2.setLayoutParams(marginLayoutParams2);
                                                }
                                            } else {
                                                resetDockingMargins();
                                            }
                                            this.mLastDockingState = dexTaskDockingState;
                                        }
                                    }
                                    view2 = null;
                                    if (view2 != null) {
                                    }
                                }
                                i7 = 0;
                                i9 = i10;
                                findViewById = ((WindowDecorLinearLayout) this.mResult.mRootView).findViewById(R.id.caption);
                                if (findViewById != null) {
                                    if (dexTaskDockingState != 1) {
                                    }
                                    if (dexTaskDockingState != i9) {
                                    }
                                    if (!isLayoutRtl) {
                                    }
                                    if (isLayoutRtl) {
                                        view2 = findViewById.findViewById(R.id.back_window);
                                        if (view2 != null) {
                                        }
                                    }
                                    view2 = findViewById.findViewById(R.id.back_window);
                                    if (view2 != null) {
                                    }
                                }
                                view2 = null;
                                if (view2 != null) {
                                }
                            }
                        }
                        if (z13 && this.mFreeformOutlineWrapper == null) {
                            this.mFreeformOutlineWrapper = new FreeformOutlineWrapper(this);
                        }
                        setupCaptionColor();
                        freeformOutlineWrapper2 = this.mFreeformOutlineWrapper;
                        if (freeformOutlineWrapper2 != null) {
                            if (!this.mTaskInfo.isMinimized) {
                                OutlineView outlineView = freeformOutlineWrapper2.getOutlineView();
                                if (outlineView != null) {
                                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) outlineView.getLayoutParams();
                                    Rect freeformOutlineFrame = freeformOutlineWrapper2.getFreeformOutlineFrame();
                                    layoutParams.width = freeformOutlineFrame.width();
                                    layoutParams.height = freeformOutlineFrame.height();
                                    freeformOutlineWrapper2.mFreeformOutline.mWindowViewHost.relayout(layoutParams, true);
                                    WindowlessWindowManager windowlessWM = freeformOutlineWrapper2.mFreeformOutline.mWindowViewHost.getWindowlessWM();
                                    MultitaskingWindowDecoration multitaskingWindowDecoration = MultitaskingWindowDecoration.this;
                                    windowlessWM.setConfiguration(multitaskingWindowDecoration.getConfigurationWithOverrides(multitaskingWindowDecoration.mTaskInfo));
                                }
                            } else if (CoreRune.SAFE_DEBUG) {
                                Log.w(str, "task is minimized");
                            }
                        }
                        windowMenuCaptionPresenter2 = this.mCaptionMenuPresenter;
                        if (windowMenuCaptionPresenter2 != null) {
                            ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
                            boolean isStashed = this.mFreeformStashState.isStashed();
                            if (runningTaskInfo2 != windowMenuCaptionPresenter2.mTaskInfo) {
                                windowMenuCaptionPresenter2.mTaskInfo = runningTaskInfo2;
                                windowMenuCaptionPresenter2.mRootView.dispatchConfigurationChanged(runningTaskInfo2.configuration);
                                if (!isStashed) {
                                    windowMenuCaptionPresenter2.setTaskFocusState(windowMenuCaptionPresenter2.mTaskInfo.isFocused);
                                }
                                boolean isNightMode = MultiWindowUtils.isNightMode(runningTaskInfo2);
                                if (windowMenuCaptionPresenter2.mIsNightMode != isNightMode) {
                                    windowMenuCaptionPresenter2.mIsNightMode = isNightMode;
                                    windowMenuCaptionPresenter2.updateButtonColor();
                                }
                                if (CoreRune.MW_CAPTION_SHELL_OVERFLOW_MENU && windowMenuCaptionPresenter2.mIsDexEnabled && windowMenuCaptionPresenter2.mTaskInfo.getWindowingMode() == 5) {
                                    windowMenuCaptionPresenter2.adjustOverflowButton(runningTaskInfo2.configuration.windowConfiguration.getBounds().width());
                                }
                            }
                            WindowMenuCaptionPresenter windowMenuCaptionPresenter4 = this.mCaptionMenuPresenter;
                            WindowMenuItemView windowMenuItemView2 = (WindowMenuItemView) windowMenuCaptionPresenter4.mButtons.get(R.id.split_window);
                            if (windowMenuItemView2 != null) {
                                windowMenuCaptionPresenter4.setSplitButtonDrawable(windowMenuItemView2, MultiWindowManager.getInstance().getMultiSplitFlags());
                            }
                        }
                        windowMenuPopupPresenter = this.mPopupMenuPresenter;
                        if (windowMenuPopupPresenter != null) {
                            WindowMenuItemView windowMenuItemView3 = (WindowMenuItemView) windowMenuPopupPresenter.mButtons.get(R.id.split_window);
                            if (windowMenuItemView3 != null) {
                                windowMenuPopupPresenter.setSplitButtonDrawable(windowMenuItemView3, MultiWindowManager.getInstance().getMultiSplitFlags());
                            }
                            if (this.mPopupMenuPresenter.needRecreateHandleMenu(this.mTaskInfo)) {
                                releaseHandleMenu();
                            } else if (z13) {
                                this.mPopupMenuPresenter.createPopupAnimation(this.mTaskInfo, false);
                            }
                        }
                    }
                    if (z13 && this.mFreeformStashState.mLastFreeformBoundsBeforeStash.isEmpty()) {
                        this.mFreeformStashState.mLastFreeformBoundsBeforeStash.set(this.mTaskInfo.configuration.windowConfiguration.getBounds());
                    }
                    this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                    view = this.mResult.mRootView;
                    if (view == null) {
                        return;
                    }
                    if (windowDecorLinearLayout != view) {
                        View findViewById3 = ((WindowDecorLinearLayout) view).findViewById(R.id.caption);
                        findViewById3.setOnTouchListener(this.mOnCaptionTouchListener);
                        if (CoreRune.MW_CAPTION_SHELL) {
                            if (this.mCaptionType == 1) {
                                findViewById3.setContentDescription(this.mContext.getString(android.R.string.android_upgrading_complete, getAppName()));
                            }
                            int windowingMode2 = this.mTaskInfo.getWindowingMode();
                            boolean z23 = windowingMode2 == 5;
                            if (z23 && (freeformOutlineWrapper = this.mFreeformOutlineWrapper) != null) {
                                SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
                                WindowDecoration.AdditionalWindow additionalWindow = freeformOutlineWrapper.mFreeformOutline;
                                MultitaskingWindowDecoration multitaskingWindowDecoration2 = MultitaskingWindowDecoration.this;
                                if (additionalWindow == null) {
                                    Rect freeformOutlineFrame2 = freeformOutlineWrapper.getFreeformOutlineFrame();
                                    freeformOutlineWrapper.mFreeformOutline = MultitaskingWindowDecoration.this.addWindow(freeformOutlineWrapper.mLayoutId, freeformOutlineWrapper.mNamePrefix, transaction3, freeformOutlineFrame2.left, freeformOutlineFrame2.top - multitaskingWindowDecoration2.getCaptionVisibleHeight(), freeformOutlineFrame2.width(), freeformOutlineFrame2.height(), 2038, 24, freeformOutlineWrapper.calculateElevation(), false);
                                }
                                transaction3.setRelativeLayer(freeformOutlineWrapper.mFreeformOutline.mWindowSurface, multitaskingWindowDecoration2.mTaskSurface, -1);
                                multitaskingWindowDecoration2.mSyncQueue.runInSync(new MultitaskingWindowDecoration$$ExternalSyntheticLambda1(4, transaction3));
                                freeformOutlineWrapper.updateOutlineView(false);
                            }
                            if (!this.mIsDexMode && ((!z23 && !this.mIsNewDexMode) || this.mCaptionType != 1)) {
                                View findViewById4 = ((WindowDecorLinearLayout) this.mResult.mRootView).findViewById(R.id.caption_handle);
                                if (findViewById4 != 0) {
                                    ((TaskFocusStateConsumer) findViewById4).setTaskFocusState(WindowDecoration.hasBarFocus(this.mTaskInfo));
                                    findViewById4.setOnTouchListener(this.mOnCaptionTouchListener);
                                    findViewById4.setOnClickListener(this.mOnCaptionTouchListener);
                                    findViewById4.setContentDescription(((Object) getAppName()) + " " + this.mContext.getString(R.string.sec_decor_handle_text));
                                }
                                if (CoreRune.MW_CAPTION_SHELL_POPUP_HELP && windowingMode2 == 5 && (multiTaskingHelpController = this.mMultiTaskingHelpController) != null) {
                                    int i23 = multiTaskingHelpController.mWindowingMode;
                                    if (i23 == 6) {
                                        z10 = MultiTaskingHelpController.SPLIT_HANDLER_HELP_POPUP_ENABLED;
                                    } else {
                                        z10 = i23 == 5 ? MultiTaskingHelpController.FREEFORM_HANDLER_HELP_POPUP_ENABLED : false;
                                    }
                                    if (z10 && (handleView3 = getHandleView()) != null && handleView3.getVisibility() == 0) {
                                        showHandleMenu();
                                    }
                                }
                                i4 = 8;
                            } else {
                                WindowMenuCaptionPresenter windowMenuCaptionPresenter5 = new WindowMenuCaptionPresenter(this.mDecorWindowContext, this.mTaskInfo, findViewById3, this.mOnCaptionTouchListener, this.mAlpha, this.mIsAdditionalDisplayAdded);
                                this.mCaptionMenuPresenter = windowMenuCaptionPresenter5;
                                windowMenuCaptionPresenter5.mShowPrimaryButtonSet = true;
                                WindowMenuItemView windowMenuItemView4 = windowMenuCaptionPresenter5.mMoreButton;
                                if (windowMenuItemView4 != null) {
                                    windowMenuItemView4.mShowIconBackground = false;
                                }
                                WindowMenuAnimationView windowMenuAnimationView = windowMenuCaptionPresenter5.mUnpinButton;
                                if (windowMenuAnimationView != null) {
                                    windowMenuAnimationView.setProgressInternal(0.0f, true);
                                }
                                boolean z24 = windowMenuCaptionPresenter5.mIsDexEnabled;
                                ViewGroup[] viewGroupArr2 = windowMenuCaptionPresenter5.mButtonSet;
                                if (z24) {
                                    r1 = 0;
                                    z9 = true;
                                    i6 = 8;
                                } else {
                                    View findViewById5 = windowMenuCaptionPresenter5.mRootView.findViewById(R.id.button_container);
                                    r1 = 0;
                                    if (findViewById5 != null) {
                                        findViewById5.setVisibility(0);
                                    }
                                    viewGroupArr2[0].setVisibility(0);
                                    z9 = true;
                                    viewGroupArr2[1].setVisibility(8);
                                    i6 = 8;
                                }
                                CaptionAnimationUtils.createViewAlphaAnimator(viewGroupArr2[r1], z9, 350L, InterpolatorUtils.ONE_EASING).start();
                                this.mIsHandleVisibleState = r1;
                                if (this.mIsDexEnabled) {
                                    if (this.mTaskInfo.getConfiguration().windowConfiguration.getFreeformTaskPinningState() == 2) {
                                        this.mCaptionMenuPresenter.changePinButtonIconBackground(z9);
                                    } else if (!this.mTaskOrganizer.isPinStateChangeable(this.mTaskInfo.taskId)) {
                                        this.mCaptionMenuPresenter.changePinButtonDisable(z9);
                                    }
                                    if (CoreRune.MW_CAPTION_SHELL_OVERFLOW_MENU && this.mIsDexEnabled && this.mTaskInfo.getWindowingMode() == 5) {
                                        this.mCaptionMenuPresenter.adjustOverflowButton(this.mTaskInfo.configuration.windowConfiguration.getBounds().width());
                                    }
                                }
                                i4 = i6;
                            }
                        } else {
                            i4 = 8;
                            findViewById3.findViewById(R.id.close_window).setOnClickListener(this.mOnCaptionButtonClickListener);
                            findViewById3.findViewById(R.id.minimize_window).setOnClickListener(this.mOnCaptionButtonClickListener);
                            findViewById3.findViewById(R.id.maximize_window).setOnClickListener(this.mOnCaptionButtonClickListener);
                        }
                        if (CoreRune.MW_CAPTION_SHELL_KEEP_SCREEN_ON && this.mHandleAutoHide != null && windowingMode != 5 && (handleView2 = getHandleView()) != null) {
                            this.mHandleAutoHide.updateHandleAnimation(handleView2);
                            HandleAutoHide handleAutoHide = this.mHandleAutoHide;
                            boolean z25 = handleAutoHide.mEnabled;
                            if (z25 && z25 && handleAutoHide.mIsShowing) {
                                handleAutoHide.mHandler.postDelayed(handleAutoHide.mHideRunnable, 2000L);
                            }
                        }
                    } else {
                        i4 = 8;
                    }
                    if (CoreRune.MD_DEX_COMPAT_CAPTION_SHELL && (windowMenuCaptionPresenter = this.mCaptionMenuPresenter) != null && (rotationButton = windowMenuCaptionPresenter.getRotationButton()) != null) {
                        rotationButton.setVisibility(windowMenuCaptionPresenter.mTaskInfo.isRotationButtonVisible ? 0 : i4);
                    }
                    if (CoreRune.MW_CAPTION_SHELL_OPACITY) {
                        float freeformTaskOpacity = this.mTaskOrganizer.getFreeformTaskOpacity(this.mTaskInfo.taskId);
                        if (windowingMode != 5) {
                            setDecorationOpacity(1.0f);
                            this.mAlpha = 1.0f;
                        } else if (freeformTaskOpacity != this.mAlpha) {
                            setDecorationOpacity(freeformTaskOpacity);
                        }
                    }
                    if (CoreRune.MW_CAPTION_SHELL_OVERFLOW_MENU) {
                        closeMoreMenu();
                    }
                    if (this.mFreeformStashState.isStashed()) {
                        if (surfaceControl != this.mDecorationContainerSurface || this.mFreeformStashDimInputListener == null) {
                            FreeformDimInputListener freeformDimInputListener = this.mFreeformStashDimInputListener;
                            if (freeformDimInputListener != null) {
                                freeformDimInputListener.close();
                                this.mFreeformStashDimInputListener = null;
                            }
                            if (this.mDragResizeInputSurface != null) {
                                this.mFreeformStashDimInputListener = new FreeformDimInputListener(this.mContext, this.mHandler, this.mChoreographer, this.mDisplay.getDisplayId(), this.mDragResizeInputSurface, this.mTaskPositioner, runningTaskInfo.taskId);
                                this.mFreeformStashState.createStashDimOverlay(this.mTaskSurface, this.mContext, this.mTaskInfo, getCaptionVisibleHeight());
                                this.mFreeformStashState.setDimOverlayAlpha(MultiWindowUtils.isNightMode(this.mTaskInfo) ? 0.4f : 0.2f);
                            } else {
                                Log.w(str, "mDragResizeInputSurface has already been removed.");
                            }
                        }
                        Rect bounds3 = runningTaskInfo.configuration.windowConfiguration.getBounds();
                        float height = this.mTaskPositioner.mTaskMotionController.mScaledFreeformHeight / bounds3.height();
                        FreeformStashState freeformStashState = this.mFreeformStashState;
                        if (height != freeformStashState.mScale || z5) {
                            freeformStashState.updateDimBounds(getFreeformThickness$1(), getCaptionVisibleHeight(), bounds3);
                            this.mFreeformStashState.mScale = height;
                        }
                        transaction2.setPosition(this.mTaskSurface, bounds3.left + this.mFreeformStashState.getStashScaleOffsetX(bounds3.width()), bounds3.top).setMatrix(this.mTaskSurface, height, 0.0f, 0.0f, height);
                    }
                    FreeformDimInputListener freeformDimInputListener2 = this.mFreeformStashDimInputListener;
                    if (freeformDimInputListener2 != null && (!runningTaskInfo.isForceHidden) != freeformDimInputListener2.mTouchableState) {
                        freeformDimInputListener2.mTouchableState = z8;
                        if (!z8) {
                            i4 = 24;
                        }
                        try {
                            freeformDimInputListener2.mWindowSession.updateInputChannel(freeformDimInputListener2.mInputChannel.getToken(), freeformDimInputListener2.mDisplayId, freeformDimInputListener2.mDecorationSurface, i4, QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT, 0, (Region) null);
                        } catch (RemoteException e) {
                            e.rethrowFromSystemServer();
                        }
                    }
                    if (z4 && !this.mFreeformStashState.isStashed()) {
                        FreeformDimInputListener freeformDimInputListener3 = this.mFreeformStashDimInputListener;
                        if (freeformDimInputListener3 != null) {
                            freeformDimInputListener3.close();
                            this.mFreeformStashDimInputListener = null;
                        }
                        if (surfaceControl != this.mDecorationContainerSurface || this.mDragResizeListener == null) {
                            DragResizeInputListener dragResizeInputListener = this.mDragResizeListener;
                            if (dragResizeInputListener != null) {
                                dragResizeInputListener.close();
                                this.mDragResizeListener = null;
                            }
                            DragResizeInputListener dragResizeInputListener2 = new DragResizeInputListener(this.mContext, this.mHandler, this.mChoreographer, this.mDisplay.getDisplayId(), CoreRune.MW_CAPTION_SHELL ? this.mDragResizeInputSurface : this.mDecorationContainerSurface, this.mDragPositioningCallback, this.mTaskOrganizer);
                            this.mDragResizeListener = dragResizeInputListener2;
                            dragResizeInputListener2.mMultitaskingWindowDecoration = this;
                        }
                        Rect bounds4 = this.mTaskInfo.configuration.windowConfiguration.getBounds();
                        Rect visibleTaskBounds = getVisibleTaskBounds();
                        Rect bounds5 = this.mTaskInfo.getConfiguration().windowConfiguration.getBounds();
                        int loadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_handle_width, this.mDecorWindowContext.getResources());
                        if (visibleTaskBounds.width() < bounds5.width() - ((bounds5.width() - loadDimensionPixelSize) / 2)) {
                            if (visibleTaskBounds.width() < loadDimensionPixelSize && (handleView = getHandleView()) != null) {
                                handleView.setPadding(0, handleView.getPaddingTop(), 0, handleView.getPaddingBottom());
                            }
                            z7 = true;
                        } else {
                            z7 = false;
                        }
                        if (z7) {
                            FreeformStashState freeformStashState2 = this.mFreeformStashState;
                            if (!(freeformStashState2.mAnimType != -1 && freeformStashState2.mAnimating)) {
                                boolean z26 = getVisibleTaskBounds().left > bounds4.left;
                                if (getHandleView() == null || this.mIsRemoving || (displayLayout2 = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId)) == null) {
                                    i5 = 0;
                                } else {
                                    Rect rect5 = new Rect();
                                    i5 = 0;
                                    rect5.set(0, 0, displayLayout2.mWidth, displayLayout2.mHeight);
                                    rect5.intersect(bounds4);
                                    int handleViewWidth = getHandleViewWidth();
                                    if (rect5.width() > handleViewWidth) {
                                        width = (rect5.width() - handleViewWidth) / 2;
                                        if (z26) {
                                            width += Math.abs(bounds4.left);
                                        }
                                    } else {
                                        width = z26 ? bounds4.width() - handleViewWidth : 0;
                                    }
                                    transaction.setPosition(this.mCaptionContainerSurface, width, this.mRelayoutParams.mCaptionY);
                                    if (this.mRelayoutParams.mApplyStartTransactionOnDraw && (surfaceControlViewHost = this.mViewHost) != null) {
                                        surfaceControlViewHost.getRootSurfaceControl().applyTransactionOnDraw(transaction);
                                    }
                                }
                                int scaledTouchSlop = ViewConfiguration.get(((WindowDecorLinearLayout) this.mResult.mRootView).getContext()).getScaledTouchSlop();
                                if (CoreRune.MW_CAPTION_SHELL || this.mDragDetector != null) {
                                    this.mDragDetector.mTouchSlop = scaledTouchSlop;
                                }
                                DisplayMetrics displayMetrics = ((WindowDecorLinearLayout) this.mResult.mRootView).getResources().getDisplayMetrics();
                                int dipToPixel = MultiWindowUtils.dipToPixel(10, displayMetrics);
                                int i24 = (this.mTaskInfo.displayId == 2 || !this.mIsDexEnabled) ? 48 : dipToPixel;
                                int i25 = i24 * 2;
                                int dipToPixel2 = this.mIsDexEnabled ? MultiWindowUtils.dipToPixel(4, displayMetrics) : i5;
                                int dimensionPixelSize = (CoreRune.MW_CAPTION_SHELL || this.mCaptionType != 0) ? i5 : ((WindowDecorLinearLayout) this.mResult.mRootView).getResources().getDimensionPixelSize(this.mRelayoutParams.mCaptionWidthId);
                                DragResizeInputListener dragResizeInputListener3 = this.mDragResizeListener;
                                if (CoreRune.MW_CAPTION_SHELL) {
                                    i5 = getCaptionVisibleHeight();
                                }
                                WindowDecoration.RelayoutResult relayoutResult = this.mResult;
                                dragResizeInputListener3.setGeometry(i5, dimensionPixelSize, dipToPixel, dipToPixel2, relayoutResult.mWidth, relayoutResult.mHeight, i24, i25, scaledTouchSlop, !runningTaskInfo.isForceHidden);
                                return;
                            }
                        }
                        i5 = 0;
                        HandleView handleView4 = getHandleView();
                        if (handleView4 != null) {
                            handleView4.setHandleViewPadding();
                        }
                        int scaledTouchSlop2 = ViewConfiguration.get(((WindowDecorLinearLayout) this.mResult.mRootView).getContext()).getScaledTouchSlop();
                        if (CoreRune.MW_CAPTION_SHELL) {
                        }
                        this.mDragDetector.mTouchSlop = scaledTouchSlop2;
                        DisplayMetrics displayMetrics2 = ((WindowDecorLinearLayout) this.mResult.mRootView).getResources().getDisplayMetrics();
                        int dipToPixel3 = MultiWindowUtils.dipToPixel(10, displayMetrics2);
                        if (this.mTaskInfo.displayId == 2) {
                        }
                        int i252 = i24 * 2;
                        if (this.mIsDexEnabled) {
                        }
                        if (CoreRune.MW_CAPTION_SHELL) {
                        }
                        DragResizeInputListener dragResizeInputListener32 = this.mDragResizeListener;
                        if (CoreRune.MW_CAPTION_SHELL) {
                        }
                        WindowDecoration.RelayoutResult relayoutResult2 = this.mResult;
                        dragResizeInputListener32.setGeometry(i5, dimensionPixelSize, dipToPixel3, dipToPixel2, relayoutResult2.mWidth, relayoutResult2.mHeight, i24, i252, scaledTouchSlop2, !runningTaskInfo.isForceHidden);
                        return;
                    }
                    DragResizeInputListener dragResizeInputListener4 = this.mDragResizeListener;
                    if (dragResizeInputListener4 == null) {
                        return;
                    }
                    dragResizeInputListener4.close();
                    this.mDragResizeListener = null;
                    return;
                }
                z3 = true;
                i3 = 0;
                if (CoreRune.MW_CAPTION_SHELL) {
                }
                displayLayout = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId);
                if (CoreRune.MW_CAPTION_SHELL) {
                    displayLayout.getStableBounds(this.mLastStableBounds, z6);
                    displayCutout = displayLayout.mCutout;
                    if (displayCutout != null) {
                    }
                    if (this.mResult.mRootView != null) {
                    }
                    if (z13) {
                        this.mFreeformOutlineWrapper = new FreeformOutlineWrapper(this);
                    }
                    setupCaptionColor();
                    freeformOutlineWrapper2 = this.mFreeformOutlineWrapper;
                    if (freeformOutlineWrapper2 != null) {
                    }
                    windowMenuCaptionPresenter2 = this.mCaptionMenuPresenter;
                    if (windowMenuCaptionPresenter2 != null) {
                    }
                    windowMenuPopupPresenter = this.mPopupMenuPresenter;
                    if (windowMenuPopupPresenter != null) {
                    }
                }
                if (z13) {
                    this.mFreeformStashState.mLastFreeformBoundsBeforeStash.set(this.mTaskInfo.configuration.windowConfiguration.getBounds());
                }
                this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                view = this.mResult.mRootView;
                if (view == null) {
                }
            }
        }
        i3 = 0;
        if (!CoreRune.MW_CAPTION_SHELL) {
        }
        z3 = true;
        i3 = 0;
        if (CoreRune.MW_CAPTION_SHELL) {
        }
        displayLayout = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId);
        if (CoreRune.MW_CAPTION_SHELL) {
        }
        if (z13) {
        }
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        view = this.mResult.mRootView;
        if (view == null) {
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecoration
    public final MultitaskingWindowDecoration asMultitaskingWindowDecoration() {
        return this;
    }
}
