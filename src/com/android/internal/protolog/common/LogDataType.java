package com.android.internal.protolog.common;

import android.media.MediaMetrics;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
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
        int iIntValue = 0;
        for (int i = 0; i < list.size(); i++) {
            iIntValue |= list.get(i).intValue() << (i * 2);
        }
        return iIntValue;
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
                char cCharAt = str.charAt(i2);
                if (cCharAt != '%') {
                    if (cCharAt == 'b') {
                        arrayList.add(3);
                    } else if (cCharAt == 'd') {
                        arrayList.add(1);
                    } else if (cCharAt == 'f') {
                        arrayList.add(2);
                    } else if (cCharAt != 's') {
                        if (cCharAt != 'x') {
                            throw new InvalidFormatStringException("Invalid Protolog message format in \"" + str + "\" at index " + i + MediaMetrics.SEPARATOR);
                        }
                        arrayList.add(1);
                    } else {
                        arrayList.add(0);
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
