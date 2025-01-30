package com.android.server.am.pmm;

import android.util.Slog;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/* loaded from: classes.dex */
public abstract class HeapDumpHelper {
    public static final DateTimeFormatter LOG_NAME_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss", Locale.ROOT);

    public static void cleanUpPath() {
        try {
            for (File file : new File("/data/log/core/").listFiles()) {
                if (file.getName().startsWith("heapdump-dmabufleak")) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            Slog.i("pmm.HeapDumpHelper", e.getMessage());
        }
    }
}
