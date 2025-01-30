package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class HeadsUpAppearanceController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HeadsUpAppearanceController f$0;

    public /* synthetic */ HeadsUpAppearanceController$$ExternalSyntheticLambda0(HeadsUpAppearanceController headsUpAppearanceController, int i) {
        this.$r8$classId = i;
        this.f$0 = headsUpAppearanceController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                HeadsUpAppearanceController headsUpAppearanceController = this.f$0;
                ExpandableNotificationRow expandableNotificationRow = headsUpAppearanceController.mTrackedChild;
                headsUpAppearanceController.mTrackedChild = (ExpandableNotificationRow) obj;
                if (expandableNotificationRow != null) {
                    NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                    headsUpAppearanceController.updateHeader(notificationEntry);
                    headsUpAppearanceController.updateHeadsUpAndPulsingRoundness(notificationEntry);
                    break;
                }
                break;
            case 1:
                this.f$0.hide((View) obj, 4, null);
                break;
            case 2:
                this.f$0.show((View) obj);
                break;
            default:
                HeadsUpAppearanceController headsUpAppearanceController2 = this.f$0;
                NotificationEntry notificationEntry2 = (NotificationEntry) obj;
                headsUpAppearanceController2.updateHeader(notificationEntry2);
                headsUpAppearanceController2.updateHeadsUpAndPulsingRoundness(notificationEntry2);
                break;
        }
    }
}
