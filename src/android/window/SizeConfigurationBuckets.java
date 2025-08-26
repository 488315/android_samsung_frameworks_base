package android.window;

import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;
import java.util.Arrays;

/* loaded from: classes5.dex */
public final class SizeConfigurationBuckets implements Parcelable {
    public static final Parcelable.Creator<SizeConfigurationBuckets> CREATOR = new Parcelable.Creator<SizeConfigurationBuckets>() { // from class: android.window.SizeConfigurationBuckets.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SizeConfigurationBuckets[] newArray(int i) {
            return new SizeConfigurationBuckets[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SizeConfigurationBuckets createFromParcel(Parcel parcel) {
            return new SizeConfigurationBuckets(parcel);
        }
    };
    private final int[] mHorizontal;
    private final boolean mScreenLayoutLongSet;
    private final int[] mScreenLayoutSize;
    private final int[] mSmallest;
    private final int[] mVertical;

    @Deprecated
    private void __metadata() {
    }

    public static boolean areNonSizeLayoutFieldsUnchanged(int i, int i2) {
        return (i & 268436416) == (i2 & 268436416);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SizeConfigurationBuckets(Configuration[] configurationArr) {
        SparseIntArray sparseIntArray = new SparseIntArray();
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        SparseIntArray sparseIntArray3 = new SparseIntArray();
        SparseIntArray sparseIntArray4 = new SparseIntArray();
        boolean z = false;
        for (int length = configurationArr.length - 1; length >= 0; length--) {
            Configuration configuration = configurationArr[length];
            if (configuration.screenHeightDp != 0) {
                sparseIntArray2.put(configuration.screenHeightDp, 0);
            }
            if (configuration.screenWidthDp != 0) {
                sparseIntArray.put(configuration.screenWidthDp, 0);
            }
            if (configuration.smallestScreenWidthDp != 0) {
                sparseIntArray3.put(configuration.smallestScreenWidthDp, 0);
            }
            int i = configuration.screenLayout & 15;
            if (i != 0) {
                sparseIntArray4.put(i, 0);
            }
            if (!z && (configuration.screenLayout & 48) != 0) {
                z = true;
            }
        }
        this.mHorizontal = sparseIntArray.copyKeys();
        this.mVertical = sparseIntArray2.copyKeys();
        this.mSmallest = sparseIntArray3.copyKeys();
        this.mScreenLayoutSize = sparseIntArray4.copyKeys();
        this.mScreenLayoutLongSet = z;
    }

    public static int filterDiff(int i, Configuration configuration, Configuration configuration2, SizeConfigurationBuckets sizeConfigurationBuckets) {
        if (sizeConfigurationBuckets == null) {
            return i;
        }
        boolean zAreNonSizeLayoutFieldsUnchanged = areNonSizeLayoutFieldsUnchanged(configuration.screenLayout, configuration2.screenLayout);
        if ((i & 1024) != 0 && !sizeConfigurationBuckets.crossesHorizontalSizeThreshold(configuration.screenWidthDp, configuration2.screenWidthDp) && !sizeConfigurationBuckets.crossesVerticalSizeThreshold(configuration.screenHeightDp, configuration2.screenHeightDp)) {
            i &= -1025;
        }
        if ((i & 2048) != 0 && !sizeConfigurationBuckets.crossesSmallestSizeThreshold(configuration.smallestScreenWidthDp, configuration2.smallestScreenWidthDp)) {
            i &= -2049;
        }
        return ((i & 256) == 0 || !zAreNonSizeLayoutFieldsUnchanged || sizeConfigurationBuckets.crossesScreenLayoutSizeThreshold(configuration, configuration2) || sizeConfigurationBuckets.crossesScreenLayoutLongThreshold(configuration.screenLayout, configuration2.screenLayout)) ? i : i & (-257);
    }

    private boolean crossesHorizontalSizeThreshold(int i, int i2) {
        return crossesSizeThreshold(this.mHorizontal, i, i2);
    }

    private boolean crossesVerticalSizeThreshold(int i, int i2) {
        return crossesSizeThreshold(this.mVertical, i, i2);
    }

    private boolean crossesSmallestSizeThreshold(int i, int i2) {
        return crossesSizeThreshold(this.mSmallest, i, i2);
    }

    public boolean crossesScreenLayoutSizeThreshold(Configuration configuration, Configuration configuration2) {
        if ((configuration.screenLayout & 15) == (configuration2.screenLayout & 15)) {
            return false;
        }
        if (!configuration2.isLayoutSizeAtLeast(configuration.screenLayout & 15)) {
            return true;
        }
        int[] iArr = this.mScreenLayoutSize;
        if (iArr != null) {
            for (int i : iArr) {
                if (configuration.isLayoutSizeAtLeast(i) != configuration2.isLayoutSizeAtLeast(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean crossesScreenLayoutLongThreshold(int i, int i2) {
        return this.mScreenLayoutLongSet && (i & 48) != (i2 & 48);
    }

    public static boolean crossesSizeThreshold(int[] iArr, int i, int i2) {
        if (iArr == null) {
            return false;
        }
        for (int length = iArr.length - 1; length >= 0; length--) {
            int i3 = iArr[length];
            if ((i < i3 && i2 >= i3) || (i >= i3 && i2 < i3)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return Arrays.toString(this.mHorizontal) + " " + Arrays.toString(this.mVertical) + " " + Arrays.toString(this.mSmallest) + " " + Arrays.toString(this.mScreenLayoutSize) + " " + this.mScreenLayoutLongSet;
    }

    public SizeConfigurationBuckets(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, boolean z) {
        this.mHorizontal = iArr;
        this.mVertical = iArr2;
        this.mSmallest = iArr3;
        this.mScreenLayoutSize = iArr4;
        this.mScreenLayoutLongSet = z;
    }

    public int[] getHorizontal() {
        return this.mHorizontal;
    }

    public int[] getVertical() {
        return this.mVertical;
    }

    public int[] getSmallest() {
        return this.mSmallest;
    }

    public int[] getScreenLayoutSize() {
        return this.mScreenLayoutSize;
    }

    public boolean isScreenLayoutLongSet() {
        return this.mScreenLayoutLongSet;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mScreenLayoutLongSet ? (byte) 16 : (byte) 0;
        if (this.mHorizontal != null) {
            b = (byte) (b | 1);
        }
        if (this.mVertical != null) {
            b = (byte) (b | 2);
        }
        if (this.mSmallest != null) {
            b = (byte) (b | 4);
        }
        if (this.mScreenLayoutSize != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        int[] iArr = this.mHorizontal;
        if (iArr != null) {
            parcel.writeIntArray(iArr);
        }
        int[] iArr2 = this.mVertical;
        if (iArr2 != null) {
            parcel.writeIntArray(iArr2);
        }
        int[] iArr3 = this.mSmallest;
        if (iArr3 != null) {
            parcel.writeIntArray(iArr3);
        }
        int[] iArr4 = this.mScreenLayoutSize;
        if (iArr4 != null) {
            parcel.writeIntArray(iArr4);
        }
    }

    SizeConfigurationBuckets(Parcel parcel) {
        byte b = parcel.readByte();
        boolean z = (b & 16) != 0;
        int[] iArrCreateIntArray = (b & 1) == 0 ? null : parcel.createIntArray();
        int[] iArrCreateIntArray2 = (b & 2) == 0 ? null : parcel.createIntArray();
        int[] iArrCreateIntArray3 = (b & 4) == 0 ? null : parcel.createIntArray();
        int[] iArrCreateIntArray4 = (b & 8) != 0 ? parcel.createIntArray() : null;
        this.mHorizontal = iArrCreateIntArray;
        this.mVertical = iArrCreateIntArray2;
        this.mSmallest = iArrCreateIntArray3;
        this.mScreenLayoutSize = iArrCreateIntArray4;
        this.mScreenLayoutLongSet = z;
    }
}
