package com.android.systemui.statusbar.notification.headsup;

import android.util.Log;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpManagerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 2;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ HeadsUpManagerImpl$$ExternalSyntheticLambda0(HeadsUpManagerImpl.HeadsUpEntry headsUpEntry, HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0 headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0, String str) {
        this.f$2 = headsUpEntry;
        this.f$0 = headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ExpandableNotificationRow expandableNotificationRow;
        ExpandableNotificationRow expandableNotificationRow2;
        long elapsedRealtime;
        switch (this.$r8$classId) {
            case 0:
                HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) this.f$0;
                String str = this.f$1;
                HeadsUpManagerImpl.HeadsUpEntry headsUpEntry = (HeadsUpManagerImpl.HeadsUpEntry) this.f$2;
                HeadsUpManagerLogger headsUpManagerLogger = headsUpManagerImpl.mLogger;
                headsUpManagerLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda0 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(24);
                LogBuffer logBuffer = headsUpManagerLogger.buffer;
                LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) obtain).str1 = NotificationUtils.logKey(str);
                logBuffer.commit(obtain);
                headsUpManagerImpl.setEntryPinned(headsUpEntry, PinnedStatus.NotPinned, "unpinAll");
                headsUpEntry.updateEntry("unpinAll", false, true);
                NotificationEntry notificationEntry = headsUpEntry.mEntry;
                if (notificationEntry != null && (expandableNotificationRow = notificationEntry.row) != null && expandableNotificationRow.mustStayOnScreen() && (expandableNotificationRow2 = headsUpEntry.mEntry.row) != null) {
                    expandableNotificationRow2.mMustStayOnScreen = false;
                    break;
                }
                break;
            case 1:
                ((HeadsUpManagerImpl) this.f$0).updateNotificationInternal(this.f$1, (PinnedStatus) this.f$2);
                break;
            default:
                HeadsUpManagerImpl.HeadsUpEntry headsUpEntry2 = (HeadsUpManagerImpl.HeadsUpEntry) this.f$2;
                HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0 headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0 = (HeadsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0) this.f$0;
                String str2 = this.f$1;
                switch (headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0.$r8$classId) {
                    case 0:
                        HeadsUpManagerImpl.HeadsUpEntry headsUpEntry3 = headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0.f$0;
                        elapsedRealtime = headsUpEntry3.mEarliestRemovalTime - HeadsUpManagerImpl.this.mSystemClock.elapsedRealtime();
                        break;
                    default:
                        HeadsUpManagerImpl.HeadsUpEntry headsUpEntry4 = headsUpManagerImpl$HeadsUpEntry$$ExternalSyntheticLambda0.f$0;
                        HeadsUpManagerImpl headsUpManagerImpl2 = HeadsUpManagerImpl.this;
                        AvalancheController avalancheController = headsUpManagerImpl2.mAvalancheController;
                        avalancheController.getClass();
                        RemainingDuration$UpdatedDuration remainingDuration$UpdatedDuration = new RemainingDuration$UpdatedDuration(headsUpManagerImpl2.mAutoDismissTime);
                        if (avalancheController.isEnabled()) {
                            ArrayList arrayList = new ArrayList();
                            HeadsUpManagerImpl.HeadsUpEntry headsUpEntry5 = avalancheController.headsUpEntryShowing;
                            if (headsUpEntry5 != null) {
                                arrayList.add(headsUpEntry5);
                            }
                            CollectionsKt__MutableCollectionsJVMKt.sort(avalancheController.nextList);
                            List plus = CollectionsKt___CollectionsKt.plus((Iterable) avalancheController.nextList, (Collection) arrayList);
                            String key = AvalancheController.getKey(headsUpEntry4);
                            ArrayList arrayList2 = (ArrayList) plus;
                            boolean isEmpty = arrayList2.isEmpty();
                            HeadsUpManagerLogger headsUpManagerLogger2 = avalancheController.headsUpManagerLogger;
                            if (isEmpty) {
                                headsUpManagerLogger2.logAvalancheDuration(key, remainingDuration$UpdatedDuration, "No avalanche HUNs, use default", "");
                            } else {
                                Iterator it = arrayList2.iterator();
                                int i = -1;
                                int i2 = 0;
                                while (it.hasNext()) {
                                    int i3 = i2 + 1;
                                    if (Intrinsics.areEqual((HeadsUpManagerImpl.HeadsUpEntry) it.next(), headsUpEntry4)) {
                                        i = i2;
                                    }
                                    i2 = i3;
                                }
                                if (i == -1) {
                                    headsUpManagerLogger2.logAvalancheDuration(key, remainingDuration$UpdatedDuration, "Untracked entry, use default", "");
                                } else {
                                    int i4 = i + 1;
                                    if (i4 >= arrayList2.size()) {
                                        headsUpManagerLogger2.logAvalancheDuration(key, remainingDuration$UpdatedDuration, "Last entry, use default", "");
                                    } else {
                                        HeadsUpManagerImpl.HeadsUpEntry headsUpEntry6 = (HeadsUpManagerImpl.HeadsUpEntry) arrayList2.get(i4);
                                        String key2 = AvalancheController.getKey(headsUpEntry6);
                                        if (headsUpEntry6.compareNonTimeFields(headsUpEntry4) == -1) {
                                            remainingDuration$UpdatedDuration = new RemainingDuration$UpdatedDuration(500);
                                            headsUpManagerLogger2.logAvalancheDuration(key, remainingDuration$UpdatedDuration, "LOWER priority than next: ", key2);
                                        } else if (headsUpEntry6.compareNonTimeFields(headsUpEntry4) == 0) {
                                            remainingDuration$UpdatedDuration = new RemainingDuration$UpdatedDuration(1000);
                                            headsUpManagerLogger2.logAvalancheDuration(key, remainingDuration$UpdatedDuration, "SAME priority as next: ", key2);
                                        } else {
                                            headsUpManagerLogger2.logAvalancheDuration(key, remainingDuration$UpdatedDuration, "HIGHER priority than next: ", key2);
                                        }
                                    }
                                }
                            }
                        }
                        NotificationEntry notificationEntry2 = headsUpEntry4.mEntry;
                        long recommendedTimeoutMillis = headsUpEntry4.mPostTime + HeadsUpManagerImpl.this.mAccessibilityMgr.mAccessibilityManager.getRecommendedTimeoutMillis(notificationEntry2 == null ? false : notificationEntry2.isStickyAndNotDemoted() ? HeadsUpManagerImpl.this.mStickyForSomeTimeAutoDismissTime : remainingDuration$UpdatedDuration.duration, 7) + (headsUpEntry4.extended ? HeadsUpManagerImpl.this.mExtensionTime : 0);
                        long elapsedRealtime2 = HeadsUpManagerImpl.this.mSystemClock.elapsedRealtime();
                        NotificationThrottleHun notificationThrottleHun = NotificationThrottleHun.INSTANCE;
                        elapsedRealtime = Math.max(recommendedTimeoutMillis, headsUpEntry4.mEarliestRemovalTime) - elapsedRealtime2;
                        break;
                }
                if (headsUpEntry2.mRemoveRunnable != null) {
                    Runnable runnable = headsUpEntry2.mCancelRemoveRunnable;
                    boolean z = runnable != null;
                    if (z) {
                        runnable.run();
                        headsUpEntry2.mCancelRemoveRunnable = null;
                    }
                    headsUpEntry2.mCancelRemoveRunnable = HeadsUpManagerImpl.this.mExecutor.executeDelayed(headsUpEntry2.mRemoveRunnable, elapsedRealtime);
                    if (!z) {
                        HeadsUpManagerLogger headsUpManagerLogger3 = HeadsUpManagerImpl.this.mLogger;
                        NotificationEntry notificationEntry3 = headsUpEntry2.mEntry;
                        headsUpManagerLogger3.getClass();
                        LogLevel logLevel2 = LogLevel.INFO;
                        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda02 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(18);
                        LogBuffer logBuffer2 = headsUpManagerLogger3.buffer;
                        LogMessage obtain2 = logBuffer2.obtain("HeadsUpManager", logLevel2, headsUpManagerLogger$$ExternalSyntheticLambda02, null);
                        String logKey = NotificationUtilsKt.getLogKey(notificationEntry3);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain2;
                        logMessageImpl.str1 = logKey;
                        logMessageImpl.long1 = elapsedRealtime;
                        logMessageImpl.str2 = str2;
                        logBuffer2.commit(obtain2);
                        break;
                    } else {
                        HeadsUpManagerLogger headsUpManagerLogger4 = HeadsUpManagerImpl.this.mLogger;
                        NotificationEntry notificationEntry4 = headsUpEntry2.mEntry;
                        headsUpManagerLogger4.getClass();
                        LogLevel logLevel3 = LogLevel.INFO;
                        HeadsUpManagerLogger$$ExternalSyntheticLambda0 headsUpManagerLogger$$ExternalSyntheticLambda03 = new HeadsUpManagerLogger$$ExternalSyntheticLambda0(15);
                        LogBuffer logBuffer3 = headsUpManagerLogger4.buffer;
                        LogMessage obtain3 = logBuffer3.obtain("HeadsUpManager", logLevel3, headsUpManagerLogger$$ExternalSyntheticLambda03, null);
                        String logKey2 = NotificationUtilsKt.getLogKey(notificationEntry4);
                        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain3;
                        logMessageImpl2.str1 = logKey2;
                        logMessageImpl2.long1 = elapsedRealtime;
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

    public /* synthetic */ HeadsUpManagerImpl$$ExternalSyntheticLambda0(HeadsUpManagerImpl headsUpManagerImpl, String str, HeadsUpManagerImpl.HeadsUpEntry headsUpEntry) {
        this.f$0 = headsUpManagerImpl;
        this.f$1 = str;
        this.f$2 = headsUpEntry;
    }

    public /* synthetic */ HeadsUpManagerImpl$$ExternalSyntheticLambda0(HeadsUpManagerImpl headsUpManagerImpl, String str, PinnedStatus pinnedStatus) {
        this.f$0 = headsUpManagerImpl;
        this.f$1 = str;
        this.f$2 = pinnedStatus;
    }
}
