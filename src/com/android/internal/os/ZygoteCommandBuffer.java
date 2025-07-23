package com.android.internal.os;

import android.net.LocalSocket;
import java.io.FileDescriptor;
import java.lang.ref.Reference;

/* loaded from: classes5.dex */
class ZygoteCommandBuffer implements AutoCloseable {
    private long mNativeBuffer;
    private final int mNativeSocket;
    private final LocalSocket mSocket;

    private static native void freeNativeBuffer(long j);

    private static native long getNativeBuffer(int i);

    private static native void insert(long j, String str);

    private static native boolean nativeForkRepeatedly(long j, int i, int i2, int i3, String str);

    private static native int nativeGetCount(long j);

    private static native String nativeNextArg(long j);

    private static native void nativeReadFullyAndReset(long j);

    ZygoteCommandBuffer(LocalSocket localSocket) {
        this.mSocket = localSocket;
        if (localSocket == null) {
            this.mNativeSocket = -1;
        } else {
            this.mNativeSocket = localSocket.getFileDescriptor().getInt$();
        }
        this.mNativeBuffer = getNativeBuffer(this.mNativeSocket);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    ZygoteCommandBuffer(String[] strArr) {
        this((LocalSocket) null);
        setCommand(strArr);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        freeNativeBuffer(this.mNativeBuffer);
        this.mNativeBuffer = 0L;
    }

    int getCount() {
        try {
            return nativeGetCount(this.mNativeBuffer);
        } finally {
            Reference.reachabilityFence(this.mSocket);
        }
    }

    private void setCommand(String[] strArr) {
        insert(this.mNativeBuffer, Integer.toString(strArr.length));
        for (String str : strArr) {
            insert(this.mNativeBuffer, str);
        }
    }

    String nextArg() {
        try {
            return nativeNextArg(this.mNativeBuffer);
        } finally {
            Reference.reachabilityFence(this.mSocket);
        }
    }

    void readFullyAndReset() {
        try {
            nativeReadFullyAndReset(this.mNativeBuffer);
        } finally {
            Reference.reachabilityFence(this.mSocket);
        }
    }

    boolean forkRepeatedly(FileDescriptor fileDescriptor, int i, int i2, String str) {
        try {
            return nativeForkRepeatedly(this.mNativeBuffer, fileDescriptor.getInt$(), i, i2, str);
        } finally {
            Reference.reachabilityFence(this.mSocket);
            Reference.reachabilityFence(fileDescriptor);
        }
    }
}
