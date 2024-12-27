package com.android.server.notification;

import android.R;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ShortcutInfo;
import android.content.res.Resources;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class BubbleExtractor implements NotificationSignalExtractor {
    public ActivityManager mActivityManager;
    public RankingConfig mConfig;
    public Context mContext;
    public ShortcutHelper mShortcutHelper;
    public boolean mSupportsBubble;

    public boolean canLaunchInTaskView(Context context, PendingIntent pendingIntent, String str) {
        if (pendingIntent == null) {
            Slog.w("BubbleExtractor", "Unable to create bubble -- no intent");
            return false;
        }
        Intent intent = pendingIntent.getIntent();
        ActivityInfo resolveActivityInfo =
                intent != null ? intent.resolveActivityInfo(context.getPackageManager(), 0) : null;
        if (resolveActivityInfo == null) {
            FrameworkStatsLog.write(173, str, 1);
            Slog.w(
                    "BubbleExtractor",
                    "Unable to send as bubble -- couldn't find activity info for intent: "
                            + intent);
            return false;
        }
        if (ActivityInfo.isResizeableMode(resolveActivityInfo.resizeMode)) {
            return true;
        }
        FrameworkStatsLog.write(173, str, 2);
        Slog.w(
                "BubbleExtractor",
                "Unable to send as bubble -- activity is not resizable for intent: " + intent);
        return false;
    }

    public boolean canPresentAsBubble(NotificationRecord notificationRecord) {
        if (!this.mSupportsBubble) {
            return false;
        }
        Notification.BubbleMetadata bubbleMetadata =
                notificationRecord.sbn.getNotification().getBubbleMetadata();
        String packageName = notificationRecord.sbn.getPackageName();
        if (bubbleMetadata == null) {
            return false;
        }
        String shortcutId = bubbleMetadata.getShortcutId();
        ShortcutInfo shortcutInfo = notificationRecord.mShortcutInfo;
        String id = shortcutInfo != null ? shortcutInfo.getId() : null;
        boolean equals =
                (id == null || shortcutId == null)
                        ? (shortcutId == null
                                        || this.mShortcutHelper.getValidShortcutInfo(
                                                        shortcutId,
                                                        notificationRecord.sbn.getUser(),
                                                        packageName)
                                                == null)
                                ? false
                                : true
                        : shortcutId.equals(id);
        if (bubbleMetadata.getIntent() == null && !equals) {
            notificationRecord.sbn.getKey();
            return false;
        }
        if (equals) {
            return true;
        }
        return canLaunchInTaskView(this.mContext, bubbleMetadata.getIntent(), packageName);
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void initialize(Context context, NotificationUsageStats notificationUsageStats) {
        this.mContext = context;
        this.mActivityManager = (ActivityManager) context.getSystemService("activity");
        this.mSupportsBubble =
                Resources.getSystem()
                        .getBoolean(R.bool.config_switch_phone_on_voice_reg_state_change);
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final RankingReconsideration process(NotificationRecord notificationRecord) {
        int i;
        if (notificationRecord.sbn.getNotification() == null
                || this.mConfig == null
                || this.mShortcutHelper == null) {
            return null;
        }
        boolean z =
                (!canPresentAsBubble(notificationRecord)
                                || this.mActivityManager.isLowRamDevice()
                                || !notificationRecord.isConversation()
                                || notificationRecord.mShortcutInfo == null
                                || notificationRecord.sbn.getNotification().isFgsOrUij())
                        ? false
                        : true;
        boolean bubblesEnabled =
                ((PreferencesHelper) this.mConfig).bubblesEnabled(notificationRecord.sbn.getUser());
        RankingConfig rankingConfig = this.mConfig;
        String packageName = notificationRecord.sbn.getPackageName();
        int uid = notificationRecord.sbn.getUid();
        PreferencesHelper preferencesHelper = (PreferencesHelper) rankingConfig;
        synchronized (preferencesHelper.mLock) {
            i =
                    preferencesHelper.getOrCreatePackagePreferencesLocked(uid, packageName)
                            .bubblePreference;
        }
        NotificationChannel notificationChannel = notificationRecord.mChannel;
        if (!bubblesEnabled || i == 0 || !z) {
            notificationRecord.mAllowBubble = false;
            if (!z) {
                notificationRecord.sbn.getNotification().setBubbleMetadata(null);
            }
        } else if (notificationChannel == null) {
            notificationRecord.mAllowBubble = true;
        } else if (i == 1) {
            notificationRecord.mAllowBubble = notificationChannel.getAllowBubbles() != 0;
        } else if (i == 2) {
            notificationRecord.mAllowBubble = notificationChannel.canBubble();
        }
        if (!notificationRecord.mAllowBubble || notificationRecord.mFlagBubbleRemoved) {
            notificationRecord.sbn.getNotification().flags &= -4097;
        } else {
            notificationRecord.sbn.getNotification().flags |= 4096;
        }
        return null;
    }

    public void setActivityManager(ActivityManager activityManager) {
        this.mActivityManager = activityManager;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setConfig(RankingConfig rankingConfig) {
        this.mConfig = rankingConfig;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setZenHelper(ZenModeHelper zenModeHelper) {}
}
