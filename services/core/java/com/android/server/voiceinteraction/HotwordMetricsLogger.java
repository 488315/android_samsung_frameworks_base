package com.android.server.voiceinteraction;

import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class HotwordMetricsLogger {
    public static void writeAudioEgressEvent(int i, int i2, int i3, int i4, int i5, int i6) {
        int i7 = 1;
        if (i != 1) {
            i7 = 2;
            if (i != 2) {
                i7 = 0;
            }
        }
        FrameworkStatsLog.write(
                FrameworkStatsLog.HOTWORD_AUDIO_EGRESS_EVENT_REPORTED, i7, i2, i3, i4, i5, i6);
    }

    public static void writeDetectorEvent(int i, int i2, int i3) {
        int i4 = 1;
        if (i != 1) {
            i4 = 2;
            if (i != 2) {
                i4 = 0;
            }
        }
        FrameworkStatsLog.write(FrameworkStatsLog.HOTWORD_DETECTOR_EVENTS, i4, i2, i3);
    }

    public static void writeKeyphraseTriggerEvent(int i, int i2, int i3) {
        int i4 = 1;
        if (i != 1) {
            i4 = 2;
            if (i != 2) {
                i4 = 0;
            }
        }
        FrameworkStatsLog.write(FrameworkStatsLog.HOTWORD_DETECTOR_KEYPHRASE_TRIGGERED, i4, i2, i3);
    }
}
