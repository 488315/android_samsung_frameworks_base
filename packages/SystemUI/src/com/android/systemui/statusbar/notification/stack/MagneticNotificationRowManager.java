package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface MagneticNotificationRowManager {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
