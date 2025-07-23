package android.spay;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class TACommandResponse implements Parcelable {
    public static final Parcelable.Creator<TACommandResponse> CREATOR = new Parcelable.Creator<TACommandResponse>() { // from class: android.spay.TACommandResponse.1
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
        parcel.writeInt(this.mResponse.length);
        parcel.writeByteArray(this.mResponse);
    }

    public void readFromParcel(Parcel parcel) {
        this.mResponseCode = parcel.readInt();
        this.mErrorMsg = parcel.readString();
        byte[] bArr = new byte[parcel.readInt()];
        this.mResponse = bArr;
        parcel.readByteArray(bArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x008a A[Catch: Exception -> 0x0093, IOException -> 0x0098, TRY_ENTER, TryCatch #10 {IOException -> 0x0098, Exception -> 0x0093, blocks: (B:23:0x006d, B:30:0x008a, B:32:0x008f), top: B:17:0x005a }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x008f A[Catch: Exception -> 0x0093, IOException -> 0x0098, TRY_LEAVE, TryCatch #10 {IOException -> 0x0098, Exception -> 0x0093, blocks: (B:23:0x006d, B:30:0x008a, B:32:0x008f), top: B:17:0x005a }] */
    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00aa A[Catch: Exception -> 0x00a4, IOException -> 0x00a6, TRY_LEAVE, TryCatch #9 {IOException -> 0x00a6, Exception -> 0x00a4, blocks: (B:49:0x00a0, B:39:0x00aa), top: B:48:0x00a0 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dump() {
        /*
            r6 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Length = "
            r0.<init>(r1)
            byte[] r1 = r6.mResponse
            int r1 = r1.length
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "TACommandResponse"
            android.util.Log.d(r1, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            byte[] r2 = r6.mResponse
            int r2 = r2.length
            int r2 = r2 * 3
            int r2 = r2 + 100
            r0.<init>(r2)
            r2 = 0
            r3 = r2
        L24:
            byte[] r4 = r6.mResponse
            int r5 = r4.length
            if (r3 >= r5) goto L52
            if (r3 <= 0) goto L3a
            r5 = r4[r3]
            if (r5 == 0) goto L3a
            int r5 = r3 + (-1)
            r4 = r4[r5]
            if (r4 != 0) goto L3a
            java.lang.String r4 = "\n"
            r0.append(r4)
        L3a:
            byte[] r4 = r6.mResponse
            r4 = r4[r3]
            java.lang.Byte r4 = java.lang.Byte.valueOf(r4)
            java.lang.Object[] r4 = new java.lang.Object[]{r4}
            java.lang.String r5 = "%02X "
            java.lang.String r4 = java.lang.String.format(r5, r4)
            r0.append(r4)
            int r3 = r3 + 1
            goto L24
        L52:
            java.lang.String r6 = r0.toString()
            android.util.Log.d(r1, r6)
            r6 = 0
            java.io.FileWriter r1 = new java.io.FileWriter     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L81
            java.lang.String r3 = "/mnt/sdcard/respbuf.txt"
            r1.<init>(r3, r2)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L81
            java.io.BufferedWriter r2 = new java.io.BufferedWriter     // Catch: java.lang.Throwable -> L76 java.lang.Exception -> L79
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L76 java.lang.Exception -> L79
            java.lang.String r6 = r0.toString()     // Catch: java.lang.Exception -> L74 java.lang.Throwable -> L9d
            r2.append(r6)     // Catch: java.lang.Exception -> L74 java.lang.Throwable -> L9d
            r2.close()     // Catch: java.lang.Exception -> L93 java.io.IOException -> L98
            r1.close()     // Catch: java.lang.Exception -> L93 java.io.IOException -> L98
            return
        L74:
            r6 = move-exception
            goto L85
        L76:
            r0 = move-exception
            r2 = r6
            goto L7f
        L79:
            r0 = move-exception
            r2 = r6
            goto L84
        L7c:
            r0 = move-exception
            r1 = r6
            r2 = r1
        L7f:
            r6 = r0
            goto L9e
        L81:
            r0 = move-exception
            r1 = r6
            r2 = r1
        L84:
            r6 = r0
        L85:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L9d
            if (r2 == 0) goto L8d
            r2.close()     // Catch: java.lang.Exception -> L93 java.io.IOException -> L98
        L8d:
            if (r1 == 0) goto L9c
            r1.close()     // Catch: java.lang.Exception -> L93 java.io.IOException -> L98
            goto L9c
        L93:
            r6 = move-exception
            r6.printStackTrace()
            goto L9c
        L98:
            r6 = move-exception
            r6.printStackTrace()
        L9c:
            return
        L9d:
            r6 = move-exception
        L9e:
            if (r2 == 0) goto La8
            r2.close()     // Catch: java.lang.Exception -> La4 java.io.IOException -> La6
            goto La8
        La4:
            r0 = move-exception
            goto Lae
        La6:
            r0 = move-exception
            goto Lb2
        La8:
            if (r1 == 0) goto Lb5
            r1.close()     // Catch: java.lang.Exception -> La4 java.io.IOException -> La6
            goto Lb5
        Lae:
            r0.printStackTrace()
            goto Lb5
        Lb2:
            r0.printStackTrace()
        Lb5:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.spay.TACommandResponse.dump():void");
    }
}
