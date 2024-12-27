package com.android.systemui.communal.data.repository;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

public final class CommunalTutorialDisabledRepositoryImpl implements CommunalTutorialRepository {
    public final ReadonlyStateFlow tutorialSettingState;

    public CommunalTutorialDisabledRepositoryImpl(CoroutineScope coroutineScope) {
        this.tutorialSettingState = FlowKt.stateIn(EmptyFlow.INSTANCE, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 10);
    }
}
