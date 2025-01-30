package com.android.server.chimera.umr;

import android.os.StrictMode;
import android.util.Slog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/* loaded from: classes.dex */
public abstract class KernelMemoryProxy$ReclaimerLog {
  public static boolean RECLAIMER_LOG_SUPPORT = true;
  public static boolean RECLAIMER_LOG_SUPPORT_CHECKED = false;
  public static String reclaimerLogPath = "/proc/reclaimer_log";

  public static boolean reclaimerLogSupported() {
    if (!RECLAIMER_LOG_SUPPORT_CHECKED) {
      RECLAIMER_LOG_SUPPORT_CHECKED = true;
      StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
      if (!new File(reclaimerLogPath).exists()) {
        RECLAIMER_LOG_SUPPORT = false;
      }
      StrictMode.setThreadPolicy(allowThreadDiskReads);
    }
    return RECLAIMER_LOG_SUPPORT;
  }

  public static void write(String str, boolean z) {
    OutputStreamWriter outputStreamWriter;
    if (z) {
      Slog.i("UMR", str);
    }
    if (reclaimerLogSupported()) {
      StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
      OutputStreamWriter outputStreamWriter2 = null;
      try {
        try {
          try {
            outputStreamWriter =
                new OutputStreamWriter(
                    new FileOutputStream(reclaimerLogPath), StandardCharsets.UTF_8);
          } catch (Exception unused) {
          }
        } catch (Exception e) {
          e = e;
        }
      } catch (Throwable th) {
        th = th;
      }
      try {
        outputStreamWriter.write("UMR: " + str);
        outputStreamWriter.close();
      } catch (Exception e2) {
        e = e2;
        outputStreamWriter2 = outputStreamWriter;
        e.printStackTrace();
        if (outputStreamWriter2 != null) {
          outputStreamWriter2.close();
        }
        StrictMode.setThreadPolicy(allowThreadDiskWrites);
      } catch (Throwable th2) {
        th = th2;
        outputStreamWriter2 = outputStreamWriter;
        if (outputStreamWriter2 != null) {
          try {
            outputStreamWriter2.close();
          } catch (Exception unused2) {
          }
        }
        throw th;
      }
      StrictMode.setThreadPolicy(allowThreadDiskWrites);
    }
  }

  public static void write(String str) {
    write(str, true);
  }
}
