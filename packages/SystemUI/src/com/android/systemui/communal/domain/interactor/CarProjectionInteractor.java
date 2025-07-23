package com.android.systemui.communal.domain.interactor;

import com.android.systemui.communal.data.repository.CarProjectionRepository;
import com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CarProjectionInteractor {
    public CarProjectionInteractor(CarProjectionRepository carProjectionRepository) {
        Flow flow = ((CarProjectionRepositoryImpl) carProjectionRepository).projectionActive;
    }
}
