package com.android.internal.protolog;

import android.util.proto.ProtoInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public final class AutoClosableProtoInputStream implements AutoCloseable {
    private final FileInputStream mFileInputStream;
    private final ProtoInputStream mProtoInputStream;

    public AutoClosableProtoInputStream(FileInputStream fileInputStream) {
        this.mProtoInputStream = new ProtoInputStream(fileInputStream);
        this.mFileInputStream = fileInputStream;
    }

    public AutoClosableProtoInputStream(byte[] bArr) {
        this.mProtoInputStream = new ProtoInputStream(bArr);
        this.mFileInputStream = null;
    }

    public ProtoInputStream get() {
        return this.mProtoInputStream;
    }

    @Override // java.lang.AutoCloseable
    public void close() throws IOException {
        FileInputStream fileInputStream = this.mFileInputStream;
        if (fileInputStream != null) {
            fileInputStream.close();
        }
    }
}
