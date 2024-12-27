package com.android.systemui.shade;

import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

public interface ShadeHeadsUpTracker {
    void addTrackingHeadsUpListener(Consumer consumer);

    ExpandableNotificationRow getTrackedHeadsUpNotification();

    void removeTrackingHeadsUpListener(HeadsUpAppearanceController$$ExternalSyntheticLambda0 headsUpAppearanceController$$ExternalSyntheticLambda0);

    void setHeadsUpAppearanceController(HeadsUpAppearanceController headsUpAppearanceController);
}
