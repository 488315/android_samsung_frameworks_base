package com.android.internal.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.service.gatekeeper.GateKeeperResponse;
import android.util.Slog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: classes6.dex */
public final class VerifyCredentialResponse implements Parcelable {
    public static final int RESPONSE_ERROR = -1;
    public static final int RESPONSE_OK = 0;
    public static final int RESPONSE_RETRY = 1;
    public static final int RESPONSE_SKIP = 1;
    private static final String TAG = "VerifyCredentialResponse";
    private final byte[] mGatekeeperHAT;
    private final long mGatekeeperPasswordHandle;
    private final int mResponseCode;
    private byte[] mSecret;
    private final int mTimeout;
    public static final VerifyCredentialResponse OK = new Builder().build();
    public static final VerifyCredentialResponse ERROR = fromError();
    public static final VerifyCredentialResponse SKIP = fromTimeout(0);
    public static final Parcelable.Creator<VerifyCredentialResponse> CREATOR = new Parcelable.Creator<VerifyCredentialResponse>() { // from class: com.android.internal.widget.VerifyCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifyCredentialResponse createFromParcel(Parcel parcel) {
            return new VerifyCredentialResponse(parcel.readInt(), parcel.readInt(), parcel.createByteArray(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifyCredentialResponse[] newArray(int i) {
            return new VerifyCredentialResponse[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    @interface ResponseCode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Builder {
        private byte[] mGatekeeperHAT;
        private long mGatekeeperPasswordHandle;

        public Builder setGatekeeperHAT(byte[] bArr) {
            this.mGatekeeperHAT = bArr;
            return this;
        }

        public Builder setGatekeeperPasswordHandle(long j) {
            this.mGatekeeperPasswordHandle = j;
            return this;
        }

        public VerifyCredentialResponse build() {
            return new VerifyCredentialResponse(0, 0, this.mGatekeeperHAT, this.mGatekeeperPasswordHandle);
        }
    }

    public static VerifyCredentialResponse fromTimeout(int i) {
        return new VerifyCredentialResponse(1, i, null, 0L);
    }

    public static VerifyCredentialResponse fromError() {
        return new VerifyCredentialResponse(-1, 0, null, 0L);
    }

    private VerifyCredentialResponse(int i, int i2, byte[] bArr, long j) {
        this.mResponseCode = i;
        this.mTimeout = i2;
        this.mGatekeeperHAT = bArr;
        this.mGatekeeperPasswordHandle = j;
    }

    private VerifyCredentialResponse(int i, int i2, byte[] bArr, long j, byte[] bArr2) {
        this.mResponseCode = i;
        this.mTimeout = i2;
        this.mGatekeeperHAT = bArr;
        this.mGatekeeperPasswordHandle = j;
        this.mSecret = bArr2;
    }

    public VerifyCredentialResponse stripPayload() {
        return new VerifyCredentialResponse(this.mResponseCode, this.mTimeout, null, 0L);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResponseCode);
        parcel.writeInt(this.mTimeout);
        parcel.writeByteArray(this.mGatekeeperHAT);
        parcel.writeLong(this.mGatekeeperPasswordHandle);
    }

    public byte[] getGatekeeperHAT() {
        return this.mGatekeeperHAT;
    }

    public long getGatekeeperPasswordHandle() {
        return this.mGatekeeperPasswordHandle;
    }

    public boolean containsGatekeeperPasswordHandle() {
        return this.mGatekeeperPasswordHandle != 0;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }

    public boolean isMatched() {
        return this.mResponseCode == 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Response: ");
        sb.append(this.mResponseCode);
        sb.append(", GK HAT: ");
        sb.append(this.mGatekeeperHAT != null);
        sb.append(", GK PW: ");
        sb.append(this.mGatekeeperPasswordHandle != 0);
        return sb.toString();
    }

    public static VerifyCredentialResponse fromGateKeeperResponse(GateKeeperResponse gateKeeperResponse) {
        int responseCode = gateKeeperResponse.getResponseCode();
        if (responseCode == 1) {
            return fromTimeout(gateKeeperResponse.getTimeout());
        }
        if (responseCode == 0) {
            byte[] payload = gateKeeperResponse.getPayload();
            if (payload == null) {
                Slog.e(TAG, "verifyChallenge response had no associated payload");
                return fromError();
            }
            return new Builder().setGatekeeperHAT(payload).build();
        }
        return fromError();
    }

    public byte[] getSecret() {
        return this.mSecret;
    }

    public void setSecret(byte[] bArr) {
        this.mSecret = bArr;
    }

    public void destroy() {
        byte[] bArr = this.mSecret;
        if (bArr != null) {
            Arrays.fill(bArr, 0, bArr.length, (byte) 0);
        }
    }
}
