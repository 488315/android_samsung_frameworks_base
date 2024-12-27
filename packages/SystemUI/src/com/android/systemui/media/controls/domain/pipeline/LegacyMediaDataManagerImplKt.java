package com.android.systemui.media.controls.domain.pipeline;

import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import kotlin.collections.EmptyList;

public abstract class LegacyMediaDataManagerImplKt {
    public static final String[] ART_URIS = {"android.media.metadata.ALBUM_ART_URI", "android.media.metadata.ART_URI", "android.media.metadata.DISPLAY_ICON_URI"};
    public static final SmartspaceMediaData EMPTY_SMARTSPACE_MEDIA_DATA;
    public static final MediaData LOADING;

    static {
        EmptyList emptyList = EmptyList.INSTANCE;
        LOADING = new MediaData(-1, false, null, null, null, null, null, emptyList, emptyList, null, "INVALID", null, null, null, true, null, 0, false, null, false, null, false, 0L, 0L, InstanceId.fakeInstanceId(-1), -1, false, null, 218038784, null);
        EMPTY_SMARTSPACE_MEDIA_DATA = new SmartspaceMediaData("INVALID", false, "INVALID", null, emptyList, null, 0L, InstanceId.fakeInstanceId(-1), 0L);
    }
}
