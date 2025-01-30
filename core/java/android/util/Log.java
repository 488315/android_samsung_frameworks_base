package android.util;

import android.annotation.SystemApi;
import android.p009os.DeadSystemException;
import android.util.secutil.LogSwitcher;
import com.android.internal.p029os.RuntimeInit;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.LineBreakBufferedWriter;
import dalvik.annotation.optimization.FastNative;
import java.io.PrintWriter;
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
  private static TerribleFailureHandler sWtfHandler =
      new TerribleFailureHandler() { // from class: android.util.Log.1
        @Override // android.util.Log.TerribleFailureHandler
        public void onTerribleFailure(String tag, TerribleFailure what, boolean system) {
          RuntimeInit.wtf(tag, what, system);
        }
      };

  @Retention(RetentionPolicy.SOURCE)
  public @interface Level {}

  public interface TerribleFailureHandler {
    void onTerribleFailure(String str, TerribleFailure terribleFailure, boolean z);
  }

  @FastNative
  public static native boolean isLoggable(String str, int i);

  /* JADX INFO: Access modifiers changed from: private */
  public static native int logger_entry_max_payload_native();

  public static native int println_native(int i, int i2, String str, String str2);

  public static class TerribleFailure extends Exception {
    TerribleFailure(String msg, Throwable cause) {
      super(msg, cause);
    }
  }

  private Log() {}

  /* renamed from: v */
  public static int m100v(String tag, String msg) {
    return println_native(0, 2, tag, msg);
  }

  public static int secV(String tag, String msg) {
    if (LogSwitcher.isShowingSecVLog) {
      return m100v(tag, msg);
    }
    return 0;
  }

  /* renamed from: v */
  public static int m101v(String tag, String msg, Throwable tr) {
    return printlns(0, 2, tag, msg, tr);
  }

  public static int secV(String tag, String msg, Throwable tr) {
    if (LogSwitcher.isShowingSecVLog) {
      return m101v(tag, msg, tr);
    }
    return 0;
  }

  /* renamed from: d */
  public static int m94d(String tag, String msg) {
    return println_native(0, 3, tag, msg);
  }

  public static int secD(String tag, String msg) {
    if (LogSwitcher.isShowingSecDLog) {
      return m94d(tag, msg);
    }
    return 0;
  }

  /* renamed from: d */
  public static int m95d(String tag, String msg, Throwable tr) {
    return printlns(0, 3, tag, msg, tr);
  }

  public static int secD(String tag, String msg, Throwable tr) {
    if (LogSwitcher.isShowingSecDLog) {
      return m95d(tag, msg, tr);
    }
    return 0;
  }

  /* renamed from: i */
  public static int m98i(String tag, String msg) {
    return println_native(0, 4, tag, msg);
  }

  public static int secI(String tag, String msg) {
    if (LogSwitcher.isShowingSecILog) {
      return m98i(tag, msg);
    }
    return 0;
  }

  /* renamed from: i */
  public static int m99i(String tag, String msg, Throwable tr) {
    return printlns(0, 4, tag, msg, tr);
  }

  public static int secI(String tag, String msg, Throwable tr) {
    if (LogSwitcher.isShowingSecILog) {
      return m99i(tag, msg, tr);
    }
    return 0;
  }

  /* renamed from: w */
  public static int m102w(String tag, String msg) {
    return println_native(0, 5, tag, msg);
  }

  public static int secW(String tag, String msg) {
    if (LogSwitcher.isShowingSecWLog) {
      return m102w(tag, msg);
    }
    return 0;
  }

  /* renamed from: w */
  public static int m103w(String tag, String msg, Throwable tr) {
    return printlns(0, 5, tag, msg, tr);
  }

  public static int secW(String tag, String msg, Throwable tr) {
    if (LogSwitcher.isShowingSecWLog) {
      return m103w(tag, msg, tr);
    }
    return 0;
  }

  /* renamed from: w */
  public static int m104w(String tag, Throwable tr) {
    return printlns(0, 5, tag, "", tr);
  }

  public static int secW(String tag, Throwable tr) {
    if (LogSwitcher.isShowingSecWLog) {
      return m104w(tag, tr);
    }
    return 0;
  }

  /* renamed from: e */
  public static int m96e(String tag, String msg) {
    return println_native(0, 6, tag, msg);
  }

  public static int secE(String tag, String msg) {
    if (LogSwitcher.isShowingSecELog) {
      return m96e(tag, msg);
    }
    return 0;
  }

  /* renamed from: e */
  public static int m97e(String tag, String msg, Throwable tr) {
    return printlns(0, 6, tag, msg, tr);
  }

  public static int secE(String tag, String msg, Throwable tr) {
    if (LogSwitcher.isShowingSecELog) {
      return m97e(tag, msg, tr);
    }
    return 0;
  }

  public static int wtf(String tag, String msg) {
    return wtf(0, tag, msg, null, false, false);
  }

  public static int secWtf(String tag, String msg) {
    if (LogSwitcher.isShowingSecWtfLog) {
      return wtf(tag, msg);
    }
    return 0;
  }

  public static int wtfStack(String tag, String msg) {
    return wtf(0, tag, msg, null, true, false);
  }

  public static int secWtfStack(String tag, String msg) {
    if (LogSwitcher.isShowingSecWtfLog) {
      return wtfStack(tag, msg);
    }
    return 0;
  }

  public static int wtf(String tag, Throwable tr) {
    return wtf(0, tag, tr.getMessage(), tr, false, false);
  }

  public static int secWtf(String tag, Throwable tr) {
    if (LogSwitcher.isShowingSecWtfLog) {
      return wtf(tag, tr);
    }
    return 0;
  }

  public static int wtf(String tag, String msg, Throwable tr) {
    return wtf(0, tag, msg, tr, false, false);
  }

  public static int secWtf(String tag, String msg, Throwable tr) {
    if (LogSwitcher.isShowingSecWtfLog) {
      return wtf(tag, msg, tr);
    }
    return 0;
  }

  static int wtf(
      int logId, String tag, String msg, Throwable tr, boolean localStack, boolean system) {
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
    return println_native(0, priority, tag, msg);
  }

  @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
  public static int logToRadioBuffer(int priority, String tag, String message) {
    return println_native(1, priority, tag, message);
  }

  public static int printlns(int bufID, int priority, String tag, String msg, Throwable tr) {
    ImmediateLogWriter logWriter = new ImmediateLogWriter(bufID, priority, tag);
    int bufferSize =
        ((PreloadHolder.LOGGER_ENTRY_MAX_PAYLOAD - 2) - (tag != null ? tag.length() : 0)) - 32;
    LineBreakBufferedWriter lbbw =
        new LineBreakBufferedWriter(logWriter, Math.max(bufferSize, 100));
    lbbw.println(msg);
    if (tr != null) {
      Throwable t = tr;
      while (true) {
        if (t == null || (t instanceof UnknownHostException)) {
          break;
        }
        if (t instanceof DeadSystemException) {
          lbbw.println(
              "DeadSystemException: The system died; earlier logs will point to the root cause");
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

  static class PreloadHolder {
    public static final int LOGGER_ENTRY_MAX_PAYLOAD = Log.logger_entry_max_payload_native();

    PreloadHolder() {}
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
      this.written +=
          Log.println_native(this.bufID, this.priority, this.tag, new String(cbuf, off, len));
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {}

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {}
  }
}
