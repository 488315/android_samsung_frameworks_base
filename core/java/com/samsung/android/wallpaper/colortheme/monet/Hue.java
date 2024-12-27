package com.samsung.android.wallpaper.colortheme.monet;

import android.util.Pair;

import com.android.internal.graphics.cam.Cam;

import java.util.List;

interface Hue {
    double get(Cam cam);

    default double getHueRotation(float sourceHue, List<Pair> hueAndRotations) {
        float f = 0.0f;
        if (sourceHue >= 0.0f && sourceHue < 360.0f) {
            f = sourceHue;
        }
        float sanitizedSourceHue = Float.valueOf(f).floatValue();
        int i = 0;
        int var5 = hueAndRotations.size() - 2;
        if (0 <= var5) {
            while (true) {
                float thisHue = ((Number) hueAndRotations.get(i).first).intValue();
                float nextHue = ((Number) hueAndRotations.get(i + 1).first).intValue();
                if (thisHue <= sanitizedSourceHue && sanitizedSourceHue < nextHue) {
                    return ColorScheme.wrapDegreesDouble(
                            sanitizedSourceHue
                                    + ((Number) hueAndRotations.get(i).second).doubleValue());
                }
                if (i == var5) {
                    break;
                }
                i++;
            }
        }
        return sourceHue;
    }
}
