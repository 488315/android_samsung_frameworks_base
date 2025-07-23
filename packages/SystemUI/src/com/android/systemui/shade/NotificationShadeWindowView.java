package com.android.systemui.shade;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.ActionMode;
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
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.internal.policy.SystemBarUtils;
import com.android.internal.view.FloatingActionMode;
import com.android.internal.widget.floatingtoolbar.FloatingToolbar;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor$registerMonitor$1$1;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.shade.NotificationShadeWindowViewController;
import com.android.systemui.shade.PanelSlideEventHandler;
import com.android.systemui.shade.domain.interactor.SecNotificationShadeWindowStateInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.statusbar.DragDownHelper;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.ConfigurationForwarder;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationShadeWindowView extends WindowRootView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAnimatingContentLaunch;
    public ConfigurationForwarder mConfigurationForwarder;
    public final AnonymousClass1 mFakeWindow;
    public ActionMode mFloatingActionMode;
    public View mFloatingActionModeOriginatingView;
    public FloatingToolbar mFloatingToolbar;
    public NotificationShadeWindowView$$ExternalSyntheticLambda0 mFloatingToolbarPreDrawListener;
    public NotificationShadeWindowViewController.AnonymousClass1 mInteractionEventHandler;
    public final SecNotificationShadeWindowStateInteractor mSecNotificationShadeWindowStateInteractor;
    public KeyguardVisibilityMonitor$registerMonitor$1$1 mVisibilityChangedListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ActionModeCallback2Wrapper extends ActionMode.Callback2 {
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

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.shade.NotificationShadeWindowView$1] */
    public NotificationShadeWindowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAnimatingContentLaunch = false;
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
        if (notificationShadeWindowViewController.mStatusBarStateController.getState() == 1 && action == 7) {
            ((CentralSurfacesImpl) notificationShadeWindowViewController.mService).userActivity();
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0205  */
    /* JADX WARN: Type inference failed for: r15v1, types: [android.view.View] */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean dispatchTouchEvent(android.view.MotionEvent r17) {
        /*
            Method dump skipped, instructions count: 591
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationShadeWindowView.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // com.android.systemui.scene.ui.view.WindowRootView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setWillNotDraw(true);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ShadeTraceLogger shadeTraceLogger = ShadeTraceLogger.INSTANCE;
        if (Trace.isEnabled()) {
            TrackTracer trackTracer = ShadeTraceLogger.t;
            Trace.instantForTrack(trackTracer.traceTag, trackTracer.trackName, MutableVectorKt$$ExternalSyntheticOutline0.m(configuration.densityDpi, configuration.smallestScreenWidthDp, "NotificationShadeWindowView#onConfigurationChanged(dpi=", ", smallestWidthDp=", ")"));
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /* JADX WARN: Code restructure failed: missing block: B:96:0x01c6, code lost:
    
        if (java.lang.Math.abs(r4) < java.lang.Math.abs(r0)) goto L93;
     */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x024c  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x023d  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r11) {
        /*
            Method dump skipped, instructions count: 619
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.NotificationShadeWindowView.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Trace.beginSection("NotificationShadeWindowView#onMeasure");
        super.onMeasure(i, i2);
        Trace.endSection();
    }

    public final void onMovedToDisplay(int i, Configuration configuration) {
        ArrayList arrayList;
        super.onMovedToDisplay(i, configuration);
        ShadeWindowGoesAround shadeWindowGoesAround = ShadeWindowGoesAround.INSTANCE;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (!ShadeWindowGoesAround.FLAG.isTrue()) {
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.shade_window_goes_around to be enabled.");
        }
        ShadeTraceLogger shadeTraceLogger = ShadeTraceLogger.INSTANCE;
        if (Trace.isEnabled()) {
            TrackTracer trackTracer = ShadeTraceLogger.t;
            Trace.instantForTrack(trackTracer.traceTag, trackTracer.trackName, MutableVectorKt$$ExternalSyntheticOutline0.m(i, configuration.densityDpi, "onMovedToDisplay(displayId=", ", dpi=", ")"));
        }
        ConfigurationForwarder configurationForwarder = this.mConfigurationForwarder;
        if (configurationForwarder != null) {
            ConfigurationControllerImpl configurationControllerImpl = (ConfigurationControllerImpl) configurationForwarder;
            synchronized (configurationControllerImpl.listeners) {
                arrayList = new ArrayList(configurationControllerImpl.listeners);
            }
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ConfigurationController.ConfigurationListener configurationListener = (ConfigurationController.ConfigurationListener) obj;
                if (((ArrayList) configurationControllerImpl.listeners).contains(configurationListener)) {
                    configurationListener.onMovedToDisplay(i, configuration);
                }
            }
        }
        onConfigurationChanged(configuration);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (CscRune.SECURITY_SIM_PERM_DISABLED && ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isSimDisabledPermanently() && CscRune.LOCKUI_BOTTOM_USIM_TEXT && !NotificationShadeWindowViewController.this.mShadeViewController.isTouchableArea(motionEvent)) {
            return true;
        }
        NotificationShadeWindowViewController.AnonymousClass1 anonymousClass1 = this.mInteractionEventHandler;
        NotificationShadeWindowViewController notificationShadeWindowViewController = NotificationShadeWindowViewController.this;
        boolean z = notificationShadeWindowViewController.mStatusBarStateController.isDozing() ? !notificationShadeWindowViewController.mDozeServiceHost.mPulsing : false;
        if (anonymousClass1.mLastInterceptWasDragDownHelper) {
            DragDownHelper dragDownHelper = notificationShadeWindowViewController.mDragDownHelper;
            if (dragDownHelper.isDraggingDown) {
                if (((dragDownHelper.maxDragDownAnimator == null && dragDownHelper.overDragDownAnimator == null) ? false : true) == false) {
                    if (dragDownHelper.isDraggingDown) {
                        float y = motionEvent.getY();
                        dragDownHelper.velocityTracker.addMovement(motionEvent);
                        int actionMasked = motionEvent.getActionMasked();
                        LockscreenShadeTransitionController lockscreenShadeTransitionController = dragDownHelper.dragDownCallback;
                        if (actionMasked == 1) {
                            dragDownHelper.isInitialDirectionMeasured = false;
                            dragDownHelper.isInitiallyDraggedDownard = false;
                            FalsingManager falsingManager = dragDownHelper.falsingManager;
                            if (!falsingManager.isUnlockingDisabled() && ((lockscreenShadeTransitionController.statusBarStateController.getState() != 1 || (!falsingManager.isFalseTouch(2) && dragDownHelper.draggedFarEnough)) && lockscreenShadeTransitionController.canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core())) {
                                float fractionToShade = lockscreenShadeTransitionController.getFractionToShade();
                                SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = lockscreenShadeTransitionController.panelSAStatusLogInteractor;
                                if (fractionToShade == 1.0f) {
                                    SecPanelSplitHelper panelSplitHelper$1 = lockscreenShadeTransitionController.getPanelSplitHelper$1();
                                    if ((panelSplitHelper$1 != null ? panelSplitHelper$1.shouldQsDownInLockscreen : null) == PanelSlideEventHandler.Direction.UNDECIDED) {
                                        if (dragDownHelper.initialTouchY > SystemBarUtils.getStatusBarHeight(dragDownHelper.context)) {
                                            if (secPanelSAStatusLogInteractor != null) {
                                                secPanelSAStatusLogInteractor.countOpenNotificationPanelFromLockscreen();
                                            }
                                        } else if (secPanelSAStatusLogInteractor != null) {
                                            secPanelSAStatusLogInteractor.countOpenNotificationPanelFromStatusbarOnLockscreen();
                                        }
                                    }
                                    if (lockscreenShadeTransitionController.overDragAmount > 0.0f) {
                                        dragDownHelper.springBack$1();
                                    } else {
                                        dragDownHelper.onFinishDraggingDown();
                                    }
                                } else {
                                    dragDownHelper.velocityTracker.computeCurrentVelocity(1000);
                                    SecPanelSplitHelper panelSplitHelper$12 = lockscreenShadeTransitionController.getPanelSplitHelper$1();
                                    if ((panelSplitHelper$12 != null ? panelSplitHelper$12.shouldQsDownInLockscreen : null) == PanelSlideEventHandler.Direction.UNDECIDED && dragDownHelper.velocityTracker.getYVelocity() > 0.0f) {
                                        if (dragDownHelper.initialTouchY > SystemBarUtils.getStatusBarHeight(dragDownHelper.context)) {
                                            if (secPanelSAStatusLogInteractor != null) {
                                                secPanelSAStatusLogInteractor.countOpenNotificationPanelFromLockscreen();
                                            }
                                        } else if (secPanelSAStatusLogInteractor != null) {
                                            secPanelSAStatusLogInteractor.countOpenNotificationPanelFromStatusbarOnLockscreen();
                                        }
                                    }
                                    dragDownHelper.animateToMaxDragDown(dragDownHelper.velocityTracker.getYVelocity(), lockscreenShadeTransitionController.isOverDraggingAllowed() ? 1.0f : 0.0f, dragDownHelper.velocityTracker.getYVelocity() > 0.0f);
                                }
                            } else if (lockscreenShadeTransitionController.overDragAmount > 0.0f) {
                                dragDownHelper.springBack$1();
                            } else {
                                dragDownHelper.stopDragging();
                            }
                        } else if (actionMasked == 2) {
                            float f = dragDownHelper.initialTouchY;
                            dragDownHelper.lastHeight = y - f;
                            dragDownHelper.captureStartingChild$1(dragDownHelper.initialTouchX, f);
                            lockscreenShadeTransitionController.setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(dragDownHelper.lastHeight + dragDownHelper.dragDownAmountOnStart);
                            ExpandableView expandableView = dragDownHelper.startingChild;
                            if (expandableView != null) {
                                float f2 = dragDownHelper.lastHeight;
                                float f3 = f2 >= 0.0f ? f2 : 0.0f;
                                boolean isContentExpandable = expandableView.isContentExpandable();
                                float f4 = f3 * (isContentExpandable ? 0.5f : 0.15f);
                                if (isContentExpandable && expandableView.getCollapsedHeight() + f4 > expandableView.getMaxContentHeight()) {
                                    f4 -= ((expandableView.getCollapsedHeight() + f4) - expandableView.getMaxContentHeight()) * 0.85f;
                                }
                                expandableView.setActualHeight((int) (expandableView.getCollapsedHeight() + f4), true);
                                ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class))).userActivity();
                            }
                            if (dragDownHelper.lastHeight > dragDownHelper.minDragDistance) {
                                if (!dragDownHelper.draggedFarEnough) {
                                    dragDownHelper.draggedFarEnough = true;
                                    if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isNotificationAsCard()) {
                                        NotificationStackScrollLayoutController notificationStackScrollLayoutController = lockscreenShadeTransitionController.nsslController;
                                        NotificationStackScrollLayout notificationStackScrollLayout = (notificationStackScrollLayoutController != null ? notificationStackScrollLayoutController : null).mView;
                                        notificationStackScrollLayout.onKeyguard();
                                        notificationStackScrollLayout.mAmbientState.mDimmed = false;
                                        if (notificationStackScrollLayout.mAnimationsEnabled) {
                                            notificationStackScrollLayout.mNeedsAnimation = true;
                                        }
                                        notificationStackScrollLayout.requestChildrenUpdate();
                                    }
                                }
                            } else if (dragDownHelper.draggedFarEnough) {
                                dragDownHelper.draggedFarEnough = false;
                                if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isNotificationAsCard()) {
                                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = lockscreenShadeTransitionController.nsslController;
                                    NotificationStackScrollLayout notificationStackScrollLayout2 = (notificationStackScrollLayoutController2 != null ? notificationStackScrollLayoutController2 : null).mView;
                                    notificationStackScrollLayout2.mAmbientState.mDimmed = notificationStackScrollLayout2.onKeyguard();
                                    if (notificationStackScrollLayout2.mAnimationsEnabled) {
                                        notificationStackScrollLayout2.mNeedsAnimation = true;
                                    }
                                    notificationStackScrollLayout2.requestChildrenUpdate();
                                }
                            }
                        } else if (actionMasked == 3) {
                            dragDownHelper.isInitialDirectionMeasured = false;
                            dragDownHelper.isInitiallyDraggedDownard = false;
                            dragDownHelper.stopDragging();
                        }
                    }
                    z |= !r6 || z;
                }
                r6 = true;
                z |= !r6 || z;
            }
        }
        if (!z && notificationShadeWindowViewController.mShadeViewController.handleExternalTouch(motionEvent)) {
            z = true;
        }
        if (!z) {
            z = super.onTouchEvent(motionEvent);
        }
        if (!z) {
            NotificationShadeWindowViewController.AnonymousClass1 anonymousClass12 = this.mInteractionEventHandler;
            anonymousClass12.getClass();
            int actionMasked2 = motionEvent.getActionMasked();
            if (actionMasked2 == 1 || actionMasked2 == 3) {
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
        KeyguardVisibilityMonitor$registerMonitor$1$1 keyguardVisibilityMonitor$registerMonitor$1$1 = this.mVisibilityChangedListener;
        if (keyguardVisibilityMonitor$registerMonitor$1$1 != null) {
            keyguardVisibilityMonitor$registerMonitor$1$1.accept(i);
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

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean requestSendAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        if (this.mAnimatingContentLaunch && accessibilityEvent.getEventType() == 32768) {
            return false;
        }
        return super.requestSendAccessibilityEvent(view, accessibilityEvent);
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
