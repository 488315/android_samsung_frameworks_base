package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellIdentity implements Parcelable {
    public static final Parcelable.Creator<CellIdentity> CREATOR = new Parcelable.Creator<CellIdentity>() { // from class: android.hardware.radio.network.CellIdentity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentity createFromParcel(Parcel parcel) {
            return new CellIdentity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentity[] newArray(int i) {
            return new CellIdentity[i];
        }
    };
    public static final int cdma = 4;
    public static final int gsm = 1;
    public static final int lte = 5;
    public static final int noinit = 0;
    public static final int nr = 6;
    public static final int tdscdma = 3;
    public static final int wcdma = 2;
    private int _tag;
    private Object _value;

    public @interface Tag {

        @Deprecated
        public static final int cdma = 4;
        public static final int gsm = 1;
        public static final int lte = 5;
        public static final int noinit = 0;
        public static final int nr = 6;
        public static final int tdscdma = 3;
        public static final int wcdma = 2;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public CellIdentity() {
        this._tag = 0;
        this._value = false;
    }

    private CellIdentity(Parcel parcel) {
        readFromParcel(parcel);
    }

    private CellIdentity(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static CellIdentity noinit(boolean z) {
        return new CellIdentity(0, Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, Boolean.valueOf(z));
    }

    public static CellIdentity gsm(CellIdentityGsm cellIdentityGsm) {
        return new CellIdentity(1, cellIdentityGsm);
    }

    public CellIdentityGsm getGsm() {
        _assertTag(1);
        return (CellIdentityGsm) this._value;
    }

    public void setGsm(CellIdentityGsm cellIdentityGsm) {
        _set(1, cellIdentityGsm);
    }

    public static CellIdentity wcdma(CellIdentityWcdma cellIdentityWcdma) {
        return new CellIdentity(2, cellIdentityWcdma);
    }

    public CellIdentityWcdma getWcdma() {
        _assertTag(2);
        return (CellIdentityWcdma) this._value;
    }

    public void setWcdma(CellIdentityWcdma cellIdentityWcdma) {
        _set(2, cellIdentityWcdma);
    }

    public static CellIdentity tdscdma(CellIdentityTdscdma cellIdentityTdscdma) {
        return new CellIdentity(3, cellIdentityTdscdma);
    }

    public CellIdentityTdscdma getTdscdma() {
        _assertTag(3);
        return (CellIdentityTdscdma) this._value;
    }

    public void setTdscdma(CellIdentityTdscdma cellIdentityTdscdma) {
        _set(3, cellIdentityTdscdma);
    }

    @Deprecated
    public static CellIdentity cdma(CellIdentityCdma cellIdentityCdma) {
        return new CellIdentity(4, cellIdentityCdma);
    }

    public CellIdentityCdma getCdma() {
        _assertTag(4);
        return (CellIdentityCdma) this._value;
    }

    public void setCdma(CellIdentityCdma cellIdentityCdma) {
        _set(4, cellIdentityCdma);
    }

    public static CellIdentity lte(CellIdentityLte cellIdentityLte) {
        return new CellIdentity(5, cellIdentityLte);
    }

    public CellIdentityLte getLte() {
        _assertTag(5);
        return (CellIdentityLte) this._value;
    }

    public void setLte(CellIdentityLte cellIdentityLte) {
        _set(5, cellIdentityLte);
    }

    public static CellIdentity nr(CellIdentityNr cellIdentityNr) {
        return new CellIdentity(6, cellIdentityNr);
    }

    public CellIdentityNr getNr() {
        _assertTag(6);
        return (CellIdentityNr) this._value;
    }

    public void setNr(CellIdentityNr cellIdentityNr) {
        _set(6, cellIdentityNr);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeBoolean(getNoinit());
                break;
            case 1:
                parcel.writeTypedObject(getGsm(), i);
                break;
            case 2:
                parcel.writeTypedObject(getWcdma(), i);
                break;
            case 3:
                parcel.writeTypedObject(getTdscdma(), i);
                break;
            case 4:
                parcel.writeTypedObject(getCdma(), i);
                break;
            case 5:
                parcel.writeTypedObject(getLte(), i);
                break;
            case 6:
                parcel.writeTypedObject(getNr(), i);
                break;
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, Boolean.valueOf(parcel.readBoolean()));
                return;
            case 1:
                _set(readInt, (CellIdentityGsm) parcel.readTypedObject(CellIdentityGsm.CREATOR));
                return;
            case 2:
                _set(readInt, (CellIdentityWcdma) parcel.readTypedObject(CellIdentityWcdma.CREATOR));
                return;
            case 3:
                _set(readInt, (CellIdentityTdscdma) parcel.readTypedObject(CellIdentityTdscdma.CREATOR));
                return;
            case 4:
                _set(readInt, (CellIdentityCdma) parcel.readTypedObject(CellIdentityCdma.CREATOR));
                return;
            case 5:
                _set(readInt, (CellIdentityLte) parcel.readTypedObject(CellIdentityLte.CREATOR));
                return;
            case 6:
                _set(readInt, (CellIdentityNr) parcel.readTypedObject(CellIdentityNr.CREATOR));
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 1:
                return describeContents(getGsm());
            case 2:
                return describeContents(getWcdma());
            case 3:
                return describeContents(getTdscdma());
            case 4:
                return describeContents(getCdma());
            case 5:
                return describeContents(getLte());
            case 6:
                return describeContents(getNr());
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

    public String toString() {
        switch (this._tag) {
            case 0:
                return "CellIdentity.noinit(" + getNoinit() + NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "CellIdentity.gsm(" + Objects.toString(getGsm()) + NavigationBarInflaterView.KEY_CODE_END;
            case 2:
                return "CellIdentity.wcdma(" + Objects.toString(getWcdma()) + NavigationBarInflaterView.KEY_CODE_END;
            case 3:
                return "CellIdentity.tdscdma(" + Objects.toString(getTdscdma()) + NavigationBarInflaterView.KEY_CODE_END;
            case 4:
                return "CellIdentity.cdma(" + Objects.toString(getCdma()) + NavigationBarInflaterView.KEY_CODE_END;
            case 5:
                return "CellIdentity.lte(" + Objects.toString(getLte()) + NavigationBarInflaterView.KEY_CODE_END;
            case 6:
                return "CellIdentity.nr(" + Objects.toString(getNr()) + NavigationBarInflaterView.KEY_CODE_END;
            default:
                throw new IllegalStateException("unknown field: " + this._tag);
        }
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
                return "noinit";
            case 1:
                return "gsm";
            case 2:
                return "wcdma";
            case 3:
                return "tdscdma";
            case 4:
                return "cdma";
            case 5:
                return "lte";
            case 6:
                return "nr";
            default:
                throw new IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
