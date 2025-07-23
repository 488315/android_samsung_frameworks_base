package com.android.systemui.shade.domain.interactor;

import com.android.compose.animation.scene.ContentKey;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PanelExpansionInteractorImpl implements PanelExpansionInteractor {
    public final ChannelFlowTransformLatest legacyPanelExpansion;
    public final SceneInteractor sceneInteractor;
    public final ShadeAnimationInteractor shadeAnimationInteractor;
    public final ShadeInteractor shadeInteractor;
    public final SysuiStatusBarStateController statusBarStateController;

    public PanelExpansionInteractorImpl(SceneInteractor sceneInteractor, ShadeInteractor shadeInteractor, ShadeAnimationInteractor shadeAnimationInteractor, SysuiStatusBarStateController sysuiStatusBarStateController) {
        this.sceneInteractor = sceneInteractor;
        this.shadeInteractor = shadeInteractor;
        this.shadeAnimationInteractor = shadeAnimationInteractor;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.legacyPanelExpansion = FlowKt.transformLatest(sceneInteractor.transitionState, new PanelExpansionInteractorImpl$special$$inlined$flatMapLatest$1(null, this));
    }

    public static final boolean access$isExpandable(PanelExpansionInteractorImpl panelExpansionInteractorImpl, ContentKey contentKey) {
        panelExpansionInteractorImpl.getClass();
        return Intrinsics.areEqual(contentKey, Scenes.Shade) || Intrinsics.areEqual(contentKey, Scenes.QuickSettings) || Intrinsics.areEqual(contentKey, Overlays.NotificationsShade) || Intrinsics.areEqual(contentKey, Overlays.QuickSettingsShade);
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final int getBarState() {
        return this.statusBarStateController.getState();
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isCollapsing() {
        ShadeAnimationInteractor shadeAnimationInteractor = this.shadeAnimationInteractor;
        return ((Boolean) shadeAnimationInteractor.isAnyCloseAnimationRunning().getValue()).booleanValue() || ((Boolean) shadeAnimationInteractor.isLaunchingActivity.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isFullyCollapsed() {
        return !((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isFullyExpanded() {
        return ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).isAnyFullyExpanded.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isPanelExpanded() {
        return ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isTracking() {
        return ((Boolean) this.sceneInteractor.isTransitionUserInputOngoing.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean shouldHideStatusBarIconsWhenExpanded() {
        if (((Boolean) this.shadeAnimationInteractor.isLaunchingActivity.$$delegate_0.getValue()).booleanValue()) {
            return false;
        }
        return Intrinsics.areEqual(this.sceneInteractor.currentScene.getValue(), Scenes.Lockscreen);
    }
}
