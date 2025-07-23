package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.promoted.PromotedNotificationUi;
import com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor;
import java.util.Collections;
import java.util.List;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
public class ColorizedFgsCoordinator implements Coordinator {
    private static final String TAG = "ColorizedCoordinator";
    private final CoroutineScope mMainScope;
    private final PromotedNotificationsInteractor mPromotedNotificationsInteractor;
    private List<String> mOrderedPromotedNotifKeys = Collections.EMPTY_LIST;
    private final NotifPromoter mPromotedOngoingPromoter = new NotifPromoter(this, "PromotedOngoing") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ColorizedFgsCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter
        public boolean shouldPromoteToTopLevel(NotificationEntry notificationEntry) {
            return ColorizedFgsCoordinator.isPromotedOngoing(notificationEntry);
        }
    };
    private final NotifSectioner mNotifSectioner = new NotifSectioner("ColorizedSectioner", 8) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ColorizedFgsCoordinator.2
        private final NotifComparator mOngoingComparator = new NotifComparator("OngoingComparator") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ColorizedFgsCoordinator.2.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator, java.util.Comparator
            public int compare(PipelineEntry pipelineEntry, PipelineEntry pipelineEntry2) {
                return Integer.compare(getSortKey(pipelineEntry.getRepresentativeEntry()), getSortKey(pipelineEntry2.getRepresentativeEntry()));
            }
        };

        /* JADX INFO: Access modifiers changed from: private */
        public int getSortKey(NotificationEntry notificationEntry) {
            if (notificationEntry == null) {
                return Integer.MAX_VALUE;
            }
            int indexOf = ColorizedFgsCoordinator.this.mOrderedPromotedNotifKeys.indexOf(notificationEntry.mKey);
            return indexOf >= 0 ? indexOf : ColorizedFgsCoordinator.isPromotedOngoing(notificationEntry) ? 2147483646 : Integer.MAX_VALUE;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NotifComparator getComparator() {
            int i = PromotedNotificationUi.$r8$clinit;
            return null;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            if (representativeEntry != null) {
                if (representativeEntry.isInsignificant()) {
                    return false;
                }
                return ColorizedFgsCoordinator.isCall(representativeEntry);
            }
            if (BundleUtil.Companion.isClassified(representativeEntry)) {
                return false;
            }
            return ColorizedFgsCoordinator.isRichOngoing(representativeEntry) || ColorizedFgsCoordinator.this.isPromotedNotifChip(representativeEntry);
        }
    };

    public ColorizedFgsCoordinator(CoroutineScope coroutineScope, PromotedNotificationsInteractor promotedNotificationsInteractor) {
        this.mPromotedNotificationsInteractor = promotedNotificationsInteractor;
        this.mMainScope = coroutineScope;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCall(NotificationEntry notificationEntry) {
        return notificationEntry.mRanking.getImportance() > 1 && notificationEntry.mSbn.getNotification().isStyle(Notification.CallStyle.class);
    }

    private static boolean isColorizedForegroundService(NotificationEntry notificationEntry) {
        Notification notification2 = notificationEntry.mSbn.getNotification();
        return notification2.isForegroundService() && notification2.isColorized() && notificationEntry.mRanking.getImportance() > 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPromotedNotifChip(NotificationEntry notificationEntry) {
        int i = PromotedNotificationUi.$r8$clinit;
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPromotedOngoing(NotificationEntry notificationEntry) {
        return notificationEntry != null && notificationEntry.mSbn.getNotification().isPromotedOngoing();
    }

    public static boolean isRichOngoing(NotificationEntry notificationEntry) {
        return isPromotedOngoing(notificationEntry) || isColorizedForegroundService(notificationEntry) || isCall(notificationEntry);
    }

    private /* synthetic */ void lambda$attach$0(List list) {
        this.mOrderedPromotedNotifKeys = list;
        this.mNotifSectioner.invalidateList("updated mOrderedPromotedNotifKeys");
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        int i = PromotedNotificationUi.$r8$clinit;
    }

    public NotifSectioner getSectioner() {
        return this.mNotifSectioner;
    }
}
