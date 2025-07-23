package com.android.systemui.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.model.SceneContainerPlugin;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import dagger.Lazy;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.Map;
import java.util.Set;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneContainerPlugin {
    public static final Companion Companion = null;
    public static final Map EvaluatorByFlag = null;
    public final Lazy shadeDisplaysRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SceneContainerPluginState {
        public final boolean invisibleDueToOcclusion;
        public final boolean isVisible;
        public final Set overlays;
        public final SceneKey scene;

        public SceneContainerPluginState(SceneKey sceneKey, Set<OverlayKey> set, boolean z, boolean z2) {
            this.scene = sceneKey;
            this.overlays = set;
            this.invisibleDueToOcclusion = z;
            this.isVisible = z2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SceneContainerPluginState)) {
                return false;
            }
            SceneContainerPluginState sceneContainerPluginState = (SceneContainerPluginState) obj;
            return Intrinsics.areEqual(this.scene, sceneContainerPluginState.scene) && Intrinsics.areEqual(this.overlays, sceneContainerPluginState.overlays) && this.invisibleDueToOcclusion == sceneContainerPluginState.invisibleDueToOcclusion && this.isVisible == sceneContainerPluginState.isVisible;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isVisible) + TransitionData$$ExternalSyntheticOutline0.m((this.overlays.hashCode() + (this.scene.identity.hashCode() * 31)) * 31, 31, this.invisibleDueToOcclusion);
        }

        public final String toString() {
            Set set = this.overlays;
            StringBuilder sb = new StringBuilder("SceneContainerPluginState(scene=");
            sb.append(this.scene);
            sb.append(", overlays=");
            sb.append(set);
            sb.append(", invisibleDueToOcclusion=");
            sb.append(this.invisibleDueToOcclusion);
            sb.append(", isVisible=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isVisible, ")");
        }
    }

    static {
        new Companion(null);
        final int i = 0;
        Pair pair = new Pair(1073741824L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z = false;
                SceneContainerPlugin.SceneContainerPluginState sceneContainerPluginState = (SceneContainerPlugin.SceneContainerPluginState) obj;
                switch (i) {
                    case 0:
                        SceneContainerPlugin.Companion companion = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (!Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Gone) || !sceneContainerPluginState.overlays.isEmpty()) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 1:
                        SceneContainerPlugin.Companion companion2 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            SceneKey sceneKey = Scenes.Lockscreen;
                            SceneKey sceneKey2 = sceneContainerPluginState.scene;
                            if (Intrinsics.areEqual(sceneKey2, sceneKey) || Intrinsics.areEqual(sceneKey2, Scenes.Shade) || sceneContainerPluginState.overlays.contains(Overlays.NotificationsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 2:
                        SceneContainerPlugin.Companion companion3 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.QuickSettings) || sceneContainerPluginState.overlays.contains(Overlays.QuickSettingsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 3:
                        SceneContainerPlugin.Companion companion4 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible && sceneContainerPluginState.overlays.contains(Overlays.Bouncer)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 4:
                        SceneContainerPlugin.Companion companion5 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 5:
                        SceneContainerPlugin.Companion companion6 = SceneContainerPlugin.Companion;
                        if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen) && sceneContainerPluginState.invisibleDueToOcclusion) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        SceneContainerPlugin.Companion companion7 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Communal)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                }
            }
        });
        final int i2 = 1;
        Pair pair2 = new Pair(4L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z = false;
                SceneContainerPlugin.SceneContainerPluginState sceneContainerPluginState = (SceneContainerPlugin.SceneContainerPluginState) obj;
                switch (i2) {
                    case 0:
                        SceneContainerPlugin.Companion companion = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (!Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Gone) || !sceneContainerPluginState.overlays.isEmpty()) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 1:
                        SceneContainerPlugin.Companion companion2 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            SceneKey sceneKey = Scenes.Lockscreen;
                            SceneKey sceneKey2 = sceneContainerPluginState.scene;
                            if (Intrinsics.areEqual(sceneKey2, sceneKey) || Intrinsics.areEqual(sceneKey2, Scenes.Shade) || sceneContainerPluginState.overlays.contains(Overlays.NotificationsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 2:
                        SceneContainerPlugin.Companion companion3 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.QuickSettings) || sceneContainerPluginState.overlays.contains(Overlays.QuickSettingsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 3:
                        SceneContainerPlugin.Companion companion4 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible && sceneContainerPluginState.overlays.contains(Overlays.Bouncer)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 4:
                        SceneContainerPlugin.Companion companion5 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 5:
                        SceneContainerPlugin.Companion companion6 = SceneContainerPlugin.Companion;
                        if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen) && sceneContainerPluginState.invisibleDueToOcclusion) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        SceneContainerPlugin.Companion companion7 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Communal)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                }
            }
        });
        final int i3 = 2;
        Pair pair3 = new Pair(2048L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z = false;
                SceneContainerPlugin.SceneContainerPluginState sceneContainerPluginState = (SceneContainerPlugin.SceneContainerPluginState) obj;
                switch (i3) {
                    case 0:
                        SceneContainerPlugin.Companion companion = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (!Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Gone) || !sceneContainerPluginState.overlays.isEmpty()) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 1:
                        SceneContainerPlugin.Companion companion2 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            SceneKey sceneKey = Scenes.Lockscreen;
                            SceneKey sceneKey2 = sceneContainerPluginState.scene;
                            if (Intrinsics.areEqual(sceneKey2, sceneKey) || Intrinsics.areEqual(sceneKey2, Scenes.Shade) || sceneContainerPluginState.overlays.contains(Overlays.NotificationsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 2:
                        SceneContainerPlugin.Companion companion3 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.QuickSettings) || sceneContainerPluginState.overlays.contains(Overlays.QuickSettingsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 3:
                        SceneContainerPlugin.Companion companion4 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible && sceneContainerPluginState.overlays.contains(Overlays.Bouncer)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 4:
                        SceneContainerPlugin.Companion companion5 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 5:
                        SceneContainerPlugin.Companion companion6 = SceneContainerPlugin.Companion;
                        if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen) && sceneContainerPluginState.invisibleDueToOcclusion) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        SceneContainerPlugin.Companion companion7 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Communal)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                }
            }
        });
        final int i4 = 3;
        Pair pair4 = new Pair(8L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z = false;
                SceneContainerPlugin.SceneContainerPluginState sceneContainerPluginState = (SceneContainerPlugin.SceneContainerPluginState) obj;
                switch (i4) {
                    case 0:
                        SceneContainerPlugin.Companion companion = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (!Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Gone) || !sceneContainerPluginState.overlays.isEmpty()) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 1:
                        SceneContainerPlugin.Companion companion2 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            SceneKey sceneKey = Scenes.Lockscreen;
                            SceneKey sceneKey2 = sceneContainerPluginState.scene;
                            if (Intrinsics.areEqual(sceneKey2, sceneKey) || Intrinsics.areEqual(sceneKey2, Scenes.Shade) || sceneContainerPluginState.overlays.contains(Overlays.NotificationsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 2:
                        SceneContainerPlugin.Companion companion3 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.QuickSettings) || sceneContainerPluginState.overlays.contains(Overlays.QuickSettingsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 3:
                        SceneContainerPlugin.Companion companion4 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible && sceneContainerPluginState.overlays.contains(Overlays.Bouncer)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 4:
                        SceneContainerPlugin.Companion companion5 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 5:
                        SceneContainerPlugin.Companion companion6 = SceneContainerPlugin.Companion;
                        if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen) && sceneContainerPluginState.invisibleDueToOcclusion) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        SceneContainerPlugin.Companion companion7 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Communal)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                }
            }
        });
        final int i5 = 4;
        Pair pair5 = new Pair(64L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z = false;
                SceneContainerPlugin.SceneContainerPluginState sceneContainerPluginState = (SceneContainerPlugin.SceneContainerPluginState) obj;
                switch (i5) {
                    case 0:
                        SceneContainerPlugin.Companion companion = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (!Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Gone) || !sceneContainerPluginState.overlays.isEmpty()) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 1:
                        SceneContainerPlugin.Companion companion2 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            SceneKey sceneKey = Scenes.Lockscreen;
                            SceneKey sceneKey2 = sceneContainerPluginState.scene;
                            if (Intrinsics.areEqual(sceneKey2, sceneKey) || Intrinsics.areEqual(sceneKey2, Scenes.Shade) || sceneContainerPluginState.overlays.contains(Overlays.NotificationsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 2:
                        SceneContainerPlugin.Companion companion3 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.QuickSettings) || sceneContainerPluginState.overlays.contains(Overlays.QuickSettingsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 3:
                        SceneContainerPlugin.Companion companion4 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible && sceneContainerPluginState.overlays.contains(Overlays.Bouncer)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 4:
                        SceneContainerPlugin.Companion companion5 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 5:
                        SceneContainerPlugin.Companion companion6 = SceneContainerPlugin.Companion;
                        if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen) && sceneContainerPluginState.invisibleDueToOcclusion) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        SceneContainerPlugin.Companion companion7 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Communal)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                }
            }
        });
        final int i6 = 5;
        Pair pair6 = new Pair(512L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z = false;
                SceneContainerPlugin.SceneContainerPluginState sceneContainerPluginState = (SceneContainerPlugin.SceneContainerPluginState) obj;
                switch (i6) {
                    case 0:
                        SceneContainerPlugin.Companion companion = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (!Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Gone) || !sceneContainerPluginState.overlays.isEmpty()) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 1:
                        SceneContainerPlugin.Companion companion2 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            SceneKey sceneKey = Scenes.Lockscreen;
                            SceneKey sceneKey2 = sceneContainerPluginState.scene;
                            if (Intrinsics.areEqual(sceneKey2, sceneKey) || Intrinsics.areEqual(sceneKey2, Scenes.Shade) || sceneContainerPluginState.overlays.contains(Overlays.NotificationsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 2:
                        SceneContainerPlugin.Companion companion3 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.QuickSettings) || sceneContainerPluginState.overlays.contains(Overlays.QuickSettingsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 3:
                        SceneContainerPlugin.Companion companion4 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible && sceneContainerPluginState.overlays.contains(Overlays.Bouncer)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 4:
                        SceneContainerPlugin.Companion companion5 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 5:
                        SceneContainerPlugin.Companion companion6 = SceneContainerPlugin.Companion;
                        if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen) && sceneContainerPluginState.invisibleDueToOcclusion) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        SceneContainerPlugin.Companion companion7 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Communal)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                }
            }
        });
        final int i7 = 6;
        MapsKt__MapsKt.mapOf(pair, pair2, pair3, pair4, pair5, pair6, new Pair(34359738368L, new Function1() { // from class: com.android.systemui.model.SceneContainerPlugin$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                boolean z = false;
                SceneContainerPlugin.SceneContainerPluginState sceneContainerPluginState = (SceneContainerPlugin.SceneContainerPluginState) obj;
                switch (i7) {
                    case 0:
                        SceneContainerPlugin.Companion companion = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (!Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Gone) || !sceneContainerPluginState.overlays.isEmpty()) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 1:
                        SceneContainerPlugin.Companion companion2 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            SceneKey sceneKey = Scenes.Lockscreen;
                            SceneKey sceneKey2 = sceneContainerPluginState.scene;
                            if (Intrinsics.areEqual(sceneKey2, sceneKey) || Intrinsics.areEqual(sceneKey2, Scenes.Shade) || sceneContainerPluginState.overlays.contains(Overlays.NotificationsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 2:
                        SceneContainerPlugin.Companion companion3 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.QuickSettings) || sceneContainerPluginState.overlays.contains(Overlays.QuickSettingsShade)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 3:
                        SceneContainerPlugin.Companion companion4 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible && sceneContainerPluginState.overlays.contains(Overlays.Bouncer)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    case 4:
                        SceneContainerPlugin.Companion companion5 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                    case 5:
                        SceneContainerPlugin.Companion companion6 = SceneContainerPlugin.Companion;
                        if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Lockscreen) && sceneContainerPluginState.invisibleDueToOcclusion) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        SceneContainerPlugin.Companion companion7 = SceneContainerPlugin.Companion;
                        if (sceneContainerPluginState.isVisible) {
                            if (Intrinsics.areEqual(sceneContainerPluginState.scene, Scenes.Communal)) {
                                z = true;
                            }
                        }
                        return Boolean.valueOf(z);
                }
            }
        }));
    }

    public SceneContainerPlugin(Lazy lazy, Lazy lazy2, Lazy lazy3) {
        this.shadeDisplaysRepository = lazy3;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.model.SceneContainerPlugin$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ((ShadeDisplaysRepositoryImpl) ((ShadeDisplaysRepository) SceneContainerPlugin.this.shadeDisplaysRepository.get())).displayId;
            }
        });
    }
}
