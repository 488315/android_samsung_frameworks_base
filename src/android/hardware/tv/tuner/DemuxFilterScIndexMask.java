package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxFilterScIndexMask implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterScIndexMask> CREATOR = new Parcelable.Creator<DemuxFilterScIndexMask>() { // from class: android.hardware.tv.tuner.DemuxFilterScIndexMask.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterScIndexMask createFromParcel(Parcel parcel) {
            return new DemuxFilterScIndexMask(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterScIndexMask[] newArray(int i) {
            return new DemuxFilterScIndexMask[i];
        }
    };
    public static final int scAvc = 1;
    public static final int scHevc = 2;
    public static final int scIndex = 0;
    public static final int scVvc = 3;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int scAvc = 1;
        public static final int scHevc = 2;
        public static final int scIndex = 0;
        public static final int scVvc = 3;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DemuxFilterScIndexMask() {
        this._tag = 0;
        this._value = 0;
    }

    private DemuxFilterScIndexMask(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterScIndexMask(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxFilterScIndexMask scIndex(int i) {
        return new DemuxFilterScIndexMask(0, Integer.valueOf(i));
    }

    public int getScIndex() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setScIndex(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static DemuxFilterScIndexMask scAvc(int i) {
        return new DemuxFilterScIndexMask(1, Integer.valueOf(i));
    }

    public int getScAvc() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setScAvc(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static DemuxFilterScIndexMask scHevc(int i) {
        return new DemuxFilterScIndexMask(2, Integer.valueOf(i));
    }

    public int getScHevc() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setScHevc(int i) {
        _set(2, Integer.valueOf(i));
    }

    public static DemuxFilterScIndexMask scVvc(int i) {
        return new DemuxFilterScIndexMask(3, Integer.valueOf(i));
    }

    public int getScVvc() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setScVvc(int i) {
        _set(3, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeInt(getScIndex());
            return;
        }
        if (i2 == 1) {
            parcel.writeInt(getScAvc());
        } else if (i2 == 2) {
            parcel.writeInt(getScHevc());
        } else {
            if (i2 != 3) {
                return;
            }
            parcel.writeInt(getScVvc());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (i == 1) {
            _set(i, Integer.valueOf(parcel.readInt()));
            return;
        }
        if (i == 2) {
            _set(i, Integer.valueOf(parcel.readInt()));
        } else if (i == 3) {
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
            return "scIndex";
        }
        if (i == 1) {
            return "scAvc";
        }
        if (i == 2) {
            return "scHevc";
        }
        if (i == 3) {
            return "scVvc";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
