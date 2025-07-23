package com.android.systemui.qs.tiles.base.domain.interactor;

import android.os.UserHandle;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface QSTileAvailabilityInteractor {
    Flow availability(UserHandle userHandle);
}
