package com.android.systemui.bixby2.controller.mediacontrol;

import com.android.systemui.bixby2.CommandActionResponse;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class InvalidActionController extends MediaCommandType {
    @Override // com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType
    public CommandActionResponse action() {
        return new CommandActionResponse(2, "invalid_action");
    }
}
