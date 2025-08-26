package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellInfoRatSpecificInfo implements Parcelable {
    public static final Parcelable.Creator<CellInfoRatSpecificInfo> CREATOR = new Parcelable.Creator<CellInfoRatSpecificInfo>() { // from class: android.hardware.radio.network.CellInfoRatSpecificInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoRatSpecificInfo createFromParcel(Parcel parcel) {
            return new CellInfoRatSpecificInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellInfoRatSpecificInfo[] newArray(int i) {
            return new CellInfoRatSpecificInfo[i];
        }
    };
    public static final int cdma = 5;
    public static final int gsm = 0;
    public static final int lte = 3;
    public static final int nr = 4;
    public static final int tdscdma = 2;
    public static final int wcdma = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {

        @Deprecated
        public static final int cdma = 5;
        public static final int gsm = 0;
        public static final int lte = 3;
        public static final int nr = 4;
        public static final int tdscdma = 2;
        public static final int wcdma = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public CellInfoRatSpecificInfo() {
        this._tag = 0;
        this._value = null;
    }

    private CellInfoRatSpecificInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    private CellInfoRatSpecificInfo(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static CellInfoRatSpecificInfo gsm(CellInfoGsm cellInfoGsm) {
        return new CellInfoRatSpecificInfo(0, cellInfoGsm);
    }

    public CellInfoGsm getGsm() {
        _assertTag(0);
        return (CellInfoGsm) this._value;
    }

    public void setGsm(CellInfoGsm cellInfoGsm) {
        _set(0, cellInfoGsm);
    }

    public static CellInfoRatSpecificInfo wcdma(CellInfoWcdma cellInfoWcdma) {
        return new CellInfoRatSpecificInfo(1, cellInfoWcdma);
    }

    public CellInfoWcdma getWcdma() {
        _assertTag(1);
        return (CellInfoWcdma) this._value;
    }

    public void setWcdma(CellInfoWcdma cellInfoWcdma) {
        _set(1, cellInfoWcdma);
    }

    public static CellInfoRatSpecificInfo tdscdma(CellInfoTdscdma cellInfoTdscdma) {
        return new CellInfoRatSpecificInfo(2, cellInfoTdscdma);
    }

    public CellInfoTdscdma getTdscdma() {
        _assertTag(2);
        return (CellInfoTdscdma) this._value;
    }

    public void setTdscdma(CellInfoTdscdma cellInfoTdscdma) {
        _set(2, cellInfoTdscdma);
    }

    public static CellInfoRatSpecificInfo lte(CellInfoLte cellInfoLte) {
        return new CellInfoRatSpecificInfo(3, cellInfoLte);
    }

    public CellInfoLte getLte() {
        _assertTag(3);
        return (CellInfoLte) this._value;
    }

    public void setLte(CellInfoLte cellInfoLte) {
        _set(3, cellInfoLte);
    }

    public static CellInfoRatSpecificInfo nr(CellInfoNr cellInfoNr) {
        return new CellInfoRatSpecificInfo(4, cellInfoNr);
    }

    public CellInfoNr getNr() {
        _assertTag(4);
        return (CellInfoNr) this._value;
    }

    public void setNr(CellInfoNr cellInfoNr) {
        _set(4, cellInfoNr);
    }

    @Deprecated
    public static CellInfoRatSpecificInfo cdma(CellInfoCdma cellInfoCdma) {
        return new CellInfoRatSpecificInfo(5, cellInfoCdma);
    }

    public CellInfoCdma getCdma() {
        _assertTag(5);
        return (CellInfoCdma) this._value;
    }

    public void setCdma(CellInfoCdma cellInfoCdma) {
        _set(5, cellInfoCdma);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeTypedObject(getGsm(), i);
            return;
        }
        if (i2 == 1) {
            parcel.writeTypedObject(getWcdma(), i);
            return;
        }
        if (i2 == 2) {
            parcel.writeTypedObject(getTdscdma(), i);
            return;
        }
        if (i2 == 3) {
            parcel.writeTypedObject(getLte(), i);
        } else if (i2 == 4) {
            parcel.writeTypedObject(getNr(), i);
        } else {
            if (i2 != 5) {
                return;
            }
            parcel.writeTypedObject(getCdma(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, (CellInfoGsm) parcel.readTypedObject(CellInfoGsm.CREATOR));
            return;
        }
        if (i == 1) {
            _set(i, (CellInfoWcdma) parcel.readTypedObject(CellInfoWcdma.CREATOR));
            return;
        }
        if (i == 2) {
            _set(i, (CellInfoTdscdma) parcel.readTypedObject(CellInfoTdscdma.CREATOR));
            return;
        }
        if (i == 3) {
            _set(i, (CellInfoLte) parcel.readTypedObject(CellInfoLte.CREATOR));
            return;
        }
        if (i == 4) {
            _set(i, (CellInfoNr) parcel.readTypedObject(CellInfoNr.CREATOR));
        } else if (i == 5) {
            _set(i, (CellInfoCdma) parcel.readTypedObject(CellInfoCdma.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 0) {
            return describeContents(getGsm());
        }
        if (tag == 1) {
            return describeContents(getWcdma());
        }
        if (tag == 2) {
            return describeContents(getTdscdma());
        }
        if (tag == 3) {
            return describeContents(getLte());
        }
        if (tag == 4) {
            return describeContents(getNr());
        }
        if (tag != 5) {
            return 0;
        }
        return describeContents(getCdma());
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
            return "CellInfoRatSpecificInfo.gsm(" + Objects.toString(getGsm()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 1) {
            return "CellInfoRatSpecificInfo.wcdma(" + Objects.toString(getWcdma()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 2) {
            return "CellInfoRatSpecificInfo.tdscdma(" + Objects.toString(getTdscdma()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 3) {
            return "CellInfoRatSpecificInfo.lte(" + Objects.toString(getLte()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 4) {
            return "CellInfoRatSpecificInfo.nr(" + Objects.toString(getNr()) + NavigationBarInflaterView.KEY_CODE_END;
        }
        if (i == 5) {
            return "CellInfoRatSpecificInfo.cdma(" + Objects.toString(getCdma()) + NavigationBarInflaterView.KEY_CODE_END;
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
            return "gsm";
        }
        if (i == 1) {
            return "wcdma";
        }
        if (i == 2) {
            return "tdscdma";
        }
        if (i == 3) {
            return "lte";
        }
        if (i == 4) {
            return "nr";
        }
        if (i == 5) {
            return "cdma";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
