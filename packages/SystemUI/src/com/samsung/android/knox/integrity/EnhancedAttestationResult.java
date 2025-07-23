package com.samsung.android.knox.integrity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class EnhancedAttestationResult implements Parcelable {
    public static final Parcelable.Creator<EnhancedAttestationResult> CREATOR = new Parcelable.Creator<EnhancedAttestationResult>() { // from class: com.samsung.android.knox.integrity.EnhancedAttestationResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnhancedAttestationResult createFromParcel(Parcel parcel) {
            return new EnhancedAttestationResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnhancedAttestationResult[] newArray(int i) {
            return new EnhancedAttestationResult[i];
        }
    };
    public static final String DATA_FIELD_BLOB = "dataFieldBlob";
    public static final String DATA_FIELD_SERVER_RESPONSE_ID = "serverResponseId";
    public static final String DATA_FIELD_SERVER_RESPONSE_RAW_DATA = "serverResponseRawData";
    public static final String DATA_FIELD_UNIQUE_ID = "dataFieldUniqueId";
    public static final String DATA_FIELD_URL = "dataFieldUrl";
    public static final int ERROR_BAD_REQUEST = 400;
    public static final int ERROR_BIND_FAIL = -7;
    public static final int ERROR_CONFLICT = 409;
    public static final int ERROR_DEVICE_NOT_SUPPORTED = -4;
    public static final int ERROR_FORBIDDEN = 403;
    public static final int ERROR_INTERNAL_SERVER = 500;
    public static final int ERROR_INVALID_AUK = -6;
    public static final int ERROR_INVALID_NONCE = -5;
    public static final int ERROR_NETWORK = -8;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_FOUND = 404;
    public static final int ERROR_PERMISSION = -2;
    public static final String ERROR_RETRY_AFTER = "Retry-After:";
    public static final int ERROR_SERVICE_UNAVAILABLE = 503;
    public static final int ERROR_TIMA_INTERNAL = -3;
    public static final int ERROR_UNAUTHORIZED = 401;
    public static final int ERROR_UNKNOWN = -1;
    public static final String TAG = "EAPolicyResult";
    public Bundle data;
    public int errorCode;
    public String reason;

    public EnhancedAttestationResult() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public byte[] getBlob() {
        Bundle bundle = this.data;
        if (bundle == null) {
            return null;
        }
        return bundle.getByteArray(DATA_FIELD_BLOB);
    }

    public int getError() {
        return this.errorCode;
    }

    public String getReason() {
        return this.reason;
    }

    public String getResponseRawData() {
        Bundle bundle = this.data;
        if (bundle == null) {
            return null;
        }
        return bundle.getString(DATA_FIELD_SERVER_RESPONSE_RAW_DATA);
    }

    public int getRetryAfterTime() {
        try {
            String str = this.reason;
            if (str == null || !str.contains(ERROR_RETRY_AFTER)) {
                return -1;
            }
            int parseInt = Integer.parseInt(this.reason.replace(ERROR_RETRY_AFTER, ""));
            if (parseInt > 0) {
                return parseInt;
            }
            return -1;
        } catch (Exception e) {
            Log.i(TAG, "getRetryAfterTime: " + e.toString());
            return -1;
        }
    }

    public String getServerResponseId() {
        Bundle bundle = this.data;
        if (bundle == null) {
            return null;
        }
        return bundle.getString(DATA_FIELD_SERVER_RESPONSE_ID);
    }

    public String getUniqueId() {
        Bundle bundle = this.data;
        if (bundle == null) {
            return null;
        }
        return bundle.getString(DATA_FIELD_UNIQUE_ID);
    }

    public String getUrl() {
        Bundle bundle = this.data;
        if (bundle == null) {
            return null;
        }
        return bundle.getString(DATA_FIELD_URL);
    }

    public void readFromParcel(Parcel parcel) {
        this.errorCode = parcel.readInt();
        this.reason = parcel.readString();
        this.data = parcel.readBundle();
    }

    public void setData(Bundle bundle) {
        this.data = bundle;
    }

    public void setErrorCode(int i) {
        this.errorCode = i;
    }

    public void setReason(String str) {
        this.reason = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.errorCode);
        parcel.writeString(this.reason);
        parcel.writeBundle(this.data);
    }

    public EnhancedAttestationResult(Parcel parcel) {
        readFromParcel(parcel);
    }
}
