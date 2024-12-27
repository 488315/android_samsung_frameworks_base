package com.android.systemui.qs.tiles.base.interactor;

import android.os.UserHandle;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

public interface QSTileDataInteractor extends QSTileAvailabilityInteractor {
    Flow tileData(UserHandle userHandle, ReadonlySharedFlow readonlySharedFlow);
}
