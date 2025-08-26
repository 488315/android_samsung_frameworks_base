package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.util.SparseArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class BarringInfo implements Parcelable {
    public static final int BARRING_SERVICE_TYPE_CS_FALLBACK = 5;
    public static final int BARRING_SERVICE_TYPE_CS_SERVICE = 0;
    public static final int BARRING_SERVICE_TYPE_CS_VOICE = 2;
    public static final int BARRING_SERVICE_TYPE_EMERGENCY = 8;
    public static final int BARRING_SERVICE_TYPE_MMTEL_VIDEO = 7;
    public static final int BARRING_SERVICE_TYPE_MMTEL_VOICE = 6;
    public static final int BARRING_SERVICE_TYPE_MO_DATA = 4;
    public static final int BARRING_SERVICE_TYPE_MO_SIGNALLING = 3;
    public static final int BARRING_SERVICE_TYPE_PS_SERVICE = 1;
    public static final int BARRING_SERVICE_TYPE_SMS = 9;
    private SparseArray<BarringServiceInfo> mBarringServiceInfos;
    private CellIdentity mCellIdentity;
    private static final BarringServiceInfo BARRING_SERVICE_INFO_UNKNOWN = new BarringServiceInfo(-1);
    private static final BarringServiceInfo BARRING_SERVICE_INFO_UNBARRED = new BarringServiceInfo(0);
    public static final Parcelable.Creator<BarringInfo> CREATOR = new Parcelable.Creator<BarringInfo>() { // from class: android.telephony.BarringInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BarringInfo createFromParcel(Parcel parcel) {
            return new BarringInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BarringInfo[] newArray(int i) {
            return new BarringInfo[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface BarringServiceType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class BarringServiceInfo implements Parcelable {
        public static final int BARRING_TYPE_CONDITIONAL = 1;
        public static final int BARRING_TYPE_NONE = 0;
        public static final int BARRING_TYPE_UNCONDITIONAL = 2;
        public static final int BARRING_TYPE_UNKNOWN = -1;
        public static final Parcelable.Creator<BarringServiceInfo> CREATOR = new Parcelable.Creator<BarringServiceInfo>() { // from class: android.telephony.BarringInfo.BarringServiceInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BarringServiceInfo createFromParcel(Parcel parcel) {
                return new BarringServiceInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public BarringServiceInfo[] newArray(int i) {
                return new BarringServiceInfo[i];
            }
        };
        private final int mBarringType;
        private final int mConditionalBarringFactor;
        private final int mConditionalBarringTimeSeconds;
        private final boolean mIsConditionallyBarred;

        @Retention(RetentionPolicy.SOURCE)
        public @interface BarringType {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public BarringServiceInfo(int i) {
            this(i, false, 0, 0);
        }

        public BarringServiceInfo(int i, boolean z, int i2, int i3) {
            this.mBarringType = i;
            this.mIsConditionallyBarred = z;
            this.mConditionalBarringFactor = i2;
            this.mConditionalBarringTimeSeconds = i3;
        }

        public int getBarringType() {
            return this.mBarringType;
        }

        public boolean isConditionallyBarred() {
            return this.mIsConditionallyBarred;
        }

        public int getConditionalBarringFactor() {
            return this.mConditionalBarringFactor;
        }

        public int getConditionalBarringTimeSeconds() {
            return this.mConditionalBarringTimeSeconds;
        }

        public boolean isBarred() {
            int i = this.mBarringType;
            return i == 2 || (i == 1 && this.mIsConditionallyBarred);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mBarringType), Boolean.valueOf(this.mIsConditionallyBarred), Integer.valueOf(this.mConditionalBarringFactor), Integer.valueOf(this.mConditionalBarringTimeSeconds));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof BarringServiceInfo)) {
                return false;
            }
            BarringServiceInfo barringServiceInfo = (BarringServiceInfo) obj;
            return this.mBarringType == barringServiceInfo.mBarringType && this.mIsConditionallyBarred == barringServiceInfo.mIsConditionallyBarred && this.mConditionalBarringFactor == barringServiceInfo.mConditionalBarringFactor && this.mConditionalBarringTimeSeconds == barringServiceInfo.mConditionalBarringTimeSeconds;
        }

        private static String barringTypeToString(int i) {
            if (i == -1) {
                return "UNKNOWN";
            }
            if (i == 0) {
                return KeyProperties.DIGEST_NONE;
            }
            if (i == 1) {
                return "CONDITIONAL";
            }
            if (i == 2) {
                return "UNCONDITIONAL";
            }
            return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
        }

        public String toString() {
            return "BarringServiceInfo {mBarringType=" + barringTypeToString(this.mBarringType) + ", mIsConditionallyBarred=" + this.mIsConditionallyBarred + ", mConditionalBarringFactor=" + this.mConditionalBarringFactor + ", mConditionalBarringTimeSeconds=" + this.mConditionalBarringTimeSeconds + "}";
        }

        public BarringServiceInfo(Parcel parcel) {
            this.mBarringType = parcel.readInt();
            this.mIsConditionallyBarred = parcel.readBoolean();
            this.mConditionalBarringFactor = parcel.readInt();
            this.mConditionalBarringTimeSeconds = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mBarringType);
            parcel.writeBoolean(this.mIsConditionallyBarred);
            parcel.writeInt(this.mConditionalBarringFactor);
            parcel.writeInt(this.mConditionalBarringTimeSeconds);
        }
    }

    @SystemApi
    public BarringInfo() {
        this.mBarringServiceInfos = new SparseArray<>();
    }

    public BarringInfo(CellIdentity cellIdentity, SparseArray<BarringServiceInfo> sparseArray) {
        this.mCellIdentity = cellIdentity;
        this.mBarringServiceInfos = sparseArray;
    }

    public BarringServiceInfo getBarringServiceInfo(int i) {
        BarringServiceInfo barringServiceInfo = this.mBarringServiceInfos.get(i);
        return barringServiceInfo != null ? barringServiceInfo : this.mBarringServiceInfos.size() > 0 ? BARRING_SERVICE_INFO_UNBARRED : BARRING_SERVICE_INFO_UNKNOWN;
    }

    @SystemApi
    public BarringInfo createLocationInfoSanitizedCopy() {
        CellIdentity cellIdentity = this.mCellIdentity;
        return cellIdentity == null ? this : new BarringInfo(cellIdentity.sanitizeLocationInfo(), this.mBarringServiceInfos);
    }

    public BarringInfo(Parcel parcel) {
        this.mCellIdentity = (CellIdentity) parcel.readParcelable(CellIdentity.class.getClassLoader(), CellIdentity.class);
        this.mBarringServiceInfos = parcel.readSparseArray(BarringServiceInfo.class.getClassLoader(), BarringServiceInfo.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mCellIdentity, i);
        parcel.writeSparseArray(this.mBarringServiceInfos);
    }

    public int hashCode() {
        CellIdentity cellIdentity = this.mCellIdentity;
        int iHashCode = cellIdentity != null ? cellIdentity.hashCode() : 7;
        for (int i = 0; i < this.mBarringServiceInfos.size(); i++) {
            iHashCode = iHashCode + (this.mBarringServiceInfos.keyAt(i) * 15) + (this.mBarringServiceInfos.valueAt(i).hashCode() * 31);
        }
        return iHashCode;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BarringInfo)) {
            return false;
        }
        BarringInfo barringInfo = (BarringInfo) obj;
        if (hashCode() != barringInfo.hashCode() || this.mBarringServiceInfos.size() != barringInfo.mBarringServiceInfos.size()) {
            return false;
        }
        for (int i = 0; i < this.mBarringServiceInfos.size(); i++) {
            if (this.mBarringServiceInfos.keyAt(i) != barringInfo.mBarringServiceInfos.keyAt(i) || !Objects.equals(this.mBarringServiceInfos.valueAt(i), barringInfo.mBarringServiceInfos.valueAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "BarringInfo {mCellIdentity=" + this.mCellIdentity + ", mBarringServiceInfos=" + this.mBarringServiceInfos + "}";
    }

    public CellIdentity getCellIdentity() {
        return this.mCellIdentity;
    }
}
