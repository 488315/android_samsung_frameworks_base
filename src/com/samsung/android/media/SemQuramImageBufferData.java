package com.samsung.android.media;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class SemQuramImageBufferData {
    public Bitmap bitmap;
    public ByteBuffer buffer;
    public long handle;
    public int height;
    public Type type;
    public int width;

    public enum Type {
        BITMAP,
        BUFFER,
        HANDLE
    }

    public SemQuramImageBufferData() {
        this.type = Type.BITMAP;
        this.width = 0;
        this.height = 0;
        this.bitmap = null;
        this.buffer = null;
        this.handle = 0L;
    }

    public SemQuramImageBufferData(Bitmap bitmap) {
        this.type = Type.BITMAP;
        if (bitmap != null) {
            this.width = bitmap.getWidth();
            this.height = bitmap.getHeight();
            this.bitmap = bitmap;
        } else {
            this.width = 0;
            this.height = 0;
            this.bitmap = null;
        }
        this.buffer = null;
        this.handle = 0L;
    }

    public SemQuramImageBufferData(int i, int i2, ByteBuffer byteBuffer) {
        this.type = Type.BUFFER;
        this.width = i;
        this.height = i2;
        this.bitmap = null;
        this.buffer = byteBuffer;
        this.handle = 0L;
    }

    public SemQuramImageBufferData(int i, int i2, long j) {
        this.type = Type.HANDLE;
        this.width = i;
        this.height = i2;
        this.bitmap = null;
        this.buffer = null;
        this.handle = j;
    }

    public boolean isEmpty() {
        int iOrdinal = this.type.ordinal();
        return iOrdinal != 0 ? iOrdinal != 1 ? iOrdinal != 2 || this.handle == 0 : this.buffer == null : this.bitmap == null;
    }

    public boolean isUseBitmap() {
        return this.type == Type.BITMAP && this.bitmap != null;
    }

    public boolean isUseBuffer() {
        return this.type == Type.BUFFER && this.buffer != null;
    }

    public boolean isUseHandle() {
        return this.type == Type.HANDLE && this.handle != 0;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public long getHandle() {
        return this.handle;
    }

    public void recycle() {
        int iOrdinal = this.type.ordinal();
        if (iOrdinal == 0) {
            Bitmap bitmap = this.bitmap;
            if (bitmap != null) {
                bitmap.recycle();
                this.bitmap = null;
                return;
            }
            return;
        }
        if (iOrdinal != 2) {
            return;
        }
        long j = this.handle;
        if (j != 0) {
            SemQuramBitmapFactory.recycleNativeBuffer(j);
            this.handle = 0L;
        }
    }
}
