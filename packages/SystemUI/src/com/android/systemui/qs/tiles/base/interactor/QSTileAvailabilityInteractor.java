package com.android.systemui.qs.tiles.base.interactor;

import android.os.UserHandle;
import kotlinx.coroutines.flow.Flow;

public interface QSTileAvailabilityInteractor {
    Flow availability(UserHandle userHandle);
}
