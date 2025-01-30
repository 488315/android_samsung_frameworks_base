package com.android.systemui.monet;

import com.android.internal.graphics.cam.Cam;
import com.android.systemui.monet.ColorScheme;
import java.util.List;
import kotlin.Pair;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface Hue {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class DefaultImpls {
        public static double getHueRotation(float f, List list) {
            int i = 0;
            float floatValue = ((f < 0.0f || f >= 360.0f) ? 0 : Float.valueOf(f)).floatValue();
            int size = list.size() - 2;
            if (size >= 0) {
                while (true) {
                    float intValue = ((Number) ((Pair) list.get(i)).getFirst()).intValue();
                    int i2 = i + 1;
                    float intValue2 = ((Number) ((Pair) list.get(i2)).getFirst()).intValue();
                    if (intValue <= floatValue && floatValue < intValue2) {
                        ColorScheme.Companion companion = ColorScheme.Companion;
                        double doubleValue = ((Number) ((Pair) list.get(i)).getSecond()).doubleValue() + floatValue;
                        companion.getClass();
                        return ColorScheme.Companion.wrapDegreesDouble(doubleValue);
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

    double get(Cam cam);
}
