package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.OverlayKey;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Overlays {
    public static final OverlayKey Bouncer;
    public static final OverlayKey NotificationsShade;
    public static final OverlayKey QuickSettingsShade;

    static {
        new Overlays();
        Bouncer = new OverlayKey("bouncer", null, 2, null);
        NotificationsShade = new OverlayKey("notifications_shade", null, 2, null);
        QuickSettingsShade = new OverlayKey("quick_settings_shade", null, 2, null);
    }

    private Overlays() {
    }
}
