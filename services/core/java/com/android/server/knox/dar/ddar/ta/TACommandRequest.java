package com.android.server.knox.dar.ddar.ta;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class TACommandRequest {
    public static final int HEADER_SIZE = 100;
    public static final int MAX_BUFFER_SIZE = 5242880;
    public static final int MAX_DATA_TRANSACTION_SIZE = 3072;
    public static final int PAYLOAD_SIZE = 2972;
    private static final String TAG = "TACommandRequest";
    public int mVersion = -1;
    public byte[] mMagicNum = null;
    public int mLength = 0;
    public int mCommandId = -1;
    public byte[] mRequest = null;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ca A[Catch: Exception -> 0x00a5, IOException -> 0x00a7, TRY_ENTER, TryCatch #10 {IOException -> 0x00a7, Exception -> 0x00a5, blocks: (B:19:0x009e, B:28:0x00ca, B:30:0x00cf), top: B:12:0x0089 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00cf A[Catch: Exception -> 0x00a5, IOException -> 0x00a7, TRY_LEAVE, TryCatch #10 {IOException -> 0x00a7, Exception -> 0x00a5, blocks: (B:19:0x009e, B:28:0x00ca, B:30:0x00cf), top: B:12:0x0089 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00df A[Catch: Exception -> 0x00d9, IOException -> 0x00db, TRY_LEAVE, TryCatch #8 {IOException -> 0x00db, Exception -> 0x00d9, blocks: (B:50:0x00d5, B:40:0x00df), top: B:49:0x00d5 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00d5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v13, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15, types: [java.io.FileWriter] */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21, types: [java.io.FileWriter] */
    /* JADX WARN: Type inference failed for: r2v22, types: [java.io.FileWriter, java.io.Writer] */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dump() {
        /*
            Method dump skipped, instructions count: 235
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.ddar.ta.TACommandRequest.dump():void");
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
    }
}
