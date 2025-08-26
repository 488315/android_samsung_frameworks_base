package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendCapabilities implements Parcelable {
    public static final Parcelable.Creator<FrontendCapabilities> CREATOR = new Parcelable.Creator<FrontendCapabilities>() { // from class: android.hardware.tv.tuner.FrontendCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendCapabilities createFromParcel(Parcel parcel) {
            return new FrontendCapabilities(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendCapabilities[] newArray(int i) {
            return new FrontendCapabilities[i];
        }
    };
    public static final int analogCaps = 0;
    public static final int atsc3Caps = 2;
    public static final int atscCaps = 1;
    public static final int dtmbCaps = 3;
    public static final int dvbcCaps = 5;
    public static final int dvbsCaps = 4;
    public static final int dvbtCaps = 6;
    public static final int iptvCaps = 10;
    public static final int isdbs3Caps = 8;
    public static final int isdbsCaps = 7;
    public static final int isdbtCaps = 9;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int analogCaps = 0;
        public static final int atsc3Caps = 2;
        public static final int atscCaps = 1;
        public static final int dtmbCaps = 3;
        public static final int dvbcCaps = 5;
        public static final int dvbsCaps = 4;
        public static final int dvbtCaps = 6;
        public static final int iptvCaps = 10;
        public static final int isdbs3Caps = 8;
        public static final int isdbsCaps = 7;
        public static final int isdbtCaps = 9;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public FrontendCapabilities() {
        this._tag = 0;
        this._value = null;
    }

    private FrontendCapabilities(Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendCapabilities(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendCapabilities analogCaps(FrontendAnalogCapabilities frontendAnalogCapabilities) {
        return new FrontendCapabilities(0, frontendAnalogCapabilities);
    }

    public FrontendAnalogCapabilities getAnalogCaps() {
        _assertTag(0);
        return (FrontendAnalogCapabilities) this._value;
    }

    public void setAnalogCaps(FrontendAnalogCapabilities frontendAnalogCapabilities) {
        _set(0, frontendAnalogCapabilities);
    }

    public static FrontendCapabilities atscCaps(FrontendAtscCapabilities frontendAtscCapabilities) {
        return new FrontendCapabilities(1, frontendAtscCapabilities);
    }

    public FrontendAtscCapabilities getAtscCaps() {
        _assertTag(1);
        return (FrontendAtscCapabilities) this._value;
    }

    public void setAtscCaps(FrontendAtscCapabilities frontendAtscCapabilities) {
        _set(1, frontendAtscCapabilities);
    }

    public static FrontendCapabilities atsc3Caps(FrontendAtsc3Capabilities frontendAtsc3Capabilities) {
        return new FrontendCapabilities(2, frontendAtsc3Capabilities);
    }

    public FrontendAtsc3Capabilities getAtsc3Caps() {
        _assertTag(2);
        return (FrontendAtsc3Capabilities) this._value;
    }

    public void setAtsc3Caps(FrontendAtsc3Capabilities frontendAtsc3Capabilities) {
        _set(2, frontendAtsc3Capabilities);
    }

    public static FrontendCapabilities dtmbCaps(FrontendDtmbCapabilities frontendDtmbCapabilities) {
        return new FrontendCapabilities(3, frontendDtmbCapabilities);
    }

    public FrontendDtmbCapabilities getDtmbCaps() {
        _assertTag(3);
        return (FrontendDtmbCapabilities) this._value;
    }

    public void setDtmbCaps(FrontendDtmbCapabilities frontendDtmbCapabilities) {
        _set(3, frontendDtmbCapabilities);
    }

    public static FrontendCapabilities dvbsCaps(FrontendDvbsCapabilities frontendDvbsCapabilities) {
        return new FrontendCapabilities(4, frontendDvbsCapabilities);
    }

    public FrontendDvbsCapabilities getDvbsCaps() {
        _assertTag(4);
        return (FrontendDvbsCapabilities) this._value;
    }

    public void setDvbsCaps(FrontendDvbsCapabilities frontendDvbsCapabilities) {
        _set(4, frontendDvbsCapabilities);
    }

    public static FrontendCapabilities dvbcCaps(FrontendDvbcCapabilities frontendDvbcCapabilities) {
        return new FrontendCapabilities(5, frontendDvbcCapabilities);
    }

    public FrontendDvbcCapabilities getDvbcCaps() {
        _assertTag(5);
        return (FrontendDvbcCapabilities) this._value;
    }

    public void setDvbcCaps(FrontendDvbcCapabilities frontendDvbcCapabilities) {
        _set(5, frontendDvbcCapabilities);
    }

    public static FrontendCapabilities dvbtCaps(FrontendDvbtCapabilities frontendDvbtCapabilities) {
        return new FrontendCapabilities(6, frontendDvbtCapabilities);
    }

    public FrontendDvbtCapabilities getDvbtCaps() {
        _assertTag(6);
        return (FrontendDvbtCapabilities) this._value;
    }

    public void setDvbtCaps(FrontendDvbtCapabilities frontendDvbtCapabilities) {
        _set(6, frontendDvbtCapabilities);
    }

    public static FrontendCapabilities isdbsCaps(FrontendIsdbsCapabilities frontendIsdbsCapabilities) {
        return new FrontendCapabilities(7, frontendIsdbsCapabilities);
    }

    public FrontendIsdbsCapabilities getIsdbsCaps() {
        _assertTag(7);
        return (FrontendIsdbsCapabilities) this._value;
    }

    public void setIsdbsCaps(FrontendIsdbsCapabilities frontendIsdbsCapabilities) {
        _set(7, frontendIsdbsCapabilities);
    }

    public static FrontendCapabilities isdbs3Caps(FrontendIsdbs3Capabilities frontendIsdbs3Capabilities) {
        return new FrontendCapabilities(8, frontendIsdbs3Capabilities);
    }

    public FrontendIsdbs3Capabilities getIsdbs3Caps() {
        _assertTag(8);
        return (FrontendIsdbs3Capabilities) this._value;
    }

    public void setIsdbs3Caps(FrontendIsdbs3Capabilities frontendIsdbs3Capabilities) {
        _set(8, frontendIsdbs3Capabilities);
    }

    public static FrontendCapabilities isdbtCaps(FrontendIsdbtCapabilities frontendIsdbtCapabilities) {
        return new FrontendCapabilities(9, frontendIsdbtCapabilities);
    }

    public FrontendIsdbtCapabilities getIsdbtCaps() {
        _assertTag(9);
        return (FrontendIsdbtCapabilities) this._value;
    }

    public void setIsdbtCaps(FrontendIsdbtCapabilities frontendIsdbtCapabilities) {
        _set(9, frontendIsdbtCapabilities);
    }

    public static FrontendCapabilities iptvCaps(FrontendIptvCapabilities frontendIptvCapabilities) {
        return new FrontendCapabilities(10, frontendIptvCapabilities);
    }

    public FrontendIptvCapabilities getIptvCaps() {
        _assertTag(10);
        return (FrontendIptvCapabilities) this._value;
    }

    public void setIptvCaps(FrontendIptvCapabilities frontendIptvCapabilities) {
        _set(10, frontendIptvCapabilities);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeTypedObject(getAnalogCaps(), i);
                break;
            case 1:
                parcel.writeTypedObject(getAtscCaps(), i);
                break;
            case 2:
                parcel.writeTypedObject(getAtsc3Caps(), i);
                break;
            case 3:
                parcel.writeTypedObject(getDtmbCaps(), i);
                break;
            case 4:
                parcel.writeTypedObject(getDvbsCaps(), i);
                break;
            case 5:
                parcel.writeTypedObject(getDvbcCaps(), i);
                break;
            case 6:
                parcel.writeTypedObject(getDvbtCaps(), i);
                break;
            case 7:
                parcel.writeTypedObject(getIsdbsCaps(), i);
                break;
            case 8:
                parcel.writeTypedObject(getIsdbs3Caps(), i);
                break;
            case 9:
                parcel.writeTypedObject(getIsdbtCaps(), i);
                break;
            case 10:
                parcel.writeTypedObject(getIptvCaps(), i);
                break;
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        switch (i) {
            case 0:
                _set(i, (FrontendAnalogCapabilities) parcel.readTypedObject(FrontendAnalogCapabilities.CREATOR));
                return;
            case 1:
                _set(i, (FrontendAtscCapabilities) parcel.readTypedObject(FrontendAtscCapabilities.CREATOR));
                return;
            case 2:
                _set(i, (FrontendAtsc3Capabilities) parcel.readTypedObject(FrontendAtsc3Capabilities.CREATOR));
                return;
            case 3:
                _set(i, (FrontendDtmbCapabilities) parcel.readTypedObject(FrontendDtmbCapabilities.CREATOR));
                return;
            case 4:
                _set(i, (FrontendDvbsCapabilities) parcel.readTypedObject(FrontendDvbsCapabilities.CREATOR));
                return;
            case 5:
                _set(i, (FrontendDvbcCapabilities) parcel.readTypedObject(FrontendDvbcCapabilities.CREATOR));
                return;
            case 6:
                _set(i, (FrontendDvbtCapabilities) parcel.readTypedObject(FrontendDvbtCapabilities.CREATOR));
                return;
            case 7:
                _set(i, (FrontendIsdbsCapabilities) parcel.readTypedObject(FrontendIsdbsCapabilities.CREATOR));
                return;
            case 8:
                _set(i, (FrontendIsdbs3Capabilities) parcel.readTypedObject(FrontendIsdbs3Capabilities.CREATOR));
                return;
            case 9:
                _set(i, (FrontendIsdbtCapabilities) parcel.readTypedObject(FrontendIsdbtCapabilities.CREATOR));
                return;
            case 10:
                _set(i, (FrontendIptvCapabilities) parcel.readTypedObject(FrontendIptvCapabilities.CREATOR));
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return describeContents(getAnalogCaps());
            case 1:
                return describeContents(getAtscCaps());
            case 2:
                return describeContents(getAtsc3Caps());
            case 3:
                return describeContents(getDtmbCaps());
            case 4:
                return describeContents(getDvbsCaps());
            case 5:
                return describeContents(getDvbcCaps());
            case 6:
                return describeContents(getDvbtCaps());
            case 7:
                return describeContents(getIsdbsCaps());
            case 8:
                return describeContents(getIsdbs3Caps());
            case 9:
                return describeContents(getIsdbtCaps());
            case 10:
                return describeContents(getIptvCaps());
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
                return "analogCaps";
            case 1:
                return "atscCaps";
            case 2:
                return "atsc3Caps";
            case 3:
                return "dtmbCaps";
            case 4:
                return "dvbsCaps";
            case 5:
                return "dvbcCaps";
            case 6:
                return "dvbtCaps";
            case 7:
                return "isdbsCaps";
            case 8:
                return "isdbs3Caps";
            case 9:
                return "isdbtCaps";
            case 10:
                return "iptvCaps";
            default:
                throw new IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
