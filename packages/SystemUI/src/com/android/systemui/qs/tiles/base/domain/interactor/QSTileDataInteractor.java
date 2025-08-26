package com.android.systemui.qs.tiles.base.domain.interactor;

import android.os.UserHandle;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public interface QSTileDataInteractor extends QSTileAvailabilityInteractor {
    Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow);
}
