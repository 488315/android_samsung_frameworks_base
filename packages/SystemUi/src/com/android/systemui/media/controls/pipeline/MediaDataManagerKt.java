package com.android.systemui.media.controls.pipeline;

import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import kotlin.collections.EmptyList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class MediaDataManagerKt {
    public static final String[] ART_URIS = {"android.media.metadata.ALBUM_ART_URI", "android.media.metadata.ART_URI", "android.media.metadata.DISPLAY_ICON_URI"};
    public static final SmartspaceMediaData EMPTY_SMARTSPACE_MEDIA_DATA;
    public static final MediaData LOADING;

    static {
        EmptyList emptyList = EmptyList.INSTANCE;
        LOADING = new MediaData(-1, false, null, null, null, null, null, emptyList, emptyList, null, "INVALID", null, null, null, true, null, 0, false, null, false, null, false, 0L, InstanceId.fakeInstanceId(-1), -1, false, null, null, 243204608, null);
        EMPTY_SMARTSPACE_MEDIA_DATA = new SmartspaceMediaData("INVALID", false, "INVALID", null, emptyList, null, 0L, InstanceId.fakeInstanceId(-1), 0L);
    }
}
