package com.android.systemui.statusbar.policy;

import android.util.ArrayMap;
import android.util.Log;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationThrottleHun;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import kotlin.jvm.functions.Function1;

public final /* synthetic */ class BaseHeadsUpManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ BaseHeadsUpManager.HeadsUpEntry f$2;

    public /* synthetic */ BaseHeadsUpManager$$ExternalSyntheticLambda1(BaseHeadsUpManager.HeadsUpEntry headsUpEntry, BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2 baseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2, String str) {
        this.f$2 = headsUpEntry;
        this.f$0 = baseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        long max;
        switch (this.$r8$classId) {
            case 0:
                BaseHeadsUpManager baseHeadsUpManager = (BaseHeadsUpManager) this.f$0;
                NotificationEntry notificationEntry = (NotificationEntry) this.f$1;
                BaseHeadsUpManager.HeadsUpEntry headsUpEntry = this.f$2;
                HeadsUpManagerLogger headsUpManagerLogger = baseHeadsUpManager.mLogger;
                headsUpManagerLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                HeadsUpManagerLogger$logShowNotification$2 headsUpManagerLogger$logShowNotification$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logShowNotification$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("show notification ", ((LogMessage) obj).getStr1());
                    }
                };
                LogBuffer logBuffer = headsUpManagerLogger.buffer;
                LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logShowNotification$2, null);
                ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                logBuffer.commit(obtain);
                ArrayMap arrayMap = baseHeadsUpManager.mHeadsUpEntryMap;
                String str = notificationEntry.mKey;
                BaseHeadsUpManager.HeadsUpEntry headsUpEntry2 = (BaseHeadsUpManager.HeadsUpEntry) arrayMap.get(str);
                if (headsUpEntry2 != null) {
                    headsUpEntry2.reset();
                }
                baseHeadsUpManager.mHeadsUpEntryMap.put(str, headsUpEntry);
                baseHeadsUpManager.onEntryAdded(headsUpEntry);
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.sendAccessibilityEvent(2048);
                }
                notificationEntry.mIsHeadsUpEntry = true;
                baseHeadsUpManager.updateNotificationInternal(str, true);
                notificationEntry.interruption = true;
                break;
            default:
                BaseHeadsUpManager.HeadsUpEntry headsUpEntry3 = this.f$2;
                BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2 baseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2 = (BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2) this.f$0;
                String str2 = (String) this.f$1;
                headsUpEntry3.getClass();
                switch (baseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2.$r8$classId) {
                    case 0:
                        BaseHeadsUpManager.HeadsUpEntry headsUpEntry4 = baseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2.f$0;
                        long calculateFinishTime = headsUpEntry4.calculateFinishTime();
                        long elapsedRealtime = BaseHeadsUpManager.this.mSystemClock.elapsedRealtime();
                        int i = NotificationThrottleHun.$r8$clinit;
                        Flags.notificationAvalancheThrottleHun();
                        max = Math.max(calculateFinishTime - elapsedRealtime, BaseHeadsUpManager.this.mMinimumDisplayTime);
                        break;
                    default:
                        BaseHeadsUpManager.HeadsUpEntry headsUpEntry5 = baseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2.f$0;
                        max = headsUpEntry5.mEarliestRemovalTime - BaseHeadsUpManager.this.mSystemClock.elapsedRealtime();
                        break;
                }
                if (headsUpEntry3.mRemoveRunnable != null) {
                    Runnable runnable = headsUpEntry3.mCancelRemoveRunnable;
                    boolean z = runnable != null;
                    if (z) {
                        runnable.run();
                        headsUpEntry3.mCancelRemoveRunnable = null;
                    }
                    headsUpEntry3.mCancelRemoveRunnable = BaseHeadsUpManager.this.mExecutor.executeDelayed(headsUpEntry3.mRemoveRunnable, max);
                    if (!z) {
                        HeadsUpManagerLogger headsUpManagerLogger2 = BaseHeadsUpManager.this.mLogger;
                        NotificationEntry notificationEntry2 = headsUpEntry3.mEntry;
                        headsUpManagerLogger2.getClass();
                        LogLevel logLevel2 = LogLevel.INFO;
                        HeadsUpManagerLogger$logAutoRemoveScheduled$2 headsUpManagerLogger$logAutoRemoveScheduled$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logAutoRemoveScheduled$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                String str1 = logMessage.getStr1();
                                long long1 = logMessage.getLong1();
                                String str22 = logMessage.getStr2();
                                StringBuilder sb = new StringBuilder("schedule auto remove of ");
                                sb.append(str1);
                                sb.append(" in ");
                                sb.append(long1);
                                return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, " ms reason: ", str22);
                            }
                        };
                        LogBuffer logBuffer2 = headsUpManagerLogger2.buffer;
                        LogMessage obtain2 = logBuffer2.obtain("HeadsUpManager", logLevel2, headsUpManagerLogger$logAutoRemoveScheduled$2, null);
                        String logKey = NotificationUtilsKt.getLogKey(notificationEntry2);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain2;
                        logMessageImpl.str1 = logKey;
                        logMessageImpl.long1 = max;
                        logMessageImpl.str2 = str2;
                        logBuffer2.commit(obtain2);
                        break;
                    } else {
                        HeadsUpManagerLogger headsUpManagerLogger3 = BaseHeadsUpManager.this.mLogger;
                        NotificationEntry notificationEntry3 = headsUpEntry3.mEntry;
                        headsUpManagerLogger3.getClass();
                        LogLevel logLevel3 = LogLevel.INFO;
                        HeadsUpManagerLogger$logAutoRemoveRescheduled$2 headsUpManagerLogger$logAutoRemoveRescheduled$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logAutoRemoveRescheduled$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                String str1 = logMessage.getStr1();
                                long long1 = logMessage.getLong1();
                                String str22 = logMessage.getStr2();
                                StringBuilder sb = new StringBuilder("reschedule auto remove of ");
                                sb.append(str1);
                                sb.append(" in ");
                                sb.append(long1);
                                return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, " ms reason: ", str22);
                            }
                        };
                        LogBuffer logBuffer3 = headsUpManagerLogger3.buffer;
                        LogMessage obtain3 = logBuffer3.obtain("HeadsUpManager", logLevel3, headsUpManagerLogger$logAutoRemoveRescheduled$2, null);
                        String logKey2 = NotificationUtilsKt.getLogKey(notificationEntry3);
                        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain3;
                        logMessageImpl2.str1 = logKey2;
                        logMessageImpl2.long1 = max;
                        logMessageImpl2.str2 = str2;
                        logBuffer3.commit(obtain3);
                        break;
                    }
                } else {
                    Log.wtf("BaseHeadsUpManager", "scheduleAutoRemovalCallback with no callback set");
                    break;
                }
        }
    }

    public /* synthetic */ BaseHeadsUpManager$$ExternalSyntheticLambda1(BaseHeadsUpManager baseHeadsUpManager, NotificationEntry notificationEntry, BaseHeadsUpManager.HeadsUpEntry headsUpEntry) {
        this.f$0 = baseHeadsUpManager;
        this.f$1 = notificationEntry;
        this.f$2 = headsUpEntry;
    }
}
