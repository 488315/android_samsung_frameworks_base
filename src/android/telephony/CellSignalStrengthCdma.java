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

    /* JADX WARN: Code restructure failed: missing block: B:21:0x004f, code lost:
    
        if (r11 >= (-150)) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0053 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0054 A[RETURN] */
    @Override // android.telephony.CellSignalStrength
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getAsuLevel() {
        /*
            r11 = this;
            int r0 = r11.getCdmaDbm()
            int r11 = r11.getCdmaEcio()
            r1 = 1
            r2 = 2
            r3 = -100
            r4 = 4
            r5 = 8
            r6 = -90
            r7 = 16
            r8 = 99
            r9 = 2147483647(0x7fffffff, float:NaN)
            if (r0 != r9) goto L1c
        L1a:
            r0 = r8
            goto L35
        L1c:
            r10 = -75
            if (r0 < r10) goto L22
            r0 = r7
            goto L35
        L22:
            r10 = -82
            if (r0 < r10) goto L28
            r0 = r5
            goto L35
        L28:
            if (r0 < r6) goto L2c
            r0 = r4
            goto L35
        L2c:
            r10 = -95
            if (r0 < r10) goto L32
            r0 = r2
            goto L35
        L32:
            if (r0 < r3) goto L1a
            r0 = r1
        L35:
            if (r11 != r9) goto L39
        L37:
            r1 = r8
            goto L51
        L39:
            if (r11 < r6) goto L3d
            r1 = r7
            goto L51
        L3d:
            if (r11 < r3) goto L41
            r1 = r5
            goto L51
        L41:
            r3 = -115(0xffffffffffffff8d, float:NaN)
            if (r11 < r3) goto L47
            r1 = r4
            goto L51
        L47:
            r3 = -130(0xffffffffffffff7e, float:NaN)
            if (r11 < r3) goto L4d
            r1 = r2
            goto L51
        L4d:
            r2 = -150(0xffffffffffffff6a, float:NaN)
            if (r11 < r2) goto L37
        L51:
            if (r0 >= r1) goto L54
            return r0
        L54:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.CellSignalStrengthCdma.getAsuLevel():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0043, code lost:
    
        if (r8 >= (-150)) goto L30;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0047 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0048 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getCdmaLevel() {
        /*
            r8 = this;
            int r0 = r8.getCdmaDbm()
            int r8 = r8.getCdmaEcio()
            r1 = 1
            r2 = 2
            r3 = 3
            r4 = 4
            r5 = 0
            r6 = 2147483647(0x7fffffff, float:NaN)
            if (r0 != r6) goto L14
        L12:
            r0 = r5
            goto L2b
        L14:
            r7 = -75
            if (r0 < r7) goto L1a
            r0 = r4
            goto L2b
        L1a:
            r7 = -85
            if (r0 < r7) goto L20
            r0 = r3
            goto L2b
        L20:
            r7 = -95
            if (r0 < r7) goto L26
            r0 = r2
            goto L2b
        L26:
            r7 = -100
            if (r0 < r7) goto L12
            r0 = r1
        L2b:
            if (r8 != r6) goto L2f
        L2d:
            r1 = r5
            goto L45
        L2f:
            r6 = -90
            if (r8 < r6) goto L35
            r1 = r4
            goto L45
        L35:
            r4 = -110(0xffffffffffffff92, float:NaN)
            if (r8 < r4) goto L3b
            r1 = r3
            goto L45
        L3b:
            r3 = -130(0xffffffffffffff7e, float:NaN)
            if (r8 < r3) goto L41
            r1 = r2
            goto L45
        L41:
            r2 = -150(0xffffffffffffff6a, float:NaN)
            if (r8 < r2) goto L2d
        L45:
            if (r0 >= r1) goto L48
            return r0
        L48:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.CellSignalStrengthCdma.getCdmaLevel():int");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0041 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0042 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getEvdoLevel() {
        /*
            r8 = this;
            int r0 = r8.getEvdoDbm()
            int r8 = r8.getEvdoSnr()
            r1 = 2
            r2 = 4
            r3 = 1
            r4 = 3
            r5 = 0
            r6 = 2147483647(0x7fffffff, float:NaN)
            if (r0 != r6) goto L14
        L12:
            r0 = r5
            goto L2b
        L14:
            r7 = -65
            if (r0 < r7) goto L1a
            r0 = r2
            goto L2b
        L1a:
            r7 = -75
            if (r0 < r7) goto L20
            r0 = r4
            goto L2b
        L20:
            r7 = -90
            if (r0 < r7) goto L26
            r0 = r1
            goto L2b
        L26:
            r7 = -105(0xffffffffffffff97, float:NaN)
            if (r0 < r7) goto L12
            r0 = r3
        L2b:
            if (r8 != r6) goto L2f
        L2d:
            r1 = r5
            goto L3f
        L2f:
            r6 = 7
            if (r8 < r6) goto L34
            r1 = r2
            goto L3f
        L34:
            r2 = 5
            if (r8 < r2) goto L39
            r1 = r4
            goto L3f
        L39:
            if (r8 < r4) goto L3c
            goto L3f
        L3c:
            if (r8 < r3) goto L2d
            r1 = r3
        L3f:
            if (r0 >= r1) goto L42
            return r0
        L42:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.CellSignalStrengthCdma.getEvdoLevel():int");
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
