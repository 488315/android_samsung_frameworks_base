package com.android.systemui.media.audiovisseekbar.renderer;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TrackRendererType {
    public static final /* synthetic */ TrackRendererType[] $VALUES;
    public static final TrackRendererType WAVE_MULTI_AREA_AUTO;

    static {
        TrackRendererType trackRendererType = new TrackRendererType("WAVE_MULTI_AREA_AUTO", 0);
        WAVE_MULTI_AREA_AUTO = trackRendererType;
        TrackRendererType[] trackRendererTypeArr = {trackRendererType};
        $VALUES = trackRendererTypeArr;
        EnumEntriesKt.enumEntries(trackRendererTypeArr);
    }

    private TrackRendererType(String str, int i) {
    }

    public static TrackRendererType valueOf(String str) {
        return (TrackRendererType) Enum.valueOf(TrackRendererType.class, str);
    }

    public static TrackRendererType[] values() {
        return (TrackRendererType[]) $VALUES.clone();
    }
}
