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
public final class CellIdentityWcdma extends CellIdentity {
    public static final Parcelable.Creator<CellIdentityWcdma> CREATOR = new Parcelable.Creator<CellIdentityWcdma>() { // from class: android.telephony.CellIdentityWcdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityWcdma createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellIdentityWcdma.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityWcdma[] newArray(int i) {
            return new CellIdentityWcdma[i];
        }
    };
    private static final boolean DBG = false;
    private static final int MAX_CID = 268435455;
    private static final int MAX_LAC = 65535;
    private static final int MAX_PSC = 511;
    private static final int MAX_UARFCN = 16383;
    private static final String TAG = "CellIdentityWcdma";
    private final ArraySet<String> mAdditionalPlmns;
    private final int mCid;
    private final ClosedSubscriberGroupInfo mCsgInfo;
    private final int mLac;
    private final int mPsc;
    private final int mUarfcn;

    public CellIdentityWcdma() {
        super(TAG, 4, null, null, null, null);
        this.mLac = Integer.MAX_VALUE;
        this.mCid = Integer.MAX_VALUE;
        this.mPsc = Integer.MAX_VALUE;
        this.mUarfcn = Integer.MAX_VALUE;
        this.mAdditionalPlmns = new ArraySet<>();
        this.mCsgInfo = null;
        this.mGlobalCellId = null;
    }

    public CellIdentityWcdma(int i, int i2, int i3, int i4, String str, String str2, String str3, String str4, Collection<String> collection, ClosedSubscriberGroupInfo closedSubscriberGroupInfo) {
        super(TAG, 4, str, str2, str3, str4);
        this.mLac = inRangeOrUnavailable(i, 0, 65535);
        this.mCid = inRangeOrUnavailable(i2, 0, 268435455);
        this.mPsc = inRangeOrUnavailable(i3, 0, 511);
        this.mUarfcn = inRangeOrUnavailable(i4, 0, 16383);
        this.mAdditionalPlmns = new ArraySet<>(collection.size());
        for (String str5 : collection) {
            if (isValidPlmn(str5)) {
                this.mAdditionalPlmns.add(str5);
            }
        }
        this.mCsgInfo = closedSubscriberGroupInfo;
        updateGlobalCellId();
    }

    private CellIdentityWcdma(CellIdentityWcdma cellIdentityWcdma) {
        this(cellIdentityWcdma.mLac, cellIdentityWcdma.mCid, cellIdentityWcdma.mPsc, cellIdentityWcdma.mUarfcn, cellIdentityWcdma.mMccStr, cellIdentityWcdma.mMncStr, cellIdentityWcdma.mAlphaLong, cellIdentityWcdma.mAlphaShort, cellIdentityWcdma.mAdditionalPlmns, cellIdentityWcdma.mCsgInfo);
    }

    @Override // android.telephony.CellIdentity
    public CellIdentityWcdma sanitizeLocationInfo() {
        return new CellIdentityWcdma(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mMccStr, this.mMncStr, this.mAlphaLong, this.mAlphaShort, this.mAdditionalPlmns, null);
    }

    CellIdentityWcdma copy() {
        return new CellIdentityWcdma(this);
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

    public int getPsc() {
        return this.mPsc;
    }

    @Override // android.telephony.CellIdentity
    public String getMccString() {
        return this.mMccStr;
    }

    @Override // android.telephony.CellIdentity
    public String getMncString() {
        return this.mMncStr;
    }

    public String getMobileNetworkOperator() {
        if (this.mMccStr == null || this.mMncStr == null) {
            return null;
        }
        return this.mMccStr + this.mMncStr;
    }

    @Override // android.telephony.CellIdentity
    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mLac), Integer.valueOf(this.mCid), Integer.valueOf(this.mPsc), Integer.valueOf(this.mAdditionalPlmns.hashCode()), Integer.valueOf(super.hashCode()));
    }

    public int getUarfcn() {
        return this.mUarfcn;
    }

    @Override // android.telephony.CellIdentity
    public int getChannelNumber() {
        return this.mUarfcn;
    }

    public Set<String> getAdditionalPlmns() {
        return Collections.unmodifiableSet(this.mAdditionalPlmns);
    }

    public ClosedSubscriberGroupInfo getClosedSubscriberGroupInfo() {
        return this.mCsgInfo;
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
        int i3 = this.mPsc;
        int i4 = i3 != Integer.MAX_VALUE ? i3 : -1;
        gsmCellLocation.setLacAndCid(i, i2);
        gsmCellLocation.setPsc(i4);
        return gsmCellLocation;
    }

    @Override // android.telephony.CellIdentity
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CellIdentityWcdma)) {
            return false;
        }
        CellIdentityWcdma cellIdentityWcdma = (CellIdentityWcdma) obj;
        return this.mLac == cellIdentityWcdma.mLac && this.mCid == cellIdentityWcdma.mCid && this.mPsc == cellIdentityWcdma.mPsc && this.mUarfcn == cellIdentityWcdma.mUarfcn && TextUtils.equals(this.mMccStr, cellIdentityWcdma.mMccStr) && TextUtils.equals(this.mMncStr, cellIdentityWcdma.mMncStr) && this.mAdditionalPlmns.equals(cellIdentityWcdma.mAdditionalPlmns) && Objects.equals(this.mCsgInfo, cellIdentityWcdma.mCsgInfo) && super.equals(obj);
    }

    public String toString() {
        return TAG + ":{ mLac=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mLac) + " mCid=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mCid) + " mPsc=" + this.mPsc + " mUarfcn=" + this.mUarfcn + " mMcc=" + this.mMccStr + " mMnc=" + this.mMncStr + " mAlphaLong=" + this.mAlphaLong + " mAlphaShort=" + this.mAlphaShort + " mAdditionalPlmns=" + this.mAdditionalPlmns + " mCsgInfo=" + this.mCsgInfo + "}";
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, 4);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mPsc);
        parcel.writeInt(this.mUarfcn);
        parcel.writeArraySet(this.mAdditionalPlmns);
        parcel.writeParcelable(this.mCsgInfo, i);
    }

    private CellIdentityWcdma(Parcel parcel) {
        super(TAG, 4, parcel);
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
        this.mPsc = parcel.readInt();
        this.mUarfcn = parcel.readInt();
        this.mAdditionalPlmns = parcel.readArraySet(null);
        this.mCsgInfo = (ClosedSubscriberGroupInfo) parcel.readParcelable(null, ClosedSubscriberGroupInfo.class);
        updateGlobalCellId();
    }

    protected static CellIdentityWcdma createFromParcelBody(Parcel parcel) {
        return new CellIdentityWcdma(parcel);
    }

    @Override // android.telephony.CellIdentity
    public boolean isSameCell(CellIdentity cellIdentity) {
        boolean isSameCell = super.isSameCell(cellIdentity);
        if (isSameCell && (cellIdentity instanceof CellIdentityWcdma) && this.mPsc != ((CellIdentityWcdma) cellIdentity).getPsc()) {
            return false;
        }
        return isSameCell;
    }
}
