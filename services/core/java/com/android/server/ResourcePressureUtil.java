package com.android.server;

import android.os.StrictMode;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class ResourcePressureUtil {
    public static final List PSI_FILES =
            Arrays.asList("/proc/pressure/memory", "/proc/pressure/cpu", "/proc/pressure/io");

    public static String currentPsiState() {
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        final StringWriter stringWriter = new StringWriter();
        try {
            PSI_FILES.stream()
                    .map(new ResourcePressureUtil$$ExternalSyntheticLambda0())
                    .forEach(
                            new Consumer() { // from class:
                                             // com.android.server.ResourcePressureUtil$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    stringWriter.append((CharSequence) obj);
                                }
                            });
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            String stringWriter2 = stringWriter.toString();
            return stringWriter2.length() > 0 ? stringWriter2.concat("\n") : stringWriter2;
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
            throw th;
        }
    }
}
