package com.android.bouncer.ui.composable;

import com.android.compose.animation.scene.SceneKey;

/* loaded from: classes.dex */
public final class SecSceneKeys {
    public static final SecSceneKeys INSTANCE = new SecSceneKeys();
    public static final SceneKey ContiguousSceneKey = new SceneKey("default", null, 2, null);
    public static final SceneKey SplitSceneKey = new SceneKey("split", null, 2, null);

    private SecSceneKeys() {
    }
}
