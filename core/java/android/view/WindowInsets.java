package android.view;

import android.content.pm.PackageManager;
import android.graphics.Insets;
import android.graphics.Rect;
import android.util.Size;

import com.android.internal.util.Preconditions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class WindowInsets {
    public static final WindowInsets CONSUMED =
            new WindowInsets(
                    createCompatTypeMap(null),
                    createCompatTypeMap(null),
                    createCompatVisibilityMap(createCompatTypeMap(null)),
                    false,
                    0,
                    false,
                    0,
                    null,
                    null,
                    null,
                    null,
                    Type.systemBars(),
                    false,
                    null,
                    null,
                    0,
                    0);
    private final boolean mCompatIgnoreVisibility;
    private int mCompatInsetsTypes;
    private final DisplayCutout mDisplayCutout;
    private final boolean mDisplayCutoutConsumed;
    private final DisplayShape mDisplayShape;
    private final boolean mForceConsumingOpaqueCaptionBar;
    private final int mForceConsumingTypes;
    private final int mFrameHeight;
    private final int mFrameWidth;
    private final boolean mIsRound;
    private final PrivacyIndicatorBounds mPrivacyIndicatorBounds;
    private final RoundedCorners mRoundedCorners;
    private final boolean mStableInsetsConsumed;
    private final int mSuppressScrimTypes;
    private final boolean mSystemWindowInsetsConsumed;
    private Rect mTempRect;
    private final Rect[][] mTypeBoundingRectsMap;
    private final Insets[] mTypeInsetsMap;
    private final Rect[][] mTypeMaxBoundingRectsMap;
    private final Insets[] mTypeMaxInsetsMap;
    private final boolean[] mTypeVisibilityMap;

    public WindowInsets(
            Insets[] typeInsetsMap,
            Insets[] typeMaxInsetsMap,
            boolean[] typeVisibilityMap,
            boolean isRound,
            int forceConsumingTypes,
            boolean forceConsumingOpaqueCaptionBar,
            int suppressScrimTypes,
            DisplayCutout displayCutout,
            RoundedCorners roundedCorners,
            PrivacyIndicatorBounds privacyIndicatorBounds,
            DisplayShape displayShape,
            int compatInsetsTypes,
            boolean compatIgnoreVisibility,
            Rect[][] typeBoundingRectsMap,
            Rect[][] typeMaxBoundingRectsMap,
            int frameWidth,
            int frameHeight) {
        Insets[] insetsArr;
        Insets[] insetsArr2;
        Rect[][] rectArr;
        Rect[][] rectArr2;
        this.mSystemWindowInsetsConsumed = typeInsetsMap == null;
        if (this.mSystemWindowInsetsConsumed) {
            insetsArr = new Insets[10];
        } else {
            insetsArr = (Insets[]) typeInsetsMap.clone();
        }
        this.mTypeInsetsMap = insetsArr;
        this.mStableInsetsConsumed = typeMaxInsetsMap == null;
        if (this.mStableInsetsConsumed) {
            insetsArr2 = new Insets[10];
        } else {
            insetsArr2 = (Insets[]) typeMaxInsetsMap.clone();
        }
        this.mTypeMaxInsetsMap = insetsArr2;
        this.mTypeVisibilityMap = typeVisibilityMap;
        this.mIsRound = isRound;
        this.mForceConsumingTypes = forceConsumingTypes;
        this.mForceConsumingOpaqueCaptionBar = forceConsumingOpaqueCaptionBar;
        this.mSuppressScrimTypes = suppressScrimTypes;
        this.mCompatInsetsTypes = compatInsetsTypes;
        this.mCompatIgnoreVisibility = compatIgnoreVisibility;
        this.mDisplayCutoutConsumed = displayCutout == null;
        this.mDisplayCutout =
                (this.mDisplayCutoutConsumed || displayCutout.isEmpty()) ? null : displayCutout;
        this.mRoundedCorners = roundedCorners;
        this.mPrivacyIndicatorBounds = privacyIndicatorBounds;
        this.mDisplayShape = displayShape;
        if (this.mSystemWindowInsetsConsumed || typeBoundingRectsMap == null) {
            rectArr = new Rect[10][];
        } else {
            rectArr = (Rect[][]) typeBoundingRectsMap.clone();
        }
        this.mTypeBoundingRectsMap = rectArr;
        if (this.mStableInsetsConsumed || typeMaxBoundingRectsMap == null) {
            rectArr2 = new Rect[10][];
        } else {
            rectArr2 = (Rect[][]) typeMaxBoundingRectsMap.clone();
        }
        this.mTypeMaxBoundingRectsMap = rectArr2;
        this.mFrameWidth = frameWidth;
        this.mFrameHeight = frameHeight;
    }

    public WindowInsets(WindowInsets src) {
        this(
                src.mSystemWindowInsetsConsumed ? null : src.mTypeInsetsMap,
                src.mStableInsetsConsumed ? null : src.mTypeMaxInsetsMap,
                src.mTypeVisibilityMap,
                src.mIsRound,
                src.mForceConsumingTypes,
                src.mForceConsumingOpaqueCaptionBar,
                src.mSuppressScrimTypes,
                displayCutoutCopyConstructorArgument(src),
                src.mRoundedCorners,
                src.mPrivacyIndicatorBounds,
                src.mDisplayShape,
                src.mCompatInsetsTypes,
                src.mCompatIgnoreVisibility,
                src.mSystemWindowInsetsConsumed ? null : src.mTypeBoundingRectsMap,
                src.mStableInsetsConsumed ? null : src.mTypeMaxBoundingRectsMap,
                src.mFrameWidth,
                src.mFrameHeight);
    }

    public static DisplayCutout displayCutoutCopyConstructorArgument(WindowInsets w) {
        if (w.mDisplayCutoutConsumed) {
            return null;
        }
        if (w.mDisplayCutout == null) {
            return DisplayCutout.NO_CUTOUT;
        }
        return w.mDisplayCutout;
    }

    static Insets getInsets(Insets[] typeInsetsMap, int typeMask) {
        Insets insets;
        Insets result = null;
        for (int i = 1; i <= 512; i <<= 1) {
            if ((typeMask & i) != 0 && (insets = typeInsetsMap[Type.indexOf(i)]) != null) {
                if (result == null) {
                    result = Insets.max(Insets.NONE, insets);
                } else {
                    result = Insets.max(result, insets);
                }
            }
        }
        return result == null ? Insets.NONE : result;
    }

    public static void setInsets(Insets[] typeInsetsMap, int typeMask, Insets insets) {
        for (int i = 1; i <= 512; i <<= 1) {
            if ((typeMask & i) != 0) {
                typeInsetsMap[Type.indexOf(i)] = insets;
            }
        }
    }

    public WindowInsets(Rect systemWindowInsets) {
        this(
                createCompatTypeMap(systemWindowInsets),
                null,
                new boolean[10],
                false,
                0,
                false,
                0,
                null,
                null,
                null,
                null,
                Type.systemBars(),
                false,
                new Rect[10][],
                null,
                0,
                0);
    }

    public static Insets[] createCompatTypeMap(Rect insets) {
        if (insets == null) {
            return null;
        }
        Insets[] typeInsetsMap = new Insets[10];
        assignCompatInsets(typeInsetsMap, insets);
        return typeInsetsMap;
    }

    public static void assignCompatInsets(Insets[] typeInsetsMap, Rect insets) {
        typeInsetsMap[Type.indexOf(1)] = Insets.of(0, insets.top, 0, 0);
        typeInsetsMap[Type.indexOf(2)] = Insets.of(insets.left, 0, insets.right, insets.bottom);
    }

    private static boolean[] createCompatVisibilityMap(Insets[] typeInsetsMap) {
        boolean[] typeVisibilityMap = new boolean[10];
        if (typeInsetsMap == null) {
            return typeVisibilityMap;
        }
        for (int i = 1; i <= 512; i <<= 1) {
            int index = Type.indexOf(i);
            if (!Insets.NONE.equals(typeInsetsMap[index])) {
                typeVisibilityMap[index] = true;
            }
        }
        return typeVisibilityMap;
    }

    @Deprecated
    public Rect getSystemWindowInsetsAsRect() {
        if (this.mTempRect == null) {
            this.mTempRect = new Rect();
        }
        Insets insets = getSystemWindowInsets();
        this.mTempRect.set(insets.left, insets.top, insets.right, insets.bottom);
        return this.mTempRect;
    }

    @Deprecated
    public Insets getSystemWindowInsets() {
        Insets result;
        if (this.mCompatIgnoreVisibility) {
            result = getInsetsIgnoringVisibility(this.mCompatInsetsTypes & (~Type.ime()));
        } else {
            result = getInsets(this.mCompatInsetsTypes);
        }
        if ((this.mCompatInsetsTypes & Type.ime()) != 0 && this.mCompatIgnoreVisibility) {
            return Insets.max(result, getInsets(Type.ime()));
        }
        return result;
    }

    public Insets getInsets(int typeMask) {
        return getInsets(this.mTypeInsetsMap, typeMask);
    }

    public Insets getInsetsIgnoringVisibility(int typeMask) {
        if ((typeMask & 8) != 0) {
            throw new IllegalArgumentException("Unable to query the maximum insets for IME");
        }
        return getInsets(this.mTypeMaxInsetsMap, typeMask);
    }

    public boolean isVisible(int typeMask) {
        for (int i = 1; i <= 512; i <<= 1) {
            if ((typeMask & i) != 0 && !this.mTypeVisibilityMap[Type.indexOf(i)]) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public int getSystemWindowInsetLeft() {
        return getSystemWindowInsets().left;
    }

    @Deprecated
    public int getSystemWindowInsetTop() {
        return getSystemWindowInsets().top;
    }

    @Deprecated
    public int getSystemWindowInsetRight() {
        return getSystemWindowInsets().right;
    }

    @Deprecated
    public int getSystemWindowInsetBottom() {
        return getSystemWindowInsets().bottom;
    }

    @Deprecated
    public boolean hasSystemWindowInsets() {
        return !getSystemWindowInsets().equals(Insets.NONE);
    }

    public boolean hasInsets() {
        return (getInsets(this.mTypeInsetsMap, Type.all()).equals(Insets.NONE)
                        && getInsets(this.mTypeMaxInsetsMap, Type.all()).equals(Insets.NONE)
                        && this.mDisplayCutout == null
                        && this.mRoundedCorners == null)
                ? false
                : true;
    }

    public List<Rect> getBoundingRects(int typeMask) {
        return getBoundingRects(this.mTypeBoundingRectsMap, typeMask);
    }

    public List<Rect> getBoundingRectsIgnoringVisibility(int typeMask) {
        if ((typeMask & 8) != 0) {
            throw new IllegalArgumentException("Unable to query the bounding rects for IME");
        }
        return getBoundingRects(this.mTypeMaxBoundingRectsMap, typeMask);
    }

    private List<Rect> getBoundingRects(Rect[][] typeBoundingRectsMap, int typeMask) {
        Rect[] rects;
        Rect[] allRects = null;
        for (int i = 1; i <= 512; i <<= 1) {
            if ((typeMask & i) != 0 && (rects = typeBoundingRectsMap[Type.indexOf(i)]) != null) {
                if (allRects == null) {
                    allRects = rects;
                } else {
                    Rect[] concat = new Rect[allRects.length + rects.length];
                    System.arraycopy(allRects, 0, concat, 0, allRects.length);
                    System.arraycopy(rects, 0, concat, allRects.length, rects.length);
                    allRects = concat;
                }
            }
        }
        if (allRects == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(allRects);
    }

    public DisplayCutout getDisplayCutout() {
        return this.mDisplayCutout;
    }

    public RoundedCorner getRoundedCorner(int position) {
        if (this.mRoundedCorners == null) {
            return null;
        }
        return this.mRoundedCorners.getRoundedCorner(position);
    }

    public Rect getPrivacyIndicatorBounds() {
        if (this.mPrivacyIndicatorBounds == null) {
            return null;
        }
        return this.mPrivacyIndicatorBounds.getStaticPrivacyIndicatorBounds();
    }

    public DisplayShape getDisplayShape() {
        return this.mDisplayShape;
    }

    @Deprecated
    public WindowInsets consumeDisplayCutout() {
        return new WindowInsets(
                this.mSystemWindowInsetsConsumed ? null : this.mTypeInsetsMap,
                this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap,
                this.mTypeVisibilityMap,
                this.mIsRound,
                this.mForceConsumingTypes,
                this.mForceConsumingOpaqueCaptionBar,
                this.mSuppressScrimTypes,
                null,
                this.mRoundedCorners,
                this.mPrivacyIndicatorBounds,
                this.mDisplayShape,
                this.mCompatInsetsTypes,
                this.mCompatIgnoreVisibility,
                this.mSystemWindowInsetsConsumed ? null : this.mTypeBoundingRectsMap,
                this.mStableInsetsConsumed ? null : this.mTypeMaxBoundingRectsMap,
                this.mFrameWidth,
                this.mFrameHeight);
    }

    public boolean isConsumed() {
        return this.mSystemWindowInsetsConsumed
                && this.mStableInsetsConsumed
                && this.mDisplayCutoutConsumed;
    }

    public boolean isRound() {
        return this.mIsRound;
    }

    @Deprecated
    public WindowInsets consumeSystemWindowInsets() {
        return new WindowInsets(
                null,
                null,
                this.mTypeVisibilityMap,
                this.mIsRound,
                this.mForceConsumingTypes,
                this.mForceConsumingOpaqueCaptionBar,
                this.mSuppressScrimTypes,
                (this.mCompatInsetsTypes & Type.displayCutout()) != 0
                        ? null
                        : displayCutoutCopyConstructorArgument(this),
                this.mRoundedCorners,
                this.mPrivacyIndicatorBounds,
                this.mDisplayShape,
                this.mCompatInsetsTypes,
                this.mCompatIgnoreVisibility,
                null,
                null,
                this.mFrameWidth,
                this.mFrameHeight);
    }

    @Deprecated
    public WindowInsets replaceSystemWindowInsets(int left, int top, int right, int bottom) {
        if (this.mSystemWindowInsetsConsumed) {
            return this;
        }
        return new Builder(this).setSystemWindowInsets(Insets.of(left, top, right, bottom)).build();
    }

    @Deprecated
    public WindowInsets replaceSystemWindowInsets(Rect systemWindowInsets) {
        return replaceSystemWindowInsets(
                systemWindowInsets.left,
                systemWindowInsets.top,
                systemWindowInsets.right,
                systemWindowInsets.bottom);
    }

    @Deprecated
    public Insets getStableInsets() {
        return getInsets(this.mTypeMaxInsetsMap, Type.systemBars());
    }

    @Deprecated
    public int getStableInsetTop() {
        return getStableInsets().top;
    }

    @Deprecated
    public int getStableInsetLeft() {
        return getStableInsets().left;
    }

    @Deprecated
    public int getStableInsetRight() {
        return getStableInsets().right;
    }

    @Deprecated
    public int getStableInsetBottom() {
        return getStableInsets().bottom;
    }

    @Deprecated
    public boolean hasStableInsets() {
        return !getStableInsets().equals(Insets.NONE);
    }

    @Deprecated
    public Insets getSystemGestureInsets() {
        return getInsets(this.mTypeInsetsMap, 16);
    }

    @Deprecated
    public Insets getMandatorySystemGestureInsets() {
        return getInsets(this.mTypeInsetsMap, 32);
    }

    @Deprecated
    public Insets getTappableElementInsets() {
        return getInsets(this.mTypeInsetsMap, 64);
    }

    @Deprecated
    public WindowInsets consumeStableInsets() {
        return this;
    }

    public int getForceConsumingTypes() {
        return this.mForceConsumingTypes;
    }

    public boolean isForceConsumingOpaqueCaptionBar() {
        return this.mForceConsumingOpaqueCaptionBar;
    }

    public int getSuppressScrimTypes() {
        return this.mSuppressScrimTypes;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("WindowInsets{\n    ");
        for (int i = 0; i < 10; i++) {
            Insets insets = this.mTypeInsetsMap[i];
            Insets maxInsets = this.mTypeMaxInsetsMap[i];
            boolean visible = this.mTypeVisibilityMap[i];
            if (!Insets.NONE.equals(insets) || !Insets.NONE.equals(maxInsets) || visible) {
                result.append(Type.toString(1 << i))
                        .append("=")
                        .append(insets)
                        .append(" max=")
                        .append(maxInsets)
                        .append(" vis=")
                        .append(visible)
                        .append(" boundingRects=")
                        .append(Arrays.toString(this.mTypeBoundingRectsMap[i]))
                        .append(" maxBoundingRects=")
                        .append(Arrays.toString(this.mTypeMaxBoundingRectsMap[i]))
                        .append("\n    ");
            }
        }
        result.append(this.mDisplayCutout != null ? "cutout=" + this.mDisplayCutout : "");
        result.append("\n    ");
        result.append(this.mRoundedCorners != null ? "roundedCorners=" + this.mRoundedCorners : "");
        result.append("\n    ");
        result.append(
                this.mPrivacyIndicatorBounds != null
                        ? "privacyIndicatorBounds=" + this.mPrivacyIndicatorBounds
                        : "");
        result.append("\n    ");
        result.append(this.mDisplayShape != null ? "displayShape=" + this.mDisplayShape : "");
        result.append("\n    ");
        result.append("forceConsumingTypes=" + Type.toString(this.mForceConsumingTypes));
        result.append("\n    ");
        result.append("\n    ");
        result.append("forceConsumingOpaqueCaptionBar=" + this.mForceConsumingOpaqueCaptionBar);
        result.append("\n    ");
        result.append("suppressScrimTypes=" + Type.toString(this.mSuppressScrimTypes));
        result.append("\n    ");
        result.append("compatInsetsTypes=" + Type.toString(this.mCompatInsetsTypes));
        result.append("\n    ");
        result.append("compatIgnoreVisibility=" + this.mCompatIgnoreVisibility);
        result.append("\n    ");
        result.append("systemWindowInsetsConsumed=" + this.mSystemWindowInsetsConsumed);
        result.append("\n    ");
        result.append("stableInsetsConsumed=" + this.mStableInsetsConsumed);
        result.append("\n    ");
        result.append("displayCutoutConsumed=" + this.mDisplayCutoutConsumed);
        result.append("\n    ");
        result.append(isRound() ? "round" : "");
        result.append("\n    ");
        result.append("frameWidth=" + this.mFrameWidth);
        result.append("\n    ");
        result.append("frameHeight=" + this.mFrameHeight);
        result.append("}");
        return result.toString();
    }

    @Deprecated
    public WindowInsets inset(Rect r) {
        return inset(r.left, r.top, r.right, r.bottom);
    }

    public WindowInsets inset(Insets insets) {
        Objects.requireNonNull(insets);
        return inset(insets.left, insets.top, insets.right, insets.bottom);
    }

    public WindowInsets inset(int left, int top, int right, int bottom) {
        Preconditions.checkArgumentNonnegative(left);
        Preconditions.checkArgumentNonnegative(top);
        Preconditions.checkArgumentNonnegative(right);
        Preconditions.checkArgumentNonnegative(bottom);
        return insetUnchecked(left, top, right, bottom);
    }

    public Size getFrame() {
        return new Size(this.mFrameWidth, this.mFrameHeight);
    }

    public WindowInsets insetUnchecked(int left, int top, int right, int bottom) {
        Insets[] insetInsets;
        Insets[] insetInsets2;
        DisplayCutout inset;
        RoundedCorners inset2;
        PrivacyIndicatorBounds inset3;
        int i;
        boolean z;
        boolean z2;
        int i2;
        DisplayShape displayShape;
        int i3;
        boolean z3;
        Rect[][] insetBoundingRects;
        Rect[][] insetBoundingRects2;
        if (this.mSystemWindowInsetsConsumed) {
            insetInsets = null;
        } else {
            insetInsets = insetInsets(this.mTypeInsetsMap, left, top, right, bottom);
        }
        if (this.mStableInsetsConsumed) {
            insetInsets2 = null;
        } else {
            insetInsets2 = insetInsets(this.mTypeMaxInsetsMap, left, top, right, bottom);
        }
        boolean[] zArr = this.mTypeVisibilityMap;
        boolean z4 = this.mIsRound;
        int i4 = this.mForceConsumingTypes;
        boolean z5 = this.mForceConsumingOpaqueCaptionBar;
        int i5 = this.mSuppressScrimTypes;
        if (this.mDisplayCutoutConsumed) {
            inset = null;
        } else if (this.mDisplayCutout == null) {
            inset = DisplayCutout.NO_CUTOUT;
        } else {
            inset = this.mDisplayCutout.inset(left, top, right, bottom);
        }
        if (this.mRoundedCorners == null) {
            inset2 = RoundedCorners.NO_ROUNDED_CORNERS;
        } else {
            inset2 = this.mRoundedCorners.inset(left, top, right, bottom);
        }
        if (this.mPrivacyIndicatorBounds == null) {
            inset3 = null;
        } else {
            inset3 = this.mPrivacyIndicatorBounds.inset(left, top, right, bottom);
        }
        DisplayShape displayShape2 = this.mDisplayShape;
        int i6 = this.mCompatInsetsTypes;
        boolean z6 = this.mCompatIgnoreVisibility;
        if (!this.mSystemWindowInsetsConsumed) {
            i = i4;
            z = z4;
            z2 = z6;
            i2 = i6;
            displayShape = displayShape2;
            i3 = i5;
            z3 = z5;
            insetBoundingRects =
                    insetBoundingRects(
                            this.mTypeBoundingRectsMap,
                            left,
                            top,
                            right,
                            bottom,
                            this.mFrameWidth,
                            this.mFrameHeight);
        } else {
            z2 = z6;
            i2 = i6;
            displayShape = displayShape2;
            i3 = i5;
            z3 = z5;
            i = i4;
            z = z4;
            insetBoundingRects = null;
        }
        if (!this.mStableInsetsConsumed) {
            insetBoundingRects2 =
                    insetBoundingRects(
                            this.mTypeMaxBoundingRectsMap,
                            left,
                            top,
                            right,
                            bottom,
                            this.mFrameWidth,
                            this.mFrameHeight);
        } else {
            insetBoundingRects2 = null;
        }
        return new WindowInsets(
                insetInsets,
                insetInsets2,
                zArr,
                z,
                i,
                z3,
                i3,
                inset,
                inset2,
                inset3,
                displayShape,
                i2,
                z2,
                insetBoundingRects,
                insetBoundingRects2,
                Math.max(0, (this.mFrameWidth - left) - right),
                Math.max(0, (this.mFrameHeight - top) - bottom));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof WindowInsets)) {
            return false;
        }
        WindowInsets that = (WindowInsets) o;
        if (this.mIsRound == that.mIsRound
                && this.mForceConsumingTypes == that.mForceConsumingTypes
                && this.mForceConsumingOpaqueCaptionBar == that.mForceConsumingOpaqueCaptionBar
                && this.mSuppressScrimTypes == that.mSuppressScrimTypes
                && this.mSystemWindowInsetsConsumed == that.mSystemWindowInsetsConsumed
                && this.mStableInsetsConsumed == that.mStableInsetsConsumed
                && this.mDisplayCutoutConsumed == that.mDisplayCutoutConsumed
                && Arrays.equals(this.mTypeInsetsMap, that.mTypeInsetsMap)
                && Arrays.equals(this.mTypeMaxInsetsMap, that.mTypeMaxInsetsMap)
                && Arrays.equals(this.mTypeVisibilityMap, that.mTypeVisibilityMap)
                && Objects.equals(this.mDisplayCutout, that.mDisplayCutout)
                && Objects.equals(this.mRoundedCorners, that.mRoundedCorners)
                && Objects.equals(this.mPrivacyIndicatorBounds, that.mPrivacyIndicatorBounds)
                && Objects.equals(this.mDisplayShape, that.mDisplayShape)
                && Arrays.deepEquals(this.mTypeBoundingRectsMap, that.mTypeBoundingRectsMap)
                && Arrays.deepEquals(this.mTypeMaxBoundingRectsMap, that.mTypeMaxBoundingRectsMap)
                && this.mFrameWidth == that.mFrameWidth
                && this.mFrameHeight == that.mFrameHeight) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(Arrays.hashCode(this.mTypeInsetsMap)),
                Integer.valueOf(Arrays.hashCode(this.mTypeMaxInsetsMap)),
                Integer.valueOf(Arrays.hashCode(this.mTypeVisibilityMap)),
                Boolean.valueOf(this.mIsRound),
                this.mDisplayCutout,
                this.mRoundedCorners,
                Integer.valueOf(this.mForceConsumingTypes),
                Boolean.valueOf(this.mForceConsumingOpaqueCaptionBar),
                Integer.valueOf(this.mSuppressScrimTypes),
                Boolean.valueOf(this.mSystemWindowInsetsConsumed),
                Boolean.valueOf(this.mStableInsetsConsumed),
                Boolean.valueOf(this.mDisplayCutoutConsumed),
                this.mPrivacyIndicatorBounds,
                this.mDisplayShape,
                Integer.valueOf(Arrays.deepHashCode(this.mTypeBoundingRectsMap)),
                Integer.valueOf(Arrays.deepHashCode(this.mTypeMaxBoundingRectsMap)),
                Integer.valueOf(this.mFrameWidth),
                Integer.valueOf(this.mFrameHeight));
    }

    private static Insets[] insetInsets(
            Insets[] typeInsetsMap, int left, int top, int right, int bottom) {
        Insets insetInsets;
        boolean cloned = false;
        for (int i = 0; i < 10; i++) {
            Insets insets = typeInsetsMap[i];
            if (insets != null
                    && (insetInsets = insetInsets(insets, left, top, right, bottom)) != insets) {
                if (!cloned) {
                    typeInsetsMap = (Insets[]) typeInsetsMap.clone();
                    cloned = true;
                }
                typeInsetsMap[i] = insetInsets;
            }
        }
        return typeInsetsMap;
    }

    static Insets insetInsets(Insets insets, int left, int top, int right, int bottom) {
        int newLeft = Math.max(0, insets.left - left);
        int newTop = Math.max(0, insets.top - top);
        int newRight = Math.max(0, insets.right - right);
        int newBottom = Math.max(0, insets.bottom - bottom);
        if (newLeft == left && newTop == top && newRight == right && newBottom == bottom) {
            return insets;
        }
        return Insets.of(newLeft, newTop, newRight, newBottom);
    }

    static Rect[][] insetBoundingRects(
            Rect[][] typeBoundingRectsMap,
            int insetLeft,
            int insetTop,
            int insetRight,
            int insetBottom,
            int frameWidth,
            int frameHeight) {
        if (insetLeft == 0 && insetTop == 0 && insetRight == 0 && insetBottom == 0) {
            return typeBoundingRectsMap;
        }
        Rect[][] typeBoundingRectsMap2 = typeBoundingRectsMap;
        boolean cloned = false;
        for (int i = 0; i < 10; i++) {
            Rect[] boundingRects = typeBoundingRectsMap2[i];
            if (boundingRects != null) {
                Rect[] insetBoundingRects =
                        insetBoundingRects(
                                boundingRects,
                                insetLeft,
                                insetTop,
                                insetRight,
                                insetBottom,
                                frameWidth,
                                frameHeight);
                if (!Arrays.equals(insetBoundingRects, boundingRects)) {
                    if (!cloned) {
                        typeBoundingRectsMap2 = (Rect[][]) typeBoundingRectsMap2.clone();
                        cloned = true;
                    }
                    typeBoundingRectsMap2[i] = insetBoundingRects;
                }
            }
        }
        return typeBoundingRectsMap2;
    }

    static Rect[] insetBoundingRects(
            Rect[] boundingRects,
            int left,
            int top,
            int right,
            int bottom,
            int frameWidth,
            int frameHeight) {
        List<Rect> insetBoundingRectsList = new ArrayList<>();
        for (Rect rect : boundingRects) {
            Rect insetRect = insetRect(rect, left, top, right, bottom, frameWidth, frameHeight);
            if (insetRect != null) {
                insetBoundingRectsList.add(insetRect);
            }
        }
        return (Rect[]) insetBoundingRectsList.toArray(new Rect[0]);
    }

    private static Rect insetRect(
            Rect orig,
            int insetLeft,
            int insetTop,
            int insetRight,
            int insetBottom,
            int frameWidth,
            int frameHeight) {
        if (orig == null) {
            return null;
        }
        Rect insetFrame =
                new Rect(insetLeft, insetTop, frameWidth - insetRight, frameHeight - insetBottom);
        Rect insetRect = new Rect();
        if (!insetRect.setIntersect(insetFrame, orig)) {
            return null;
        }
        insetRect.offset(-insetLeft, -insetTop);
        return insetRect;
    }

    boolean isSystemWindowInsetsConsumed() {
        return this.mSystemWindowInsetsConsumed;
    }

    public static final class Builder {
        private DisplayCutout mDisplayCutout;
        private DisplayShape mDisplayShape;
        private boolean mForceConsumingOpaqueCaptionBar;
        private int mForceConsumingTypes;
        private int mFrameHeight;
        private int mFrameWidth;
        private boolean mIsRound;
        private PrivacyIndicatorBounds mPrivacyIndicatorBounds;
        private RoundedCorners mRoundedCorners;
        private boolean mStableInsetsConsumed;
        private int mSuppressScrimTypes;
        private boolean mSystemInsetsConsumed;
        private final Rect[][] mTypeBoundingRectsMap;
        private final Insets[] mTypeInsetsMap;
        private final Rect[][] mTypeMaxBoundingRectsMap;
        private final Insets[] mTypeMaxInsetsMap;
        private final boolean[] mTypeVisibilityMap;

        public Builder() {
            this.mSystemInsetsConsumed = true;
            this.mStableInsetsConsumed = true;
            this.mRoundedCorners = RoundedCorners.NO_ROUNDED_CORNERS;
            this.mDisplayShape = DisplayShape.NONE;
            this.mPrivacyIndicatorBounds = new PrivacyIndicatorBounds();
            this.mTypeInsetsMap = new Insets[10];
            this.mTypeMaxInsetsMap = new Insets[10];
            this.mTypeVisibilityMap = new boolean[10];
            this.mTypeBoundingRectsMap = new Rect[10][];
            this.mTypeMaxBoundingRectsMap = new Rect[10][];
        }

        public Builder(WindowInsets insets) {
            this.mSystemInsetsConsumed = true;
            this.mStableInsetsConsumed = true;
            this.mRoundedCorners = RoundedCorners.NO_ROUNDED_CORNERS;
            this.mDisplayShape = DisplayShape.NONE;
            this.mPrivacyIndicatorBounds = new PrivacyIndicatorBounds();
            this.mTypeInsetsMap = (Insets[]) insets.mTypeInsetsMap.clone();
            this.mTypeMaxInsetsMap = (Insets[]) insets.mTypeMaxInsetsMap.clone();
            this.mTypeVisibilityMap = (boolean[]) insets.mTypeVisibilityMap.clone();
            this.mSystemInsetsConsumed = insets.mSystemWindowInsetsConsumed;
            this.mStableInsetsConsumed = insets.mStableInsetsConsumed;
            this.mDisplayCutout = WindowInsets.displayCutoutCopyConstructorArgument(insets);
            this.mRoundedCorners = insets.mRoundedCorners;
            this.mIsRound = insets.mIsRound;
            this.mForceConsumingTypes = insets.mForceConsumingTypes;
            this.mForceConsumingOpaqueCaptionBar = insets.mForceConsumingOpaqueCaptionBar;
            this.mSuppressScrimTypes = insets.mSuppressScrimTypes;
            this.mPrivacyIndicatorBounds = insets.mPrivacyIndicatorBounds;
            this.mDisplayShape = insets.mDisplayShape;
            this.mTypeBoundingRectsMap = (Rect[][]) insets.mTypeBoundingRectsMap.clone();
            this.mTypeMaxBoundingRectsMap = (Rect[][]) insets.mTypeMaxBoundingRectsMap.clone();
            this.mFrameWidth = insets.mFrameWidth;
            this.mFrameHeight = insets.mFrameHeight;
        }

        @Deprecated
        public Builder setSystemWindowInsets(Insets systemWindowInsets) {
            Preconditions.checkNotNull(systemWindowInsets);
            WindowInsets.assignCompatInsets(this.mTypeInsetsMap, systemWindowInsets.toRect());
            this.mSystemInsetsConsumed = false;
            return this;
        }

        @Deprecated
        public Builder setSystemGestureInsets(Insets insets) {
            WindowInsets.setInsets(this.mTypeInsetsMap, 16, insets);
            return this;
        }

        @Deprecated
        public Builder setMandatorySystemGestureInsets(Insets insets) {
            WindowInsets.setInsets(this.mTypeInsetsMap, 32, insets);
            return this;
        }

        @Deprecated
        public Builder setTappableElementInsets(Insets insets) {
            WindowInsets.setInsets(this.mTypeInsetsMap, 64, insets);
            return this;
        }

        public Builder setInsets(int typeMask, Insets insets) {
            Preconditions.checkNotNull(insets);
            WindowInsets.setInsets(this.mTypeInsetsMap, typeMask, insets);
            this.mSystemInsetsConsumed = false;
            return this;
        }

        public Builder setInsetsIgnoringVisibility(int typeMask, Insets insets)
                throws IllegalArgumentException {
            if (typeMask == 8) {
                throw new IllegalArgumentException("Maximum inset not available for IME");
            }
            Preconditions.checkNotNull(insets);
            WindowInsets.setInsets(this.mTypeMaxInsetsMap, typeMask, insets);
            this.mStableInsetsConsumed = false;
            return this;
        }

        public Builder setVisible(int typeMask, boolean visible) {
            for (int i = 1; i <= 512; i <<= 1) {
                if ((typeMask & i) != 0) {
                    this.mTypeVisibilityMap[Type.indexOf(i)] = visible;
                }
            }
            return this;
        }

        @Deprecated
        public Builder setStableInsets(Insets stableInsets) {
            Preconditions.checkNotNull(stableInsets);
            WindowInsets.assignCompatInsets(this.mTypeMaxInsetsMap, stableInsets.toRect());
            this.mStableInsetsConsumed = false;
            return this;
        }

        public Builder setDisplayCutout(DisplayCutout displayCutout) {
            this.mDisplayCutout = displayCutout != null ? displayCutout : DisplayCutout.NO_CUTOUT;
            if (!this.mDisplayCutout.isEmpty()) {
                Insets safeInsets = Insets.of(this.mDisplayCutout.getSafeInsets());
                int index = Type.indexOf(128);
                this.mTypeInsetsMap[index] = safeInsets;
                this.mTypeMaxInsetsMap[index] = safeInsets;
                this.mTypeVisibilityMap[index] = true;
            }
            return this;
        }

        public Builder setRoundedCorners(RoundedCorners roundedCorners) {
            this.mRoundedCorners =
                    roundedCorners != null ? roundedCorners : RoundedCorners.NO_ROUNDED_CORNERS;
            return this;
        }

        public Builder setRoundedCorner(int position, RoundedCorner roundedCorner) {
            this.mRoundedCorners.setRoundedCorner(position, roundedCorner);
            return this;
        }

        public Builder setPrivacyIndicatorBounds(PrivacyIndicatorBounds bounds) {
            this.mPrivacyIndicatorBounds = bounds;
            return this;
        }

        public Builder setPrivacyIndicatorBounds(Rect bounds) {
            Rect[] boundsArr = {bounds, bounds, bounds, bounds};
            this.mPrivacyIndicatorBounds = new PrivacyIndicatorBounds(boundsArr, 0);
            return this;
        }

        public Builder setDisplayShape(DisplayShape displayShape) {
            this.mDisplayShape = displayShape;
            return this;
        }

        public Builder setRound(boolean round) {
            this.mIsRound = round;
            return this;
        }

        public Builder setAlwaysConsumeSystemBars(boolean alwaysConsumeSystemBars) {
            return this;
        }

        public Builder setForceConsumingTypes(int forceConsumingTypes) {
            this.mForceConsumingTypes = forceConsumingTypes;
            return this;
        }

        public Builder setForceConsumingOpaqueCaptionBar(boolean forceConsumingOpaqueCaptionBar) {
            this.mForceConsumingOpaqueCaptionBar = forceConsumingOpaqueCaptionBar;
            return this;
        }

        public Builder setSuppressScrimTypes(int suppressScrimTypes) {
            this.mSuppressScrimTypes = suppressScrimTypes;
            return this;
        }

        public Builder setBoundingRects(int typeMask, List<Rect> rects) {
            for (int i = 1; i <= 512; i <<= 1) {
                if ((typeMask & i) != 0) {
                    this.mTypeBoundingRectsMap[Type.indexOf(i)] =
                            (Rect[]) rects.toArray(new Rect[0]);
                }
            }
            return this;
        }

        public Builder setBoundingRectsIgnoringVisibility(int typeMask, List<Rect> rects) {
            if (typeMask == 8) {
                throw new IllegalArgumentException("Maximum bounding rects not available for IME");
            }
            for (int i = 1; i <= 512; i <<= 1) {
                if ((typeMask & i) != 0) {
                    this.mTypeMaxBoundingRectsMap[Type.indexOf(i)] =
                            (Rect[]) rects.toArray(new Rect[0]);
                }
            }
            return this;
        }

        public Builder setFrame(int width, int height) {
            this.mFrameWidth = width;
            this.mFrameHeight = height;
            return this;
        }

        public WindowInsets build() {
            return new WindowInsets(
                    this.mSystemInsetsConsumed ? null : this.mTypeInsetsMap,
                    this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap,
                    this.mTypeVisibilityMap,
                    this.mIsRound,
                    this.mForceConsumingTypes,
                    this.mForceConsumingOpaqueCaptionBar,
                    this.mSuppressScrimTypes,
                    this.mDisplayCutout,
                    this.mRoundedCorners,
                    this.mPrivacyIndicatorBounds,
                    this.mDisplayShape,
                    Type.systemBars(),
                    false,
                    this.mSystemInsetsConsumed ? null : this.mTypeBoundingRectsMap,
                    this.mStableInsetsConsumed ? null : this.mTypeMaxBoundingRectsMap,
                    this.mFrameWidth,
                    this.mFrameHeight);
        }
    }

    public static final class Type {
        static final int CAPTION_BAR = 4;
        static final int DEFAULT_VISIBLE = -9;
        static final int DISPLAY_CUTOUT = 128;
        static final int FIRST = 1;
        static final int IME = 8;
        static final int LAST = 512;
        static final int MANDATORY_SYSTEM_GESTURES = 32;
        static final int NAVIGATION_BARS = 2;
        static final int SIZE = 10;
        static final int STATUS_BARS = 1;
        static final int SYSTEM_GESTURES = 16;
        static final int SYSTEM_OVERLAYS = 512;
        static final int TAPPABLE_ELEMENT = 64;
        static final int WINDOW_DECOR = 256;

        @Retention(RetentionPolicy.SOURCE)
        public @interface InsetsType {}

        static int indexOf(int type) {
            switch (type) {
                case 1:
                    return 0;
                case 2:
                    return 1;
                case 4:
                    return 2;
                case 8:
                    return 3;
                case 16:
                    return 4;
                case 32:
                    return 5;
                case 64:
                    return 6;
                case 128:
                    return 7;
                case 256:
                    return 8;
                case 512:
                    return 9;
                default:
                    throw new IllegalArgumentException(
                            "type needs to be >= FIRST and <= LAST, type=" + type);
            }
        }

        public static String toString(int types) {
            StringBuilder result = new StringBuilder();
            if ((types & 1) != 0) {
                result.append("statusBars ");
            }
            if ((types & 2) != 0) {
                result.append("navigationBars ");
            }
            if ((types & 4) != 0) {
                result.append("captionBar ");
            }
            if ((types & 8) != 0) {
                result.append("ime ");
            }
            if ((types & 16) != 0) {
                result.append("systemGestures ");
            }
            if ((types & 32) != 0) {
                result.append("mandatorySystemGestures ");
            }
            if ((types & 64) != 0) {
                result.append("tappableElement ");
            }
            if ((types & 128) != 0) {
                result.append("displayCutout ");
            }
            if ((types & 256) != 0) {
                result.append("windowDecor ");
            }
            if ((types & 512) != 0) {
                result.append("systemOverlays ");
            }
            if (result.length() > 0) {
                result.delete(result.length() - 1, result.length());
            }
            return result.toString();
        }

        private Type() {}

        public static int statusBars() {
            return 1;
        }

        public static int navigationBars() {
            return 2;
        }

        public static int captionBar() {
            return 4;
        }

        public static int ime() {
            return 8;
        }

        public static int systemGestures() {
            return 16;
        }

        public static int mandatorySystemGestures() {
            return 32;
        }

        public static int tappableElement() {
            return 64;
        }

        public static int displayCutout() {
            return 128;
        }

        public static int systemOverlays() {
            return 512;
        }

        public static int systemBars() {
            return 519;
        }

        public static int systemBarsWithoutCaptionBar() {
            return 515;
        }

        public static int defaultVisible() {
            return -9;
        }

        public static int all() {
            return -1;
        }

        public static boolean hasCompatSystemBars(int types) {
            return (types & 3) != 0;
        }
    }

    public static final class Side {
        public static final int BOTTOM = 8;
        public static final int LEFT = 1;
        public static final int RIGHT = 4;
        public static final int TOP = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface InsetsSide {}

        private Side() {}

        public static int all() {
            return 15;
        }
    }

    public WindowInsets removeCutoutInsets() {
        this.mCompatInsetsTypes &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        WindowInsets insets =
                new WindowInsets(
                        this.mSystemWindowInsetsConsumed ? null : this.mTypeInsetsMap,
                        this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap,
                        this.mTypeVisibilityMap,
                        this.mIsRound,
                        this.mForceConsumingTypes,
                        this.mForceConsumingOpaqueCaptionBar,
                        this.mSuppressScrimTypes,
                        DisplayCutout.NO_CUTOUT,
                        this.mRoundedCorners,
                        this.mPrivacyIndicatorBounds,
                        this.mDisplayShape,
                        this.mCompatInsetsTypes,
                        this.mCompatIgnoreVisibility,
                        this.mTypeBoundingRectsMap,
                        this.mTypeMaxBoundingRectsMap,
                        this.mFrameWidth,
                        this.mFrameHeight);
        int index = Type.indexOf(128);
        insets.mTypeInsetsMap[index] = null;
        insets.mTypeMaxInsetsMap[index] = null;
        insets.mTypeVisibilityMap[index] = false;
        return insets;
    }

    public WindowInsets consumeCaptionInsets() {
        this.mCompatInsetsTypes &= -5;
        WindowInsets insets =
                new WindowInsets(
                        this.mSystemWindowInsetsConsumed ? null : this.mTypeInsetsMap,
                        this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap,
                        this.mTypeVisibilityMap,
                        this.mIsRound,
                        this.mForceConsumingTypes,
                        this.mForceConsumingOpaqueCaptionBar,
                        this.mSuppressScrimTypes,
                        this.mDisplayCutout,
                        this.mRoundedCorners,
                        this.mPrivacyIndicatorBounds,
                        this.mDisplayShape,
                        this.mCompatInsetsTypes,
                        this.mCompatIgnoreVisibility,
                        this.mTypeBoundingRectsMap,
                        this.mTypeMaxBoundingRectsMap,
                        this.mFrameWidth,
                        this.mFrameHeight);
        int index = Type.indexOf(4);
        insets.mTypeInsetsMap[index] = null;
        insets.mTypeMaxInsetsMap[index] = null;
        insets.mTypeVisibilityMap[index] = false;
        return insets;
    }
}
