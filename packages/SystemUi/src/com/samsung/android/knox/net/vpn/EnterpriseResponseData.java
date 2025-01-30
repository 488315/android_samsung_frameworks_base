package com.samsung.android.knox.net.vpn;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class EnterpriseResponseData<T> implements Parcelable {
    public static final int APINOTSUPPORTED = 1;
    public static final Parcelable.Creator<EnterpriseResponseData> CREATOR = new Parcelable.Creator<EnterpriseResponseData>() { // from class: com.samsung.android.knox.net.vpn.EnterpriseResponseData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final EnterpriseResponseData[] newArray(int i) {
            return new EnterpriseResponseData[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final EnterpriseResponseData createFromParcel(Parcel parcel) {
            return new EnterpriseResponseData(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final EnterpriseResponseData[] newArray(int i) {
            return new EnterpriseResponseData[i];
        }
    };
    public static final int ERROR = -1;
    public static final int EXCEPTIONFAILURE = 3;
    public static final int FAILURE = 1;
    public static final int INSTALL_FAILURE = 6;
    public static final int INVALID_ADMIN = 8;
    public static final int INVALID_CONTAINER_ID = 11;
    public static final int INVALID_PARAMETER = 9;
    public static final int INVALID_VENDOR = 7;
    public static final int INVALID_VPN_STATE = 12;
    public static final int NOERROR = 0;
    public static final int NULLPACKAGE = 4;
    public static final int NULLPROFILE = 2;
    public static final int RECREATE_CONNECTION_FAILURE = 2;
    public static final int SERVICE_NOT_STARTED = 10;
    public static final int SUCCESS = 0;
    public static final int SYSTEM_UID_FAILURE = 5;
    public T mData;
    public int mFailureState;
    public int mStatus;

    public /* synthetic */ EnterpriseResponseData(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final T getData() {
        return this.mData;
    }

    public final int getFailureState() {
        return this.mFailureState;
    }

    public final int getStatus() {
        return this.mStatus;
    }

    public final void readFromParcel(Parcel parcel) {
        this.mData = (T) parcel.readValue(null);
        this.mStatus = parcel.readInt();
        this.mFailureState = parcel.readInt();
    }

    public final void setData(T t) {
        this.mData = t;
    }

    public final void setStatus(int i, int i2) {
        this.mStatus = i;
        this.mFailureState = i2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.mData);
        parcel.writeInt(this.mStatus);
        parcel.writeInt(this.mFailureState);
    }

    public EnterpriseResponseData() {
        this.mStatus = 0;
        this.mFailureState = 0;
    }

    private EnterpriseResponseData(Parcel parcel) {
        this.mStatus = 0;
        this.mFailureState = 0;
        readFromParcel(parcel);
    }
}
