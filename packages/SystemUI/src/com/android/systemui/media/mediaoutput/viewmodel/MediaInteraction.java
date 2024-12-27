package com.android.systemui.media.mediaoutput.viewmodel;

import com.android.systemui.media.mediaoutput.entity.MediaInfo;
import kotlinx.coroutines.flow.Flow;

public interface MediaInteraction {
    void execute(MediaInfo mediaInfo, long j, long j2);

    MediaInfo getEmpty();

    Flow getMediaInfo();
}
