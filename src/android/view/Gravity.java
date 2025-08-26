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

    /* JADX WARN: Removed duplicated region for block: B:8:0x0013 A[PHI: r3
      0x0013: PHI (r3v6 int) = (r3v1 int), (r3v8 int) binds: [B:13:0x0024, B:7:0x0011] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0016 A[PHI: r3
      0x0016: PHI (r3v2 int) = (r3v1 int), (r3v8 int) binds: [B:13:0x0024, B:7:0x0011] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getAbsoluteGravity(int i, int i2) {
        int i3;
        if ((8388608 & i) <= 0) {
            return i;
        }
        if ((i & START) == 8388611) {
            i3 = i & (-8388612);
            i = i2 == 1 ? i3 | 5 : i3 | 3;
        } else if ((i & END) == 8388613) {
            i3 = i & (-8388614);
            if (i2 == 1) {
            }
        }
        return i & (-8388609);
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void applyDisplay(int i, Rect rect, Rect rect2) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = 0;
        if ((268435456 & i) != 0) {
            if (rect2.top < rect.top) {
                rect2.top = rect.top;
            }
            if (rect2.bottom > rect.bottom) {
                rect2.bottom = rect.bottom;
            }
        } else {
            if (rect2.top < rect.top) {
                i3 = rect.top;
                i4 = rect2.top;
            } else if (rect2.bottom > rect.bottom) {
                i3 = rect.bottom;
                i4 = rect2.bottom;
            } else {
                i2 = 0;
                if (i2 != 0) {
                    if (rect2.height() > rect.bottom - rect.top) {
                        rect2.top = rect.top;
                        rect2.bottom = rect.bottom;
                    } else {
                        rect2.top += i2;
                        rect2.bottom += i2;
                    }
                }
            }
            i2 = i3 - i4;
            if (i2 != 0) {
            }
        }
        if ((i & 16777216) != 0) {
            if (rect2.left < rect.left) {
                rect2.left = rect.left;
            }
            if (rect2.right > rect.right) {
                rect2.right = rect.right;
                return;
            }
            return;
        }
        if (rect2.left >= rect.left) {
            if (rect2.right > rect.right) {
                i5 = rect.right;
                i6 = rect2.right;
            }
            if (i7 == 0) {
                if (rect2.width() > rect.right - rect.left) {
                    rect2.left = rect.left;
                    rect2.right = rect.right;
                    return;
                } else {
                    rect2.left += i7;
                    rect2.right += i7;
                    return;
                }
            }
            return;
        }
        i5 = rect.left;
        i6 = rect2.left;
        i7 = i5 - i6;
        if (i7 == 0) {
        }
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
