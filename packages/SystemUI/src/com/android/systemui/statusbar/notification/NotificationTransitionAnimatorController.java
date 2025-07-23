package com.android.systemui.statusbar.notification;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.MathUtils;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationLaunchAnimationInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationTransitionAnimatorController implements ActivityTransitionAnimator.Controller {
    public final HeadsUpManager headsUpManager;
    public final boolean isLaunching = true;
    public final InteractionJankMonitor jankMonitor;

    /* renamed from: notification, reason: collision with root package name */
    public final ExpandableNotificationRow f134notification;
    public final String notificationKey;
    public final NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor;
    public final NotificationListContainer notificationListContainer;
    public final Runnable onFinishAnimationCallback;

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

    public NotificationTransitionAnimatorController(NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor, NotificationListContainer notificationListContainer, HeadsUpManager headsUpManager, ExpandableNotificationRow expandableNotificationRow, InteractionJankMonitor interactionJankMonitor, Runnable runnable) {
        this.notificationLaunchAnimationInteractor = notificationLaunchAnimationInteractor;
        this.notificationListContainer = notificationListContainer;
        this.headsUpManager = headsUpManager;
        this.f134notification = expandableNotificationRow;
        this.jankMonitor = interactionJankMonitor;
        this.onFinishAnimationCallback = runnable;
        this.notificationKey = expandableNotificationRow.getKey();
    }

    public final void applyParams(LaunchAnimationParameters launchAnimationParameters) {
        int i;
        boolean z = true;
        ExpandableNotificationRow expandableNotificationRow = this.f134notification;
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
                    i5 -= (int) translationY;
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
                expandableNotificationRow.setTranslationX(launchAnimationParameters.getCenterX() - (((expandableNotificationRow.getWidth() / 2.0f) + expandableNotificationRow.getLocationOnScreen()[0]) - expandableNotificationRow.getTranslationX()));
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
        ExpandableNotificationRow expandableNotificationRow = this.f134notification;
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
        float f = i2 > 0 ? 0.0f : expandableNotificationRow.getRoundableState().topRoundness * expandableNotificationRow.getRoundableState().maxRadius;
        int i4 = locationOnScreen[1] + max;
        int i5 = locationOnScreen[0];
        LaunchAnimationParameters launchAnimationParameters = new LaunchAnimationParameters(i3, i4, i5, expandableNotificationRow.getWidth() + i5, f, expandableNotificationRow.getRoundableState().maxRadius * expandableNotificationRow.getRoundableState().bottomRoundness);
        launchAnimationParameters.startTranslationZ = expandableNotificationRow.getTranslationZ();
        launchAnimationParameters.startNotificationTop = locationOnScreen[1];
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
        notificationStackScrollLayout.getClass();
        launchAnimationParameters.notificationParentTop = notificationStackScrollLayout.getLocationOnScreen()[1];
        launchAnimationParameters.startRoundedTopClipping = i2;
        launchAnimationParameters.startClipTopAmount = expandableNotificationRow.mClipTopAmount;
        if (expandableNotificationRow.isChildInGroup()) {
            int i6 = height - expandableNotificationRow.mNotificationParent.getLocationOnScreen()[1];
            launchAnimationParameters.parentStartRoundedTopClipping = i6 >= 0 ? i6 : 0;
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
        return (ViewGroup) this.f134notification.getRootView();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final boolean isLaunching() {
        return this.isLaunching;
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onIntentStarted(boolean z) {
        String str = "onIntentStarted(willAnimate=" + z + ")";
        if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
            Log.d("NotificationLaunchAnimatorController", str);
        }
        this.notificationLaunchAnimationInteractor.setIsLaunchAnimationRunning(z);
        ExpandableNotificationRow expandableNotificationRow = this.f134notification;
        expandableNotificationRow.getClass();
        int i = NotificationBundleUi.$r8$clinit;
        expandableNotificationRow.getEntryLegacy().mExpandAnimationRunning = z;
        if (z) {
            return;
        }
        removeHun(str, false);
        Runnable runnable = this.onFinishAnimationCallback;
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onTransitionAnimationCancelled() {
        if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
            Log.d("NotificationLaunchAnimatorController", "onLaunchAnimationCancelled()");
        }
        this.notificationLaunchAnimationInteractor.setIsLaunchAnimationRunning(false);
        ExpandableNotificationRow expandableNotificationRow = this.f134notification;
        expandableNotificationRow.getClass();
        int i = NotificationBundleUi.$r8$clinit;
        expandableNotificationRow.getEntryLegacy().mExpandAnimationRunning = false;
        removeHun("onLaunchAnimationCancelled()", true);
        Runnable runnable = this.onFinishAnimationCallback;
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationEnd(boolean z) {
        boolean z2 = ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION;
        ExpandableNotificationRow expandableNotificationRow = this.f134notification;
        if (z2) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onLaunchAnimationEnd()", expandableNotificationRow.getKey(), "NotificationLaunchAnimatorController");
        }
        this.jankMonitor.end(16);
        expandableNotificationRow.setExpandAnimationRunning(false);
        this.notificationLaunchAnimationInteractor.setIsLaunchAnimationRunning(false);
        int i = NotificationBundleUi.$r8$clinit;
        expandableNotificationRow.getEntryLegacy().mExpandAnimationRunning = false;
        ((NotificationStackScrollLayoutController.NotificationListContainerImpl) this.notificationListContainer).setExpandingNotification(null);
        applyParams(null);
        removeHun("onLaunchAnimationEnd()", false);
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
        ExpandableNotificationRow expandableNotificationRow = this.f134notification;
        expandableNotificationRow.setExpandAnimationRunning(true);
        ((NotificationStackScrollLayoutController.NotificationListContainerImpl) this.notificationListContainer).setExpandingNotification(expandableNotificationRow);
        this.jankMonitor.begin(expandableNotificationRow, 16);
    }

    public final void removeHun(String str, boolean z) {
        ExpandableNotificationRow expandableNotificationRow = this.f134notification;
        PipelineEntry pipelineEntry = expandableNotificationRow.getEntryLegacy().mAttachState.parent;
        GroupEntry groupEntry = pipelineEntry instanceof GroupEntry ? (GroupEntry) pipelineEntry : null;
        NotificationEntry notificationEntry = groupEntry != null ? groupEntry.mSummary : null;
        HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) this.headsUpManager;
        ExpandableNotificationRow expandableNotificationRow2 = headsUpManagerImpl.isHeadsUpEntry(this.notificationKey) ? expandableNotificationRow : (notificationEntry != null && headsUpManagerImpl.isHeadsUpEntry(notificationEntry.mKey)) ? notificationEntry.row : null;
        if (expandableNotificationRow2 == null) {
            return;
        }
        expandableNotificationRow.setTag(R.id.is_clicked_heads_up_tag, z ? Boolean.TRUE : null);
        String key = expandableNotificationRow2.getKey();
        if (z) {
            headsUpManagerImpl.getClass();
            headsUpManagerImpl.removeNotification(key, "removeNotification(animate: true), reason: " + str, true);
            return;
        }
        ((NotificationStackScrollLayout) headsUpManagerImpl.mAnimationStateHandler.f$0).mHeadsUpGoingAwayAnimationsAllowed = false;
        headsUpManagerImpl.removeNotification(key, "removeNotification(animate: false), reason: " + str, true);
        ((NotificationStackScrollLayout) headsUpManagerImpl.mAnimationStateHandler.f$0).mHeadsUpGoingAwayAnimationsAllowed = true;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void setTransitionContainer(ViewGroup viewGroup) {
    }
}
