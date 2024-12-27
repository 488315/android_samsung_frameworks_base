package com.android.systemui.communal.shared.model;

import com.android.compose.animation.scene.SceneKey;

public final class CommunalScenes {
    public static final SceneKey Blank;
    public static final SceneKey Communal;
    public static final SceneKey Default;

    static {
        new CommunalScenes();
        SceneKey sceneKey = new SceneKey("blank", null, 2, null);
        Blank = sceneKey;
        Communal = new SceneKey("communal", null, 2, null);
        Default = sceneKey;
    }

    private CommunalScenes() {
    }
}
