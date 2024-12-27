package com.android.server;

import java.io.Closeable;
import java.io.FileDescriptor;

public interface DropBoxManagerInternal$EntrySource extends Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    default void close() {}

    default long length() {
        return 0L;
    }

    void writeTo(FileDescriptor fileDescriptor);
}
