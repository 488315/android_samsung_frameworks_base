package com.samsung.android.knox.mpos;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public class TACommandRequest implements Parcelable {
    public static final Parcelable.Creator<TACommandRequest> CREATOR = new Parcelable.Creator<TACommandRequest>() { // from class: com.samsung.android.knox.mpos.TACommandRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandRequest createFromParcel(Parcel parcel) {
            return new TACommandRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TACommandRequest[] newArray(int i) {
            return new TACommandRequest[i];
        }
    };
    public static final int HEADER_SIZE = 100;
    public static final int MAX_BUFFER_SIZE = 5242880;
    public static final int MAX_DATA_TRANSACTION_SIZE = 3072;
    public static final int PAYLOAD_SIZE = 2972;
    private static final String TAG = "TACommandRequest";
    public int mCommandId;
    public int mLength;
    public byte[] mMagicNum;
    public int mOffset;
    public byte[] mRequest;
    public int mVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void dump() {
    }

    public TACommandRequest() {
        this.mVersion = -1;
        this.mMagicNum = null;
        this.mLength = 0;
        this.mOffset = 0;
        this.mCommandId = -1;
        this.mRequest = null;
    }

    public void init(int i, byte[] bArr, int i2, byte[] bArr2) {
        this.mVersion = i;
        this.mMagicNum = bArr;
        this.mCommandId = i2;
        this.mRequest = bArr2;
        if (bArr2 != null) {
            this.mLength = bArr2.length;
        } else {
            this.mLength = 0;
        }
        this.mOffset = 0;
    }

    private TACommandRequest(Parcel parcel) {
        this.mVersion = -1;
        this.mMagicNum = null;
        this.mLength = 0;
        this.mOffset = 0;
        this.mCommandId = -1;
        this.mRequest = null;
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mVersion);
        byte[] bArr = this.mMagicNum;
        if (bArr == null || bArr.length != 4) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(bArr.length);
            parcel.writeByteArray(this.mMagicNum);
        }
        parcel.writeInt(this.mCommandId);
        parcel.writeInt(this.mLength);
        parcel.writeInt(this.mOffset);
        byte[] bArr2 = this.mRequest;
        if (bArr2 == null || bArr2.length == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(bArr2.length);
            parcel.writeByteArray(this.mRequest);
        }
    }

    public void readFromParcel(Parcel parcel) {
        this.mVersion = parcel.readInt();
        int i = parcel.readInt();
        if (i > 0) {
            byte[] bArr = new byte[i];
            this.mMagicNum = bArr;
            parcel.readByteArray(bArr);
        }
        this.mCommandId = parcel.readInt();
        this.mLength = parcel.readInt();
        this.mOffset = parcel.readInt();
        int i2 = parcel.readInt();
        if (i2 > 0) {
            byte[] bArr2 = new byte[i2];
            this.mRequest = bArr2;
            parcel.readByteArray(bArr2);
        }
    }

    private TACommandRequest(int i, byte[] bArr, int i2, int i3, int i4, byte[] bArr2) {
        this.mVersion = i;
        this.mMagicNum = bArr;
        this.mCommandId = i2;
        this.mLength = i3;
        this.mOffset = i4;
        this.mRequest = bArr2;
    }

    public int getTotalLength() {
        return this.mLength;
    }

    public int getChunkOffset() {
        return this.mOffset;
    }

    public byte[] getPayload() {
        return this.mRequest;
    }

    public List<TACommandRequest> disassemble() {
        ArrayList arrayList = new ArrayList();
        byte[] bArr = this.mRequest;
        if (bArr == null) {
            return null;
        }
        if (bArr.length <= 2972) {
            arrayList.add(this);
            return arrayList;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            i = i2 + 2972;
            int i3 = this.mLength;
            if (i < i3) {
                arrayList.add(new TACommandRequest(this.mVersion, this.mMagicNum, this.mCommandId, i3, i2, Arrays.copyOfRange(this.mRequest, i2, i)));
            } else {
                arrayList.add(new TACommandRequest(this.mVersion, this.mMagicNum, this.mCommandId, i3, i2, Arrays.copyOfRange(this.mRequest, i2, i3)));
                return arrayList;
            }
        }
    }
}
