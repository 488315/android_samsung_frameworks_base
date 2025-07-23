package com.android.systemui.media.audiovisseekbar.config;

import com.android.systemui.media.audiovisseekbar.utils.DimensionUtilsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RendererConfig {
    public static final RendererConfig INSTANCE = new RendererConfig();

    private RendererConfig() {
    }

    public static float getRemainTrackBorderBound() {
        return DimensionUtilsKt.dpToPx(8.0f) / 2;
    }
}
