package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxFilterSectionSettingsCondition implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterSectionSettingsCondition> CREATOR = new Parcelable.Creator<DemuxFilterSectionSettingsCondition>() { // from class: android.hardware.tv.tuner.DemuxFilterSectionSettingsCondition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSectionSettingsCondition createFromParcel(Parcel parcel) {
            return new DemuxFilterSectionSettingsCondition(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterSectionSettingsCondition[] newArray(int i) {
            return new DemuxFilterSectionSettingsCondition[i];
        }
    };
    public static final int sectionBits = 0;
    public static final int tableInfo = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int sectionBits = 0;
        public static final int tableInfo = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DemuxFilterSectionSettingsCondition() {
        this._tag = 0;
        this._value = null;
    }

    private DemuxFilterSectionSettingsCondition(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterSectionSettingsCondition(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxFilterSectionSettingsCondition sectionBits(DemuxFilterSectionBits demuxFilterSectionBits) {
        return new DemuxFilterSectionSettingsCondition(0, demuxFilterSectionBits);
    }

    public DemuxFilterSectionBits getSectionBits() {
        _assertTag(0);
        return (DemuxFilterSectionBits) this._value;
    }

    public void setSectionBits(DemuxFilterSectionBits demuxFilterSectionBits) {
        _set(0, demuxFilterSectionBits);
    }

    public static DemuxFilterSectionSettingsCondition tableInfo(DemuxFilterSectionSettingsConditionTableInfo demuxFilterSectionSettingsConditionTableInfo) {
        return new DemuxFilterSectionSettingsCondition(1, demuxFilterSectionSettingsConditionTableInfo);
    }

    public DemuxFilterSectionSettingsConditionTableInfo getTableInfo() {
        _assertTag(1);
        return (DemuxFilterSectionSettingsConditionTableInfo) this._value;
    }

    public void setTableInfo(DemuxFilterSectionSettingsConditionTableInfo demuxFilterSectionSettingsConditionTableInfo) {
        _set(1, demuxFilterSectionSettingsConditionTableInfo);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeTypedObject(getSectionBits(), i);
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeTypedObject(getTableInfo(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, (DemuxFilterSectionBits) parcel.readTypedObject(DemuxFilterSectionBits.CREATOR));
        } else if (readInt == 1) {
            _set(readInt, (DemuxFilterSectionSettingsConditionTableInfo) parcel.readTypedObject(DemuxFilterSectionSettingsConditionTableInfo.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 0) {
            return describeContents(getSectionBits());
        }
        if (tag != 1) {
            return 0;
        }
        return describeContents(getTableInfo());
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
            return "sectionBits";
        }
        if (i == 1) {
            return "tableInfo";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
