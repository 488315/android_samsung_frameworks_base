package com.android.systemui.privacy;

import android.content.pm.UserInfo;
import android.os.UserHandle;
import com.android.systemui.appops.AppOpItem;
import com.android.systemui.appops.AppOpsControllerImpl;
import com.android.systemui.privacy.logging.PrivacyLogger;
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

public final class PrivacyItemController$updateListAndNotifyChanges$1 implements Runnable {
    public final /* synthetic */ DelayableExecutor $uiExecutor;
    public final /* synthetic */ PrivacyItemController this$0;

    public PrivacyItemController$updateListAndNotifyChanges$1(PrivacyItemController privacyItemController, DelayableExecutor delayableExecutor) {
        this.this$0 = privacyItemController;
        this.$uiExecutor = delayableExecutor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object next;
        PrivacyLogger privacyLogger;
        ArrayList arrayList;
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
                List activeAppOps = ((AppOpsControllerImpl) appOpsPrivacyItemMonitor.appOpsController).getActiveAppOps(true);
                List userProfiles = ((UserTrackerImpl) appOpsPrivacyItemMonitor.userTracker).getUserProfiles();
                synchronized (appOpsPrivacyItemMonitor.lock) {
                    try {
                        ArrayList arrayList3 = new ArrayList();
                        Iterator it2 = ((ArrayList) activeAppOps).iterator();
                        while (it2.hasNext()) {
                            Object next2 = it2.next();
                            AppOpItem appOpItem = (AppOpItem) next2;
                            List list = userProfiles;
                            if (!(list instanceof Collection) || !list.isEmpty()) {
                                Iterator it3 = list.iterator();
                                while (it3.hasNext()) {
                                    if (((UserInfo) it3.next()).id == UserHandle.getUserId(appOpItem.mUid)) {
                                        break;
                                    }
                                }
                            }
                            if (ArraysKt___ArraysKt.contains(appOpItem.mCode, AppOpsPrivacyItemMonitor.USER_INDEPENDENT_OPS)) {
                                arrayList3.add(next2);
                            }
                        }
                        arrayList = new ArrayList();
                        Iterator it4 = arrayList3.iterator();
                        while (it4.hasNext()) {
                            AppOpItem appOpItem2 = (AppOpItem) it4.next();
                            Intrinsics.checkNotNull(appOpItem2);
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
            }
            List distinct = CollectionsKt___CollectionsKt.distinct(arrayList2);
            PrivacyLogger privacyLogger2 = privacyItemController.logger;
            privacyLogger2.logRetrievedPrivacyItemsList(distinct);
            long elapsedRealtime = privacyItemController.systemClock.elapsedRealtime() - 5000;
            List privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core = privacyItemController.getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            ArrayList arrayList4 = new ArrayList();
            for (Object obj : privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core) {
                PrivacyItem privacyItem = (PrivacyItem) obj;
                if (privacyItem.timeStampElapsed > elapsedRealtime) {
                    List<PrivacyItem> list2 = distinct;
                    if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                        for (PrivacyItem privacyItem2 : list2) {
                            if (privacyItem2.privacyType == privacyItem.privacyType && Intrinsics.areEqual(privacyItem2.application, privacyItem.application)) {
                                privacyLogger = privacyLogger2;
                                if (privacyItem2.timeStampElapsed == privacyItem.timeStampElapsed) {
                                    break;
                                }
                            } else {
                                privacyLogger = privacyLogger2;
                            }
                            privacyLogger2 = privacyLogger;
                        }
                    }
                    privacyLogger = privacyLogger2;
                    arrayList4.add(obj);
                } else {
                    privacyLogger = privacyLogger2;
                }
                privacyLogger2 = privacyLogger;
            }
            PrivacyLogger privacyLogger3 = privacyLogger2;
            if (!arrayList4.isEmpty()) {
                privacyLogger3.logPrivacyItemsToHold(arrayList4);
                Iterator it5 = arrayList4.iterator();
                if (it5.hasNext()) {
                    next = it5.next();
                    if (it5.hasNext()) {
                        long j = ((PrivacyItem) next).timeStampElapsed;
                        do {
                            Object next3 = it5.next();
                            long j2 = ((PrivacyItem) next3).timeStampElapsed;
                            if (j > j2) {
                                next = next3;
                                j = j2;
                            }
                        } while (it5.hasNext());
                    }
                } else {
                    next = null;
                }
                Intrinsics.checkNotNull(next);
                long j3 = ((PrivacyItem) next).timeStampElapsed - elapsedRealtime;
                privacyLogger3.logPrivacyItemsUpdateScheduled(j3);
                privacyItemController.holdingRunnableCanceler = privacyItemController.bgExecutor.executeDelayed(privacyItemController.updateListAndNotifyChanges, j3);
            }
            ArrayList arrayList5 = new ArrayList();
            for (Object obj2 : distinct) {
                if (!((PrivacyItem) obj2).paused) {
                    arrayList5.add(obj2);
                }
            }
            privacyItemController.privacyList = CollectionsKt___CollectionsKt.plus((Iterable) arrayList4, (Collection) arrayList5);
        } else {
            privacyItemController.privacyList = EmptyList.INSTANCE;
        }
        this.$uiExecutor.execute(this.this$0.notifyChanges);
    }
}
