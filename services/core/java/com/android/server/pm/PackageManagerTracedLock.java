package com.android.server.pm;

import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageManagerTracedLock implements AutoCloseable {
    public final RawLock mLock;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
