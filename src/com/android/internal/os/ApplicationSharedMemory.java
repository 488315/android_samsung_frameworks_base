package com.android.internal.os;

import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.io.FileDescriptor;
import java.io.IOException;
import java.time.DateTimeException;
import libcore.io.IoUtils;

/* loaded from: classes5.dex */
public class ApplicationSharedMemory implements AutoCloseable {
    private static final boolean DEBUG = false;
    public static final long INVALID_NETWORK_TIME = -1;
    private static final String LOG_TAG = "ApplicationSharedMemory";
    public static ApplicationSharedMemory sInstance;
    private FileDescriptor mFileDescriptor;
    private final boolean mMutable;
    private volatile long mPtr;

    private static native int nativeCreate();

    private static native int nativeDupAsReadOnly(int i);

    @CriticalNative
    public static native long nativeGetLatestNetworkTimeUnixEpochMillisAtZeroElapsedRealtimeMillis(long j);

    @FastNative
    private static native long nativeGetSystemNonceBlock(long j);

    private static native void nativeInit(long j);

    private static native long nativeMap(int i, boolean z);

    @FastNative
    private static native int[] nativeReadSystemFeaturesCache(long j);

    @CriticalNative
    private static native void nativeSetLatestNetworkTimeUnixEpochMillisAtZeroElapsedRealtimeMillis(long j, long j2);

    private static native void nativeUnmap(long j);

    @FastNative
    private static native void nativeWriteSystemFeaturesCache(long j, int[] iArr);

    public static ApplicationSharedMemory getInstance() {
        ApplicationSharedMemory applicationSharedMemory = sInstance;
        if (applicationSharedMemory != null) {
            return applicationSharedMemory;
        }
        throw new IllegalStateException("ApplicationSharedMemory not initialized");
    }

    public static void setInstance(ApplicationSharedMemory applicationSharedMemory) {
        if (sInstance != null) {
            throw new IllegalStateException("ApplicationSharedMemory already initialized");
        }
        sInstance = applicationSharedMemory;
    }

    public static ApplicationSharedMemory create() {
        int iNativeCreate = nativeCreate();
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setInt$(iNativeCreate);
        long jNativeMap = nativeMap(iNativeCreate, true);
        nativeInit(jNativeMap);
        return new ApplicationSharedMemory(fileDescriptor, true, jNativeMap);
    }

    public static ApplicationSharedMemory fromFileDescriptor(FileDescriptor fileDescriptor, boolean z) {
        return new ApplicationSharedMemory(fileDescriptor, z, nativeMap(fileDescriptor.getInt$(), z));
    }

    ApplicationSharedMemory(FileDescriptor fileDescriptor, boolean z, long j) {
        this.mFileDescriptor = fileDescriptor;
        this.mMutable = z;
        this.mPtr = j;
    }

    public FileDescriptor getFileDescriptor() {
        checkFileOpen();
        return this.mFileDescriptor;
    }

    public FileDescriptor getReadOnlyFileDescriptor() throws IOException {
        checkFileOpen();
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setInt$(nativeDupAsReadOnly(this.mFileDescriptor.getInt$()));
        return fileDescriptor;
    }

    public void setLatestNetworkTimeUnixEpochMillisAtZeroElapsedRealtimeMillis(long j) {
        checkMutable();
        nativeSetLatestNetworkTimeUnixEpochMillisAtZeroElapsedRealtimeMillis(this.mPtr, j);
    }

    public void clearLatestNetworkTimeUnixEpochMillisAtZeroElapsedRealtimeMillis() {
        checkMutable();
        nativeSetLatestNetworkTimeUnixEpochMillisAtZeroElapsedRealtimeMillis(this.mPtr, -1L);
    }

    public long getLatestNetworkTimeUnixEpochMillisAtZeroElapsedRealtimeMillis() throws DateTimeException {
        checkMapped();
        long jNativeGetLatestNetworkTimeUnixEpochMillisAtZeroElapsedRealtimeMillis = nativeGetLatestNetworkTimeUnixEpochMillisAtZeroElapsedRealtimeMillis(this.mPtr);
        if (jNativeGetLatestNetworkTimeUnixEpochMillisAtZeroElapsedRealtimeMillis != -1) {
            return jNativeGetLatestNetworkTimeUnixEpochMillisAtZeroElapsedRealtimeMillis;
        }
        throw new DateTimeException("No network time available");
    }

    public void closeFileDescriptor() {
        FileDescriptor fileDescriptor = this.mFileDescriptor;
        if (fileDescriptor != null) {
            IoUtils.closeQuietly(fileDescriptor);
            this.mFileDescriptor = null;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.mPtr != 0) {
            nativeUnmap(this.mPtr);
            this.mPtr = 0L;
        }
        FileDescriptor fileDescriptor = this.mFileDescriptor;
        if (fileDescriptor != null) {
            IoUtils.closeQuietly(fileDescriptor);
            this.mFileDescriptor = null;
        }
    }

    private void checkFileOpen() {
        if (this.mFileDescriptor == null) {
            throw new IllegalStateException("File descriptor is closed");
        }
    }

    private void checkMapped() {
        if (this.mPtr == 0) {
            throw new IllegalStateException("Instance is closed");
        }
    }

    private void checkMutable() {
        checkMapped();
        if (!this.mMutable) {
            throw new IllegalStateException("Not mutable");
        }
    }

    public boolean isMapped() {
        return this.mPtr != 0;
    }

    public boolean isMutable() {
        return isMapped() && this.mMutable;
    }

    public long getSystemNonceBlock() {
        if (isMapped()) {
            return nativeGetSystemNonceBlock(this.mPtr);
        }
        return 0L;
    }

    public void writeSystemFeaturesCache(int[] iArr) {
        checkMutable();
        nativeWriteSystemFeaturesCache(this.mPtr, iArr);
    }

    public int[] readSystemFeaturesCache() {
        checkMapped();
        return nativeReadSystemFeaturesCache(this.mPtr);
    }
}
