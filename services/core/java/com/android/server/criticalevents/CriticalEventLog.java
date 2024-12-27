package com.android.server.criticalevents;

import android.os.Handler;
import android.util.Slog;

import com.android.internal.util.RingBuffer;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.criticalevents.nano.CriticalEventLogProto;
import com.android.server.criticalevents.nano.CriticalEventLogStorageProto;
import com.android.server.criticalevents.nano.CriticalEventProto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CriticalEventLog {
    static final String FILENAME = "critical_event_log.pb";
    public static CriticalEventLog sInstance;
    public final ThreadSafeRingBuffer mEvents;
    public final Handler mHandler;
    public final boolean mLoadAndSaveImmediately;
    public final File mLogFile;
    public final long mMinTimeBetweenSavesMs;
    public final int mWindowMs;
    public long mLastSaveAttemptMs = 0;
    public final CriticalEventLog$$ExternalSyntheticLambda0 mSaveRunnable =
            new Runnable() { // from class:
                             // com.android.server.criticalevents.CriticalEventLog$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CriticalEventLog.this.saveLogToFileNow();
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ILogLoader {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LogLoader implements ILogLoader {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LogSanitizer {
        public int mTraceProcessClassEnum;
        public String mTraceProcessName;
        public int mTraceUid;

        public final boolean shouldSanitize(int i, int i2, String str) {
            return i == 1
                    && this.mTraceProcessClassEnum == 1
                    && !(str != null && str.equals(this.mTraceProcessName) && this.mTraceUid == i2);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ThreadSafeRingBuffer {
        public final RingBuffer mBuffer;
        public final int mCapacity;

        public ThreadSafeRingBuffer(Class cls, int i) {
            this.mCapacity = i;
            this.mBuffer = new RingBuffer(cls, i);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.criticalevents.CriticalEventLog$$ExternalSyntheticLambda0] */
    public CriticalEventLog(
            String str, int i, int i2, long j, boolean z, final ILogLoader iLogLoader) {
        this.mLogFile = Paths.get(str, FILENAME).toFile();
        this.mWindowMs = i2;
        this.mMinTimeBetweenSavesMs = j;
        this.mLoadAndSaveImmediately = z;
        this.mEvents = new ThreadSafeRingBuffer(CriticalEventProto.class, i);
        Handler handler =
                new Handler(
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("CriticalEventLogIO")
                                .getLooper());
        this.mHandler = handler;
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.android.server.criticalevents.CriticalEventLog$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CriticalEventLogStorageProto criticalEventLogStorageProto;
                        CriticalEventLog criticalEventLog = CriticalEventLog.this;
                        CriticalEventLog.ILogLoader iLogLoader2 = iLogLoader;
                        File file = criticalEventLog.mLogFile;
                        ((CriticalEventLog.LogLoader) iLogLoader2).getClass();
                        if (file.exists()) {
                            try {
                                criticalEventLogStorageProto =
                                        CriticalEventLogStorageProto.parseFrom(
                                                Files.readAllBytes(file.toPath()));
                            } catch (IOException e) {
                                Slog.e("CriticalEventLog", "Error reading log from disk.", e);
                                criticalEventLogStorageProto = new CriticalEventLogStorageProto();
                            }
                        } else {
                            Slog.i("CriticalEventLog", "No log found, returning empty log proto.");
                            criticalEventLogStorageProto = new CriticalEventLogStorageProto();
                        }
                        for (CriticalEventProto criticalEventProto :
                                criticalEventLogStorageProto.events) {
                            CriticalEventLog.ThreadSafeRingBuffer threadSafeRingBuffer =
                                    criticalEventLog.mEvents;
                            synchronized (threadSafeRingBuffer) {
                                threadSafeRingBuffer.mBuffer.append(criticalEventProto);
                            }
                        }
                    }
                };
        if (z) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }

    public static CriticalEventLog getInstance() {
        if (sInstance == null) {
            sInstance =
                    new CriticalEventLog(
                            "/data/misc/critical-events",
                            20,
                            (int) Duration.ofMinutes(5L).toMillis(),
                            Duration.ofSeconds(2L).toMillis(),
                            false,
                            new LogLoader());
        }
        return sInstance;
    }

    public void appendAndSave(CriticalEventProto criticalEventProto) {
        ThreadSafeRingBuffer threadSafeRingBuffer = this.mEvents;
        synchronized (threadSafeRingBuffer) {
            threadSafeRingBuffer.mBuffer.append(criticalEventProto);
        }
        if (this.mLoadAndSaveImmediately) {
            saveLogToFileNow();
            return;
        }
        Handler handler = this.mHandler;
        if (handler.hasCallbacks(this.mSaveRunnable)
                || handler.postDelayed(this.mSaveRunnable, saveDelayMs())) {
            return;
        }
        Slog.w("CriticalEventLog", "Error scheduling save");
    }

    public CriticalEventLogProto getOutputLogProto(int i, String str, int i2) {
        Object[] array;
        CriticalEventProto[] criticalEventProtoArr;
        CriticalEventProto criticalEventProto;
        CriticalEventLogProto criticalEventLogProto = new CriticalEventLogProto();
        long wallTimeMillis = getWallTimeMillis();
        criticalEventLogProto.timestampMs = wallTimeMillis;
        int i3 = this.mWindowMs;
        criticalEventLogProto.windowMs = i3;
        ThreadSafeRingBuffer threadSafeRingBuffer = this.mEvents;
        criticalEventLogProto.capacity = threadSafeRingBuffer.mCapacity;
        long j = wallTimeMillis - i3;
        synchronized (threadSafeRingBuffer) {
            array = threadSafeRingBuffer.mBuffer.toArray();
        }
        CriticalEventProto[] criticalEventProtoArr2 = (CriticalEventProto[]) array;
        int i4 = 0;
        while (true) {
            if (i4 >= criticalEventProtoArr2.length) {
                criticalEventProtoArr = new CriticalEventProto[0];
                break;
            }
            if (criticalEventProtoArr2[i4].timestampMs >= j) {
                criticalEventProtoArr =
                        (CriticalEventProto[])
                                Arrays.copyOfRange(
                                        criticalEventProtoArr2, i4, criticalEventProtoArr2.length);
                break;
            }
            i4++;
        }
        LogSanitizer logSanitizer = new LogSanitizer();
        logSanitizer.mTraceProcessClassEnum = i;
        logSanitizer.mTraceProcessName = str;
        logSanitizer.mTraceUid = i2;
        for (int i5 = 0; i5 < criticalEventProtoArr.length; i5++) {
            CriticalEventProto criticalEventProto2 = criticalEventProtoArr[i5];
            if (criticalEventProto2.hasAnr()) {
                CriticalEventProto.AppNotResponding anr = criticalEventProto2.getAnr();
                if (logSanitizer.shouldSanitize(anr.processClass, anr.uid, anr.process)) {
                    CriticalEventProto.AppNotResponding appNotResponding =
                            new CriticalEventProto.AppNotResponding();
                    appNotResponding.processClass = criticalEventProto2.getAnr().processClass;
                    appNotResponding.uid = criticalEventProto2.getAnr().uid;
                    appNotResponding.pid = criticalEventProto2.getAnr().pid;
                    criticalEventProto = new CriticalEventProto();
                    criticalEventProto.timestampMs = criticalEventProto2.timestampMs;
                    criticalEventProto.setAnr(appNotResponding);
                    criticalEventProto2 = criticalEventProto;
                    criticalEventProtoArr[i5] = criticalEventProto2;
                } else {
                    criticalEventProtoArr[i5] = criticalEventProto2;
                }
            } else if (criticalEventProto2.hasJavaCrash()) {
                CriticalEventProto.JavaCrash javaCrash = criticalEventProto2.getJavaCrash();
                if (logSanitizer.shouldSanitize(
                        javaCrash.processClass, javaCrash.uid, javaCrash.process)) {
                    CriticalEventProto.JavaCrash javaCrash2 = new CriticalEventProto.JavaCrash();
                    javaCrash2.processClass = criticalEventProto2.getJavaCrash().processClass;
                    javaCrash2.uid = criticalEventProto2.getJavaCrash().uid;
                    javaCrash2.pid = criticalEventProto2.getJavaCrash().pid;
                    criticalEventProto = new CriticalEventProto();
                    criticalEventProto.timestampMs = criticalEventProto2.timestampMs;
                    criticalEventProto.setJavaCrash(javaCrash2);
                    criticalEventProto2 = criticalEventProto;
                    criticalEventProtoArr[i5] = criticalEventProto2;
                } else {
                    criticalEventProtoArr[i5] = criticalEventProto2;
                }
            } else {
                if (criticalEventProto2.hasNativeCrash()) {
                    CriticalEventProto.NativeCrash nativeCrash =
                            criticalEventProto2.getNativeCrash();
                    if (logSanitizer.shouldSanitize(
                            nativeCrash.processClass, nativeCrash.uid, nativeCrash.process)) {
                        CriticalEventProto.NativeCrash nativeCrash2 =
                                new CriticalEventProto.NativeCrash();
                        nativeCrash2.processClass =
                                criticalEventProto2.getNativeCrash().processClass;
                        nativeCrash2.uid = criticalEventProto2.getNativeCrash().uid;
                        nativeCrash2.pid = criticalEventProto2.getNativeCrash().pid;
                        criticalEventProto = new CriticalEventProto();
                        criticalEventProto.timestampMs = criticalEventProto2.timestampMs;
                        criticalEventProto.setNativeCrash(nativeCrash2);
                        criticalEventProto2 = criticalEventProto;
                    }
                }
                criticalEventProtoArr[i5] = criticalEventProto2;
            }
        }
        criticalEventLogProto.events = criticalEventProtoArr;
        return criticalEventLogProto;
    }

    public long getWallTimeMillis() {
        return System.currentTimeMillis();
    }

    public final void log(CriticalEventProto criticalEventProto) {
        criticalEventProto.timestampMs = getWallTimeMillis();
        appendAndSave(criticalEventProto);
    }

    public long saveDelayMs() {
        return Math.max(
                0L, (this.mLastSaveAttemptMs + this.mMinTimeBetweenSavesMs) - getWallTimeMillis());
    }

    public void saveLogToFileNow() {
        Object[] array;
        this.mLastSaveAttemptMs = getWallTimeMillis();
        File parentFile = this.mLogFile.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdir()) {
            Slog.e("CriticalEventLog", "Error creating log directory: " + parentFile.getPath());
            return;
        }
        if (!this.mLogFile.exists()) {
            try {
                this.mLogFile.createNewFile();
            } catch (IOException e) {
                Slog.e("CriticalEventLog", "Error creating log file", e);
                return;
            }
        }
        CriticalEventLogStorageProto criticalEventLogStorageProto =
                new CriticalEventLogStorageProto();
        ThreadSafeRingBuffer threadSafeRingBuffer = this.mEvents;
        synchronized (threadSafeRingBuffer) {
            array = threadSafeRingBuffer.mBuffer.toArray();
        }
        criticalEventLogStorageProto.events = (CriticalEventProto[]) array;
        byte[] byteArray = CriticalEventLogStorageProto.toByteArray(criticalEventLogStorageProto);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.mLogFile, false);
            try {
                fileOutputStream.write(byteArray);
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e2) {
            Slog.e("CriticalEventLog", "Error saving log to disk.", e2);
        }
    }
}
