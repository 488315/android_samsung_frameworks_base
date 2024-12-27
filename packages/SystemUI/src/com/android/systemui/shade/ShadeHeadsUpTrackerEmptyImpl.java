package com.android.systemui.shade;

import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

public final class ShadeHeadsUpTrackerEmptyImpl implements ShadeHeadsUpTracker {
    @Override // com.android.systemui.shade.ShadeHeadsUpTracker
    public final ExpandableNotificationRow getTrackedHeadsUpNotification() {
        return null;
    }

    @Override // com.android.systemui.shade.ShadeHeadsUpTracker
    public final void addTrackingHeadsUpListener(Consumer consumer) {
    }

    @Override // com.android.systemui.shade.ShadeHeadsUpTracker
    public final void removeTrackingHeadsUpListener(HeadsUpAppearanceController$$ExternalSyntheticLambda0 headsUpAppearanceController$$ExternalSyntheticLambda0) {
    }

    @Override // com.android.systemui.shade.ShadeHeadsUpTracker
    public final void setHeadsUpAppearanceController(HeadsUpAppearanceController headsUpAppearanceController) {
    }
}
