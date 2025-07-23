package com.android.systemui.media.mediaoutput.common;

import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaOutputConst {
    public static final MediaOutputConst INSTANCE = new MediaOutputConst();
    public static final long AUDIO_PATH_DEBOUNCE_TIMEOUT = DurationKt.toDuration(100, DurationUnit.MILLISECONDS);

    private MediaOutputConst() {
    }
}
