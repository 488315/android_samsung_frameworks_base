package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.qs.panels.shared.model.GridLayoutType;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GridConsistencyInteractor {
    public final Map consistencyInteractors;
    public final CurrentTilesInteractor currentTilesInteractor;
    public final GridTypeConsistencyInteractor defaultConsistencyInteractor;
    public final GridLayoutTypeInteractor gridLayoutTypeInteractor;
    public final LogBuffer logBuffer;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public GridConsistencyInteractor(GridLayoutTypeInteractor gridLayoutTypeInteractor, CurrentTilesInteractor currentTilesInteractor, Map<GridLayoutType, GridTypeConsistencyInteractor> map, GridTypeConsistencyInteractor gridTypeConsistencyInteractor, LogBuffer logBuffer, CoroutineScope coroutineScope) {
    }
}
