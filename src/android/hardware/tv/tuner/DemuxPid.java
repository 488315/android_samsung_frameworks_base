package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxPid implements Parcelable {
    public static final Parcelable.Creator<DemuxPid> CREATOR = new Parcelable.Creator<DemuxPid>() { // from class: android.hardware.tv.tuner.DemuxPid.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxPid createFromParcel(Parcel parcel) {
            return new DemuxPid(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxPid[] newArray(int i) {
            return new DemuxPid[i];
        }
    };
    public static final int mmtpPid = 1;
    public static final int tPid = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int mmtpPid = 1;
        public static final int tPid = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DemuxPid() {
        this._tag = 0;
        this._value = 0;
    }

    private DemuxPid(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxPid(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxPid tPid(int i) {
        return new DemuxPid(0, Integer.valueOf(i));
    }

    public int getTPid() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setTPid(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static DemuxPid mmtpPid(int i) {
        return new DemuxPid(1, Integer.valueOf(i));
    }

    public int getMmtpPid() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setMmtpPid(int i) {
        _set(1, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeInt(getTPid());
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeInt(getMmtpPid());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Integer.valueOf(parcel.readInt()));
        } else if (i == 1) {
            _set(i, Integer.valueOf(parcel.readInt()));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
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
            return "tPid";
        }
        if (i == 1) {
            return "mmtpPid";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
