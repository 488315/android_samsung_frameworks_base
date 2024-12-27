package com.android.server.notification;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;

public class PriorityExtractor implements NotificationSignalExtractor {
    public RankingConfig mConfig;
    public ZenModeHelper mHelper;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void initialize(Context context, NotificationUsageStats notificationUsageStats) {}

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final RankingReconsideration process(NotificationRecord notificationRecord) {
        if (notificationRecord.sbn.getNotification() == null || this.mConfig == null) {
            return null;
        }
        ArrayList appsToBypassDndForEnabledForMode =
                this.mHelper.getAppsToBypassDndForEnabledForMode();
        if (appsToBypassDndForEnabledForMode != null) {
            Iterator it = appsToBypassDndForEnabledForMode.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (str != null) {
                    String[] split = str.split(":");
                    if (split.length >= 2
                            && split[0].equals(notificationRecord.sbn.getPackageName())
                            && Integer.parseInt(split[1]) == notificationRecord.sbn.getUserId()) {
                        notificationRecord.mPackagePriority = 2;
                    }
                }
            }
        } else {
            notificationRecord.mPackagePriority =
                    notificationRecord.mChannel.canBypassDnd() ? 2 : 0;
        }
        return null;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setConfig(RankingConfig rankingConfig) {
        this.mConfig = rankingConfig;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setZenHelper(ZenModeHelper zenModeHelper) {
        this.mHelper = zenModeHelper;
    }
}
