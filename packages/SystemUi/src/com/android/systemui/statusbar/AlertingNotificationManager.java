package com.android.systemui.statusbar;

import android.os.Handler;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.ArraySet;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.HeadsUpManagerLogger;
import java.util.Iterator;
import java.util.stream.Stream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class AlertingNotificationManager {
    public int mAutoDismissNotificationDecay;
    public Handler mHandler;
    public final HeadsUpManagerLogger mLogger;
    public int mMinimumDisplayTime;
    public int mStickyDisplayTime;
    public final Clock mClock = new Clock();
    public final ArrayMap mAlertEntries = new ArrayMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class AlertEntry implements Comparable {
        public long mEarliestRemovaltime;
        public NotificationEntry mEntry;
        public long mPostTime;
        public Runnable mRemoveAlertRunnable;

        public AlertEntry() {
        }

        public long calculateFinishTime() {
            return 0L;
        }

        public long calculatePostTime() {
            AlertingNotificationManager.this.mClock.getClass();
            return SystemClock.elapsedRealtime();
        }

        public boolean isSticky() {
            return false;
        }

        public final void removeAutoRemovalCallbacks() {
            Runnable runnable = this.mRemoveAlertRunnable;
            if (runnable != null) {
                AlertingNotificationManager.this.mHandler.removeCallbacks(runnable);
            }
        }

        public void reset() {
            this.mEntry = null;
            removeAutoRemovalCallbacks();
            this.mRemoveAlertRunnable = null;
        }

        public void setEntry(final NotificationEntry notificationEntry) {
            setEntry(notificationEntry, new Runnable() { // from class: com.android.systemui.statusbar.AlertingNotificationManager$AlertEntry$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AlertingNotificationManager.this.removeAlertEntry(notificationEntry.mKey);
                }
            });
        }

        public void updateEntry(boolean z) {
            AlertingNotificationManager.this.mLogger.logUpdateEntry(this.mEntry, z);
            AlertingNotificationManager.this.mClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.mEarliestRemovaltime = AlertingNotificationManager.this.mMinimumDisplayTime + elapsedRealtime;
            if (z) {
                this.mPostTime = Math.max(this.mPostTime, elapsedRealtime);
            }
            removeAutoRemovalCallbacks();
            if (isSticky()) {
                return;
            }
            AlertingNotificationManager.this.mHandler.postDelayed(this.mRemoveAlertRunnable, Math.max(calculateFinishTime() - elapsedRealtime, AlertingNotificationManager.this.mMinimumDisplayTime));
        }

        @Override // java.lang.Comparable
        public int compareTo(AlertEntry alertEntry) {
            long j = this.mPostTime;
            long j2 = alertEntry.mPostTime;
            if (j < j2) {
                return 1;
            }
            if (j == j2) {
                return this.mEntry.mKey.compareTo(alertEntry.mEntry.mKey);
            }
            return -1;
        }

        public final void setEntry(NotificationEntry notificationEntry, Runnable runnable) {
            AlertEntry alertEntry = (AlertEntry) AlertingNotificationManager.this.mAlertEntries.get(notificationEntry.mKey);
            if (alertEntry != null) {
                alertEntry.removeAutoRemovalCallbacks();
                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("setEntry remove old mRemoveAlertRunnable : "), notificationEntry.mKey, "AlertNotifManager");
            }
            this.mEntry = notificationEntry;
            this.mRemoveAlertRunnable = runnable;
            this.mPostTime = calculatePostTime();
            updateEntry(true);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Clock {
    }

    public AlertingNotificationManager(HeadsUpManagerLogger headsUpManagerLogger, Handler handler) {
        this.mLogger = headsUpManagerLogger;
        this.mHandler = handler;
    }

    public boolean canRemoveImmediately(String str) {
        AlertEntry alertEntry = (AlertEntry) this.mAlertEntries.get(str);
        if (alertEntry == null) {
            return true;
        }
        long j = alertEntry.mEarliestRemovaltime;
        AlertingNotificationManager.this.mClock.getClass();
        if (j < SystemClock.elapsedRealtime()) {
            return true;
        }
        ExpandableNotificationRow expandableNotificationRow = alertEntry.mEntry.row;
        return expandableNotificationRow != null && expandableNotificationRow.mDismissed;
    }

    public final Stream getAllEntries() {
        return this.mAlertEntries.values().stream().map(new AlertingNotificationManager$$ExternalSyntheticLambda0());
    }

    public final boolean isAlerting(String str) {
        return this.mAlertEntries.containsKey(str);
    }

    public abstract void onAlertEntryRemoved(AlertEntry alertEntry);

    public final void releaseAllImmediately() {
        this.mLogger.logReleaseAllImmediately();
        Iterator it = new ArraySet(this.mAlertEntries.keySet()).iterator();
        while (it.hasNext()) {
            removeAlertEntry((String) it.next());
        }
    }

    public final void removeAlertEntry(String str) {
        ArrayMap arrayMap = this.mAlertEntries;
        AlertEntry alertEntry = (AlertEntry) arrayMap.get(str);
        if (alertEntry == null) {
            return;
        }
        NotificationEntry notificationEntry = alertEntry.mEntry;
        if (notificationEntry == null || !notificationEntry.mExpandAnimationRunning) {
            notificationEntry.mIsDemoted = true;
            arrayMap.remove(str);
            onAlertEntryRemoved(alertEntry);
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            if (expandableNotificationRow != null) {
                expandableNotificationRow.sendAccessibilityEvent(2048);
            }
            alertEntry.reset();
        }
    }

    public final boolean removeNotification(String str, boolean z) {
        this.mLogger.logRemoveNotification(str, z);
        AlertEntry alertEntry = (AlertEntry) this.mAlertEntries.get(str);
        if (alertEntry == null) {
            return true;
        }
        if (z || canRemoveImmediately(str)) {
            removeAlertEntry(str);
            return true;
        }
        if (alertEntry.mRemoveAlertRunnable == null) {
            return false;
        }
        alertEntry.removeAutoRemovalCallbacks();
        long j = alertEntry.mEarliestRemovaltime;
        AlertingNotificationManager.this.mClock.getClass();
        AlertingNotificationManager.this.mHandler.postDelayed(alertEntry.mRemoveAlertRunnable, j - SystemClock.elapsedRealtime());
        return false;
    }
}
