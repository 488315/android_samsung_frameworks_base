package com.android.compose.animation.scene.content.state;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.SceneTransitionLayoutImpl;
import com.android.compose.animation.scene.TransformationSpec;
import com.android.compose.animation.scene.TransformationSpecImpl;
import com.android.compose.animation.scene.TransitionKey;
import com.android.compose.animation.scene.content.state.TransitionState;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.collections.builders.SetBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface TransitionState {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Transition implements TransitionState {
        public CoroutineScope _coroutineScope;
        public Integer _cuj;
        public Set currentOverlaysWhenTransitionStarted;
        public SceneKey currentSceneWhenTransitionStarted;
        public final ContentKey fromContent;
        public Animatable interruptionDecay;
        public final MutableState isProgressStable$delegate;
        public TransformationSpecImpl previewTransformationSpec;
        public final Transition replacedTransition;
        public final ContentKey toContent;
        public TransformationSpecImpl transformationSpec;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract class ChangeScene extends Transition {
            public final SceneKey fromScene;
            public final SceneKey toScene;

            public /* synthetic */ ChangeScene(SceneKey sceneKey, SceneKey sceneKey2, Transition transition, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(sceneKey, sceneKey2, (i & 4) != 0 ? null : transition);
            }

            @Override // com.android.compose.animation.scene.content.state.TransitionState
            public final Set getCurrentOverlays() {
                Set set = this.currentOverlaysWhenTransitionStarted;
                if (set != null) {
                    return set;
                }
                return null;
            }

            public final String toString() {
                return "ChangeScene(fromScene=" + this.fromScene + ", toScene=" + this.toScene + ")";
            }

            public ChangeScene(SceneKey sceneKey, SceneKey sceneKey2, Transition transition) {
                super(sceneKey, sceneKey2, transition, null);
                this.fromScene = sceneKey;
                this.toScene = sceneKey2;
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract class OverlayTransition extends Transition {
            public final State currentOverlays$delegate;

            public /* synthetic */ OverlayTransition(ContentKey contentKey, ContentKey contentKey2, Transition transition, DefaultConstructorMarker defaultConstructorMarker) {
                this(contentKey, contentKey2, transition);
            }

            public abstract Set computeCurrentOverlays();

            @Override // com.android.compose.animation.scene.content.state.TransitionState
            public final Set getCurrentOverlays() {
                return (Set) this.currentOverlays$delegate.getValue();
            }

            @Override // com.android.compose.animation.scene.content.state.TransitionState
            public final SceneKey getCurrentScene() {
                SceneKey sceneKey = this.currentSceneWhenTransitionStarted;
                if (sceneKey != null) {
                    return sceneKey;
                }
                return null;
            }

            private OverlayTransition(ContentKey contentKey, ContentKey contentKey2, Transition transition) {
                super(contentKey, contentKey2, transition, null);
                this.currentOverlays$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.compose.animation.scene.content.state.TransitionState$Transition$OverlayTransition$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return TransitionState.Transition.OverlayTransition.this.computeCurrentOverlays();
                    }
                });
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract class ReplaceOverlay extends OverlayTransition {
            public final OverlayKey fromOverlay;
            public final OverlayKey toOverlay;

            public /* synthetic */ ReplaceOverlay(OverlayKey overlayKey, OverlayKey overlayKey2, Transition transition, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(overlayKey, overlayKey2, (i & 4) != 0 ? null : transition);
            }

            @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition.OverlayTransition
            public final Set computeCurrentOverlays() {
                OverlayKey effectivelyShownOverlay = getEffectivelyShownOverlay();
                OverlayKey overlayKey = this.fromOverlay;
                boolean areEqual = Intrinsics.areEqual(effectivelyShownOverlay, overlayKey);
                OverlayKey overlayKey2 = this.toOverlay;
                if (areEqual) {
                    SetBuilder setBuilder = new SetBuilder();
                    Set set = this.currentOverlaysWhenTransitionStarted;
                    setBuilder.addAll(set != null ? set : null);
                    setBuilder.remove(overlayKey2);
                    setBuilder.add(overlayKey);
                    return setBuilder.build();
                }
                if (Intrinsics.areEqual(effectivelyShownOverlay, overlayKey2)) {
                    SetBuilder setBuilder2 = new SetBuilder();
                    Set set2 = this.currentOverlaysWhenTransitionStarted;
                    setBuilder2.addAll(set2 != null ? set2 : null);
                    setBuilder2.remove(overlayKey);
                    setBuilder2.add(overlayKey2);
                    return setBuilder2.build();
                }
                throw new IllegalStateException(("effectivelyShownOverlay=" + getEffectivelyShownOverlay() + ", should be equal to fromOverlay=" + overlayKey + " or toOverlay=" + overlayKey2).toString());
            }

            public abstract OverlayKey getEffectivelyShownOverlay();

            public final String toString() {
                return "ReplaceOverlay(fromOverlay=" + this.fromOverlay + ", toOverlay=" + this.toOverlay + ")";
            }

            public ReplaceOverlay(OverlayKey overlayKey, OverlayKey overlayKey2, Transition transition) {
                super(overlayKey, overlayKey2, transition, null);
                this.fromOverlay = overlayKey;
                this.toOverlay = overlayKey2;
                if (Intrinsics.areEqual(overlayKey, overlayKey2)) {
                    throw new IllegalStateException("Check failed.");
                }
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract class ShowOrHideOverlay extends OverlayTransition {
            public final SceneKey fromOrToScene;
            public final OverlayKey overlay;

            public /* synthetic */ ShowOrHideOverlay(OverlayKey overlayKey, SceneKey sceneKey, ContentKey contentKey, ContentKey contentKey2, Transition transition, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(overlayKey, sceneKey, contentKey, contentKey2, (i & 16) != 0 ? null : transition);
            }

            @Override // com.android.compose.animation.scene.content.state.TransitionState.Transition.OverlayTransition
            public final Set computeCurrentOverlays() {
                boolean isEffectivelyShown = isEffectivelyShown();
                OverlayKey overlayKey = this.overlay;
                if (isEffectivelyShown) {
                    Set set = this.currentOverlaysWhenTransitionStarted;
                    return SetsKt___SetsKt.plus(set != null ? set : null, overlayKey);
                }
                Set set2 = this.currentOverlaysWhenTransitionStarted;
                return SetsKt___SetsKt.minus(set2 != null ? set2 : null, overlayKey);
            }

            public abstract boolean isEffectivelyShown();

            public final String toString() {
                OverlayKey overlayKey = this.overlay;
                return "ShowOrHideOverlay(overlay=" + overlayKey + ", fromOrToScene=" + this.fromOrToScene + ", isShowing=" + Intrinsics.areEqual(overlayKey, this.toContent) + ")";
            }

            public ShowOrHideOverlay(OverlayKey overlayKey, SceneKey sceneKey, ContentKey contentKey, ContentKey contentKey2, Transition transition) {
                super(contentKey, contentKey2, transition, null);
                this.overlay = overlayKey;
                this.fromOrToScene = sceneKey;
                if (Intrinsics.areEqual(contentKey, sceneKey) && Intrinsics.areEqual(contentKey2, overlayKey)) {
                    return;
                }
                if (!Intrinsics.areEqual(contentKey, overlayKey) || !Intrinsics.areEqual(contentKey2, sceneKey)) {
                    throw new IllegalStateException("Check failed.");
                }
            }
        }

        public /* synthetic */ Transition(ContentKey contentKey, ContentKey contentKey2, Transition transition, DefaultConstructorMarker defaultConstructorMarker) {
            this(contentKey, contentKey2, transition);
        }

        public abstract void freezeAndAnimateToCurrentState();

        public final CoroutineScope getCoroutineScope$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout() {
            CoroutineScope coroutineScope = this._coroutineScope;
            if (coroutineScope != null) {
                return coroutineScope;
            }
            throw new IllegalStateException("Transition.coroutineScope can only be accessed once the transition was started ");
        }

        public TransitionKey getKey() {
            return null;
        }

        public float getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout() {
            return 0.0f;
        }

        public abstract float getProgress();

        public abstract float getProgressVelocity();

        public final float interruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(SceneTransitionLayoutImpl sceneTransitionLayoutImpl) {
            Transition transition = this.replacedTransition;
            if (transition != null) {
                return transition.interruptionProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(sceneTransitionLayoutImpl);
            }
            Animatable animatable = this.interruptionDecay;
            if (animatable == null) {
                animatable = AnimatableKt.Animatable(1.0f, 0.001f);
                BuildersKt.launch$default(sceneTransitionLayoutImpl.animationScope, null, null, new TransitionState$Transition$interruptionProgress$create$1(animatable, sceneTransitionLayoutImpl, null), 3);
                this.interruptionDecay = animatable;
            }
            return ((Number) ((SnapshotMutableStateImpl) animatable.internalState.value$delegate).getValue()).floatValue();
        }

        public boolean isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout() {
            return false;
        }

        public abstract boolean isInitiatedByUserInput();

        public abstract boolean isUserInputOngoing();

        public abstract Object run(Continuation continuation);

        public final Object runInternal$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(Continuation continuation) {
            if (this._coroutineScope != null) {
                throw new IllegalStateException("A Transition can be started only once.");
            }
            Object coroutineScope = CoroutineScopeKt.coroutineScope(new TransitionState$Transition$runInternal$3(this, null), continuation);
            return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
        }

        private Transition(ContentKey contentKey, ContentKey contentKey2, Transition transition) {
            this.fromContent = contentKey;
            this.toContent = contentKey2;
            this.replacedTransition = transition;
            this.isProgressStable$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
            TransformationSpec.Companion.getClass();
            this.transformationSpec = TransformationSpec.Companion.Empty;
            if (Intrinsics.areEqual(contentKey, contentKey2)) {
                throw new IllegalStateException("Check failed.");
            }
            if (transition != null) {
                if (!Intrinsics.areEqual(transition.fromContent, contentKey) || !Intrinsics.areEqual(transition.toContent, contentKey2)) {
                    throw new IllegalStateException("Check failed.");
                }
            }
        }

        public /* synthetic */ Transition(ContentKey contentKey, ContentKey contentKey2, Transition transition, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(contentKey, contentKey2, (i & 4) != 0 ? null : transition, null);
        }
    }

    Set getCurrentOverlays();

    SceneKey getCurrentScene();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Idle implements TransitionState {
        public final Set currentOverlays;
        public final SceneKey currentScene;

        public Idle(SceneKey sceneKey, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(sceneKey, (i & 2) != 0 ? EmptySet.INSTANCE : set);
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

        @Override // com.android.compose.animation.scene.content.state.TransitionState
        public final Set getCurrentOverlays() {
            return this.currentOverlays;
        }

        @Override // com.android.compose.animation.scene.content.state.TransitionState
        public final SceneKey getCurrentScene() {
            return this.currentScene;
        }

        public final int hashCode() {
            return this.currentOverlays.hashCode() + (this.currentScene.identity.hashCode() * 31);
        }

        public final String toString() {
            return "Idle(currentScene=" + this.currentScene + ", currentOverlays=" + this.currentOverlays + ")";
        }

        public Idle(SceneKey sceneKey, Set<OverlayKey> set) {
            this.currentScene = sceneKey;
            this.currentOverlays = set;
        }
    }
}
