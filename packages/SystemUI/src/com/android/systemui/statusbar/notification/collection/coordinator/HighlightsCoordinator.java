package com.android.systemui.statusbar.notification.collection.coordinator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.net.Uri;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.SectionHeaderView;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SystemSettings;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public class HighlightsCoordinator implements Coordinator {
    private final Lazy mCommonNotifCollectionLazy;
    private Context mContext;
    private final NodeController mHighlightsController;
    private NotifPipeline mNotifPipeline;
    private boolean mSettingEnabled;
    private StatusBarStateController mStatusBarStateController;
    private SystemSettings mSystemSettings;
    private final String TAG = "HighlightsCoordinator";
    private boolean DEBUG = false;
    private final String SETTING_CONTENT = SettingsHelper.INDEX_NOTI_SETTINGS_HIGHLIGHTS;
    private boolean mHighlightsSectionShowing = false;
    private final SettingsHelper.OnChangedCallback mSettingsChangedListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HighlightsCoordinator.1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public void onChanged(Uri uri) {
            ExpandableNotificationRow expandableNotificationRow;
            HighlightsCoordinator.this.mSettingEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableHighlights();
            Log.d("HighlightsCoordinator", "mSettingValue: " + HighlightsCoordinator.this.mSettingEnabled);
            for (NotificationEntry notificationEntry : ((NotifPipeline) ((CommonNotifCollection) HighlightsCoordinator.this.mCommonNotifCollectionLazy.get())).getAllNotifs()) {
                if ((notificationEntry.mSbn.getNotification().semFlags & 131072) != 0 && (expandableNotificationRow = notificationEntry.row) != null) {
                    expandableNotificationRow.updateBackgroundColors();
                }
            }
            HighlightsCoordinator.this.mNotifPipeline.mShadeListBuilder.buildList();
        }
    };
    private final NotifFilter mNotifFilter = new NotifFilter("HighlightsCoordinator") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HighlightsCoordinator.2
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            String str;
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            if (!statusBarNotification.getNotification().isGroupSummary()) {
                return false;
            }
            String groupKey = statusBarNotification.getGroupKey();
            Iterator it = ((NotifPipeline) ((CommonNotifCollection) HighlightsCoordinator.this.mCommonNotifCollectionLazy.get())).getAllNotifs().iterator();
            char c = 0;
            while (true) {
                boolean hasNext = it.hasNext();
                str = notificationEntry.mKey;
                if (!hasNext) {
                    break;
                }
                NotificationEntry notificationEntry2 = (NotificationEntry) it.next();
                if (!notificationEntry2.mKey.equals(str) && groupKey.equals(notificationEntry2.mSbn.getGroupKey())) {
                    if (!notificationEntry2.isHighlightsStyle() && !notificationEntry2.isInsignificant()) {
                        c = 2;
                    } else if (c != 2) {
                        c = 1;
                    }
                }
            }
            if (c == 1) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(" filter out group summary : ", str, "HighlightsCoordinator");
            }
            return c == 1;
        }
    };
    private final NotifSectioner mHighlightsSectioner = new NotifSectioner("Highlights", 4) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HighlightsCoordinator.3
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            HighlightsCoordinator.this.mHighlightsController.getView();
            return HighlightsCoordinator.this.mHighlightsController;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            return representativeEntry != null && representativeEntry.isHighlightsStyle();
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public void onEntriesUpdated(List<ListEntry> list) {
            HighlightsCoordinator.this.mHighlightsSectionShowing = !list.isEmpty();
            if (HighlightsCoordinator.this.mHighlightsSectionShowing) {
                return;
            }
            SectionHeaderView sectionHeaderView = HighlightsCoordinator.this.mHighlightsController.getView() instanceof SectionHeaderView ? (SectionHeaderView) HighlightsCoordinator.this.mHighlightsController.getView() : null;
            if (sectionHeaderView != null) {
                Iterator it = ((ArrayList) sectionHeaderView.mLabelView.shaderSpans).iterator();
                while (it.hasNext()) {
                    ValueAnimator valueAnimator = (ValueAnimator) ((Pair) it.next()).component2();
                    if (valueAnimator.isRunning()) {
                        valueAnimator.cancel();
                    }
                }
            }
        }
    };

    public HighlightsCoordinator(NodeController nodeController, Lazy lazy, Context context, SystemSettings systemSettings, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarStateController statusBarStateController) {
        this.mHighlightsController = nodeController;
        this.mCommonNotifCollectionLazy = lazy;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mNotifPipeline = notifPipeline;
        notifPipeline.addFinalizeFilter(this.mNotifFilter);
    }

    public NotifSectioner getHighlightsSection() {
        return this.mHighlightsSectioner;
    }

    public void onPanelStateChanged(int i) {
    }
}
