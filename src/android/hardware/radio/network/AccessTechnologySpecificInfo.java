package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AccessTechnologySpecificInfo implements Parcelable {
    public static final Parcelable.Creator<AccessTechnologySpecificInfo> CREATOR = new Parcelable.Creator<AccessTechnologySpecificInfo>() { // from class: android.hardware.radio.network.AccessTechnologySpecificInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessTechnologySpecificInfo createFromParcel(Parcel parcel) {
            return new AccessTechnologySpecificInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessTechnologySpecificInfo[] newArray(int i) {
            return new AccessTechnologySpecificInfo[i];
        }
    };
    public static final int cdmaInfo = 1;
    public static final int eutranInfo = 2;
    public static final int geranDtmSupported = 4;
    public static final int ngranNrVopsInfo = 3;
    public static final int noinit = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {

        @Deprecated
        public static final int cdmaInfo = 1;
        public static final int eutranInfo = 2;
        public static final int geranDtmSupported = 4;
        public static final int ngranNrVopsInfo = 3;
        public static final int noinit = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public AccessTechnologySpecificInfo() {
        this._tag = 0;
        this._value = false;
    }

    private AccessTechnologySpecificInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AccessTechnologySpecificInfo(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AccessTechnologySpecificInfo noinit(boolean z) {
        return new AccessTechnologySpecificInfo(0, Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, Boolean.valueOf(z));
    }

    @Deprecated
    public static AccessTechnologySpecificInfo cdmaInfo(Cdma2000RegistrationInfo cdma2000RegistrationInfo) {
        return new AccessTechnologySpecificInfo(1, cdma2000RegistrationInfo);
    }

    public Cdma2000RegistrationInfo getCdmaInfo() {
        _assertTag(1);
        return (Cdma2000RegistrationInfo) this._value;
    }

    public void setCdmaInfo(Cdma2000RegistrationInfo cdma2000RegistrationInfo) {
        _set(1, cdma2000RegistrationInfo);
    }

    public static AccessTechnologySpecificInfo eutranInfo(EutranRegistrationInfo eutranRegistrationInfo) {
        return new AccessTechnologySpecificInfo(2, eutranRegistrationInfo);
    }

    public EutranRegistrationInfo getEutranInfo() {
        _assertTag(2);
        return (EutranRegistrationInfo) this._value;
    }

    public void setEutranInfo(EutranRegistrationInfo eutranRegistrationInfo) {
        _set(2, eutranRegistrationInfo);
    }

    public static AccessTechnologySpecificInfo ngranNrVopsInfo(NrVopsInfo nrVopsInfo) {
        return new AccessTechnologySpecificInfo(3, nrVopsInfo);
    }

    public NrVopsInfo getNgranNrVopsInfo() {
        _assertTag(3);
        return (NrVopsInfo) this._value;
    }

    public void setNgranNrVopsInfo(NrVopsInfo nrVopsInfo) {
        _set(3, nrVopsInfo);
    }

    public static AccessTechnologySpecificInfo geranDtmSupported(boolean z) {
        return new AccessTechnologySpecificInfo(4, Boolean.valueOf(z));
    }

    public boolean getGeranDtmSupported() {
        _assertTag(4);
        return ((Boolean) this._value).booleanValue();
    }

    public void setGeranDtmSupported(boolean z) {
        _set(4, Boolean.valueOf(z));
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
            parcel.writeTypedObject(getCdmaInfo(), i);
            return;
        }
        if (i2 == 2) {
            parcel.writeTypedObject(getEutranInfo(), i);
        } else if (i2 == 3) {
            parcel.writeTypedObject(getNgranNrVopsInfo(), i);
        } else {
            if (i2 != 4) {
                return;
            }
            parcel.writeBoolean(getGeranDtmSupported());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, Boolean.valueOf(parcel.readBoolean()));
            return;
        }
        if (i == 1) {
            _set(i, (Cdma2000RegistrationInfo) parcel.readTypedObject(Cdma2000RegistrationInfo.CREATOR));
            return;
        }
        if (i == 2) {
            _set(i, (EutranRegistrationInfo) parcel.readTypedObject(EutranRegistrationInfo.CREATOR));
            return;
        }
        if (i == 3) {
            _set(i, (NrVopsInfo) parcel.readTypedObject(NrVopsInfo.CREATOR));
        } else if (i == 4) {
            _set(i, Boolean.valueOf(parcel.readBoolean()));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 1) {
            return describeContents(getCdmaInfo());
        }
        if (tag == 2) {
            return describeContents(getEutranInfo());
        }
        if (tag != 3) {
            return 0;
        }
        return describeContents(getNgranNrVopsInfo());
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    public String toString() {
        int i = this._tag;
        if (i == 0) {
            return "AccessTechnologySpecificInfo.noinit(" + getNoinit() + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 1) {
            return "AccessTechnologySpecificInfo.cdmaInfo(" + Objects.toString(getCdmaInfo()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 2) {
            return "AccessTechnologySpecificInfo.eutranInfo(" + Objects.toString(getEutranInfo()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 3) {
            return "AccessTechnologySpecificInfo.ngranNrVopsInfo(" + Objects.toString(getNgranNrVopsInfo()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 4) {
            return "AccessTechnologySpecificInfo.geranDtmSupported(" + getGeranDtmSupported() + NavigationBarInflaterView.KEY_CODE_END;
        }
        throw new IllegalStateException("unknown field: " + this._tag);
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
            return "cdmaInfo";
        }
        if (i == 2) {
            return "eutranInfo";
        }
        if (i == 3) {
            return "ngranNrVopsInfo";
        }
        if (i == 4) {
            return "geranDtmSupported";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
