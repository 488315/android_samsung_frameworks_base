package com.android.systemui.media.controls.domain.pipeline;

import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.shared.model.MediaData;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class LegacyMediaDataManagerImplKt {
    public static final String[] ART_URIS = null;
    public static final MediaData LOADING;

    static {
        EmptyList emptyList = EmptyList.INSTANCE;
        LOADING = new MediaData(-1, false, null, null, null, null, null, emptyList, emptyList, null, "INVALID", null, null, null, true, null, 0, false, null, false, null, false, 0L, 0L, InstanceId.fakeInstanceId(-1), -1, false, null, 218038784, null);
    }
}
