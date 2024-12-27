package android.view;

import android.app.WindowConfiguration;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.window.ClientWindowFrames;

import com.samsung.android.multiwindow.MultiWindowCoreState;

/* loaded from: classes4.dex */
public class WindowLayout {
    private static final boolean DEBUG = false;
    static final int MAX_X = 100000;
    static final int MAX_Y = 100000;
    static final int MIN_X = -100000;
    static final int MIN_Y = -100000;
    private static final String TAG = WindowLayout.class.getSimpleName();
    public static final int UNSPECIFIED_LENGTH = -1;
    private final Rect mTempDisplayCutoutSafeExceptMaybeBarsRect = new Rect();
    private final Rect mTempRect = new Rect();

    public void computeFrames(
            WindowManager.LayoutParams attrs,
            InsetsState state,
            Rect displayCutoutSafe,
            Rect windowBounds,
            int windowingMode,
            int requestedWidth,
            int requestedHeight,
            int requestedVisibleTypes,
            float compatScale,
            ClientWindowFrames frames) {
        computeFrames(
                attrs,
                state,
                displayCutoutSafe,
                windowBounds,
                windowingMode,
                requestedWidth,
                requestedHeight,
                requestedVisibleTypes,
                compatScale,
                frames,
                0);
    }

    public void computeFrames(
            WindowManager.LayoutParams attrs,
            InsetsState state,
            Rect displayCutoutSafe,
            Rect windowBounds,
            int windowingMode,
            int requestedWidth,
            int requestedHeight,
            int requestedVisibleTypes,
            float compatScale,
            ClientWindowFrames frames,
            int stageType) {
        Rect outFrame;
        int left;
        int type;
        int type2;
        int pfl;
        int rw;
        int w;
        int rw2;
        float x;
        float y;
        boolean inMultiWindowMode;
        Rect outFrame2;
        int w2;
        InsetsSource source;
        int type3 = attrs.type;
        int fl = attrs.flags;
        int pfl2 = attrs.privateFlags;
        boolean layoutInScreen = (fl & 256) == 256;
        Rect attachedWindowFrame = frames.attachedFrame;
        Rect outDisplayFrame = frames.displayFrame;
        Rect outParentFrame = frames.parentFrame;
        Rect outFrame3 = frames.frame;
        Insets insets =
                state.calculateInsets(
                        windowBounds,
                        attrs.getFitInsetsTypes(),
                        attrs.isFitInsetsIgnoringVisibility());
        int sides = attrs.getFitInsetsSides();
        if ((sides & 1) != 0) {
            outFrame = outFrame3;
            left = insets.left;
        } else {
            outFrame = outFrame3;
            left = 0;
        }
        if ((sides & 2) != 0) {
            type = type3;
            type2 = insets.top;
        } else {
            type = type3;
            type2 = 0;
        }
        int right = (sides & 4) != 0 ? insets.right : 0;
        int sides2 = (sides & 8) != 0 ? insets.bottom : 0;
        int i = windowBounds.left + left;
        int left2 = windowBounds.top;
        int i2 = left2 + type2;
        int top = windowBounds.right;
        int i3 = top - right;
        int right2 = windowBounds.bottom;
        outDisplayFrame.set(i, i2, i3, right2 - sides2);
        if (attachedWindowFrame == null) {
            outParentFrame.set(outDisplayFrame);
            if ((1073741824 & pfl2) != 0
                    && (source = state.peekSource(InsetsSource.ID_IME)) != null) {
                outParentFrame.inset(source.calculateInsets(outParentFrame, false));
            }
        } else {
            outParentFrame.set(!layoutInScreen ? attachedWindowFrame : outDisplayFrame);
        }
        int cutoutMode = attrs.layoutInDisplayCutoutMode;
        DisplayCutout cutout = state.getDisplayCutout();
        Rect displayCutoutSafeExceptMaybeBars = this.mTempDisplayCutoutSafeExceptMaybeBarsRect;
        displayCutoutSafeExceptMaybeBars.set(displayCutoutSafe);
        frames.isParentFrameClippedByDisplayCutout = false;
        if (ignoreCutoutMode(attrs, windowingMode, stageType)) {
            pfl = type;
        } else if (cutoutMode == 3 || cutout.isEmpty()) {
            pfl = type;
        } else {
            Rect displayFrame = state.getDisplayFrame();
            int pfl3 = WindowInsets.Type.statusBars();
            Insets statusBarsInsets = state.calculateInsets(displayFrame, pfl3, true);
            if (statusBarsInsets.top > 0 && displayCutoutSafeExceptMaybeBars.top > 0) {
                displayCutoutSafeExceptMaybeBars.top =
                        Math.max(statusBarsInsets.top, displayCutoutSafeExceptMaybeBars.top);
            }
            if (cutoutMode == 1) {
                if (displayFrame.width() < displayFrame.height()) {
                    displayCutoutSafeExceptMaybeBars.top = -100000;
                    displayCutoutSafeExceptMaybeBars.bottom = 100000;
                } else {
                    displayCutoutSafeExceptMaybeBars.left = -100000;
                    displayCutoutSafeExceptMaybeBars.right = 100000;
                }
            }
            boolean layoutInsetDecor = (attrs.flags & 65536) != 0;
            if (layoutInScreen && layoutInsetDecor) {
                if (cutoutMode == 0 || cutoutMode == 1) {
                    Insets systemBarsInsets =
                            state.calculateInsets(
                                    displayFrame,
                                    WindowInsets.Type.systemBars(),
                                    requestedVisibleTypes);
                    if (systemBarsInsets.left >= cutout.getSafeInsetLeft()) {
                        displayCutoutSafeExceptMaybeBars.left = -100000;
                    }
                    if (systemBarsInsets.top >= cutout.getSafeInsetTop()) {
                        displayCutoutSafeExceptMaybeBars.top = -100000;
                    }
                    if (systemBarsInsets.right >= cutout.getSafeInsetRight()) {
                        displayCutoutSafeExceptMaybeBars.right = 100000;
                    }
                    if (systemBarsInsets.bottom >= cutout.getSafeInsetBottom()) {
                        displayCutoutSafeExceptMaybeBars.bottom = 100000;
                    }
                }
            }
            pfl = type;
            if (pfl == 2011
                    && displayCutoutSafeExceptMaybeBars.bottom != 100000
                    && state.calculateInsets(displayFrame, WindowInsets.Type.navigationBars(), true)
                                    .bottom
                            > 0) {
                displayCutoutSafeExceptMaybeBars.bottom = 100000;
            }
            boolean attachedInParent =
                    (attachedWindowFrame == null || layoutInScreen) ? false : true;
            boolean floatingInScreenWindow =
                    (attrs.isFullscreen() || !layoutInScreen || pfl == 1) ? false : true;
            if (!attachedInParent && !floatingInScreenWindow) {
                this.mTempRect.set(outParentFrame);
                outParentFrame.intersectUnchecked(displayCutoutSafeExceptMaybeBars);
                frames.isParentFrameClippedByDisplayCutout = !this.mTempRect.equals(outParentFrame);
            }
            outDisplayFrame.intersectUnchecked(displayCutoutSafeExceptMaybeBars);
        }
        boolean noLimits = (attrs.flags & 512) != 0;
        boolean inMultiWindowMode2 = WindowConfiguration.inMultiWindowMode(windowingMode);
        if (noLimits && pfl != 2010 && !inMultiWindowMode2) {
            outDisplayFrame.left = -100000;
            outDisplayFrame.top = -100000;
            outDisplayFrame.right = 100000;
            outDisplayFrame.bottom = 100000;
        }
        boolean hasCompatScale = compatScale != 1.0f;
        int pw = outParentFrame.width();
        int ph = outParentFrame.height();
        boolean extendedByCutout = (attrs.privateFlags & 4096) != 0;
        if (requestedWidth != -1 && !extendedByCutout) {
            rw = requestedWidth;
        } else {
            rw = attrs.width >= 0 ? attrs.width : pw;
        }
        int rh = requestedHeight;
        if (rh == -1 || extendedByCutout) {
            rh = attrs.height >= 0 ? attrs.height : ph;
        }
        if ((attrs.flags & 16384) != 0) {
            if (attrs.width < 0) {
                w2 = pw;
            } else if (hasCompatScale) {
                w2 = (int) ((attrs.width * compatScale) + 0.5f);
            } else {
                w2 = attrs.width;
            }
            int w3 = w2;
            int w4 = attrs.height;
            if (w4 < 0) {
                rw2 = ph;
                w = w3;
            } else if (hasCompatScale) {
                rw2 = (int) ((attrs.height * compatScale) + 0.5f);
                w = w3;
            } else {
                int h = attrs.height;
                rw2 = h;
                w = w3;
            }
        } else {
            int h2 = attrs.width;
            if (h2 == -1) {
                w = pw;
            } else if (hasCompatScale) {
                w = (int) ((rw * compatScale) + 0.5f);
            } else {
                w = rw;
            }
            if (attrs.height == -1) {
                rw2 = ph;
            } else if (hasCompatScale) {
                rw2 = (int) ((rh * compatScale) + 0.5f);
            } else {
                rw2 = rh;
            }
        }
        if (hasCompatScale) {
            x = attrs.x * compatScale;
            y = attrs.y * compatScale;
        } else {
            x = attrs.x;
            y = attrs.y;
        }
        if (inMultiWindowMode2 && (attrs.privateFlags & 16384) == 0) {
            w = Math.min(w, pw);
            rw2 = Math.min(rw2, ph);
        }
        boolean isStartingWindowInFreeform = windowingMode == 5 && attrs.type == 3;
        if (inMultiWindowMode2) {
            inMultiWindowMode = true;
            if (attrs.type == 1 || isStartingWindowInFreeform || noLimits) {
                inMultiWindowMode = false;
            }
        } else {
            inMultiWindowMode = true;
        }
        Gravity.apply(
                attrs.gravity,
                w,
                rw2,
                outParentFrame,
                (int) ((attrs.horizontalMargin * pw) + x),
                (int) ((attrs.verticalMargin * ph) + y),
                outFrame);
        if (!inMultiWindowMode) {
            outFrame2 = outFrame;
        } else {
            outFrame2 = outFrame;
            Gravity.applyDisplay(attrs.gravity, outDisplayFrame, outFrame2);
        }
        if (extendedByCutout) {
            extendFrameByCutout(displayCutoutSafe, outDisplayFrame, outFrame2, this.mTempRect);
        }
    }

    public static void extendFrameByCutout(
            Rect displayCutoutSafe, Rect displayFrame, Rect inOutFrame, Rect tempRect) {
        if (displayCutoutSafe.contains(inOutFrame)) {
            return;
        }
        tempRect.set(inOutFrame);
        Gravity.applyDisplay(0, displayCutoutSafe, tempRect);
        if (tempRect.intersect(displayFrame)) {
            inOutFrame.union(tempRect);
        }
    }

    public static void computeSurfaceSize(
            WindowManager.LayoutParams attrs,
            Rect maxBounds,
            int requestedWidth,
            int requestedHeight,
            Rect winFrame,
            boolean dragResizing,
            Point outSurfaceSize) {
        int width;
        int height;
        if ((attrs.flags & 16384) != 0) {
            width = requestedWidth;
            height = requestedHeight;
        } else if (dragResizing) {
            width = maxBounds.width();
            height = maxBounds.height();
        } else {
            width = winFrame.width();
            height = winFrame.height();
        }
        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }
        Rect surfaceInsets = attrs.surfaceInsets;
        outSurfaceSize.set(
                width + surfaceInsets.left + surfaceInsets.right,
                height + surfaceInsets.top + surfaceInsets.bottom);
    }

    private static boolean ignoreCutoutMode(
            WindowManager.LayoutParams attrs, int windowingMode, int stageType) {
        if (windowingMode == 5 || windowingMode == 2) {
            return true;
        }
        return MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED
                && WindowConfiguration.isSplitScreenWindowingMode(stageType);
    }
}
