package com.android.systemui.media.audiovisseekbar.config;

import com.android.systemui.media.audiovisseekbar.utils.DimensionUtilsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RendererConfig {
    public static final RendererConfig INSTANCE = new RendererConfig();

    private RendererConfig() {
    }

    public static float getRemainTrackBorderBound() {
        return DimensionUtilsKt.dpToPx(8.0f) / 2;
    }
}
