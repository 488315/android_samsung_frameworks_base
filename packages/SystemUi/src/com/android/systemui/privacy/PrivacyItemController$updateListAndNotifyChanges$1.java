package com.android.systemui.privacy;

import android.os.SystemClock;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PrivacyItemController$updateListAndNotifyChanges$1 implements Runnable {
    public final /* synthetic */ DelayableExecutor $uiExecutor;
    public final /* synthetic */ PrivacyItemController this$0;

    public PrivacyItemController$updateListAndNotifyChanges$1(PrivacyItemController privacyItemController, DelayableExecutor delayableExecutor) {
        this.this$0 = privacyItemController;
        this.$uiExecutor = delayableExecutor;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b4, code lost:
    
        if (r11 == false) goto L43;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00af A[LOOP:2: B:28:0x0080->B:38:0x00af, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ad A[SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        List list;
        Object obj;
        boolean z;
        Iterator it;
        boolean z2;
        PrivacyItemController privacyItemController = this.this$0;
        ExecutorImpl.ExecutionToken executionToken = privacyItemController.holdingRunnableCanceler;
        if (executionToken != null) {
            executionToken.run();
            Unit unit = Unit.INSTANCE;
            privacyItemController.holdingRunnableCanceler = null;
        }
        if (privacyItemController.listening) {
            ArrayList arrayList = new ArrayList();
            Iterator it2 = privacyItemController.privacyItemMonitors.iterator();
            while (it2.hasNext()) {
                CollectionsKt__MutableCollectionsKt.addAll(((PrivacyItemMonitor) it2.next()).getActivePrivacyItems(), arrayList);
            }
            List distinct = CollectionsKt___CollectionsKt.distinct(arrayList);
            PrivacyLogger privacyLogger = privacyItemController.logger;
            privacyLogger.logRetrievedPrivacyItemsList(distinct);
            ((SystemClockImpl) privacyItemController.systemClock).getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime() - 5000;
            synchronized (privacyItemController) {
                list = CollectionsKt___CollectionsKt.toList(privacyItemController.privacyList);
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it3 = list.iterator();
            while (true) {
                boolean z3 = true;
                if (!it3.hasNext()) {
                    break;
                }
                Object next = it3.next();
                PrivacyItem privacyItem = (PrivacyItem) next;
                if (privacyItem.timeStampElapsed > elapsedRealtime) {
                    if (!distinct.isEmpty()) {
                        Iterator it4 = distinct.iterator();
                        while (it4.hasNext()) {
                            PrivacyItem privacyItem2 = (PrivacyItem) it4.next();
                            if (privacyItem2.privacyType == privacyItem.privacyType && Intrinsics.areEqual(privacyItem2.application, privacyItem.application)) {
                                it = it4;
                                if (privacyItem2.timeStampElapsed == privacyItem.timeStampElapsed) {
                                    z2 = true;
                                    if (!z2) {
                                        z = true;
                                        break;
                                    }
                                    it4 = it;
                                }
                            } else {
                                it = it4;
                            }
                            z2 = false;
                            if (!z2) {
                            }
                        }
                    }
                    z = false;
                }
                z3 = false;
                if (z3) {
                    arrayList2.add(next);
                }
            }
            if (!arrayList2.isEmpty()) {
                privacyLogger.logPrivacyItemsToHold(arrayList2);
                Iterator it5 = arrayList2.iterator();
                if (it5.hasNext()) {
                    Object next2 = it5.next();
                    if (it5.hasNext()) {
                        long j = ((PrivacyItem) next2).timeStampElapsed;
                        do {
                            Object next3 = it5.next();
                            long j2 = ((PrivacyItem) next3).timeStampElapsed;
                            if (j > j2) {
                                next2 = next3;
                                j = j2;
                            }
                        } while (it5.hasNext());
                    }
                    obj = next2;
                } else {
                    obj = null;
                }
                Intrinsics.checkNotNull(obj);
                long j3 = ((PrivacyItem) obj).timeStampElapsed - elapsedRealtime;
                privacyLogger.logPrivacyItemsUpdateScheduled(j3);
                privacyItemController.holdingRunnableCanceler = privacyItemController.bgExecutor.executeDelayed(j3, privacyItemController.updateListAndNotifyChanges);
            }
            ArrayList arrayList3 = new ArrayList();
            for (Object obj2 : distinct) {
                if (!((PrivacyItem) obj2).paused) {
                    arrayList3.add(obj2);
                }
            }
            privacyItemController.privacyList = CollectionsKt___CollectionsKt.plus((Iterable) arrayList2, (Collection) arrayList3);
        } else {
            privacyItemController.privacyList = EmptyList.INSTANCE;
        }
        ((ExecutorImpl) this.$uiExecutor).execute(this.this$0.notifyChanges);
    }
}
