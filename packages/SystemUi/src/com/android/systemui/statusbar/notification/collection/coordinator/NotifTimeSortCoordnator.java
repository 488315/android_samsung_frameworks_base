package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence$iterator$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifTimeSortCoordnator implements Coordinator {
    public final SettingsHelper settingsHelper;
    public final SystemClock systemClock;
    public final String[] pkgArray = {"com.samsung.android.incallui", "com.skt.prod.dialer", "com.android.systemui"};
    public final String[] channelArray = {"Ongoing_call", "NO_HUN_CHANNEL_CALL_CONTROL", "ZEN_ONGOING"};
    public final NotifTimeSortCoordnator$sectionerForPriority$1 sectionerForPriority = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$sectionerForPriority$1
        {
            super("TimeOrderPriority", 2);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final NotifComparator getComparator() {
            return new NotifTimeSortCoordnator$sectionerForPriority$1$getComparator$1(NotifTimeSortCoordnator.this);
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x0054, code lost:
        
            if (r0 != false) goto L25;
         */
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean isInSection(ListEntry listEntry) {
            boolean z;
            NotificationChannel channel;
            NotifTimeSortCoordnator notifTimeSortCoordnator = NotifTimeSortCoordnator.this;
            boolean z2 = true;
            if (notifTimeSortCoordnator.settingsHelper.mItemLists.get("notification_sort_order").getIntValue() == 1) {
                NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
                String[] strArr = notifTimeSortCoordnator.pkgArray;
                int length = strArr.length;
                int i = 0;
                int i2 = 0;
                while (true) {
                    if (i >= length) {
                        z = false;
                        break;
                    }
                    String str = strArr[i];
                    int i3 = i2 + 1;
                    String str2 = null;
                    StatusBarNotification statusBarNotification = representativeEntry != null ? representativeEntry.mSbn : null;
                    Intrinsics.checkNotNull(statusBarNotification);
                    if (str.equals(statusBarNotification.getPackageName())) {
                        String str3 = notifTimeSortCoordnator.channelArray[i2];
                        if (representativeEntry != null && (channel = representativeEntry.getChannel()) != null) {
                            str2 = channel.getId();
                        }
                        if (str3.equals(str2)) {
                            z = true;
                            break;
                        }
                    }
                    i++;
                    i2 = i3;
                }
            }
            z2 = false;
            if (z2) {
                NotifTimeSortCoordnator$sectionerForPriority$1$getComparator$1 notifTimeSortCoordnator$sectionerForPriority$1$getComparator$1 = new NotifTimeSortCoordnator$sectionerForPriority$1$getComparator$1(notifTimeSortCoordnator);
                if (listEntry instanceof GroupEntry) {
                    ((ArrayList) ((GroupEntry) listEntry).mChildren).sort(notifTimeSortCoordnator$sectionerForPriority$1$getComparator$1);
                }
            }
            return z2;
        }
    };
    public final NotifTimeSortCoordnator$sectioner$1 sectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$sectioner$1
        {
            super("TimeOrder", 3);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final NotifComparator getComparator() {
            return new NotifTimeSortCoordnator$sectioner$1$getComparator$1(NotifTimeSortCoordnator.this);
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final boolean isInSection(ListEntry listEntry) {
            NotifTimeSortCoordnator notifTimeSortCoordnator = NotifTimeSortCoordnator.this;
            boolean z = notifTimeSortCoordnator.settingsHelper.mItemLists.get("notification_sort_order").getIntValue() == 1;
            if (z) {
                NotifTimeSortCoordnator$sectioner$1$getComparator$1 notifTimeSortCoordnator$sectioner$1$getComparator$1 = new NotifTimeSortCoordnator$sectioner$1$getComparator$1(notifTimeSortCoordnator);
                if (listEntry instanceof GroupEntry) {
                    ((ArrayList) ((GroupEntry) listEntry).mChildren).sort(notifTimeSortCoordnator$sectioner$1$getComparator$1);
                }
            }
            return z;
        }
    };

    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$sectionerForPriority$1] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$sectioner$1] */
    public NotifTimeSortCoordnator(SettingsHelper settingsHelper, SystemClock systemClock) {
        this.settingsHelper = settingsHelper;
        this.systemClock = systemClock;
    }

    public static long getTime(NotificationEntry notificationEntry) {
        if (notificationEntry == null) {
            return 0L;
        }
        long j = notificationEntry.mSbn.getNotification().when;
        return j > 0 ? j : notificationEntry.mSbn.getPostTime();
    }

    public final long calculateRepresentativeNotificationTime(ListEntry listEntry) {
        if (!(listEntry instanceof GroupEntry)) {
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            Intrinsics.checkNotNull(representativeEntry);
            return getTime(representativeEntry);
        }
        ((SystemClockImpl) this.systemClock).getClass();
        long currentTimeMillis = System.currentTimeMillis();
        GroupEntry groupEntry = (GroupEntry) listEntry;
        TransformingSequence$iterator$1 transformingSequence$iterator$1 = new TransformingSequence$iterator$1(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(groupEntry.mUnmodifiableChildren), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifTimeSortCoordnator$calculateGroupNotificationTime$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                NotifTimeSortCoordnator.this.getClass();
                return Long.valueOf(NotifTimeSortCoordnator.getTime((NotificationEntry) obj));
            }
        }));
        long j = Long.MIN_VALUE;
        long j2 = Long.MAX_VALUE;
        while (transformingSequence$iterator$1.hasNext()) {
            long longValue = ((Number) transformingSequence$iterator$1.next()).longValue();
            if (currentTimeMillis - longValue > 0) {
                j = Math.max(j, longValue);
            } else {
                j2 = Math.min(j2, longValue);
            }
        }
        if (j == Long.MIN_VALUE && j2 == Long.MAX_VALUE) {
            NotificationEntry notificationEntry = groupEntry.mSummary;
            Intrinsics.checkNotNull(notificationEntry);
            return getTime(notificationEntry);
        }
        if (j2 != Long.MAX_VALUE) {
            j = j2;
        }
        return j;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
    }
}
