package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxFilterMediaEventExtraMetaData implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterMediaEventExtraMetaData> CREATOR = new Parcelable.Creator<DemuxFilterMediaEventExtraMetaData>() { // from class: android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterMediaEventExtraMetaData createFromParcel(Parcel parcel) {
            return new DemuxFilterMediaEventExtraMetaData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterMediaEventExtraMetaData[] newArray(int i) {
            return new DemuxFilterMediaEventExtraMetaData[i];
        }
    };
    public static final int audio = 1;
    public static final int audioPresentations = 2;
    public static final int noinit = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int audio = 1;
        public static final int audioPresentations = 2;
        public static final int noinit = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DemuxFilterMediaEventExtraMetaData() {
        this._tag = 0;
        this._value = false;
    }

    private DemuxFilterMediaEventExtraMetaData(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterMediaEventExtraMetaData(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxFilterMediaEventExtraMetaData noinit(boolean z) {
        return new DemuxFilterMediaEventExtraMetaData(0, Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, Boolean.valueOf(z));
    }

    public static DemuxFilterMediaEventExtraMetaData audio(AudioExtraMetaData audioExtraMetaData) {
        return new DemuxFilterMediaEventExtraMetaData(1, audioExtraMetaData);
    }

    public AudioExtraMetaData getAudio() {
        _assertTag(1);
        return (AudioExtraMetaData) this._value;
    }

    public void setAudio(AudioExtraMetaData audioExtraMetaData) {
        _set(1, audioExtraMetaData);
    }

    public static DemuxFilterMediaEventExtraMetaData audioPresentations(AudioPresentation[] audioPresentationArr) {
        return new DemuxFilterMediaEventExtraMetaData(2, audioPresentationArr);
    }

    public AudioPresentation[] getAudioPresentations() {
        _assertTag(2);
        return (AudioPresentation[]) this._value;
    }

    public void setAudioPresentations(AudioPresentation[] audioPresentationArr) {
        _set(2, audioPresentationArr);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeBoolean(getNoinit());
        } else if (i2 == 1) {
            parcel.writeTypedObject(getAudio(), i);
        } else {
            if (i2 != 2) {
                return;
            }
            parcel.writeTypedArray(getAudioPresentations(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Boolean.valueOf(parcel.readBoolean()));
            return;
        }
        if (i == 1) {
            _set(i, (AudioExtraMetaData) parcel.readTypedObject(AudioExtraMetaData.CREATOR));
        } else if (i == 2) {
            _set(i, (AudioPresentation[]) parcel.createTypedArray(AudioPresentation.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 1) {
            return describeContents(getAudio());
        }
        if (tag != 2) {
            return 0;
        }
        return describeContents(getAudioPresentations());
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
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
            return "audio";
        }
        if (i == 2) {
            return "audioPresentations";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
