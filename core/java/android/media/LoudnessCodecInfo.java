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
        public LoudnessCodecInfo createFromParcel(Parcel _aidl_source) {
            LoudnessCodecInfo _aidl_out = new LoudnessCodecInfo();
            _aidl_out.readFromParcel(_aidl_source);
            return _aidl_out;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LoudnessCodecInfo[] newArray(int _aidl_size) {
            return new LoudnessCodecInfo[_aidl_size];
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
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.writeInt(0);
        _aidl_parcel.writeInt(this.metadataType);
        _aidl_parcel.writeBoolean(this.isDownmixing);
        int _aidl_end_pos = _aidl_parcel.dataPosition();
        _aidl_parcel.setDataPosition(_aidl_start_pos);
        _aidl_parcel.writeInt(_aidl_end_pos - _aidl_start_pos);
        _aidl_parcel.setDataPosition(_aidl_end_pos);
    }

    public final void readFromParcel(Parcel _aidl_parcel) {
        int _aidl_start_pos = _aidl_parcel.dataPosition();
        int _aidl_parcelable_size = _aidl_parcel.readInt();
        try {
            if (_aidl_parcelable_size < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                return;
            }
            this.metadataType = _aidl_parcel.readInt();
            if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            } else {
                this.isDownmixing = _aidl_parcel.readBoolean();
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            }
        } catch (Throwable th) {
            if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
            throw th;
        }
    }

    public String toString() {
        StringJoiner _aidl_sj = new StringJoiner(", ", "{", "}");
        _aidl_sj.add("metadataType: " + this.metadataType);
        _aidl_sj.add("isDownmixing: " + this.isDownmixing);
        return "LoudnessCodecInfo" + _aidl_sj.toString();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof LoudnessCodecInfo)) {
            return false;
        }
        LoudnessCodecInfo that = (LoudnessCodecInfo) other;
        if (Objects.deepEquals(Integer.valueOf(this.metadataType), Integer.valueOf(that.metadataType)) && Objects.deepEquals(Boolean.valueOf(this.isDownmixing), Boolean.valueOf(that.isDownmixing))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.metadataType), Boolean.valueOf(this.isDownmixing)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
