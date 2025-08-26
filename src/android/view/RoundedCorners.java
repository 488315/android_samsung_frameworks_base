package android.view;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.DisplayUtils;
import android.util.Pair;
import com.android.internal.R;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class RoundedCorners implements Parcelable {
    public static final int ROUNDED_CORNER_POSITION_LENGTH = 4;
    private static int sCachedDisplayHeight;
    private static int sCachedDisplayWidth;
    private static float sCachedPhysicalPixelDisplaySizeRatio;
    private static Pair<Integer, Integer> sCachedRadii;
    private static RoundedCorners sCachedRoundedCorners;
    public final RoundedCorner[] mRoundedCorners;
    public static final RoundedCorners NO_ROUNDED_CORNERS = new RoundedCorners(new RoundedCorner(0), new RoundedCorner(1), new RoundedCorner(2), new RoundedCorner(3));
    private static final Object CACHE_LOCK = new Object();
    public static final Parcelable.Creator<RoundedCorners> CREATOR = new Parcelable.Creator<RoundedCorners>() { // from class: android.view.RoundedCorners.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RoundedCorners createFromParcel(Parcel parcel) {
            if (parcel.readInt() == 0) {
                return RoundedCorners.NO_ROUNDED_CORNERS;
            }
            RoundedCorner[] roundedCornerArr = new RoundedCorner[4];
            parcel.readTypedArray(roundedCornerArr, RoundedCorner.CREATOR);
            return new RoundedCorners(roundedCornerArr);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RoundedCorners[] newArray(int i) {
            return new RoundedCorners[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RoundedCorners(RoundedCorner[] roundedCornerArr) {
        this.mRoundedCorners = roundedCornerArr;
    }

    public RoundedCorners(RoundedCorner roundedCorner, RoundedCorner roundedCorner2, RoundedCorner roundedCorner3, RoundedCorner roundedCorner4) {
        this.mRoundedCorners = new RoundedCorner[]{roundedCorner, roundedCorner2, roundedCorner3, roundedCorner4};
    }

    public RoundedCorners(RoundedCorners roundedCorners) {
        this.mRoundedCorners = new RoundedCorner[4];
        for (int i = 0; i < 4; i++) {
            this.mRoundedCorners[i] = new RoundedCorner(roundedCorners.mRoundedCorners[i]);
        }
    }

    public static RoundedCorners fromResources(Resources resources, String str, int i, int i2, int i3, int i4) {
        return fromRadii(loadRoundedCornerRadii(resources, str), i, i2, i3, i4);
    }

    public static RoundedCorners fromRadii(Pair<Integer, Integer> pair, int i, int i2) {
        return fromRadii(pair, i, i2, i, i2);
    }

    private static RoundedCorners fromRadii(Pair<Integer, Integer> pair, int i, int i2, int i3, int i4) {
        if (pair == null) {
            return null;
        }
        float physicalPixelDisplaySizeRatio = DisplayUtils.getPhysicalPixelDisplaySizeRatio(i, i2, i3, i4);
        synchronized (CACHE_LOCK) {
            if (pair.equals(sCachedRadii) && sCachedDisplayWidth == i3 && sCachedDisplayHeight == i4 && sCachedPhysicalPixelDisplaySizeRatio == physicalPixelDisplaySizeRatio) {
                return sCachedRoundedCorners;
            }
            RoundedCorner[] roundedCornerArr = new RoundedCorner[4];
            int i5 = 0;
            int iIntValue = pair.first.intValue() > 0 ? pair.first.intValue() : 0;
            int iIntValue2 = pair.second.intValue() > 0 ? pair.second.intValue() : 0;
            while (i5 < 4) {
                roundedCornerArr[i5] = createRoundedCorner(i5, i5 <= 1 ? iIntValue : iIntValue2, i3, i4);
                i5++;
            }
            RoundedCorners roundedCorners = new RoundedCorners(roundedCornerArr);
            synchronized (CACHE_LOCK) {
                sCachedDisplayWidth = i3;
                sCachedDisplayHeight = i4;
                sCachedRadii = pair;
                sCachedRoundedCorners = roundedCorners;
                sCachedPhysicalPixelDisplaySizeRatio = physicalPixelDisplaySizeRatio;
            }
            return roundedCorners;
        }
    }

    public static RoundedCorners fromCustomResources(Resources resources, String str, int i, int i2, int i3, int i4, DisplayMetrics displayMetrics, boolean z) {
        return fromRadii(loadCustomRoundedCornerRadii(resources, str, displayMetrics, z), i, i2, i3, i4);
    }

    private static Pair<Integer, Integer> loadCustomRoundedCornerRadii(Resources resources, String str, DisplayMetrics displayMetrics, boolean z) throws Resources.NotFoundException {
        int customRoundedCornerRadius = getCustomRoundedCornerRadius(R.dimen.rounded_corner_radius, resources, str, displayMetrics);
        int customRoundedCornerRadius2 = getCustomRoundedCornerRadius(R.dimen.rounded_corner_radius_top, resources, str, displayMetrics);
        int customRoundedCornerRadius3 = getCustomRoundedCornerRadius(R.dimen.rounded_corner_radius_bottom, resources, str, displayMetrics);
        if (customRoundedCornerRadius == 0 && customRoundedCornerRadius2 == 0 && customRoundedCornerRadius3 == 0) {
            return null;
        }
        if (customRoundedCornerRadius2 <= 0) {
            customRoundedCornerRadius2 = customRoundedCornerRadius;
        }
        Integer numValueOf = Integer.valueOf(customRoundedCornerRadius2);
        if (customRoundedCornerRadius3 > 0) {
            customRoundedCornerRadius = customRoundedCornerRadius3;
        }
        return new Pair<>(numValueOf, Integer.valueOf(customRoundedCornerRadius));
    }

    public static int getCustomRoundedCornerRadius(int i, Resources resources, String str, DisplayMetrics displayMetrics) throws Resources.NotFoundException {
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_roundedCornerRadiusArray);
        if (displayUniqueIdConfigIndex >= 0) {
            try {
                if (displayUniqueIdConfigIndex < typedArrayObtainTypedArray.length()) {
                    return typedArrayObtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
                }
            } finally {
                typedArrayObtainTypedArray.recycle();
            }
        }
        return resources.getDimensionPixelSize(i, displayMetrics);
    }

    private static Pair<Integer, Integer> loadRoundedCornerRadii(Resources resources, String str) throws Resources.NotFoundException {
        int roundedCornerRadius = getRoundedCornerRadius(resources, str);
        int roundedCornerTopRadius = getRoundedCornerTopRadius(resources, str);
        int roundedCornerBottomRadius = getRoundedCornerBottomRadius(resources, str);
        if (roundedCornerRadius == 0 && roundedCornerTopRadius == 0 && roundedCornerBottomRadius == 0) {
            return null;
        }
        if (roundedCornerTopRadius <= 0) {
            roundedCornerTopRadius = roundedCornerRadius;
        }
        Integer numValueOf = Integer.valueOf(roundedCornerTopRadius);
        if (roundedCornerBottomRadius > 0) {
            roundedCornerRadius = roundedCornerBottomRadius;
        }
        return new Pair<>(numValueOf, Integer.valueOf(roundedCornerRadius));
    }

    public static int getRoundedCornerRadius(Resources resources, String str) throws Resources.NotFoundException {
        int dimensionPixelSize;
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_roundedCornerRadiusArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < typedArrayObtainTypedArray.length()) {
            dimensionPixelSize = typedArrayObtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.rounded_corner_radius);
        }
        typedArrayObtainTypedArray.recycle();
        return (dimensionPixelSize == 0 && resources.getConfiguration().isScreenRound()) ? resources.getDisplayMetrics().widthPixels / 2 : dimensionPixelSize;
    }

    public static int getRoundedCornerTopRadius(Resources resources, String str) throws Resources.NotFoundException {
        int dimensionPixelSize;
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_roundedCornerTopRadiusArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < typedArrayObtainTypedArray.length()) {
            dimensionPixelSize = typedArrayObtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.rounded_corner_radius_top);
        }
        typedArrayObtainTypedArray.recycle();
        return dimensionPixelSize;
    }

    public static int getRoundedCornerBottomRadius(Resources resources, String str) throws Resources.NotFoundException {
        int dimensionPixelSize;
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_roundedCornerBottomRadiusArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < typedArrayObtainTypedArray.length()) {
            dimensionPixelSize = typedArrayObtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.rounded_corner_radius_bottom);
        }
        typedArrayObtainTypedArray.recycle();
        return dimensionPixelSize;
    }

    public static int getRoundedCornerRadiusAdjustment(Resources resources, String str) throws Resources.NotFoundException {
        int dimensionPixelSize;
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_roundedCornerRadiusAdjustmentArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < typedArrayObtainTypedArray.length()) {
            dimensionPixelSize = typedArrayObtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.rounded_corner_radius_adjustment);
        }
        typedArrayObtainTypedArray.recycle();
        return dimensionPixelSize;
    }

    public static int getRoundedCornerRadiusTopAdjustment(Resources resources, String str) throws Resources.NotFoundException {
        int dimensionPixelSize;
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_roundedCornerTopRadiusAdjustmentArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < typedArrayObtainTypedArray.length()) {
            dimensionPixelSize = typedArrayObtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.rounded_corner_radius_top_adjustment);
        }
        typedArrayObtainTypedArray.recycle();
        return dimensionPixelSize;
    }

    public static int getRoundedCornerRadiusBottomAdjustment(Resources resources, String str) throws Resources.NotFoundException {
        int dimensionPixelSize;
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_roundedCornerBottomRadiusAdjustmentArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < typedArrayObtainTypedArray.length()) {
            dimensionPixelSize = typedArrayObtainTypedArray.getDimensionPixelSize(displayUniqueIdConfigIndex, 0);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.rounded_corner_radius_bottom_adjustment);
        }
        typedArrayObtainTypedArray.recycle();
        return dimensionPixelSize;
    }

    public static boolean getBuiltInDisplayIsRound(Resources resources, String str) {
        boolean z;
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R.array.config_builtInDisplayIsRoundArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < typedArrayObtainTypedArray.length()) {
            z = typedArrayObtainTypedArray.getBoolean(displayUniqueIdConfigIndex, false);
        } else {
            z = resources.getBoolean(R.bool.config_mainBuiltInDisplayIsRound);
        }
        typedArrayObtainTypedArray.recycle();
        return z;
    }

    public RoundedCorners insetWithFrame(Rect rect, Rect rect2) {
        RoundedCorners roundedCorners;
        int iWidth;
        int iHeight;
        int i = rect.left - rect2.left;
        int i2 = rect.top - rect2.top;
        int i3 = rect2.right - rect.right;
        int i4 = rect2.bottom - rect.bottom;
        RoundedCorner[] roundedCornerArr = new RoundedCorner[4];
        int i5 = 0;
        while (i5 < 4) {
            if (this.mRoundedCorners[i5].isEmpty()) {
                roundedCornerArr[i5] = new RoundedCorner(i5);
                roundedCorners = this;
            } else {
                int radius = this.mRoundedCorners[i5].getRadius();
                if (i5 == 0) {
                    roundedCorners = this;
                    iWidth = radius;
                    iHeight = iWidth;
                } else if (i5 == 1) {
                    roundedCorners = this;
                    iWidth = rect2.width() - radius;
                    iHeight = radius;
                } else if (i5 == 2) {
                    iWidth = rect2.width() - radius;
                    iHeight = rect2.height() - radius;
                    roundedCorners = this;
                } else if (i5 == 3) {
                    roundedCorners = this;
                    iHeight = rect2.height() - radius;
                    iWidth = radius;
                } else {
                    throw new IllegalArgumentException("The position is not one of the RoundedCornerPosition =" + i5);
                }
                roundedCornerArr[i5] = roundedCorners.insetRoundedCorner(i5, radius, iWidth, iHeight, i, i2, i3, i4);
            }
            i5++;
            this = roundedCorners;
        }
        return new RoundedCorners(roundedCornerArr);
    }

    public RoundedCorners inset(int i, int i2, int i3, int i4) {
        RoundedCorner[] roundedCornerArr = new RoundedCorner[4];
        for (int i5 = 0; i5 < 4; i5++) {
            roundedCornerArr[i5] = insetRoundedCorner(i5, this.mRoundedCorners[i5].getRadius(), this.mRoundedCorners[i5].getCenter().x, this.mRoundedCorners[i5].getCenter().y, i, i2, i3, i4);
        }
        return new RoundedCorners(roundedCornerArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private RoundedCorner insetRoundedCorner(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (this.mRoundedCorners[i].isEmpty()) {
            return new RoundedCorner(i);
        }
        boolean z = true;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalArgumentException("The position is not one of the RoundedCornerPosition =" + i);
                    }
                    if (i2 <= i8 || i2 <= i5) {
                        z = false;
                    }
                } else if (i2 <= i8 || i2 <= i7) {
                }
            } else if (i2 <= i6 || i2 <= i7) {
            }
        } else if (i2 <= i6 || i2 <= i5) {
        }
        return new RoundedCorner(i, i2, z ? i3 - i5 : 0, z ? i4 - i6 : 0);
    }

    public RoundedCorner getRoundedCorner(int i) {
        if (this.mRoundedCorners[i].isEmpty()) {
            return null;
        }
        return new RoundedCorner(this.mRoundedCorners[i]);
    }

    public void setRoundedCorner(int i, RoundedCorner roundedCorner) {
        RoundedCorner[] roundedCornerArr = this.mRoundedCorners;
        if (roundedCorner == null) {
            roundedCorner = new RoundedCorner(i);
        }
        roundedCornerArr[i] = roundedCorner;
    }

    public RoundedCorner[] getAllRoundedCorners() {
        RoundedCorner[] roundedCornerArr = new RoundedCorner[4];
        for (int i = 0; i < 4; i++) {
            roundedCornerArr[i] = new RoundedCorner(roundedCornerArr[i]);
        }
        return roundedCornerArr;
    }

    public RoundedCorners scale(float f) {
        if (f == 1.0f) {
            return this;
        }
        RoundedCorner[] roundedCornerArr = new RoundedCorner[4];
        for (int i = 0; i < 4; i++) {
            RoundedCorner roundedCorner = this.mRoundedCorners[i];
            roundedCornerArr[i] = new RoundedCorner(i, (int) (roundedCorner.getRadius() * f), (int) (roundedCorner.getCenter().x * f), (int) (roundedCorner.getCenter().y * f));
        }
        return new RoundedCorners(roundedCornerArr);
    }

    public RoundedCorners rotate(int i, int i2, int i3) {
        if (i == 0) {
            return this;
        }
        boolean z = true;
        if (i != 1 && i != 3) {
            z = false;
        }
        RoundedCorner[] roundedCornerArr = new RoundedCorner[4];
        for (int i4 = 0; i4 < this.mRoundedCorners.length; i4++) {
            int rotatedIndex = getRotatedIndex(i4, i);
            roundedCornerArr[rotatedIndex] = createRoundedCorner(rotatedIndex, this.mRoundedCorners[i4].getRadius(), z ? i3 : i2, z ? i2 : i3);
        }
        return new RoundedCorners(roundedCornerArr);
    }

    private static RoundedCorner createRoundedCorner(int i, int i2, int i3, int i4) {
        if (i == 0) {
            return new RoundedCorner(0, i2, i2 > 0 ? i2 : 0, i2 > 0 ? i2 : 0);
        }
        if (i == 1) {
            return new RoundedCorner(1, i2, i2 > 0 ? i3 - i2 : 0, i2 > 0 ? i2 : 0);
        }
        if (i == 2) {
            return new RoundedCorner(2, i2, i2 > 0 ? i3 - i2 : 0, i2 > 0 ? i4 - i2 : 0);
        }
        if (i == 3) {
            return new RoundedCorner(3, i2, i2 > 0 ? i2 : 0, i2 > 0 ? i4 - i2 : 0);
        }
        throw new IllegalArgumentException("The position is not one of the RoundedCornerPosition =" + i);
    }

    private static int getRotatedIndex(int i, int i2) {
        return ((i - i2) + 4) % 4;
    }

    public int hashCode() {
        int iHashCode = 0;
        for (RoundedCorner roundedCorner : this.mRoundedCorners) {
            iHashCode = (iHashCode * 31) + roundedCorner.hashCode();
        }
        return iHashCode;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RoundedCorners) {
            return Arrays.deepEquals(this.mRoundedCorners, ((RoundedCorners) obj).mRoundedCorners);
        }
        return false;
    }

    public String toString() {
        return "RoundedCorners{" + Arrays.toString(this.mRoundedCorners) + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (equals(NO_ROUNDED_CORNERS)) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeTypedArray(this.mRoundedCorners, i);
        }
    }
}
