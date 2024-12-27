package com.android.server.am.mars;

import android.app.ActivityManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.INetd;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Slog;

import com.android.internal.os.BackgroundThread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public final class EventRecorder {
    public static boolean FEATURE_ENABLE = true;
    public static File file;
    public static volatile boolean mCommitPending;
    public static final ReentrantLock mFileLock = new ReentrantLock();
    public Context mContext;
    public final Object mLock = new Object();
    public final ArrayMap mLatestUnfreezeEvent = new ArrayMap();
    public final ArrayDeque mPendingWrite = new ArrayDeque();

    public final class Converter {
        public final ArrayMap hashCache = new ArrayMap();
    }

    public final class Event {
        public final EventType eventType;
        public Integer procStateAfterEvent;
        public final Integer procStateBeforeEvent;
        public final String reason;
        public final long time;
        public final Integer uid;

        public Event(
                long j, Integer num, EventType eventType, Integer num2, Integer num3, String str) {
            this.time = j;
            this.uid = num;
            this.eventType = eventType;
            this.procStateBeforeEvent = num2;
            this.procStateAfterEvent = num3;
            this.reason = str;
        }

        public Event(String str) {
            String[] split = str.split(",");
            this.time = Long.parseLong(split[0]);
            this.uid = Integer.valueOf(Integer.parseInt(split[1]));
            EventType fromInt = EventType.fromInt(Integer.parseInt(split[2]));
            this.eventType = fromInt;
            int ordinal = fromInt.ordinal();
            if (ordinal == 0 || ordinal == 1) {
                this.procStateBeforeEvent = 0;
                this.procStateAfterEvent = 0;
                this.reason = "";
            } else if (ordinal == 2 || ordinal == 3) {
                this.procStateBeforeEvent = Integer.valueOf(Integer.parseInt(split[3]));
                this.procStateAfterEvent = Integer.valueOf(Integer.parseInt(split[4]));
                this.reason = split[5];
            }
        }

        public final String toFile() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.time);
            sb.append(",");
            sb.append(this.uid);
            sb.append(",");
            sb.append(this.eventType.getNumber());
            sb.append(",");
            sb.append(this.procStateBeforeEvent);
            sb.append(",");
            sb.append(this.procStateAfterEvent);
            sb.append(",");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.reason, "\n");
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("time: ");
            sb.append(new Date(this.time));
            sb.append(" uid: ");
            sb.append(this.uid);
            sb.append(" type: ");
            EventType eventType = this.eventType;
            sb.append(eventType.getNumber());
            if (EventType.m208$$Nest$smisUnfreezeEvent(Integer.valueOf(eventType.number))) {
                sb.append(" procBefore: ");
                sb.append(ActivityManager.procStateToString(this.procStateBeforeEvent.intValue()));
                sb.append(" procAfter: ");
                sb.append(ActivityManager.procStateToString(this.procStateAfterEvent.intValue()));
            }
            String str = this.reason;
            if (!str.isEmpty()) {
                sb.append(" reason: ");
                sb.append(str);
            }
            return sb.toString();
        }
    }

    public abstract class EventRecorderHolder {
        public static final EventRecorder INSTANCE = new EventRecorder();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public enum EventType {
        UID_RUN("UID_RUN"),
        UID_STOP("UID_STOP"),
        FREEZE("FREEZE"),
        UNFREEZE("UNFREEZE"),
        NONE("NONE");

        private final int number;

        /* renamed from: -$$Nest$smisUnfreezeEvent, reason: not valid java name */
        public static boolean m208$$Nest$smisUnfreezeEvent(Integer num) {
            return num.intValue() == UNFREEZE.number;
        }

        EventType(String str) {
            this.number = r2;
        }

        public static EventType fromInt(int i) {
            for (EventType eventType : values()) {
                if (eventType.number == i) {
                    return eventType;
                }
            }
            return NONE;
        }

        public final int getNumber() {
            return this.number;
        }
    }

    public class RemoveOutdatedJobService extends JobService {
        public static final /* synthetic */ int $r8$clinit = 0;
        public volatile boolean isJobCancelled = false;

        @Override // android.app.job.JobService
        public final boolean onStartJob(final JobParameters jobParameters) {
            BackgroundThread.getHandler()
                    .post(
                            new Runnable() { // from class:
                                // com.android.server.am.mars.EventRecorder$RemoveOutdatedJobService$$ExternalSyntheticLambda0
                                /* JADX WARN: Removed duplicated region for block: B:135:0x01d3  */
                                @Override // java.lang.Runnable
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final void run() {
                                    /*
                                        Method dump skipped, instructions count: 484
                                        To view this dump change 'Code comments level' option to 'DEBUG'
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.server.am.mars.EventRecorder$RemoveOutdatedJobService$$ExternalSyntheticLambda0.run():void");
                                }
                            });
            return true;
        }

        @Override // android.app.job.JobService
        public final boolean onStopJob(JobParameters jobParameters) {
            Slog.d("RemoveOutdatedJobService", "onStopJob is called");
            this.isJobCancelled = true;
            return true;
        }
    }

    public static long calculateOverlapPeriod(long j, long j2, long j3, long j4) {
        if (j4 < j || j2 < j3) {
            return 0L;
        }
        return (j3 >= j || j2 >= j4)
                ? (j > j3 || j4 > j2) ? Math.min(j2, j4) - Math.max(j, j3) : j4 - j3
                : j2 - j;
    }

    public static void countUnfreezeEvent(ArrayMap arrayMap, Event event, Converter converter) {
        Integer valueOf;
        ArrayMap arrayMap2 =
                (ArrayMap)
                        arrayMap.computeIfAbsent(
                                event.uid, new EventRecorder$$ExternalSyntheticLambda1());
        String str = event.reason;
        int hashCode = str.hashCode();
        int i = 0;
        while (true) {
            if (!converter.hashCache.containsKey(Integer.valueOf(hashCode))) {
                converter.hashCache.put(Integer.valueOf(hashCode), str);
                valueOf = Integer.valueOf(hashCode);
                break;
            } else {
                if (((String) converter.hashCache.get(Integer.valueOf(hashCode))).equals(str)) {
                    valueOf = Integer.valueOf(hashCode);
                    break;
                }
                i++;
                hashCode = (str + i).hashCode();
            }
        }
        arrayMap2.put(
                valueOf,
                Integer.valueOf(((Integer) arrayMap2.getOrDefault(valueOf, 0)).intValue() + 1));
    }

    public static boolean createNewEmptyFile(File file2) {
        try {
            file2.mkdirs();
            if (file2.exists() && !file2.delete()) {
                Slog.e("EventRecorder", "delete failed");
                return false;
            }
            if (file2.createNewFile()) {
                Slog.d("EventRecorder", "created new file");
                return true;
            }
            Slog.e("EventRecorder", "createNewFile failed");
            return false;
        } catch (Exception e) {
            Slog.e("EventRecorder", "createNewFile failed. ", e);
            return false;
        }
    }

    public static void processFreezeEvent(
            long j,
            long j2,
            Event event,
            ArrayMap arrayMap,
            ArrayMap arrayMap2,
            ArrayMap arrayMap3) {
        boolean z = event.eventType == EventType.FREEZE;
        long j3 = event.time;
        Integer num = event.uid;
        if (z) {
            if (arrayMap.get(num) != null) {
                return;
            }
            arrayMap.put(num, Long.valueOf(j3));
        } else {
            if (arrayMap.get(num) == null) {
                Slog.e("EventRecorder", "unfreeze event without freeze event. uid : " + num);
                return;
            }
            arrayMap2.put(
                    num,
                    Long.valueOf(
                            ((Long) arrayMap2.getOrDefault(num, 0L)).longValue()
                                    + calculateOverlapPeriod(
                                            j,
                                            j2,
                                            ((Long) arrayMap.remove(num)).longValue(),
                                            event.time)));
            if (j > j3 || j2 < j3) {
                return;
            }
            ArrayList arrayList =
                    (ArrayList)
                            arrayMap3.getOrDefault(
                                    num,
                                    new ArrayList(
                                            Collections.nCopies(
                                                    MARsFreezeStateRecord.UnfreezeReasonType
                                                            .values()
                                                            .length,
                                                    0)));
            int typeNum =
                    MARsFreezeStateRecord.UnfreezeReasonType.reasonTypeOf(event.reason)
                            .getTypeNum();
            arrayList.set(
                    typeNum, Integer.valueOf(((Integer) arrayList.get(typeNum)).intValue() + 1));
            arrayMap3.put(num, arrayList);
        }
    }

    public static void processUidEvent(
            long j, long j2, Event event, ArrayMap arrayMap, ArrayMap arrayMap2) {
        boolean z = event.eventType == EventType.UID_RUN;
        Integer num = event.uid;
        if (z) {
            if (arrayMap.get(num) != null) {
                return;
            }
            arrayMap.put(num, Long.valueOf(event.time));
        } else {
            if (arrayMap.get(num) != null) {
                arrayMap2.put(
                        num,
                        Long.valueOf(
                                ((Long) arrayMap2.getOrDefault(num, 0L)).longValue()
                                        + calculateOverlapPeriod(
                                                j,
                                                j2,
                                                ((Long) arrayMap.remove(num)).longValue(),
                                                event.time)));
                return;
            }
            Slog.e("EventRecorder", "stop event without run event. uid : " + num);
        }
    }

    public final void addFreezeEvent(Integer num, long j, String str) {
        if (FEATURE_ENABLE) {
            synchronized (this.mLock) {
                try {
                    this.mPendingWrite.add(new Event(j, num, EventType.FREEZE, 0, 0, str));
                    if (this.mPendingWrite.size() > 250) {
                        performWriteAsync(System.currentTimeMillis());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void addUnFreezeEvent(Integer num, long j, String str, int i) {
        if (FEATURE_ENABLE) {
            synchronized (this.mLock) {
                try {
                    Event event =
                            new Event(
                                    j,
                                    num,
                                    EventType.UNFREEZE,
                                    Integer.valueOf(i),
                                    Integer.valueOf(i),
                                    str);
                    long currentTimeMillis = System.currentTimeMillis();
                    this.mPendingWrite.add(event);
                    if (this.mPendingWrite.size() > 250) {
                        performWriteAsync(currentTimeMillis);
                    }
                    this.mLatestUnfreezeEvent.put(num, event);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void onUidStart(Integer num, long j) {
        if (FEATURE_ENABLE) {
            synchronized (this.mLock) {
                try {
                    this.mPendingWrite.add(new Event(j, num, EventType.UID_RUN, 0, 0, ""));
                    if (this.mPendingWrite.size() > 250) {
                        performWriteAsync(System.currentTimeMillis());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void onUidStop(Integer num, long j) {
        if (FEATURE_ENABLE) {
            synchronized (this.mLock) {
                try {
                    this.mPendingWrite.add(new Event(j, num, EventType.UID_STOP, 0, 0, ""));
                    if (this.mPendingWrite.size() > 250) {
                        performWriteAsync(System.currentTimeMillis());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void performWrite(long j) {
        if (FEATURE_ENABLE) {
            StringBuilder sb = new StringBuilder();
            synchronized (this.mLock) {
                try {
                    int size = this.mPendingWrite.size();
                    Iterator descendingIterator = this.mPendingWrite.descendingIterator();
                    while (descendingIterator.hasNext()
                            && ((Event) descendingIterator.next()).time + 500 > j) {
                        size--;
                    }
                    while (size > 0) {
                        sb.append(((Event) this.mPendingWrite.removeFirst()).toFile());
                        size--;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            mCommitPending = false;
            ReentrantLock reentrantLock = mFileLock;
            reentrantLock.lock();
            try {
                try {
                    BufferedWriter bufferedWriter =
                            new BufferedWriter(
                                    new OutputStreamWriter(
                                            new FileOutputStream(file.getAbsolutePath(), true),
                                            StandardCharsets.UTF_8));
                    try {
                        bufferedWriter.write(sb.toString());
                        bufferedWriter.close();
                    } catch (Throwable th2) {
                        try {
                            bufferedWriter.close();
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                        }
                        throw th2;
                    }
                } catch (Exception e) {
                    Slog.e("EventRecorder", "error at performWrite. " + e);
                    reentrantLock = mFileLock;
                }
                reentrantLock.unlock();
            } catch (Throwable th4) {
                mFileLock.unlock();
                throw th4;
            }
        }
    }

    public final void performWriteAsync(final long j) {
        if (mCommitPending) {
            return;
        }
        mCommitPending = true;
        BackgroundThread.getHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.android.server.am.mars.EventRecorder$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                EventRecorder.this.performWrite(j);
                            }
                        });
    }

    public final void reportUnfreezeCount(ArrayMap arrayMap, PrintWriter printWriter) {
        if (FEATURE_ENABLE) {
            performWrite(System.currentTimeMillis());
            ArrayMap arrayMap2 = new ArrayMap();
            Converter converter = new Converter();
            mFileLock.lock();
            try {
                try {
                    BufferedReader bufferedReader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            new FileInputStream(file.getAbsolutePath()),
                                            StandardCharsets.UTF_8));
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            Event event = new Event(readLine);
                            if (event.eventType == EventType.UNFREEZE
                                    && (event.reason.startsWith(INetd.IF_FLAG_BROADCAST)
                                            || event.reason.startsWith("Binder(0)")
                                            || event.reason.startsWith("Binder(1)")
                                            || event.reason.startsWith("free_buffer_full"))) {
                                if (event.procStateAfterEvent.intValue() >= 16
                                        || event.procStateBeforeEvent.intValue() >= 16) {
                                    countUnfreezeEvent(arrayMap2, event, converter);
                                }
                            }
                        } catch (Throwable th) {
                            try {
                                bufferedReader.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    }
                    bufferedReader.close();
                } catch (Exception e) {
                    Slog.e("EventRecorder", "Error at reportUnfreezeCount", e);
                }
                IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
                indentingPrintWriter.println(
                        "ACTIVITY MANAGER FREECESS UNFREEZE COUNT(dumpsys activity freecess"
                            + " report_unfreeze)");
                for (Map.Entry entry : arrayMap2.entrySet()) {
                    Integer num = (Integer) entry.getKey();
                    ArrayMap arrayMap3 = (ArrayMap) entry.getValue();
                    if (arrayMap.get(num) != null) {
                        indentingPrintWriter.print((String) arrayMap.get(num));
                        indentingPrintWriter.println();
                        indentingPrintWriter.increaseIndent();
                        for (Map.Entry entry2 : arrayMap3.entrySet()) {
                            String str =
                                    (String) converter.hashCache.get((Integer) entry2.getKey());
                            if (str != null) {
                                indentingPrintWriter.println(
                                        str + " " + ((Integer) entry2.getValue()));
                            }
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                    }
                }
            } finally {
                mFileLock.unlock();
            }
        }
    }
}
