package com.samsung.android.wallpaper.colortheme.monet;

import android.util.Pair;
import com.android.internal.graphics.cam.Cam;
import java.util.List;

/* compiled from: ColorScheme.java */
/* loaded from: classes6.dex */
interface Hue {
    double get(Cam cam);

    /* JADX WARN: Multi-variable type inference failed */
    default double getHueRotation(float f, List<Pair> list) {
        float f2 = 0.0f;
        if (f >= 0.0f && f < 360.0f) {
            f2 = f;
        }
        float floatValue = Float.valueOf(f2).floatValue();
        int size = list.size() - 2;
        if (size >= 0) {
            int i = 0;
            while (true) {
                float intValue = ((Number) list.get(i).first).intValue();
                int i2 = i + 1;
                float intValue2 = ((Number) list.get(i2).first).intValue();
                if (intValue <= floatValue && floatValue < intValue2) {
                    return ColorScheme.wrapDegreesDouble(floatValue + ((Number) list.get(i).second).doubleValue());
                }
                if (i == size) {
                    break;
                }
                i = i2;
            }
        }
        return f;
    }
}
