package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutManager;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.SnoozeCriterion;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.settingslib.notification.ConversationIconFactory;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.FeedbackIcon;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.FavoriteNotifCoordnator;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewListener;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewManager;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationSnooze;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CapturedBlurContainerController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.AnonymousClass2;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.wmshell.BubblesManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.function.Consumer;

public final class NotificationGutsManager implements NotifGutsViewManager, CoreStartable {
    public final AccessibilityManager mAccessibilityManager;
    public final ActivityStarter mActivityStarter;
    public final AssistantFeedbackController mAssistantFeedbackController;
    public final Handler mBgHandler;
    public final SecQpBlurController mBlurController;
    public final Optional mBubblesManagerOptional;
    public final ChannelEditorDialogController mChannelEditorDialogController;
    public final Context mContext;
    public final UserContextProvider mContextTracker;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public String mFavoriteSectionSettingsLists;
    public NotifGutsViewListener mGutsListener;
    public NotificationMenuRowPlugin.MenuItem mGutsMenuItem;
    public final HeadsUpManager mHeadsUpManager;
    public final HighPriorityProvider mHighPriorityProvider;
    public FavoriteNotifCoordnator mInvalidateListener;
    public final JavaAdapter mJavaAdapter;
    public final LauncherApps mLauncherApps;
    public NotificationListContainer mListContainer;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final Handler mMainHandler;
    public final MetricsLogger mMetricsLogger;
    public NotificationActivityStarter mNotificationActivityStarter;
    public NotificationGuts mNotificationGutsExposed;
    public final INotificationManager mNotificationManager;
    public StatusBarNotificationPresenter.AnonymousClass2 mOnSettingsClickListener;
    public final OnUserInteractionCallback mOnUserInteractionCallback;
    public AnonymousClass1 mOpenRunnable;
    public final PeopleSpaceWidgetManager mPeopleSpaceWidgetManager;
    public NotificationPresenter mPresenter;
    public final ShadeController mShadeController;
    public final ShortcutManager mShortcutManager;
    public final IStatusBarService mStatusBarService;
    public final StatusBarStateController mStatusBarStateController;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final WindowRootViewVisibilityInteractor mWindowRootViewVisibilityInteractor;
    public boolean mIsGoingGutOpenedFromLock = false;
    public final AnonymousClass2 mOnFavoriteNotifUpdateListener = new Object(this) { // from class: com.android.systemui.statusbar.notification.row.NotificationGutsManager.2
    };
    private SettingsHelper.OnChangedCallback mSettingsChangedListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationGutsManager.3
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            NotificationGutsManager notificationGutsManager = NotificationGutsManager.this;
            notificationGutsManager.mFavoriteSectionSettingsLists = Settings.Secure.getStringForUser(notificationGutsManager.mContext.getContentResolver(), SettingsHelper.INDEX_SECURE_NOTIFICATION_PANEL_SHOW_FAVORITE_APP_NOTIFICATIONS, ((NotificationLockscreenUserManagerImpl) notificationGutsManager.mLockscreenUserManager).mCurrentUserId);
            notificationGutsManager.mInvalidateListener.onUpdateNotifStack();
        }
    };

    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.systemui.statusbar.notification.row.NotificationGutsManager$2] */
    public NotificationGutsManager(Context context, Handler handler, Handler handler2, JavaAdapter javaAdapter, AccessibilityManager accessibilityManager, HighPriorityProvider highPriorityProvider, INotificationManager iNotificationManager, UserManager userManager, PeopleSpaceWidgetManager peopleSpaceWidgetManager, LauncherApps launcherApps, ShortcutManager shortcutManager, ChannelEditorDialogController channelEditorDialogController, UserContextProvider userContextProvider, AssistantFeedbackController assistantFeedbackController, Optional<BubblesManager> optional, UiEventLogger uiEventLogger, OnUserInteractionCallback onUserInteractionCallback, ShadeController shadeController, WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor, NotificationLockscreenUserManager notificationLockscreenUserManager, StatusBarStateController statusBarStateController, IStatusBarService iStatusBarService, DeviceProvisionedController deviceProvisionedController, MetricsLogger metricsLogger, HeadsUpManager headsUpManager, ActivityStarter activityStarter, SecQpBlurController secQpBlurController) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mBgHandler = handler2;
        this.mJavaAdapter = javaAdapter;
        this.mAccessibilityManager = accessibilityManager;
        this.mHighPriorityProvider = highPriorityProvider;
        this.mNotificationManager = iNotificationManager;
        this.mUserManager = userManager;
        this.mPeopleSpaceWidgetManager = peopleSpaceWidgetManager;
        this.mLauncherApps = launcherApps;
        this.mContextTracker = userContextProvider;
        this.mChannelEditorDialogController = channelEditorDialogController;
        this.mAssistantFeedbackController = assistantFeedbackController;
        this.mBubblesManagerOptional = optional;
        this.mUiEventLogger = uiEventLogger;
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mShadeController = shadeController;
        this.mWindowRootViewVisibilityInteractor = windowRootViewVisibilityInteractor;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mStatusBarService = iStatusBarService;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mMetricsLogger = metricsLogger;
        this.mHeadsUpManager = headsUpManager;
        this.mActivityStarter = activityStarter;
        this.mBlurController = secQpBlurController;
        context.getSharedPreferences("favorite_notif", 0);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSettingsChangedListener, Settings.Secure.getUriFor(SettingsHelper.INDEX_SECURE_NOTIFICATION_PANEL_SHOW_FAVORITE_APP_NOTIFICATIONS));
        this.mFavoriteSectionSettingsLists = Settings.Secure.getStringForUser(context.getContentResolver(), SettingsHelper.INDEX_SECURE_NOTIFICATION_PANEL_SHOW_FAVORITE_APP_NOTIFICATIONS, ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mCurrentUserId);
    }

    public boolean bindGuts(final ExpandableNotificationRow expandableNotificationRow, NotificationMenuRowPlugin.MenuItem menuItem) {
        final NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        if (expandableNotificationRow.mGuts != null && (menuItem.getGutsView() instanceof NotificationGuts.GutsContent)) {
            expandableNotificationRow.mGuts.setGutsContent((NotificationGuts.GutsContent) menuItem.getGutsView());
        }
        expandableNotificationRow.setTag(notificationEntry.mSbn.getPackageName());
        expandableNotificationRow.mGuts.mClosedListener = new NotificationGuts.OnGutsClosedListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationGutsManager$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.OnGutsClosedListener
            public final void onGutsClosed(NotificationGuts notificationGuts) {
                NotificationGutsManager notificationGutsManager = NotificationGutsManager.this;
                notificationGutsManager.getClass();
                ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                expandableNotificationRow2.updateContentAccessibilityImportanceForGuts(true);
                expandableNotificationRow2.mIsSnoozed = false;
                NotificationGuts.GutsContent gutsContent = notificationGuts.mGutsContent;
                if (!(gutsContent != null ? gutsContent.willBeRemoved() : false)) {
                    ((NotificationStackScrollLayoutController.NotificationListContainerImpl) notificationGutsManager.mListContainer).onHeightChanged(expandableNotificationRow2, true ^ ((StatusBarNotificationPresenter) notificationGutsManager.mPresenter).mPanelExpansionInteractor.isFullyCollapsed());
                }
                if (notificationGutsManager.mNotificationGutsExposed == notificationGuts) {
                    notificationGutsManager.mNotificationGutsExposed = null;
                    notificationGutsManager.mGutsMenuItem = null;
                }
                NotifGutsViewListener notifGutsViewListener = notificationGutsManager.mGutsListener;
                if (notifGutsViewListener != null) {
                    notifGutsViewListener.onGutsClose(notificationEntry);
                }
                ((HeadsUpManagerPhone) notificationGutsManager.mHeadsUpManager).setGutsShown(expandableNotificationRow2.mEntry, false);
            }
        };
        View gutsView = menuItem.getGutsView();
        if ((menuItem instanceof GutContentInitializer) && ((GutContentInitializer) menuItem).initializeGutContentView(expandableNotificationRow)) {
            return true;
        }
        try {
            if (gutsView instanceof NotificationSnooze) {
                initializeSnoozeView(expandableNotificationRow, (NotificationSnooze) gutsView);
            } else if (gutsView instanceof NotificationInfo) {
                initializeNotificationInfo(expandableNotificationRow, (NotificationInfo) gutsView);
            } else if (gutsView instanceof NotificationConversationInfo) {
                initializeConversationNotificationInfo(expandableNotificationRow, (NotificationConversationInfo) gutsView);
            } else if (gutsView instanceof PartialConversationInfo) {
                initializePartialConversationNotificationInfo(expandableNotificationRow, (PartialConversationInfo) gutsView);
            } else if (gutsView instanceof FeedbackInfo) {
                FeedbackInfo feedbackInfo = (FeedbackInfo) gutsView;
                NotificationEntry notificationEntry2 = expandableNotificationRow.mEntry;
                AssistantFeedbackController assistantFeedbackController = this.mAssistantFeedbackController;
                if (((FeedbackIcon) assistantFeedbackController.mIcons.get(assistantFeedbackController.getFeedbackStatus(notificationEntry2))) != null) {
                    StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
                    UserHandle user = statusBarNotification.getUser();
                    feedbackInfo.bindGuts(CentralSurfaces.getPackageManagerForUser(user.getIdentifier(), this.mContext), statusBarNotification, expandableNotificationRow.mEntry, expandableNotificationRow, this.mAssistantFeedbackController, this.mStatusBarService, this);
                }
            }
            return true;
        } catch (Exception e) {
            Log.e("NotificationGutsManager", "error binding guts", e);
            return false;
        }
    }

    public final void closeAndSaveGuts(boolean z, boolean z2, boolean z3, boolean z4) {
        NotificationListContainer notificationListContainer;
        NotificationGuts notificationGuts = this.mNotificationGutsExposed;
        if (notificationGuts != null) {
            notificationGuts.removeCallbacks(this.mOpenRunnable);
            NotificationGuts notificationGuts2 = this.mNotificationGutsExposed;
            NotificationGuts.GutsContent gutsContent = notificationGuts2.mGutsContent;
            if (gutsContent != null && ((gutsContent.isLeavebehind() && z) || (!notificationGuts2.mGutsContent.isLeavebehind() && z3))) {
                notificationGuts2.closeControls(-1, -1, notificationGuts2.mGutsContent.shouldBeSavedOnClose(), z2);
            }
        }
        if (!z4 || (notificationListContainer = this.mListContainer) == null) {
            return;
        }
        NotificationStackScrollLayoutController.this.mSwipeHelper.resetExposedMenuView(false, true);
    }

    public void initializeConversationNotificationInfo(ExpandableNotificationRow expandableNotificationRow, NotificationConversationInfo notificationConversationInfo) throws Exception {
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        String packageName = statusBarNotification.getPackageName();
        UserHandle user = statusBarNotification.getUser();
        PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(user.getIdentifier(), this.mContext);
        NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1 = (!user.equals(UserHandle.ALL) || ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).mCurrentUserId == 0) ? new NotificationGutsManager$$ExternalSyntheticLambda1(this, notificationGuts, statusBarNotification, packageName, expandableNotificationRow, 1) : null;
        Context context = this.mContext;
        ConversationIconFactory conversationIconFactory = new ConversationIconFactory(context, this.mLauncherApps, packageManagerForUser, IconDrawableFactory.newInstance(context, false), this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_guts_conversation_icon_size));
        UserManager userManager = this.mUserManager;
        INotificationManager iNotificationManager = this.mNotificationManager;
        NotificationChannel channel = notificationEntry.mRanking.getChannel();
        Notification.BubbleMetadata bubbleMetadata = notificationEntry.mBubbleMetadata;
        ((UserTrackerImpl) this.mContextTracker).getUserContext();
        notificationConversationInfo.bindNotification(packageManagerForUser, userManager, this.mPeopleSpaceWidgetManager, iNotificationManager, this.mOnUserInteractionCallback, packageName, channel, notificationEntry, bubbleMetadata, notificationGutsManager$$ExternalSyntheticLambda1, conversationIconFactory, ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).deviceProvisioned.get(), this.mMainHandler, this.mBgHandler, this.mBubblesManagerOptional, this.mShadeController);
    }

    public void initializeNotificationInfo(ExpandableNotificationRow expandableNotificationRow, final NotificationInfo notificationInfo) throws Exception {
        NotificationChannelGroup notificationChannelGroupForPackage;
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
        String packageName = statusBarNotification.getPackageName();
        UserHandle user = statusBarNotification.getUser();
        PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(user.getIdentifier(), this.mContext);
        NotificationGutsManager$$ExternalSyntheticLambda3 notificationGutsManager$$ExternalSyntheticLambda3 = new NotificationGutsManager$$ExternalSyntheticLambda3(this, notificationGuts, statusBarNotification, expandableNotificationRow);
        NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1 = (!user.equals(UserHandle.ALL) || ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).mCurrentUserId == 0) ? new NotificationGutsManager$$ExternalSyntheticLambda1(this, notificationGuts, statusBarNotification, packageName, expandableNotificationRow, 2) : null;
        INotificationManager iNotificationManager = this.mNotificationManager;
        OnUserInteractionCallback onUserInteractionCallback = this.mOnUserInteractionCallback;
        ChannelEditorDialogController channelEditorDialogController = this.mChannelEditorDialogController;
        NotificationChannel channel = expandableNotificationRow.mEntry.mRanking.getChannel();
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        UiEventLogger uiEventLogger = this.mUiEventLogger;
        boolean z = ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).deviceProvisioned.get();
        boolean isNonblockable = expandableNotificationRow.getIsNonblockable();
        boolean isHighPriority = this.mHighPriorityProvider.isHighPriority(expandableNotificationRow.mEntry, true);
        AssistantFeedbackController assistantFeedbackController = this.mAssistantFeedbackController;
        MetricsLogger metricsLogger = this.mMetricsLogger;
        notificationInfo.mINotificationManager = iNotificationManager;
        notificationInfo.mMetricsLogger = metricsLogger;
        notificationInfo.mOnUserInteractionCallback = onUserInteractionCallback;
        notificationInfo.mChannelEditorDialogController = channelEditorDialogController;
        notificationInfo.mAssistantFeedbackController = assistantFeedbackController;
        notificationInfo.mPackageName = packageName;
        notificationInfo.mEntry = notificationEntry;
        notificationInfo.mSbn = notificationEntry.mSbn;
        notificationInfo.mPm = packageManagerForUser;
        notificationInfo.mAppSettingsClickListener = notificationGutsManager$$ExternalSyntheticLambda3;
        notificationInfo.mAppName = packageName;
        notificationInfo.mOnSettingsClickListener = notificationGutsManager$$ExternalSyntheticLambda1;
        notificationInfo.mSingleNotificationChannel = channel;
        notificationInfo.mStartingChannelImportance = channel.getImportance();
        notificationInfo.mWasShownHighPriority = isHighPriority;
        notificationInfo.mIsNonblockable = isNonblockable;
        notificationInfo.mAppUid = notificationInfo.mSbn.getUid();
        notificationInfo.mDelegatePkg = notificationInfo.mSbn.getOpPkg();
        notificationInfo.mIsDeviceProvisioned = z;
        notificationInfo.mShowAutomaticSetting = notificationInfo.mAssistantFeedbackController.mFeedbackEnabled;
        notificationInfo.mUiEventLogger = uiEventLogger;
        notificationInfo.mIsSystemRegisteredCall = notificationInfo.mSbn.getNotification().isStyle(Notification.CallStyle.class) && notificationInfo.mINotificationManager.isInCall(notificationInfo.mSbn.getPackageName(), notificationInfo.mSbn.getUid());
        notificationInfo.mIsSingleDefaultChannel = notificationInfo.mSingleNotificationChannel.getId().equals("miscellaneous") && notificationInfo.mINotificationManager.getNumNotificationChannelsForPackage(packageName, notificationInfo.mAppUid, false) == 1;
        notificationInfo.mIsAutomaticChosen = notificationInfo.getAlertingBehavior() == 2;
        notificationInfo.mPkgIcon = null;
        ApplicationInfo applicationInfo = (ApplicationInfo) notificationInfo.mSbn.getNotification().extras.getParcelable("android.appInfo", ApplicationInfo.class);
        if (applicationInfo != null) {
            try {
                notificationInfo.mAppName = String.valueOf(notificationInfo.mPm.getApplicationLabel(applicationInfo));
                notificationInfo.mPkgIcon = notificationInfo.mPm.getApplicationIcon(applicationInfo);
            } catch (Exception unused) {
            }
        }
        if (notificationInfo.mPkgIcon == null) {
            notificationInfo.mPkgIcon = notificationInfo.mPm.getDefaultActivityIcon();
        }
        ((ImageView) notificationInfo.findViewById(R.id.pkg_icon)).setImageDrawable(notificationInfo.mPkgIcon);
        ((TextView) notificationInfo.findViewById(R.id.pkg_name)).setText(notificationInfo.mAppName);
        TextView textView = (TextView) notificationInfo.findViewById(R.id.delegate_name);
        if (TextUtils.equals(notificationInfo.mPackageName, notificationInfo.mDelegatePkg)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
        View findViewById = notificationInfo.findViewById(R.id.app_settings);
        PackageManager packageManager = notificationInfo.mPm;
        String str = notificationInfo.mPackageName;
        NotificationChannel notificationChannel = notificationInfo.mSingleNotificationChannel;
        int id = notificationInfo.mSbn.getId();
        String tag = notificationInfo.mSbn.getTag();
        final Intent intent = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.NOTIFICATION_PREFERENCES").setPackage(str);
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 65536);
        if (queryIntentActivities == null || queryIntentActivities.size() == 0 || queryIntentActivities.get(0) == null) {
            intent = null;
        } else {
            ActivityInfo activityInfo = queryIntentActivities.get(0).activityInfo;
            intent.setClassName(activityInfo.packageName, activityInfo.name);
            if (notificationChannel != null) {
                intent.putExtra("android.intent.extra.CHANNEL_ID", notificationChannel.getId());
            }
            intent.putExtra("android.intent.extra.NOTIFICATION_ID", id);
            intent.putExtra("android.intent.extra.NOTIFICATION_TAG", tag);
        }
        if (intent == null || TextUtils.isEmpty(notificationInfo.mSbn.getNotification().getSettingsText())) {
            findViewById.setVisibility(8);
        } else {
            findViewById.setVisibility(0);
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationInfo$$ExternalSyntheticLambda8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NotificationInfo notificationInfo2 = NotificationInfo.this;
                    Intent intent2 = intent;
                    NotificationGutsManager$$ExternalSyntheticLambda3 notificationGutsManager$$ExternalSyntheticLambda32 = notificationInfo2.mAppSettingsClickListener;
                    StatusBarNotification statusBarNotification2 = notificationGutsManager$$ExternalSyntheticLambda32.f$2;
                    NotificationGutsManager notificationGutsManager = notificationGutsManager$$ExternalSyntheticLambda32.f$0;
                    notificationGutsManager.mMetricsLogger.action(206);
                    notificationGutsManager$$ExternalSyntheticLambda32.f$1.resetFalsingCheck();
                    NotificationActivityStarter notificationActivityStarter = notificationGutsManager.mNotificationActivityStarter;
                    int uid = statusBarNotification2.getUid();
                    StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) notificationActivityStarter;
                    ActivityStarter activityStarter = statusBarNotificationActivityStarter.mActivityStarter;
                    activityStarter.dismissKeyguardThenExecute(statusBarNotificationActivityStarter.new AnonymousClass2(notificationGutsManager$$ExternalSyntheticLambda32.f$3, activityStarter.shouldAnimateLaunch(true), intent2, uid), null, false);
                }
            });
        }
        View findViewById2 = notificationInfo.findViewById(R.id.info);
        final int i = notificationInfo.mAppUid;
        findViewById2.setOnClickListener((i < 0 || notificationInfo.mOnSettingsClickListener == null || !notificationInfo.mIsDeviceProvisioned) ? null : new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationInfo$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationInfo notificationInfo2 = NotificationInfo.this;
                notificationInfo2.mOnSettingsClickListener.onClick(notificationInfo2.mSingleNotificationChannel, i);
            }
        });
        findViewById2.setVisibility(findViewById2.hasOnClickListeners() ? 0 : 8);
        TextView textView2 = (TextView) notificationInfo.findViewById(R.id.channel_name);
        if (notificationInfo.mIsSingleDefaultChannel) {
            textView2.setVisibility(8);
        } else {
            textView2.setText(notificationInfo.mSingleNotificationChannel.getName());
        }
        NotificationChannel notificationChannel2 = notificationInfo.mSingleNotificationChannel;
        CharSequence name = (notificationChannel2 == null || notificationChannel2.getGroup() == null || (notificationChannelGroupForPackage = notificationInfo.mINotificationManager.getNotificationChannelGroupForPackage(notificationInfo.mSingleNotificationChannel.getGroup(), notificationInfo.mPackageName, notificationInfo.mAppUid)) == null) ? null : notificationChannelGroupForPackage.getName();
        TextView textView3 = (TextView) notificationInfo.findViewById(R.id.group_name);
        if (name != null) {
            textView3.setText(name);
            textView3.setVisibility(0);
        } else {
            textView3.setVisibility(8);
        }
        notificationInfo.bindInlineControls();
        notificationInfo.logUiEvent(NotificationControlsEvent.NOTIFICATION_CONTROLS_OPEN);
        MetricsLogger metricsLogger2 = notificationInfo.mMetricsLogger;
        StatusBarNotification statusBarNotification2 = notificationInfo.mSbn;
        metricsLogger2.write((statusBarNotification2 == null ? new LogMaker(1621) : statusBarNotification2.getLogMaker().setCategory(1621)).setCategory(204).setType(1).setSubtype(0));
    }

    public void initializePartialConversationNotificationInfo(ExpandableNotificationRow expandableNotificationRow, final PartialConversationInfo partialConversationInfo) throws Exception {
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
        String packageName = statusBarNotification.getPackageName();
        UserHandle user = statusBarNotification.getUser();
        PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(user.getIdentifier(), this.mContext);
        View.OnClickListener onClickListener = null;
        NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1 = (!user.equals(UserHandle.ALL) || ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).mCurrentUserId == 0) ? new NotificationGutsManager$$ExternalSyntheticLambda1(this, notificationGuts, statusBarNotification, packageName, expandableNotificationRow, 0) : null;
        NotificationChannel channel = expandableNotificationRow.mEntry.mRanking.getChannel();
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        boolean z = ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).deviceProvisioned.get();
        boolean isNonblockable = expandableNotificationRow.getIsNonblockable();
        partialConversationInfo.mPackageName = packageName;
        StatusBarNotification statusBarNotification2 = notificationEntry.mSbn;
        partialConversationInfo.mSbn = statusBarNotification2;
        partialConversationInfo.mPm = packageManagerForUser;
        partialConversationInfo.mAppName = packageName;
        partialConversationInfo.mOnSettingsClickListener = notificationGutsManager$$ExternalSyntheticLambda1;
        partialConversationInfo.mNotificationChannel = channel;
        partialConversationInfo.mAppUid = statusBarNotification2.getUid();
        partialConversationInfo.mDelegatePkg = partialConversationInfo.mSbn.getOpPkg();
        partialConversationInfo.mIsDeviceProvisioned = z;
        partialConversationInfo.mIsNonBlockable = isNonblockable;
        partialConversationInfo.mChannelEditorDialogController = this.mChannelEditorDialogController;
        try {
            ApplicationInfo applicationInfo = partialConversationInfo.mPm.getApplicationInfo(partialConversationInfo.mPackageName, 795136);
            if (applicationInfo != null) {
                partialConversationInfo.mAppName = String.valueOf(partialConversationInfo.mPm.getApplicationLabel(applicationInfo));
                partialConversationInfo.mPkgIcon = partialConversationInfo.mPm.getApplicationIcon(applicationInfo);
            }
        } catch (PackageManager.NameNotFoundException unused) {
            partialConversationInfo.mPkgIcon = partialConversationInfo.mPm.getDefaultActivityIcon();
        }
        ((TextView) partialConversationInfo.findViewById(R.id.name)).setText(partialConversationInfo.mAppName);
        ((ImageView) partialConversationInfo.findViewById(R.id.icon)).setImageDrawable(partialConversationInfo.mPkgIcon);
        TextView textView = (TextView) partialConversationInfo.findViewById(R.id.delegate_name);
        int i = 8;
        if (TextUtils.equals(partialConversationInfo.mPackageName, partialConversationInfo.mDelegatePkg)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
        final int i2 = partialConversationInfo.mAppUid;
        if (i2 >= 0 && partialConversationInfo.mOnSettingsClickListener != null && partialConversationInfo.mIsDeviceProvisioned) {
            onClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.PartialConversationInfo$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PartialConversationInfo partialConversationInfo2 = PartialConversationInfo.this;
                    partialConversationInfo2.mOnSettingsClickListener.onClick(partialConversationInfo2.mNotificationChannel, i2);
                }
            };
        }
        View findViewById = partialConversationInfo.findViewById(R.id.info);
        findViewById.setOnClickListener(onClickListener);
        findViewById.setVisibility(findViewById.hasOnClickListeners() ? 0 : 8);
        partialConversationInfo.findViewById(R.id.settings_link).setOnClickListener(onClickListener);
        ((TextView) partialConversationInfo.findViewById(R.id.non_configurable_text)).setText(partialConversationInfo.getResources().getString(R.string.no_shortcut, partialConversationInfo.mAppName));
        View findViewById2 = partialConversationInfo.findViewById(R.id.turn_off_notifications);
        findViewById2.setOnClickListener(new PartialConversationInfo$$ExternalSyntheticLambda0(partialConversationInfo, 1));
        if (findViewById2.hasOnClickListeners() && !partialConversationInfo.mIsNonBlockable) {
            i = 0;
        }
        findViewById2.setVisibility(i);
        View findViewById3 = partialConversationInfo.findViewById(R.id.done);
        findViewById3.setOnClickListener(partialConversationInfo.mOnDone);
        findViewById3.setAccessibilityDelegate(partialConversationInfo.mGutsContainer.getAccessibilityDelegate());
    }

    public final void initializeSnoozeView(final ExpandableNotificationRow expandableNotificationRow, NotificationSnooze notificationSnooze) {
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        notificationSnooze.mSnoozeListener = NotificationStackScrollLayoutController.this.mSwipeHelper;
        notificationSnooze.mSbn = statusBarNotification;
        List snoozeCriteria = notificationEntry.mRanking.getSnoozeCriteria();
        if (snoozeCriteria != null) {
            notificationSnooze.mSnoozeOptions.clear();
            notificationSnooze.mSnoozeOptions = notificationSnooze.getDefaultSnoozeOptions();
            int min = Math.min(1, snoozeCriteria.size());
            for (int i = 0; i < min; i++) {
                SnoozeCriterion snoozeCriterion = (SnoozeCriterion) snoozeCriteria.get(i);
                notificationSnooze.mSnoozeOptions.add(new NotificationSnooze.NotificationSnoozeOption(notificationSnooze, snoozeCriterion, 0, snoozeCriterion.getExplanation(), snoozeCriterion.getConfirmation(), new AccessibilityNodeInfo.AccessibilityAction(R.id.action_snooze_assistant_suggestion_1, snoozeCriterion.getExplanation())));
            }
            notificationSnooze.createOptionViews();
        }
        notificationGuts.mHeightListener = new NotificationGuts.OnHeightChangedListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationGutsManager$$ExternalSyntheticLambda6
            @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.OnHeightChangedListener
            public final void onHeightChanged() {
                NotificationListContainer notificationListContainer = NotificationGutsManager.this.mListContainer;
                ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                ((NotificationStackScrollLayoutController.NotificationListContainerImpl) notificationListContainer).onHeightChanged(expandableNotificationRow2, expandableNotificationRow2.isShown());
            }
        };
    }

    public final boolean isFavoriteNotif(String str) {
        String str2 = this.mFavoriteSectionSettingsLists;
        HashSet hashSet = new HashSet();
        if (str2 != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(str2, ";");
            while (stringTokenizer.hasMoreTokens()) {
                hashSet.add(stringTokenizer.nextToken());
            }
        }
        return hashSet.contains(str);
    }

    public final boolean openGuts(View view, int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem) {
        if (!(menuItem.getGutsView() instanceof NotificationGuts.GutsContent) || !((NotificationGuts.GutsContent) menuItem.getGutsView()).needsFalsingProtection()) {
            return openGutsInternal(view, i, i2, menuItem);
        }
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        if (statusBarStateController instanceof StatusBarStateControllerImpl) {
            ((StatusBarStateControllerImpl) statusBarStateController).mLeaveOpenOnKeyguardHide = true;
            if (((StatusBarStateControllerImpl) statusBarStateController).mState == 1) {
                this.mIsGoingGutOpenedFromLock = true;
            }
        }
        KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_GUTS);
        if (this.mIsGoingGutOpenedFromLock) {
            SecQpBlurController secQpBlurController = this.mBlurController;
            secQpBlurController.getClass();
            if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
                secQpBlurController.doCaptureContainerAlpha(1.0f, CapturedBlurContainerController.BlurType.QUICK_PANEL);
            } else {
                secQpBlurController.doWindowBlur(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
            }
        }
        NotificationGutsManager$$ExternalSyntheticLambda7 notificationGutsManager$$ExternalSyntheticLambda7 = new NotificationGutsManager$$ExternalSyntheticLambda7(this, view, i, i2, menuItem, 0);
        view.setPressed(false);
        this.mActivityStarter.executeRunnableDismissingKeyguard(notificationGutsManager$$ExternalSyntheticLambda7, null, false, true, true);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.android.systemui.statusbar.notification.row.NotificationGutsManager$1, java.lang.Runnable] */
    public boolean openGutsInternal(View view, final int i, final int i2, final NotificationMenuRowPlugin.MenuItem menuItem) {
        boolean z;
        NotificationGuts.GutsContent gutsContent;
        if (!(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        if (view.getWindowToken() == null) {
            Log.e("NotificationGutsManager", "Trying to show notification guts, but not attached to window");
            return false;
        }
        final ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        if (expandableNotificationRow.mLongPressListener == null) {
            z = false;
        } else if (expandableNotificationRow.areGutsExposed()) {
            NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
            z = !((notificationGuts == null || (gutsContent = notificationGuts.mGutsContent) == null || !gutsContent.isLeavebehind()) ? false : true);
        } else {
            z = true;
        }
        if (z) {
            view.performHapticFeedback(0);
        }
        if (expandableNotificationRow.areGutsExposed()) {
            closeAndSaveGuts(false, false, true, true);
            return false;
        }
        if (this.mNotificationGutsExposed != null) {
            Log.d("NotificationGutsManager", " previous opened guts close!");
            closeAndSaveGuts(false, false, true, true);
        }
        if (expandableNotificationRow.mGuts == null) {
            expandableNotificationRow.mGutsStub.inflate();
        }
        final NotificationGuts notificationGuts2 = expandableNotificationRow.mGuts;
        this.mNotificationGutsExposed = notificationGuts2;
        if (!bindGuts(expandableNotificationRow, menuItem) || notificationGuts2 == 0) {
            return false;
        }
        notificationGuts2.setVisibility(4);
        ?? r0 = new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationGutsManager.1
            /* JADX WARN: Type inference failed for: r7v0, types: [com.android.systemui.statusbar.notification.row.NotificationGutsManager$1$$ExternalSyntheticLambda0] */
            @Override // java.lang.Runnable
            public final void run() {
                if (expandableNotificationRow.getWindowToken() == null) {
                    Log.e("NotificationGutsManager", "Trying to show notification guts in post(), but not attached to window");
                    return;
                }
                notificationGuts2.setVisibility(0);
                boolean z2 = NotificationGutsManager.this.mStatusBarStateController.getState() == 1 && !NotificationGutsManager.this.mAccessibilityManager.isTouchExplorationEnabled();
                NotificationGuts notificationGuts3 = notificationGuts2;
                int i3 = i;
                int i4 = i2;
                final ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                Objects.requireNonNull(expandableNotificationRow2);
                ?? r7 = new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationGutsManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ExpandableNotificationRow expandableNotificationRow3 = ExpandableNotificationRow.this;
                        expandableNotificationRow3.resetTranslation();
                        expandableNotificationRow3.updateContentAccessibilityImportanceForGuts(false);
                    }
                };
                if (notificationGuts3.isAttachedToWindow()) {
                    float hypot = (float) Math.hypot(Math.max(notificationGuts3.getWidth() - i3, i3), Math.max(notificationGuts3.getHeight() - i4, i4));
                    notificationGuts3.setAlpha(1.0f);
                    Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(notificationGuts3, i3, i4, 0.0f, hypot);
                    createCircularReveal.setDuration(360L);
                    createCircularReveal.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                    createCircularReveal.addListener(new NotificationGuts.AnimateOpenListener((NotificationGutsManager$1$$ExternalSyntheticLambda0) r7));
                    createCircularReveal.start();
                } else {
                    Log.w("NotificationGuts", "Failed to animate guts open");
                }
                notificationGuts3.setExposed(true, z2);
                NotifGutsViewListener notifGutsViewListener = NotificationGutsManager.this.mGutsListener;
                if (notifGutsViewListener != null) {
                    notifGutsViewListener.onGutsOpen(expandableNotificationRow.mEntry, notificationGuts2);
                }
                for (NotificationContentView notificationContentView : expandableNotificationRow.mLayouts) {
                    RemoteInputView remoteInputView = notificationContentView.mHeadsUpRemoteInput;
                    if (remoteInputView != null) {
                        RemoteInputView.RemoteEditText remoteEditText = remoteInputView.mEditText;
                        int i5 = RemoteInputView.RemoteEditText.$r8$clinit;
                        remoteEditText.defocusIfNeeded(false);
                    }
                    RemoteInputView remoteInputView2 = notificationContentView.mExpandedRemoteInput;
                    if (remoteInputView2 != null) {
                        RemoteInputView.RemoteEditText remoteEditText2 = remoteInputView2.mEditText;
                        int i6 = RemoteInputView.RemoteEditText.$r8$clinit;
                        remoteEditText2.defocusIfNeeded(false);
                    }
                }
                ((NotificationStackScrollLayoutController.NotificationListContainerImpl) NotificationGutsManager.this.mListContainer).onHeightChanged(expandableNotificationRow, true);
                NotificationGutsManager notificationGutsManager = NotificationGutsManager.this;
                notificationGutsManager.mGutsMenuItem = menuItem;
                ((HeadsUpManagerPhone) notificationGutsManager.mHeadsUpManager).setGutsShown(expandableNotificationRow.mEntry, true);
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_QPNE_SHOW_GUTS, "type", "long press");
            }
        };
        this.mOpenRunnable = r0;
        notificationGuts2.post(r0);
        return true;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mJavaAdapter.alwaysCollectFlow(this.mWindowRootViewVisibilityInteractor.isLockscreenOrShadeVisible, new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationGutsManager$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationGutsManager notificationGutsManager = NotificationGutsManager.this;
                if (((Boolean) obj).booleanValue()) {
                    notificationGutsManager.getClass();
                } else {
                    notificationGutsManager.closeAndSaveGuts(true, true, true, true);
                }
            }
        });
    }

    public final void startAppNotificationSettingsActivity(String str, int i, NotificationChannel notificationChannel, ExpandableNotificationRow expandableNotificationRow) {
        boolean z = str.equals("com.samsung.android.app.aodservice") && notificationChannel.getId().equals("google_sports_nowbar_ongoing_channel");
        Intent intent = new Intent(z ? "com.samsung.settings.GoogleSportsFavoriteManage" : "android.settings.APP_NOTIFICATION_SETTINGS");
        if (z) {
            intent.putExtra("com.google.android.ambient.intent.extra.AMBIENT_SCHEMA_NAME", "AmbientDataSchema:SportsScore");
            intent.setFlags(268566528);
        }
        intent.putExtra("android.provider.extra.APP_PACKAGE", str);
        intent.putExtra("app_uid", i);
        if (notificationChannel != null) {
            Bundle bundle = new Bundle();
            if (z) {
                bundle.putString(":settings:fragment_args_key", notificationChannel.getId());
            } else {
                bundle.putString(":settings:fragment_args_key", "app_channel_link");
                bundle.putString("highlight_channel_key", notificationChannel.getId());
            }
            intent.putExtra(":settings:show_fragment_args", bundle);
        }
        StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) this.mNotificationActivityStarter;
        ActivityStarter activityStarter = statusBarNotificationActivityStarter.mActivityStarter;
        activityStarter.dismissKeyguardThenExecute(statusBarNotificationActivityStarter.new AnonymousClass2(expandableNotificationRow, activityStarter.shouldAnimateLaunch(true), intent, i), null, false);
    }
}
