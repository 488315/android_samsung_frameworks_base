package com.android.server;

import java.io.Closeable;
import java.io.FileDescriptor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface DropBoxManagerInternal$EntrySource extends Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    default void close() {}

    default long length() {
        return 0L;
    }

    void writeTo(FileDescriptor fileDescriptor);
}
