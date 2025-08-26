package com.android.systemui.statusbar.notification.row;

import android.animation.TimeInterpolator;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.Html;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.UseElapsedRealtimeForCreationTime;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl;
import com.android.systemui.statusbar.notification.promoted.domain.interactor.PackageDemotionInteractor;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.icon.AppIconProvider;
import com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.List;

/* loaded from: classes3.dex */
public class NotificationInfo extends LinearLayout implements NotificationGuts.GutsContent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mActualHeight;
    public String mAppName;
    public NotificationGutsManager$$ExternalSyntheticLambda2 mAppSettingsClickListener;
    public int mAppUid;
    public AssistantFeedbackController mAssistantFeedbackController;
    public TextView mAutomaticDescriptionView;
    public ChannelEditorDialogController mChannelEditorDialogController;
    public Integer mChosenImportance;
    public String mDelegatePkg;
    public NotificationEntry mEntry;
    public ViewCompat$$ExternalSyntheticLambda0 mFeedbackClickListener;
    public NotificationGuts mGutsContainer;
    public INotificationManager mINotificationManager;
    public boolean mIsAutomaticChosen;
    public boolean mIsDeviceProvisioned;
    public boolean mIsDismissable;
    public boolean mIsNonblockable;
    public boolean mIsSingleDefaultChannel;
    public boolean mIsSystemRegisteredCall;
    public MetricsLogger mMetricsLogger;
    public final NotificationInfo$$ExternalSyntheticLambda0 mOnAlert;
    public final NotificationInfo$$ExternalSyntheticLambda0 mOnAutomatic;
    public ExpandableNotificationRow$$ExternalSyntheticLambda6 mOnCloseClickListener;
    public final NotificationInfo$$ExternalSyntheticLambda0 mOnDismissSettings;
    public NotificationGutsManager$$ExternalSyntheticLambda1 mOnSettingsClickListener;
    public final NotificationInfo$$ExternalSyntheticLambda0 mOnSilent;
    public OnUserInteractionCallback mOnUserInteractionCallback;
    public String mPackageName;
    public Drawable mPkgIcon;
    public PackageManager mPm;
    public boolean mPresentingChannelEditorDialog;
    public boolean mPressedApply;
    public TextView mPriorityDescriptionView;
    public NotificationListenerService.Ranking mRanking;
    public StatusBarNotification mSbn;
    public boolean mShowAutomaticSetting;
    public TextView mSilentDescriptionView;
    public NotificationChannel mSingleNotificationChannel;
    boolean mSkipPost;
    public int mStartingChannelImportance;
    public UiEventLogger mUiEventLogger;
    public boolean mWasShownHighPriority;

    public class UpdateImportanceRunnable implements Runnable {
        public final int mAppUid;
        public final NotificationChannel mChannelToUpdate;
        public final int mCurrentImportance;
        public final INotificationManager mINotificationManager;
        public final int mNewImportance;
        public final String mPackageName;
        public final boolean mUnlockImportance;

        public UpdateImportanceRunnable(INotificationManager iNotificationManager, String str, int i, NotificationChannel notificationChannel, int i2, int i3, boolean z) {
            this.mINotificationManager = iNotificationManager;
            this.mPackageName = str;
            this.mAppUid = i;
            this.mChannelToUpdate = notificationChannel;
            this.mCurrentImportance = i2;
            this.mNewImportance = i3;
            this.mUnlockImportance = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                NotificationChannel notificationChannel = this.mChannelToUpdate;
                if (notificationChannel == null) {
                    this.mINotificationManager.setNotificationsEnabledWithImportanceLockForPackage(this.mPackageName, this.mAppUid, this.mNewImportance >= this.mCurrentImportance);
                } else {
                    if (this.mUnlockImportance) {
                        this.mINotificationManager.unlockNotificationChannel(this.mPackageName, this.mAppUid, notificationChannel.getId());
                        return;
                    }
                    notificationChannel.setImportance(this.mNewImportance);
                    this.mChannelToUpdate.lockFields(4);
                    this.mINotificationManager.updateNotificationChannelForPackage(this.mPackageName, this.mAppUid, this.mChannelToUpdate);
                }
            } catch (RemoteException e) {
                Log.e("InfoGuts", "Unable to update notification importance", e);
            }
        }
    }

    public NotificationInfo(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPresentingChannelEditorDialog = false;
        this.mSkipPost = false;
        this.mOnAutomatic = new NotificationInfo$$ExternalSyntheticLambda0(this, 0);
        this.mOnAlert = new NotificationInfo$$ExternalSyntheticLambda0(this, 1);
        this.mOnSilent = new NotificationInfo$$ExternalSyntheticLambda0(this, 2);
        this.mOnDismissSettings = new NotificationInfo$$ExternalSyntheticLambda0(this, 3);
    }

    public final void applyAlertingBehavior(int i, boolean z) {
        if (z) {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.setOrdering(0);
            TransitionSet transitionSetAddTransition = transitionSet.addTransition(new Fade(2)).addTransition(new ChangeBounds());
            Transition duration = new Fade(1).setStartDelay(150L).setDuration(200L);
            Interpolator interpolator = Interpolators.FAST_OUT_SLOW_IN;
            transitionSetAddTransition.addTransition(duration.setInterpolator(interpolator));
            transitionSet.setDuration(350L);
            transitionSet.setInterpolator((TimeInterpolator) interpolator);
            TransitionManager.beginDelayedTransition(this, transitionSet);
        }
        final View viewFindViewById = findViewById(R.id.alert);
        final View viewFindViewById2 = findViewById(R.id.silence);
        final View viewFindViewById3 = findViewById(R.id.automatic);
        if (i == 0) {
            this.mPriorityDescriptionView.setVisibility(0);
            this.mSilentDescriptionView.setVisibility(8);
            this.mAutomaticDescriptionView.setVisibility(8);
            final int i2 = 0;
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationInfo$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            View view = viewFindViewById;
                            View view2 = viewFindViewById2;
                            View view3 = viewFindViewById3;
                            int i3 = NotificationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = viewFindViewById;
                            View view5 = viewFindViewById2;
                            View view6 = viewFindViewById3;
                            int i4 = NotificationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(true);
                            view6.setSelected(false);
                            break;
                        default:
                            View view7 = viewFindViewById;
                            View view8 = viewFindViewById2;
                            View view9 = viewFindViewById3;
                            int i5 = NotificationInfo.$r8$clinit;
                            view7.setSelected(true);
                            view8.setSelected(false);
                            view9.setSelected(false);
                            break;
                    }
                }
            });
        } else if (i == 1) {
            this.mSilentDescriptionView.setVisibility(0);
            this.mPriorityDescriptionView.setVisibility(8);
            this.mAutomaticDescriptionView.setVisibility(8);
            final int i3 = 1;
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationInfo$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i3) {
                        case 0:
                            View view = viewFindViewById;
                            View view2 = viewFindViewById2;
                            View view3 = viewFindViewById3;
                            int i32 = NotificationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = viewFindViewById;
                            View view5 = viewFindViewById2;
                            View view6 = viewFindViewById3;
                            int i4 = NotificationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(true);
                            view6.setSelected(false);
                            break;
                        default:
                            View view7 = viewFindViewById;
                            View view8 = viewFindViewById2;
                            View view9 = viewFindViewById3;
                            int i5 = NotificationInfo.$r8$clinit;
                            view7.setSelected(true);
                            view8.setSelected(false);
                            view9.setSelected(false);
                            break;
                    }
                }
            });
        } else {
            if (i != 2) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unrecognized alerting behavior: "));
            }
            this.mAutomaticDescriptionView.setVisibility(0);
            this.mPriorityDescriptionView.setVisibility(8);
            this.mSilentDescriptionView.setVisibility(8);
            final int i4 = 2;
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationInfo$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i4) {
                        case 0:
                            View view = viewFindViewById3;
                            View view2 = viewFindViewById;
                            View view3 = viewFindViewById2;
                            int i32 = NotificationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = viewFindViewById3;
                            View view5 = viewFindViewById;
                            View view6 = viewFindViewById2;
                            int i42 = NotificationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(true);
                            view6.setSelected(false);
                            break;
                        default:
                            View view7 = viewFindViewById3;
                            View view8 = viewFindViewById;
                            View view9 = viewFindViewById2;
                            int i5 = NotificationInfo.$r8$clinit;
                            view7.setSelected(true);
                            view8.setSelected(false);
                            view9.setSelected(false);
                            break;
                    }
                }
            });
        }
        ((TextView) findViewById(R.id.done)).setText(getAlertingBehavior() != i ? R.string.inline_ok_button : R.string.inline_done_button);
    }

    public final void bindInlineControls() {
        if (this.mIsSystemRegisteredCall) {
            findViewById(R.id.non_configurable_call_text).setVisibility(0);
            findViewById(R.id.non_configurable_text).setVisibility(8);
            findViewById(R.id.non_configurable_multichannel_text).setVisibility(8);
            findViewById(R.id.interruptiveness_settings).setVisibility(8);
            ((TextView) findViewById(R.id.done)).setText(R.string.inline_done_button);
            findViewById(R.id.turn_off_notifications).setVisibility(8);
        } else if (this.mIsNonblockable) {
            findViewById(R.id.non_configurable_text).setVisibility(0);
            findViewById(R.id.non_configurable_call_text).setVisibility(8);
            findViewById(R.id.non_configurable_multichannel_text).setVisibility(8);
            findViewById(R.id.interruptiveness_settings).setVisibility(8);
            ((TextView) findViewById(R.id.done)).setText(R.string.inline_done_button);
            findViewById(R.id.turn_off_notifications).setVisibility(8);
        } else {
            findViewById(R.id.non_configurable_call_text).setVisibility(8);
            findViewById(R.id.non_configurable_text).setVisibility(8);
            findViewById(R.id.non_configurable_multichannel_text).setVisibility(8);
            findViewById(R.id.interruptiveness_settings).setVisibility(0);
        }
        View viewFindViewById = findViewById(R.id.turn_off_notifications);
        viewFindViewById.setOnClickListener(new NotificationInfo$$ExternalSyntheticLambda0(this, 4));
        viewFindViewById.setVisibility((!viewFindViewById.hasOnClickListeners() || this.mIsNonblockable) ? 8 : 0);
        View viewFindViewById2 = findViewById(R.id.inline_dismiss);
        viewFindViewById2.setOnClickListener(this.mOnCloseClickListener);
        viewFindViewById2.setVisibility((viewFindViewById2.hasOnClickListeners() && this.mIsDismissable) ? 0 : 8);
        View viewFindViewById3 = findViewById(R.id.done);
        viewFindViewById3.setOnClickListener(this.mOnDismissSettings);
        viewFindViewById3.setAccessibilityDelegate(this.mGutsContainer.getAccessibilityDelegate());
        View viewFindViewById4 = findViewById(R.id.silence);
        View viewFindViewById5 = findViewById(R.id.alert);
        viewFindViewById4.setOnClickListener(this.mOnSilent);
        viewFindViewById5.setOnClickListener(this.mOnAlert);
        View viewFindViewById6 = findViewById(R.id.automatic);
        if (this.mShowAutomaticSetting) {
            TextView textView = this.mAutomaticDescriptionView;
            Context context = ((LinearLayout) this).mContext;
            int feedbackStatus = this.mAssistantFeedbackController.getFeedbackStatus(this.mRanking);
            textView.setText(Html.fromHtml(context.getText(feedbackStatus != 1 ? feedbackStatus != 2 ? feedbackStatus != 3 ? feedbackStatus != 4 ? R.string.notification_channel_summary_automatic : R.string.notification_channel_summary_automatic_demoted : R.string.notification_channel_summary_automatic_promoted : R.string.notification_channel_summary_automatic_silenced : R.string.notification_channel_summary_automatic_alerted).toString()));
            viewFindViewById6.setVisibility(0);
            viewFindViewById6.setOnClickListener(this.mOnAutomatic);
        } else {
            viewFindViewById6.setVisibility(8);
        }
        applyAlertingBehavior(getAlertingBehavior(), false);
    }

    public void bindNotification(PackageManager packageManager, INotificationManager iNotificationManager, AppIconProvider appIconProvider, NotificationIconStyleProvider notificationIconStyleProvider, OnUserInteractionCallback onUserInteractionCallback, ChannelEditorDialogController channelEditorDialogController, PackageDemotionInteractor packageDemotionInteractor, String str, NotificationListenerService.Ranking ranking, StatusBarNotification statusBarNotification, NotificationEntry notificationEntry, NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1, NotificationGutsManager$$ExternalSyntheticLambda2 notificationGutsManager$$ExternalSyntheticLambda2, ViewCompat$$ExternalSyntheticLambda0 viewCompat$$ExternalSyntheticLambda0, UiEventLogger uiEventLogger, boolean z, boolean z2, boolean z3, boolean z4, AssistantFeedbackController assistantFeedbackController, MetricsLogger metricsLogger, ExpandableNotificationRow$$ExternalSyntheticLambda6 expandableNotificationRow$$ExternalSyntheticLambda6) {
        NotificationChannelGroup notificationChannelGroupForPackage;
        this.mINotificationManager = iNotificationManager;
        this.mMetricsLogger = metricsLogger;
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mChannelEditorDialogController = channelEditorDialogController;
        this.mAssistantFeedbackController = assistantFeedbackController;
        this.mPackageName = str;
        this.mSbn = statusBarNotification;
        this.mRanking = ranking;
        this.mEntry = notificationEntry;
        this.mPm = packageManager;
        this.mAppSettingsClickListener = notificationGutsManager$$ExternalSyntheticLambda2;
        this.mFeedbackClickListener = viewCompat$$ExternalSyntheticLambda0;
        this.mAppName = str;
        this.mOnSettingsClickListener = notificationGutsManager$$ExternalSyntheticLambda1;
        NotificationChannel channel = ranking.getChannel();
        this.mSingleNotificationChannel = channel;
        this.mStartingChannelImportance = channel.getImportance();
        this.mWasShownHighPriority = z4;
        this.mIsNonblockable = z2;
        this.mIsDismissable = z3;
        this.mAppUid = this.mSbn.getUid();
        this.mDelegatePkg = this.mSbn.getOpPkg();
        this.mIsDeviceProvisioned = z;
        this.mShowAutomaticSetting = this.mAssistantFeedbackController.mFeedbackEnabled;
        this.mUiEventLogger = uiEventLogger;
        this.mOnCloseClickListener = expandableNotificationRow$$ExternalSyntheticLambda6;
        this.mIsSystemRegisteredCall = this.mSbn.getNotification().isStyle(Notification.CallStyle.class) && this.mINotificationManager.isInCall(this.mSbn.getPackageName(), this.mSbn.getUid());
        this.mIsSingleDefaultChannel = this.mSingleNotificationChannel.getId().equals("miscellaneous") && this.mINotificationManager.getNumNotificationChannelsForPackage(str, this.mAppUid, false) == 1;
        this.mIsAutomaticChosen = getAlertingBehavior() == 2;
        CharSequence name = null;
        this.mPkgIcon = null;
        ApplicationInfo applicationInfo = (ApplicationInfo) this.mSbn.getNotification().extras.getParcelable("android.appInfo", ApplicationInfo.class);
        if (applicationInfo != null) {
            try {
                this.mAppName = String.valueOf(this.mPm.getApplicationLabel(applicationInfo));
                this.mPkgIcon = this.mPm.getApplicationIcon(applicationInfo);
            } catch (Exception unused) {
            }
        }
        if (this.mPkgIcon == null) {
            this.mPkgIcon = this.mPm.getDefaultActivityIcon();
        }
        ((ImageView) findViewById(R.id.pkg_icon)).setImageDrawable(this.mPkgIcon);
        ((TextView) findViewById(R.id.pkg_name)).setText(this.mAppName);
        TextView textView = (TextView) findViewById(R.id.delegate_name);
        if (TextUtils.equals(this.mPackageName, this.mDelegatePkg)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
        View viewFindViewById = findViewById(R.id.app_settings);
        PackageManager packageManager2 = this.mPm;
        String str2 = this.mPackageName;
        NotificationChannel notificationChannel = this.mSingleNotificationChannel;
        int id = this.mSbn.getId();
        String tag = this.mSbn.getTag();
        final Intent intent = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.NOTIFICATION_PREFERENCES").setPackage(str2);
        List<ResolveInfo> listQueryIntentActivities = packageManager2.queryIntentActivities(intent, 65536);
        if (listQueryIntentActivities == null || listQueryIntentActivities.isEmpty() || listQueryIntentActivities.get(0) == null) {
            intent = null;
        } else {
            ActivityInfo activityInfo = listQueryIntentActivities.get(0).activityInfo;
            intent.setClassName(activityInfo.packageName, activityInfo.name);
            if (notificationChannel != null) {
                intent.putExtra("android.intent.extra.CHANNEL_ID", notificationChannel.getId());
            }
            intent.putExtra("android.intent.extra.NOTIFICATION_ID", id);
            intent.putExtra("android.intent.extra.NOTIFICATION_TAG", tag);
        }
        if (intent == null || TextUtils.isEmpty(this.mSbn.getNotification().getSettingsText())) {
            viewFindViewById.setVisibility(8);
        } else {
            viewFindViewById.setVisibility(0);
            viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationInfo$$ExternalSyntheticLambda8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NotificationInfo notificationInfo = this.f$0;
                    Intent intent2 = intent;
                    NotificationGutsManager$$ExternalSyntheticLambda2 notificationGutsManager$$ExternalSyntheticLambda22 = notificationInfo.mAppSettingsClickListener;
                    StatusBarNotification statusBarNotification2 = notificationGutsManager$$ExternalSyntheticLambda22.f$2;
                    NotificationGutsManager notificationGutsManager = notificationGutsManager$$ExternalSyntheticLambda22.f$0;
                    notificationGutsManager.mMetricsLogger.action(206);
                    notificationGutsManager$$ExternalSyntheticLambda22.f$1.resetFalsingCheck();
                    ((StatusBarNotificationActivityStarter) notificationGutsManager.mNotificationActivityStarter).startNotificationGutsIntent(intent2, statusBarNotification2.getUid(), notificationGutsManager$$ExternalSyntheticLambda22.f$3);
                }
            });
        }
        View viewFindViewById2 = findViewById(R.id.info);
        final int i = this.mAppUid;
        viewFindViewById2.setOnClickListener((i < 0 || this.mOnSettingsClickListener == null || !this.mIsDeviceProvisioned) ? null : new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationInfo$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationInfo notificationInfo = this.f$0;
                notificationInfo.mOnSettingsClickListener.onClick(notificationInfo.mSingleNotificationChannel, i);
            }
        });
        viewFindViewById2.setVisibility(viewFindViewById2.hasOnClickListeners() ? 0 : 8);
        TextView textView2 = (TextView) findViewById(R.id.channel_name);
        if (this.mIsSingleDefaultChannel) {
            textView2.setVisibility(8);
        } else {
            textView2.setText(this.mSingleNotificationChannel.getName());
        }
        NotificationChannel notificationChannel2 = this.mSingleNotificationChannel;
        if (notificationChannel2 != null && notificationChannel2.getGroup() != null && (notificationChannelGroupForPackage = this.mINotificationManager.getNotificationChannelGroupForPackage(this.mSingleNotificationChannel.getGroup(), this.mPackageName, this.mAppUid)) != null) {
            name = notificationChannelGroupForPackage.getName();
        }
        TextView textView3 = (TextView) findViewById(R.id.group_name);
        if (name != null) {
            textView3.setText(name);
            textView3.setVisibility(0);
        } else {
            textView3.setVisibility(8);
        }
        bindInlineControls();
        logUiEvent(NotificationControlsEvent.NOTIFICATION_CONTROLS_OPEN);
        MetricsLogger metricsLogger2 = this.mMetricsLogger;
        StatusBarNotification statusBarNotification2 = this.mSbn;
        metricsLogger2.write((statusBarNotification2 == null ? new LogMaker(1621) : statusBarNotification2.getLogMaker().setCategory(1621)).setCategory(204).setType(1).setSubtype(0));
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final int getActualHeight() {
        return this.mActualHeight;
    }

    public final int getAlertingBehavior() {
        if (!this.mShowAutomaticSetting || this.mSingleNotificationChannel.hasUserSetImportance()) {
            return !this.mWasShownHighPriority ? 1 : 0;
        }
        return 2;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean handleCloseControls(boolean z, boolean z2) {
        ChannelEditorDialogController channelEditorDialogController;
        if (this.mPresentingChannelEditorDialog && (channelEditorDialogController = this.mChannelEditorDialogController) != null) {
            this.mPresentingChannelEditorDialog = false;
            channelEditorDialogController.onFinishListener = null;
            channelEditorDialogController.done();
        }
        if (z && !this.mIsNonblockable) {
            if (this.mChosenImportance == null) {
                this.mChosenImportance = Integer.valueOf(this.mStartingChannelImportance);
            }
            if (this.mChosenImportance != null) {
                logUiEvent(NotificationControlsEvent.NOTIFICATION_CONTROLS_SAVE_IMPORTANCE);
                MetricsLogger metricsLogger = this.mMetricsLogger;
                Integer num = this.mChosenImportance;
                int iIntValue = num != null ? num.intValue() : this.mStartingChannelImportance;
                StatusBarNotification statusBarNotification = this.mSbn;
                metricsLogger.write((statusBarNotification == null ? new LogMaker(1621) : statusBarNotification.getLogMaker().setCategory(1621)).setCategory(IKnoxCustomManager.Stub.TRANSACTION_getBsohUnbiased).setType(4).setSubtype(iIntValue - this.mStartingChannelImportance));
                int iIntValue2 = this.mChosenImportance.intValue();
                if (this.mStartingChannelImportance != -1000 && ((this.mWasShownHighPriority && this.mChosenImportance.intValue() >= 3) || (!this.mWasShownHighPriority && this.mChosenImportance.intValue() < 3))) {
                    iIntValue2 = this.mStartingChannelImportance;
                }
                new Handler((Looper) Dependency.sDependency.getDependencyInner(Dependency.BG_LOOPER)).post(new UpdateImportanceRunnable(this.mINotificationManager, this.mPackageName, this.mAppUid, this.mSingleNotificationChannel, this.mStartingChannelImportance, iIntValue2, this.mIsAutomaticChosen));
                int i = NotificationBundleUi.$r8$clinit;
                OnUserInteractionCallback onUserInteractionCallback = this.mOnUserInteractionCallback;
                NotificationEntry notificationEntry = this.mEntry;
                OnUserInteractionCallbackImpl onUserInteractionCallbackImpl = (OnUserInteractionCallbackImpl) onUserInteractionCallback;
                onUserInteractionCallbackImpl.getClass();
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i2 = UseElapsedRealtimeForCreationTime.$r8$clinit;
                onUserInteractionCallbackImpl.mVisualStabilityCoordinator.temporarilyAllowSectionChanges(notificationEntry, SystemClock.uptimeMillis());
            }
        }
        this.mChosenImportance = null;
        this.mPressedApply = false;
        return false;
    }

    public boolean isAnimating() {
        return false;
    }

    public final void logUiEvent(NotificationControlsEvent notificationControlsEvent) {
        StatusBarNotification statusBarNotification = this.mSbn;
        if (statusBarNotification != null) {
            this.mUiEventLogger.logWithInstanceId(notificationControlsEvent, statusBarNotification.getUid(), this.mSbn.getPackageName(), this.mSbn.getInstanceId());
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean needsFalsingProtection() {
        return true;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mPriorityDescriptionView = (TextView) findViewById(R.id.alert_summary);
        this.mSilentDescriptionView = (TextView) findViewById(R.id.silence_summary);
        this.mAutomaticDescriptionView = (TextView) findViewById(R.id.automatic_summary);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final void onFinishedClosing() {
        bindInlineControls();
        logUiEvent(NotificationControlsEvent.NOTIFICATION_CONTROLS_CLOSE);
        MetricsLogger metricsLogger = this.mMetricsLogger;
        StatusBarNotification statusBarNotification = this.mSbn;
        metricsLogger.write((statusBarNotification == null ? new LogMaker(1621) : statusBarNotification.getLogMaker().setCategory(1621)).setCategory(204).setType(1).setSubtype(0).setType(2));
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
    public void setGutsParent(NotificationGuts notificationGuts) {
        this.mGutsContainer = notificationGuts;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean shouldBeSavedOnClose() {
        return this.mPressedApply;
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
