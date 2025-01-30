package com.android.systemui.statusbar.notification.collection.coordinator;

import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.util.SettingsHelper;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SettingsChangedCoordinator$mSettingsCallback$1 implements SettingsHelper.OnChangedCallback {
    public final /* synthetic */ SettingsChangedCoordinator this$0;

    public SettingsChangedCoordinator$mSettingsCallback$1(SettingsChangedCoordinator settingsChangedCoordinator) {
        this.this$0 = settingsChangedCoordinator;
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        if (Intrinsics.areEqual(uri, Settings.Secure.getUriFor("show_notification_snooze"))) {
            Iterator it = ((Iterable) this.this$0.notifLiveDataStoreImpl.activeNotifList.getValue()).iterator();
            while (it.hasNext()) {
                ExpandableNotificationRow expandableNotificationRow = ((NotificationEntry) it.next()).row;
                if (expandableNotificationRow != null) {
                    for (NotificationContentView notificationContentView : expandableNotificationRow.mLayouts) {
                        notificationContentView.applySnoozeAction(notificationContentView.mExpandedChild);
                    }
                }
            }
        }
    }
}
