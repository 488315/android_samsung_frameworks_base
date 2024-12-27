package com.android.systemui.communal.shared.model;

import com.android.compose.animation.scene.SceneKey;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
