package androidx.customview.widget;

import android.graphics.Rect;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import java.util.Comparator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class FocusStrategy {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface BoundsAdapter {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SequentialComparator implements Comparator {
        public final BoundsAdapter mAdapter;
        public final boolean mIsLayoutRtl;
        public final Rect mTemp1 = new Rect();
        public final Rect mTemp2 = new Rect();

        public SequentialComparator(boolean z, BoundsAdapter boundsAdapter) {
            this.mIsLayoutRtl = z;
            this.mAdapter = boundsAdapter;
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            Rect rect = this.mTemp1;
            Rect rect2 = this.mTemp2;
            ((ExploreByTouchHelper.AnonymousClass1) this.mAdapter).getClass();
            ((AccessibilityNodeInfoCompat) obj).getBoundsInParent(rect);
            ((ExploreByTouchHelper.AnonymousClass1) this.mAdapter).getClass();
            ((AccessibilityNodeInfoCompat) obj2).getBoundsInParent(rect2);
            int i = rect.top;
            int i2 = rect2.top;
            if (i < i2) {
                return -1;
            }
            if (i > i2) {
                return 1;
            }
            int i3 = rect.left;
            int i4 = rect2.left;
            if (i3 < i4) {
                return this.mIsLayoutRtl ? 1 : -1;
            }
            if (i3 > i4) {
                return this.mIsLayoutRtl ? -1 : 1;
            }
            int i5 = rect.bottom;
            int i6 = rect2.bottom;
            if (i5 < i6) {
                return -1;
            }
            if (i5 > i6) {
                return 1;
            }
            int i7 = rect.right;
            int i8 = rect2.right;
            if (i7 < i8) {
                return this.mIsLayoutRtl ? 1 : -1;
            }
            if (i7 > i8) {
                return this.mIsLayoutRtl ? -1 : 1;
            }
            return 0;
        }
    }

    private FocusStrategy() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0025, code lost:
    
        if (r8.bottom <= r10.top) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0042, code lost:
    
        if (r11 == 17) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0044, code lost:
    
        if (r11 != 66) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0047, code lost:
    
        r9 = majorAxisDistance(r11, r8, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x004b, code lost:
    
        if (r11 == 17) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004d, code lost:
    
        if (r11 == 33) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004f, code lost:
    
        if (r11 == 66) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0051, code lost:
    
        if (r11 != 130) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0053, code lost:
    
        r10 = r10.bottom;
        r8 = r8.bottom;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0057, code lost:
    
        r10 = r10 - r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0074, code lost:
    
        if (r9 >= java.lang.Math.max(1, r10)) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:?, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x005e, code lost:
    
        throw new java.lang.IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005f, code lost:
    
        r10 = r10.right;
        r8 = r8.right;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0064, code lost:
    
        r8 = r8.top;
        r10 = r10.top;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0068, code lost:
    
        r10 = r8 - r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x006b, code lost:
    
        r8 = r8.left;
        r10 = r10.left;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0032, code lost:
    
        if (r8.right <= r10.left) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0039, code lost:
    
        if (r8.top >= r10.bottom) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0040, code lost:
    
        if (r8.left >= r10.right) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean beamBeats(android.graphics.Rect r8, android.graphics.Rect r9, android.graphics.Rect r10, int r11) {
        /*
            boolean r0 = beamsOverlap(r11, r8, r9)
            boolean r1 = beamsOverlap(r11, r8, r10)
            if (r1 != 0) goto L77
            if (r0 != 0) goto Le
            goto L77
        Le:
            java.lang.String r0 = "direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}."
            r1 = 130(0x82, float:1.82E-43)
            r2 = 33
            r3 = 66
            r4 = 17
            r5 = 1
            if (r11 == r4) goto L3c
            if (r11 == r2) goto L35
            if (r11 == r3) goto L2e
            if (r11 != r1) goto L28
            int r6 = r8.bottom
            int r7 = r10.top
            if (r6 > r7) goto L76
            goto L42
        L28:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            r8.<init>(r0)
            throw r8
        L2e:
            int r6 = r8.right
            int r7 = r10.left
            if (r6 > r7) goto L76
            goto L42
        L35:
            int r6 = r8.top
            int r7 = r10.bottom
            if (r6 < r7) goto L76
            goto L42
        L3c:
            int r6 = r8.left
            int r7 = r10.right
            if (r6 < r7) goto L76
        L42:
            if (r11 == r4) goto L76
            if (r11 != r3) goto L47
            goto L76
        L47:
            int r9 = majorAxisDistance(r11, r8, r9)
            if (r11 == r4) goto L6b
            if (r11 == r2) goto L64
            if (r11 == r3) goto L5f
            if (r11 != r1) goto L59
            int r10 = r10.bottom
            int r8 = r8.bottom
        L57:
            int r10 = r10 - r8
            goto L70
        L59:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            r8.<init>(r0)
            throw r8
        L5f:
            int r10 = r10.right
            int r8 = r8.right
            goto L57
        L64:
            int r8 = r8.top
            int r10 = r10.top
        L68:
            int r10 = r8 - r10
            goto L70
        L6b:
            int r8 = r8.left
            int r10 = r10.left
            goto L68
        L70:
            int r8 = java.lang.Math.max(r5, r10)
            if (r9 >= r8) goto L77
        L76:
            return r5
        L77:
            r8 = 0
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.customview.widget.FocusStrategy.beamBeats(android.graphics.Rect, android.graphics.Rect, android.graphics.Rect, int):boolean");
    }

    public static boolean beamsOverlap(int i, Rect rect, Rect rect2) {
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                }
            }
            return rect2.right >= rect.left && rect2.left <= rect.right;
        }
        return rect2.bottom >= rect.top && rect2.top <= rect.bottom;
    }

    public static boolean isCandidate(int i, Rect rect, Rect rect2) {
        if (i == 17) {
            int i2 = rect.right;
            int i3 = rect2.right;
            return (i2 > i3 || rect.left >= i3) && rect.left > rect2.left;
        }
        if (i == 33) {
            int i4 = rect.bottom;
            int i5 = rect2.bottom;
            return (i4 > i5 || rect.top >= i5) && rect.top > rect2.top;
        }
        if (i == 66) {
            int i6 = rect.left;
            int i7 = rect2.left;
            return (i6 < i7 || rect.right <= i7) && rect.right < rect2.right;
        }
        if (i != 130) {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        int i8 = rect.top;
        int i9 = rect2.top;
        return (i8 < i9 || rect.bottom <= i9) && rect.bottom < rect2.bottom;
    }

    public static int majorAxisDistance(int i, Rect rect, Rect rect2) {
        int i2;
        int i3;
        if (i == 17) {
            i2 = rect.left;
            i3 = rect2.right;
        } else if (i == 33) {
            i2 = rect.top;
            i3 = rect2.bottom;
        } else if (i == 66) {
            i2 = rect2.left;
            i3 = rect.right;
        } else {
            if (i != 130) {
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
            i2 = rect2.top;
            i3 = rect.bottom;
        }
        return Math.max(0, i2 - i3);
    }

    public static int minorAxisDistance(int i, Rect rect, Rect rect2) {
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                }
            }
            return Math.abs(((rect.width() / 2) + rect.left) - ((rect2.width() / 2) + rect2.left));
        }
        return Math.abs(((rect.height() / 2) + rect.top) - ((rect2.height() / 2) + rect2.top));
    }
}
