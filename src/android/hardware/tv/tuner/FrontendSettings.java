package android.hardware.tv.tuner;

import android.media.audio.common.AudioDeviceDescription;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendSettings implements Parcelable {
    public static final Parcelable.Creator<FrontendSettings> CREATOR = new Parcelable.Creator<FrontendSettings>() { // from class: android.hardware.tv.tuner.FrontendSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendSettings createFromParcel(Parcel parcel) {
            return new FrontendSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendSettings[] newArray(int i) {
            return new FrontendSettings[i];
        }
    };
    public static final int analog = 0;
    public static final int atsc = 1;
    public static final int atsc3 = 2;
    public static final int dtmb = 9;
    public static final int dvbc = 4;
    public static final int dvbs = 3;
    public static final int dvbt = 5;
    public static final int iptv = 10;
    public static final int isdbs = 6;
    public static final int isdbs3 = 7;
    public static final int isdbt = 8;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int analog = 0;
        public static final int atsc = 1;
        public static final int atsc3 = 2;
        public static final int dtmb = 9;
        public static final int dvbc = 4;
        public static final int dvbs = 3;
        public static final int dvbt = 5;
        public static final int iptv = 10;
        public static final int isdbs = 6;
        public static final int isdbs3 = 7;
        public static final int isdbt = 8;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public FrontendSettings() {
        this._tag = 0;
        this._value = null;
    }

    private FrontendSettings(Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendSettings(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendSettings analog(FrontendAnalogSettings frontendAnalogSettings) {
        return new FrontendSettings(0, frontendAnalogSettings);
    }

    public FrontendAnalogSettings getAnalog() {
        _assertTag(0);
        return (FrontendAnalogSettings) this._value;
    }

    public void setAnalog(FrontendAnalogSettings frontendAnalogSettings) {
        _set(0, frontendAnalogSettings);
    }

    public static FrontendSettings atsc(FrontendAtscSettings frontendAtscSettings) {
        return new FrontendSettings(1, frontendAtscSettings);
    }

    public FrontendAtscSettings getAtsc() {
        _assertTag(1);
        return (FrontendAtscSettings) this._value;
    }

    public void setAtsc(FrontendAtscSettings frontendAtscSettings) {
        _set(1, frontendAtscSettings);
    }

    public static FrontendSettings atsc3(FrontendAtsc3Settings frontendAtsc3Settings) {
        return new FrontendSettings(2, frontendAtsc3Settings);
    }

    public FrontendAtsc3Settings getAtsc3() {
        _assertTag(2);
        return (FrontendAtsc3Settings) this._value;
    }

    public void setAtsc3(FrontendAtsc3Settings frontendAtsc3Settings) {
        _set(2, frontendAtsc3Settings);
    }

    public static FrontendSettings dvbs(FrontendDvbsSettings frontendDvbsSettings) {
        return new FrontendSettings(3, frontendDvbsSettings);
    }

    public FrontendDvbsSettings getDvbs() {
        _assertTag(3);
        return (FrontendDvbsSettings) this._value;
    }

    public void setDvbs(FrontendDvbsSettings frontendDvbsSettings) {
        _set(3, frontendDvbsSettings);
    }

    public static FrontendSettings dvbc(FrontendDvbcSettings frontendDvbcSettings) {
        return new FrontendSettings(4, frontendDvbcSettings);
    }

    public FrontendDvbcSettings getDvbc() {
        _assertTag(4);
        return (FrontendDvbcSettings) this._value;
    }

    public void setDvbc(FrontendDvbcSettings frontendDvbcSettings) {
        _set(4, frontendDvbcSettings);
    }

    public static FrontendSettings dvbt(FrontendDvbtSettings frontendDvbtSettings) {
        return new FrontendSettings(5, frontendDvbtSettings);
    }

    public FrontendDvbtSettings getDvbt() {
        _assertTag(5);
        return (FrontendDvbtSettings) this._value;
    }

    public void setDvbt(FrontendDvbtSettings frontendDvbtSettings) {
        _set(5, frontendDvbtSettings);
    }

    public static FrontendSettings isdbs(FrontendIsdbsSettings frontendIsdbsSettings) {
        return new FrontendSettings(6, frontendIsdbsSettings);
    }

    public FrontendIsdbsSettings getIsdbs() {
        _assertTag(6);
        return (FrontendIsdbsSettings) this._value;
    }

    public void setIsdbs(FrontendIsdbsSettings frontendIsdbsSettings) {
        _set(6, frontendIsdbsSettings);
    }

    public static FrontendSettings isdbs3(FrontendIsdbs3Settings frontendIsdbs3Settings) {
        return new FrontendSettings(7, frontendIsdbs3Settings);
    }

    public FrontendIsdbs3Settings getIsdbs3() {
        _assertTag(7);
        return (FrontendIsdbs3Settings) this._value;
    }

    public void setIsdbs3(FrontendIsdbs3Settings frontendIsdbs3Settings) {
        _set(7, frontendIsdbs3Settings);
    }

    public static FrontendSettings isdbt(FrontendIsdbtSettings frontendIsdbtSettings) {
        return new FrontendSettings(8, frontendIsdbtSettings);
    }

    public FrontendIsdbtSettings getIsdbt() {
        _assertTag(8);
        return (FrontendIsdbtSettings) this._value;
    }

    public void setIsdbt(FrontendIsdbtSettings frontendIsdbtSettings) {
        _set(8, frontendIsdbtSettings);
    }

    public static FrontendSettings dtmb(FrontendDtmbSettings frontendDtmbSettings) {
        return new FrontendSettings(9, frontendDtmbSettings);
    }

    public FrontendDtmbSettings getDtmb() {
        _assertTag(9);
        return (FrontendDtmbSettings) this._value;
    }

    public void setDtmb(FrontendDtmbSettings frontendDtmbSettings) {
        _set(9, frontendDtmbSettings);
    }

    public static FrontendSettings iptv(FrontendIptvSettings frontendIptvSettings) {
        return new FrontendSettings(10, frontendIptvSettings);
    }

    public FrontendIptvSettings getIptv() {
        _assertTag(10);
        return (FrontendIptvSettings) this._value;
    }

    public void setIptv(FrontendIptvSettings frontendIptvSettings) {
        _set(10, frontendIptvSettings);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeTypedObject(getAnalog(), i);
                break;
            case 1:
                parcel.writeTypedObject(getAtsc(), i);
                break;
            case 2:
                parcel.writeTypedObject(getAtsc3(), i);
                break;
            case 3:
                parcel.writeTypedObject(getDvbs(), i);
                break;
            case 4:
                parcel.writeTypedObject(getDvbc(), i);
                break;
            case 5:
                parcel.writeTypedObject(getDvbt(), i);
                break;
            case 6:
                parcel.writeTypedObject(getIsdbs(), i);
                break;
            case 7:
                parcel.writeTypedObject(getIsdbs3(), i);
                break;
            case 8:
                parcel.writeTypedObject(getIsdbt(), i);
                break;
            case 9:
                parcel.writeTypedObject(getDtmb(), i);
                break;
            case 10:
                parcel.writeTypedObject(getIptv(), i);
                break;
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (FrontendAnalogSettings) parcel.readTypedObject(FrontendAnalogSettings.CREATOR));
                return;
            case 1:
                _set(readInt, (FrontendAtscSettings) parcel.readTypedObject(FrontendAtscSettings.CREATOR));
                return;
            case 2:
                _set(readInt, (FrontendAtsc3Settings) parcel.readTypedObject(FrontendAtsc3Settings.CREATOR));
                return;
            case 3:
                _set(readInt, (FrontendDvbsSettings) parcel.readTypedObject(FrontendDvbsSettings.CREATOR));
                return;
            case 4:
                _set(readInt, (FrontendDvbcSettings) parcel.readTypedObject(FrontendDvbcSettings.CREATOR));
                return;
            case 5:
                _set(readInt, (FrontendDvbtSettings) parcel.readTypedObject(FrontendDvbtSettings.CREATOR));
                return;
            case 6:
                _set(readInt, (FrontendIsdbsSettings) parcel.readTypedObject(FrontendIsdbsSettings.CREATOR));
                return;
            case 7:
                _set(readInt, (FrontendIsdbs3Settings) parcel.readTypedObject(FrontendIsdbs3Settings.CREATOR));
                return;
            case 8:
                _set(readInt, (FrontendIsdbtSettings) parcel.readTypedObject(FrontendIsdbtSettings.CREATOR));
                return;
            case 9:
                _set(readInt, (FrontendDtmbSettings) parcel.readTypedObject(FrontendDtmbSettings.CREATOR));
                return;
            case 10:
                _set(readInt, (FrontendIptvSettings) parcel.readTypedObject(FrontendIptvSettings.CREATOR));
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return describeContents(getAnalog());
            case 1:
                return describeContents(getAtsc());
            case 2:
                return describeContents(getAtsc3());
            case 3:
                return describeContents(getDvbs());
            case 4:
                return describeContents(getDvbc());
            case 5:
                return describeContents(getDvbt());
            case 6:
                return describeContents(getIsdbs());
            case 7:
                return describeContents(getIsdbs3());
            case 8:
                return describeContents(getIsdbt());
            case 9:
                return describeContents(getDtmb());
            case 10:
                return describeContents(getIptv());
            default:
                return 0;
        }
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
        switch (i) {
            case 0:
                return AudioDeviceDescription.CONNECTION_ANALOG;
            case 1:
                return "atsc";
            case 2:
                return "atsc3";
            case 3:
                return "dvbs";
            case 4:
                return "dvbc";
            case 5:
                return "dvbt";
            case 6:
                return "isdbs";
            case 7:
                return "isdbs3";
            case 8:
                return "isdbt";
            case 9:
                return "dtmb";
            case 10:
                return "iptv";
            default:
                throw new IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
