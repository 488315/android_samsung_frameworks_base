package com.android.server.location.eventlog;

import android.util.TimeUtils;

import com.android.internal.util.Preconditions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class LocalEventLog {
    public static final int IS_FILLER_OFFSET;
    static final int MAX_TIME_DELTA;
    public static final int TIME_DELTA_OFFSET;
    public final int[] mEntries;
    public long mLastLogTime;
    public int mLogEndIndex;
    public final Object[] mLogEvents;
    public int mLogSize;
    public long mModificationCount;
    public long mStartTime;

    public final class LogIterator {
        public int mCount;
        public Object mCurrentLogEvent;
        public long mCurrentTime;
        public int mIndex;
        public long mLogTime;
        public final long mModificationCount;

        public LogIterator() {
            synchronized (LocalEventLog.this) {
                this.mModificationCount = LocalEventLog.this.mModificationCount;
                this.mLogTime = LocalEventLog.this.mStartTime;
                this.mIndex = -1;
                this.mCount = -1;
                increment();
            }
        }

        public final boolean hasNext() {
            boolean z;
            synchronized (LocalEventLog.this) {
                try {
                    LocalEventLog localEventLog = LocalEventLog.this;
                    if (this.mModificationCount != localEventLog.mModificationCount) {
                        throw new ConcurrentModificationException();
                    }
                    z = this.mCount < localEventLog.mLogSize;
                } finally {
                }
            }
            return z;
        }

        public final void increment() {
            int[] iArr;
            int length;
            int i = this.mIndex;
            LocalEventLog localEventLog = LocalEventLog.this;
            long j =
                    i == -1
                            ? 0L
                            : (localEventLog.mEntries[i] & Integer.MAX_VALUE)
                                    >>> LocalEventLog.TIME_DELTA_OFFSET;
            do {
                this.mLogTime += j;
                int i2 = this.mIndex;
                iArr = localEventLog.mEntries;
                if (i2 == -1) {
                    length =
                            (((localEventLog.mLogEndIndex - localEventLog.mLogSize) % iArr.length)
                                            + iArr.length)
                                    % iArr.length;
                } else {
                    if (i2 < 0) {
                        throw new IllegalArgumentException();
                    }
                    length = (((i2 + 1) % iArr.length) + iArr.length) % iArr.length;
                }
                this.mIndex = length;
                int i3 = this.mCount + 1;
                this.mCount = i3;
                int i4 = localEventLog.mLogSize;
                if (i3 < i4) {
                    j = (iArr[length] & Integer.MAX_VALUE) >>> LocalEventLog.TIME_DELTA_OFFSET;
                }
                if (i3 >= i4) {
                    return;
                }
            } while ((iArr[length] & Integer.MIN_VALUE) != 0);
        }

        public final void next() {
            synchronized (LocalEventLog.this) {
                try {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    long j = this.mLogTime;
                    LocalEventLog localEventLog = LocalEventLog.this;
                    int[] iArr = localEventLog.mEntries;
                    int i = this.mIndex;
                    this.mCurrentTime =
                            j + ((iArr[i] & Integer.MAX_VALUE) >>> LocalEventLog.TIME_DELTA_OFFSET);
                    Object obj = localEventLog.mLogEvents[i];
                    Objects.requireNonNull(obj);
                    this.mCurrentLogEvent = obj;
                    increment();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    static {
        int i = 0;
        for (int i2 = Integer.MIN_VALUE; i2 != 0 && (i2 & 1) == 0; i2 >>>= 1) {
            i++;
        }
        IS_FILLER_OFFSET = i;
        TIME_DELTA_OFFSET = 0;
        MAX_TIME_DELTA = (1 << Integer.bitCount(Integer.MAX_VALUE)) - 1;
    }

    public LocalEventLog(int i) {
        Preconditions.checkArgument(i > 0);
        this.mEntries = new int[i];
        this.mLogEvents = (Object[]) Array.newInstance((Class<?>) Object.class, i);
        this.mLogSize = 0;
        this.mLogEndIndex = 0;
        this.mStartTime = -1L;
        this.mLastLogTime = -1L;
    }

    public static void iterate(
            LocationEventLog$$ExternalSyntheticLambda0 locationEventLog$$ExternalSyntheticLambda0,
            LocalEventLog... localEventLogArr) {
        ArrayList arrayList = new ArrayList(localEventLogArr.length);
        for (LocalEventLog localEventLog : localEventLogArr) {
            Objects.requireNonNull(localEventLog);
            LogIterator logIterator = localEventLog.new LogIterator();
            if (logIterator.hasNext()) {
                arrayList.add(logIterator);
                logIterator.next();
            }
        }
        while (true) {
            Iterator it = arrayList.iterator();
            LogIterator logIterator2 = null;
            while (it.hasNext()) {
                LogIterator logIterator3 = (LogIterator) it.next();
                if (logIterator3 != null
                        && (logIterator2 == null
                                || logIterator3.mCurrentTime < logIterator2.mCurrentTime)) {
                    logIterator2 = logIterator3;
                }
            }
            if (logIterator2 == null) {
                return;
            }
            long j = logIterator2.mCurrentTime;
            Object obj = logIterator2.mCurrentLogEvent;
            Consumer consumer = locationEventLog$$ExternalSyntheticLambda0.f$3;
            String str = locationEventLog$$ExternalSyntheticLambda0.f$0;
            if (str == null
                    || ((obj instanceof LocationEventLog.ProviderEvent)
                            && str.equals(((LocationEventLog.ProviderEvent) obj).mProvider))) {
                StringBuilder sb = locationEventLog$$ExternalSyntheticLambda0.f$1;
                sb.setLength(0);
                sb.append(
                        TimeUtils.logTimeOfDay(j + locationEventLog$$ExternalSyntheticLambda0.f$2));
                sb.append(": ");
                sb.append(obj);
                consumer.accept(sb.toString());
            }
            if (logIterator2.hasNext()) {
                logIterator2.next();
            } else {
                arrayList.remove(logIterator2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x005d A[Catch: all -> 0x003b, TryCatch #1 {all -> 0x003b, blocks: (B:5:0x0005, B:10:0x0016, B:12:0x001f, B:16:0x002f, B:18:0x0036, B:20:0x0059, B:22:0x005d, B:23:0x0066, B:27:0x003d, B:30:0x0052, B:33:0x0057, B:34:0x0058, B:29:0x003e), top: B:4:0x0005, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void addLog(long r16, java.lang.Object r18) {
        /*
            r15 = this;
            r1 = r15
            r2 = r16
            monitor-enter(r15)
            r0 = 1
            com.android.internal.util.Preconditions.checkArgument(r0)     // Catch: java.lang.Throwable -> L3b
            int r4 = r1.mLogSize     // Catch: java.lang.Throwable -> L3b
            r5 = 0
            if (r4 != 0) goto Lf
            r4 = r0
            goto L10
        Lf:
            r4 = r5
        L10:
            r6 = 1
            r8 = 0
            if (r4 != 0) goto L53
            long r10 = r1.mLastLogTime     // Catch: java.lang.Throwable -> L3b
            long r10 = r2 - r10
            int r4 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            r12 = 0
            if (r4 < 0) goto L3d
            int r4 = com.android.server.location.eventlog.LocalEventLog.MAX_TIME_DELTA     // Catch: java.lang.Throwable -> L3b
            long r13 = (long) r4     // Catch: java.lang.Throwable -> L3b
            long r13 = r10 / r13
            int[] r4 = r1.mEntries     // Catch: java.lang.Throwable -> L3b
            int r4 = r4.length     // Catch: java.lang.Throwable -> L3b
            int r4 = r4 - r0
            long r8 = (long) r4     // Catch: java.lang.Throwable -> L3b
            int r4 = (r13 > r8 ? 1 : (r13 == r8 ? 0 : -1))
            if (r4 < 0) goto L2e
            goto L3d
        L2e:
            r8 = r10
        L2f:
            int r4 = com.android.server.location.eventlog.LocalEventLog.MAX_TIME_DELTA     // Catch: java.lang.Throwable -> L3b
            long r10 = (long) r4     // Catch: java.lang.Throwable -> L3b
            int r13 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r13 < 0) goto L59
            r15.addLogEventInternal(r12, r4, r0)     // Catch: java.lang.Throwable -> L3b
            long r8 = r8 - r10
            goto L2f
        L3b:
            r0 = move-exception
            goto L6e
        L3d:
            monitor-enter(r15)     // Catch: java.lang.Throwable -> L3b
            java.lang.Object[] r0 = r1.mLogEvents     // Catch: java.lang.Throwable -> L56
            java.util.Arrays.fill(r0, r12)     // Catch: java.lang.Throwable -> L56
            r1.mLogEndIndex = r5     // Catch: java.lang.Throwable -> L56
            r1.mLogSize = r5     // Catch: java.lang.Throwable -> L56
            long r8 = r1.mModificationCount     // Catch: java.lang.Throwable -> L56
            long r8 = r8 + r6
            r1.mModificationCount = r8     // Catch: java.lang.Throwable -> L56
            r8 = -1
            r1.mStartTime = r8     // Catch: java.lang.Throwable -> L56
            r1.mLastLogTime = r8     // Catch: java.lang.Throwable -> L56
            monitor-exit(r15)     // Catch: java.lang.Throwable -> L3b
        L53:
            r8 = 0
            goto L59
        L56:
            r0 = move-exception
            monitor-exit(r15)     // Catch: java.lang.Throwable -> L3b
            throw r0     // Catch: java.lang.Throwable -> L3b
        L59:
            int r0 = r1.mLogSize     // Catch: java.lang.Throwable -> L3b
            if (r0 != 0) goto L66
            r1.mStartTime = r2     // Catch: java.lang.Throwable -> L3b
            r1.mLastLogTime = r2     // Catch: java.lang.Throwable -> L3b
            long r2 = r1.mModificationCount     // Catch: java.lang.Throwable -> L3b
            long r2 = r2 + r6
            r1.mModificationCount = r2     // Catch: java.lang.Throwable -> L3b
        L66:
            int r0 = (int) r8     // Catch: java.lang.Throwable -> L3b
            r2 = r18
            r15.addLogEventInternal(r2, r0, r5)     // Catch: java.lang.Throwable -> L3b
            monitor-exit(r15)
            return
        L6e:
            monitor-exit(r15)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.location.eventlog.LocalEventLog.addLog(long,"
                    + " java.lang.Object):void");
    }

    public final void addLogEventInternal(Object obj, int i, boolean z) {
        int length;
        boolean z2 = false;
        Preconditions.checkArgument(z || obj != null);
        Preconditions.checkState((this.mStartTime == -1 || this.mLastLogTime == -1) ? false : true);
        int i2 = this.mLogSize;
        int[] iArr = this.mEntries;
        int length2 = iArr.length;
        int i3 = TIME_DELTA_OFFSET;
        if (i2 == length2) {
            this.mStartTime +=
                    (iArr[(((this.mLogEndIndex - i2) % iArr.length) + iArr.length) % iArr.length]
                                    & Integer.MAX_VALUE)
                            >>> i3;
            this.mModificationCount++;
        } else {
            this.mLogSize = i2 + 1;
        }
        int i4 = this.mLogEndIndex;
        if (i >= 0 && i <= MAX_TIME_DELTA) {
            z2 = true;
        }
        Preconditions.checkArgument(z2);
        iArr[i4] =
                (((z ? 1 : 0) << IS_FILLER_OFFSET) & Integer.MIN_VALUE)
                        | ((i << i3) & Integer.MAX_VALUE);
        int i5 = this.mLogEndIndex;
        this.mLogEvents[i5] = obj;
        if (i5 == -1) {
            length = (((i5 - this.mLogSize) % iArr.length) + iArr.length) % iArr.length;
        } else {
            if (i5 < 0) {
                throw new IllegalArgumentException();
            }
            length = (((i5 + 1) % iArr.length) + iArr.length) % iArr.length;
        }
        this.mLogEndIndex = length;
        this.mLastLogTime += i;
    }
}
