package com.android.systemui.media.mediaoutput.entity;

import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.util.Arrays;
import kotlin.jvm.internal.StringCompanionObject;

public final class MediaInfoExt {
    public static final MediaInfoExt INSTANCE = new MediaInfoExt();

    private MediaInfoExt() {
    }

    public static boolean isSet(long j, long j2) {
        return (j & j2) > 0;
    }

    public static String timeText(long j) {
        long j2 = j / 1000;
        long j3 = PluginLock.VERSION;
        long j4 = j2 / j3;
        long j5 = 60;
        long j6 = (j2 % j3) / j5;
        long j7 = j2 % j5;
        if (j4 > 0) {
            int i = StringCompanionObject.$r8$clinit;
            return String.format("%02d:%02d:%02d", Arrays.copyOf(new Object[]{Long.valueOf(j4), Long.valueOf(j6), Long.valueOf(j7)}, 3));
        }
        int i2 = StringCompanionObject.$r8$clinit;
        return String.format("%02d:%02d", Arrays.copyOf(new Object[]{Long.valueOf(j6), Long.valueOf(j7)}, 2));
    }
}
