package com.android.server.notification;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class NotificationIntrusivenessExtractor implements NotificationSignalExtractor {
    public static final boolean DBG = Log.isLoggable("IntrusivenessExtractor", 3);
    static final long HANG_TIME_MS = 10000;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationIntrusivenessExtractor$1, reason: invalid class name */
    public final class AnonymousClass1 extends RankingReconsideration {
        @Override // com.android.server.notification.RankingReconsideration
        public final void applyChangesLocked(NotificationRecord notificationRecord) {
            if (System.currentTimeMillis() - notificationRecord.mLastIntrusive >= NotificationIntrusivenessExtractor.HANG_TIME_MS) {
                notificationRecord.mRecentlyIntrusive = false;
            }
        }

        @Override // com.android.server.notification.RankingReconsideration
        public final void work() {
        }
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void initialize(Context context, NotificationUsageStats notificationUsageStats) {
        if (DBG) {
            Slog.d("IntrusivenessExtractor", "Initializing  " + getClass().getSimpleName() + ".");
        }
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final RankingReconsideration process(NotificationRecord notificationRecord) {
        if (notificationRecord.sbn.getNotification() == null) {
            if (DBG) {
                Slog.d("IntrusivenessExtractor", "skipping empty notification");
            }
            return null;
        }
        if (((int) (System.currentTimeMillis() - notificationRecord.mUpdateTimeMs)) < HANG_TIME_MS && notificationRecord.mImportance >= 3) {
            Uri uri = notificationRecord.mSound;
            if (uri != null && uri != Uri.EMPTY) {
                notificationRecord.mRecentlyIntrusive = true;
                notificationRecord.mLastIntrusive = System.currentTimeMillis();
            }
            if (notificationRecord.mVibration != null) {
                notificationRecord.mRecentlyIntrusive = true;
                notificationRecord.mLastIntrusive = System.currentTimeMillis();
            }
            if (notificationRecord.sbn.getNotification().fullScreenIntent != null) {
                notificationRecord.mRecentlyIntrusive = true;
                notificationRecord.mLastIntrusive = System.currentTimeMillis();
            }
        }
        if (notificationRecord.mRecentlyIntrusive) {
            return new AnonymousClass1(HANG_TIME_MS, notificationRecord.sbn.getKey());
        }
        return null;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setConfig(RankingConfig rankingConfig) {
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setZenHelper(ZenModeHelper zenModeHelper) {
    }
}
