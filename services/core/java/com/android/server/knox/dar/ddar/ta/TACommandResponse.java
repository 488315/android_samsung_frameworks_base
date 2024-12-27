package com.android.server.knox.dar.ddar.ta;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class TACommandResponse {
    private static final String TAG = "TACommandResponse";
    public String mErrorMsg;
    public byte[] mResponse;
    public int mResponseCode;

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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009b A[Catch: Exception -> 0x0076, IOException -> 0x0078, TRY_ENTER, TryCatch #10 {IOException -> 0x0078, Exception -> 0x0076, blocks: (B:24:0x006f, B:33:0x009b, B:35:0x00a0), top: B:17:0x005a }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a0 A[Catch: Exception -> 0x0076, IOException -> 0x0078, TRY_LEAVE, TryCatch #10 {IOException -> 0x0078, Exception -> 0x0076, blocks: (B:24:0x006f, B:33:0x009b, B:35:0x00a0), top: B:17:0x005a }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b0 A[Catch: Exception -> 0x00aa, IOException -> 0x00ac, TRY_LEAVE, TryCatch #9 {IOException -> 0x00ac, Exception -> 0x00aa, blocks: (B:55:0x00a6, B:45:0x00b0), top: B:54:0x00a6 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00a6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15, types: [java.io.FileWriter] */
    /* JADX WARN: Type inference failed for: r2v16, types: [java.io.FileWriter, java.io.Writer] */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.io.FileWriter] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dump() {
        /*
            r7 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Length = "
            r0.<init>(r1)
            byte[] r1 = r7.mResponse
            int r1 = r1.length
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.String r3 = "TACommandResponse"
            com.android.server.knox.dar.ddar.DDLog.d(r3, r0, r2)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            byte[] r2 = r7.mResponse
            int r2 = r2.length
            int r2 = r2 * 3
            int r2 = r2 + 100
            r0.<init>(r2)
            r2 = r1
        L26:
            byte[] r4 = r7.mResponse
            int r5 = r4.length
            if (r2 >= r5) goto L50
            if (r2 <= 0) goto L3c
            r5 = r4[r2]
            if (r5 == 0) goto L3c
            int r5 = r2 + (-1)
            r4 = r4[r5]
            if (r4 != 0) goto L3c
            java.lang.String r4 = "\n"
            r0.append(r4)
        L3c:
            byte[] r4 = r7.mResponse
            r4 = r4[r2]
            java.lang.Byte r4 = java.lang.Byte.valueOf(r4)
            java.lang.Object[] r4 = new java.lang.Object[]{r4}
            java.lang.String r5 = "%02X "
            r6 = 1
            int r2 = com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(r5, r4, r0, r2, r6)
            goto L26
        L50:
            java.lang.String r7 = r0.toString()
            java.lang.Object[] r2 = new java.lang.Object[r1]
            com.android.server.knox.dar.ddar.DDLog.d(r3, r7, r2)
            r7 = 0
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L92
            java.lang.String r3 = "/mnt/sdcard/respbuf.txt"
            java.nio.charset.Charset r4 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L92
            r2.<init>(r3, r4, r1)     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L92
            java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L8a
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L8a
            java.lang.String r7 = r0.toString()     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L84
            r1.append(r7)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L84
            r1.close()     // Catch: java.lang.Exception -> L76 java.io.IOException -> L78
            r2.close()     // Catch: java.lang.Exception -> L76 java.io.IOException -> L78
            goto La3
        L76:
            r7 = move-exception
            goto L7a
        L78:
            r7 = move-exception
            goto L7e
        L7a:
            r7.printStackTrace()
            goto La3
        L7e:
            r7.printStackTrace()
            goto La3
        L82:
            r7 = move-exception
            goto La4
        L84:
            r7 = move-exception
            goto L96
        L86:
            r0 = move-exception
            r1 = r7
        L88:
            r7 = r0
            goto La4
        L8a:
            r0 = move-exception
            r1 = r7
        L8c:
            r7 = r0
            goto L96
        L8e:
            r0 = move-exception
            r1 = r7
            r2 = r1
            goto L88
        L92:
            r0 = move-exception
            r1 = r7
            r2 = r1
            goto L8c
        L96:
            r7.printStackTrace()     // Catch: java.lang.Throwable -> L82
            if (r1 == 0) goto L9e
            r1.close()     // Catch: java.lang.Exception -> L76 java.io.IOException -> L78
        L9e:
            if (r2 == 0) goto La3
            r2.close()     // Catch: java.lang.Exception -> L76 java.io.IOException -> L78
        La3:
            return
        La4:
            if (r1 == 0) goto Lae
            r1.close()     // Catch: java.lang.Exception -> Laa java.io.IOException -> Lac
            goto Lae
        Laa:
            r0 = move-exception
            goto Lb4
        Lac:
            r0 = move-exception
            goto Lb8
        Lae:
            if (r2 == 0) goto Lbb
            r2.close()     // Catch: java.lang.Exception -> Laa java.io.IOException -> Lac
            goto Lbb
        Lb4:
            r0.printStackTrace()
            goto Lbb
        Lb8:
            r0.printStackTrace()
        Lbb:
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.knox.dar.ddar.ta.TACommandResponse.dump():void");
    }
}
