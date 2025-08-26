package android.hardware.camera2;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.media.TtmlUtils;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class CameraMetadataInfo implements Parcelable {
    public static final Parcelable.Creator<CameraMetadataInfo> CREATOR = new Parcelable.Creator<CameraMetadataInfo>() { // from class: android.hardware.camera2.CameraMetadataInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraMetadataInfo createFromParcel(Parcel parcel) {
            return new CameraMetadataInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraMetadataInfo[] newArray(int i) {
            return new CameraMetadataInfo[i];
        }
    };
    public static final int fmqSize = 0;
    public static final int metadata = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int fmqSize = 0;
        public static final int metadata = 1;
    }

    public CameraMetadataInfo() {
        this._tag = 0;
        this._value = 0L;
    }

    private CameraMetadataInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    private CameraMetadataInfo(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static CameraMetadataInfo fmqSize(long j) {
        return new CameraMetadataInfo(0, Long.valueOf(j));
    }

    public long getFmqSize() {
        _assertTag(0);
        return ((Long) this._value).longValue();
    }

    public void setFmqSize(long j) {
        _set(0, Long.valueOf(j));
    }

    public static CameraMetadataInfo metadata(CameraMetadataNative cameraMetadataNative) {
        return new CameraMetadataInfo(1, cameraMetadataNative);
    }

    public CameraMetadataNative getMetadata() {
        _assertTag(1);
        return (CameraMetadataNative) this._value;
    }

    public void setMetadata(CameraMetadataNative cameraMetadataNative) {
        _set(1, cameraMetadataNative);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeLong(getFmqSize());
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeTypedObject(getMetadata(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Long.valueOf(parcel.readLong()));
        } else if (i == 1) {
            _set(i, (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (getTag() != 1) {
            return 0;
        }
        return describeContents(getMetadata());
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    private void _assertTag(int i) {
        if (getTag() == i) {
            return;
        }
        throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
    }

    private String _tagString(int i) {
        if (i == 0) {
            return "fmqSize";
        }
        if (i == 1) {
            return TtmlUtils.TAG_METADATA;
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
