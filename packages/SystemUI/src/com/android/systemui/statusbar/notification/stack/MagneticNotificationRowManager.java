package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* loaded from: classes3.dex */
public interface MagneticNotificationRowManager {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    void onDensityChange(float f);

    void onMagneticInteractionEnd(ExpandableNotificationRow expandableNotificationRow, Float f);

    void reset();

    void resetRoundness();

    void setMagneticAndRoundableTargets(ExpandableNotificationRow expandableNotificationRow, NotificationStackScrollLayout notificationStackScrollLayout, NotificationSectionsManager notificationSectionsManager);

    boolean setMagneticRowTranslation(ExpandableNotificationRow expandableNotificationRow, float f);
}
