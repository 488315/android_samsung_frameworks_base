package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxFilterSettings implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterSettings> CREATOR = new Parcelable.Creator<DemuxFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSettings createFromParcel(Parcel parcel) {
            return new DemuxFilterSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSettings[] newArray(int i) {
            return new DemuxFilterSettings[i];
        }
    };
    public static final int alp = 4;
    public static final int ip = 2;
    public static final int mmtp = 1;
    public static final int tlv = 3;
    public static final int ts = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int alp = 4;
        public static final int ip = 2;
        public static final int mmtp = 1;
        public static final int tlv = 3;
        public static final int ts = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DemuxFilterSettings() {
        this._tag = 0;
        this._value = null;
    }

    private DemuxFilterSettings(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterSettings(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxFilterSettings ts(DemuxTsFilterSettings demuxTsFilterSettings) {
        return new DemuxFilterSettings(0, demuxTsFilterSettings);
    }

    public DemuxTsFilterSettings getTs() {
        _assertTag(0);
        return (DemuxTsFilterSettings) this._value;
    }

    public void setTs(DemuxTsFilterSettings demuxTsFilterSettings) {
        _set(0, demuxTsFilterSettings);
    }

    public static DemuxFilterSettings mmtp(DemuxMmtpFilterSettings demuxMmtpFilterSettings) {
        return new DemuxFilterSettings(1, demuxMmtpFilterSettings);
    }

    public DemuxMmtpFilterSettings getMmtp() {
        _assertTag(1);
        return (DemuxMmtpFilterSettings) this._value;
    }

    public void setMmtp(DemuxMmtpFilterSettings demuxMmtpFilterSettings) {
        _set(1, demuxMmtpFilterSettings);
    }

    public static DemuxFilterSettings ip(DemuxIpFilterSettings demuxIpFilterSettings) {
        return new DemuxFilterSettings(2, demuxIpFilterSettings);
    }

    public DemuxIpFilterSettings getIp() {
        _assertTag(2);
        return (DemuxIpFilterSettings) this._value;
    }

    public void setIp(DemuxIpFilterSettings demuxIpFilterSettings) {
        _set(2, demuxIpFilterSettings);
    }

    public static DemuxFilterSettings tlv(DemuxTlvFilterSettings demuxTlvFilterSettings) {
        return new DemuxFilterSettings(3, demuxTlvFilterSettings);
    }

    public DemuxTlvFilterSettings getTlv() {
        _assertTag(3);
        return (DemuxTlvFilterSettings) this._value;
    }

    public void setTlv(DemuxTlvFilterSettings demuxTlvFilterSettings) {
        _set(3, demuxTlvFilterSettings);
    }

    public static DemuxFilterSettings alp(DemuxAlpFilterSettings demuxAlpFilterSettings) {
        return new DemuxFilterSettings(4, demuxAlpFilterSettings);
    }

    public DemuxAlpFilterSettings getAlp() {
        _assertTag(4);
        return (DemuxAlpFilterSettings) this._value;
    }

    public void setAlp(DemuxAlpFilterSettings demuxAlpFilterSettings) {
        _set(4, demuxAlpFilterSettings);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeTypedObject(getTs(), i);
            return;
        }
        if (i2 == 1) {
            parcel.writeTypedObject(getMmtp(), i);
            return;
        }
        if (i2 == 2) {
            parcel.writeTypedObject(getIp(), i);
        } else if (i2 == 3) {
            parcel.writeTypedObject(getTlv(), i);
        } else {
            if (i2 != 4) {
                return;
            }
            parcel.writeTypedObject(getAlp(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, (DemuxTsFilterSettings) parcel.readTypedObject(DemuxTsFilterSettings.CREATOR));
            return;
        }
        if (readInt == 1) {
            _set(readInt, (DemuxMmtpFilterSettings) parcel.readTypedObject(DemuxMmtpFilterSettings.CREATOR));
            return;
        }
        if (readInt == 2) {
            _set(readInt, (DemuxIpFilterSettings) parcel.readTypedObject(DemuxIpFilterSettings.CREATOR));
            return;
        }
        if (readInt == 3) {
            _set(readInt, (DemuxTlvFilterSettings) parcel.readTypedObject(DemuxTlvFilterSettings.CREATOR));
        } else if (readInt == 4) {
            _set(readInt, (DemuxAlpFilterSettings) parcel.readTypedObject(DemuxAlpFilterSettings.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 0) {
            return describeContents(getTs());
        }
        if (tag == 1) {
            return describeContents(getMmtp());
        }
        if (tag == 2) {
            return describeContents(getIp());
        }
        if (tag == 3) {
            return describeContents(getTlv());
        }
        if (tag != 4) {
            return 0;
        }
        return describeContents(getAlp());
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
            return "ts";
        }
        if (i == 1) {
            return "mmtp";
        }
        if (i == 2) {
            return "ip";
        }
        if (i == 3) {
            return "tlv";
        }
        if (i == 4) {
            return "alp";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
