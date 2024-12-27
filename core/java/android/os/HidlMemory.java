package android.os;

import android.annotation.SystemApi;

import java.io.Closeable;
import java.io.IOException;

@SystemApi
/* loaded from: classes3.dex */
public class HidlMemory implements Closeable {
    private NativeHandle mHandle;
    private final String mName;
    private long mNativeContext;
    private final long mSize;

    private native void nativeFinalize();

    public HidlMemory(String name, long size, NativeHandle handle) {
        this.mName = name;
        this.mSize = size;
        this.mHandle = handle;
    }

    public HidlMemory dup() throws IOException {
        return new HidlMemory(
                this.mName, this.mSize, this.mHandle != null ? this.mHandle.dup() : null);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.mHandle != null) {
            this.mHandle.close();
            this.mHandle = null;
        }
    }

    public NativeHandle releaseHandle() {
        NativeHandle handle = this.mHandle;
        this.mHandle = null;
        return handle;
    }

    public String getName() {
        return this.mName;
    }

    public long getSize() {
        return this.mSize;
    }

    public NativeHandle getHandle() {
        return this.mHandle;
    }

    protected void finalize() {
        try {
            try {
                close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            nativeFinalize();
        }
    }
}
