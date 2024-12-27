package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.qs.panels.data.repository.QSPreferencesRepository;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSPreferencesInteractor {
    public final QSPreferencesRepository repo;
    public final Flow showLabels;

    public QSPreferencesInteractor(QSPreferencesRepository qSPreferencesRepository) {
        this.showLabels = qSPreferencesRepository.showLabels;
    }
}
