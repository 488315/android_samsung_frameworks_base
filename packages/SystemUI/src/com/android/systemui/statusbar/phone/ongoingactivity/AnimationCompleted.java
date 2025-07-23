package com.android.systemui.statusbar.phone.ongoingactivity;

import java.util.concurrent.CancellationException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AnimationCompleted extends CancellationException {
    private final String reason;

    public AnimationCompleted(String str) {
        super(str);
        this.reason = str;
    }
}
