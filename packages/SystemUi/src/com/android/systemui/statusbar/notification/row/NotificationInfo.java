package com.android.systemui.statusbar.notification.row;

import android.animation.TimeInterpolator;
import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.Html;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationInfo extends LinearLayout implements NotificationGuts.GutsContent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mActualHeight;
    public String mAppName;
    public NotificationGutsManager$$ExternalSyntheticLambda0 mAppSettingsClickListener;
    public int mAppUid;
    public AssistantFeedbackController mAssistantFeedbackController;
    public TextView mAutomaticDescriptionView;
    public ChannelEditorDialogController mChannelEditorDialogController;
    public Integer mChosenImportance;
    public String mDelegatePkg;
    public NotificationEntry mEntry;
    public NotificationGuts mGutsContainer;
    public INotificationManager mINotificationManager;
    public boolean mIsAutomaticChosen;
    public boolean mIsDeviceProvisioned;
    public boolean mIsNonblockable;
    public boolean mIsSingleDefaultChannel;
    public boolean mIsSystemRegisteredCall;
    public MetricsLogger mMetricsLogger;
    public int mNumUniqueChannelsInRow;
    public final NotificationInfo$$ExternalSyntheticLambda0 mOnAlert;
    public final NotificationInfo$$ExternalSyntheticLambda0 mOnAutomatic;
    public final NotificationInfo$$ExternalSyntheticLambda0 mOnDismissSettings;
    public OnSettingsClickListener mOnSettingsClickListener;
    public final NotificationInfo$$ExternalSyntheticLambda0 mOnSilent;
    public OnUserInteractionCallback mOnUserInteractionCallback;
    public String mPackageName;
    public Drawable mPkgIcon;
    public PackageManager mPm;
    public boolean mPresentingChannelEditorDialog;
    public boolean mPressedApply;
    public TextView mPriorityDescriptionView;
    public StatusBarNotification mSbn;
    public boolean mShowAutomaticSetting;
    public TextView mSilentDescriptionView;
    public NotificationChannel mSingleNotificationChannel;
    boolean mSkipPost;
    public int mStartingChannelImportance;
    public UiEventLogger mUiEventLogger;
    public Set mUniqueChannelsInRow;
    public boolean mWasShownHighPriority;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnSettingsClickListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UpdateImportanceRunnable implements Runnable {
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
                } else if (this.mUnlockImportance) {
                    this.mINotificationManager.unlockNotificationChannel(this.mPackageName, this.mAppUid, notificationChannel.getId());
                } else {
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
        final int i2 = 2;
        final int i3 = 0;
        if (z) {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.setOrdering(0);
            TransitionSet addTransition = transitionSet.addTransition(new Fade(2)).addTransition(new ChangeBounds());
            Transition duration = new Fade(1).setStartDelay(150L).setDuration(200L);
            Interpolator interpolator = Interpolators.FAST_OUT_SLOW_IN;
            addTransition.addTransition(duration.setInterpolator(interpolator));
            transitionSet.setDuration(350L);
            transitionSet.setInterpolator((TimeInterpolator) interpolator);
            TransitionManager.beginDelayedTransition(this, transitionSet);
        }
        final View findViewById = findViewById(R.id.alert);
        final View findViewById2 = findViewById(R.id.silence);
        final View findViewById3 = findViewById(R.id.automatic);
        if (i == 0) {
            this.mPriorityDescriptionView.setVisibility(0);
            this.mSilentDescriptionView.setVisibility(8);
            this.mAutomaticDescriptionView.setVisibility(8);
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationInfo$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i3) {
                        case 0:
                            View view = findViewById;
                            View view2 = findViewById2;
                            View view3 = findViewById3;
                            int i4 = NotificationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = findViewById;
                            View view5 = findViewById2;
                            View view6 = findViewById3;
                            int i5 = NotificationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(true);
                            view6.setSelected(false);
                            break;
                        default:
                            View view7 = findViewById;
                            View view8 = findViewById2;
                            View view9 = findViewById3;
                            int i6 = NotificationInfo.$r8$clinit;
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
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationInfo$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    switch (r4) {
                        case 0:
                            View view = findViewById;
                            View view2 = findViewById2;
                            View view3 = findViewById3;
                            int i4 = NotificationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = findViewById;
                            View view5 = findViewById2;
                            View view6 = findViewById3;
                            int i5 = NotificationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(true);
                            view6.setSelected(false);
                            break;
                        default:
                            View view7 = findViewById;
                            View view8 = findViewById2;
                            View view9 = findViewById3;
                            int i6 = NotificationInfo.$r8$clinit;
                            view7.setSelected(true);
                            view8.setSelected(false);
                            view9.setSelected(false);
                            break;
                    }
                }
            });
        } else {
            if (i != 2) {
                throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unrecognized alerting behavior: ", i));
            }
            this.mAutomaticDescriptionView.setVisibility(0);
            this.mPriorityDescriptionView.setVisibility(8);
            this.mSilentDescriptionView.setVisibility(8);
            post(new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationInfo$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            View view = findViewById3;
                            View view2 = findViewById;
                            View view3 = findViewById2;
                            int i4 = NotificationInfo.$r8$clinit;
                            view.setSelected(true);
                            view2.setSelected(false);
                            view3.setSelected(false);
                            break;
                        case 1:
                            View view4 = findViewById3;
                            View view5 = findViewById;
                            View view6 = findViewById2;
                            int i5 = NotificationInfo.$r8$clinit;
                            view4.setSelected(false);
                            view5.setSelected(true);
                            view6.setSelected(false);
                            break;
                        default:
                            View view7 = findViewById3;
                            View view8 = findViewById;
                            View view9 = findViewById2;
                            int i6 = NotificationInfo.$r8$clinit;
                            view7.setSelected(true);
                            view8.setSelected(false);
                            view9.setSelected(false);
                            break;
                    }
                }
            });
        }
        ((TextView) findViewById(R.id.done)).setText((getAlertingBehavior() == i ? 0 : 1) != 0 ? R.string.inline_ok_button : R.string.inline_done_button);
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
        } else if (this.mNumUniqueChannelsInRow > 1) {
            findViewById(R.id.non_configurable_call_text).setVisibility(8);
            findViewById(R.id.non_configurable_text).setVisibility(8);
            findViewById(R.id.interruptiveness_settings).setVisibility(8);
            findViewById(R.id.non_configurable_multichannel_text).setVisibility(0);
        } else {
            findViewById(R.id.non_configurable_call_text).setVisibility(8);
            findViewById(R.id.non_configurable_text).setVisibility(8);
            findViewById(R.id.non_configurable_multichannel_text).setVisibility(8);
            findViewById(R.id.interruptiveness_settings).setVisibility(0);
        }
        View findViewById = findViewById(R.id.turn_off_notifications);
        findViewById.setOnClickListener(new NotificationInfo$$ExternalSyntheticLambda0(this, 4));
        findViewById.setVisibility((!findViewById.hasOnClickListeners() || this.mIsNonblockable) ? 8 : 0);
        View findViewById2 = findViewById(R.id.done);
        findViewById2.setOnClickListener(this.mOnDismissSettings);
        findViewById2.setAccessibilityDelegate(this.mGutsContainer.getAccessibilityDelegate());
        View findViewById3 = findViewById(R.id.silence);
        View findViewById4 = findViewById(R.id.alert);
        findViewById3.setOnClickListener(this.mOnSilent);
        findViewById4.setOnClickListener(this.mOnAlert);
        View findViewById5 = findViewById(R.id.automatic);
        if (this.mShowAutomaticSetting) {
            TextView textView = this.mAutomaticDescriptionView;
            Context context = ((LinearLayout) this).mContext;
            int feedbackStatus = this.mAssistantFeedbackController.getFeedbackStatus(this.mEntry);
            textView.setText(Html.fromHtml(context.getText(feedbackStatus != 1 ? feedbackStatus != 2 ? feedbackStatus != 3 ? feedbackStatus != 4 ? R.string.notification_channel_summary_automatic : R.string.notification_channel_summary_automatic_demoted : R.string.notification_channel_summary_automatic_promoted : R.string.notification_channel_summary_automatic_silenced : R.string.notification_channel_summary_automatic_alerted).toString()));
            findViewById5.setVisibility(0);
            findViewById5.setOnClickListener(this.mOnAutomatic);
        } else {
            findViewById5.setVisibility(8);
        }
        applyAlertingBehavior(getAlertingBehavior(), false);
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
                Integer valueOf = Integer.valueOf(num != null ? num.intValue() : this.mStartingChannelImportance);
                StatusBarNotification statusBarNotification = this.mSbn;
                metricsLogger.write((statusBarNotification == null ? new LogMaker(1621) : statusBarNotification.getLogMaker().setCategory(1621)).setCategory(IKnoxCustomManager.Stub.TRANSACTION_getBsohUnbiased).setType(4).setSubtype(valueOf.intValue() - this.mStartingChannelImportance));
                int intValue = this.mChosenImportance.intValue();
                if (this.mStartingChannelImportance != -1000 && ((this.mWasShownHighPriority && this.mChosenImportance.intValue() >= 3) || (!this.mWasShownHighPriority && this.mChosenImportance.intValue() < 3))) {
                    intValue = this.mStartingChannelImportance;
                }
                new Handler((Looper) Dependency.get(Dependency.BG_LOOPER)).post(new UpdateImportanceRunnable(this.mINotificationManager, this.mPackageName, this.mAppUid, this.mNumUniqueChannelsInRow == 1 ? this.mSingleNotificationChannel : null, this.mStartingChannelImportance, intValue, this.mIsAutomaticChosen));
                ((OnUserInteractionCallbackImpl) this.mOnUserInteractionCallback).onImportanceChanged(this.mEntry);
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
    public final void setGutsParent(NotificationGuts notificationGuts) {
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
