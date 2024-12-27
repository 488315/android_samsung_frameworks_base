package com.android.systemui.qs.tiles.impl.saver.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

public final class DataSaverTileDataInteractor implements QSTileDataInteractor {
    public final DataSaverController dataSaverController;

    public DataSaverTileDataInteractor(DataSaverController dataSaverController) {
        this.dataSaverController = dataSaverController;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlySharedFlow readonlySharedFlow) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DataSaverTileDataInteractor$tileData$1 dataSaverTileDataInteractor$tileData$1 = new DataSaverTileDataInteractor$tileData$1(this, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(dataSaverTileDataInteractor$tileData$1);
    }
}
