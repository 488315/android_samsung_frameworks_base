package com.android.server.knox.dar.ddar.ta;

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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dump() {
        /*
            Method dump skipped, instructions count: 235
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.knox.dar.ddar.ta.TACommandRequest.dump():void");
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
