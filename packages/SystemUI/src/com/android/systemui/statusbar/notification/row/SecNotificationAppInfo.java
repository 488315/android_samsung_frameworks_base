package com.android.systemui.statusbar.notification.row;

import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.CachingIconView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.row.GutContentInitializer;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.NotificationSAUtil;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.List;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SecNotificationAppInfo extends FrameLayout implements NotificationGuts.GutsContent, GutContentInitializer {
    public int mActualHeight;
    public boolean mAlertAllowed;
    public String mAppName;
    public int mAppUid;
    public AssistantFeedbackController mAssistantFeedbackController;
    public View mContentContainer;
    public CachingIconView mContentIcon;
    public View mContentNameContainer;
    public TextView mDoneButton;
    public NotificationEntry mEntry;
    public NotificationGuts mGutsContainer;
    public CachingIconView mGutsIcon;
    public View mGutsNameContainer;
    public INotificationManager mINotificationManager;
    public boolean mIsDeviceProvisioned;
    public boolean mIsNonblockable;
    public MetricsLogger mMetricsLogger;
    public int mNumTotalChannels;
    public int mNumUniqueChannelsInRow;
    public final SecNotificationAppInfo$$ExternalSyntheticLambda0 mOnCancelSettings;
    public GutContentInitializer.OnSettingsClickListener mOnSettingsClickListener;
    public Icon mOngoingAppIcon;
    public String mOngoingAppName;
    public GutContentInitializer.OnSettingsClickListener mOngoingOnSettingsClickListener;
    public PendingIntent mOngoingPendingIntent;
    public boolean mPackageIsBlocked;
    public String mPackageName;
    public Drawable mPkgIcon;
    public PackageManager mPm;
    public boolean mPressedApply;
    public StatusBarNotification mSbn;
    public NotificationChannel mSingleNotificationChannel;
    boolean mSkipPost;
    public View mToggleButtonContainer;
    public ImageView mToggleSettingsButton;
    public ImageView mTurnOffButton;
    public TextView mTurnOffConFirmButton;
    public UiEventLogger mUiEventLogger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class UpdateAppLevelSettingRunnable implements Runnable {
        public final int mAppUid;
        public final boolean mCurrentAlertAllowed;
        public final INotificationManager mINotificationManager;
        public final boolean mPackageBlocked;
        public final String mPackageName;
        public final boolean mUpdateAlertAllowed;

        public UpdateAppLevelSettingRunnable(INotificationManager iNotificationManager, String str, int i, boolean z, boolean z2, boolean z3) {
            this.mINotificationManager = iNotificationManager;
            this.mPackageName = str;
            this.mAppUid = i;
            this.mCurrentAlertAllowed = z;
            this.mUpdateAlertAllowed = z2;
            this.mPackageBlocked = z3;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                if (this.mUpdateAlertAllowed) {
                    this.mINotificationManager.setNotificationAlertsEnabledForPackage(this.mPackageName, this.mAppUid, !this.mCurrentAlertAllowed);
                }
                if (this.mPackageBlocked) {
                    this.mINotificationManager.setNotificationsEnabledWithImportanceLockForPackage(this.mPackageName, this.mAppUid, false);
                }
            } catch (RemoteException e) {
                Log.e("InfoGuts", "Unable to update notification importance", e);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.row.SecNotificationAppInfo$$ExternalSyntheticLambda0] */
    public SecNotificationAppInfo(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSkipPost = false;
        this.mPackageIsBlocked = false;
        this.mOnCancelSettings = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.SecNotificationAppInfo$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SecNotificationAppInfo secNotificationAppInfo = SecNotificationAppInfo.this;
                secNotificationAppInfo.mPressedApply = false;
                secNotificationAppInfo.updateBottomButtonContainer(true);
                secNotificationAppInfo.mGutsContainer.closeControls(view, false);
            }
        };
    }

    public final void bindInlineControls$1() {
        View.OnClickListener onClickListener;
        ImageView imageView = (ImageView) findViewById(R.id.notification_guts_toggle_settings);
        this.mToggleSettingsButton = imageView;
        imageView.setVisibility(0);
        ImageView imageView2 = this.mToggleSettingsButton;
        View.OnClickListener onClickListener2 = null;
        onClickListener2 = null;
        onClickListener2 = null;
        onClickListener2 = null;
        onClickListener2 = null;
        if (this.mOngoingOnSettingsClickListener != null) {
            final int i = this.mAppUid;
            if (i >= 0 && this.mIsDeviceProvisioned) {
                final NotificationChannel notificationChannel = this.mNumUniqueChannelsInRow <= 1 ? this.mSingleNotificationChannel : null;
                final int i2 = 1;
                onClickListener = new View.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.row.SecNotificationAppInfo$$ExternalSyntheticLambda1
                    public final /* synthetic */ SecNotificationAppInfo f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                SecNotificationAppInfo secNotificationAppInfo = this.f$0;
                                NotificationChannel notificationChannel2 = notificationChannel;
                                int i3 = i;
                                secNotificationAppInfo.mOnSettingsClickListener.onClick();
                                ((NotificationGutsManager) Dependency.sDependency.getDependencyInner(NotificationGutsManager.class)).startAppNotificationSettingsActivity(secNotificationAppInfo.mPackageName, i3, notificationChannel2, secNotificationAppInfo.mEntry.row);
                                break;
                            default:
                                SecNotificationAppInfo secNotificationAppInfo2 = this.f$0;
                                int i4 = i;
                                secNotificationAppInfo2.mOngoingOnSettingsClickListener.onClick();
                                NotificationGutsManager notificationGutsManager = (NotificationGutsManager) Dependency.sDependency.getDependencyInner(NotificationGutsManager.class);
                                ExpandableNotificationRow expandableNotificationRow = secNotificationAppInfo2.mEntry.row;
                                PendingIntent pendingIntent = secNotificationAppInfo2.mOngoingPendingIntent;
                                ((StatusBarNotificationActivityStarter) notificationGutsManager.mNotificationActivityStarter).startNotificationGutsIntent(pendingIntent.getIntent(), i4, expandableNotificationRow);
                                break;
                        }
                    }
                };
                onClickListener2 = onClickListener;
            }
        } else {
            final int i3 = this.mAppUid;
            if (i3 >= 0 && this.mOnSettingsClickListener != null && this.mIsDeviceProvisioned) {
                final NotificationChannel notificationChannel2 = this.mNumUniqueChannelsInRow <= 1 ? this.mSingleNotificationChannel : null;
                final int i4 = 0;
                onClickListener = new View.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.row.SecNotificationAppInfo$$ExternalSyntheticLambda1
                    public final /* synthetic */ SecNotificationAppInfo f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i4) {
                            case 0:
                                SecNotificationAppInfo secNotificationAppInfo = this.f$0;
                                NotificationChannel notificationChannel22 = notificationChannel2;
                                int i32 = i3;
                                secNotificationAppInfo.mOnSettingsClickListener.onClick();
                                ((NotificationGutsManager) Dependency.sDependency.getDependencyInner(NotificationGutsManager.class)).startAppNotificationSettingsActivity(secNotificationAppInfo.mPackageName, i32, notificationChannel22, secNotificationAppInfo.mEntry.row);
                                break;
                            default:
                                SecNotificationAppInfo secNotificationAppInfo2 = this.f$0;
                                int i42 = i3;
                                secNotificationAppInfo2.mOngoingOnSettingsClickListener.onClick();
                                NotificationGutsManager notificationGutsManager = (NotificationGutsManager) Dependency.sDependency.getDependencyInner(NotificationGutsManager.class);
                                ExpandableNotificationRow expandableNotificationRow = secNotificationAppInfo2.mEntry.row;
                                PendingIntent pendingIntent = secNotificationAppInfo2.mOngoingPendingIntent;
                                ((StatusBarNotificationActivityStarter) notificationGutsManager.mNotificationActivityStarter).startNotificationGutsIntent(pendingIntent.getIntent(), i42, expandableNotificationRow);
                                break;
                        }
                    }
                };
                onClickListener2 = onClickListener;
            }
        }
        imageView2.setOnClickListener(onClickListener2);
        ImageView imageView3 = (ImageView) findViewById(R.id.notification_guts_toggle_off);
        this.mTurnOffButton = imageView3;
        imageView3.setEnabled(!this.mIsNonblockable);
        this.mTurnOffButton.setAlpha(this.mIsNonblockable ? 0.4f : 1.0f);
        this.mTurnOffButton.setVisibility(this.mIsNonblockable ? 8 : 0);
        this.mTurnOffButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.SecNotificationAppInfo.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SecNotificationAppInfo.this.mToggleButtonContainer.setVisibility(8);
                SecNotificationAppInfo.this.mContentContainer.setVisibility(0);
                SecNotificationAppInfo.this.updateIconNameVisibility(true);
                final SecNotificationAppInfo secNotificationAppInfo = SecNotificationAppInfo.this;
                secNotificationAppInfo.updateBottomButtonContainer(false);
                TextView textView = (TextView) secNotificationAppInfo.findViewById(R.id.notification_guts_button_done);
                secNotificationAppInfo.mDoneButton = textView;
                textView.semSetButtonShapeEnabled(true);
                secNotificationAppInfo.mDoneButton.setVisibility(0);
                secNotificationAppInfo.mDoneButton.setText(R.string.notification_cancel);
                secNotificationAppInfo.mDoneButton.setOnClickListener(secNotificationAppInfo.mOnCancelSettings);
                TextView textView2 = (TextView) secNotificationAppInfo.findViewById(R.id.notification_guts_button_off_confirm);
                secNotificationAppInfo.mTurnOffConFirmButton = textView2;
                textView2.semSetButtonShapeEnabled(true);
                secNotificationAppInfo.mTurnOffConFirmButton.setVisibility(0);
                secNotificationAppInfo.mTurnOffConFirmButton.setText(R.string.sec_notification_app_info_off_button);
                secNotificationAppInfo.mTurnOffConFirmButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.SecNotificationAppInfo.5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        SecNotificationAppInfo secNotificationAppInfo2 = SecNotificationAppInfo.this;
                        secNotificationAppInfo2.mPackageIsBlocked = true;
                        secNotificationAppInfo2.mPressedApply = true;
                        secNotificationAppInfo2.updateBottomButtonContainer(true);
                        SecNotificationAppInfo secNotificationAppInfo3 = SecNotificationAppInfo.this;
                        secNotificationAppInfo3.mGutsContainer.closeControls(secNotificationAppInfo3.mTurnOffButton, true);
                    }
                });
                secNotificationAppInfo.updateTextColorOnOpenThemeOrColoring();
            }
        });
        updateBottomButtonContainer(true);
    }

    public final void bindNotification(PackageManager packageManager, INotificationManager iNotificationManager, String str, NotificationChannel notificationChannel, NotificationEntry notificationEntry, GutContentInitializer.OnSettingsClickListener onSettingsClickListener, UiEventLogger uiEventLogger, boolean z, boolean z2, AssistantFeedbackController assistantFeedbackController, Icon icon, String str2, GutContentInitializer.OnSettingsClickListener onSettingsClickListener2, PendingIntent pendingIntent) {
        boolean z3;
        this.mINotificationManager = iNotificationManager;
        this.mMetricsLogger = (MetricsLogger) Dependency.sDependency.getDependencyInner(MetricsLogger.class);
        this.mAssistantFeedbackController = assistantFeedbackController;
        this.mPackageName = str;
        this.mEntry = notificationEntry;
        this.mSbn = notificationEntry.mSbn;
        this.mPm = packageManager;
        this.mAppName = str;
        this.mOnSettingsClickListener = onSettingsClickListener;
        this.mSingleNotificationChannel = notificationChannel;
        notificationChannel.getImportance();
        this.mIsNonblockable = z2;
        this.mAppUid = this.mSbn.getUid();
        this.mSbn.getOpPkg();
        this.mIsDeviceProvisioned = z;
        this.mAssistantFeedbackController.getClass();
        this.mUiEventLogger = uiEventLogger;
        ArraySet arraySet = new ArraySet();
        arraySet.add(this.mEntry.mRanking.getChannel());
        ExpandableNotificationRow expandableNotificationRow = this.mEntry.row;
        if (expandableNotificationRow.mIsSummaryWithChildren) {
            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
            List list = notificationChildrenContainer == null ? null : notificationChildrenContainer.mAttachedChildren;
            if (list != null) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) list.get(i);
                    NotificationChannel channel = expandableNotificationRow2.mEntry.mRanking.getChannel();
                    StatusBarNotification statusBarNotification = expandableNotificationRow2.mEntry.mSbn;
                    if (statusBarNotification.getUser().equals(this.mEntry.mSbn.getUser()) && statusBarNotification.getPackageName().equals(this.mEntry.mSbn.getPackageName())) {
                        arraySet.add(channel);
                    }
                }
            }
        }
        this.mNumUniqueChannelsInRow = arraySet.size();
        this.mNumTotalChannels = this.mINotificationManager.getNumNotificationChannelsForPackage(str, this.mAppUid, false);
        int i2 = this.mNumUniqueChannelsInRow;
        if (i2 == 0) {
            throw new IllegalArgumentException("bindNotification requires at least one channel");
        }
        if (i2 == 1 && this.mSingleNotificationChannel.getId().equals("miscellaneous")) {
            int i3 = this.mNumTotalChannels;
        }
        try {
            z3 = this.mINotificationManager.getNotificationAlertsEnabledForPackage(this.mPackageName, this.mSbn.getUid());
        } catch (RemoteException e) {
            Log.e("InfoGuts", "Unable to getNotificationAlertsEnabledForPackage", e);
            z3 = true;
        }
        this.mAlertAllowed = z3;
        this.mToggleButtonContainer = findViewById(R.id.notification_guts_toggle_container);
        this.mContentContainer = findViewById(R.id.notification_guts_content_container);
        ((TextView) findViewById(R.id.notification_guts_content_title)).setText(R.string.sec_notification_app_info_off_description);
        ((TextView) findViewById(R.id.notification_guts_content_text)).setText(R.string.sec_notification_app_info_off_description2);
        this.mGutsIcon = findViewById(R.id.notification_guts_icon);
        this.mGutsNameContainer = findViewById(R.id.notification_guts_name_container);
        this.mContentIcon = findViewById(R.id.notification_guts_content_icon);
        this.mContentNameContainer = findViewById(R.id.notification_guts_content_name_container);
        this.mPackageIsBlocked = false;
        this.mToggleButtonContainer.setVisibility(0);
        this.mContentContainer.setVisibility(8);
        updateIconNameVisibility(false);
        this.mOngoingAppIcon = icon;
        this.mOngoingAppName = str2;
        this.mOngoingOnSettingsClickListener = onSettingsClickListener2;
        this.mOngoingPendingIntent = pendingIntent;
        this.mPkgIcon = null;
        try {
            ApplicationInfo applicationInfo = this.mPm.getApplicationInfo(this.mPackageName, 795136);
            if (applicationInfo != null) {
                this.mAppName = String.valueOf(this.mPm.getApplicationLabel(applicationInfo));
                this.mPkgIcon = this.mPm.semGetApplicationIconForIconTray(applicationInfo, 1);
                checkGoogleSports();
            }
        } catch (PackageManager.NameNotFoundException unused) {
            this.mPkgIcon = this.mPm.getDefaultActivityIcon();
        }
        Icon icon2 = this.mOngoingAppIcon;
        if (icon2 != null) {
            this.mGutsIcon.setImageIcon(icon2);
            this.mContentIcon.setImageIcon(this.mOngoingAppIcon);
        } else {
            this.mGutsIcon.setImageDrawable(this.mPkgIcon);
            this.mContentIcon.setImageDrawable(this.mPkgIcon);
        }
        TextView textView = (TextView) findViewById(R.id.notification_guts_name);
        TextView textView2 = (TextView) findViewById(R.id.notification_guts_content_name);
        String str3 = this.mOngoingAppName;
        if (str3 == null) {
            str3 = this.mAppName;
        }
        textView.setText(str3);
        textView2.setText(str3);
        bindInlineControls$1();
        updateTextColorOnOpenThemeOrColoring();
        NotificationControlsEvent notificationControlsEvent = NotificationControlsEvent.NOTIFICATION_CONTROLS_OPEN;
        StatusBarNotification statusBarNotification2 = this.mSbn;
        if (statusBarNotification2 != null) {
            this.mUiEventLogger.logWithInstanceId(notificationControlsEvent, statusBarNotification2.getUid(), this.mSbn.getPackageName(), this.mSbn.getInstanceId());
        }
        this.mMetricsLogger.write(notificationControlsLogMaker$1());
    }

    public final void checkGoogleSports() {
        if (this.mPackageName.equals("com.samsung.android.app.aodservice") && this.mSingleNotificationChannel.getId().equals("google_sports_nowbar_ongoing_channel")) {
            try {
                ApplicationInfo applicationInfo = this.mPm.getApplicationInfo("com.google.android.googlequicksearchbox", 795136);
                this.mAppName = "Google sports";
                this.mPackageName = "com.samsung.android.app.aodservice";
                this.mPkgIcon = this.mPm.semGetApplicationIconForIconTray(applicationInfo, 1);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final int getActualHeight() {
        return this.mActualHeight;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean handleCloseControls(boolean z, boolean z2) {
        if (!z || !this.mPackageIsBlocked) {
            return false;
        }
        new Handler((Looper) Dependency.sDependency.getDependencyInner(Dependency.BG_LOOPER)).post(new UpdateAppLevelSettingRunnable(this.mINotificationManager, this.mPackageName, this.mAppUid, this.mAlertAllowed, false, this.mPackageIsBlocked));
        if (!this.mPackageIsBlocked) {
            return false;
        }
        NotificationSAUtil.sendTypeLog(SystemUIAnalytics.EID_QPNE_TURN_OFF_APP_NOTIFICATION_FROM_GUTS, this.mEntry);
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.GutContentInitializer
    public final boolean initializeGutContentView(ExpandableNotificationRow expandableNotificationRow) {
        Icon icon;
        String str;
        GutContentInitializer.OnSettingsClickListener onSettingsClickListener;
        PendingIntent pendingIntent;
        String str2;
        NotificationGutsManager$$ExternalSyntheticLambda2 notificationGutsManager$$ExternalSyntheticLambda2;
        StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
        String packageName = statusBarNotification.getPackageName();
        PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(statusBarNotification.getUser().getIdentifier(), getContext());
        NotificationGutsManager notificationGutsManager = (NotificationGutsManager) Dependency.sDependency.getDependencyInner(NotificationGutsManager.class);
        CharSequence charSequence = statusBarNotification.getNotification().extras.getCharSequence("android.ongoingActivityNoti.aodRemoteAppName");
        String charSequence2 = charSequence != null ? charSequence.toString() : null;
        Icon icon2 = (Icon) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.aodRemoteAppIcon", Icon.class);
        PendingIntent pendingIntent2 = (PendingIntent) statusBarNotification.getNotification().extras.getParcelable("android.ongoingActivityNoti.aodRemoteAppPendingIntent", PendingIntent.class);
        GutContentInitializer.OnSettingsClickListener onSettingsClickListener2 = new GutContentInitializer.OnSettingsClickListener(this) { // from class: com.android.systemui.statusbar.notification.row.SecNotificationAppInfo.6
            @Override // com.android.systemui.statusbar.notification.row.GutContentInitializer.OnSettingsClickListener
            public final void onClick() {
            }
        };
        if (charSequence2 == null || charSequence2.isEmpty() || icon2 == null || pendingIntent2 == null) {
            icon = null;
            str = null;
            onSettingsClickListener = null;
            pendingIntent = null;
        } else {
            Log.i("InfoGuts", "AOD guts information is used. ongoingAppName:".concat(charSequence2));
            pendingIntent = pendingIntent2;
            str = charSequence2;
            icon = icon2;
            onSettingsClickListener = onSettingsClickListener2;
        }
        try {
            INotificationManager iNotificationManager = (INotificationManager) Dependency.sDependency.getDependencyInner(INotificationManager.class);
            NotificationChannel channel = expandableNotificationRow.mEntry.mRanking.getChannel();
            NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
            notificationGutsManager.getClass();
            NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
            StatusBarNotification statusBarNotification2 = expandableNotificationRow.mEntry.mSbn;
            try {
                try {
                    if (statusBarNotification2.getUser().equals(UserHandle.ALL) && ((NotificationLockscreenUserManagerImpl) notificationGutsManager.mLockscreenUserManager).mCurrentUserId != 0) {
                        notificationGutsManager$$ExternalSyntheticLambda2 = null;
                        UiEventLogger uiEventLogger = (UiEventLogger) Dependency.sDependency.getDependencyInner(UiEventLogger.class);
                        boolean z = ((DeviceProvisionedControllerImpl) ((DeviceProvisionedController) Dependency.sDependency.getDependencyInner(DeviceProvisionedController.class))).deviceProvisioned.get();
                        boolean isNonPackageBlockable = expandableNotificationRow.getIsNonPackageBlockable();
                        ((HighPriorityProvider) Dependency.sDependency.getDependencyInner(HighPriorityProvider.class)).isHighPriority(expandableNotificationRow.mEntry, true);
                        AssistantFeedbackController assistantFeedbackController = (AssistantFeedbackController) Dependency.sDependency.getDependencyInner(AssistantFeedbackController.class);
                        notificationGutsManager.isFavoriteNotif(packageName);
                        str2 = "InfoGuts";
                        bindNotification(packageManagerForUser, iNotificationManager, packageName, channel, notificationEntry, notificationGutsManager$$ExternalSyntheticLambda2, uiEventLogger, z, isNonPackageBlockable, assistantFeedbackController, icon, str, onSettingsClickListener, pendingIntent);
                        return true;
                    }
                    bindNotification(packageManagerForUser, iNotificationManager, packageName, channel, notificationEntry, notificationGutsManager$$ExternalSyntheticLambda2, uiEventLogger, z, isNonPackageBlockable, assistantFeedbackController, icon, str, onSettingsClickListener, pendingIntent);
                    return true;
                } catch (Exception e) {
                    e = e;
                    Log.e(str2, "error binding guts", e);
                    return false;
                }
                boolean isNonPackageBlockable2 = expandableNotificationRow.getIsNonPackageBlockable();
                ((HighPriorityProvider) Dependency.sDependency.getDependencyInner(HighPriorityProvider.class)).isHighPriority(expandableNotificationRow.mEntry, true);
                AssistantFeedbackController assistantFeedbackController2 = (AssistantFeedbackController) Dependency.sDependency.getDependencyInner(AssistantFeedbackController.class);
                notificationGutsManager.isFavoriteNotif(packageName);
                str2 = "InfoGuts";
            } catch (Exception e2) {
                e = e2;
                str2 = "InfoGuts";
            }
            notificationGutsManager$$ExternalSyntheticLambda2 = new NotificationGutsManager$$ExternalSyntheticLambda2(notificationGutsManager, notificationGuts, statusBarNotification2, expandableNotificationRow);
            UiEventLogger uiEventLogger2 = (UiEventLogger) Dependency.sDependency.getDependencyInner(UiEventLogger.class);
            boolean z2 = ((DeviceProvisionedControllerImpl) ((DeviceProvisionedController) Dependency.sDependency.getDependencyInner(DeviceProvisionedController.class))).deviceProvisioned.get();
        } catch (Exception e3) {
            e = e3;
            str2 = "InfoGuts";
        }
    }

    public boolean isAnimating() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean needsFalsingProtection() {
        return true;
    }

    public final LogMaker notificationControlsLogMaker$1() {
        StatusBarNotification statusBarNotification = this.mSbn;
        return (statusBarNotification == null ? new LogMaker(1621) : statusBarNotification.getLogMaker().setCategory(1621)).setCategory(204).setType(1).setSubtype(0);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final void onFinishedClosing() {
        bindInlineControls$1();
        NotificationControlsEvent notificationControlsEvent = NotificationControlsEvent.NOTIFICATION_CONTROLS_CLOSE;
        StatusBarNotification statusBarNotification = this.mSbn;
        if (statusBarNotification != null) {
            this.mUiEventLogger.logWithInstanceId(notificationControlsEvent, statusBarNotification.getUid(), this.mSbn.getPackageName(), this.mSbn.getInstanceId());
        }
        MetricsLogger metricsLogger = this.mMetricsLogger;
        if (metricsLogger != null) {
            metricsLogger.write(notificationControlsLogMaker$1().setType(2));
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (this.mGutsContainer == null || accessibilityEvent.getEventType() != 32) {
            return;
        }
        if (this.mGutsContainer.mExposed) {
            accessibilityEvent.getText().add(((FrameLayout) this).mContext.getString(R.string.notification_channel_controls_opened_accessibility, this.mAppName));
        } else {
            accessibilityEvent.getText().add(((FrameLayout) this).mContext.getString(R.string.notification_channel_controls_closed_accessibility, this.mAppName));
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mActualHeight = getHeight();
    }

    @Override // android.view.View
    public final boolean post(Runnable runnable) {
        if (!this.mSkipPost) {
            return super.post(runnable);
        }
        runnable.run();
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final void setGutsParent(NotificationGuts notificationGuts) {
        this.mGutsContainer = notificationGuts;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean shouldBeSavedOnClose() {
        return this.mPressedApply;
    }

    public final void updateBottomButtonContainer(boolean z) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.notification_guts_button_container);
        if (linearLayout != null) {
            linearLayout.setVisibility(z ? 8 : 0);
            TextView textView = (TextView) findViewById(R.id.notification_guts_name);
            if (textView != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
                if (z) {
                    layoutParams.setMargins(0, 0, 0, 0);
                } else {
                    layoutParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.notification_guts_info_header_text_margin_top), 0, 0);
                }
                textView.setLayoutParams(layoutParams);
            }
        }
    }

    public final void updateIconNameVisibility(boolean z) {
        this.mGutsIcon.setVisibility(z ? 8 : 0);
        this.mGutsNameContainer.setVisibility(z ? 8 : 0);
        this.mContentIcon.setVisibility(z ? 0 : 8);
        this.mContentNameContainer.setVisibility(z ? 0 : 8);
    }

    public final void updateTextColorOnOpenThemeOrColoring() {
        NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
        int gutsTextColor = notificationColorPicker.getGutsTextColor();
        int gutsTextColor2 = notificationColorPicker.getGutsTextColor();
        int color = ((FrameLayout) this).mContext.getResources().getColor(R.color.notification_guts_turn_off_text_color);
        if (gutsTextColor == 0 || gutsTextColor2 == 0) {
            return;
        }
        if (DeviceState.isOpenTheme(((FrameLayout) this).mContext)) {
            setBackgroundColor(notificationColorPicker.getNotificationBgColor());
        }
        if (((FrameLayout) this).mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
            setBackgroundColor(((FrameLayout) this).mContext.getResources().getColor(R.color.qp_notification_guts_color));
        }
        TextView textView = (TextView) findViewById(R.id.notification_guts_name);
        if (textView != null) {
            textView.setTextColor(gutsTextColor);
        }
        TextView textView2 = (TextView) findViewById(R.id.notification_guts_content_title);
        if (textView2 != null) {
            textView2.setTextColor(gutsTextColor2);
        }
        TextView textView3 = (TextView) findViewById(R.id.notification_guts_content_text);
        if (textView3 != null) {
            textView3.setTextColor(gutsTextColor2);
        }
        TextView textView4 = (TextView) findViewById(R.id.notification_guts_content_name);
        if (textView4 != null) {
            textView4.setTextColor(gutsTextColor2);
        }
        TextView textView5 = this.mDoneButton;
        if (textView5 != null) {
            textView5.setTextColor(gutsTextColor);
        }
        TextView textView6 = this.mTurnOffConFirmButton;
        if (textView6 != null) {
            textView6.setTextColor(color);
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.notification_guts_button_container);
        if (linearLayout != null) {
            Drawable mutate = ((FrameLayout) this).mContext.getDrawable(R.drawable.notification_guts_button_divider).mutate();
            mutate.setTint(gutsTextColor);
            linearLayout.setDividerDrawable(mutate);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean willBeRemoved() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final View getContentView() {
        return this;
    }
}
