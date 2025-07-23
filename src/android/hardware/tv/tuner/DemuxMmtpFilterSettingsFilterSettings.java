package android.hardware.tv.tuner;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxMmtpFilterSettingsFilterSettings implements Parcelable {
    public static final Parcelable.Creator<DemuxMmtpFilterSettingsFilterSettings> CREATOR = new Parcelable.Creator<DemuxMmtpFilterSettingsFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxMmtpFilterSettingsFilterSettings createFromParcel(Parcel parcel) {
            return new DemuxMmtpFilterSettingsFilterSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxMmtpFilterSettingsFilterSettings[] newArray(int i) {
            return new DemuxMmtpFilterSettingsFilterSettings[i];
        }
    };
    public static final int av = 2;
    public static final int download = 5;
    public static final int noinit = 0;
    public static final int pesData = 3;
    public static final int record = 4;
    public static final int section = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int av = 2;
        public static final int download = 5;
        public static final int noinit = 0;
        public static final int pesData = 3;
        public static final int record = 4;
        public static final int section = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DemuxMmtpFilterSettingsFilterSettings() {
        this._tag = 0;
        this._value = false;
    }

    private DemuxMmtpFilterSettingsFilterSettings(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxMmtpFilterSettingsFilterSettings(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxMmtpFilterSettingsFilterSettings noinit(boolean z) {
        return new DemuxMmtpFilterSettingsFilterSettings(0, Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, Boolean.valueOf(z));
    }

    public static DemuxMmtpFilterSettingsFilterSettings section(DemuxFilterSectionSettings demuxFilterSectionSettings) {
        return new DemuxMmtpFilterSettingsFilterSettings(1, demuxFilterSectionSettings);
    }

    public DemuxFilterSectionSettings getSection() {
        _assertTag(1);
        return (DemuxFilterSectionSettings) this._value;
    }

    public void setSection(DemuxFilterSectionSettings demuxFilterSectionSettings) {
        _set(1, demuxFilterSectionSettings);
    }

    public static DemuxMmtpFilterSettingsFilterSettings av(DemuxFilterAvSettings demuxFilterAvSettings) {
        return new DemuxMmtpFilterSettingsFilterSettings(2, demuxFilterAvSettings);
    }

    public DemuxFilterAvSettings getAv() {
        _assertTag(2);
        return (DemuxFilterAvSettings) this._value;
    }

    public void setAv(DemuxFilterAvSettings demuxFilterAvSettings) {
        _set(2, demuxFilterAvSettings);
    }

    public static DemuxMmtpFilterSettingsFilterSettings pesData(DemuxFilterPesDataSettings demuxFilterPesDataSettings) {
        return new DemuxMmtpFilterSettingsFilterSettings(3, demuxFilterPesDataSettings);
    }

    public DemuxFilterPesDataSettings getPesData() {
        _assertTag(3);
        return (DemuxFilterPesDataSettings) this._value;
    }

    public void setPesData(DemuxFilterPesDataSettings demuxFilterPesDataSettings) {
        _set(3, demuxFilterPesDataSettings);
    }

    public static DemuxMmtpFilterSettingsFilterSettings record(DemuxFilterRecordSettings demuxFilterRecordSettings) {
        return new DemuxMmtpFilterSettingsFilterSettings(4, demuxFilterRecordSettings);
    }

    public DemuxFilterRecordSettings getRecord() {
        _assertTag(4);
        return (DemuxFilterRecordSettings) this._value;
    }

    public void setRecord(DemuxFilterRecordSettings demuxFilterRecordSettings) {
        _set(4, demuxFilterRecordSettings);
    }

    public static DemuxMmtpFilterSettingsFilterSettings download(DemuxFilterDownloadSettings demuxFilterDownloadSettings) {
        return new DemuxMmtpFilterSettingsFilterSettings(5, demuxFilterDownloadSettings);
    }

    public DemuxFilterDownloadSettings getDownload() {
        _assertTag(5);
        return (DemuxFilterDownloadSettings) this._value;
    }

    public void setDownload(DemuxFilterDownloadSettings demuxFilterDownloadSettings) {
        _set(5, demuxFilterDownloadSettings);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeBoolean(getNoinit());
            return;
        }
        if (i2 == 1) {
            parcel.writeTypedObject(getSection(), i);
            return;
        }
        if (i2 == 2) {
            parcel.writeTypedObject(getAv(), i);
            return;
        }
        if (i2 == 3) {
            parcel.writeTypedObject(getPesData(), i);
        } else if (i2 == 4) {
            parcel.writeTypedObject(getRecord(), i);
        } else {
            if (i2 != 5) {
                return;
            }
            parcel.writeTypedObject(getDownload(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, Boolean.valueOf(parcel.readBoolean()));
            return;
        }
        if (readInt == 1) {
            _set(readInt, (DemuxFilterSectionSettings) parcel.readTypedObject(DemuxFilterSectionSettings.CREATOR));
            return;
        }
        if (readInt == 2) {
            _set(readInt, (DemuxFilterAvSettings) parcel.readTypedObject(DemuxFilterAvSettings.CREATOR));
            return;
        }
        if (readInt == 3) {
            _set(readInt, (DemuxFilterPesDataSettings) parcel.readTypedObject(DemuxFilterPesDataSettings.CREATOR));
            return;
        }
        if (readInt == 4) {
            _set(readInt, (DemuxFilterRecordSettings) parcel.readTypedObject(DemuxFilterRecordSettings.CREATOR));
        } else if (readInt == 5) {
            _set(readInt, (DemuxFilterDownloadSettings) parcel.readTypedObject(DemuxFilterDownloadSettings.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 1) {
            return describeContents(getSection());
        }
        if (tag == 2) {
            return describeContents(getAv());
        }
        if (tag == 3) {
            return describeContents(getPesData());
        }
        if (tag == 4) {
            return describeContents(getRecord());
        }
        if (tag != 5) {
            return 0;
        }
        return describeContents(getDownload());
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
            return "av";
        }
        if (i == 3) {
            return "pesData";
        }
        if (i == 4) {
            return "record";
        }
        if (i == 5) {
            return Context.DOWNLOAD_SERVICE;
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
