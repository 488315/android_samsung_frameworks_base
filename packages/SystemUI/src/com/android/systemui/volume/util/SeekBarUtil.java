package com.android.systemui.volume.util;

import android.view.HapticFeedbackConstants;
import android.widget.SeekBar;

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
