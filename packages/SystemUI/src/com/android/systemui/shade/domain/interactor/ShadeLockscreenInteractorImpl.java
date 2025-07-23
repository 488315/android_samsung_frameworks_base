package com.android.systemui.shade.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.TransitionKey;
import com.android.keyguard.SecLockIconViewController;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.shared.model.TransitionKeys;
import com.android.systemui.shade.data.repository.ShadeRepository;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeLockscreenInteractorImpl implements ShadeLockscreenInteractor {
    public final CoroutineScope backgroundScope;
    public final CoroutineDispatcher mainDispatcher;
    public final SceneInteractor sceneInteractor;
    public final ShadeInteractor shadeInteractor;

    public ShadeLockscreenInteractorImpl(CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, ShadeInteractor shadeInteractor, SceneInteractor sceneInteractor, SecLockIconViewController secLockIconViewController, ShadeRepository shadeRepository) {
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundScope = coroutineScope;
        this.shadeInteractor = shadeInteractor;
        this.sceneInteractor = sceneInteractor;
        shadeRepository.getClass();
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void expandToNotifications() {
        ((ShadeInteractorImpl) this.shadeInteractor).expandNotificationsShade("ShadeLockscreenInteractorImpl.expandToNotifications");
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final boolean isExpanded() {
        return ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isAnyExpanded().getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor, com.android.systemui.shade.domain.interactor.PanelExpansionInteractor
    public final boolean isFullyCollapsed() {
        return true;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void resetViews(boolean z) {
        TransitionKeys.INSTANCE.getClass();
        TransitionKey transitionKey = TransitionKeys.Instant;
        if (z) {
            transitionKey = null;
        }
        BaseShadeInteractor.collapseQuickSettingsShade$default(this.shadeInteractor, "ShadeLockscreenInteractorImpl.resetViews", transitionKey, 4);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void showAodUi() {
        SceneInteractor.changeScene$default(this.sceneInteractor, Scenes.Lockscreen, "showAodUi", null, KeyguardState.AOD, false, 20);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void transitionToExpandedShade(long j, boolean z) {
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new ShadeLockscreenInteractorImpl$transitionToExpandedShade$1(j, this, null), 7);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setOverStretchAmount(float f) {
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor
    public final void setPulsing(boolean z) {
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
}
