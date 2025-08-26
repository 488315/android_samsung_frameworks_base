package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArrayMap;
import android.util.Log;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
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
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence.AnonymousClass1;
import kotlin.sequences.SequencesKt___SequencesKt;

@CoordinatorScope
/* loaded from: classes3.dex */
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
            invalidateList("future notification invalidation");
        }
    };

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$invalidator$1] */
    public GroupWhenCoordinator(DelayableExecutor delayableExecutor, SystemClock systemClock) {
        this.delayableExecutor = delayableExecutor;
        this.systemClock = systemClock;
    }

    private final long calculateGroupNotificationTime(GroupEntry groupEntry, long j) {
        FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.mapNotNull(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(groupEntry.mUnmodifiableChildren), new GroupWhenCoordinator$$ExternalSyntheticLambda0()).new AnonymousClass1();
        long jMax = Long.MIN_VALUE;
        long jMin = Long.MAX_VALUE;
        while (anonymousClass1.hasNext()) {
            long jLongValue = ((Number) anonymousClass1.next()).longValue();
            if (j - jLongValue > 0) {
                jMax = Math.max(jMax, jLongValue);
            } else {
                jMin = Math.min(jMin, jLongValue);
            }
        }
        if (jMax != Long.MIN_VALUE || jMin != Long.MAX_VALUE) {
            return jMin != Long.MAX_VALUE ? jMin : jMax;
        }
        NotificationEntry notificationEntry = groupEntry.mSummary;
        if (notificationEntry != null) {
            return notificationEntry.mCreationTime;
        }
        throw new IllegalStateException("Required value was null.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Long calculateGroupNotificationTime$lambda$2(NotificationEntry notificationEntry) {
        Long lValueOf = Long.valueOf(notificationEntry.mSbn.getNotification().getWhen());
        if (lValueOf.longValue() > 0) {
            return lValueOf;
        }
        return null;
    }

    private final void cancelListInvalidation() {
        Runnable runnable = this.cancelInvalidateListRunnable;
        if (runnable != null) {
            runnable.run();
        }
        this.cancelInvalidateListRunnable = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderGroupListener(GroupEntry groupEntry, NotifGroupController notifGroupController) {
        NotificationViewWrapper notificationViewWrapper;
        Long l = this.notificationGroupTimes.get(groupEntry);
        if (l != null) {
            long jLongValue = l.longValue();
            ExpandableNotificationRow expandableNotificationRow = ((ExpandableNotificationRowController) notifGroupController).mView;
            boolean z = expandableNotificationRow.mIsSummaryWithChildren;
            if (!z) {
                Log.w("NotifRowController", "Called setNotificationTime(" + jLongValue + ") on a leaf row");
                return;
            }
            if (!z) {
                Log.w("ExpandableNotifRow", "setNotificationGroupWhen( whenMillis: " + jLongValue + ") mIsSummaryWithChildren: false mChildrenContainer has not been inflated yet.");
                return;
            }
            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
            NotificationHeaderViewWrapper notificationHeaderViewWrapper = notificationChildrenContainer.mGroupHeaderWrapper;
            if (notificationHeaderViewWrapper != null) {
                notificationHeaderViewWrapper.setNotificationWhen(jLongValue);
            }
            NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = notificationChildrenContainer.mMinimizedGroupHeaderWrapper;
            if (notificationHeaderViewWrapper2 != null) {
                notificationHeaderViewWrapper2.setNotificationWhen(jLongValue);
            }
            if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON) {
                notificationChildrenContainer.mWhenMillis = jLongValue;
            }
            NotificationContentView notificationContentView = expandableNotificationRow.mPublicLayout;
            if ((notificationContentView.mContractedChild == null || (notificationViewWrapper = notificationContentView.mContractedWrapper) == null) && ((notificationContentView.mExpandedChild == null || (notificationViewWrapper = notificationContentView.mExpandedWrapper) == null) && (notificationContentView.mHeadsUpChild == null || (notificationViewWrapper = notificationContentView.mHeadsUpWrapper) == null))) {
                notificationViewWrapper = null;
            }
            if (notificationViewWrapper instanceof NotificationHeaderViewWrapper) {
                ((NotificationHeaderViewWrapper) notificationViewWrapper).setNotificationWhen(jLongValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeFinalizeFilterListener(List<? extends PipelineEntry> list) {
        cancelListInvalidation();
        this.notificationGroupTimes.clear();
        long jCurrentTimeMillis = this.systemClock.currentTimeMillis();
        FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$onBeforeFinalizeFilterListener$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Boolean mo781invoke(Object obj) {
                return Boolean.valueOf(obj instanceof GroupEntry);
            }
        }).new AnonymousClass1();
        long jMin = Long.MAX_VALUE;
        while (anonymousClass1.hasNext()) {
            GroupEntry groupEntry = (GroupEntry) anonymousClass1.next();
            long jCalculateGroupNotificationTime = calculateGroupNotificationTime(groupEntry, jCurrentTimeMillis);
            this.notificationGroupTimes.put(groupEntry, Long.valueOf(jCalculateGroupNotificationTime));
            if (jCalculateGroupNotificationTime > jCurrentTimeMillis) {
                jMin = Math.min(jMin, jCalculateGroupNotificationTime);
            }
        }
        if (jMin != Long.MAX_VALUE) {
            this.cancelInvalidateListRunnable = this.delayableExecutor.executeDelayed(this.invalidateListRunnable, jMin - jCurrentTimeMillis);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List<? extends PipelineEntry> list) {
                GroupWhenCoordinator.this.onBeforeFinalizeFilterListener(list);
            }
        });
        ((ArrayList) notifPipeline.mRenderStageManager.onAfterRenderGroupListeners).add(new OnAfterRenderGroupListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator.attach.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener
            public final void onAfterRenderGroup(GroupEntry groupEntry, NotifGroupController notifGroupController) {
                GroupWhenCoordinator.this.onAfterRenderGroupListener(groupEntry, notifGroupController);
            }
        });
        notifPipeline.addPreRenderInvalidator(this.invalidator);
    }
}
