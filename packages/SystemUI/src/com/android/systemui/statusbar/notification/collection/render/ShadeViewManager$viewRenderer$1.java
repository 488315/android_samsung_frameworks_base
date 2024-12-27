package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.LinkedHashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShadeViewManager$viewRenderer$1 {
    public final /* synthetic */ ShadeViewManager this$0;

    public ShadeViewManager$viewRenderer$1(ShadeViewManager shadeViewManager) {
        this.this$0 = shadeViewManager;
    }

    public final NotifViewController getGroupController(GroupEntry groupEntry) {
        NotifViewBarn notifViewBarn = this.this$0.viewBarn;
        NotificationEntry notificationEntry = groupEntry.mSummary;
        if (notificationEntry == null) {
            throw new IllegalStateException(("No Summary: " + groupEntry).toString());
        }
        LinkedHashMap linkedHashMap = (LinkedHashMap) notifViewBarn.rowMap;
        String str = notificationEntry.mKey;
        NotifViewController notifViewController = (NotifViewController) linkedHashMap.get(str);
        if (notifViewController != null) {
            return notifViewController;
        }
        throw new IllegalStateException(("No view has been registered for entry: " + str).toString());
    }

    public final NotifViewController getRowController(NotificationEntry notificationEntry) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) this.this$0.viewBarn.rowMap;
        String str = notificationEntry.mKey;
        NotifViewController notifViewController = (NotifViewController) linkedHashMap.get(str);
        if (notifViewController != null) {
            return notifViewController;
        }
        throw new IllegalStateException(("No view has been registered for entry: " + str).toString());
    }
}
