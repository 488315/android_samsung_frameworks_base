package com.android.systemui.media.audiovisseekbar.config;

import com.android.systemui.media.audiovisseekbar.utils.DimensionUtilsKt;

public final class RendererConfig {
    public static final RendererConfig INSTANCE = new RendererConfig();

    private RendererConfig() {
    }

    public static float getRemainTrackBorderBound() {
        return DimensionUtilsKt.dpToPx(8.0f) / 2;
    }
}
