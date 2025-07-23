package com.android.systemui.volume.haptics.ui;

import com.android.systemui.haptics.slider.SeekableSliderTrackerConfig;
import com.android.systemui.haptics.slider.SliderHapticFeedbackConfig;
import com.android.systemui.haptics.slider.SliderHapticFeedbackFilter;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.ClosedFloatingPointRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeHapticsConfigsProvider {
    public static final VolumeHapticsConfigsProvider INSTANCE = new VolumeHapticsConfigsProvider();
    public static final SeekableSliderTrackerConfig seekableSliderTrackerConfig = new SeekableSliderTrackerConfig(0, 0.0f, 0.0f, 1.0f, 3, null);

    private VolumeHapticsConfigsProvider() {
    }

    public static SliderHapticFeedbackConfig sliderHapticFeedbackConfig(ClosedFloatingPointRange closedFloatingPointRange, SliderHapticFeedbackFilter sliderHapticFeedbackFilter) {
        ClosedFloatRange closedFloatRange = (ClosedFloatRange) closedFloatingPointRange;
        return new SliderHapticFeedbackConfig(0.0f, 0.0f, 0.2f, 0.5f, 0.2f, 0.0f, 0.0f, 0, 0.1f, 0, 0.0f, 0.2f, 0.0f, 1.0f / (closedFloatRange._endInclusive - closedFloatRange._start), sliderHapticFeedbackFilter, 5795, null);
    }
}
