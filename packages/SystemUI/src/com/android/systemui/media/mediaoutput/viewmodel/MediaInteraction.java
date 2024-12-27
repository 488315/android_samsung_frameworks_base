package com.android.systemui.media.mediaoutput.viewmodel;

import com.android.systemui.media.mediaoutput.entity.MediaInfo;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface MediaInteraction {
    void execute(MediaInfo mediaInfo, long j, long j2);

    MediaInfo getEmpty();

    Flow getMediaInfo();
}
