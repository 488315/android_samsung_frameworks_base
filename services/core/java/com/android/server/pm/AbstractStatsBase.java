package com.android.server.pm;

import android.os.Environment;
import android.os.SystemClock;
import android.util.AtomicFile;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes3.dex */
public abstract class AbstractStatsBase {
    public final String mBackgroundThreadName;
    public final String mFileName;
    public final boolean mLock;
    public final Object mFileLock = new Object();
    public final AtomicLong mLastTimeWritten = new AtomicLong(0);
    public final AtomicBoolean mBackgroundWriteRunning = new AtomicBoolean(false);

    public abstract void readInternal(Object obj);

    public abstract void writeInternal(Object obj);

    public AbstractStatsBase(String str, String str2, boolean z) {
        this.mFileName = str;
        this.mBackgroundThreadName = str2;
        this.mLock = z;
    }

    public AtomicFile getFile() {
        return new AtomicFile(new File(new File(Environment.getDataDirectory(), "system"), this.mFileName));
    }

    public void writeNow(Object obj) {
        writeImpl(obj);
        this.mLastTimeWritten.set(SystemClock.elapsedRealtime());
    }

    public boolean maybeWriteAsync(final Object obj) {
        if (SystemClock.elapsedRealtime() - this.mLastTimeWritten.get() < 1800000 || !this.mBackgroundWriteRunning.compareAndSet(false, true)) {
            return false;
        }
        new Thread(this.mBackgroundThreadName) { // from class: com.android.server.pm.AbstractStatsBase.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    AbstractStatsBase.this.writeImpl(obj);
                    AbstractStatsBase.this.mLastTimeWritten.set(SystemClock.elapsedRealtime());
                } finally {
                    AbstractStatsBase.this.mBackgroundWriteRunning.set(false);
                }
            }
        }.start();
        return true;
    }

    public final void writeImpl(Object obj) {
        if (this.mLock) {
            synchronized (obj) {
                synchronized (this.mFileLock) {
                    writeInternal(obj);
                }
            }
            return;
        }
        synchronized (this.mFileLock) {
            writeInternal(obj);
        }
    }

    public void read(Object obj) {
        if (this.mLock) {
            synchronized (obj) {
                synchronized (this.mFileLock) {
                    readInternal(obj);
                }
            }
        } else {
            synchronized (this.mFileLock) {
                readInternal(obj);
            }
        }
        this.mLastTimeWritten.set(SystemClock.elapsedRealtime());
    }
}
