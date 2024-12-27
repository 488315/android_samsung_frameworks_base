package com.android.systemui.statusbar.notification;

import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.util.MathUtils;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationLaunchAnimationInteractor;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableOutlineView;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpUtil;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationTransitionAnimatorController implements ActivityTransitionAnimator.Controller {
    public final HeadsUpManager headsUpManager;
    public final boolean isLaunching;
    public final InteractionJankMonitor jankMonitor;

    /* renamed from: notification, reason: collision with root package name */
    public final ExpandableNotificationRow f99notification;
    public final NotificationEntry notificationEntry;
    public final String notificationKey;
    public final NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor;
    public final NotificationListContainer notificationListContainer;
    public final Runnable onFinishAnimationCallback;

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
    }

    public NotificationTransitionAnimatorController(NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor, NotificationListContainer notificationListContainer, HeadsUpManager headsUpManager, ExpandableNotificationRow expandableNotificationRow, InteractionJankMonitor interactionJankMonitor, Runnable runnable) {
        StatusBarNotification statusBarNotification;
        this.notificationLaunchAnimationInteractor = notificationLaunchAnimationInteractor;
        this.notificationListContainer = notificationListContainer;
        this.headsUpManager = headsUpManager;
        this.f99notification = expandableNotificationRow;
        this.jankMonitor = interactionJankMonitor;
        this.onFinishAnimationCallback = runnable;
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        this.notificationEntry = notificationEntry;
        String key = (notificationEntry == null || (statusBarNotification = notificationEntry.mSbn) == null) ? null : statusBarNotification.getKey();
        this.notificationKey = key == null ? "" : key;
        this.isLaunching = true;
    }

    public final void applyParams(LaunchAnimationParameters launchAnimationParameters) {
        int i;
        boolean z = true;
        ExpandableNotificationRow expandableNotificationRow = this.f99notification;
        if (launchAnimationParameters == null) {
            ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow.mNotificationParent;
            if (expandableNotificationRow2 != null) {
                expandableNotificationRow2.setClipTopAmount(0);
            }
            expandableNotificationRow.setTranslationX(0.0f);
        } else {
            expandableNotificationRow.getClass();
            if (launchAnimationParameters.visible) {
                Interpolator interpolator = Interpolators.FAST_OUT_SLOW_IN;
                TransitionAnimator.Companion companion = TransitionAnimator.Companion;
                TransitionAnimator.Timings timings = ActivityTransitionAnimator.TIMINGS;
                float f = launchAnimationParameters.linearProgress;
                companion.getClass();
                PathInterpolator pathInterpolator = (PathInterpolator) interpolator;
                float lerp = MathUtils.lerp(launchAnimationParameters.startTranslationZ, expandableNotificationRow.mNotificationLaunchHeight, pathInterpolator.getInterpolation(TransitionAnimator.Companion.getProgress(timings, f, 0L, 50L)));
                expandableNotificationRow.setTranslationZ(lerp);
                float width = launchAnimationParameters.getWidth() - expandableNotificationRow.getWidth();
                expandableNotificationRow.mExtraWidthForClipping = width;
                expandableNotificationRow.invalidate();
                if (launchAnimationParameters.startRoundedTopClipping > 0) {
                    float f2 = launchAnimationParameters.linearProgress;
                    companion.getClass();
                    float interpolation = pathInterpolator.getInterpolation(TransitionAnimator.Companion.getProgress(timings, f2, 0L, 100L));
                    int i2 = launchAnimationParameters.startNotificationTop;
                    i = (int) Math.min(MathUtils.lerp(i2, launchAnimationParameters.top, interpolation), i2);
                } else {
                    i = launchAnimationParameters.top;
                }
                int i3 = launchAnimationParameters.bottom - i;
                expandableNotificationRow.setActualHeight(i3, true);
                int i4 = launchAnimationParameters.notificationParentTop;
                int i5 = i - i4;
                int i6 = launchAnimationParameters.startClipTopAmount;
                int lerp2 = (int) MathUtils.lerp(i6, 0, launchAnimationParameters.progress);
                ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow.mNotificationParent;
                if (expandableNotificationRow3 != null) {
                    float translationY = expandableNotificationRow3.getTranslationY();
                    i5 = (int) (i5 - translationY);
                    expandableNotificationRow.mNotificationParent.setTranslationZ(lerp);
                    expandableNotificationRow.mNotificationParent.setClipTopAmount(Math.min(launchAnimationParameters.parentStartClipTopAmount, lerp2 + i5));
                    ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow.mNotificationParent;
                    expandableNotificationRow4.mExtraWidthForClipping = width;
                    expandableNotificationRow4.invalidate();
                    float f3 = launchAnimationParameters.bottom - i4;
                    ExpandableNotificationRow expandableNotificationRow5 = expandableNotificationRow.mNotificationParent;
                    int max = (int) (Math.max(f3, (expandableNotificationRow5.mActualHeight + translationY) - expandableNotificationRow5.mClipBottomAmount) - Math.min(launchAnimationParameters.top - i4, translationY));
                    ExpandableNotificationRow expandableNotificationRow6 = expandableNotificationRow.mNotificationParent;
                    expandableNotificationRow6.mMinimumHeightForClipping = max;
                    expandableNotificationRow6.updateClipping$1();
                    expandableNotificationRow6.invalidate();
                } else if (i6 != 0) {
                    expandableNotificationRow.setClipTopAmount(lerp2);
                }
                expandableNotificationRow.setTranslationY(i5);
                expandableNotificationRow.setTranslationX(((launchAnimationParameters.getWidth() / 2.0f) + launchAnimationParameters.left) - (((expandableNotificationRow.getWidth() / 2.0f) + expandableNotificationRow.getLocationOnScreen()[0]) - expandableNotificationRow.getTranslationX()));
                float f4 = ((ExpandableOutlineView) expandableNotificationRow).mRoundableState.maxRadius;
                expandableNotificationRow.invalidateOutline();
                NotificationBackgroundView notificationBackgroundView = expandableNotificationRow.mBackgroundNormal;
                int width2 = launchAnimationParameters.getWidth();
                notificationBackgroundView.mExpandAnimationHeight = i3;
                notificationBackgroundView.mExpandAnimationWidth = width2;
                notificationBackgroundView.invalidate();
            } else if (expandableNotificationRow.getVisibility() == 0) {
                expandableNotificationRow.setVisibility(4);
            }
        }
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
        notificationStackScrollLayout.mLaunchAnimationParams = launchAnimationParameters;
        boolean z2 = launchAnimationParameters != null;
        if (z2 != notificationStackScrollLayout.mLaunchingNotification) {
            notificationStackScrollLayout.mLaunchingNotification = z2;
            if (launchAnimationParameters == null || (launchAnimationParameters.startRoundedTopClipping <= 0 && launchAnimationParameters.parentStartRoundedTopClipping <= 0)) {
                z = false;
            }
            notificationStackScrollLayout.mLaunchingNotificationNeedsToBeClipped = z;
            if (!z || !z2) {
                notificationStackScrollLayout.mLaunchedNotificationClipPath.reset();
            }
            notificationStackScrollLayout.invalidate();
        }
        notificationStackScrollLayout.updateLaunchedNotificationClipPath();
        notificationStackScrollLayout.requestChildrenUpdate();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final TransitionAnimator.State createAnimatorState() {
        ExpandableNotificationRow expandableNotificationRow = this.f99notification;
        int max = Math.max(0, expandableNotificationRow.mActualHeight - expandableNotificationRow.mClipBottomAmount);
        int[] locationOnScreen = expandableNotificationRow.getLocationOnScreen();
        NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl = (NotificationStackScrollLayoutController.NotificationListContainerImpl) this.notificationListContainer;
        int height = NotificationStackScrollLayoutController.this.mView.mIsExpanded ? ((ShadeHeaderController) Dependency.sDependency.getDependencyInner(ShadeHeaderController.class)).header.getHeight() : 0;
        int i = locationOnScreen[1];
        int i2 = height - i;
        if (i2 < 0) {
            i2 = 0;
        }
        int i3 = i + i2;
        float topCornerRadius = i2 > 0 ? 0.0f : expandableNotificationRow.getTopCornerRadius();
        int i4 = locationOnScreen[1] + max;
        int i5 = locationOnScreen[0];
        LaunchAnimationParameters launchAnimationParameters = new LaunchAnimationParameters(i3, i4, i5, expandableNotificationRow.getWidth() + i5, topCornerRadius, expandableNotificationRow.getBottomCornerRadius());
        launchAnimationParameters.startTranslationZ = expandableNotificationRow.getTranslationZ();
        launchAnimationParameters.startNotificationTop = locationOnScreen[1];
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
        notificationStackScrollLayout.getClass();
        launchAnimationParameters.notificationParentTop = notificationStackScrollLayout.getLocationOnScreen()[1];
        launchAnimationParameters.startRoundedTopClipping = i2;
        launchAnimationParameters.startClipTopAmount = expandableNotificationRow.mClipTopAmount;
        if (expandableNotificationRow.isChildInGroup()) {
            int i6 = height - expandableNotificationRow.mNotificationParent.getLocationOnScreen()[1];
            if (i6 < 0) {
                i6 = 0;
            }
            launchAnimationParameters.parentStartRoundedTopClipping = i6;
            int i7 = expandableNotificationRow.mNotificationParent.mClipTopAmount;
            launchAnimationParameters.parentStartClipTopAmount = i7;
            if (i7 != 0) {
                float translationY = i7 - expandableNotificationRow.getTranslationY();
                if (translationY > 0.0f) {
                    launchAnimationParameters.startClipTopAmount = (int) Math.ceil(translationY);
                }
            }
        }
        return launchAnimationParameters;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final ViewGroup getTransitionContainer() {
        return (ViewGroup) this.f99notification.getRootView();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final boolean isLaunching() {
        return this.isLaunching;
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onIntentStarted(boolean z) {
        if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
            Log.d("NotificationLaunchAnimatorController", "onIntentStarted(willAnimate=" + z + ")");
        }
        this.notificationLaunchAnimationInteractor.setIsLaunchAnimationRunning(z);
        NotificationEntry notificationEntry = this.notificationEntry;
        if (notificationEntry != null) {
            notificationEntry.mExpandAnimationRunning = z;
        }
        if (z) {
            return;
        }
        removeHun(false);
        Runnable runnable = this.onFinishAnimationCallback;
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onTransitionAnimationCancelled(Boolean bool) {
        if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
            Log.d("NotificationLaunchAnimatorController", "onLaunchAnimationCancelled()");
        }
        this.notificationLaunchAnimationInteractor.setIsLaunchAnimationRunning(false);
        NotificationEntry notificationEntry = this.notificationEntry;
        if (notificationEntry != null) {
            notificationEntry.mExpandAnimationRunning = false;
        }
        removeHun(true);
        Runnable runnable = this.onFinishAnimationCallback;
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationEnd(boolean z) {
        if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
            Log.d("NotificationLaunchAnimatorController", "onLaunchAnimationEnd()");
        }
        this.jankMonitor.end(16);
        this.f99notification.setExpandAnimationRunning(false);
        this.notificationLaunchAnimationInteractor.setIsLaunchAnimationRunning(false);
        NotificationEntry notificationEntry = this.notificationEntry;
        if (notificationEntry != null) {
            notificationEntry.mExpandAnimationRunning = false;
        }
        ((NotificationStackScrollLayoutController.NotificationListContainerImpl) this.notificationListContainer).setExpandingNotification(null);
        applyParams(null);
        removeHun(false);
        Runnable runnable = this.onFinishAnimationCallback;
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
        LaunchAnimationParameters launchAnimationParameters = (LaunchAnimationParameters) state;
        launchAnimationParameters.progress = f;
        launchAnimationParameters.linearProgress = f2;
        applyParams(launchAnimationParameters);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationStart(boolean z) {
        ExpandableNotificationRow expandableNotificationRow = this.f99notification;
        expandableNotificationRow.setExpandAnimationRunning(true);
        ((NotificationStackScrollLayoutController.NotificationListContainerImpl) this.notificationListContainer).setExpandingNotification(expandableNotificationRow);
        this.jankMonitor.begin(expandableNotificationRow, 16);
    }

    public final void removeHun(boolean z) {
        GroupEntry parent;
        ExpandableNotificationRow expandableNotificationRow = null;
        NotificationEntry notificationEntry = this.notificationEntry;
        NotificationEntry notificationEntry2 = (notificationEntry == null || (parent = notificationEntry.getParent()) == null) ? null : parent.mSummary;
        HeadsUpManager headsUpManager = this.headsUpManager;
        BaseHeadsUpManager baseHeadsUpManager = (BaseHeadsUpManager) headsUpManager;
        boolean isHeadsUpEntry = baseHeadsUpManager.isHeadsUpEntry(this.notificationKey);
        ExpandableNotificationRow expandableNotificationRow2 = this.f99notification;
        if (isHeadsUpEntry) {
            expandableNotificationRow = expandableNotificationRow2;
        } else if (notificationEntry2 != null && baseHeadsUpManager.isHeadsUpEntry(notificationEntry2.mKey)) {
            expandableNotificationRow = notificationEntry2.row;
        }
        if (expandableNotificationRow == null) {
            return;
        }
        HeadsUpUtil.setNeedsHeadsUpDisappearAnimationAfterClick(expandableNotificationRow2, z);
        String str = expandableNotificationRow.mEntry.mKey;
        HeadsUpManagerPhone headsUpManagerPhone = (HeadsUpManagerPhone) headsUpManager;
        if (z) {
            headsUpManagerPhone.removeNotification$1(str, true);
            return;
        }
        headsUpManagerPhone.mAnimationStateHandler.f$0.mHeadsUpGoingAwayAnimationsAllowed = false;
        headsUpManagerPhone.removeNotification$1(str, true);
        headsUpManagerPhone.mAnimationStateHandler.f$0.mHeadsUpGoingAwayAnimationsAllowed = true;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void setTransitionContainer(ViewGroup viewGroup) {
    }
}
