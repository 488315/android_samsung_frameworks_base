package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.qs.pipeline.data.repository.MinimumTilesRepository;

public final class MinimumTilesInteractor {
    public final MinimumTilesRepository minimumTilesRepository;

    public MinimumTilesInteractor(MinimumTilesRepository minimumTilesRepository) {
        this.minimumTilesRepository = minimumTilesRepository;
    }
}
