package android.hardware.tv.tuner;

import android.app.jank.AppJankStats;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DvrSettings implements Parcelable {
    public static final Parcelable.Creator<DvrSettings> CREATOR = new Parcelable.Creator<DvrSettings>() { // from class: android.hardware.tv.tuner.DvrSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DvrSettings createFromParcel(Parcel parcel) {
            return new DvrSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DvrSettings[] newArray(int i) {
            return new DvrSettings[i];
        }
    };
    public static final int playback = 1;
    public static final int record = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int playback = 1;
        public static final int record = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DvrSettings() {
        this._tag = 0;
        this._value = null;
    }

    private DvrSettings(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DvrSettings(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DvrSettings record(RecordSettings recordSettings) {
        return new DvrSettings(0, recordSettings);
    }

    public RecordSettings getRecord() {
        _assertTag(0);
        return (RecordSettings) this._value;
    }

    public void setRecord(RecordSettings recordSettings) {
        _set(0, recordSettings);
    }

    public static DvrSettings playback(PlaybackSettings playbackSettings) {
        return new DvrSettings(1, playbackSettings);
    }

    public PlaybackSettings getPlayback() {
        _assertTag(1);
        return (PlaybackSettings) this._value;
    }

    public void setPlayback(PlaybackSettings playbackSettings) {
        _set(1, playbackSettings);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeTypedObject(getRecord(), i);
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeTypedObject(getPlayback(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, (RecordSettings) parcel.readTypedObject(RecordSettings.CREATOR));
        } else if (readInt == 1) {
            _set(readInt, (PlaybackSettings) parcel.readTypedObject(PlaybackSettings.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 0) {
            return describeContents(getRecord());
        }
        if (tag != 1) {
            return 0;
        }
        return describeContents(getPlayback());
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
            return "record";
        }
        if (i == 1) {
            return AppJankStats.WIDGET_STATE_PLAYBACK;
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
