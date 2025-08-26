package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxTlvFilterSettingsFilterSettings implements Parcelable {
    public static final Parcelable.Creator<DemuxTlvFilterSettingsFilterSettings> CREATOR = new Parcelable.Creator<DemuxTlvFilterSettingsFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxTlvFilterSettingsFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxTlvFilterSettingsFilterSettings createFromParcel(Parcel parcel) {
            return new DemuxTlvFilterSettingsFilterSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxTlvFilterSettingsFilterSettings[] newArray(int i) {
            return new DemuxTlvFilterSettingsFilterSettings[i];
        }
    };
    public static final int bPassthrough = 2;
    public static final int noinit = 0;
    public static final int section = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int bPassthrough = 2;
        public static final int noinit = 0;
        public static final int section = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DemuxTlvFilterSettingsFilterSettings() {
        this._tag = 0;
        this._value = false;
    }

    private DemuxTlvFilterSettingsFilterSettings(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxTlvFilterSettingsFilterSettings(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxTlvFilterSettingsFilterSettings noinit(boolean z) {
        return new DemuxTlvFilterSettingsFilterSettings(0, Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, Boolean.valueOf(z));
    }

    public static DemuxTlvFilterSettingsFilterSettings section(DemuxFilterSectionSettings demuxFilterSectionSettings) {
        return new DemuxTlvFilterSettingsFilterSettings(1, demuxFilterSectionSettings);
    }

    public DemuxFilterSectionSettings getSection() {
        _assertTag(1);
        return (DemuxFilterSectionSettings) this._value;
    }

    public void setSection(DemuxFilterSectionSettings demuxFilterSectionSettings) {
        _set(1, demuxFilterSectionSettings);
    }

    public static DemuxTlvFilterSettingsFilterSettings bPassthrough(boolean z) {
        return new DemuxTlvFilterSettingsFilterSettings(2, Boolean.valueOf(z));
    }

    public boolean getBPassthrough() {
        _assertTag(2);
        return ((Boolean) this._value).booleanValue();
    }

    public void setBPassthrough(boolean z) {
        _set(2, Boolean.valueOf(z));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeBoolean(getNoinit());
        } else if (i2 == 1) {
            parcel.writeTypedObject(getSection(), i);
        } else {
            if (i2 != 2) {
                return;
            }
            parcel.writeBoolean(getBPassthrough());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Boolean.valueOf(parcel.readBoolean()));
            return;
        }
        if (i == 1) {
            _set(i, (DemuxFilterSectionSettings) parcel.readTypedObject(DemuxFilterSectionSettings.CREATOR));
        } else if (i == 2) {
            _set(i, Boolean.valueOf(parcel.readBoolean()));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (getTag() != 1) {
            return 0;
        }
        return describeContents(getSection());
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
            return "noinit";
        }
        if (i == 1) {
            return "section";
        }
        if (i == 2) {
            return "bPassthrough";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
