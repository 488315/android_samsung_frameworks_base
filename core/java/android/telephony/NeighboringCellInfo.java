package android.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.telephony.SemTelephonyUtils;

@Deprecated
public class NeighboringCellInfo implements Parcelable {
    public static final Parcelable.Creator<NeighboringCellInfo> CREATOR =
            new Parcelable.Creator<
                    NeighboringCellInfo>() { // from class: android.telephony.NeighboringCellInfo.1
                @Override // android.os.Parcelable.Creator
                public NeighboringCellInfo createFromParcel(Parcel in) {
                    return new NeighboringCellInfo(in);
                }

                @Override // android.os.Parcelable.Creator
                public NeighboringCellInfo[] newArray(int size) {
                    return new NeighboringCellInfo[size];
                }
            };
    public static final int UNKNOWN_CID = -1;
    public static final int UNKNOWN_RSSI = 99;
    private int mCid;
    private int mLac;
    private int mNetworkType;
    private int mPsc;
    private int mRssi;

    @Deprecated
    public NeighboringCellInfo() {
        this.mRssi = 99;
        this.mLac = -1;
        this.mCid = -1;
        this.mPsc = -1;
        this.mNetworkType = 0;
    }

    @Deprecated
    public NeighboringCellInfo(int rssi, int cid) {
        this.mRssi = rssi;
        this.mCid = cid;
    }

    public NeighboringCellInfo(CellInfoGsm info) {
        this.mNetworkType = 1;
        this.mRssi = info.getCellSignalStrength().getAsuLevel();
        if (this.mRssi == Integer.MAX_VALUE) {
            this.mRssi = 99;
        }
        this.mLac = info.getCellIdentity().getLac();
        if (this.mLac == Integer.MAX_VALUE) {
            this.mLac = -1;
        }
        this.mCid = info.getCellIdentity().getCid();
        if (this.mCid == Integer.MAX_VALUE) {
            this.mCid = -1;
        }
        this.mPsc = -1;
    }

    public NeighboringCellInfo(CellInfoWcdma info) {
        this.mNetworkType = 3;
        this.mRssi = info.getCellSignalStrength().getAsuLevel();
        if (this.mRssi == Integer.MAX_VALUE) {
            this.mRssi = 99;
        }
        this.mLac = info.getCellIdentity().getLac();
        if (this.mLac == Integer.MAX_VALUE) {
            this.mLac = -1;
        }
        this.mCid = info.getCellIdentity().getCid();
        if (this.mCid == Integer.MAX_VALUE) {
            this.mCid = -1;
        }
        this.mPsc = info.getCellIdentity().getPsc();
        if (this.mPsc == Integer.MAX_VALUE) {
            this.mPsc = -1;
        }
    }

    public NeighboringCellInfo(int rssi, String location, int radioType) {
        this.mRssi = rssi;
        this.mNetworkType = 0;
        this.mPsc = -1;
        this.mLac = -1;
        this.mCid = -1;
        int l = location.length();
        if (l > 8) {
            return;
        }
        if (l < 8) {
            for (int i = 0; i < 8 - l; i++) {
                location = "0" + location;
            }
        }
        try {
            switch (radioType) {
                case 1:
                case 2:
                    this.mNetworkType = radioType;
                    if (!location.equalsIgnoreCase("FFFFFFFF")) {
                        this.mCid = Integer.parseInt(location.substring(4), 16);
                        this.mLac = Integer.parseInt(location.substring(0, 4), 16);
                        break;
                    }
                    break;
                case 3:
                case 8:
                case 9:
                case 10:
                    this.mNetworkType = radioType;
                    this.mPsc = Integer.parseInt(location, 16);
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                default:
                    return;
            }
        } catch (NumberFormatException e) {
            this.mPsc = -1;
            this.mLac = -1;
            this.mCid = -1;
            this.mNetworkType = 0;
        }
    }

    public NeighboringCellInfo(Parcel in) {
        this.mRssi = in.readInt();
        this.mLac = in.readInt();
        this.mCid = in.readInt();
        this.mPsc = in.readInt();
        this.mNetworkType = in.readInt();
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
    public void setCid(int cid) {
        this.mCid = cid;
    }

    @Deprecated
    public void setRssi(int rssi) {
        this.mRssi = rssi;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        int i = this.mPsc;
        Object obj = NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        if (i != -1) {
            StringBuilder append = sb.append(Integer.toHexString(this.mPsc)).append("@");
            if (this.mRssi != 99) {
                obj = Integer.valueOf(this.mRssi);
            }
            append.append(obj);
        } else if (this.mLac != -1 && this.mCid != -1) {
            StringBuilder append2 =
                    sb.append(SemTelephonyUtils.maskPii(Integer.toHexString(this.mLac)))
                            .append(SemTelephonyUtils.maskPii(Integer.toHexString(this.mCid)))
                            .append("@");
            if (this.mRssi != 99) {
                obj = Integer.valueOf(this.mRssi);
            }
            append2.append(obj);
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRssi);
        dest.writeInt(this.mLac);
        dest.writeInt(this.mCid);
        dest.writeInt(this.mPsc);
        dest.writeInt(this.mNetworkType);
    }
}
