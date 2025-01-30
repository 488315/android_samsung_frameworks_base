package com.android.systemui.statusbar.policy;

import android.os.RemoteException;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
