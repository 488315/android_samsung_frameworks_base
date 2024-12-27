package com.android.server.notification;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.notification.NotificationManagerService;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class NotificationUsageStats {
    public final Context mContext;
    public final AnonymousClass1 mHandler;
    public final Map mStats = new HashMap();
    public final ArrayDeque mStatsArrays = new ArrayDeque();
    public final ArraySet mStatExpiredkeys = new ArraySet();
    public long mLastEmitTime = SystemClock.elapsedRealtime();

    public final class AggregatedStats {
        public final AlertRateLimiter alertRate;
        public final RateEstimator enqueueRate;
        public final ImportanceHistogram finalImportance;
        public final String key;
        public final Context mContext;
        public final long mCreated = SystemClock.elapsedRealtime();
        public long mLastAccessTime;
        public AggregatedStats mPrevious;
        public final ImportanceHistogram noisyImportance;
        public int numAlertViolations;
        public int numAutoCancel;
        public int numBlocked;
        public int numEnqueuedByApp;
        public int numForegroundService;
        public int numImagesRemoved;
        public int numInterrupt;
        public int numOngoing;
        public int numPeopleCacheHit;
        public int numPeopleCacheMiss;
        public int numPostedByApp;
        public int numPrivate;
        public int numQuotaViolations;
        public int numRateViolations;
        public int numRemovedByApp;
        public int numSecret;
        public int numSuspendedByAdmin;
        public int numTooOld;
        public int numUndecoratedRemoteViews;
        public int numUpdatedByApp;
        public int numUserInitiatedJob;
        public int numWithActions;
        public int numWithBigPicture;
        public int numWithBigText;
        public int numWithInbox;
        public int numWithInfoText;
        public int numWithLargeIcon;
        public int numWithMediaSession;
        public int numWithStaredPeople;
        public int numWithSubText;
        public int numWithText;
        public int numWithTitle;
        public int numWithValidPeople;
        public final ImportanceHistogram quietImportance;

        public AggregatedStats(Context context, String str) {
            this.key = str;
            this.mContext = context;
            this.noisyImportance = new ImportanceHistogram(context, "note_imp_noisy_");
            this.quietImportance = new ImportanceHistogram(context, "note_imp_quiet_");
            this.finalImportance = new ImportanceHistogram(context, "note_importance_");
            RateEstimator rateEstimator = new RateEstimator();
            rateEstimator.mInterarrivalTime = 1.0d;
            this.enqueueRate = rateEstimator;
            AlertRateLimiter alertRateLimiter = new AlertRateLimiter();
            alertRateLimiter.mLastNotificationMillis = 0L;
            this.alertRate = alertRateLimiter;
        }

        public static void maybePut(JSONObject jSONObject, String str, int i) {
            if (i > 0) {
                jSONObject.put(str, i);
            }
        }

        public final void countApiUse(NotificationRecord notificationRecord) {
            Notification notification = notificationRecord.sbn.getNotification();
            if (notification.actions != null) {
                this.numWithActions++;
            }
            int i = notification.flags;
            if ((i & 64) != 0) {
                this.numForegroundService++;
            }
            if ((32768 & i) != 0) {
                this.numUserInitiatedJob++;
            }
            if ((i & 2) != 0) {
                this.numOngoing++;
            }
            if ((i & 16) != 0) {
                this.numAutoCancel++;
            }
            int i2 = notification.defaults;
            if ((i2 & 1) != 0 || (i2 & 2) != 0 || notification.sound != null || notification.vibrate != null) {
                this.numInterrupt++;
            }
            int i3 = notification.visibility;
            if (i3 == -1) {
                this.numSecret++;
            } else if (i3 == 0) {
                this.numPrivate++;
            }
            SingleNotificationStats singleNotificationStats = notificationRecord.stats;
            if (singleNotificationStats.isNoisy) {
                this.noisyImportance.increment(singleNotificationStats.requestedImportance);
            } else {
                this.quietImportance.increment(singleNotificationStats.requestedImportance);
            }
            this.finalImportance.increment(notificationRecord.mImportance);
            Set<String> keySet = notification.extras.keySet();
            if (keySet.contains("android.bigText")) {
                this.numWithBigText++;
            }
            if (keySet.contains("android.picture")) {
                this.numWithBigPicture++;
            }
            if (keySet.contains("android.largeIcon")) {
                this.numWithLargeIcon++;
            }
            if (keySet.contains("android.textLines")) {
                this.numWithInbox++;
            }
            if (keySet.contains("android.mediaSession")) {
                this.numWithMediaSession++;
            }
            if (keySet.contains("android.title") && !TextUtils.isEmpty(notification.extras.getCharSequence("android.title"))) {
                this.numWithTitle++;
            }
            if (keySet.contains("android.text") && !TextUtils.isEmpty(notification.extras.getCharSequence("android.text"))) {
                this.numWithText++;
            }
            if (keySet.contains("android.subText") && !TextUtils.isEmpty(notification.extras.getCharSequence("android.subText"))) {
                this.numWithSubText++;
            }
            if (!keySet.contains("android.infoText") || TextUtils.isEmpty(notification.extras.getCharSequence("android.infoText"))) {
                return;
            }
            this.numWithInfoText++;
        }

        public final JSONObject dumpJson() {
            AggregatedStats aggregatedStats = this.mPrevious;
            String str = this.key;
            if (aggregatedStats == null) {
                this.mPrevious = new AggregatedStats(this.mContext, str);
            }
            AggregatedStats aggregatedStats2 = this.mPrevious;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("key", str);
            jSONObject.put("duration", SystemClock.elapsedRealtime() - this.mCreated);
            maybePut(jSONObject, "numEnqueuedByApp", this.numEnqueuedByApp);
            maybePut(jSONObject, "numPostedByApp", this.numPostedByApp);
            maybePut(jSONObject, "numUpdatedByApp", this.numUpdatedByApp);
            maybePut(jSONObject, "numRemovedByApp", this.numRemovedByApp);
            maybePut(jSONObject, "numPeopleCacheHit", this.numPeopleCacheHit);
            maybePut(jSONObject, "numPeopleCacheMiss", this.numPeopleCacheMiss);
            maybePut(jSONObject, "numWithStaredPeople", this.numWithStaredPeople);
            maybePut(jSONObject, "numWithValidPeople", this.numWithValidPeople);
            maybePut(jSONObject, "numBlocked", this.numBlocked);
            maybePut(jSONObject, "numSuspendedByAdmin", this.numSuspendedByAdmin);
            maybePut(jSONObject, "numWithActions", this.numWithActions);
            maybePut(jSONObject, "numPrivate", this.numPrivate);
            maybePut(jSONObject, "numSecret", this.numSecret);
            maybePut(jSONObject, "numInterrupt", this.numInterrupt);
            maybePut(jSONObject, "numWithBigText", this.numWithBigText);
            maybePut(jSONObject, "numWithBigPicture", this.numWithBigPicture);
            maybePut(jSONObject, "numForegroundService", this.numForegroundService);
            maybePut(jSONObject, "numUserInitiatedJob", this.numUserInitiatedJob);
            maybePut(jSONObject, "numOngoing", this.numOngoing);
            maybePut(jSONObject, "numAutoCancel", this.numAutoCancel);
            maybePut(jSONObject, "numWithLargeIcon", this.numWithLargeIcon);
            maybePut(jSONObject, "numWithInbox", this.numWithInbox);
            maybePut(jSONObject, "numWithMediaSession", this.numWithMediaSession);
            maybePut(jSONObject, "numWithTitle", this.numWithTitle);
            maybePut(jSONObject, "numWithText", this.numWithText);
            maybePut(jSONObject, "numWithSubText", this.numWithSubText);
            maybePut(jSONObject, "numWithInfoText", this.numWithInfoText);
            maybePut(jSONObject, "numRateViolations", this.numRateViolations);
            maybePut(jSONObject, "numQuotaLViolations", this.numQuotaViolations);
            long elapsedRealtime = SystemClock.elapsedRealtime();
            RateEstimator rateEstimator = this.enqueueRate;
            double interarrivalEstimate = rateEstimator.mLastEventTime == null ? FullScreenMagnificationGestureHandler.MAX_SCALE : (float) (1.0d / rateEstimator.getInterarrivalEstimate(elapsedRealtime));
            if (interarrivalEstimate > 0.0d) {
                jSONObject.put("notificationEnqueueRate", interarrivalEstimate);
            }
            maybePut(jSONObject, "numAlertViolations", this.numAlertViolations);
            maybePut(jSONObject, "numImagesRemoved", this.numImagesRemoved);
            maybePut(jSONObject, "numTooOld", this.numTooOld);
            ImportanceHistogram importanceHistogram = aggregatedStats2.noisyImportance;
            ImportanceHistogram importanceHistogram2 = this.noisyImportance;
            importanceHistogram2.getClass();
            jSONObject.put(importanceHistogram2.mPrefix, new JSONArray(importanceHistogram2.mCount));
            ImportanceHistogram importanceHistogram3 = this.quietImportance;
            importanceHistogram3.getClass();
            jSONObject.put(importanceHistogram3.mPrefix, new JSONArray(importanceHistogram3.mCount));
            ImportanceHistogram importanceHistogram4 = this.finalImportance;
            importanceHistogram4.getClass();
            jSONObject.put(importanceHistogram4.mPrefix, new JSONArray(importanceHistogram4.mCount));
            return jSONObject;
        }

        public final void emit() {
            if (this.mPrevious == null) {
                this.mPrevious = new AggregatedStats(this.mContext, this.key);
            }
            AggregatedStats aggregatedStats = this.mPrevious;
            maybeCount(this.numEnqueuedByApp - aggregatedStats.numEnqueuedByApp, "note_enqueued");
            maybeCount(this.numPostedByApp - aggregatedStats.numPostedByApp, "note_post");
            maybeCount(this.numUpdatedByApp - aggregatedStats.numUpdatedByApp, "note_update");
            maybeCount(this.numRemovedByApp - aggregatedStats.numRemovedByApp, "note_remove");
            maybeCount(this.numWithValidPeople - aggregatedStats.numWithValidPeople, "note_with_people");
            maybeCount(this.numWithStaredPeople - aggregatedStats.numWithStaredPeople, "note_with_stars");
            maybeCount(this.numPeopleCacheHit - aggregatedStats.numPeopleCacheHit, "people_cache_hit");
            maybeCount(this.numPeopleCacheMiss - aggregatedStats.numPeopleCacheMiss, "people_cache_miss");
            maybeCount(this.numBlocked - aggregatedStats.numBlocked, "note_blocked");
            maybeCount(this.numSuspendedByAdmin - aggregatedStats.numSuspendedByAdmin, "note_suspended");
            maybeCount(this.numWithActions - aggregatedStats.numWithActions, "note_with_actions");
            maybeCount(this.numPrivate - aggregatedStats.numPrivate, "note_private");
            maybeCount(this.numSecret - aggregatedStats.numSecret, "note_secret");
            maybeCount(this.numInterrupt - aggregatedStats.numInterrupt, "note_interupt");
            maybeCount(this.numWithBigText - aggregatedStats.numWithBigText, "note_big_text");
            maybeCount(this.numWithBigPicture - aggregatedStats.numWithBigPicture, "note_big_pic");
            maybeCount(this.numForegroundService - aggregatedStats.numForegroundService, "note_fg");
            maybeCount(this.numUserInitiatedJob - aggregatedStats.numUserInitiatedJob, "note_uij");
            maybeCount(this.numOngoing - aggregatedStats.numOngoing, "note_ongoing");
            maybeCount(this.numAutoCancel - aggregatedStats.numAutoCancel, "note_auto");
            maybeCount(this.numWithLargeIcon - aggregatedStats.numWithLargeIcon, "note_large_icon");
            maybeCount(this.numWithInbox - aggregatedStats.numWithInbox, "note_inbox");
            maybeCount(this.numWithMediaSession - aggregatedStats.numWithMediaSession, "note_media");
            maybeCount(this.numWithTitle - aggregatedStats.numWithTitle, "note_title");
            maybeCount(this.numWithText - aggregatedStats.numWithText, "note_text");
            maybeCount(this.numWithSubText - aggregatedStats.numWithSubText, "note_sub_text");
            maybeCount(this.numWithInfoText - aggregatedStats.numWithInfoText, "note_info_text");
            maybeCount(this.numRateViolations - aggregatedStats.numRateViolations, "note_over_rate");
            maybeCount(this.numAlertViolations - aggregatedStats.numAlertViolations, "note_over_alert_rate");
            maybeCount(this.numQuotaViolations - aggregatedStats.numQuotaViolations, "note_over_quota");
            maybeCount(this.numImagesRemoved - aggregatedStats.numImagesRemoved, "note_images_removed");
            maybeCount(this.numTooOld - aggregatedStats.numTooOld, "not_too_old");
            ImportanceHistogram importanceHistogram = this.noisyImportance;
            ImportanceHistogram importanceHistogram2 = aggregatedStats.noisyImportance;
            importanceHistogram.maybeCount(importanceHistogram2);
            ImportanceHistogram importanceHistogram3 = this.quietImportance;
            ImportanceHistogram importanceHistogram4 = aggregatedStats.quietImportance;
            importanceHistogram3.maybeCount(importanceHistogram4);
            ImportanceHistogram importanceHistogram5 = this.finalImportance;
            ImportanceHistogram importanceHistogram6 = aggregatedStats.finalImportance;
            importanceHistogram5.maybeCount(importanceHistogram6);
            aggregatedStats.numEnqueuedByApp = this.numEnqueuedByApp;
            aggregatedStats.numPostedByApp = this.numPostedByApp;
            aggregatedStats.numUpdatedByApp = this.numUpdatedByApp;
            aggregatedStats.numRemovedByApp = this.numRemovedByApp;
            aggregatedStats.numPeopleCacheHit = this.numPeopleCacheHit;
            aggregatedStats.numPeopleCacheMiss = this.numPeopleCacheMiss;
            aggregatedStats.numWithStaredPeople = this.numWithStaredPeople;
            aggregatedStats.numWithValidPeople = this.numWithValidPeople;
            aggregatedStats.numBlocked = this.numBlocked;
            aggregatedStats.numSuspendedByAdmin = this.numSuspendedByAdmin;
            aggregatedStats.numWithActions = this.numWithActions;
            aggregatedStats.numPrivate = this.numPrivate;
            aggregatedStats.numSecret = this.numSecret;
            aggregatedStats.numInterrupt = this.numInterrupt;
            aggregatedStats.numWithBigText = this.numWithBigText;
            aggregatedStats.numWithBigPicture = this.numWithBigPicture;
            aggregatedStats.numForegroundService = this.numForegroundService;
            aggregatedStats.numUserInitiatedJob = this.numUserInitiatedJob;
            aggregatedStats.numOngoing = this.numOngoing;
            aggregatedStats.numAutoCancel = this.numAutoCancel;
            aggregatedStats.numWithLargeIcon = this.numWithLargeIcon;
            aggregatedStats.numWithInbox = this.numWithInbox;
            aggregatedStats.numWithMediaSession = this.numWithMediaSession;
            aggregatedStats.numWithTitle = this.numWithTitle;
            aggregatedStats.numWithText = this.numWithText;
            aggregatedStats.numWithSubText = this.numWithSubText;
            aggregatedStats.numWithInfoText = this.numWithInfoText;
            aggregatedStats.numRateViolations = this.numRateViolations;
            aggregatedStats.numAlertViolations = this.numAlertViolations;
            aggregatedStats.numQuotaViolations = this.numQuotaViolations;
            aggregatedStats.numImagesRemoved = this.numImagesRemoved;
            aggregatedStats.numTooOld = this.numTooOld;
            importanceHistogram.update(importanceHistogram2);
            importanceHistogram3.update(importanceHistogram4);
            importanceHistogram5.update(importanceHistogram6);
        }

        public final void maybeCount(int i, String str) {
            if (i > 0) {
                MetricsLogger.count(this.mContext, str, i);
            }
        }

        public final String toString() {
            return toStringWithIndent("");
        }

        public final String toStringWithIndent(String str) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "AggregatedStats{\n");
            String concat = str.concat("  ");
            m.append(concat);
            m.append("key='");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(m, this.key, "',\n", concat, "numEnqueuedByApp=");
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numEnqueuedByApp, ",\n", concat, "numPostedByApp=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numPostedByApp, ",\n", concat, "numUpdatedByApp=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numUpdatedByApp, ",\n", concat, "numRemovedByApp=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numRemovedByApp, ",\n", concat, "numPeopleCacheHit=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numPeopleCacheHit, ",\n", concat, "numWithStaredPeople=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numWithStaredPeople, ",\n", concat, "numWithValidPeople=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numWithValidPeople, ",\n", concat, "numPeopleCacheMiss=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numPeopleCacheMiss, ",\n", concat, "numBlocked=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numBlocked, ",\n", concat, "numSuspendedByAdmin=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numSuspendedByAdmin, ",\n", concat, "numWithActions=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numWithActions, ",\n", concat, "numPrivate=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numPrivate, ",\n", concat, "numSecret=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numSecret, ",\n", concat, "numInterrupt=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numInterrupt, ",\n", concat, "numWithBigText=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numWithBigText, ",\n", concat, "numWithBigPicture=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numWithBigPicture, "\n", concat, "numForegroundService=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numForegroundService, "\n", concat, "numUserInitiatedJob=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numUserInitiatedJob, "\n", concat, "numOngoing=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numOngoing, "\n", concat, "numAutoCancel=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numAutoCancel, "\n", concat, "numWithLargeIcon=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numWithLargeIcon, "\n", concat, "numWithInbox=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numWithInbox, "\n", concat, "numWithMediaSession=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numWithMediaSession, "\n", concat, "numWithTitle=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numWithTitle, "\n", concat, "numWithText=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numWithText, "\n", concat, "numWithSubText=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numWithSubText, "\n", concat, "numWithInfoText=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numWithInfoText, "\n", concat, "numRateViolations=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numRateViolations, "\n", concat, "numAlertViolations=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numAlertViolations, "\n", concat, "numQuotaViolations=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numQuotaViolations, "\n", concat, "numImagesRemoved=", m);
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.numImagesRemoved, "\n", concat, "numTooOld=", m);
            m.append(this.numTooOld);
            m.append("\n");
            m.append(concat);
            m.append(this.noisyImportance.toString());
            m.append("\n");
            m.append(concat);
            m.append(this.quietImportance.toString());
            m.append("\n");
            m.append(concat);
            m.append(this.finalImportance.toString());
            m.append("\n");
            m.append(concat);
            m.append("numUndecorateRVs=");
            m.append(this.numUndecoratedRemoteViews);
            m.append("\n");
            m.append(str);
            m.append("}");
            return m.toString();
        }
    }

    public final class ImportanceHistogram {
        public static final String[] IMPORTANCE_NAMES = {"none", "min", "low", "default", "high", "max"};
        public final Context mContext;
        public final int[] mCount = new int[6];
        public final String[] mCounterNames = new String[6];
        public final String mPrefix;

        public ImportanceHistogram(Context context, String str) {
            this.mContext = context;
            this.mPrefix = str;
            for (int i = 0; i < 6; i++) {
                this.mCounterNames[i] = this.mPrefix + IMPORTANCE_NAMES[i];
            }
        }

        public final void increment(int i) {
            int[] iArr = this.mCount;
            int max = Math.max(0, Math.min(i, iArr.length - 1));
            iArr[max] = iArr[max] + 1;
        }

        public final void maybeCount(ImportanceHistogram importanceHistogram) {
            for (int i = 0; i < 6; i++) {
                int i2 = this.mCount[i] - importanceHistogram.mCount[i];
                if (i2 > 0) {
                    MetricsLogger.count(this.mContext, this.mCounterNames[i], i2);
                }
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mPrefix);
            sb.append(": [");
            for (int i = 0; i < 6; i++) {
                sb.append(this.mCount[i]);
                if (i < 5) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }

        public final void update(ImportanceHistogram importanceHistogram) {
            for (int i = 0; i < 6; i++) {
                this.mCount[i] = importanceHistogram.mCount[i];
            }
        }
    }

    public final class SingleNotificationStats {
        public long airtimeCount;
        public long airtimeExpandedMs;
        public long airtimeMs;
        public long currentAirtimeExpandedStartElapsedMs;
        public long currentAirtimeStartElapsedMs;
        public boolean isExpanded;
        public boolean isNoisy;
        public boolean isVisible;
        public int naturalImportance;
        public long posttimeElapsedMs;
        public long posttimeToDismissMs;
        public long posttimeToFirstAirtimeMs;
        public long posttimeToFirstClickMs;
        public long posttimeToFirstVisibleExpansionMs;
        public int requestedImportance;

        public final void onVisibilityChanged(boolean z) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            boolean z2 = this.isVisible;
            this.isVisible = z;
            if (z) {
                if (this.currentAirtimeStartElapsedMs < 0) {
                    this.airtimeCount++;
                    this.currentAirtimeStartElapsedMs = elapsedRealtime;
                }
                if (this.posttimeToFirstAirtimeMs < 0) {
                    this.posttimeToFirstAirtimeMs = elapsedRealtime - this.posttimeElapsedMs;
                }
            } else {
                long j = this.currentAirtimeStartElapsedMs;
                if (j >= 0) {
                    this.airtimeMs = (elapsedRealtime - j) + this.airtimeMs;
                    this.currentAirtimeStartElapsedMs = -1L;
                }
            }
            if (z2 != z) {
                updateVisiblyExpandedStats();
            }
        }

        public final String toString() {
            return "SingleNotificationStats{posttimeElapsedMs=" + this.posttimeElapsedMs + ", posttimeToFirstClickMs=" + this.posttimeToFirstClickMs + ", posttimeToDismissMs=" + this.posttimeToDismissMs + ", airtimeCount=" + this.airtimeCount + ", airtimeMs=" + this.airtimeMs + ", currentAirtimeStartElapsedMs=" + this.currentAirtimeStartElapsedMs + ", airtimeExpandedMs=" + this.airtimeExpandedMs + ", posttimeToFirstVisibleExpansionMs=" + this.posttimeToFirstVisibleExpansionMs + ", currentAirtimeExpandedStartElapsedMs=" + this.currentAirtimeExpandedStartElapsedMs + ", requestedImportance=" + this.requestedImportance + ", naturalImportance=" + this.naturalImportance + ", isNoisy=" + this.isNoisy + '}';
        }

        public final void updateFrom(SingleNotificationStats singleNotificationStats) {
            this.posttimeElapsedMs = singleNotificationStats.posttimeElapsedMs;
            this.posttimeToFirstClickMs = singleNotificationStats.posttimeToFirstClickMs;
            this.airtimeCount = singleNotificationStats.airtimeCount;
            this.posttimeToFirstAirtimeMs = singleNotificationStats.posttimeToFirstAirtimeMs;
            this.currentAirtimeStartElapsedMs = singleNotificationStats.currentAirtimeStartElapsedMs;
            this.airtimeMs = singleNotificationStats.airtimeMs;
            this.posttimeToFirstVisibleExpansionMs = singleNotificationStats.posttimeToFirstVisibleExpansionMs;
            this.currentAirtimeExpandedStartElapsedMs = singleNotificationStats.currentAirtimeExpandedStartElapsedMs;
            this.airtimeExpandedMs = singleNotificationStats.airtimeExpandedMs;
        }

        public final void updateVisiblyExpandedStats() {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (this.isExpanded && this.isVisible) {
                if (this.currentAirtimeExpandedStartElapsedMs < 0) {
                    this.currentAirtimeExpandedStartElapsedMs = elapsedRealtime;
                }
                if (this.posttimeToFirstVisibleExpansionMs < 0) {
                    this.posttimeToFirstVisibleExpansionMs = elapsedRealtime - this.posttimeElapsedMs;
                    return;
                }
                return;
            }
            long j = this.currentAirtimeExpandedStartElapsedMs;
            if (j >= 0) {
                this.airtimeExpandedMs = (elapsedRealtime - j) + this.airtimeExpandedMs;
                this.currentAirtimeExpandedStartElapsedMs = -1L;
            }
        }
    }

    public NotificationUsageStats(Context context) {
        this.mContext = context;
        ?? r0 = new Handler(context.getMainLooper()) { // from class: com.android.server.notification.NotificationUsageStats.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 1) {
                    Log.wtf("NotificationUsageStats", "Unknown message type: " + message.what);
                    return;
                }
                NotificationUsageStats notificationUsageStats = NotificationUsageStats.this;
                synchronized (notificationUsageStats) {
                    try {
                        notificationUsageStats.getOrCreateAggregatedStatsLocked("__global").emit();
                        notificationUsageStats.mHandler.removeMessages(1);
                        notificationUsageStats.mHandler.sendEmptyMessageDelayed(1, BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
                        for (String str : ((HashMap) notificationUsageStats.mStats).keySet()) {
                            if (((AggregatedStats) ((HashMap) notificationUsageStats.mStats).get(str)).mLastAccessTime < notificationUsageStats.mLastEmitTime) {
                                notificationUsageStats.mStatExpiredkeys.add(str);
                            }
                        }
                        Iterator it = notificationUsageStats.mStatExpiredkeys.iterator();
                        while (it.hasNext()) {
                            ((HashMap) notificationUsageStats.mStats).remove((String) it.next());
                        }
                        notificationUsageStats.mStatExpiredkeys.clear();
                        notificationUsageStats.mLastEmitTime = SystemClock.elapsedRealtime();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mHandler = r0;
        r0.sendEmptyMessageDelayed(1, BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
    }

    public final synchronized void dump(PrintWriter printWriter, NotificationManagerService.DumpFilter dumpFilter) {
        try {
            for (AggregatedStats aggregatedStats : ((HashMap) this.mStats).values()) {
                if (dumpFilter.matches(aggregatedStats.key)) {
                    printWriter.println(aggregatedStats.toStringWithIndent("    "));
                }
            }
            printWriter.println("    mStatsArrays.size(): " + this.mStatsArrays.size());
            printWriter.println("    mStats.size(): " + ((HashMap) this.mStats).size());
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized JSONObject dumpJson(NotificationManagerService.DumpFilter dumpFilter) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            try {
                JSONArray jSONArray = new JSONArray();
                for (AggregatedStats aggregatedStats : ((HashMap) this.mStats).values()) {
                    if (dumpFilter.matches(aggregatedStats.key)) {
                        jSONArray.put(aggregatedStats.dumpJson());
                    }
                }
                jSONObject.put("current", jSONArray);
            } catch (JSONException unused) {
            }
        } catch (Throwable th) {
            throw th;
        }
        return jSONObject;
    }

    public final AggregatedStats[] getAggregatedStatsLocked(String str) {
        AggregatedStats[] aggregatedStatsArr = (AggregatedStats[]) this.mStatsArrays.poll();
        if (aggregatedStatsArr == null) {
            aggregatedStatsArr = new AggregatedStats[2];
        }
        aggregatedStatsArr[0] = getOrCreateAggregatedStatsLocked("__global");
        aggregatedStatsArr[1] = getOrCreateAggregatedStatsLocked(str);
        return aggregatedStatsArr;
    }

    public final AggregatedStats getOrCreateAggregatedStatsLocked(String str) {
        AggregatedStats aggregatedStats = (AggregatedStats) ((HashMap) this.mStats).get(str);
        if (aggregatedStats == null) {
            aggregatedStats = new AggregatedStats(this.mContext, str);
            ((HashMap) this.mStats).put(str, aggregatedStats);
        }
        aggregatedStats.mLastAccessTime = SystemClock.elapsedRealtime();
        return aggregatedStats;
    }

    public final synchronized void registerBlocked(NotificationRecord notificationRecord) {
        try {
            AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(notificationRecord.sbn.getPackageName());
            for (AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                aggregatedStats.numBlocked++;
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void registerClickedByUser(NotificationRecord notificationRecord) {
        MetricsLogger.histogram(this.mContext, "note_click_longevity", ((int) (System.currentTimeMillis() - notificationRecord.mRankingTimeMs)) / 60000);
        SingleNotificationStats singleNotificationStats = notificationRecord.stats;
        if (singleNotificationStats.posttimeToFirstClickMs < 0) {
            singleNotificationStats.posttimeToFirstClickMs = SystemClock.elapsedRealtime() - singleNotificationStats.posttimeElapsedMs;
        }
    }

    public final synchronized void registerImageRemoved(String str) {
        for (AggregatedStats aggregatedStats : getAggregatedStatsLocked(str)) {
            aggregatedStats.numImagesRemoved++;
        }
    }

    public final synchronized void registerOverCountQuota(String str) {
        for (AggregatedStats aggregatedStats : getAggregatedStatsLocked(str)) {
            aggregatedStats.numQuotaViolations++;
        }
    }

    public final synchronized void registerOverRateQuota(String str) {
        for (AggregatedStats aggregatedStats : getAggregatedStatsLocked(str)) {
            aggregatedStats.numRateViolations++;
        }
    }

    public final synchronized void registerPeopleAffinity(NotificationRecord notificationRecord, boolean z, boolean z2, boolean z3) {
        try {
            AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(notificationRecord.sbn.getPackageName());
            for (AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                if (z) {
                    aggregatedStats.numWithValidPeople++;
                }
                if (z2) {
                    aggregatedStats.numWithStaredPeople++;
                }
                if (z3) {
                    aggregatedStats.numPeopleCacheHit++;
                } else {
                    aggregatedStats.numPeopleCacheMiss++;
                }
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void registerPostedByApp(NotificationRecord notificationRecord) {
        boolean z;
        try {
            notificationRecord.stats.posttimeElapsedMs = SystemClock.elapsedRealtime();
            AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(notificationRecord.sbn.getPackageName());
            for (AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                int i = 1;
                aggregatedStats.numPostedByApp++;
                aggregatedStats.countApiUse(notificationRecord);
                int i2 = aggregatedStats.numUndecoratedRemoteViews;
                Notification notification = notificationRecord.sbn.getNotification();
                if (!notification.isStyle(Notification.DecoratedCustomViewStyle.class) && !notification.isStyle(Notification.DecoratedMediaCustomViewStyle.class)) {
                    z = false;
                    if ((notification.contentView != null && notification.bigContentView == null && notification.headsUpContentView == null) || z) {
                        i = 0;
                    }
                    aggregatedStats.numUndecoratedRemoteViews = i2 + i;
                }
                z = true;
                if (notification.contentView != null) {
                }
                aggregatedStats.numUndecoratedRemoteViews = i2 + i;
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void registerSuspendedByAdmin(NotificationRecord notificationRecord) {
        try {
            AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(notificationRecord.sbn.getPackageName());
            for (AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                aggregatedStats.numSuspendedByAdmin++;
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void registerUpdatedByApp(NotificationRecord notificationRecord, NotificationRecord notificationRecord2) {
        try {
            notificationRecord.stats.updateFrom(notificationRecord2.stats);
            AggregatedStats[] aggregatedStatsLocked = getAggregatedStatsLocked(notificationRecord.sbn.getPackageName());
            for (AggregatedStats aggregatedStats : aggregatedStatsLocked) {
                aggregatedStats.numUpdatedByApp++;
                aggregatedStats.countApiUse(notificationRecord);
            }
            releaseAggregatedStatsLocked(aggregatedStatsLocked);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void releaseAggregatedStatsLocked(AggregatedStats[] aggregatedStatsArr) {
        for (int i = 0; i < aggregatedStatsArr.length; i++) {
            aggregatedStatsArr[i] = null;
        }
        this.mStatsArrays.offer(aggregatedStatsArr);
    }

    public final PulledStats remoteViewStats(long j) {
        PulledStats pulledStats = new PulledStats(j);
        for (AggregatedStats aggregatedStats : ((HashMap) this.mStats).values()) {
            if (aggregatedStats.numUndecoratedRemoteViews > 0) {
                ((ArrayList) pulledStats.mUndecoratedPackageNames).add(aggregatedStats.key);
                pulledStats.mTimePeriodEndMs = Math.max(pulledStats.mTimePeriodEndMs, aggregatedStats.mCreated);
            }
        }
        return pulledStats;
    }
}
