package com.android.server.pm;

import java.util.concurrent.locks.ReentrantLock;

public final class PackageManagerTracedLock implements AutoCloseable {
    public final RawLock mLock;

    public class RawLock extends ReentrantLock {
        private final String mLockName;

        public RawLock(String str) {
            this.mLockName = str;
        }
    }

    public PackageManagerTracedLock() {
        this(null);
    }

    public PackageManagerTracedLock(String str) {
        this.mLock = new RawLock(str);
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.mLock.unlock();
    }
}
