package com.android.systemui.communal.domain.interactor;

import com.android.systemui.communal.data.repository.CarProjectionRepository;
import com.android.systemui.communal.data.repository.CarProjectionRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class CarProjectionInteractor {
    public CarProjectionInteractor(CarProjectionRepository carProjectionRepository) {
        Flow flow = ((CarProjectionRepositoryImpl) carProjectionRepository).projectionActive;
    }
}
