package com.android.systemui.volume.util;

import android.view.HapticFeedbackConstants;
import android.widget.SeekBar;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SeekBarUtil {
    public static final SeekBarUtil INSTANCE = new SeekBarUtil();

    private SeekBarUtil() {
    }

    public static final void vibrateIfNeeded(SeekBar seekBar, int i, int i2) {
        INSTANCE.getClass();
        boolean z = false;
        if (i != 2 ? i2 == seekBar.getMin() || i2 == seekBar.getMax() : i2 == seekBar.getMax()) {
            z = true;
        }
        if (z) {
            seekBar.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
        }
    }
}
