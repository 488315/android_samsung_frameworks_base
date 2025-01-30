package com.android.systemui.statusbar.notification.collection.inflation;

import android.os.SystemClock;
import android.util.ArrayMap;
import android.view.View;
import com.android.systemui.NotiRune;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationRowBinderImpl$$ExternalSyntheticLambda0 {
    public final /* synthetic */ NotificationRowBinderImpl f$0;
    public final /* synthetic */ NotificationEntry f$1;
    public final /* synthetic */ NotifInflater.Params f$2;
    public final /* synthetic */ NotificationRowContentBinder.InflationCallback f$3;

    public /* synthetic */ NotificationRowBinderImpl$$ExternalSyntheticLambda0(NotificationRowBinderImpl notificationRowBinderImpl, NotificationEntry notificationEntry, NotifInflater.Params params, NotifInflaterImpl.C27911 c27911) {
        this.f$0 = notificationRowBinderImpl;
        this.f$1 = notificationEntry;
        this.f$2 = params;
        this.f$3 = c27911;
    }

    public final void onInflationFinished(final ExpandableNotificationRow expandableNotificationRow) {
        NotificationEntry notificationEntry = this.f$1;
        NotificationRowBinderImpl notificationRowBinderImpl = this.f$0;
        final ExpandableNotificationRowController expandableNotificationRowController = notificationRowBinderImpl.mExpandableNotificationRowComponentBuilder.expandableNotificationRow(expandableNotificationRow).notificationEntry(notificationEntry).onExpandClickListener(notificationRowBinderImpl.mPresenter).listContainer(notificationRowBinderImpl.mListContainer).build().getExpandableNotificationRowController();
        expandableNotificationRowController.mActivatableNotificationViewController.init();
        expandableNotificationRowController.mView.initialize(notificationEntry, expandableNotificationRowController.mRemoteInputViewSubcomponentFactory, expandableNotificationRowController.mAppName, expandableNotificationRowController.mNotificationKey, expandableNotificationRowController.mLoggerCallback, expandableNotificationRowController.mKeyguardBypassController, expandableNotificationRowController.mGroupMembershipManager, expandableNotificationRowController.mGroupExpansionManager, expandableNotificationRowController.mHeadsUpManager, expandableNotificationRowController.mRowContentBindStage, expandableNotificationRowController.mOnExpandClickListener, expandableNotificationRowController.mOnFeedbackClickListener, expandableNotificationRowController.mFalsingManager, expandableNotificationRowController.mFalsingCollector, expandableNotificationRowController.mStatusBarStateController, expandableNotificationRowController.mPeopleNotificationIdentifier, expandableNotificationRowController.mOnUserInteractionCallback, expandableNotificationRowController.mBubblesManagerOptional, expandableNotificationRowController.mNotificationGutsManager, expandableNotificationRowController.mDismissibilityProvider, expandableNotificationRowController.mMetricsLogger, expandableNotificationRowController.mSmartReplyConstants, expandableNotificationRowController.mSmartReplyController, expandableNotificationRowController.mFeatureFlags, expandableNotificationRowController.mStatusBarService, expandableNotificationRowController.mActivityStarter);
        ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRowController.mView;
        expandableNotificationRow2.setDescendantFocusability(393216);
        if (expandableNotificationRowController.mAllowLongPress) {
            if (((FeatureFlagsRelease) expandableNotificationRowController.mFeatureFlags).isEnabled(Flags.NOTIFICATION_DRAG_TO_CONTENTS)) {
                expandableNotificationRow2.mDragController = expandableNotificationRowController.mDragController;
            }
            ExpandableNotificationRowController$$ExternalSyntheticLambda0 expandableNotificationRowController$$ExternalSyntheticLambda0 = new ExpandableNotificationRowController$$ExternalSyntheticLambda0(expandableNotificationRowController);
            expandableNotificationRow2.mLongPressListener = expandableNotificationRowController$$ExternalSyntheticLambda0;
            expandableNotificationRow2.mLongPressListenerForBubble = expandableNotificationRowController$$ExternalSyntheticLambda0;
        }
        if (NotificationRemoteInputManager.ENABLE_REMOTE_INPUT) {
            expandableNotificationRow2.setDescendantFocusability(131072);
        }
        expandableNotificationRow2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController.2
            public ViewOnAttachStateChangeListenerC28692() {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                ExpandableNotificationRowController expandableNotificationRowController2 = ExpandableNotificationRowController.this;
                NotificationEntry notificationEntry2 = expandableNotificationRowController2.mView.mEntry;
                ((SystemClockImpl) expandableNotificationRowController2.mClock).getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (notificationEntry2.initializationTime == -1) {
                    notificationEntry2.initializationTime = elapsedRealtime;
                }
                ExpandableNotificationRowController expandableNotificationRowController3 = ExpandableNotificationRowController.this;
                expandableNotificationRowController3.mPluginManager.addPluginListener((PluginListener) expandableNotificationRowController3.mView, NotificationMenuRowPlugin.class, false);
                ExpandableNotificationRowController expandableNotificationRowController4 = ExpandableNotificationRowController.this;
                expandableNotificationRowController4.mView.setOnKeyguard(expandableNotificationRowController4.mStatusBarStateController.getState() == 1);
                ExpandableNotificationRowController expandableNotificationRowController5 = ExpandableNotificationRowController.this;
                expandableNotificationRowController5.mStatusBarStateController.addCallback(expandableNotificationRowController5.mStatusBarStateListener);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                ExpandableNotificationRowController expandableNotificationRowController2 = ExpandableNotificationRowController.this;
                expandableNotificationRowController2.mPluginManager.removePluginListener(expandableNotificationRowController2.mView);
                ExpandableNotificationRowController expandableNotificationRowController3 = ExpandableNotificationRowController.this;
                expandableNotificationRowController3.mStatusBarStateController.removeCallback(expandableNotificationRowController3.mStatusBarStateListener);
            }
        });
        notificationEntry.mRowController = expandableNotificationRowController;
        final NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl = (NotificationStackScrollLayoutController.NotificationListContainerImpl) notificationRowBinderImpl.mListContainer;
        notificationListContainerImpl.getClass();
        expandableNotificationRow.mHeadsUpAnimatingAwayListener = new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$NotificationListContainerImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl2 = NotificationStackScrollLayoutController.NotificationListContainerImpl.this;
                ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow;
                notificationListContainerImpl2.getClass();
                NotificationEntry notificationEntry2 = expandableNotificationRow3.mEntry;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.mHeadsUpAppearanceController.updateHeader(notificationEntry2);
                notificationStackScrollLayoutController.mHeadsUpAppearanceController.updateHeadsUpAndPulsingRoundness(notificationEntry2);
            }
        };
        expandableNotificationRow.mPrivateLayout.mRemoteInputController = notificationRowBinderImpl.mNotificationRemoteInputManager.mRemoteInputController;
        notificationEntry.row = expandableNotificationRow;
        NotifBindPipeline notifBindPipeline = notificationRowBinderImpl.mNotifBindPipeline;
        NotifBindPipelineLogger notifBindPipelineLogger = notifBindPipeline.mLogger;
        notifBindPipelineLogger.logManagedRow(notificationEntry);
        notifBindPipelineLogger.logManagedRow(notificationEntry);
        NotifBindPipeline.BindEntry bindEntry = (NotifBindPipeline.BindEntry) ((ArrayMap) notifBindPipeline.mBindEntries).get(notificationEntry);
        if (bindEntry != null) {
            bindEntry.row = expandableNotificationRow;
            if (bindEntry.invalidated) {
                notifBindPipeline.requestPipelineRun(notificationEntry);
            }
        }
        StatusBarNotificationPresenter statusBarNotificationPresenter = (StatusBarNotificationPresenter) notificationRowBinderImpl.mBindRowCallback;
        expandableNotificationRow.mAboveShelfChangedListener = statusBarNotificationPresenter.mAboveShelfObserver;
        final KeyguardStateController keyguardStateController = statusBarNotificationPresenter.mKeyguardStateController;
        Objects.requireNonNull(keyguardStateController);
        expandableNotificationRow.mSecureStateProvider = new BooleanSupplier() { // from class: com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda3
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return ((KeyguardStateControllerImpl) KeyguardStateController.this).mCanDismissLockScreen;
            }
        };
        Flags flags = Flags.INSTANCE;
        notificationRowBinderImpl.mFeatureFlags.getClass();
        expandableNotificationRow.mIsInlineReplyAnimationFlagEnabled = false;
        notificationRowBinderImpl.updateRow(notificationEntry, expandableNotificationRow);
        notificationRowBinderImpl.inflateContentViews(notificationEntry, this.f$2, expandableNotificationRow, this.f$3);
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
            notificationEntry.setMessageUriToBitmap(notificationRowBinderImpl.mContext);
        }
    }
}
