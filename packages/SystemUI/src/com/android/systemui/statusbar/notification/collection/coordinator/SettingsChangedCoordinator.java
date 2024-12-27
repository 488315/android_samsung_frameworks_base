package com.android.systemui.statusbar.notification.collection.coordinator;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.util.SettingsHelper;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public final class SettingsChangedCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final SettingsHelper.OnChangedCallback mSettingsCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SettingsChangedCoordinator$mSettingsCallback$1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            if (Intrinsics.areEqual(uri, Settings.Secure.getUriFor(SettingsHelper.INDEX_SNOOZE_SETTING))) {
                SettingsChangedCoordinator.this.updateSnoozeButton();
            }
        }
    };
    private final NotifLiveDataStoreImpl notifLiveDataStoreImpl;

    public SettingsChangedCoordinator(NotifLiveDataStoreImpl notifLiveDataStoreImpl) {
        this.notifLiveDataStoreImpl = notifLiveDataStoreImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateSnoozeButton() {
        Iterator it = ((Iterable) this.notifLiveDataStoreImpl.activeNotifList.atomicValue.get()).iterator();
        while (it.hasNext()) {
            ExpandableNotificationRow expandableNotificationRow = ((NotificationEntry) it.next()).row;
            if (expandableNotificationRow != null) {
                for (NotificationContentView notificationContentView : expandableNotificationRow.mLayouts) {
                    notificationContentView.applySnoozeAction(notificationContentView.mExpandedChild);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSettingsCallback, Settings.Secure.getUriFor(SettingsHelper.INDEX_SNOOZE_SETTING));
        this.mSettingsCallback.onChanged(Settings.Secure.getUriFor(SettingsHelper.INDEX_SNOOZE_SETTING));
    }

    private static /* synthetic */ void getMSettingsCallback$annotations() {
    }
}
