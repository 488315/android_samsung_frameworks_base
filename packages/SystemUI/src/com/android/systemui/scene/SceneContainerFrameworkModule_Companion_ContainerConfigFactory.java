package com.android.systemui.scene;

import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.SceneContainerConfig;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.ui.composable.SceneContainerTransitions;
import dagger.internal.Provider;
import kotlin.Pair;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneContainerFrameworkModule_Companion_ContainerConfigFactory implements Provider {
    public static SceneContainerConfig containerConfig() {
        SceneContainerFrameworkModule.Companion.getClass();
        SceneKey sceneKey = Scenes.Gone;
        SceneKey sceneKey2 = Scenes.Communal;
        SceneKey sceneKey3 = Scenes.Dream;
        SceneKey sceneKey4 = Scenes.Lockscreen;
        SceneKey sceneKey5 = Scenes.QuickSettings;
        SceneKey sceneKey6 = Scenes.Shade;
        return new SceneContainerConfig(ArraysKt___ArraysKt.filterNotNull(new SceneKey[]{sceneKey, sceneKey2, sceneKey3, sceneKey4, sceneKey5, sceneKey6}), sceneKey4, ArraysKt___ArraysKt.filterNotNull(new OverlayKey[]{Overlays.NotificationsShade, Overlays.QuickSettingsShade, Overlays.Bouncer}), MapsKt__MapsKt.mapOf(new Pair(sceneKey, 0), new Pair(sceneKey4, 0), new Pair(sceneKey2, 1), new Pair(sceneKey3, 2), new Pair(sceneKey6, 3), new Pair(sceneKey5, 4)), new SceneContainerTransitions());
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return containerConfig();
    }
}
