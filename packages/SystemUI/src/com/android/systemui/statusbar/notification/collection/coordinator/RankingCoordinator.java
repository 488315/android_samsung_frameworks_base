package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.BundleEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl;
import com.android.systemui.statusbar.notification.stack.SectionHeaderView;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.List;

@CoordinatorScope
/* loaded from: classes3.dex */
public class RankingCoordinator implements Coordinator {
    public static final boolean SHOW_ALL_SECTIONS = false;
    private final NodeController mAlertingHeaderController;
    private boolean mHasMinimizedEntries;
    private boolean mHasSilentEntries;
    private final HighPriorityProvider mHighPriorityProvider;
    private final NotifSectioner mMinimizedNotifSectioner;
    private final SectionHeaderController mSilentHeaderController;
    private final NodeController mSilentNodeController;
    private final NotifSectioner mSilentNotifSectioner;
    private final StatusBarStateController mStatusBarStateController;
    private final NotifSectioner mAlertingNotifSectioner = new NotifSectioner(this, "Alerting", 15) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            return null;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            if (BundleUtil.Companion.isClassified(pipelineEntry)) {
                return false;
            }
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            return representativeEntry == null || !representativeEntry.isInsignificant();
        }
    };
    private final NotifFilter mSuspendedFilter = new NotifFilter(this, "IsSuspendedFilter") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.4
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return notificationEntry.mRanking.isSuspended();
        }
    };
    private final NotifFilter mDndVisualEffectsFilter = new NotifFilter("DndSuppressingVisualEffects") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.5
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            if ((RankingCoordinator.this.mStatusBarStateController.isDozing() || RankingCoordinator.this.mStatusBarStateController.getDozeAmount() == 1.0f) && notificationEntry.shouldSuppressVisualEffect(128)) {
                return true;
            }
            return notificationEntry.shouldSuppressVisualEffect(256);
        }
    };
    private final NotifFilter mDndPreGroupFilter = new NotifFilter(this, "DndPreGroupFilter") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.6
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return notificationEntry.shouldSuppressVisualEffect(256) && notificationEntry.shouldSuppressVisualEffect(128);
        }
    };
    private final StatusBarStateController.StateListener mStatusBarStateCallback = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.7
        private boolean mPrevDozeAmountIsOne = false;

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public void onDozeAmountChanged(float f, float f2) {
            super.onDozeAmountChanged(f, f2);
            boolean z = f == 1.0f;
            if (this.mPrevDozeAmountIsOne != z) {
                RankingCoordinator.this.mDndVisualEffectsFilter.invalidateList("dozeAmount changed to ".concat(z ? "one" : "not one"));
                this.mPrevDozeAmountIsOne = z;
            }
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public void onDozingChanged(boolean z) {
            RankingCoordinator.this.mDndVisualEffectsFilter.invalidateList("onDozingChanged to " + z);
        }
    };

    public RankingCoordinator(StatusBarStateController statusBarStateController, HighPriorityProvider highPriorityProvider, NodeController nodeController, SectionHeaderController sectionHeaderController, NodeController nodeController2) {
        int i = 20;
        this.mSilentNotifSectioner = new NotifSectioner(SystemUIAnalytics.DID_NOTI_SELECT_SILENT, i) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NodeController getHeaderNodeController() {
                return RankingCoordinator.this.mSilentNodeController;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public boolean isInSection(PipelineEntry pipelineEntry) {
                if (pipelineEntry instanceof BundleEntry) {
                    return true;
                }
                return (BundleUtil.Companion.isClassified(pipelineEntry) || RankingCoordinator.this.mHighPriorityProvider.isHighPriority(pipelineEntry, true) || pipelineEntry.getRepresentativeEntry() == null || pipelineEntry.getRepresentativeEntry().mRanking.isAmbient()) ? false : true;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public void onEntriesUpdated(List<PipelineEntry> list) {
                RankingCoordinator.this.mHasSilentEntries = false;
                int i2 = 0;
                while (true) {
                    if (i2 >= list.size()) {
                        break;
                    }
                    NotificationEntry representativeEntry = list.get(i2).getRepresentativeEntry();
                    if (representativeEntry != null && representativeEntry.mSbn.isClearable()) {
                        RankingCoordinator.this.mHasSilentEntries = true;
                        break;
                    }
                    i2++;
                }
                SectionHeaderController sectionHeaderController2 = RankingCoordinator.this.mSilentHeaderController;
                boolean z = RankingCoordinator.this.mHasMinimizedEntries | RankingCoordinator.this.mHasSilentEntries;
                SectionHeaderNodeControllerImpl sectionHeaderNodeControllerImpl = (SectionHeaderNodeControllerImpl) sectionHeaderController2;
                sectionHeaderNodeControllerImpl.clearAllButtonEnabled = z;
                SectionHeaderView sectionHeaderView = sectionHeaderNodeControllerImpl._view;
                if (sectionHeaderView != null) {
                    sectionHeaderView.mClearAllButton.setVisibility(z ? 0 : 8);
                }
            }
        };
        this.mMinimizedNotifSectioner = new NotifSectioner("Minimized", i) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.3
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NodeController getHeaderNodeController() {
                return RankingCoordinator.this.mSilentNodeController;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public boolean isInSection(PipelineEntry pipelineEntry) {
                return (BundleUtil.Companion.isClassified(pipelineEntry) || RankingCoordinator.this.mHighPriorityProvider.isHighPriority(pipelineEntry, true) || pipelineEntry.getRepresentativeEntry() == null || !pipelineEntry.getRepresentativeEntry().mRanking.isAmbient()) ? false : true;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public void onEntriesUpdated(List<PipelineEntry> list) {
                RankingCoordinator.this.mHasMinimizedEntries = false;
                int i2 = 0;
                while (true) {
                    if (i2 >= list.size()) {
                        break;
                    }
                    NotificationEntry representativeEntry = list.get(i2).getRepresentativeEntry();
                    if (representativeEntry != null && representativeEntry.mSbn.isClearable()) {
                        RankingCoordinator.this.mHasMinimizedEntries = true;
                        break;
                    }
                    i2++;
                }
                SectionHeaderController sectionHeaderController2 = RankingCoordinator.this.mSilentHeaderController;
                boolean z = RankingCoordinator.this.mHasMinimizedEntries | RankingCoordinator.this.mHasSilentEntries;
                SectionHeaderNodeControllerImpl sectionHeaderNodeControllerImpl = (SectionHeaderNodeControllerImpl) sectionHeaderController2;
                sectionHeaderNodeControllerImpl.clearAllButtonEnabled = z;
                SectionHeaderView sectionHeaderView = sectionHeaderNodeControllerImpl._view;
                if (sectionHeaderView != null) {
                    sectionHeaderView.mClearAllButton.setVisibility(z ? 0 : 8);
                }
            }
        };
        this.mStatusBarStateController = statusBarStateController;
        this.mHighPriorityProvider = highPriorityProvider;
        this.mAlertingHeaderController = nodeController;
        this.mSilentNodeController = nodeController2;
        this.mSilentHeaderController = sectionHeaderController;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mStatusBarStateController.addCallback(this.mStatusBarStateCallback);
        notifPipeline.addPreGroupFilter(this.mSuspendedFilter);
        notifPipeline.addPreGroupFilter(this.mDndVisualEffectsFilter);
    }

    public NotifSectioner getAlertingSectioner() {
        return this.mAlertingNotifSectioner;
    }

    public NotifSectioner getMinimizedSectioner() {
        return this.mMinimizedNotifSectioner;
    }

    public NotifSectioner getSilentSectioner() {
        return this.mSilentNotifSectioner;
    }
}
