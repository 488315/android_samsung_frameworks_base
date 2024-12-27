package com.android.systemui.statusbar.policy;

import android.os.RemoteException;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RemoteInputUriController {
    public final IStatusBarService mStatusBarManagerService;

    public RemoteInputUriController(IStatusBarService iStatusBarService) {
        new NotifCollectionListener() { // from class: com.android.systemui.statusbar.policy.RemoteInputUriController.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                try {
                    RemoteInputUriController.this.mStatusBarManagerService.clearInlineReplyUriPermissions(notificationEntry.mKey);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        };
        this.mStatusBarManagerService = iStatusBarService;
    }
}
