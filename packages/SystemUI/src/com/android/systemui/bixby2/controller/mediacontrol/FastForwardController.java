package com.android.systemui.bixby2.controller.mediacontrol;

import com.android.systemui.bixby2.CommandActionResponse;
import com.android.systemui.bixby2.actionresult.ActionResults;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class FastForwardController extends MediaCommandType {
    public static final int $stable = 0;

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
