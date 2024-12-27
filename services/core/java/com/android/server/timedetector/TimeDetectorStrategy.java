package com.android.server.timedetector;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import com.android.server.timezonedetector.Dumpable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
        throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "origin="));
    }
}
