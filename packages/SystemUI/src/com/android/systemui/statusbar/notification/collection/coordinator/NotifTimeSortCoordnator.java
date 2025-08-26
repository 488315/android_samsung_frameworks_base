package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence.AnonymousClass1;

/* loaded from: classes3.dex */
public final class NotifTimeSortCoordnator implements Coordinator {
    public static final int $stable = 8;
    private final HeadsUpManager mHeadsUpManager;
    private final SettingsHelper settingsHelper;
    private final SystemClock systemClock;
    private String[] pkgArray = {"com.samsung.android.incallui", "com.skt.prod.dialer", "com.android.systemui"};
    private String[] channelArray = {"Ongoing_call", "NO_HUN_CHANNEL_CALL_CONTROL", "ZEN_ONGOING"};
    private final NotifSectioner sectionerForPriority = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$sectionerForPriority$1
        {
            super("TimeOrderPriority", 5);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NotifComparator getComparator() {
            return this.this$0.getTimeComparator();
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            boolean z = this.this$0.settingsHelper.getNotificationSortOrderValue() == 1 && this.this$0.isIncludedPriortyList(pipelineEntry);
            if (z) {
                this.this$0.sortChildren(pipelineEntry, getComparator());
            }
            return z;
        }
    };
    private final NotifSectioner sectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$sectioner$1
        {
            super("TimeOrder", 6);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NotifComparator getComparator() {
            return this.this$0.getTimeComparator();
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            NotificationEntry representativeEntry;
            if (!((HeadsUpManagerImpl) this.this$0.mHeadsUpManager).isHeadsUpEntry(pipelineEntry.getKey()) && (representativeEntry = pipelineEntry.getRepresentativeEntry()) != null && representativeEntry.isInsignificant()) {
                return false;
            }
            boolean z = this.this$0.settingsHelper.getNotificationSortOrderValue() == 1;
            if (z) {
                this.this$0.sortChildren(pipelineEntry, getComparator());
            }
            return z;
        }
    };
    private final NotifComparator timeComparator = new NotifComparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$timeComparator$1
        {
            super("TimeOrder");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator, java.util.Comparator
        public int compare(PipelineEntry pipelineEntry, PipelineEntry pipelineEntry2) {
            return this.this$0.getTimeCompare(pipelineEntry, pipelineEntry2);
        }
    };

    public NotifTimeSortCoordnator(SettingsHelper settingsHelper, SystemClock systemClock, HeadsUpManager headsUpManager) {
        this.settingsHelper = settingsHelper;
        this.systemClock = systemClock;
        this.mHeadsUpManager = headsUpManager;
    }

    private final long calculateGroupNotificationTime(GroupEntry groupEntry, long j) {
        TransformingSequence.AnonymousClass1 anonymousClass1 = new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(groupEntry.mUnmodifiableChildren), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return Long.valueOf(NotifTimeSortCoordnator.calculateGroupNotificationTime$lambda$1(this.f$0, (NotificationEntry) obj));
            }
        }).new AnonymousClass1();
        long jMax = Long.MIN_VALUE;
        long jMin = Long.MAX_VALUE;
        while (anonymousClass1.iterator.hasNext()) {
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
        notificationEntry.getClass();
        return getTime(notificationEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final long calculateGroupNotificationTime$lambda$1(NotifTimeSortCoordnator notifTimeSortCoordnator, NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return notifTimeSortCoordnator.getTime(notificationEntry);
    }

    private final long calculateRepresentativeNotificationTime(PipelineEntry pipelineEntry) {
        if (pipelineEntry instanceof GroupEntry) {
            return calculateGroupNotificationTime((GroupEntry) pipelineEntry, this.systemClock.currentTimeMillis());
        }
        NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
        representativeEntry.getClass();
        return getTime(representativeEntry);
    }

    private final long getTime(NotificationEntry notificationEntry) {
        if (notificationEntry == null) {
            return 0L;
        }
        long j = notificationEntry.mSbn.getNotification().when;
        return j > 0 ? j : notificationEntry.mSbn.getPostTime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getTimeCompare(PipelineEntry pipelineEntry, PipelineEntry pipelineEntry2) {
        return Long.compare(calculateRepresentativeNotificationTime(pipelineEntry2), calculateRepresentativeNotificationTime(pipelineEntry));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isIncludedPriortyList(PipelineEntry pipelineEntry) {
        NotificationChannel channel;
        NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
        String[] strArr = this.pkgArray;
        int length = strArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            String str = strArr[i];
            int i3 = i2 + 1;
            String id = null;
            StatusBarNotification statusBarNotification = representativeEntry != null ? representativeEntry.mSbn : null;
            statusBarNotification.getClass();
            if (str.equals(statusBarNotification.getPackageName())) {
                String str2 = this.channelArray[i2];
                if (representativeEntry != null && (channel = representativeEntry.mRanking.getChannel()) != null) {
                    id = channel.getId();
                }
                if (str2.equals(id)) {
                    return true;
                }
            }
            i++;
            i2 = i3;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sortChildren(PipelineEntry pipelineEntry, NotifComparator notifComparator) {
        if (pipelineEntry instanceof GroupEntry) {
            ((ArrayList) ((GroupEntry) pipelineEntry).mChildren).sort(notifComparator);
        }
    }

    public final String[] getChannelArray() {
        return this.channelArray;
    }

    public final String[] getPkgArray() {
        return this.pkgArray;
    }

    public final NotifSectioner getSectioner() {
        return this.sectioner;
    }

    public final NotifSectioner getSectionerForPriority() {
        return this.sectionerForPriority;
    }

    public final NotifComparator getTimeComparator() {
        return this.timeComparator;
    }

    public final void setChannelArray(String[] strArr) {
        this.channelArray = strArr;
    }

    public final void setPkgArray(String[] strArr) {
        this.pkgArray = strArr;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
    }
}
