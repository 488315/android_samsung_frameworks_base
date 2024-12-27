package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;

public final class TilesAvailabilityInteractor {
    public final QSFactory qsFactoryImpl;
    public final QSPipelineFlagsRepository qsPipelineFlagsRepository;

    public TilesAvailabilityInteractor(NewTilesAvailabilityInteractor newTilesAvailabilityInteractor, QSFactory qSFactory, QSPipelineFlagsRepository qSPipelineFlagsRepository) {
        this.qsFactoryImpl = qSFactory;
        this.qsPipelineFlagsRepository = qSPipelineFlagsRepository;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getUnavailableTiles(java.lang.Iterable r5, kotlin.coroutines.Continuation r6) {
        /*
            Method dump skipped, instructions count: 247
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.domain.interactor.TilesAvailabilityInteractor.getUnavailableTiles(java.lang.Iterable, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
