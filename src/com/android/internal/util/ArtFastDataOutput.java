package com.android.internal.util;

import android.util.CharsetUtils;
import com.android.modules.utils.FastDataOutput;
import dalvik.system.VMRuntime;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public class ArtFastDataOutput extends FastDataOutput {
    private static AtomicReference<ArtFastDataOutput> sOutCache = new AtomicReference<>();
    private static VMRuntime sRuntime = VMRuntime.getRuntime();
    private final long mBufferPtr;

    public ArtFastDataOutput(OutputStream outputStream, int i) {
        super(outputStream, i);
        this.mBufferPtr = sRuntime.addressOf(this.mBuffer);
    }

    public static ArtFastDataOutput obtain(OutputStream outputStream) {
        ArtFastDataOutput andSet = sOutCache.getAndSet(null);
        if (andSet != null) {
            andSet.setOutput(outputStream);
            return andSet;
        }
        return new ArtFastDataOutput(outputStream, 32768);
    }

    @Override // com.android.modules.utils.FastDataOutput
    public void release() {
        super.release();
        if (this.mBufferCap == 32768) {
            sOutCache.compareAndSet(null, this);
        }
    }

    @Override // com.android.modules.utils.FastDataOutput
    public byte[] newByteArray(int i) {
        return (byte[]) sRuntime.newNonMovableArray(Byte.TYPE, i);
    }

    @Override // com.android.modules.utils.FastDataOutput, java.io.DataOutput
    public void writeUTF(String str) throws IOException {
        if (this.mBufferCap - this.mBufferPos < str.length() + 2) {
            drain();
        }
        int modifiedUtf8Bytes = CharsetUtils.toModifiedUtf8Bytes(str, this.mBufferPtr, this.mBufferPos + 2, this.mBufferCap);
        if (Math.abs(modifiedUtf8Bytes) > 65535) {
            throw new IOException("Modified UTF-8 length too large: " + modifiedUtf8Bytes);
        }
        if (modifiedUtf8Bytes >= 0) {
            writeShort(modifiedUtf8Bytes);
            this.mBufferPos += modifiedUtf8Bytes;
            return;
        }
        int i = -modifiedUtf8Bytes;
        byte[] bArr = (byte[]) sRuntime.newNonMovableArray(Byte.TYPE, i + 1);
        CharsetUtils.toModifiedUtf8Bytes(str, sRuntime.addressOf(bArr), 0, bArr.length);
        writeShort(i);
        write(bArr, 0, i);
    }
}
