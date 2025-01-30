package com.android.systemui.statusbar.notification.row;

import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.Set;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SecNotificationAppInfo extends LinearLayout implements NotificationGuts.GutsContent, GutContentInitializer {
    public int mActualHeight;
    public boolean mAlertAllowed;
    public String mAppName;
    public int mAppUid;
    public AssistantFeedbackController mAssistantFeedbackController;
    public TextView mDoneButton;
    public NotificationEntry mEntry;
    public NotificationGuts mGutsContainer;
    public INotificationManager mINotificationManager;
    public boolean mIsDeviceProvisioned;
    public boolean mIsNonblockable;
    public MetricsLogger mMetricsLogger;
    public int mNumUniqueChannelsInRow;
    public final SecNotificationAppInfo$$ExternalSyntheticLambda0 mOnCancelSettings;
    public NotificationGutsManager$$ExternalSyntheticLambda1 mOnSettingsClickListener;
    public View mPackageDisableContent;
    public View mPackageEnableContent;
    public boolean mPackageIsBlocked;
    public String mPackageName;
    public Drawable mPkgIcon;
    public PackageManager mPm;
    public boolean mPressedApply;
    public StatusBarNotification mSbn;
    public TextView mSettingToggle;
    public NotificationChannel mSingleNotificationChannel;
    boolean mSkipPost;
    public TextView mToggleSettingsButton;
    public TextView mTurnOffButton;
    public TextView mTurnOffConFirmButton;
    public UiEventLogger mUiEventLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UpdateAppLevelSettingRunnable implements Runnable {
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
                secNotificationAppInfo.mGutsContainer.closeControls(view, false);
            }
        };
    }

    public final void bindBottomButtons(boolean z) {
        TextView textView = (TextView) findViewById(R.id.done);
        this.mDoneButton = textView;
        textView.semSetButtonShapeEnabled(true);
        this.mDoneButton.setVisibility(0);
        this.mDoneButton.setText(z ? R.string.notification_cancel : R.string.notification_info_done_button);
        this.mDoneButton.setOnClickListener(this.mOnCancelSettings);
        TextView textView2 = (TextView) findViewById(R.id.off_confirm);
        this.mTurnOffConFirmButton = textView2;
        textView2.semSetButtonShapeEnabled(true);
        this.mTurnOffConFirmButton.setVisibility(z ? 0 : 8);
        this.mTurnOffConFirmButton.setText(R.string.sec_notification_app_info_off_button);
        this.mTurnOffConFirmButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.SecNotificationAppInfo.4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SecNotificationAppInfo secNotificationAppInfo = SecNotificationAppInfo.this;
                secNotificationAppInfo.mPackageIsBlocked = true;
                secNotificationAppInfo.mPressedApply = true;
                secNotificationAppInfo.mGutsContainer.closeControls(secNotificationAppInfo.mTurnOffButton, true);
            }
        });
    }

    public final void bindInlineControls() {
        TextView textView = (TextView) findViewById(R.id.toggle_settings);
        this.mToggleSettingsButton = textView;
        textView.setText(R.string.notification_info_setting_button);
        this.mToggleSettingsButton.setVisibility(0);
        TextView textView2 = this.mToggleSettingsButton;
        final int i = this.mAppUid;
        View.OnClickListener onClickListener = null;
        onClickListener = null;
        onClickListener = null;
        if (i >= 0 && this.mOnSettingsClickListener != null && this.mIsDeviceProvisioned) {
            final NotificationChannel notificationChannel = this.mNumUniqueChannelsInRow <= 1 ? this.mSingleNotificationChannel : null;
            onClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.SecNotificationAppInfo$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SecNotificationAppInfo secNotificationAppInfo = SecNotificationAppInfo.this;
                    NotificationChannel notificationChannel2 = notificationChannel;
                    int i2 = i;
                    NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1 = secNotificationAppInfo.mOnSettingsClickListener;
                    NotificationGuts notificationGuts = (NotificationGuts) notificationGutsManager$$ExternalSyntheticLambda1.f$2;
                    StatusBarNotification statusBarNotification = (StatusBarNotification) notificationGutsManager$$ExternalSyntheticLambda1.f$1;
                    NotificationGutsManager notificationGutsManager = notificationGutsManager$$ExternalSyntheticLambda1.f$0;
                    notificationGutsManager.mMetricsLogger.action(205);
                    notificationGuts.resetFalsingCheck();
                    StatusBarNotificationPresenter.C31573 c31573 = notificationGutsManager.mOnSettingsClickListener;
                    String key = statusBarNotification.getKey();
                    c31573.getClass();
                    try {
                        StatusBarNotificationPresenter.this.mBarService.onNotificationSettingsViewed(key);
                    } catch (RemoteException unused) {
                    }
                    SystemUIAnalytics.sendEventCDLog("QPN001", "QPNE0015", "app", statusBarNotification.getPackageName());
                    ((NotificationGutsManager) Dependency.get(NotificationGutsManager.class)).startAppNotificationSettingsActivity(secNotificationAppInfo.mPackageName, i2, notificationChannel2, secNotificationAppInfo.mEntry.row);
                }
            };
        }
        textView2.setOnClickListener(onClickListener);
        TextView textView3 = (TextView) findViewById(R.id.toggle_off);
        this.mTurnOffButton = textView3;
        textView3.setText(R.string.sec_notification_app_info_off);
        this.mTurnOffButton.setEnabled(!this.mIsNonblockable);
        this.mTurnOffButton.setAlpha(this.mIsNonblockable ? 0.4f : 1.0f);
        this.mTurnOffButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.SecNotificationAppInfo.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SecNotificationAppInfo.this.mPackageEnableContent.setVisibility(8);
                SecNotificationAppInfo.this.mPackageDisableContent.setVisibility(0);
                SecNotificationAppInfo.this.bindBottomButtons(true);
            }
        });
        updateInlineButtonLayout();
    }

    public final void bindNotification(PackageManager packageManager, INotificationManager iNotificationManager, String str, NotificationChannel notificationChannel, Set set, NotificationEntry notificationEntry, NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1, UiEventLogger uiEventLogger, boolean z, boolean z2, AssistantFeedbackController assistantFeedbackController) {
        this.mINotificationManager = iNotificationManager;
        this.mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
        this.mAssistantFeedbackController = assistantFeedbackController;
        this.mPackageName = str;
        this.mNumUniqueChannelsInRow = ((ArraySet) set).size();
        this.mEntry = notificationEntry;
        this.mSbn = notificationEntry.mSbn;
        this.mPm = packageManager;
        this.mAppName = this.mPackageName;
        this.mOnSettingsClickListener = notificationGutsManager$$ExternalSyntheticLambda1;
        this.mSingleNotificationChannel = notificationChannel;
        notificationChannel.getImportance();
        this.mIsNonblockable = z2;
        this.mAppUid = this.mSbn.getUid();
        this.mSbn.getOpPkg();
        this.mIsDeviceProvisioned = z;
        this.mAssistantFeedbackController.getClass();
        this.mUiEventLogger = uiEventLogger;
        this.mINotificationManager.getNumNotificationChannelsForPackage(str, this.mAppUid, false);
        int i = this.mNumUniqueChannelsInRow;
        if (i == 0) {
            throw new IllegalArgumentException("bindNotification requires at least one channel");
        }
        boolean z3 = true;
        if (i == 1) {
            this.mSingleNotificationChannel.getId().equals("miscellaneous");
        }
        try {
            z3 = this.mINotificationManager.getNotificationAlertsEnabledForPackage(this.mPackageName, this.mSbn.getUid());
        } catch (RemoteException e) {
            Log.e("InfoGuts", "Unable to getNotificationAlertsEnabledForPackage", e);
        }
        this.mAlertAllowed = z3;
        this.mPackageEnableContent = findViewById(R.id.package_enable_content);
        this.mPackageDisableContent = findViewById(R.id.package_block_content);
        ((TextView) findViewById(R.id.package_block_content_text)).setText(R.string.sec_notification_app_info_off_description);
        ((TextView) findViewById(R.id.package_block_content_text2)).setText(R.string.sec_notification_app_info_off_description2);
        this.mPackageIsBlocked = false;
        this.mPackageEnableContent.setVisibility(0);
        this.mPackageDisableContent.setVisibility(8);
        this.mPkgIcon = null;
        try {
            ApplicationInfo applicationInfo = this.mPm.getApplicationInfo(this.mPackageName, 795136);
            if (applicationInfo != null) {
                this.mAppName = String.valueOf(this.mPm.getApplicationLabel(applicationInfo));
                this.mPkgIcon = this.mPm.getApplicationIcon(applicationInfo);
            }
        } catch (PackageManager.NameNotFoundException unused) {
            this.mPkgIcon = this.mPm.getDefaultActivityIcon();
        }
        ((ImageView) findViewById(R.id.pkg_icon)).setImageDrawable(this.mPkgIcon);
        ((TextView) findViewById(R.id.pkg_name)).setText(this.mAppName);
        bindInlineControls();
        bindBottomButtons(false);
        this.mSettingToggle = (TextView) findViewById(R.id.toggle_settings);
        NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.get(NotificationColorPicker.class);
        int gutsTextColor = notificationColorPicker.getGutsTextColor();
        int gutsTextColor2 = notificationColorPicker.getGutsTextColor();
        if (gutsTextColor != 0 && gutsTextColor2 != 0) {
            if (DeviceState.isOpenTheme(((LinearLayout) this).mContext)) {
                setBackgroundColor(notificationColorPicker.getNotificationBgColor$1());
            }
            if (((LinearLayout) this).mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                setBackgroundColor(((LinearLayout) this).mContext.getResources().getColor(R.color.qp_notification_guts_color));
            }
            TextView textView = (TextView) findViewById(R.id.pkg_name);
            if (textView != null) {
                textView.setTextColor(gutsTextColor);
            }
            TextView textView2 = this.mTurnOffButton;
            if (textView2 != null) {
                textView2.setTextColor(gutsTextColor);
            }
            TextView textView3 = this.mSettingToggle;
            if (textView3 != null) {
                textView3.setTextColor(gutsTextColor);
            }
            TextView textView4 = (TextView) findViewById(R.id.package_block_content_text);
            if (textView4 != null) {
                textView4.setTextColor(gutsTextColor2);
            }
            TextView textView5 = (TextView) findViewById(R.id.package_block_content_text2);
            if (textView5 != null) {
                textView5.setTextColor(gutsTextColor2);
            }
            TextView textView6 = this.mDoneButton;
            if (textView6 != null) {
                textView6.setTextColor(gutsTextColor);
            }
            TextView textView7 = this.mTurnOffConFirmButton;
            if (textView7 != null) {
                textView7.setTextColor(gutsTextColor);
            }
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.button_container);
            if (linearLayout != null) {
                Drawable mutate = ((LinearLayout) this).mContext.getDrawable(R.drawable.notification_guts_button_divider).mutate();
                mutate.setTint(gutsTextColor);
                linearLayout.setDividerDrawable(mutate);
            }
        }
        NotificationControlsEvent notificationControlsEvent = NotificationControlsEvent.NOTIFICATION_CONTROLS_OPEN;
        StatusBarNotification statusBarNotification = this.mSbn;
        if (statusBarNotification != null) {
            this.mUiEventLogger.logWithInstanceId(notificationControlsEvent, statusBarNotification.getUid(), this.mSbn.getPackageName(), this.mSbn.getInstanceId());
        }
        this.mMetricsLogger.write(notificationControlsLogMaker());
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
        new Handler((Looper) Dependency.get(Dependency.BG_LOOPER)).post(new UpdateAppLevelSettingRunnable(this.mINotificationManager, this.mPackageName, this.mAppUid, this.mAlertAllowed, false, this.mPackageIsBlocked));
        if (!this.mPackageIsBlocked) {
            return false;
        }
        SystemUIAnalytics.sendEventCDLog("QPN001", "QPNE0025", "app", this.mPackageName);
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.GutContentInitializer
    public final boolean initializeGutContentView(ExpandableNotificationRow expandableNotificationRow) {
        NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1;
        StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
        String packageName = statusBarNotification.getPackageName();
        PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(statusBarNotification.getUser().getIdentifier(), getContext());
        NotificationGutsManager notificationGutsManager = (NotificationGutsManager) Dependency.get(NotificationGutsManager.class);
        try {
            INotificationManager iNotificationManager = (INotificationManager) Dependency.get(INotificationManager.class);
            NotificationChannel channel = expandableNotificationRow.mEntry.getChannel();
            ArraySet uniqueChannels = expandableNotificationRow.getUniqueChannels();
            NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
            notificationGutsManager.getClass();
            NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
            StatusBarNotification statusBarNotification2 = expandableNotificationRow.mEntry.mSbn;
            if (statusBarNotification2.getUser().equals(UserHandle.ALL) && ((NotificationLockscreenUserManagerImpl) notificationGutsManager.mLockscreenUserManager).mCurrentUserId != 0) {
                notificationGutsManager$$ExternalSyntheticLambda1 = null;
                UiEventLogger uiEventLogger = (UiEventLogger) Dependency.get(UiEventLogger.class);
                boolean isDeviceProvisioned = ((DeviceProvisionedControllerImpl) ((DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class))).isDeviceProvisioned();
                boolean isNonPackageBlockable = expandableNotificationRow.getIsNonPackageBlockable();
                ((HighPriorityProvider) Dependency.get(HighPriorityProvider.class)).isHighPriority(expandableNotificationRow.mEntry, true);
                bindNotification(packageManagerForUser, iNotificationManager, packageName, channel, uniqueChannels, notificationEntry, notificationGutsManager$$ExternalSyntheticLambda1, uiEventLogger, isDeviceProvisioned, isNonPackageBlockable, (AssistantFeedbackController) Dependency.get(AssistantFeedbackController.class));
                return true;
            }
            notificationGutsManager$$ExternalSyntheticLambda1 = new NotificationGutsManager$$ExternalSyntheticLambda1(notificationGutsManager, notificationGuts, statusBarNotification2);
            UiEventLogger uiEventLogger2 = (UiEventLogger) Dependency.get(UiEventLogger.class);
            boolean isDeviceProvisioned2 = ((DeviceProvisionedControllerImpl) ((DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class))).isDeviceProvisioned();
            boolean isNonPackageBlockable2 = expandableNotificationRow.getIsNonPackageBlockable();
            ((HighPriorityProvider) Dependency.get(HighPriorityProvider.class)).isHighPriority(expandableNotificationRow.mEntry, true);
            bindNotification(packageManagerForUser, iNotificationManager, packageName, channel, uniqueChannels, notificationEntry, notificationGutsManager$$ExternalSyntheticLambda1, uiEventLogger2, isDeviceProvisioned2, isNonPackageBlockable2, (AssistantFeedbackController) Dependency.get(AssistantFeedbackController.class));
            return true;
        } catch (Exception e) {
            Log.e("InfoGuts", "error binding guts", e);
            return false;
        }
    }

    public boolean isAnimating() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean needsFalsingProtection() {
        return true;
    }

    public final LogMaker notificationControlsLogMaker() {
        StatusBarNotification statusBarNotification = this.mSbn;
        return (statusBarNotification == null ? new LogMaker(1621) : statusBarNotification.getLogMaker().setCategory(1621)).setCategory(204).setType(1).setSubtype(0);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final void onFinishedClosing() {
        bindInlineControls();
        NotificationControlsEvent notificationControlsEvent = NotificationControlsEvent.NOTIFICATION_CONTROLS_CLOSE;
        StatusBarNotification statusBarNotification = this.mSbn;
        if (statusBarNotification != null) {
            this.mUiEventLogger.logWithInstanceId(notificationControlsEvent, statusBarNotification.getUid(), this.mSbn.getPackageName(), this.mSbn.getInstanceId());
        }
        this.mMetricsLogger.write(notificationControlsLogMaker().setType(2));
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (this.mGutsContainer == null || accessibilityEvent.getEventType() != 32) {
            return;
        }
        if (this.mGutsContainer.mExposed) {
            accessibilityEvent.getText().add(((LinearLayout) this).mContext.getString(R.string.notification_channel_controls_opened_accessibility, this.mAppName));
        } else {
            accessibilityEvent.getText().add(((LinearLayout) this).mContext.getString(R.string.notification_channel_controls_closed_accessibility, this.mAppName));
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mActualHeight = getHeight();
        if (z) {
            updateInlineButtonLayout();
        }
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

    public final void updateInlineButtonLayout() {
        Resources resources = getContext().getResources();
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        Context context = getContext();
        secQSPanelResourcePicker.getClass();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) ((SecQSPanelResourcePicker.getPanelWidth(context) - (resources.getDimensionPixelSize(R.dimen.notification_side_paddings) * 2)) * 0.5f), -2);
        layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.notification_guts_toggle_button_margin_top);
        TextView textView = this.mToggleSettingsButton;
        if (textView != null) {
            textView.setLayoutParams(layoutParams);
        }
        TextView textView2 = this.mTurnOffButton;
        if (textView2 != null) {
            textView2.setLayoutParams(layoutParams);
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
