package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxFilterMonitorEvent implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterMonitorEvent> CREATOR = new Parcelable.Creator<DemuxFilterMonitorEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterMonitorEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterMonitorEvent createFromParcel(Parcel parcel) {
            return new DemuxFilterMonitorEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterMonitorEvent[] newArray(int i) {
            return new DemuxFilterMonitorEvent[i];
        }
    };
    public static final int cid = 1;
    public static final int scramblingStatus = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int cid = 1;
        public static final int scramblingStatus = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DemuxFilterMonitorEvent() {
        this._tag = 0;
        this._value = 1;
    }

    private DemuxFilterMonitorEvent(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterMonitorEvent(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxFilterMonitorEvent scramblingStatus(int i) {
        return new DemuxFilterMonitorEvent(0, Integer.valueOf(i));
    }

    public int getScramblingStatus() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setScramblingStatus(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static DemuxFilterMonitorEvent cid(int i) {
        return new DemuxFilterMonitorEvent(1, Integer.valueOf(i));
    }

    public int getCid() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setCid(int i) {
        _set(1, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeInt(getScramblingStatus());
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeInt(getCid());
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
            return "scramblingStatus";
        }
        if (i == 1) {
            return "cid";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
