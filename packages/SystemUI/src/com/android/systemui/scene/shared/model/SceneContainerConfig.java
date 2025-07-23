package com.android.systemui.scene.shared.model;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.ui.composable.SceneContainerTransitionsBuilder;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneContainerConfig {
    public final SceneKey initialSceneKey;
    public final Map navigationDistances;
    public final List overlayKeys;
    public final List sceneKeys;
    public final SceneContainerTransitionsBuilder transitionsBuilder;

    public SceneContainerConfig(List<SceneKey> list, SceneKey sceneKey, List<OverlayKey> list2, Map<SceneKey, Integer> map, SceneContainerTransitionsBuilder sceneContainerTransitionsBuilder) {
        this.sceneKeys = list;
        this.initialSceneKey = sceneKey;
        this.overlayKeys = list2;
        this.navigationDistances = map;
        this.transitionsBuilder = sceneContainerTransitionsBuilder;
        if (list.isEmpty()) {
            throw new IllegalStateException("A container must have at least one scene key.");
        }
        if (list.contains(sceneKey)) {
            if (!Intrinsics.areEqual(map.keySet(), CollectionsKt___CollectionsKt.toSet(list))) {
                throw new IllegalStateException("Scene keys and distance map must match.");
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
        return Intrinsics.areEqual(this.sceneKeys, sceneContainerConfig.sceneKeys) && Intrinsics.areEqual(this.initialSceneKey, sceneContainerConfig.initialSceneKey) && Intrinsics.areEqual(this.overlayKeys, sceneContainerConfig.overlayKeys) && Intrinsics.areEqual(this.navigationDistances, sceneContainerConfig.navigationDistances) && Intrinsics.areEqual(this.transitionsBuilder, sceneContainerConfig.transitionsBuilder);
    }

    public final int hashCode() {
        return this.transitionsBuilder.hashCode() + ((this.navigationDistances.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.overlayKeys, (this.initialSceneKey.identity.hashCode() + (this.sceneKeys.hashCode() * 31)) * 31, 31)) * 31);
    }

    public final String toString() {
        return "SceneContainerConfig(sceneKeys=" + this.sceneKeys + ", initialSceneKey=" + this.initialSceneKey + ", overlayKeys=" + this.overlayKeys + ", navigationDistances=" + this.navigationDistances + ", transitionsBuilder=" + this.transitionsBuilder + ")";
    }

    public SceneContainerConfig(List list, SceneKey sceneKey, List list2, Map map, SceneContainerTransitionsBuilder sceneContainerTransitionsBuilder, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, sceneKey, (i & 4) != 0 ? EmptyList.INSTANCE : list2, map, sceneContainerTransitionsBuilder);
    }
}
