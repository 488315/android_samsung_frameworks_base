package com.android.systemui.volume.util;

import android.view.HapticFeedbackConstants;
import android.widget.SeekBar;

public final class SeekBarUtil {
    public static final SeekBarUtil INSTANCE = new SeekBarUtil();

    private SeekBarUtil() {
    }

    public static final void vibrateIfNeeded(SeekBar seekBar, int i, int i2) {
        INSTANCE.getClass();
        if (i == 2) {
            if (i2 != seekBar.getMax()) {
                return;
            }
        } else if (i2 != seekBar.getMin() && i2 != seekBar.getMax()) {
            return;
        }
        seekBar.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
    }
}
