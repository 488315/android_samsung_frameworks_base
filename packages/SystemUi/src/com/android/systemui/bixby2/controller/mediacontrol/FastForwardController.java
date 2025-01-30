package com.android.systemui.bixby2.controller.mediacontrol;

import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FastForwardController extends MediaCommandType {
    @Override // com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType
    public CommandActionResponse action() {
        if (!isPlayingOrFocused()) {
            return new CommandActionResponse(2, ActionResults.RESULT_MEDIA_NOT_PLAYING);
        }
        if (!isValidAction(64L)) {
            return new CommandActionResponse(2, ActionResults.RESULT_NO_SUPPORT_FEATURE);
        }
        sendMediaKeyEvent(90);
        return new CommandActionResponse(1, "success");
    }
}
