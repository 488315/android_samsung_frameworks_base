package com.android.server.battery;

import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Iterator;

public abstract class BattLogBuffer {
    public static final LogBuffer mEventLogBuffer = new LogBuffer("EventLogBuffer", 20);
    public static final LogBuffer mSleepChargingLogBuffer =
            new LogBuffer("SleepChargingLogBuffer", 15);
    public static final LogBuffer mBattInfoLogBuffer = new LogBuffer("BattInfoLogBuffer", 15);
    public static final LogBuffer mBattActionChangedLogBuffer =
            new LogBuffer("BattActionChangedLogBuffer", 20);

    public final class LogBuffer {
        public static final DateTimeFormatter FORMATTER =
                DateTimeFormatter.ofPattern("MM-dd HH:mm:ss.SSS");
        public final String bufferName;
        public final ArrayDeque logs = new ArrayDeque();
        public final int maxBufferSize;

        /* renamed from: -$$Nest$mgetLogs, reason: not valid java name */
        public static String m304$$Nest$mgetLogs(LogBuffer logBuffer) {
            StringBuilder sb = new StringBuilder();
            sb.append("[" + logBuffer.bufferName + "]");
            sb.append("\n");
            Iterator it = logBuffer.logs.iterator();
            while (it.hasNext()) {
                sb.append((String) it.next());
                sb.append("\n");
            }
            return sb.toString();
        }

        public LogBuffer(String str, int i) {
            this.bufferName = str;
            this.maxBufferSize = i;
        }
    }

    public static void addLog(int i, String str) {
        LogBuffer logBuffer =
                i != 1
                        ? i != 2
                                ? i != 3
                                        ? i != 4 ? null : mBattActionChangedLogBuffer
                                        : mBattInfoLogBuffer
                                : mSleepChargingLogBuffer
                        : mEventLogBuffer;
        if (logBuffer == null) {
            Slog.e("[SS]BattLogBuffer", "[addLog]wrong bufferType");
            return;
        }
        DateTimeFormatter dateTimeFormatter = LogBuffer.FORMATTER;
        String m =
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        LocalDateTime.now().format(LogBuffer.FORMATTER), "  ", str);
        if (logBuffer.logs.size() == logBuffer.maxBufferSize) {
            logBuffer.logs.pollFirst();
        }
        logBuffer.logs.addLast(m);
    }

    public static String getAllLogs() {
        StringBuilder sb = new StringBuilder();
        sb.append(LogBuffer.m304$$Nest$mgetLogs(mEventLogBuffer) + "\n");
        sb.append(LogBuffer.m304$$Nest$mgetLogs(mSleepChargingLogBuffer) + "\n");
        sb.append(LogBuffer.m304$$Nest$mgetLogs(mBattInfoLogBuffer) + "\n");
        sb.append(LogBuffer.m304$$Nest$mgetLogs(mBattActionChangedLogBuffer));
        return sb.toString();
    }
}
