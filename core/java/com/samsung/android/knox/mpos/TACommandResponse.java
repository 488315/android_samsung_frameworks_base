package com.samsung.android.knox.mpos;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class TACommandResponse implements Parcelable {
    public static final Parcelable.Creator<TACommandResponse> CREATOR = new Parcelable.Creator<TACommandResponse>() { // from class: com.samsung.android.knox.mpos.TACommandResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandResponse createFromParcel(Parcel in) {
            return new TACommandResponse(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandResponse[] newArray(int size) {
            return new TACommandResponse[size];
        }
    };
    private static final String TAG = "TACommandResponse";
    public String mErrorMsg;
    public byte[] mResponse;
    public int mResponseCode;

    public TACommandResponse() {
        this.mResponseCode = -1;
        this.mErrorMsg = null;
        this.mResponse = null;
    }

    public TACommandResponse(int responseId, String errorMsg, byte[] response) {
        this.mResponseCode = -1;
        this.mErrorMsg = null;
        this.mResponse = null;
        this.mResponseCode = responseId;
        this.mErrorMsg = errorMsg;
        this.mResponse = response;
    }

    private TACommandResponse(Parcel in) {
        this.mResponseCode = -1;
        this.mErrorMsg = null;
        this.mResponse = null;
        readFromParcel(in);
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        out.writeInt(this.mResponseCode);
        out.writeString(this.mErrorMsg);
        byte[] bArr = this.mResponse;
        int len = bArr == null ? 0 : bArr.length;
        out.writeInt(len);
        if (len > 0) {
            out.writeByteArray(this.mResponse);
        }
    }

    public void readFromParcel(Parcel in) {
        this.mResponseCode = in.readInt();
        this.mErrorMsg = in.readString();
        int len = in.readInt();
        if (len > 0) {
            byte[] bArr = new byte[len];
            this.mResponse = bArr;
            in.readByteArray(bArr);
        }
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void dump() {
    }
}
