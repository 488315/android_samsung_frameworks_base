package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.qs.panels.data.repository.QSPreferencesRepository;
import kotlinx.coroutines.flow.Flow;

public final class QSPreferencesInteractor {
    public final QSPreferencesRepository repo;
    public final Flow showLabels;

    public QSPreferencesInteractor(QSPreferencesRepository qSPreferencesRepository) {
        this.showLabels = qSPreferencesRepository.showLabels;
    }
}
