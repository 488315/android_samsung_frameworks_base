package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.SceneKey;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
