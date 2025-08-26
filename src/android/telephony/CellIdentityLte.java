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
public final class CellIdentityLte extends CellIdentity {
    public static final Parcelable.Creator<CellIdentityLte> CREATOR = new Parcelable.Creator<CellIdentityLte>() { // from class: android.telephony.CellIdentityLte.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityLte createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellIdentityLte.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityLte[] newArray(int i) {
            return new CellIdentityLte[i];
        }
    };
    private static final boolean DBG = false;
    private static final int MAX_BANDWIDTH = 20000;
    private static final int MAX_CI = 268435455;
    private static final int MAX_EARFCN = 262143;
    private static final int MAX_PCI = 503;
    private static final int MAX_TAC = 65535;
    private static final String TAG = "CellIdentityLte";
    private final ArraySet<String> mAdditionalPlmns;
    private final int[] mBands;
    private final int mBandwidth;
    private final int mCi;
    private ClosedSubscriberGroupInfo mCsgInfo;
    private final int mEarfcn;
    private final int mPci;
    private final int mTac;

    public CellIdentityLte() {
        super(TAG, 3, null, null, null, null);
        this.mCi = Integer.MAX_VALUE;
        this.mPci = Integer.MAX_VALUE;
        this.mTac = Integer.MAX_VALUE;
        this.mEarfcn = Integer.MAX_VALUE;
        this.mBands = new int[0];
        this.mBandwidth = Integer.MAX_VALUE;
        this.mAdditionalPlmns = new ArraySet<>();
        this.mCsgInfo = null;
        this.mGlobalCellId = null;
    }

    public CellIdentityLte(int i, int i2, int i3, int i4, int i5) {
        this(i3, i4, i5, Integer.MAX_VALUE, new int[0], Integer.MAX_VALUE, String.valueOf(i), String.valueOf(i2), null, null, new ArraySet(), null);
    }

    public CellIdentityLte(int i, int i2, int i3, int i4, int[] iArr, int i5, String str, String str2, String str3, String str4, Collection<String> collection, ClosedSubscriberGroupInfo closedSubscriberGroupInfo) {
        super(TAG, 3, str, str2, str3, str4);
        this.mCi = inRangeOrUnavailable(i, 0, 268435455);
        this.mPci = inRangeOrUnavailable(i2, 0, 503);
        this.mTac = inRangeOrUnavailable(i3, 0, 65535);
        this.mEarfcn = inRangeOrUnavailable(i4, 0, 262143);
        this.mBands = iArr;
        this.mBandwidth = inRangeOrUnavailable(i5, 0, 20000);
        this.mAdditionalPlmns = new ArraySet<>(collection.size());
        for (String str5 : collection) {
            if (isValidPlmn(str5)) {
                this.mAdditionalPlmns.add(str5);
            }
        }
        this.mCsgInfo = closedSubscriberGroupInfo;
        updateGlobalCellId();
    }

    private CellIdentityLte(CellIdentityLte cellIdentityLte) {
        this(cellIdentityLte.mCi, cellIdentityLte.mPci, cellIdentityLte.mTac, cellIdentityLte.mEarfcn, cellIdentityLte.mBands, cellIdentityLte.mBandwidth, cellIdentityLte.mMccStr, cellIdentityLte.mMncStr, cellIdentityLte.mAlphaLong, cellIdentityLte.mAlphaShort, cellIdentityLte.mAdditionalPlmns, cellIdentityLte.mCsgInfo);
    }

    @Override // android.telephony.CellIdentity
    public CellIdentityLte sanitizeLocationInfo() {
        return new CellIdentityLte(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mBands, Integer.MAX_VALUE, this.mMccStr, this.mMncStr, this.mAlphaLong, this.mAlphaShort, this.mAdditionalPlmns, null);
    }

    CellIdentityLte copy() {
        return new CellIdentityLte(this);
    }

    @Override // android.telephony.CellIdentity
    protected void updateGlobalCellId() {
        this.mGlobalCellId = null;
        String plmn = getPlmn();
        if (plmn == null || this.mCi == Integer.MAX_VALUE) {
            return;
        }
        this.mGlobalCellId = plmn + TextUtils.formatSimple("%07x", Integer.valueOf(this.mCi));
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

    public int getCi() {
        return this.mCi;
    }

    public int getPci() {
        return this.mPci;
    }

    public int getTac() {
        return this.mTac;
    }

    public int getEarfcn() {
        return this.mEarfcn;
    }

    public int[] getBands() {
        int[] iArr = this.mBands;
        return Arrays.copyOf(iArr, iArr.length);
    }

    public int getBandwidth() {
        return this.mBandwidth;
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
    public int getChannelNumber() {
        return this.mEarfcn;
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
        int i = this.mTac;
        if (i == Integer.MAX_VALUE) {
            i = -1;
        }
        int i2 = this.mCi;
        gsmCellLocation.setLacAndCid(i, i2 != Integer.MAX_VALUE ? i2 : -1);
        gsmCellLocation.setPsc(0);
        return gsmCellLocation;
    }

    @Override // android.telephony.CellIdentity
    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mCi), Integer.valueOf(this.mPci), Integer.valueOf(this.mTac), Integer.valueOf(this.mEarfcn), Integer.valueOf(Arrays.hashCode(this.mBands)), Integer.valueOf(this.mBandwidth), Integer.valueOf(this.mAdditionalPlmns.hashCode()), this.mCsgInfo, Integer.valueOf(super.hashCode()));
    }

    @Override // android.telephony.CellIdentity
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CellIdentityLte)) {
            return false;
        }
        CellIdentityLte cellIdentityLte = (CellIdentityLte) obj;
        return this.mCi == cellIdentityLte.mCi && this.mPci == cellIdentityLte.mPci && this.mTac == cellIdentityLte.mTac && this.mEarfcn == cellIdentityLte.mEarfcn && Arrays.equals(this.mBands, cellIdentityLte.mBands) && this.mBandwidth == cellIdentityLte.mBandwidth && TextUtils.equals(this.mMccStr, cellIdentityLte.mMccStr) && TextUtils.equals(this.mMncStr, cellIdentityLte.mMncStr) && this.mAdditionalPlmns.equals(cellIdentityLte.mAdditionalPlmns) && Objects.equals(this.mCsgInfo, cellIdentityLte.mCsgInfo) && super.equals(obj);
    }

    public String toString() {
        return TAG + ":{ mCi=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mCi) + " mPci=" + this.mPci + " mTac=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mTac) + " mEarfcn=" + this.mEarfcn + " mBands=" + Arrays.toString(this.mBands) + " mBandwidth=" + this.mBandwidth + " mMcc=" + this.mMccStr + " mMnc=" + this.mMncStr + " mAlphaLong=" + this.mAlphaLong + " mAlphaShort=" + this.mAlphaShort + " mAdditionalPlmns=" + this.mAdditionalPlmns + " mCsgInfo=" + this.mCsgInfo + "}";
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, 3);
        parcel.writeInt(this.mCi);
        parcel.writeInt(this.mPci);
        parcel.writeInt(this.mTac);
        parcel.writeInt(this.mEarfcn);
        parcel.writeIntArray(this.mBands);
        parcel.writeInt(this.mBandwidth);
        parcel.writeArraySet(this.mAdditionalPlmns);
        parcel.writeParcelable(this.mCsgInfo, i);
    }

    private CellIdentityLte(Parcel parcel) {
        super(TAG, 3, parcel);
        this.mCi = parcel.readInt();
        this.mPci = parcel.readInt();
        this.mTac = parcel.readInt();
        this.mEarfcn = parcel.readInt();
        this.mBands = parcel.createIntArray();
        this.mBandwidth = parcel.readInt();
        this.mAdditionalPlmns = parcel.readArraySet(null);
        this.mCsgInfo = (ClosedSubscriberGroupInfo) parcel.readParcelable(null, ClosedSubscriberGroupInfo.class);
        updateGlobalCellId();
    }

    protected static CellIdentityLte createFromParcelBody(Parcel parcel) {
        return new CellIdentityLte(parcel);
    }

    @Override // android.telephony.CellIdentity
    public boolean isSameCell(CellIdentity cellIdentity) {
        boolean zIsSameCell = super.isSameCell(cellIdentity);
        if (zIsSameCell && (cellIdentity instanceof CellIdentityLte) && this.mTac != ((CellIdentityLte) cellIdentity).getTac()) {
            return false;
        }
        return zIsSameCell;
    }
}
