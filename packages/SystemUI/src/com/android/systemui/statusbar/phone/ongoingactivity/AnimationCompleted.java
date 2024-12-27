package com.android.systemui.statusbar.phone.ongoingactivity;

import java.util.concurrent.CancellationException;

public final class AnimationCompleted extends CancellationException {
    private final String reason;

    public AnimationCompleted(String str) {
        super(str);
        this.reason = str;
    }
}
