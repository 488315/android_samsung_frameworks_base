package com.android.systemui.bixby2.controller.mediacontrol;

import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PlayController extends MediaCommandType {
    @Override // com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType
    public CommandActionResponse action() {
        MediaCommandType.Companion companion = MediaCommandType.Companion;
        if (companion.isMediaControlActive(companion.getMediaInfo().isMediaActive) || !isMusicAvailable()) {
            return companion.isMediaControlActive(companion.getMediaInfo().isMediaActive) ? new CommandActionResponse(2, ActionResults.RESULT_MEDIA_ALREADY_PLAY) : new CommandActionResponse(2, ActionResults.RESULT_NO_MEDIA_EXISTS);
        }
        sendMediaKeyEvent(126);
        return new CommandActionResponse(1, "success");
    }
}
