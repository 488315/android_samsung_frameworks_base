package com.android.server.display;

import android.util.ArrayMap;
import android.util.Slog;

public final class HighBrightnessModeMetadataMapper {
    public final ArrayMap mHighBrightnessModeMetadataMap = new ArrayMap();

    public final HighBrightnessModeMetadata getHighBrightnessModeMetadataLocked(
            LogicalDisplay logicalDisplay) {
        DisplayDevice displayDevice = logicalDisplay.mPrimaryDisplayDevice;
        if (displayDevice == null) {
            Slog.wtf(
                    "HighBrightnessModeMetadataMapper",
                    "Display Device is null in DisplayPowerController for display: "
                            + logicalDisplay.mDisplayId);
            return null;
        }
        if (displayDevice.getDisplayDeviceConfig().getHighBrightnessModeData() == null) {
            return null;
        }
        ArrayMap arrayMap = this.mHighBrightnessModeMetadataMap;
        String str = displayDevice.mUniqueId;
        if (arrayMap.containsKey(str)) {
            return (HighBrightnessModeMetadata) this.mHighBrightnessModeMetadataMap.get(str);
        }
        HighBrightnessModeMetadata highBrightnessModeMetadata = new HighBrightnessModeMetadata();
        this.mHighBrightnessModeMetadataMap.put(str, highBrightnessModeMetadata);
        return highBrightnessModeMetadata;
    }
}
