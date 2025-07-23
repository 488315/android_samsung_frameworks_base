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
public final class CellIdentityTdscdma extends CellIdentity {
    public static final Parcelable.Creator<CellIdentityTdscdma> CREATOR = new Parcelable.Creator<CellIdentityTdscdma>() { // from class: android.telephony.CellIdentityTdscdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityTdscdma createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellIdentityTdscdma.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityTdscdma[] newArray(int i) {
            return new CellIdentityTdscdma[i];
        }
    };
    private static final boolean DBG = false;
    private static final int MAX_CID = 268435455;
    private static final int MAX_CPID = 127;
    private static final int MAX_LAC = 65535;
    private static final int MAX_UARFCN = 65535;
    private static final String TAG = "CellIdentityTdscdma";
    private final ArraySet<String> mAdditionalPlmns;
    private final int mCid;
    private final int mCpid;
    private ClosedSubscriberGroupInfo mCsgInfo;
    private final int mLac;
    private final int mUarfcn;

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CellIdentityTdscdma() {
        super(TAG, 5, null, null, null, null);
        this.mLac = Integer.MAX_VALUE;
        this.mCid = Integer.MAX_VALUE;
        this.mCpid = Integer.MAX_VALUE;
        this.mUarfcn = Integer.MAX_VALUE;
        this.mAdditionalPlmns = new ArraySet<>();
        this.mCsgInfo = null;
        this.mGlobalCellId = null;
    }

    public CellIdentityTdscdma(String str, String str2, int i, int i2, int i3, int i4, String str3, String str4, Collection<String> collection, ClosedSubscriberGroupInfo closedSubscriberGroupInfo) {
        super(TAG, 5, str, str2, str3, str4);
        this.mLac = inRangeOrUnavailable(i, 0, 65535);
        this.mCid = inRangeOrUnavailable(i2, 0, 268435455);
        this.mCpid = inRangeOrUnavailable(i3, 0, 127);
        this.mUarfcn = inRangeOrUnavailable(i4, 0, 65535);
        this.mAdditionalPlmns = new ArraySet<>(collection.size());
        for (String str5 : collection) {
            if (isValidPlmn(str5)) {
                this.mAdditionalPlmns.add(str5);
            }
        }
        this.mCsgInfo = closedSubscriberGroupInfo;
        updateGlobalCellId();
    }

    private CellIdentityTdscdma(CellIdentityTdscdma cellIdentityTdscdma) {
        this(cellIdentityTdscdma.mMccStr, cellIdentityTdscdma.mMncStr, cellIdentityTdscdma.mLac, cellIdentityTdscdma.mCid, cellIdentityTdscdma.mCpid, cellIdentityTdscdma.mUarfcn, cellIdentityTdscdma.mAlphaLong, cellIdentityTdscdma.mAlphaShort, cellIdentityTdscdma.mAdditionalPlmns, cellIdentityTdscdma.mCsgInfo);
    }

    @Override // android.telephony.CellIdentity
    public CellIdentityTdscdma sanitizeLocationInfo() {
        return new CellIdentityTdscdma(this.mMccStr, this.mMncStr, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mAlphaLong, this.mAlphaShort, this.mAdditionalPlmns, null);
    }

    CellIdentityTdscdma copy() {
        return new CellIdentityTdscdma(this);
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

    public int getLac() {
        return this.mLac;
    }

    public int getCid() {
        return this.mCid;
    }

    public int getCpid() {
        return this.mCpid;
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
        gsmCellLocation.setLacAndCid(i, i2);
        gsmCellLocation.setPsc(-1);
        return gsmCellLocation;
    }

    @Override // android.telephony.CellIdentity
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CellIdentityTdscdma)) {
            return false;
        }
        CellIdentityTdscdma cellIdentityTdscdma = (CellIdentityTdscdma) obj;
        return this.mLac == cellIdentityTdscdma.mLac && this.mCid == cellIdentityTdscdma.mCid && this.mCpid == cellIdentityTdscdma.mCpid && this.mUarfcn == cellIdentityTdscdma.mUarfcn && this.mAdditionalPlmns.equals(cellIdentityTdscdma.mAdditionalPlmns) && Objects.equals(this.mCsgInfo, cellIdentityTdscdma.mCsgInfo) && super.equals(obj);
    }

    @Override // android.telephony.CellIdentity
    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mLac), Integer.valueOf(this.mCid), Integer.valueOf(this.mCpid), Integer.valueOf(this.mUarfcn), Integer.valueOf(this.mAdditionalPlmns.hashCode()), this.mCsgInfo, Integer.valueOf(super.hashCode()));
    }

    public String toString() {
        return TAG + ":{ mMcc=" + this.mMccStr + " mMnc=" + this.mMncStr + " mAlphaLong=" + this.mAlphaLong + " mAlphaShort=" + this.mAlphaShort + " mLac=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mLac) + " mCid=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mCid) + " mCpid=" + this.mCpid + " mUarfcn=" + this.mUarfcn + " mAdditionalPlmns=" + this.mAdditionalPlmns + " mCsgInfo=" + this.mCsgInfo + "}";
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, 5);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mCpid);
        parcel.writeInt(this.mUarfcn);
        parcel.writeArraySet(this.mAdditionalPlmns);
        parcel.writeParcelable(this.mCsgInfo, i);
    }

    private CellIdentityTdscdma(Parcel parcel) {
        super(TAG, 5, parcel);
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
        this.mCpid = parcel.readInt();
        this.mUarfcn = parcel.readInt();
        this.mAdditionalPlmns = parcel.readArraySet(null);
        this.mCsgInfo = (ClosedSubscriberGroupInfo) parcel.readParcelable(null, ClosedSubscriberGroupInfo.class);
        updateGlobalCellId();
    }

    protected static CellIdentityTdscdma createFromParcelBody(Parcel parcel) {
        return new CellIdentityTdscdma(parcel);
    }
}
