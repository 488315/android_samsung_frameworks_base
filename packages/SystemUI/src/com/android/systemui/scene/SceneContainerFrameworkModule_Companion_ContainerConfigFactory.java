package com.android.systemui.scene;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.Flags;
import com.android.systemui.scene.shared.model.SceneContainerConfig;
import com.android.systemui.scene.shared.model.Scenes;
import dagger.internal.Provider;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SceneContainerFrameworkModule_Companion_ContainerConfigFactory implements Provider {
    public static SceneContainerConfig containerConfig() {
        SceneContainerFrameworkModule.Companion.getClass();
        SceneKey sceneKey = Scenes.Gone;
        SceneKey sceneKey2 = Scenes.Communal;
        SceneKey sceneKey3 = Scenes.Lockscreen;
        SceneKey sceneKey4 = Scenes.Bouncer;
        SceneKey sceneKey5 = Scenes.QuickSettings;
        Flags.dualShade();
        SceneKey sceneKey6 = Scenes.QuickSettingsShade;
        Flags.dualShade();
        SceneKey sceneKey7 = Scenes.NotificationsShade;
        Flags.dualShade();
        SceneKey sceneKey8 = Scenes.Shade;
        Flags.dualShade();
        List filterNotNull = ArraysKt___ArraysKt.filterNotNull(new SceneKey[]{sceneKey, sceneKey2, sceneKey3, sceneKey4, sceneKey5, null, null, sceneKey8});
        Pair pair = new Pair(sceneKey, 0);
        Pair pair2 = new Pair(sceneKey3, 0);
        Pair pair3 = new Pair(sceneKey2, 1);
        Flags.dualShade();
        Pair pair4 = new Pair(sceneKey7, null);
        Flags.dualShade();
        Pair pair5 = new Pair(sceneKey8, 2);
        Flags.dualShade();
        Pair pair6 = new Pair(sceneKey6, null);
        Flags.dualShade();
        Map mapOf = MapsKt__MapsKt.mapOf(pair, pair2, pair3, pair4, pair5, pair6, new Pair(sceneKey5, 3), new Pair(sceneKey4, 4));
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : mapOf.entrySet()) {
            if (((Integer) entry.getValue()) != null) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(linkedHashMap.size()));
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            Object key = entry2.getKey();
            Object value = entry2.getValue();
            if (value == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            linkedHashMap2.put(key, Integer.valueOf(((Number) value).intValue()));
        }
        return new SceneContainerConfig(filterNotNull, sceneKey3, linkedHashMap2);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return containerConfig();
    }
}
