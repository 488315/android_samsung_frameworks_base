package com.android.systemui.media.mediaoutput.common;

import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* loaded from: classes2.dex */
public final class MediaOutputConst {
    public static final MediaOutputConst INSTANCE = new MediaOutputConst();
    public static final long AUDIO_PATH_DEBOUNCE_TIMEOUT = DurationKt.toDuration(100, DurationUnit.MILLISECONDS);

    private MediaOutputConst() {
    }
}
