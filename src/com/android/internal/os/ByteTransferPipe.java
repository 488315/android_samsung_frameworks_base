package com.android.internal.os;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class ByteTransferPipe extends TransferPipe {
    static final String TAG = "ByteTransferPipe";
    private ByteArrayOutputStream mOutputStream;

    public ByteTransferPipe() throws IOException {
    }

    public ByteTransferPipe(String str) throws IOException {
        super(str, TAG);
    }

    @Override // com.android.internal.os.TransferPipe
    protected OutputStream getNewOutputStream() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.mOutputStream = byteArrayOutputStream;
        return byteArrayOutputStream;
    }

    public byte[] get() throws IOException {
        go(null);
        return this.mOutputStream.toByteArray();
    }
}
