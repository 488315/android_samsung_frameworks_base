package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.qs.panels.data.repository.QSColumnsRepository;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSColumnsInteractor {
    public final ReadonlyStateFlow columns;

    public QSColumnsInteractor(CoroutineScope coroutineScope, QSColumnsRepository qSColumnsRepository, ShadeModeInteractor shadeModeInteractor) {
        this.columns = FlowKt.stateIn(FlowKt.transformLatest(((ShadeModeInteractorImpl) shadeModeInteractor).shadeMode, new QSColumnsInteractor$special$$inlined$flatMapLatest$1(null, qSColumnsRepository)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Integer.valueOf(qSColumnsRepository.defaultColumns));
    }
}
