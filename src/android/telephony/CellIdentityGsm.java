package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.ArraySet;
import com.android.internal.telephony.SemTelephonyUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public final class CellIdentityGsm extends CellIdentity {
    public static final Parcelable.Creator<CellIdentityGsm> CREATOR = new Parcelable.Creator<CellIdentityGsm>() { // from class: android.telephony.CellIdentityGsm.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityGsm createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellIdentityGsm.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityGsm[] newArray(int i) {
            return new CellIdentityGsm[i];
        }
    };
    private static final boolean DBG = false;
    private static final int MAX_ARFCN = 65535;
    private static final int MAX_BSIC = 63;
    private static final int MAX_CID = 65535;
    private static final int MAX_LAC = 65535;
    private static final String TAG = "CellIdentityGsm";
    private final ArraySet<String> mAdditionalPlmns;
    private final int mArfcn;
    private final int mBsic;
    private final int mCid;
    private final int mLac;

    @Deprecated
    public int getPsc() {
        return Integer.MAX_VALUE;
    }

    public CellIdentityGsm() {
        super(TAG, 1, null, null, null, null);
        this.mLac = Integer.MAX_VALUE;
        this.mCid = Integer.MAX_VALUE;
        this.mArfcn = Integer.MAX_VALUE;
        this.mBsic = Integer.MAX_VALUE;
        this.mAdditionalPlmns = new ArraySet<>();
        this.mGlobalCellId = null;
    }

    public CellIdentityGsm(int i, int i2, int i3, int i4, String str, String str2, String str3, String str4, Collection<String> collection) {
        super(TAG, 1, str, str2, str3, str4);
        this.mLac = inRangeOrUnavailable(i, 0, 65535);
        this.mCid = inRangeOrUnavailable(i2, 0, 65535);
        this.mArfcn = inRangeOrUnavailable(i3, 0, 65535);
        this.mBsic = inRangeOrUnavailable(i4, 0, 63);
        this.mAdditionalPlmns = new ArraySet<>(collection.size());
        for (String str5 : collection) {
            if (isValidPlmn(str5)) {
                this.mAdditionalPlmns.add(str5);
            }
        }
        updateGlobalCellId();
    }

    private CellIdentityGsm(CellIdentityGsm cellIdentityGsm) {
        this(cellIdentityGsm.mLac, cellIdentityGsm.mCid, cellIdentityGsm.mArfcn, cellIdentityGsm.mBsic, cellIdentityGsm.mMccStr, cellIdentityGsm.mMncStr, cellIdentityGsm.mAlphaLong, cellIdentityGsm.mAlphaShort, cellIdentityGsm.mAdditionalPlmns);
    }

    CellIdentityGsm copy() {
        return new CellIdentityGsm(this);
    }

    @Override // android.telephony.CellIdentity
    public CellIdentityGsm sanitizeLocationInfo() {
        return new CellIdentityGsm(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mMccStr, this.mMncStr, this.mAlphaLong, this.mAlphaShort, this.mAdditionalPlmns);
    }

    @Override // android.telephony.CellIdentity
    protected void updateGlobalCellId() {
        this.mGlobalCellId = null;
        String plmn = getPlmn();
        if (plmn == null || this.mLac == Integer.MAX_VALUE || this.mCid == Integer.MAX_VALUE) {
            return;
        }
        this.mGlobalCellId = plmn + TextUtils.formatSimple("%04x%04x", Integer.valueOf(this.mLac), Integer.valueOf(this.mCid));
    }

    @Deprecated
    public int getMcc() {
        if (this.mMccStr != null) {
            return Integer.valueOf(this.mMccStr).intValue();
        }
        return Integer.MAX_VALUE;
    }

    @Deprecated
    public int getMnc() {
        if (this.mMncStr != null) {
            return Integer.valueOf(this.mMncStr).intValue();
        }
        return Integer.MAX_VALUE;
    }

    public int getLac() {
        return this.mLac;
    }

    public int getCid() {
        return this.mCid;
    }

    public int getArfcn() {
        return this.mArfcn;
    }

    public int getBsic() {
        return this.mBsic;
    }

    public String getMobileNetworkOperator() {
        if (this.mMccStr == null || this.mMncStr == null) {
            return null;
        }
        return this.mMccStr + this.mMncStr;
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
        return this.mArfcn;
    }

    public Set<String> getAdditionalPlmns() {
        return Collections.unmodifiableSet(this.mAdditionalPlmns);
    }

    @Override // android.telephony.CellIdentity
    public GsmCellLocation asCellLocation() {
        GsmCellLocation gsmCellLocation = new GsmCellLocation();
        int i = this.mLac;
        if (i == Integer.MAX_VALUE) {
            i = -1;
        }
        int i2 = this.mCid;
        if (i2 == Integer.MAX_VALUE) {
            i2 = -1;
        }
        gsmCellLocation.setLacAndCid(i, i2);
        gsmCellLocation.setPsc(-1);
        return gsmCellLocation;
    }

    @Override // android.telephony.CellIdentity
    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mLac), Integer.valueOf(this.mCid), Integer.valueOf(this.mAdditionalPlmns.hashCode()), Integer.valueOf(super.hashCode()));
    }

    @Override // android.telephony.CellIdentity
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CellIdentityGsm)) {
            return false;
        }
        CellIdentityGsm cellIdentityGsm = (CellIdentityGsm) obj;
        return this.mLac == cellIdentityGsm.mLac && this.mCid == cellIdentityGsm.mCid && this.mArfcn == cellIdentityGsm.mArfcn && this.mBsic == cellIdentityGsm.mBsic && TextUtils.equals(this.mMccStr, cellIdentityGsm.mMccStr) && TextUtils.equals(this.mMncStr, cellIdentityGsm.mMncStr) && this.mAdditionalPlmns.equals(cellIdentityGsm.mAdditionalPlmns) && super.equals(obj);
    }

    public String toString() {
        return TAG + ":{ mLac=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mLac) + " mCid=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mCid) + " mArfcn=" + this.mArfcn + " mBsic=0x" + Integer.toHexString(this.mBsic) + " mMcc=" + this.mMccStr + " mMnc=" + this.mMncStr + " mAlphaLong=" + this.mAlphaLong + " mAlphaShort=" + this.mAlphaShort + " mAdditionalPlmns=" + this.mAdditionalPlmns + "}";
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, 1);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mArfcn);
        parcel.writeInt(this.mBsic);
        parcel.writeArraySet(this.mAdditionalPlmns);
    }

    private CellIdentityGsm(Parcel parcel) {
        super(TAG, 1, parcel);
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
        this.mArfcn = parcel.readInt();
        this.mBsic = parcel.readInt();
        this.mAdditionalPlmns = parcel.readArraySet(null);
        updateGlobalCellId();
    }

    protected static CellIdentityGsm createFromParcelBody(Parcel parcel) {
        return new CellIdentityGsm(parcel);
    }
}
