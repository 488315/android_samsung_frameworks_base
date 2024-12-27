package com.android.server.am;

import android.os.Trace;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;

import java.util.UUID;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TraceErrorLogger {
    public static void addProcessInfoAndErrorIdToTrace(String str, int i, UUID uuid) {
        StringBuilder m =
                StorageManagerService$$ExternalSyntheticOutline0.m(i, "ErrorId:", str, " ", "#");
        m.append(uuid.toString());
        Trace.traceCounter(64L, m.toString(), 1);
    }

    public static void addSubjectToTrace(String str, UUID uuid) {
        Trace.traceCounter(
                64L,
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "Subject(for ErrorId ", uuid.toString(), "):", str),
                1);
    }
}
