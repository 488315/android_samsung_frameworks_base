package com.android.systemui.shade;

import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
