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
import android.view.CutoutSpecification;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import com.android.internal.protolog.PerfettoProtoLogImpl;
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
    private static final Object CACHE_LOCK;
    private static final CutoutPathParserInfo EMPTY_PARSER_INFO;
    public static final String EMULATION_OVERLAY_CATEGORY = "com.android.internal.display_cutout_emulation";
    static final int[] INVALID_OVERRIDES;
    private static final int INVALID_SIDE_OVERRIDE = -1;
    public static final int LETTERBOX_BOTTOM = 8;
    public static final int LETTERBOX_LEFT = 1;
    public static final int LETTERBOX_NONE = 0;
    public static final int LETTERBOX_RIGHT = 2;
    public static final int LETTERBOX_TOP = 4;
    public static final DisplayCutout NO_CUTOUT;
    private static final Pair<Path, DisplayCutout> NULL_PAIR;
    private static final String SIDE_STRING_BOTTOM = "bottom";
    private static final String SIDE_STRING_LEFT = "left";
    private static final String SIDE_STRING_RIGHT = "right";
    private static final String SIDE_STRING_TOP = "top";
    private static final String TAG = "DisplayCutout";
    private static final Rect ZERO_RECT;
    private static Pair<Path, DisplayCutout> sCachedCutout;
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

    @Retention(RetentionPolicy.SOURCE)
    public @interface BoundsPosition {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CutoutPolicy {
        public static final int DEFAULT = 0;
        public static final int NOT_OVERLAP = 2;
        public static final int OVERLAP = 1;
    }

    private static int atLeastZero(int i) {
        if (i < 0) {
            return 0;
        }
        return i;
    }

    static {
        Rect rect = new Rect();
        ZERO_RECT = rect;
        CutoutPathParserInfo cutoutPathParserInfo = new CutoutPathParserInfo(0, 0, 0, 0, 0.0f, "", 0, 0.0f, 0.0f);
        EMPTY_PARSER_INFO = cutoutPathParserInfo;
        NO_CUTOUT = new DisplayCutout(rect, Insets.NONE, rect, rect, rect, rect, cutoutPathParserInfo, false);
        Pair<Path, DisplayCutout> pair = new Pair<>(null, null);
        NULL_PAIR = pair;
        CACHE_LOCK = new Object();
        sCachedCutout = pair;
        INVALID_OVERRIDES = new int[]{-1, -1, -1, -1};
    }

    private static class Bounds {
        private final Rect[] mRects;

        private Bounds(Rect rect, Rect rect2, Rect rect3, Rect rect4, boolean z) {
            this.mRects = new Rect[]{DisplayCutout.getCopyOrRef(rect, z), DisplayCutout.getCopyOrRef(rect2, z), DisplayCutout.getCopyOrRef(rect3, z), DisplayCutout.getCopyOrRef(rect4, z)};
        }

        private Bounds(Rect[] rectArr, boolean z) {
            if (rectArr.length != 4) {
                throw new IllegalArgumentException("rects must have exactly 4 elements: rects=" + Arrays.toString(rectArr));
            }
            int i = 0;
            if (z) {
                this.mRects = new Rect[4];
                while (i < 4) {
                    this.mRects[i] = new Rect(rectArr[i]);
                    i++;
                }
                return;
            }
            int length = rectArr.length;
            while (i < length) {
                if (rectArr[i] == null) {
                    throw new IllegalArgumentException("rects must have non-null elements: rects=" + Arrays.toString(rectArr));
                }
                i++;
            }
            this.mRects = rectArr;
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
        public Rect getRect(int i) {
            return new Rect(this.mRects[i]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Rect[] getRects() {
            Rect[] rectArr = new Rect[4];
            for (int i = 0; i < 4; i++) {
                rectArr[i] = new Rect(this.mRects[i]);
            }
            return rectArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void scale(float f) {
            for (int i = 0; i < 4; i++) {
                this.mRects[i].scale(f);
            }
        }

        public int hashCode() {
            int iHashCode = 0;
            for (Rect rect : this.mRects) {
                iHashCode = (iHashCode * 48271) + rect.hashCode();
            }
            return iHashCode;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Bounds) {
                return Arrays.deepEquals(this.mRects, ((Bounds) obj).mRects);
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

        public CutoutPathParserInfo(int i, int i2, int i3, int i4, float f, String str, int i5, float f2, float f3) {
            this.mDisplayWidth = i;
            this.mDisplayHeight = i2;
            this.mPhysicalDisplayWidth = i3;
            this.mPhysicalDisplayHeight = i4;
            this.mDensity = f;
            this.mCutoutSpec = str == null ? "" : str;
            this.mRotation = i5;
            this.mScale = f2;
            this.mPhysicalPixelDisplaySizeRatio = f3;
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
            this.mPhysicalPixelDisplaySizeRatio = cutoutPathParserInfo.mPhysicalPixelDisplaySizeRatio;
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
            return (((((((((((((((Integer.hashCode(this.mDisplayWidth) * 48271) + Integer.hashCode(this.mDisplayHeight)) * 48271) + Float.hashCode(this.mDensity)) * 48271) + this.mCutoutSpec.hashCode()) * 48271) + Integer.hashCode(this.mRotation)) * 48271) + Float.hashCode(this.mScale)) * 48271) + Float.hashCode(this.mPhysicalPixelDisplaySizeRatio)) * 48271) + Integer.hashCode(this.mPhysicalDisplayWidth)) * 48271) + Integer.hashCode(this.mPhysicalDisplayHeight);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof CutoutPathParserInfo) {
                CutoutPathParserInfo cutoutPathParserInfo = (CutoutPathParserInfo) obj;
                if (this.mDisplayWidth == cutoutPathParserInfo.mDisplayWidth && this.mDisplayHeight == cutoutPathParserInfo.mDisplayHeight && this.mPhysicalDisplayWidth == cutoutPathParserInfo.mPhysicalDisplayWidth && this.mPhysicalDisplayHeight == cutoutPathParserInfo.mPhysicalDisplayHeight && this.mDensity == cutoutPathParserInfo.mDensity && this.mCutoutSpec.equals(cutoutPathParserInfo.mCutoutSpec) && this.mRotation == cutoutPathParserInfo.mRotation && this.mScale == cutoutPathParserInfo.mScale && this.mPhysicalPixelDisplaySizeRatio == cutoutPathParserInfo.mPhysicalPixelDisplaySizeRatio) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return "CutoutPathParserInfo{displayWidth=" + this.mDisplayWidth + " displayHeight=" + this.mDisplayHeight + " physicalDisplayWidth=" + this.mPhysicalDisplayWidth + " physicalDisplayHeight=" + this.mPhysicalDisplayHeight + " density={" + this.mDensity + "} cutoutSpec={" + this.mCutoutSpec + "} rotation={" + this.mRotation + "} scale={" + this.mScale + "} physicalPixelDisplaySizeRatio={" + this.mPhysicalPixelDisplaySizeRatio + "}}";
        }
    }

    public DisplayCutout(Insets insets, Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        this(getCopyOrRef(insets.toRect(), true), Insets.NONE, new Bounds(rect, rect2, rect3, rect4, true), (CutoutPathParserInfo) null, (int[]) null);
    }

    public DisplayCutout(Insets insets, Rect rect, Rect rect2, Rect rect3, Rect rect4, Insets insets2, CutoutPathParserInfo cutoutPathParserInfo) {
        this(getCopyOrRef(insets.toRect(), true), insets2, new Bounds(rect, rect2, rect3, rect4, true), cutoutPathParserInfo, (int[]) null);
    }

    public DisplayCutout(Insets insets, Rect rect, Rect rect2, Rect rect3, Rect rect4, Insets insets2, CutoutPathParserInfo cutoutPathParserInfo, int[] iArr) {
        this(insets.toRect(), insets2, new Bounds(rect, rect2, rect3, rect4, true), cutoutPathParserInfo, iArr);
    }

    public DisplayCutout(Insets insets, Rect rect, Rect rect2, Rect rect3, Rect rect4, Insets insets2) {
        this(getCopyOrRef(insets.toRect(), true), insets2, new Bounds(rect, rect2, rect3, rect4, true), (CutoutPathParserInfo) null, (int[]) null);
    }

    @Deprecated
    public DisplayCutout(Rect rect, List<Rect> list) {
        this(getCopyOrRef(rect, true), Insets.NONE, new Bounds(extractBoundsFromList(rect, list), true), (CutoutPathParserInfo) null, (int[]) null);
    }

    private DisplayCutout(Rect rect, Insets insets, Rect rect2, Rect rect3, Rect rect4, Rect rect5, CutoutPathParserInfo cutoutPathParserInfo, boolean z) {
        this(getCopyOrRef(rect, z), insets, new Bounds(rect2, rect3, rect4, rect5, z), cutoutPathParserInfo, (int[]) null);
    }

    private DisplayCutout(Rect rect, Insets insets, Rect[] rectArr, CutoutPathParserInfo cutoutPathParserInfo, boolean z) {
        this(getCopyOrRef(rect, z), insets, new Bounds(rectArr, z), cutoutPathParserInfo, (int[]) null);
    }

    private DisplayCutout(Rect rect, Insets insets, Bounds bounds, CutoutPathParserInfo cutoutPathParserInfo) {
        this(rect, insets, bounds, cutoutPathParserInfo, (int[]) null);
    }

    private DisplayCutout(Rect rect, Insets insets, Bounds bounds, CutoutPathParserInfo cutoutPathParserInfo, int[] iArr) {
        this.mSafeInsets = rect;
        this.mWaterfallInsets = insets == null ? Insets.NONE : insets;
        this.mBounds = bounds;
        this.mCutoutPathParserInfo = cutoutPathParserInfo == null ? EMPTY_PARSER_INFO : cutoutPathParserInfo;
        this.mSideOverrides = iArr;
    }

    public boolean isCutoutOnLongEdge(int i, int i2) {
        boolean z = i < i2;
        for (Rect rect : getBoundingRects()) {
            if (z) {
                if (rect.left == 0 || rect.right == i) {
                    return true;
                }
            } else if (rect.top == 0 || rect.bottom == i2) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Rect getCopyOrRef(Rect rect, boolean z) {
        if (rect == null) {
            return ZERO_RECT;
        }
        return z ? new Rect(rect) : rect;
    }

    public Insets getWaterfallInsets() {
        return this.mWaterfallInsets;
    }

    public static Rect[] extractBoundsFromList(Rect rect, List<Rect> list) {
        Rect[] rectArr = new Rect[4];
        for (int i = 0; i < 4; i++) {
            rectArr[i] = ZERO_RECT;
        }
        if (rect != null && list != null) {
            boolean z = rect.top > 0 || rect.bottom > 0;
            for (Rect rect2 : list) {
                if (z) {
                    if (rect2.top == 0) {
                        rectArr[1] = rect2;
                    } else {
                        rectArr[3] = rect2;
                    }
                } else if (rect2.left == 0) {
                    rectArr[0] = rect2;
                } else {
                    rectArr[2] = rect2;
                }
            }
        }
        return rectArr;
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
        ArrayList arrayList = new ArrayList();
        for (Rect rect : getBoundingRectsAll()) {
            if (!rect.isEmpty()) {
                arrayList.add(new Rect(rect));
            }
        }
        return arrayList;
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
        Object obj = CACHE_LOCK;
        synchronized (obj) {
            if (this.mCutoutPathParserInfo.equals(sCachedCutoutPathParserInfo)) {
                return sCachedCutoutPath;
            }
            Path path = new CutoutSpecification.Parser(this.mCutoutPathParserInfo.getDensity(), this.mCutoutPathParserInfo.getPhysicalDisplayWidth(), this.mCutoutPathParserInfo.getPhysicalDisplayHeight(), this.mCutoutPathParserInfo.getPhysicalPixelDisplaySizeRatio()).parse(this.mCutoutPathParserInfo.getCutoutSpec()).getPath();
            if (path == null || path.isEmpty()) {
                return null;
            }
            Matrix matrix = new Matrix();
            if (this.mCutoutPathParserInfo.getRotation() != 0) {
                RotationUtils.transformPhysicalToLogicalCoordinates(this.mCutoutPathParserInfo.getRotation(), this.mCutoutPathParserInfo.getDisplayWidth(), this.mCutoutPathParserInfo.getDisplayHeight(), matrix);
            }
            matrix.postScale(this.mCutoutPathParserInfo.getScale(), this.mCutoutPathParserInfo.getScale());
            path.transform(matrix);
            synchronized (obj) {
                sCachedCutoutPathParserInfo = new CutoutPathParserInfo(this.mCutoutPathParserInfo);
                sCachedCutoutPath = path;
            }
            return path;
        }
    }

    public CutoutPathParserInfo getCutoutPathParserInfo() {
        return this.mCutoutPathParserInfo;
    }

    public int hashCode() {
        return (((((((this.mSafeInsets.hashCode() * 48271) + this.mBounds.hashCode()) * 48271) + this.mWaterfallInsets.hashCode()) * 48271) + this.mCutoutPathParserInfo.hashCode()) * 48271) + Arrays.hashCode(this.mSideOverrides);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof DisplayCutout) {
            DisplayCutout displayCutout = (DisplayCutout) obj;
            if (this.mSafeInsets.equals(displayCutout.mSafeInsets) && this.mBounds.equals(displayCutout.mBounds) && this.mWaterfallInsets.equals(displayCutout.mWaterfallInsets) && this.mCutoutPathParserInfo.equals(displayCutout.mCutoutPathParserInfo) && Arrays.equals(this.mSideOverrides, displayCutout.mSideOverrides)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "DisplayCutout{insets=" + this.mSafeInsets + " waterfall=" + this.mWaterfallInsets + " boundingRect={" + this.mBounds + "} cutoutPathParserInfo={" + this.mCutoutPathParserInfo + "} sideOverrides=" + sideOverridesToString(this.mSideOverrides) + "}";
    }

    private static String sideOverridesToString(int[] iArr) {
        if (iArr == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        StringBuilder sb = new StringBuilder("{");
        int length = iArr.length;
        if (length != 4) {
            sb.append("length=");
            sb.append(iArr.length);
            sb.append(". ");
        }
        boolean z = false;
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            if (i2 != -1) {
                if (z) {
                    sb.append(", ");
                }
                sb.append(Surface.rotationToString(i));
                sb.append(": ");
                z = true;
                if (i2 == 0) {
                    sb.append("left");
                } else if (i2 == 1) {
                    sb.append("top");
                } else if (i2 == 2) {
                    sb.append("right");
                } else if (i2 == 3) {
                    sb.append("bottom");
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        this.mSafeInsets.dumpDebug(protoOutputStream, 1146756268033L);
        this.mBounds.getRect(0).dumpDebug(protoOutputStream, 1146756268035L);
        this.mBounds.getRect(1).dumpDebug(protoOutputStream, 1146756268036L);
        this.mBounds.getRect(2).dumpDebug(protoOutputStream, 1146756268037L);
        this.mBounds.getRect(3).dumpDebug(protoOutputStream, 1146756268038L);
        this.mWaterfallInsets.toRect().dumpDebug(protoOutputStream, 1146756268039L);
        int[] iArr = this.mSideOverrides;
        if (iArr != null) {
            for (int i : iArr) {
                protoOutputStream.write(DisplayCutoutProto.SIDE_OVERRIDES, i);
            }
        }
        protoOutputStream.end(jStart);
    }

    public DisplayCutout inset(int i, int i2, int i3, int i4) {
        if ((i != 0 || i2 != 0 || i3 != 0 || i4 != 0) && (!isBoundsEmpty() || !this.mWaterfallInsets.equals(Insets.NONE))) {
            Rect rectInsetInsets = insetInsets(i, i2, i3, i4, new Rect(this.mSafeInsets));
            if (i != 0 || i2 != 0 || !this.mSafeInsets.equals(rectInsetInsets)) {
                Rect rectInsetInsets2 = insetInsets(i, i2, i3, i4, this.mWaterfallInsets.toRect());
                Rect[] rects = this.mBounds.getRects();
                for (int i5 = 0; i5 < rects.length; i5++) {
                    if (!rects[i5].equals(ZERO_RECT)) {
                        rects[i5].offset(-i, -i2);
                    }
                }
                return new DisplayCutout(rectInsetInsets, Insets.of(rectInsetInsets2), rects, this.mCutoutPathParserInfo, false);
            }
        }
        return this;
    }

    private Rect insetInsets(int i, int i2, int i3, int i4, Rect rect) {
        if (i2 > 0 || rect.top > 0) {
            rect.top = atLeastZero(rect.top - i2);
        }
        if (i4 > 0 || rect.bottom > 0) {
            rect.bottom = atLeastZero(rect.bottom - i4);
        }
        if (i > 0 || rect.left > 0) {
            rect.left = atLeastZero(rect.left - i);
        }
        if (i3 <= 0 && rect.right <= 0) {
            return rect;
        }
        rect.right = atLeastZero(rect.right - i3);
        return rect;
    }

    public DisplayCutout replaceSafeInsets(Rect rect) {
        return new DisplayCutout(new Rect(rect), this.mWaterfallInsets, this.mBounds, this.mCutoutPathParserInfo, this.mSideOverrides);
    }

    public static DisplayCutout fromBoundingRect(int i, int i2, int i3, int i4, int i5) {
        Rect[] rectArr = new Rect[4];
        int i6 = 0;
        while (i6 < 4) {
            rectArr[i6] = i5 == i6 ? new Rect(i, i2, i3, i4) : new Rect();
            i6++;
        }
        return new DisplayCutout(ZERO_RECT, Insets.NONE, rectArr, (CutoutPathParserInfo) null, false);
    }

    public static DisplayCutout constructDisplayCutout(Rect[] rectArr, Insets insets, CutoutPathParserInfo cutoutPathParserInfo) {
        return new DisplayCutout(ZERO_RECT, insets, rectArr, cutoutPathParserInfo, false);
    }

    public static DisplayCutout fromBounds(Rect[] rectArr) {
        return new DisplayCutout(ZERO_RECT, Insets.NONE, rectArr, (CutoutPathParserInfo) null, false);
    }

    private static String getDisplayCutoutPath(Resources resources, String str) throws Resources.NotFoundException {
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        String[] stringArray = resources.getStringArray(R.array.config_displayCutoutPathArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < stringArray.length) {
            return stringArray[displayUniqueIdConfigIndex];
        }
        return resources.getString(R.string.config_mainBuiltInDisplayCutout);
    }

    private static String getDisplayCutoutApproximationRect(Resources resources, String str) throws Resources.NotFoundException {
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        String[] stringArray = resources.getStringArray(R.array.config_displayCutoutApproximationRectArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < stringArray.length) {
            return stringArray[displayUniqueIdConfigIndex];
        }
        return resources.getString(R.string.config_mainBuiltInDisplayCutoutRectApproximation);
    }

    public static boolean getMaskBuiltInDisplayCutout(Resources resources, String str) throws Resources.NotFoundException {
        boolean z;
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_maskBuiltInDisplayCutoutArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < typedArrayObtainTypedArray.length()) {
            z = typedArrayObtainTypedArray.getBoolean(displayUniqueIdConfigIndex, false);
        } else {
            z = resources.getBoolean(R.bool.config_maskMainBuiltInDisplayCutout);
        }
        typedArrayObtainTypedArray.recycle();
        return z;
    }

    public static boolean getFillBuiltInDisplayCutout(Resources resources, String str) throws Resources.NotFoundException {
        boolean z;
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_fillBuiltInDisplayCutoutArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < typedArrayObtainTypedArray.length()) {
            z = typedArrayObtainTypedArray.getBoolean(displayUniqueIdConfigIndex, false);
        } else {
            z = resources.getBoolean(R.bool.config_fillMainBuiltInDisplayCutout);
        }
        typedArrayObtainTypedArray.recycle();
        return z;
    }

    private static Insets getWaterfallInsets(Resources resources, String str) throws Resources.NotFoundException {
        Insets insetsLoadWaterfallInset;
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_waterfallCutoutArray);
        int resourceId = (displayUniqueIdConfigIndex < 0 || displayUniqueIdConfigIndex >= typedArrayObtainTypedArray.length()) ? 0 : typedArrayObtainTypedArray.getResourceId(displayUniqueIdConfigIndex, 0);
        if (resourceId != 0) {
            TypedArray typedArrayObtainTypedArray2 = resources.obtainTypedArray(resourceId);
            insetsLoadWaterfallInset = Insets.of(typedArrayObtainTypedArray2.getDimensionPixelSize(0, 0), typedArrayObtainTypedArray2.getDimensionPixelSize(1, 0), typedArrayObtainTypedArray2.getDimensionPixelSize(2, 0), typedArrayObtainTypedArray2.getDimensionPixelSize(3, 0));
            typedArrayObtainTypedArray2.recycle();
        } else {
            insetsLoadWaterfallInset = loadWaterfallInset(resources);
        }
        typedArrayObtainTypedArray.recycle();
        return insetsLoadWaterfallInset;
    }

    private static int[] getDisplayCutoutSideOverrides(Resources resources, String str) throws Resources.NotFoundException, IllegalArgumentException {
        int[] intArray;
        if (!Flags.movableCutoutConfiguration()) {
            return null;
        }
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_displayCutoutSideOverrideArray);
        int resourceId = (displayUniqueIdConfigIndex < 0 || displayUniqueIdConfigIndex >= typedArrayObtainTypedArray.length()) ? 0 : typedArrayObtainTypedArray.getResourceId(displayUniqueIdConfigIndex, 0);
        if (resourceId != 0) {
            intArray = typedArrayObtainTypedArray.getResources().getIntArray(resourceId);
        } else {
            intArray = resources.getIntArray(R.array.config_mainBuiltInDisplayCutoutSideOverride);
        }
        typedArrayObtainTypedArray.recycle();
        if (intArray.length == 0) {
            return INVALID_OVERRIDES;
        }
        if (intArray.length != 4) {
            throw new IllegalArgumentException("Invalid side override definition, exact 4 overrides required: " + Arrays.toString(intArray));
        }
        for (int i = 0; i <= 3; i++) {
            int i2 = intArray[i];
            if (i2 < 0 || i2 >= 4) {
                throw new IllegalArgumentException("Invalid side override definition: " + Arrays.toString(intArray));
            }
        }
        return intArray;
    }

    public static DisplayCutout fromResourcesRectApproximation(Resources resources, String str, int i, int i2, int i3, int i4) {
        return fromResourcesRectApproximation(resources, str, i, i2, i3, i4, DisplayMetrics.DENSITY_DEVICE_STABLE, false);
    }

    public static DisplayCutout fromResourcesRectApproximation(Resources resources, String str, int i, int i2, int i3, int i4, int i5, boolean z) {
        return pathAndDisplayCutoutFromSpec(getDisplayCutoutPath(resources, str), getDisplayCutoutApproximationRect(resources, str), i, i2, i3, i4, i5 / 160.0f, getWaterfallInsets(resources, str), getDisplayCutoutSideOverrides(resources, str)).second;
    }

    public static DisplayCutout fromSpec(String str, int i, int i2, float f, Insets insets, int[] iArr) {
        return pathAndDisplayCutoutFromSpec(str, null, i, i2, i, i2, f, insets, iArr).second;
    }

    private static Pair<Path, DisplayCutout> pathAndDisplayCutoutFromSpec(String str, String str2, int i, int i2, int i3, int i4, float f, Insets insets, int[] iArr) {
        String str3 = str2 != null ? str2 : str;
        if (TextUtils.isEmpty(str3) && insets.equals(Insets.NONE)) {
            return NULL_PAIR;
        }
        float physicalPixelDisplaySizeRatio = DisplayUtils.getPhysicalPixelDisplaySizeRatio(i, i2, i3, i4);
        Object obj = CACHE_LOCK;
        synchronized (obj) {
            if (str3.equals(sCachedSpec) && sCachedDisplayWidth == i3 && sCachedDisplayHeight == i4 && sCachedDensity == f && insets.equals(sCachedWaterfallInsets) && sCachedPhysicalPixelDisplaySizeRatio == physicalPixelDisplaySizeRatio && Arrays.equals(sCachedSideOverrides, iArr)) {
                return sCachedCutout;
            }
            String strTrim = str3.trim();
            CutoutSpecification cutoutSpecification = new CutoutSpecification.Parser(f, i, i2, physicalPixelDisplaySizeRatio).parse(strTrim);
            Rect safeInset = cutoutSpecification.getSafeInset();
            Rect leftBound = cutoutSpecification.getLeftBound();
            Rect topBound = cutoutSpecification.getTopBound();
            Rect rightBound = cutoutSpecification.getRightBound();
            Rect bottomBound = cutoutSpecification.getBottomBound();
            if (!insets.equals(Insets.NONE)) {
                safeInset.set(Math.max(insets.left, safeInset.left), Math.max(insets.top, safeInset.top), Math.max(insets.right, safeInset.right), Math.max(insets.bottom, safeInset.bottom));
            }
            CutoutPathParserInfo cutoutPathParserInfo = new CutoutPathParserInfo(i3, i4, i, i2, f, str.trim(), 0, 1.0f, physicalPixelDisplaySizeRatio);
            int sideOverride = getSideOverride(iArr, 0);
            Rect[] rects = new Bounds(leftBound, topBound, rightBound, bottomBound, false).getRects();
            int rotationToOverride = getRotationToOverride(sideOverride, rects, 0);
            if (rotationToOverride != 0) {
                Collections.rotate(Arrays.asList(rects), rotationToOverride);
            }
            Pair<Path, DisplayCutout> pair = new Pair<>(cutoutSpecification.getPath(), new DisplayCutout(computeSafeInsets(i3, i4, insets, rects), insets, new Bounds(rects[0], rects[1], rects[2], rects[3], false), cutoutPathParserInfo, iArr));
            synchronized (obj) {
                sCachedSpec = strTrim;
                sCachedDisplayWidth = i3;
                sCachedDisplayHeight = i4;
                sCachedDensity = f;
                sCachedCutout = pair;
                sCachedWaterfallInsets = insets;
                sCachedPhysicalPixelDisplaySizeRatio = physicalPixelDisplaySizeRatio;
                sCachedSideOverrides = iArr;
            }
            return pair;
        }
    }

    private static Insets loadWaterfallInset(Resources resources) {
        return Insets.of(resources.getDimensionPixelSize(R.dimen.waterfall_display_left_edge_size), resources.getDimensionPixelSize(R.dimen.waterfall_display_top_edge_size), resources.getDimensionPixelSize(R.dimen.waterfall_display_right_edge_size), resources.getDimensionPixelSize(R.dimen.waterfall_display_bottom_edge_size));
    }

    public DisplayCutout getRotated(int i, int i2, int i3, int i4) {
        DisplayCutout displayCutout = NO_CUTOUT;
        if (this == displayCutout) {
            return displayCutout;
        }
        int iDeltaRotation = RotationUtils.deltaRotation(i3, i4);
        if (iDeltaRotation == 0) {
            return this;
        }
        Insets insetsRotateInsets = RotationUtils.rotateInsets(getWaterfallInsets(), iDeltaRotation);
        Rect[] boundingRectsAll = getBoundingRectsAll();
        int i5 = i;
        Rect rect = new Rect(0, 0, i5, i2);
        for (int i6 = 0; i6 < boundingRectsAll.length; i6++) {
            if (!boundingRectsAll[i6].isEmpty()) {
                RotationUtils.rotateBounds(boundingRectsAll[i6], rect, iDeltaRotation);
            }
        }
        Collections.rotate(Arrays.asList(boundingRectsAll), getRotationToOverride(getSideOverride(this.mSideOverrides, i4), boundingRectsAll, -iDeltaRotation));
        CutoutPathParserInfo cutoutPathParserInfo = getCutoutPathParserInfo();
        CutoutPathParserInfo cutoutPathParserInfo2 = new CutoutPathParserInfo(cutoutPathParserInfo.getDisplayWidth(), cutoutPathParserInfo.getDisplayHeight(), cutoutPathParserInfo.getPhysicalDisplayWidth(), cutoutPathParserInfo.getPhysicalDisplayHeight(), cutoutPathParserInfo.getDensity(), cutoutPathParserInfo.getCutoutSpec(), i4, cutoutPathParserInfo.getScale(), cutoutPathParserInfo.getPhysicalPixelDisplaySizeRatio());
        boolean z = iDeltaRotation % 2 != 0;
        int i7 = z ? i2 : i5;
        if (!z) {
            i5 = i2;
        }
        DisplayCutout displayCutoutConstructDisplayCutout = constructDisplayCutout(boundingRectsAll, insetsRotateInsets, cutoutPathParserInfo2);
        Rect rectComputeSafeInsets = computeSafeInsets(i7, i5, displayCutoutConstructDisplayCutout);
        displayCutoutConstructDisplayCutout.mSideOverrides = this.mSideOverrides;
        return displayCutoutConstructDisplayCutout.replaceSafeInsets(rectComputeSafeInsets);
    }

    private static int getSideOverride(int[] iArr, int i) {
        if (iArr == null || iArr.length != 4) {
            return -1;
        }
        return iArr[i];
    }

    private static int getRotationToOverride(int i, Rect[] rectArr, int i2) {
        if (i != -1) {
            int i3 = 0;
            int i4 = -1;
            while (true) {
                if (i3 <= 3) {
                    if (!rectArr[i3].isEmpty()) {
                        if (i4 != -1) {
                            break;
                        }
                        i4 = i3;
                    }
                    i3++;
                } else if (i4 != -1) {
                    int i5 = i - i4;
                    return i5 < 0 ? i5 + 4 : i5;
                }
            }
        }
        return i2;
    }

    public static Rect computeSafeInsets(int i, int i2, DisplayCutout displayCutout) {
        return computeSafeInsets(i, i2, displayCutout.getWaterfallInsets(), displayCutout.getBoundingRectsAll());
    }

    private static Rect computeSafeInsets(int i, int i2, Insets insets, Rect[] rectArr) {
        return new Rect(Math.max(insets.left, findCutoutInsetForSide(i, i2, rectArr[0], 3)), Math.max(insets.top, findCutoutInsetForSide(i, i2, rectArr[1], 48)), Math.max(insets.right, findCutoutInsetForSide(i, i2, rectArr[2], 5)), Math.max(insets.bottom, findCutoutInsetForSide(i, i2, rectArr[3], 80)));
    }

    private static int findCutoutInsetForSide(int i, int i2, Rect rect, int i3) {
        if (rect.isEmpty()) {
            return 0;
        }
        if (i3 == 3) {
            return Math.max(0, rect.right);
        }
        if (i3 == 5) {
            return Math.max(0, i - rect.left);
        }
        if (i3 == 48) {
            return Math.max(0, rect.bottom);
        }
        if (i3 == 80) {
            return Math.max(0, i2 - rect.top);
        }
        throw new IllegalArgumentException("unknown gravity: " + i3);
    }

    public static final class ParcelableWrapper implements Parcelable {
        public static final Parcelable.Creator<ParcelableWrapper> CREATOR = new Parcelable.Creator<ParcelableWrapper>() { // from class: android.view.DisplayCutout.ParcelableWrapper.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ParcelableWrapper createFromParcel(Parcel parcel) {
                return new ParcelableWrapper(ParcelableWrapper.readCutoutFromParcel(parcel));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ParcelableWrapper[] newArray(int i) {
                return new ParcelableWrapper[i];
            }
        };
        private DisplayCutout mInner;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public ParcelableWrapper() {
            this(DisplayCutout.NO_CUTOUT);
        }

        public ParcelableWrapper(DisplayCutout displayCutout) {
            this.mInner = displayCutout;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            writeCutoutToParcel(this.mInner, parcel, i);
        }

        public static void writeCutoutToParcel(DisplayCutout displayCutout, Parcel parcel, int i) {
            if (displayCutout == null) {
                parcel.writeInt(-1);
                return;
            }
            if (displayCutout == DisplayCutout.NO_CUTOUT) {
                parcel.writeInt(0);
                return;
            }
            parcel.writeInt(1);
            parcel.writeTypedObject(displayCutout.mSafeInsets, i);
            parcel.writeTypedArray(displayCutout.mBounds.getRects(), i);
            parcel.writeTypedObject(displayCutout.mWaterfallInsets, i);
            parcel.writeInt(displayCutout.mCutoutPathParserInfo.getDisplayWidth());
            parcel.writeInt(displayCutout.mCutoutPathParserInfo.getDisplayHeight());
            parcel.writeInt(displayCutout.mCutoutPathParserInfo.getPhysicalDisplayWidth());
            parcel.writeInt(displayCutout.mCutoutPathParserInfo.getPhysicalDisplayHeight());
            parcel.writeFloat(displayCutout.mCutoutPathParserInfo.getDensity());
            parcel.writeString(displayCutout.mCutoutPathParserInfo.getCutoutSpec());
            parcel.writeInt(displayCutout.mCutoutPathParserInfo.getRotation());
            parcel.writeFloat(displayCutout.mCutoutPathParserInfo.getScale());
            parcel.writeFloat(displayCutout.mCutoutPathParserInfo.getPhysicalPixelDisplaySizeRatio());
            parcel.writeIntArray(displayCutout.mSideOverrides);
        }

        public void readFromParcel(Parcel parcel) {
            this.mInner = readCutoutFromParcel(parcel);
        }

        public static DisplayCutout readCutoutFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i == -1) {
                return null;
            }
            if (i == 0) {
                return DisplayCutout.NO_CUTOUT;
            }
            Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
            Rect[] rectArr = new Rect[4];
            parcel.readTypedArray(rectArr, Rect.CREATOR);
            return new DisplayCutout(rect, (Insets) parcel.readTypedObject(Insets.CREATOR), new Bounds(rectArr, false), new CutoutPathParserInfo(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readFloat(), parcel.readString(), parcel.readInt(), parcel.readFloat(), parcel.readFloat()), parcel.createIntArray());
        }

        public DisplayCutout get() {
            return this.mInner;
        }

        public void set(ParcelableWrapper parcelableWrapper) {
            this.mInner = parcelableWrapper.get();
        }

        public void set(DisplayCutout displayCutout) {
            this.mInner = displayCutout;
        }

        public void scale(float f) {
            Rect safeInsets = this.mInner.getSafeInsets();
            safeInsets.scale(f);
            Bounds bounds = new Bounds(this.mInner.mBounds.mRects, true);
            bounds.scale(f);
            Rect rect = this.mInner.mWaterfallInsets.toRect();
            rect.scale(f);
            this.mInner = new DisplayCutout(safeInsets, Insets.of(rect), bounds, new CutoutPathParserInfo(this.mInner.mCutoutPathParserInfo.getDisplayWidth(), this.mInner.mCutoutPathParserInfo.getDisplayHeight(), this.mInner.mCutoutPathParserInfo.getPhysicalDisplayWidth(), this.mInner.mCutoutPathParserInfo.getPhysicalDisplayHeight(), this.mInner.mCutoutPathParserInfo.getDensity(), this.mInner.mCutoutPathParserInfo.getCutoutSpec(), this.mInner.mCutoutPathParserInfo.getRotation(), f, this.mInner.mCutoutPathParserInfo.getPhysicalPixelDisplaySizeRatio()), this.mInner.mSideOverrides);
        }

        public int hashCode() {
            return this.mInner.hashCode();
        }

        public boolean equals(Object obj) {
            return (obj instanceof ParcelableWrapper) && this.mInner.equals(((ParcelableWrapper) obj).mInner);
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
            CutoutPathParserInfo cutoutPathParserInfo;
            if (this.mCutoutPath != null) {
                cutoutPathParserInfo = new CutoutPathParserInfo(0, 0, 0, 0, 0.0f, "test", 0, 1.0f, 1.0f);
                synchronized (DisplayCutout.CACHE_LOCK) {
                    DisplayCutout.sCachedCutoutPathParserInfo = cutoutPathParserInfo;
                    DisplayCutout.sCachedCutoutPath = this.mCutoutPath;
                }
            } else {
                cutoutPathParserInfo = null;
            }
            return new DisplayCutout(this.mSafeInsets.toRect(), this.mWaterfallInsets, this.mBoundingRectLeft, this.mBoundingRectTop, this.mBoundingRectRight, this.mBoundingRectBottom, cutoutPathParserInfo, false);
        }

        public Builder setSafeInsets(Insets insets) {
            this.mSafeInsets = insets;
            return this;
        }

        public Builder setWaterfallInsets(Insets insets) {
            this.mWaterfallInsets = insets;
            return this;
        }

        public Builder setBoundingRectLeft(Rect rect) {
            this.mBoundingRectLeft.set(rect);
            return this;
        }

        public Builder setBoundingRectTop(Rect rect) {
            this.mBoundingRectTop.set(rect);
            return this;
        }

        public Builder setBoundingRectRight(Rect rect) {
            this.mBoundingRectRight.set(rect);
            return this;
        }

        public Builder setBoundingRectBottom(Rect rect) {
            this.mBoundingRectBottom.set(rect);
            return this;
        }

        public Builder setCutoutPath(Path path) {
            this.mCutoutPath = path;
            return this;
        }
    }

    public static DisplayCutout fromResourcesRectApproximation(Resources resources, String str, int i, int i2, int i3, int i4, int i5, String str2) {
        return pathAndDisplayCutoutFromSpec(str2, null, i, i2, i3, i4, i5 / 160.0f, loadWaterfallInset(resources), getDisplayCutoutSideOverrides(resources, str)).second;
    }

    public static Path pathFromResourcesForUDC(Resources resources, String str, int i, int i2, int i3, boolean z) {
        return pathAndDisplayCutoutFromSpec(resources.getString(R.string.config_mainBuiltInDisplayCutoutForUDC), null, i, i2, i, i2, i3 / 160.0f, getWaterfallInsets(resources, str), getDisplayCutoutSideOverrides(resources, str)).first;
    }
}
