package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.ArraySet;
import com.android.internal.telephony.SemTelephonyUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public final class CellIdentityNr extends CellIdentity {
    public static final Parcelable.Creator<CellIdentityNr> CREATOR = new Parcelable.Creator<CellIdentityNr>() { // from class: android.telephony.CellIdentityNr.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityNr createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellIdentityNr.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityNr[] newArray(int i) {
            return new CellIdentityNr[i];
        }
    };
    private static final long MAX_NCI = 68719476735L;
    private static final int MAX_NRARFCN = 3279165;
    private static final int MAX_PCI = 1007;
    private static final int MAX_TAC = 16777215;
    private static final String TAG = "CellIdentityNr";
    private final ArraySet<String> mAdditionalPlmns;
    private final int[] mBands;
    private final long mNci;
    private final int mNrArfcn;
    private final int mPci;
    private final int mTac;

    public CellIdentityNr() {
        super(TAG, 6, null, null, null, null);
        this.mNrArfcn = Integer.MAX_VALUE;
        this.mPci = Integer.MAX_VALUE;
        this.mTac = Integer.MAX_VALUE;
        this.mNci = 2147483647L;
        this.mBands = new int[0];
        this.mAdditionalPlmns = new ArraySet<>();
        this.mGlobalCellId = null;
    }

    public CellIdentityNr(int i, int i2, int i3, int[] iArr, String str, String str2, long j, String str3, String str4, Collection<String> collection) {
        super(TAG, 6, str, str2, str3, str4);
        this.mPci = inRangeOrUnavailable(i, 0, 1007);
        this.mTac = inRangeOrUnavailable(i2, 0, 16777215);
        this.mNrArfcn = inRangeOrUnavailable(i3, 0, MAX_NRARFCN);
        this.mBands = iArr;
        this.mNci = inRangeOrUnavailable(j, 0L, MAX_NCI);
        this.mAdditionalPlmns = new ArraySet<>(collection.size());
        for (String str5 : collection) {
            if (isValidPlmn(str5)) {
                this.mAdditionalPlmns.add(str5);
            }
        }
        updateGlobalCellId();
    }

    @Override // android.telephony.CellIdentity
    public CellIdentityNr sanitizeLocationInfo() {
        return new CellIdentityNr(Integer.MAX_VALUE, Integer.MAX_VALUE, this.mNrArfcn, this.mBands, this.mMccStr, this.mMncStr, Long.MAX_VALUE, this.mAlphaLong, this.mAlphaShort, this.mAdditionalPlmns);
    }

    @Override // android.telephony.CellIdentity
    protected void updateGlobalCellId() {
        this.mGlobalCellId = null;
        String plmn = getPlmn();
        if (plmn == null || this.mNci == Long.MAX_VALUE) {
            return;
        }
        this.mGlobalCellId = plmn + TextUtils.formatSimple("%09x", Long.valueOf(this.mNci));
    }

    @Override // android.telephony.CellIdentity
    public CellLocation asCellLocation() {
        GsmCellLocation gsmCellLocation = new GsmCellLocation();
        int i = this.mTac;
        if (i == Integer.MAX_VALUE) {
            i = -1;
        }
        gsmCellLocation.setLacAndCid(i, -1);
        gsmCellLocation.setPsc(0);
        return gsmCellLocation;
    }

    @Override // android.telephony.CellIdentity
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), Integer.valueOf(this.mPci), Integer.valueOf(this.mTac), Integer.valueOf(this.mNrArfcn), Integer.valueOf(Arrays.hashCode(this.mBands)), Long.valueOf(this.mNci), Integer.valueOf(this.mAdditionalPlmns.hashCode()));
    }

    @Override // android.telephony.CellIdentity
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CellIdentityNr)) {
            return false;
        }
        CellIdentityNr cellIdentityNr = (CellIdentityNr) obj;
        return super.equals(cellIdentityNr) && this.mPci == cellIdentityNr.mPci && this.mTac == cellIdentityNr.mTac && this.mNrArfcn == cellIdentityNr.mNrArfcn && Arrays.equals(this.mBands, cellIdentityNr.mBands) && this.mNci == cellIdentityNr.mNci && this.mAdditionalPlmns.equals(cellIdentityNr.mAdditionalPlmns);
    }

    public long getNci() {
        return this.mNci;
    }

    public int getNrarfcn() {
        return this.mNrArfcn;
    }

    public int[] getBands() {
        int[] iArr = this.mBands;
        return Arrays.copyOf(iArr, iArr.length);
    }

    public int getPci() {
        return this.mPci;
    }

    public int getTac() {
        return this.mTac;
    }

    @Override // android.telephony.CellIdentity
    public String getMccString() {
        return this.mMccStr;
    }

    @Override // android.telephony.CellIdentity
    public String getMncString() {
        return this.mMncStr;
    }

    @Override // android.telephony.CellIdentity
    public int getChannelNumber() {
        return this.mNrArfcn;
    }

    public Set<String> getAdditionalPlmns() {
        return Collections.unmodifiableSet(this.mAdditionalPlmns);
    }

    public String toString() {
        return "CellIdentityNr:{ mPci = " + this.mPci + " mTac = " + SemTelephonyUtils.maskPiiFromCellIdentity(this.mTac) + " mNrArfcn = " + this.mNrArfcn + " mBands = " + Arrays.toString(this.mBands) + " mMcc = " + this.mMccStr + " mMnc = " + this.mMncStr + " mNci = " + SemTelephonyUtils.maskPiiFromCellIdentity(this.mNci) + " mAlphaLong = " + this.mAlphaLong + " mAlphaShort = " + this.mAlphaShort + " mAdditionalPlmns = " + this.mAdditionalPlmns + " }";
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, 6);
        parcel.writeInt(this.mPci);
        parcel.writeInt(this.mTac);
        parcel.writeInt(this.mNrArfcn);
        parcel.writeIntArray(this.mBands);
        parcel.writeLong(this.mNci);
        parcel.writeArraySet(this.mAdditionalPlmns);
    }

    private CellIdentityNr(Parcel parcel) {
        super(TAG, 6, parcel);
        this.mPci = parcel.readInt();
        this.mTac = parcel.readInt();
        this.mNrArfcn = parcel.readInt();
        this.mBands = parcel.createIntArray();
        this.mNci = parcel.readLong();
        this.mAdditionalPlmns = parcel.readArraySet(null);
        updateGlobalCellId();
    }

    protected static CellIdentityNr createFromParcelBody(Parcel parcel) {
        return new CellIdentityNr(parcel);
    }

    @Override // android.telephony.CellIdentity
    public boolean isSameCell(CellIdentity cellIdentity) {
        boolean zIsSameCell = super.isSameCell(cellIdentity);
        if (zIsSameCell && (cellIdentity instanceof CellIdentityNr) && this.mTac != ((CellIdentityNr) cellIdentity).getTac()) {
            return false;
        }
        return zIsSameCell;
    }
}
