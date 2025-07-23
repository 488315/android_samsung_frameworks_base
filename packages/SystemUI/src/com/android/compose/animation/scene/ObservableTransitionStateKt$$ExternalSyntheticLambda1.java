package com.android.compose.animation.scene;

import androidx.compose.runtime.SnapshotStateKt;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.content.state.TransitionState;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ObservableTransitionStateKt$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ObservableTransitionStateKt$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object replaceOverlay;
        switch (this.$r8$classId) {
            case 0:
                return ((TransitionState.Transition.ChangeScene) this.f$0).getCurrentScene();
            case 1:
                return Boolean.valueOf(((TransitionState.Transition.ChangeScene) this.f$0).isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
            case 2:
                return Float.valueOf(((TransitionState.Transition.ChangeScene) this.f$0).getProgress());
            case 3:
                return Boolean.valueOf(((TransitionState.Transition.ChangeScene) this.f$0).isUserInputOngoing());
            case 4:
                return Float.valueOf(((TransitionState.Transition.ChangeScene) this.f$0).getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
            default:
                TransitionState transitionState = ((MutableSceneTransitionLayoutStateImpl) ((MutableSceneTransitionLayoutState) this.f$0)).getTransitionState();
                if (transitionState instanceof TransitionState.Idle) {
                    TransitionState.Idle idle = (TransitionState.Idle) transitionState;
                    return new ObservableTransitionState.Idle(idle.currentScene, idle.currentOverlays);
                }
                if (transitionState instanceof TransitionState.Transition.ChangeScene) {
                    TransitionState.Transition.ChangeScene changeScene = (TransitionState.Transition.ChangeScene) transitionState;
                    SceneKey sceneKey = changeScene.fromScene;
                    TransitionState.Transition.ChangeScene changeScene2 = (TransitionState.Transition.ChangeScene) transitionState;
                    SafeFlow snapshotFlow = SnapshotStateKt.snapshotFlow(new ObservableTransitionStateKt$$ExternalSyntheticLambda1(changeScene2, 0));
                    Set set = changeScene.currentOverlaysWhenTransitionStarted;
                    replaceOverlay = new ObservableTransitionState.Transition.ChangeScene(sceneKey, changeScene.toScene, snapshotFlow, set != null ? set : null, SnapshotStateKt.snapshotFlow(new ObservableTransitionStateKt$$ExternalSyntheticLambda1(changeScene2, 2)), changeScene.isInitiatedByUserInput(), SnapshotStateKt.snapshotFlow(new ObservableTransitionStateKt$$ExternalSyntheticLambda1(changeScene2, 3)), SnapshotStateKt.snapshotFlow(new ObservableTransitionStateKt$$ExternalSyntheticLambda1(changeScene2, 4)), SnapshotStateKt.snapshotFlow(new ObservableTransitionStateKt$$ExternalSyntheticLambda1(changeScene2, 1)));
                } else {
                    if (transitionState instanceof TransitionState.Transition.ShowOrHideOverlay) {
                        TransitionState.Transition.ShowOrHideOverlay showOrHideOverlay = (TransitionState.Transition.ShowOrHideOverlay) transitionState;
                        SceneKey sceneKey2 = showOrHideOverlay.fromOrToScene;
                        SceneKey sceneKey3 = showOrHideOverlay.currentSceneWhenTransitionStarted;
                        if (sceneKey3 == null) {
                            sceneKey3 = null;
                        }
                        if (!Intrinsics.areEqual(sceneKey2, sceneKey3)) {
                            throw new IllegalStateException("Check failed.");
                        }
                        SceneKey sceneKey4 = showOrHideOverlay.currentSceneWhenTransitionStarted;
                        SceneKey sceneKey5 = sceneKey4 != null ? sceneKey4 : null;
                        final TransitionState.Transition.ShowOrHideOverlay showOrHideOverlay2 = (TransitionState.Transition.ShowOrHideOverlay) transitionState;
                        final int i = 0;
                        SafeFlow snapshotFlow2 = SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.compose.animation.scene.ObservableTransitionStateKt$$ExternalSyntheticLambda11
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                switch (i) {
                                    case 0:
                                        return showOrHideOverlay2.getCurrentOverlays();
                                    case 1:
                                        return Float.valueOf(showOrHideOverlay2.getProgress());
                                    case 2:
                                        return Boolean.valueOf(showOrHideOverlay2.isUserInputOngoing());
                                    case 3:
                                        return Float.valueOf(showOrHideOverlay2.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                    default:
                                        return Boolean.valueOf(showOrHideOverlay2.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                }
                            }
                        });
                        final int i2 = 1;
                        SafeFlow snapshotFlow3 = SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.compose.animation.scene.ObservableTransitionStateKt$$ExternalSyntheticLambda11
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                switch (i2) {
                                    case 0:
                                        return showOrHideOverlay2.getCurrentOverlays();
                                    case 1:
                                        return Float.valueOf(showOrHideOverlay2.getProgress());
                                    case 2:
                                        return Boolean.valueOf(showOrHideOverlay2.isUserInputOngoing());
                                    case 3:
                                        return Float.valueOf(showOrHideOverlay2.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                    default:
                                        return Boolean.valueOf(showOrHideOverlay2.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                }
                            }
                        });
                        boolean isInitiatedByUserInput = showOrHideOverlay.isInitiatedByUserInput();
                        final int i3 = 2;
                        SafeFlow snapshotFlow4 = SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.compose.animation.scene.ObservableTransitionStateKt$$ExternalSyntheticLambda11
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                switch (i3) {
                                    case 0:
                                        return showOrHideOverlay2.getCurrentOverlays();
                                    case 1:
                                        return Float.valueOf(showOrHideOverlay2.getProgress());
                                    case 2:
                                        return Boolean.valueOf(showOrHideOverlay2.isUserInputOngoing());
                                    case 3:
                                        return Float.valueOf(showOrHideOverlay2.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                    default:
                                        return Boolean.valueOf(showOrHideOverlay2.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                }
                            }
                        });
                        final int i4 = 3;
                        SafeFlow snapshotFlow5 = SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.compose.animation.scene.ObservableTransitionStateKt$$ExternalSyntheticLambda11
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                switch (i4) {
                                    case 0:
                                        return showOrHideOverlay2.getCurrentOverlays();
                                    case 1:
                                        return Float.valueOf(showOrHideOverlay2.getProgress());
                                    case 2:
                                        return Boolean.valueOf(showOrHideOverlay2.isUserInputOngoing());
                                    case 3:
                                        return Float.valueOf(showOrHideOverlay2.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                    default:
                                        return Boolean.valueOf(showOrHideOverlay2.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                }
                            }
                        });
                        final int i5 = 4;
                        return new ObservableTransitionState.Transition.ShowOrHideOverlay(showOrHideOverlay.overlay, showOrHideOverlay.fromContent, showOrHideOverlay.toContent, sceneKey5, snapshotFlow2, snapshotFlow3, isInitiatedByUserInput, snapshotFlow4, snapshotFlow5, SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.compose.animation.scene.ObservableTransitionStateKt$$ExternalSyntheticLambda11
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                switch (i5) {
                                    case 0:
                                        return showOrHideOverlay2.getCurrentOverlays();
                                    case 1:
                                        return Float.valueOf(showOrHideOverlay2.getProgress());
                                    case 2:
                                        return Boolean.valueOf(showOrHideOverlay2.isUserInputOngoing());
                                    case 3:
                                        return Float.valueOf(showOrHideOverlay2.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                    default:
                                        return Boolean.valueOf(showOrHideOverlay2.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                }
                            }
                        }));
                    }
                    if (!(transitionState instanceof TransitionState.Transition.ReplaceOverlay)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    TransitionState.Transition.ReplaceOverlay replaceOverlay2 = (TransitionState.Transition.ReplaceOverlay) transitionState;
                    OverlayKey overlayKey = replaceOverlay2.fromOverlay;
                    SceneKey sceneKey6 = replaceOverlay2.currentSceneWhenTransitionStarted;
                    SceneKey sceneKey7 = sceneKey6 != null ? sceneKey6 : null;
                    final TransitionState.Transition.ReplaceOverlay replaceOverlay3 = (TransitionState.Transition.ReplaceOverlay) transitionState;
                    final int i6 = 0;
                    SafeFlow snapshotFlow6 = SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.compose.animation.scene.ObservableTransitionStateKt$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i6) {
                                case 0:
                                    return replaceOverlay3.getCurrentOverlays();
                                case 1:
                                    return Float.valueOf(replaceOverlay3.getProgress());
                                case 2:
                                    return Boolean.valueOf(replaceOverlay3.isUserInputOngoing());
                                case 3:
                                    return Float.valueOf(replaceOverlay3.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                default:
                                    return Boolean.valueOf(replaceOverlay3.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                            }
                        }
                    });
                    final int i7 = 1;
                    SafeFlow snapshotFlow7 = SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.compose.animation.scene.ObservableTransitionStateKt$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i7) {
                                case 0:
                                    return replaceOverlay3.getCurrentOverlays();
                                case 1:
                                    return Float.valueOf(replaceOverlay3.getProgress());
                                case 2:
                                    return Boolean.valueOf(replaceOverlay3.isUserInputOngoing());
                                case 3:
                                    return Float.valueOf(replaceOverlay3.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                default:
                                    return Boolean.valueOf(replaceOverlay3.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                            }
                        }
                    });
                    boolean isInitiatedByUserInput2 = replaceOverlay2.isInitiatedByUserInput();
                    final int i8 = 2;
                    SafeFlow snapshotFlow8 = SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.compose.animation.scene.ObservableTransitionStateKt$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i8) {
                                case 0:
                                    return replaceOverlay3.getCurrentOverlays();
                                case 1:
                                    return Float.valueOf(replaceOverlay3.getProgress());
                                case 2:
                                    return Boolean.valueOf(replaceOverlay3.isUserInputOngoing());
                                case 3:
                                    return Float.valueOf(replaceOverlay3.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                default:
                                    return Boolean.valueOf(replaceOverlay3.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                            }
                        }
                    });
                    final int i9 = 3;
                    SafeFlow snapshotFlow9 = SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.compose.animation.scene.ObservableTransitionStateKt$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i9) {
                                case 0:
                                    return replaceOverlay3.getCurrentOverlays();
                                case 1:
                                    return Float.valueOf(replaceOverlay3.getProgress());
                                case 2:
                                    return Boolean.valueOf(replaceOverlay3.isUserInputOngoing());
                                case 3:
                                    return Float.valueOf(replaceOverlay3.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                default:
                                    return Boolean.valueOf(replaceOverlay3.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                            }
                        }
                    });
                    final int i10 = 4;
                    replaceOverlay = new ObservableTransitionState.Transition.ReplaceOverlay(overlayKey, replaceOverlay2.toOverlay, sceneKey7, snapshotFlow6, snapshotFlow7, isInitiatedByUserInput2, snapshotFlow8, snapshotFlow9, SnapshotStateKt.snapshotFlow(new Function0() { // from class: com.android.compose.animation.scene.ObservableTransitionStateKt$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i10) {
                                case 0:
                                    return replaceOverlay3.getCurrentOverlays();
                                case 1:
                                    return Float.valueOf(replaceOverlay3.getProgress());
                                case 2:
                                    return Boolean.valueOf(replaceOverlay3.isUserInputOngoing());
                                case 3:
                                    return Float.valueOf(replaceOverlay3.getPreviewProgress$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                                default:
                                    return Boolean.valueOf(replaceOverlay3.isInPreviewStage$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
                            }
                        }
                    }));
                }
                return replaceOverlay;
        }
    }
}
