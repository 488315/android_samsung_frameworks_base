package com.android.server;

import java.io.Closeable;
import java.io.FileDescriptor;

/* loaded from: classes.dex */
public abstract class DropBoxManagerInternal {

  public interface EntrySource extends Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    default void close() {}

    default long length() {
      return 0L;
    }

    void writeTo(FileDescriptor fileDescriptor);
  }

  public abstract void addEntry(String str, EntrySource entrySource, int i);
}
