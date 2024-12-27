package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Debug;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.PathInterpolator;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.ExpandHelper;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.keyguard.animator.KeyguardTouchSecurityInjector;
import com.android.systemui.keyguard.domain.interactor.NaturalScrollingSettingObserver;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.SecPanelTouchBlockHelper;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.animation.Interpolators;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DragDownHelper implements Gefingerpoken {
    public float dragDownAmountOnStart;
    public final LockscreenShadeTransitionController dragDownCallback;
    public boolean draggedFarEnough;
    public ExpandHelper.Callback expandCallback;
    public final FalsingManager falsingManager;
    public float initialTouchX;
    public float initialTouchY;
    public boolean isDraggingDown;
    public boolean isInitialDirectionMeasured;
    public boolean isInitiallyDraggedDownard;
    public boolean isTouchConsumablePosition;
    public boolean isTrackpadReverseScroll;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public float lastHeight;
    public final LSShadeTransitionLogger logger;
    public ValueAnimator maxDragDownAnimator;
    public int minDragDistance;
    public final NaturalScrollingSettingObserver naturalScrollingSettingObserver;
    public NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public final PluginLockMediator pluginLockMediator;
    public final KeyguardTouchSecurityInjector securityInjector;
    public final ShadeRepository shadeRepository;
    public float slopMultiplier;
    public ExpandableView startingChild;
    public float touchSlop;
    public final VelocityTracker velocityTracker = VelocityTracker.obtain();
    public final PathInterpolator mInterpolator = new PathInterpolator(0.17f, 0.17f, 0.4f, 1.0f);

    public DragDownHelper(FalsingManager falsingManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, NaturalScrollingSettingObserver naturalScrollingSettingObserver, ShadeRepository shadeRepository, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardTouchSecurityInjector keyguardTouchSecurityInjector, Context context, LSShadeTransitionLogger lSShadeTransitionLogger, PluginLockMediator pluginLockMediator) {
        this.falsingManager = falsingManager;
        this.dragDownCallback = lockscreenShadeTransitionController;
        this.naturalScrollingSettingObserver = naturalScrollingSettingObserver;
        this.shadeRepository = shadeRepository;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.securityInjector = keyguardTouchSecurityInjector;
        this.logger = lSShadeTransitionLogger;
        this.pluginLockMediator = pluginLockMediator;
        updateResources$1(context);
    }

    public final void animateToMaxDragDown(final boolean z) {
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
        SecPanelTouchBlockHelper secPanelTouchBlockHelper = (SecPanelTouchBlockHelper) lockscreenShadeTransitionController.panelTouchBlockHelper$delegate.getValue();
        if (secPanelTouchBlockHelper != null && secPanelTouchBlockHelper.isKeyguardPanelDisabled()) {
            LockscreenShadeTransitionController.Companion.getClass();
            Log.d(LockscreenShadeTransitionController.TAG, "animateToMaxDragDown: returned");
            return;
        }
        LockscreenShadeTransitionController.Companion.getClass();
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("animateToMaxDragDown\n", Debug.getCallers(3, " "), LockscreenShadeTransitionController.TAG);
        float f = lockscreenShadeTransitionController.notificationShelfTransitionDistance;
        float f2 = z ? f : 0.0f;
        float f3 = lockscreenShadeTransitionController.dragDownAmount;
        float abs = Math.abs(f2 - f3);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f3, f2);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.DragDownHelper$animateToMaxDragDown$animator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DragDownHelper.this.dragDownCallback.setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.DragDownHelper$animateToMaxDragDown$animator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (z) {
                    this.onFinishDraggingDown();
                } else {
                    this.stopDragging();
                }
                this.maxDragDownAnimator = null;
            }
        });
        ofFloat.setDuration((long) ((abs / f) * KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED));
        ofFloat.setInterpolator(this.mInterpolator);
        ofFloat.start();
        this.maxDragDownAnimator = ofFloat;
    }

    public final void cancelChildExpansion(final ExpandableView expandableView, long j) {
        if (expandableView.mActualHeight == expandableView.getCollapsedHeight()) {
            ExpandHelper.Callback callback = this.expandCallback;
            if (callback == null) {
                callback = null;
            }
            ((NotificationStackScrollLayout.AnonymousClass15) callback).setUserLockedChild(expandableView, false);
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
                ((NotificationStackScrollLayout.AnonymousClass15) callback2).setUserLockedChild(expandableView, false);
            }
        });
        ofInt.start();
    }

    public final void captureStartingChild$1(float f, float f2) {
        if (((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).isNeedsToExpandLocksNoti()) {
            this.startingChild = null;
            return;
        }
        if (this.startingChild == null) {
            ExpandHelper.Callback callback = this.expandCallback;
            if (callback == null) {
                callback = null;
            }
            ExpandableView childAtRawPosition = NotificationStackScrollLayout.this.getChildAtRawPosition(f, f2);
            this.startingChild = childAtRawPosition;
            if (childAtRawPosition != null) {
                if (!this.dragDownCallback.isDragDownEnabledForView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(childAtRawPosition)) {
                    this.startingChild = null;
                } else {
                    ExpandHelper.Callback callback2 = this.expandCallback;
                    ((NotificationStackScrollLayout.AnonymousClass15) (callback2 != null ? callback2 : null)).setUserLockedChild(this.startingChild, true);
                }
            }
        }
    }

    public final void onFinishDraggingDown() {
        SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor;
        float f = (0.0f - this.initialTouchY) * (this.isTrackpadReverseScroll ? -1 : 1);
        final ExpandableView expandableView = this.startingChild;
        int i = (int) f;
        final LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
        boolean canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core = lockscreenShadeTransitionController.canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        LSShadeTransitionLogger lSShadeTransitionLogger = lockscreenShadeTransitionController.logger;
        if (canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core) {
            LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 = new LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1(lockscreenShadeTransitionController);
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
                }, lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1, false);
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
                            ((ShadeLockscreenInteractor) lockscreenShadeTransitionController.shadeLockscreenInteractorLazy.get()).transitionToExpandedShade(longValue, lockscreenShadeTransitionController.ambientState.isNeedsToExpandLocksNoti());
                            Iterator it = ((ArrayList) lockscreenShadeTransitionController.callbacks).iterator();
                            while (it.hasNext()) {
                                LockscreenShadeTransitionController.Callback callback = (LockscreenShadeTransitionController.Callback) it.next();
                                callback.setTransitionToFullShadeAmount(0.0f, true, longValue);
                                callback.setTransitionToFullShadeAmount(0.0f);
                            }
                            LockscreenShadeTransitionController lockscreenShadeTransitionController2 = lockscreenShadeTransitionController;
                            lockscreenShadeTransitionController2.forceApplyAmount = true;
                            lockscreenShadeTransitionController2.logger.logDragDownAmountReset();
                            lockscreenShadeTransitionController.setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0.0f);
                            lockscreenShadeTransitionController.forceApplyAmount = false;
                            return Unit.INSTANCE;
                        }
                    }, lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1);
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_OPEN_NOTIFICATION_LIST, SystemUIAnalytics.DID_NOTI_SWIPE_DOWN);
                    SecPanelSplitHelper.Companion.getClass();
                    if (!SecPanelSplitHelper.isEnabled && (secPanelSAStatusLogInteractor = lockscreenShadeTransitionController.panelSAStatusLogInteractor) != null) {
                        StateFlowImpl stateFlowImpl = secPanelSAStatusLogInteractor.repository._openQuickPanelFrom1Depth;
                        stateFlowImpl.updateState(null, Long.valueOf(((Number) stateFlowImpl.getValue()).longValue() + 1));
                    }
                }
            }
        } else {
            lSShadeTransitionLogger.logUnSuccessfulDragDown(expandableView);
            lockscreenShadeTransitionController.setDragDownAmountAnimated(0.0f, 0L, null);
        }
        Iterator it = ((ArrayList) lockscreenShadeTransitionController.callbacks).iterator();
        while (it.hasNext()) {
            ((LockscreenShadeTransitionController.Callback) it.next()).onExpansionFinished();
        }
        ExpandableView expandableView2 = this.startingChild;
        if (expandableView2 != null) {
            ExpandHelper.Callback callback = this.expandCallback;
            if (callback == null) {
                callback = null;
            }
            ((NotificationStackScrollLayout.AnonymousClass15) callback).setUserLockedChild(expandableView2, false);
            this.startingChild = null;
        }
        this.isDraggingDown = false;
        this.isTrackpadReverseScroll = false;
        ((ShadeRepositoryImpl) this.shadeRepository).legacyLockscreenShadeTracking.updateState(null, Boolean.FALSE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01f8  */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r11) {
        /*
            Method dump skipped, instructions count: 509
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.DragDownHelper.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.maxDragDownAnimator != null) {
            return true;
        }
        if (!this.isDraggingDown) {
            return false;
        }
        float y = motionEvent.getY();
        this.velocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
        if (actionMasked == 1) {
            this.isInitialDirectionMeasured = false;
            this.isInitiallyDraggedDownard = false;
            FalsingManager falsingManager = this.falsingManager;
            if (falsingManager.isUnlockingDisabled() || ((((StatusBarStateControllerImpl) lockscreenShadeTransitionController.statusBarStateController).mState == 1 && (falsingManager.isFalseTouch(2) || !this.draggedFarEnough)) || !lockscreenShadeTransitionController.canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core())) {
                stopDragging();
                return false;
            }
            if (lockscreenShadeTransitionController.getFractionToShade() == 1.0f) {
                onFinishDraggingDown();
                return true;
            }
            this.velocityTracker.computeCurrentVelocity(1000);
            animateToMaxDragDown(this.velocityTracker.getYVelocity() > 0.0f);
            return true;
        }
        if (actionMasked != 2) {
            if (actionMasked != 3) {
                return false;
            }
            this.isInitialDirectionMeasured = false;
            this.isInitiallyDraggedDownard = false;
            stopDragging();
            return false;
        }
        int i = this.isTrackpadReverseScroll ? -1 : 1;
        float f = this.initialTouchY;
        this.lastHeight = (y - f) * i;
        captureStartingChild$1(this.initialTouchX, f);
        lockscreenShadeTransitionController.setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(this.lastHeight + this.dragDownAmountOnStart);
        ExpandableView expandableView = this.startingChild;
        if (expandableView != null) {
            float f2 = this.lastHeight;
            float f3 = f2 >= 0.0f ? f2 : 0.0f;
            boolean isContentExpandable = expandableView.isContentExpandable();
            float f4 = f3 * (isContentExpandable ? 0.5f : 0.15f);
            if (isContentExpandable && expandableView.getCollapsedHeight() + f4 > expandableView.getMaxContentHeight()) {
                f4 -= ((expandableView.getCollapsedHeight() + f4) - expandableView.getMaxContentHeight()) * 0.85f;
            }
            expandableView.setActualHeight((int) (expandableView.getCollapsedHeight() + f4), true);
            ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class))).userActivity();
        }
        if (this.lastHeight > this.minDragDistance) {
            if (!this.draggedFarEnough) {
                this.draggedFarEnough = true;
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
        } else if (this.draggedFarEnough) {
            this.draggedFarEnough = false;
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
        return true;
    }

    public final void stopDragging() {
        ExpandableView expandableView = this.startingChild;
        if (expandableView != null) {
            cancelChildExpansion(expandableView, 375L);
            this.startingChild = null;
        }
        this.isDraggingDown = false;
        this.isTrackpadReverseScroll = false;
        ((ShadeRepositoryImpl) this.shadeRepository).legacyLockscreenShadeTracking.updateState(null, Boolean.FALSE);
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
        lockscreenShadeTransitionController.logger.logDragDownAborted();
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isNotificationAsCard()) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = lockscreenShadeTransitionController.nsslController;
            if (notificationStackScrollLayoutController == null) {
                notificationStackScrollLayoutController = null;
            }
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.mAmbientState.mDimmed = notificationStackScrollLayout.onKeyguard();
            if (notificationStackScrollLayout.mAnimationsEnabled) {
                notificationStackScrollLayout.mNeedsAnimation = true;
            }
            notificationStackScrollLayout.requestChildrenUpdate();
        }
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
        Iterator it = lockscreenShadeTransitionController.callbacks.iterator();
        while (it.hasNext()) {
            ((LockscreenShadeTransitionController.Callback) it.next()).onExpansionReset();
        }
    }

    public final void updateResources$1(Context context) {
        this.minDragDistance = context.getResources().getDimensionPixelSize(R.dimen.keyguard_drag_down_min_distance);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.touchSlop = viewConfiguration.getScaledTouchSlop();
        this.slopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.minDragDistance = (int) (this.touchSlop * 3);
    }
}
