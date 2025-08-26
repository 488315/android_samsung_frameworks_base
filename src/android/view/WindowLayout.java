package android.view;

import android.app.WindowConfiguration;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.window.ClientWindowFrames;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;

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

    /* JADX WARN: Removed duplicated region for block: B:187:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:191:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void computeFrames(WindowManager.LayoutParams layoutParams, InsetsState insetsState, Rect rect, Rect rect2, int i, int i2, int i3, int i4, float f, ClientWindowFrames clientWindowFrames, int i5, Rect rect3, boolean z) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        Rect rect4;
        int i12;
        int i13;
        boolean z2;
        int iMin;
        float f2;
        float f3;
        float f4;
        boolean z3;
        boolean z4;
        InsetsSource insetsSourcePeekSource;
        int i14 = layoutParams.type;
        int i15 = layoutParams.flags;
        int i16 = layoutParams.privateFlags;
        boolean z5 = (i15 & 256) == 256;
        Rect rect5 = clientWindowFrames.attachedFrame;
        Rect rect6 = clientWindowFrames.displayFrame;
        Rect rect7 = clientWindowFrames.parentFrame;
        Rect rect8 = clientWindowFrames.frame;
        boolean z6 = z5;
        Insets insetsCalculateInsets = insetsState.calculateInsets(rect2, layoutParams.getFitInsetsTypes(), layoutParams.isFitInsetsIgnoringVisibility());
        int fitInsetsSides = layoutParams.getFitInsetsSides();
        if ((fitInsetsSides & 1) != 0) {
            i6 = i16;
            i7 = insetsCalculateInsets.left;
        } else {
            i6 = i16;
            i7 = 0;
        }
        if ((fitInsetsSides & 2) != 0) {
            i8 = i7;
            i9 = insetsCalculateInsets.top;
        } else {
            i8 = i7;
            i9 = 0;
        }
        if ((fitInsetsSides & 4) != 0) {
            i10 = i9;
            i11 = insetsCalculateInsets.right;
        } else {
            i10 = i9;
            i11 = 0;
        }
        rect6.set(rect2.left + i8, rect2.top + i10, rect2.right - i11, rect2.bottom - ((fitInsetsSides & 8) != 0 ? insetsCalculateInsets.bottom : 0));
        if (rect5 == null) {
            rect7.set(rect6);
            if ((i6 & 1073741824) != 0 && (insetsSourcePeekSource = insetsState.peekSource(InsetsSource.ID_IME)) != null) {
                rect7.inset(insetsSourcePeekSource.calculateInsets(rect7, false));
            }
        } else {
            rect7.set(!z6 ? rect5 : rect6);
        }
        int i17 = layoutParams.layoutInDisplayCutoutMode;
        DisplayCutout displayCutout = insetsState.getDisplayCutout();
        Rect rect9 = this.mTempDisplayCutoutSafeExceptMaybeBarsRect;
        rect9.set(rect);
        clientWindowFrames.isParentFrameClippedByDisplayCutout = false;
        if (!ignoreCutoutMode(layoutParams, i, i5) && (!(i17 == 3 || displayCutout.isEmpty()) || (CoreRune.FW_USE_UDC_CUTOUT_TO_SHOW_LETTERBOX && z))) {
            Rect displayFrame = insetsState.getDisplayFrame();
            Insets insetsCalculateInsets2 = insetsState.calculateInsets(displayFrame, WindowInsets.Type.statusBars(), true);
            if (insetsCalculateInsets2.top > 0 && rect9.top > 0) {
                rect9.top = Math.max(insetsCalculateInsets2.top, rect9.top);
            }
            if (i17 == 1) {
                if (displayFrame.width() < displayFrame.height()) {
                    rect9.top = -100000;
                    rect9.bottom = 100000;
                } else {
                    rect9.left = -100000;
                    rect9.right = 100000;
                }
            }
            boolean z7 = (layoutParams.flags & 65536) != 0;
            if (z6 && z7 && (i17 == 0 || i17 == 1)) {
                Insets insetsCalculateInsets3 = insetsState.calculateInsets(displayFrame, WindowInsets.Type.systemBars(), i4);
                if (insetsCalculateInsets3.left >= displayCutout.getSafeInsetLeft()) {
                    i13 = -100000;
                    rect9.left = -100000;
                } else {
                    i13 = -100000;
                }
                rect4 = rect8;
                if (insetsCalculateInsets3.top >= displayCutout.getSafeInsetTop()) {
                    rect9.top = i13;
                }
                if (insetsCalculateInsets3.right >= displayCutout.getSafeInsetRight()) {
                    i12 = 100000;
                    rect9.right = 100000;
                } else {
                    i12 = 100000;
                }
                if (insetsCalculateInsets3.bottom >= displayCutout.getSafeInsetBottom()) {
                    rect9.bottom = i12;
                }
            } else {
                rect4 = rect8;
                i12 = 100000;
            }
            if (i14 == 2011 && rect9.bottom != i12 && insetsState.calculateInsets(displayFrame, WindowInsets.Type.navigationBars(), true).bottom > 0) {
                rect9.bottom = i12;
            }
            boolean z8 = (rect5 == null || z6) ? false : true;
            boolean z9 = (layoutParams.isFullscreen() || !z6 || i14 == 1) ? false : true;
            if (!z8 && !z9) {
                this.mTempRect.set(rect7);
                intersectOrClamp(rect7, rect9);
                clientWindowFrames.isParentFrameClippedByDisplayCutout = !this.mTempRect.equals(rect7);
            }
            intersectOrClamp(rect6, rect9);
        } else {
            rect4 = rect8;
        }
        boolean z10 = (layoutParams.flags & 512) != 0;
        boolean zInMultiWindowMode = WindowConfiguration.inMultiWindowMode(i);
        if (z10 && i14 != 2010 && !zInMultiWindowMode) {
            rect6.left = -100000;
            rect6.top = -100000;
            rect6.right = 100000;
            rect6.bottom = 100000;
        }
        boolean z11 = f != 1.0f;
        int iWidth = rect7.width();
        int iHeight = rect7.height();
        boolean z12 = (layoutParams.privateFlags & 4096) != 0;
        int i18 = i2;
        if (i18 == -1 || z12) {
            i18 = layoutParams.width >= 0 ? layoutParams.width : iWidth;
        }
        int iMin2 = i3;
        if (iMin2 == -1 || z12) {
            iMin2 = layoutParams.height >= 0 ? layoutParams.height : iHeight;
        }
        if ((layoutParams.flags & 16384) != 0) {
            if (layoutParams.width < 0) {
                iMin = iWidth;
            } else if (z11) {
                iMin = (int) ((layoutParams.width * f) + 0.5f);
            } else {
                iMin = layoutParams.width;
            }
            if (layoutParams.height < 0) {
                z2 = zInMultiWindowMode;
                iMin2 = iHeight;
            } else {
                if (z11) {
                    iMin2 = (int) ((layoutParams.height * f) + 0.5f);
                } else {
                    iMin2 = layoutParams.height;
                }
                z2 = zInMultiWindowMode;
            }
        } else {
            z2 = zInMultiWindowMode;
            if (layoutParams.width == -1) {
                i18 = iWidth;
            } else if (z11) {
                i18 = (int) ((i18 * f) + 0.5f);
            }
            if (layoutParams.height == -1) {
                iMin2 = iHeight;
            } else if (z11) {
                iMin2 = (int) ((iMin2 * f) + 0.5f);
            }
            iMin = i18;
        }
        if (z11) {
            f2 = layoutParams.x * f;
            f3 = layoutParams.y * f;
        } else {
            f2 = layoutParams.x;
            f3 = layoutParams.y;
        }
        if (z2 && (layoutParams.privateFlags & 16384) == 0) {
            iMin = Math.min(iMin, iWidth);
            iMin2 = Math.min(iMin2, iHeight);
        }
        boolean z13 = i == 5 && layoutParams.type == 3;
        if (z2) {
            f4 = f2;
            z3 = true;
            if (layoutParams.type == 1 || z13 || z10) {
                z4 = false;
            }
            if (rect3 != null && !rect3.isEmpty()) {
                rect7.bottom = rect3.top + ((int) ((rect3.height() * f) + 0.5f));
                rect7.right = rect3.left + ((int) ((rect3.width() * f) + 0.5f));
            }
            Rect rect10 = rect4;
            Gravity.apply(layoutParams.gravity, iMin, iMin2, rect7, (int) (f4 + (layoutParams.horizontalMargin * iWidth)), (int) (f3 + (layoutParams.verticalMargin * iHeight)), rect10);
            if (z4) {
                Gravity.applyDisplay(layoutParams.gravity, rect6, rect10);
            }
            if (z12) {
                return;
            }
            extendFrameByCutout(rect, rect6, rect10, this.mTempRect);
            return;
        }
        f4 = f2;
        z3 = true;
        z4 = z3;
        if (rect3 != null) {
            rect7.bottom = rect3.top + ((int) ((rect3.height() * f) + 0.5f));
            rect7.right = rect3.left + ((int) ((rect3.width() * f) + 0.5f));
        }
        Rect rect102 = rect4;
        Gravity.apply(layoutParams.gravity, iMin, iMin2, rect7, (int) (f4 + (layoutParams.horizontalMargin * iWidth)), (int) (f3 + (layoutParams.verticalMargin * iHeight)), rect102);
        if (z4) {
        }
        if (z12) {
        }
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
