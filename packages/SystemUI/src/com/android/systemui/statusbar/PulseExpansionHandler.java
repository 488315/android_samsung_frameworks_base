package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.util.IndentingPrintWriter;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.android.app.animation.Interpolators;
import com.android.systemui.Dumpable;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PulseExpansionHandler implements Gefingerpoken, Dumpable {
    public static final int SPRING_BACK_ANIMATION_LENGTH_MS;
    public boolean bouncerShowing;
    public final KeyguardBypassController bypassController;
    public final FalsingManager falsingManager;
    public final HeadsUpManager headsUpManager;
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
    public final ShadeInteractor shadeInteractor;
    public NotificationStackScrollLayoutController stackScrollerController;
    public final StatusBarStateController statusBarStateController;
    public float touchSlop;
    public VelocityTracker velocityTracker;
    public final NotificationWakeUpCoordinator wakeUpCoordinator;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public PulseExpansionHandler(final Context context, NotificationWakeUpCoordinator notificationWakeUpCoordinator, KeyguardBypassController keyguardBypassController, HeadsUpManager headsUpManager, ConfigurationController configurationController, StatusBarStateController statusBarStateController, FalsingManager falsingManager, ShadeInteractor shadeInteractor, LockscreenShadeTransitionController lockscreenShadeTransitionController, DumpManager dumpManager) {
        this.wakeUpCoordinator = notificationWakeUpCoordinator;
        this.bypassController = keyguardBypassController;
        this.headsUpManager = headsUpManager;
        this.statusBarStateController = statusBarStateController;
        this.falsingManager = falsingManager;
        this.shadeInteractor = shadeInteractor;
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
        context.getResources().getDimensionPixelSize(R.dimen.keyguard_drag_down_min_distance);
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.PulseExpansionHandler.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                Context context2 = context;
                int i = PulseExpansionHandler.SPRING_BACK_ANIMATION_LENGTH_MS;
                PulseExpansionHandler pulseExpansionHandler = PulseExpansionHandler.this;
                pulseExpansionHandler.getClass();
                context2.getResources().getDimensionPixelSize(R.dimen.keyguard_drag_down_min_distance);
                pulseExpansionHandler.touchSlop = ViewConfiguration.get(context2).getScaledTouchSlop();
            }
        });
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        dumpManager.registerDumpable(this);
    }

    public final boolean canHandleMotionEvent() {
        return (!this.wakeUpCoordinator.getCanShowPulsingHuns() || ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isQsExpanded().getValue()).booleanValue() || this.bouncerShowing) ? false : true;
    }

    public final void cancelExpansion() {
        setExpanding(false);
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
        indentingPrintWriter.println("isExpanding: " + this.isExpanding);
        indentingPrintWriter.println("leavingLockscreen: " + this.leavingLockscreen);
        indentingPrintWriter.println("mPulsing: " + this.mPulsing);
        indentingPrintWriter.println("bouncerShowing: " + this.bouncerShowing);
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return canHandleMotionEvent() && startExpansion(motionEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x012b  */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r11) {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.PulseExpansionHandler.onTouchEvent(android.view.MotionEvent):boolean");
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
            ((BaseHeadsUpManager) this.headsUpManager).unpinAll();
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
