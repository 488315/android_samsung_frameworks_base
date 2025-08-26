package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class LoudnessCodecInfo implements Parcelable {
    public static final Parcelable.Creator<LoudnessCodecInfo> CREATOR = new Parcelable.Creator<LoudnessCodecInfo>() { // from class: android.media.LoudnessCodecInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LoudnessCodecInfo createFromParcel(Parcel parcel) {
            LoudnessCodecInfo loudnessCodecInfo = new LoudnessCodecInfo();
            loudnessCodecInfo.readFromParcel(parcel);
            return loudnessCodecInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LoudnessCodecInfo[] newArray(int i) {
            return new LoudnessCodecInfo[i];
        }
    };
    public boolean isDownmixing = false;
    public int metadataType;

    public @interface CodecMetadataType {
        public static final int CODEC_METADATA_TYPE_AC_3 = 3;
        public static final int CODEC_METADATA_TYPE_AC_4 = 4;
        public static final int CODEC_METADATA_TYPE_DTS_HD = 5;
        public static final int CODEC_METADATA_TYPE_DTS_UHD = 6;
        public static final int CODEC_METADATA_TYPE_INVALID = 0;
        public static final int CODEC_METADATA_TYPE_MPEG_4 = 1;
        public static final int CODEC_METADATA_TYPE_MPEG_D = 2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.metadataType);
        parcel.writeBoolean(this.isDownmixing);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.metadataType = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.isDownmixing = parcel.readBoolean();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("metadataType: " + this.metadataType);
        stringJoiner.add("isDownmixing: " + this.isDownmixing);
        return "LoudnessCodecInfo" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof LoudnessCodecInfo)) {
            return false;
        }
        LoudnessCodecInfo loudnessCodecInfo = (LoudnessCodecInfo) obj;
        return Objects.deepEquals(Integer.valueOf(this.metadataType), Integer.valueOf(loudnessCodecInfo.metadataType)) && Objects.deepEquals(Boolean.valueOf(this.isDownmixing), Boolean.valueOf(loudnessCodecInfo.isDownmixing));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.metadataType), Boolean.valueOf(this.isDownmixing)).toArray());
    }
}
