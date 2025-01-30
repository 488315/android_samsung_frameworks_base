package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.IndentingPrintWriter;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.android.app.animation.Interpolators;
import com.android.systemui.DisplayCutoutBaseView$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeQsExpansionListener;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PulseExpansionHandler implements Gefingerpoken, Dumpable {
    public static final int SPRING_BACK_ANIMATION_LENGTH_MS;
    public boolean bouncerShowing;
    public final KeyguardBypassController bypassController;
    public final FalsingCollector falsingCollector;
    public final FalsingManager falsingManager;
    public final HeadsUpManagerPhone headsUpManager;
    public boolean isExpanding;
    public boolean leavingLockscreen;
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public final PowerManager mPowerManager;
    public boolean mPulsing;
    public ExpandableView mStartingChild;
    public final int[] mTemp2 = new int[2];
    public Runnable pulseExpandAbortListener;
    public boolean qsExpanded;
    public NotificationStackScrollLayoutController stackScrollerController;
    public final StatusBarStateController statusBarStateController;
    public float touchSlop;
    public VelocityTracker velocityTracker;
    public final NotificationWakeUpCoordinator wakeUpCoordinator;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        SPRING_BACK_ANIMATION_LENGTH_MS = 375;
    }

    public PulseExpansionHandler(final Context context, NotificationWakeUpCoordinator notificationWakeUpCoordinator, KeyguardBypassController keyguardBypassController, HeadsUpManagerPhone headsUpManagerPhone, NotificationRoundnessManager notificationRoundnessManager, ConfigurationController configurationController, StatusBarStateController statusBarStateController, FalsingManager falsingManager, ShadeExpansionStateManager shadeExpansionStateManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, FalsingCollector falsingCollector, DumpManager dumpManager) {
        this.wakeUpCoordinator = notificationWakeUpCoordinator;
        this.bypassController = keyguardBypassController;
        this.headsUpManager = headsUpManagerPhone;
        this.statusBarStateController = statusBarStateController;
        this.falsingManager = falsingManager;
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.falsingCollector = falsingCollector;
        context.getResources().getDimensionPixelSize(R.dimen.keyguard_drag_down_min_distance);
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.PulseExpansionHandler.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i = PulseExpansionHandler.SPRING_BACK_ANIMATION_LENGTH_MS;
                PulseExpansionHandler pulseExpansionHandler = PulseExpansionHandler.this;
                pulseExpansionHandler.getClass();
                context.getResources().getDimensionPixelSize(R.dimen.keyguard_drag_down_min_distance);
                pulseExpansionHandler.touchSlop = ViewConfiguration.get(r2).getScaledTouchSlop();
            }
        });
        shadeExpansionStateManager.addQsExpansionListener(new ShadeQsExpansionListener() { // from class: com.android.systemui.statusbar.PulseExpansionHandler.2
            @Override // com.android.systemui.shade.ShadeQsExpansionListener
            public final void onQsExpansionChanged(boolean z) {
                PulseExpansionHandler pulseExpansionHandler = PulseExpansionHandler.this;
                if (pulseExpansionHandler.qsExpanded != z) {
                    pulseExpansionHandler.qsExpanded = z;
                }
            }
        });
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        dumpManager.registerDumpable(this);
    }

    public final void cancelExpansion() {
        setExpanding(false);
        this.falsingCollector.getClass();
        ExpandableView expandableView = this.mStartingChild;
        if (expandableView != null) {
            Intrinsics.checkNotNull(expandableView);
            reset(expandableView, SPRING_BACK_ANIMATION_LENGTH_MS);
            this.mStartingChild = null;
        }
        this.lockscreenShadeTransitionController.finishPulseAnimation(true);
        this.wakeUpCoordinator.setNotificationsVisibleForExpansion(false, true, false);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("PulseExpansionHandler:");
        indentingPrintWriter.increaseIndent();
        DisplayCutoutBaseView$$ExternalSyntheticOutline0.m93m("isExpanding: ", this.isExpanding, indentingPrintWriter);
        DisplayCutoutBaseView$$ExternalSyntheticOutline0.m93m("leavingLockscreen: ", this.leavingLockscreen, indentingPrintWriter);
        DisplayCutoutBaseView$$ExternalSyntheticOutline0.m93m("mPulsing: ", this.mPulsing, indentingPrintWriter);
        DisplayCutoutBaseView$$ExternalSyntheticOutline0.m93m("qsExpanded: ", this.qsExpanded, indentingPrintWriter);
        DisplayCutoutBaseView$$ExternalSyntheticOutline0.m93m("bouncerShowing: ", this.bouncerShowing, indentingPrintWriter);
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return (this.wakeUpCoordinator.getCanShowPulsingHuns() && !this.qsExpanded && !this.bouncerShowing) && startExpansion(motionEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:82:0x0141  */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        FalsingManager falsingManager;
        VelocityTracker velocityTracker;
        boolean z2 = (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) && this.isExpanding;
        ExpandableView expandableView = this.mStartingChild;
        boolean z3 = (expandableView != null && expandableView.showingPulsing()) || this.bypassController.canBypass();
        NotificationWakeUpCoordinator notificationWakeUpCoordinator = this.wakeUpCoordinator;
        if ((!((!notificationWakeUpCoordinator.getCanShowPulsingHuns() || this.qsExpanded || this.bouncerShowing) ? false : true) || !z3) && !z2) {
            return false;
        }
        if (this.velocityTracker == null || !this.isExpanding || motionEvent.getActionMasked() == 0) {
            return startExpansion(motionEvent);
        }
        VelocityTracker velocityTracker2 = this.velocityTracker;
        Intrinsics.checkNotNull(velocityTracker2);
        velocityTracker2.addMovement(motionEvent);
        float y = motionEvent.getY() - this.mInitialTouchY;
        int actionMasked = motionEvent.getActionMasked();
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.lockscreenShadeTransitionController;
        if (actionMasked == 1) {
            VelocityTracker velocityTracker3 = this.velocityTracker;
            Intrinsics.checkNotNull(velocityTracker3);
            velocityTracker3.computeCurrentVelocity(1000);
            StatusBarStateController statusBarStateController = this.statusBarStateController;
            if (y > 0.0f) {
                VelocityTracker velocityTracker4 = this.velocityTracker;
                Intrinsics.checkNotNull(velocityTracker4);
                if (velocityTracker4.getYVelocity() > -1000.0f && statusBarStateController.getState() != 0) {
                    z = true;
                    falsingManager = this.falsingManager;
                    if (falsingManager.isUnlockingDisabled() && !falsingManager.isFalseTouch(2) && z) {
                        ExpandableView expandableView2 = this.mStartingChild;
                        if (expandableView2 != null) {
                            if (expandableView2 instanceof ExpandableNotificationRow) {
                                ((ExpandableNotificationRow) expandableView2).setUserLocked(false);
                            }
                            this.mStartingChild = null;
                        }
                        if (statusBarStateController.isDozing()) {
                            if (!(notificationWakeUpCoordinator.outputLinearDozeAmount == 0.0f)) {
                                notificationWakeUpCoordinator.willWakeUp = true;
                            }
                            PowerManager powerManager = this.mPowerManager;
                            Intrinsics.checkNotNull(powerManager);
                            powerManager.wakeUp(SystemClock.uptimeMillis(), 4, "com.android.systemui:PULSEDRAG");
                        }
                        lockscreenShadeTransitionController.goToLockedShade(expandableView2, false);
                        lockscreenShadeTransitionController.finishPulseAnimation(false);
                        this.leavingLockscreen = true;
                        setExpanding(false);
                        ExpandableView expandableView3 = this.mStartingChild;
                        if (expandableView3 instanceof ExpandableNotificationRow) {
                            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView3;
                            Intrinsics.checkNotNull(expandableNotificationRow);
                            expandableNotificationRow.onExpandedByGesture(true);
                        }
                    } else {
                        cancelExpansion();
                    }
                    velocityTracker = this.velocityTracker;
                    if (velocityTracker != null) {
                        velocityTracker.recycle();
                    }
                    this.velocityTracker = null;
                }
            }
            z = false;
            falsingManager = this.falsingManager;
            if (falsingManager.isUnlockingDisabled()) {
            }
            cancelExpansion();
            velocityTracker = this.velocityTracker;
            if (velocityTracker != null) {
            }
            this.velocityTracker = null;
        } else if (actionMasked == 2) {
            float max = Math.max(y, 0.0f);
            ExpandableView expandableView4 = this.mStartingChild;
            if (expandableView4 != null) {
                expandableView4.setActualHeight(Math.min((int) (expandableView4.getCollapsedHeight() + max), expandableView4.getMaxContentHeight()), true);
            } else {
                notificationWakeUpCoordinator.setNotificationsVisibleForExpansion(y > ((float) lockscreenShadeTransitionController.fullTransitionDistance), true, true);
            }
            lockscreenShadeTransitionController.setPulseHeight(max, false);
        } else if (actionMasked == 3) {
            cancelExpansion();
            VelocityTracker velocityTracker5 = this.velocityTracker;
            if (velocityTracker5 != null) {
                velocityTracker5.recycle();
            }
            this.velocityTracker = null;
        }
        return this.isExpanding;
    }

    public final void reset(final ExpandableView expandableView, long j) {
        if (expandableView.mActualHeight == expandableView.getCollapsedHeight()) {
            if (expandableView instanceof ExpandableNotificationRow) {
                ((ExpandableNotificationRow) expandableView).setUserLocked(false);
            }
        } else {
            ValueAnimator ofInt = ValueAnimator.ofInt(expandableView.mActualHeight, expandableView.getCollapsedHeight());
            ofInt.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            ofInt.setDuration(j);
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.PulseExpansionHandler$reset$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ExpandableView.this.setActualHeight(((Integer) valueAnimator.getAnimatedValue()).intValue(), true);
                }
            });
            ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.PulseExpansionHandler$reset$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    PulseExpansionHandler pulseExpansionHandler = PulseExpansionHandler.this;
                    ExpandableView expandableView2 = expandableView;
                    int i = PulseExpansionHandler.SPRING_BACK_ANIMATION_LENGTH_MS;
                    pulseExpansionHandler.getClass();
                    if (expandableView2 instanceof ExpandableNotificationRow) {
                        ((ExpandableNotificationRow) expandableView2).setUserLocked(false);
                    }
                }
            });
            ofInt.start();
        }
    }

    public final void setExpanding(boolean z) {
        boolean z2 = this.isExpanding != z;
        this.isExpanding = z;
        KeyguardBypassController keyguardBypassController = this.bypassController;
        keyguardBypassController.isPulseExpanding = z;
        if (z2) {
            if (z) {
                LockscreenShadeTransitionController lockscreenShadeTransitionController = this.lockscreenShadeTransitionController;
                LSShadeTransitionLogger lSShadeTransitionLogger = lockscreenShadeTransitionController.logger;
                lSShadeTransitionLogger.logPulseExpansionStarted();
                ValueAnimator valueAnimator = lockscreenShadeTransitionController.pulseHeightAnimator;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    lSShadeTransitionLogger.logAnimationCancelled(true);
                    valueAnimator.cancel();
                }
            } else if (!this.leavingLockscreen) {
                keyguardBypassController.maybePerformPendingUnlock();
                Runnable runnable = this.pulseExpandAbortListener;
                if (runnable != null) {
                    runnable.run();
                }
            }
            this.headsUpManager.unpinAll();
        }
    }

    public final boolean startExpansion(MotionEvent motionEvent) {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        VelocityTracker velocityTracker = this.velocityTracker;
        Intrinsics.checkNotNull(velocityTracker);
        velocityTracker.addMovement(motionEvent);
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int actionMasked = motionEvent.getActionMasked();
        ExpandableView expandableView = null;
        if (actionMasked == 0) {
            setExpanding(false);
            this.leavingLockscreen = false;
            this.mStartingChild = null;
            this.mInitialTouchY = y;
            this.mInitialTouchX = x;
        } else if (actionMasked == 1) {
            VelocityTracker velocityTracker2 = this.velocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.recycle();
            }
            this.velocityTracker = null;
            setExpanding(false);
        } else if (actionMasked == 2) {
            float f = y - this.mInitialTouchY;
            if (f > this.touchSlop && f > Math.abs(x - this.mInitialTouchX)) {
                this.falsingCollector.getClass();
                setExpanding(true);
                float f2 = this.mInitialTouchX;
                float f3 = this.mInitialTouchY;
                if (this.mStartingChild == null && !this.bypassController.getBypassEnabled()) {
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.stackScrollerController;
                    if (notificationStackScrollLayoutController == null) {
                        notificationStackScrollLayoutController = null;
                    }
                    notificationStackScrollLayoutController.mView.getLocationOnScreen(this.mTemp2);
                    float f4 = f2 + r7[0];
                    float f5 = f3 + r7[1];
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = this.stackScrollerController;
                    if (notificationStackScrollLayoutController2 == null) {
                        notificationStackScrollLayoutController2 = null;
                    }
                    ExpandableView childAtRawPosition = notificationStackScrollLayoutController2.mView.getChildAtRawPosition(f4, f5);
                    if (childAtRawPosition != null && childAtRawPosition.isContentExpandable()) {
                        expandableView = childAtRawPosition;
                    }
                    this.mStartingChild = expandableView;
                    if (expandableView != null && (expandableView instanceof ExpandableNotificationRow)) {
                        ((ExpandableNotificationRow) expandableView).setUserLocked(true);
                    }
                }
                this.mInitialTouchY = y;
                this.mInitialTouchX = x;
                return true;
            }
        } else if (actionMasked == 3) {
            VelocityTracker velocityTracker3 = this.velocityTracker;
            if (velocityTracker3 != null) {
                velocityTracker3.recycle();
            }
            this.velocityTracker = null;
            setExpanding(false);
        }
        return false;
    }
}
