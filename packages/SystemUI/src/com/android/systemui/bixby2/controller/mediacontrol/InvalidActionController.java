package com.android.systemui.bixby2.controller.mediacontrol;

import com.android.systemui.bixby2.CommandActionResponse;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class InvalidActionController extends MediaCommandType {
    public static final int $stable = 0;

    @Override // com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType
    public CommandActionResponse action() {
        return new CommandActionResponse(2, "invalid_action");
    }
}
