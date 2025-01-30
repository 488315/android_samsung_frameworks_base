package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl;
import com.android.systemui.statusbar.notification.stack.SectionHeaderView;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RankingCoordinator implements Coordinator {
    public boolean mHasMinimizedEntries;
    public boolean mHasSilentEntries;
    public final HighPriorityProvider mHighPriorityProvider;
    public final C28293 mMinimizedNotifSectioner;
    public final SectionHeaderController mSilentHeaderController;
    public final NodeController mSilentNodeController;
    public final C28282 mSilentNotifSectioner;
    public final StatusBarStateController mStatusBarStateController;
    public final C28271 mAlertingNotifSectioner = new NotifSectioner(this, "Alerting", 8) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final NodeController getHeaderNodeController() {
            return null;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final boolean isInSection(ListEntry listEntry) {
            return true;
        }
    };
    public final C28304 mSuspendedFilter = new NotifFilter(this, "IsSuspendedFilter") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.4
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return notificationEntry.mRanking.isSuspended();
        }
    };
    public final C28315 mDndVisualEffectsFilter = new NotifFilter("DndSuppressingVisualEffects") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.5
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            RankingCoordinator rankingCoordinator = RankingCoordinator.this;
            if ((rankingCoordinator.mStatusBarStateController.isDozing() || rankingCoordinator.mStatusBarStateController.getDozeAmount() == 1.0f) && notificationEntry.shouldSuppressVisualEffect(128)) {
                return true;
            }
            return notificationEntry.shouldSuppressVisualEffect(256);
        }
    };
    public final C28326 mStatusBarStateCallback = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.6
        public boolean mPrevDozeAmountIsOne = false;

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozeAmountChanged(float f, float f2) {
            super.onDozeAmountChanged(f, f2);
            boolean z = f == 1.0f;
            if (this.mPrevDozeAmountIsOne != z) {
                invalidateList("dozeAmount changed to ".concat(z ? "one" : "not one"));
                this.mPrevDozeAmountIsOne = z;
            }
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozingChanged(boolean z) {
            invalidateList("onDozingChanged to " + z);
        }
    };

    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator$1] */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator$2] */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator$3] */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator$4] */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator$5] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator$6] */
    public RankingCoordinator(StatusBarStateController statusBarStateController, HighPriorityProvider highPriorityProvider, NodeController nodeController, SectionHeaderController sectionHeaderController, NodeController nodeController2) {
        int i = 9;
        this.mSilentNotifSectioner = new NotifSectioner("Silent", i) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public final NodeController getHeaderNodeController() {
                return RankingCoordinator.this.mSilentNodeController;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public final boolean isInSection(ListEntry listEntry) {
                return (RankingCoordinator.this.mHighPriorityProvider.isHighPriority(listEntry, true) || listEntry.getRepresentativeEntry().isAmbient()) ? false : true;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public final void onEntriesUpdated(List list) {
                RankingCoordinator rankingCoordinator = RankingCoordinator.this;
                rankingCoordinator.mHasSilentEntries = false;
                int i2 = 0;
                while (true) {
                    ArrayList arrayList = (ArrayList) list;
                    if (i2 >= arrayList.size()) {
                        break;
                    }
                    if (((ListEntry) arrayList.get(i2)).getRepresentativeEntry().mSbn.isClearable()) {
                        rankingCoordinator.mHasSilentEntries = true;
                        break;
                    }
                    i2++;
                }
                boolean z = rankingCoordinator.mHasSilentEntries | rankingCoordinator.mHasMinimizedEntries;
                SectionHeaderNodeControllerImpl sectionHeaderNodeControllerImpl = (SectionHeaderNodeControllerImpl) rankingCoordinator.mSilentHeaderController;
                sectionHeaderNodeControllerImpl.clearAllButtonEnabled = z;
                SectionHeaderView sectionHeaderView = sectionHeaderNodeControllerImpl._view;
                if (sectionHeaderView != null) {
                    sectionHeaderView.mClearAllButton.setVisibility(z ? 0 : 8);
                }
            }
        };
        this.mMinimizedNotifSectioner = new NotifSectioner("Minimized", i) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.3
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public final NodeController getHeaderNodeController() {
                return RankingCoordinator.this.mSilentNodeController;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public final boolean isInSection(ListEntry listEntry) {
                return !RankingCoordinator.this.mHighPriorityProvider.isHighPriority(listEntry, true) && listEntry.getRepresentativeEntry().isAmbient();
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public final void onEntriesUpdated(List list) {
                RankingCoordinator rankingCoordinator = RankingCoordinator.this;
                rankingCoordinator.mHasMinimizedEntries = false;
                int i2 = 0;
                while (true) {
                    ArrayList arrayList = (ArrayList) list;
                    if (i2 >= arrayList.size()) {
                        break;
                    }
                    if (((ListEntry) arrayList.get(i2)).getRepresentativeEntry().mSbn.isClearable()) {
                        rankingCoordinator.mHasMinimizedEntries = true;
                        break;
                    }
                    i2++;
                }
                boolean z = rankingCoordinator.mHasSilentEntries | rankingCoordinator.mHasMinimizedEntries;
                SectionHeaderNodeControllerImpl sectionHeaderNodeControllerImpl = (SectionHeaderNodeControllerImpl) rankingCoordinator.mSilentHeaderController;
                sectionHeaderNodeControllerImpl.clearAllButtonEnabled = z;
                SectionHeaderView sectionHeaderView = sectionHeaderNodeControllerImpl._view;
                if (sectionHeaderView != null) {
                    sectionHeaderView.mClearAllButton.setVisibility(z ? 0 : 8);
                }
            }
        };
        this.mStatusBarStateController = statusBarStateController;
        this.mHighPriorityProvider = highPriorityProvider;
        this.mSilentNodeController = nodeController2;
        this.mSilentHeaderController = sectionHeaderController;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        this.mStatusBarStateController.addCallback(this.mStatusBarStateCallback);
        notifPipeline.addPreGroupFilter(this.mSuspendedFilter);
        notifPipeline.addPreGroupFilter(this.mDndVisualEffectsFilter);
    }
}
