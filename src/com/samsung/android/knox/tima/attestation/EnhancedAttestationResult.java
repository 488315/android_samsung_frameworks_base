package com.samsung.android.knox.tima.attestation;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class EnhancedAttestationResult implements Parcelable {
    public static final Parcelable.Creator<EnhancedAttestationResult> CREATOR = new Parcelable.Creator<EnhancedAttestationResult>() { // from class: com.samsung.android.knox.tima.attestation.EnhancedAttestationResult.1
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public EnhancedAttestationResult() {
    }

    private EnhancedAttestationResult(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel != null) {
            parcel.writeInt(this.errorCode);
            parcel.writeString(this.reason);
            parcel.writeBundle(this.data);
        }
    }

    private void readFromParcel(Parcel parcel) {
        try {
            this.errorCode = parcel.readInt();
            this.reason = parcel.readString();
            this.data = parcel.readBundle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setErrorCode(int i) {
        this.errorCode = i;
    }

    public void setData(Bundle bundle) {
        this.data = bundle;
    }

    public void setReason(String str) {
        this.reason = str;
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

    int getRetryAfterTime() throws NumberFormatException {
        try {
            String str = this.reason;
            if (str == null || !str.contains(ERROR_RETRY_AFTER)) {
                return -1;
            }
            int i = Integer.parseInt(this.reason.replace(ERROR_RETRY_AFTER, ""));
            if (i > 0) {
                return i;
            }
            return -1;
        } catch (Exception e) {
            Log.i(TAG, "getRetryAfterTime: " + e.toString());
            return -1;
        }
    }
}
