package com.android.server.chimera.umr;

import android.os.StrictMode;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class KernelMemoryProxy$ReclaimerLog {
    public static boolean sReclaimerLogSupport = true;
    public static boolean sReclaimerLogSupportChecked;

    public static void write(String str, boolean z) {
        if (z) {
            Slog.i("UMR", str);
        }
        if (!sReclaimerLogSupportChecked) {
            sReclaimerLogSupportChecked = true;
            StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
            if (!BatteryService$$ExternalSyntheticOutline0.m45m("/proc/reclaimer_log")) {
                sReclaimerLogSupport = false;
            }
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
        if (!sReclaimerLogSupport) {
            return;
        }
        StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        OutputStreamWriter outputStreamWriter = null;
        try {
            try {
                OutputStreamWriter outputStreamWriter2 =
                        new OutputStreamWriter(
                                new FileOutputStream("/proc/reclaimer_log"),
                                StandardCharsets.UTF_8);
                try {
                    outputStreamWriter2.write("UMR: " + str);
                    outputStreamWriter2.close();
                } catch (Exception e) {
                    e = e;
                    outputStreamWriter = outputStreamWriter2;
                    e.printStackTrace();
                    if (outputStreamWriter != null) {
                        outputStreamWriter.close();
                    }
                    StrictMode.setThreadPolicy(allowThreadDiskWrites);
                } catch (Throwable th) {
                    th = th;
                    outputStreamWriter = outputStreamWriter2;
                    if (outputStreamWriter != null) {
                        try {
                            outputStreamWriter.close();
                        } catch (Exception unused) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
