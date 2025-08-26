package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArrayMap;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
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
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence.AnonymousClass1;
import kotlin.sequences.SequencesKt___SequencesKt;

@CoordinatorScope
/* loaded from: classes3.dex */
public final class RowAlertTimeCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final ArrayMap<NotificationEntry, Long> latestAlertTimeBySummary = new ArrayMap<>();

    private final long calculateLatestAlertTime(GroupEntry groupEntry) {
        Long l;
        Iterator it = groupEntry.mUnmodifiableChildren.iterator();
        if (it.hasNext()) {
            Long lValueOf = Long.valueOf(((NotificationEntry) it.next()).mRanking.getLastAudiblyAlertedMillis());
            while (it.hasNext()) {
                Long lValueOf2 = Long.valueOf(((NotificationEntry) it.next()).mRanking.getLastAudiblyAlertedMillis());
                if (lValueOf.compareTo(lValueOf2) < 0) {
                    lValueOf = lValueOf2;
                }
            }
            l = lValueOf;
        } else {
            l = null;
        }
        long jLongValue = l != null ? l.longValue() : 0L;
        NotificationEntry notificationEntry = groupEntry.mSummary;
        if (notificationEntry != null) {
            return Math.max(jLongValue, notificationEntry.mRanking.getLastAudiblyAlertedMillis());
        }
        throw new IllegalStateException("Required value was null.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
        Long l = this.latestAlertTimeBySummary.get(notificationEntry);
        long jLongValue = l != null ? l.longValue() : notificationEntry.mRanking.getLastAudiblyAlertedMillis();
        ExpandableNotificationRow expandableNotificationRow = ((ExpandableNotificationRowController) notifRowController).mView;
        expandableNotificationRow.getClass();
        long jCurrentTimeMillis = System.currentTimeMillis() - jLongValue;
        long j = ExpandableNotificationRow.RECENTLY_ALERTED_THRESHOLD_MS;
        boolean z = jCurrentTimeMillis < j;
        expandableNotificationRow.applyAudiblyAlertedRecently(z);
        expandableNotificationRow.removeCallbacks(expandableNotificationRow.mExpireRecentlyAlertedFlag);
        if (z) {
            expandableNotificationRow.postDelayed(expandableNotificationRow.mExpireRecentlyAlertedFlag, j - jCurrentTimeMillis);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeFinalizeFilterListener(List<? extends PipelineEntry> list) {
        this.latestAlertTimeBySummary.clear();
        FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAlertTimeCoordinator$onBeforeFinalizeFilterListener$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Boolean mo781invoke(Object obj) {
                return Boolean.valueOf(obj instanceof GroupEntry);
            }
        }).new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            GroupEntry groupEntry = (GroupEntry) anonymousClass1.next();
            NotificationEntry notificationEntry = groupEntry.mSummary;
            if (notificationEntry == null) {
                throw new IllegalStateException("Required value was null.");
            }
            this.latestAlertTimeBySummary.put(notificationEntry, Long.valueOf(calculateLatestAlertTime(groupEntry)));
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAlertTimeCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List<? extends PipelineEntry> list) {
                RowAlertTimeCoordinator.this.onBeforeFinalizeFilterListener(list);
            }
        });
        ((ArrayList) notifPipeline.mRenderStageManager.onAfterRenderEntryListeners).add(new OnAfterRenderEntryListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAlertTimeCoordinator.attach.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener
            public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
                RowAlertTimeCoordinator.this.onAfterRenderEntry(notificationEntry, notifRowController);
            }
        });
    }
}
