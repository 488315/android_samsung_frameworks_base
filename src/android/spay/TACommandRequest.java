package android.spay;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class TACommandRequest implements Parcelable {
    public static final Parcelable.Creator<TACommandRequest> CREATOR = new Parcelable.Creator<TACommandRequest>() { // from class: android.spay.TACommandRequest.1
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
    private static boolean DEBUG = true;
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
        int readInt = parcel.readInt();
        if (readInt > 0) {
            byte[] bArr = new byte[readInt];
            this.mMagicNum = bArr;
            parcel.readByteArray(bArr);
        }
        this.mCommandId = parcel.readInt();
        this.mLength = parcel.readInt();
        this.mOffset = parcel.readInt();
        int readInt2 = parcel.readInt();
        if (readInt2 > 0) {
            byte[] bArr2 = new byte[readInt2];
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
        int i;
        int i2;
        ArrayList arrayList = new ArrayList();
        byte[] bArr = this.mRequest;
        if (bArr == null) {
            return null;
        }
        if (bArr.length <= 2972) {
            arrayList.add(this);
            if (DEBUG) {
                Log.i(TAG, "no need to divide the mRequest, len=" + this.mRequest.length);
                return arrayList;
            }
        } else {
            if (DEBUG) {
                Log.i(TAG, "dividing the mRequest, len=" + this.mRequest.length);
            }
            int i3 = 0;
            while (true) {
                i = i3;
                i3 = i + 2972;
                i2 = this.mLength;
                if (i3 >= i2) {
                    break;
                }
                if (DEBUG) {
                    Log.i(TAG, "generating the chunk from " + i + " to " + (i + 2971));
                }
                arrayList.add(new TACommandRequest(this.mVersion, this.mMagicNum, this.mCommandId, this.mLength, i, Arrays.copyOfRange(this.mRequest, i, i3)));
            }
            arrayList.add(new TACommandRequest(this.mVersion, this.mMagicNum, this.mCommandId, i2, i, Arrays.copyOfRange(this.mRequest, i, i2)));
            if (DEBUG) {
                StringBuilder sb = new StringBuilder("generating the chunk from ");
                sb.append(i);
                sb.append(" to ");
                sb.append(this.mLength - 1);
                Log.i(TAG, sb.toString());
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a0 A[Catch: Exception -> 0x00a9, IOException -> 0x00ae, TRY_ENTER, TryCatch #7 {IOException -> 0x00ae, Exception -> 0x00a9, blocks: (B:19:0x0083, B:26:0x00a0, B:28:0x00a5), top: B:12:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a5 A[Catch: Exception -> 0x00a9, IOException -> 0x00ae, TRY_LEAVE, TryCatch #7 {IOException -> 0x00ae, Exception -> 0x00a9, blocks: (B:19:0x0083, B:26:0x00a0, B:28:0x00a5), top: B:12:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00c0 A[Catch: Exception -> 0x00ba, IOException -> 0x00bc, TRY_LEAVE, TryCatch #8 {IOException -> 0x00bc, Exception -> 0x00ba, blocks: (B:51:0x00b6, B:41:0x00c0), top: B:50:0x00b6 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00b6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.io.FileWriter] */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.io.FileWriter, java.io.Writer] */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.FileWriter] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dump() {
        /*
            Method dump skipped, instructions count: 204
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.spay.TACommandRequest.dump():void");
    }
}
