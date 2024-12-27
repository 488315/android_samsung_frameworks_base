package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationGuts;

public class FeedbackInfo extends LinearLayout implements NotificationGuts.GutsContent {
    public String mAppName;
    public NotificationEntry mEntry;
    public ExpandableNotificationRow mExpandableNotificationRow;
    public AssistantFeedbackController mFeedbackController;
    public NotificationGuts mGutsContainer;
    public NotificationMenuRowPlugin mMenuRowPlugin;
    public NotificationGutsManager mNotificationGutsManager;
    public PackageManager mPm;
    public NotificationListenerService.Ranking mRanking;
    public IStatusBarService mStatusBarService;

    public static void $r8$lambda$pngimGXdXE9MbapRjY8pWAU1A5w(FeedbackInfo feedbackInfo, View view) {
        NotificationMenuRowPlugin notificationMenuRowPlugin = feedbackInfo.mExpandableNotificationRow.mMenuRow;
        feedbackInfo.mMenuRowPlugin = notificationMenuRowPlugin;
        NotificationMenuRowPlugin.MenuItem longpressMenuItem = notificationMenuRowPlugin != null ? notificationMenuRowPlugin.getLongpressMenuItem(((LinearLayout) feedbackInfo).mContext) : null;
        feedbackInfo.mGutsContainer.closeControls(view, false);
        feedbackInfo.mNotificationGutsManager.openGuts(feedbackInfo.mExpandableNotificationRow, 0, 0, longpressMenuItem);
        feedbackInfo.handleFeedback(false);
    }

    public FeedbackInfo(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void bindGuts(PackageManager packageManager, StatusBarNotification statusBarNotification, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, AssistantFeedbackController assistantFeedbackController, IStatusBarService iStatusBarService, NotificationGutsManager notificationGutsManager) {
        Drawable defaultActivityIcon;
        String packageName = statusBarNotification.getPackageName();
        this.mPm = packageManager;
        this.mEntry = notificationEntry;
        this.mExpandableNotificationRow = expandableNotificationRow;
        this.mRanking = notificationEntry.mRanking;
        this.mFeedbackController = assistantFeedbackController;
        this.mAppName = packageName;
        this.mStatusBarService = iStatusBarService;
        this.mNotificationGutsManager = notificationGutsManager;
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 795136);
            if (applicationInfo != null) {
                this.mAppName = String.valueOf(this.mPm.getApplicationLabel(applicationInfo));
                defaultActivityIcon = this.mPm.getApplicationIcon(applicationInfo);
            } else {
                defaultActivityIcon = null;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            defaultActivityIcon = this.mPm.getDefaultActivityIcon();
        }
        ((ImageView) findViewById(R.id.pkg_icon)).setImageDrawable(defaultActivityIcon);
        ((TextView) findViewById(R.id.pkg_name)).setText(this.mAppName);
        TextView textView = (TextView) findViewById(R.id.prompt);
        TextView textView2 = (TextView) findViewById(R.id.yes);
        TextView textView3 = (TextView) findViewById(R.id.no);
        textView2.setVisibility(0);
        textView3.setVisibility(0);
        final int i = 0;
        textView2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.row.FeedbackInfo$$ExternalSyntheticLambda0
            public final /* synthetic */ FeedbackInfo f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                FeedbackInfo feedbackInfo = this.f$0;
                switch (i2) {
                    case 0:
                        feedbackInfo.mGutsContainer.closeControls(view, false);
                        feedbackInfo.handleFeedback(true);
                        break;
                    default:
                        FeedbackInfo.$r8$lambda$pngimGXdXE9MbapRjY8pWAU1A5w(feedbackInfo, view);
                        break;
                }
            }
        });
        final int i2 = 1;
        textView3.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.row.FeedbackInfo$$ExternalSyntheticLambda0
            public final /* synthetic */ FeedbackInfo f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                FeedbackInfo feedbackInfo = this.f$0;
                switch (i22) {
                    case 0:
                        feedbackInfo.mGutsContainer.closeControls(view, false);
                        feedbackInfo.handleFeedback(true);
                        break;
                    default:
                        FeedbackInfo.$r8$lambda$pngimGXdXE9MbapRjY8pWAU1A5w(feedbackInfo, view);
                        break;
                }
            }
        });
        StringBuilder sb = new StringBuilder();
        int feedbackStatus = this.mFeedbackController.getFeedbackStatus(this.mEntry);
        if (feedbackStatus == 1) {
            sb.append(((LinearLayout) this).mContext.getText(R.string.feedback_alerted));
        } else if (feedbackStatus == 2) {
            sb.append(((LinearLayout) this).mContext.getText(R.string.feedback_silenced));
        } else if (feedbackStatus == 3) {
            sb.append(((LinearLayout) this).mContext.getText(R.string.feedback_promoted));
        } else if (feedbackStatus == 4) {
            sb.append(((LinearLayout) this).mContext.getText(R.string.feedback_demoted));
        }
        sb.append(" ");
        sb.append(((LinearLayout) this).mContext.getText(R.string.feedback_prompt));
        textView.setText(Html.fromHtml(sb.toString()));
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final int getActualHeight() {
        return getHeight();
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean handleCloseControls(boolean z, boolean z2) {
        return false;
    }

    public final void handleFeedback(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putInt("feedback.rating", z ? 1 : -1);
        if (this.mFeedbackController.mFeedbackEnabled) {
            try {
                this.mStatusBarService.onNotificationFeedbackReceived(this.mRanking.getKey(), bundle);
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean needsFalsingProtection() {
        return false;
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

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final void setGutsParent(NotificationGuts notificationGuts) {
        this.mGutsContainer = notificationGuts;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean shouldBeSavedOnClose() {
        return false;
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
