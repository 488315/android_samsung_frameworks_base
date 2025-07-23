package com.android.systemui.shade.domain.interactor;

import com.android.app.tracing.FlowTracing;
import com.android.compose.animation.scene.TransitionKey;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeInteractorLegacyImpl implements BaseShadeInteractor {
    public final ReadonlyStateFlow anyExpansion;
    public final ReadonlyStateFlow isAnyExpanded;
    public final ReadonlyStateFlow isQsBypassingShade;
    public final ReadonlyStateFlow isQsExpanded;
    public final ReadonlyStateFlow isQsFullscreen;
    public final SafeFlow isUserInteractingWithQs;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isUserInteractingWithShade;
    public final ReadonlyStateFlow qsExpansion;
    public final ReadonlyStateFlow shadeExpansion;

    public ShadeInteractorLegacyImpl(CoroutineScope coroutineScope, KeyguardRepository keyguardRepository, ShadeRepository shadeRepository) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        FlowTracing flowTracing = FlowTracing.INSTANCE;
        ShadeRepositoryImpl shadeRepositoryImpl = (ShadeRepositoryImpl) shadeRepository;
        ReadonlyStateFlow readonlyStateFlow = shadeRepositoryImpl.lockscreenShadeExpansion;
        ReadonlyStateFlow readonlyStateFlow2 = ((KeyguardRepositoryImpl) keyguardRepository).statusBarState;
        ReadonlyStateFlow readonlyStateFlow3 = shadeRepositoryImpl.legacyShadeExpansion;
        ShadeInteractorLegacyImpl$shadeExpansion$1 shadeInteractorLegacyImpl$shadeExpansion$1 = new ShadeInteractorLegacyImpl$shadeExpansion$1(null);
        ReadonlyStateFlow readonlyStateFlow4 = shadeRepositoryImpl.qsExpansion;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 traceAsCounter$default = FlowTracing.traceAsCounter$default(flowTracing, FlowKt.distinctUntilChanged(FlowKt.combine(readonlyStateFlow, readonlyStateFlow2, readonlyStateFlow3, readonlyStateFlow4, shadeRepositoryImpl.isShadeLayoutWide, shadeInteractorLegacyImpl$shadeExpansion$1)), "panel_expansion", new ShadeInteractorLegacyImpl$$ExternalSyntheticLambda0());
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(traceAsCounter$default, coroutineScope, startedEagerly, Float.valueOf(0.0f));
        this.shadeExpansion = stateIn;
        this.qsExpansion = readonlyStateFlow4;
        this.isQsExpanded = shadeRepositoryImpl.legacyIsQsExpanded;
        this.isQsBypassingShade = shadeRepositoryImpl.legacyExpandImmediate;
        this.isQsFullscreen = shadeRepositoryImpl.legacyQsFullscreen;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, readonlyStateFlow4, new ShadeInteractorKt$createAnyExpansionFlow$1(null));
        companion.getClass();
        this.anyExpansion = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedEagerly, Float.valueOf(0.0f));
        this.isAnyExpanded = FlowKt.stateIn(shadeRepositoryImpl.legacyExpandedOrAwaitingInputTransfer, coroutineScope, startedEagerly, Boolean.FALSE);
        this.isUserInteractingWithShade = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new SafeFlow(new ShadeInteractorLegacyImpl$userInteractingFlow$1(shadeRepositoryImpl.legacyShadeTracking, readonlyStateFlow3, null)), shadeRepositoryImpl.legacyLockscreenShadeTracking, new ShadeInteractorLegacyImpl$isUserInteractingWithShade$1(null));
        this.isUserInteractingWithQs = new SafeFlow(new ShadeInteractorLegacyImpl$userInteractingFlow$1(shadeRepositoryImpl.legacyQsTracking, readonlyStateFlow4, null));
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final void collapseEitherShade(String str, TransitionKey transitionKey) {
        throw new UnsupportedOperationException("collapseEitherShade() is not supported in legacy shade");
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final void collapseNotificationsShade(String str, TransitionKey transitionKey) {
        throw new UnsupportedOperationException("collapseNotificationShade() is not supported in legacy shade");
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final void collapseQuickSettingsShade(String str, TransitionKey transitionKey, boolean z) {
        throw new UnsupportedOperationException("collapseQuickSettingsShade() is not supported in legacy shade");
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final void expandNotificationsShade(String str) {
        throw new UnsupportedOperationException("expandNotificationShade() is not supported in legacy shade");
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final void expandQuickSettingsShade(String str) {
        throw new UnsupportedOperationException("expandQuickSettingsShade() is not supported in legacy shade");
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getAnyExpansion() {
        return this.anyExpansion;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getQsExpansion() {
        return this.qsExpansion;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow getShadeExpansion() {
        return this.shadeExpansion;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow isAnyExpanded() {
        return this.isAnyExpanded;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isQsBypassingShade() {
        return this.isQsBypassingShade;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final StateFlow isQsExpanded() {
        return this.isQsExpanded;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isQsFullscreen() {
        return this.isQsFullscreen;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isUserInteractingWithQs() {
        return this.isUserInteractingWithQs;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isUserInteractingWithShade() {
        return this.isUserInteractingWithShade;
    }
}
