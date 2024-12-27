package com.android.server.pm;

import android.os.Environment;
import android.os.SystemClock;
import android.util.AtomicFile;
import com.android.server.utils.WatchedArrayMap;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class AbstractStatsBase {
    public final String mBackgroundThreadName;
    public final String mFileName;
    public final boolean mLock;
    public final Object mFileLock = new Object();
    public final AtomicLong mLastTimeWritten = new AtomicLong(0);
    public final AtomicBoolean mBackgroundWriteRunning = new AtomicBoolean(false);

    public AbstractStatsBase(String str, String str2, boolean z) {
        this.mFileName = str;
        this.mBackgroundThreadName = str2;
        this.mLock = z;
    }

    public final AtomicFile getFile() {
        return new AtomicFile(new File(new File(Environment.getDataDirectory(), "system"), this.mFileName));
    }

    public final boolean maybeWriteAsync(final Object obj) {
        if (SystemClock.elapsedRealtime() - this.mLastTimeWritten.get() < 1800000 || !this.mBackgroundWriteRunning.compareAndSet(false, true)) {
            return false;
        }
        new Thread(this.mBackgroundThreadName) { // from class: com.android.server.pm.AbstractStatsBase.1
            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
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

    public final void read(WatchedArrayMap watchedArrayMap) {
        if (this.mLock) {
            synchronized (watchedArrayMap) {
                synchronized (this.mFileLock) {
                    readInternal(watchedArrayMap);
                }
            }
        } else {
            synchronized (this.mFileLock) {
                readInternal(watchedArrayMap);
            }
        }
        this.mLastTimeWritten.set(SystemClock.elapsedRealtime());
    }

    public abstract void readInternal(WatchedArrayMap watchedArrayMap);

    public final void writeImpl(Object obj) {
        if (!this.mLock) {
            synchronized (this.mFileLock) {
                writeInternal(obj);
            }
        } else {
            synchronized (obj) {
                synchronized (this.mFileLock) {
                    writeInternal(obj);
                }
            }
        }
    }

    public abstract void writeInternal(Object obj);
}
