package com.android.systemui.bixby2.controller.mediacontrol;

import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;

public final class SeekToController extends MediaCommandType {
    public static final int $stable = 0;

    @Override // com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType
    public CommandActionResponse action() {
        return isPlayingOrFocused() ? seekTo(MediaCommandType.Companion.getMediaInfo().time) : new CommandActionResponse(2, ActionResults.RESULT_MEDIA_NOT_PLAYING);
    }
}
