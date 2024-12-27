package com.android.server.usage;

import android.app.ActivityManager;
import android.text.TextUtils;
import android.util.TimeUtils;

import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.RingBuffer;

import java.util.function.IntFunction;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BroadcastResponseStatsLogger {
    public static final int MAX_LOG_SIZE;
    public final LogBuffer mBroadcastEventsBuffer;
    public final Object mLock = new Object();
    public final LogBuffer mNotificationEventsBuffer;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BroadcastEvent implements Data {
        public long idForResponseEvent;
        public int sourceUid;
        public String targetPackage;
        public int targetUidProcessState;
        public int targetUserId;
        public long timestampMs;

        @Override // com.android.server.usage.BroadcastResponseStatsLogger.Data
        public final void reset() {
            this.targetPackage = null;
        }

        public final String toString() {
            int i = this.sourceUid;
            String str = this.targetPackage;
            int i2 = this.targetUserId;
            long j = this.idForResponseEvent;
            return TextUtils.formatSimple(
                    "broadcast:%s; srcUid=%d, tgtPkg=%s, tgtUsr=%d, id=%d, state=%s",
                    new Object[] {
                        TimeUtils.formatDuration(this.timestampMs),
                        Integer.valueOf(i),
                        str,
                        Integer.valueOf(i2),
                        Long.valueOf(j),
                        ActivityManager.procStateToString(this.targetUidProcessState)
                    });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Data {
        void reset();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LogBuffer extends RingBuffer {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationEvent implements Data {
        public String packageName;
        public long timestampMs;
        public int type;
        public int userId;

        @Override // com.android.server.usage.BroadcastResponseStatsLogger.Data
        public final void reset() {
            this.packageName = null;
        }

        public final String toString() {
            return BroadcastResponseStatsLogger.getNotificationEventLog(
                    this.type, this.userId, this.timestampMs, this.packageName);
        }
    }

    static {
        MAX_LOG_SIZE = ActivityManager.isLowRamDeviceStatic() ? 20 : 50;
    }

    public BroadcastResponseStatsLogger() {
        final int i = 0;
        Supplier supplier =
                new Supplier() { // from class:
                                 // com.android.server.usage.BroadcastResponseStatsLogger$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        switch (i) {
                            case 0:
                                return new BroadcastResponseStatsLogger.BroadcastEvent();
                            default:
                                return new BroadcastResponseStatsLogger.NotificationEvent();
                        }
                    }
                };
        final int i2 = 0;
        IntFunction intFunction =
                new IntFunction() { // from class:
                                    // com.android.server.usage.BroadcastResponseStatsLogger$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i3) {
                        switch (i2) {
                            case 0:
                                return new BroadcastResponseStatsLogger.BroadcastEvent[i3];
                            default:
                                return new BroadcastResponseStatsLogger.NotificationEvent[i3];
                        }
                    }
                };
        int i3 = MAX_LOG_SIZE;
        this.mBroadcastEventsBuffer = new LogBuffer(supplier, intFunction, i3);
        final int i4 = 1;
        final int i5 = 1;
        this.mNotificationEventsBuffer =
                new LogBuffer(
                        new Supplier() { // from class:
                                         // com.android.server.usage.BroadcastResponseStatsLogger$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i4) {
                                    case 0:
                                        return new BroadcastResponseStatsLogger.BroadcastEvent();
                                    default:
                                        return new BroadcastResponseStatsLogger.NotificationEvent();
                                }
                            }
                        },
                        new IntFunction() { // from class:
                                            // com.android.server.usage.BroadcastResponseStatsLogger$$ExternalSyntheticLambda1
                            @Override // java.util.function.IntFunction
                            public final Object apply(int i32) {
                                switch (i5) {
                                    case 0:
                                        return new BroadcastResponseStatsLogger.BroadcastEvent[i32];
                                    default:
                                        return new BroadcastResponseStatsLogger.NotificationEvent
                                                [i32];
                                }
                            }
                        },
                        i3);
    }

    public static String getNotificationEventLog(int i, int i2, long j, String str) {
        return TextUtils.formatSimple(
                "notification:%s; event=<%s>, pkg=%s, usr=%d",
                new Object[] {
                    TimeUtils.formatDuration(j),
                    i != 0
                            ? i != 1 ? i != 2 ? String.valueOf(i) : "cancelled" : "updated"
                            : "posted",
                    str,
                    Integer.valueOf(i2)
                });
    }

    public final void dumpLogs(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.println("Broadcast events (most recent first):");
            indentingPrintWriter.increaseIndent();
            Data[] dataArr = (Data[]) this.mBroadcastEventsBuffer.toArray();
            for (int length = dataArr.length - 1; length >= 0; length--) {
                Data data = dataArr[length];
                if (data != null) {
                    indentingPrintWriter.println(data.toString());
                }
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("Notification events (most recent first):");
            indentingPrintWriter.increaseIndent();
            Data[] dataArr2 = (Data[]) this.mNotificationEventsBuffer.toArray();
            for (int length2 = dataArr2.length - 1; length2 >= 0; length2--) {
                Data data2 = dataArr2[length2];
                if (data2 != null) {
                    indentingPrintWriter.println(data2.toString());
                }
            }
            indentingPrintWriter.decreaseIndent();
        }
    }
}
