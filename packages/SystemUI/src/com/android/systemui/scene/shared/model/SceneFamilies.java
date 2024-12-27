package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.SceneKey;

public final class SceneFamilies {
    public static final SceneKey Home;
    public static final SceneKey NotifShade;
    public static final SceneKey QuickSettings;

    static {
        new SceneFamilies();
        Home = new SceneKey("scene_family_home", null, 2, null);
        NotifShade = new SceneKey("scene_family_notif_shade", null, 2, null);
        QuickSettings = new SceneKey("scene_family_quick_settings", null, 2, null);
    }

    private SceneFamilies() {
    }
}
