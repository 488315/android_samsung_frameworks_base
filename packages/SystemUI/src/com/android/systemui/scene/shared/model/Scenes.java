package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class Scenes {
    public static final SceneKey Bouncer;
    public static final SceneKey Communal;
    public static final SceneKey Gone;
    public static final SceneKey Lockscreen;
    public static final SceneKey NotificationsShade;
    public static final SceneKey QuickSettings;
    public static final SceneKey QuickSettingsShade;
    public static final SceneKey Shade;

    static {
        new Scenes();
        DefaultConstructorMarker defaultConstructorMarker = null;
        int i = 2;
        Bouncer = new SceneKey("bouncer", defaultConstructorMarker, i, defaultConstructorMarker);
        Communal = new SceneKey("communal", defaultConstructorMarker, i, defaultConstructorMarker);
        Gone = new SceneKey("gone", defaultConstructorMarker, i, defaultConstructorMarker);
        Lockscreen = new SceneKey(BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD, defaultConstructorMarker, i, defaultConstructorMarker);
        NotificationsShade = new SceneKey("notifications_shade", defaultConstructorMarker, i, defaultConstructorMarker);
        QuickSettings = new SceneKey("quick_settings", defaultConstructorMarker, i, defaultConstructorMarker);
        QuickSettingsShade = new SceneKey("quick_settings_shade", defaultConstructorMarker, i, defaultConstructorMarker);
        Shade = new SceneKey("shade", defaultConstructorMarker, i, defaultConstructorMarker);
    }

    private Scenes() {
    }
}
