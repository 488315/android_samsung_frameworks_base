package com.android.server.notification;

import android.content.Context;
import android.util.Log;
import android.util.Slog;

public class ZenModeExtractor implements NotificationSignalExtractor {
    public static final boolean DBG = Log.isLoggable("ZenModeExtractor", 3);
    public ZenModeHelper mZenModeHelper;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void initialize(Context context, NotificationUsageStats notificationUsageStats) {
        if (DBG) {
            Slog.d("ZenModeExtractor", "Initializing  " + getClass().getSimpleName() + ".");
        }
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final RankingReconsideration process(NotificationRecord notificationRecord) {
        boolean shouldIntercept;
        boolean z = DBG;
        if (notificationRecord.sbn.getNotification() == null) {
            if (z) {
                Slog.d("ZenModeExtractor", "skipping empty notification");
            }
            return null;
        }
        ZenModeHelper zenModeHelper = this.mZenModeHelper;
        if (zenModeHelper == null) {
            if (z) {
                Slog.d("ZenModeExtractor", "skipping - no zen info available");
            }
            return null;
        }
        synchronized (zenModeHelper.mConfigLock) {
            shouldIntercept =
                    zenModeHelper.mFiltering.shouldIntercept(
                            zenModeHelper.mZenMode,
                            zenModeHelper.mConsolidatedPolicy,
                            notificationRecord);
        }
        notificationRecord.mIntercept = shouldIntercept;
        notificationRecord.mInterceptSet = true;
        if (shouldIntercept) {
            notificationRecord.mSuppressedVisualEffects =
                    this.mZenModeHelper.mConsolidatedPolicy.copy().suppressedVisualEffects;
        } else {
            notificationRecord.mSuppressedVisualEffects = 0;
        }
        return null;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setConfig(RankingConfig rankingConfig) {}

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setZenHelper(ZenModeHelper zenModeHelper) {
        this.mZenModeHelper = zenModeHelper;
    }
}
