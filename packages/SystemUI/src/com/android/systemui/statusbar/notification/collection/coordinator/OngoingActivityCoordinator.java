package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.NotifRowController;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
public final class OngoingActivityCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final Context context;
    private int currentNotifIconCount;
    private final NotificationIconAreaController notificationIconAreaController;
    private final OngoingActivityController ongoingActivityController;
    private final NodeController ongoingActivityHeaderController;
    private final SettingsHelper settingsHelper;
    private final NotifTimeSortCoordnator timeSortCoordnator;
    private final NotifFilter ongoingSummary = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OngoingActivityCoordinator$ongoingSummary$1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return Intrinsics.areEqual(notificationEntry.mSbn.getTag(), "noti_DoNotDisturb") || notificationEntry.mSbn.getNotification().extras.getInt("android.ongoingActivityNoti.style", 0) == 2;
        }
    };
    private final OngoingActivityCoordinator$ongoingActivitySectioner$1 ongoingActivitySectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OngoingActivityCoordinator$ongoingActivitySectioner$1
        {
            super("OngoingActivity", 3);
        }

        private final void sortChildren(PipelineEntry pipelineEntry, NotifComparator notifComparator) {
            if (pipelineEntry instanceof GroupEntry) {
                ((ArrayList) ((GroupEntry) pipelineEntry).mChildren).sort(notifComparator);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NotifComparator getComparator() {
            return OngoingActivityCoordinator.this.getTimeSortCoordnator().getTimeComparator();
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            return OngoingActivityCoordinator.this.getOngoingActivityHeaderController();
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            NotificationEntry representativeEntry;
            if (OngoingActivityCoordinator.this.getSettingsHelper().getNotificationSortOrderValue() == 1) {
                sortChildren(pipelineEntry, getComparator());
            }
            NotificationEntry representativeEntry2 = pipelineEntry.getRepresentativeEntry();
            return representativeEntry2 != null && representativeEntry2.isOngoingActivity() && (representativeEntry = pipelineEntry.getRepresentativeEntry()) != null && representativeEntry.isPromotedState();
        }
    };

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.OngoingActivityCoordinator$ongoingActivitySectioner$1] */
    public OngoingActivityCoordinator(Context context, NotifTimeSortCoordnator notifTimeSortCoordnator, NodeController nodeController, OngoingActivityController ongoingActivityController, NotificationIconAreaController notificationIconAreaController, SettingsHelper settingsHelper) {
        this.context = context;
        this.timeSortCoordnator = notifTimeSortCoordnator;
        this.ongoingActivityHeaderController = nodeController;
        this.ongoingActivityController = ongoingActivityController;
        this.notificationIconAreaController = notificationIconAreaController;
        this.settingsHelper = settingsHelper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
        int showingIconCount = this.notificationIconAreaController.getShowingIconCount();
        if (this.currentNotifIconCount != showingIconCount) {
            this.currentNotifIconCount = showingIconCount;
            this.ongoingActivityController.updateAdapter();
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addFinalizeFilter(this.ongoingSummary);
        ((ArrayList) notifPipeline.mRenderStageManager.onAfterRenderEntryListeners).add(new OnAfterRenderEntryListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.OngoingActivityCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener
            public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
                OngoingActivityCoordinator.this.onAfterRenderEntry(notificationEntry, notifRowController);
            }
        });
    }

    public final Context getContext() {
        return this.context;
    }

    public final int getCurrentNotifIconCount() {
        return this.currentNotifIconCount;
    }

    public final NotificationIconAreaController getNotificationIconAreaController() {
        return this.notificationIconAreaController;
    }

    public final OngoingActivityController getOngoingActivityController() {
        return this.ongoingActivityController;
    }

    public final NodeController getOngoingActivityHeaderController() {
        return this.ongoingActivityHeaderController;
    }

    public final NotifSectioner getOngoingActivitySectioner() {
        return this.ongoingActivitySectioner;
    }

    public final SettingsHelper getSettingsHelper() {
        return this.settingsHelper;
    }

    public final NotifTimeSortCoordnator getTimeSortCoordnator() {
        return this.timeSortCoordnator;
    }

    public final void setCurrentNotifIconCount(int i) {
        this.currentNotifIconCount = i;
    }

    public static /* synthetic */ void getCurrentNotifIconCount$annotations() {
    }
}
