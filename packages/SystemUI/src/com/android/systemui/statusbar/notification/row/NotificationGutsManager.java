package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
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
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.settingslib.notification.ConversationIconFactory;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
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
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.promoted.domain.interactor.PackageDemotionInteractor;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationSnooze;
import com.android.systemui.statusbar.notification.row.icon.AppIconProvider;
import com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.wmshell.BubblesManager;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationGutsManager implements NotifGutsViewManager, CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager mAccessibilityManager;
    public final ActivityStarter mActivityStarter;
    public final AppIconProvider mAppIconProvider;
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
    public View mGutsViewOffButton;
    public View mGutsViewSettingsButton;
    public final HeadsUpManager mHeadsUpManager;
    public final HighPriorityProvider mHighPriorityProvider;
    public final NotificationIconStyleProvider mIconStyleProvider;
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
    public AnonymousClass2 mOpenRunnable;
    public final PackageDemotionInteractor mPackageDemotionInteractor;
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
    public final AnonymousClass3 mOnFavoriteNotifUpdateListener = new Object(this) { // from class: com.android.systemui.statusbar.notification.row.NotificationGutsManager.3
    };
    private SettingsHelper.OnChangedCallback mSettingsChangedListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationGutsManager.4
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            NotificationGutsManager notificationGutsManager = NotificationGutsManager.this;
            notificationGutsManager.mFavoriteSectionSettingsLists = Settings.Secure.getStringForUser(notificationGutsManager.mContext.getContentResolver(), SettingsHelper.INDEX_SECURE_NOTIFICATION_PANEL_SHOW_FAVORITE_APP_NOTIFICATIONS, ((NotificationLockscreenUserManagerImpl) notificationGutsManager.mLockscreenUserManager).mCurrentUserId);
            notificationGutsManager.mInvalidateListener.onUpdateNotifStack();
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.row.NotificationGutsManager$1, reason: invalid class name */
    public class AnonymousClass1 implements View.OnClickListener {
        public final /* synthetic */ StatusBarNotification val$sbn;

        public AnonymousClass1(StatusBarNotification statusBarNotification) {
            this.val$sbn = statusBarNotification;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            try {
                NotificationGutsManager.this.mNotificationManager.setCanBePromoted(this.val$sbn.getPackageName(), this.val$sbn.getUid(), false, true);
            } catch (RemoteException e) {
                Log.e("NotificationGutsManager", "Couldn't revoke live update permission", e);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.systemui.statusbar.notification.row.NotificationGutsManager$3] */
    public NotificationGutsManager(Context context, Handler handler, Handler handler2, JavaAdapter javaAdapter, AccessibilityManager accessibilityManager, HighPriorityProvider highPriorityProvider, INotificationManager iNotificationManager, AppIconProvider appIconProvider, NotificationIconStyleProvider notificationIconStyleProvider, UserManager userManager, PeopleSpaceWidgetManager peopleSpaceWidgetManager, LauncherApps launcherApps, ShortcutManager shortcutManager, ChannelEditorDialogController channelEditorDialogController, PackageDemotionInteractor packageDemotionInteractor, UserContextProvider userContextProvider, AssistantFeedbackController assistantFeedbackController, Optional<BubblesManager> optional, UiEventLogger uiEventLogger, OnUserInteractionCallback onUserInteractionCallback, ShadeController shadeController, WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor, NotificationLockscreenUserManager notificationLockscreenUserManager, StatusBarStateController statusBarStateController, IStatusBarService iStatusBarService, DeviceProvisionedController deviceProvisionedController, MetricsLogger metricsLogger, HeadsUpManager headsUpManager, ActivityStarter activityStarter, SecQpBlurController secQpBlurController) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mBgHandler = handler2;
        this.mJavaAdapter = javaAdapter;
        this.mAccessibilityManager = accessibilityManager;
        this.mHighPriorityProvider = highPriorityProvider;
        this.mNotificationManager = iNotificationManager;
        this.mAppIconProvider = appIconProvider;
        this.mIconStyleProvider = notificationIconStyleProvider;
        this.mUserManager = userManager;
        this.mPeopleSpaceWidgetManager = peopleSpaceWidgetManager;
        this.mLauncherApps = launcherApps;
        this.mShortcutManager = shortcutManager;
        this.mContextTracker = userContextProvider;
        this.mChannelEditorDialogController = channelEditorDialogController;
        this.mPackageDemotionInteractor = packageDemotionInteractor;
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

    public boolean bindGuts(ExpandableNotificationRow expandableNotificationRow, NotificationMenuRowPlugin.MenuItem menuItem) {
        int i = NotificationBundleUi.$r8$clinit;
        StatusBarNotification statusBarNotification = expandableNotificationRow.getEntryLegacy().mSbn;
        NotificationListenerService.Ranking ranking = expandableNotificationRow.getEntryLegacy().mRanking;
        if (statusBarNotification != null && ranking != null) {
            if (expandableNotificationRow.mGuts != null && (menuItem.getGutsView() instanceof NotificationGuts.GutsContent)) {
                expandableNotificationRow.mGuts.setGutsContent((NotificationGuts.GutsContent) menuItem.getGutsView());
            }
            expandableNotificationRow.setTag(statusBarNotification.getPackageName());
            expandableNotificationRow.mGuts.mClosedListener = new NotificationGutsManager$$ExternalSyntheticLambda0(this, expandableNotificationRow);
            View gutsView = menuItem.getGutsView();
            this.mGutsViewOffButton = gutsView.findViewById(R.id.notification_guts_toggle_off);
            this.mGutsViewSettingsButton = gutsView.findViewById(R.id.notification_guts_toggle_settings);
            if (!(menuItem instanceof GutContentInitializer) || !((GutContentInitializer) menuItem).initializeGutContentView(expandableNotificationRow)) {
                try {
                    if (gutsView instanceof NotificationSnooze) {
                        initializeSnoozeView(expandableNotificationRow, statusBarNotification, ranking, (NotificationSnooze) gutsView);
                        return true;
                    }
                    if (gutsView instanceof NotificationInfo) {
                        initializeNotificationInfo(expandableNotificationRow, statusBarNotification, ranking, (NotificationInfo) gutsView);
                        return true;
                    }
                    if (gutsView instanceof NotificationConversationInfo) {
                        initializeConversationNotificationInfo(expandableNotificationRow, statusBarNotification, ranking, (NotificationConversationInfo) gutsView);
                        return true;
                    }
                    if (gutsView instanceof PartialConversationInfo) {
                        initializePartialConversationNotificationInfo(expandableNotificationRow, statusBarNotification, ranking, (PartialConversationInfo) gutsView);
                        return true;
                    }
                    if (gutsView instanceof FeedbackInfo) {
                        FeedbackInfo feedbackInfo = (FeedbackInfo) gutsView;
                        AssistantFeedbackController assistantFeedbackController = this.mAssistantFeedbackController;
                        if (((FeedbackIcon) assistantFeedbackController.mIcons.get(assistantFeedbackController.getFeedbackStatus(ranking))) != null) {
                            UserHandle user = statusBarNotification.getUser();
                            feedbackInfo.bindGuts(CentralSurfaces.getPackageManagerForUser(user.getIdentifier(), this.mContext), statusBarNotification, ranking, expandableNotificationRow, this.mAssistantFeedbackController, this.mStatusBarService, this);
                            return true;
                        }
                    } else if (gutsView instanceof PromotedPermissionGutsContent) {
                        PromotedPermissionGutsContent promotedPermissionGutsContent = (PromotedPermissionGutsContent) gutsView;
                        promotedPermissionGutsContent.setStatusBarNotification(statusBarNotification);
                        promotedPermissionGutsContent.mDemoteAction = new AnonymousClass1(statusBarNotification);
                    }
                } catch (Exception e) {
                    Log.e("NotificationGutsManager", "error binding guts", e);
                }
            }
            return true;
        }
        return false;
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
        NotificationStackScrollLayoutController.this.mSwipeHelper.resetExposedMenuView$1(false, true);
    }

    public final void closeAndUndoGuts() {
        NotificationGuts notificationGuts = this.mNotificationGutsExposed;
        if (notificationGuts != null) {
            notificationGuts.removeCallbacks(this.mOpenRunnable);
            this.mNotificationGutsExposed.closeControls(-1, -1, false, false);
        }
    }

    public void initializeConversationNotificationInfo(ExpandableNotificationRow expandableNotificationRow, StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking, NotificationConversationInfo notificationConversationInfo) throws Exception {
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        String packageName = statusBarNotification.getPackageName();
        UserHandle user = statusBarNotification.getUser();
        PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(user.getIdentifier(), this.mContext);
        NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1 = (!user.equals(UserHandle.ALL) || ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).mCurrentUserId == 0) ? new NotificationGutsManager$$ExternalSyntheticLambda1(this, notificationGuts, statusBarNotification, packageName, expandableNotificationRow, 0) : null;
        Context context = this.mContext;
        ConversationIconFactory conversationIconFactory = new ConversationIconFactory(context, this.mLauncherApps, packageManagerForUser, IconDrawableFactory.newInstance(context, false), this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_guts_conversation_icon_size));
        ViewCompat$$ExternalSyntheticLambda0 viewCompat$$ExternalSyntheticLambda0 = new ViewCompat$$ExternalSyntheticLambda0();
        UserManager userManager = this.mUserManager;
        INotificationManager iNotificationManager = this.mNotificationManager;
        int i = NotificationBundleUi.$r8$clinit;
        NotificationEntry entryLegacy = expandableNotificationRow.getEntryLegacy();
        ((UserTrackerImpl) this.mContextTracker).getUserContext();
        notificationConversationInfo.bindNotification(packageManagerForUser, userManager, this.mPeopleSpaceWidgetManager, iNotificationManager, this.mOnUserInteractionCallback, packageName, entryLegacy, ranking, statusBarNotification, notificationGutsManager$$ExternalSyntheticLambda1, viewCompat$$ExternalSyntheticLambda0, conversationIconFactory, ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).deviceProvisioned.get(), this.mMainHandler, this.mBgHandler, this.mBubblesManagerOptional, this.mShadeController, expandableNotificationRow.canViewBeDismissed$1(), new ExpandableNotificationRow$$ExternalSyntheticLambda6(expandableNotificationRow));
    }

    public void initializeNotificationInfo(ExpandableNotificationRow expandableNotificationRow, StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking, NotificationInfo notificationInfo) throws Exception {
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        String packageName = statusBarNotification.getPackageName();
        UserHandle user = statusBarNotification.getUser();
        PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(user.getIdentifier(), this.mContext);
        NotificationGutsManager$$ExternalSyntheticLambda2 notificationGutsManager$$ExternalSyntheticLambda2 = new NotificationGutsManager$$ExternalSyntheticLambda2(this, notificationGuts, statusBarNotification, expandableNotificationRow);
        NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1 = (!user.equals(UserHandle.ALL) || ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).mCurrentUserId == 0) ? new NotificationGutsManager$$ExternalSyntheticLambda1(this, notificationGuts, statusBarNotification, packageName, expandableNotificationRow, 1) : null;
        ViewCompat$$ExternalSyntheticLambda0 viewCompat$$ExternalSyntheticLambda0 = new ViewCompat$$ExternalSyntheticLambda0();
        INotificationManager iNotificationManager = this.mNotificationManager;
        int i = NotificationBundleUi.$r8$clinit;
        notificationInfo.bindNotification(packageManagerForUser, iNotificationManager, this.mAppIconProvider, this.mIconStyleProvider, this.mOnUserInteractionCallback, this.mChannelEditorDialogController, this.mPackageDemotionInteractor, packageName, ranking, statusBarNotification, expandableNotificationRow.getEntryLegacy(), notificationGutsManager$$ExternalSyntheticLambda1, notificationGutsManager$$ExternalSyntheticLambda2, viewCompat$$ExternalSyntheticLambda0, this.mUiEventLogger, ((DeviceProvisionedControllerImpl) this.mDeviceProvisionedController).deviceProvisioned.get(), expandableNotificationRow.getIsNonblockable(), expandableNotificationRow.canViewBeDismissed$1(), this.mHighPriorityProvider.isHighPriority(expandableNotificationRow.getEntryLegacy(), true), this.mAssistantFeedbackController, this.mMetricsLogger, new ExpandableNotificationRow$$ExternalSyntheticLambda6(expandableNotificationRow));
    }

    public void initializePartialConversationNotificationInfo(ExpandableNotificationRow expandableNotificationRow, StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking, final PartialConversationInfo partialConversationInfo) throws Exception {
        NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1;
        NotificationGutsManager notificationGutsManager;
        ExpandableNotificationRow expandableNotificationRow2;
        StatusBarNotification statusBarNotification2;
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        String packageName = statusBarNotification.getPackageName();
        UserHandle user = statusBarNotification.getUser();
        PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(user.getIdentifier(), this.mContext);
        View.OnClickListener onClickListener = null;
        if (!user.equals(UserHandle.ALL) || ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).mCurrentUserId == 0) {
            notificationGutsManager = this;
            expandableNotificationRow2 = expandableNotificationRow;
            statusBarNotification2 = statusBarNotification;
            notificationGutsManager$$ExternalSyntheticLambda1 = new NotificationGutsManager$$ExternalSyntheticLambda1(notificationGutsManager, notificationGuts, statusBarNotification2, packageName, expandableNotificationRow2, 2);
        } else {
            notificationGutsManager = this;
            expandableNotificationRow2 = expandableNotificationRow;
            statusBarNotification2 = statusBarNotification;
            notificationGutsManager$$ExternalSyntheticLambda1 = null;
        }
        boolean z = ((DeviceProvisionedControllerImpl) notificationGutsManager.mDeviceProvisionedController).deviceProvisioned.get();
        int i = NotificationBundleUi.$r8$clinit;
        boolean isNonblockable = expandableNotificationRow2.getIsNonblockable();
        partialConversationInfo.mPackageName = packageName;
        partialConversationInfo.mSbn = statusBarNotification2;
        partialConversationInfo.mPm = packageManagerForUser;
        partialConversationInfo.mAppName = packageName;
        partialConversationInfo.mOnSettingsClickListener = notificationGutsManager$$ExternalSyntheticLambda1;
        partialConversationInfo.mNotificationChannel = ranking.getChannel();
        partialConversationInfo.mAppUid = partialConversationInfo.mSbn.getUid();
        partialConversationInfo.mDelegatePkg = partialConversationInfo.mSbn.getOpPkg();
        partialConversationInfo.mIsDeviceProvisioned = z;
        partialConversationInfo.mIsNonBlockable = isNonblockable;
        partialConversationInfo.mChannelEditorDialogController = notificationGutsManager.mChannelEditorDialogController;
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
        int i2 = 8;
        if (TextUtils.equals(partialConversationInfo.mPackageName, partialConversationInfo.mDelegatePkg)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
        final int i3 = partialConversationInfo.mAppUid;
        if (i3 >= 0 && partialConversationInfo.mOnSettingsClickListener != null && partialConversationInfo.mIsDeviceProvisioned) {
            onClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.PartialConversationInfo$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PartialConversationInfo partialConversationInfo2 = PartialConversationInfo.this;
                    partialConversationInfo2.mOnSettingsClickListener.onClick(partialConversationInfo2.mNotificationChannel, i3);
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
            i2 = 0;
        }
        findViewById2.setVisibility(i2);
        View findViewById3 = partialConversationInfo.findViewById(R.id.done);
        findViewById3.setOnClickListener(partialConversationInfo.mOnDone);
        findViewById3.setAccessibilityDelegate(partialConversationInfo.mGutsContainer.getAccessibilityDelegate());
    }

    public final void initializeSnoozeView(ExpandableNotificationRow expandableNotificationRow, StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking, NotificationSnooze notificationSnooze) {
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        notificationSnooze.mSnoozeListener = NotificationStackScrollLayoutController.this.mSwipeHelper;
        notificationSnooze.mSbn = statusBarNotification;
        List snoozeCriteria = ranking.getSnoozeCriteria();
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
        notificationGuts.mHeightListener = new NotificationGutsManager$$ExternalSyntheticLambda0(this, expandableNotificationRow);
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
            ((StatusBarStateControllerImpl) statusBarStateController).setLeaveOpenOnKeyguardHide(true);
            if (statusBarStateController.getState() == 1) {
                this.mIsGoingGutOpenedFromLock = true;
            }
        }
        KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_GUTS);
        NotificationGutsManager$$ExternalSyntheticLambda7 notificationGutsManager$$ExternalSyntheticLambda7 = new NotificationGutsManager$$ExternalSyntheticLambda7(this, view, i, i2, menuItem, 0);
        NotificationGutsManager$$ExternalSyntheticLambda8 notificationGutsManager$$ExternalSyntheticLambda8 = new NotificationGutsManager$$ExternalSyntheticLambda8(this, 0);
        view.setPressed(false);
        if (!this.mIsGoingGutOpenedFromLock) {
            notificationGutsManager$$ExternalSyntheticLambda8 = null;
        }
        this.mActivityStarter.executeRunnableDismissingKeyguard(notificationGutsManager$$ExternalSyntheticLambda7, notificationGutsManager$$ExternalSyntheticLambda8, false, true, true);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.statusbar.notification.row.NotificationGutsManager$2, java.lang.Runnable] */
    public boolean openGutsInternal(View view, final int i, final int i2, final NotificationMenuRowPlugin.MenuItem menuItem) {
        boolean z;
        if (view instanceof ExpandableNotificationRow) {
            if (view.getWindowToken() == null) {
                Log.e("NotificationGutsManager", "Trying to show notification guts, but not attached to window");
                return false;
            }
            final ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            if (expandableNotificationRow.mLongPressListener == null) {
                z = false;
            } else if (expandableNotificationRow.areGutsExposed()) {
                NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
                z = !(notificationGuts != null && notificationGuts.isLeavebehind());
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
            if (bindGuts(expandableNotificationRow, menuItem) && notificationGuts2 != 0) {
                notificationGuts2.setVisibility(4);
                ?? r3 = new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationGutsManager.2
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
                        ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                        Objects.requireNonNull(expandableNotificationRow2);
                        NotificationGutsManager$$ExternalSyntheticLambda8 notificationGutsManager$$ExternalSyntheticLambda8 = new NotificationGutsManager$$ExternalSyntheticLambda8(expandableNotificationRow2, 2);
                        if (notificationGuts3.isAttachedToWindow()) {
                            View view2 = (View) notificationGuts3.getParent();
                            if (view2 instanceof ExpandableNotificationRow) {
                                ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) view2;
                                View contentView = expandableNotificationRow3.mIsSummaryWithChildren ? expandableNotificationRow3.getContentView() : expandableNotificationRow3.mEntry.isOngoingActivity() ? expandableNotificationRow3.getContentView().findViewById(R.id.ongoing_activity_expand_custom_content) : expandableNotificationRow3.getContentView().findViewById(16909884);
                                if (contentView != null) {
                                    contentView.setAlpha(0.0f);
                                }
                            }
                            float hypot = (float) Math.hypot(Math.max(notificationGuts3.getWidth() - i3, i3), Math.max(notificationGuts3.getHeight() - i4, i4));
                            notificationGuts3.setAlpha(1.0f);
                            Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(notificationGuts3, i3, i4, 0.0f, hypot);
                            createCircularReveal.setDuration(360L);
                            createCircularReveal.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                            createCircularReveal.addListener(new NotificationGuts.AnimateOpenListener(notificationGutsManager$$ExternalSyntheticLambda8));
                            createCircularReveal.start();
                        } else {
                            Log.w("NotificationGuts", "Failed to animate guts open");
                        }
                        notificationGuts3.setExposed(true, z2);
                        NotifGutsViewListener notifGutsViewListener = NotificationGutsManager.this.mGutsListener;
                        if (notifGutsViewListener != null) {
                            int i5 = NotificationBundleUi.$r8$clinit;
                            notifGutsViewListener.onGutsOpen(expandableNotificationRow.getEntryLegacy(), notificationGuts2);
                        }
                        for (NotificationContentView notificationContentView : expandableNotificationRow.mLayouts) {
                            notificationContentView.getClass();
                            RemoteInputView remoteInputView = notificationContentView.mExpandedRemoteInput;
                            if (remoteInputView != null) {
                                RemoteInputView.RemoteEditText remoteEditText = remoteInputView.mEditText;
                                int i6 = RemoteInputView.RemoteEditText.$r8$clinit;
                                remoteEditText.defocusIfNeeded(false);
                            }
                        }
                        ((NotificationStackScrollLayoutController.NotificationListContainerImpl) NotificationGutsManager.this.mListContainer).onHeightChanged(expandableNotificationRow, true);
                        NotificationGutsManager notificationGutsManager = NotificationGutsManager.this;
                        notificationGutsManager.mGutsMenuItem = menuItem;
                        int i7 = NotificationBundleUi.$r8$clinit;
                        ((HeadsUpManagerImpl) notificationGutsManager.mHeadsUpManager).setGutsShown(expandableNotificationRow.getEntryLegacy(), true);
                        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_QPNE_SHOW_GUTS, "type", "long press");
                    }
                };
                this.mOpenRunnable = r3;
                notificationGuts2.post(r3);
                return true;
            }
        }
        return false;
    }

    public void setExposedGuts(NotificationGuts notificationGuts) {
        this.mNotificationGutsExposed = notificationGuts;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mJavaAdapter.alwaysCollectFlow(this.mWindowRootViewVisibilityInteractor.isLockscreenOrShadeVisible, new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationGutsManager$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationGutsManager notificationGutsManager = NotificationGutsManager.this;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                int i = NotificationGutsManager.$r8$clinit;
                if (booleanValue) {
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
        ((StatusBarNotificationActivityStarter) this.mNotificationActivityStarter).startNotificationGutsIntent(intent, i, expandableNotificationRow);
    }
}
