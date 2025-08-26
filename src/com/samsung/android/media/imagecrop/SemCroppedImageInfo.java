package com.samsung.android.media.imagecrop;

import android.util.Log;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class SemCroppedImageInfo {
    private static final String TAG = "imagecrop";
    private ByteBuffer buffer;
    private int width = -1;
    private int height = -1;

    SemCroppedImageInfo(int i) {
        this.buffer = NativeBuffer.allocNativeBuffer(i);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public ByteBuffer getByteBuffer() {
        return this.buffer;
    }

    void reAllocInJavaBuffer(int i) {
        Log.d(TAG, "reAllocate : " + i);
        this.buffer.limit(i);
        this.buffer.rewind();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i);
        byteBufferAllocate.put(this.buffer);
        byteBufferAllocate.flip();
        NativeBuffer.freeNativeBuffer(this.buffer);
        this.buffer = byteBufferAllocate;
    }

    Buffer limit(int i) {
        return this.buffer.limit(i);
    }

    Buffer rewind() {
        return this.buffer.rewind();
    }

    Buffer flip() {
        return this.buffer.flip();
    }
}
