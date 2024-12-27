package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.NotiRune;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
/* loaded from: classes2.dex */
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
    private final NotifSectioner mAlertingNotifSectioner = new NotifSectioner("Alerting", 12) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            return null;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            NotificationEntry representativeEntry;
            return (NotiRune.NOTI_INSIGNIFICANT && (representativeEntry = listEntry.getRepresentativeEntry()) != null && representativeEntry.isInsignificant()) ? false : true;
        }
    };
    private final NotifFilter mSuspendedFilter = new NotifFilter("IsSuspendedFilter") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.4
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
    private final StatusBarStateController.StateListener mStatusBarStateCallback = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.6
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
        int i = 13;
        this.mSilentNotifSectioner = new NotifSectioner(SystemUIAnalytics.DID_NOTI_SELECT_SILENT, i) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NodeController getHeaderNodeController() {
                return RankingCoordinator.this.mSilentNodeController;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public boolean isInSection(ListEntry listEntry) {
                return (RankingCoordinator.this.mHighPriorityProvider.isHighPriority(listEntry, true) || listEntry.getRepresentativeEntry().mRanking.isAmbient()) ? false : true;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public void onEntriesUpdated(List<ListEntry> list) {
                RankingCoordinator.this.mHasSilentEntries = false;
                int i2 = 0;
                while (true) {
                    if (i2 >= list.size()) {
                        break;
                    }
                    if (list.get(i2).getRepresentativeEntry().mSbn.isClearable()) {
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
            public boolean isInSection(ListEntry listEntry) {
                return !RankingCoordinator.this.mHighPriorityProvider.isHighPriority(listEntry, true) && listEntry.getRepresentativeEntry().mRanking.isAmbient();
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public void onEntriesUpdated(List<ListEntry> list) {
                RankingCoordinator.this.mHasMinimizedEntries = false;
                int i2 = 0;
                while (true) {
                    if (i2 >= list.size()) {
                        break;
                    }
                    if (list.get(i2).getRepresentativeEntry().mSbn.isClearable()) {
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
