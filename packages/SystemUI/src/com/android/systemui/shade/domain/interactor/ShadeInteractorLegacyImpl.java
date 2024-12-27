package com.android.systemui.shade.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
    public final ReadonlyStateFlow shadeMode;

    public ShadeInteractorLegacyImpl(CoroutineScope coroutineScope, KeyguardRepository keyguardRepository, SharedNotificationContainerInteractor sharedNotificationContainerInteractor, ShadeRepository shadeRepository) {
        ShadeRepositoryImpl shadeRepositoryImpl = (ShadeRepositoryImpl) shadeRepository;
        ReadonlyStateFlow readonlyStateFlow = shadeRepositoryImpl.lockscreenShadeExpansion;
        ShadeInteractorLegacyImpl$shadeExpansion$1 shadeInteractorLegacyImpl$shadeExpansion$1 = new ShadeInteractorLegacyImpl$shadeExpansion$1(null);
        ReadonlyStateFlow readonlyStateFlow2 = ((KeyguardRepositoryImpl) keyguardRepository).statusBarState;
        Flow flow = sharedNotificationContainerInteractor.isSplitShadeEnabled;
        ReadonlyStateFlow readonlyStateFlow3 = shadeRepositoryImpl.legacyShadeExpansion;
        ReadonlyStateFlow readonlyStateFlow4 = shadeRepositoryImpl.qsExpansion;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.combine(readonlyStateFlow, readonlyStateFlow2, readonlyStateFlow3, readonlyStateFlow4, flow, shadeInteractorLegacyImpl$shadeExpansion$1));
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(distinctUntilChanged, coroutineScope, startedEagerly, Float.valueOf(0.0f));
        this.shadeExpansion = stateIn;
        this.qsExpansion = readonlyStateFlow4;
        this.isQsExpanded = shadeRepositoryImpl.legacyIsQsExpanded;
        this.isQsBypassingShade = shadeRepositoryImpl.legacyExpandImmediate;
        ReadonlyStateFlow readonlyStateFlow5 = shadeRepositoryImpl.legacyQsFullscreen;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, readonlyStateFlow4, new ShadeInteractorKt$createAnyExpansionFlow$1(null));
        companion.getClass();
        this.anyExpansion = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedEagerly, Float.valueOf(0.0f));
        this.isAnyExpanded = FlowKt.stateIn(shadeRepositoryImpl.legacyExpandedOrAwaitingInputTransfer, coroutineScope, startedEagerly, Boolean.FALSE);
        this.isUserInteractingWithShade = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new SafeFlow(new ShadeInteractorLegacyImpl$userInteractingFlow$1(shadeRepositoryImpl.legacyShadeTracking, readonlyStateFlow3, null)), shadeRepositoryImpl.legacyLockscreenShadeTracking, new ShadeInteractorLegacyImpl$isUserInteractingWithShade$1(null));
        this.isUserInteractingWithQs = new SafeFlow(new ShadeInteractorLegacyImpl$userInteractingFlow$1(shadeRepositoryImpl.legacyQsTracking, readonlyStateFlow4, null));
        this.shadeMode = shadeRepositoryImpl.shadeMode;
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
    public final StateFlow getShadeMode() {
        return this.shadeMode;
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
    public final Flow isUserInteractingWithQs() {
        return this.isUserInteractingWithQs;
    }

    @Override // com.android.systemui.shade.domain.interactor.BaseShadeInteractor
    public final Flow isUserInteractingWithShade() {
        return this.isUserInteractingWithShade;
    }
}
