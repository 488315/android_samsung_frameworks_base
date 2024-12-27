package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.service.notification.StatusBarNotification;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence$iterator$1;

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
            return NotifTimeSortCoordnator.this.getTimeComparator();
        }

        /* JADX WARN: Code restructure failed: missing block: B:4:0x0013, code lost:
        
            if (r0 != false) goto L8;
         */
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean isInSection(com.android.systemui.statusbar.notification.collection.ListEntry r3) {
            /*
                r2 = this;
                com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator r0 = com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator.this
                com.android.systemui.util.SettingsHelper r0 = com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator.access$getSettingsHelper$p(r0)
                int r0 = r0.getNotificationSortOrderValue()
                r1 = 1
                if (r0 != r1) goto L16
                com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator r0 = com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator.this
                boolean r0 = com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator.access$isIncludedPriortyList(r0, r3)
                if (r0 == 0) goto L16
                goto L17
            L16:
                r1 = 0
            L17:
                if (r1 == 0) goto L22
                com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator r0 = com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator.this
                com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator r2 = r2.getComparator()
                com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator.access$sortChildren(r0, r3, r2)
            L22:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$sectionerForPriority$1.isInSection(com.android.systemui.statusbar.notification.collection.ListEntry):boolean");
        }
    };
    private final NotifSectioner sectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$sectioner$1
        {
            super("TimeOrder", 6);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NotifComparator getComparator() {
            return NotifTimeSortCoordnator.this.getTimeComparator();
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            SettingsHelper settingsHelper;
            HeadsUpManager headsUpManager;
            NotificationEntry representativeEntry;
            if (NotiRune.NOTI_INSIGNIFICANT) {
                headsUpManager = NotifTimeSortCoordnator.this.mHeadsUpManager;
                if (!((BaseHeadsUpManager) headsUpManager).isHeadsUpEntry(listEntry.getKey()) && (representativeEntry = listEntry.getRepresentativeEntry()) != null && representativeEntry.isInsignificant()) {
                    return false;
                }
            }
            settingsHelper = NotifTimeSortCoordnator.this.settingsHelper;
            boolean z = settingsHelper.getNotificationSortOrderValue() == 1;
            if (z) {
                NotifTimeSortCoordnator.this.sortChildren(listEntry, getComparator());
            }
            return z;
        }
    };
    private final NotifComparator timeComparator = new NotifComparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$timeComparator$1
        {
            super("TimeOrder");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator, java.util.Comparator
        public int compare(ListEntry listEntry, ListEntry listEntry2) {
            int timeCompare;
            timeCompare = NotifTimeSortCoordnator.this.getTimeCompare(listEntry, listEntry2);
            return timeCompare;
        }
    };

    public NotifTimeSortCoordnator(SettingsHelper settingsHelper, SystemClock systemClock, HeadsUpManager headsUpManager) {
        this.settingsHelper = settingsHelper;
        this.systemClock = systemClock;
        this.mHeadsUpManager = headsUpManager;
    }

    private final long calculateGroupNotificationTime(GroupEntry groupEntry, long j) {
        TransformingSequence$iterator$1 transformingSequence$iterator$1 = new TransformingSequence$iterator$1(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(groupEntry.mUnmodifiableChildren), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$calculateGroupNotificationTime$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Long invoke(NotificationEntry notificationEntry) {
                long time;
                NotifTimeSortCoordnator notifTimeSortCoordnator = NotifTimeSortCoordnator.this;
                Intrinsics.checkNotNull(notificationEntry);
                time = notifTimeSortCoordnator.getTime(notificationEntry);
                return Long.valueOf(time);
            }
        }));
        long j2 = Long.MIN_VALUE;
        long j3 = Long.MAX_VALUE;
        while (transformingSequence$iterator$1.hasNext()) {
            long longValue = ((Number) transformingSequence$iterator$1.next()).longValue();
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
        Intrinsics.checkNotNull(notificationEntry);
        return getTime(notificationEntry);
    }

    private final long calculateRepresentativeNotificationTime(ListEntry listEntry) {
        if (listEntry instanceof GroupEntry) {
            return calculateGroupNotificationTime((GroupEntry) listEntry, this.systemClock.currentTimeMillis());
        }
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        Intrinsics.checkNotNull(representativeEntry);
        return getTime(representativeEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long getTime(NotificationEntry notificationEntry) {
        if (notificationEntry == null) {
            return 0L;
        }
        long j = notificationEntry.mSbn.getNotification().when;
        return j > 0 ? j : notificationEntry.mSbn.getPostTime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getTimeCompare(ListEntry listEntry, ListEntry listEntry2) {
        return Long.compare(calculateRepresentativeNotificationTime(listEntry2), calculateRepresentativeNotificationTime(listEntry));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isIncludedPriortyList(ListEntry listEntry) {
        NotificationChannel channel;
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        String[] strArr = this.pkgArray;
        int length = strArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            String str = strArr[i];
            int i3 = i2 + 1;
            String str2 = null;
            StatusBarNotification statusBarNotification = representativeEntry != null ? representativeEntry.mSbn : null;
            Intrinsics.checkNotNull(statusBarNotification);
            if (str.equals(statusBarNotification.getPackageName())) {
                String str3 = this.channelArray[i2];
                if (representativeEntry != null && (channel = representativeEntry.mRanking.getChannel()) != null) {
                    str2 = channel.getId();
                }
                if (str3.equals(str2)) {
                    return true;
                }
            }
            i++;
            i2 = i3;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sortChildren(ListEntry listEntry, NotifComparator notifComparator) {
        if (listEntry instanceof GroupEntry) {
            ((ArrayList) ((GroupEntry) listEntry).mChildren).sort(notifComparator);
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
