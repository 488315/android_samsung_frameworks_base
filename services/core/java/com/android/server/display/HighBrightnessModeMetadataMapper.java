package com.android.server.display;

import android.util.ArrayMap;
import android.util.Slog;

/* loaded from: classes2.dex */
public class HighBrightnessModeMetadataMapper {
  public final ArrayMap mHighBrightnessModeMetadataMap = new ArrayMap();

  public HighBrightnessModeMetadata getHighBrightnessModeMetadataLocked(
      LogicalDisplay logicalDisplay) {
    DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
    if (primaryDisplayDeviceLocked == null) {
      Slog.wtf(
          "HighBrightnessModeMetadataMapper",
          "Display Device is null in DisplayPowerController for display: "
              + logicalDisplay.getDisplayIdLocked());
      return null;
    }
    String uniqueId = primaryDisplayDeviceLocked.getUniqueId();
    if (this.mHighBrightnessModeMetadataMap.containsKey(uniqueId)) {
      return (HighBrightnessModeMetadata) this.mHighBrightnessModeMetadataMap.get(uniqueId);
    }
    HighBrightnessModeMetadata highBrightnessModeMetadata = new HighBrightnessModeMetadata();
    this.mHighBrightnessModeMetadataMap.put(uniqueId, highBrightnessModeMetadata);
    return highBrightnessModeMetadata;
  }
}
