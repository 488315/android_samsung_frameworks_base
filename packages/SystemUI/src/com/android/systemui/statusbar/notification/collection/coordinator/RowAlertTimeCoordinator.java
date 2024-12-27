package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArrayMap;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.render.NotifRowController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public final class RowAlertTimeCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final ArrayMap<NotificationEntry, Long> latestAlertTimeBySummary = new ArrayMap<>();

    private final long calculateLatestAlertTime(GroupEntry groupEntry) {
        Long l;
        Iterator it = groupEntry.mUnmodifiableChildren.iterator();
        if (it.hasNext()) {
            Long valueOf = Long.valueOf(((NotificationEntry) it.next()).mRanking.getLastAudiblyAlertedMillis());
            while (it.hasNext()) {
                Long valueOf2 = Long.valueOf(((NotificationEntry) it.next()).mRanking.getLastAudiblyAlertedMillis());
                if (valueOf.compareTo(valueOf2) < 0) {
                    valueOf = valueOf2;
                }
            }
            l = valueOf;
        } else {
            l = null;
        }
        long longValue = l != null ? l.longValue() : 0L;
        NotificationEntry notificationEntry = groupEntry.mSummary;
        if (notificationEntry != null) {
            return Math.max(longValue, notificationEntry.mRanking.getLastAudiblyAlertedMillis());
        }
        throw new IllegalStateException("Required value was null.".toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
        Long l = this.latestAlertTimeBySummary.get(notificationEntry);
        if (l == null) {
            l = Long.valueOf(notificationEntry.mRanking.getLastAudiblyAlertedMillis());
        }
        long longValue = l.longValue();
        ExpandableNotificationRow expandableNotificationRow = ((ExpandableNotificationRowController) notifRowController).mView;
        expandableNotificationRow.getClass();
        long currentTimeMillis = System.currentTimeMillis() - longValue;
        long j = ExpandableNotificationRow.RECENTLY_ALERTED_THRESHOLD_MS;
        boolean z = currentTimeMillis < j;
        expandableNotificationRow.applyAudiblyAlertedRecently(z);
        expandableNotificationRow.removeCallbacks(expandableNotificationRow.mExpireRecentlyAlertedFlag);
        if (z) {
            expandableNotificationRow.postDelayed(expandableNotificationRow.mExpireRecentlyAlertedFlag, j - currentTimeMillis);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeFinalizeFilterListener(List<? extends ListEntry> list) {
        this.latestAlertTimeBySummary.clear();
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAlertTimeCoordinator$onBeforeFinalizeFilterListener$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof GroupEntry);
            }
        }));
        while (filteringSequence$iterator$1.hasNext()) {
            GroupEntry groupEntry = (GroupEntry) filteringSequence$iterator$1.next();
            NotificationEntry notificationEntry = groupEntry.mSummary;
            if (notificationEntry == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            this.latestAlertTimeBySummary.put(notificationEntry, Long.valueOf(calculateLatestAlertTime(groupEntry)));
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAlertTimeCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List<? extends ListEntry> list) {
                RowAlertTimeCoordinator.this.onBeforeFinalizeFilterListener(list);
            }
        });
        ((ArrayList) notifPipeline.mRenderStageManager.onAfterRenderEntryListeners).add(new OnAfterRenderEntryListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAlertTimeCoordinator$attach$2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener
            public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
                RowAlertTimeCoordinator.this.onAfterRenderEntry(notificationEntry, notifRowController);
            }
        });
    }
}
