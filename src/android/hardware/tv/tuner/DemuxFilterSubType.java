package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxFilterSubType implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterSubType> CREATOR = new Parcelable.Creator<DemuxFilterSubType>() { // from class: android.hardware.tv.tuner.DemuxFilterSubType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSubType createFromParcel(Parcel parcel) {
            return new DemuxFilterSubType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSubType[] newArray(int i) {
            return new DemuxFilterSubType[i];
        }
    };
    public static final int alpFilterType = 4;
    public static final int ipFilterType = 2;
    public static final int mmtpFilterType = 1;
    public static final int tlvFilterType = 3;
    public static final int tsFilterType = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int alpFilterType = 4;
        public static final int ipFilterType = 2;
        public static final int mmtpFilterType = 1;
        public static final int tlvFilterType = 3;
        public static final int tsFilterType = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DemuxFilterSubType() {
        this._tag = 0;
        this._value = 0;
    }

    private DemuxFilterSubType(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterSubType(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxFilterSubType tsFilterType(int i) {
        return new DemuxFilterSubType(0, Integer.valueOf(i));
    }

    public int getTsFilterType() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setTsFilterType(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static DemuxFilterSubType mmtpFilterType(int i) {
        return new DemuxFilterSubType(1, Integer.valueOf(i));
    }

    public int getMmtpFilterType() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setMmtpFilterType(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static DemuxFilterSubType ipFilterType(int i) {
        return new DemuxFilterSubType(2, Integer.valueOf(i));
    }

    public int getIpFilterType() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setIpFilterType(int i) {
        _set(2, Integer.valueOf(i));
    }

    public static DemuxFilterSubType tlvFilterType(int i) {
        return new DemuxFilterSubType(3, Integer.valueOf(i));
    }

    public int getTlvFilterType() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setTlvFilterType(int i) {
        _set(3, Integer.valueOf(i));
    }

    public static DemuxFilterSubType alpFilterType(int i) {
        return new DemuxFilterSubType(4, Integer.valueOf(i));
    }

    public int getAlpFilterType() {
        _assertTag(4);
        return ((Integer) this._value).intValue();
    }

    public void setAlpFilterType(int i) {
        _set(4, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeInt(getTsFilterType());
            return;
        }
        if (i2 == 1) {
            parcel.writeInt(getMmtpFilterType());
            return;
        }
        if (i2 == 2) {
            parcel.writeInt(getIpFilterType());
        } else if (i2 == 3) {
            parcel.writeInt(getTlvFilterType());
        } else {
            if (i2 != 4) {
                return;
            }
            parcel.writeInt(getAlpFilterType());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (readInt == 1) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (readInt == 2) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (readInt == 3) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
        } else if (readInt == 4) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        getTag();
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
            return "tsFilterType";
        }
        if (i == 1) {
            return "mmtpFilterType";
        }
        if (i == 2) {
            return "ipFilterType";
        }
        if (i == 3) {
            return "tlvFilterType";
        }
        if (i == 4) {
            return "alpFilterType";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
