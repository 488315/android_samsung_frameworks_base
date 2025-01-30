package com.samsung.android.knox.net.firewall;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FirewallResponse implements Parcelable {
    public static final Parcelable.Creator<FirewallResponse> CREATOR = new Parcelable.Creator<FirewallResponse>() { // from class: com.samsung.android.knox.net.firewall.FirewallResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final FirewallResponse[] newArray(int i) {
            return new FirewallResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final FirewallResponse createFromParcel(Parcel parcel) {
            return new FirewallResponse(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final FirewallResponse[] newArray(int i) {
            return new FirewallResponse[i];
        }
    };
    public ErrorCode mErrorCode;
    public String mMessage;
    public Result mResult;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum ErrorCode {
        NO_ERROR,
        DATABASE_ERROR,
        INVALID_PARAMETER_ERROR,
        OPERATION_NOT_PERMITTED_ERROR,
        NOT_AUTHORIZED_ERROR,
        IPV6_NOT_SUPPORTED_ERROR,
        UNEXPECTED_ERROR,
        INPUT_CHAIN_NOT_SUPPORTED_ERROR
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Result {
        SUCCESS,
        NO_CHANGES,
        FAILED
    }

    public /* synthetic */ FirewallResponse(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final ErrorCode getErrorCode() {
        return this.mErrorCode;
    }

    public final String getMessage() {
        return this.mMessage;
    }

    public final Result getResult() {
        return this.mResult;
    }

    public final void setErrorCode(ErrorCode errorCode) {
        this.mErrorCode = errorCode;
    }

    public final void setMessage(String str) {
        this.mMessage = str;
    }

    public final void setResult(Result result) {
        this.mResult = result;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(this.mResult);
        parcel.writeString(this.mMessage);
        parcel.writeSerializable(this.mErrorCode);
    }

    public FirewallResponse(Result result, ErrorCode errorCode, String str) {
        this.mResult = result;
        this.mMessage = str;
        this.mErrorCode = errorCode;
    }

    private FirewallResponse(Parcel parcel) {
        this.mResult = (Result) parcel.readSerializable();
        this.mMessage = parcel.readString();
        this.mErrorCode = (ErrorCode) parcel.readSerializable();
    }
}
