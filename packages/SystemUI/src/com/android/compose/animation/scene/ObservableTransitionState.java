package com.android.compose.animation.scene;

import java.util.Set;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes.dex */
public interface ObservableTransitionState {

    public final class Idle implements ObservableTransitionState {
        public final Set currentOverlays;
        public final SceneKey currentScene;

        public Idle(SceneKey sceneKey) {
            this(sceneKey, null, 2, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Idle)) {
                return false;
            }
            Idle idle = (Idle) obj;
            return Intrinsics.areEqual(this.currentScene, idle.currentScene) && Intrinsics.areEqual(this.currentOverlays, idle.currentOverlays);
        }

        public final int hashCode() {
            return this.currentOverlays.hashCode() + (this.currentScene.identity.hashCode() * 31);
        }

        public final String toString() {
            return "Idle(currentScene=" + this.currentScene + ", currentOverlays=" + this.currentOverlays + ")";
        }

        public Idle(SceneKey sceneKey, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(sceneKey, (i & 2) != 0 ? EmptySet.INSTANCE : set);
        }

        public Idle(SceneKey sceneKey, Set<OverlayKey> set) {
            this.currentScene = sceneKey;
            this.currentOverlays = set;
        }
    }

    public abstract class Transition implements ObservableTransitionState {
        public final ContentKey fromContent;
        public final Flow isInPreviewStage;
        public final boolean isInitiatedByUserInput;
        public final Flow isUserInputOngoing;
        public final Flow progress;
        public final ContentKey toContent;

        public final class ChangeScene extends Transition {
            public final Set currentOverlays;
            public final Flow currentScene;
            public final SceneKey fromScene;
            public final SceneKey toScene;

            public ChangeScene(SceneKey sceneKey, SceneKey sceneKey2, Flow flow, Set<OverlayKey> set, Flow flow2, boolean z, Flow flow3, Flow flow4, Flow flow5) {
                super(sceneKey, sceneKey2, flow2, z, flow3, flow4, flow5, null);
                this.fromScene = sceneKey;
                this.toScene = sceneKey2;
                this.currentScene = flow;
                this.currentOverlays = set;
            }
        }

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public abstract class OverlayTransition extends Transition {
            public final Flow currentOverlays;
            public final SceneKey currentScene;

            public /* synthetic */ OverlayTransition(ContentKey contentKey, ContentKey contentKey2, SceneKey sceneKey, Flow flow, Flow flow2, boolean z, Flow flow3, Flow flow4, Flow flow5, DefaultConstructorMarker defaultConstructorMarker) {
                this(contentKey, contentKey2, sceneKey, flow, flow2, z, flow3, flow4, flow5);
            }

            private OverlayTransition(ContentKey contentKey, ContentKey contentKey2, SceneKey sceneKey, Flow flow, Flow flow2, boolean z, Flow flow3, Flow flow4, Flow flow5) {
                super(contentKey, contentKey2, flow2, z, flow3, flow4, flow5, null);
                this.currentScene = sceneKey;
                this.currentOverlays = flow;
            }
        }

        public final class ReplaceOverlay extends OverlayTransition {
            public final OverlayKey fromOverlay;
            public final OverlayKey toOverlay;

            public ReplaceOverlay(OverlayKey overlayKey, OverlayKey overlayKey2, SceneKey sceneKey, Flow flow, Flow flow2, boolean z, Flow flow3, Flow flow4, Flow flow5) {
                super(overlayKey, overlayKey2, sceneKey, flow, flow2, z, flow3, flow4, flow5, null);
                this.fromOverlay = overlayKey;
                this.toOverlay = overlayKey2;
            }
        }

        public final class ShowOrHideOverlay extends OverlayTransition {
            public ShowOrHideOverlay(OverlayKey overlayKey, ContentKey contentKey, ContentKey contentKey2, SceneKey sceneKey, Flow flow, Flow flow2, boolean z, Flow flow3, Flow flow4, Flow flow5) {
                super(contentKey, contentKey2, sceneKey, flow, flow2, z, flow3, flow4, flow5, null);
            }
        }

        static {
            new Companion(null);
        }

        public /* synthetic */ Transition(ContentKey contentKey, ContentKey contentKey2, Flow flow, boolean z, Flow flow2, Flow flow3, Flow flow4, DefaultConstructorMarker defaultConstructorMarker) {
            this(contentKey, contentKey2, flow, z, flow2, flow3, flow4);
        }

        public final String toString() {
            return StringsKt__IndentKt.trimMargin$default("Transition\n                |(from=" + this.fromContent + ",\n                | to=" + this.toContent + ",\n                | isInitiatedByUserInput=" + this.isInitiatedByUserInput + ",\n                | isUserInputOngoing=" + this.isUserInputOngoing + "\n                |)");
        }

        private Transition(ContentKey contentKey, ContentKey contentKey2, Flow flow, boolean z, Flow flow2, Flow flow3, Flow flow4) {
            this.fromContent = contentKey;
            this.toContent = contentKey2;
            this.progress = flow;
            this.isInitiatedByUserInput = z;
            this.isUserInputOngoing = flow2;
            this.isInPreviewStage = flow4;
        }
    }

    static boolean isIdle$default(ObservableTransitionState observableTransitionState, SceneKey sceneKey, OverlayKey overlayKey, int i) {
        if ((i & 1) != 0) {
            sceneKey = null;
        }
        if ((i & 2) != 0) {
            overlayKey = null;
        }
        observableTransitionState.getClass();
        if (!(observableTransitionState instanceof Idle)) {
            return false;
        }
        if (sceneKey == null || Intrinsics.areEqual(((Idle) observableTransitionState).currentScene, sceneKey)) {
            return overlayKey == null || ((Idle) observableTransitionState).currentOverlays.contains(overlayKey);
        }
        return false;
    }

    static /* synthetic */ boolean isTransitioning$default(ObservableTransitionState observableTransitionState, ContentKey contentKey, ContentKey contentKey2, int i) {
        if ((i & 1) != 0) {
            contentKey = null;
        }
        if ((i & 2) != 0) {
            contentKey2 = null;
        }
        return observableTransitionState.isTransitioning(contentKey, contentKey2);
    }

    static boolean isTransitioningSets$default(ObservableTransitionState observableTransitionState, Set set) {
        observableTransitionState.getClass();
        if (observableTransitionState instanceof Transition) {
            return set == null || set.contains(((Transition) observableTransitionState).fromContent);
        }
        return false;
    }

    default boolean isTransitioning(ContentKey contentKey, ContentKey contentKey2) {
        if (!(this instanceof Transition)) {
            return false;
        }
        if (contentKey == null || Intrinsics.areEqual(((Transition) this).fromContent, contentKey)) {
            return contentKey2 == null || Intrinsics.areEqual(((Transition) this).toContent, contentKey2);
        }
        return false;
    }
}
