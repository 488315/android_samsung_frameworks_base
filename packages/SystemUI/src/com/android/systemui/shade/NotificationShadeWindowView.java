package com.android.systemui.shade;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.os.VibrationEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.InputDevice;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.FrameLayout;
import com.android.internal.view.FloatingActionMode;
import com.android.internal.widget.floatingtoolbar.FloatingToolbar;
import com.android.keyguard.KeyguardPresentationDisabler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.LsRune;
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;
import com.android.systemui.keyguard.MigrateClocksToBlueprint;
import com.android.systemui.keyguard.domain.interactor.KeyguardKeyEventInteractor;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.shade.NotificationShadeWindowViewController;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.shade.domain.interactor.SecNotificationShadeWindowStateInteractor;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import java.util.Arrays;
import java.util.function.IntConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class NotificationShadeWindowView extends WindowRootView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mFakeWindow;
    public ActionMode mFloatingActionMode;
    public View mFloatingActionModeOriginatingView;
    public FloatingToolbar mFloatingToolbar;
    public NotificationShadeWindowView$$ExternalSyntheticLambda0 mFloatingToolbarPreDrawListener;
    public NotificationShadeWindowViewController.AnonymousClass1 mInteractionEventHandler;
    public final SecNotificationShadeWindowStateInteractor mSecNotificationShadeWindowStateInteractor;
    public IntConsumer mVisibilityChangedListener;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ActionModeCallback2Wrapper extends ActionMode.Callback2 {
        public final ActionMode.Callback mWrapped;

        public ActionModeCallback2Wrapper(ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        @Override // android.view.ActionMode.Callback
        public final boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public final boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onCreateActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public final void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            NotificationShadeWindowView notificationShadeWindowView = NotificationShadeWindowView.this;
            if (actionMode == notificationShadeWindowView.mFloatingActionMode) {
                notificationShadeWindowView.cleanupFloatingActionModeViews();
                NotificationShadeWindowView.this.mFloatingActionMode = null;
            }
            NotificationShadeWindowView.this.requestFitSystemWindows();
        }

        @Override // android.view.ActionMode.Callback2
        public final void onGetContentRect(ActionMode actionMode, View view, Rect rect) {
            ActionMode.Callback callback = this.mWrapped;
            if (callback instanceof ActionMode.Callback2) {
                ((ActionMode.Callback2) callback).onGetContentRect(actionMode, view, rect);
            } else {
                super.onGetContentRect(actionMode, view, rect);
            }
        }

        @Override // android.view.ActionMode.Callback
        public final boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            NotificationShadeWindowView.this.requestFitSystemWindows();
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.shade.NotificationShadeWindowView$1] */
    public NotificationShadeWindowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFakeWindow = new Window(((FrameLayout) this).mContext) { // from class: com.android.systemui.shade.NotificationShadeWindowView.1
            @Override // android.view.Window
            public final View getCurrentFocus() {
                return null;
            }

            @Override // android.view.Window
            public final View getDecorView() {
                return NotificationShadeWindowView.this;
            }

            @Override // android.view.Window
            public final WindowInsetsController getInsetsController() {
                return null;
            }

            @Override // android.view.Window
            public final LayoutInflater getLayoutInflater() {
                return null;
            }

            @Override // android.view.Window
            public final int getNavigationBarColor() {
                return 0;
            }

            @Override // android.view.Window
            public final int getStatusBarColor() {
                return 0;
            }

            @Override // android.view.Window
            public final int getVolumeControlStream() {
                return 0;
            }

            @Override // android.view.Window
            public final boolean isFloating() {
                return false;
            }

            @Override // android.view.Window
            public final boolean isShortcutKey(int i, KeyEvent keyEvent) {
                return false;
            }

            @Override // android.view.Window
            public final View peekDecorView() {
                return null;
            }

            @Override // android.view.Window
            public final boolean performContextMenuIdentifierAction(int i, int i2) {
                return false;
            }

            @Override // android.view.Window
            public final boolean performPanelIdentifierAction(int i, int i2, int i3) {
                return false;
            }

            @Override // android.view.Window
            public final boolean performPanelShortcut(int i, int i2, KeyEvent keyEvent, int i3) {
                return false;
            }

            @Override // android.view.Window
            public final Bundle saveHierarchyState() {
                return null;
            }

            @Override // android.view.Window
            public final void setContentView(int i) {
            }

            @Override // android.view.Window
            public final boolean superDispatchGenericMotionEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.Window
            public final boolean superDispatchKeyEvent(KeyEvent keyEvent) {
                return false;
            }

            @Override // android.view.Window
            public final boolean superDispatchKeyShortcutEvent(KeyEvent keyEvent) {
                return false;
            }

            @Override // android.view.Window
            public final boolean superDispatchTouchEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.Window
            public final boolean superDispatchTrackballEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.Window
            public final void setContentView(View view) {
            }

            @Override // android.view.Window
            public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
            }

            public final void alwaysReadCloseOnTouchAttr() {
            }

            public final void clearContentView() {
            }

            @Override // android.view.Window
            public final void closeAllPanels() {
            }

            @Override // android.view.Window
            public final void onActive() {
            }

            public final void onMultiWindowModeChanged() {
            }

            @Override // android.view.Window
            public final void closePanel(int i) {
            }

            @Override // android.view.Window
            public final void invalidatePanelMenu(int i) {
            }

            @Override // android.view.Window
            public final void onConfigurationChanged(Configuration configuration) {
            }

            public final void onPictureInPictureModeChanged(boolean z) {
            }

            @Override // android.view.Window
            public final void restoreHierarchyState(Bundle bundle) {
            }

            @Override // android.view.Window
            public final void setBackgroundDrawable(Drawable drawable) {
            }

            @Override // android.view.Window
            public final void setDecorCaptionShade(int i) {
            }

            @Override // android.view.Window
            public final void setNavigationBarColor(int i) {
            }

            @Override // android.view.Window
            public final void setResizingCaptionDrawable(Drawable drawable) {
            }

            @Override // android.view.Window
            public final void setStatusBarColor(int i) {
            }

            @Override // android.view.Window
            public final void setTitle(CharSequence charSequence) {
            }

            @Override // android.view.Window
            public final void setTitleColor(int i) {
            }

            @Override // android.view.Window
            public final void setVolumeControlStream(int i) {
            }

            @Override // android.view.Window
            public final void takeInputQueue(InputQueue.Callback callback) {
            }

            @Override // android.view.Window
            public final void takeKeyEvents(boolean z) {
            }

            @Override // android.view.Window
            public final void takeSurface(SurfaceHolder.Callback2 callback2) {
            }

            @Override // android.view.Window
            public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
            }

            @Override // android.view.Window
            public final void openPanel(int i, KeyEvent keyEvent) {
            }

            @Override // android.view.Window
            public final void setChildDrawable(int i, Drawable drawable) {
            }

            @Override // android.view.Window
            public final void setChildInt(int i, int i2) {
            }

            @Override // android.view.Window
            public final void setFeatureDrawable(int i, Drawable drawable) {
            }

            @Override // android.view.Window
            public final void setFeatureDrawableAlpha(int i, int i2) {
            }

            @Override // android.view.Window
            public final void setFeatureDrawableResource(int i, int i2) {
            }

            @Override // android.view.Window
            public final void setFeatureDrawableUri(int i, Uri uri) {
            }

            @Override // android.view.Window
            public final void setFeatureInt(int i, int i2) {
            }

            @Override // android.view.Window
            public final void togglePanel(int i, KeyEvent keyEvent) {
            }
        };
        setMotionEventSplittingEnabled(false);
        this.mSecNotificationShadeWindowStateInteractor = (SecNotificationShadeWindowStateInteractor) Dependency.sDependency.getDependencyInner(SecNotificationShadeWindowStateInteractor.class);
    }

    public final void cleanupFloatingActionModeViews() {
        FloatingToolbar floatingToolbar = this.mFloatingToolbar;
        if (floatingToolbar != null) {
            floatingToolbar.dismiss();
            this.mFloatingToolbar = null;
        }
        View view = this.mFloatingActionModeOriginatingView;
        if (view != null) {
            if (this.mFloatingToolbarPreDrawListener != null) {
                view.getViewTreeObserver().removeOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
                this.mFloatingToolbarPreDrawListener = null;
            }
            this.mFloatingActionModeOriginatingView = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        NotificationShadeWindowViewController.AnonymousClass1 anonymousClass1 = this.mInteractionEventHandler;
        anonymousClass1.getClass();
        int action = motionEvent.getAction();
        NotificationShadeWindowViewController notificationShadeWindowViewController = NotificationShadeWindowViewController.this;
        if (((StatusBarStateControllerImpl) notificationShadeWindowViewController.mStatusBarStateController).mState == 1 && action == 7) {
            ((CentralSurfacesImpl) notificationShadeWindowViewController.mService).userActivity();
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int[] iArr;
        int i;
        int i2;
        NotificationShadeWindowViewController.this.mFalsingCollector.onKeyEvent(keyEvent);
        NotificationShadeWindowViewController notificationShadeWindowViewController = NotificationShadeWindowViewController.this;
        KeyguardPresentationDisabler keyguardPresentationDisabler = notificationShadeWindowViewController.mPresentationDisabler;
        if (!keyguardPresentationDisabler.mKeyEnabled) {
            long currentTimeMillis = System.currentTimeMillis();
            int[] iArr2 = KeyguardPresentationDisabler.KEYS;
            int i3 = 0;
            boolean z = false;
            while (true) {
                iArr = keyguardPresentationDisabler.mDownCount;
                if (i3 >= 2) {
                    break;
                }
                int i4 = iArr2[i3];
                if (i4 == keyEvent.getKeyCode() && keyEvent.getAction() == 0) {
                    i = i3;
                    long j = keyguardPresentationDisabler.mLastDownTime;
                    if ((j == 0 || currentTimeMillis - j <= 30) && (i2 = i4 - iArr2[0]) >= 0) {
                        keyguardPresentationDisabler.mLastDownTime = currentTimeMillis;
                        iArr[i2] = iArr[i2] + 1;
                        z = true;
                    }
                } else {
                    i = i3;
                }
                i3 = i + 1;
            }
            if (z) {
                int i5 = 0;
                int i6 = 0;
                for (int i7 : iArr) {
                    if (i7 > 0) {
                        i5++;
                        i6 += i7;
                    }
                }
                if (i5 == 2) {
                    if (i6 == 2) {
                        final VibratorHelper vibratorHelper = keyguardPresentationDisabler.mVibratorHelper;
                        if (vibratorHelper.hasVibrator()) {
                            vibratorHelper.mExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda5
                                public final /* synthetic */ int f$1 = 5;

                                @Override // java.lang.Runnable
                                public final void run() {
                                    VibratorHelper.this.mVibrator.vibrate(VibrationEffect.get(this.f$1, false), VibratorHelper.TOUCH_VIBRATION_ATTRIBUTES);
                                }
                            });
                        }
                        Log.d("KeyguardDisplayManager", "keys were pressed to disable KeyguardPresentation");
                        keyguardPresentationDisabler.mKeyEnabled = true;
                    } else {
                        Arrays.fill(iArr, 0);
                        keyguardPresentationDisabler.mLastDownTime = 0L;
                    }
                }
            } else {
                Arrays.fill(iArr, 0);
                keyguardPresentationDisabler.mLastDownTime = 0L;
            }
        }
        if (notificationShadeWindowViewController.mSysUIKeyEventHandler.interceptMediaKey(keyEvent) || super.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        return NotificationShadeWindowViewController.this.mSysUIKeyEventHandler.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        InputDevice device;
        KeyguardKeyEventInteractor keyguardKeyEventInteractor = NotificationShadeWindowViewController.this.mSysUIKeyEventHandler.keyguardKeyEventInteractor;
        keyguardKeyEventInteractor.getClass();
        if (keyEvent.getAction() == 0 && (device = keyEvent.getDevice()) != null && device.isFullKeyboard() && device.isExternal()) {
            PowerInteractor.onUserTouch$default(keyguardKeyEventInteractor.powerInteractor);
        }
        if (keyEvent.getKeyCode() != 4) {
            return false;
        }
        boolean z = LsRune.SECURITY_CAPTURED_BLUR;
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = keyguardKeyEventInteractor.statusBarKeyguardViewManager;
        if (!(z && statusBarKeyguardViewManager.getLastPrimaryBouncerShowing()) && keyguardKeyEventInteractor.statusBarStateController.getState() == 1 && statusBarKeyguardViewManager.dispatchBackKeyEventPreIme()) {
            return keyguardKeyEventInteractor.backActionInteractor.onBackRequested();
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v1, types: [android.view.View] */
    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        Boolean bool;
        NotificationShadeWindowViewController notificationShadeWindowViewController = NotificationShadeWindowViewController.this;
        if (notificationShadeWindowViewController.mStatusBarViewController == null) {
            bool = Boolean.FALSE;
            NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "Ignoring touch while statusBarView not yet set", bool);
        } else {
            boolean z = motionEvent.getActionMasked() == 0;
            boolean z2 = motionEvent.getActionMasked() == 1;
            boolean z3 = motionEvent.getActionMasked() == 3;
            boolean z4 = notificationShadeWindowViewController.mExpandingBelowNotch;
            if (z2 || z3) {
                notificationShadeWindowViewController.mExpandingBelowNotch = false;
            }
            if (z3 || !((CentralSurfacesImpl) notificationShadeWindowViewController.mService).shouldIgnoreTouch()) {
                if (z) {
                    notificationShadeWindowViewController.mTouchActive = true;
                    notificationShadeWindowViewController.mTouchCancelled = false;
                    notificationShadeWindowViewController.mDownEvent = motionEvent;
                    MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
                    Flags.migrateClocksToBlueprint();
                } else if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
                    notificationShadeWindowViewController.mTouchActive = false;
                    notificationShadeWindowViewController.mDownEvent = null;
                }
                boolean z5 = notificationShadeWindowViewController.mTouchCancelled;
                NotificationShadeWindowView notificationShadeWindowView = notificationShadeWindowViewController.mView;
                if (z5 || notificationShadeWindowViewController.mExpandAnimationRunning) {
                    if (notificationShadeWindowView.getVisibility() != 0 && motionEvent.getActionMasked() == 3) {
                        bool = null;
                    } else if (notificationShadeWindowViewController.mTouchCancelled) {
                        bool = Boolean.FALSE;
                        NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "touch cancelled", bool);
                    } else if (notificationShadeWindowViewController.mExpandAnimationRunning) {
                        if (!z || notificationShadeWindowViewController.mClock.uptimeMillis() <= notificationShadeWindowViewController.mLaunchAnimationTimeout) {
                            bool = Boolean.FALSE;
                            NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "expand animation running", bool);
                        } else {
                            Log.wtf("NotifShadeWindowVC", "NSWVC: launch animation timed out");
                            notificationShadeWindowViewController.setExpandAnimationRunning(false);
                        }
                    }
                }
                if (notificationShadeWindowViewController.mKeyguardUnlockAnimationController.playingCannedUnlockAnimation) {
                    bool = null;
                } else if (notificationShadeWindowViewController.mIsOcclusionTransitionRunning) {
                    bool = Boolean.FALSE;
                    NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "occlusion transition running", bool);
                } else {
                    notificationShadeWindowViewController.mFalsingCollector.onTouchEvent(motionEvent);
                    int i = SceneContainerFlag.$r8$clinit;
                    Flags.sceneContainer();
                    if (notificationShadeWindowViewController.mGlanceableHubContainerController.onTouchEvent(motionEvent)) {
                        bool = Boolean.TRUE;
                        NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "dispatched to glanceable hub container", bool);
                    } else if (notificationShadeWindowViewController.mStatusBarKeyguardViewManager.dispatchTouchEvent(motionEvent)) {
                        bool = Boolean.TRUE;
                        NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "dispatched to Keyguard", bool);
                    } else {
                        View view = notificationShadeWindowViewController.mBrightnessMirror;
                        if (view != null && view.getVisibility() == 0 && motionEvent.getActionMasked() == 5) {
                            bool = Boolean.FALSE;
                            NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "disallowed new pointer", bool);
                        } else {
                            if (z) {
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationShadeWindowViewController.mNotificationStackScrollLayoutController;
                                NotificationGuts notificationGuts = notificationStackScrollLayoutController.mNotificationGutsManager.mNotificationGutsExposed;
                                NotificationMenuRowPlugin currentMenuRow = notificationStackScrollLayoutController.mSwipeHelper.getCurrentMenuRow();
                                ?? r14 = notificationStackScrollLayoutController.mSwipeHelper.mTranslatingParentView;
                                if (notificationGuts == null || notificationGuts.mGutsContent.isLeavebehind()) {
                                    notificationGuts = (currentMenuRow == null || !currentMenuRow.isMenuVisible() || r14 == 0) ? null : r14;
                                }
                                if (notificationGuts != null && !NotificationSwipeHelper.isTouchInView(notificationGuts, motionEvent)) {
                                    notificationStackScrollLayoutController.mNotificationGutsManager.closeAndSaveGuts(false, false, true, false);
                                    notificationStackScrollLayoutController.mSwipeHelper.resetExposedMenuView(true, true);
                                }
                            }
                            if (((StatusBarStateControllerImpl) notificationShadeWindowViewController.mStatusBarStateController).mIsDozing) {
                                DozeScrimController dozeScrimController = notificationShadeWindowViewController.mDozeScrimController;
                                dozeScrimController.mHandler.removeCallbacks(dozeScrimController.mPulseOut);
                            }
                            if (CscRune.SECURITY_SIM_PERM_DISABLED && notificationShadeWindowViewController.mKeyguardUpdateMonitor.isIccBlockedPermanently()) {
                                notificationShadeWindowViewController.mExpandingBelowNotch = false;
                                z4 = false;
                            } else if (z && motionEvent.getY() >= notificationShadeWindowView.getBottom()) {
                                notificationShadeWindowViewController.mExpandingBelowNotch = true;
                                z4 = true;
                            }
                            if (z4) {
                                bool = Boolean.valueOf(notificationShadeWindowViewController.mStatusBarViewController.sendTouchToView(motionEvent));
                                NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "expand below notch. sending touch to status bar", bool);
                            } else if (!notificationShadeWindowViewController.mIsTrackingBarGesture && z && notificationShadeWindowViewController.mPanelExpansionInteractor.isFullyCollapsed()) {
                                if (notificationShadeWindowViewController.mStatusBarViewController.touchIsWithinView(motionEvent.getRawX(), motionEvent.getRawY())) {
                                    MigrateClocksToBlueprint migrateClocksToBlueprint2 = MigrateClocksToBlueprint.INSTANCE;
                                    Flags.migrateClocksToBlueprint();
                                    if (notificationShadeWindowViewController.mStatusBarWindowStateController.windowState == 0) {
                                        notificationShadeWindowViewController.mIsTrackingBarGesture = true;
                                        bool = Boolean.valueOf(notificationShadeWindowViewController.mStatusBarViewController.sendTouchToView(motionEvent));
                                        NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "sending touch to status bar", bool);
                                    } else {
                                        bool = Boolean.TRUE;
                                        NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "hidden or hiding", bool);
                                    }
                                }
                                NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "no custom touch dispatch of down event", null);
                                bool = null;
                            } else {
                                if (notificationShadeWindowViewController.mIsTrackingBarGesture) {
                                    boolean sendTouchToView = notificationShadeWindowViewController.mStatusBarViewController.sendTouchToView(motionEvent);
                                    if (z2 || z3) {
                                        notificationShadeWindowViewController.mIsTrackingBarGesture = false;
                                    }
                                    bool = Boolean.valueOf(sendTouchToView);
                                    NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "sending bar gesture to status bar", bool);
                                }
                                NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "no custom touch dispatch of down event", null);
                                bool = null;
                            }
                        }
                    }
                }
            } else {
                bool = Boolean.FALSE;
                NotificationShadeWindowViewController.m2107$$Nest$mlogDownDispatch(notificationShadeWindowViewController, motionEvent, "touch ignored by CS", bool);
            }
        }
        boolean booleanValue = bool != null ? bool.booleanValue() : super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "NotificationShadeWindowView", booleanValue);
        NotificationShadeWindowViewController.this.mFalsingCollector.onMotionEventComplete();
        return booleanValue;
    }

    @Override // com.android.systemui.scene.ui.view.WindowRootView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setWillNotDraw(true);
        Flags.FEATURE_FLAGS.getClass();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (CscRune.SECURITY_SIM_PERM_DISABLED && ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isSimDisabledPermanently() && CscRune.LOCKUI_BOTTOM_USIM_TEXT && !NotificationShadeWindowViewController.this.mShadeViewController.isTouchableArea(motionEvent)) {
            return true;
        }
        NotificationShadeWindowViewController.AnonymousClass1 anonymousClass1 = this.mInteractionEventHandler;
        anonymousClass1.getClass();
        NotificationShadeWindowViewController notificationShadeWindowViewController = NotificationShadeWindowViewController.this;
        notificationShadeWindowViewController.mStatusBarKeyguardViewManager.shouldInterceptTouchEvent();
        int actionMasked = motionEvent.getActionMasked();
        ShadeViewController shadeViewController = notificationShadeWindowViewController.mShadeViewController;
        SysuiStatusBarStateController sysuiStatusBarStateController = notificationShadeWindowViewController.mStatusBarStateController;
        if (actionMasked == 0 && ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 1) {
            notificationShadeWindowViewController.mPluginLockTouchArea = shadeViewController.isInLockStarContainer(motionEvent);
        }
        if (motionEvent.getActionMasked() == 0 && ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 1) {
            notificationShadeWindowViewController.mSecKeyguardStatusViewTouchArea = shadeViewController.isInFaceWidgetContainer(motionEvent);
        }
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
        boolean z = false;
        boolean z2 = notificationShadeWindowViewController.mPrimaryBouncerInteractor.isBouncerShowing() || notificationShadeWindowViewController.mAlternateBouncerInteractor.isVisibleState();
        if ((!notificationShadeWindowViewController.mPanelExpansionInteractor.isFullyExpanded() && ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState != 1) || z2 || ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mIsDozing || notificationShadeWindowViewController.mPluginLockTouchArea || notificationShadeWindowViewController.mSecKeyguardStatusViewTouchArea) {
            MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
            Flags.migrateClocksToBlueprint();
        } else {
            SecPanelTouchBlockHelper secPanelTouchBlockHelper = notificationShadeWindowViewController.mPanelTouchBlockHelper;
            ShadeLogger shadeLogger = notificationShadeWindowViewController.mShadeLogger;
            if (secPanelTouchBlockHelper != null && secPanelTouchBlockHelper.isKeyguardPanelDisabled()) {
                shadeLogger.d("NSWVC: return false by isKeyguardPanelDisabled");
            } else if (notificationShadeWindowViewController.mDragDownHelper.dragDownCallback.isDragDownEnabledForView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(null)) {
                MigrateClocksToBlueprint migrateClocksToBlueprint2 = MigrateClocksToBlueprint.INSTANCE;
                Flags.migrateClocksToBlueprint();
                z = notificationShadeWindowViewController.mDragDownHelper.onInterceptTouchEvent(motionEvent);
                Flags.migrateClocksToBlueprint();
                if (z && motionEvent.getAction() == 0) {
                    shadeLogger.d("NSWVC: drag down helper intercepted");
                }
            } else {
                MigrateClocksToBlueprint migrateClocksToBlueprint3 = MigrateClocksToBlueprint.INSTANCE;
                Flags.migrateClocksToBlueprint();
            }
        }
        if (!z) {
            z = super.onInterceptTouchEvent(motionEvent);
        }
        if (z) {
            NotificationShadeWindowViewController.AnonymousClass1 anonymousClass12 = this.mInteractionEventHandler;
            anonymousClass12.getClass();
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            obtain.setAction(3);
            Log.w("KeyguardTouchAnimator", "ACTION_CANCEL is called on didIntercept");
            NotificationShadeWindowViewController notificationShadeWindowViewController2 = NotificationShadeWindowViewController.this;
            notificationShadeWindowViewController2.mStackScrollLayout.onInterceptTouchEvent(obtain);
            MigrateClocksToBlueprint migrateClocksToBlueprint4 = MigrateClocksToBlueprint.INSTANCE;
            Flags.migrateClocksToBlueprint();
            notificationShadeWindowViewController2.mShadeViewController.handleExternalInterceptTouch(obtain);
            obtain.recycle();
        }
        return z;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Trace.beginSection("NotificationShadeWindowView#onMeasure");
        super.onMeasure(i, i2);
        Trace.endSection();
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (CscRune.SECURITY_SIM_PERM_DISABLED && ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isSimDisabledPermanently() && CscRune.LOCKUI_BOTTOM_USIM_TEXT && !NotificationShadeWindowViewController.this.mShadeViewController.isTouchableArea(motionEvent)) {
            return true;
        }
        NotificationShadeWindowViewController notificationShadeWindowViewController = NotificationShadeWindowViewController.this;
        boolean z = ((StatusBarStateControllerImpl) notificationShadeWindowViewController.mStatusBarStateController).mIsDozing ? !notificationShadeWindowViewController.mDozeServiceHost.mPulsing : false;
        notificationShadeWindowViewController.mStatusBarKeyguardViewManager.onTouch();
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        if (notificationShadeWindowViewController.mDragDownHelper.dragDownCallback.isDragDownEnabledForView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(null) || notificationShadeWindowViewController.mDragDownHelper.isDraggingDown) {
            z = notificationShadeWindowViewController.mDragDownHelper.onTouchEvent(motionEvent) || z;
        }
        if (!z) {
            z = super.onTouchEvent(motionEvent);
        }
        if (!z) {
            NotificationShadeWindowViewController.AnonymousClass1 anonymousClass1 = this.mInteractionEventHandler;
            anonymousClass1.getClass();
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 1 || actionMasked == 3) {
                ((CentralSurfacesImpl) NotificationShadeWindowViewController.this.mService).setInteracting(1, false);
            }
        }
        return z;
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).setFocusForBiometrics(1, z);
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        IntConsumer intConsumer = this.mVisibilityChangedListener;
        if (intConsumer != null) {
            intConsumer.accept(i);
        }
        SecNotificationShadeWindowStateInteractor secNotificationShadeWindowStateInteractor = this.mSecNotificationShadeWindowStateInteractor;
        if (secNotificationShadeWindowStateInteractor != null) {
            secNotificationShadeWindowStateInteractor.repository._visibility.updateState(null, Integer.valueOf(i));
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        Trace.instant(4096L, "NotificationShadeWindowView#requestLayout");
        super.requestLayout();
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.shade.NotificationShadeWindowView$$ExternalSyntheticLambda0] */
    @Override // android.view.ViewGroup, android.view.ViewParent
    public final ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i) {
        if (i != 1) {
            return super.startActionModeForChild(view, callback, i);
        }
        ActionModeCallback2Wrapper actionModeCallback2Wrapper = new ActionModeCallback2Wrapper(callback);
        ActionMode actionMode = this.mFloatingActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        cleanupFloatingActionModeViews();
        this.mFloatingToolbar = new FloatingToolbar(this.mFakeWindow);
        final ActionMode floatingActionMode = new FloatingActionMode(((FrameLayout) this).mContext, actionModeCallback2Wrapper, view, this.mFloatingToolbar);
        this.mFloatingActionModeOriginatingView = view;
        this.mFloatingToolbarPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.shade.NotificationShadeWindowView$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                FloatingActionMode floatingActionMode2 = floatingActionMode;
                int i2 = NotificationShadeWindowView.$r8$clinit;
                floatingActionMode2.updateViewLocationInWindow();
                return true;
            }
        };
        if (!actionModeCallback2Wrapper.mWrapped.onCreateActionMode(floatingActionMode, floatingActionMode.getMenu())) {
            return null;
        }
        this.mFloatingActionMode = floatingActionMode;
        floatingActionMode.invalidate();
        this.mFloatingActionModeOriginatingView.getViewTreeObserver().addOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
        return floatingActionMode;
    }
}
