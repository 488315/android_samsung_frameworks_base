package android.view;

import android.graphics.Rect;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class Gravity {
    public static final int AXIS_CLIP = 8;
    public static final int AXIS_PULL_AFTER = 4;
    public static final int AXIS_PULL_BEFORE = 2;
    public static final int AXIS_SPECIFIED = 1;
    public static final int AXIS_X_SHIFT = 0;
    public static final int AXIS_Y_SHIFT = 4;
    public static final int BOTTOM = 80;
    public static final int CENTER = 17;
    public static final int CENTER_HORIZONTAL = 1;
    public static final int CENTER_VERTICAL = 16;
    public static final int CLIP_HORIZONTAL = 8;
    public static final int CLIP_VERTICAL = 128;
    public static final int DISPLAY_CLIP_HORIZONTAL = 16777216;
    public static final int DISPLAY_CLIP_VERTICAL = 268435456;
    public static final int END = 8388613;
    public static final int FILL = 119;
    public static final int FILL_HORIZONTAL = 7;
    public static final int FILL_VERTICAL = 112;
    public static final int HORIZONTAL_GRAVITY_MASK = 7;
    public static final int LEFT = 3;
    public static final int NO_GRAVITY = 0;
    public static final int RELATIVE_HORIZONTAL_GRAVITY_MASK = 8388615;
    public static final int RELATIVE_LAYOUT_DIRECTION = 8388608;
    public static final int RIGHT = 5;
    public static final int START = 8388611;
    public static final int TOP = 48;
    public static final int VERTICAL_GRAVITY_MASK = 112;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityFlags {
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0016, code lost:
    
        r3 = r3 | 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0024, code lost:
    
        if (r4 == 1) goto L9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0011, code lost:
    
        if (r4 == 1) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0013, code lost:
    
        r3 = r3 | 5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getAbsoluteGravity(int r3, int r4) {
        /*
            r0 = 8388608(0x800000, float:1.1754944E-38)
            r0 = r0 & r3
            if (r0 <= 0) goto L2b
            r0 = 8388611(0x800003, float:1.1754948E-38)
            r1 = r3 & r0
            r2 = 1
            if (r1 != r0) goto L19
            r0 = -8388612(0xffffffffff7ffffc, float:-3.4028229E38)
            r3 = r3 & r0
            if (r4 != r2) goto L16
        L13:
            r3 = r3 | 5
            goto L27
        L16:
            r3 = r3 | 3
            goto L27
        L19:
            r0 = 8388613(0x800005, float:1.175495E-38)
            r1 = r3 & r0
            if (r1 != r0) goto L27
            r0 = -8388614(0xffffffffff7ffffa, float:-3.4028225E38)
            r3 = r3 & r0
            if (r4 != r2) goto L13
            goto L16
        L27:
            r4 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            r3 = r3 & r4
        L2b:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.Gravity.getAbsoluteGravity(int, int):int");
    }

    public static boolean isHorizontal(int i) {
        return i > 0 && (i & RELATIVE_HORIZONTAL_GRAVITY_MASK) != 0;
    }

    public static boolean isVertical(int i) {
        return i > 0 && (i & 112) != 0;
    }

    public static void apply(int i, int i2, int i3, Rect rect, Rect rect2) {
        apply(i, i2, i3, rect, 0, 0, rect2);
    }

    public static void apply(int i, int i2, int i3, Rect rect, Rect rect2, int i4) {
        apply(getAbsoluteGravity(i, i4), i2, i3, rect, 0, 0, rect2);
    }

    public static void apply(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2) {
        int i6 = i & 6;
        if (i6 == 0) {
            rect2.left = rect.left + (((rect.right - rect.left) - i2) / 2) + i4;
            rect2.right = rect2.left + i2;
            if ((i & 8) == 8) {
                if (rect2.left < rect.left) {
                    rect2.left = rect.left;
                }
                if (rect2.right > rect.right) {
                    rect2.right = rect.right;
                }
            }
        } else if (i6 == 2) {
            rect2.left = rect.left + i4;
            rect2.right = rect2.left + i2;
            if ((i & 8) == 8 && rect2.right > rect.right) {
                rect2.right = rect.right;
            }
        } else if (i6 == 4) {
            rect2.right = rect.right - i4;
            rect2.left = rect2.right - i2;
            if ((i & 8) == 8 && rect2.left < rect.left) {
                rect2.left = rect.left;
            }
        } else {
            rect2.left = rect.left + i4;
            rect2.right = rect.right + i4;
        }
        int i7 = i & 96;
        if (i7 == 0) {
            rect2.top = rect.top + (((rect.bottom - rect.top) - i3) / 2) + i5;
            rect2.bottom = rect2.top + i3;
            if ((i & 128) == 128) {
                if (rect2.top < rect.top) {
                    rect2.top = rect.top;
                }
                if (rect2.bottom > rect.bottom) {
                    rect2.bottom = rect.bottom;
                    return;
                }
                return;
            }
            return;
        }
        if (i7 == 32) {
            rect2.top = rect.top + i5;
            rect2.bottom = rect2.top + i3;
            if ((i & 128) != 128 || rect2.bottom <= rect.bottom) {
                return;
            }
            rect2.bottom = rect.bottom;
            return;
        }
        if (i7 == 64) {
            rect2.bottom = rect.bottom - i5;
            rect2.top = rect2.bottom - i3;
            if ((i & 128) != 128 || rect2.top >= rect.top) {
                return;
            }
            rect2.top = rect.top;
            return;
        }
        rect2.top = rect.top + i5;
        rect2.bottom = rect.bottom + i5;
    }

    public static void apply(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2, int i6) {
        apply(getAbsoluteGravity(i, i6), i2, i3, rect, i4, i5, rect2);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void applyDisplay(int r5, android.graphics.Rect r6, android.graphics.Rect r7) {
        /*
            r0 = 268435456(0x10000000, float:2.524355E-29)
            r0 = r0 & r5
            r1 = 0
            if (r0 == 0) goto L1b
            int r0 = r7.top
            int r2 = r6.top
            if (r0 >= r2) goto L10
            int r0 = r6.top
            r7.top = r0
        L10:
            int r0 = r7.bottom
            int r2 = r6.bottom
            if (r0 <= r2) goto L53
            int r0 = r6.bottom
            r7.bottom = r0
            goto L53
        L1b:
            int r0 = r7.top
            int r2 = r6.top
            if (r0 >= r2) goto L27
            int r0 = r6.top
            int r2 = r7.top
        L25:
            int r0 = r0 - r2
            goto L33
        L27:
            int r0 = r7.bottom
            int r2 = r6.bottom
            if (r0 <= r2) goto L32
            int r0 = r6.bottom
            int r2 = r7.bottom
            goto L25
        L32:
            r0 = r1
        L33:
            if (r0 == 0) goto L53
            int r2 = r7.height()
            int r3 = r6.bottom
            int r4 = r6.top
            int r3 = r3 - r4
            if (r2 <= r3) goto L49
            int r0 = r6.top
            r7.top = r0
            int r0 = r6.bottom
            r7.bottom = r0
            goto L53
        L49:
            int r2 = r7.top
            int r2 = r2 + r0
            r7.top = r2
            int r2 = r7.bottom
            int r2 = r2 + r0
            r7.bottom = r2
        L53:
            r0 = 16777216(0x1000000, float:2.3509887E-38)
            r5 = r5 & r0
            if (r5 == 0) goto L6d
            int r5 = r7.left
            int r0 = r6.left
            if (r5 >= r0) goto L62
            int r5 = r6.left
            r7.left = r5
        L62:
            int r5 = r7.right
            int r0 = r6.right
            if (r5 <= r0) goto La5
            int r5 = r6.right
            r7.right = r5
            return
        L6d:
            int r5 = r7.left
            int r0 = r6.left
            if (r5 >= r0) goto L7a
            int r5 = r6.left
            int r0 = r7.left
        L77:
            int r1 = r5 - r0
            goto L85
        L7a:
            int r5 = r7.right
            int r0 = r6.right
            if (r5 <= r0) goto L85
            int r5 = r6.right
            int r0 = r7.right
            goto L77
        L85:
            if (r1 == 0) goto La5
            int r5 = r7.width()
            int r0 = r6.right
            int r2 = r6.left
            int r0 = r0 - r2
            if (r5 <= r0) goto L9b
            int r5 = r6.left
            r7.left = r5
            int r5 = r6.right
            r7.right = r5
            return
        L9b:
            int r5 = r7.left
            int r5 = r5 + r1
            r7.left = r5
            int r5 = r7.right
            int r5 = r5 + r1
            r7.right = r5
        La5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.Gravity.applyDisplay(int, android.graphics.Rect, android.graphics.Rect):void");
    }

    public static void applyDisplay(int i, Rect rect, Rect rect2, int i2) {
        applyDisplay(getAbsoluteGravity(i, i2), rect, rect2);
    }

    public static String toString(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 119) == 119) {
            sb.append("FILL ");
        } else {
            if ((i & 112) == 112) {
                sb.append("FILL_VERTICAL ");
            } else {
                if ((i & 48) == 48) {
                    sb.append("TOP ");
                }
                if ((i & 80) == 80) {
                    sb.append("BOTTOM ");
                }
            }
            if ((i & 7) == 7) {
                sb.append("FILL_HORIZONTAL ");
            } else {
                if ((i & START) == 8388611) {
                    sb.append("START ");
                } else if ((i & 3) == 3) {
                    sb.append("LEFT ");
                }
                if ((i & END) == 8388613) {
                    sb.append("END ");
                } else if ((i & 5) == 5) {
                    sb.append("RIGHT ");
                }
            }
        }
        if ((i & 17) == 17) {
            sb.append("CENTER ");
        } else {
            if ((i & 16) == 16) {
                sb.append("CENTER_VERTICAL ");
            }
            if ((i & 1) == 1) {
                sb.append("CENTER_HORIZONTAL ");
            }
        }
        if (sb.length() == 0) {
            sb.append("NO GRAVITY");
            sb.append(' ');
        }
        if ((i & 268435456) == 268435456) {
            sb.append("DISPLAY_CLIP_VERTICAL");
            sb.append(' ');
        }
        if ((i & 16777216) == 16777216) {
            sb.append("DISPLAY_CLIP_HORIZONTAL");
            sb.append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
