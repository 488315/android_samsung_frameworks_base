package android.text.style;

import android.text.Spannable;
import android.util.LongArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class SpanUtils {
    private static boolean hasIntersection(int i, int i2, int i3, int i4) {
        return i < i4 && i3 < i2;
    }

    private static int max(long j) {
        return (int) (j & 4294967295L);
    }

    private static int min(long j) {
        return (int) (j >> 32);
    }

    private static long pack(int i, int i2) {
        return i2 | (i << 32);
    }

    private SpanUtils() {
    }

    public static boolean toggleBold(Spannable spannable, int i, int i2) {
        if (i == i2) {
            return false;
        }
        StyleSpan[] styleSpanArr = (StyleSpan[]) spannable.getSpans(i, i2, StyleSpan.class);
        ArrayList arrayList = new ArrayList();
        for (StyleSpan styleSpan : styleSpanArr) {
            if ((styleSpan.getStyle() & 1) == 1) {
                arrayList.add(styleSpan);
            }
        }
        if (!isCovered(spannable, arrayList, i, i2)) {
            spannable.setSpan(new StyleSpan(1), i, i2, 17);
            return true;
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            StyleSpan styleSpan2 = (StyleSpan) arrayList.get(i3);
            int spanStart = spannable.getSpanStart(styleSpan2);
            int spanEnd = spannable.getSpanEnd(styleSpan2);
            int spanFlags = spannable.getSpanFlags(styleSpan2);
            boolean z = (styleSpan2.getStyle() & 2) == 2;
            if (spanStart < i) {
                if (spanEnd > i2) {
                    spannable.setSpan(styleSpan2, spanStart, i, spanFlags);
                    spannable.setSpan(new StyleSpan(styleSpan2.getStyle()), i2, spanEnd, spanFlags);
                    if (z) {
                        spannable.setSpan(new StyleSpan(2), i, i2, spanFlags);
                    }
                } else {
                    spannable.setSpan(styleSpan2, spanStart, i, spanFlags);
                    if (z) {
                        spannable.setSpan(new StyleSpan(2), i, spanEnd, spanFlags);
                    }
                }
            } else if (spanEnd > i2) {
                spannable.setSpan(styleSpan2, i2, spanEnd, spanFlags);
                if (z) {
                    spannable.setSpan(new StyleSpan(2), i2, spanEnd, spanFlags);
                }
            } else {
                spannable.removeSpan(styleSpan2);
                if (z) {
                    spannable.setSpan(new StyleSpan(2), spanStart, spanEnd, spanFlags);
                }
            }
        }
        return true;
    }

    public static boolean toggleItalic(Spannable spannable, int i, int i2) {
        if (i == i2) {
            return false;
        }
        StyleSpan[] styleSpanArr = (StyleSpan[]) spannable.getSpans(i, i2, StyleSpan.class);
        ArrayList arrayList = new ArrayList();
        for (StyleSpan styleSpan : styleSpanArr) {
            if ((styleSpan.getStyle() & 2) == 2) {
                arrayList.add(styleSpan);
            }
        }
        if (!isCovered(spannable, arrayList, i, i2)) {
            spannable.setSpan(new StyleSpan(2), i, i2, 17);
            return true;
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            StyleSpan styleSpan2 = (StyleSpan) arrayList.get(i3);
            int spanStart = spannable.getSpanStart(styleSpan2);
            int spanEnd = spannable.getSpanEnd(styleSpan2);
            int spanFlags = spannable.getSpanFlags(styleSpan2);
            boolean z = (styleSpan2.getStyle() & 1) == 1;
            if (spanStart < i) {
                if (spanEnd > i2) {
                    spannable.setSpan(styleSpan2, spanStart, i, spanFlags);
                    spannable.setSpan(new StyleSpan(styleSpan2.getStyle()), i2, spanEnd, spanFlags);
                    if (z) {
                        spannable.setSpan(new StyleSpan(1), i, i2, spanFlags);
                    }
                } else {
                    spannable.setSpan(styleSpan2, spanStart, i, spanFlags);
                    if (z) {
                        spannable.setSpan(new StyleSpan(1), i, spanEnd, spanFlags);
                    }
                }
            } else if (spanEnd > i2) {
                spannable.setSpan(styleSpan2, i2, spanEnd, spanFlags);
                if (z) {
                    spannable.setSpan(new StyleSpan(1), i2, spanEnd, spanFlags);
                }
            } else {
                spannable.removeSpan(styleSpan2);
                if (z) {
                    spannable.setSpan(new StyleSpan(1), spanStart, spanEnd, spanFlags);
                }
            }
        }
        return true;
    }

    public static boolean toggleUnderline(Spannable spannable, int i, int i2) {
        if (i == i2) {
            return false;
        }
        List asList = Arrays.asList((UnderlineSpan[]) spannable.getSpans(i, i2, UnderlineSpan.class));
        if (!isCovered(spannable, asList, i, i2)) {
            spannable.setSpan(new UnderlineSpan(), i, i2, 17);
            return true;
        }
        for (int i3 = 0; i3 < asList.size(); i3++) {
            UnderlineSpan underlineSpan = (UnderlineSpan) asList.get(i3);
            int spanStart = spannable.getSpanStart(underlineSpan);
            int spanEnd = spannable.getSpanEnd(underlineSpan);
            int spanFlags = spannable.getSpanFlags(underlineSpan);
            if (spanStart < i) {
                if (spanEnd > i2) {
                    spannable.setSpan(underlineSpan, spanStart, i, spanFlags);
                    spannable.setSpan(new UnderlineSpan(), i2, spanEnd, spanFlags);
                } else {
                    spannable.setSpan(underlineSpan, spanStart, i, spanFlags);
                }
            } else if (spanEnd > i2) {
                spannable.setSpan(underlineSpan, i2, spanEnd, spanFlags);
            } else {
                spannable.removeSpan(underlineSpan);
            }
        }
        return true;
    }

    private static long intersection(int i, int i2, int i3, int i4) {
        return pack(Math.max(i, i3), Math.min(i2, i4));
    }

    private static <T> boolean isCovered(Spannable spannable, List<T> list, int i, int i2) {
        if (i == i2) {
            return false;
        }
        LongArray longArray = new LongArray();
        LongArray longArray2 = new LongArray();
        longArray.add(pack(i, i2));
        int i3 = 0;
        while (i3 < list.size()) {
            T t = list.get(i3);
            int spanStart = spannable.getSpanStart(t);
            int spanEnd = spannable.getSpanEnd(t);
            for (int i4 = 0; i4 < longArray.size(); i4++) {
                long j = longArray.get(i4);
                int min = min(j);
                int max = max(j);
                if (!hasIntersection(spanStart, spanEnd, min, max)) {
                    longArray2.add(j);
                } else {
                    long intersection = intersection(spanStart, spanEnd, min, max);
                    int min2 = min(intersection);
                    int max2 = max(intersection);
                    if (min != min2) {
                        longArray2.add(pack(min, min2));
                    }
                    if (max2 != max) {
                        longArray2.add(pack(max2, max));
                    }
                }
            }
            if (longArray2.size() == 0) {
                return true;
            }
            longArray.clear();
            i3++;
            LongArray longArray3 = longArray2;
            longArray2 = longArray;
            longArray = longArray3;
        }
        return false;
    }
}
