package com.samsung.android.knox.mpos;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class TACommandResponse implements Parcelable {
    public static final Parcelable.Creator<TACommandResponse> CREATOR = new Parcelable.Creator<TACommandResponse>() { // from class: com.samsung.android.knox.mpos.TACommandResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandResponse createFromParcel(Parcel parcel) {
            return new TACommandResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandResponse[] newArray(int i) {
            return new TACommandResponse[i];
        }
    };
    private static final String TAG = "TACommandResponse";
    public String mErrorMsg;
    public byte[] mResponse;
    public int mResponseCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void dump() {
    }

    public TACommandResponse() {
        this.mResponseCode = -1;
        this.mErrorMsg = null;
        this.mResponse = null;
    }

    public TACommandResponse(int i, String str, byte[] bArr) {
        this.mResponseCode = i;
        this.mErrorMsg = str;
        this.mResponse = bArr;
    }

    private TACommandResponse(Parcel parcel) {
        this.mResponseCode = -1;
        this.mErrorMsg = null;
        this.mResponse = null;
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResponseCode);
        parcel.writeString(this.mErrorMsg);
        byte[] bArr = this.mResponse;
        int length = bArr == null ? 0 : bArr.length;
        parcel.writeInt(length);
        if (length > 0) {
            parcel.writeByteArray(this.mResponse);
        }
    }

    public void readFromParcel(Parcel parcel) {
        this.mResponseCode = parcel.readInt();
        this.mErrorMsg = parcel.readString();
        int readInt = parcel.readInt();
        if (readInt > 0) {
            byte[] bArr = new byte[readInt];
            this.mResponse = bArr;
            parcel.readByteArray(bArr);
        }
    }
}
