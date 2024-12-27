package com.android.systemui.plugins.clocks;

import android.graphics.drawable.Drawable;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ClockProvider {
    ClockController createClock(ClockSettings clockSettings);

    Drawable getClockThumbnail(String str);

    List<ClockMetadata> getClocks();

    void initialize(ClockMessageBuffers clockMessageBuffers);
}
