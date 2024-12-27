package com.android.systemui.shade;

import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
