package com.android.systemui.statusbar.notification.row;

import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationSettingsController;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainerLogger;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.wmshell.BubblesManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ExpandableNotificationRowController implements NotifViewController {
    public static final Uri BUBBLES_SETTING_URI = Settings.Secure.getUriFor("notification_bubbles");
    public final ActivatableNotificationViewController mActivatableNotificationViewController;
    public final boolean mAllowLongPress;
    public final String mAppName;
    public final Optional mBubblesManagerOptional;
    public final NotificationChildrenContainerLogger mChildrenContainerLogger;
    public final SystemClock mClock;
    public final ColorUpdateLogger mColorUpdateLogger;
    public final NotificationDismissibilityProvider mDismissibilityProvider;
    public final ExpandableNotificationRowDragController mDragController;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final GroupExpansionManager mGroupExpansionManager;
    public final GroupMembershipManager mGroupMembershipManager;
    public final HeadsUpManager mHeadsUpManager;
    public final KeyguardBypassController mKeyguardBypassController;
    public final NotificationListContainer mListContainer;
    public final NotificationRowLogger mLogBufferLogger;
    public final MetricsLogger mMetricsLogger;
    public final NotificationGutsManager mNotificationGutsManager;
    public final String mNotificationKey;
    public final ExpandableNotificationRow.OnExpandClickListener mOnExpandClickListener;
    public final ExpandableNotificationRowController$$ExternalSyntheticLambda0 mOnFeedbackClickListener;
    public final OnUserInteractionCallback mOnUserInteractionCallback;
    public final PeopleNotificationIdentifier mPeopleNotificationIdentifier;
    public final PluginManager mPluginManager;
    public final RemoteInputViewSubcomponent.Factory mRemoteInputViewSubcomponentFactory;
    public final RowContentBindStage mRowContentBindStage;
    public final NotificationSettingsController mSettingsController;
    public final SmartReplyConstants mSmartReplyConstants;
    public final SmartReplyController mSmartReplyController;
    public final NotificationRowStatsLogger mStatsLogger;
    public final IStatusBarService mStatusBarService;
    public final StatusBarStateController mStatusBarStateController;
    public final ExpandableNotificationRow mView;
    final NotificationSettingsController.Listener mSettingsListener = new AnonymousClass1();
    public final AnonymousClass2 mLoggerCallback = new AnonymousClass2();
    public final AnonymousClass4 mStatusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController.4
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            ExpandableNotificationRowController.this.mView.setOnKeyguard(i == 1);
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$1, reason: invalid class name */
    public final class AnonymousClass1 implements NotificationSettingsController.Listener {
        public AnonymousClass1() {
        }

        public final void onSettingChanged(int i, Uri uri, String str) {
            if (ExpandableNotificationRowController.BUBBLES_SETTING_URI.equals(uri)) {
                ExpandableNotificationRowController expandableNotificationRowController = ExpandableNotificationRowController.this;
                int userId = expandableNotificationRowController.mView.mEntry.mSbn.getUserId();
                if (userId == -1 || userId == i) {
                    NotificationContentView notificationContentView = expandableNotificationRowController.mView.mPrivateLayout;
                    notificationContentView.mBubblesEnabledForUser = "1".equals(str);
                    notificationContentView.applyBubbleAction(notificationContentView.mExpandedChild, notificationContentView.mNotificationEntry);
                    notificationContentView.applyBubbleAction(notificationContentView.mHeadsUpChild, notificationContentView.mNotificationEntry);
                }
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$4] */
    public ExpandableNotificationRowController(ExpandableNotificationRow expandableNotificationRow, ActivatableNotificationViewController activatableNotificationViewController, RemoteInputViewSubcomponent.Factory factory, MetricsLogger metricsLogger, ColorUpdateLogger colorUpdateLogger, NotificationRowLogger notificationRowLogger, NotificationChildrenContainerLogger notificationChildrenContainerLogger, NotificationListContainer notificationListContainer, SmartReplyConstants smartReplyConstants, SmartReplyController smartReplyController, PluginManager pluginManager, SystemClock systemClock, String str, String str2, KeyguardBypassController keyguardBypassController, GroupMembershipManager groupMembershipManager, GroupExpansionManager groupExpansionManager, RowContentBindStage rowContentBindStage, NotificationRowStatsLogger notificationRowStatsLogger, HeadsUpManager headsUpManager, ExpandableNotificationRow.OnExpandClickListener onExpandClickListener, StatusBarStateController statusBarStateController, NotificationGutsManager notificationGutsManager, boolean z, OnUserInteractionCallback onUserInteractionCallback, FalsingManager falsingManager, FeatureFlags featureFlags, PeopleNotificationIdentifier peopleNotificationIdentifier, Optional<BubblesManager> optional, NotificationSettingsController notificationSettingsController, ExpandableNotificationRowDragController expandableNotificationRowDragController, NotificationDismissibilityProvider notificationDismissibilityProvider, IStatusBarService iStatusBarService) {
        this.mView = expandableNotificationRow;
        this.mListContainer = notificationListContainer;
        this.mRemoteInputViewSubcomponentFactory = factory;
        this.mActivatableNotificationViewController = activatableNotificationViewController;
        this.mPluginManager = pluginManager;
        this.mClock = systemClock;
        this.mAppName = str;
        this.mNotificationKey = str2;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mGroupMembershipManager = groupMembershipManager;
        this.mGroupExpansionManager = groupExpansionManager;
        this.mRowContentBindStage = rowContentBindStage;
        this.mStatsLogger = notificationRowStatsLogger;
        this.mHeadsUpManager = headsUpManager;
        this.mOnExpandClickListener = onExpandClickListener;
        this.mStatusBarStateController = statusBarStateController;
        this.mNotificationGutsManager = notificationGutsManager;
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mFalsingManager = falsingManager;
        Objects.requireNonNull(notificationGutsManager);
        this.mOnFeedbackClickListener = new ExpandableNotificationRowController$$ExternalSyntheticLambda0(notificationGutsManager);
        this.mAllowLongPress = z;
        this.mFeatureFlags = featureFlags;
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
        this.mSettingsController = notificationSettingsController;
        this.mDragController = expandableNotificationRowDragController;
        this.mMetricsLogger = metricsLogger;
        this.mChildrenContainerLogger = notificationChildrenContainerLogger;
        this.mColorUpdateLogger = colorUpdateLogger;
        this.mLogBufferLogger = notificationRowLogger;
        this.mSmartReplyConstants = smartReplyConstants;
        this.mSmartReplyController = smartReplyController;
        this.mDismissibilityProvider = notificationDismissibilityProvider;
        this.mStatusBarService = iStatusBarService;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void addChildAt(NodeController nodeController, int i) {
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) nodeController.getView();
        this.mView.addChildNotification((ExpandableNotificationRow) nodeController.getView(), i);
        NotificationStackScrollLayoutController.this.mView.onViewAddedInternal(expandableNotificationRow);
        expandableNotificationRow.mChangingPosition = false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final View getChildAt(int i) {
        ExpandableNotificationRow expandableNotificationRow = this.mView;
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
        if (notificationChildrenContainer == null || ((ArrayList) notificationChildrenContainer.mAttachedChildren).size() <= i) {
            return null;
        }
        return (ExpandableNotificationRow) ((ArrayList) expandableNotificationRow.mChildrenContainer.mAttachedChildren).get(i);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final int getChildCount() {
        List attachedChildren = this.mView.getAttachedChildren();
        if (attachedChildren != null) {
            return attachedChildren.size();
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final String getNodeLabel() {
        return NotificationUtils.logKey(this.mView.mEntry);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final View getView() {
        return this.mView;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void moveChildTo(NodeController nodeController, int i) {
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) nodeController.getView();
        expandableNotificationRow.mChangingPosition = true;
        ExpandableNotificationRow expandableNotificationRow2 = this.mView;
        expandableNotificationRow2.removeChildNotification(expandableNotificationRow);
        expandableNotificationRow2.addChildNotification(expandableNotificationRow, i);
        expandableNotificationRow.mChangingPosition = false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final boolean offerToKeepInParentForAnimation() {
        ExpandableNotificationRow expandableNotificationRow = this.mView;
        if (expandableNotificationRow.mEntry.mDismissState != NotificationEntry.DismissState.PARENT_DISMISSED) {
            return false;
        }
        expandableNotificationRow.mKeepInParentForDismissAnimation = true;
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void removeChild(NodeController nodeController, boolean z) {
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) nodeController.getView();
        if (z) {
            expandableNotificationRow.mChangingPosition = true;
        }
        ExpandableNotificationRow expandableNotificationRow2 = this.mView;
        expandableNotificationRow2.removeChildNotification(expandableNotificationRow);
        if (z) {
            return;
        }
        NotificationStackScrollLayoutController.this.mView.onViewRemovedInternal(expandableNotificationRow, expandableNotificationRow2.mChildrenContainer);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final boolean removeFromParentIfKeptForAnimation() {
        ExpandableNotificationRow expandableNotificationRow = this.mView;
        ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow.mNotificationParent;
        if (!expandableNotificationRow.mKeepInParentForDismissAnimation || expandableNotificationRow2 == null) {
            return false;
        }
        expandableNotificationRow2.removeChildNotification(expandableNotificationRow);
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void resetKeepInParentForAnimation() {
        this.mView.mKeepInParentForDismissAnimation = false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void onViewAdded() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void onViewRemoved() {
    }
}
