package com.android.internal.util;

import android.util.CharsetUtils;
import com.android.modules.utils.FastDataInput;
import dalvik.system.VMRuntime;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public class ArtFastDataInput extends FastDataInput {
    private static AtomicReference<ArtFastDataInput> sInCache = new AtomicReference<>();
    private static VMRuntime sRuntime = VMRuntime.getRuntime();
    private final long mBufferPtr;

    public ArtFastDataInput(InputStream inputStream, int i) {
        super(inputStream, i);
        this.mBufferPtr = sRuntime.addressOf(this.mBuffer);
    }

    public static ArtFastDataInput obtain(InputStream inputStream) {
        ArtFastDataInput andSet = sInCache.getAndSet(null);
        if (andSet != null) {
            andSet.setInput(inputStream);
            return andSet;
        }
        return new ArtFastDataInput(inputStream, 32768);
    }

    @Override // com.android.modules.utils.FastDataInput
    public void release() {
        super.release();
        if (this.mBufferCap == 32768) {
            sInCache.compareAndSet(null, this);
        }
    }

    @Override // com.android.modules.utils.FastDataInput
    public byte[] newByteArray(int i) {
        return (byte[]) sRuntime.newNonMovableArray(Byte.TYPE, i);
    }

    @Override // com.android.modules.utils.FastDataInput, java.io.DataInput
    public String readUTF() throws IOException {
        int readUnsignedShort = readUnsignedShort();
        if (this.mBufferCap > readUnsignedShort) {
            if (this.mBufferLim - this.mBufferPos < readUnsignedShort) {
                fill(readUnsignedShort);
            }
            String fromModifiedUtf8Bytes = CharsetUtils.fromModifiedUtf8Bytes(this.mBufferPtr, this.mBufferPos, readUnsignedShort);
            this.mBufferPos += readUnsignedShort;
            return fromModifiedUtf8Bytes;
        }
        byte[] bArr = (byte[]) sRuntime.newNonMovableArray(Byte.TYPE, readUnsignedShort + 1);
        readFully(bArr, 0, readUnsignedShort);
        return CharsetUtils.fromModifiedUtf8Bytes(sRuntime.addressOf(bArr), 0, readUnsignedShort);
    }
}
