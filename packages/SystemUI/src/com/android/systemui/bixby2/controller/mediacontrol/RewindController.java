package com.android.systemui.bixby2.controller.mediacontrol;

import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;

public final class RewindController extends MediaCommandType {
    public static final int $stable = 0;

    @Override // com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType
    public CommandActionResponse action() {
        if (!isPlayingOrFocused()) {
            return new CommandActionResponse(2, ActionResults.RESULT_MEDIA_NOT_PLAYING);
        }
        if (!isValidAction(8L)) {
            return new CommandActionResponse(2, ActionResults.RESULT_NO_SUPPORT_FEATURE);
        }
        sendMediaKeyEvent(89);
        return new CommandActionResponse(1, "success");
    }
}
