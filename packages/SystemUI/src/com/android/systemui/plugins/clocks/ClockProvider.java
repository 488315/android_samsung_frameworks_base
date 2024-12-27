package com.android.systemui.plugins.clocks;

import android.graphics.drawable.Drawable;
import java.util.List;

public interface ClockProvider {
    ClockController createClock(ClockSettings clockSettings);

    Drawable getClockThumbnail(String str);

    List<ClockMetadata> getClocks();

    void initialize(ClockMessageBuffers clockMessageBuffers);
}
