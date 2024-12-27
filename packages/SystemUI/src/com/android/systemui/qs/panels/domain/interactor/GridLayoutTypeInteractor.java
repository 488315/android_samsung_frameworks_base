package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.qs.panels.data.repository.GridLayoutTypeRepository;
import com.android.systemui.qs.panels.data.repository.GridLayoutTypeRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class GridLayoutTypeInteractor {
    public final ReadonlyStateFlow layout;

    public GridLayoutTypeInteractor(GridLayoutTypeRepository gridLayoutTypeRepository) {
        this.layout = ((GridLayoutTypeRepositoryImpl) gridLayoutTypeRepository).layout;
    }
}
