package com.android.server.timedetector;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;

import com.android.server.timezonedetector.Dumpable;

public interface TimeDetectorStrategy extends Dumpable {
    static String originToString(int i) {
        if (i == 1) {
            return "telephony";
        }
        if (i == 2) {
            return "manual";
        }
        if (i == 3) {
            return "network";
        }
        if (i == 4) {
            return "gnss";
        }
        if (i == 5) {
            return "external";
        }
        throw new IllegalArgumentException(
                VibrationParam$1$$ExternalSyntheticOutline0.m(i, "origin="));
    }
}
