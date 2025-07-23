package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Debug;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.PathInterpolator;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.animator.KeyguardTouchSecurityInjector;
import com.android.systemui.keyguard.domain.interactor.NaturalScrollingSettingObserver;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticOutline0;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.PanelSlideEventHandler;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.SecPanelSplitHelper$panelSlideEventHandler$1$1;
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
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.animation.FlingAnimationUtils;
import com.android.wm.shell.shared.animation.Interpolators;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DragDownHelper implements Gefingerpoken {
    public final Context context;
    public float dragDownAmountOnStart;
    public final LockscreenShadeTransitionController dragDownCallback;
    public boolean draggedFarEnough;
    public NotificationStackScrollLayout.AnonymousClass11 expandCallback;
    public final FalsingManager falsingManager;
    public final FlingAnimationUtils flingAnimationUtils;
    public float initialTouchX;
    public float initialTouchY;
    public boolean isDraggingDown;
    public boolean isInitialDirectionMeasured;
    public boolean isInitiallyDraggedDownard;
    public boolean isTouchConsumablePosition;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public float lastHeight;
    public final LSShadeTransitionLogger logger;
    public ValueAnimator maxDragDownAnimator;
    public int minDragDistance;
    public NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public ValueAnimator overDragDownAnimator;
    public final PluginLockMediator pluginLockMediator;
    public final KeyguardTouchSecurityInjector securityInjector;
    public final ShadeRepository shadeRepository;
    public float slopMultiplier;
    public ExpandableView startingChild;
    public float touchSlop;
    public final VelocityTracker velocityTracker = VelocityTracker.obtain();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DragDownHelper(FalsingManager falsingManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, NaturalScrollingSettingObserver naturalScrollingSettingObserver, ShadeRepository shadeRepository, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardTouchSecurityInjector keyguardTouchSecurityInjector, Context context, LSShadeTransitionLogger lSShadeTransitionLogger, PluginLockMediator pluginLockMediator) {
        this.falsingManager = falsingManager;
        this.dragDownCallback = lockscreenShadeTransitionController;
        this.shadeRepository = shadeRepository;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.securityInjector = keyguardTouchSecurityInjector;
        this.context = context;
        this.logger = lSShadeTransitionLogger;
        this.pluginLockMediator = pluginLockMediator;
        this.flingAnimationUtils = new FlingAnimationUtils(context.getResources().getDisplayMetrics(), 0.4f);
        updateResources$1(context);
    }

    public final void animateToMaxDragDown(float f, final float f2, final boolean z) {
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
        SecPanelTouchBlockHelper secPanelTouchBlockHelper = (SecPanelTouchBlockHelper) lockscreenShadeTransitionController.panelTouchBlockHelper$delegate.getValue();
        if (secPanelTouchBlockHelper != null && secPanelTouchBlockHelper.isKeyguardPanelDisabled()) {
            LockscreenShadeTransitionController.Companion.getClass();
            Log.d(LockscreenShadeTransitionController.TAG, "animateToMaxDragDown: returned");
            return;
        }
        LockscreenShadeTransitionController.Companion.getClass();
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("animateToMaxDragDown\n", Debug.getCallers(3, " "), LockscreenShadeTransitionController.TAG);
        final float f3 = lockscreenShadeTransitionController.notificationShelfTransitionDistance;
        if (!z) {
            f3 = 0.0f;
        }
        float f4 = lockscreenShadeTransitionController.dragDownAmount;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f4, f3);
        final float f5 = lockscreenShadeTransitionController.overDragAmount;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.DragDownHelper$animateToMaxDragDown$animator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DragDownHelper.this.dragDownCallback.setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Float) valueAnimator.getAnimatedValue()).floatValue());
                float f6 = f2;
                if (f6 > 0.0f || (f3 == 0.0f && f5 != 0.0f)) {
                    DragDownHelper.this.dragDownCallback.setOverDragAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(MathUtils.lerp(f5, DragDownHelper.this.dragDownCallback.panelFlingOvershootAmount * f6, ((PathInterpolator) Interpolators.FAST_OUT_SLOW_IN).getInterpolation(((Float) valueAnimator.getAnimatedValue()).floatValue())));
                }
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.DragDownHelper$animateToMaxDragDown$animator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DragDownHelper dragDownHelper = DragDownHelper.this;
                dragDownHelper.maxDragDownAnimator = null;
                if (z) {
                    LockscreenShadeTransitionController lockscreenShadeTransitionController2 = dragDownHelper.dragDownCallback;
                    if (lockscreenShadeTransitionController2.overDragAmount > 0.0f && lockscreenShadeTransitionController2.isOverDraggingAllowed()) {
                        DragDownHelper.this.springBack$1();
                        return;
                    }
                }
                DragDownHelper.this.dragDownCallback.setOverDragAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0.0f);
                if (z) {
                    DragDownHelper.this.onFinishDraggingDown();
                } else {
                    DragDownHelper.this.stopDragging();
                }
            }
        });
        if (f == 0.0f) {
            ofFloat.setDuration(300L);
        } else {
            this.flingAnimationUtils.apply(ofFloat, f4, z ? f3 : 0.0f, f, lockscreenShadeTransitionController.qS != null ? r12.getDesiredHeight() : DeviceState.getScreenHeight(this.context));
        }
        ofFloat.start();
        this.maxDragDownAnimator = ofFloat;
    }

    public final void cancelChildExpansion(final ExpandableView expandableView, long j) {
        if (expandableView.mActualHeight == expandableView.getCollapsedHeight()) {
            NotificationStackScrollLayout.AnonymousClass11 anonymousClass11 = this.expandCallback;
            if (anonymousClass11 == null) {
                anonymousClass11 = null;
            }
            anonymousClass11.setUserLockedChild(expandableView, false);
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
                NotificationStackScrollLayout.AnonymousClass11 anonymousClass112 = DragDownHelper.this.expandCallback;
                if (anonymousClass112 == null) {
                    anonymousClass112 = null;
                }
                anonymousClass112.setUserLockedChild(expandableView, false);
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
            NotificationStackScrollLayout.AnonymousClass11 anonymousClass11 = this.expandCallback;
            if (anonymousClass11 == null) {
                anonymousClass11 = null;
            }
            ExpandableView childAtRawPosition = NotificationStackScrollLayout.this.getChildAtRawPosition(f, f2);
            this.startingChild = childAtRawPosition;
            if (childAtRawPosition != null) {
                if (!this.dragDownCallback.isDragDownEnabledForView$frameworks__base__packages__SystemUI__android_common__SystemUI_core(childAtRawPosition)) {
                    this.startingChild = null;
                } else {
                    NotificationStackScrollLayout.AnonymousClass11 anonymousClass112 = this.expandCallback;
                    (anonymousClass112 != null ? anonymousClass112 : null).setUserLockedChild(this.startingChild, true);
                }
            }
        }
    }

    public final void onFinishDraggingDown() {
        String str;
        String str2;
        String str3;
        float f = 0.0f - this.initialTouchY;
        final ExpandableView expandableView = this.startingChild;
        int i = (int) f;
        final LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
        boolean canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core = lockscreenShadeTransitionController.canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        String str4 = "no entry";
        LSShadeTransitionLogger lSShadeTransitionLogger = lockscreenShadeTransitionController.logger;
        if (canDragDown$frameworks__base__packages__SystemUI__android_common__SystemUI_core) {
            LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1 = new LockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1(lockscreenShadeTransitionController);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = lockscreenShadeTransitionController.nsslController;
            if (notificationStackScrollLayoutController == null) {
                notificationStackScrollLayoutController = null;
            }
            if (notificationStackScrollLayoutController.mDynamicPrivacyController.isInLockedDownShade()) {
                lSShadeTransitionLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda0 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(3);
                LogBuffer logBuffer = lSShadeTransitionLogger.buffer;
                LogMessage obtain = logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$$ExternalSyntheticLambda0, null);
                ExpandableNotificationRow expandableNotificationRow = expandableView instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) expandableView : null;
                if (expandableNotificationRow != null && (str3 = expandableNotificationRow.mLoggingKey) != null) {
                    str4 = str3;
                }
                ((LogMessageImpl) obtain).str1 = str4;
                logBuffer.commit(obtain);
                ((StatusBarStateControllerImpl) lockscreenShadeTransitionController.statusBarStateController).setLeaveOpenOnKeyguardHide(true);
                lockscreenShadeTransitionController.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$onDraggedDown$1
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        LockscreenShadeTransitionController.this.nextHideKeyguardNeedsNoAnimation = true;
                        return false;
                    }
                }, lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1, false);
            } else {
                lSShadeTransitionLogger.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda02 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer2 = lSShadeTransitionLogger.buffer;
                LogMessage obtain2 = logBuffer2.obtain("LockscreenShadeTransitionController", logLevel2, lSShadeTransitionLogger$$ExternalSyntheticLambda02, null);
                ExpandableNotificationRow expandableNotificationRow2 = expandableView instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) expandableView : null;
                if (expandableNotificationRow2 != null && (str2 = expandableNotificationRow2.mLoggingKey) != null) {
                    str4 = str2;
                }
                ((LogMessageImpl) obtain2).str1 = str4;
                logBuffer2.commit(obtain2);
                lSShadeTransitionLogger.lockscreenGestureLogger.write(187, (int) (i / lSShadeTransitionLogger.displayMetrics.density), 0);
                new UiEventLoggerImpl().log(LockscreenGestureLogger.LockscreenUiEvent.LOCKSCREEN_PULL_SHADE_OPEN);
                if (!lockscreenShadeTransitionController.ambientState.mDozing || expandableView != null) {
                    lockscreenShadeTransitionController.goToLockedShadeInternal(expandableView, new Function1() { // from class: com.android.systemui.statusbar.LockscreenShadeTransitionController$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            View view = expandableView;
                            long longValue = ((Long) obj).longValue();
                            LockscreenShadeTransitionController.Companion companion = LockscreenShadeTransitionController.Companion;
                            if (view instanceof ExpandableNotificationRow) {
                                ((ExpandableNotificationRow) view).onExpandedByGesture(true);
                            }
                            LockscreenShadeTransitionController lockscreenShadeTransitionController2 = lockscreenShadeTransitionController;
                            ((ShadeLockscreenInteractor) lockscreenShadeTransitionController2.shadeLockscreenInteractorLazy.get()).transitionToExpandedShade(longValue, lockscreenShadeTransitionController2.ambientState.isNeedsToExpandLocksNoti());
                            ArrayList arrayList = (ArrayList) lockscreenShadeTransitionController2.callbacks;
                            int size = arrayList.size();
                            int i2 = 0;
                            while (i2 < size) {
                                Object obj2 = arrayList.get(i2);
                                i2++;
                                LockscreenShadeTransitionController.Callback callback = (LockscreenShadeTransitionController.Callback) obj2;
                                callback.setTransitionToFullShadeAmount(0.0f, true, longValue);
                                callback.setTransitionToFullShadeAmount(0.0f);
                            }
                            lockscreenShadeTransitionController2.forceApplyAmount = true;
                            LSShadeTransitionLogger lSShadeTransitionLogger2 = lockscreenShadeTransitionController2.logger;
                            lSShadeTransitionLogger2.getClass();
                            LogLevel logLevel3 = LogLevel.DEBUG;
                            LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda03 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(11);
                            LogBuffer logBuffer3 = lSShadeTransitionLogger2.buffer;
                            logBuffer3.commit(logBuffer3.obtain("LockscreenShadeTransitionController", logLevel3, lSShadeTransitionLogger$$ExternalSyntheticLambda03, null));
                            lockscreenShadeTransitionController2.setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0.0f);
                            lockscreenShadeTransitionController2.forceApplyAmount = false;
                            return Unit.INSTANCE;
                        }
                    }, lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1);
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_OPEN_NOTIFICATION_LIST, SystemUIAnalytics.DID_NOTI_SWIPE_DOWN);
                }
            }
        } else {
            lSShadeTransitionLogger.getClass();
            LogLevel logLevel3 = LogLevel.INFO;
            LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda03 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer3 = lSShadeTransitionLogger.buffer;
            LogMessage obtain3 = logBuffer3.obtain("LockscreenShadeTransitionController", logLevel3, lSShadeTransitionLogger$$ExternalSyntheticLambda03, null);
            ExpandableNotificationRow expandableNotificationRow3 = expandableView instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) expandableView : null;
            if (expandableNotificationRow3 != null && (str = expandableNotificationRow3.mLoggingKey) != null) {
                str4 = str;
            }
            ((LogMessageImpl) obtain3).str1 = str4;
            logBuffer3.commit(obtain3);
            lockscreenShadeTransitionController.setDragDownAmountAnimated(0.0f, 0L, null);
        }
        ArrayList arrayList = (ArrayList) lockscreenShadeTransitionController.callbacks;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((LockscreenShadeTransitionController.Callback) obj).onExpansionFinished();
        }
        ExpandableView expandableView2 = this.startingChild;
        if (expandableView2 != null) {
            NotificationStackScrollLayout.AnonymousClass11 anonymousClass11 = this.expandCallback;
            if (anonymousClass11 == null) {
                anonymousClass11 = null;
            }
            anonymousClass11.setUserLockedChild(expandableView2, false);
            this.startingChild = null;
        }
        this.isDraggingDown = false;
        ((ShadeRepositoryImpl) this.shadeRepository).legacyLockscreenShadeTracking.updateState(null, Boolean.FALSE);
        SecPanelSplitHelper.Companion.getClass();
        boolean z = SecPanelSplitHelper.isEnabled;
        SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = lockscreenShadeTransitionController.panelSAStatusLogInteractor;
        if (!z) {
            if (this.initialTouchY < SystemBarUtils.getStatusBarHeight(this.context)) {
                if (secPanelSAStatusLogInteractor != null) {
                    StateFlowImpl stateFlowImpl = secPanelSAStatusLogInteractor.repository._openQuickPanelFrom1DepthStatusBarInKeyguard;
                    LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
                }
            } else if (secPanelSAStatusLogInteractor != null) {
                StateFlowImpl stateFlowImpl2 = secPanelSAStatusLogInteractor.repository._openQuickPanelFrom1DepthEtcInKeyguard;
                LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl2.getValue(), 1L, stateFlowImpl2, null);
            }
        }
        SecPanelSplitHelper panelSplitHelper$1 = lockscreenShadeTransitionController.getPanelSplitHelper$1();
        if ((panelSplitHelper$1 != null ? panelSplitHelper$1.shouldQsDownInLockscreen : null) == PanelSlideEventHandler.Direction.UNDECIDED || this.initialTouchY >= SystemBarUtils.getStatusBarHeight(this.context) || secPanelSAStatusLogInteractor == null) {
            return;
        }
        StateFlowImpl stateFlowImpl3 = secPanelSAStatusLogInteractor.repository._openQuickPanelFromStatusBarInKeyguard;
        LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl3.getValue(), 1L, stateFlowImpl3, null);
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        SecPanelSplitHelper panelSplitHelper$1;
        PanelSlideEventHandler.Direction direction;
        if ((this.maxDragDownAnimator == null && this.overDragDownAnimator == null) ? false : true) {
            return true;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        this.velocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
        if (actionMasked == 0) {
            this.draggedFarEnough = false;
            this.isDraggingDown = false;
            this.startingChild = null;
            this.initialTouchY = y;
            this.initialTouchX = x;
            this.isTouchConsumablePosition = this.pluginLockMediator.isTouchConsumablePosition(x, y);
            this.isInitialDirectionMeasured = false;
            this.isInitiallyDraggedDownard = false;
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled && (panelSplitHelper$1 = lockscreenShadeTransitionController.getPanelSplitHelper$1()) != null) {
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                NotificationStackScrollLayout.AnonymousClass11 anonymousClass11 = this.expandCallback;
                if (anonymousClass11 == null) {
                    anonymousClass11 = null;
                }
                if (NotificationStackScrollLayout.this.getChildAtRawPosition(x2, y2) != null) {
                    panelSplitHelper$1.shouldQsDownInLockscreen = PanelSlideEventHandler.Direction.UNDECIDED;
                    return false;
                }
                PanelSlideEventHandler panelSlideEventHandler = panelSplitHelper$1.panelSlideEventHandler;
                panelSlideEventHandler.getClass();
                if (motionEvent.getActionMasked() != 0) {
                    direction = PanelSlideEventHandler.Direction.UNDECIDED;
                } else {
                    SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$1 = panelSlideEventHandler.panelSlideEventCallback;
                    Log.d("SecPanelSplitHelper", "shouldQSDownInLockScreen state = " + (secPanelSplitHelper$panelSlideEventHandler$1$1 != null ? Integer.valueOf(secPanelSplitHelper$panelSlideEventHandler$1$1.this$0.currentState) : null));
                    if (motionEvent.getY() > SystemBarUtils.getStatusBarHeight(panelSlideEventHandler.context)) {
                        SecPanelSplitHelper$panelSlideEventHandler$1$1 secPanelSplitHelper$panelSlideEventHandler$1$12 = panelSlideEventHandler.panelSlideEventCallback;
                        if (secPanelSplitHelper$panelSlideEventHandler$1$12 != null) {
                            secPanelSplitHelper$panelSlideEventHandler$1$12.this$0.slide$1(1);
                        }
                        Log.d("SecPanelSplitHelper", "shouldQSDownInLockScreen y, SHADE_STATE return UNDECIDED");
                        direction = PanelSlideEventHandler.Direction.UNDECIDED;
                    } else {
                        float x3 = motionEvent.getX();
                        int i = panelSlideEventHandler.displayWidthOfDivider;
                        if (x3 >= i) {
                            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "shouldQSDownInLockScreen x, displayWidthOfDivider = ", ", return RIGHT", "SecPanelSplitHelper");
                            direction = PanelSlideEventHandler.Direction.RIGHT;
                        } else {
                            boolean isReversed = panelSlideEventHandler.secPanelSplitHelper.isReversed();
                            Log.d("SecPanelSplitHelper", "shouldQSDownInLockScreen else, isReversed? " + isReversed + ", return " + (isReversed ? "LEFT" : "UNDECIDED"));
                            direction = isReversed ? PanelSlideEventHandler.Direction.LEFT : PanelSlideEventHandler.Direction.UNDECIDED;
                        }
                    }
                }
                panelSplitHelper$1.shouldQsDownInLockscreen = direction;
                int i2 = (!panelSplitHelper$1.isReversed() || panelSplitHelper$1.secQsUiDisplayModeInteractor.isTablet()) ? 0 : 1;
                if (panelSplitHelper$1.shouldQsDownInLockscreen != PanelSlideEventHandler.Direction.UNDECIDED) {
                    panelSplitHelper$1.stateOnDown = i2 ^ 1;
                    panelSplitHelper$1.stateToChange = i2;
                }
            }
        } else {
            if (actionMasked == 1) {
                this.isTouchConsumablePosition = false;
                return false;
            }
            if (actionMasked == 2) {
                float f = y - this.initialTouchY;
                float f2 = motionEvent.getClassification() == 1 ? this.touchSlop * this.slopMultiplier : this.touchSlop;
                if (!this.isInitialDirectionMeasured && (Math.abs(f) > f2 || Math.abs(x - this.initialTouchX) > f2)) {
                    this.isInitiallyDraggedDownard = f > f2 && f > Math.abs(x - this.initialTouchX);
                    this.isInitialDirectionMeasured = true;
                }
                KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
                if (keyguardUpdateMonitor.mDeviceProvisioned && ((!keyguardUpdateMonitor.isFingerprintDetectionRunning() || !this.securityInjector.isFingerprintArea(motionEvent)) && !lockscreenShadeTransitionController.isKeyguardAnimatorStarted && this.isInitiallyDraggedDownard && f > f2 && f > Math.abs(x - this.initialTouchX))) {
                    this.isDraggingDown = true;
                    captureStartingChild$1(this.initialTouchX, this.initialTouchY);
                    this.initialTouchY = y;
                    this.initialTouchX = x;
                    lockscreenShadeTransitionController.onDragDownStarted$frameworks__base__packages__SystemUI__android_common__SystemUI_core(this.startingChild);
                    this.dragDownAmountOnStart = lockscreenShadeTransitionController.dragDownAmount;
                    boolean z = (this.startingChild != null || lockscreenShadeTransitionController.isDragDownAnywhereEnabled$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) && !this.isTouchConsumablePosition;
                    if (z) {
                        ((ShadeRepositoryImpl) this.shadeRepository).legacyLockscreenShadeTracking.updateState(null, Boolean.TRUE);
                    }
                    return z;
                }
                float f3 = this.initialTouchX;
                if (((StatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class)).getState() == 1) {
                    float abs = (float) Math.abs(f);
                    if (f < 0.0f && abs > f2 && abs > Math.abs(x - f3)) {
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.notificationStackScrollLayoutController;
                        if (notificationStackScrollLayoutController == null) {
                            notificationStackScrollLayoutController = null;
                        }
                        float f4 = this.initialTouchX;
                        float f5 = this.initialTouchY;
                        notificationStackScrollLayoutController.getClass();
                        int i3 = SceneContainerFlag.$r8$clinit;
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        if (!notificationStackScrollLayoutController.mView.isBelowLastNotification(f4, f5)) {
                            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.notificationStackScrollLayoutController;
                            if (notificationStackScrollLayoutController2 == null) {
                                notificationStackScrollLayoutController2 = null;
                            }
                            if (!notificationStackScrollLayoutController2.isLeftOrRightOutOfNSSL(this.initialTouchX)) {
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = this.notificationStackScrollLayoutController;
                                if (notificationStackScrollLayoutController3 == null) {
                                    notificationStackScrollLayoutController3 = null;
                                }
                                notificationStackScrollLayoutController3.getClass();
                                if (notificationStackScrollLayoutController3.mView.getTopPadding() <= this.initialTouchY) {
                                    LSShadeTransitionLogger lSShadeTransitionLogger = this.logger;
                                    lSShadeTransitionLogger.getClass();
                                    LogLevel logLevel = LogLevel.INFO;
                                    LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda0 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(0);
                                    LogBuffer logBuffer = lSShadeTransitionLogger.buffer;
                                    logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$$ExternalSyntheticLambda0, null));
                                    return !this.isTouchConsumablePosition;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public final void springBack$1() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.dragDownCallback.overDragAmount, 0.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.DragDownHelper$springBack$animator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DragDownHelper.this.dragDownCallback.setOverDragAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.DragDownHelper$springBack$animator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DragDownHelper.this.dragDownCallback.setOverDragAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0.0f);
                DragDownHelper dragDownHelper = DragDownHelper.this;
                dragDownHelper.overDragDownAnimator = null;
                dragDownHelper.onFinishDraggingDown();
            }
        });
        ofFloat.setDuration(250L);
        ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofFloat.start();
        this.overDragDownAnimator = ofFloat;
    }

    public final void stopDragging() {
        ExpandableView expandableView = this.startingChild;
        if (expandableView != null) {
            cancelChildExpansion(expandableView, 375L);
            this.startingChild = null;
        }
        this.isDraggingDown = false;
        ((ShadeRepositoryImpl) this.shadeRepository).legacyLockscreenShadeTracking.updateState(null, Boolean.FALSE);
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.dragDownCallback;
        LSShadeTransitionLogger lSShadeTransitionLogger = lockscreenShadeTransitionController.logger;
        lSShadeTransitionLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda0 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(17);
        LogBuffer logBuffer = lSShadeTransitionLogger.buffer;
        logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$$ExternalSyntheticLambda0, null));
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
