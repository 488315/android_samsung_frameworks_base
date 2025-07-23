package android.view;

import android.app.WindowConfiguration;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.WindowManager;
import android.window.ClientWindowFrames;
import com.samsung.android.multiwindow.MultiWindowCoreState;

/* loaded from: classes4.dex */
public class WindowLayout {
    private static final boolean DEBUG = false;
    static final int MAX_X = 100000;
    static final int MAX_Y = 100000;
    static final int MIN_X = -100000;
    static final int MIN_Y = -100000;
    private static final String TAG = "WindowLayout";
    public static final int UNSPECIFIED_LENGTH = -1;
    private final Rect mTempDisplayCutoutSafeExceptMaybeBarsRect = new Rect();
    private final Rect mTempRect = new Rect();

    public void computeFrames(WindowManager.LayoutParams layoutParams, InsetsState insetsState, Rect rect, Rect rect2, int i, int i2, int i3, int i4, float f, ClientWindowFrames clientWindowFrames) {
        computeFrames(layoutParams, insetsState, rect, rect2, i, i2, i3, i4, f, clientWindowFrames, 0, null, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void computeFrames(android.view.WindowManager.LayoutParams r23, android.view.InsetsState r24, android.graphics.Rect r25, android.graphics.Rect r26, int r27, int r28, int r29, int r30, float r31, android.window.ClientWindowFrames r32, int r33, android.graphics.Rect r34, boolean r35) {
        /*
            Method dump skipped, instructions count: 761
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.WindowLayout.computeFrames(android.view.WindowManager$LayoutParams, android.view.InsetsState, android.graphics.Rect, android.graphics.Rect, int, int, int, int, float, android.window.ClientWindowFrames, int, android.graphics.Rect, boolean):void");
    }

    private static void intersectOrClamp(Rect rect, Rect rect2) {
        rect.left = Math.min(Math.max(rect.left, rect2.left), rect.right);
        rect.top = Math.min(Math.max(rect.top, rect2.top), rect.bottom);
        rect.right = Math.max(Math.min(rect.right, rect2.right), rect.left);
        rect.bottom = Math.max(Math.min(rect.bottom, rect2.bottom), rect.top);
    }

    public static void extendFrameByCutout(Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        if (rect.contains(rect3)) {
            return;
        }
        rect4.set(rect3);
        Gravity.applyDisplay(0, rect, rect4);
        if (rect4.intersect(rect2)) {
            rect3.union(rect4);
        }
    }

    public static void computeSurfaceSize(WindowManager.LayoutParams layoutParams, Rect rect, int i, int i2, Rect rect2, boolean z, Point point) {
        if ((layoutParams.flags & 16384) == 0) {
            if (z) {
                i = rect.width();
                i2 = rect.height();
            } else {
                i = rect2.width();
                i2 = rect2.height();
            }
        }
        if (i < 1) {
            i = 1;
        }
        if (i2 < 1) {
            i2 = 1;
        }
        Rect rect3 = layoutParams.surfaceInsets;
        point.set(i + rect3.left + rect3.right, i2 + rect3.top + rect3.bottom);
    }

    private static boolean ignoreCutoutMode(WindowManager.LayoutParams layoutParams, int i, int i2) {
        if (i == 5 || i == 2) {
            return true;
        }
        return MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED && WindowConfiguration.isSplitScreenWindowingMode(i2);
    }
}
