package com.android.systemui.notifications.ui.composable;

import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.LowestZIndexContentPicker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Notifications$Elements {
    public static final ElementKey HeadsUpNotificationPlaceholder = null;
    public static final Notifications$Elements INSTANCE = new Notifications$Elements();
    public static final ElementKey NotificationScrim = new ElementKey("NotificationScrim", null, null, false, 14, null);
    public static final ElementKey NotificationStackCutoffGuideline = null;
    public static final ElementKey NotificationStackPlaceholder = null;

    static {
        new ElementKey("NotificationStackPlaceholder", null, null, false, 14, null);
        new ElementKey("HeadsUpNotificationPlaceholder", null, LowestZIndexContentPicker.INSTANCE, false, 10, null);
        new ElementKey("NotificationStackCutoffGuideline", null, null, false, 14, null);
    }

    private Notifications$Elements() {
    }
}
