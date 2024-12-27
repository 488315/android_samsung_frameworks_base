package com.android.systemui.scene.shared.model;

import com.android.compose.animation.scene.SceneKey;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SceneContainerConfig {
    public final SceneKey initialSceneKey;
    public final Map navigationDistances;
    public final List sceneKeys;

    public SceneContainerConfig(List<SceneKey> list, SceneKey sceneKey, Map<SceneKey, Integer> map) {
        this.sceneKeys = list;
        this.initialSceneKey = sceneKey;
        this.navigationDistances = map;
        if (!(!list.isEmpty())) {
            throw new IllegalStateException("A container must have at least one scene key.".toString());
        }
        if (list.contains(sceneKey)) {
            if (!Intrinsics.areEqual(map.keySet(), CollectionsKt___CollectionsKt.toSet(list))) {
                throw new IllegalStateException("Scene keys and distance map must match.".toString());
            }
        } else {
            throw new IllegalStateException(("The initial key \"" + sceneKey + "\" is not present in this container.").toString());
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SceneContainerConfig)) {
            return false;
        }
        SceneContainerConfig sceneContainerConfig = (SceneContainerConfig) obj;
        return Intrinsics.areEqual(this.sceneKeys, sceneContainerConfig.sceneKeys) && Intrinsics.areEqual(this.initialSceneKey, sceneContainerConfig.initialSceneKey) && Intrinsics.areEqual(this.navigationDistances, sceneContainerConfig.navigationDistances);
    }

    public final int hashCode() {
        return this.navigationDistances.hashCode() + ((this.initialSceneKey.identity.hashCode() + (this.sceneKeys.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "SceneContainerConfig(sceneKeys=" + this.sceneKeys + ", initialSceneKey=" + this.initialSceneKey + ", navigationDistances=" + this.navigationDistances + ")";
    }
}
