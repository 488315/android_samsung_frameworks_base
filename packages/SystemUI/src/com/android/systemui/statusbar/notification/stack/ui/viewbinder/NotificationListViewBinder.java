package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.animation.view.LaunchableTextView;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.NotificationIconContainerShelfViewBinder;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.DisplaySwitchNotificationsHiderTracker;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda5;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import java.util.Optional;
import javax.inject.Provider;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationListViewBinder {
    public final CoroutineDispatcher backgroundDispatcher;
    public final ConfigurationState configuration;
    public final FalsingManager falsingManager;
    public final DisplaySwitchNotificationsHiderTracker hiderTracker;
    public final HeadsUpNotificationViewBinder hunBinder;
    public final NotificationIconAreaController iconAreaController;
    public final MetricsLogger metricsLogger;
    public final NotificationIconContainerShelfViewBinder nicBinder;
    public final Provider notificationActivityStarter;
    public final NotificationShelfManager shelfManager;
    public final SectionHeaderController silentHeaderController;
    public final NotificationListViewModel viewModel;

    public NotificationListViewBinder(CoroutineDispatcher coroutineDispatcher, DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker, ConfigurationState configurationState, FalsingManager falsingManager, HeadsUpNotificationViewBinder headsUpNotificationViewBinder, NotificationIconAreaController notificationIconAreaController, Optional<NotificationStatsLogger> optional, MetricsLogger metricsLogger, NotificationIconContainerShelfViewBinder notificationIconContainerShelfViewBinder, Provider provider, SectionHeaderController sectionHeaderController, NotificationListViewModel notificationListViewModel, NotificationShelfManager notificationShelfManager) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.hiderTracker = displaySwitchNotificationsHiderTracker;
        this.configuration = configurationState;
        this.falsingManager = falsingManager;
        this.iconAreaController = notificationIconAreaController;
        this.metricsLogger = metricsLogger;
        this.nicBinder = notificationIconContainerShelfViewBinder;
        this.notificationActivityStarter = provider;
        this.silentHeaderController = sectionHeaderController;
        this.viewModel = notificationListViewModel;
        this.shelfManager = notificationShelfManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.Unit access$bindLogger(com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder r4, kotlin.coroutines.Continuation r5) {
        /*
            r4.getClass()
            boolean r0 = r5 instanceof com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindLogger$1
            if (r0 == 0) goto L16
            r0 = r5
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindLogger$1 r0 = (com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindLogger$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindLogger$1 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindLogger$1
            r0.<init>(r4, r5)
        L1b:
            java.lang.Object r4 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r5 = r0.label
            if (r5 == 0) goto L32
            r0 = 1
            if (r5 != r0) goto L2a
            kotlin.ResultKt.throwOnFailure(r4)
            goto L38
        L2a:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L32:
            kotlin.ResultKt.throwOnFailure(r4)
            com.android.systemui.Flags.notificationsLiveDataStoreRefactor()
        L38:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.access$bindLogger(com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder, kotlin.coroutines.Continuation):kotlin.Unit");
    }

    public final void bindWhileAttached(NotificationStackScrollLayout notificationStackScrollLayout, NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        int i;
        LayoutInflater from = LayoutInflater.from(notificationStackScrollLayout.getContext());
        this.shelfManager.getClass();
        final NotificationShelf notificationShelf = (NotificationShelf) from.inflate(R.layout.sec_status_bar_notification_shelf, (ViewGroup) notificationStackScrollLayout, false);
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
        NotificationShelf notificationShelf2 = notificationStackScrollLayout2.mShelf;
        if (notificationShelf2 != null) {
            i = notificationStackScrollLayout2.indexOfChild(notificationShelf2);
            notificationStackScrollLayout2.removeView(notificationStackScrollLayout2.mShelf);
        } else {
            i = -1;
        }
        notificationStackScrollLayout2.mShelf = notificationShelf;
        notificationStackScrollLayout2.addView(notificationShelf, i);
        AmbientState ambientState = notificationStackScrollLayout2.mAmbientState;
        NotificationShelf notificationShelf3 = notificationStackScrollLayout2.mShelf;
        ambientState.mShelf = notificationShelf3;
        notificationStackScrollLayout2.mStateAnimator.mShelf = notificationShelf3;
        NotificationRoundnessManager notificationRoundnessManager = notificationStackScrollLayout2.mController.mNotificationRoundnessManager;
        notificationShelf.mAmbientState = ambientState;
        ambientState.mKeyguardNotiExpandListeners.add(notificationShelf);
        notificationShelf.mHostLayout = notificationStackScrollLayout2;
        notificationShelf.mRoundnessManager = notificationRoundnessManager;
        NotificationShelfManager notificationShelfManager = (NotificationShelfManager) Dependency.sDependency.getDependencyInner(NotificationShelfManager.class);
        notificationShelf.mShelfManager = notificationShelfManager;
        notificationShelfManager.shelf = notificationShelf;
        StatusBarStateController statusBarStateController = notificationShelfManager.statusBarStateController;
        statusBarStateController.addCallback(notificationShelf);
        notificationShelfManager.mSettingButton = (LaunchableTextView) notificationShelf.findViewById(R.id.noti_setting);
        notificationShelfManager.mClearAllButton = (LaunchableTextView) notificationShelf.findViewById(R.id.clear_all);
        notificationShelfManager.mShelfTextArea = (LinearLayout) notificationShelf.findViewById(R.id.notification_shelf_text_area);
        notificationShelfManager.mNotificationIconContainer = (NotificationIconContainer) notificationShelf.findViewById(R.id.content);
        LaunchableTextView launchableTextView = notificationShelfManager.mSettingButton;
        if (launchableTextView != null) {
            launchableTextView.semSetButtonShapeEnabled(true);
        }
        LaunchableTextView launchableTextView2 = notificationShelfManager.mClearAllButton;
        if (launchableTextView2 != null) {
            launchableTextView2.semSetButtonShapeEnabled(true);
        }
        notificationShelfManager.updateResources();
        notificationShelfManager.statusBarState = statusBarStateController.getState();
        notificationShelfManager.updateShelfHeight();
        notificationShelfManager.updateShelfTextArea();
        LaunchableTextView launchableTextView3 = notificationShelfManager.mSettingButton;
        if (launchableTextView3 != null) {
            launchableTextView3.setVisibility(4);
        }
        LaunchableTextView launchableTextView4 = notificationShelfManager.mClearAllButton;
        if (launchableTextView4 != null) {
            launchableTextView4.setVisibility(4);
        }
        LinearLayout linearLayout = notificationShelfManager.mShelfTextArea;
        if (linearLayout != null) {
            linearLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.NotificationShelfManager$shelf$1$1
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                    NotificationShelf.this.updateIconsPaddingEnd();
                }
            });
        }
        notificationShelf.updateResources$3();
        ContrastColorUtil.getInstance(notificationShelf.getContext());
        NotificationShelfManager notificationShelfManager2 = notificationStackScrollLayout2.mShelfManager;
        NotificationStackScrollLayout$$ExternalSyntheticLambda1 notificationStackScrollLayout$$ExternalSyntheticLambda1 = new NotificationStackScrollLayout$$ExternalSyntheticLambda1(notificationStackScrollLayout2, 1);
        LaunchableTextView launchableTextView5 = notificationShelfManager2.mClearAllButton;
        if (launchableTextView5 != null) {
            launchableTextView5.setOnClickListener(notificationStackScrollLayout$$ExternalSyntheticLambda1);
        }
        notificationStackScrollLayout2.mShelfManager.updateAccessibility();
        NotificationStackScrollLayoutController$$ExternalSyntheticLambda5 notificationStackScrollLayoutController$$ExternalSyntheticLambda5 = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda5(notificationStackScrollLayoutController, 2);
        LaunchableTextView launchableTextView6 = notificationStackScrollLayoutController.mShelfManager.mSettingButton;
        if (launchableTextView6 != null) {
            launchableTextView6.setOnClickListener(notificationStackScrollLayoutController$$ExternalSyntheticLambda5);
        }
        RepeatWhenAttachedKt.repeatWhenAttached(notificationStackScrollLayout, EmptyCoroutineContext.INSTANCE, new NotificationListViewBinder$bindWhileAttached$1(notificationStackScrollLayoutController, this, notificationStackScrollLayout, notificationShelf, null));
    }
}
