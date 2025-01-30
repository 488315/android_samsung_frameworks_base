package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.wm.shell.animation.Interpolators;
import com.android.systemui.Dependency;
import com.android.systemui.ExpandHelper;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.SecPanelPolicy;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DragDownHelper implements Gefingerpoken {
    public float dragDownAmountOnStart;
    public final LockscreenShadeTransitionController dragDownCallback;
    public boolean draggedFarEnough;
    public ExpandHelper.Callback expandCallback;
    public final FalsingCollector falsingCollector;
    public final FalsingManager falsingManager;
    public float initialTouchX;
    public float initialTouchY;
    public boolean isDraggingDown;
    public float lastHeight;
    public int minDragDistance;
    public float slopMultiplier;
    public ExpandableView startingChild;
    public float touchSlop;

    public DragDownHelper(FalsingManager falsingManager, FalsingCollector falsingCollector, LockscreenShadeTransitionController lockscreenShadeTransitionController, Context context) {
        this.falsingManager = falsingManager;
        this.falsingCollector = falsingCollector;
        this.dragDownCallback = lockscreenShadeTransitionController;
        updateResources(context);
    }

    public final void cancelChildExpansion(final ExpandableView expandableView, long j) {
        if (expandableView.mActualHeight == expandableView.getCollapsedHeight()) {
            ExpandHelper.Callback callback = this.expandCallback;
            if (callback == null) {
                callback = null;
            }
            ((NotificationStackScrollLayout.C293017) callback).setUserLockedChild(expandableView, false);
            return;
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(expandableView.mActualHeight, expandableView.getCollapsedHeight());
        ofInt.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofInt.setDuration(j);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.DragDownHelper$cancelChildExpansion$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ExpandableView.this.setActualHeight(((Integer) valueAnimator.getAnimatedValue()).intValue(), true);
            }
        });
        ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.DragDownHelper$cancelChildExpansion$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                ExpandHelper.Callback callback2 = DragDownHelper.this.expandCallback;
                if (callback2 == null) {
                    callback2 = null;
                }
                ((NotificationStackScrollLayout.C293017) callback2).setUserLockedChild(expandableView, false);
            }
        });
        ofInt.start();
    }

    public final void captureStartingChild(float f, float f2) {
        if (this.startingChild == null) {
            ExpandHelper.Callback callback = this.expandCallback;
            if (callback == null) {
                callback = null;
            }
            ExpandableView childAtRawPosition = NotificationStackScrollLayout.this.getChildAtRawPosition(f, f2);
            this.startingChild = childAtRawPosition;
            if (childAtRawPosition != null) {
                if (!this.dragDownCallback.m196x229f8ab3(childAtRawPosition)) {
                    this.startingChild = null;
                } else {
                    ExpandHelper.Callback callback2 = this.expandCallback;
                    ((NotificationStackScrollLayout.C293017) (callback2 != null ? callback2 : null)).setUserLockedChild(this.startingChild, true);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00b9 A[RETURN] */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.draggedFarEnough = false;
            this.isDraggingDown = false;
            this.startingChild = null;
            this.initialTouchY = y;
            this.initialTouchX = x;
        } else if (actionMasked == 2) {
            float f = y - this.initialTouchY;
            float f2 = motionEvent.getClassification() == 1 ? this.touchSlop * this.slopMultiplier : this.touchSlop;
            if (f > f2 && f > Math.abs(x - this.initialTouchX)) {
                this.falsingCollector.getClass();
                this.isDraggingDown = true;
                captureStartingChild(this.initialTouchX, this.initialTouchY);
                this.initialTouchY = y;
                this.initialTouchX = x;
                ExpandableView expandableView = this.startingChild;
                LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
                LSShadeTransitionLogger lSShadeTransitionLogger = lockscreenShadeTransitionController.logger;
                lSShadeTransitionLogger.logDragDownStarted(expandableView);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = lockscreenShadeTransitionController.nsslController;
                if (notificationStackScrollLayoutController == null) {
                    notificationStackScrollLayoutController = null;
                }
                notificationStackScrollLayoutController.mView.cancelLongPress();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = lockscreenShadeTransitionController.nsslController;
                (notificationStackScrollLayoutController2 != null ? notificationStackScrollLayoutController2 : null).checkSnoozeLeavebehind();
                ValueAnimator valueAnimator = lockscreenShadeTransitionController.dragDownAnimator;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    lSShadeTransitionLogger.logAnimationCancelled(false);
                    valueAnimator.cancel();
                }
                this.dragDownAmountOnStart = lockscreenShadeTransitionController.dragDownAmount;
                return this.startingChild != null || lockscreenShadeTransitionController.m195x7adb072c();
            }
            SecPanelPolicy secPanelPolicy = (SecPanelPolicy) Dependency.get(SecPanelPolicy.class);
            float f3 = this.initialTouchX;
            if (((StatusBarStateControllerImpl) secPanelPolicy.mSysuiStatusBarStateController).mState == 1) {
                float abs = Math.abs(f);
                if (f < 0.0f && abs > f2 && abs > Math.abs(x - f3)) {
                    z = true;
                    if (!z) {
                        return true;
                    }
                }
            }
            z = false;
            if (!z) {
            }
        }
        return false;
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.isDraggingDown) {
            return false;
        }
        motionEvent.getX();
        float y = motionEvent.getY();
        int actionMasked = motionEvent.getActionMasked();
        final LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
        if (actionMasked == 1) {
            FalsingManager falsingManager = this.falsingManager;
            if (!falsingManager.isUnlockingDisabled()) {
                if (!((((StatusBarStateControllerImpl) lockscreenShadeTransitionController.statusBarStateController).mState == 1) && (falsingManager.isFalseTouch(2) || !this.draggedFarEnough)) && lockscreenShadeTransitionController.m194x90582e2c()) {
                    final ExpandableView expandableView = this.startingChild;
                    int i = (int) (y - this.initialTouchY);
                    boolean m194x90582e2c = lockscreenShadeTransitionController.m194x90582e2c();
                    LSShadeTransitionLogger lSShadeTransitionLogger = lockscreenShadeTransitionController.logger;
                    if (m194x90582e2c) {
                        RunnableC2573xbbc01eb0 runnableC2573xbbc01eb0 = new RunnableC2573xbbc01eb0(lockscreenShadeTransitionController);
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController = lockscreenShadeTransitionController.nsslController;
                        if (notificationStackScrollLayoutController == null) {
                            notificationStackScrollLayoutController = null;
                        }
                        if (notificationStackScrollLayoutController.mDynamicPrivacyController.isInLockedDownShade()) {
                            lSShadeTransitionLogger.logDraggedDownLockDownShade(expandableView);
                            ((StatusBarStateControllerImpl) lockscreenShadeTransitionController.statusBarStateController).mLeaveOpenOnKeyguardHide = true;
                            lockscreenShadeTransitionController.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$onDraggedDown$1
                                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                                public final boolean onDismiss() {
                                    LockscreenShadeTransitionController.this.nextHideKeyguardNeedsNoAnimation = true;
                                    return false;
                                }
                            }, runnableC2573xbbc01eb0, false);
                        } else {
                            lSShadeTransitionLogger.logDraggedDown(expandableView, i);
                            if (!lockscreenShadeTransitionController.ambientState.mDozing || expandableView != null) {
                                lockscreenShadeTransitionController.goToLockedShadeInternal(expandableView, new Function1() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$onDraggedDown$animationHandler$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        long longValue = ((Number) obj).longValue();
                                        View view = expandableView;
                                        if (view instanceof ExpandableNotificationRow) {
                                            ((ExpandableNotificationRow) view).onExpandedByGesture(true);
                                        }
                                        ShadeViewController shadeViewController = lockscreenShadeTransitionController.shadeViewController;
                                        if (shadeViewController == null) {
                                            shadeViewController = null;
                                        }
                                        ((NotificationPanelViewController) shadeViewController).transitionToExpandedShade(longValue);
                                        Iterator it = ((ArrayList) lockscreenShadeTransitionController.callbacks).iterator();
                                        while (it.hasNext()) {
                                            ((QuickSettingsController.LockscreenShadeTransitionCallback) it.next()).getClass();
                                        }
                                        LockscreenShadeTransitionController lockscreenShadeTransitionController2 = lockscreenShadeTransitionController;
                                        lockscreenShadeTransitionController2.forceApplyAmount = true;
                                        lockscreenShadeTransitionController2.logger.logDragDownAmountReset();
                                        lockscreenShadeTransitionController.m197xcfc05636(0.0f);
                                        lockscreenShadeTransitionController.forceApplyAmount = false;
                                        return Unit.INSTANCE;
                                    }
                                }, runnableC2573xbbc01eb0);
                                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "1005", "Noti swipe down");
                            }
                        }
                    } else {
                        lSShadeTransitionLogger.logUnSuccessfulDragDown(expandableView);
                        lockscreenShadeTransitionController.setDragDownAmountAnimated(0.0f, 0L, null);
                    }
                    ExpandableView expandableView2 = this.startingChild;
                    if (expandableView2 != null) {
                        ExpandHelper.Callback callback = this.expandCallback;
                        if (callback == null) {
                            callback = null;
                        }
                        ((NotificationStackScrollLayout.C293017) callback).setUserLockedChild(expandableView2, false);
                        this.startingChild = null;
                    }
                    this.isDraggingDown = false;
                }
            }
            stopDragging();
            return false;
        }
        if (actionMasked == 2) {
            float f = this.initialTouchY;
            this.lastHeight = y - f;
            captureStartingChild(this.initialTouchX, f);
            lockscreenShadeTransitionController.m197xcfc05636(this.lastHeight + this.dragDownAmountOnStart);
            ExpandableView expandableView3 = this.startingChild;
            if (expandableView3 != null) {
                float f2 = this.lastHeight;
                float f3 = f2 >= 0.0f ? f2 : 0.0f;
                boolean isContentExpandable = expandableView3.isContentExpandable();
                float f4 = f3 * (isContentExpandable ? 0.5f : 0.15f);
                if (isContentExpandable && expandableView3.getCollapsedHeight() + f4 > expandableView3.getMaxContentHeight()) {
                    f4 -= ((expandableView3.getCollapsedHeight() + f4) - expandableView3.getMaxContentHeight()) * 0.85f;
                }
                expandableView3.setActualHeight((int) (expandableView3.getCollapsedHeight() + f4), true);
                ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.get(CentralSurfaces.class))).userActivity();
            }
            if (this.lastHeight > this.minDragDistance) {
                if (!this.draggedFarEnough) {
                    this.draggedFarEnough = true;
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = lockscreenShadeTransitionController.nsslController;
                    (notificationStackScrollLayoutController2 != null ? notificationStackScrollLayoutController2 : null).mView.setDimmed(false, true);
                }
            } else if (this.draggedFarEnough) {
                this.draggedFarEnough = false;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = lockscreenShadeTransitionController.nsslController;
                (notificationStackScrollLayoutController3 != null ? notificationStackScrollLayoutController3 : null).mView.setDimmed(true, true);
            }
            return true;
        }
        if (actionMasked == 3) {
            stopDragging();
            return false;
        }
        return false;
    }

    public final void stopDragging() {
        this.falsingCollector.getClass();
        ExpandableView expandableView = this.startingChild;
        if (expandableView != null) {
            cancelChildExpansion(expandableView, 375L);
            this.startingChild = null;
        }
        this.isDraggingDown = false;
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
        lockscreenShadeTransitionController.logger.logDragDownAborted();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = lockscreenShadeTransitionController.nsslController;
        if (notificationStackScrollLayoutController == null) {
            notificationStackScrollLayoutController = null;
        }
        notificationStackScrollLayoutController.mView.setDimmed(true, true);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = lockscreenShadeTransitionController.nsslController;
        if (notificationStackScrollLayoutController2 == null) {
            notificationStackScrollLayoutController2 = null;
        }
        notificationStackScrollLayoutController2.mView.resetScrollPosition();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = lockscreenShadeTransitionController.nsslController;
        if (notificationStackScrollLayoutController3 == null) {
            notificationStackScrollLayoutController3 = null;
        }
        notificationStackScrollLayoutController3.mView.mCheckForLeavebehind = true;
        lockscreenShadeTransitionController.setDragDownAmountAnimated(0.0f, 0L, null);
    }

    public final void updateResources(Context context) {
        this.minDragDistance = context.getResources().getDimensionPixelSize(R.dimen.keyguard_drag_down_min_distance);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.touchSlop = viewConfiguration.getScaledTouchSlop();
        this.slopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
    }
}
