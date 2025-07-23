package com.android.systemui.statusbar.policy;

import android.os.RemoteException;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class RemoteInputUriController {
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
