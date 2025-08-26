package android.media;

import android.app.slice.Slice;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.UserDictionary;
import com.sec.android.iaft.SmLib_IafdConstant;

/* loaded from: classes2.dex */
public final class AudioMixMatchCriterionValue implements Parcelable {
    public static final Parcelable.Creator<AudioMixMatchCriterionValue> CREATOR = new Parcelable.Creator<AudioMixMatchCriterionValue>() { // from class: android.media.AudioMixMatchCriterionValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMixMatchCriterionValue createFromParcel(Parcel parcel) {
            return new AudioMixMatchCriterionValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMixMatchCriterionValue[] newArray(int i) {
            return new AudioMixMatchCriterionValue[i];
        }
    };
    public static final int appid = 6;
    public static final int audioSessionId = 4;
    public static final int pid = 5;
    public static final int source = 1;
    public static final int uid = 2;
    public static final int usage = 0;
    public static final int userId = 3;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int appid = 6;
        public static final int audioSessionId = 4;
        public static final int pid = 5;
        public static final int source = 1;
        public static final int uid = 2;
        public static final int usage = 0;
        public static final int userId = 3;
    }

    public AudioMixMatchCriterionValue() {
        this._tag = 0;
        this._value = 0;
    }

    private AudioMixMatchCriterionValue(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioMixMatchCriterionValue(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AudioMixMatchCriterionValue usage(int i) {
        return new AudioMixMatchCriterionValue(0, Integer.valueOf(i));
    }

    public int getUsage() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setUsage(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static AudioMixMatchCriterionValue source(int i) {
        return new AudioMixMatchCriterionValue(1, Integer.valueOf(i));
    }

    public int getSource() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setSource(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static AudioMixMatchCriterionValue uid(int i) {
        return new AudioMixMatchCriterionValue(2, Integer.valueOf(i));
    }

    public int getUid() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setUid(int i) {
        _set(2, Integer.valueOf(i));
    }

    public static AudioMixMatchCriterionValue userId(int i) {
        return new AudioMixMatchCriterionValue(3, Integer.valueOf(i));
    }

    public int getUserId() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setUserId(int i) {
        _set(3, Integer.valueOf(i));
    }

    public static AudioMixMatchCriterionValue audioSessionId(int i) {
        return new AudioMixMatchCriterionValue(4, Integer.valueOf(i));
    }

    public int getAudioSessionId() {
        _assertTag(4);
        return ((Integer) this._value).intValue();
    }

    public void setAudioSessionId(int i) {
        _set(4, Integer.valueOf(i));
    }

    public static AudioMixMatchCriterionValue pid(int i) {
        return new AudioMixMatchCriterionValue(5, Integer.valueOf(i));
    }

    public int getPid() {
        _assertTag(5);
        return ((Integer) this._value).intValue();
    }

    public void setPid(int i) {
        _set(5, Integer.valueOf(i));
    }

    public static AudioMixMatchCriterionValue appid(int i) {
        return new AudioMixMatchCriterionValue(6, Integer.valueOf(i));
    }

    public int getAppid() {
        _assertTag(6);
        return ((Integer) this._value).intValue();
    }

    public void setAppid(int i) {
        _set(6, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeInt(getUsage());
                break;
            case 1:
                parcel.writeInt(getSource());
                break;
            case 2:
                parcel.writeInt(getUid());
                break;
            case 3:
                parcel.writeInt(getUserId());
                break;
            case 4:
                parcel.writeInt(getAudioSessionId());
                break;
            case 5:
                parcel.writeInt(getPid());
                break;
            case 6:
                parcel.writeInt(getAppid());
                break;
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        switch (i) {
            case 0:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 1:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 2:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 3:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 4:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 5:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            case 6:
                _set(i, Integer.valueOf(parcel.readInt()));
                return;
            default:
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
        switch (i) {
            case 0:
                return "usage";
            case 1:
                return Slice.SUBTYPE_SOURCE;
            case 2:
                return "uid";
            case 3:
                return SmLib_IafdConstant.KEY_USER_ID;
            case 4:
                return "audioSessionId";
            case 5:
                return "pid";
            case 6:
                return UserDictionary.Words.APP_ID;
            default:
                throw new IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
