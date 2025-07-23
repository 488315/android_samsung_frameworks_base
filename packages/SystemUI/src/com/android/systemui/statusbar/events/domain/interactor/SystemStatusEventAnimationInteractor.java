package com.android.systemui.statusbar.events.domain.interactor;

import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.statusbar.events.data.repository.SystemStatusEventAnimationRepository;
import com.android.systemui.statusbar.events.data.repository.SystemStatusEventAnimationRepositoryImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SystemStatusEventAnimationInteractor {
    public final ReadonlyStateFlow animationState;
    public final ReadonlyStateFlow chipAnimateInTranslationX;
    public final ReadonlyStateFlow chipAnimateOutTranslationX;

    public SystemStatusEventAnimationInteractor(SystemStatusEventAnimationRepository systemStatusEventAnimationRepository, ConfigurationInteractor configurationInteractor, CoroutineScope coroutineScope) {
        ConfigurationInteractorImpl configurationInteractorImpl = (ConfigurationInteractorImpl) configurationInteractor;
        ChannelFlowTransformLatest dimensionPixelSize = configurationInteractorImpl.dimensionPixelSize(R.dimen.ongoing_appops_chip_animation_in_status_bar_translation_x);
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.chipAnimateInTranslationX = FlowKt.stateIn(dimensionPixelSize, coroutineScope, startedEagerly, 0);
        this.chipAnimateOutTranslationX = FlowKt.stateIn(configurationInteractorImpl.dimensionPixelSize(R.dimen.ongoing_appops_chip_animation_out_status_bar_translation_x), coroutineScope, startedEagerly, 0);
        this.animationState = ((SystemStatusEventAnimationRepositoryImpl) systemStatusEventAnimationRepository).animationState;
    }
}
