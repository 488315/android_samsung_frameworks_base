package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewListener;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationGutsManager$$ExternalSyntheticLambda0 implements NotificationGuts.OnHeightChangedListener {
    public final /* synthetic */ NotificationGutsManager f$0;
    public final /* synthetic */ ExpandableNotificationRow f$1;

    public /* synthetic */ NotificationGutsManager$$ExternalSyntheticLambda0(NotificationGutsManager notificationGutsManager, ExpandableNotificationRow expandableNotificationRow) {
        this.f$0 = notificationGutsManager;
        this.f$1 = expandableNotificationRow;
    }

    public void onGutsClosed(NotificationGuts notificationGuts) {
        int i = NotificationGutsManager.$r8$clinit;
        NotificationGutsManager notificationGutsManager = this.f$0;
        ExpandableNotificationRow expandableNotificationRow = this.f$1;
        expandableNotificationRow.updateContentAccessibilityImportanceForGuts(true);
        expandableNotificationRow.mIsSnoozed = false;
        NotificationGuts.GutsContent gutsContent = notificationGuts.mGutsContent;
        if (!(gutsContent != null ? gutsContent.willBeRemoved() : false)) {
            ((NotificationStackScrollLayoutController.NotificationListContainerImpl) notificationGutsManager.mListContainer).onHeightChanged(expandableNotificationRow, true ^ ((StatusBarNotificationPresenter) notificationGutsManager.mPresenter).mPanelExpansionInteractor.isFullyCollapsed());
        }
        if (notificationGutsManager.mNotificationGutsExposed == notificationGuts) {
            notificationGutsManager.mNotificationGutsExposed = null;
        }
        NotifGutsViewListener notifGutsViewListener = notificationGutsManager.mGutsListener;
        if (notifGutsViewListener != null) {
            int i2 = NotificationBundleUi.$r8$clinit;
            notifGutsViewListener.onGutsClose(expandableNotificationRow.getEntryLegacy());
        }
        int i3 = NotificationBundleUi.$r8$clinit;
        ((HeadsUpManagerImpl) notificationGutsManager.mHeadsUpManager).setGutsShown(expandableNotificationRow.getEntryLegacy(), false);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.OnHeightChangedListener
    public void onHeightChanged() {
        NotificationListContainer notificationListContainer = this.f$0.mListContainer;
        ExpandableNotificationRow expandableNotificationRow = this.f$1;
        ((NotificationStackScrollLayoutController.NotificationListContainerImpl) notificationListContainer).onHeightChanged(expandableNotificationRow, expandableNotificationRow.isShown());
    }
}
