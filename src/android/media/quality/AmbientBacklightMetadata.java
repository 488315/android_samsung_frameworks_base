package android.media.quality;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class AmbientBacklightMetadata implements Parcelable {
    public static final int ALGORITHM_NONE = 0;
    public static final int ALGORITHM_RLE = 1;
    public static final Parcelable.Creator<AmbientBacklightMetadata> CREATOR = new Parcelable.Creator<AmbientBacklightMetadata>() { // from class: android.media.quality.AmbientBacklightMetadata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientBacklightMetadata createFromParcel(Parcel parcel) {
            return new AmbientBacklightMetadata(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientBacklightMetadata[] newArray(int i) {
            return new AmbientBacklightMetadata[i];
        }
    };
    private final int mColorFormat;
    private final int mCompressAlgorithm;
    private final int mHorizontalZonesNumber;
    private final String mPackageName;
    private final int mSource;
    private final int mVerticalZonesNumber;
    private final int[] mZonesColors;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CompressionAlgorithm {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AmbientBacklightMetadata(String str, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        this.mPackageName = str;
        this.mCompressAlgorithm = i;
        this.mSource = i2;
        this.mColorFormat = i3;
        this.mHorizontalZonesNumber = i4;
        this.mVerticalZonesNumber = i5;
        this.mZonesColors = iArr;
    }

    private AmbientBacklightMetadata(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mCompressAlgorithm = parcel.readInt();
        this.mSource = parcel.readInt();
        this.mColorFormat = parcel.readInt();
        this.mHorizontalZonesNumber = parcel.readInt();
        this.mVerticalZonesNumber = parcel.readInt();
        this.mZonesColors = parcel.createIntArray();
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getCompressionAlgorithm() {
        return this.mCompressAlgorithm;
    }

    public int getSource() {
        return this.mSource;
    }

    public int getColorFormat() {
        return this.mColorFormat;
    }

    public int getHorizontalZonesCount() {
        return this.mHorizontalZonesNumber;
    }

    public int getVerticalZonesCount() {
        return this.mVerticalZonesNumber;
    }

    public int[] getZoneColors() {
        return this.mZonesColors;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mCompressAlgorithm);
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mColorFormat);
        parcel.writeInt(this.mHorizontalZonesNumber);
        parcel.writeInt(this.mVerticalZonesNumber);
        parcel.writeIntArray(this.mZonesColors);
    }

    public String toString() {
        return "AmbientBacklightMetadata{packageName=" + this.mPackageName + ", compressAlgorithm=" + this.mCompressAlgorithm + ", source=" + this.mSource + ", colorFormat=" + this.mColorFormat + ", horizontalZonesNumber=" + this.mHorizontalZonesNumber + ", verticalZonesNumber=" + this.mVerticalZonesNumber + ", zonesColors=" + Arrays.toString(this.mZonesColors) + "}";
    }
}
