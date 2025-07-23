package android.view;

import android.content.pm.PackageManager;
import android.graphics.Insets;
import android.graphics.Rect;
import android.util.Size;
import com.android.internal.util.Preconditions;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class WindowInsets {
    public static final WindowInsets CONSUMED = new WindowInsets(createCompatTypeMap(null), createCompatTypeMap(null), createCompatVisibilityMap(createCompatTypeMap(null)), false, 0, false, -1, 0, null, null, null, null, Type.systemBars(), false, null, null, 0, 0);
    private final boolean mCompatIgnoreVisibility;
    private int mCompatInsetsTypes;
    private int mConsumedCaptionType;
    private final DisplayCutout mDisplayCutout;
    private final boolean mDisplayCutoutConsumed;
    private final DisplayCutout mDisplayCutoutForUdc;
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

    @Deprecated
    public WindowInsets consumeStableInsets() {
        return this;
    }

    public WindowInsets(Insets[] insetsArr, Insets[] insetsArr2, boolean[] zArr, boolean z, int i, boolean z2, int i2, int i3, DisplayCutout displayCutout, RoundedCorners roundedCorners, PrivacyIndicatorBounds privacyIndicatorBounds, DisplayShape displayShape, int i4, boolean z3, Rect[][] rectArr, Rect[][] rectArr2, int i5, int i6) {
        this(insetsArr, insetsArr2, zArr, z, i, z2, i2, i3, displayCutout, roundedCorners, privacyIndicatorBounds, displayShape, i4, z3, rectArr, rectArr2, i5, i6, null);
    }

    public WindowInsets(Insets[] insetsArr, Insets[] insetsArr2, boolean[] zArr, boolean z, int i, boolean z2, int i2, int i3, DisplayCutout displayCutout, RoundedCorners roundedCorners, PrivacyIndicatorBounds privacyIndicatorBounds, DisplayShape displayShape, int i4, boolean z3, Rect[][] rectArr, Rect[][] rectArr2, int i5, int i6, DisplayCutout displayCutout2) {
        Insets[] insetsArr3;
        Insets[] insetsArr4;
        Rect[][] rectArr3;
        Rect[][] rectArr4;
        this.mConsumedCaptionType = -1;
        boolean z4 = insetsArr == null;
        this.mSystemWindowInsetsConsumed = z4;
        if (z4) {
            insetsArr3 = new Insets[10];
        } else {
            insetsArr3 = (Insets[]) insetsArr.clone();
        }
        this.mTypeInsetsMap = insetsArr3;
        boolean z5 = insetsArr2 == null;
        this.mStableInsetsConsumed = z5;
        if (z5) {
            insetsArr4 = new Insets[10];
        } else {
            insetsArr4 = (Insets[]) insetsArr2.clone();
        }
        this.mTypeMaxInsetsMap = insetsArr4;
        this.mTypeVisibilityMap = zArr;
        this.mIsRound = z;
        this.mForceConsumingTypes = i;
        this.mForceConsumingOpaqueCaptionBar = z2;
        this.mSuppressScrimTypes = i3;
        this.mCompatInsetsTypes = i4;
        this.mCompatIgnoreVisibility = z3;
        boolean z6 = displayCutout == null;
        this.mDisplayCutoutConsumed = z6;
        this.mDisplayCutout = (z6 || displayCutout.isEmpty()) ? null : displayCutout;
        this.mRoundedCorners = roundedCorners;
        this.mPrivacyIndicatorBounds = privacyIndicatorBounds;
        this.mDisplayShape = displayShape;
        if (z4 || rectArr == null) {
            rectArr3 = new Rect[10][];
        } else {
            rectArr3 = (Rect[][]) rectArr.clone();
        }
        this.mTypeBoundingRectsMap = rectArr3;
        if (z5 || rectArr2 == null) {
            rectArr4 = new Rect[10][];
        } else {
            rectArr4 = (Rect[][]) rectArr2.clone();
        }
        this.mTypeMaxBoundingRectsMap = rectArr4;
        this.mFrameWidth = i5;
        this.mFrameHeight = i6;
        this.mDisplayCutoutForUdc = displayCutout2;
        this.mConsumedCaptionType = i2;
    }

    public WindowInsets(WindowInsets windowInsets) {
        this(windowInsets.mSystemWindowInsetsConsumed ? null : windowInsets.mTypeInsetsMap, windowInsets.mStableInsetsConsumed ? null : windowInsets.mTypeMaxInsetsMap, windowInsets.mTypeVisibilityMap, windowInsets.mIsRound, windowInsets.mForceConsumingTypes, windowInsets.mForceConsumingOpaqueCaptionBar, windowInsets.mConsumedCaptionType, windowInsets.mSuppressScrimTypes, displayCutoutCopyConstructorArgument(windowInsets), windowInsets.mRoundedCorners, windowInsets.mPrivacyIndicatorBounds, windowInsets.mDisplayShape, windowInsets.mCompatInsetsTypes, windowInsets.mCompatIgnoreVisibility, windowInsets.mSystemWindowInsetsConsumed ? null : windowInsets.mTypeBoundingRectsMap, windowInsets.mStableInsetsConsumed ? null : windowInsets.mTypeMaxBoundingRectsMap, windowInsets.mFrameWidth, windowInsets.mFrameHeight);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DisplayCutout displayCutoutCopyConstructorArgument(WindowInsets windowInsets) {
        if (windowInsets.mDisplayCutoutConsumed) {
            return null;
        }
        DisplayCutout displayCutout = windowInsets.mDisplayCutout;
        return displayCutout == null ? DisplayCutout.NO_CUTOUT : displayCutout;
    }

    static Insets getInsets(Insets[] insetsArr, int i) {
        Insets insets;
        Insets insets2 = null;
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0 && (insets = insetsArr[Type.indexOf(i2)]) != null) {
                if (insets2 == null) {
                    insets2 = Insets.max(Insets.NONE, insets);
                } else {
                    insets2 = Insets.max(insets2, insets);
                }
            }
        }
        return insets2 == null ? Insets.NONE : insets2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setInsets(Insets[] insetsArr, int i, Insets insets) {
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0) {
                insetsArr[Type.indexOf(i2)] = insets;
            }
        }
    }

    public WindowInsets(Rect rect) {
        this(createCompatTypeMap(rect), null, new boolean[10], false, 0, false, -1, 0, null, null, null, null, Type.systemBars(), false, new Rect[10][], null, 0, 0);
    }

    public static Insets[] createCompatTypeMap(Rect rect) {
        if (rect == null) {
            return null;
        }
        Insets[] insetsArr = new Insets[10];
        assignCompatInsets(insetsArr, rect);
        return insetsArr;
    }

    public static void assignCompatInsets(Insets[] insetsArr, Rect rect) {
        insetsArr[Type.indexOf(1)] = Insets.of(0, rect.top, 0, 0);
        insetsArr[Type.indexOf(2)] = Insets.of(rect.left, 0, rect.right, rect.bottom);
    }

    private static boolean[] createCompatVisibilityMap(Insets[] insetsArr) {
        boolean[] zArr = new boolean[10];
        if (insetsArr != null) {
            for (int i = 1; i <= 512; i <<= 1) {
                int indexOf = Type.indexOf(i);
                if (!Insets.NONE.equals(insetsArr[indexOf])) {
                    zArr[indexOf] = true;
                }
            }
        }
        return zArr;
    }

    @Deprecated
    public Rect getSystemWindowInsetsAsRect() {
        if (this.mTempRect == null) {
            this.mTempRect = new Rect();
        }
        Insets systemWindowInsets = getSystemWindowInsets();
        this.mTempRect.set(systemWindowInsets.left, systemWindowInsets.top, systemWindowInsets.right, systemWindowInsets.bottom);
        return this.mTempRect;
    }

    @Deprecated
    public Insets getSystemWindowInsets() {
        Insets insets;
        if (this.mCompatIgnoreVisibility) {
            insets = getInsetsIgnoringVisibility(this.mCompatInsetsTypes & (~Type.ime()));
        } else {
            insets = getInsets(this.mCompatInsetsTypes);
        }
        return ((this.mCompatInsetsTypes & Type.ime()) == 0 || !this.mCompatIgnoreVisibility) ? insets : Insets.max(insets, getInsets(Type.ime()));
    }

    public Insets getInsets(int i) {
        return getInsets(this.mTypeInsetsMap, i);
    }

    public Insets getInsetsIgnoringVisibility(int i) {
        if ((i & 8) != 0) {
            throw new IllegalArgumentException("Unable to query the maximum insets for IME");
        }
        return getInsets(this.mTypeMaxInsetsMap, i);
    }

    public boolean isVisible(int i) {
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0 && !this.mTypeVisibilityMap[Type.indexOf(i2)]) {
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
        return (getInsets(this.mTypeInsetsMap, Type.all()).equals(Insets.NONE) && getInsets(this.mTypeMaxInsetsMap, Type.all()).equals(Insets.NONE) && this.mDisplayCutout == null && this.mRoundedCorners == null) ? false : true;
    }

    public List<Rect> getBoundingRects(int i) {
        return getBoundingRects(this.mTypeBoundingRectsMap, i);
    }

    public List<Rect> getBoundingRectsIgnoringVisibility(int i) {
        if ((i & 8) != 0) {
            throw new IllegalArgumentException("Unable to query the bounding rects for IME");
        }
        return getBoundingRects(this.mTypeMaxBoundingRectsMap, i);
    }

    private List<Rect> getBoundingRects(Rect[][] rectArr, int i) {
        Rect[] rectArr2;
        Rect[] rectArr3 = null;
        for (int i2 = 1; i2 <= 512; i2 <<= 1) {
            if ((i & i2) != 0 && (rectArr2 = rectArr[Type.indexOf(i2)]) != null) {
                if (rectArr3 == null) {
                    rectArr3 = rectArr2;
                } else {
                    Rect[] rectArr4 = new Rect[rectArr3.length + rectArr2.length];
                    System.arraycopy(rectArr3, 0, rectArr4, 0, rectArr3.length);
                    System.arraycopy(rectArr2, 0, rectArr4, rectArr3.length, rectArr2.length);
                    rectArr3 = rectArr4;
                }
            }
        }
        if (rectArr3 == null) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(rectArr3);
    }

    public DisplayCutout getDisplayCutout() {
        return this.mDisplayCutout;
    }

    public DisplayCutout getDisplayCutoutForUdc() {
        return this.mDisplayCutoutForUdc;
    }

    private DisplayCutout hidden_getDisplayCutoutForUdc() {
        return getDisplayCutoutForUdc();
    }

    public RoundedCorner getRoundedCorner(int i) {
        RoundedCorners roundedCorners = this.mRoundedCorners;
        if (roundedCorners == null) {
            return null;
        }
        return roundedCorners.getRoundedCorner(i);
    }

    public Rect getPrivacyIndicatorBounds() {
        PrivacyIndicatorBounds privacyIndicatorBounds = this.mPrivacyIndicatorBounds;
        if (privacyIndicatorBounds == null) {
            return null;
        }
        return privacyIndicatorBounds.getStaticPrivacyIndicatorBounds();
    }

    public DisplayShape getDisplayShape() {
        return this.mDisplayShape;
    }

    @Deprecated
    public WindowInsets consumeDisplayCutout() {
        Insets[] insetsArr;
        Rect[][] rectArr;
        Insets[] insetsArr2;
        Rect[][] rectArr2;
        boolean z = this.mSystemWindowInsetsConsumed;
        Insets[] insetsArr3 = z ? null : this.mTypeInsetsMap;
        boolean z2 = this.mStableInsetsConsumed;
        if (z2) {
            insetsArr = null;
            rectArr = null;
        } else {
            insetsArr = this.mTypeMaxInsetsMap;
            rectArr = null;
        }
        boolean[] zArr = this.mTypeVisibilityMap;
        Insets[] insetsArr4 = insetsArr3;
        boolean z3 = this.mIsRound;
        int i = this.mForceConsumingTypes;
        Insets[] insetsArr5 = insetsArr;
        boolean z4 = this.mForceConsumingOpaqueCaptionBar;
        Rect[][] rectArr3 = rectArr;
        int i2 = this.mConsumedCaptionType;
        int i3 = this.mSuppressScrimTypes;
        RoundedCorners roundedCorners = this.mRoundedCorners;
        PrivacyIndicatorBounds privacyIndicatorBounds = this.mPrivacyIndicatorBounds;
        DisplayShape displayShape = this.mDisplayShape;
        int i4 = this.mCompatInsetsTypes;
        Rect[][] rectArr4 = rectArr3;
        boolean z5 = this.mCompatIgnoreVisibility;
        if (z) {
            insetsArr2 = insetsArr4;
            rectArr2 = rectArr4;
        } else {
            insetsArr2 = insetsArr4;
            rectArr2 = this.mTypeBoundingRectsMap;
        }
        if (!z2) {
            rectArr4 = this.mTypeMaxBoundingRectsMap;
        }
        return new WindowInsets(insetsArr2, insetsArr5, zArr, z3, i, z4, i2, i3, null, roundedCorners, privacyIndicatorBounds, displayShape, i4, z5, rectArr2, rectArr4, this.mFrameWidth, this.mFrameHeight);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public WindowInsets consumeDisplayCutout(boolean z) {
        Insets[] insetsArr;
        Rect[][] rectArr;
        Insets[] insetsArr2;
        Rect[][] rectArr2;
        boolean z2 = this.mSystemWindowInsetsConsumed;
        Insets[] insetsArr3 = z2 ? null : this.mTypeInsetsMap;
        boolean z3 = this.mStableInsetsConsumed;
        if (z3) {
            insetsArr = null;
            rectArr = null;
        } else {
            insetsArr = this.mTypeMaxInsetsMap;
            rectArr = null;
        }
        boolean[] zArr = this.mTypeVisibilityMap;
        Insets[] insetsArr4 = insetsArr3;
        boolean z4 = this.mIsRound;
        int i = this.mForceConsumingTypes;
        Insets[] insetsArr5 = insetsArr;
        boolean z5 = this.mForceConsumingOpaqueCaptionBar;
        Rect[][] rectArr3 = rectArr;
        int i2 = this.mConsumedCaptionType;
        int i3 = this.mSuppressScrimTypes;
        RoundedCorners roundedCorners = this.mRoundedCorners;
        PrivacyIndicatorBounds privacyIndicatorBounds = this.mPrivacyIndicatorBounds;
        DisplayShape displayShape = this.mDisplayShape;
        int i4 = this.mCompatInsetsTypes;
        boolean z6 = this.mCompatIgnoreVisibility;
        if (z2) {
            insetsArr2 = insetsArr4;
            rectArr2 = rectArr3;
        } else {
            insetsArr2 = insetsArr4;
            rectArr2 = this.mTypeBoundingRectsMap;
        }
        return new WindowInsets(insetsArr2, insetsArr5, zArr, z4, i, z5, i2, i3, null, roundedCorners, privacyIndicatorBounds, displayShape, i4, z6, rectArr2, z3 ? rectArr3 : this.mTypeMaxBoundingRectsMap, this.mFrameWidth, this.mFrameHeight, z ? this.mDisplayCutoutForUdc : rectArr3);
    }

    public boolean isConsumed() {
        return this.mSystemWindowInsetsConsumed && this.mStableInsetsConsumed && this.mDisplayCutoutConsumed;
    }

    public boolean isRound() {
        return this.mIsRound;
    }

    @Deprecated
    public WindowInsets consumeSystemWindowInsets() {
        return new WindowInsets(null, null, this.mTypeVisibilityMap, this.mIsRound, this.mForceConsumingTypes, this.mForceConsumingOpaqueCaptionBar, this.mConsumedCaptionType, this.mSuppressScrimTypes, (this.mCompatInsetsTypes & Type.displayCutout()) != 0 ? null : displayCutoutCopyConstructorArgument(this), this.mRoundedCorners, this.mPrivacyIndicatorBounds, this.mDisplayShape, this.mCompatInsetsTypes, this.mCompatIgnoreVisibility, null, null, this.mFrameWidth, this.mFrameHeight);
    }

    @Deprecated
    public WindowInsets replaceSystemWindowInsets(int i, int i2, int i3, int i4) {
        return this.mSystemWindowInsetsConsumed ? this : new Builder(this).setSystemWindowInsets(Insets.of(i, i2, i3, i4)).build();
    }

    @Deprecated
    public WindowInsets replaceSystemWindowInsets(Rect rect) {
        return replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom);
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

    public int getForceConsumingTypes() {
        return this.mForceConsumingTypes;
    }

    public boolean isForceConsumingOpaqueCaptionBar() {
        return this.mForceConsumingOpaqueCaptionBar;
    }

    public int getConsumedCaptionType() {
        return this.mConsumedCaptionType;
    }

    public int getSuppressScrimTypes() {
        return this.mSuppressScrimTypes;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("WindowInsets{\n    ");
        for (int i = 0; i < 10; i++) {
            Insets insets = this.mTypeInsetsMap[i];
            Insets insets2 = this.mTypeMaxInsetsMap[i];
            boolean z = this.mTypeVisibilityMap[i];
            if (!Insets.NONE.equals(insets) || !Insets.NONE.equals(insets2) || z) {
                sb.append(Type.toString(1 << i));
                sb.append("=");
                sb.append(insets);
                sb.append(" max=");
                sb.append(insets2);
                sb.append(" vis=");
                sb.append(z);
                sb.append(" boundingRects=");
                sb.append(Arrays.toString(this.mTypeBoundingRectsMap[i]));
                sb.append(" maxBoundingRects=");
                sb.append(Arrays.toString(this.mTypeMaxBoundingRectsMap[i]));
                sb.append("\n    ");
            }
        }
        sb.append(this.mDisplayCutout != null ? "cutout=" + this.mDisplayCutout : "");
        sb.append("\n    ");
        sb.append(this.mDisplayCutoutForUdc != null ? "cutoutForUdc=" + this.mDisplayCutoutForUdc : "");
        sb.append("\n    ");
        sb.append(this.mRoundedCorners != null ? "roundedCorners=" + this.mRoundedCorners : "");
        sb.append("\n    ");
        sb.append(this.mPrivacyIndicatorBounds != null ? "privacyIndicatorBounds=" + this.mPrivacyIndicatorBounds : "");
        sb.append("\n    ");
        sb.append(this.mDisplayShape != null ? "displayShape=" + this.mDisplayShape : "");
        sb.append("\n    ");
        sb.append("forceConsumingTypes=" + Type.toString(this.mForceConsumingTypes));
        sb.append("\n    ");
        sb.append("forceConsumingOpaqueCaptionBar=" + this.mForceConsumingOpaqueCaptionBar);
        sb.append("\n    ");
        sb.append("suppressScrimTypes=" + Type.toString(this.mSuppressScrimTypes));
        sb.append("\n    ");
        sb.append("compatInsetsTypes=" + Type.toString(this.mCompatInsetsTypes));
        sb.append("\n    ");
        sb.append("compatIgnoreVisibility=" + this.mCompatIgnoreVisibility);
        sb.append("\n    ");
        sb.append("systemWindowInsetsConsumed=" + this.mSystemWindowInsetsConsumed);
        sb.append("\n    ");
        sb.append("stableInsetsConsumed=" + this.mStableInsetsConsumed);
        sb.append("\n    ");
        sb.append("displayCutoutConsumed=" + this.mDisplayCutoutConsumed);
        sb.append("\n    ");
        sb.append(isRound() ? "round" : "");
        sb.append("\n    ");
        sb.append("frameWidth=" + this.mFrameWidth);
        sb.append("\n    ");
        sb.append("frameHeight=" + this.mFrameHeight);
        sb.append("}");
        return sb.toString();
    }

    @Deprecated
    public WindowInsets inset(Rect rect) {
        return inset(rect.left, rect.top, rect.right, rect.bottom);
    }

    public WindowInsets inset(Insets insets) {
        Objects.requireNonNull(insets);
        return inset(insets.left, insets.top, insets.right, insets.bottom);
    }

    public WindowInsets inset(int i, int i2, int i3, int i4) {
        Preconditions.checkArgumentNonnegative(i);
        Preconditions.checkArgumentNonnegative(i2);
        Preconditions.checkArgumentNonnegative(i3);
        Preconditions.checkArgumentNonnegative(i4);
        return insetUnchecked(i, i2, i3, i4);
    }

    public Size getFrame() {
        return new Size(this.mFrameWidth, this.mFrameHeight);
    }

    public WindowInsets insetUnchecked(int i, int i2, int i3, int i4) {
        DisplayCutout inset;
        DisplayCutout displayCutout;
        RoundedCorners inset2;
        int i5;
        Rect[][] insetBoundingRects;
        Insets[] insetInsets = this.mSystemWindowInsetsConsumed ? null : insetInsets(this.mTypeInsetsMap, i, i2, i3, i4);
        Insets[] insetInsets2 = this.mStableInsetsConsumed ? null : insetInsets(this.mTypeMaxInsetsMap, i, i2, i3, i4);
        boolean[] zArr = this.mTypeVisibilityMap;
        boolean z = this.mIsRound;
        int i6 = this.mForceConsumingTypes;
        boolean z2 = this.mForceConsumingOpaqueCaptionBar;
        int i7 = this.mConsumedCaptionType;
        int i8 = this.mSuppressScrimTypes;
        if (this.mDisplayCutoutConsumed) {
            displayCutout = null;
        } else {
            DisplayCutout displayCutout2 = this.mDisplayCutout;
            if (displayCutout2 == null) {
                inset = DisplayCutout.NO_CUTOUT;
            } else {
                inset = displayCutout2.inset(i, i2, i3, i4);
            }
            displayCutout = inset;
        }
        RoundedCorners roundedCorners = this.mRoundedCorners;
        if (roundedCorners == null) {
            inset2 = RoundedCorners.NO_ROUNDED_CORNERS;
        } else {
            inset2 = roundedCorners.inset(i, i2, i3, i4);
        }
        RoundedCorners roundedCorners2 = inset2;
        PrivacyIndicatorBounds privacyIndicatorBounds = this.mPrivacyIndicatorBounds;
        PrivacyIndicatorBounds inset3 = privacyIndicatorBounds == null ? null : privacyIndicatorBounds.inset(i, i2, i3, i4);
        DisplayShape displayShape = this.mDisplayShape;
        int i9 = this.mCompatInsetsTypes;
        boolean z3 = this.mCompatIgnoreVisibility;
        if (this.mSystemWindowInsetsConsumed) {
            i5 = i7;
            insetBoundingRects = null;
        } else {
            i5 = i7;
            insetBoundingRects = insetBoundingRects(this.mTypeBoundingRectsMap, i, i2, i3, i4, this.mFrameWidth, this.mFrameHeight);
        }
        return new WindowInsets(insetInsets, insetInsets2, zArr, z, i6, z2, i5, i8, displayCutout, roundedCorners2, inset3, displayShape, i9, z3, insetBoundingRects, this.mStableInsetsConsumed ? null : insetBoundingRects(this.mTypeMaxBoundingRectsMap, i, i2, i3, i4, this.mFrameWidth, this.mFrameHeight), Math.max(0, (this.mFrameWidth - i) - i3), Math.max(0, (this.mFrameHeight - i2) - i4));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof WindowInsets)) {
            WindowInsets windowInsets = (WindowInsets) obj;
            if (this.mIsRound == windowInsets.mIsRound && this.mForceConsumingTypes == windowInsets.mForceConsumingTypes && this.mForceConsumingOpaqueCaptionBar == windowInsets.mForceConsumingOpaqueCaptionBar && this.mSuppressScrimTypes == windowInsets.mSuppressScrimTypes && this.mSystemWindowInsetsConsumed == windowInsets.mSystemWindowInsetsConsumed && this.mStableInsetsConsumed == windowInsets.mStableInsetsConsumed && this.mDisplayCutoutConsumed == windowInsets.mDisplayCutoutConsumed && Arrays.equals(this.mTypeInsetsMap, windowInsets.mTypeInsetsMap) && Arrays.equals(this.mTypeMaxInsetsMap, windowInsets.mTypeMaxInsetsMap) && Arrays.equals(this.mTypeVisibilityMap, windowInsets.mTypeVisibilityMap) && Objects.equals(this.mDisplayCutout, windowInsets.mDisplayCutout) && Objects.equals(this.mRoundedCorners, windowInsets.mRoundedCorners) && Objects.equals(this.mPrivacyIndicatorBounds, windowInsets.mPrivacyIndicatorBounds) && Objects.equals(this.mDisplayShape, windowInsets.mDisplayShape) && Arrays.deepEquals(this.mTypeBoundingRectsMap, windowInsets.mTypeBoundingRectsMap) && Arrays.deepEquals(this.mTypeMaxBoundingRectsMap, windowInsets.mTypeMaxBoundingRectsMap) && this.mFrameWidth == windowInsets.mFrameWidth && this.mFrameHeight == windowInsets.mFrameHeight) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(Arrays.hashCode(this.mTypeInsetsMap)), Integer.valueOf(Arrays.hashCode(this.mTypeMaxInsetsMap)), Integer.valueOf(Arrays.hashCode(this.mTypeVisibilityMap)), Boolean.valueOf(this.mIsRound), this.mDisplayCutout, this.mRoundedCorners, Integer.valueOf(this.mForceConsumingTypes), Boolean.valueOf(this.mForceConsumingOpaqueCaptionBar), Integer.valueOf(this.mSuppressScrimTypes), Boolean.valueOf(this.mSystemWindowInsetsConsumed), Boolean.valueOf(this.mStableInsetsConsumed), Boolean.valueOf(this.mDisplayCutoutConsumed), this.mPrivacyIndicatorBounds, this.mDisplayShape, Integer.valueOf(Arrays.deepHashCode(this.mTypeBoundingRectsMap)), Integer.valueOf(Arrays.deepHashCode(this.mTypeMaxBoundingRectsMap)), Integer.valueOf(this.mFrameWidth), Integer.valueOf(this.mFrameHeight));
    }

    private static Insets[] insetInsets(Insets[] insetsArr, int i, int i2, int i3, int i4) {
        Insets insetInsets;
        boolean z = false;
        for (int i5 = 0; i5 < 10; i5++) {
            Insets insets = insetsArr[i5];
            if (insets != null && (insetInsets = insetInsets(insets, i, i2, i3, i4)) != insets) {
                if (!z) {
                    insetsArr = (Insets[]) insetsArr.clone();
                    z = true;
                }
                insetsArr[i5] = insetInsets;
            }
        }
        return insetsArr;
    }

    static Insets insetInsets(Insets insets, int i, int i2, int i3, int i4) {
        int max = Math.max(0, insets.left - i);
        int max2 = Math.max(0, insets.top - i2);
        int max3 = Math.max(0, insets.right - i3);
        int max4 = Math.max(0, insets.bottom - i4);
        return (max == insets.left && max2 == insets.top && max3 == insets.right && max4 == insets.bottom) ? insets : Insets.of(max, max2, max3, max4);
    }

    static Rect[][] insetBoundingRects(Rect[][] rectArr, int i, int i2, int i3, int i4, int i5, int i6) {
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return rectArr;
        }
        boolean z = false;
        for (int i7 = 0; i7 < 10; i7++) {
            Rect[] rectArr2 = rectArr[i7];
            if (rectArr2 != null) {
                Rect[] insetBoundingRects = insetBoundingRects(rectArr2, i, i2, i3, i4, i5, i6);
                if (!Arrays.equals(insetBoundingRects, rectArr2)) {
                    if (!z) {
                        rectArr = (Rect[][]) rectArr.clone();
                        z = true;
                    }
                    rectArr[i7] = insetBoundingRects;
                }
            }
        }
        return rectArr;
    }

    static Rect[] insetBoundingRects(Rect[] rectArr, int i, int i2, int i3, int i4, int i5, int i6) {
        ArrayList arrayList = new ArrayList();
        for (Rect rect : rectArr) {
            Rect insetRect = insetRect(rect, i, i2, i3, i4, i5, i6);
            if (insetRect != null) {
                arrayList.add(insetRect);
            }
        }
        return (Rect[]) arrayList.toArray(new Rect[0]);
    }

    private static Rect insetRect(Rect rect, int i, int i2, int i3, int i4, int i5, int i6) {
        if (rect == null) {
            return null;
        }
        Rect rect2 = new Rect(i, i2, i5 - i3, i6 - i4);
        Rect rect3 = new Rect();
        if (!rect3.setIntersect(rect2, rect)) {
            return null;
        }
        rect3.offset(-i, -i2);
        return rect3;
    }

    boolean isSystemWindowInsetsConsumed() {
        return this.mSystemWindowInsetsConsumed;
    }

    public WindowInsets consumeCaptionInsets() {
        int i = this.mCompatInsetsTypes & (-5);
        this.mCompatInsetsTypes = i;
        WindowInsets windowInsets = new WindowInsets(this.mSystemWindowInsetsConsumed ? null : this.mTypeInsetsMap, this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap, this.mTypeVisibilityMap, this.mIsRound, this.mForceConsumingTypes, this.mForceConsumingOpaqueCaptionBar, this.mConsumedCaptionType, this.mSuppressScrimTypes, this.mDisplayCutout, this.mRoundedCorners, this.mPrivacyIndicatorBounds, this.mDisplayShape, i, this.mCompatIgnoreVisibility, this.mTypeBoundingRectsMap, this.mTypeMaxBoundingRectsMap, this.mFrameWidth, this.mFrameHeight);
        int indexOf = Type.indexOf(4);
        windowInsets.mTypeInsetsMap[indexOf] = null;
        windowInsets.mTypeMaxInsetsMap[indexOf] = null;
        windowInsets.mTypeVisibilityMap[indexOf] = false;
        return windowInsets;
    }

    public static final class Builder {
        private int mConsumedCaptionType;
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

        public Builder setAlwaysConsumeSystemBars(boolean z) {
            return this;
        }

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

        public Builder(WindowInsets windowInsets) {
            this.mSystemInsetsConsumed = true;
            this.mStableInsetsConsumed = true;
            this.mRoundedCorners = RoundedCorners.NO_ROUNDED_CORNERS;
            this.mDisplayShape = DisplayShape.NONE;
            this.mPrivacyIndicatorBounds = new PrivacyIndicatorBounds();
            this.mTypeInsetsMap = (Insets[]) windowInsets.mTypeInsetsMap.clone();
            this.mTypeMaxInsetsMap = (Insets[]) windowInsets.mTypeMaxInsetsMap.clone();
            this.mTypeVisibilityMap = (boolean[]) windowInsets.mTypeVisibilityMap.clone();
            this.mSystemInsetsConsumed = windowInsets.mSystemWindowInsetsConsumed;
            this.mStableInsetsConsumed = windowInsets.mStableInsetsConsumed;
            this.mDisplayCutout = WindowInsets.displayCutoutCopyConstructorArgument(windowInsets);
            this.mRoundedCorners = windowInsets.mRoundedCorners;
            this.mIsRound = windowInsets.mIsRound;
            this.mForceConsumingTypes = windowInsets.mForceConsumingTypes;
            this.mForceConsumingOpaqueCaptionBar = windowInsets.mForceConsumingOpaqueCaptionBar;
            this.mSuppressScrimTypes = windowInsets.mSuppressScrimTypes;
            this.mPrivacyIndicatorBounds = windowInsets.mPrivacyIndicatorBounds;
            this.mDisplayShape = windowInsets.mDisplayShape;
            this.mTypeBoundingRectsMap = (Rect[][]) windowInsets.mTypeBoundingRectsMap.clone();
            this.mTypeMaxBoundingRectsMap = (Rect[][]) windowInsets.mTypeMaxBoundingRectsMap.clone();
            this.mFrameWidth = windowInsets.mFrameWidth;
            this.mFrameHeight = windowInsets.mFrameHeight;
            this.mConsumedCaptionType = windowInsets.mConsumedCaptionType;
        }

        @Deprecated
        public Builder setSystemWindowInsets(Insets insets) {
            Preconditions.checkNotNull(insets);
            WindowInsets.assignCompatInsets(this.mTypeInsetsMap, insets.toRect());
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

        public Builder setInsets(int i, Insets insets) {
            Preconditions.checkNotNull(insets);
            WindowInsets.setInsets(this.mTypeInsetsMap, i, insets);
            this.mSystemInsetsConsumed = false;
            return this;
        }

        public Builder setInsetsIgnoringVisibility(int i, Insets insets) throws IllegalArgumentException {
            if (i == 8) {
                throw new IllegalArgumentException("Maximum inset not available for IME");
            }
            Preconditions.checkNotNull(insets);
            WindowInsets.setInsets(this.mTypeMaxInsetsMap, i, insets);
            this.mStableInsetsConsumed = false;
            return this;
        }

        public Builder setVisible(int i, boolean z) {
            for (int i2 = 1; i2 <= 512; i2 <<= 1) {
                if ((i & i2) != 0) {
                    this.mTypeVisibilityMap[Type.indexOf(i2)] = z;
                }
            }
            return this;
        }

        @Deprecated
        public Builder setStableInsets(Insets insets) {
            Preconditions.checkNotNull(insets);
            WindowInsets.assignCompatInsets(this.mTypeMaxInsetsMap, insets.toRect());
            this.mStableInsetsConsumed = false;
            return this;
        }

        public Builder setDisplayCutout(DisplayCutout displayCutout) {
            if (displayCutout == null) {
                displayCutout = DisplayCutout.NO_CUTOUT;
            }
            this.mDisplayCutout = displayCutout;
            if (!displayCutout.isEmpty()) {
                Insets of = Insets.of(this.mDisplayCutout.getSafeInsets());
                int indexOf = Type.indexOf(128);
                this.mTypeInsetsMap[indexOf] = of;
                this.mTypeMaxInsetsMap[indexOf] = of;
                this.mTypeVisibilityMap[indexOf] = true;
            }
            return this;
        }

        public Builder setRoundedCorners(RoundedCorners roundedCorners) {
            if (roundedCorners == null) {
                roundedCorners = RoundedCorners.NO_ROUNDED_CORNERS;
            }
            this.mRoundedCorners = roundedCorners;
            return this;
        }

        public Builder setRoundedCorner(int i, RoundedCorner roundedCorner) {
            this.mRoundedCorners.setRoundedCorner(i, roundedCorner);
            return this;
        }

        public Builder setPrivacyIndicatorBounds(PrivacyIndicatorBounds privacyIndicatorBounds) {
            this.mPrivacyIndicatorBounds = privacyIndicatorBounds;
            return this;
        }

        public Builder setPrivacyIndicatorBounds(Rect rect) {
            this.mPrivacyIndicatorBounds = new PrivacyIndicatorBounds(new Rect[]{rect, rect, rect, rect}, 0);
            return this;
        }

        public Builder setDisplayShape(DisplayShape displayShape) {
            this.mDisplayShape = displayShape;
            return this;
        }

        public Builder setRound(boolean z) {
            this.mIsRound = z;
            return this;
        }

        public Builder setForceConsumingTypes(int i) {
            this.mForceConsumingTypes = i;
            return this;
        }

        public Builder setForceConsumingOpaqueCaptionBar(boolean z) {
            this.mForceConsumingOpaqueCaptionBar = z;
            return this;
        }

        public Builder setSuppressScrimTypes(int i) {
            this.mSuppressScrimTypes = i;
            return this;
        }

        public Builder setBoundingRects(int i, List<Rect> list) {
            for (int i2 = 1; i2 <= 512; i2 <<= 1) {
                if ((i & i2) != 0) {
                    this.mTypeBoundingRectsMap[Type.indexOf(i2)] = (Rect[]) list.toArray(new Rect[0]);
                }
            }
            this.mSystemInsetsConsumed = false;
            return this;
        }

        public Builder setBoundingRectsIgnoringVisibility(int i, List<Rect> list) {
            if (i == 8) {
                throw new IllegalArgumentException("Maximum bounding rects not available for IME");
            }
            for (int i2 = 1; i2 <= 512; i2 <<= 1) {
                if ((i & i2) != 0) {
                    this.mTypeMaxBoundingRectsMap[Type.indexOf(i2)] = (Rect[]) list.toArray(new Rect[0]);
                }
            }
            this.mStableInsetsConsumed = false;
            return this;
        }

        public Builder setFrame(int i, int i2) {
            this.mFrameWidth = i;
            this.mFrameHeight = i2;
            return this;
        }

        public WindowInsets build() {
            Insets[] insetsArr;
            Object obj;
            Insets[] insetsArr2 = this.mSystemInsetsConsumed ? null : this.mTypeInsetsMap;
            if (this.mStableInsetsConsumed) {
                insetsArr = null;
                obj = null;
            } else {
                insetsArr = this.mTypeMaxInsetsMap;
                obj = null;
            }
            return new WindowInsets(insetsArr2, insetsArr, this.mTypeVisibilityMap, this.mIsRound, this.mForceConsumingTypes, this.mForceConsumingOpaqueCaptionBar, this.mConsumedCaptionType, this.mSuppressScrimTypes, this.mDisplayCutout, this.mRoundedCorners, this.mPrivacyIndicatorBounds, this.mDisplayShape, Type.systemBars(), false, this.mSystemInsetsConsumed ? null : this.mTypeBoundingRectsMap, this.mStableInsetsConsumed ? null : this.mTypeMaxBoundingRectsMap, this.mFrameWidth, this.mFrameHeight);
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
        public @interface InsetsType {
        }

        public static int all() {
            return -1;
        }

        public static int captionBar() {
            return 4;
        }

        public static int defaultVisible() {
            return -9;
        }

        public static int displayCutout() {
            return 128;
        }

        public static boolean hasCompatSystemBars(int i) {
            return (i & 3) != 0;
        }

        public static int ime() {
            return 8;
        }

        public static int mandatorySystemGestures() {
            return 32;
        }

        public static int navigationBars() {
            return 2;
        }

        public static int statusBars() {
            return 1;
        }

        public static int systemBars() {
            return 519;
        }

        public static int systemBarsWithoutCaptionBar() {
            return 515;
        }

        public static int systemGestures() {
            return 16;
        }

        public static int systemOverlays() {
            return 512;
        }

        public static int tappableElement() {
            return 64;
        }

        static int indexOf(int i) {
            if (i == 1) {
                return 0;
            }
            if (i == 2) {
                return 1;
            }
            if (i == 4) {
                return 2;
            }
            if (i == 8) {
                return 3;
            }
            if (i == 16) {
                return 4;
            }
            if (i == 32) {
                return 5;
            }
            if (i == 64) {
                return 6;
            }
            if (i == 128) {
                return 7;
            }
            if (i == 256) {
                return 8;
            }
            if (i == 512) {
                return 9;
            }
            throw new IllegalArgumentException("type needs to be >= FIRST and <= LAST, type=" + i);
        }

        public static String toString(int i) {
            StringBuilder sb = new StringBuilder();
            if ((i & 1) != 0) {
                sb.append("statusBars ");
            }
            if ((i & 2) != 0) {
                sb.append("navigationBars ");
            }
            if ((i & 4) != 0) {
                sb.append("captionBar ");
            }
            if ((i & 8) != 0) {
                sb.append("ime ");
            }
            if ((i & 16) != 0) {
                sb.append("systemGestures ");
            }
            if ((i & 32) != 0) {
                sb.append("mandatorySystemGestures ");
            }
            if ((i & 64) != 0) {
                sb.append("tappableElement ");
            }
            if ((i & 128) != 0) {
                sb.append("displayCutout ");
            }
            if ((i & 256) != 0) {
                sb.append("windowDecor ");
            }
            if ((i & 512) != 0) {
                sb.append("systemOverlays ");
            }
            if (sb.length() > 0) {
                sb.delete(sb.length() - 1, sb.length());
            }
            return sb.toString();
        }

        private Type() {
        }
    }

    public static final class Side {
        public static final int BOTTOM = 8;
        public static final int LEFT = 1;
        public static final int RIGHT = 4;
        public static final int TOP = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface InsetsSide {
        }

        public static int all() {
            return 15;
        }

        private Side() {
        }
    }

    public WindowInsets removeCutoutInsets(boolean z) {
        this.mCompatInsetsTypes &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        WindowInsets windowInsets = new WindowInsets(this.mSystemWindowInsetsConsumed ? null : this.mTypeInsetsMap, this.mStableInsetsConsumed ? null : this.mTypeMaxInsetsMap, this.mTypeVisibilityMap, this.mIsRound, this.mForceConsumingTypes, this.mForceConsumingOpaqueCaptionBar, this.mConsumedCaptionType, this.mSuppressScrimTypes, DisplayCutout.NO_CUTOUT, this.mRoundedCorners, this.mPrivacyIndicatorBounds, this.mDisplayShape, this.mCompatInsetsTypes, this.mCompatIgnoreVisibility, this.mTypeBoundingRectsMap, this.mTypeMaxBoundingRectsMap, this.mFrameWidth, this.mFrameHeight, (CoreRune.FW_CAN_DISPATCH_UDC_CUTOUT && z) ? this.mDisplayCutoutForUdc : null);
        int indexOf = Type.indexOf(128);
        windowInsets.mTypeInsetsMap[indexOf] = null;
        windowInsets.mTypeMaxInsetsMap[indexOf] = null;
        windowInsets.mTypeVisibilityMap[indexOf] = false;
        return windowInsets;
    }
}
