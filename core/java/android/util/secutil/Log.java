package android.util.secutil;

import android.p009os.DeadSystemException;
import com.android.internal.p029os.RuntimeInit;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.LineBreakBufferedWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.UnknownHostException;

/* loaded from: classes4.dex */
public final class Log {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int LOG_ID_CRASH = 4;
    public static final int LOG_ID_EVENTS = 2;
    public static final int LOG_ID_MAIN = 0;
    public static final int LOG_ID_RADIO = 1;
    public static final int LOG_ID_SYSTEM = 3;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static TerribleFailureHandler sWtfHandler = new TerribleFailureHandler() { // from class: android.util.secutil.Log.1
        @Override // android.util.secutil.Log.TerribleFailureHandler
        public void onTerribleFailure(String tag, TerribleFailure what, boolean system) {
            RuntimeInit.wtf(tag, what, system);
        }
    };

    public interface TerribleFailureHandler {
        void onTerribleFailure(String str, TerribleFailure terribleFailure, boolean z);
    }

    public static native boolean isLoggable(String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int logger_entry_max_payload_native();

    public static native int println_native(int i, int i2, String str, String str2);

    private static class TerribleFailure extends Exception {
        TerribleFailure(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    private Log() {
    }

    /* renamed from: v */
    public static int m131v(String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return println_native(0, 2, tag, msg);
        }
        return 0;
    }

    public static int secV(String tag, String msg) {
        if (LogSwitcher.isShowingSecVLog) {
            return m131v(tag, msg);
        }
        return 0;
    }

    /* renamed from: v */
    public static int m132v(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingGlobalLog) {
            return printlns(0, 2, tag, msg, tr);
        }
        return 0;
    }

    public static int secV(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecVLog) {
            return m132v(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: d */
    public static int m125d(String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return println_native(0, 3, tag, msg);
        }
        return 0;
    }

    public static int secD(String tag, String msg) {
        if (LogSwitcher.isShowingSecDLog) {
            return m125d(tag, msg);
        }
        return 0;
    }

    /* renamed from: d */
    public static int m126d(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingGlobalLog) {
            return printlns(0, 3, tag, msg, tr);
        }
        return 0;
    }

    public static int secD(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecDLog) {
            return m126d(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: i */
    public static int m129i(String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return println_native(0, 4, tag, msg);
        }
        return 0;
    }

    public static int secI(String tag, String msg) {
        if (LogSwitcher.isShowingSecILog) {
            return m129i(tag, msg);
        }
        return 0;
    }

    /* renamed from: i */
    public static int m130i(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingGlobalLog) {
            return printlns(0, 4, tag, msg, tr);
        }
        return 0;
    }

    public static int secI(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecILog) {
            return m130i(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: w */
    public static int m133w(String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return println_native(0, 5, tag, msg);
        }
        return 0;
    }

    public static int secW(String tag, String msg) {
        if (LogSwitcher.isShowingSecWLog) {
            return m133w(tag, msg);
        }
        return 0;
    }

    /* renamed from: w */
    public static int m134w(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingGlobalLog) {
            return printlns(0, 5, tag, msg, tr);
        }
        return 0;
    }

    public static int secW(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecWLog) {
            return m134w(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: w */
    public static int m135w(String tag, Throwable tr) {
        if (LogSwitcher.isShowingGlobalLog) {
            return printlns(0, 5, tag, "", tr);
        }
        return 0;
    }

    public static int secW(String tag, Throwable tr) {
        if (LogSwitcher.isShowingSecWLog) {
            return m135w(tag, tr);
        }
        return 0;
    }

    /* renamed from: e */
    public static int m127e(String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return println_native(0, 6, tag, msg);
        }
        return 0;
    }

    public static int secE(String tag, String msg) {
        if (LogSwitcher.isShowingSecELog) {
            return m127e(tag, msg);
        }
        return 0;
    }

    /* renamed from: e */
    public static int m128e(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingGlobalLog) {
            return printlns(0, 6, tag, msg, tr);
        }
        return 0;
    }

    public static int secE(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecELog) {
            return m128e(tag, msg, tr);
        }
        return 0;
    }

    public static int wtf(String tag, String msg) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(0, tag, msg, null, false, false);
        }
        return 0;
    }

    public static int secWtf(String tag, String msg) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(tag, msg);
        }
        return 0;
    }

    public static int wtfStack(String tag, String msg) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(0, tag, msg, null, true, false);
        }
        return 0;
    }

    public static int secWtfStack(String tag, String msg) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtfStack(tag, msg);
        }
        return 0;
    }

    public static int wtf(String tag, Throwable tr) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(0, tag, tr.getMessage(), tr, false, false);
        }
        return 0;
    }

    public static int secWtf(String tag, Throwable tr) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(tag, tr);
        }
        return 0;
    }

    public static int wtf(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(0, tag, msg, tr, false, false);
        }
        return 0;
    }

    public static int secWtf(String tag, String msg, Throwable tr) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(tag, msg, tr);
        }
        return 0;
    }

    static int wtf(int logId, String tag, String msg, Throwable tr, boolean localStack, boolean system) {
        TerribleFailure what = new TerribleFailure(msg, tr);
        int bytes = printlns(logId, 6, tag, msg, localStack ? what : tr);
        sWtfHandler.onTerribleFailure(tag, what, system);
        return bytes;
    }

    static void wtfQuiet(int logId, String tag, String msg, boolean system) {
        TerribleFailure what = new TerribleFailure(msg, null);
        sWtfHandler.onTerribleFailure(tag, what, system);
    }

    public static TerribleFailureHandler setWtfHandler(TerribleFailureHandler handler) {
        if (handler == null) {
            throw new NullPointerException("handler == null");
        }
        TerribleFailureHandler oldHandler = sWtfHandler;
        sWtfHandler = handler;
        return oldHandler;
    }

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        for (Throwable t = tr; t != null; t = t.getCause()) {
            if (t instanceof UnknownHostException) {
                return "";
            }
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new FastPrintWriter((Writer) sw, false, 256);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public static int println(int priority, String tag, String msg) {
        if (LogSwitcher.isShowingGlobalLog) {
            return println_native(0, priority, tag, msg);
        }
        return 0;
    }

    public static int printlns(int bufID, int priority, String tag, String msg, Throwable tr) {
        ImmediateLogWriter logWriter = new ImmediateLogWriter(bufID, priority, tag);
        int bufferSize = ((NoPreloadHolder.LOGGER_ENTRY_MAX_PAYLOAD - 2) - (tag != null ? tag.length() : 0)) - 32;
        LineBreakBufferedWriter lbbw = new LineBreakBufferedWriter(logWriter, Math.max(bufferSize, 100));
        lbbw.println(msg);
        if (tr != null) {
            Throwable t = tr;
            while (true) {
                if (t == null || (t instanceof UnknownHostException)) {
                    break;
                }
                if (t instanceof DeadSystemException) {
                    lbbw.println("DeadSystemException: The system died; earlier logs will point to the root cause");
                    break;
                }
                t = t.getCause();
            }
            if (t == null) {
                tr.printStackTrace(lbbw);
            }
        }
        lbbw.flush();
        return logWriter.getWritten();
    }

    static class NoPreloadHolder {
        public static final int LOGGER_ENTRY_MAX_PAYLOAD = Log.logger_entry_max_payload_native();

        NoPreloadHolder() {
        }
    }

    private static class ImmediateLogWriter extends Writer {
        private int bufID;
        private int priority;
        private String tag;
        private int written = 0;

        public ImmediateLogWriter(int bufID, int priority, String tag) {
            this.bufID = bufID;
            this.priority = priority;
            this.tag = tag;
        }

        public int getWritten() {
            return this.written;
        }

        @Override // java.io.Writer
        public void write(char[] cbuf, int off, int len) {
            this.written += Log.println_native(this.bufID, this.priority, this.tag, new String(cbuf, off, len));
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }
    }
}
