package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class AvStreamType implements Parcelable {
    public static final Parcelable.Creator<AvStreamType> CREATOR = new Parcelable.Creator<AvStreamType>() { // from class: android.hardware.tv.tuner.AvStreamType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AvStreamType createFromParcel(Parcel parcel) {
            return new AvStreamType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AvStreamType[] newArray(int i) {
            return new AvStreamType[i];
        }
    };
    public static final int audio = 1;
    public static final int video = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int audio = 1;
        public static final int video = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public AvStreamType() {
        this._tag = 0;
        this._value = 0;
    }

    private AvStreamType(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AvStreamType(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AvStreamType video(int i) {
        return new AvStreamType(0, Integer.valueOf(i));
    }

    public int getVideo() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setVideo(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static AvStreamType audio(int i) {
        return new AvStreamType(1, Integer.valueOf(i));
    }

    public int getAudio() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setAudio(int i) {
        _set(1, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeInt(getVideo());
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeInt(getAudio());
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
            return "video";
        }
        if (i == 1) {
            return "audio";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
