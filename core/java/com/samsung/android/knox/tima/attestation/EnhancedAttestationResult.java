package com.samsung.android.knox.tima.attestation;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class EnhancedAttestationResult implements Parcelable {
    public static final Parcelable.Creator<EnhancedAttestationResult> CREATOR = new Parcelable.Creator<EnhancedAttestationResult>() { // from class: com.samsung.android.knox.tima.attestation.EnhancedAttestationResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnhancedAttestationResult createFromParcel(Parcel source) {
            return new EnhancedAttestationResult(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnhancedAttestationResult[] newArray(int size) {
            return new EnhancedAttestationResult[size];
        }
    };
    static final String DATA_FIELD_BLOB = "dataFieldBlob";
    static final String DATA_FIELD_SERVER_RESPONSE_ID = "serverResponseId";
    static final String DATA_FIELD_SERVER_RESPONSE_RAW_DATA = "serverResponseRawData";
    static final String DATA_FIELD_UNIQUE_ID = "dataFieldUniqueId";
    static final String DATA_FIELD_URL = "dataFieldUrl";
    static final int ERROR_BAD_REQUEST = 400;
    static final int ERROR_BIND_FAIL = -7;
    static final int ERROR_CONFLICT = 409;
    static final int ERROR_DEVICE_NOT_SUPPORTED = -4;
    static final int ERROR_FORBIDDEN = 403;
    static final int ERROR_INTERNAL_SERVER = 500;
    static final int ERROR_INVALID_AUK = -6;
    static final int ERROR_INVALID_NONCE = -5;
    static final int ERROR_NETWORK = -8;
    static final int ERROR_NONE = 0;
    static final int ERROR_NOT_FOUND = 404;
    static final int ERROR_PERMISSION = -2;
    static final String ERROR_RETRY_AFTER = "Retry-After:";
    static final int ERROR_SERVICE_UNAVAILABLE = 503;
    static final int ERROR_TIMA_INTERNAL = -3;
    static final int ERROR_UNAUTHORIZED = 401;
    static final int ERROR_UNKNOWN = -1;
    private static final String TAG = "SEMEAPolicyResult";
    private Bundle data;
    private int errorCode;
    private String reason;

    public EnhancedAttestationResult() {
    }

    private EnhancedAttestationResult(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int arg) {
        dest.writeInt(this.errorCode);
        dest.writeString(this.reason);
        dest.writeBundle(this.data);
    }

    private void readFromParcel(Parcel in) {
        this.errorCode = in.readInt();
        this.reason = in.readString();
        this.data = in.readBundle();
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setData(Bundle data) {
        this.data = data;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    int getError() {
        return this.errorCode;
    }

    String getReason() {
        return this.reason;
    }

    String getUniqueId() {
        Bundle bundle = this.data;
        if (bundle == null) {
            return null;
        }
        return bundle.getString(DATA_FIELD_UNIQUE_ID);
    }

    String getUrl() {
        Bundle bundle = this.data;
        if (bundle == null) {
            return null;
        }
        return bundle.getString(DATA_FIELD_URL);
    }

    String getResponseRawData() {
        Bundle bundle = this.data;
        if (bundle == null) {
            return null;
        }
        return bundle.getString(DATA_FIELD_SERVER_RESPONSE_RAW_DATA);
    }

    byte[] getBlob() {
        Bundle bundle = this.data;
        if (bundle == null) {
            return null;
        }
        return bundle.getByteArray(DATA_FIELD_BLOB);
    }

    String getServerResponseId() {
        Bundle bundle = this.data;
        if (bundle == null) {
            return null;
        }
        return bundle.getString(DATA_FIELD_SERVER_RESPONSE_ID);
    }

    int getRetryAfterTime() {
        try {
            String str = this.reason;
            if (str == null || !str.contains(ERROR_RETRY_AFTER)) {
                return -1;
            }
            int tempTime = Integer.parseInt(this.reason.replace(ERROR_RETRY_AFTER, ""));
            if (tempTime > 0) {
                return tempTime;
            }
            return -1;
        } catch (Exception e) {
            Log.m98i(TAG, "getRetryAfterTime: " + e.toString());
            return -1;
        }
    }
}
