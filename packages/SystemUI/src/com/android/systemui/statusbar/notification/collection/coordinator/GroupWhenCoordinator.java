package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArrayMap;
import android.util.Log;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.render.NotifGroupController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

@CoordinatorScope
public final class GroupWhenCoordinator implements Coordinator {
    public static final int $stable = 8;
    private Runnable cancelInvalidateListRunnable;
    private final DelayableExecutor delayableExecutor;
    private final SystemClock systemClock;
    private final GroupWhenCoordinator$invalidator$1 invalidator = new Invalidator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$invalidator$1
    };
    private final ArrayMap<GroupEntry, Long> notificationGroupTimes = new ArrayMap<>();
    private final Runnable invalidateListRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$invalidateListRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            GroupWhenCoordinator$invalidator$1 groupWhenCoordinator$invalidator$1;
            groupWhenCoordinator$invalidator$1 = GroupWhenCoordinator.this.invalidator;
            groupWhenCoordinator$invalidator$1.invalidateList("future notification invalidation");
        }
    };

    public GroupWhenCoordinator(DelayableExecutor delayableExecutor, SystemClock systemClock) {
        this.delayableExecutor = delayableExecutor;
        this.systemClock = systemClock;
    }

    private final long calculateGroupNotificationTime(GroupEntry groupEntry, long j) {
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(groupEntry.mUnmodifiableChildren), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$calculateGroupNotificationTime$1
            @Override // kotlin.jvm.functions.Function1
            public final Long invoke(NotificationEntry notificationEntry) {
                Long valueOf = Long.valueOf(notificationEntry.mSbn.getNotification().getWhen());
                if (valueOf.longValue() > 0) {
                    return valueOf;
                }
                return null;
            }
        }));
        long j2 = Long.MIN_VALUE;
        long j3 = Long.MAX_VALUE;
        while (filteringSequence$iterator$1.hasNext()) {
            long longValue = ((Number) filteringSequence$iterator$1.next()).longValue();
            if (j - longValue > 0) {
                j2 = Math.max(j2, longValue);
            } else {
                j3 = Math.min(j3, longValue);
            }
        }
        if (j2 != Long.MIN_VALUE || j3 != Long.MAX_VALUE) {
            return j3 != Long.MAX_VALUE ? j3 : j2;
        }
        NotificationEntry notificationEntry = groupEntry.mSummary;
        if (notificationEntry != null) {
            return notificationEntry.mCreationTime;
        }
        throw new IllegalStateException("Required value was null.".toString());
    }

    private final void cancelListInvalidation() {
        Runnable runnable = this.cancelInvalidateListRunnable;
        if (runnable != null) {
            runnable.run();
        }
        this.cancelInvalidateListRunnable = null;
    }

    public final void onAfterRenderGroupListener(GroupEntry groupEntry, NotifGroupController notifGroupController) {
        NotificationViewWrapper notificationViewWrapper;
        Long l = this.notificationGroupTimes.get(groupEntry);
        if (l != null) {
            long longValue = l.longValue();
            ExpandableNotificationRow expandableNotificationRow = ((ExpandableNotificationRowController) notifGroupController).mView;
            boolean z = expandableNotificationRow.mIsSummaryWithChildren;
            if (!z) {
                Log.w("NotifRowController", "Called setNotificationTime(" + longValue + ") on a leaf row");
                return;
            }
            if (!z) {
                Log.w("ExpandableNotifRow", "setNotificationGroupWhen( whenMillis: " + longValue + ") mIsSummaryWithChildren: false mChildrenContainer has not been inflated yet.");
                return;
            }
            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
            NotificationHeaderViewWrapper notificationHeaderViewWrapper = notificationChildrenContainer.mGroupHeaderWrapper;
            if (notificationHeaderViewWrapper != null) {
                notificationHeaderViewWrapper.setNotificationWhen(longValue);
            }
            NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = notificationChildrenContainer.mMinimizedGroupHeaderWrapper;
            if (notificationHeaderViewWrapper2 != null) {
                notificationHeaderViewWrapper2.setNotificationWhen(longValue);
            }
            if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON) {
                notificationChildrenContainer.mWhenMillis = longValue;
            }
            NotificationContentView notificationContentView = expandableNotificationRow.mPublicLayout;
            if ((notificationContentView.mContractedChild == null || (notificationViewWrapper = notificationContentView.mContractedWrapper) == null) && ((notificationContentView.mExpandedChild == null || (notificationViewWrapper = notificationContentView.mExpandedWrapper) == null) && (notificationContentView.mHeadsUpChild == null || (notificationViewWrapper = notificationContentView.mHeadsUpWrapper) == null))) {
                notificationViewWrapper = null;
            }
            if (notificationViewWrapper instanceof NotificationHeaderViewWrapper) {
                ((NotificationHeaderViewWrapper) notificationViewWrapper).setNotificationWhen(longValue);
            }
        }
    }

    public final void onBeforeFinalizeFilterListener(List<? extends ListEntry> list) {
        cancelListInvalidation();
        this.notificationGroupTimes.clear();
        long currentTimeMillis = this.systemClock.currentTimeMillis();
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$onBeforeFinalizeFilterListener$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof GroupEntry);
            }
        }));
        long j = Long.MAX_VALUE;
        while (filteringSequence$iterator$1.hasNext()) {
            GroupEntry groupEntry = (GroupEntry) filteringSequence$iterator$1.next();
            long calculateGroupNotificationTime = calculateGroupNotificationTime(groupEntry, currentTimeMillis);
            this.notificationGroupTimes.put(groupEntry, Long.valueOf(calculateGroupNotificationTime));
            if (calculateGroupNotificationTime > currentTimeMillis) {
                j = Math.min(j, calculateGroupNotificationTime);
            }
        }
        if (j != Long.MAX_VALUE) {
            this.cancelInvalidateListRunnable = this.delayableExecutor.executeDelayed(this.invalidateListRunnable, j - currentTimeMillis);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List<? extends ListEntry> list) {
                GroupWhenCoordinator.this.onBeforeFinalizeFilterListener(list);
            }
        });
        ((ArrayList) notifPipeline.mRenderStageManager.onAfterRenderGroupListeners).add(new OnAfterRenderGroupListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$attach$2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener
            public final void onAfterRenderGroup(GroupEntry groupEntry, NotifGroupController notifGroupController) {
                GroupWhenCoordinator.this.onAfterRenderGroupListener(groupEntry, notifGroupController);
            }
        });
        notifPipeline.addPreRenderInvalidator(this.invalidator);
    }
}
