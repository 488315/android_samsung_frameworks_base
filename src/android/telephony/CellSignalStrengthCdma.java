package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CellSignalStrengthCdma extends CellSignalStrength implements Parcelable {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "CellSignalStrengthCdma";
    private int mCdmaDbm;
    private int mCdmaEcio;
    private int mEvdoDbm;
    private int mEvdoEcio;
    private int mEvdoSnr;
    private int mLevel;
    private static final CellSignalStrengthCdma sInvalid = new CellSignalStrengthCdma();
    public static final Parcelable.Creator<CellSignalStrengthCdma> CREATOR = new Parcelable.Creator<CellSignalStrengthCdma>() { // from class: android.telephony.CellSignalStrengthCdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellSignalStrengthCdma createFromParcel(Parcel parcel) {
            return new CellSignalStrengthCdma(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellSignalStrengthCdma[] newArray(int i) {
            return new CellSignalStrengthCdma[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CellSignalStrengthCdma() {
        setDefaultValues();
    }

    public CellSignalStrengthCdma(int i, int i2, int i3, int i4, int i5) {
        if (Flags.cleanupCdma()) {
            setDefaultValues();
            return;
        }
        this.mCdmaDbm = inRangeOrUnavailable(i, -120, 0);
        this.mCdmaEcio = inRangeOrUnavailable(i2, -160, 0);
        this.mEvdoDbm = inRangeOrUnavailable(i3, -120, 0);
        this.mEvdoEcio = inRangeOrUnavailable(i4, -160, 0);
        this.mEvdoSnr = inRangeOrUnavailable(i5, 0, 8);
        updateLevel(null, null);
    }

    public CellSignalStrengthCdma(CellSignalStrengthCdma cellSignalStrengthCdma) {
        copyFrom(cellSignalStrengthCdma);
    }

    protected void copyFrom(CellSignalStrengthCdma cellSignalStrengthCdma) {
        if (Flags.cleanupCdma()) {
            setDefaultValues();
            return;
        }
        this.mCdmaDbm = cellSignalStrengthCdma.mCdmaDbm;
        this.mCdmaEcio = cellSignalStrengthCdma.mCdmaEcio;
        this.mEvdoDbm = cellSignalStrengthCdma.mEvdoDbm;
        this.mEvdoEcio = cellSignalStrengthCdma.mEvdoEcio;
        this.mEvdoSnr = cellSignalStrengthCdma.mEvdoSnr;
        this.mLevel = cellSignalStrengthCdma.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public CellSignalStrengthCdma copy() {
        return new CellSignalStrengthCdma(this);
    }

    @Override // android.telephony.CellSignalStrength
    public void setDefaultValues() {
        this.mCdmaDbm = Integer.MAX_VALUE;
        this.mCdmaEcio = Integer.MAX_VALUE;
        this.mEvdoDbm = Integer.MAX_VALUE;
        this.mEvdoEcio = Integer.MAX_VALUE;
        this.mEvdoSnr = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    @Override // android.telephony.CellSignalStrength
    public int getLevel() {
        return this.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public void updateLevel(PersistableBundle persistableBundle, ServiceState serviceState) {
        int cdmaLevel = getCdmaLevel();
        int evdoLevel = getEvdoLevel();
        if (evdoLevel == 0) {
            this.mLevel = getCdmaLevel();
        } else {
            if (cdmaLevel == 0) {
                this.mLevel = getEvdoLevel();
                return;
            }
            if (cdmaLevel >= evdoLevel) {
                cdmaLevel = evdoLevel;
            }
            this.mLevel = cdmaLevel;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:4:0x001a  */
    @Override // android.telephony.CellSignalStrength
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getAsuLevel() {
        int i;
        int cdmaDbm = getCdmaDbm();
        int cdmaEcio = getCdmaEcio();
        int i2 = 1;
        if (cdmaDbm != Integer.MAX_VALUE) {
            i = cdmaDbm >= -75 ? 16 : cdmaDbm >= -82 ? 8 : cdmaDbm >= -90 ? 4 : cdmaDbm >= -95 ? 2 : cdmaDbm >= -100 ? 1 : 99;
        }
        if (cdmaEcio != Integer.MAX_VALUE) {
            if (cdmaEcio >= -90) {
                i2 = 16;
            } else if (cdmaEcio >= -100) {
                i2 = 8;
            } else if (cdmaEcio >= -115) {
                i2 = 4;
            } else if (cdmaEcio >= -130) {
                i2 = 2;
            } else if (cdmaEcio < -150) {
                i2 = 99;
            }
        }
        return i < i2 ? i : i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getCdmaLevel() {
        int i;
        int cdmaDbm = getCdmaDbm();
        int cdmaEcio = getCdmaEcio();
        int i2 = 1;
        if (cdmaDbm != Integer.MAX_VALUE) {
            i = cdmaDbm >= -75 ? 4 : cdmaDbm >= -85 ? 3 : cdmaDbm >= -95 ? 2 : cdmaDbm >= -100 ? 1 : 0;
        }
        if (cdmaEcio != Integer.MAX_VALUE) {
            if (cdmaEcio >= -90) {
                i2 = 4;
            } else if (cdmaEcio >= -110) {
                i2 = 3;
            } else if (cdmaEcio >= -130) {
                i2 = 2;
            } else if (cdmaEcio < -150) {
                i2 = 0;
            }
        }
        return i < i2 ? i : i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getEvdoLevel() {
        int i;
        int evdoDbm = getEvdoDbm();
        int evdoSnr = getEvdoSnr();
        int i2 = 2;
        if (evdoDbm != Integer.MAX_VALUE) {
            i = evdoDbm >= -65 ? 4 : evdoDbm >= -75 ? 3 : evdoDbm >= -90 ? 2 : evdoDbm >= -105 ? 1 : 0;
        }
        if (evdoSnr != Integer.MAX_VALUE) {
            if (evdoSnr >= 7) {
                i2 = 4;
            } else if (evdoSnr >= 5) {
                i2 = 3;
            } else if (evdoSnr < 3) {
                i2 = evdoSnr >= 1 ? 1 : 0;
            }
        }
        return i < i2 ? i : i2;
    }

    public int getEvdoAsuLevel() {
        int evdoDbm = getEvdoDbm();
        int evdoSnr = getEvdoSnr();
        int i = 99;
        int i2 = evdoDbm >= -65 ? 16 : evdoDbm >= -75 ? 8 : evdoDbm >= -85 ? 4 : evdoDbm >= -95 ? 2 : evdoDbm >= -105 ? 1 : 99;
        if (evdoSnr >= 7) {
            i = 16;
        } else if (evdoSnr >= 6) {
            i = 8;
        } else if (evdoSnr >= 5) {
            i = 4;
        } else if (evdoSnr >= 3) {
            i = 2;
        } else if (evdoSnr >= 1) {
            i = 1;
        }
        return i2 < i ? i2 : i;
    }

    @Override // android.telephony.CellSignalStrength
    public int getDbm() {
        int cdmaDbm = getCdmaDbm();
        int evdoDbm = getEvdoDbm();
        return cdmaDbm < evdoDbm ? cdmaDbm : evdoDbm;
    }

    public int getCdmaDbm() {
        return this.mCdmaDbm;
    }

    public void setCdmaDbm(int i) {
        this.mCdmaDbm = i;
    }

    public int getCdmaEcio() {
        return this.mCdmaEcio;
    }

    public void setCdmaEcio(int i) {
        this.mCdmaEcio = i;
    }

    public int getEvdoDbm() {
        return this.mEvdoDbm;
    }

    public void setEvdoDbm(int i) {
        this.mEvdoDbm = i;
    }

    public int getEvdoEcio() {
        return this.mEvdoEcio;
    }

    public void setEvdoEcio(int i) {
        this.mEvdoEcio = i;
    }

    public int getEvdoSnr() {
        return this.mEvdoSnr;
    }

    public void setEvdoSnr(int i) {
        this.mEvdoSnr = i;
    }

    @Override // android.telephony.CellSignalStrength
    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mCdmaDbm), Integer.valueOf(this.mCdmaEcio), Integer.valueOf(this.mEvdoDbm), Integer.valueOf(this.mEvdoEcio), Integer.valueOf(this.mEvdoSnr), Integer.valueOf(this.mLevel));
    }

    @Override // android.telephony.CellSignalStrength
    public boolean isValid() {
        if (Flags.cleanupCdma()) {
            return false;
        }
        return !equals(sInvalid);
    }

    @Override // android.telephony.CellSignalStrength
    public boolean equals(Object obj) {
        if (!(obj instanceof CellSignalStrengthCdma)) {
            return false;
        }
        CellSignalStrengthCdma cellSignalStrengthCdma = (CellSignalStrengthCdma) obj;
        return this.mCdmaDbm == cellSignalStrengthCdma.mCdmaDbm && this.mCdmaEcio == cellSignalStrengthCdma.mCdmaEcio && this.mEvdoDbm == cellSignalStrengthCdma.mEvdoDbm && this.mEvdoEcio == cellSignalStrengthCdma.mEvdoEcio && this.mEvdoSnr == cellSignalStrengthCdma.mEvdoSnr && this.mLevel == cellSignalStrengthCdma.mLevel;
    }

    public String toString() {
        return "CellSignalStrengthCdma: cdmaDbm=" + this.mCdmaDbm + " cdmaEcio=" + this.mCdmaEcio + " evdoDbm=" + this.mEvdoDbm + " evdoEcio=" + this.mEvdoEcio + " evdoSnr=" + this.mEvdoSnr + " level=" + this.mLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCdmaDbm);
        parcel.writeInt(this.mCdmaEcio);
        parcel.writeInt(this.mEvdoDbm);
        parcel.writeInt(this.mEvdoEcio);
        parcel.writeInt(this.mEvdoSnr);
        parcel.writeInt(this.mLevel);
    }

    private CellSignalStrengthCdma(Parcel parcel) {
        this.mCdmaDbm = parcel.readInt();
        this.mCdmaEcio = parcel.readInt();
        this.mEvdoDbm = parcel.readInt();
        this.mEvdoEcio = parcel.readInt();
        this.mEvdoSnr = parcel.readInt();
        this.mLevel = parcel.readInt();
        if (Flags.cleanupCdma()) {
            setDefaultValues();
        }
    }

    private static void log(String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
