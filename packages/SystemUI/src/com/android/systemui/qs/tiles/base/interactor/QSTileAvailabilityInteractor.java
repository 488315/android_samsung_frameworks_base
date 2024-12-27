package com.android.systemui.qs.tiles.base.interactor;

import android.os.UserHandle;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface QSTileAvailabilityInteractor {
    Flow availability(UserHandle userHandle);
}
