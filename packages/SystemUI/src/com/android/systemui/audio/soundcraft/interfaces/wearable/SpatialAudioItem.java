package com.android.systemui.audio.soundcraft.interfaces.wearable;

import com.android.systemui.R;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class SpatialAudioItem {
    public static final /* synthetic */ SpatialAudioItem[] $VALUES;
    public static final SpatialAudioItem OFF;
    public static final SpatialAudioItem SPATIAL_AND_HEAD_TRACKING;
    public static final SpatialAudioItem SPATIAL_ONLY;
    private final int position;
    private final int titleResId;

    static {
        SpatialAudioItem spatialAudioItem = new SpatialAudioItem("OFF", 0, 0, R.string.soundcraft_spatial_audio_option_off);
        OFF = spatialAudioItem;
        SpatialAudioItem spatialAudioItem2 = new SpatialAudioItem("SPATIAL_ONLY", 1, 1, R.string.soundcraft_spatial_audio_option_360_only);
        SPATIAL_ONLY = spatialAudioItem2;
        SpatialAudioItem spatialAudioItem3 = new SpatialAudioItem("SPATIAL_AND_HEAD_TRACKING", 2, 2, R.string.soundcraft_spatial_audio_option_360_headtracking);
        SPATIAL_AND_HEAD_TRACKING = spatialAudioItem3;
        SpatialAudioItem[] spatialAudioItemArr = {spatialAudioItem, spatialAudioItem2, spatialAudioItem3};
        $VALUES = spatialAudioItemArr;
        EnumEntriesKt.enumEntries(spatialAudioItemArr);
    }

    private SpatialAudioItem(String str, int i, int i2, int i3) {
        this.position = i2;
        this.titleResId = i3;
    }

    public static SpatialAudioItem valueOf(String str) {
        return (SpatialAudioItem) Enum.valueOf(SpatialAudioItem.class, str);
    }

    public static SpatialAudioItem[] values() {
        return (SpatialAudioItem[]) $VALUES.clone();
    }

    public final int getPosition() {
        return this.position;
    }
}
