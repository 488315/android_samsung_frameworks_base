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

    SemCroppedImageInfo(int size) {
        this.buffer = NativeBuffer.allocNativeBuffer(size);
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

    void reAllocInJavaBuffer(int newSize) {
        Log.d(TAG, "reAllocate : " + newSize);
        this.buffer.limit(newSize);
        this.buffer.rewind();
        ByteBuffer copyBuffer = ByteBuffer.allocate(newSize);
        copyBuffer.put(this.buffer);
        copyBuffer.flip();
        NativeBuffer.freeNativeBuffer(this.buffer);
        this.buffer = copyBuffer;
    }

    Buffer limit(int limit) {
        return this.buffer.limit(limit);
    }

    Buffer rewind() {
        return this.buffer.rewind();
    }

    Buffer flip() {
        return this.buffer.flip();
    }
}
