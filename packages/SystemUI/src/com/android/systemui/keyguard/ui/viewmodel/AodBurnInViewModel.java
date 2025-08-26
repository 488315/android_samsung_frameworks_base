package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.keyguard.domain.interactor.BurnInInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.BurnInModel;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class AodBurnInViewModel {
    public final AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel;
    public final BurnInInteractor burnInInteractor;
    public final StateFlowImpl burnInParams;
    public final ConfigurationInteractor configurationInteractor;
    public final GoneToAodTransitionViewModel goneToAodTransitionViewModel;
    public final KeyguardClockViewModel keyguardClockViewModel;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel;
    public final ReadonlyStateFlow movement;
    public final OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel;

    public AodBurnInViewModel(CoroutineScope coroutineScope, BurnInInteractor burnInInteractor, ConfigurationInteractor configurationInteractor, KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, GoneToAodTransitionViewModel goneToAodTransitionViewModel, LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel, AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel, OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel, KeyguardClockViewModel keyguardClockViewModel) {
        this.burnInInteractor = burnInInteractor;
        this.configurationInteractor = configurationInteractor;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.goneToAodTransitionViewModel = goneToAodTransitionViewModel;
        this.lockscreenToAodTransitionViewModel = lockscreenToAodTransitionViewModel;
        this.aodToLockscreenTransitionViewModel = aodToLockscreenTransitionViewModel;
        this.occludedToLockscreenTransitionViewModel = occludedToLockscreenTransitionViewModel;
        this.keyguardClockViewModel = keyguardClockViewModel;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new BurnInParameters(0, 0, null, null, 15, null));
        this.burnInParams = stateFlowImplMutableStateFlow;
        this.movement = FlowKt.stateIn(FlowKt.transformLatest(stateFlowImplMutableStateFlow, new AodBurnInViewModel$special$$inlined$flatMapLatest$1(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), new BurnInModel(0, 0, 0.0f, false, 15, null));
    }
}
