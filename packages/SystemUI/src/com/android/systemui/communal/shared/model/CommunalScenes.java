package com.android.systemui.communal.shared.model;

import com.android.compose.animation.scene.SceneKey;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
