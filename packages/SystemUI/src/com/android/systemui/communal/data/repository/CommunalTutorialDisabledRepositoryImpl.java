package com.android.systemui.communal.data.repository;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalTutorialDisabledRepositoryImpl implements CommunalTutorialRepository {
    public final ReadonlyStateFlow tutorialSettingState;

    public CommunalTutorialDisabledRepositoryImpl(CoroutineScope coroutineScope) {
        this.tutorialSettingState = FlowKt.stateIn(EmptyFlow.INSTANCE, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 10);
    }
}
