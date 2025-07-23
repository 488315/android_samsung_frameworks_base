package com.android.internal.protolog.common;

import android.media.MediaMetrics;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class LogDataType {
    public static final int BOOLEAN = 3;
    public static final int DOUBLE = 2;
    public static final int LONG = 1;
    public static final int STRING = 0;
    private static final int TYPE_MASK = 3;
    private static final int TYPE_WIDTH = 2;

    public static int logDataTypesToBitMask(List<Integer> list) {
        if (list.size() > 16) {
            throw new BitmaskConversionException("Too many log call parameters - max 16 parameters supported");
        }
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            i |= list.get(i2).intValue() << (i2 * 2);
        }
        return i;
    }

    public static int bitmaskToLogDataType(int i, int i2) {
        if (i2 <= 16) {
            return (i >> (i2 * 2)) & 3;
        }
        throw new BitmaskConversionException("Max 16 parameters allowed");
    }

    public static List<Integer> parseFormatString(String str) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < str.length()) {
            if (str.charAt(i) == '%') {
                int i2 = i + 1;
                if (i2 >= str.length()) {
                    throw new InvalidFormatStringException("Invalid format string in config");
                }
                char charAt = str.charAt(i2);
                if (charAt != '%') {
                    if (charAt == 'b') {
                        arrayList.add(3);
                    } else {
                        if (charAt != 'd') {
                            if (charAt == 'f') {
                                arrayList.add(2);
                            } else if (charAt == 's') {
                                arrayList.add(0);
                            } else if (charAt != 'x') {
                                throw new InvalidFormatStringException("Invalid Protolog message format in \"" + str + "\" at index " + i + MediaMetrics.SEPARATOR);
                            }
                        }
                        arrayList.add(1);
                    }
                }
                i += 2;
            } else {
                i++;
            }
        }
        return arrayList;
    }
}
