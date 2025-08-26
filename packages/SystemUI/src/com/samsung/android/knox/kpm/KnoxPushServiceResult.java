package com.samsung.android.knox.kpm;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class KnoxPushServiceResult implements Parcelable {
    public static final Parcelable.Creator<KnoxPushServiceResult> CREATOR = new Parcelable.Creator<KnoxPushServiceResult>() { // from class: com.samsung.android.knox.kpm.KnoxPushServiceResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnoxPushServiceResult createFromParcel(Parcel parcel) {
            return new KnoxPushServiceResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnoxPushServiceResult[] newArray(int i) {
            return new KnoxPushServiceResult[i];
        }
    };
    public static final int ERROR_BAD_REQUEST = 400;
    public static final int ERROR_BIND_FAIL = -2;
    public static final int ERROR_CB_EMPTY = -9;
    public static final int ERROR_CONFLICT = 409;
    public static final int ERROR_DEVICE_NOT_SUPPORTED = -7;
    public static final int ERROR_FORBIDDEN = 403;
    public static final int ERROR_FW = -5;
    public static final int ERROR_INTERNAL = -6;
    public static final int ERROR_INTERNAL_SERVER = 500;
    public static final int ERROR_NETWORK = -8;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_FOUND = 404;
    public static final int ERROR_PERMISSION = -3;
    public static final int ERROR_REQUEST_NOT_FINISHED = -4;
    public static final int ERROR_SERVICE_UNAVAILABLE = 503;
    public static final int ERROR_UNAUTHORIZED = 401;
    public static final int ERROR_UNKNOWN = -1;
    public static final int NOT_REGISTERED = 2;
    public static final int REGISTERED = 1;
    public static final String TAG = "KnoxPushServiceResult";
    public String deviceId;
    public int errorCode;
    public String reason;

    public KnoxPushServiceResult() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public int getError() {
        return this.errorCode;
    }

    public String getReason() {
        return this.reason;
    }

    public void readFromParcel(Parcel parcel) {
        try {
            this.errorCode = parcel.readInt();
            this.reason = parcel.readString();
            this.deviceId = parcel.readString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setErrorCode(int i) {
        this.errorCode = i;
    }

    public void setReason(String str) {
        this.reason = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel != null) {
            parcel.writeInt(this.errorCode);
            parcel.writeString(this.reason);
            parcel.writeString(this.deviceId);
        }
    }

    public KnoxPushServiceResult(Parcel parcel) {
        readFromParcel(parcel);
    }
}
