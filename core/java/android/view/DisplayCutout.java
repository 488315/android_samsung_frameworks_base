package android.view;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.DisplayUtils;
import android.util.Pair;
import android.util.RotationUtils;
import android.util.proto.ProtoOutputStream;

import com.android.internal.R;
import com.android.window.flags.Flags;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class DisplayCutout {
    public static final int BOUNDS_POSITION_BOTTOM = 3;
    public static final int BOUNDS_POSITION_LEFT = 0;
    public static final int BOUNDS_POSITION_LENGTH = 4;
    public static final int BOUNDS_POSITION_RIGHT = 2;
    public static final int BOUNDS_POSITION_TOP = 1;
    public static final String EMULATION_OVERLAY_CATEGORY =
            "com.android.internal.display_cutout_emulation";
    private static final int INVALID_SIDE_OVERRIDE = -1;
    public static final int LETTERBOX_BOTTOM = 8;
    public static final int LETTERBOX_LEFT = 1;
    public static final int LETTERBOX_NONE = 0;
    public static final int LETTERBOX_RIGHT = 2;
    public static final int LETTERBOX_TOP = 4;
    private static final String SIDE_STRING_BOTTOM = "bottom";
    private static final String SIDE_STRING_LEFT = "left";
    private static final String SIDE_STRING_RIGHT = "right";
    private static final String SIDE_STRING_TOP = "top";
    private static final String TAG = "DisplayCutout";
    private static Path sCachedCutoutPath;
    private static CutoutPathParserInfo sCachedCutoutPathParserInfo;
    private static float sCachedDensity;
    private static int sCachedDisplayHeight;
    private static int sCachedDisplayWidth;
    private static float sCachedPhysicalPixelDisplaySizeRatio;
    private static int[] sCachedSideOverrides;
    private static String sCachedSpec;
    private static Insets sCachedWaterfallInsets;
    private final Bounds mBounds;
    private final CutoutPathParserInfo mCutoutPathParserInfo;
    private final Rect mSafeInsets;
    private int[] mSideOverrides;
    private final Insets mWaterfallInsets;
    private static final Rect ZERO_RECT = new Rect();
    private static final CutoutPathParserInfo EMPTY_PARSER_INFO =
            new CutoutPathParserInfo(0, 0, 0, 0, 0.0f, "", 0, 0.0f, 0.0f);
    public static final DisplayCutout NO_CUTOUT =
            new DisplayCutout(
                    ZERO_RECT,
                    Insets.NONE,
                    ZERO_RECT,
                    ZERO_RECT,
                    ZERO_RECT,
                    ZERO_RECT,
                    EMPTY_PARSER_INFO,
                    false);
    private static final Pair<Path, DisplayCutout> NULL_PAIR = new Pair<>(null, null);
    private static final Object CACHE_LOCK = new Object();
    private static Pair<Path, DisplayCutout> sCachedCutout = NULL_PAIR;
    static final int[] INVALID_OVERRIDES = {-1, -1, -1, -1};

    @Retention(RetentionPolicy.SOURCE)
    public @interface BoundsPosition {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface CutoutPolicy {
        public static final int DEFAULT = 0;
        public static final int NOT_OVERLAP = 2;
        public static final int OVERLAP = 1;
    }

    private static class Bounds {
        private final Rect[] mRects;

        private Bounds(Rect left, Rect top, Rect right, Rect bottom, boolean copyArguments) {
            this.mRects = new Rect[4];
            this.mRects[0] = DisplayCutout.getCopyOrRef(left, copyArguments);
            this.mRects[1] = DisplayCutout.getCopyOrRef(top, copyArguments);
            this.mRects[2] = DisplayCutout.getCopyOrRef(right, copyArguments);
            this.mRects[3] = DisplayCutout.getCopyOrRef(bottom, copyArguments);
        }

        private Bounds(Rect[] rects, boolean copyArguments) {
            if (rects.length != 4) {
                throw new IllegalArgumentException(
                        "rects must have exactly 4 elements: rects=" + Arrays.toString(rects));
            }
            if (copyArguments) {
                this.mRects = new Rect[4];
                for (int i = 0; i < 4; i++) {
                    this.mRects[i] = new Rect(rects[i]);
                }
                return;
            }
            for (Rect rect : rects) {
                if (rect == null) {
                    throw new IllegalArgumentException(
                            "rects must have non-null elements: rects=" + Arrays.toString(rects));
                }
            }
            this.mRects = rects;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmpty() {
            for (Rect rect : this.mRects) {
                if (!rect.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Rect getRect(int pos) {
            return new Rect(this.mRects[pos]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Rect[] getRects() {
            Rect[] rects = new Rect[4];
            for (int i = 0; i < 4; i++) {
                rects[i] = new Rect(this.mRects[i]);
            }
            return rects;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void scale(float scale) {
            for (int i = 0; i < 4; i++) {
                this.mRects[i].scale(scale);
            }
        }

        public int hashCode() {
            int result = 0;
            for (Rect rect : this.mRects) {
                result = (48271 * result) + rect.hashCode();
            }
            return result;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Bounds) {
                Bounds b = (Bounds) o;
                return Arrays.deepEquals(this.mRects, b.mRects);
            }
            return false;
        }

        public String toString() {
            return "Bounds=" + Arrays.toString(this.mRects);
        }
    }

    public static class CutoutPathParserInfo {
        private final String mCutoutSpec;
        private final float mDensity;
        private final int mDisplayHeight;
        private final int mDisplayWidth;
        private final int mPhysicalDisplayHeight;
        private final int mPhysicalDisplayWidth;
        private final float mPhysicalPixelDisplaySizeRatio;
        private final int mRotation;
        private final float mScale;

        public CutoutPathParserInfo(
                int displayWidth,
                int displayHeight,
                int physicalDisplayWidth,
                int physicalDisplayHeight,
                float density,
                String cutoutSpec,
                int rotation,
                float scale,
                float physicalPixelDisplaySizeRatio) {
            this.mDisplayWidth = displayWidth;
            this.mDisplayHeight = displayHeight;
            this.mPhysicalDisplayWidth = physicalDisplayWidth;
            this.mPhysicalDisplayHeight = physicalDisplayHeight;
            this.mDensity = density;
            this.mCutoutSpec = cutoutSpec == null ? "" : cutoutSpec;
            this.mRotation = rotation;
            this.mScale = scale;
            this.mPhysicalPixelDisplaySizeRatio = physicalPixelDisplaySizeRatio;
        }

        public CutoutPathParserInfo(CutoutPathParserInfo cutoutPathParserInfo) {
            this.mDisplayWidth = cutoutPathParserInfo.mDisplayWidth;
            this.mDisplayHeight = cutoutPathParserInfo.mDisplayHeight;
            this.mPhysicalDisplayWidth = cutoutPathParserInfo.mPhysicalDisplayWidth;
            this.mPhysicalDisplayHeight = cutoutPathParserInfo.mPhysicalDisplayHeight;
            this.mDensity = cutoutPathParserInfo.mDensity;
            this.mCutoutSpec = cutoutPathParserInfo.mCutoutSpec;
            this.mRotation = cutoutPathParserInfo.mRotation;
            this.mScale = cutoutPathParserInfo.mScale;
            this.mPhysicalPixelDisplaySizeRatio =
                    cutoutPathParserInfo.mPhysicalPixelDisplaySizeRatio;
        }

        public int getDisplayWidth() {
            return this.mDisplayWidth;
        }

        public int getDisplayHeight() {
            return this.mDisplayHeight;
        }

        public int getPhysicalDisplayWidth() {
            return this.mPhysicalDisplayWidth;
        }

        public int getPhysicalDisplayHeight() {
            return this.mPhysicalDisplayHeight;
        }

        public float getDensity() {
            return this.mDensity;
        }

        public String getCutoutSpec() {
            return this.mCutoutSpec;
        }

        public int getRotation() {
            return this.mRotation;
        }

        public float getScale() {
            return this.mScale;
        }

        public float getPhysicalPixelDisplaySizeRatio() {
            return this.mPhysicalPixelDisplaySizeRatio;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasCutout() {
            return !this.mCutoutSpec.isEmpty();
        }

        public int hashCode() {
            int result = (0 * 48271) + Integer.hashCode(this.mDisplayWidth);
            return (48271
                            * ((((((((((((((result * 48271) + Integer.hashCode(this.mDisplayHeight))
                                                                                                                            * 48271)
                                                                                                                    + Float
                                                                                                                            .hashCode(
                                                                                                                                    this
                                                                                                                                            .mDensity))
                                                                                                            * 48271)
                                                                                                    + this
                                                                                                            .mCutoutSpec
                                                                                                            .hashCode())
                                                                                            * 48271)
                                                                                    + Integer
                                                                                            .hashCode(
                                                                                                    this
                                                                                                            .mRotation))
                                                                            * 48271)
                                                                    + Float.hashCode(this.mScale))
                                                            * 48271)
                                                    + Float.hashCode(
                                                            this.mPhysicalPixelDisplaySizeRatio))
                                            * 48271)
                                    + Integer.hashCode(this.mPhysicalDisplayWidth)))
                    + Integer.hashCode(this.mPhysicalDisplayHeight);
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof CutoutPathParserInfo)) {
                return false;
            }
            CutoutPathParserInfo c = (CutoutPathParserInfo) o;
            return this.mDisplayWidth == c.mDisplayWidth
                    && this.mDisplayHeight == c.mDisplayHeight
                    && this.mPhysicalDisplayWidth == c.mPhysicalDisplayWidth
                    && this.mPhysicalDisplayHeight == c.mPhysicalDisplayHeight
                    && this.mDensity == c.mDensity
                    && this.mCutoutSpec.equals(c.mCutoutSpec)
                    && this.mRotation == c.mRotation
                    && this.mScale == c.mScale
                    && this.mPhysicalPixelDisplaySizeRatio == c.mPhysicalPixelDisplaySizeRatio;
        }

        public String toString() {
            return "CutoutPathParserInfo{displayWidth="
                    + this.mDisplayWidth
                    + " displayHeight="
                    + this.mDisplayHeight
                    + " physicalDisplayWidth="
                    + this.mPhysicalDisplayWidth
                    + " physicalDisplayHeight="
                    + this.mPhysicalDisplayHeight
                    + " density={"
                    + this.mDensity
                    + "} cutoutSpec={"
                    + this.mCutoutSpec
                    + "} rotation={"
                    + this.mRotation
                    + "} scale={"
                    + this.mScale
                    + "} physicalPixelDisplaySizeRatio={"
                    + this.mPhysicalPixelDisplaySizeRatio
                    + "}}";
        }
    }

    public DisplayCutout(
            Insets safeInsets, Rect boundLeft, Rect boundTop, Rect boundRight, Rect boundBottom) {
        this(
                getCopyOrRef(safeInsets.toRect(), true),
                Insets.NONE,
                new Bounds(boundLeft, boundTop, boundRight, boundBottom, true),
                (CutoutPathParserInfo) null,
                (int[]) null);
    }

    public DisplayCutout(
            Insets safeInsets,
            Rect boundLeft,
            Rect boundTop,
            Rect boundRight,
            Rect boundBottom,
            Insets waterfallInsets,
            CutoutPathParserInfo info) {
        this(
                getCopyOrRef(safeInsets.toRect(), true),
                waterfallInsets,
                new Bounds(boundLeft, boundTop, boundRight, boundBottom, true),
                info,
                (int[]) null);
    }

    public DisplayCutout(
            Insets safeInsets,
            Rect boundLeft,
            Rect boundTop,
            Rect boundRight,
            Rect boundBottom,
            Insets waterfallInsets,
            CutoutPathParserInfo info,
            int[] sideOverrides) {
        this(
                safeInsets.toRect(),
                waterfallInsets,
                new Bounds(boundLeft, boundTop, boundRight, boundBottom, true),
                info,
                sideOverrides);
    }

    public DisplayCutout(
            Insets safeInsets,
            Rect boundLeft,
            Rect boundTop,
            Rect boundRight,
            Rect boundBottom,
            Insets waterfallInsets) {
        this(
                getCopyOrRef(safeInsets.toRect(), true),
                waterfallInsets,
                new Bounds(boundLeft, boundTop, boundRight, boundBottom, true),
                (CutoutPathParserInfo) null,
                (int[]) null);
    }

    @Deprecated
    public DisplayCutout(Rect safeInsets, List<Rect> boundingRects) {
        this(
                getCopyOrRef(safeInsets, true),
                Insets.NONE,
                new Bounds(extractBoundsFromList(safeInsets, boundingRects), true),
                (CutoutPathParserInfo) null,
                (int[]) null);
    }

    private DisplayCutout(
            Rect safeInsets,
            Insets waterfallInsets,
            Rect boundLeft,
            Rect boundTop,
            Rect boundRight,
            Rect boundBottom,
            CutoutPathParserInfo info,
            boolean copyArguments) {
        this(
                getCopyOrRef(safeInsets, copyArguments),
                waterfallInsets,
                new Bounds(boundLeft, boundTop, boundRight, boundBottom, copyArguments),
                info,
                (int[]) null);
    }

    private DisplayCutout(
            Rect safeInsets,
            Insets waterfallInsets,
            Rect[] bounds,
            CutoutPathParserInfo info,
            boolean copyArguments) {
        this(
                getCopyOrRef(safeInsets, copyArguments),
                waterfallInsets,
                new Bounds(bounds, copyArguments),
                info,
                (int[]) null);
    }

    private DisplayCutout(
            Rect safeInsets, Insets waterfallInsets, Bounds bounds, CutoutPathParserInfo info) {
        this(safeInsets, waterfallInsets, bounds, info, (int[]) null);
    }

    private DisplayCutout(
            Rect safeInsets,
            Insets waterfallInsets,
            Bounds bounds,
            CutoutPathParserInfo info,
            int[] sideOverrides) {
        this.mSafeInsets = safeInsets;
        this.mWaterfallInsets = waterfallInsets == null ? Insets.NONE : waterfallInsets;
        this.mBounds = bounds;
        this.mCutoutPathParserInfo = info == null ? EMPTY_PARSER_INFO : info;
        this.mSideOverrides = sideOverrides;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Rect getCopyOrRef(Rect r, boolean copyArguments) {
        if (r == null) {
            return ZERO_RECT;
        }
        if (copyArguments) {
            return new Rect(r);
        }
        return r;
    }

    public Insets getWaterfallInsets() {
        return this.mWaterfallInsets;
    }

    public static Rect[] extractBoundsFromList(Rect safeInsets, List<Rect> boundingRects) {
        Rect[] sortedBounds = new Rect[4];
        for (int i = 0; i < sortedBounds.length; i++) {
            sortedBounds[i] = ZERO_RECT;
        }
        if (safeInsets != null && boundingRects != null) {
            boolean topBottomInset = safeInsets.top > 0 || safeInsets.bottom > 0;
            for (Rect bound : boundingRects) {
                if (topBottomInset) {
                    if (bound.top == 0) {
                        sortedBounds[1] = bound;
                    } else {
                        sortedBounds[3] = bound;
                    }
                } else if (bound.left == 0) {
                    sortedBounds[0] = bound;
                } else {
                    sortedBounds[2] = bound;
                }
            }
        }
        return sortedBounds;
    }

    public boolean isBoundsEmpty() {
        return this.mBounds.isEmpty();
    }

    public boolean isEmpty() {
        return this.mSafeInsets.equals(ZERO_RECT);
    }

    public int getSafeInsetTop() {
        return this.mSafeInsets.top;
    }

    public int getSafeInsetBottom() {
        return this.mSafeInsets.bottom;
    }

    public int getSafeInsetLeft() {
        return this.mSafeInsets.left;
    }

    public int getSafeInsetRight() {
        return this.mSafeInsets.right;
    }

    public Rect getSafeInsets() {
        return new Rect(this.mSafeInsets);
    }

    public List<Rect> getBoundingRects() {
        List<Rect> result = new ArrayList<>();
        for (Rect bound : getBoundingRectsAll()) {
            if (!bound.isEmpty()) {
                result.add(new Rect(bound));
            }
        }
        return result;
    }

    public Rect[] getBoundingRectsAll() {
        return this.mBounds.getRects();
    }

    public Rect getBoundingRectLeft() {
        return this.mBounds.getRect(0);
    }

    public Rect getBoundingRectTop() {
        return this.mBounds.getRect(1);
    }

    public Rect getBoundingRectRight() {
        return this.mBounds.getRect(2);
    }

    public Rect getBoundingRectBottom() {
        return this.mBounds.getRect(3);
    }

    public Path getCutoutPath() {
        if (!this.mCutoutPathParserInfo.hasCutout()) {
            return null;
        }
        synchronized (CACHE_LOCK) {
            if (this.mCutoutPathParserInfo.equals(sCachedCutoutPathParserInfo)) {
                return sCachedCutoutPath;
            }
            CutoutSpecification cutoutSpec =
                    new CutoutSpecification.Parser(
                                    this.mCutoutPathParserInfo.getDensity(),
                                    this.mCutoutPathParserInfo.getPhysicalDisplayWidth(),
                                    this.mCutoutPathParserInfo.getPhysicalDisplayHeight(),
                                    this.mCutoutPathParserInfo.getPhysicalPixelDisplaySizeRatio())
                            .parse(this.mCutoutPathParserInfo.getCutoutSpec());
            Path cutoutPath = cutoutSpec.getPath();
            if (cutoutPath == null || cutoutPath.isEmpty()) {
                return null;
            }
            Matrix matrix = new Matrix();
            if (this.mCutoutPathParserInfo.getRotation() != 0) {
                RotationUtils.transformPhysicalToLogicalCoordinates(
                        this.mCutoutPathParserInfo.getRotation(),
                        this.mCutoutPathParserInfo.getDisplayWidth(),
                        this.mCutoutPathParserInfo.getDisplayHeight(),
                        matrix);
            }
            matrix.postScale(
                    this.mCutoutPathParserInfo.getScale(), this.mCutoutPathParserInfo.getScale());
            cutoutPath.transform(matrix);
            synchronized (CACHE_LOCK) {
                sCachedCutoutPathParserInfo = new CutoutPathParserInfo(this.mCutoutPathParserInfo);
                sCachedCutoutPath = cutoutPath;
            }
            return cutoutPath;
        }
    }

    public CutoutPathParserInfo getCutoutPathParserInfo() {
        return this.mCutoutPathParserInfo;
    }

    public int hashCode() {
        int result = (0 * 48271) + this.mSafeInsets.hashCode();
        return (48271
                        * ((((((result * 48271) + this.mBounds.hashCode()) * 48271)
                                                + this.mWaterfallInsets.hashCode())
                                        * 48271)
                                + this.mCutoutPathParserInfo.hashCode()))
                + Arrays.hashCode(this.mSideOverrides);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DisplayCutout)) {
            return false;
        }
        DisplayCutout c = (DisplayCutout) o;
        return this.mSafeInsets.equals(c.mSafeInsets)
                && this.mBounds.equals(c.mBounds)
                && this.mWaterfallInsets.equals(c.mWaterfallInsets)
                && this.mCutoutPathParserInfo.equals(c.mCutoutPathParserInfo)
                && Arrays.equals(this.mSideOverrides, c.mSideOverrides);
    }

    public String toString() {
        return "DisplayCutout{insets="
                + this.mSafeInsets
                + " waterfall="
                + this.mWaterfallInsets
                + " boundingRect={"
                + this.mBounds
                + "} cutoutPathParserInfo={"
                + this.mCutoutPathParserInfo
                + "} sideOverrides="
                + sideOverridesToString(this.mSideOverrides)
                + "}";
    }

    private static String sideOverridesToString(int[] sideOverrides) {
        if (sideOverrides == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int length = sideOverrides.length;
        if (length != 4) {
            sb.append("length=").append(sideOverrides.length).append(". ");
        }
        boolean hasContent = false;
        for (int i = 0; i < length; i++) {
            int override = sideOverrides[i];
            if (override != -1) {
                if (hasContent) {
                    sb.append(", ");
                }
                sb.append(Surface.rotationToString(i)).append(": ");
                switch (override) {
                    case 0:
                        sb.append("left");
                        break;
                    case 1:
                        sb.append("top");
                        break;
                    case 2:
                        sb.append("right");
                        break;
                    case 3:
                        sb.append("bottom");
                        break;
                }
                hasContent = true;
            }
        }
        return sb.append("}").toString();
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        this.mSafeInsets.dumpDebug(proto, 1146756268033L);
        this.mBounds.getRect(0).dumpDebug(proto, 1146756268035L);
        this.mBounds.getRect(1).dumpDebug(proto, 1146756268036L);
        this.mBounds.getRect(2).dumpDebug(proto, 1146756268037L);
        this.mBounds.getRect(3).dumpDebug(proto, 1146756268038L);
        this.mWaterfallInsets.toRect().dumpDebug(proto, 1146756268039L);
        if (this.mSideOverrides != null) {
            for (int sideOverride : this.mSideOverrides) {
                proto.write(DisplayCutoutProto.SIDE_OVERRIDES, sideOverride);
            }
        }
        proto.end(token);
    }

    public DisplayCutout inset(int insetLeft, int insetTop, int insetRight, int insetBottom) {
        if ((insetLeft == 0 && insetTop == 0 && insetRight == 0 && insetBottom == 0)
                || (isBoundsEmpty() && this.mWaterfallInsets.equals(Insets.NONE))) {
            return this;
        }
        Rect safeInsets =
                insetInsets(
                        insetLeft, insetTop, insetRight, insetBottom, new Rect(this.mSafeInsets));
        if (insetLeft == 0 && insetTop == 0 && this.mSafeInsets.equals(safeInsets)) {
            return this;
        }
        Rect waterfallInsets =
                insetInsets(
                        insetLeft,
                        insetTop,
                        insetRight,
                        insetBottom,
                        this.mWaterfallInsets.toRect());
        Rect[] bounds = this.mBounds.getRects();
        for (int i = 0; i < bounds.length; i++) {
            if (!bounds[i].equals(ZERO_RECT)) {
                bounds[i].offset(-insetLeft, -insetTop);
            }
        }
        return new DisplayCutout(
                safeInsets, Insets.of(waterfallInsets), bounds, this.mCutoutPathParserInfo, false);
    }

    private Rect insetInsets(
            int insetLeft, int insetTop, int insetRight, int insetBottom, Rect insets) {
        if (insetTop > 0 || insets.top > 0) {
            insets.top = atLeastZero(insets.top - insetTop);
        }
        if (insetBottom > 0 || insets.bottom > 0) {
            insets.bottom = atLeastZero(insets.bottom - insetBottom);
        }
        if (insetLeft > 0 || insets.left > 0) {
            insets.left = atLeastZero(insets.left - insetLeft);
        }
        if (insetRight > 0 || insets.right > 0) {
            insets.right = atLeastZero(insets.right - insetRight);
        }
        return insets;
    }

    public DisplayCutout replaceSafeInsets(Rect safeInsets) {
        return new DisplayCutout(
                new Rect(safeInsets),
                this.mWaterfallInsets,
                this.mBounds,
                this.mCutoutPathParserInfo,
                this.mSideOverrides);
    }

    private static int atLeastZero(int value) {
        if (value < 0) {
            return 0;
        }
        return value;
    }

    public static DisplayCutout fromBoundingRect(
            int left, int top, int right, int bottom, int pos) {
        Rect[] bounds = new Rect[4];
        int i = 0;
        while (i < 4) {
            bounds[i] = pos == i ? new Rect(left, top, right, bottom) : new Rect();
            i++;
        }
        return new DisplayCutout(
                ZERO_RECT, Insets.NONE, bounds, (CutoutPathParserInfo) null, false);
    }

    public static DisplayCutout constructDisplayCutout(
            Rect[] bounds, Insets waterfallInsets, CutoutPathParserInfo info) {
        return new DisplayCutout(ZERO_RECT, waterfallInsets, bounds, info, false);
    }

    public static DisplayCutout fromBounds(Rect[] bounds) {
        return new DisplayCutout(
                ZERO_RECT, Insets.NONE, bounds, (CutoutPathParserInfo) null, false);
    }

    private static String getDisplayCutoutPath(Resources res, String displayUniqueId) {
        int index = DisplayUtils.getDisplayUniqueIdConfigIndex(res, displayUniqueId);
        String[] array = res.getStringArray(R.array.config_displayCutoutPathArray);
        if (index >= 0 && index < array.length) {
            return array[index];
        }
        return res.getString(R.string.config_mainBuiltInDisplayCutout);
    }

    private static String getDisplayCutoutApproximationRect(Resources res, String displayUniqueId) {
        int index = DisplayUtils.getDisplayUniqueIdConfigIndex(res, displayUniqueId);
        String[] array = res.getStringArray(R.array.config_displayCutoutApproximationRectArray);
        if (index >= 0 && index < array.length) {
            return array[index];
        }
        return res.getString(R.string.config_mainBuiltInDisplayCutoutRectApproximation);
    }

    public static boolean getMaskBuiltInDisplayCutout(Resources res, String displayUniqueId) {
        boolean maskCutout;
        int index = DisplayUtils.getDisplayUniqueIdConfigIndex(res, displayUniqueId);
        TypedArray array = res.obtainTypedArray(R.array.config_maskBuiltInDisplayCutoutArray);
        if (index >= 0 && index < array.length()) {
            maskCutout = array.getBoolean(index, false);
        } else {
            maskCutout = res.getBoolean(R.bool.config_maskMainBuiltInDisplayCutout);
        }
        array.recycle();
        return maskCutout;
    }

    public static boolean getFillBuiltInDisplayCutout(Resources res, String displayUniqueId) {
        boolean fillCutout;
        int index = DisplayUtils.getDisplayUniqueIdConfigIndex(res, displayUniqueId);
        TypedArray array = res.obtainTypedArray(R.array.config_fillBuiltInDisplayCutoutArray);
        if (index >= 0 && index < array.length()) {
            fillCutout = array.getBoolean(index, false);
        } else {
            fillCutout = res.getBoolean(R.bool.config_fillMainBuiltInDisplayCutout);
        }
        array.recycle();
        return fillCutout;
    }

    private static Insets getWaterfallInsets(Resources res, String displayUniqueId) {
        int resourceId;
        Insets insets;
        int index = DisplayUtils.getDisplayUniqueIdConfigIndex(res, displayUniqueId);
        TypedArray array = res.obtainTypedArray(R.array.config_waterfallCutoutArray);
        if (index >= 0 && index < array.length()) {
            resourceId = array.getResourceId(index, 0);
        } else {
            resourceId = 0;
        }
        if (resourceId != 0) {
            TypedArray waterfall = res.obtainTypedArray(resourceId);
            insets =
                    Insets.of(
                            waterfall.getDimensionPixelSize(0, 0),
                            waterfall.getDimensionPixelSize(1, 0),
                            waterfall.getDimensionPixelSize(2, 0),
                            waterfall.getDimensionPixelSize(3, 0));
            waterfall.recycle();
        } else {
            insets = loadWaterfallInset(res);
        }
        array.recycle();
        return insets;
    }

    private static int[] getDisplayCutoutSideOverrides(Resources res, String displayUniqueId)
            throws IllegalArgumentException {
        int[] rawOverrides;
        if (!Flags.movableCutoutConfiguration()) {
            return null;
        }
        int index = DisplayUtils.getDisplayUniqueIdConfigIndex(res, displayUniqueId);
        TypedArray array = res.obtainTypedArray(R.array.config_displayCutoutSideOverrideArray);
        int resourceId = 0;
        if (index >= 0 && index < array.length()) {
            resourceId = array.getResourceId(index, 0);
        }
        if (resourceId != 0) {
            rawOverrides = array.getResources().getIntArray(resourceId);
        } else {
            rawOverrides = res.getIntArray(R.array.config_mainBuiltInDisplayCutoutSideOverride);
        }
        array.recycle();
        if (rawOverrides.length == 0) {
            return INVALID_OVERRIDES;
        }
        if (rawOverrides.length != 4) {
            throw new IllegalArgumentException(
                    "Invalid side override definition, exact 4 overrides required: "
                            + Arrays.toString(rawOverrides));
        }
        for (int rotation = 0; rotation <= 3; rotation++) {
            if (rawOverrides[rotation] < 0 || rawOverrides[rotation] >= 4) {
                throw new IllegalArgumentException(
                        "Invalid side override definition: " + Arrays.toString(rawOverrides));
            }
        }
        return rawOverrides;
    }

    public static DisplayCutout fromResourcesRectApproximation(
            Resources res,
            String displayUniqueId,
            int physicalDisplayWidth,
            int physicalDisplayHeight,
            int displayWidth,
            int displayHeight) {
        return fromResourcesRectApproximation(
                res,
                displayUniqueId,
                physicalDisplayWidth,
                physicalDisplayHeight,
                displayWidth,
                displayHeight,
                DisplayMetrics.DENSITY_DEVICE_STABLE,
                false);
    }

    public static DisplayCutout fromResourcesRectApproximation(
            Resources res,
            String displayUniqueId,
            int physicalDisplayWidth,
            int physicalDisplayHeight,
            int displayWidth,
            int displayHeight,
            int displayDensity,
            boolean isSubBuiltInDisplay) {
        return pathAndDisplayCutoutFromSpec(
                        getDisplayCutoutPath(res, displayUniqueId),
                        getDisplayCutoutApproximationRect(res, displayUniqueId),
                        physicalDisplayWidth,
                        physicalDisplayHeight,
                        displayWidth,
                        displayHeight,
                        displayDensity / 160.0f,
                        getWaterfallInsets(res, displayUniqueId),
                        getDisplayCutoutSideOverrides(res, displayUniqueId))
                .second;
    }

    public static DisplayCutout fromSpec(
            String pathSpec,
            int displayWidth,
            int displayHeight,
            float density,
            Insets waterfallInsets,
            int[] sideOverrides) {
        return pathAndDisplayCutoutFromSpec(
                        pathSpec,
                        null,
                        displayWidth,
                        displayHeight,
                        displayWidth,
                        displayHeight,
                        density,
                        waterfallInsets,
                        sideOverrides)
                .second;
    }

    private static Pair<Path, DisplayCutout> pathAndDisplayCutoutFromSpec(
            String pathSpec,
            String rectSpec,
            int physicalDisplayWidth,
            int physicalDisplayHeight,
            int displayWidth,
            int displayHeight,
            float density,
            Insets waterfallInsets,
            int[] sideOverrides) {
        String spec = rectSpec != null ? rectSpec : pathSpec;
        if (TextUtils.isEmpty(spec) && waterfallInsets.equals(Insets.NONE)) {
            return NULL_PAIR;
        }
        float physicalPixelDisplaySizeRatio =
                DisplayUtils.getPhysicalPixelDisplaySizeRatio(
                        physicalDisplayWidth, physicalDisplayHeight, displayWidth, displayHeight);
        synchronized (CACHE_LOCK) {
            try {
                if (spec.equals(sCachedSpec)) {
                    try {
                        if (sCachedDisplayWidth == displayWidth
                                && sCachedDisplayHeight == displayHeight
                                && sCachedDensity == density
                                && waterfallInsets.equals(sCachedWaterfallInsets)
                                && sCachedPhysicalPixelDisplaySizeRatio
                                        == physicalPixelDisplaySizeRatio
                                && Arrays.equals(sCachedSideOverrides, sideOverrides)) {
                            return sCachedCutout;
                        }
                    } catch (Throwable th) {
                        th = th;
                        while (true) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        }
                    }
                }
                String spec2 = spec.trim();
                CutoutSpecification cutoutSpec =
                        new CutoutSpecification.Parser(
                                        density,
                                        physicalDisplayWidth,
                                        physicalDisplayHeight,
                                        physicalPixelDisplaySizeRatio)
                                .parse(spec2);
                Rect safeInset = cutoutSpec.getSafeInset();
                Rect boundLeft = cutoutSpec.getLeftBound();
                Rect boundTop = cutoutSpec.getTopBound();
                Rect boundRight = cutoutSpec.getRightBound();
                Rect boundBottom = cutoutSpec.getBottomBound();
                if (!waterfallInsets.equals(Insets.NONE)) {
                    safeInset.set(
                            Math.max(waterfallInsets.left, safeInset.left),
                            Math.max(waterfallInsets.top, safeInset.top),
                            Math.max(waterfallInsets.right, safeInset.right),
                            Math.max(waterfallInsets.bottom, safeInset.bottom));
                }
                CutoutPathParserInfo cutoutPathParserInfo =
                        new CutoutPathParserInfo(
                                displayWidth,
                                displayHeight,
                                physicalDisplayWidth,
                                physicalDisplayHeight,
                                density,
                                pathSpec.trim(),
                                0,
                                1.0f,
                                physicalPixelDisplaySizeRatio);
                int sideOverride = getSideOverride(sideOverrides, 0);
                Rect[] bounds =
                        new Bounds(boundLeft, boundTop, boundRight, boundBottom, false).getRects();
                int rotateDistance = getRotationToOverride(sideOverride, bounds, 0);
                if (rotateDistance != 0) {
                    Collections.rotate(Arrays.asList(bounds), rotateDistance);
                }
                Rect safeInsets =
                        computeSafeInsets(displayWidth, displayHeight, waterfallInsets, bounds);
                DisplayCutout cutout =
                        new DisplayCutout(
                                safeInsets,
                                waterfallInsets,
                                new Bounds(bounds[0], bounds[1], bounds[2], bounds[3], false),
                                cutoutPathParserInfo,
                                sideOverrides);
                Pair<Path, DisplayCutout> result = new Pair<>(cutoutSpec.getPath(), cutout);
                synchronized (CACHE_LOCK) {
                    sCachedSpec = spec2;
                    sCachedDisplayWidth = displayWidth;
                    sCachedDisplayHeight = displayHeight;
                    sCachedDensity = density;
                    sCachedCutout = result;
                    sCachedWaterfallInsets = waterfallInsets;
                    sCachedPhysicalPixelDisplaySizeRatio = physicalPixelDisplaySizeRatio;
                    sCachedSideOverrides = sideOverrides;
                }
                return result;
            } catch (Throwable th3) {
                th = th3;
            }
        }
    }

    private static Insets loadWaterfallInset(Resources res) {
        return Insets.of(
                res.getDimensionPixelSize(R.dimen.waterfall_display_left_edge_size),
                res.getDimensionPixelSize(R.dimen.waterfall_display_top_edge_size),
                res.getDimensionPixelSize(R.dimen.waterfall_display_right_edge_size),
                res.getDimensionPixelSize(R.dimen.waterfall_display_bottom_edge_size));
    }

    public DisplayCutout getRotated(
            int startWidth, int startHeight, int fromRotation, int toRotation) {
        if (this == NO_CUTOUT) {
            return NO_CUTOUT;
        }
        int rotation = RotationUtils.deltaRotation(fromRotation, toRotation);
        if (rotation == 0) {
            return this;
        }
        Insets waterfallInsets = RotationUtils.rotateInsets(getWaterfallInsets(), rotation);
        Rect[] newBounds = getBoundingRectsAll();
        Rect displayBounds = new Rect(0, 0, startWidth, startHeight);
        for (int i = 0; i < newBounds.length; i++) {
            if (!newBounds[i].isEmpty()) {
                RotationUtils.rotateBounds(newBounds[i], displayBounds, rotation);
            }
        }
        int i2 = -rotation;
        int override = getSideOverride(this.mSideOverrides, toRotation);
        Collections.rotate(
                Arrays.asList(newBounds), getRotationToOverride(override, newBounds, i2));
        CutoutPathParserInfo info = getCutoutPathParserInfo();
        CutoutPathParserInfo newInfo =
                new CutoutPathParserInfo(
                        info.getDisplayWidth(),
                        info.getDisplayHeight(),
                        info.getPhysicalDisplayWidth(),
                        info.getPhysicalDisplayHeight(),
                        info.getDensity(),
                        info.getCutoutSpec(),
                        toRotation,
                        info.getScale(),
                        info.getPhysicalPixelDisplaySizeRatio());
        boolean swapAspect = rotation % 2 != 0;
        int endWidth = swapAspect ? startHeight : startWidth;
        int endHeight = swapAspect ? startWidth : startHeight;
        DisplayCutout tmp = constructDisplayCutout(newBounds, waterfallInsets, newInfo);
        Rect safeInsets = computeSafeInsets(endWidth, endHeight, tmp);
        tmp.mSideOverrides = this.mSideOverrides;
        return tmp.replaceSafeInsets(safeInsets);
    }

    private static int getSideOverride(int[] sideOverrides, int rotation) {
        if (sideOverrides == null || sideOverrides.length != 4) {
            return -1;
        }
        return sideOverrides[rotation];
    }

    private static int getRotationToOverride(int sideOverride, Rect[] bounds, int defaultRotation) {
        if (sideOverride == -1) {
            return defaultRotation;
        }
        int side = -1;
        for (int i = 0; i <= 3; i++) {
            if (!bounds[i].isEmpty()) {
                if (side != -1) {
                    return defaultRotation;
                }
                side = i;
            }
        }
        if (side == -1) {
            return defaultRotation;
        }
        int rotation = sideOverride - side;
        if (rotation < 0) {
            return rotation + 4;
        }
        return rotation;
    }

    public static Rect computeSafeInsets(int displayW, int displayH, DisplayCutout cutout) {
        return computeSafeInsets(
                displayW, displayH, cutout.getWaterfallInsets(), cutout.getBoundingRectsAll());
    }

    private static Rect computeSafeInsets(
            int displayW, int displayH, Insets waterFallInsets, Rect[] bounds) {
        int leftInset =
                Math.max(
                        waterFallInsets.left,
                        findCutoutInsetForSide(displayW, displayH, bounds[0], 3));
        int topInset =
                Math.max(
                        waterFallInsets.top,
                        findCutoutInsetForSide(displayW, displayH, bounds[1], 48));
        int rightInset =
                Math.max(
                        waterFallInsets.right,
                        findCutoutInsetForSide(displayW, displayH, bounds[2], 5));
        int bottomInset =
                Math.max(
                        waterFallInsets.bottom,
                        findCutoutInsetForSide(displayW, displayH, bounds[3], 80));
        return new Rect(leftInset, topInset, rightInset, bottomInset);
    }

    private static int findCutoutInsetForSide(
            int displayW, int displayH, Rect boundingRect, int gravity) {
        if (boundingRect.isEmpty()) {
            return 0;
        }
        switch (gravity) {
            case 3:
                return Math.max(0, boundingRect.right);
            case 5:
                return Math.max(0, displayW - boundingRect.left);
            case 48:
                return Math.max(0, boundingRect.bottom);
            case 80:
                return Math.max(0, displayH - boundingRect.top);
            default:
                throw new IllegalArgumentException("unknown gravity: " + gravity);
        }
    }

    public static final class ParcelableWrapper implements Parcelable {
        public static final Parcelable.Creator<ParcelableWrapper> CREATOR =
                new Parcelable.Creator<
                        ParcelableWrapper>() { // from class:
                                               // android.view.DisplayCutout.ParcelableWrapper.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public ParcelableWrapper createFromParcel(Parcel in) {
                        return new ParcelableWrapper(ParcelableWrapper.readCutoutFromParcel(in));
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public ParcelableWrapper[] newArray(int size) {
                        return new ParcelableWrapper[size];
                    }
                };
        private DisplayCutout mInner;

        public ParcelableWrapper() {
            this(DisplayCutout.NO_CUTOUT);
        }

        public ParcelableWrapper(DisplayCutout cutout) {
            this.mInner = cutout;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            writeCutoutToParcel(this.mInner, out, flags);
        }

        public static void writeCutoutToParcel(DisplayCutout cutout, Parcel out, int flags) {
            if (cutout == null) {
                out.writeInt(-1);
                return;
            }
            if (cutout == DisplayCutout.NO_CUTOUT) {
                out.writeInt(0);
                return;
            }
            out.writeInt(1);
            out.writeTypedObject(cutout.mSafeInsets, flags);
            out.writeTypedArray(cutout.mBounds.getRects(), flags);
            out.writeTypedObject(cutout.mWaterfallInsets, flags);
            out.writeInt(cutout.mCutoutPathParserInfo.getDisplayWidth());
            out.writeInt(cutout.mCutoutPathParserInfo.getDisplayHeight());
            out.writeInt(cutout.mCutoutPathParserInfo.getPhysicalDisplayWidth());
            out.writeInt(cutout.mCutoutPathParserInfo.getPhysicalDisplayHeight());
            out.writeFloat(cutout.mCutoutPathParserInfo.getDensity());
            out.writeString(cutout.mCutoutPathParserInfo.getCutoutSpec());
            out.writeInt(cutout.mCutoutPathParserInfo.getRotation());
            out.writeFloat(cutout.mCutoutPathParserInfo.getScale());
            out.writeFloat(cutout.mCutoutPathParserInfo.getPhysicalPixelDisplaySizeRatio());
            out.writeIntArray(cutout.mSideOverrides);
        }

        public void readFromParcel(Parcel in) {
            this.mInner = readCutoutFromParcel(in);
        }

        public static DisplayCutout readCutoutFromParcel(Parcel in) {
            int variant = in.readInt();
            if (variant == -1) {
                return null;
            }
            if (variant != 0) {
                Rect safeInsets = (Rect) in.readTypedObject(Rect.CREATOR);
                Rect[] bounds = new Rect[4];
                in.readTypedArray(bounds, Rect.CREATOR);
                Insets waterfallInsets = (Insets) in.readTypedObject(Insets.CREATOR);
                int displayWidth = in.readInt();
                int displayHeight = in.readInt();
                int physicalDisplayWidth = in.readInt();
                int physicalDisplayHeight = in.readInt();
                float density = in.readFloat();
                String cutoutSpec = in.readString();
                int rotation = in.readInt();
                float scale = in.readFloat();
                float physicalPixelDisplaySizeRatio = in.readFloat();
                CutoutPathParserInfo info =
                        new CutoutPathParserInfo(
                                displayWidth,
                                displayHeight,
                                physicalDisplayWidth,
                                physicalDisplayHeight,
                                density,
                                cutoutSpec,
                                rotation,
                                scale,
                                physicalPixelDisplaySizeRatio);
                int[] sideOverrides = in.createIntArray();
                return new DisplayCutout(
                        safeInsets,
                        waterfallInsets,
                        new Bounds(bounds, false),
                        info,
                        sideOverrides);
            }
            return DisplayCutout.NO_CUTOUT;
        }

        public DisplayCutout get() {
            return this.mInner;
        }

        public void set(ParcelableWrapper cutout) {
            this.mInner = cutout.get();
        }

        public void set(DisplayCutout cutout) {
            this.mInner = cutout;
        }

        public void scale(float scale) {
            Rect safeInsets = this.mInner.getSafeInsets();
            safeInsets.scale(scale);
            Bounds bounds = new Bounds(this.mInner.mBounds.mRects, true);
            bounds.scale(scale);
            Rect waterfallInsets = this.mInner.mWaterfallInsets.toRect();
            waterfallInsets.scale(scale);
            CutoutPathParserInfo info =
                    new CutoutPathParserInfo(
                            this.mInner.mCutoutPathParserInfo.getDisplayWidth(),
                            this.mInner.mCutoutPathParserInfo.getDisplayHeight(),
                            this.mInner.mCutoutPathParserInfo.getPhysicalDisplayWidth(),
                            this.mInner.mCutoutPathParserInfo.getPhysicalDisplayHeight(),
                            this.mInner.mCutoutPathParserInfo.getDensity(),
                            this.mInner.mCutoutPathParserInfo.getCutoutSpec(),
                            this.mInner.mCutoutPathParserInfo.getRotation(),
                            scale,
                            this.mInner.mCutoutPathParserInfo.getPhysicalPixelDisplaySizeRatio());
            int[] sideOverrides = this.mInner.mSideOverrides;
            this.mInner =
                    new DisplayCutout(
                            safeInsets, Insets.of(waterfallInsets), bounds, info, sideOverrides);
        }

        public int hashCode() {
            return this.mInner.hashCode();
        }

        public boolean equals(Object o) {
            return (o instanceof ParcelableWrapper)
                    && this.mInner.equals(((ParcelableWrapper) o).mInner);
        }

        public String toString() {
            return String.valueOf(this.mInner);
        }
    }

    public static final class Builder {
        private Path mCutoutPath;
        private Insets mSafeInsets = Insets.NONE;
        private Insets mWaterfallInsets = Insets.NONE;
        private final Rect mBoundingRectLeft = new Rect();
        private final Rect mBoundingRectTop = new Rect();
        private final Rect mBoundingRectRight = new Rect();
        private final Rect mBoundingRectBottom = new Rect();

        public DisplayCutout build() {
            CutoutPathParserInfo info;
            if (this.mCutoutPath != null) {
                info = new CutoutPathParserInfo(0, 0, 0, 0, 0.0f, "test", 0, 1.0f, 1.0f);
                synchronized (DisplayCutout.CACHE_LOCK) {
                    DisplayCutout.sCachedCutoutPathParserInfo = info;
                    DisplayCutout.sCachedCutoutPath = this.mCutoutPath;
                }
            } else {
                info = null;
            }
            return new DisplayCutout(
                    this.mSafeInsets.toRect(),
                    this.mWaterfallInsets,
                    this.mBoundingRectLeft,
                    this.mBoundingRectTop,
                    this.mBoundingRectRight,
                    this.mBoundingRectBottom,
                    info,
                    false);
        }

        public Builder setSafeInsets(Insets safeInsets) {
            this.mSafeInsets = safeInsets;
            return this;
        }

        public Builder setWaterfallInsets(Insets waterfallInsets) {
            this.mWaterfallInsets = waterfallInsets;
            return this;
        }

        public Builder setBoundingRectLeft(Rect boundingRectLeft) {
            this.mBoundingRectLeft.set(boundingRectLeft);
            return this;
        }

        public Builder setBoundingRectTop(Rect boundingRectTop) {
            this.mBoundingRectTop.set(boundingRectTop);
            return this;
        }

        public Builder setBoundingRectRight(Rect boundingRectRight) {
            this.mBoundingRectRight.set(boundingRectRight);
            return this;
        }

        public Builder setBoundingRectBottom(Rect boundingRectBottom) {
            this.mBoundingRectBottom.set(boundingRectBottom);
            return this;
        }

        public Builder setCutoutPath(Path cutoutPath) {
            this.mCutoutPath = cutoutPath;
            return this;
        }
    }

    public static DisplayCutout fromResourcesRectApproximation(
            Resources res,
            String displayUniqueId,
            int physicalDisplayWidth,
            int physicalDisplayHeight,
            int displayWidth,
            int displayHeight,
            int displayDensity,
            String pathSpec) {
        return pathAndDisplayCutoutFromSpec(
                        pathSpec,
                        null,
                        physicalDisplayWidth,
                        physicalDisplayHeight,
                        displayWidth,
                        displayHeight,
                        displayDensity / 160.0f,
                        loadWaterfallInset(res),
                        getDisplayCutoutSideOverrides(res, displayUniqueId))
                .second;
    }

    public static Path pathFromResourcesForUDC(
            Resources res,
            String displayUniqueId,
            int displayWidth,
            int displayHeight,
            int displayDensity,
            boolean isSubBuiltInDisplay) {
        String pathSpec = res.getString(R.string.config_mainBuiltInDisplayCutoutForUDC);
        return pathAndDisplayCutoutFromSpec(
                        pathSpec,
                        null,
                        displayWidth,
                        displayHeight,
                        displayWidth,
                        displayHeight,
                        displayDensity / 160.0f,
                        getWaterfallInsets(res, displayUniqueId),
                        getDisplayCutoutSideOverrides(res, displayUniqueId))
                .first;
    }
}
