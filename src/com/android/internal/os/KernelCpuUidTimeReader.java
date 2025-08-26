package com.android.internal.os;

import android.hardware.scontext.SContextConstants;
import android.os.StrictMode;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.os.KernelCpuProcStringReader;
import com.android.internal.os.KernelCpuUidBpfMapReader;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestUtils;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    KernelCpuUidTimeReader(KernelCpuProcStringReader kernelCpuProcStringReader, KernelCpuUidBpfMapReader kernelCpuUidBpfMapReader, boolean z, Clock clock) {
        this.mTag = getClass().getSimpleName();
        this.mLastTimes = new SparseArray<>();
        this.mMinTimeBetweenRead = 1000L;
        this.mLastReadTimeMs = 0L;
        this.mReader = kernelCpuProcStringReader;
        this.mThrottle = z;
        this.mBpfReader = kernelCpuUidBpfMapReader;
        this.mClock = clock;
        this.mBpfTimesAvailable = kernelCpuUidBpfMapReader != null;
    }

    KernelCpuUidTimeReader(KernelCpuProcStringReader kernelCpuProcStringReader, boolean z, Clock clock) {
        this(kernelCpuProcStringReader, null, z, clock);
    }

    public void readDelta(Callback<T> callback) {
        readDelta(false, callback);
    }

    public void readDelta(boolean z, Callback<T> callback) {
        if (!this.mThrottle) {
            readDeltaImpl(callback, z);
            return;
        }
        long jElapsedRealtime = this.mClock.elapsedRealtime();
        if (z || jElapsedRealtime >= this.mLastReadTimeMs + this.mMinTimeBetweenRead) {
            readDeltaImpl(callback, z);
            this.mLastReadTimeMs = jElapsedRealtime;
        }
    }

    public void readAbsolute(Callback<T> callback) {
        if (!this.mThrottle) {
            readAbsoluteImpl(callback);
            return;
        }
        long jElapsedRealtime = this.mClock.elapsedRealtime();
        if (jElapsedRealtime < this.mLastReadTimeMs + this.mMinTimeBetweenRead) {
            return;
        }
        readAbsoluteImpl(callback);
        this.mLastReadTimeMs = jElapsedRealtime;
    }

    public void removeUid(int i) {
        this.mLastTimes.delete(i);
        if (this.mBpfTimesAvailable) {
            this.mBpfReader.removeUidsInRange(i, i);
        }
    }

    public void removeUidsInRange(int i, int i2) {
        if (i2 < i) {
            Slog.e(this.mTag, "start UID " + i + " > end UID " + i2);
            return;
        }
        this.mLastTimes.put(i, null);
        this.mLastTimes.put(i2, null);
        int iIndexOfKey = this.mLastTimes.indexOfKey(i);
        this.mLastTimes.removeAtRange(iIndexOfKey, (this.mLastTimes.indexOfKey(i2) - iIndexOfKey) + 1);
        if (this.mBpfTimesAvailable) {
            this.mBpfReader.removeUidsInRange(i, i2);
        }
    }

    public void setThrottle(long j) {
        if (!this.mThrottle || j < 0) {
            return;
        }
        this.mMinTimeBetweenRead = j;
    }

    public static class KernelCpuUidUserSysTimeReader extends KernelCpuUidTimeReader<long[]> {
        private static final String REMOVE_UID_PROC_FILE = "/proc/uid_cputime/remove_uid_range";
        private final long[] mBuffer;
        private final long[] mUsrSysTime;

        public KernelCpuUidUserSysTimeReader(boolean z) {
            this(z, Clock.SYSTEM_CLOCK);
        }

        public KernelCpuUidUserSysTimeReader(boolean z, Clock clock) {
            super(KernelCpuProcStringReader.getUserSysTimeReaderInstance(), z, clock);
            this.mBuffer = new long[4];
            this.mUsrSysTime = new long[2];
        }

        public KernelCpuUidUserSysTimeReader(KernelCpuProcStringReader kernelCpuProcStringReader, boolean z, Clock clock) {
            super(kernelCpuProcStringReader, z, clock);
            this.mBuffer = new long[4];
            this.mUsrSysTime = new long[2];
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readDeltaImpl(Callback<long[]> callback, boolean z) {
            KernelCpuProcStringReader.ProcFileIterator procFileIteratorOpen = this.mReader.open(!this.mThrottle || z);
            if (procFileIteratorOpen == null) {
                if (procFileIteratorOpen != null) {
                    procFileIteratorOpen.close();
                    return;
                }
                return;
            }
            while (true) {
                try {
                    CharBuffer charBufferNextLine = procFileIteratorOpen.nextLine();
                    if (charBufferNextLine == null) {
                        break;
                    }
                    if (KernelCpuProcStringReader.asLongs(charBufferNextLine, this.mBuffer) < 3) {
                        Slog.wtf(this.mTag, "Invalid line: " + charBufferNextLine.toString());
                    } else {
                        int i = (int) this.mBuffer[0];
                        long[] jArr = (long[]) this.mLastTimes.get(i);
                        if (jArr == null) {
                            jArr = new long[2];
                            this.mLastTimes.put(i, jArr);
                        }
                        long[] jArr2 = this.mBuffer;
                        long j = jArr2[1];
                        long j2 = jArr2[2];
                        long[] jArr3 = this.mUsrSysTime;
                        long j3 = j - jArr[0];
                        jArr3[0] = j3;
                        long j4 = j2 - jArr[1];
                        jArr3[1] = j4;
                        if (j3 < 0 || j4 < 0) {
                            Slog.e(this.mTag, "Negative user/sys time delta for UID=" + i + "\nPrev times: u=" + jArr[0] + " s=" + jArr[1] + " Curr times: u=" + j + " s=" + j2);
                        } else if ((j3 > 0 || j4 > 0) && callback != null) {
                            callback.onUidCpuTime(i, jArr3);
                        }
                        jArr[0] = j;
                        jArr[1] = j2;
                    }
                } finally {
                }
            }
            if (procFileIteratorOpen != null) {
                procFileIteratorOpen.close();
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<long[]> callback) {
            KernelCpuProcStringReader.ProcFileIterator procFileIteratorOpen = this.mReader.open(!this.mThrottle);
            if (procFileIteratorOpen == null) {
                if (procFileIteratorOpen != null) {
                    procFileIteratorOpen.close();
                    return;
                }
                return;
            }
            while (true) {
                try {
                    CharBuffer charBufferNextLine = procFileIteratorOpen.nextLine();
                    if (charBufferNextLine == null) {
                        break;
                    }
                    if (KernelCpuProcStringReader.asLongs(charBufferNextLine, this.mBuffer) < 3) {
                        Slog.wtf(this.mTag, "Invalid line: " + charBufferNextLine.toString());
                    } else {
                        long[] jArr = this.mUsrSysTime;
                        long[] jArr2 = this.mBuffer;
                        jArr[0] = jArr2[1];
                        jArr[1] = jArr2[2];
                        callback.onUidCpuTime((int) jArr2[0], jArr);
                    }
                } catch (Throwable th) {
                    if (procFileIteratorOpen != null) {
                        try {
                            procFileIteratorOpen.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            if (procFileIteratorOpen != null) {
                procFileIteratorOpen.close();
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        public void removeUid(int i) {
            super.removeUid(i);
            removeUidsFromKernelModule(i, i);
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        public void removeUidsInRange(int i, int i2) {
            super.removeUidsInRange(i, i2);
            removeUidsFromKernelModule(i, i2);
        }

        private void removeUidsFromKernelModule(int i, int i2) {
            Slog.d(this.mTag, "Removing uids " + i + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + i2);
            int iAllowThreadDiskWritesMask = StrictMode.allowThreadDiskWritesMask();
            try {
                FileWriter fileWriter = new FileWriter(REMOVE_UID_PROC_FILE);
                try {
                    fileWriter.write(i + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + i2);
                    fileWriter.flush();
                    fileWriter.close();
                } catch (Throwable th) {
                    try {
                        fileWriter.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException e) {
                Slog.e(this.mTag, "failed to remove uids " + i + " - " + i2 + " from uid_cputime module", e);
            } finally {
                StrictMode.setThreadPolicyMask(iAllowThreadDiskWritesMask);
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

        public KernelCpuUidFreqTimeReader(boolean z) {
            this(z, Clock.SYSTEM_CLOCK);
        }

        public KernelCpuUidFreqTimeReader(boolean z, Clock clock) {
            this(UID_TIMES_PROC_FILE, KernelCpuProcStringReader.getFreqTimeReaderInstance(), KernelCpuUidBpfMapReader.getFreqTimeReaderInstance(), z, clock);
        }

        public KernelCpuUidFreqTimeReader(String str, KernelCpuProcStringReader kernelCpuProcStringReader, KernelCpuUidBpfMapReader kernelCpuUidBpfMapReader, boolean z) {
            this(str, kernelCpuProcStringReader, kernelCpuUidBpfMapReader, z, Clock.SYSTEM_CLOCK);
        }

        private KernelCpuUidFreqTimeReader(String str, KernelCpuProcStringReader kernelCpuProcStringReader, KernelCpuUidBpfMapReader kernelCpuUidBpfMapReader, boolean z, Clock clock) {
            super(kernelCpuProcStringReader, kernelCpuUidBpfMapReader, z, clock);
            this.mFreqCount = 0;
            this.mErrors = 0;
            this.mProcFilePath = Paths.get(str, new String[0]);
        }

        public void onSystemReady() {
            if (this.mBpfTimesAvailable && this.mCpuFreqs == null) {
                readFreqsThroughBpf();
                this.mAllUidTimesAvailable = this.mCpuFreqs != null;
            }
        }

        public boolean perClusterTimesAvailable() {
            return this.mBpfTimesAvailable;
        }

        public boolean allUidTimesAvailable() {
            return this.mAllUidTimesAvailable;
        }

        public SparseArray<long[]> getAllUidCpuFreqTimeMs() {
            return this.mLastTimes;
        }

        private long[] readFreqsThroughBpf() {
            if (!this.mBpfTimesAvailable || this.mBpfReader == null) {
                return null;
            }
            long[] dataDimensions = this.mBpfReader.getDataDimensions();
            this.mCpuFreqs = dataDimensions;
            if (dataDimensions == null) {
                return null;
            }
            int length = dataDimensions.length;
            this.mFreqCount = length;
            this.mCurTimes = new long[length];
            this.mDeltaTimes = new long[length];
            this.mBuffer = new long[length + 1];
            return dataDimensions;
        }

        private long[] readFreqs(String str) {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            String[] strArrSplit = str.split(" ");
            if (strArrSplit.length <= 1) {
                Slog.wtf(this.mTag, "Malformed freq line: " + str);
                return null;
            }
            int length = strArrSplit.length;
            int i = length - 1;
            this.mFreqCount = i;
            this.mCpuFreqs = new long[i];
            this.mCurTimes = new long[i];
            this.mDeltaTimes = new long[i];
            this.mBuffer = new long[length];
            int i2 = 0;
            while (i2 < this.mFreqCount) {
                int i3 = i2 + 1;
                this.mCpuFreqs[i2] = Long.parseLong(strArrSplit[i3], 10);
                i2 = i3;
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
            int i2 = 0;
            boolean z = false;
            while (true) {
                int i3 = this.mFreqCount;
                if (i2 >= i3) {
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
                long[] jArr2 = this.mDeltaTimes;
                long j = this.mCurTimes[i2] - jArr[i2];
                jArr2[i2] = j;
                if (j < 0) {
                    Slog.e(this.mTag, "Negative delta from freq time for uid: " + i + ", delta: " + this.mDeltaTimes[i2]);
                    return;
                }
                z |= j > 0;
                i2++;
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readDeltaImpl(Callback<long[]> callback, boolean z) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIteratorOpen = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(bpfMapIteratorOpen)) {
                        while (bpfMapIteratorOpen.getNextUid(this.mBuffer)) {
                            processUidDelta(callback);
                        }
                        if (bpfMapIteratorOpen != null) {
                            bpfMapIteratorOpen.close();
                            return;
                        }
                        return;
                    }
                    if (bpfMapIteratorOpen != null) {
                        bpfMapIteratorOpen.close();
                    }
                } catch (Throwable th) {
                    if (bpfMapIteratorOpen != null) {
                        try {
                            bpfMapIteratorOpen.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            KernelCpuProcStringReader.ProcFileIterator procFileIteratorOpen = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(procFileIteratorOpen)) {
                    if (procFileIteratorOpen != null) {
                        procFileIteratorOpen.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer charBufferNextLine = procFileIteratorOpen.nextLine();
                    if (charBufferNextLine == null) {
                        break;
                    }
                    if (KernelCpuProcStringReader.asLongs(charBufferNextLine, this.mBuffer) != this.mBuffer.length) {
                        Slog.wtf(this.mTag, "Invalid line: " + charBufferNextLine.toString());
                    } else {
                        processUidDelta(callback);
                    }
                }
                if (procFileIteratorOpen != null) {
                    procFileIteratorOpen.close();
                }
            } catch (Throwable th3) {
                if (procFileIteratorOpen != null) {
                    try {
                        procFileIteratorOpen.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<long[]> callback) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIteratorOpen = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(bpfMapIteratorOpen)) {
                        while (bpfMapIteratorOpen.getNextUid(this.mBuffer)) {
                            copyToCurTimes();
                            callback.onUidCpuTime((int) this.mBuffer[0], this.mCurTimes);
                        }
                        if (bpfMapIteratorOpen != null) {
                            bpfMapIteratorOpen.close();
                            return;
                        }
                        return;
                    }
                    if (bpfMapIteratorOpen != null) {
                        bpfMapIteratorOpen.close();
                    }
                } catch (Throwable th) {
                    if (bpfMapIteratorOpen != null) {
                        try {
                            bpfMapIteratorOpen.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            KernelCpuProcStringReader.ProcFileIterator procFileIteratorOpen = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(procFileIteratorOpen)) {
                    if (procFileIteratorOpen != null) {
                        procFileIteratorOpen.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer charBufferNextLine = procFileIteratorOpen.nextLine();
                    if (charBufferNextLine == null) {
                        break;
                    }
                    if (KernelCpuProcStringReader.asLongs(charBufferNextLine, this.mBuffer) != this.mBuffer.length) {
                        Slog.wtf(this.mTag, "Invalid line: " + charBufferNextLine.toString());
                    } else {
                        copyToCurTimes();
                        callback.onUidCpuTime((int) this.mBuffer[0], this.mCurTimes);
                    }
                }
                if (procFileIteratorOpen != null) {
                    procFileIteratorOpen.close();
                }
            } catch (Throwable th3) {
                if (procFileIteratorOpen != null) {
                    try {
                        procFileIteratorOpen.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        private void copyToCurTimes() {
            long j = this.mBpfTimesAvailable ? 1L : 10L;
            int i = 0;
            while (i < this.mFreqCount) {
                int i2 = i + 1;
                this.mCurTimes[i] = this.mBuffer[i2] * j;
                i = i2;
            }
        }

        private boolean checkPrecondition(KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIterator) {
            if (bpfMapIterator == null) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            if (this.mCpuFreqs != null) {
                return true;
            }
            this.mBpfTimesAvailable = readFreqsThroughBpf() != null;
            return this.mBpfTimesAvailable;
        }

        private boolean checkPrecondition(KernelCpuProcStringReader.ProcFileIterator procFileIterator) {
            if (procFileIterator != null && procFileIterator.hasNextLine()) {
                CharBuffer charBufferNextLine = procFileIterator.nextLine();
                if (this.mCpuFreqs != null || readFreqs(charBufferNextLine.toString()) != null) {
                    return true;
                }
            }
            return false;
        }

        /* JADX WARN: Removed duplicated region for block: B:9:0x001c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private IntArray extractClusterInfoFromProcFileFreqs() {
            IntArray intArray = new IntArray();
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = this.mFreqCount;
                if (i >= i3) {
                    return intArray;
                }
                i2++;
                int i4 = i + 1;
                if (i4 != i3) {
                    long[] jArr = this.mCpuFreqs;
                    if (jArr[i4] <= jArr[i]) {
                        intArray.add(i2);
                        i2 = 0;
                    }
                }
                i = i4;
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

        public KernelCpuUidFullTimeReader(boolean z) {
            this(null, KernelCpuUidBpfMapReader.getFullTimeReaderInstance(), z, Clock.SYSTEM_CLOCK);
        }

        private KernelCpuUidFullTimeReader(KernelCpuProcStringReader kernelCpuProcStringReader, KernelCpuUidBpfMapReader kernelCpuUidBpfMapReader, boolean z, Clock clock) {
            super(kernelCpuProcStringReader, kernelCpuUidBpfMapReader, z, clock);
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
            int i2 = 0;
            boolean z = false;
            while (true) {
                int i3 = this.mNumClusters;
                if (i2 >= i3) {
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
                long[] jArr2 = this.mDeltaTimes;
                long j = this.mCurTimes[i2] - jArr[i2];
                jArr2[i2] = j;
                if (j < 0) {
                    Slog.e(this.mTag, "Negative delta from freq time for uid: " + i + ", delta: " + this.mDeltaTimes[i2]);
                    System.arraycopy(this.mCurTimes, 0, jArr, 0, this.mNumClusters);
                    return;
                }
                z |= j > 0;
                i2++;
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readDeltaImpl(Callback<long[]> callback, boolean z) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIteratorOpen = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (!checkPrecondition(bpfMapIteratorOpen)) {
                        if (bpfMapIteratorOpen != null) {
                            bpfMapIteratorOpen.close();
                        }
                    } else {
                        while (bpfMapIteratorOpen.getNextUid(this.mBuffer)) {
                            processUidDelta(callback);
                        }
                        if (bpfMapIteratorOpen != null) {
                            bpfMapIteratorOpen.close();
                        }
                    }
                } catch (Throwable th) {
                    if (bpfMapIteratorOpen != null) {
                        try {
                            bpfMapIteratorOpen.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<long[]> callback) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIteratorOpen = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (!checkPrecondition(bpfMapIteratorOpen)) {
                        if (bpfMapIteratorOpen != null) {
                            bpfMapIteratorOpen.close();
                        }
                    } else {
                        while (bpfMapIteratorOpen.getNextUid(this.mBuffer)) {
                            copyToCurTimes();
                            callback.onUidCpuTime((int) this.mBuffer[0], this.mCurTimes);
                        }
                        if (bpfMapIteratorOpen != null) {
                            bpfMapIteratorOpen.close();
                        }
                    }
                } catch (Throwable th) {
                    if (bpfMapIteratorOpen != null) {
                        try {
                            bpfMapIteratorOpen.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
        }

        private void copyToCurTimes() {
            long j = this.mBpfTimesAvailable ? 1L : 10L;
            int i = 0;
            while (i < this.mNumClusters) {
                int i2 = i + 1;
                this.mCurTimes[i] = this.mBuffer[i2] * j;
                i = i2;
            }
        }

        private boolean checkPrecondition(KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIterator) {
            if (bpfMapIterator == null) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            if (this.mNumClusters > 0) {
                return true;
            }
            long[] dataDimensions = this.mBpfReader.getDataDimensions();
            if (dataDimensions == null || dataDimensions.length < 1) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            int length = dataDimensions.length;
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

        public KernelCpuUidActiveTimeReader(boolean z) {
            this(z, Clock.SYSTEM_CLOCK);
        }

        public KernelCpuUidActiveTimeReader(boolean z, Clock clock) {
            super(KernelCpuProcStringReader.getActiveTimeReaderInstance(), KernelCpuUidBpfMapReader.getActiveTimeReaderInstance(), z, clock);
            this.mCores = 0;
        }

        public KernelCpuUidActiveTimeReader(KernelCpuProcStringReader kernelCpuProcStringReader, KernelCpuUidBpfMapReader kernelCpuUidBpfMapReader, boolean z) {
            super(kernelCpuProcStringReader, kernelCpuUidBpfMapReader, z, Clock.SYSTEM_CLOCK);
            this.mCores = 0;
        }

        private void processUidDelta(Callback<Long> callback) {
            long[] jArr = this.mBuffer;
            int i = (int) jArr[0];
            long jSumActiveTime = sumActiveTime(jArr, this.mBpfTimesAvailable ? 1.0d : 10.0d);
            if (jSumActiveTime > 0) {
                long jLongValue = jSumActiveTime - ((Long) this.mLastTimes.get(i, 0L)).longValue();
                if (jLongValue > 0) {
                    this.mLastTimes.put(i, Long.valueOf(jSumActiveTime));
                    if (callback != null) {
                        callback.onUidCpuTime(i, Long.valueOf(jLongValue));
                        return;
                    }
                    return;
                }
                if (jLongValue < 0) {
                    Slog.e(this.mTag, "Negative delta from active time for uid: " + i + ", delta: " + jLongValue);
                }
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readDeltaImpl(Callback<Long> callback, boolean z) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIteratorOpen = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(bpfMapIteratorOpen)) {
                        while (bpfMapIteratorOpen.getNextUid(this.mBuffer)) {
                            processUidDelta(callback);
                        }
                        if (bpfMapIteratorOpen != null) {
                            bpfMapIteratorOpen.close();
                            return;
                        }
                        return;
                    }
                    if (bpfMapIteratorOpen != null) {
                        bpfMapIteratorOpen.close();
                    }
                } catch (Throwable th) {
                    if (bpfMapIteratorOpen != null) {
                        try {
                            bpfMapIteratorOpen.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            KernelCpuProcStringReader.ProcFileIterator procFileIteratorOpen = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(procFileIteratorOpen)) {
                    if (procFileIteratorOpen != null) {
                        procFileIteratorOpen.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer charBufferNextLine = procFileIteratorOpen.nextLine();
                    if (charBufferNextLine == null) {
                        break;
                    }
                    if (KernelCpuProcStringReader.asLongs(charBufferNextLine, this.mBuffer) != this.mBuffer.length) {
                        Slog.wtf(this.mTag, "Invalid line: " + charBufferNextLine.toString());
                    } else {
                        processUidDelta(callback);
                    }
                }
                if (procFileIteratorOpen != null) {
                    procFileIteratorOpen.close();
                }
            } catch (Throwable th3) {
                if (procFileIteratorOpen != null) {
                    try {
                        procFileIteratorOpen.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        private void processUidAbsolute(Callback<Long> callback) {
            long jSumActiveTime = sumActiveTime(this.mBuffer, this.mBpfTimesAvailable ? 1.0d : 10.0d);
            if (jSumActiveTime > 0) {
                callback.onUidCpuTime((int) this.mBuffer[0], Long.valueOf(jSumActiveTime));
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<Long> callback) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIteratorOpen = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(bpfMapIteratorOpen)) {
                        while (bpfMapIteratorOpen.getNextUid(this.mBuffer)) {
                            processUidAbsolute(callback);
                        }
                        if (bpfMapIteratorOpen != null) {
                            bpfMapIteratorOpen.close();
                            return;
                        }
                        return;
                    }
                    if (bpfMapIteratorOpen != null) {
                        bpfMapIteratorOpen.close();
                    }
                } catch (Throwable th) {
                    if (bpfMapIteratorOpen != null) {
                        try {
                            bpfMapIteratorOpen.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            KernelCpuProcStringReader.ProcFileIterator procFileIteratorOpen = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(procFileIteratorOpen)) {
                    if (procFileIteratorOpen != null) {
                        procFileIteratorOpen.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer charBufferNextLine = procFileIteratorOpen.nextLine();
                    if (charBufferNextLine == null) {
                        break;
                    }
                    if (KernelCpuProcStringReader.asLongs(charBufferNextLine, this.mBuffer) != this.mBuffer.length) {
                        Slog.wtf(this.mTag, "Invalid line: " + charBufferNextLine.toString());
                    } else {
                        processUidAbsolute(callback);
                    }
                }
                if (procFileIteratorOpen != null) {
                    procFileIteratorOpen.close();
                }
            } catch (Throwable th3) {
                if (procFileIteratorOpen != null) {
                    try {
                        procFileIteratorOpen.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        private static long sumActiveTime(long[] jArr, double d) {
            double d2 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            for (int i = 1; i < jArr.length; i++) {
                d2 += (jArr[i] * d) / i;
            }
            return (long) d2;
        }

        private boolean checkPrecondition(KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIterator) {
            if (bpfMapIterator == null) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            if (this.mCores > 0) {
                return true;
            }
            long[] dataDimensions = this.mBpfReader.getDataDimensions();
            if (dataDimensions == null || dataDimensions.length < 1) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            int i = (int) dataDimensions[0];
            this.mCores = i;
            this.mBuffer = new long[i + 1];
            return true;
        }

        private boolean checkPrecondition(KernelCpuProcStringReader.ProcFileIterator procFileIterator) throws NumberFormatException {
            if (procFileIterator == null || !procFileIterator.hasNextLine()) {
                return false;
            }
            CharBuffer charBufferNextLine = procFileIterator.nextLine();
            if (this.mCores > 0) {
                return true;
            }
            String strTrim = charBufferNextLine.toString().trim();
            if (strTrim.isEmpty()) {
                Slog.w(this.mTag, "Empty uid_concurrent_active_time");
                return false;
            }
            if (!strTrim.startsWith("cpus:")) {
                Slog.wtf(this.mTag, "Malformed uid_concurrent_active_time line: " + strTrim);
                return false;
            }
            int i = Integer.parseInt(strTrim.substring(5).trim(), 10);
            if (i <= 0) {
                Slog.wtf(this.mTag, "Malformed uid_concurrent_active_time line: " + strTrim);
                return false;
            }
            this.mCores = i;
            this.mBuffer = new long[i + 1];
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

        public KernelCpuUidClusterTimeReader(boolean z) {
            this(z, Clock.SYSTEM_CLOCK);
        }

        public KernelCpuUidClusterTimeReader(boolean z, Clock clock) {
            super(KernelCpuProcStringReader.getClusterTimeReaderInstance(), KernelCpuUidBpfMapReader.getClusterTimeReaderInstance(), z, clock);
        }

        public KernelCpuUidClusterTimeReader(KernelCpuProcStringReader kernelCpuProcStringReader, KernelCpuUidBpfMapReader kernelCpuUidBpfMapReader, boolean z) {
            super(kernelCpuProcStringReader, kernelCpuUidBpfMapReader, z, Clock.SYSTEM_CLOCK);
        }

        void processUidDelta(Callback<long[]> callback) {
            int i = (int) this.mBuffer[0];
            long[] jArr = (long[]) this.mLastTimes.get(i);
            if (jArr == null) {
                jArr = new long[this.mNumClusters];
                this.mLastTimes.put(i, jArr);
            }
            sumClusterTime();
            int i2 = 0;
            boolean z = false;
            while (true) {
                int i3 = this.mNumClusters;
                if (i2 >= i3) {
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
                long[] jArr2 = this.mDeltaTime;
                long j = this.mCurTime[i2] - jArr[i2];
                jArr2[i2] = j;
                if (j < 0) {
                    Slog.e(this.mTag, "Negative delta from cluster time for uid: " + i + ", delta: " + this.mDeltaTime[i2]);
                    return;
                }
                z |= j > 0;
                i2++;
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readDeltaImpl(Callback<long[]> callback, boolean z) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIteratorOpen = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(bpfMapIteratorOpen)) {
                        while (bpfMapIteratorOpen.getNextUid(this.mBuffer)) {
                            processUidDelta(callback);
                        }
                        if (bpfMapIteratorOpen != null) {
                            bpfMapIteratorOpen.close();
                            return;
                        }
                        return;
                    }
                    if (bpfMapIteratorOpen != null) {
                        bpfMapIteratorOpen.close();
                    }
                } catch (Throwable th) {
                    if (bpfMapIteratorOpen != null) {
                        try {
                            bpfMapIteratorOpen.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            KernelCpuProcStringReader.ProcFileIterator procFileIteratorOpen = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(procFileIteratorOpen)) {
                    if (procFileIteratorOpen != null) {
                        procFileIteratorOpen.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer charBufferNextLine = procFileIteratorOpen.nextLine();
                    if (charBufferNextLine == null) {
                        break;
                    }
                    if (KernelCpuProcStringReader.asLongs(charBufferNextLine, this.mBuffer) != this.mBuffer.length) {
                        Slog.wtf(this.mTag, "Invalid line: " + charBufferNextLine.toString());
                    } else {
                        processUidDelta(callback);
                    }
                }
                if (procFileIteratorOpen != null) {
                    procFileIteratorOpen.close();
                }
            } catch (Throwable th3) {
                if (procFileIteratorOpen != null) {
                    try {
                        procFileIteratorOpen.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readAbsoluteImpl(Callback<long[]> callback) {
            if (this.mBpfTimesAvailable) {
                KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIteratorOpen = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(bpfMapIteratorOpen)) {
                        while (bpfMapIteratorOpen.getNextUid(this.mBuffer)) {
                            sumClusterTime();
                            callback.onUidCpuTime((int) this.mBuffer[0], this.mCurTime);
                        }
                        if (bpfMapIteratorOpen != null) {
                            bpfMapIteratorOpen.close();
                            return;
                        }
                        return;
                    }
                    if (bpfMapIteratorOpen != null) {
                        bpfMapIteratorOpen.close();
                    }
                } catch (Throwable th) {
                    if (bpfMapIteratorOpen != null) {
                        try {
                            bpfMapIteratorOpen.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            KernelCpuProcStringReader.ProcFileIterator procFileIteratorOpen = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(procFileIteratorOpen)) {
                    if (procFileIteratorOpen != null) {
                        procFileIteratorOpen.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    CharBuffer charBufferNextLine = procFileIteratorOpen.nextLine();
                    if (charBufferNextLine == null) {
                        break;
                    }
                    if (KernelCpuProcStringReader.asLongs(charBufferNextLine, this.mBuffer) != this.mBuffer.length) {
                        Slog.wtf(this.mTag, "Invalid line: " + charBufferNextLine.toString());
                    } else {
                        sumClusterTime();
                        callback.onUidCpuTime((int) this.mBuffer[0], this.mCurTime);
                    }
                }
                if (procFileIteratorOpen != null) {
                    procFileIteratorOpen.close();
                }
            } catch (Throwable th3) {
                if (procFileIteratorOpen != null) {
                    try {
                        procFileIteratorOpen.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        private void sumClusterTime() {
            double d = this.mBpfTimesAvailable ? 1.0d : 10.0d;
            int i = 1;
            for (int i2 = 0; i2 < this.mNumClusters; i2++) {
                double d2 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                int i3 = 1;
                while (i3 <= this.mCoresOnClusters[i2]) {
                    d2 += (this.mBuffer[i] * d) / i3;
                    i3++;
                    i++;
                }
                this.mCurTime[i2] = (long) d2;
            }
        }

        private boolean checkPrecondition(KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIterator) {
            int i = 0;
            if (bpfMapIterator == null) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            if (this.mNumClusters > 0) {
                return true;
            }
            long[] dataDimensions = this.mBpfReader.getDataDimensions();
            if (dataDimensions == null || dataDimensions.length < 1) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            int length = dataDimensions.length;
            this.mNumClusters = length;
            this.mCoresOnClusters = new int[length];
            int i2 = 0;
            while (true) {
                int i3 = this.mNumClusters;
                if (i < i3) {
                    int[] iArr = this.mCoresOnClusters;
                    int i4 = (int) dataDimensions[i];
                    iArr[i] = i4;
                    i2 += i4;
                    i++;
                } else {
                    this.mNumCores = i2;
                    this.mBuffer = new long[i2 + 1];
                    this.mCurTime = new long[i3];
                    this.mDeltaTime = new long[i3];
                    return true;
                }
            }
        }

        private boolean checkPrecondition(KernelCpuProcStringReader.ProcFileIterator procFileIterator) throws NumberFormatException {
            if (procFileIterator == null || !procFileIterator.hasNextLine()) {
                return false;
            }
            CharBuffer charBufferNextLine = procFileIterator.nextLine();
            if (this.mNumClusters > 0) {
                return true;
            }
            String strTrim = charBufferNextLine.toString().trim();
            if (strTrim.isEmpty()) {
                Slog.w(this.mTag, "Empty uid_concurrent_policy_time");
                return false;
            }
            String[] strArrSplit = strTrim.split(" ");
            if (strArrSplit.length % 2 != 0) {
                Slog.wtf(this.mTag, "Malformed uid_concurrent_policy_time line: " + strTrim);
                return false;
            }
            int length = strArrSplit.length / 2;
            int[] iArr = new int[length];
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = i2 * 2;
                if (!strArrSplit[i3].startsWith(RuntimeManifestUtils.TAG_POLICY)) {
                    Slog.wtf(this.mTag, "Malformed uid_concurrent_policy_time line: " + strTrim);
                    return false;
                }
                int i4 = Integer.parseInt(strArrSplit[i3 + 1], 10);
                iArr[i2] = i4;
                i += i4;
            }
            this.mNumClusters = length;
            this.mNumCores = i;
            this.mCoresOnClusters = iArr;
            this.mBuffer = new long[i + 1];
            this.mCurTime = new long[length];
            this.mDeltaTime = new long[length];
            return true;
        }
    }
}
