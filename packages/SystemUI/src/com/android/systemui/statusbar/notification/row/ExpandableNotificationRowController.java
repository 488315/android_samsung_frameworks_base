package com.android.systemui.statusbar.notification.row;

import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.EntryAdapterFactory;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationSettingsController;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainerLogger;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.window.domain.interactor.WindowRootViewBlurInteractor;
import com.google.android.msdl.domain.MSDLPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ExpandableNotificationRowController implements NotifViewController {
    public static final Uri BUBBLES_SETTING_URI = Settings.Secure.getUriFor("notification_bubbles");
    public final ActivatableNotificationViewController mActivatableNotificationViewController;
    public final boolean mAllowLongPress;
    public final String mAppName;
    public final NotificationChildrenContainerLogger mChildrenContainerLogger;
    public final SystemClock mClock;
    public final ColorUpdateLogger mColorUpdateLogger;
    public final NotificationDismissibilityProvider mDismissibilityProvider;
    public final ExpandableNotificationRowDragController mDragController;
    public final EntryAdapterFactory mEntryAdapterFactory;
    public final FalsingManager mFalsingManager;
    public final FeatureFlagsClassic mFeatureFlags;
    public final GroupExpansionManager mGroupExpansionManager;
    public final GroupMembershipManager mGroupMembershipManager;
    public final HeadsUpManager mHeadsUpManager;
    public final KeyguardBypassController mKeyguardBypassController;
    public final NotificationListContainer mListContainer;
    public final NotificationRowLogger mLogBufferLogger;
    public ExpandableNotificationRowController$$ExternalSyntheticLambda1 mLongPressListener;
    public final MediaDataManager mMediaDataManager;
    public final SecMediaHost mMediaHost;
    public final MetricsLogger mMetricsLogger;
    public final NotificationGutsManager mNotificationGutsManager;
    public final String mNotificationKey;
    public final NotificationRebindingTracker mNotificationRebindingTracker;
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
    public final UiEventLogger mUiEventLogger;
    public final ExpandableNotificationRow mView;
    final NotificationSettingsController.Listener mSettingsListener = new AnonymousClass1();
    public final AnonymousClass2 mLoggerCallback = new AnonymousClass2();
    public final AnonymousClass4 mStatusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController.4
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            ExpandableNotificationRowController.this.mView.setOnKeyguard(i == 1);
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$1, reason: invalid class name */
    public class AnonymousClass1 implements NotificationSettingsController.Listener {
        public AnonymousClass1() {
        }

        public final void onSettingChanged(int i, Uri uri, String str) {
            if (ExpandableNotificationRowController.BUBBLES_SETTING_URI.equals(uri)) {
                int i2 = NotificationBundleUi.$r8$clinit;
                ExpandableNotificationRowController expandableNotificationRowController = ExpandableNotificationRowController.this;
                int userId = expandableNotificationRowController.mView.getEntryLegacy().mSbn.getUserId();
                if (userId == -1 || userId == i) {
                    NotificationContentView notificationContentView = expandableNotificationRowController.mView.mPrivateLayout;
                    notificationContentView.mBubblesEnabledForUser = "1".equals(str);
                    notificationContentView.applyBubbleAction(notificationContentView.mExpandedChild, notificationContentView.mNotificationEntry);
                    notificationContentView.applyBubbleAction(notificationContentView.mHeadsUpChild, notificationContentView.mNotificationEntry);
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }

        public final void logStartAppearAnimation(String str, boolean z) {
            NotificationRowLogger notificationRowLogger = ExpandableNotificationRowController.this.mLogBufferLogger;
            notificationRowLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda0 = new NotificationRowLogger$$ExternalSyntheticLambda0(12);
            LogBuffer logBuffer = notificationRowLogger.notificationRenderBuffer;
            LogMessage obtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = str;
            logMessageImpl.bool1 = z;
            logBuffer.commit(obtain);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$4] */
    public ExpandableNotificationRowController(ExpandableNotificationRow expandableNotificationRow, ActivatableNotificationViewController activatableNotificationViewController, RemoteInputViewSubcomponent.Factory factory, MetricsLogger metricsLogger, ColorUpdateLogger colorUpdateLogger, NotificationRowLogger notificationRowLogger, NotificationChildrenContainerLogger notificationChildrenContainerLogger, NotificationListContainer notificationListContainer, SmartReplyConstants smartReplyConstants, SmartReplyController smartReplyController, PluginManager pluginManager, SystemClock systemClock, String str, String str2, KeyguardBypassController keyguardBypassController, GroupMembershipManager groupMembershipManager, GroupExpansionManager groupExpansionManager, RowContentBindStage rowContentBindStage, NotificationRowStatsLogger notificationRowStatsLogger, HeadsUpManager headsUpManager, ExpandableNotificationRow.OnExpandClickListener onExpandClickListener, StatusBarStateController statusBarStateController, NotificationGutsManager notificationGutsManager, boolean z, OnUserInteractionCallback onUserInteractionCallback, FalsingManager falsingManager, FeatureFlagsClassic featureFlagsClassic, PeopleNotificationIdentifier peopleNotificationIdentifier, NotificationSettingsController notificationSettingsController, ExpandableNotificationRowDragController expandableNotificationRowDragController, NotificationDismissibilityProvider notificationDismissibilityProvider, IStatusBarService iStatusBarService, UiEventLogger uiEventLogger, MSDLPlayer mSDLPlayer, NotificationRebindingTracker notificationRebindingTracker, EntryAdapterFactory entryAdapterFactory, WindowRootViewBlurInteractor windowRootViewBlurInteractor, SecMediaHost secMediaHost, MediaDataManager mediaDataManager) {
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
        this.mNotificationRebindingTracker = notificationRebindingTracker;
        Objects.requireNonNull(notificationGutsManager);
        this.mOnFeedbackClickListener = new ExpandableNotificationRowController$$ExternalSyntheticLambda0();
        this.mAllowLongPress = z;
        this.mFeatureFlags = featureFlagsClassic;
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
        this.mUiEventLogger = uiEventLogger;
        this.mEntryAdapterFactory = entryAdapterFactory;
        this.mMediaHost = secMediaHost;
        this.mMediaDataManager = mediaDataManager;
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
            return ((ArrayList) attachedChildren).size();
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final String getNodeLabel() {
        int i = NotificationBundleUi.$r8$clinit;
        return NotificationUtils.logKey(this.mView.getEntryLegacy());
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
        expandableNotificationRow.getClass();
        int i = NotificationBundleUi.$r8$clinit;
        if (expandableNotificationRow.getEntryLegacy().mDismissState != NotificationEntry.DismissState.PARENT_DISMISSED) {
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
