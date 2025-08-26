package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.cdma.CdmaCellLocation;
import android.text.TextUtils;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
import com.android.internal.telephony.SemTelephonyUtils;
import java.util.Objects;

@Deprecated
/* loaded from: classes4.dex */
public final class CellIdentityCdma extends CellIdentity {
    private static final int BASESTATION_ID_MAX = 65535;

    @Deprecated
    public static final Parcelable.Creator<CellIdentityCdma> CREATOR = new Parcelable.Creator<CellIdentityCdma>() { // from class: android.telephony.CellIdentityCdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityCdma createFromParcel(Parcel parcel) {
            parcel.readInt();
            return CellIdentityCdma.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentityCdma[] newArray(int i) {
            return new CellIdentityCdma[i];
        }
    };
    private static final boolean DBG = false;
    private static final int LATITUDE_MAX = 1296000;
    private static final int LATITUDE_MIN = -1296000;
    private static final int LONGITUDE_MAX = 2592000;
    private static final int LONGITUDE_MIN = -2592000;
    private static final int NETWORK_ID_MAX = 65535;
    private static final int SYSTEM_ID_MAX = 32767;
    private static final String TAG = "CellIdentityCdma";
    private final int mBasestationId;
    private final int mLatitude;
    private final int mLongitude;
    private final int mNetworkId;
    private final int mSystemId;

    public CellIdentityCdma() {
        super(TAG, 2, null, null, null, null);
        this.mNetworkId = Integer.MAX_VALUE;
        this.mSystemId = Integer.MAX_VALUE;
        this.mBasestationId = Integer.MAX_VALUE;
        this.mLongitude = Integer.MAX_VALUE;
        this.mLatitude = Integer.MAX_VALUE;
        this.mGlobalCellId = null;
    }

    public CellIdentityCdma(int i, int i2, int i3, int i4, int i5, String str, String str2) {
        super(TAG, 2, null, null, Flags.cleanupCdma() ? null : str, Flags.cleanupCdma() ? null : str2);
        if (Flags.cleanupCdma()) {
            this.mNetworkId = Integer.MAX_VALUE;
            this.mSystemId = Integer.MAX_VALUE;
            this.mBasestationId = Integer.MAX_VALUE;
            this.mLongitude = Integer.MAX_VALUE;
            this.mLatitude = Integer.MAX_VALUE;
            this.mGlobalCellId = null;
            return;
        }
        this.mNetworkId = inRangeOrUnavailable(i, 0, 65535);
        this.mSystemId = inRangeOrUnavailable(i2, 0, 32767);
        this.mBasestationId = inRangeOrUnavailable(i3, 0, 65535);
        int iInRangeOrUnavailable = inRangeOrUnavailable(i5, LATITUDE_MIN, LATITUDE_MAX);
        int iInRangeOrUnavailable2 = inRangeOrUnavailable(i4, LONGITUDE_MIN, LONGITUDE_MAX);
        if (!isNullIsland(iInRangeOrUnavailable, iInRangeOrUnavailable2)) {
            this.mLongitude = iInRangeOrUnavailable2;
            this.mLatitude = iInRangeOrUnavailable;
        } else {
            this.mLatitude = Integer.MAX_VALUE;
            this.mLongitude = Integer.MAX_VALUE;
        }
        updateGlobalCellId();
    }

    private CellIdentityCdma(CellIdentityCdma cellIdentityCdma) {
        this(cellIdentityCdma.mNetworkId, cellIdentityCdma.mSystemId, cellIdentityCdma.mBasestationId, cellIdentityCdma.mLongitude, cellIdentityCdma.mLatitude, cellIdentityCdma.mAlphaLong, cellIdentityCdma.mAlphaShort);
    }

    CellIdentityCdma copy() {
        return new CellIdentityCdma(this);
    }

    @Override // android.telephony.CellIdentity
    @Deprecated
    public CellIdentityCdma sanitizeLocationInfo() {
        return new CellIdentityCdma(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, this.mAlphaLong, this.mAlphaShort);
    }

    @Override // android.telephony.CellIdentity
    protected void updateGlobalCellId() {
        int i;
        this.mGlobalCellId = null;
        if (this.mNetworkId == Integer.MAX_VALUE || (i = this.mSystemId) == Integer.MAX_VALUE || this.mBasestationId == Integer.MAX_VALUE) {
            return;
        }
        this.mGlobalCellId = TextUtils.formatSimple("%04x%04x%04x", Integer.valueOf(i), Integer.valueOf(this.mNetworkId), Integer.valueOf(this.mBasestationId));
    }

    private boolean isNullIsland(int i, int i2) {
        return Math.abs(i) <= 1 && Math.abs(i2) <= 1;
    }

    @Deprecated
    public int getNetworkId() {
        return this.mNetworkId;
    }

    @Deprecated
    public int getSystemId() {
        return this.mSystemId;
    }

    @Deprecated
    public int getBasestationId() {
        return this.mBasestationId;
    }

    @Deprecated
    public int getLongitude() {
        return this.mLongitude;
    }

    @Deprecated
    public int getLatitude() {
        return this.mLatitude;
    }

    @Override // android.telephony.CellIdentity
    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mNetworkId), Integer.valueOf(this.mSystemId), Integer.valueOf(this.mBasestationId), Integer.valueOf(this.mLatitude), Integer.valueOf(this.mLongitude), Integer.valueOf(super.hashCode()));
    }

    @Override // android.telephony.CellIdentity
    @Deprecated
    public CdmaCellLocation asCellLocation() {
        CdmaCellLocation cdmaCellLocation = new CdmaCellLocation();
        int i = this.mBasestationId;
        if (i == Integer.MAX_VALUE) {
            i = -1;
        }
        int i2 = this.mSystemId;
        if (i2 == Integer.MAX_VALUE) {
            i2 = -1;
        }
        int i3 = this.mNetworkId;
        if (i3 == Integer.MAX_VALUE) {
            i3 = -1;
        }
        cdmaCellLocation.setCellLocationData(i, this.mLatitude, this.mLongitude, i2, i3);
        return cdmaCellLocation;
    }

    @Override // android.telephony.CellIdentity
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CellIdentityCdma)) {
            return false;
        }
        CellIdentityCdma cellIdentityCdma = (CellIdentityCdma) obj;
        return this.mNetworkId == cellIdentityCdma.mNetworkId && this.mSystemId == cellIdentityCdma.mSystemId && this.mBasestationId == cellIdentityCdma.mBasestationId && this.mLatitude == cellIdentityCdma.mLatitude && this.mLongitude == cellIdentityCdma.mLongitude && super.equals(obj);
    }

    public String toString() {
        return TAG + ":{ mNetworkId=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mNetworkId) + " mSystemId=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mSystemId) + " mBasestationId=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mBasestationId) + " mLongitude=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mLongitude) + " mLatitude=" + SemTelephonyUtils.maskPiiFromCellIdentity(this.mLatitude) + " mAlphaLong=" + this.mAlphaLong + " mAlphaShort=" + this.mAlphaShort + "}";
    }

    @Override // android.telephony.CellIdentity, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, 2);
        parcel.writeInt(this.mNetworkId);
        parcel.writeInt(this.mSystemId);
        parcel.writeInt(this.mBasestationId);
        parcel.writeInt(this.mLongitude);
        parcel.writeInt(this.mLatitude);
    }

    private CellIdentityCdma(Parcel parcel) {
        super(TAG, 2, parcel);
        if (Flags.cleanupCdma()) {
            parcel.readInt();
            this.mNetworkId = Integer.MAX_VALUE;
            parcel.readInt();
            this.mSystemId = Integer.MAX_VALUE;
            parcel.readInt();
            this.mBasestationId = Integer.MAX_VALUE;
            parcel.readInt();
            this.mLongitude = Integer.MAX_VALUE;
            parcel.readInt();
            this.mLatitude = Integer.MAX_VALUE;
            this.mGlobalCellId = null;
            return;
        }
        this.mNetworkId = parcel.readInt();
        this.mSystemId = parcel.readInt();
        this.mBasestationId = parcel.readInt();
        this.mLongitude = parcel.readInt();
        this.mLatitude = parcel.readInt();
        updateGlobalCellId();
    }

    protected static CellIdentityCdma createFromParcelBody(Parcel parcel) {
        return new CellIdentityCdma(parcel);
    }
}
