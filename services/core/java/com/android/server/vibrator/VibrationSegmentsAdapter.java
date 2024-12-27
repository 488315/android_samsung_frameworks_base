package com.android.server.vibrator;

import android.os.VibratorInfo;

import java.util.List;

public interface VibrationSegmentsAdapter {
    int adaptToVibrator(VibratorInfo vibratorInfo, List list, int i);
}
