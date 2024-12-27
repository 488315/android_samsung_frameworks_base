package com.android.server.notification;

import android.content.Context;

import com.android.internal.compat.IPlatformCompat;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface NotificationSignalExtractor {
    void initialize(Context context, NotificationUsageStats notificationUsageStats);

    RankingReconsideration process(NotificationRecord notificationRecord);

    default void setCompatChangeLogger(IPlatformCompat iPlatformCompat) {}

    void setConfig(RankingConfig rankingConfig);

    void setZenHelper(ZenModeHelper zenModeHelper);
}
