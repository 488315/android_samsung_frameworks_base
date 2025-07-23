package com.android.systemui.privacy;

import android.content.pm.UserInfo;
import android.os.UserHandle;
import com.android.systemui.appops.AppOpItem;
import com.android.systemui.appops.AppOpsControllerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0;
import com.android.systemui.privacy.logging.PrivacyLoggerKt;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PrivacyItemController$updateListAndNotifyChanges$1 implements Runnable {
    public final /* synthetic */ DelayableExecutor $uiExecutor;
    public final /* synthetic */ PrivacyItemController this$0;

    public PrivacyItemController$updateListAndNotifyChanges$1(PrivacyItemController privacyItemController, DelayableExecutor delayableExecutor) {
        this.this$0 = privacyItemController;
        this.$uiExecutor = delayableExecutor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v6, types: [int] */
    @Override // java.lang.Runnable
    public final void run() {
        Object next;
        long j;
        boolean z;
        ArrayList arrayList;
        boolean z2 = true;
        PrivacyItemController privacyItemController = this.this$0;
        Runnable runnable = privacyItemController.holdingRunnableCanceler;
        if (runnable != null) {
            runnable.run();
            Unit unit = Unit.INSTANCE;
            privacyItemController.holdingRunnableCanceler = null;
        }
        if (privacyItemController.listening) {
            Set set = privacyItemController.privacyItemMonitors;
            ArrayList arrayList2 = new ArrayList();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor = (AppOpsPrivacyItemMonitor) ((PrivacyItemMonitor) it.next());
                List activeAppOps = ((AppOpsControllerImpl) appOpsPrivacyItemMonitor.appOpsController).getActiveAppOps(z2);
                List userProfiles = ((UserTrackerImpl) appOpsPrivacyItemMonitor.userTracker).getUserProfiles();
                synchronized (appOpsPrivacyItemMonitor.lock) {
                    try {
                        ArrayList arrayList3 = new ArrayList();
                        ArrayList arrayList4 = (ArrayList) activeAppOps;
                        int size = arrayList4.size();
                        int i = 0;
                        ?? r1 = z2;
                        while (i < size) {
                            Object obj = arrayList4.get(i);
                            i += r1;
                            AppOpItem appOpItem = (AppOpItem) obj;
                            boolean z3 = r1;
                            List list = userProfiles;
                            if (!(list instanceof Collection) || !list.isEmpty()) {
                                Iterator it2 = list.iterator();
                                while (it2.hasNext()) {
                                    if (((UserInfo) it2.next()).id == UserHandle.getUserId(appOpItem.mUid)) {
                                        break;
                                    }
                                }
                            }
                            if (!ArraysKt___ArraysKt.contains(appOpItem.mCode, AppOpsPrivacyItemMonitor.USER_INDEPENDENT_OPS)) {
                                r1 = z3;
                            }
                            arrayList3.add(obj);
                            r1 = z3;
                        }
                        z = r1;
                        arrayList = new ArrayList();
                        int size2 = arrayList3.size();
                        int i2 = 0;
                        while (i2 < size2) {
                            Object obj2 = arrayList3.get(i2);
                            i2++;
                            AppOpItem appOpItem2 = (AppOpItem) obj2;
                            appOpItem2.getClass();
                            PrivacyItem privacyItemLocked = appOpsPrivacyItemMonitor.toPrivacyItemLocked(appOpItem2);
                            if (privacyItemLocked != null) {
                                arrayList.add(privacyItemLocked);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                CollectionsKt__MutableCollectionsKt.addAll(CollectionsKt___CollectionsKt.distinct(arrayList), arrayList2);
                z2 = z;
            }
            List distinct = CollectionsKt___CollectionsKt.distinct(arrayList2);
            PrivacyLogger privacyLogger = privacyItemController.logger;
            privacyLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(11);
            LogBuffer logBuffer = privacyLogger.buffer;
            LogMessage obtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).str1 = PrivacyLogger.listToString(distinct);
            logBuffer.commit(obtain);
            long elapsedRealtime = privacyItemController.systemClock.elapsedRealtime() - 5000;
            List privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core = privacyItemController.getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            ArrayList arrayList5 = new ArrayList();
            for (Object obj3 : privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core) {
                PrivacyItem privacyItem = (PrivacyItem) obj3;
                if (privacyItem.timeStampElapsed > elapsedRealtime) {
                    List<PrivacyItem> list2 = distinct;
                    boolean z4 = list2 instanceof Collection;
                    PrivacyType privacyType = privacyItem.privacyType;
                    if (!z4 || !list2.isEmpty()) {
                        for (PrivacyItem privacyItem2 : list2) {
                            if (privacyItem2.privacyType == privacyType && Intrinsics.areEqual(privacyItem2.application, privacyItem.application)) {
                                j = elapsedRealtime;
                                if (privacyItem2.timeStampElapsed == privacyItem.timeStampElapsed) {
                                    break;
                                }
                            } else {
                                j = elapsedRealtime;
                            }
                            elapsedRealtime = j;
                        }
                    }
                    j = elapsedRealtime;
                    if (privacyType != PrivacyType.TYPE_LOCATION) {
                        arrayList5.add(obj3);
                    }
                } else {
                    j = elapsedRealtime;
                }
                elapsedRealtime = j;
            }
            long j2 = elapsedRealtime;
            if (!arrayList5.isEmpty()) {
                LogLevel logLevel2 = LogLevel.DEBUG;
                PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda02 = new PrivacyLogger$$ExternalSyntheticLambda0(8);
                LogBuffer logBuffer2 = privacyLogger.buffer;
                LogMessage obtain2 = logBuffer2.obtain("PrivacyLog", logLevel2, privacyLogger$$ExternalSyntheticLambda02, null);
                ((LogMessageImpl) obtain2).str1 = PrivacyLogger.listToString(arrayList5);
                logBuffer2.commit(obtain2);
                Iterator it3 = arrayList5.iterator();
                if (it3.hasNext()) {
                    next = it3.next();
                    if (it3.hasNext()) {
                        long j3 = ((PrivacyItem) next).timeStampElapsed;
                        do {
                            Object next2 = it3.next();
                            long j4 = ((PrivacyItem) next2).timeStampElapsed;
                            if (j3 > j4) {
                                next = next2;
                                j3 = j4;
                            }
                        } while (it3.hasNext());
                    }
                } else {
                    next = null;
                }
                next.getClass();
                long j5 = ((PrivacyItem) next).timeStampElapsed - j2;
                LogLevel logLevel3 = LogLevel.INFO;
                PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda03 = new PrivacyLogger$$ExternalSyntheticLambda0(7);
                LogBuffer logBuffer3 = privacyLogger.buffer;
                LogMessage obtain3 = logBuffer3.obtain("PrivacyLog", logLevel3, privacyLogger$$ExternalSyntheticLambda03, null);
                ((LogMessageImpl) obtain3).str1 = PrivacyLoggerKt.DATE_FORMAT.format(Long.valueOf(System.currentTimeMillis() + j5));
                logBuffer3.commit(obtain3);
                privacyItemController.holdingRunnableCanceler = privacyItemController.bgExecutor.executeDelayed(privacyItemController.updateListAndNotifyChanges, j5);
            }
            ArrayList arrayList6 = new ArrayList();
            for (Object obj4 : distinct) {
                if (!((PrivacyItem) obj4).paused) {
                    arrayList6.add(obj4);
                }
            }
            privacyItemController.privacyList = CollectionsKt___CollectionsKt.plus((Iterable) arrayList5, (Collection) arrayList6);
        } else {
            privacyItemController.privacyList = EmptyList.INSTANCE;
        }
        this.$uiExecutor.execute(this.this$0.notifyChanges);
    }
}
