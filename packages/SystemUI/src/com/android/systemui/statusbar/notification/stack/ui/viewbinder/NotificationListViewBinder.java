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
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import java.util.Optional;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationListViewBinder {
    public final FalsingManager falsingManager;
    public final DisplaySwitchNotificationsHiderTracker hiderTracker;
    public final HeadsUpNotificationViewBinder hunBinder;
    public final NotificationIconAreaController iconAreaController;
    public final Optional loggerOptional;
    public final NotificationIconContainerShelfViewBinder nicBinder;
    public final Provider notificationActivityStarter;
    public final NotificationShelfManager shelfManager;
    public final SectionHeaderController silentHeaderController;
    public final NotificationListViewModel viewModel;

    public NotificationListViewBinder(CoroutineDispatcher coroutineDispatcher, DisplaySwitchNotificationsHiderTracker displaySwitchNotificationsHiderTracker, ConfigurationState configurationState, FalsingManager falsingManager, HeadsUpNotificationViewBinder headsUpNotificationViewBinder, NotificationIconAreaController notificationIconAreaController, Optional<NotificationStatsLogger> optional, MetricsLogger metricsLogger, NotificationIconContainerShelfViewBinder notificationIconContainerShelfViewBinder, Provider provider, SectionHeaderController sectionHeaderController, NotificationListViewModel notificationListViewModel, NotificationShelfManager notificationShelfManager) {
        this.hiderTracker = displaySwitchNotificationsHiderTracker;
        this.falsingManager = falsingManager;
        this.hunBinder = headsUpNotificationViewBinder;
        this.iconAreaController = notificationIconAreaController;
        this.loggerOptional = optional;
        this.nicBinder = notificationIconContainerShelfViewBinder;
        this.notificationActivityStarter = provider;
        this.silentHeaderController = sectionHeaderController;
        this.viewModel = notificationListViewModel;
        this.shelfManager = notificationShelfManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$bindLogger(com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder r7, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r7.getClass()
            boolean r0 = r9 instanceof com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindLogger$1
            if (r0 == 0) goto L16
            r0 = r9
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
            r0.<init>(r7, r9)
        L1b:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r9)
            goto L7c
        L2a:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L32:
            kotlin.ResultKt.throwOnFailure(r9)
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel r9 = r7.viewModel
            java.util.Optional r9 = r9.logger
            r2 = 0
            java.lang.Object r9 = r9.orElse(r2)
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel r9 = (com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel) r9
            if (r9 == 0) goto L7c
            java.util.Optional r7 = r7.loggerOptional
            java.lang.Object r7 = r7.orElse(r2)
            com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger r7 = (com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger) r7
            if (r7 == 0) goto L7c
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinder r4 = com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinder.INSTANCE
            r0.label = r3
            r4.getClass()
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinder$bindLogger$4 r3 = com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinder$bindLogger$4.INSTANCE
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 r4 = new kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1
            kotlinx.coroutines.flow.StateFlowImpl r5 = r9.isOnLockScreen
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel$special$$inlined$map$1 r6 = r9.activeNotifications
            r4.<init>(r5, r6, r3)
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinder$bindLogger$5 r3 = new com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinder$bindLogger$5
            com.android.systemui.util.kotlin.Utils$Companion r5 = com.android.systemui.util.kotlin.Utils.Companion
            r3.<init>(r5)
            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r9.isLockscreenOrShadeInteractive
            kotlinx.coroutines.flow.Flow r3 = com.android.systemui.util.kotlin.FlowKt.sample(r5, r4, r3)
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinder$bindLogger$6 r4 = new com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationStatsLoggerBinder$bindLogger$6
            r4.<init>(r7, r8, r9, r2)
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.collectLatest(r3, r4, r0)
            if (r7 != r1) goto L77
            goto L79
        L77:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
        L79:
            if (r7 != r1) goto L7c
            return r1
        L7c:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder.access$bindLogger(com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final void bindWhileAttached(NotificationStackScrollLayout notificationStackScrollLayout, NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        int i;
        LayoutInflater from = LayoutInflater.from(notificationStackScrollLayout.getContext());
        this.shelfManager.getClass();
        final NotificationShelf notificationShelf = (NotificationShelf) from.inflate(R.layout.sec_status_bar_notification_shelf, (ViewGroup) notificationStackScrollLayout, false);
        View view = notificationStackScrollLayout.mShelf;
        if (view != null) {
            i = notificationStackScrollLayout.indexOfChild(view);
            notificationStackScrollLayout.removeView(notificationStackScrollLayout.mShelf);
        } else {
            i = -1;
        }
        notificationStackScrollLayout.mShelf = notificationShelf;
        notificationStackScrollLayout.addView(notificationShelf, i);
        notificationStackScrollLayout.mAmbientState.mShelf = notificationStackScrollLayout.mShelf;
        notificationStackScrollLayout.mStateAnimator.getClass();
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        NotificationRoundnessManager notificationRoundnessManager = notificationStackScrollLayout.mController.mNotificationRoundnessManager;
        notificationShelf.mAmbientState = ambientState;
        ambientState.mKeyguardNotiExpandListeners.add(notificationShelf);
        notificationShelf.mHostLayout = notificationStackScrollLayout;
        notificationShelf.mRoundnessManager = notificationRoundnessManager;
        NotificationShelfManager notificationShelfManager = (NotificationShelfManager) Dependency.sDependency.getDependencyInner(NotificationShelfManager.class);
        notificationShelf.mShelfManager = notificationShelfManager;
        notificationShelfManager.shelf = notificationShelf;
        StatusBarStateController statusBarStateController = notificationShelfManager.statusBarStateController;
        statusBarStateController.addCallback(notificationShelf);
        notificationShelfManager.statusBarState = statusBarStateController.getState();
        LaunchableTextView launchableTextView = (LaunchableTextView) notificationShelf.findViewById(R.id.noti_setting);
        notificationShelfManager.mSettingButton = launchableTextView;
        if (launchableTextView != null) {
            launchableTextView.setSelected(true);
        }
        LaunchableTextView launchableTextView2 = (LaunchableTextView) notificationShelf.findViewById(R.id.clear_all);
        notificationShelfManager.mClearAllButton = launchableTextView2;
        if (launchableTextView2 != null) {
            launchableTextView2.setSelected(true);
        }
        notificationShelfManager.mShelfTextArea = (LinearLayout) notificationShelf.findViewById(R.id.notification_shelf_text_area);
        notificationShelfManager.mNotificationIconContainer = (NotificationIconContainer) notificationShelf.findViewById(R.id.content);
        notificationShelfManager.updateResources();
        notificationShelfManager.statusBarState = statusBarStateController.getState();
        notificationShelfManager.updateShelfLayout();
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
                public final void onLayoutChange(View view2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                    NotificationShelf.this.updateIconsPaddingEnd();
                }
            });
        }
        notificationShelf.updateResources$3();
        ContrastColorUtil.getInstance(notificationShelf.getContext());
        NotificationShelfManager notificationShelfManager2 = notificationStackScrollLayout.mShelfManager;
        NotificationStackScrollLayout$$ExternalSyntheticLambda1 notificationStackScrollLayout$$ExternalSyntheticLambda1 = new NotificationStackScrollLayout$$ExternalSyntheticLambda1(notificationStackScrollLayout, 1);
        LaunchableTextView launchableTextView5 = notificationShelfManager2.mClearAllButton;
        if (launchableTextView5 != null) {
            launchableTextView5.setOnClickListener(notificationStackScrollLayout$$ExternalSyntheticLambda1);
        }
        notificationStackScrollLayout.mShelfManager.updateAccessibility();
        final int i2 = 0;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$$ExternalSyntheticLambda0
            public final /* synthetic */ NotificationListViewBinder f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return this.f$0.viewModel.footerViewModelFactory.create();
                    default:
                        return this.f$0.viewModel.emptyShadeViewModelFactory.create();
                }
            }
        });
        final int i3 = 1;
        RepeatWhenAttachedKt.repeatWhenAttached(notificationStackScrollLayout, EmptyCoroutineContext.INSTANCE, new NotificationListViewBinder$bindWhileAttached$1(notificationStackScrollLayoutController, this, notificationStackScrollLayout, notificationShelf, lazy, LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$$ExternalSyntheticLambda0
            public final /* synthetic */ NotificationListViewBinder f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        return this.f$0.viewModel.footerViewModelFactory.create();
                    default:
                        return this.f$0.viewModel.emptyShadeViewModelFactory.create();
                }
            }
        }), null));
    }
}
