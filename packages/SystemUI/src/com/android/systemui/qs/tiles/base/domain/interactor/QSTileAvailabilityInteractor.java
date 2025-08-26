package com.android.systemui.qs.tiles.base.domain.interactor;

import android.os.UserHandle;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public interface QSTileAvailabilityInteractor {
    Flow availability(UserHandle userHandle);
}
