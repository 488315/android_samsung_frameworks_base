package com.android.internal.p029os;

import android.hardware.scontext.SContextConstants;
import android.p009os.StrictMode;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.p029os.KernelCpuProcStringReader;
import com.android.internal.p029os.KernelCpuUidBpfMapReader;
import com.android.internal.util.Preconditions;
import com.samsung.android.core.p036pm.runtimemanifest.RuntimeManifestUtils;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/* loaded from: classes5.dex */
public abstract class KernelCpuUidTimeReader<T> {
    protected static final boolean DEBUG = false;
    private static final long DEFAULT_MIN_TIME_BETWEEN_READ = 1000;
    final KernelCpuUidBpfMapReader mBpfReader;
    protected boolean mBpfTimesAvailable;
    private final Clock mClock;
    private long mLastReadTimeMs;
    final SparseArray<T> mLastTimes;
    private long mMinTimeBetweenRead;
    final KernelCpuProcStringReader mReader;
    final String mTag;
    final boolean mThrottle;

    public interface Callback<T> {
        void onUidCpuTime(int i, T t);
    }

    abstract void readAbsoluteImpl(Callback<T> callback);

    abstract void readDeltaImpl(Callback<T> callback, boolean z);

    KernelCpuUidTimeReader(KernelCpuProcStringReader reader, KernelCpuUidBpfMapReader bpfReader, boolean throttle, Clock clock) {
        this.mTag = getClass().getSimpleName();
        this.mLastTimes = new SparseArray<>();
        this.mMinTimeBetweenRead = 1000L;
        this.mLastReadTimeMs = 0L;
        this.mReader = reader;
        this.mThrottle = throttle;
        this.mBpfReader = bpfReader;
        this.mClock = clock;
        this.mBpfTimesAvailable = bpfReader != null;
    }

    KernelCpuUidTimeReader(KernelCpuProcStringReader reader, boolean throttle, Clock clock) {
        this(reader, null, throttle, clock);
    }

    public void readDelta(Callback<T> cb) {
        readDelta(false, cb);
    }

    public void readDelta(boolean force, Callback<T> cb) {
        if (!this.mThrottle) {
            readDeltaImpl(cb, force);
            return;
        }
        long currTimeMs = this.mClock.elapsedRealtime();
        if (!force && currTimeMs < this.mLastReadTimeMs + this.mMinTimeBetweenRead) {
            return;
        }
        readDeltaImpl(cb, force);
        this.mLastReadTimeMs = currTimeMs;
    }

    public void readAbsolute(Callback<T> cb) {
        if (!this.mThrottle) {
            readAbsoluteImpl(cb);
            return;
        }
        long currTimeMs = this.mClock.elapsedRealtime();
        if (currTimeMs < this.mLastReadTimeMs + this.mMinTimeBetweenRead) {
            return;
        }
        readAbsoluteImpl(cb);
        this.mLastReadTimeMs = currTimeMs;
    }

    public void removeUid(int uid) {
        this.mLastTimes.delete(uid);
        if (this.mBpfTimesAvailable) {
            this.mBpfReader.removeUidsInRange(uid, uid);
        }
    }

    public void removeUidsInRange(int startUid, int endUid) {
        if (endUid < startUid) {
            Slog.m115e(this.mTag, "start UID " + startUid + " > end UID " + endUid);
            return;
        }
        this.mLastTimes.put(startUid, null);
        this.mLastTimes.put(endUid, null);
        int firstIndex = this.mLastTimes.indexOfKey(startUid);
        int lastIndex = this.mLastTimes.indexOfKey(endUid);
        this.mLastTimes.removeAtRange(firstIndex, (lastIndex - firstIndex) + 1);
        if (this.mBpfTimesAvailable) {
            this.mBpfReader.removeUidsInRange(startUid, endUid);
        }
    }

    public void setThrottle(long minTimeBetweenRead) {
        if (this.mThrottle && minTimeBetweenRead >= 0) {
            this.mMinTimeBetweenRead = minTimeBetweenRead;
        }
    }

    public static class KernelCpuUidUserSysTimeReader extends KernelCpuUidTimeReader<long[]> {
        private static final String REMOVE_UID_PROC_FILE = "/proc/uid_cputime/remove_uid_range";
        private final long[] mBuffer;
        private final long[] mUsrSysTime;

        public KernelCpuUidUserSysTimeReader(boolean throttle) {
            this(throttle, Clock.SYSTEM_CLOCK);
        }

        public KernelCpuUidUserSysTimeReader(boolean throttle, Clock clock) {
            super(KernelCpuProcStringReader.getUserSysTimeReaderInstance(), throttle, clock);
            this.mBuffer = new long[4];
            this.mUsrSysTime = new long[2];
        }

        public KernelCpuUidUserSysTimeReader(KernelCpuProcStringReader reader, boolean throttle, Clock clock) {
            super(reader, throttle, clock);
            this.mBuffer = new long[4];
            this.mUsrSysTime = new long[2];
        }

        /* JADX WARN: Code restructure failed: missing block: B:37:0x0092, code lost:
        
            r2.onUidCpuTime(r4, r13);
         */
        @Override // com.android.internal.p029os.KernelCpuUidTimeReader
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        void readDeltaImpl(Callback<long[]> callback, boolean z) {
            KernelCpuUidUserSysTimeReader kernelCpuUidUserSysTimeReader = this;
            Callback<long[]> callback2 = callback;
            char c = 1;
            KernelCpuProcStringReader.ProcFileIterator open = kernelCpuUidUserSysTimeReader.mReader.open(!kernelCpuUidUserSysTimeReader.mThrottle || z);
            if (open == null) {
                if (open != null) {
                    open.close();
                    return;
                }
                return;
            }
            while (true) {
                try {
                    CharBuffer nextLine = open.nextLine();
                    if (nextLine == null) {
                        break;
                    }
                    if (KernelCpuProcStringReader.asLongs(nextLine, kernelCpuUidUserSysTimeReader.mBuffer) < 3) {
                        Slog.wtf(kernelCpuUidUserSysTimeReader.mTag, "Invalid line: " + nextLine.toString());
                    } else {
                        int i = (int) kernelCpuUidUserSysTimeReader.mBuffer[0];
                        long[] jArr = (long[]) kernelCpuUidUserSysTimeReader.mLastTimes.get(i);
                        if (jArr == null) {
                            jArr = new long[2];
                            kernelCpuUidUserSysTimeReader.mLastTimes.put(i, jArr);
                        }
                        long[] jArr2 = kernelCpuUidUserSysTimeReader.mBuffer;
                        long j = jArr2[c];
                        long j2 = jArr2[2];
                        long[] jArr3 = kernelCpuUidUserSysTimeReader.mUsrSysTime;
                        long j3 = j - jArr[0];
                        jArr3[0] = j3;
                        long j4 = j2 - jArr[c];
                        jArr3[c] = j4;
                        if (j3 >= 0 && j4 >= 0) {
                            if (j3 <= 0 && j4 <= 0) {
                                jArr[0] = j;
                                jArr[1] = j2;
                                callback2 = callback;
                                c = 1;
                                kernelCpuUidUserSysTimeReader = this;
                            }
                            jArr[0] = j;
                            jArr[1] = j2;
                            callback2 = callback;
                            c = 1;
                            kernelCpuUidUserSysTimeReader = this;
                        }
                        Slog.m115e(kernelCpuUidUserSysTimeReader.mTag, "Negative user/sys time delta for UID=" + i + "\nPrev times: u=" + jArr[0] + " s=" + jArr[1] + " Curr times: u=" + j + " s=" + j2);
                        jArr[0] = j;
                        jArr[1] = j2;
                        callback2 = callback;
                        c = 1;
                        kernelCpuUidUserSysTimeReader = this;
                    }
                } finally {
                }
            }
            if (open != null) {
                open.close();
            }
        }

        @Override // com.android.internal.p029os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<long[]> cb) {
            KernelCpuProcStringReader.ProcFileIterator iter = this.mReader.open(!this.mThrottle);
            if (iter == null) {
                if (iter != null) {
                    iter.close();
                    return;
                }
                return;
            }
            while (true) {
                try {
                    CharBuffer buf = iter.nextLine();
                    if (buf == null) {
                        break;
                    }
                    if (KernelCpuProcStringReader.asLongs(buf, this.mBuffer) < 3) {
                        Slog.wtf(this.mTag, "Invalid line: " + buf.toString());
                    } else {
                        long[] jArr = this.mUsrSysTime;
                        long[] jArr2 = this.mBuffer;
                        jArr[0] = jArr2[1];
                        jArr[1] = jArr2[2];
                        cb.onUidCpuTime((int) jArr2[0], jArr);
                    }
                } catch (Throwable th) {
                    if (iter != null) {
                        try {
                            iter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            if (iter != null) {
                iter.close();
            }
        }

        @Override // com.android.internal.p029os.KernelCpuUidTimeReader
        public void removeUid(int uid) {
            super.removeUid(uid);
            removeUidsFromKernelModule(uid, uid);
        }

        @Override // com.android.internal.p029os.KernelCpuUidTimeReader
        public void removeUidsInRange(int startUid, int endUid) {
            super.removeUidsInRange(startUid, endUid);
            removeUidsFromKernelModule(startUid, endUid);
        }

        private void removeUidsFromKernelModule(int startUid, int endUid) {
            FileWriter writer;
            Slog.m113d(this.mTag, "Removing uids " + startUid + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + endUid);
            int oldMask = StrictMode.allowThreadDiskWritesMask();
            try {
                try {
                    writer = new FileWriter(REMOVE_UID_PROC_FILE);
                } catch (IOException e) {
                    Slog.m116e(this.mTag, "failed to remove uids " + startUid + " - " + endUid + " from uid_cputime module", e);
                }
                try {
                    writer.write(startUid + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + endUid);
                    writer.flush();
                    writer.close();
                } catch (Throwable th) {
                    try {
                        writer.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } finally {
                StrictMode.setThreadPolicyMask(oldMask);
            }
        }
    }

    public static class KernelCpuUidFreqTimeReader extends KernelCpuUidTimeReader<long[]> {
        private static final int MAX_ERROR_COUNT = 5;
        private static final String UID_TIMES_PROC_FILE = "/proc/uid_time_in_state";
        private boolean mAllUidTimesAvailable;
        private long[] mBuffer;
        private long[] mCpuFreqs;
        private long[] mCurTimes;
        private long[] mDeltaTimes;
        private int mErrors;
        private int mFreqCount;
        private boolean mPerClusterTimesAvailable;
        private final Path mProcFilePath;

        public KernelCpuUidFreqTimeReader(boolean throttle) {
            this(throttle, Clock.SYSTEM_CLOCK);
        }

        public KernelCpuUidFreqTimeReader(boolean throttle, Clock clock) {
            this(UID_TIMES_PROC_FILE, KernelCpuProcStringReader.getFreqTimeReaderInstance(), KernelCpuUidBpfMapReader.getFreqTimeReaderInstance(), throttle, clock);
        }

        public KernelCpuUidFreqTimeReader(String procFile, KernelCpuProcStringReader reader, KernelCpuUidBpfMapReader bpfReader, boolean throttle) {
            this(procFile, reader, bpfReader, throttle, Clock.SYSTEM_CLOCK);
        }

        private KernelCpuUidFreqTimeReader(String procFile, KernelCpuProcStringReader reader, KernelCpuUidBpfMapReader bpfReader, boolean throttle, Clock clock) {
            super(reader, bpfReader, throttle, clock);
            this.mFreqCount = 0;
            this.mErrors = 0;
            this.mAllUidTimesAvailable = true;
            this.mProcFilePath = Paths.get(procFile, new String[0]);
        }

        public boolean perClusterTimesAvailable() {
            return this.mPerClusterTimesAvailable;
        }

        public boolean allUidTimesAvailable() {
            return this.mAllUidTimesAvailable;
        }

        public SparseArray<long[]> getAllUidCpuFreqTimeMs() {
            return this.mLastTimes;
        }

        public long[] readFreqs(PowerProfile powerProfile) {
            Preconditions.checkNotNull(powerProfile);
            long[] jArr = this.mCpuFreqs;
            if (jArr != null) {
                return jArr;
            }
            if (!this.mAllUidTimesAvailable) {
                return null;
            }
            if (this.mBpfTimesAvailable) {
                readFreqsThroughBpf();
            }
            if (this.mCpuFreqs == null) {
                int oldMask = StrictMode.allowThreadDiskReadsMask();
                try {
                    BufferedReader reader = Files.newBufferedReader(this.mProcFilePath);
                    try {
                        if (readFreqs(reader.readLine()) == null) {
                            if (reader != null) {
                                reader.close();
                            }
                            return null;
                        }
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (Throwable th) {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (IOException e) {
                    int i = this.mErrors + 1;
                    this.mErrors = i;
                    if (i >= 5) {
                        this.mAllUidTimesAvailable = false;
                    }
                    Slog.m115e(this.mTag, "Failed to read /proc/uid_time_in_state: " + e);
                    return null;
                } finally {
                    StrictMode.setThreadPolicyMask(oldMask);
                }
            }
            IntArray numClusterFreqs = extractClusterInfoFromProcFileFreqs();
            int numClusters = powerProfile.getNumCpuClusters();
            if (numClusterFreqs.size() == numClusters) {
                this.mPerClusterTimesAvailable = true;
                int i2 = 0;
                while (true) {
                    if (i2 >= numClusters) {
                        break;
                    }
                    if (numClusterFreqs.get(i2) != powerProfile.getNumSpeedStepsInCpuCluster(i2)) {
                        this.mPerClusterTimesAvailable = false;
                        break;
                    }
                    i2++;
                }
            } else {
                this.mPerClusterTimesAvailable = false;
            }
            Slog.m117i(this.mTag, "mPerClusterTimesAvailable=" + this.mPerClusterTimesAvailable);
            return this.mCpuFreqs;
        }

        private long[] readFreqsThroughBpf() {
            if (!this.mBpfTimesAvailable || this.mBpfReader == null) {
                return null;
            }
            this.mCpuFreqs = this.mBpfReader.getDataDimensions();
            Slog.m117i(this.mTag, "mCpuFreqs=" + Arrays.toString(this.mCpuFreqs));
            long[] jArr = this.mCpuFreqs;
            if (jArr == null) {
                return null;
            }
            int length = jArr.length;
            this.mFreqCount = length;
            this.mCurTimes = new long[length];
            this.mDeltaTimes = new long[length];
            this.mBuffer = new long[length + 1];
            return jArr;
        }

        private long[] readFreqs(String line) {
            if (line == null || line.trim().isEmpty()) {
                return null;
            }
            String[] lineArray = line.split(" ");
            if (lineArray.length <= 1) {
                Slog.wtf(this.mTag, "Malformed freq line: " + line);
                return null;
            }
            int length = lineArray.length - 1;
            this.mFreqCount = length;
            this.mCpuFreqs = new long[length];
            this.mCurTimes = new long[length];
            this.mDeltaTimes = new long[length];
            this.mBuffer = new long[length + 1];
            for (int i = 0; i < this.mFreqCount; i++) {
                this.mCpuFreqs[i] = Long.parseLong(lineArray[i + 1], 10);
            }
            return this.mCpuFreqs;
        }

        private void processUidDelta(Callback<long[]> callback) {
            int i = (int) this.mBuffer[0];
            long[] jArr = (long[]) this.mLastTimes.get(i);
            if (jArr == null) {
                jArr = new long[this.mFreqCount];
                this.mLastTimes.put(i, jArr);
            }
            copyToCurTimes();
            boolean z = false;
            int i2 = 0;
            while (true) {
                int i3 = this.mFreqCount;
                if (i2 < i3) {
                    long[] jArr2 = this.mDeltaTimes;
                    long j = this.mCurTimes[i2] - jArr[i2];
                    jArr2[i2] = j;
                    if (j >= 0) {
                        z |= j > 0;
                        i2++;
                    } else {
                        Slog.m115e(this.mTag, "Negative delta from freq time for uid: " + i + ", delta: " + this.mDeltaTimes[i2]);
                        return;
                    }
                } else {
                    if (z) {
                        System.arraycopy(this.mCurTimes, 0, jArr, 0, i3);
                        if (callback != null) {
                            callback.onUidCpuTime(i, this.mDeltaTimes);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
        }

        @Override // com.android.internal.p029os.KernelCpuUidTimeReader
        void readDeltaImpl(Callback<long[]> cb, boolean forceRead) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator iter = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(iter)) {
                        while (iter.getNextUid(this.mBuffer)) {
                            processUidDelta(cb);
                        }
                        if (iter != null) {
                            iter.close();
                            return;
                        }
                        return;
                    }
                    if (iter != null) {
                        iter.close();
                    }
                } catch (Throwable th) {
                    if (iter != null) {
                        try {
                            iter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            KernelCpuProcStringReader.ProcFileIterator iter2 = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(iter2)) {
                    if (iter2 != null) {
                        iter2.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer buf = iter2.nextLine();
                    if (buf == null) {
                        break;
                    } else if (KernelCpuProcStringReader.asLongs(buf, this.mBuffer) != this.mBuffer.length) {
                        Slog.wtf(this.mTag, "Invalid line: " + buf.toString());
                    } else {
                        processUidDelta(cb);
                    }
                }
                if (iter2 != null) {
                    iter2.close();
                }
            } catch (Throwable th3) {
                if (iter2 != null) {
                    try {
                        iter2.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        @Override // com.android.internal.p029os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<long[]> cb) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator iter = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(iter)) {
                        while (iter.getNextUid(this.mBuffer)) {
                            copyToCurTimes();
                            cb.onUidCpuTime((int) this.mBuffer[0], this.mCurTimes);
                        }
                        if (iter != null) {
                            iter.close();
                            return;
                        }
                        return;
                    }
                    if (iter != null) {
                        iter.close();
                    }
                } catch (Throwable th) {
                    if (iter != null) {
                        try {
                            iter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            KernelCpuProcStringReader.ProcFileIterator iter2 = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(iter2)) {
                    if (iter2 != null) {
                        iter2.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer buf = iter2.nextLine();
                    if (buf == null) {
                        break;
                    }
                    if (KernelCpuProcStringReader.asLongs(buf, this.mBuffer) != this.mBuffer.length) {
                        Slog.wtf(this.mTag, "Invalid line: " + buf.toString());
                    } else {
                        copyToCurTimes();
                        cb.onUidCpuTime((int) this.mBuffer[0], this.mCurTimes);
                    }
                }
                if (iter2 != null) {
                    iter2.close();
                }
            } catch (Throwable th3) {
                if (iter2 != null) {
                    try {
                        iter2.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        private void copyToCurTimes() {
            long factor = this.mBpfTimesAvailable ? 1L : 10L;
            for (int i = 0; i < this.mFreqCount; i++) {
                this.mCurTimes[i] = this.mBuffer[i + 1] * factor;
            }
        }

        private boolean checkPrecondition(KernelCpuUidBpfMapReader.BpfMapIterator iter) {
            if (iter == null) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            if (this.mCpuFreqs != null) {
                return true;
            }
            this.mBpfTimesAvailable = readFreqsThroughBpf() != null;
            return this.mBpfTimesAvailable;
        }

        private boolean checkPrecondition(KernelCpuProcStringReader.ProcFileIterator iter) {
            if (iter == null || !iter.hasNextLine()) {
                return false;
            }
            CharBuffer line = iter.nextLine();
            return (this.mCpuFreqs == null && readFreqs(line.toString()) == null) ? false : true;
        }

        private IntArray extractClusterInfoFromProcFileFreqs() {
            int i;
            IntArray numClusterFreqs = new IntArray();
            int freqsFound = 0;
            while (true) {
                int i2 = this.mFreqCount;
                if (i < i2) {
                    freqsFound++;
                    if (i + 1 != i2) {
                        long[] jArr = this.mCpuFreqs;
                        i = jArr[i + 1] > jArr[i] ? i + 1 : 0;
                    }
                    numClusterFreqs.add(freqsFound);
                    freqsFound = 0;
                } else {
                    return numClusterFreqs;
                }
            }
        }

        public boolean isFastCpuTimesReader() {
            return this.mBpfTimesAvailable;
        }
    }

    public static class KernelCpuUidFullTimeReader extends KernelCpuUidTimeReader<long[]> {
        private long[] mBuffer;
        private long[] mCurTimes;
        private long[] mDeltaTimes;
        private int mNumClusters;

        public KernelCpuUidFullTimeReader(boolean throttle) {
            this(null, KernelCpuUidBpfMapReader.getFullTimeReaderInstance(), throttle, Clock.SYSTEM_CLOCK);
        }

        private KernelCpuUidFullTimeReader(KernelCpuProcStringReader reader, KernelCpuUidBpfMapReader bpfReader, boolean throttle, Clock clock) {
            super(reader, bpfReader, throttle, clock);
            this.mNumClusters = 0;
        }

        private void processUidDelta(Callback<long[]> callback) {
            int i = (int) this.mBuffer[0];
            long[] jArr = (long[]) this.mLastTimes.get(i);
            if (jArr == null) {
                jArr = new long[this.mNumClusters];
                this.mLastTimes.put(i, jArr);
            }
            copyToCurTimes();
            boolean z = false;
            int i2 = 0;
            while (true) {
                int i3 = this.mNumClusters;
                if (i2 < i3) {
                    long[] jArr2 = this.mDeltaTimes;
                    long j = this.mCurTimes[i2] - jArr[i2];
                    jArr2[i2] = j;
                    if (j >= 0) {
                        z |= j > 0;
                        i2++;
                    } else {
                        Slog.m115e(this.mTag, "Negative delta from freq time for uid: " + i + ", delta: " + this.mDeltaTimes[i2]);
                        System.arraycopy(this.mCurTimes, 0, jArr, 0, this.mNumClusters);
                        return;
                    }
                } else {
                    if (z) {
                        System.arraycopy(this.mCurTimes, 0, jArr, 0, i3);
                        if (callback != null) {
                            callback.onUidCpuTime(i, this.mDeltaTimes);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
        }

        @Override // com.android.internal.p029os.KernelCpuUidTimeReader
        void readDeltaImpl(Callback<long[]> cb, boolean forceRead) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator iter = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(iter)) {
                        while (iter.getNextUid(this.mBuffer)) {
                            processUidDelta(cb);
                        }
                        if (iter != null) {
                            iter.close();
                            return;
                        }
                        return;
                    }
                    if (iter != null) {
                        iter.close();
                    }
                } catch (Throwable th) {
                    if (iter != null) {
                        try {
                            iter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
        }

        @Override // com.android.internal.p029os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<long[]> cb) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator iter = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(iter)) {
                        while (iter.getNextUid(this.mBuffer)) {
                            copyToCurTimes();
                            cb.onUidCpuTime((int) this.mBuffer[0], this.mCurTimes);
                        }
                        if (iter != null) {
                            iter.close();
                            return;
                        }
                        return;
                    }
                    if (iter != null) {
                        iter.close();
                    }
                } catch (Throwable th) {
                    if (iter != null) {
                        try {
                            iter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
        }

        private void copyToCurTimes() {
            long factor = this.mBpfTimesAvailable ? 1L : 10L;
            for (int i = 0; i < this.mNumClusters; i++) {
                this.mCurTimes[i] = this.mBuffer[i + 1] * factor;
            }
        }

        private boolean checkPrecondition(KernelCpuUidBpfMapReader.BpfMapIterator iter) {
            if (iter == null) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            if (this.mNumClusters > 0) {
                return true;
            }
            long[] coresOnClusters = this.mBpfReader.getDataDimensions();
            if (coresOnClusters == null || coresOnClusters.length < 1) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            int length = coresOnClusters.length;
            this.mNumClusters = length;
            this.mBuffer = new long[length + 1];
            this.mCurTimes = new long[length];
            this.mDeltaTimes = new long[length];
            return this.mBpfTimesAvailable;
        }
    }

    public static class KernelCpuUidActiveTimeReader extends KernelCpuUidTimeReader<Long> {
        private long[] mBuffer;
        private int mCores;

        public KernelCpuUidActiveTimeReader(boolean throttle) {
            this(throttle, Clock.SYSTEM_CLOCK);
        }

        public KernelCpuUidActiveTimeReader(boolean throttle, Clock clock) {
            super(KernelCpuProcStringReader.getActiveTimeReaderInstance(), KernelCpuUidBpfMapReader.getActiveTimeReaderInstance(), throttle, clock);
            this.mCores = 0;
        }

        public KernelCpuUidActiveTimeReader(KernelCpuProcStringReader reader, KernelCpuUidBpfMapReader bpfReader, boolean throttle) {
            super(reader, bpfReader, throttle, Clock.SYSTEM_CLOCK);
            this.mCores = 0;
        }

        private void processUidDelta(Callback<Long> callback) {
            long[] jArr = this.mBuffer;
            int i = (int) jArr[0];
            long sumActiveTime = sumActiveTime(jArr, this.mBpfTimesAvailable ? 1.0d : 10.0d);
            if (sumActiveTime > 0) {
                long longValue = sumActiveTime - ((Long) this.mLastTimes.get(i, 0L)).longValue();
                if (longValue <= 0) {
                    if (longValue < 0) {
                        Slog.m115e(this.mTag, "Negative delta from active time for uid: " + i + ", delta: " + longValue);
                    }
                } else {
                    this.mLastTimes.put(i, Long.valueOf(sumActiveTime));
                    if (callback != null) {
                        callback.onUidCpuTime(i, Long.valueOf(longValue));
                    }
                }
            }
        }

        @Override // com.android.internal.p029os.KernelCpuUidTimeReader
        void readDeltaImpl(Callback<Long> cb, boolean forceRead) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator iter = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(iter)) {
                        while (iter.getNextUid(this.mBuffer)) {
                            processUidDelta(cb);
                        }
                        if (iter != null) {
                            iter.close();
                            return;
                        }
                        return;
                    }
                    if (iter != null) {
                        iter.close();
                    }
                } catch (Throwable th) {
                    if (iter != null) {
                        try {
                            iter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            KernelCpuProcStringReader.ProcFileIterator iter2 = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(iter2)) {
                    if (iter2 != null) {
                        iter2.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer buf = iter2.nextLine();
                    if (buf == null) {
                        break;
                    } else if (KernelCpuProcStringReader.asLongs(buf, this.mBuffer) != this.mBuffer.length) {
                        Slog.wtf(this.mTag, "Invalid line: " + buf.toString());
                    } else {
                        processUidDelta(cb);
                    }
                }
                if (iter2 != null) {
                    iter2.close();
                }
            } catch (Throwable th3) {
                if (iter2 != null) {
                    try {
                        iter2.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        private void processUidAbsolute(Callback<Long> cb) {
            long cpuActiveTime = sumActiveTime(this.mBuffer, this.mBpfTimesAvailable ? 1.0d : 10.0d);
            if (cpuActiveTime > 0) {
                cb.onUidCpuTime((int) this.mBuffer[0], Long.valueOf(cpuActiveTime));
            }
        }

        @Override // com.android.internal.p029os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<Long> cb) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator iter = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(iter)) {
                        while (iter.getNextUid(this.mBuffer)) {
                            processUidAbsolute(cb);
                        }
                        if (iter != null) {
                            iter.close();
                            return;
                        }
                        return;
                    }
                    if (iter != null) {
                        iter.close();
                    }
                } catch (Throwable th) {
                    if (iter != null) {
                        try {
                            iter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            KernelCpuProcStringReader.ProcFileIterator iter2 = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(iter2)) {
                    if (iter2 != null) {
                        iter2.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer buf = iter2.nextLine();
                    if (buf == null) {
                        break;
                    } else if (KernelCpuProcStringReader.asLongs(buf, this.mBuffer) != this.mBuffer.length) {
                        Slog.wtf(this.mTag, "Invalid line: " + buf.toString());
                    } else {
                        processUidAbsolute(cb);
                    }
                }
                if (iter2 != null) {
                    iter2.close();
                }
            } catch (Throwable th3) {
                if (iter2 != null) {
                    try {
                        iter2.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        private static long sumActiveTime(long[] times, double factor) {
            double sum = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            for (int i = 1; i < times.length; i++) {
                sum += (times[i] * factor) / i;
            }
            return (long) sum;
        }

        private boolean checkPrecondition(KernelCpuUidBpfMapReader.BpfMapIterator iter) {
            if (iter == null) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            if (this.mCores > 0) {
                return true;
            }
            long[] cores = this.mBpfReader.getDataDimensions();
            if (cores == null || cores.length < 1) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            int i = (int) cores[0];
            this.mCores = i;
            this.mBuffer = new long[i + 1];
            return true;
        }

        private boolean checkPrecondition(KernelCpuProcStringReader.ProcFileIterator iter) {
            if (iter == null || !iter.hasNextLine()) {
                return false;
            }
            CharBuffer line = iter.nextLine();
            if (this.mCores > 0) {
                return true;
            }
            String str = line.toString().trim();
            if (str.isEmpty()) {
                Slog.m121w(this.mTag, "Empty uid_concurrent_active_time");
                return false;
            }
            if (!str.startsWith("cpus:")) {
                Slog.wtf(this.mTag, "Malformed uid_concurrent_active_time line: " + str);
                return false;
            }
            int cores = Integer.parseInt(str.substring(5).trim(), 10);
            if (cores <= 0) {
                Slog.wtf(this.mTag, "Malformed uid_concurrent_active_time line: " + str);
                return false;
            }
            this.mCores = cores;
            this.mBuffer = new long[cores + 1];
            return true;
        }
    }

    public static class KernelCpuUidClusterTimeReader extends KernelCpuUidTimeReader<long[]> {
        private long[] mBuffer;
        private int[] mCoresOnClusters;
        private long[] mCurTime;
        private long[] mDeltaTime;
        private int mNumClusters;
        private int mNumCores;

        public KernelCpuUidClusterTimeReader(boolean throttle) {
            this(throttle, Clock.SYSTEM_CLOCK);
        }

        public KernelCpuUidClusterTimeReader(boolean throttle, Clock clock) {
            super(KernelCpuProcStringReader.getClusterTimeReaderInstance(), KernelCpuUidBpfMapReader.getClusterTimeReaderInstance(), throttle, clock);
        }

        public KernelCpuUidClusterTimeReader(KernelCpuProcStringReader reader, KernelCpuUidBpfMapReader bpfReader, boolean throttle) {
            super(reader, bpfReader, throttle, Clock.SYSTEM_CLOCK);
        }

        void processUidDelta(Callback<long[]> callback) {
            int i = (int) this.mBuffer[0];
            long[] jArr = (long[]) this.mLastTimes.get(i);
            if (jArr == null) {
                jArr = new long[this.mNumClusters];
                this.mLastTimes.put(i, jArr);
            }
            sumClusterTime();
            boolean z = false;
            int i2 = 0;
            while (true) {
                int i3 = this.mNumClusters;
                if (i2 < i3) {
                    long[] jArr2 = this.mDeltaTime;
                    long j = this.mCurTime[i2] - jArr[i2];
                    jArr2[i2] = j;
                    if (j >= 0) {
                        z |= j > 0;
                        i2++;
                    } else {
                        Slog.m115e(this.mTag, "Negative delta from cluster time for uid: " + i + ", delta: " + this.mDeltaTime[i2]);
                        return;
                    }
                } else {
                    if (z) {
                        System.arraycopy(this.mCurTime, 0, jArr, 0, i3);
                        if (callback != null) {
                            callback.onUidCpuTime(i, this.mDeltaTime);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
        }

        @Override // com.android.internal.p029os.KernelCpuUidTimeReader
        void readDeltaImpl(Callback<long[]> cb, boolean forceRead) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator iter = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(iter)) {
                        while (iter.getNextUid(this.mBuffer)) {
                            processUidDelta(cb);
                        }
                        if (iter != null) {
                            iter.close();
                            return;
                        }
                        return;
                    }
                    if (iter != null) {
                        iter.close();
                    }
                } catch (Throwable th) {
                    if (iter != null) {
                        try {
                            iter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            KernelCpuProcStringReader.ProcFileIterator iter2 = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(iter2)) {
                    if (iter2 != null) {
                        iter2.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer buf = iter2.nextLine();
                    if (buf == null) {
                        break;
                    } else if (KernelCpuProcStringReader.asLongs(buf, this.mBuffer) != this.mBuffer.length) {
                        Slog.wtf(this.mTag, "Invalid line: " + buf.toString());
                    } else {
                        processUidDelta(cb);
                    }
                }
                if (iter2 != null) {
                    iter2.close();
                }
            } catch (Throwable th3) {
                if (iter2 != null) {
                    try {
                        iter2.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        @Override // com.android.internal.p029os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<long[]> cb) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator iter = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(iter)) {
                        while (iter.getNextUid(this.mBuffer)) {
                            sumClusterTime();
                            cb.onUidCpuTime((int) this.mBuffer[0], this.mCurTime);
                        }
                        if (iter != null) {
                            iter.close();
                            return;
                        }
                        return;
                    }
                    if (iter != null) {
                        iter.close();
                    }
                } catch (Throwable th) {
                    if (iter != null) {
                        try {
                            iter.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            KernelCpuProcStringReader.ProcFileIterator iter2 = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(iter2)) {
                    if (iter2 != null) {
                        iter2.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer buf = iter2.nextLine();
                    if (buf == null) {
                        break;
                    }
                    if (KernelCpuProcStringReader.asLongs(buf, this.mBuffer) != this.mBuffer.length) {
                        Slog.wtf(this.mTag, "Invalid line: " + buf.toString());
                    } else {
                        sumClusterTime();
                        cb.onUidCpuTime((int) this.mBuffer[0], this.mCurTime);
                    }
                }
                if (iter2 != null) {
                    iter2.close();
                }
            } catch (Throwable th3) {
                if (iter2 != null) {
                    try {
                        iter2.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        private void sumClusterTime() {
            double factor = this.mBpfTimesAvailable ? 1.0d : 10.0d;
            int core = 1;
            for (int i = 0; i < this.mNumClusters; i++) {
                double sum = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                int j = 1;
                while (j <= this.mCoresOnClusters[i]) {
                    sum += (this.mBuffer[core] * factor) / j;
                    j++;
                    core++;
                }
                this.mCurTime[i] = (long) sum;
            }
        }

        private boolean checkPrecondition(KernelCpuUidBpfMapReader.BpfMapIterator iter) {
            if (iter == null) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            if (this.mNumClusters > 0) {
                return true;
            }
            long[] coresOnClusters = this.mBpfReader.getDataDimensions();
            if (coresOnClusters == null || coresOnClusters.length < 1) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            int length = coresOnClusters.length;
            this.mNumClusters = length;
            this.mCoresOnClusters = new int[length];
            int cores = 0;
            int i = 0;
            while (true) {
                int i2 = this.mNumClusters;
                if (i < i2) {
                    int[] iArr = this.mCoresOnClusters;
                    int i3 = (int) coresOnClusters[i];
                    iArr[i] = i3;
                    cores += i3;
                    i++;
                } else {
                    this.mNumCores = cores;
                    this.mBuffer = new long[cores + 1];
                    this.mCurTime = new long[i2];
                    this.mDeltaTime = new long[i2];
                    return true;
                }
            }
        }

        private boolean checkPrecondition(KernelCpuProcStringReader.ProcFileIterator iter) {
            if (iter == null || !iter.hasNextLine()) {
                return false;
            }
            CharBuffer line = iter.nextLine();
            if (this.mNumClusters > 0) {
                return true;
            }
            String lineStr = line.toString().trim();
            if (lineStr.isEmpty()) {
                Slog.m121w(this.mTag, "Empty uid_concurrent_policy_time");
                return false;
            }
            String[] lineArray = lineStr.split(" ");
            if (lineArray.length % 2 != 0) {
                Slog.wtf(this.mTag, "Malformed uid_concurrent_policy_time line: " + lineStr);
                return false;
            }
            int[] clusters = new int[lineArray.length / 2];
            int cores = 0;
            for (int i = 0; i < clusters.length; i++) {
                if (!lineArray[i * 2].startsWith(RuntimeManifestUtils.TAG_POLICY)) {
                    Slog.wtf(this.mTag, "Malformed uid_concurrent_policy_time line: " + lineStr);
                    return false;
                }
                clusters[i] = Integer.parseInt(lineArray[(i * 2) + 1], 10);
                cores += clusters[i];
            }
            int length = clusters.length;
            this.mNumClusters = length;
            this.mNumCores = cores;
            this.mCoresOnClusters = clusters;
            this.mBuffer = new long[cores + 1];
            this.mCurTime = new long[length];
            this.mDeltaTime = new long[length];
            return true;
        }
    }
}
