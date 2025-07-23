package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpAppearanceController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HeadsUpAppearanceController f$0;

    public /* synthetic */ HeadsUpAppearanceController$$ExternalSyntheticLambda0(HeadsUpAppearanceController headsUpAppearanceController, int i) {
        this.$r8$classId = i;
        this.f$0 = headsUpAppearanceController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        HeadsUpAppearanceController headsUpAppearanceController = this.f$0;
        switch (i) {
            case 0:
                SourceType$Companion$from$1 sourceType$Companion$from$1 = HeadsUpAppearanceController.HEADS_UP;
                headsUpAppearanceController.hide((View) obj, 4, null);
                break;
            case 1:
                SourceType$Companion$from$1 sourceType$Companion$from$12 = HeadsUpAppearanceController.HEADS_UP;
                headsUpAppearanceController.hide((View) obj, 4, null);
                break;
            case 2:
                SourceType$Companion$from$1 sourceType$Companion$from$13 = HeadsUpAppearanceController.HEADS_UP;
                headsUpAppearanceController.show((View) obj);
                break;
            case 3:
                ExpandableNotificationRow expandableNotificationRow = headsUpAppearanceController.mTrackedChild;
                headsUpAppearanceController.mTrackedChild = (ExpandableNotificationRow) obj;
                if (expandableNotificationRow != null) {
                    headsUpAppearanceController.updateHeader(expandableNotificationRow);
                    headsUpAppearanceController.updateHeadsUpAndPulsingRoundness(expandableNotificationRow);
                    break;
                }
                break;
            default:
                NotificationEntry notificationEntry = (NotificationEntry) obj;
                SourceType$Companion$from$1 sourceType$Companion$from$14 = HeadsUpAppearanceController.HEADS_UP;
                headsUpAppearanceController.getClass();
                headsUpAppearanceController.updateHeader(notificationEntry.row);
                headsUpAppearanceController.updateHeadsUpAndPulsingRoundness(notificationEntry.row);
                break;
        }
    }
}
