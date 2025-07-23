package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import com.android.internal.telephony.DctConstants;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TelephonyDisplayInfo implements Parcelable {
    public static final Parcelable.Creator<TelephonyDisplayInfo> CREATOR = new Parcelable.Creator<TelephonyDisplayInfo>() { // from class: android.telephony.TelephonyDisplayInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TelephonyDisplayInfo createFromParcel(Parcel parcel) {
            return new TelephonyDisplayInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TelephonyDisplayInfo[] newArray(int i) {
            return new TelephonyDisplayInfo[i];
        }
    };
    public static final int OVERRIDE_NETWORK_TYPE_LTE_ADVANCED_PRO = 2;
    public static final int OVERRIDE_NETWORK_TYPE_LTE_CA = 1;
    public static final int OVERRIDE_NETWORK_TYPE_NONE = 0;
    public static final int OVERRIDE_NETWORK_TYPE_NR_ADVANCED = 5;
    public static final int OVERRIDE_NETWORK_TYPE_NR_NSA = 3;

    @Deprecated
    public static final int OVERRIDE_NETWORK_TYPE_NR_NSA_MMWAVE = 4;
    private boolean m5gAvailable;
    private final boolean mIsNtn;
    private final boolean mIsRoaming;
    private final boolean mIsSatelliteConstrainedData;
    private final int mNetworkType;
    private final int mOverrideNetworkType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public TelephonyDisplayInfo(int i, int i2) {
        this(i, i2, false, false, false);
    }

    @Deprecated
    public TelephonyDisplayInfo(int i, int i2, boolean z) {
        this.mNetworkType = i;
        this.mOverrideNetworkType = i2;
        this.mIsRoaming = z;
        this.mIsNtn = false;
        this.mIsSatelliteConstrainedData = false;
    }

    public TelephonyDisplayInfo(int i, int i2, boolean z, boolean z2, boolean z3) {
        this.mNetworkType = i;
        this.mOverrideNetworkType = i2;
        this.mIsRoaming = z;
        this.mIsNtn = z2;
        this.mIsSatelliteConstrainedData = z3;
    }

    public TelephonyDisplayInfo(Parcel parcel) {
        this.mNetworkType = parcel.readInt();
        this.mOverrideNetworkType = parcel.readInt();
        this.mIsRoaming = parcel.readBoolean();
        this.mIsNtn = parcel.readBoolean();
        this.mIsSatelliteConstrainedData = parcel.readBoolean();
        this.m5gAvailable = parcel.readBoolean();
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public int getOverrideNetworkType() {
        return this.mOverrideNetworkType;
    }

    public boolean is5gAvailable() {
        return this.m5gAvailable;
    }

    public void set5gAvailable(boolean z) {
        this.m5gAvailable = z;
    }

    public boolean isRoaming() {
        return this.mIsRoaming;
    }

    public boolean isSatelliteConstrainedData() {
        return this.mIsSatelliteConstrainedData;
    }

    public boolean isNtn() {
        return this.mIsNtn;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mNetworkType);
        parcel.writeInt(this.mOverrideNetworkType);
        parcel.writeBoolean(this.mIsRoaming);
        parcel.writeBoolean(this.mIsNtn);
        parcel.writeBoolean(this.mIsSatelliteConstrainedData);
        parcel.writeBoolean(this.m5gAvailable);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TelephonyDisplayInfo telephonyDisplayInfo = (TelephonyDisplayInfo) obj;
            if (this.mNetworkType == telephonyDisplayInfo.mNetworkType && this.mOverrideNetworkType == telephonyDisplayInfo.mOverrideNetworkType && this.mIsRoaming == telephonyDisplayInfo.mIsRoaming && this.mIsNtn == telephonyDisplayInfo.mIsNtn && this.mIsSatelliteConstrainedData == telephonyDisplayInfo.mIsSatelliteConstrainedData && this.m5gAvailable == telephonyDisplayInfo.m5gAvailable) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mNetworkType), Integer.valueOf(this.mOverrideNetworkType), Boolean.valueOf(this.mIsRoaming), Boolean.valueOf(this.mIsNtn), Boolean.valueOf(this.mIsSatelliteConstrainedData), Boolean.valueOf(this.m5gAvailable));
    }

    public static String overrideNetworkTypeToString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "LTE_CA";
        }
        if (i == 2) {
            return "LTE_ADV_PRO";
        }
        if (i == 3) {
            return DctConstants.RAT_NAME_NR_NSA;
        }
        if (i == 4) {
            return DctConstants.RAT_NAME_NR_NSA_MMWAVE;
        }
        if (i == 5) {
            return "NR_ADVANCED";
        }
        return "UNKNOWN";
    }

    public String toString() {
        return "TelephonyDisplayInfo {network=" + TelephonyManager.getNetworkTypeName(this.mNetworkType) + ", overrideNetwork=" + overrideNetworkTypeToString(this.mOverrideNetworkType) + ", isRoaming=" + this.mIsRoaming + ", isNtn=" + this.mIsNtn + ", isSatelliteConstrainedData=" + this.mIsSatelliteConstrainedData + "}";
    }
}
