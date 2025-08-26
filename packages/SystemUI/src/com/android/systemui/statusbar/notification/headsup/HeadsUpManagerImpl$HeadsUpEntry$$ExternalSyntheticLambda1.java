package com.android.systemui.statusbar.notification.headsup;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationThrottleHun;

/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HeadsUpManagerImpl.HeadsUpEntry f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda1(HeadsUpManagerImpl.HeadsUpEntry headsUpEntry, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = headsUpEntry;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ExpandableNotificationRow expandableNotificationRow;
        switch (this.$r8$classId) {
            case 0:
                HeadsUpManagerImpl.HeadsUpEntry headsUpEntry = this.f$0;
                NotificationEntry notificationEntry = (NotificationEntry) this.f$1;
                NotificationThrottleHun notificationThrottleHun = NotificationThrottleHun.INSTANCE;
                if (!((Boolean) HeadsUpManagerImpl.this.mTrackingHeadsUp.getValue()).booleanValue()) {
                    if (HeadsUpManagerImpl.this.mVisualStabilityProvider.isReorderingAllowed || ((expandableNotificationRow = notificationEntry.row) != null && expandableNotificationRow.showingPulsing())) {
                        HeadsUpManagerImpl.this.removeEntry(notificationEntry.mKey, "createRemoveRunnable");
                        break;
                    }
                } else {
                    HeadsUpManagerImpl.this.mEntriesToRemoveAfterExpand.add(notificationEntry);
                    HeadsUpManagerLogger headsUpManagerLogger = HeadsUpManagerImpl.this.mLogger;
                    headsUpManagerLogger.getClass();
                    LogLevel logLevel = LogLevel.VERBOSE;
                    HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(12);
                    LogBuffer logBuffer = headsUpManagerLogger.buffer;
                    LogMessage logMessageObtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
                    ((LogMessageImpl) logMessageObtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                    logBuffer.commit(logMessageObtain);
                    break;
                }
                break;
            default:
                HeadsUpManagerImpl.HeadsUpEntry headsUpEntry2 = this.f$0;
                String str = (String) this.f$1;
                Runnable runnable = headsUpEntry2.mCancelRemoveRunnable;
                boolean z = runnable != null;
                if (z) {
                    runnable.run();
                    headsUpEntry2.mCancelRemoveRunnable = null;
                }
                if (z) {
                    HeadsUpManagerLogger headsUpManagerLogger2 = HeadsUpManagerImpl.this.mLogger;
                    NotificationEntry notificationEntry2 = headsUpEntry2.mEntry;
                    headsUpManagerLogger2.getClass();
                    LogLevel logLevel2 = LogLevel.INFO;
                    HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda02 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(14);
                    LogBuffer logBuffer2 = headsUpManagerLogger2.buffer;
                    LogMessage logMessageObtain2 = logBuffer2.obtain("HeadsUpManager", logLevel2, headsUpManagerLogger$$ExternalSyntheticLambda02, null);
                    String strLogKey = notificationEntry2 != null ? NotificationUtils.logKey(notificationEntry2) : null;
                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain2;
                    logMessageImpl.str1 = strLogKey;
                    logMessageImpl.str2 = str;
                    logBuffer2.commit(logMessageObtain2);
                    break;
                }
                break;
        }
    }
}
