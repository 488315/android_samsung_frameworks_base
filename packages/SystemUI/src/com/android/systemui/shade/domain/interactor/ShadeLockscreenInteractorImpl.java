package com.android.systemui.shade.domain.interactor;

import com.android.keyguard.SecLockIconViewController;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.data.repository.ShadeRepository;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShadeLockscreenInteractorImpl implements ShadeLockscreenInteractor {
    public final CoroutineScope backgroundScope;
    public final boolean isFullyCollapsed = true;
    public final SecLockIconViewController lockIconViewController;
    public final CoroutineDispatcher mainDispatcher;
    public final SceneInteractor sceneInteractor;
    public final ShadeInteractor shadeInteractor;

    public ShadeLockscreenInteractorImpl(CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, ShadeInteractor shadeInteractor, SceneInteractor sceneInteractor, SecLockIconViewController secLockIconViewController, ShadeRepository shadeRepository) {
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundScope = coroutineScope;
        this.shadeInteractor = shadeInteractor;
        this.sceneInteractor = sceneInteractor;
        this.lockIconViewController = secLockIconViewController;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void dozeTimeTick() {
        this.lockIconViewController.getClass();
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void expandToNotifications() {
        SceneInteractor.changeScene$default(this.sceneInteractor, SceneFamilies.NotifShade, "ShadeLockscreenInteractorImpl.expandToNotifications", null, 12);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final boolean isExpanded() {
        return ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor, com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isFullyCollapsed() {
        return this.isFullyCollapsed;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void resetViews(boolean z) {
        SceneInteractor.changeScene$default(this.sceneInteractor, SceneFamilies.NotifShade, "ShadeLockscreenInteractorImpl.expandToNotifications", null, 12);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void showAodUi() {
        SceneInteractor.changeScene$default(this.sceneInteractor, Scenes.Lockscreen, "showAodUi", null, 12);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void transitionToExpandedShade(long j, boolean z) {
        BuildersKt.launch$default(this.backgroundScope, null, null, new ShadeLockscreenInteractorImpl$transitionToExpandedShade$1(j, this, null), 3);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void blockExpansionForCurrentTouch() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void resetViewGroupFade() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setKeyguardStatusBarAlpha() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void startBouncerPreHideAnimation() {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setKeyguardTransitionProgress(float f) {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setOverStretchAmount(float f) {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setPulsing(boolean z) {
    }
}
