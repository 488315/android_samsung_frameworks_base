package com.android.systemui.communal.shared.model;

import com.android.compose.animation.scene.SceneKey;

/* loaded from: classes2.dex */
public final class CommunalScenes {
    public static final SceneKey Blank;
    public static final SceneKey Communal;
    public static final SceneKey Default;
    public static final CommunalScenes INSTANCE = new CommunalScenes();

    static {
        SceneKey sceneKey = new SceneKey("blank", null, 2, null);
        Blank = sceneKey;
        Communal = new SceneKey("communal", null, 2, null);
        Default = sceneKey;
    }

    private CommunalScenes() {
    }
}
