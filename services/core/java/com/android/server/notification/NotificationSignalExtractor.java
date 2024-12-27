package com.android.server.notification;

import android.content.Context;

import com.android.internal.compat.IPlatformCompat;

public interface NotificationSignalExtractor {
    void initialize(Context context, NotificationUsageStats notificationUsageStats);

    RankingReconsideration process(NotificationRecord notificationRecord);

    default void setCompatChangeLogger(IPlatformCompat iPlatformCompat) {}

    void setConfig(RankingConfig rankingConfig);

    void setZenHelper(ZenModeHelper zenModeHelper);
}
