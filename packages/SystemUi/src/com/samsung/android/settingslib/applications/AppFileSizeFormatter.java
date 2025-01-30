package com.samsung.android.settingslib.applications;

import android.icu.text.UnicodeSet;
import android.icu.text.UnicodeSetSpanner;
import android.icu.util.MeasureUnit;
import java.lang.reflect.Constructor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AppFileSizeFormatter {
    public static final MeasureUnit PETABYTE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RoundedBytesResult {
        public final int fractionDigits;
        public final MeasureUnit units;
        public final float value;

        private RoundedBytesResult(float f, MeasureUnit measureUnit, int i, long j) {
            this.value = f;
            this.units = measureUnit;
            this.fractionDigits = i;
        }

        public static RoundedBytesResult roundBytes(long j) {
            long j2;
            int i = 0;
            boolean z = j < 0;
            if (z) {
                j = -j;
            }
            float f = j;
            MeasureUnit measureUnit = MeasureUnit.BYTE;
            if (f > 900.0f) {
                measureUnit = MeasureUnit.KILOBYTE;
                f /= 1000.0f;
                j2 = 1000;
            } else {
                j2 = 1;
            }
            if (f > 900.0f) {
                measureUnit = MeasureUnit.MEGABYTE;
                j2 *= 1000;
                f /= 1000.0f;
            }
            if (f > 900.0f) {
                measureUnit = MeasureUnit.GIGABYTE;
                j2 *= 1000;
                f /= 1000.0f;
            }
            if (f > 900.0f) {
                measureUnit = MeasureUnit.TERABYTE;
                j2 *= 1000;
                f /= 1000.0f;
            }
            if (f > 900.0f) {
                measureUnit = AppFileSizeFormatter.PETABYTE;
                j2 *= 1000;
                f /= 1000.0f;
            }
            MeasureUnit measureUnit2 = measureUnit;
            if (j2 != 1 && f < 100.0f) {
                int i2 = (f > 1.0f ? 1 : (f == 1.0f ? 0 : -1));
                i = 2;
            }
            int i3 = i;
            if (z) {
                f = -f;
            }
            return new RoundedBytesResult(f, measureUnit2, i3, 0L);
        }
    }

    static {
        new UnicodeSetSpanner(new UnicodeSet("[[:Zs:][:Cf:]]").freeze());
        try {
            Constructor declaredConstructor = MeasureUnit.class.getDeclaredConstructor(String.class, String.class);
            declaredConstructor.setAccessible(true);
            PETABYTE = (MeasureUnit) declaredConstructor.newInstance("digital", "petabyte");
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to create petabyte MeasureUnit", e);
        }
    }
}
