package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;

/* loaded from: classes2.dex */
public final class Scenes {
    public static final SceneKey Communal;
    public static final SceneKey Dream;
    public static final SceneKey Gone;
    public static final SceneKey Lockscreen;
    public static final SceneKey QuickSettings;
    public static final SceneKey Shade;

    static {
        new Scenes();
        Communal = new SceneKey("communal", null, 2, null);
        Dream = new SceneKey(BcSmartspaceDataPlugin.UI_SURFACE_DREAM, null, 2, null);
        Gone = new SceneKey("gone", null, 2, null);
        Lockscreen = new SceneKey(BcSmartspaceDataPlugin.UI_SURFACE_LOCK_SCREEN_AOD, null, 2, null);
        QuickSettings = new SceneKey("quick_settings", null, 2, null);
        Shade = new SceneKey("shade", null, 2, null);
    }

    private Scenes() {
    }
}
