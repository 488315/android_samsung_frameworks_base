package com.android.internal.os;

import android.os.StrictMode;
import android.util.Slog;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes5.dex */
public class KernelCpuProcStringReader {
    private static final int ERROR_THRESHOLD = 5;
    private static final long FRESHNESS = 500;
    private static final int MAX_BUFFER_SIZE = 1048576;
    private static final String TAG = "KernelCpuProcStringReader";
    private char[] mBuf;
    private final Clock mClock;
    private int mErrors;
    private final Path mFile;
    private long mLastReadTime;
    private final ReentrantReadWriteLock mLock;
    private final ReentrantReadWriteLock.ReadLock mReadLock;
    private int mSize;
    private final ReentrantReadWriteLock.WriteLock mWriteLock;
    private static final String PROC_UID_FREQ_TIME = "/proc/uid_time_in_state";
    private static final KernelCpuProcStringReader FREQ_TIME_READER = new KernelCpuProcStringReader(PROC_UID_FREQ_TIME);
    private static final String PROC_UID_ACTIVE_TIME = "/proc/uid_concurrent_active_time";
    private static final KernelCpuProcStringReader ACTIVE_TIME_READER = new KernelCpuProcStringReader(PROC_UID_ACTIVE_TIME);
    private static final String PROC_UID_CLUSTER_TIME = "/proc/uid_concurrent_policy_time";
    private static final KernelCpuProcStringReader CLUSTER_TIME_READER = new KernelCpuProcStringReader(PROC_UID_CLUSTER_TIME);
    private static final String PROC_UID_USER_SYS_TIME = "/proc/uid_cputime/show_uid_stat";
    private static final KernelCpuProcStringReader USER_SYS_TIME_READER = new KernelCpuProcStringReader(PROC_UID_USER_SYS_TIME);

    private static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    static KernelCpuProcStringReader getFreqTimeReaderInstance() {
        return FREQ_TIME_READER;
    }

    static KernelCpuProcStringReader getActiveTimeReaderInstance() {
        return ACTIVE_TIME_READER;
    }

    static KernelCpuProcStringReader getClusterTimeReaderInstance() {
        return CLUSTER_TIME_READER;
    }

    static KernelCpuProcStringReader getUserSysTimeReaderInstance() {
        return USER_SYS_TIME_READER;
    }

    public KernelCpuProcStringReader(String str) {
        this(str, Clock.SYSTEM_CLOCK);
    }

    public KernelCpuProcStringReader(String str, Clock clock) {
        this.mErrors = 0;
        this.mLastReadTime = 0L;
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mLock = reentrantReadWriteLock;
        this.mReadLock = reentrantReadWriteLock.readLock();
        this.mWriteLock = reentrantReadWriteLock.writeLock();
        this.mFile = Paths.get(str, new String[0]);
        this.mClock = clock;
    }

    public ProcFileIterator open() {
        return open(false);
    }

    public ProcFileIterator open(boolean z) {
        if (this.mErrors >= 5) {
            return null;
        }
        if (z) {
            this.mWriteLock.lock();
        } else {
            this.mReadLock.lock();
            if (dataValid()) {
                return new ProcFileIterator(this.mSize);
            }
            this.mReadLock.unlock();
            this.mWriteLock.lock();
            if (dataValid()) {
                this.mReadLock.lock();
                this.mWriteLock.unlock();
                return new ProcFileIterator(this.mSize);
            }
        }
        int i = 0;
        this.mSize = 0;
        int allowThreadDiskReadsMask = StrictMode.allowThreadDiskReadsMask();
        try {
            try {
                try {
                    BufferedReader newBufferedReader = Files.newBufferedReader(this.mFile);
                    try {
                        if (this.mBuf == null) {
                            this.mBuf = new char[1024];
                        }
                        while (true) {
                            char[] cArr = this.mBuf;
                            int read = newBufferedReader.read(cArr, i, cArr.length - i);
                            if (read < 0) {
                                this.mSize = i;
                                this.mLastReadTime = this.mClock.elapsedRealtime();
                                this.mReadLock.lock();
                                ProcFileIterator procFileIterator = new ProcFileIterator(i);
                                if (newBufferedReader != null) {
                                    newBufferedReader.close();
                                }
                                return procFileIterator;
                            }
                            i += read;
                            char[] cArr2 = this.mBuf;
                            if (i == cArr2.length) {
                                if (cArr2.length == 1048576) {
                                    this.mErrors++;
                                    Slog.e(TAG, "Proc file too large: " + this.mFile);
                                    if (newBufferedReader != null) {
                                        newBufferedReader.close();
                                    }
                                    return null;
                                }
                                this.mBuf = Arrays.copyOf(cArr2, Math.min(cArr2.length << 1, 1048576));
                            }
                        }
                    } catch (Throwable th) {
                        if (newBufferedReader != null) {
                            try {
                                newBufferedReader.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException | NoSuchFileException unused) {
                    this.mErrors++;
                    Slog.w(TAG, "File not found. It's normal if not implemented: " + this.mFile);
                    return null;
                }
            } catch (IOException e) {
                this.mErrors++;
                Slog.e(TAG, "Error reading " + this.mFile, e);
                return null;
            }
        } finally {
            StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
            this.mWriteLock.unlock();
        }
    }

    private boolean dataValid() {
        return this.mSize > 0 && this.mClock.elapsedRealtime() - this.mLastReadTime < FRESHNESS;
    }

    public class ProcFileIterator implements AutoCloseable {
        private int mPos;
        private final int mSize;

        public ProcFileIterator(int i) {
            this.mSize = i;
        }

        public boolean hasNextLine() {
            return this.mPos < this.mSize;
        }

        public CharBuffer nextLine() {
            int i = this.mPos;
            if (i >= this.mSize) {
                return null;
            }
            while (i < this.mSize && KernelCpuProcStringReader.this.mBuf[i] != '\n') {
                i++;
            }
            int i2 = this.mPos;
            this.mPos = i + 1;
            return CharBuffer.wrap(KernelCpuProcStringReader.this.mBuf, i2, i - i2);
        }

        public int size() {
            return this.mSize;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            KernelCpuProcStringReader.this.mReadLock.unlock();
        }
    }

    public static int asLongs(CharBuffer charBuffer, long[] jArr) {
        if (charBuffer == null) {
            return -1;
        }
        int position = charBuffer.position();
        int i = 0;
        long j = -1;
        while (charBuffer.remaining() > 0 && i < jArr.length) {
            char c = charBuffer.get();
            if (!isNumber(c) && c != ' ' && c != ':') {
                charBuffer.position(position);
                return -2;
            }
            if (j < 0) {
                if (isNumber(c)) {
                    j = c - '0';
                }
            } else if (isNumber(c)) {
                j = ((j * 10) + c) - 48;
                if (j < 0) {
                    charBuffer.position(position);
                    return -3;
                }
            } else {
                jArr[i] = j;
                j = -1;
                i++;
            }
        }
        if (j >= 0) {
            jArr[i] = j;
            i++;
        }
        charBuffer.position(position);
        return i;
    }
}
