package android.media;

import android.app.slice.Slice;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.UserDictionary;

import com.sec.android.iaft.SmLib_IafdConstant;

public final class AudioMixMatchCriterionValue implements Parcelable {
    public static final Parcelable.Creator<AudioMixMatchCriterionValue> CREATOR =
            new Parcelable.Creator<AudioMixMatchCriterionValue>() { // from class:
                // android.media.AudioMixMatchCriterionValue.1
                @Override // android.os.Parcelable.Creator
                public AudioMixMatchCriterionValue createFromParcel(Parcel _aidl_source) {
                    return new AudioMixMatchCriterionValue(_aidl_source);
                }

                @Override // android.os.Parcelable.Creator
                public AudioMixMatchCriterionValue[] newArray(int _aidl_size) {
                    return new AudioMixMatchCriterionValue[_aidl_size];
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

    private AudioMixMatchCriterionValue(Parcel _aidl_parcel) {
        readFromParcel(_aidl_parcel);
    }

    private AudioMixMatchCriterionValue(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }

    public int getTag() {
        return this._tag;
    }

    public static AudioMixMatchCriterionValue usage(int _value) {
        return new AudioMixMatchCriterionValue(0, Integer.valueOf(_value));
    }

    public int getUsage() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setUsage(int _value) {
        _set(0, Integer.valueOf(_value));
    }

    public static AudioMixMatchCriterionValue source(int _value) {
        return new AudioMixMatchCriterionValue(1, Integer.valueOf(_value));
    }

    public int getSource() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setSource(int _value) {
        _set(1, Integer.valueOf(_value));
    }

    public static AudioMixMatchCriterionValue uid(int _value) {
        return new AudioMixMatchCriterionValue(2, Integer.valueOf(_value));
    }

    public int getUid() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setUid(int _value) {
        _set(2, Integer.valueOf(_value));
    }

    public static AudioMixMatchCriterionValue userId(int _value) {
        return new AudioMixMatchCriterionValue(3, Integer.valueOf(_value));
    }

    public int getUserId() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setUserId(int _value) {
        _set(3, Integer.valueOf(_value));
    }

    public static AudioMixMatchCriterionValue audioSessionId(int _value) {
        return new AudioMixMatchCriterionValue(4, Integer.valueOf(_value));
    }

    public int getAudioSessionId() {
        _assertTag(4);
        return ((Integer) this._value).intValue();
    }

    public void setAudioSessionId(int _value) {
        _set(4, Integer.valueOf(_value));
    }

    public static AudioMixMatchCriterionValue pid(int _value) {
        return new AudioMixMatchCriterionValue(5, Integer.valueOf(_value));
    }

    public int getPid() {
        _assertTag(5);
        return ((Integer) this._value).intValue();
    }

    public void setPid(int _value) {
        _set(5, Integer.valueOf(_value));
    }

    public static AudioMixMatchCriterionValue appid(int _value) {
        return new AudioMixMatchCriterionValue(6, Integer.valueOf(_value));
    }

    public int getAppid() {
        _assertTag(6);
        return ((Integer) this._value).intValue();
    }

    public void setAppid(int _value) {
        _set(6, Integer.valueOf(_value));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
        _aidl_parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                _aidl_parcel.writeInt(getUsage());
                break;
            case 1:
                _aidl_parcel.writeInt(getSource());
                break;
            case 2:
                _aidl_parcel.writeInt(getUid());
                break;
            case 3:
                _aidl_parcel.writeInt(getUserId());
                break;
            case 4:
                _aidl_parcel.writeInt(getAudioSessionId());
                break;
            case 5:
                _aidl_parcel.writeInt(getPid());
                break;
            case 6:
                _aidl_parcel.writeInt(getAppid());
                break;
        }
    }

    public void readFromParcel(Parcel _aidl_parcel) {
        int _aidl_tag = _aidl_parcel.readInt();
        switch (_aidl_tag) {
            case 0:
                int _aidl_value = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value));
                return;
            case 1:
                int _aidl_value2 = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value2));
                return;
            case 2:
                int _aidl_value3 = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value3));
                return;
            case 3:
                int _aidl_value4 = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value4));
                return;
            case 4:
                int _aidl_value5 = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value5));
                return;
            case 5:
                int _aidl_value6 = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value6));
                return;
            case 6:
                int _aidl_value7 = _aidl_parcel.readInt();
                _set(_aidl_tag, Integer.valueOf(_aidl_value7));
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + _aidl_tag);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        getTag();
        return 0;
    }

    private void _assertTag(int tag) {
        if (getTag() != tag) {
            throw new IllegalStateException(
                    "bad access: "
                            + _tagString(tag)
                            + ", "
                            + _tagString(getTag())
                            + " is available.");
        }
    }

    private String _tagString(int _tag) {
        switch (_tag) {
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
                throw new IllegalStateException("unknown field: " + _tag);
        }
    }

    private void _set(int _tag, Object _value) {
        this._tag = _tag;
        this._value = _value;
    }
}
