package com.android.systemui.model;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.model.SceneContainerPlugin;
import com.android.systemui.scene.shared.model.Scenes;
import dagger.Lazy;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SceneContainerPlugin {
    public static final Companion Companion = null;
    public static final Map EvaluatorByFlag = null;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class SceneContainerPluginState {
        public final boolean invisibleDueToOcclusion;
        public final SceneKey scene;

        public SceneContainerPluginState(SceneKey sceneKey, boolean z) {
            this.scene = sceneKey;
            this.invisibleDueToOcclusion = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SceneContainerPluginState)) {
                return false;
            }
            SceneContainerPluginState sceneContainerPluginState = (SceneContainerPluginState) obj;
            return Intrinsics.areEqual(this.scene, sceneContainerPluginState.scene) && this.invisibleDueToOcclusion == sceneContainerPluginState.invisibleDueToOcclusion;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.invisibleDueToOcclusion) + (this.scene.identity.hashCode() * 31);
        }

        public final String toString() {
            return "SceneContainerPluginState(scene=" + this.scene + ", invisibleDueToOcclusion=" + this.invisibleDueToOcclusion + ")";
        }
    }

    static {
        new Companion(null);
        MapsKt__MapsKt.mapOf(new Pair(1073741824L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$Companion$EvaluatorByFlag$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(!Intrinsics.areEqual(((SceneContainerPlugin.SceneContainerPluginState) obj).scene, Scenes.Gone));
            }
        }), new Pair(4L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$Companion$EvaluatorByFlag$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                SceneKey sceneKey = Scenes.Lockscreen;
                SceneKey sceneKey2 = ((SceneContainerPlugin.SceneContainerPluginState) obj).scene;
                return Boolean.valueOf(Intrinsics.areEqual(sceneKey2, sceneKey) || Intrinsics.areEqual(sceneKey2, Scenes.NotificationsShade) || Intrinsics.areEqual(sceneKey2, Scenes.Shade));
            }
        }), new Pair(2048L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$Companion$EvaluatorByFlag$3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                SceneKey sceneKey = Scenes.QuickSettingsShade;
                SceneKey sceneKey2 = ((SceneContainerPlugin.SceneContainerPluginState) obj).scene;
                return Boolean.valueOf(Intrinsics.areEqual(sceneKey2, sceneKey) || Intrinsics.areEqual(sceneKey2, Scenes.QuickSettings));
            }
        }), new Pair(8L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$Companion$EvaluatorByFlag$4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(Intrinsics.areEqual(((SceneContainerPlugin.SceneContainerPluginState) obj).scene, Scenes.Bouncer));
            }
        }), new Pair(64L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$Companion$EvaluatorByFlag$5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                SceneContainerPlugin.SceneContainerPluginState sceneContainerPluginState = (SceneContainerPlugin.SceneContainerPluginState) obj;
                return Boolean.valueOf(Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen) && !sceneContainerPluginState.invisibleDueToOcclusion);
            }
        }), new Pair(512L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$Companion$EvaluatorByFlag$6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                SceneContainerPlugin.SceneContainerPluginState sceneContainerPluginState = (SceneContainerPlugin.SceneContainerPluginState) obj;
                return Boolean.valueOf(Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen) && sceneContainerPluginState.invisibleDueToOcclusion);
            }
        }));
    }

    public SceneContainerPlugin(Lazy lazy, Lazy lazy2) {
    }
}
