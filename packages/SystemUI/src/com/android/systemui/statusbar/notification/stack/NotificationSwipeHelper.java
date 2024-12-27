package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.SwipeHelper;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.NotificationSAUtil;
import com.android.systemui.util.SystemUIAnalytics;
import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationSwipeHelper extends SwipeHelper implements NotificationSwipeActionHelper {
    protected static final long COVER_MENU_DELAY = 4000;
    public static final SourceType$Companion$from$1 SWIPE_DISMISS = SourceType.from("SwipeDismiss");
    public final NotificationCallback mCallback;
    public WeakReference mCurrMenuRowRef;
    public final NotificationSwipeHelper$$ExternalSyntheticLambda0 mFalsingCheck;
    public boolean mIsExpanded;
    public View mMenuExposedView;
    public final NotificationMenuRowPlugin.OnMenuEventListener mMenuListener;
    public final NotificationRoundnessManager mNotificationRoundnessManager;
    public boolean mPulsing;
    public View mTranslatingParentView;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Builder {
        public final DumpManager mDumpManager;
        public final FalsingManager mFalsingManager;
        public final FeatureFlags mFeatureFlags;
        public NotificationCallback mNotificationCallback;
        public final NotificationRoundnessManager mNotificationRoundnessManager;
        public NotificationMenuRowPlugin.OnMenuEventListener mOnMenuEventListener;
        public final Resources mResources;
        public final ViewConfiguration mViewConfiguration;

        public Builder(Resources resources, ViewConfiguration viewConfiguration, DumpManager dumpManager, FalsingManager falsingManager, FeatureFlags featureFlags, NotificationRoundnessManager notificationRoundnessManager) {
            this.mResources = resources;
            this.mViewConfiguration = viewConfiguration;
            this.mDumpManager = dumpManager;
            this.mFalsingManager = falsingManager;
            this.mFeatureFlags = featureFlags;
            this.mNotificationRoundnessManager = notificationRoundnessManager;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface NotificationCallback extends SwipeHelper.Callback {
    }

    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper$$ExternalSyntheticLambda0] */
    public NotificationSwipeHelper(Resources resources, ViewConfiguration viewConfiguration, FalsingManager falsingManager, FeatureFlags featureFlags, NotificationCallback notificationCallback, NotificationMenuRowPlugin.OnMenuEventListener onMenuEventListener, NotificationRoundnessManager notificationRoundnessManager) {
        super(notificationCallback, resources, viewConfiguration, falsingManager, featureFlags);
        this.mNotificationRoundnessManager = notificationRoundnessManager;
        this.mMenuListener = onMenuEventListener;
        this.mCallback = notificationCallback;
        this.mFalsingCheck = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NotificationSwipeHelper.this.resetExposedMenuView(true, true);
            }
        };
    }

    public static boolean isTouchInView(View view, MotionEvent motionEvent) {
        if (view == null) {
            return false;
        }
        int height = view instanceof ExpandableView ? ((ExpandableView) view).mActualHeight : view.getHeight();
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        return new Rect(i, i2, view.getWidth() + i, height + i2).contains(rawX, rawY);
    }

    @Override // com.android.systemui.SwipeHelper
    public Animator createTranslationAnimation(View view, float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        return super.createTranslationAnimation(view, f, animatorUpdateListener);
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper
    public final void dismiss(View view, float f) {
        dismissChild(view, f, !swipedFastEnough());
        if (view instanceof ExpandableNotificationRow) {
            NotificationSAUtil.sendCancelLog(SystemUIAnalytics.EID_QPNE_CANCEL_NOTIFICATION_DISMISS, ((ExpandableNotificationRow) view).mEntry);
        }
    }

    @Override // com.android.systemui.SwipeHelper
    public final void dismissChild(View view, float f, boolean z) {
        superDismissChild(view, f, z);
        NotificationStackScrollLayoutController.AnonymousClass11 anonymousClass11 = (NotificationStackScrollLayoutController.AnonymousClass11) this.mCallback;
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
        if (notificationStackScrollLayout.mIsExpanded && notificationStackScrollLayout.mAmbientState.mDozeAmount == 0.0f) {
            anonymousClass11.handleChildViewDismissed(view);
        } else {
            Log.d("NotificationSwipeHelper", "dismissChild, but not shouldDismissQuickly");
        }
        NotificationStackScrollLayoutController.this.mNotificationGutsManager.closeAndSaveGuts(true, false, false, false);
        handleMenuCoveredOrDismissed();
    }

    public final NotificationMenuRowPlugin getCurrentMenuRow() {
        WeakReference weakReference = this.mCurrMenuRowRef;
        if (weakReference == null) {
            return null;
        }
        return (NotificationMenuRowPlugin) weakReference.get();
    }

    @Override // com.android.systemui.SwipeHelper
    public float getEscapeVelocity() {
        return super.getEscapeVelocity();
    }

    public Runnable getFalsingCheck() {
        return this.mFalsingCheck;
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    @Override // com.android.systemui.SwipeHelper
    public final float getTotalTranslationLength(View view) {
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
        if (!notificationStackScrollLayout.mDismissUsingRowTranslationX) {
            return view.getMeasuredWidth();
        }
        float measuredWidth = view.getMeasuredWidth();
        float measuredWidth2 = notificationStackScrollLayout.getMeasuredWidth();
        return measuredWidth2 - ((measuredWidth2 - measuredWidth) / 2.0f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.SwipeHelper
    public final float getTranslation(View view) {
        if (view instanceof SwipeableView) {
            return ((ExpandableNotificationRow) ((SwipeableView) view)).getTranslation();
        }
        return 0.0f;
    }

    @Override // com.android.systemui.SwipeHelper
    public Animator getViewTranslationAnimator(View view, float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        return super.getViewTranslationAnimator(view, f, animatorUpdateListener);
    }

    public void handleMenuCoveredOrDismissed() {
        View view = this.mMenuExposedView;
        if (view == null || view != this.mTranslatingParentView) {
            return;
        }
        this.mMenuExposedView = null;
    }

    public void handleMenuRowSwipe(MotionEvent motionEvent, View view, float f, NotificationMenuRowPlugin notificationMenuRowPlugin) {
        if (!notificationMenuRowPlugin.shouldShowMenu()) {
            if (isDismissGesture(motionEvent)) {
                dismiss(view, f);
                return;
            } else {
                snapClosed(view, f);
                notificationMenuRowPlugin.onSnapClosed();
                return;
            }
        }
        if (notificationMenuRowPlugin.isSnappedAndOnSameSide()) {
            boolean isDismissGesture = isDismissGesture(motionEvent);
            if (notificationMenuRowPlugin.isWithinSnapMenuThreshold() && !isDismissGesture) {
                notificationMenuRowPlugin.onSnapOpen();
                snapChild(view, notificationMenuRowPlugin.getMenuSnapTarget(), f);
                return;
            } else if (!isDismissGesture || notificationMenuRowPlugin.shouldSnapBack()) {
                snapClosed(view, f);
                notificationMenuRowPlugin.onSnapClosed();
                return;
            } else {
                dismiss(view, f);
                notificationMenuRowPlugin.onDismiss();
                return;
            }
        }
        Log.d("NotificationSwipeHelper", "handleSwipeFromClosedState start");
        boolean isDismissGesture2 = isDismissGesture(motionEvent);
        boolean isTowardsMenu = notificationMenuRowPlugin.isTowardsMenu(f);
        boolean z = false;
        boolean z2 = getEscapeVelocity() <= Math.abs(f);
        boolean z3 = !notificationMenuRowPlugin.canBeDismissed() && ((double) (motionEvent.getEventTime() - motionEvent.getDownTime())) >= 200.0d;
        boolean z4 = isTowardsMenu && !isDismissGesture2;
        boolean z5 = (!swipedFarEnough() && notificationMenuRowPlugin.isSwipedEnoughToShowMenu() && (!z2 || z3)) || ((z2 && !isTowardsMenu && !isDismissGesture2) && (notificationMenuRowPlugin.shouldShowGutsOnSnapOpen() || (this.mIsExpanded && !this.mPulsing)));
        int menuSnapTarget = notificationMenuRowPlugin.getMenuSnapTarget();
        if (z5 && !isFalseGesture()) {
            z = true;
        }
        if ((z4 || z) && menuSnapTarget != 0) {
            snapChild(view, menuSnapTarget, f);
            notificationMenuRowPlugin.onSnapOpen();
        } else if (!isDismissGesture2 || isTowardsMenu) {
            snapClosed(view, f);
            notificationMenuRowPlugin.onSnapClosed();
        } else {
            if (!notificationMenuRowPlugin.getHasPopped()) {
                view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
            }
            dismiss(view, f);
            notificationMenuRowPlugin.onDismiss();
        }
    }

    @Override // com.android.systemui.SwipeHelper
    public final boolean handleUpEvent(MotionEvent motionEvent, View view, float f) {
        NotificationMenuRowPlugin currentMenuRow = getCurrentMenuRow();
        if (this.mLongPressSent && (view instanceof ExpandableNotificationRow) && ((ExpandableNotificationRow) view).mIsPinned) {
            Log.d("NotificationSwipeHelper", view + " : animView is pinned and long pressed");
            return true;
        }
        if (currentMenuRow == null) {
            return false;
        }
        currentMenuRow.onTouchEnd();
        handleMenuRowSwipe(motionEvent, view, f, currentMenuRow);
        return true;
    }

    public void initializeRow(SwipeableView swipeableView) {
        if (((ExpandableNotificationRow) swipeableView).mEntry.hasFinishedInitialization()) {
            NotificationMenuRowPlugin createMenu = ((ExpandableNotificationRow) swipeableView).createMenu();
            setCurrentMenuRow(createMenu);
            if (createMenu != null) {
                createMenu.setMenuClickListener(this.mMenuListener);
                createMenu.onTouchStart();
            }
        }
    }

    @Override // com.android.systemui.SwipeHelper
    public final void onChildSnappedBack(float f, View view) {
        super.onChildSnappedBack(f, view);
        NotificationMenuRowPlugin currentMenuRow = getCurrentMenuRow();
        if (currentMenuRow != null && f == 0.0f) {
            currentMenuRow.resetMenu();
            setCurrentMenuRow(null);
        }
        InteractionJankMonitor.getInstance().end(4);
    }

    @Override // com.android.systemui.SwipeHelper
    public final void onDismissChildWithAnimationFinished() {
        InteractionJankMonitor.getInstance().end(4);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.SwipeHelper
    public final void onDownUpdate(ExpandableView expandableView) {
        this.mTranslatingParentView = expandableView;
        NotificationMenuRowPlugin currentMenuRow = getCurrentMenuRow();
        if (currentMenuRow != null) {
            currentMenuRow.onTouchStart();
        }
        setCurrentMenuRow(null);
        getHandler().removeCallbacks(getFalsingCheck());
        resetExposedMenuView(true, false);
        if (expandableView instanceof SwipeableView) {
            initializeRow((SwipeableView) expandableView);
        }
    }

    @Override // com.android.systemui.SwipeHelper, com.android.systemui.Gefingerpoken
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z = this.mIsSwiping;
        boolean onInterceptTouchEvent = super.onInterceptTouchEvent(motionEvent);
        ExpandableView expandableView = this.mIsSwiping ? this.mTouchedView : null;
        if (!z && expandableView != null) {
            InteractionJankMonitor.getInstance().begin(expandableView, 4);
        }
        return onInterceptTouchEvent;
    }

    @Override // com.android.systemui.SwipeHelper
    public final void onMoveUpdate(float f) {
        getHandler().removeCallbacks(getFalsingCheck());
        NotificationMenuRowPlugin currentMenuRow = getCurrentMenuRow();
        if (currentMenuRow != null) {
            currentMenuRow.onTouchMove(f);
        }
    }

    @Override // com.android.systemui.SwipeHelper
    public final void prepareDismissAnimation(View view, Animator animator) {
        if ((view instanceof ExpandableNotificationRow) && this.mNotificationRoundnessManager.mIsClearAllInProgress) {
            final ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            animator.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator2) {
                    expandableNotificationRow.requestRoundnessReset(NotificationSwipeHelper.SWIPE_DISMISS);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator2) {
                    expandableNotificationRow.requestRoundnessReset(NotificationSwipeHelper.SWIPE_DISMISS);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator2) {
                    expandableNotificationRow.requestRoundness(1.0f, 1.0f, NotificationSwipeHelper.SWIPE_DISMISS);
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void resetExposedMenuView(boolean z, boolean z2) {
        if (shouldResetMenu(z2)) {
            View view = this.mMenuExposedView;
            if (z) {
                Animator viewTranslationAnimator = getViewTranslationAnimator(view, 0.0f, null);
                if (viewTranslationAnimator != null) {
                    viewTranslationAnimator.start();
                }
            } else if (view instanceof SwipeableView) {
                SwipeableView swipeableView = (SwipeableView) view;
                swipeableView.getClass();
                ((ExpandableNotificationRow) swipeableView).resetTranslation();
            }
            this.mMenuExposedView = null;
        }
    }

    public void setCurrentMenuRow(NotificationMenuRowPlugin notificationMenuRowPlugin) {
        this.mCurrMenuRowRef = notificationMenuRowPlugin != null ? new WeakReference(notificationMenuRowPlugin) : null;
    }

    public void setTranslatingParentView(View view) {
        this.mTranslatingParentView = view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.SwipeHelper
    public final void setTranslation(float f, View view) {
        if (view instanceof SwipeableView) {
            ((ExpandableNotificationRow) ((SwipeableView) view)).setTranslation(f);
        }
    }

    public boolean shouldResetMenu(boolean z) {
        View view = this.mMenuExposedView;
        if (view != null) {
            return z || view != this.mTranslatingParentView;
        }
        return false;
    }

    @Override // com.android.systemui.SwipeHelper
    public final void snapChild(View view, float f, float f2) {
        if (view instanceof SwipeableView) {
            superSnapChild(view, f, f2);
        }
        this.mCallback.getClass();
        if (f == 0.0f) {
            handleMenuCoveredOrDismissed();
        }
    }

    public void snapClosed(View view, float f) {
        snapChild(view, 0.0f, f);
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper
    public final void snapOpen(View view, int i, float f) {
        snapChild(view, i, f);
    }

    @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper
    public final void snooze(StatusBarNotification statusBarNotification, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
        NotificationStackScrollLayoutController.this.mNotificationsController.setNotificationSnoozed(statusBarNotification, snoozeOption);
    }

    public void superDismissChild(View view, float f, boolean z) {
        super.dismissChild(view, f, z);
    }

    public void superSnapChild(View view, float f, float f2) {
        super.snapChild(view, f, f2);
    }

    @Override // com.android.systemui.SwipeHelper
    public boolean swipedFarEnough() {
        return super.swipedFarEnough();
    }

    @Override // com.android.systemui.SwipeHelper
    public boolean swipedFastEnough() {
        return super.swipedFastEnough();
    }

    @Override // com.android.systemui.SwipeHelper
    public final void updateSwipeProgressAlpha(float f, View view) {
        if (view instanceof ExpandableNotificationRow) {
            ((ExpandableNotificationRow) view).setContentAlpha(f);
        }
    }
}
