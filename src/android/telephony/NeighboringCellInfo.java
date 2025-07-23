package android.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.telephony.SemTelephonyUtils;

@Deprecated
/* loaded from: classes4.dex */
public class NeighboringCellInfo implements Parcelable {
    public static final Parcelable.Creator<NeighboringCellInfo> CREATOR = new Parcelable.Creator<NeighboringCellInfo>() { // from class: android.telephony.NeighboringCellInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NeighboringCellInfo createFromParcel(Parcel parcel) {
            return new NeighboringCellInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NeighboringCellInfo[] newArray(int i) {
            return new NeighboringCellInfo[i];
        }
    };
    public static final int UNKNOWN_CID = -1;
    public static final int UNKNOWN_RSSI = 99;
    private int mCid;
    private int mLac;
    private int mNetworkType;
    private int mPsc;
    private int mRssi;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public NeighboringCellInfo() {
        this.mRssi = 99;
        this.mLac = -1;
        this.mCid = -1;
        this.mPsc = -1;
        this.mNetworkType = 0;
    }

    @Deprecated
    public NeighboringCellInfo(int i, int i2) {
        this.mRssi = i;
        this.mCid = i2;
    }

    public NeighboringCellInfo(CellInfoGsm cellInfoGsm) {
        this.mNetworkType = 1;
        int asuLevel = cellInfoGsm.getCellSignalStrength().getAsuLevel();
        this.mRssi = asuLevel;
        if (asuLevel == Integer.MAX_VALUE) {
            this.mRssi = 99;
        }
        int lac = cellInfoGsm.getCellIdentity().getLac();
        this.mLac = lac;
        if (lac == Integer.MAX_VALUE) {
            this.mLac = -1;
        }
        int cid = cellInfoGsm.getCellIdentity().getCid();
        this.mCid = cid;
        if (cid == Integer.MAX_VALUE) {
            this.mCid = -1;
        }
        this.mPsc = -1;
    }

    public NeighboringCellInfo(CellInfoWcdma cellInfoWcdma) {
        this.mNetworkType = 3;
        int asuLevel = cellInfoWcdma.getCellSignalStrength().getAsuLevel();
        this.mRssi = asuLevel;
        if (asuLevel == Integer.MAX_VALUE) {
            this.mRssi = 99;
        }
        int lac = cellInfoWcdma.getCellIdentity().getLac();
        this.mLac = lac;
        if (lac == Integer.MAX_VALUE) {
            this.mLac = -1;
        }
        int cid = cellInfoWcdma.getCellIdentity().getCid();
        this.mCid = cid;
        if (cid == Integer.MAX_VALUE) {
            this.mCid = -1;
        }
        int psc = cellInfoWcdma.getCellIdentity().getPsc();
        this.mPsc = psc;
        if (psc == Integer.MAX_VALUE) {
            this.mPsc = -1;
        }
    }

    public NeighboringCellInfo(int i, String str, int i2) {
        this.mRssi = i;
        this.mNetworkType = 0;
        this.mPsc = -1;
        this.mLac = -1;
        this.mCid = -1;
        int length = str.length();
        if (length > 8) {
            return;
        }
        if (length < 8) {
            for (int i3 = 0; i3 < 8 - length; i3++) {
                str = "0" + str;
            }
        }
        try {
            if (i2 == 1 || i2 == 2) {
                this.mNetworkType = i2;
                if (str.equalsIgnoreCase("FFFFFFFF")) {
                    return;
                }
                this.mCid = Integer.parseInt(str.substring(4), 16);
                this.mLac = Integer.parseInt(str.substring(0, 4), 16);
                return;
            }
            if (i2 != 3) {
                switch (i2) {
                }
            }
            this.mNetworkType = i2;
            this.mPsc = Integer.parseInt(str, 16);
        } catch (NumberFormatException unused) {
            this.mPsc = -1;
            this.mLac = -1;
            this.mCid = -1;
            this.mNetworkType = 0;
        }
    }

    public NeighboringCellInfo(Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
        this.mPsc = parcel.readInt();
        this.mNetworkType = parcel.readInt();
    }

    public int getRssi() {
        return this.mRssi;
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

    public int getNetworkType() {
        return this.mNetworkType;
    }

    @Deprecated
    public void setCid(int i) {
        this.mCid = i;
    }

    @Deprecated
    public void setRssi(int i) {
        this.mRssi = i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        int i = this.mPsc;
        Object obj = NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        if (i != -1) {
            sb.append(Integer.toHexString(i));
            sb.append("@");
            int i2 = this.mRssi;
            if (i2 != 99) {
                obj = Integer.valueOf(i2);
            }
            sb.append(obj);
        } else {
            int i3 = this.mLac;
            if (i3 != -1 && this.mCid != -1) {
                sb.append(SemTelephonyUtils.maskPii(Integer.toHexString(i3)));
                sb.append(SemTelephonyUtils.maskPii(Integer.toHexString(this.mCid)));
                sb.append("@");
                int i4 = this.mRssi;
                if (i4 != 99) {
                    obj = Integer.valueOf(i4);
                }
                sb.append(obj);
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mPsc);
        parcel.writeInt(this.mNetworkType);
    }
}
