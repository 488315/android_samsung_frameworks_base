package com.android.server.am;

import android.os.Trace;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;

import java.util.UUID;

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
