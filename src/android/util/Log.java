package android.util;

import android.annotation.SystemApi;
import android.os.DeadSystemException;
import android.util.secutil.LogSwitcher;
import com.android.internal.os.RuntimeInit;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.LineBreakBufferedWriter;
import dalvik.annotation.optimization.FastNative;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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
    private static TerribleFailureHandler sWtfHandler = new TerribleFailureHandler() { // from class: android.util.Log.1
        @Override // android.util.Log.TerribleFailureHandler
        public void onTerribleFailure(String str, TerribleFailure terribleFailure, boolean z) {
            RuntimeInit.wtf(str, terribleFailure, z);
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface Level {
    }

    public interface TerribleFailureHandler {
        void onTerribleFailure(String str, TerribleFailure terribleFailure, boolean z);
    }

    @FastNative
    public static native boolean isLoggable(String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int logger_entry_max_payload_native();

    public static native int println_native(int i, int i2, String str, String str2);

    public static class TerribleFailure extends Exception {
        TerribleFailure(String str, Throwable th) {
            super(str, th);
        }
    }

    private Log() {
    }

    public static int v(String str, String str2) {
        return println_native(0, 2, str, str2);
    }

    public static int secV(String str, String str2) {
        if (LogSwitcher.isShowingSecVLog) {
            return v(str, str2);
        }
        return 0;
    }

    public static int v(String str, String str2, Throwable th) {
        return printlns(0, 2, str, str2, th);
    }

    public static int secV(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecVLog) {
            return v(str, str2, th);
        }
        return 0;
    }

    public static int d(String str, String str2) {
        return println_native(0, 3, str, str2);
    }

    public static int secD(String str, String str2) {
        if (LogSwitcher.isShowingSecDLog) {
            return d(str, str2);
        }
        return 0;
    }

    public static int d(String str, String str2, Throwable th) {
        return printlns(0, 3, str, str2, th);
    }

    public static int secD(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecDLog) {
            return d(str, str2, th);
        }
        return 0;
    }

    public static int i(String str, String str2) {
        return println_native(0, 4, str, str2);
    }

    public static int secI(String str, String str2) {
        if (LogSwitcher.isShowingSecILog) {
            return i(str, str2);
        }
        return 0;
    }

    public static int i(String str, String str2, Throwable th) {
        return printlns(0, 4, str, str2, th);
    }

    public static int secI(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecILog) {
            return i(str, str2, th);
        }
        return 0;
    }

    public static int w(String str, String str2) {
        return println_native(0, 5, str, str2);
    }

    public static int secW(String str, String str2) {
        if (LogSwitcher.isShowingSecWLog) {
            return w(str, str2);
        }
        return 0;
    }

    public static int w(String str, String str2, Throwable th) {
        return printlns(0, 5, str, str2, th);
    }

    public static int secW(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecWLog) {
            return w(str, str2, th);
        }
        return 0;
    }

    public static int w(String str, Throwable th) {
        return printlns(0, 5, str, "", th);
    }

    public static int secW(String str, Throwable th) {
        if (LogSwitcher.isShowingSecWLog) {
            return w(str, th);
        }
        return 0;
    }

    public static int e(String str, String str2) {
        return println_native(0, 6, str, str2);
    }

    public static int secE(String str, String str2) {
        if (LogSwitcher.isShowingSecELog) {
            return e(str, str2);
        }
        return 0;
    }

    public static int e(String str, String str2, Throwable th) {
        return printlns(0, 6, str, str2, th);
    }

    public static int secE(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecELog) {
            return e(str, str2, th);
        }
        return 0;
    }

    public static int wtf(String str, String str2) {
        return wtf(0, str, str2, null, false, false);
    }

    public static int secWtf(String str, String str2) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(str, str2);
        }
        return 0;
    }

    public static int wtfStack(String str, String str2) {
        return wtf(0, str, str2, null, true, false);
    }

    public static int secWtfStack(String str, String str2) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtfStack(str, str2);
        }
        return 0;
    }

    public static int wtf(String str, Throwable th) {
        return wtf(0, str, th.getMessage(), th, false, false);
    }

    public static int secWtf(String str, Throwable th) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(str, th);
        }
        return 0;
    }

    public static int wtf(String str, String str2, Throwable th) {
        return wtf(0, str, str2, th, false, false);
    }

    public static int secWtf(String str, String str2, Throwable th) {
        if (LogSwitcher.isShowingSecWtfLog) {
            return wtf(str, str2, th);
        }
        return 0;
    }

    static int wtf(int i, String str, String str2, Throwable th, boolean z, boolean z2) {
        TerribleFailure terribleFailure = new TerribleFailure(str2, th);
        if (z) {
            th = terribleFailure;
        }
        int printlns = printlns(i, 6, str, str2, th);
        sWtfHandler.onTerribleFailure(str, terribleFailure, z2);
        return printlns;
    }

    static void wtfQuiet(int i, String str, String str2, boolean z) {
        sWtfHandler.onTerribleFailure(str, new TerribleFailure(str2, null), z);
    }

    public static TerribleFailureHandler setWtfHandler(TerribleFailureHandler terribleFailureHandler) {
        if (terribleFailureHandler == null) {
            throw new NullPointerException("handler == null");
        }
        TerribleFailureHandler terribleFailureHandler2 = sWtfHandler;
        sWtfHandler = terribleFailureHandler;
        return terribleFailureHandler2;
    }

    public static String getStackTraceString(Throwable th) {
        if (th == null) {
            return "";
        }
        for (Throwable th2 = th; th2 != null; th2 = th2.getCause()) {
            if (th2 instanceof UnknownHostException) {
                return "";
            }
        }
        StringWriter stringWriter = new StringWriter();
        FastPrintWriter fastPrintWriter = new FastPrintWriter((Writer) stringWriter, false, 256);
        th.printStackTrace(fastPrintWriter);
        fastPrintWriter.flush();
        return stringWriter.toString();
    }

    public static int println(int i, String str, String str2) {
        return println_native(0, i, str, str2);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static int logToRadioBuffer(int i, String str, String str2) {
        return println_native(1, i, str, str2);
    }

    public static int printlns(int i, int i2, String str, String str2, Throwable th) {
        ImmediateLogWriter immediateLogWriter = new ImmediateLogWriter(i, i2, str);
        LineBreakBufferedWriter lineBreakBufferedWriter = new LineBreakBufferedWriter(immediateLogWriter, Math.max(((PreloadHolder.LOGGER_ENTRY_MAX_PAYLOAD - 2) - (str != null ? str.length() : 0)) - 32, 100));
        lineBreakBufferedWriter.println(str2);
        if (th != null) {
            Throwable th2 = th;
            while (true) {
                if (th2 == null || (th2 instanceof UnknownHostException)) {
                    break;
                }
                if (th2 instanceof DeadSystemException) {
                    lineBreakBufferedWriter.println("DeadSystemException: The system died; earlier logs will point to the root cause");
                    break;
                }
                th2 = th2.getCause();
            }
            if (th2 == null) {
                th.printStackTrace(lineBreakBufferedWriter);
            }
        }
        lineBreakBufferedWriter.flush();
        return immediateLogWriter.getWritten();
    }

    static class PreloadHolder {
        public static final int LOGGER_ENTRY_MAX_PAYLOAD = Log.logger_entry_max_payload_native();

        PreloadHolder() {
        }
    }

    private static class ImmediateLogWriter extends Writer {
        private int bufID;
        private int priority;
        private String tag;
        private int written = 0;

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
        }

        public ImmediateLogWriter(int i, int i2, String str) {
            this.bufID = i;
            this.priority = i2;
            this.tag = str;
        }

        public int getWritten() {
            return this.written;
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) {
            this.written += Log.println_native(this.bufID, this.priority, this.tag, new String(cArr, i, i2));
        }
    }
}
