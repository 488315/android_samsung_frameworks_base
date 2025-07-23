package com.android.systemui.shade.domain.interactor;

import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.shared.model.TransitionKeys;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeInteractorSceneContainerImpl implements BaseShadeInteractor {
    public final SceneInteractor sceneInteractor;
    public final ShadeModeInteractor shadeModeInteractor;

    public ShadeInteractorSceneContainerImpl(CoroutineScope coroutineScope, SceneInteractor sceneInteractor, ShadeModeInteractor shadeModeInteractor) {
        this.sceneInteractor = sceneInteractor;
        this.shadeModeInteractor = shadeModeInteractor;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        throw new IllegalStateException("New code path not supported when SceneContainerFlag is disabled.");
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final void collapseEitherShade(String str, TransitionKey transitionKey) {
        throw null;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final void collapseNotificationsShade(String str, TransitionKey transitionKey) {
        ShadeModeInteractor shadeModeInteractor = this.shadeModeInteractor;
        boolean isDualShade = shadeModeInteractor.isDualShade();
        SceneInteractor sceneInteractor = this.sceneInteractor;
        if (isDualShade) {
            TransitionKeys.INSTANCE.getClass();
            if (Intrinsics.areEqual(transitionKey, TransitionKeys.Instant)) {
                sceneInteractor.instantlyHideOverlay(Overlays.NotificationsShade, str);
                return;
            } else {
                sceneInteractor.hideOverlay(Overlays.NotificationsShade, str, transitionKey);
                return;
            }
        }
        TransitionKeys.INSTANCE.getClass();
        if (Intrinsics.areEqual(transitionKey, TransitionKeys.Instant)) {
            sceneInteractor.snapToScene(SceneFamilies.Home, str.concat(" (collapseNotificationsShade)"));
            return;
        }
        SceneKey sceneKey = SceneFamilies.Home;
        String concat = str.concat(" (collapseNotificationsShade)");
        if (transitionKey == null) {
            transitionKey = TransitionKeys.ToSplitShade;
            if (!shadeModeInteractor.isSplitShade()) {
                transitionKey = null;
            }
        }
        SceneInteractor.changeScene$default(this.sceneInteractor, sceneKey, concat, transitionKey, null, false, 24);
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final void collapseQuickSettingsShade(String str, TransitionKey transitionKey, boolean z) {
        ShadeModeInteractor shadeModeInteractor = this.shadeModeInteractor;
        boolean isDualShade = shadeModeInteractor.isDualShade();
        SceneInteractor sceneInteractor = this.sceneInteractor;
        if (isDualShade) {
            TransitionKeys.INSTANCE.getClass();
            if (Intrinsics.areEqual(transitionKey, TransitionKeys.Instant)) {
                sceneInteractor.instantlyHideOverlay(Overlays.QuickSettingsShade, str);
                return;
            } else {
                sceneInteractor.hideOverlay(Overlays.QuickSettingsShade, str, transitionKey);
                return;
            }
        }
        boolean isSplitShade = shadeModeInteractor.isSplitShade();
        SceneKey sceneKey = (z || isSplitShade) ? SceneFamilies.Home : Scenes.Shade;
        TransitionKeys.INSTANCE.getClass();
        if (Intrinsics.areEqual(transitionKey, TransitionKeys.Instant)) {
            sceneInteractor.snapToScene(sceneKey, str.concat(" (collapseQuickSettingsShade)"));
            return;
        }
        String concat = str.concat(" (collapseQuickSettingsShade)");
        if (transitionKey == null) {
            transitionKey = TransitionKeys.ToSplitShade;
            if (!isSplitShade) {
                transitionKey = null;
            }
        }
        SceneInteractor.changeScene$default(this.sceneInteractor, sceneKey, concat, transitionKey, null, false, 24);
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final void expandNotificationsShade(String str) {
        ShadeModeInteractor shadeModeInteractor = this.shadeModeInteractor;
        if (!shadeModeInteractor.isDualShade()) {
            SceneKey sceneKey = Scenes.Shade;
            TransitionKeys.INSTANCE.getClass();
            SceneInteractor.changeScene$default(this.sceneInteractor, sceneKey, str, shadeModeInteractor.isSplitShade() ? TransitionKeys.ToSplitShade : null, null, false, 24);
        } else {
            OverlayKey overlayKey = Overlays.QuickSettingsShade;
            SceneInteractor sceneInteractor = this.sceneInteractor;
            sceneInteractor.hideOverlay(overlayKey, str, null);
            sceneInteractor.showOverlay(Overlays.NotificationsShade, str);
        }
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final void expandQuickSettingsShade(String str) {
        ShadeModeInteractor shadeModeInteractor = this.shadeModeInteractor;
        if (shadeModeInteractor.isDualShade()) {
            OverlayKey overlayKey = Overlays.NotificationsShade;
            SceneInteractor sceneInteractor = this.sceneInteractor;
            sceneInteractor.hideOverlay(overlayKey, str, null);
            sceneInteractor.showOverlay(Overlays.QuickSettingsShade, str);
            return;
        }
        boolean isSplitShade = shadeModeInteractor.isSplitShade();
        SceneKey sceneKey = isSplitShade ? Scenes.Shade : Scenes.QuickSettings;
        TransitionKeys.INSTANCE.getClass();
        SceneInteractor.changeScene$default(this.sceneInteractor, sceneKey, str, isSplitShade ? TransitionKeys.ToSplitShade : null, null, false, 24);
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getAnyExpansion() {
        return null;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getQsExpansion() {
        return null;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getShadeExpansion() {
        return null;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow isAnyExpanded() {
        return null;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isQsBypassingShade() {
        return null;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow isQsExpanded() {
        return null;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isQsFullscreen() {
        return null;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isUserInteractingWithQs() {
        return null;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isUserInteractingWithShade() {
        return null;
    }
}
