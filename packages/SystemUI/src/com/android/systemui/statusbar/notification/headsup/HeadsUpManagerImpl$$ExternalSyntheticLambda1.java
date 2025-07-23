package com.android.systemui.statusbar.notification.headsup;

import android.util.ArrayMap;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.shared.NotificationThrottleHun;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpManagerImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ HeadsUpManagerImpl f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ HeadsUpManagerImpl.HeadsUpEntry f$3;
    public final /* synthetic */ Object f$4;

    public /* synthetic */ HeadsUpManagerImpl$$ExternalSyntheticLambda1(HeadsUpManagerImpl headsUpManagerImpl, NotificationEntry notificationEntry, boolean z, HeadsUpManagerImpl.HeadsUpEntry headsUpEntry, PinnedStatus pinnedStatus) {
        this.f$0 = headsUpManagerImpl;
        this.f$1 = notificationEntry;
        this.f$2 = z;
        this.f$3 = headsUpEntry;
        this.f$4 = pinnedStatus;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                HeadsUpManagerImpl headsUpManagerImpl = this.f$0;
                NotificationEntry notificationEntry = (NotificationEntry) this.f$1;
                boolean z = this.f$2;
                HeadsUpManagerImpl.HeadsUpEntry headsUpEntry = this.f$3;
                PinnedStatus pinnedStatus = (PinnedStatus) this.f$4;
                HeadsUpManagerLogger headsUpManagerLogger = headsUpManagerImpl.mLogger;
                headsUpManagerLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer = headsUpManagerLogger.buffer;
                LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                logMessageImpl.bool1 = z;
                logBuffer.commit(obtain);
                HeadsUpManagerImpl.HeadsUpEntry headsUpEntry2 = headsUpManagerImpl.mHeadsUpEntryMap.get(notificationEntry.mKey);
                if (headsUpEntry2 != null) {
                    headsUpEntry2.cancelAutoRemovalCallbacks("reset by edge");
                }
                ArrayMap<String, HeadsUpManagerImpl.HeadsUpEntry> arrayMap = headsUpManagerImpl.mHeadsUpEntryMap;
                String str = notificationEntry.mKey;
                arrayMap.put(str, headsUpEntry);
                headsUpManagerImpl.onEntryAdded(headsUpEntry, pinnedStatus);
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.sendAccessibilityEvent(2048);
                }
                int i = NotificationBundleUi.$r8$clinit;
                notificationEntry.mIsHeadsUpEntry = true;
                headsUpManagerImpl.updateNotificationInternal(str, pinnedStatus);
                notificationEntry.interruption = true;
                break;
            default:
                HeadsUpManagerImpl headsUpManagerImpl2 = this.f$0;
                String str2 = (String) this.f$1;
                String str3 = (String) this.f$4;
                boolean z2 = this.f$2;
                HeadsUpManagerImpl.HeadsUpEntry headsUpEntry3 = this.f$3;
                HeadsUpManagerLogger headsUpManagerLogger2 = headsUpManagerImpl2.mLogger;
                headsUpManagerLogger2.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                HeadsUpManagerLogger$$ExternalSyntheticLambda11 headsUpManagerLogger$$ExternalSyntheticLambda11 = new HeadsUpManagerLogger$$ExternalSyntheticLambda11(z2, 1);
                LogBuffer logBuffer2 = headsUpManagerLogger2.buffer;
                LogMessage obtain2 = logBuffer2.obtain("HeadsUpManager", logLevel2, headsUpManagerLogger$$ExternalSyntheticLambda11, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
                logMessageImpl2.str1 = NotificationUtils.logKey(str2);
                logMessageImpl2.str2 = str3;
                logMessageImpl2.bool1 = z2;
                logBuffer2.commit(obtain2);
                if (headsUpEntry3 != null) {
                    NotificationEntry notificationEntry2 = headsUpEntry3.mEntry;
                    Objects.requireNonNull(notificationEntry2);
                    if (!notificationEntry2.mExpandAnimationRunning) {
                        notificationEntry2.mIsDemoted = true;
                        headsUpManagerImpl2.mHeadsUpEntryMap.remove(str2);
                        headsUpManagerImpl2.onEntryRemoved(headsUpEntry3, str3);
                        ExpandableNotificationRow expandableNotificationRow2 = notificationEntry2.row;
                        if (expandableNotificationRow2 != null) {
                            expandableNotificationRow2.sendAccessibilityEvent(2048);
                        }
                        NotificationThrottleHun notificationThrottleHun = NotificationThrottleHun.INSTANCE;
                        headsUpEntry3.cancelAutoRemovalCallbacks("removeEntry");
                        break;
                    }
                }
                break;
        }
    }

    public /* synthetic */ HeadsUpManagerImpl$$ExternalSyntheticLambda1(HeadsUpManagerImpl headsUpManagerImpl, String str, String str2, boolean z, HeadsUpManagerImpl.HeadsUpEntry headsUpEntry) {
        this.f$0 = headsUpManagerImpl;
        this.f$1 = str;
        this.f$4 = str2;
        this.f$2 = z;
        this.f$3 = headsUpEntry;
    }
}
