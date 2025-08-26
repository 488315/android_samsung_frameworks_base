package com.android.systemui.plugins.clocks;

import java.util.List;

/* loaded from: classes2.dex */
public interface ClockProvider {
    ClockController createClock(ClockSettings clockSettings);

    ClockPickerConfig getClockPickerConfig(ClockSettings clockSettings);

    List<ClockMetadata> getClocks();

    void initialize(ClockMessageBuffers clockMessageBuffers);
}
