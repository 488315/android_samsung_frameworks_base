package com.android.systemui.statusbar.notification.row;

import android.view.View;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ExpandableNotificationRow$$ExternalSyntheticLambda4 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ExpandableNotificationRow f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ExpandableNotificationRow$$ExternalSyntheticLambda4(ExpandableNotificationRow expandableNotificationRow, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = expandableNotificationRow;
        this.f$1 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                ExpandableNotificationRow expandableNotificationRow = this.f$0;
                NotificationMenuRowPlugin.MenuItem menuItem = (NotificationMenuRowPlugin.MenuItem) this.f$1;
                expandableNotificationRow.mNotificationGutsManager.closeAndSaveGuts(true, false, false, false);
                expandableNotificationRow.mNotificationGutsManager.openGuts(expandableNotificationRow, 0, 0, menuItem);
                expandableNotificationRow.mIsSnoozed = true;
                break;
            default:
                ExpandableNotificationRow.m2221$r8$lambda$m9ShXoKTwBLcNnosIeqOTeZZvE(this.f$0, (ExpandableNotificationRowController$$ExternalSyntheticLambda0) this.f$1, view);
                break;
        }
    }
}
