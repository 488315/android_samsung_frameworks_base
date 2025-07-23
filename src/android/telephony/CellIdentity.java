package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes4.dex */
public abstract class CellIdentity implements Parcelable {
    public static final Parcelable.Creator<CellIdentity> CREATOR = new Parcelable.Creator<CellIdentity>() { // from class: android.telephony.CellIdentity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentity createFromParcel(Parcel parcel) {
            switch (parcel.readInt()) {
                case 1:
                    return CellIdentityGsm.createFromParcelBody(parcel);
                case 2:
                    return CellIdentityCdma.createFromParcelBody(parcel);
                case 3:
                    return CellIdentityLte.createFromParcelBody(parcel);
                case 4:
                    return CellIdentityWcdma.createFromParcelBody(parcel);
                case 5:
                    return CellIdentityTdscdma.createFromParcelBody(parcel);
                case 6:
                    return CellIdentityNr.createFromParcelBody(parcel);
                default:
                    throw new IllegalArgumentException("Bad Cell identity Parcel");
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellIdentity[] newArray(int i) {
            return new CellIdentity[i];
        }
    };
    public static final int INVALID_CHANNEL_NUMBER = Integer.MAX_VALUE;
    public static final int MCC_LENGTH = 3;
    public static final int MNC_MAX_LENGTH = 3;
    public static final int MNC_MIN_LENGTH = 2;
    protected String mAlphaLong;
    protected String mAlphaShort;
    protected String mGlobalCellId;
    protected final String mMccStr;
    protected final String mMncStr;
    protected final String mTag;
    protected final int mType;

    protected static final int inRangeOrUnavailable(int i, int i2, int i3) {
        if (i < i2 || i > i3) {
            return Integer.MAX_VALUE;
        }
        return i;
    }

    protected static final int inRangeOrUnavailable(int i, int i2, int i3, int i4) {
        if ((i < i2 || i > i3) && i != i4) {
            return Integer.MAX_VALUE;
        }
        return i;
    }

    protected static final long inRangeOrUnavailable(long j, long j2, long j3) {
        if (j < j2 || j > j3) {
            return Long.MAX_VALUE;
        }
        return j;
    }

    @SystemApi
    public abstract CellLocation asCellLocation();

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getChannelNumber() {
        return Integer.MAX_VALUE;
    }

    @SystemApi
    public abstract CellIdentity sanitizeLocationInfo();

    protected abstract void updateGlobalCellId();

    protected CellIdentity(String str, int i, String str2, String str3, String str4, String str5) {
        this.mTag = str;
        this.mType = i;
        if (str2 == null || isMcc(str2)) {
            this.mMccStr = str2;
        } else if (str2.isEmpty() || str2.equals(String.valueOf(Integer.MAX_VALUE))) {
            this.mMccStr = null;
        } else {
            this.mMccStr = null;
            log("invalid MCC format: " + str2);
        }
        if (str3 == null || isMnc(str3)) {
            this.mMncStr = str3;
        } else if (str3.isEmpty() || str3.equals(String.valueOf(Integer.MAX_VALUE))) {
            this.mMncStr = null;
        } else {
            this.mMncStr = null;
            log("invalid MNC format: " + str3);
        }
        String str6 = this.mMccStr;
        if ((str6 != null && this.mMncStr == null) || (str6 == null && this.mMncStr != null)) {
            AnomalyReporter.reportAnomaly(UUID.fromString("e257ae06-ac0a-44c0-ba63-823b9f07b3e4"), "CellIdentity Missing Half of PLMN ID");
        }
        this.mAlphaLong = str4;
        this.mAlphaShort = str5;
    }

    public int getType() {
        return this.mType;
    }

    public String getMccString() {
        return this.mMccStr;
    }

    public String getMncString() {
        return this.mMncStr;
    }

    public CharSequence getOperatorAlphaLong() {
        return this.mAlphaLong;
    }

    public void setOperatorAlphaLong(String str) {
        this.mAlphaLong = str;
    }

    public CharSequence getOperatorAlphaShort() {
        return this.mAlphaShort;
    }

    public void setOperatorAlphaShort(String str) {
        this.mAlphaShort = str;
    }

    public String getGlobalCellId() {
        return this.mGlobalCellId;
    }

    public boolean isSameCell(CellIdentity cellIdentity) {
        if (cellIdentity != null && getClass() == cellIdentity.getClass()) {
            return TextUtils.equals(getGlobalCellId(), cellIdentity.getGlobalCellId());
        }
        return false;
    }

    public String getPlmn() {
        if (this.mMccStr == null || this.mMncStr == null) {
            return null;
        }
        return this.mMccStr + this.mMncStr;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CellIdentity)) {
            return false;
        }
        CellIdentity cellIdentity = (CellIdentity) obj;
        return this.mType == cellIdentity.mType && TextUtils.equals(this.mMccStr, cellIdentity.mMccStr) && TextUtils.equals(this.mMncStr, cellIdentity.mMncStr) && TextUtils.equals(this.mAlphaLong, cellIdentity.mAlphaLong) && TextUtils.equals(this.mAlphaShort, cellIdentity.mAlphaShort);
    }

    public int hashCode() {
        return Objects.hash(this.mAlphaLong, this.mAlphaShort, this.mMccStr, this.mMncStr, Integer.valueOf(this.mType));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(i);
        parcel.writeString(this.mMccStr);
        parcel.writeString(this.mMncStr);
        parcel.writeString(this.mAlphaLong);
        parcel.writeString(this.mAlphaShort);
    }

    public static boolean isValidPlmn(String str) {
        return str.length() >= 5 && str.length() <= 6 && isMcc(str.substring(0, 3)) && isMnc(str.substring(3));
    }

    protected CellIdentity(String str, int i, Parcel parcel) {
        this(str, i, parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
    }

    protected void log(String str) {
        com.android.telephony.Rlog.w(this.mTag, str);
    }

    private static boolean isMcc(String str) {
        if (str.length() != 3) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    private static boolean isMnc(String str) {
        if (str.length() < 2 || str.length() > 3) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
}
